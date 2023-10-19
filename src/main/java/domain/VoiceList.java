package domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class VoiceList {

	private Integer id = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Name")
	private String name = null;

	public Integer getId() {
		return id;
	}

	public void setName(final String name) {
		this.name = name;
	}

}