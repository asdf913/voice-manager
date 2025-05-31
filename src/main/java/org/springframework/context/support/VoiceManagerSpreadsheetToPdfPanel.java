package org.springframework.context.support;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableConsumerUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageUtil;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.d2ab.function.LanguageCodeToTextObjIntFunction;
import org.d2ab.function.ObjIntFunction;
import org.d2ab.function.ObjIntFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.ThrowingRunnableUtil;
import org.springframework.beans.factory.InitializingBean;

import io.github.toolfactory.narcissus.Narcissus;
import net.miginfocom.swing.MigLayout;

public class VoiceManagerSpreadsheetToPdfPanel extends JPanel implements InitializingBean, ActionListener {

	private static final long serialVersionUID = -7995853525217556061L;

	private AbstractButton btnExecute = null;

	private VoiceManagerSpreadsheetToPdfPanel() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
		setLayout(this, new MigLayout());// TODO
		//
		final List<Field> fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(this))),
				f -> Objects.equals(Util.getName(f), "component")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f == null || Narcissus.getField(this, f) != null) {
			//
			add(btnExecute = new JButton("Execute"));
			//
		} // if
			//
		Util.addActionListener(btnExecute, this);
		//
	}

	private static void setLayout(final Container instance, final LayoutManager layoutManager) {
		if (instance != null) {
			instance.setLayout(layoutManager);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		if (Objects.equals(Util.getSource(evt), btnExecute)) {
			//
			Drawing<?> drawingPatriarch = null;
			//
			Iterable<Data> dataList = null;
			//
			File file = getSelectedFile();
			//
			try (final Workbook wb = testAndApply(Util::isFile, file, WorkbookFactory::create, null)) {
				//
				final Sheet sheet = testAndApply(x -> WorkbookUtil.getNumberOfSheets(wb) == 1, wb,
						x -> WorkbookUtil.getSheetAt(x, 0), null);
				//
				drawingPatriarch = getDrawingPatriarch(sheet);
				//
				dataList = getDataIterable(
						testAndApply(Objects::nonNull, Util.iterator(sheet), IteratorUtils::toList, null));
				//
			} catch (final IOException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			final PDDocument pdDocument = new PDDocument();
			//
			final PDPage pdPage = new PDPage(PDRectangle.A4);// TODO
			//
			pdDocument.addPage(pdPage);
			//
			final PDRectangle mediaBox = PDPageUtil.getMediaBox(pdPage);
			//
			Data data = null;
			//
			float ratioMin = 0;
			//
			try {
				//
				ratioMin = drawImage(drawingPatriarch, pdDocument, pdPage);
				//
			} catch (final IOException e) {
				//
				throw new RuntimeException(e);
				//
			} // try
				//
			PDComplexFileSpecification pdComplexFileSpecification = null;
			//
			PDAnnotationFileAttachment pdAnnotationFileAttachment = null;
			//
			final SpeechApi speechApi = new SpeechApiImpl();
			//
			File tempFile = null;
			//
			final int size = 10;// TODO
			//
			ObjIntFunction<String, String> objIntFunction = null;
			//
			for (int i = 0; i < IterableUtils.size(dataList); i++) {
				//
				if ((data = IterableUtils.get(dataList, i)) == null) {
					//
					continue;
					//
				} // if
					//
				(pdComplexFileSpecification = new PDComplexFileSpecification()).setFile(i + ".wav");
				//
				testAndAccept(Objects::nonNull,
						tempFile = createTempFile(RandomStringUtils.secureStrong().nextAlphabetic(3), null, e -> {
							//
							throw new RuntimeException(e);
							//
						}), Util::deleteOnExit);
				//
				speechApi.writeVoiceToFile(data.text, getVoice(speechApi,
						objIntFunction = ObjectUtils.getIfNull(objIntFunction, LanguageCodeToTextObjIntFunction::new),
						data.voice)
				//
						, i * -1// TODO
						//
						, 100, null, tempFile);
				//
				try (final InputStream is = Files.newInputStream(Util.toPath(tempFile))) {
					//
					pdComplexFileSpecification.setEmbeddedFile(new PDEmbeddedFile(pdDocument, is));
					//
				} catch (final IOException e) {
					//
					throw new RuntimeException(e);
					//
				} // try
					//
				(pdAnnotationFileAttachment = new PDAnnotationFileAttachment()).setFile(pdComplexFileSpecification);
				//
				pdAnnotationFileAttachment.setRectangle(new PDRectangle(floatValue(data.x, 0) * ratioMin,
						getHeight(mediaBox) - (floatValue(data.y, size) + floatValue(data.height, size)) * ratioMin
						//
						, floatValue(data.width, size) * ratioMin
						//
						, floatValue(data.height, size) * ratioMin)
				//
				);
				//
				pdAnnotationFileAttachment.setContents(data.contents);
				//
				Util.add(getAnnotations(pdPage, e -> {
					//
					throw new RuntimeException(e);
					//
				}), pdAnnotationFileAttachment);
				//
			} // for
				//
			testAndAccept(x -> !isTestMode(), file = Util.toFile(Path.of("test.pdf")), x -> {// TODO
				//
				System.out.println(Util.getAbsolutePath(x));
				//
				save(pdDocument, x, e -> {
					//
					throw new RuntimeException(e);
					//
				});
				//
			});
			//
		} // if
			//
	}

	@Nullable
	private static File createTempFile(final String prefix, @Nullable final String suffix,
			final Consumer<IOException> consumer) {
		//
		try {
			//
			return testAndApply(Objects::nonNull, prefix, x -> File.createTempFile(x, suffix), null);
			//
		} catch (final IOException e) {
			//
			Util.accept(consumer, e);
			//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static File getSelectedFile() {
		//
		final JFileChooser jfc = new JFileChooser();
		//
		if (Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(), !isTestMode())
				&& jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			//
			return jfc.getSelectedFile();
			//
		} // if
			//
		return null;
		//
	}

	private static float drawImage(@Nullable final Drawing<?> drawing, final PDDocument pdDocument, final PDPage pdPage)
			throws IOException {
		//
		final List<?> list = testAndApply(Objects::nonNull, Util.iterator(drawing), IteratorUtils::toList, null);
		//
		PictureData pictureData = null;
		//
		final PDRectangle mediaBox = PDPageUtil.getMediaBox(pdPage);
		//
		float pageWidth = getWidth(mediaBox);
		//
		float imageWidth, imageHeight, pageHeight, ratioMin = 0;
		//
		PDImageXObject pdImageXObject = null;
		//
		for (int i = 0; i < IterableUtils.size(list); i++) {
			//
			if (IterableUtils.get(list, i) instanceof Picture picture
					&& (pictureData = getPictureData(picture)) != null) {
				//
				try (final PDPageContentStream cs = new PDPageContentStream(pdDocument, pdPage)) {
					//
					imageWidth = getWidth(pdImageXObject = PDImageXObject.createFromByteArray(pdDocument,
							pictureData.getData(), null));
					//
					imageHeight = getHeight(pdImageXObject);
					//
					pageHeight = imageHeight * (ratioMin = Math.min(imageWidth == 0 ? 0 : pageWidth / imageWidth,
							imageHeight == 0 ? 0 : getHeight(mediaBox) / imageHeight));
					//
					cs.drawImage(pdImageXObject, 0, (imageHeight - pageHeight) / 2, imageWidth * ratioMin, pageHeight);
					//
				} // try
					//
			} // if
				//
		} // for
			//
		return ratioMin;
		//
	}

	@Nullable
	private static List<PDAnnotation> getAnnotations(final PDPage instance, final Consumer<IOException> consumer) {
		//
		try {
			//
			return PDPageUtil.getAnnotations(instance);
			//
		} catch (final IOException e) {
			//
			Util.accept(consumer, e);
			//
		} // try
			//
		return null;
		//
	}

	private static void save(@Nullable final PDDocument instance, final File file,
			final Consumer<IOException> consumer) {
		//
		try {
			//
			if (instance != null) {
				//
				instance.save(file);
				//
			} // if
				//
		} catch (final IOException e) {
			//
			Util.accept(consumer, e);
			//
		} // try
			//
	}

	private static class Data {

		@Target(ElementType.FIELD)
		@Retention(RetentionPolicy.RUNTIME)
		private @interface Note {
			String value();
		}

		@Note("Text")
		private String text = null;

		@Note("Voice")
		private String voice = null;

		private String contents = null;

		@Note("Width")
		private Float width = null;

		@Note("Height")
		private Float height = null;

		@Note("x")
		private Float x = null;

		private Float y = null;

	}

	public static void main(final String[] args) throws Exception {
		//
		final VoiceManagerSpreadsheetToPdfPanel instance = new VoiceManagerSpreadsheetToPdfPanel();
		//
		instance.afterPropertiesSet();
		//
		final JFrame jFrame = !GraphicsEnvironment.isHeadless() ? new JFrame() : null;
		//
		if (jFrame != null) {
			//
			jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			//
			jFrame.add(instance);
			//
			jFrame.pack();
			//
			jFrame.setVisible(true);
			//
		} // if
			//
	}

	private static int getHeight(@Nullable final PDImage instance) {
		//
		final Map<String, String> map = Map.of("org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject", "stream",
				"org.apache.pdfbox.pdmodel.graphics.image.PDInlineImage", "parameters");
		//
		final Iterable<Entry<String, String>> entrySet = Util.entrySet(map);
		//
		if (Util.iterator(entrySet) != null) {
			//
			final Class<?> clz = Util.getClass(instance);
			//
			final String name = Util.getName(Util.getClass(instance));
			//
			List<Field> fs = null;
			//
			Field f = null;
			//
			for (final Entry<String, String> entry : entrySet) {
				//
				if (!Objects.equals(name, Util.getKey(entry))) {
					//
					continue;
					//
				} // if
					//
				testAndRunThrows(
						IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
								x -> Objects.equals(Util.getName(x), Util.getValue(entry))))) > 1,
						() -> {
							//
							throw new IllegalStateException();
							//
						});
				//
				if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
						&& Narcissus.getField(instance, f) == null) {
					//
					return 0;
					//
				} // if
					//
					//
			} // for
				//
		} // if
			//
		return instance != null ? instance.getHeight() : 0;
		//
	}

	private static int getWidth(@Nullable final PDImage instance) {
		//
		final Map<String, String> map = Map.of("org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject", "stream",
				"org.apache.pdfbox.pdmodel.graphics.image.PDInlineImage", "parameters");
		//
		final Iterable<Entry<String, String>> entrySet = Util.entrySet(map);
		//
		if (Util.iterator(entrySet) != null) {
			//
			final Class<?> clz = Util.getClass(instance);
			//
			final String name = Util.getName(Util.getClass(instance));
			//
			List<Field> fs = null;
			//
			Field f = null;
			//
			for (final Entry<String, String> entry : entrySet) {
				//
				if (!Objects.equals(name, Util.getKey(entry))) {
					//
					continue;
					//
				} // if
					//
				testAndRunThrows(
						IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
								x -> Objects.equals(Util.getName(x), Util.getValue(entry))))) > 1,
						() -> {
							//
							throw new IllegalStateException();
							//
						});
				//
				if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
						&& Narcissus.getField(instance, f) == null) {
					//
					return 0;
					//
				} // if
					//
					//
			} // for
				//
		} // if
			//
		return instance != null ? instance.getWidth() : 0;
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, @Nullable final T value,
			final FailableConsumer<T, E> consumer) throws E {
		if (Util.test(predicate, value)) {
			FailableConsumerUtil.accept(consumer, value);
		}
	}

	@Nullable
	private static Iterable<Data> getDataIterable(final Iterable<Row> rows) {
		//
		Row row = null;
		//
		Map<Integer, String> map = null;
		//
		Collection<Data> dataList = null;
		//
		Field f = null;
		//
		Data data = null;
		//
		Cell cell = null;
		//
		for (int i = 0; i < IterableUtils.size(rows); i++) {
			//
			if ((row = IterableUtils.get(rows, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (map == null) {
				//
				map = toMap(row);
				//
			} else {
				//
				Util.add(dataList = ObjectUtils.getIfNull(dataList, ArrayList::new), data = new Data());
				//
				for (int j = 0; j < row.getLastCellNum(); j++) {
					//
					if ((f = getFieldByName(FieldUtils.getAllFieldsList(Data.class),
							map.get(Integer.valueOf(j)))) == null || (cell = row.getCell(j)) == null) {
						//
						continue;
						//
					} // if
						//
					setField(data, f, testAndApply(x -> Objects.equals(CellUtil.getCellType(x), CellType.NUMERIC), cell,
							VoiceManagerSpreadsheetToPdfPanel::getNumericCellValue, CellUtil::getStringCellValue));
					//
				} // for
					//
			} // if
				//
		} // for
			//
		return dataList;
		//
	}

	private static void setField(@Nullable final Object instance, @Nullable final Field field, final Object value) {
		//
		if (instance == null || field == null) {
			//
			return;
			//
		} // if
			//
		if (Objects.equals(Util.getType(field), Float.class)) {
			//
			Narcissus.setField(instance, field, value instanceof Number number ? floatValue(number, 0) : value);
			//
		} else {
			//
			Narcissus.setField(instance, field, value);
			//
		} // if
			//
	}

	@Nullable
	private static Map<Integer, String> toMap(@Nullable final Row row) {
		//
		Map<Integer, String> map = null;
		//
		for (int j = 0; row != null && j < row.getLastCellNum(); j++) {
			//
			Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), Integer.valueOf(j),
					CellUtil.getStringCellValue(row.getCell(j)));
			//
		} // for
			//
		return map;
		//
	}

	@Nullable
	private static Double getNumericCellValue(@Nullable final Cell instance) {
		return instance != null ? Double.valueOf(instance.getNumericCellValue()) : null;
	}

	@Nullable
	private static PictureData getPictureData(@Nullable final Picture instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Map<String, String> map = new LinkedHashMap<>(Map.of("org.apache.poi.xssf.streaming.SXSSFPicture",
				"_picture", "org.apache.poi.xssf.usermodel.XSSFPicture", "ctPicture"));
		//
		Util.putAll(map,
				Util.collect(
						Stream.of("org.apache.poi.hssf.usermodel.HSSFPicture",
								"org.apache.poi.hssf.usermodel.HSSFObjectData"),
						Collectors.toMap(Function.identity(), x -> "_optRecord")));
		//
		final Iterable<Entry<String, String>> entrySet = Util.entrySet(map);
		//
		if (Util.iterator(Util.entrySet(map)) != null) {
			//
			final Class<?> clz = Util.getClass(instance);
			//
			final String name = Util.getName(Util.getClass(instance));
			//
			List<Field> fs = null;
			//
			Field f = null;
			//
			for (final Entry<String, String> entry : entrySet) {
				//
				if (!Objects.equals(name, Util.getKey(entry))) {
					//
					continue;
					//
				} // if
					//
				testAndRunThrows(
						IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
								x -> Objects.equals(Util.getName(x), Util.getValue(entry))))) > 1,
						() -> {
							//
							throw new IllegalStateException();
							//
						});
				//
				if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
						&& Narcissus.getField(instance, f) == null) {
					//
					return null;
					//
				} // if
					//
					//
			} // for
				//
		} // if
			//
		return instance.getPictureData();
		//
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean b,
			final ThrowingRunnable<E> throwingRunnable) throws E {
		if (b) {
			ThrowingRunnableUtil.runThrows(throwingRunnable);
		}
	}

	private static String getVoice(final SpeechApi speechApi, final ObjIntFunction<String, String> objIntFunction,
			final String voice) {
		//
		IValue0<String> ivalue0 = null;
		//
		final String[] voiceIds = SpeechApi.getVoiceIds(speechApi);
		//
		final int length = voiceIds != null ? voiceIds.length : 0;
		//
		for (int j = 0; j < length; j++) {
			//
			if (!StringUtils.equalsIgnoreCase(ArrayUtils.get(voiceIds, j), voice)) {
				//
				continue;
				//
			} // if
				//
			testAndRunThrows(ivalue0 != null, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			ivalue0 = Unit.with(ArrayUtils.get(voiceIds, j));
			//
		} // for
			//
		if (ivalue0 != null) {
			//
			return IValue0Util.getValue0(ivalue0);
			//
		} // if
			//
		for (int j = 0; j < length; j++) {
			//
			if (!StringUtils.containsIgnoreCase(ArrayUtils.get(voiceIds, j), voice)) {
				//
				continue;
				//
			} // if
				//
			testAndRunThrows(ivalue0 != null, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			ivalue0 = Unit.with(ArrayUtils.get(voiceIds, j));
			//
		} // for
			//
		if (ivalue0 != null) {
			//
			return IValue0Util.getValue0(ivalue0);
			//
		} // if
			//
		String voiceAttribute = null;
		//
		for (int j = 0; j < length; j++) {
			//
			if (!(StringUtils.equalsIgnoreCase(
					voiceAttribute = SpeechApi.getVoiceAttribute(speechApi, ArrayUtils.get(voiceIds, j), "Language"),
					voice) || Objects.equals(ObjIntFunctionUtil.apply(objIntFunction, voiceAttribute, 16), voice))) {
				//
				continue;
				//
			} // if
				//
			testAndRunThrows(ivalue0 != null, () -> {
				//
				throw new IllegalStateException();
				//
			});
			//
			ivalue0 = Unit.with(ArrayUtils.get(voiceIds, j));
			//
		} // for
			//
		return IValue0Util.getValue0(ivalue0);
		//
	}

	@Nullable
	private static Drawing<?> getDrawingPatriarch(@Nullable final Sheet instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Map<String, String> map = new LinkedHashMap<>(
				Collections.singletonMap("org.apache.poi.hssf.usermodel.HSSFSheet", "_book"));
		//
		Util.putAll(map,
				Util.collect(
						Stream.of("org.apache.poi.xssf.streaming.SXSSFSheet",
								"org.apache.poi.xssf.streaming.DeferredSXSSFSheet"),
						Collectors.toMap(Function.identity(), x -> "_sh")));
		//
		Util.putAll(map,
				Util.collect(Stream.of("org.apache.poi.xssf.usermodel.XSSFDialogsheet",
						"org.apache.poi.xssf.usermodel.XSSFSheet", "org.apache.poi.xssf.usermodel.XSSFChartSheet"),
						Collectors.toMap(Function.identity(), x -> "worksheet")));
		//
		final Iterable<Entry<String, String>> entrySet = Util.entrySet(map);
		//
		if (Util.iterator(Util.entrySet(map)) != null) {
			//
			final Class<?> clz = Util.getClass(instance);
			//
			final String name = Util.getName(Util.getClass(instance));
			//
			List<Field> fs = null;
			//
			Field f = null;
			//
			for (final Entry<String, String> entry : entrySet) {
				//
				if (!Objects.equals(name, Util.getKey(entry))) {
					//
					continue;
					//
				} // if
					//
				testAndRunThrows(
						IterableUtils.size(fs = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(clz)),
								x -> Objects.equals(Util.getName(x), Util.getValue(entry))))) > 1,
						() -> {
							//
							throw new IllegalStateException();
							//
						});
				//
				if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
						&& Narcissus.getField(instance, f) == null) {
					//
					return null;
					//
				} // if
					//
					//
			} // for
				//
		} // if
			//
		return instance.getDrawingPatriarch();
		//
	}

	private static float getWidth(@Nullable final PDRectangle instance) {
		//
		final List<Field> fs = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(Util.getClass(instance)), Arrays::stream, null),
				x -> Objects.equals(Util.getName(x), "rectArray")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return 0;
			//
		} // if
			//
		return instance != null ? instance.getWidth() : 0;
		//
	}

	private static float getHeight(@Nullable final PDRectangle instance) {
		//
		final List<Field> fs = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredFields(Util.getClass(instance)), Arrays::stream, null),
				x -> Objects.equals(Util.getName(x), "rectArray")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return 0;
			//
		} // if
			//
		return instance != null ? instance.getHeight() : 0;
		//
	}

	private static boolean isTestMode() {
		return Util.forName("org.junit.jupiter.api.Test") != null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static float floatValue(@Nullable final Number instance, final float defaultValue) {
		return instance != null ? instance.floatValue() : defaultValue;
	}

	@Nullable
	private static Field getFieldByName(final Collection<Field> collection, final String name) {
		//
		final List<Field> fs = Util
				.toList(Util.filter(Util.stream(collection), x -> Objects.equals(Util.getName(x), name)));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		return testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
	}

}