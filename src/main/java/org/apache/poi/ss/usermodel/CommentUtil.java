package org.apache.poi.ss.usermodel;

public interface CommentUtil {

	static void setString(final Comment instance, final RichTextString string) {
		if (instance != null) {
			instance.setString(string);
		}
	}

	static void setAuthor(final Comment instance, final String string) {
		if (instance != null) {
			instance.setAuthor(string);
		}
	}

}