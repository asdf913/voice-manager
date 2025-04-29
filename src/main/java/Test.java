//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.util.Map;
//
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(final String[] args) throws MalformedURLException, IOException {
		//
		final URLConnection urlConnection = new URL("https://kanjikana.com/api/furigana").openConnection();
		//
		final HttpURLConnection httpURLConnection = cast(HttpURLConnection.class, urlConnection);
		//
		if (httpURLConnection != null) {
			//
			httpURLConnection.setRequestMethod("POST");
			//
			httpURLConnection.setDoOutput(true);
			//
			final ObjectMapper objectMapper = new ObjectMapper();
			//
			try (final OutputStream os = httpURLConnection.getOutputStream()) {
				//
				if (os != null) {
					//
					os.write(objectMapper.writeValueAsBytes(Map.of("input", "明日から１週間、海外出張なんですか？"
					// , "lang", "en"
					)));
					//
				} // if
					//
			} // try
				//
			try (final InputStream is = httpURLConnection.getInputStream()) {
				//
				final Map<?, ?> map = cast(Map.class, objectMapper.readValue(is, Object.class));
				//
				System.out.println(map != null ? map.size() : null);
				//
				System.out.println(map != null ? map.keySet() : null);
				//
				System.out.println(map);
				//
				System.out.println(get(map, "furigana"));
				//
			} // try
		} // if
			//
//		final Connec(null)tion connection = Jsoup.connect("https://kanjikana.com/api/furigana");
//		//
//		if (connection != null) {
//			//
//			connection.requestBody(
//					new ObjectMapper().writeValueAsString(Map.of("input", "明日から１週間、海外出張なんですか？", "lang", "en")));
//			// {"input":"明日から１週間、海外出張なんですか？11","lang":"en"}
////			connection.data(Map.of("input", "明日から１週間、海外出張なんですか？", "lang", "en"));
//			//
////			connection.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");
//			//
////			connection.ignoreHttpErrors(true);
//			//
//			connection.header("Content-Type", "application/json");
//			//
//			connection.ignoreContentType(true);
//			//
//		} // if
//			//
//		final Document document = connection != null ? connection.post() : null;
//		//
//		System.out.println(document);
//
	}

	private static <V> V get(final Map<?, V> instance, final Object key) {
		return instance != null ? instance.get(key) : null;
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

}