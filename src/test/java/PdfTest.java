import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ScreenshotOptions;
import com.microsoft.playwright.Playwright;

import io.github.toolfactory.narcissus.Narcissus;

public class PdfTest {

	public static void main(final String[] args) throws Exception {
		//
		try (final PDDocument document = new PDDocument()) {
			//
			final PDPage pd = new PDPage();
			//
			document.addPage(pd);
			//
			final File file = toFile(Path.of("test.pdf"));
			//
			final PDPageContentStream cs = new PDPageContentStream(document, pd);
			//
			cs.beginText();
			//
			PDFont font = null;
			//
			try (final InputStream is = PdfTest.class.getResourceAsStream("\\NotoSansCJKjp-Regular.otf")) {
				//
				font = PDType0Font.load(document, new OTFParser().parseEmbedded(is), false);
				//
			} // try
				//
			int fontSize = 64;
			//
			cs.setFont(font, fontSize);
			//
			final String text = "席をお譲りください";
			//
			cs.newLineAtOffset((pd.getMediaBox().getWidth() - getTextWidth(text, font, fontSize)) / 2,
					pd.getMediaBox().getHeight() - (font.getFontDescriptor().getAscent() / 1000 * fontSize)
							+ (font.getFontDescriptor().getDescent() / 1000 * fontSize));
			//
			cs.showText(text);
			//
			cs.endText();
			//
			cs.close();
			//
			System.out.println(getAbsolutePath(file));
			//
			document.save(file);
			//
		} // try
			//
		final Path image = Path.of("test1.png");
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			final BrowserType browserType = playwright != null ? playwright.chromium() : null;
			//
			final Browser browser = browserType != null ? browserType.launch() : null;
			//
			final BrowserContext browserContext = browser != null ? browser.newContext() : null;
			//
			final Page page = browserContext != null ? browserContext.newPage() : null;
			//
			final File f = toFile(Path.of("test.html"));
			//
			System.out.println(getAbsolutePath(f));
			//
			FileUtils.writeStringToFile(f,
					"<span style=\"font-size:64px\"><ruby>席<rt>せき</rt></ruby>をお<ruby>譲<rt>ゆず</rt></ruby>りください。</span>",
					"utf-8", false);
			//
			if (page != null) {
				//
				page.navigate(Objects.toString(f.toURI().toURL()));
				//
				System.out.println(getAbsolutePath(toFile(image)));
				//
				page.screenshot(new ScreenshotOptions().setPath(image));
				//
			} // if
				//
		} // try
			//
		final BufferedImage bi = chop(ImageIO.read(toFile(image)));
		//
		final Path path = Path.of("test2.png");
		//
		System.out.println(getAbsolutePath(toFile(path)));
		//
		try (final OutputStream os = Files.newOutputStream(path)) {
			//
			if (bi != null) {
				//
				ImageIO.write(bi, "png", os);
				//
			} // if
				//
		} // try
			//
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
						if ((ilx = ObjectUtils.getIfNull(ilx, IntList::create)) != null && !contains(ilx, x)) {
							//
							IntCollectionUtil.addInt(ilx, x);
							//
						} // if
							//
						if ((ily = ObjectUtils.getIfNull(ily, IntList::create)) != null && !contains(ily, y)) {
							//
							IntCollectionUtil.addInt(ily, x);
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
		sort(ilx, Integer::compare);
		//
		System.out.println(ilx);
		//
		sort(ily, Integer::compare);
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

	private static void sort(final IntList instance, final Comparator<? super Integer> comparator) {
		if (instance != null) {
			instance.sort(comparator);
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
					collection.add(Float.valueOf(0));
					//
				} else {
					//
					collection.add(null);
					//
				} // if
					//
			} // if
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(collection));
			//
			toString = Objects.toString(m);
			//
			if (contains(Arrays.asList(Float.TYPE, Boolean.TYPE), m.getReturnType())) {
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