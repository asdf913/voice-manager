package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.mariten.kanatools.KanaConverter;

import domain.Voice;
import fr.free.nrw.jakaroma.Jakaroma;
import mapper.VoiceMapper;
import net.miginfocom.swing.MigLayout;

public class VoiceManager extends JFrame implements ActionListener, EnvironmentAware {

	private static final long serialVersionUID = 6093437131552718994L;

	private static Logger LOG = LoggerFactory.getLogger(VoiceManager.class);

	private static Pattern PATTERN_CONTENT_INFO_MP3 = Pattern.compile("^MPEG ADTS, layer III.+$");

	private static final String WRAP = "wrap";

	private PropertyResolver propertyResolver = null;

	private JTextComponent tfFolder, tfFile, tfFileLength, tfFileDigest, tfText, tfHiragana, tfKatakana,
			tfRomaji = null;

	private AbstractButton btnConvertToRomaji, btnConvertToKatakana, btnCopyRomaji, btnExecute, btnExport = null;

	private SqlSessionFactory sqlSessionFactory = null;

	private String voiceFolder = null;

	private String outputFolder = null;

	private Map<String, String> outputFolderFileNameExpressions = null;

	private VoiceManager() {
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	public void setSqlSessionFactory(final SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setVoiceFolder(final String voiceFolder) {
		this.voiceFolder = voiceFolder;
	}

	public void setOutputFolder(final String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public void setOutputFolderFileNameExpressions(final Object value) throws JsonProcessingException {
		//
		if (value == null) {
			//
			this.outputFolderFileNameExpressions = null;
			//
			return;
			//
		} // if
			//
		final Map<?, ?> map = cast(Map.class, value);
		//
		if (map != null && map.entrySet() != null) {
			//
			for (final Entry<?, ?> entry : map.entrySet()) {
				//
				if (entry == null || (outputFolderFileNameExpressions = ObjectUtils
						.getIfNull(outputFolderFileNameExpressions, LinkedHashMap::new)) == null) {
					continue;
				} // if
					//
				outputFolderFileNameExpressions.put(toString(entry.getKey()), toString(entry.getValue()));
				//
			} // for
				//
			outputFolderFileNameExpressions = ObjectUtils.getIfNull(outputFolderFileNameExpressions,
					Collections::emptyMap);
			//
			return;
			//
		} // if
			//
		final Object object = testAndApply(StringUtils::isNotEmpty, toString(value),
				x -> new ObjectMapper().readValue(x, Object.class), null);
		//
		if (object instanceof Map || object == null) {
			setOutputFolderFileNameExpressions(object);
		} else {
			throw new IllegalArgumentException(toString(object.getClass()));
		} // if
			//
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	private void init() {
		//
		final File folder = testAndApply(StringUtils::isNotBlank, this.voiceFolder, File::new, null);
		//
		add(new JLabel("Text"));
		//
		add(tfText = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.text")));
		//
		add(btnConvertToRomaji = new JButton("Convert"), WRAP);
		//
		add(new JLabel("Romaji"));
		//
		add(tfRomaji = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.romaji")));
		//
		add(btnCopyRomaji = new JButton("Copy"), WRAP);
		//
		final String wrap = String.format("span %1$s,growx,%2$s", 2, WRAP);
		//
		add(new JLabel("Hiragana"));
		//
		add(tfHiragana = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.hiragana")));
		//
		add(btnConvertToKatakana = new JButton("Convert"), WRAP);
		//
		//
		add(new JLabel("Katakana"));
		//
		add(tfKatakana = new JTextField(
				getProperty(propertyResolver, "org.springframework.context.support.VoiceManager.katakana")), wrap);
		//
		add(new JLabel());
		//
		add(btnExecute = new JButton("Execute"));
		//
		add(btnExport = new JButton("Export"), WRAP);
		//
		add(new JLabel("Folder"));
		//
		add(tfFolder = new JTextField(folder != null ? folder.getAbsolutePath() : null), wrap);
		//
		add(new JLabel("File"));
		//
		add(tfFile = new JTextField(), wrap);
		//
		add(new JLabel("File Length"));
		//
		add(tfFileLength = new JTextField(), wrap);
		//
		add(new JLabel("File Digest"));
		//
		add(tfFileDigest = new JTextField(), wrap);
		//
		setEditable(false, tfFolder, tfFile, tfFileLength, tfFileDigest);
		//
		addActionListener(this, btnExecute, btnConvertToRomaji, btnConvertToKatakana, btnCopyRomaji, btnExport);
		//
		setPreferredWidth(intValue(
				orElse(max(map(Stream.of(tfFolder, tfFile, tfFileLength, tfFileDigest, tfText, tfHiragana, tfKatakana,
						tfRomaji), VoiceManager::getPreferredWidth), Comparator.comparing(x -> intValue(x, 0))), null),
				0) - intValue(getPreferredWidth(btnConvertToRomaji), 0), tfText, tfRomaji, tfHiragana);
		//
		setEnabled(btnExecute, folder != null && folder.exists() && folder.isDirectory());
		//
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(instance.getClass()) || mapper != null) ? instance.map(mapper)
				: null;
		//
	}

	private static <T> Optional<T> max(final Stream<T> instance, final Comparator<? super T> comparator) {
		//
		return instance != null && (Proxy.isProxyClass(instance.getClass()) || comparator != null)
				? instance.max(comparator)
				: null;
		//
	}

	private static <T> T orElse(final Optional<T> instance, final T other) {
		return instance != null ? instance.orElse(other) : null;
	}

	private static void setEnabled(final Component instance, final boolean b) {
		if (instance != null) {
			instance.setEnabled(b);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> instance, final T value)
			throws E {
		return instance != null ? instance.apply(value) : null;
	}

	private static void setEditable(final boolean editable, final JTextComponent... jtcs) {
		//
		JTextComponent jtc = null;
		//
		for (int i = 0; jtcs != null && i < jtcs.length; i++) {
			//
			if ((jtc = jtcs[i]) == null) {
				continue;
			} // if
				//
			jtc.setEditable(editable);
			//
		} // for
			//
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static void addActionListener(final ActionListener actionListener, final AbstractButton... abs) {
		//
		AbstractButton ab = null;
		//
		for (int i = 0; abs != null && i < abs.length; i++) {
			//
			if ((ab = abs[i]) == null) {
				continue;
			} // if
				//
			ab.addActionListener(actionListener);
			//
		} // for
			//
	}

	private static String getProperty(final PropertyResolver instance, final String key) {
		return instance != null ? instance.getProperty(key) : null;
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		//
		final Object source = getSource(evt);
		//
		if (Objects.equals(source, btnExecute)) {
			//
			forEach(Stream.of(tfFile, tfFileLength, tfFileDigest), x -> setText(x, null));
			//
			final JFileChooser jfc = new JFileChooser(".");
			//
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				//
				final File selectedFile = jfc.getSelectedFile();
				//
				if (selectedFile == null) {
					//
					JOptionPane.showMessageDialog(null, "No File Selected");
					//
				} else if (!selectedFile.exists()) {
					//
					JOptionPane.showMessageDialog(null,
							String.format("File \"%1$s\" does not exist", selectedFile.getAbsolutePath()));
					//
				} else if (!selectedFile.isFile()) {
					//
					JOptionPane.showMessageDialog(null, "Not A Regular File Selected");
					//
				} else if (selectedFile.length() == 0) {
					//
					JOptionPane.showMessageDialog(null, "Empty File Selected");
					//
				} else {
					//
					SqlSession sqlSession = null;
					//
					try {
						//
						final String fileExtension = getFileExtension(new ContentInfoUtil().findMatch(selectedFile));
						//
						if (fileExtension == null) {
							//
							JOptionPane.showMessageDialog(null, "File Extension is null");
							//
							return;
							//
						} else if (StringUtils.isEmpty(fileExtension)) {
							//
							JOptionPane.showMessageDialog(null, "File Extension is Empty");
							//
							return;
							//
						} else if (StringUtils.isBlank(fileExtension)) {
							//
							JOptionPane.showMessageDialog(null, "File Extension is Blank");
							//
							return;
							//
						} // if
							//
						String filePath = null;
						//
						final VoiceMapper voiceMapper = getMapper(getConfiguration(sqlSessionFactory),
								VoiceMapper.class, sqlSession = openSession(sqlSessionFactory));
						//
						final String text = getText(tfText);
						//
						final String romaji = getText(tfRomaji);
						//
						final Voice voiceOld = voiceMapper != null ? voiceMapper.searchByTextAndRomaji(text, romaji)
								: null;
						//
						final MessageDigest md = MessageDigest.getInstance("SHA-512");
						//
						final String messageDigestAlgorithm = md != null ? md.getAlgorithm() : null;
						//
						Long length = selectedFile != null ? Long.valueOf(selectedFile.length()) : null;
						//
						String fileDigest = Hex
								.encodeHexString(digest(md, FileUtils.readFileToByteArray(selectedFile)));
						//
						if (voiceOld == null
								|| !Objects.equals(voiceOld.getFileLength(),
										selectedFile != null ? Long.valueOf(selectedFile.length()) : null)
								|| !Objects.equals(voiceOld.getFileDigestAlgorithm(), messageDigestAlgorithm)
								|| !Objects.equals(voiceOld.getFileDigest(), fileDigest)) {
							//
							final File file = new File(voiceFolder, filePath = String
									.format("%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.%2$s", new Date(), fileExtension));
							//
							FileUtils.copyFile(selectedFile, file);
							//
							length = Long.valueOf(file.length());
							//
							fileDigest = Hex.encodeHexString(digest(md, FileUtils.readFileToByteArray(file)));
							//
						} else {
							//
							filePath = voiceOld.getFilePath();
							//
						} // if
							//
						setText(tfFile, StringUtils.defaultString(filePath, getText(tfFile)));
						//
						setText(tfFileLength, toString(length));
						//
						setText(tfFileDigest, fileDigest);
						//
						final StringMap stringMap = Reflection.newProxy(StringMap.class, new IH());
						//
						if (stringMap != null) {
							//
							stringMap.setString("filePath", filePath);
							//
							stringMap.setString("messageDigestAlgorithm", messageDigestAlgorithm);
							//
							stringMap.setString("fileDigest", fileDigest);
							//
							stringMap.setString("fileExtension", fileExtension);
							//
						} // if
							//
						insertOrUpdate(voiceMapper, createVoice(this, stringMap, length));
						//
					} catch (IOException | NoSuchAlgorithmException e) {
						//
						if (GraphicsEnvironment.isHeadless()) {
							//
							if (LOG != null) {
								LOG.error(getMessage(e), e);
							} else {
								e.printStackTrace();
							} // if
								//
						} else {
							//
							JOptionPane.showMessageDialog(null, getMessage(e));
							//
						} // if
							//
					} finally {
						//
						IOUtils.closeQuietly(sqlSession);
						//
					} // try
						//
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, "No File Selected");
				//
			} // if
				//
		} else if (Objects.equals(source, btnConvertToRomaji)) {
			//
			setText(tfRomaji, new Jakaroma().convert(getText(tfText), false, false));
			//
		} else if (Objects.equals(source, btnConvertToKatakana)) {
			//
			setText(tfKatakana, testAndApply(Objects::nonNull, getText(tfHiragana),
					x -> KanaConverter.convertKana(x, KanaConverter.OP_ZEN_HIRA_TO_ZEN_KATA), null));
			//
		} else if (Objects.equals(source, btnCopyRomaji)) {
			//
			setContents(getSystemClipboard(Toolkit.getDefaultToolkit()), new StringSelection(getText(tfRomaji)), null);
			//
		} else if (Objects.equals(source, btnExport)) {
			//
			SqlSession sqlSession = null;
			//
			Workbook workbook = null;
			//
			File file = null;
			//
			try {
				//
				final VoiceMapper voiceMapper = getMapper(getConfiguration(sqlSessionFactory), VoiceMapper.class,
						sqlSession = openSession(sqlSessionFactory));
				//
				final List<Voice> voices = voiceMapper != null ? voiceMapper.retrieveAll() : null;
				//
				export(voices, outputFolderFileNameExpressions, voiceFolder, outputFolder);
				//
				try (final OutputStream os = new FileOutputStream(
						file = new File(String.format("voice_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.xlsx", new Date())))) {
					//
					if ((workbook = createWorkbook(voices)) != null) {
						//
						workbook.write(os);
						//
					} // if
						//
				} // try
					//
			} catch (final IOException | IllegalAccessException e) {
				//
				if (GraphicsEnvironment.isHeadless()) {
					//
					if (LOG != null) {
						LOG.error(getMessage(e), e);
					} else {
						e.printStackTrace();
					} // if
						//
				} else {
					//
					JOptionPane.showMessageDialog(null, getMessage(e));
					//
				} // if
					//
			} catch (final InvocationTargetException e) {
				//
				final Throwable targetException = e.getTargetException();
				//
				final Throwable rootCause = ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException),
						targetException, ExceptionUtils.getRootCause(e), e);
				//
				if (GraphicsEnvironment.isHeadless()) {
					//
					if (LOG != null) {
						LOG.error(getMessage(rootCause), rootCause);
					} else if (rootCause != null) {
						rootCause.printStackTrace();
					} // if
						//
				} else {
					//
					JOptionPane.showMessageDialog(null, getMessage(rootCause));
					//
				} // if
					//
			} finally {
				//
				IOUtils.closeQuietly(sqlSession);
				//
				IOUtils.closeQuietly(workbook);
				//
				if (file != null && file.exists() && file.isFile() && file.length() == 0) {
					//
					file.delete();
					//
				} // if
					//
			} // try
				//
		} // if
			//
	}

	private class IH implements InvocationHandler {

		private Map<Object, Object> strings = null;

		private Map<Object, Object> getStrings() {
			if (strings == null) {
				strings = new LinkedHashMap<>();
			}
			return strings;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof StringMap) {
				//
				if (Objects.equals(methodName, "getString") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!getStrings().containsKey(key)) {
						//
						throw new IllegalStateException(String.format("Key [%1$s] Not Found", key));
						//
					} // if
						//
					return getStrings().get(key);
					//
				} else if (Objects.equals(methodName, "setString") && args != null && args.length > 1) {
					//
					getStrings().put(args[0], args[1]);
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static interface StringMap {

		String getString(final String key);

		void setString(final String key, final String value);

		static String getString(final StringMap instance, final String key) {
			return instance != null ? instance.getString(key) : null;
		}

	}

	private static Voice createVoice(final VoiceManager instance, final StringMap stringMap, final Long length) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		final Voice Voice = new Voice();
		//
		Voice.setText(getText(instance.tfText));
		//
		Voice.setRomaji(getText(instance.tfRomaji));
		//
		Voice.setHiragana(getText(instance.tfHiragana));
		//
		Voice.setKatakana(getText(instance.tfKatakana));
		//
		Voice.setFilePath(StringMap.getString(stringMap, "filePath"));
		//
		Voice.setFileDigestAlgorithm(StringMap.getString(stringMap, "messageDigestAlgorithm"));
		//
		Voice.setFileDigest(StringMap.getString(stringMap, "fileDigest"));
		//
		Voice.setFileExtension(StringMap.getString(stringMap, "fileExtension"));
		//
		Voice.setFileLength(length);
		//
		return Voice;
		//
	}

	private static String getMessage(final Throwable instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static <T> void forEach(final Stream<T> instance, final Consumer<? super T> action) {
		//
		if (instance != null && (action != null || Proxy.isProxyClass(instance.getClass()))) {
			instance.forEach(action);
		} // if
			//
	}

	private static void export(final List<Voice> voices, final Map<String, String> outputFolderFileNameExpressions,
			final String voiceFolder, final String outputFolder) throws IOException {
		//
		Voice voice = null;
		//
		EvaluationContext evaluationContext = null;
		//
		File folder = null;
		//
		ExpressionParser expressionParser = null;
		//
		String filePath = null;
		//
		String key = null;
		//
		String value = null;
		//
		File fileSource = null;
		//
		for (int i = 0; voices != null && i < voices.size(); i++) {
			//
			if ((voice = voices.get(i)) == null || (filePath = voice.getFilePath()) == null
					|| outputFolderFileNameExpressions == null || outputFolderFileNameExpressions.entrySet() == null) {
				continue;
			} // if
				//
			setVariable(evaluationContext = ObjectUtils.getIfNull(evaluationContext, StandardEvaluationContext::new),
					"voice", voice);
			//
			for (final Entry<String, String> folderFileNamePattern : outputFolderFileNameExpressions.entrySet()) {
				//
				if (folderFileNamePattern == null || (key = folderFileNamePattern.getKey()) == null
						|| StringUtils.isBlank(value = folderFileNamePattern.getValue())
						|| !(fileSource = voiceFolder != null ? new File(voiceFolder, filePath) : new File(filePath))
								.exists()) {
					continue;
				} // if
					// //
				FileUtils.copyFile(fileSource,
						new File((folder = ObjectUtils.getIfNull(folder,
								() -> outputFolder != null ? new File(outputFolder) : null)) != null ? new File(folder,
										key) : new File(key),
								toString(getValue(expressionParser = ObjectUtils.getIfNull(expressionParser,
										SpelExpressionParser::new), evaluationContext, value))));
				//
			} // for
				//
		} // for
			//
	}

	private static Workbook createWorkbook(final List<Voice> voices)
			throws IllegalAccessException, InvocationTargetException {
		//
		Voice voice = null;
		//
		Workbook workbook = null;
		//
		Sheet sheet = null;
		//
		Row row = null;
		//
		Cell cell = null;
		//
		Field[] fs = null;
		//
		Field f = null;
		//
		Object value = null;
		//
		final Class<?> dateFormatClass = forName("domain.Voice$DateFormat");
		//
		Annotation a = null;
		//
		Method m = null;
		//
		String[] fieldOrder = getFieldOrder();
		//
		for (int i = 0; voices != null && i < voices.size(); i++) {
			//
			if ((voice = voices.get(i)) == null
					|| (workbook = ObjectUtils.getIfNull(workbook, XSSFWorkbook::new)) == null) {
				continue;
			} // if
				//
			if (sheet == null) {
				sheet = workbook.createSheet();
			} // if
				//
			if ((fs = ObjectUtils.getIfNull(fs, () -> FieldUtils.getAllFields(Voice.class))) != null) {
				//
				Arrays.sort(fs, (x, y) -> {
					//
					return Integer.compare(ArrayUtils.indexOf(fieldOrder, getName(x)),
							ArrayUtils.indexOf(fieldOrder, getName(y)));
					//
				});
				//
			} // if
				//
				// header
				//
			if (sheet.getLastRowNum() < 0) {
				//
				if ((row = sheet.createRow(0)) == null) {
					continue;
				} // if
					//
				for (int j = 0; fs != null && j < fs.length; j++) {
					//
					if ((cell = row.createCell(j)) == null) {
						continue;
					} // if
						//
					cell.setCellValue(getName(fs[j]));
					//
				} // for
					//
			} // if
				//
				// content
				//
			if ((row = sheet.createRow(sheet.getLastRowNum() + 1)) == null) {
				continue;
			} // if
				//
			for (int j = 0; fs != null && j < fs.length; j++) {
				//
				if ((f = fs[j]) == null || (cell = row.createCell(j)) == null) {
					continue;
				} // if
					//
				f.setAccessible(true);
				//
				if ((value = f.get(voice)) instanceof Number) {
					//
					cell.setCellValue(((Number) value).doubleValue());
					//
				} else if (value instanceof Date) {
					//
					if ((m = orElse(
							findFirst(
									filter(Arrays
											.stream(getDeclaredMethods(annotationType(a = orElse(
													findFirst(filter(Arrays.stream(f.getDeclaredAnnotations()),
															x -> Objects.equals(annotationType(x), dateFormatClass))),
													null)))),
											x -> Objects.equals(getName(x), "value"))),
							null)) != null) {
						//
						m.setAccessible(true);
						//
						cell.setCellValue(new SimpleDateFormat(toString(invoke(m, a))).format(value));
						//
					} else {
						//
						cell.setCellValue(toString(value));
						//
					} // if
						//
				} else {
					//
					cell.setCellValue(toString(value));
					//
				} // if
					//
			} // for
				//
		} // for
			//
		if (sheet != null && row != null) {
			//
			sheet.setAutoFilter(new CellRangeAddress(sheet.getFirstRowNum(), sheet.getLastRowNum() - 1,
					row.getFirstCellNum(), row.getLastCellNum() - 1));
			//
		} // if
			//
		return workbook;
		//
	}

	private static String[] getFieldOrder() {
		//
		final Annotation a = orElse(findFirst(filter(Arrays.stream(Voice.class.getDeclaredAnnotations()),
				z -> Objects.equals(annotationType(z), forName("domain.FieldOrder")))), null);
		//
		final Method method = orElse(findFirst(
				filter(Arrays.stream(getDeclaredMethods(annotationType(a))), z -> Objects.equals(getName(z), "value"))),
				null);
		//
		if (method != null) {
			method.setAccessible(true);
		} // if
			//
		String[] orders = null;
		//
		try {
			//
			orders = cast(String[].class, invoke(method, a));
			//
		} catch (final IllegalAccessException e) {
			// =
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null) {
					LOG.error(getMessage(e), e);
				} else if (e != null) {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(e));
				//
			} // if
				//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			final Throwable rootCause = ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException),
					targetException, ExceptionUtils.getRootCause(e), e);
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null) {
					LOG.error(getMessage(rootCause), rootCause);
				} else if (rootCause != null) {
					rootCause.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, getMessage(rootCause));
				//
			} // if
				//
		} // try
			//
		final List<String> fieldNames = map(Arrays.stream(FieldUtils.getAllFields(Voice.class)), VoiceManager::getName)
				.collect(Collectors.toList());
		//
		String fieldName = null;
		//
		for (int i = 0; fieldNames != null && i < fieldNames.size(); i++) {
			//
			if (!ArrayUtils.contains(orders, fieldName = fieldNames.get(i))) {
				//
				orders = ArrayUtils.add(orders, fieldName);
				//
			} // if
				//
		} // for
			//
		return orders;
		//
	}

	private static Object invoke(final Method method, final Object instance, Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	private static Class<? extends Annotation> annotationType(final Annotation instance) {
		return instance != null ? instance.annotationType() : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <T> Optional<T> findFirst(final Stream<T> instance) {
		return instance != null ? instance.findFirst() : null;
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	private static Class<?> forName(final String className) {
		try {
			return StringUtils.isNotBlank(className) ? Class.forName(className) : null;
		} catch (final ClassNotFoundException e) {
			return null;
		}
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(instance.getClass()))
				? instance.filter(predicate)
				: null;
		//
	}

	private static void setVariable(final EvaluationContext instance, final String name, final Object value) {
		if (instance != null) {
			instance.setVariable(name, value);
		}
	}

	private static Object getValue(final ExpressionParser spelExpressionParser,
			final EvaluationContext evaluationContext, final String expression) {
		//
		return getValue(parseExpression(spelExpressionParser, expression), evaluationContext);
		//
	}

	private static Configuration getConfiguration(final SqlSessionFactory instance) {
		return instance != null ? instance.getConfiguration() : null;
	}

	private static SqlSession openSession(final SqlSessionFactory instance) {
		return instance != null ? instance.openSession() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static Expression parseExpression(final ExpressionParser instance, final String expressionString) {
		return instance != null ? instance.parseExpression(expressionString) : null;
	}

	private static Object getValue(final Expression instance, final EvaluationContext evaluationContext) {
		return instance != null ? instance.getValue(evaluationContext) : null;
	}

	private static void insertOrUpdate(final VoiceMapper instance, final Voice voice) {
		//
		if (instance != null) {
			//
			if (instance.searchByTextAndRomaji(voice != null ? voice.getText() : null,
					voice != null ? voice.getRomaji() : null) != null) {
				//
				if (voice != null) {
					//
					voice.setUpdateTs(new Date());
					//
				} // if
					//
				instance.update(voice);
				//
			} else {
				//
				if (voice != null) {
					//
					voice.setCreateTs(new Date());
					//
				} // if
					//
				instance.insert(voice);
				//
			} // if
				//
		} // if
			//
	}

	private static <T> T getMapper(final Configuration instance, final Class<T> type, final SqlSession sqlSession) {
		return instance != null ? instance.getMapper(type, sqlSession) : null;
	}

	private static byte[] digest(final MessageDigest instance, final byte[] input) {
		return instance != null && input != null ? instance.digest(input) : null;
	}

	private static String getFileExtension(final ContentInfo ci) {
		//
		if (ci == null) {
			//
			return null;
			//
		} // if
			//
		final String messgae = ci.getMessage();
		//
		if (matches(PATTERN_CONTENT_INFO_MP3 != null && messgae != null ? PATTERN_CONTENT_INFO_MP3.matcher(messgae)
				: null)) {
			//
			return "mp3";
			//
		} // if
			//
		final String name = ci.getName();
		//
		if (Objects.equals(name, "wav")) {
			//
			return name;
			//
		} else if (Objects.equals(ci.getMimeType(), "audio/x-hx-aac-adts")) {
			//
			return "aac";
			//
		} // if
			//
		return null;
		//
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) {
		return instance != null ? instance.getSystemClipboard() : null;
	}

	private static void setContents(final Clipboard instance, final Transferable contents, final ClipboardOwner owner) {
		if (instance != null) {
			instance.setContents(contents, owner);
		}
	}

	private static void setText(final JTextComponent instance, final String text) {
		if (instance != null) {
			instance.setText(text);
		}
	}

	private static boolean matches(final Matcher instance) {
		return instance != null && instance.matches();
	}

	private static String getText(final JTextComponent instance) {
		return instance != null ? instance.getText() : null;
	}

	private static Object getSource(final EventObject instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static void setPreferredWidth(final int width, final Component... cs) {
		//
		Component c = null;
		//
		Dimension d = null;
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if ((c = cs[i]) == null || (d = c.getPreferredSize()) == null) {
				continue;
			} // if
				//
			c.setPreferredSize(new Dimension(width, (int) d.getHeight()));
			//
		} // for
			//
	}

	private static Double getPreferredWidth(final Component c) {
		//
		final Dimension d = c != null ? c.getPreferredSize() : null;
		//
		return d != null ? Double.valueOf(d.getWidth()) : null;
		//
	}

	public static void main(final String[] args) {
		//
		final VoiceManager instance = new VoiceManager();
		//
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//
		instance.setLayout(new MigLayout());
		//
		instance.setTitle("Voice Manager");
		//
		instance.init();
		//
		instance.pack();
		//
		instance.setVisible(true);
		//
	}

}