package org.springframework.context.support;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiFunctionUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntList;

import com.helger.css.ECSSUnit;
import com.helger.css.propertyvalue.CSSSimpleValueWithUnit;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class VoiceManagerPdfPanel {

	public static void main(final String[] args) throws Exception {
		//
		final Path pathHtml = Path.of("test.html");
		//
		int fontSize = 80;
		//
		final Map<String, String> style = new LinkedHashMap<>(
				Map.of("text-align", "center", "display", "block", "margin-left", "auto", "margin-right", "auto"));
		//
		style.put("font-size", new CSSSimpleValueWithUnit(new BigDecimal(fontSize), ECSSUnit.PX).getFormatted());
		//
		FileUtils.writeStringToFile(toFile(pathHtml),
				String.format("<div style=\"%1$s\"><ruby>席<rt>せき</rt></ruby>をお<ruby>譲<rt>ゆず</rt></ruby>りください。</div>",
						Util.collect(
								Util.map(Util.stream(Util.entrySet(style)),
										x -> StringUtils.joinWith(":", Util.getKey(x), Util.getValue(x))),
								Collectors.joining(";"))),
				StandardCharsets.UTF_8, false);
		//
		final File file = toFile(Path.of("test.pdf"));
		//
		System.out.println(getAbsolutePath(file));
		//
		FileUtils.writeByteArrayToFile(file, pdf(pathHtml), false);
		//
		final PDDocument document = Loader.loadPDF(file);
		//
		if (document != null && document.getNumberOfPages() > 0) {
			//
			final PDPage pd = document.getPage(0);
			//
			final PDFRenderer pdfRenderer = new PDFRenderer(document);
			//
			final Path page1Path = Path.of("page1.png");
			//
			deleteOnExit(toFile(page1Path));
			//
			System.out.println(getAbsolutePath(toFile(page1Path)));
			//
			final BufferedImage bi = pdfRenderer.renderImage(0);
			//
			ImageIO.write(bi, "png", toFile(page1Path));
			//
			final Integer largestY = getLargestY(bi);
			//
			final PDPageContentStream cs = new PDPageContentStream(document, pd, AppendMode.PREPEND, true);
			//
			final Path pathAudio = Path.of("test.wav");
			//
			System.out.println(getAbsolutePath(toFile(pathAudio)));
			//
			// 100% Speed
			//
			final SpeechApi speechApi = new SpeechApiImpl();
			//
			final Map<Integer, String> map = new LinkedHashMap<>(Collections.singletonMap(0, "100% Speed"));
			//
			map.put(-1, "90% Speed");
			//
			map.put(-2, "80% Speed");
			//
			map.put(-3, "70% Speed");
			//
			map.put(-4, "60% Speed");
			//
			map.put(-5, "50% Speed");
			//
			map.put(-6, "40% Speed");
			//
			map.put(-7, "30% Speed");
			//
			map.put(-8, "20% Speed");
			//
			map.put(-9, "10% Speed");
			//
			int index = 0;
			//
			Integer key = null;
			//
			String value = null;
			//
			fontSize = 14;
			//
			Duration duration = null;
			//
			final String text = "席をお譲りください";
			//
			setSubject(document.getDocumentInformation(), text);
			//
			for (final Entry<Integer, String> entry : Util.entrySet(map)) {
				//
				if (entry == null || (key = Util.getKey(entry)) == null) {
					//
					continue;
					//
				} // if
					//
				speechApi.writeVoiceToFile(text, "TTS_MS_JA-JP_HARUKA_11.0", Util.intValue(key, 0), 100,
						toFile(pathAudio));
				//
				final int size = 61;
				//
				final PDFont font = new PDType1Font(FontName.HELVETICA);
				//
//				try (final InputStream is = PdfTest.class.getResourceAsStream("\\NotoSansCJKjp-Regular.otf")) {
				//
//					font = PDType0Font.load(document, new OTFParser().parseEmbedded(is), false);
				//
//				} // try
				//
				Pattern pattern = null;
				//
				Matcher matcher = null;
				//
				float lastHeight = 0;
				//
				final PDRectangle md = getMediaBox(pd);
				//
				try (final InputStream is = Files.newInputStream(pathAudio)) {
					//
					final PDEmbeddedFile pdfEmbeddedFile = new PDEmbeddedFile(document, is);
					//
					pdfEmbeddedFile.setSubtype(getMimeType(pathAudio));
					//
					pdfEmbeddedFile.setSize((int) toFile(pathAudio).length());
					//
					final PDComplexFileSpecification fileSpec = new PDComplexFileSpecification();
					//
					fileSpec.setFile(getName(toFile(pathAudio)));
					//
					fileSpec.setEmbeddedFile(pdfEmbeddedFile);
					//
					final PDAnnotationFileAttachment attachment = new PDAnnotationFileAttachment();
					//
					attachment.setFile(fileSpec);
					//
					// Position on the page
					//
					attachment.setRectangle(new PDRectangle(index++ * size,
							getHeight(md) - Util.intValue(largestY, 0) - size, size, size));
					//
					attachment.setContents(value = entry.getValue());
					//
					Util.add(getAnnotations(pd), attachment);
					//
					// Label (Speed)
					//
					cs.beginText();
					//
					cs.setFont(font, fontSize);
					//
					if (matches(matcher = matcher(
							pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("^(\\d+%).+$")), value))
							&& groupCount(matcher) > 0) {
						//
						value = matcher.group(1);
						//
					} // if
						//
					cs.newLineAtOffset((index - 1) * size + getTextWidth(value, font, fontSize) / 2,
							lastHeight = (getHeight(md) - Util.intValue(largestY, 0) - size
							//
									- (font.getFontDescriptor().getAscent() / 1000 * fontSize)
									+ (font.getFontDescriptor().getDescent() / 1000 * fontSize))
					//
					);
					//
//					cs.newLineAtOffset((pd.getMediaBox().getWidth() - getTextWidth(text, font, fontSize)) / 2,
//							pd.getMediaBox().getHeight() - (font.getFontDescriptor().getAscent() / 1000 * fontSize)
//									+ (font.getFontDescriptor().getDescent() / 1000 * fontSize));
					//
					cs.showText(value);
					//
					cs.endText();
					//
					// Label (Duration)
					//
					if ((duration = getAudioDuration(toFile(pathAudio))) != null) {
						//
						cs.beginText();
						//
						cs.setFont(font, fontSize);
						//
						cs.newLineAtOffset((index - 1) * size + getTextWidth(value, font, fontSize) / 2,
								lastHeight - (font.getFontDescriptor().getAscent() / 1000 * fontSize)
										+ (font.getFontDescriptor().getDescent() / 1000 * fontSize)
						//
						);
						cs.showText(String.format("%1$s s", duration.toMillis() / 1000d));
						//
						cs.endText();
						//
					} // if
						//
				} // try
					//
			} // for
				//
			IOUtils.close(cs);
			//
			document.save(file);
			//
		} // if
			//
	}

	private static void deleteOnExit(@Nullable final File instance) {
		if (instance != null) {
			instance.deleteOnExit();
		}
	}

	private static String getMimeType(@Nullable final Path path) throws IOException {
		//
		try (final InputStream is = testAndApply(Objects::nonNull, path, Files::newInputStream, null)) {
			//
			final ContentInfo ci = testAndApply(Objects::nonNull, is, new ContentInfoUtil()::findMatch, null);
			//
			return ci != null ? ci.getMimeType() : null;
			//
		} // try
			//
	}

	@Nullable
	private static List<PDAnnotation> getAnnotations(@Nullable final PDPage instance) throws IOException {
		return instance != null ? instance.getAnnotations() : null;
	}

	@Nullable
	private static PDRectangle getMediaBox(@Nullable final PDPage instance) {
		return instance != null ? instance.getMediaBox() : null;
	}

	@Nullable
	private static Integer getLargestY(@Nullable final BufferedImage bi) {
		//
		Color color = null;
		//
		IntList ily = null;
		//
		for (int y = 0; bi != null && y < bi.getHeight(); y++) {
			//
			for (int x = 0; x < bi.getWidth(); x++) {
				//
				if (color == null) {
					//
					color = new Color(bi.getRGB(x, y));
					//
				} else {
					//
					if (!Objects.equals(color, new Color(bi.getRGB(x, y)))
							&& !Util.contains(ily = ObjectUtils.getIfNull(ily, IntList::create), y)) {
						//
						IntCollectionUtil.addInt(ily, y);
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // for
			//
		sortInts(ily);
		//
		return ily != null && !ily.isEmpty() ? ily.getInt(ily.size() - 1) : null;
		//
	}

	private static void setSubject(@Nullable final PDDocumentInformation instance, final String keywords) {
		if (instance != null) {
			instance.setSubject(keywords);
		}
	}

	private static Duration getAudioDuration(final File file) throws Exception {
		//
		final AudioFileFormat fileFormat = testAndApply(Objects::nonNull, file, AudioSystem::getAudioFileFormat, null);
		//
		return testAndApply((a, b) -> a != null && b != null, fileFormat, getFormat(fileFormat),
				(a, b) -> Duration.parse(String.format("PT%1$sS", a.getFrameLength() / b.getFrameRate())), null);
		//
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			@Nullable final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return test(predicate, t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, final T t, final U u) {
		return instance != null && instance.test(t, u);
	}

	@Nullable
	private static AudioFormat getFormat(@Nullable final AudioFileFormat instance) {
		return instance != null ? instance.getFormat() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static int groupCount(@Nullable final Matcher instance) {
		return instance != null ? instance.groupCount() : 0;
	}

	private static boolean matches(@Nullable final Matcher instance) {
		return instance != null && instance.matches();
	}

	@Nullable
	private static Matcher matcher(@Nullable final Pattern instance, final CharSequence input) {
		return instance != null ? instance.matcher(input) : null;
	}

	private static float getHeight(@Nullable final PDRectangle instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	@Nullable
	private static String getName(@Nullable final File instance) {
		return instance != null ? instance.getName() : null;
	}

	private static byte[] pdf(@Nullable final Path pathHtml) throws MalformedURLException {
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			final Page page = newPage(newContext(launch(playwright != null ? playwright.chromium() : null)));
			//
			if (page != null) {
				//
				testAndAccept(Objects::nonNull, Util.toString(toURL(toURI(toFile(pathHtml)))), page::navigate);
				//
				return page.pdf();
				//
			} // if
				//
		} // try
			//
		return null;
		//
	}

	@Nullable
	private static Browser launch(@Nullable final BrowserType instance) {
		return instance != null ? instance.launch() : null;
	}

	@Nullable
	private static BrowserContext newContext(@Nullable final Browser instance) {
		return instance != null ? instance.newContext() : null;
	}

	@Nullable
	private static Page newPage(@Nullable final BrowserContext instance) {
		return instance != null ? instance.newPage() : null;
	}

	private static <T> void testAndAccept(@Nullable final Predicate<T> predicate, final T value,
			@Nullable final Consumer<T> consumer) {
		if (predicate != null && predicate.test(value) && consumer != null) {
			consumer.accept(value);
		}
	}

	@Nullable
	private static URL toURL(@Nullable final URI instance) throws MalformedURLException {
		return instance != null ? instance.toURL() : null;
	}

	@Nullable
	private static URI toURI(@Nullable final File instance) {
		return instance != null ? instance.toURI() : null;
	}

	private static void sortInts(@Nullable final IntList instance) {
		if (instance != null) {
			instance.sortInts();
		}
	}

	@Nullable
	private static String getAbsolutePath(@Nullable final File instance) {
		return instance != null ? instance.getAbsolutePath() : null;
	}

	@Nullable
	private static File toFile(@Nullable final Path instance) {
		return instance != null ? instance.toFile() : null;
	}

	private static float getTextWidth(final String text, final PDFont font, final float fontSize) throws IOException {
		float width = 0;
		for (int i = 0; i < StringUtils.length(text); i++) {
			// Get the width of each character and add it to the total width
			width += font.getWidth(text.charAt(i)) / 1000.0f;
		}
		return width * fontSize;
	}

}