package freemarker.template;

import java.io.IOException;

public interface ConfigurationUtil {

	static Template getTemplate(final Configuration instance, final String name) throws IOException {
		//
		if ((instance != null ? instance.getTemplateLoader() : null) == null) {
			//
			return null;
			//
		} // if
			//
		return instance != null && name != null ? instance.getTemplate(name) : null;
		//
	}

}