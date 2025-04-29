import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;
import org.htmlunit.FailingHttpStatusCodeException;
import org.htmlunit.Page;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlPage;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OnlineJTalkTts {

	public static void main(final String[] args)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		//
		test();
		//
	}

	private static void test() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		//
		try (final WebClient webClient = new WebClient()) {
			//
			final String url = "https://open-jtalk.sp.nitech.ac.jp/index.php";// TODO
			//
			HtmlPage htmlPage = webClient.getPage(url);
			//
			NodeList nodeList = getElementsByTagName(htmlPage, "textarea");
			//
			Node node = null;
			//
			for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
				//
				if ((node = nodeList.item(i)) == null) {
					//
					continue;
					//
				} // if
					//
				node.setTextContent("備える");// TODO
				//
			} // for
				//
			nodeList = getElementsByTagName(
					htmlPage = cast(HtmlPage.class,
							click(cast(DomElement.class, querySelector(htmlPage, "input[type=\"submit\"]")))),
					"source");
			//
			NamedNodeMap attributes = null;
			//
			Node namedItem = null;
			//
			String nodeValue = null;
			//
			IValue0<String> src = null;
			//
			for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
				//
				if ((node = nodeList.item(i)) == null || (attributes = node.getAttributes()) == null
						|| (namedItem = attributes.getNamedItem("src")) == null) {
					//
					continue;
					//
				} // if
					//
				if (StringUtils.startsWith(nodeValue = namedItem.getNodeValue(), "./temp/")) {
					//
					if (src == null) {
						//
						src = Unit.with(nodeValue);
						//
					} else {
						//
						throw new IllegalStateException();
						//
					} // if
						//
				} // if
					//
			} // for
				//
			if (src != null) {
				//
				System.out.println(String.join("/", StringUtils.substringBeforeLast(url, "/"),
						StringUtils.substringAfter(IValue0Util.getValue0(src), '/')));
				//
			} // if
				//
		} // try
			//
	}

	private static <N extends DomNode> N querySelector(final DomNode instance, final String selectors) {
		return instance != null ? instance.querySelector(selectors) : null;
	}

	private static NodeList getElementsByTagName(final Document instance, final String tagname) {
		return instance != null ? instance.getElementsByTagName(tagname) : null;
	}

	private static <P extends Page> P click(final DomElement instance) throws IOException {
		return instance != null ? instance.click() : null;
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}