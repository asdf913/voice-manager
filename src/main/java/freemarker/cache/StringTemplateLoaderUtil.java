package freemarker.cache;

public interface StringTemplateLoaderUtil {

	static void putTemplate(final StringTemplateLoader instance, final String name, final String templateContent) {
		if (instance != null && name != null && templateContent != null) {
			instance.putTemplate(name, templateContent);
		}
	}

}