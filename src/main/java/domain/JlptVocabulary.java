package domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class JlptVocabulary {

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("level")
	private String level = null;

	@Note("kanji")
	private String kanji = null;

	private String kana = null;

	public String getLevel() {
		return level;
	}

	public void setLevel(final String level) {
		this.level = level;
	}

	public String getKanji() {
		return kanji;
	}

	public String getKana() {
		return kana;
	}

}