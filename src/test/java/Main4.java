import org.apache.commons.lang3.reflect.FieldUtils;

import com.atilika.kuromoji.buffer.StringValueMapBuffer;
import com.atilika.kuromoji.ipadic.Tokenizer;

public class Main4 {

	public static void main(final String[] args) throws IllegalAccessException {
		//
		final Tokenizer tokenizer = new Tokenizer();
		//
		final Object object = FieldUtils.readDeclaredField(FieldUtils.readField(tokenizer, "tokenInfoDictionary", true),
				"posValues", true);
		//
		final StringValueMapBuffer svmb = object instanceof StringValueMapBuffer ? (StringValueMapBuffer) object : null;
		//
		try {
			//
			for (int i = 0; svmb != null && i < Integer.MAX_VALUE; i++) {
				//
				System.out.println(i + " " + svmb.get(i));
				//
			} // for
				//
		} catch (final Exception e) {
			//
		} // try
			//
	}

}
