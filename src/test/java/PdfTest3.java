import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
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
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.apache.pdfbox.rendering.PDFRenderer;
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

public class PdfTest3 {

	public static void main(final String[] args) throws IOException, IllegalAccessException, InvocationTargetException {
		//
		final File file = toFile(Path.of("libre_writer_test.pdf"));
		//
		System.out.println(getAbsolutePath(file));
		//
		final PDDocument document = Loader.loadPDF(file);
		//
		if (document != null && document.getNumberOfPages() > 0) {
			//
			final PDPage page = document.getPage(0);
			//
			System.out.println(page);
			//
			final Method[] ms = PDPage.class.getDeclaredMethods();
			//
			Method m = null;
			//
			for (int i = 0; ms != null && i < ms.length; i++) {
				//
				if ((m = ms[i]) == null || Objects.equals(m.getReturnType(), Void.TYPE) || m.getParameterCount() != 0) {
					//
					continue;
					//
				} // if
					//
				System.out.println(m.getName() + "=" + m.invoke(page));
				//
			} // for
				//
		} // if
			//
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static Integer getLargestY(final BufferedImage bi) {
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
					if (!Objects.equals(color, new Color(bi.getRGB(x, y)))) {
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
		sortInts(ily);
		//
		return ily != null && !ily.isEmpty() ? ily.getInt(ily.size() - 1) : null;
		//
	}

	private static void setSubject(final PDDocumentInformation instance, final String keywords) {
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

	private static byte[] pdf(final Path pathHtml) throws MalformedURLException {
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			final Page page = newPage(newContext(launch(playwright != null ? playwright.chromium() : null)));
			//
			if (page != null) {
				//
				testAndAccept(Objects::nonNull, toString(toURL(toURI(toFile(pathHtml)))), page::navigate);
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

}