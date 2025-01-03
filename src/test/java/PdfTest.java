import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

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
			File f = Path.of("test.html").toFile();
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
				final Path path = Path.of("test.png");
				//
				System.out.println(path.toFile().getAbsolutePath());
				//
				page.screenshot(new Page.ScreenshotOptions().setPath(path));
				//
			} // if
				//
		} // try
			//
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