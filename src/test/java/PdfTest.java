import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class PdfTest {

	public static void main(final String[] args) throws IOException {
		//
		try (final PDDocument document = new PDDocument()) {
			//
			final PDPage page = new PDPage();
			//
			document.addPage(page);
			//
			final File file = Paths.get("test.pdf").toFile();
			//
			final PDPageContentStream cs = new PDPageContentStream(document, page);
			//
			cs.beginText();
			//
			PDFont font = null;
			//
			try (final InputStream is = PdfTest.class.getResourceAsStream("\\NotoSansCJKjp-Regular.otf")) {
				//
				final OTFParser otfParser = new OTFParser();
//				font = PDType0Font.load(document, is);
				TrueTypeFont ttf = otfParser.parseEmbedded(is);
				font = PDType0Font.load(document, ttf, false);
				//
			} // try
				//
			int fontSize = 48;
			//
			cs.setFont(font, fontSize);
			//
			final String text = "席をお譲りください";
			//
			cs.newLineAtOffset((page.getMediaBox().getWidth() - getTextWidth(text, font, fontSize)) / 2,
					page.getMediaBox().getHeight() - (font.getFontDescriptor().getAscent() / 1000 * fontSize)
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