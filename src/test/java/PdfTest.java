import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.SpeechApi;
import org.springframework.context.support.SpeechApiImpl;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.github.toolfactory.narcissus.Narcissus;

public class PdfTest {

	public static void main(final String[] args) throws Exception {
		//
		final Path pathHtml = Path.of("test.html");
		//
		FileUtils.writeStringToFile(toFile(pathHtml),
				"<span style=\"font-size:64px\"><ruby>席<rt>せき</rt></ruby>をお<ruby>譲<rt>ゆず</rt></ruby>りください。</span>",
				"utf-8", false);
		//
		final Path pathImage = Path.of("test1.png");
		//
		System.out.println(getAbsolutePath(toFile(pathImage)));
		//
		FileUtils.writeByteArrayToFile(toFile(pathImage), screenshot(pathHtml), false);
		//
		final BufferedImage bi = chop(ImageIO.read(toFile(pathImage)));
		//
		final Path pathChoppedImage = Path.of("test2.png");
		//
		System.out.println(getAbsolutePath(toFile(pathChoppedImage)));
		//
		try (final OutputStream os = Files.newOutputStream(pathChoppedImage)) {
			//
			if (bi != null) {
				//
				ImageIO.write(bi, "png", os);
				//
			} // if
				//
		} // try
			//
		final File file = toFile(Path.of("test.pdf"));
		//
		try (final PDDocument document = new PDDocument()) {
			//
			final PDPage pd = new PDPage();
			//
			document.addPage(pd);
			//
			final PDPageContentStream cs = new PDPageContentStream(document, pd);
			//
			final PDImageXObject pdfImageXObject = PDImageXObject.createFromByteArray(document,
					Files.readAllBytes(pathChoppedImage), getName(toFile(pathChoppedImage)));
			//
			final PDRectangle md = pd.getMediaBox();
			//
			cs.drawImage(pdfImageXObject, (getWidth(md) - getWidth(pdfImageXObject)) / 2,
					getHeight(md) - getHeight(pdfImageXObject));
			//
			System.out.println(getAbsolutePath(file));
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
			final int fontSize = 14;
			//
			Duration duration = null;
			//
			for (final Entry<Integer, String> entry : map.entrySet()) {
				//
				if (entry == null || (key = entry.getKey()) == null) {
					//
					continue;
					//
				} // if
					//
				speechApi.writeVoiceToFile("席をお譲りください", "TTS_MS_JA-JP_HARUKA_11.0", key.intValue(), 100,
						toFile(pathAudio));
				//
				final int size = 60;
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
				try (final InputStream is = Files.newInputStream(pathAudio)) {
					//
					final PDEmbeddedFile pdfEmbeddedFile = new PDEmbeddedFile(document, is);
					//
					pdfEmbeddedFile.setSubtype("audio/wav");
					//
					pdfEmbeddedFile.setSize((int) toFile(pathAudio).length());
					//
					final PDAnnotationFileAttachment attachment = new PDAnnotationFileAttachment();
					//
					final PDComplexFileSpecification fileSpec = new PDComplexFileSpecification();
					//
					fileSpec.setFile(getName(toFile(pathAudio)));
					//
					fileSpec.setEmbeddedFile(pdfEmbeddedFile);
					//
					attachment.setFile(fileSpec);
					//
					// Position on the page
					//
					attachment.setRectangle(new PDRectangle(index++ * size,
							getHeight(md) - getHeight(pdfImageXObject) - size, size, size));
					//
					attachment.setContents(value = entry.getValue());
					//
					add(pd.getAnnotations(), attachment);
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
							lastHeight = (getHeight(md) - getHeight(pdfImageXObject) - size
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
		} // try
			//
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
			final FailableBiFunction<T, U, R, E> functionFalse) throws E {
		return test(predicate, t, u) ? FailableBiFunctionUtil.apply(functionTrue, t, u)
				: FailableBiFunctionUtil.apply(functionFalse, t, u);
	}

	private static <T, U> boolean test(final BiPredicate<T, U> instance, final T t, final U u) {
		return instance != null && instance.test(t, u);
	}

	private static AudioFormat getFormat(final AudioFileFormat instance) {
		return instance != null ? instance.getFormat() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static int groupCount(final Matcher instance) {
		return instance != null ? instance.groupCount() : 0;
	}

	private static boolean matches(final Matcher instance) {
		return instance != null && instance.matches();
	}

	private static Matcher matcher(final Pattern instance, final CharSequence input) {
		return instance != null ? instance.matcher(input) : null;
	}

	private static <E> void add(final Collection<E> instance, final E item) {
		if (instance != null) {
			instance.add(item);
		}
	}

	private static float getHeight(final PDRectangle instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	private static int getHeight(final PDImage instance) {
		return instance != null ? instance.getHeight() : 0;
	}

	private static float getWidth(final PDRectangle instance) {
		return instance != null ? instance.getWidth() : 0;
	}

	private static int getWidth(final PDImage instance) {
		return instance != null ? instance.getWidth() : 0;
	}

	private static String getName(final File instance) {
		return instance != null ? instance.getName() : null;
	}

	private static byte[] screenshot(final Path pathHtml) throws MalformedURLException {
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			final Page page = newPage(newContext(launch(playwright != null ? playwright.chromium() : null)));
			//
			if (page != null) {
				//
				testAndAccept(Objects::nonNull, toString(toURL(toURI(toFile(pathHtml)))), page::navigate);
				//
				return page.screenshot();
				//
			} // if
				//
		} // try
			//
		return null;
		//
	}

	private static Browser launch(final BrowserType instance) {
		return instance != null ? instance.launch() : null;
	}

	private static BrowserContext newContext(final Browser instance) {
		return instance != null ? instance.newContext() : null;
	}

	private static Page newPage(final BrowserContext instance) {
		return instance != null ? instance.newPage() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static <T> void testAndAccept(final Predicate<T> predicate, final T value, final Consumer<T> consumer) {
		if (predicate != null && predicate.test(value) && consumer != null) {
			consumer.accept(value);
		}
	}

	private static URL toURL(final URI instance) throws MalformedURLException {
		return instance != null ? instance.toURL() : null;
	}

	private static URI toURI(final File instance) {
		return instance != null ? instance.toURI() : null;
	}

	private static BufferedImage chop(final BufferedImage bi) {
		//
		Color color = null;
		//
		IntList ilx = null;
		//
		IntList ily = null;
		//
		for (int y = 0; bi != null && y < bi.getHeight(); y++) {
			//
			for (int x = 0; x < bi.getHeight(); x++) {
				//
				if (color == null) {
					//
					color = new Color(bi.getRGB(y, x));
					//
				} else {
					//
					if (!Objects.equals(color, new Color(bi.getRGB(y, x)))) {
						//
						if (!contains(ilx = ObjectUtils.getIfNull(ilx, IntList::create), x)) {
							//
							IntCollectionUtil.addInt(ilx, x);
							//
						} // if
							//
						if (!contains(ily = ObjectUtils.getIfNull(ily, IntList::create), y)) {
							//
							IntCollectionUtil.addInt(ily, y);
							//
						} // if
							//
					} // if
						//
				} // if
					//
			} // for
				//
		} // for
			//
		System.out.println(color);
		//
		sortInts(ilx);
		//
		System.out.println(ilx);
		//
		sortInts(ily);
		//
		System.out.println(ily);
		//
		if (ilx != null && ily != null) {
			//
			final int x = ilx.getInt(0);
			//
			final int y = ily.getInt(0);
			//
			return bi.getSubimage(y, x, ily.getInt(ily.size() - 1) - y + 1, ilx.getInt(ilx.size() - 1) - x + 1);
			//
		} // if
			//
		return bi;
		//
	}

	private static boolean contains(final Collection<?> instance, final Object o) {
		return instance != null && instance.contains(o);
	}

	private static void sortInts(final IntList instance) {
		if (instance != null) {
			instance.sortInts();
		}
	}

	private static String getAbsolutePath(final File instance) {
		return instance != null ? instance.getAbsolutePath() : null;
	}

	private static File toFile(final Path instance) {
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

	@Test
	void testNull() {
		//
		final Method[] ms = PdfTest.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Collection<Object> collection = null;
		//
		Object invokeStaticMethod = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()
					|| Boolean.logicalAnd(Objects.equals(m.getName(), "main"),
							Arrays.equals(m.getParameterTypes(), new Class<?>[] { String[].class }))) {
				//
				continue;
				//
			} // if
				//
			if ((collection = ObjectUtils.getIfNull(collection, ArrayList::new)) != null) {
				//
				collection.clear();
				//
			} // if
				//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterTypes[j], Float.TYPE)) {
					//
					add(collection, Float.valueOf(0));
					//
				} else {
					//
					add(collection, null);
					//
				} // if
					//
			} // if
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(collection));
			//
			toString = toString(m);
			//
			if (contains(Arrays.asList(Float.TYPE, Boolean.TYPE, Integer.TYPE), m.getReturnType())
					|| Boolean.logicalAnd(Objects.equals(m.getName(), "screenshot"),
							Arrays.equals(parameterTypes, new Class<?>[] { Path.class }))) {
				//
				Assertions.assertNotNull(invokeStaticMethod, toString);
				//
			} else {
				//
				Assertions.assertNull(invokeStaticMethod, toString);
				//
			} // if
				//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

}