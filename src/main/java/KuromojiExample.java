import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.google.common.base.Strings;
import com.mariten.kanatools.KanaConverter;

import j2html.rendering.FlatHtml;
import j2html.rendering.HtmlBuilder;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class KuromojiExample {

	public static void main(final String[] args) throws IOException {
		//
		final List<Token> tokens = new Tokenizer().tokenize("それは大変ですね。すぐ交番に行かないと。");
		//
		String surface, convertKana, commonSuffix = null;
		//
		String[] allFeatures = null;
		//
		HtmlBuilder<StringBuilder> htmlBuilder = null;
		//
		for (final Token token : tokens) {
			//
			if (token == null || (htmlBuilder = ObjectUtils.getIfNull(htmlBuilder, () -> FlatHtml.inMemory())) == null
					|| (allFeatures = token.getAllFeaturesArray()) == null || allFeatures.length < 9) {
				//
				continue;
				//
			} // if
				//
			if (StringUtils.equals(surface = token.getSurface(), convertKana = KanaConverter
					.convertKana(ArrayUtils.get(allFeatures, 7), KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA))) {
				//
				htmlBuilder.appendUnescapedText(surface);
				//
			} else {
				//
				commonSuffix = Strings.commonSuffix(surface, convertKana);
				//
				htmlBuilder.appendStartTag("ruby").completeTag().appendStartTag("rb").completeTag();
				//
				if (StringUtils.isNotBlank(commonSuffix)) {
					//
					htmlBuilder.appendUnescapedText(StringUtils.substring(surface, 0,
							StringUtils.length(surface) - StringUtils.length(commonSuffix)));
					//
				} else {
					//
					htmlBuilder.appendUnescapedText(surface);
					//
				} // if
					//
				htmlBuilder.appendEndTag("rb").appendStartTag("rp").completeTag().appendUnescapedText("(")
						.appendEndTag("rp").appendStartTag("rt").completeTag();
				//
				if (StringUtils.isNotBlank(commonSuffix)) {
					//
					htmlBuilder.appendUnescapedText(StringUtils.substring(convertKana, 0,
							StringUtils.length(convertKana) - StringUtils.length(commonSuffix)));
					//
				} else {
					//
					htmlBuilder.appendUnescapedText(convertKana);
					//
				} // if
					//
				htmlBuilder.appendEndTag("rt").appendStartTag("rp").completeTag().appendUnescapedText(")")
						.appendEndTag("rp").appendEndTag("ruby");
				//
				if (StringUtils.isNotBlank(commonSuffix)) {
					//
					htmlBuilder.appendUnescapedText(commonSuffix);
					//
				} // if
					//
			} // if
				//
		} // for
			//
		System.out.println(htmlBuilder != null ? htmlBuilder.output() : null);
		//
	}

}