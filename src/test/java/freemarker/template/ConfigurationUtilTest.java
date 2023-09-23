package freemarker.template;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import freemarker.cache.TemplateLoader;
import io.github.toolfactory.narcissus.Narcissus;

class ConfigurationUtilTest {

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			throw new Throwable(method != null ? method.getName() : null);
			//
		}

	}

	@Test
	void testGetTemplate() throws Throwable {
		//
		Assertions.assertNull(ConfigurationUtil
				.getTemplate(cast(Configuration.class, Narcissus.allocateInstance(Configuration.class)), null));
		//
		final Configuration configuration = new Configuration(Configuration.getVersion());
		//
		ConfigurationUtil.setTemplateLoader(configuration, Reflection.newProxy(TemplateLoader.class, new IH()));
		//
		Assertions.assertDoesNotThrow(() -> ConfigurationUtil.getTemplate(configuration, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Test
	void testSetTemplate() {
		//
		Assertions.assertDoesNotThrow(() -> ConfigurationUtil
				.setTemplateLoader(cast(Configuration.class, Narcissus.allocateInstance(Configuration.class)), null));
		//
	}

}