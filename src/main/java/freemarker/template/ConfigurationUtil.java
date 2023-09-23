package freemarker.template;

import java.io.IOException;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import freemarker.cache.TemplateLoader;

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

	static void setTemplateLoader(final Configuration instance, final TemplateLoader templateLoader) {
		//
		if (instance != null) {
			//
			try {
				//
				if (FieldUtils.readDeclaredField(instance, "cache", true) == null) {
					//
					return;
					//
				} // if
					//
			} catch (final IllegalAccessException e) {
				//
				LoggerUtil.error(LoggerFactory.getLogger(ConfigurationUtil.class), e.getMessage(), e);
				//
			} // try
				//
			instance.setTemplateLoader(templateLoader);
			//
		} // if
			//
	}

}