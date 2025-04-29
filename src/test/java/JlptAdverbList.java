import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.collections4.IterableUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JlptAdverbList {

	public static void main(String[] args) throws MalformedURLException, IOException, URISyntaxException {
		//
		final Document document = Jsoup.parse(new URI("https://jlptsensei.com/complete-japanese-adverbs-list").toURL(),
				0);
		//
		final Elements elements = document.getElementsByTag("table");
		//
		final Element table = IterableUtils.size(elements) == 1 ? IterableUtils.get(elements, 0) : null;
		//
		if (table != null) {
			//
			System.out.println(table);
			//
		} // if
			//
	}

}
