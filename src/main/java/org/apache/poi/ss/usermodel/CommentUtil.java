package org.apache.poi.ss.usermodel;

public interface CommentUtil {

	static void setString(final Comment instance, final RichTextString string) {
		if (instance != null) {
			instance.setString(string);
		}
	}

}