import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.google.common.base.Objects;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ScreenshotOptions;
import com.microsoft.playwright.Playwright;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class PdfTest {

	public static void main(final String[] args) throws Exception {
		//
		try (final PDDocument document = new PDDocument()) {
			//
			final PDPage pd = new PDPage();
			//
			document.addPage(pd);
			//
			final File file = Path.of("test.pdf").toFile();
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
			System.out.println(file.getAbsolutePath());
			//
			document.save(file);
			//
		} // try
			//
		final Path image = Path.of("test.png");
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
			System.out.println(f.getAbsolutePath());
			//
			FileUtils.writeStringToFile(f,
					"<span style=\"font-size:64px\"><ruby>席<rt>せき</rt></ruby>をお<ruby>譲<rt>ゆず</rt></ruby>りください。</span>",
					"utf-8", false);
			//
			if (page != null) {
				//
				page.navigate(f.toURI().toURL().toString());
				//
				System.out.println(toFile(image).getAbsolutePath());
				//
				page.screenshot(new ScreenshotOptions().setPath(image));
				//
			} // if
				//
		} // try
			//
		final BufferedImage bi = ImageIO.read(toFile(image));
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
					System.out.println("L114,y=" + y + ",x=" + x);
					//
					color = new Color(bi.getRGB(y, x));
					//
				} else {
					//
					if (!Objects.equal(color, new Color(bi.getRGB(y, x)))) {
						//
						if ((ilx = ObjectUtils.getIfNull(ilx, IntArrayList::new)) != null && !ilx.contains(x)) {
							//
							ilx.add(x);
							//
						} // if
							//
						if ((ily = ObjectUtils.getIfNull(ily, IntArrayList::new)) != null && !ily.contains(y)) {
							//
							ily.add(y);
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
		if (ilx != null) {
			//
			ilx.sort(Integer::compare);
			//
		} // if
			//
		System.out.println(ilx);
		//
		if (ily != null) {
			//
			ily.sort(Integer::compare);
			//
		} // if
			//
		System.out.println(ily);
		//
	}

	private static File toFile(final Path instance) {
		return instance != null ? instance.toFile() : null;
	}

	private static float getTextWidth(final String text, final PDFont font, final float fontSize) throws IOException {
		float width = 0;
		for (int i = 0; i < text.length(); i++) {
			// Get the width of each character and add it to the total width
			width += font.getWidth(text.charAt(i)) / 1000.0f;
		}
		return width * fontSize;
	}

}