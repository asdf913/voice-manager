package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPathFactory;

import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ConstantPushInstruction;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.LDC;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.CreationHelperUtil;
import org.apache.poi.ss.usermodel.DrawingUtil;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.slf4j.Logger;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.ReflectionUtils;
import org.w3c.dom.Node;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;
import com.healthmarketscience.jackcess.Database.FileFormat;

import domain.Voice;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Template;
import freemarker.template.Version;
import io.github.toolfactory.narcissus.Narcissus;
import j2html.attributes.Attribute;
import j2html.tags.specialized.ATag;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import net.miginfocom.swing.MigLayout;

class VoiceManagerTest {

	private static final String EMPTY = "";

	private static final String SPACE = " ";

	private static final int ZERO = 0;

	private static final int ONE = 1;

	private static final int TWO = 2;

	private static final int THREE = 3;

	private static Class<?> CLASS_OBJECT_MAP, CLASS_IH, CLASS_EXPORT_TASK = null;

	private static Method METHOD_TEST_AND_APPLY4, METHOD_FOR_EACH_STREAM, METHOD_FOR_EACH_ITERABLE, METHOD_INVOKE,
			METHOD_GET_PREFERRED_WIDTH, METHOD_ADD_CONTAINER2, METHOD_ADD_CONTAINER3, METHOD_ADD_ITERABLE,
			METHOD_MATCHER, METHOD_MATCHES, METHOD_VALUE_OF1, METHOD_AND_FAILABLE_PREDICATE, METHOD_OR,
			METHOD_CLEAR_DEFAULT_TABLE_MODEL, METHOD_TO_ARRAY_COLLECTION, METHOD_GET_TAB_INDEX_BY_TITLE,
			METHOD_GET_LIST, METHOD_TEST_AND_ACCEPT_PREDICATE, METHOD_TEST_AND_ACCEPT_BI_PREDICATE, METHOD_TO_URI_FILE,
			METHOD_TO_URI_URL, METHOD_ENCODE_TO_STRING, METHOD_GET_OS_VERSION_INFO_EX_MAP,
			METHOD_ERROR_OR_ASSERT_OR_SHOW_EXCEPTION2, METHOD_SET_VISIBLE, METHOD_GET_MAX_PAGE_PREFERRED_HEIGHT,
			METHOD_TEST_AND_RUN, METHOD_GET_IF_NULL, METHOD_SET_PREFERRED_WIDTH_ARRAY, METHOD_SET_PREFERRED_WIDTH_2,
			METHOD_GET_FIELD_BY_NAME, METHOD_CREATE_MICROSOFT_WINDOWS_COMPATIBILITY_WARNING_J_PANEL,
			METHOD_SET_FOCUS_CYCLE_ROOT, METHOD_SET_FOCUS_TRAVERSAL_POLICY, METHOD_GET_COMPONENTS,
			METHOD_GET_DECLARED_CONSTRUCTOR, METHOD_NEW_INSTANCE, METHOD_GET_NUMBER, METHOD_CREATE_IMPORT_RESULT_PANEL,
			METHOD_SET_SELECTED_INDEX, METHOD_GET_TITLED_COMPONENT_MAP = null;

	@BeforeAll
	static void beforeAll() throws Throwable {
		//
		final Class<?> clz = VoiceManager.class;
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_FOR_EACH_STREAM = clz.getDeclaredMethod("forEach", Stream.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_FOR_EACH_ITERABLE = clz.getDeclaredMethod("forEach", Iterable.class, FailableConsumer.class))
				.setAccessible(true);
		//
		(METHOD_INVOKE = clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_GET_PREFERRED_WIDTH = clz.getDeclaredMethod("getPreferredWidth", Component.class)).setAccessible(true);
		//
		(METHOD_ADD_CONTAINER2 = clz.getDeclaredMethod("add", Container.class, Component.class)).setAccessible(true);
		//
		(METHOD_ADD_CONTAINER3 = clz.getDeclaredMethod("add", Container.class, Component.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_ADD_ITERABLE = clz.getDeclaredMethod("add", Iterable.class, Number.class, Container.class))
				.setAccessible(true);
		//
		(METHOD_MATCHER = clz.getDeclaredMethod("matcher", Pattern.class, CharSequence.class)).setAccessible(true);
		//
		(METHOD_MATCHES = clz.getDeclaredMethod("matches", Matcher.class)).setAccessible(true);
		//
		(METHOD_VALUE_OF1 = clz.getDeclaredMethod("valueOf", String.class)).setAccessible(true);
		//
		(METHOD_AND_FAILABLE_PREDICATE = clz.getDeclaredMethod("and", Object.class, FailablePredicate.class,
				FailablePredicate.class, FailablePredicate[].class)).setAccessible(true);
		//
		(METHOD_OR = clz.getDeclaredMethod("or", Predicate.class, Object.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_CLEAR_DEFAULT_TABLE_MODEL = clz.getDeclaredMethod("clear", DefaultTableModel.class))
				.setAccessible(true);
		//
		(METHOD_TO_ARRAY_COLLECTION = clz.getDeclaredMethod("toArray", Collection.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_GET_TAB_INDEX_BY_TITLE = clz.getDeclaredMethod("getTabIndexByTitle", List.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_GET_LIST = clz.getDeclaredMethod("get", List.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT_PREDICATE = clz.getDeclaredMethod("testAndAccept", FailablePredicate.class,
				Object.class, FailableConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT_BI_PREDICATE = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class,
				Object.class, FailableBiConsumer.class)).setAccessible(true);
		//
		(METHOD_TO_URI_FILE = clz.getDeclaredMethod("toURI", File.class)).setAccessible(true);
		//
		(METHOD_TO_URI_URL = clz.getDeclaredMethod("toURI", URL.class)).setAccessible(true);
		//
		(METHOD_ENCODE_TO_STRING = clz.getDeclaredMethod("encodeToString", Encoder.class, byte[].class))
				.setAccessible(true);
		//
		(METHOD_GET_OS_VERSION_INFO_EX_MAP = clz.getDeclaredMethod("getOsVersionInfoExMap")).setAccessible(true);
		//
		(METHOD_ERROR_OR_ASSERT_OR_SHOW_EXCEPTION2 = clz.getDeclaredMethod("errorOrAssertOrShowException", Boolean.TYPE,
				Throwable.class)).setAccessible(true);
		//
		(METHOD_SET_VISIBLE = clz.getDeclaredMethod("setVisible", Component.class, Boolean.TYPE)).setAccessible(true);
		//
		(METHOD_GET_MAX_PAGE_PREFERRED_HEIGHT = clz.getDeclaredMethod("getMaxPagePreferredHeight", Object.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_RUN = clz.getDeclaredMethod("testAndRun", Boolean.TYPE, FailableRunnable.class))
				.setAccessible(true);
		//
		(METHOD_GET_IF_NULL = clz.getDeclaredMethod("getIfNull", Object.class, FailableSupplier.class))
				.setAccessible(true);
		//
		(METHOD_SET_PREFERRED_WIDTH_ARRAY = clz.getDeclaredMethod("setPreferredWidth", Integer.TYPE, Component[].class))
				.setAccessible(true);
		//
		(METHOD_SET_PREFERRED_WIDTH_2 = clz.getDeclaredMethod("setPreferredWidth", Component.class, Supplier.class))
				.setAccessible(true);
		//
		(METHOD_GET_FIELD_BY_NAME = clz.getDeclaredMethod("getFieldByName", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MICROSOFT_WINDOWS_COMPATIBILITY_WARNING_J_PANEL = clz.getDeclaredMethod(
				"createMicrosoftWindowsCompatibilityWarningJPanel", LayoutManager.class, String.class))
				.setAccessible(true);
		//
		(METHOD_SET_FOCUS_CYCLE_ROOT = clz.getDeclaredMethod("setFocusCycleRoot", Container.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_SET_FOCUS_TRAVERSAL_POLICY = clz.getDeclaredMethod("setFocusTraversalPolicy", Container.class,
				FocusTraversalPolicy.class)).setAccessible(true);
		//
		(METHOD_GET_COMPONENTS = clz.getDeclaredMethod("getComponents", Container.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_CONSTRUCTOR = clz.getDeclaredMethod("getDeclaredConstructor", Class.class, Class[].class))
				.setAccessible(true);
		//
		(METHOD_NEW_INSTANCE = clz.getDeclaredMethod("newInstance", Constructor.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_GET_NUMBER = clz.getDeclaredMethod("getNumber", Object.class, Iterable.class)).setAccessible(true);
		//
		(METHOD_CREATE_IMPORT_RESULT_PANEL = clz.getDeclaredMethod("createImportResultPanel", LayoutManager.class))
				.setAccessible(true);
		//
		(METHOD_SET_SELECTED_INDEX = clz.getDeclaredMethod("setSelectedIndex", JTabbedPane.class, Number.class))
				.setAccessible(true);
		//
		(METHOD_GET_TITLED_COMPONENT_MAP = clz.getDeclaredMethod("getTitledComponentMap", Map.class, String[].class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Set<Entry<?, ?>> entrySet = null;

		private String toString = null;

		private Boolean isInstalled = null;

		private Iterator<?> iterator = null;

		private Map<Object, Object> beansOfType = null;

		private Map<Object, BeanDefinition> beanDefinitions = null;

		private Map<Object, Object> beanDefinitionAttributes = null;

		private Object[] toArray = null;

		private Collection<?> values = null;

		private Map<Object, BeanDefinition> getBeanDefinitions() {
			if (beanDefinitions == null) {
				beanDefinitions = new LinkedHashMap<>();
			}
			return beanDefinitions;
		}

		private Map<Object, Object> getBeanDefinitionAttributes() {
			if (beanDefinitionAttributes == null) {
				beanDefinitionAttributes = new LinkedHashMap<>();
			}
			return beanDefinitionAttributes;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (ReflectionUtils.isToStringMethod(method)) {
				//
				return toString;
				//
			} else if (ReflectionUtils.isEqualsMethod(method) && args != null && args.length > 0) {
				//
				return EqualsBuilder.reflectionEquals(this, args[0]);
				//
			} // if
				//
			final Class<?> returnType = method != null ? method.getReturnType() : null;
			//
			if (Objects.equals(Void.TYPE, returnType)) {
				//
				return null;
				//
			} // if
				//
			if (proxy instanceof ListableBeanFactory && Objects.equals(methodName, "getBeansOfType")) {
				//
				return beansOfType;
				//
			} // if
				//
			if (proxy instanceof Iterable && Objects.equals(methodName, "iterator")) {
				//
				return iterator;
				//
			} // if
				//
			if (proxy instanceof Collection) {
				//
				if (Objects.equals(methodName, "toArray")) {
					//
					return toArray;
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return entrySet;
					//
				} else if (Objects.equals(methodName, "values")) {
					//
					return values;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "toArray")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof SpeechApi) {
				//
				if (Objects.equals(methodName, "isInstalled")) {
					//
					return isInstalled;
					//
				} // if
					//
			} else if (proxy instanceof ConfigurableListableBeanFactory) {
				//
				if (Objects.equals(methodName, "getBeanDefinition") && args != null && args.length > 0) {
					//
					return MapUtils.getObject(getBeanDefinitions(), args[0]);
					//
				} // if
					//
			} else if (proxy instanceof BeanDefinition && Objects.equals(methodName, "hasAttribute") && args != null
					&& args.length > 0) {
				//
				return Util.containsKey(getBeanDefinitionAttributes(), args[0]);
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static class MH implements MethodHandler {

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof Component && Objects.equals(methodName, "getPreferredSize")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManager instance = null;

	private ObjectMapper objectMapper = null;

	private IH ih = null;

	private Stream<?> stream = null;

	private SpeechApi speechApi = null;

	private BeanDefinition beanDefinition = null;

	private Node node = null;

	private Iterable<?> iterable = null;

	private Logger logger = null;

	private Collection<?> collection = null;

	private ConfigurableListableBeanFactory configurableListableBeanFactory = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		final Constructor<VoiceManager> constructor = getDeclaredConstructor(VoiceManager.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		objectMapper = new ObjectMapper();
		//
		instance = !GraphicsEnvironment.isHeadless() ? newInstance(constructor)
				: Util.cast(VoiceManager.class, Narcissus.allocateInstance(VoiceManager.class));
		//
		stream = Reflection.newProxy(Stream.class, ih = new IH());
		//
		speechApi = Reflection.newProxy(SpeechApi.class, ih);
		//
		beanDefinition = Reflection.newProxy(BeanDefinition.class, ih);
		//
		node = Reflection.newProxy(Node.class, ih);
		//
		iterable = Reflection.newProxy(Iterable.class, ih);
		//
		logger = Reflection.newProxy(Logger.class, ih);
		//
		collection = Reflection.newProxy(Collection.class, ih);
		//
		configurableListableBeanFactory = Reflection.newProxy(ConfigurableListableBeanFactory.class, ih);
		//
	}

	@AfterEach
	void afterEach() {
		//
		final File file = Path.of(".html").toFile();
		//
		if (file.exists() && file.isFile() && file.length() == 0) {
			//
			if (logger != null) {
				//
				logger.info("file to be deleted={}", file.getAbsolutePath());
				//
			} // if
				//
			file.delete();
			//
		} // if
			//
	}

	@Test
	void testSetFreeMarkerVersion() throws NoSuchFieldException, IllegalAccessException, IOException {
		//
		final Field freeMarkerVersion = VoiceManager.class.getDeclaredField("freeMarkerVersion");
		//
		if (freeMarkerVersion != null) {
			//
			freeMarkerVersion.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setFreeMarkerVersion(null));
		//
		Assertions.assertNull(get(freeMarkerVersion, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setFreeMarkerVersion(ONE));
		//
		Assertions.assertEquals(new Version(0, 0, ONE), get(freeMarkerVersion, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setFreeMarkerVersion(EMPTY));
		//
		Assertions.assertEquals(new Version(EMPTY), get(freeMarkerVersion, instance));
		//
		final Version version = freemarker.template.Configuration.getVersion();
		//
		Assertions.assertDoesNotThrow(() -> instance.setFreeMarkerVersion(version));
		//
		Assertions.assertSame(version, get(freeMarkerVersion, instance));
		//
		set(freeMarkerVersion, instance, null);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}",
				() -> instance.setFreeMarkerVersion(new int[] {}));
		//
		Assertions.assertDoesNotThrow(() -> instance.setFreeMarkerVersion(new int[] { ONE, TWO, THREE }));
		//
		Assertions.assertEquals(new Version(ONE, TWO, THREE), get(freeMarkerVersion, instance));
		//
	}

	@Test
	void testGetMimeTypeAndBase64EncodedString() throws IOException {
		//
		Assertions.assertEquals(Pair.of(null, null), VoiceManager.getMimeTypeAndBase64EncodedString(null, null));
		//
		Assertions.assertEquals(Pair.of(null, null), VoiceManager.getMimeTypeAndBase64EncodedString(EMPTY, null));
		//
		Assertions.assertEquals(Pair.of(null, null), VoiceManager.getMimeTypeAndBase64EncodedString(EMPTY, EMPTY));
		//
	}

	@Test
	void testPostProcessBeanFactory() throws IllegalAccessException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(null));
		//
		final MutableComboBoxModel<?> mcbm = Reflection.newProxy(MutableComboBoxModel.class, ih);
		//
		FieldUtils.writeDeclaredField(instance, "cbmAudioFormatWrite", mcbm, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(null));
		//
		FieldUtils.writeDeclaredField(instance, "cbmAudioFormatExecute", mcbm, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(null));
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
		if (ih != null) {
			//
			ih.beansOfType = Reflection.newProxy(Map.class, ih);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
		if (ih != null) {
			//
			ih.beansOfType = Collections.singletonMap(null, null);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
		Assertions
				.assertDoesNotThrow(() -> Util.put(ih != null ? ih.getBeanDefinitions() : null, null, beanDefinition));
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
		Assertions.assertDoesNotThrow(() -> Util.put(ih != null ? ih.getBeanDefinitions() : null, "format", null));
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(configurableListableBeanFactory));
		//
	}

	@Test
	void testAfterPropertiesSet() throws IOException, IllegalAccessException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final boolean headless = GraphicsEnvironment.isHeadless();
		//
		if (headless) {
			//
			Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
			//
		} else {
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
					"{localizedMessage=cannot add to layout: unknown constraint: wrap, message=cannot add to layout: unknown constraint: wrap}",
					() -> instance.afterPropertiesSet());
			//
		} // if
			//
		if (instance != null) {
			//
			instance.setSpeechApi(speechApi);
			//
		} // if
			//
		if (ih != null) {
			//
			ih.isInstalled = Boolean.FALSE;
			//
		} // if
			//
		if (headless) {
			//
			Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
			//
		} else {
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
					"{localizedMessage=cannot add to layout: unknown constraint: wrap, message=cannot add to layout: unknown constraint: wrap}",
					() -> instance.afterPropertiesSet());
			//
		} // if
			//
		if (ih != null) {
			//
			ih.isInstalled = Boolean.TRUE;
			//
		} // if
			//
		final Class<?> clz = getClass();
		//
		final URL url = getResource(clz, "/help.html.ftl");
		//
		UnknownHostException uhe = null;
		//
		if (url == null) {
			//
			Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
			//
		} else {
			//
			final freemarker.template.Configuration configuration = new freemarker.template.Configuration(
					freemarker.template.Configuration.getVersion());
			//
			final TemplateLoader tl = new ClassTemplateLoader(clz, "/");
			//
			tl.findTemplateSource("/help.html.ftl");
			//
			configuration.setTemplateLoader(tl);
			//
			final Template template = configuration.getTemplate("/help.html.ftl");
			//
			final Iterable<String> urls = new FailableStream<>(Util.filter(
					Stream.of(Util.cast(Object[].class,
							FieldUtils.readField(FieldUtils.readDeclaredField(template, "rootElement", true),
									"childBuffer", true))),
					x -> Objects.equals(Util.getName(Util.getClass(x)), "freemarker.core.Assignment"))).map(x -> {
						//
						final URI uri = new URI(Util.toString(FieldUtils
								.readDeclaredField(FieldUtils.readDeclaredField(x, "valueExp", true), "value", true)));
						//
						return StringUtils.join("", uri.getScheme(), "://", uri.getHost());
						//
					}).collect(Collectors.toSet());
			//
			if (urls != null && urls.iterator() != null) {
				//
				for (final String u : urls) {
					//
					try (final InputStream is = new URL(u).openStream()) {
						//
						IOUtils.toByteArray(is);
						//
					} catch (final UnknownHostException e) {
						//
						uhe = e;
						//
					} // try
						//
				} // for
					//
			} // if
				//
			if (uhe != null) {
				//
				if (GraphicsEnvironment.isHeadless()) {
					// )
					Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
					//
				} else {
					// )
					Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
					//
				} // if
					//
			} else {
				//
				Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
				//
			} // if
				//
		} // if
			//
		if (url == null) {
			//
			Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
			//
		} else {
			//
			if (uhe != null) {
				//
				if (GraphicsEnvironment.isHeadless()) {
					// )
					Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
					//
				} else {
					// )
					Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
					//
				} // if
					//
			} else {
				//
				Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
				//
			} // if
				//
		} // if
			//
		if (url == null) {
			//
			Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
			//
		} else {
			//
			if (uhe != null) {
				//
				if (GraphicsEnvironment.isHeadless()) {
					// )
					Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
					//
				} else {
					// )
					Assertions.assertThrows(RuntimeException.class, () -> instance.afterPropertiesSet());
					//
				} // if
					//
			} else {
				//
				Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
				//
			} // if
				//
		} // if
			//
	}

	private static URL getResource(final Class<?> instance, final String name) {
		return instance != null ? instance.getResource(name) : null;
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void set(final Field field, final Object instance, final Object value)
			throws IllegalAccessException {
		if (field != null) {
			field.set(instance, value);
		}
	}

	@Test
	void testActionPerformed1() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(EMPTY, 0, null)));
		//
		// btnSpeak
		//
		final AbstractButton btnSpeak = new JButton();
		//
		final ActionEvent actionEventBtnSpeak = new ActionEvent(btnSpeak, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeak));
		//
		if (instance != null) {
			//
			instance.setSpeechApi(speechApi);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeak));
		//
		// btnSpeechRateSlower
		//
		final AbstractButton btnSpeechRateSlower = new JButton();
		//
		final ActionEvent actionEventBtnSpeechRateSlower = new ActionEvent(btnSpeechRateSlower, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeechRateSlower));
		//
		// btnSpeechRateNormal
		//
		final AbstractButton btnSpeechRateNormal = new JButton();
		//
		final ActionEvent actionEventBtnSpeechRateNormal = new ActionEvent(btnSpeechRateNormal, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeechRateNormal));
		//
		// btnSpeechRateFaster
		//
		final AbstractButton btnSpeechRateFaster = new JButton();
		//
		final ActionEvent actionEventBtnSpeechRateFaster = new ActionEvent(btnSpeechRateFaster, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeechRateFaster));
		//
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent actionEvent) {
		if (instance != null) {
			instance.actionPerformed(actionEvent);
		} // if
	}

	private static void stateChanged(final ChangeListener instance, final ChangeEvent evt) {
		if (instance != null) {
			instance.stateChanged(evt);
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, x -> x));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, x -> x));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY4.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForEach() {
		//
		Assertions.assertDoesNotThrow(() -> forEach((Stream<?>) null, null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Stream.empty(), null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Stream.empty(), x -> {
		}));
		//
		Assertions.assertDoesNotThrow(() -> forEach(stream, null));
		//
		final Collection<?> collection = Collections.emptySet();
		//
		Assertions.assertDoesNotThrow(() -> forEach(collection, null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(collection, x -> {
		}));
		//
		Assertions.assertDoesNotThrow(() -> forEach(iterable, null));
		//
	}

	private static <T> void forEach(final Stream<T> instance, final Consumer<? super T> action) throws Throwable {
		try {
			METHOD_FOR_EACH_STREAM.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, E extends Throwable> void forEach(final Iterable<T> instance,
			final FailableConsumer<? super T, E> action) throws Throwable {
		try {
			METHOD_FOR_EACH_ITERABLE.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testInvoke() throws Throwable {
		//
		Assertions.assertNull(invoke(null, null));
		//
	}

	private static Object invoke(final Method method, final Object instance, Object... args) throws Throwable {
		try {
			return METHOD_INVOKE.invoke(null, method, instance, args);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPreferredWidth() throws Throwable {
		//
		Assertions.assertNull(getPreferredWidth(null));
		//
	}

	private static Double getPreferredWidth(final Component c) throws Throwable {
		try {
			final Object obj = METHOD_GET_PREFERRED_WIDTH.invoke(null, c);
			if (obj == null) {
				return null;
			} else if (obj instanceof Double) {
				return (Double) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAdd() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> add((Container) null, null));
		//
		final Container container = Util.cast(Container.class, Narcissus.allocateInstance(Container.class));
		//
		final Component component = new JTextField();
		//
		Assertions.assertDoesNotThrow(() -> add(container, component));
		//
		Assertions.assertDoesNotThrow(() -> add(container, component, null));
		//
		Assertions.assertDoesNotThrow(() -> add(new JPanel(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> add(new JPanel(), component, null));
		//
		Assertions.assertDoesNotThrow(() -> add(Collections.singleton(null), null, null));
		//
		final Iterable<Entry<String, IntFunction>> entrySet1 = Util.entrySet(Map.of("", x -> null));
		//
		Assertions.assertDoesNotThrow(() -> add(entrySet1, null, null));
		//
		Assertions.assertDoesNotThrow(() -> add(entrySet1, Integer.valueOf(0), null));
		//
		final Iterable<Entry<String, IntFunction>> entrySet2 = Util.entrySet(Map.of("", x -> container));
		//
		final Number number = Integer.valueOf(0);
		//
		Assertions.assertDoesNotThrow(() -> add(entrySet2, number, null));
		//
		Assertions.assertDoesNotThrow(() -> add(entrySet2, number, container));
		//
	}

	private static void add(final Container instance, final Component comp) throws Throwable {
		try {
			METHOD_ADD_CONTAINER2.invoke(null, instance, comp);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void add(final Container instance, final Component comp, final Object constraints) throws Throwable {
		try {
			METHOD_ADD_CONTAINER3.invoke(null, instance, comp, constraints);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void add(final Iterable<Entry<String, IntFunction>> entrySet, final Number preferredHeight,
			final Container container) throws Throwable {
		try {
			METHOD_ADD_ITERABLE.invoke(null, entrySet, preferredHeight, container);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMatcher() throws Throwable {
		//
		Assertions.assertNull(matcher(null, null));
		//
		Assertions.assertNull(matcher(Util.cast(Pattern.class, Narcissus.allocateInstance(Pattern.class)), ""));
		//
	}

	private static Matcher matcher(final Pattern pattern, final CharSequence input) throws Throwable {
		try {
			final Object obj = METHOD_MATCHER.invoke(null, pattern, input);
			if (obj == null) {
				return null;
			} else if (obj instanceof Matcher) {
				return (Matcher) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMatches() throws Throwable {
		//
		Assertions.assertFalse(matches(Util.cast(Matcher.class, Narcissus.allocateInstance(Matcher.class))));
		//
	}

	private static boolean matches(final Matcher instance) throws Throwable {
		try {
			final Object obj = METHOD_MATCHES.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testValueOf() throws Throwable {
		//
		Assertions.assertNull(valueOf(EMPTY));
		//
		Assertions.assertNull(valueOf(SPACE));
		//
	}

	private static Integer valueOf(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_VALUE_OF1.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testAnd() throws Throwable {
		//
		Assertions.assertFalse(and((Object) null, FailablePredicate.truePredicate(), null));
		//
	}

	private static <T, E extends Throwable> boolean and(final T value, final FailablePredicate<T, E> a,
			final FailablePredicate<T, E> b, final FailablePredicate<T, E>... ps) throws Throwable {
		try {
			final Object obj = METHOD_AND_FAILABLE_PREDICATE.invoke(null, value, a, b, ps);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOr() throws Throwable {
		//
		Assertions.assertTrue(or(x -> Objects.equals(x, EMPTY), null, EMPTY));
		//
		Assertions.assertFalse(or(Predicates.alwaysFalse(), null, null, (Object[]) null));
		//
	}

	private static <T> boolean or(final Predicate<T> predicate, final T a, final T b, final T... values)
			throws Throwable {
		try {
			final Object obj = METHOD_OR.invoke(null, predicate, a, b, values);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testClear() {
		//
		Assertions.assertDoesNotThrow(() -> clear((DefaultTableModel) null));
		//
		Assertions.assertDoesNotThrow(() -> clear(new DefaultTableModel()));
		//
	}

	private static void clear(final DefaultTableModel instance) throws Throwable {
		try {
			METHOD_CLEAR_DEFAULT_TABLE_MODEL.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToArray() throws Throwable {
		//
		Assertions.assertNull(toArray((Collection<?>) null, null));
		//
		Assertions.assertNull(toArray((Stream<?>) null, null));
		//
		final Stream<?> empty = Stream.empty();
		//
		Assertions.assertNull(toArray(empty, null));
		//
		Assertions.assertThrows(NullPointerException.class, () -> toArray(empty, x -> null));
		//
		Assertions.assertNull(toArray(stream, null));
		//
		Assertions.assertNull(toArray(Collections.emptyList(), null));
		//
		Assertions.assertNull(toArray(collection, null));
		//
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) throws Throwable {
		try {
			return (T[]) METHOD_TO_ARRAY_COLLECTION.invoke(null, instance, array);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, A> A[] toArray(final Stream<T> instance, final IntFunction<A[]> generator) {
		//
		return instance != null && (generator != null || Proxy.isProxyClass(Util.getClass(instance)))
				? instance.toArray(generator)
				: null;
		//
	}

	@Test
	void testGetTabIndexByTitle() throws Throwable {
		//
		Assertions.assertNull(getTabIndexByTitle(Collections.singletonList(null), null));
		//
	}

	private static Integer getTabIndexByTitle(final List<?> pages, final Object title) throws Throwable {
		try {
			final Object obj = METHOD_GET_TAB_INDEX_BY_TITLE.invoke(null, pages, title);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get(null, 0));
		//
	}

	private static <E> E get(final List<E> instance, final int index) throws Throwable {
		try {
			return (E) METHOD_GET_LIST.invoke(null, instance, index);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetCellComment() {
		//
		Assertions.assertDoesNotThrow(() -> CellUtil.setCellComment(null, DrawingUtil.createCellComment(
				SheetUtil.createDrawingPatriarch(null), CreationHelperUtil.createClientAnchor(null))));
		//
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(FailablePredicate.falsePredicate(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(FailablePredicate.truePredicate(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> false, null, null, null));
		//
		final BiPredicate<?, ?> biPredicate = (a, b) -> true;
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(biPredicate, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(biPredicate, null, null, (a, b) -> {
		}));
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final FailablePredicate<T, E> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT_PREDICATE.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> biPredicate, final T t,
			final U u, final FailableBiConsumer<T, U, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT_BI_PREDICATE.invoke(null, biPredicate, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToURI() throws Throwable {
		//
		Assertions.assertNull(toURI((URL) null));
		//
		Assertions.assertNull(toURI(Util.cast(URL.class, Narcissus.allocateInstance(URL.class))));
		//
		final File file = Path.of("").toFile();
		//
		Assertions.assertNotNull(toURI(file));
		//
		final URI uri = file.toURI();
		//
		Assertions.assertNotNull(toURI(toURL(uri)));
		//
	}

	private static URI toURI(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_URI_FILE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URI) {
				return (URI) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static URI toURI(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_URI_URL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URI) {
				return (URI) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private class InnerClass {

		private interface InnerInterface {

			InnerInterface INSTANCE = Reflection.newProxy(InnerInterface.class, new IH());

			String getDllPath();

		}

	}

	@Test
	void testEncodeToString() throws Throwable {
		//
		Assertions.assertNull(encodeToString(null, null));
		//
		final Encoder encoder = Base64.getEncoder();
		//
		Assertions.assertNull(encodeToString(encoder, null));
		//
		Assertions.assertEquals(EMPTY, encodeToString(encoder, new byte[] {}));
		//
	}

	private static String encodeToString(final Encoder instance, final byte[] src) throws Throwable {
		try {
			final Object obj = METHOD_ENCODE_TO_STRING.invoke(null, instance, src);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetOsVersionInfoExMap() throws Throwable {
		//
		if (isUnderWindows()) {
			//
			Assertions.assertNotNull(getOsVersionInfoExMap());
			//
		} // if
			//
	}

	private static boolean isUnderWindows() throws Throwable {
		//
		final FileSystem fs = FileSystems.getDefault();
		//
		return Objects.equals("sun.nio.fs.WindowsFileSystemProvider",
				Util.getName(Util.getClass(fs != null ? fs.provider() : null)));
		//
	}

	private static Map<String, Object> getOsVersionInfoExMap() throws Throwable {
		try {
			final Object obj = METHOD_GET_OS_VERSION_INFO_EX_MAP.invoke(null);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testErrorOrAssertOrShowException() {
		//
		Assertions.assertDoesNotThrow(() -> errorOrAssertOrShowException(true, null));
		//
	}

	private static void errorOrAssertOrShowException(final boolean headless, final Throwable throwable)
			throws Throwable {
		try {
			METHOD_ERROR_OR_ASSERT_OR_SHOW_EXCEPTION2.invoke(null, headless, throwable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetVisible() {
		//
		Assertions.assertDoesNotThrow(() -> setVisible(new JTextField(), false));
		//
	}

	private static void setVisible(final Component instance, final boolean b) throws Throwable {
		try {
			METHOD_SET_VISIBLE.invoke(null, instance, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMaxPagePreferredHeight() throws Throwable {
		//
		Assertions.assertNull(getMaxPagePreferredHeight(null));
		//
		final Object jTabbedPane = new JTabbedPane();
		//
		final List<?> pages = Util.cast(List.class,
				Narcissus.getObjectField(jTabbedPane, Util.getDeclaredField(JTabbedPane.class, "pages")));
		//
		Util.add(pages, null);
		//
		Assertions.assertNull(getMaxPagePreferredHeight(jTabbedPane));
		//
	}

	private static Double getMaxPagePreferredHeight(final Object jTabbedPane) throws Throwable {
		try {
			final Object obj = METHOD_GET_MAX_PAGE_PREFERRED_HEIGHT.invoke(null, jTabbedPane);
			if (obj == null) {
				return null;
			} else if (obj instanceof Double) {
				return (Double) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static URL toURL(final URI instance) throws MalformedURLException {
		return instance != null ? instance.toURL() : null;
	}

	@Test
	void testTestAndRun() {
		//
		Assertions.assertDoesNotThrow(() -> testAndRun(true, null));
		//
	}

	private static <E extends Throwable> void testAndRun(final boolean b, final FailableRunnable<E> runnable)
			throws Throwable {
		try {
			METHOD_TEST_AND_RUN.invoke(null, b, runnable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetIfNull() throws Throwable {
		//
		Assertions.assertNull(getIfNull(null, null));
		//
	}

	private static <T, E extends Throwable> T getIfNull(final T object, final FailableSupplier<T, E> defaultSupplier)
			throws Throwable {
		try {
			return (T) METHOD_GET_IF_NULL.invoke(null, object, defaultSupplier);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetPreferredWidth() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(ZERO, (Component[]) null));
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(ZERO, (Component) null));
		//
		final MH mh = new MH();
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(Component.class);
		//
		final Object object = newInstance(getDeclaredConstructor(proxyFactory.createClass()));
		//
		if (object instanceof ProxyObject) {
			//
			((ProxyObject) object).setHandler(mh);
			//
		} // if
			//
		final Component component = Util.cast(Component.class, object);
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(component, null));
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(component, () -> Double.valueOf(0)));
		//
	}

	private static void setPreferredWidth(final int width, final Component... cs) throws Throwable {
		try {
			METHOD_SET_PREFERRED_WIDTH_ARRAY.invoke(null, width, cs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void setPreferredWidth(final Component component, final Supplier<Double> supplier) throws Throwable {
		try {
			METHOD_SET_PREFERRED_WIDTH_2.invoke(null, component, supplier);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFieldByName() throws Throwable {
		//
		Assertions.assertNull(getFieldByName(null, null));
		//
	}

	private static Field getFieldByName(final Class<?> clz, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_FIELD_BY_NAME.invoke(null, clz, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMicrosoftWindowsCompatibilityWarningJPanel() throws Throwable {
		//
		Assertions.assertNotNull(createMicrosoftWindowsCompatibilityWarningJPanel(null, null));
		//
		Assertions.assertNotNull(createMicrosoftWindowsCompatibilityWarningJPanel(new MigLayout(), null));
		//
	}

	private static JPanel createMicrosoftWindowsCompatibilityWarningJPanel(final LayoutManager lm,
			final String microsoftWindowsCompatibilitySettingsPageUrl) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MICROSOFT_WINDOWS_COMPATIBILITY_WARNING_J_PANEL.invoke(null, lm,
					microsoftWindowsCompatibilitySettingsPageUrl);
			if (obj == null) {
				return null;
			} else if (obj instanceof JPanel) {
				return (JPanel) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetFocusCycleRoot() {
		//
		Assertions.assertDoesNotThrow(() -> setFocusCycleRoot(null, false));
		//
	}

	private static void setFocusCycleRoot(final Container instance, final boolean focusCycleRoot) throws Throwable {
		try {
			METHOD_SET_FOCUS_CYCLE_ROOT.invoke(null, instance, focusCycleRoot);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetFocusTraversalPolicy() {
		//
		Assertions.assertDoesNotThrow(() -> setFocusTraversalPolicy(null, null));
		//
	}

	private static void setFocusTraversalPolicy(final Container instance, final FocusTraversalPolicy policy)
			throws Throwable {
		try {
			METHOD_SET_FOCUS_TRAVERSAL_POLICY.invoke(null, instance, policy);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetComponents() throws Throwable {
		//
		Assertions.assertNull(getComponents(null));
		//
	}

	private static Component[] getComponents(final Container instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_COMPONENTS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Component[]) {
				return (Component[]) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testgGetDeclaredConstructor() throws Throwable {
		//
		Assertions.assertNull(getDeclaredConstructor(null));
		//
	}

	private static <T> Constructor<T> getDeclaredConstructor(final Class<T> clz, final Class<?>... parameterTypes)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_CONSTRUCTOR.invoke(null, clz, parameterTypes);
			if (obj == null) {
				return null;
			} else if (obj instanceof Constructor) {
				return (Constructor) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testNewInstance() throws Throwable {
		//
		Assertions.assertNull(newInstance(null));
		//
	}

	private static <T> T newInstance(final Constructor<T> constructor, final Object... initargs) throws Throwable {
		try {
			return (T) METHOD_NEW_INSTANCE.invoke(null, constructor, initargs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetNumber() throws Throwable {
		//
		Assertions.assertNull(getNumber(null, Collections.singleton(Boolean.class.getDeclaredField("TRUE"))));
		//
		final Collection<?> collection = new ArrayList<>();
		//
		Assertions.assertEquals(Unit.with(Integer.valueOf(collection.size())),
				getNumber(collection, Collections.singleton(ArrayList.class.getDeclaredField("size"))));
		//
		Assertions.assertEquals(Unit.with(Long.valueOf(Long.MAX_VALUE)),
				getNumber(null, Collections.singleton(Long.class.getDeclaredField("MAX_VALUE"))));
		//
	}

	private static IValue0<Number> getNumber(final Object instance, final Iterable<Field> fs) throws Throwable {
		try {
			final Object obj = METHOD_GET_NUMBER.invoke(null, instance, fs);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateImportResultPanel() throws Throwable {
		//
		Assertions.assertNotNull(createImportResultPanel(null));
		//
	}

	private JPanel createImportResultPanel(final LayoutManager layoutManager) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_IMPORT_RESULT_PANEL.invoke(instance, layoutManager);
			if (obj == null) {
				return null;
			} else if (obj instanceof JPanel) {
				return (JPanel) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetSelectedIndex() {
		//
		final JTabbedPane jTabbedPane = new JTabbedPane();
		//
		Assertions.assertDoesNotThrow(() -> setSelectedIndex(jTabbedPane, null));
		//
		final Number zero = Integer.valueOf(0);
		//
		Assertions.assertDoesNotThrow(() -> setSelectedIndex(jTabbedPane, zero));
		//
		jTabbedPane.addTab(null, null);
		//
		Assertions.assertDoesNotThrow(() -> setSelectedIndex(jTabbedPane, zero));
		//
	}

	private static void setSelectedIndex(final JTabbedPane instance, final Number index) throws Throwable {
		try {
			METHOD_SET_SELECTED_INDEX.invoke(null, instance, index);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static class TitledComponent extends Component implements Titled {

		private String title = null;

		public TitledComponent(final String title) {
			this.title = title;
		}

		@Override
		public String getTitle() {
			return title;
		}

	}

	@Test
	void testGetTitledComponentMap() throws Throwable {
		//
		final Map<String, Component> emptyMap = Collections.emptyMap();
		//
		Assertions.assertEquals(emptyMap, getTitledComponentMap(null, null));
		//
		final Map<String, Component> map = Reflection.newProxy(Map.class, ih);
		//
		Assertions.assertEquals(emptyMap, getTitledComponentMap(map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertEquals(emptyMap, getTitledComponentMap(map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(Pair.of(null, null));
			//
		} // if
			//
		Assertions.assertEquals(emptyMap, getTitledComponentMap(map, null));
		//
		final Object object = new TitledComponent(null);
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(Pair.of(null, object));
			//
		} // if
			//
		Assertions.assertEquals(Collections.singletonMap(null, object), getTitledComponentMap(map, null));
		//
		final String a = "a";
		//
		final Object oa = new TitledComponent(a);
		//
		final String b = "b";
		//
		final Object ob = new TitledComponent(b);
		//
		if (ih != null) {
			//
			ih.entrySet = new LinkedHashSet<Map.Entry<?, ?>>(Arrays.asList(Pair.of(null, oa), Pair.of(null, ob)));
			//
		} // if
			//
		Assertions.assertEquals(Map.of(a, oa, b, ob), getTitledComponentMap(map, a, b));
		//
		Assertions.assertEquals(Map.of(b, ob, a, oa), getTitledComponentMap(map, b, a));
		//
	}

	private static Map<String, Component> getTitledComponentMap(final Map<String, Component> cs, final String... orders)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_TITLED_COMPONENT_MAP.invoke(null, cs, orders);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIh1() throws Throwable {
		//
		final InvocationHandler ih = createVoiceManagerIH();
		//
		if (ih != null) {
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{}", () -> ih.invoke(null, null, null));
			//
			// org.springframework.context.support.VoiceManager$ObjectMap.getObject(java.lang.Class)
			//
			final Method getObject = CLASS_OBJECT_MAP != null
					? CLASS_OBJECT_MAP.getDeclaredMethod("getObject", Class.class)
					: null;
			//
			final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, ih);
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=getObject, message=getObject}",
					() -> ih.invoke(objectMap, getObject, null));
			//
			final Object[] empty = new Object[] {};
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=getObject, message=getObject}",
					() -> ih.invoke(objectMap, getObject, empty));
			//
			// org.springframework.context.support.VoiceManager$ObjectMap.containsObject(java.lang.Class)
			//
			final Method containsObject = CLASS_OBJECT_MAP != null
					? CLASS_OBJECT_MAP.getDeclaredMethod("containsObject", Class.class)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class,
					"{localizedMessage=containsObject, message=containsObject}",
					() -> ih.invoke(objectMap, containsObject, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class,
					"{localizedMessage=containsObject, message=containsObject}",
					() -> ih.invoke(objectMap, containsObject, empty));
			//
			Assertions.assertEquals(Boolean.FALSE, ih.invoke(objectMap, containsObject, new Object[] { null }));
			//
			// org.springframework.context.support.VoiceManager$ObjectMap.setObject(java.lang.Class,java.lang.Object)
			//
			final Method setObject = CLASS_OBJECT_MAP != null
					? CLASS_OBJECT_MAP.getDeclaredMethod("setObject", Class.class, Object.class)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setObject, message=setObject}",
					() -> ih.invoke(objectMap, setObject, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setObject, message=setObject}",
					() -> ih.invoke(objectMap, setObject, empty));
			//
			// org.springframework.context.support.VoiceManager$IntMap.getObject(int)
			//
			final Class<?> classIntMap = Util.forName("org.springframework.context.support.VoiceManager$IntMap");
			//
			final Object intMap = Reflection.newProxy(classIntMap, ih);
			//
			final Method intMapGetObject = classIntMap != null
					? classIntMap.getDeclaredMethod("getObject", Integer.TYPE)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=getObject, message=getObject}",
					() -> ih.invoke(intMap, intMapGetObject, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=getObject, message=getObject}",
					() -> ih.invoke(intMap, intMapGetObject, empty));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class,
					"{localizedMessage=Key [null] Not Found, message=Key [null] Not Found}",
					() -> ih.invoke(intMap, intMapGetObject, new String[] { null }));
			//
			// org.springframework.context.support.VoiceManager$IntMap.containsKey(int)
			//
			final Method intMapContainsKey = classIntMap != null
					? classIntMap.getDeclaredMethod("containsKey", Integer.TYPE)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=containsKey, message=containsKey}",
					() -> ih.invoke(intMap, intMapContainsKey, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=containsKey, message=containsKey}",
					() -> ih.invoke(intMap, intMapContainsKey, empty));
			//
			Assertions.assertEquals(Boolean.FALSE, ih.invoke(intMap, intMapContainsKey, new String[] { null }));
			//
			// org.springframework.context.support.VoiceManager$IntMap.setObject(int,java.lang.Object)
			//
			final Method intMapSetObject = classIntMap != null
					? classIntMap.getDeclaredMethod("setObject", Integer.TYPE, Object.class)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setObject, message=setObject}",
					() -> ih.invoke(intMap, intMapSetObject, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setObject, message=setObject}",
					() -> ih.invoke(intMap, intMapSetObject, empty));
			//
		} // if
			//
	}

	@Test
	void testIh2() throws Throwable {
		//
		final InvocationHandler ih = createVoiceManagerIH();
		//
		if (ih != null) {
			//
			// org.springframework.context.support.VoiceManager$IntIntMap.setInt(int,int)
			//
			final Class<?> classIntIntMap = Util.forName("org.springframework.context.support.VoiceManager$IntIntMap");
			//
			final Method intIntMapSetInt = classIntIntMap != null
					? classIntIntMap.getDeclaredMethod("setInt", Integer.TYPE, Integer.TYPE)
					: null;
			//
			final Object intIntMap = Reflection.newProxy(classIntIntMap, ih);
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setInt, message=setInt}",
					() -> ih.invoke(intIntMap, intIntMapSetInt, null));
			//
			final Object[] empty = new Object[] {};
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setInt, message=setInt}",
					() -> ih.invoke(intIntMap, intIntMapSetInt, empty));
			//
			Assertions.assertDoesNotThrow(
					() -> ih.invoke(intIntMap, intIntMapSetInt, new Object[] { Integer.valueOf(ONE), TWO }));
			//
			// org.springframework.context.support.VoiceManager$IntIntMap.containsKey(int)
			//
			final Method intIntMapContainsKey = classIntIntMap != null
					? classIntIntMap.getDeclaredMethod("containsKey", Integer.TYPE)
					: null;
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=containsKey, message=containsKey}",
					() -> ih.invoke(intIntMap, intIntMapContainsKey, null));
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=containsKey, message=containsKey}",
					() -> ih.invoke(intIntMap, intIntMapContainsKey, empty));
			//
			Assertions.assertEquals(Boolean.TRUE,
					ih.invoke(intIntMap, intIntMapContainsKey, new Object[] { Integer.valueOf(ONE) }));
			//
			// org.springframework.context.support.VoiceManager$IH.isArray(java.lang.invoke.TypeDescriptor.OfField)
			//
			final Method isArray = CLASS_IH != null ? CLASS_IH.getDeclaredMethod("isArray", OfField.class) : null;
			//
			if (isArray != null) {
				//
				isArray.setAccessible(true);
				//
			} // if
				//
			Assertions.assertEquals(Boolean.FALSE, invoke(isArray, null, (Object) null));
			//
			final Class<?> clz = byte[].class;
			//
			Assertions.assertEquals(Boolean.TRUE, invoke(isArray, null, clz));
			//
			// java.lang.Runnable
			//
			final Runnable runnable = Reflection.newProxy(Runnable.class, ih);
			//
			final Method run = Runnable.class.getDeclaredMethod("run");
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
					"{localizedMessage=runnable is null, message=runnable is null}",
					() -> ih.invoke(runnable, run, null));
			//
			FieldUtils.writeDeclaredField(ih, "runnable", runnable, true);
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
					"{localizedMessage=runnable==proxy, message=runnable==proxy}",
					() -> ih.invoke(runnable, run, null));
			//
			FieldUtils.writeDeclaredField(ih, "runnable", Reflection.newProxy(Runnable.class, ih), true);
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
					"{localizedMessage=runnable==proxy, message=runnable==proxy}",
					() -> ih.invoke(runnable, run, null));
			//
			final Method toString = Object.class.getDeclaredMethod("toString");
			//
			AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=toString, message=toString}",
					() -> ih.invoke(runnable, toString, null));
			//
			// printStackTrace(java.lang.Throwable)
			//
			final Method printStackTrace = CLASS_IH != null
					? CLASS_IH.getDeclaredMethod("printStackTrace", Throwable.class)
					: null;
			//
			Assertions.assertNull(invoke(printStackTrace, null, (Object) null));
			//
			Assertions.assertNull(invoke(printStackTrace, null, new Throwable()));
			//
		} // if
			//
	}

	private static InvocationHandler createVoiceManagerIH() throws IllegalArgumentException, Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_IH);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		return Util.cast(InvocationHandler.class, newInstance(constructor));
		//
	}

	@Test
	void testExportTask1() throws Throwable {
		//
		// org.springframework.context.support.VoiceManager.ExportTask.setMp3Title(java.io.File)
		//
		final Method setMp3Title = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setMp3Title", File.class)
				: null;
		//
		if (setMp3Title != null) {
			//
			setMp3Title.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setMp3Title, null, (Object) null));
		//
		Assertions.assertNull(invoke(setMp3Title, null, Path.of(".").toFile()));
		//
		Assertions.assertNull(invoke(setMp3Title, null, Path.of("pom.xml").toFile()));
		//
		// org.springframework.context.support.VoiceManager.ExportTask.min(java.util.stream.Stream,java.util.Comparator)
		//
		final Method min = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("min", Stream.class, Comparator.class)
				: null;
		//
		if (min != null) {
			//
			min.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(min, null, null, null));
		//
		Assertions.assertNull(invoke(min, null, Stream.empty(), null));
		//
		Assertions.assertNull(invoke(min, null, stream, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.showPharse(org.springframework.context.support.VoiceManager,org.apache.commons.lang3.math.Fraction)
		//
		final Method showPharse = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("showPharse", VoiceManager.class, Fraction.class)
				: null;
		//
		if (showPharse != null) {
			//
			showPharse.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(showPharse, null, null, null));
		//
		Assertions.assertNull(invoke(showPharse, null, this.instance, null));
		//
		Assertions.assertNull(invoke(showPharse, null, this.instance, Fraction.ZERO));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.newXPath(javax.xml.xpath.XPathFactory)
		//
		final Method newXPath = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("newXPath", XPathFactory.class)
				: null;
		//
		if (newXPath != null) {
			//
			newXPath.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(newXPath, null, (Object) null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.getParentNode(org.w3c.dom.Node)
		//
		final Method getParentNode = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("getParentNode", Node.class)
				: null;
		//
		if (getParentNode != null) {
			//
			getParentNode.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getParentNode, null, node));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.appendChild(org.w3c.dom.Node,org.w3c.dom.Node)
		//
		final Method appendChild = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("appendChild", Node.class, Node.class)
				: null;
		//
		if (appendChild != null) {
			//
			appendChild.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(appendChild, null, null, null));
		//
		Assertions.assertNull(invoke(appendChild, null, node, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.removeChild(org.w3c.dom.Node,org.w3c.dom.Node)
		//
		final Method removeChild = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("removeChild", Node.class, Node.class)
				: null;
		//
		if (removeChild != null) {
			//
			removeChild.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(removeChild, null, node, null));
		//
		Assertions.assertNull(invoke(removeChild, null, null, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.newTransformer(javax.xml.transform.TransformerFactory)
		//
		final Method newTransformer = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("newTransformer", TransformerFactory.class)
				: null;
		//
		if (newTransformer != null) {
			//
			newTransformer.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(newTransformer, null, (Object) null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.newTransformer(javax.xml.transform.Transformer,javax.xml.transform.Source,javax.xml.transform.Result)
		//
		final Method transform = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("transform", Transformer.class, Source.class, Result.class)
				: null;
		//
		if (transform != null) {
			//
			transform.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(transform, null, null, null, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.cloneNode(org.w3c.dom.Node,boolean)
		//
		final Method cloneNode = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("cloneNode", Node.class, Boolean.TYPE)
				: null;
		//
		if (cloneNode != null) {
			//
			cloneNode.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(cloneNode, null, node, true));
		//
		Assertions.assertNull(invoke(cloneNode, null, null, true));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.replaceText(javax.xml.xpath.XPath,org.w3c.dom.Node,domain.Voice)
		//
		final Method replaceText = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("replaceText", CLASS_OBJECT_MAP, String.class)
				: null;
		//
		if (replaceText != null) {
			//
			replaceText.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(replaceText, null, null, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.setPluginHref(org.springframework.context.support.VoiceManager$ObjectMap,java.lang.String,boolean,java.lang.String)
		//
		final Method setPluginHref = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setPluginHref", CLASS_OBJECT_MAP, String.class, Boolean.TYPE,
						String.class)
				: null;
		//
		if (setPluginHref != null) {
			//
			setPluginHref.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setPluginHref, null, null, null, Boolean.TRUE, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.setVariable(org.springframework.expression.EvaluationContext,java.lang.String,java.lang.Object)
		//
		final Method setVariable = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setVariable", EvaluationContext.class, String.class,
						Object.class)
				: null;
		//
		if (setVariable != null) {
			//
			setVariable.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setVariable, null, null, null, null));
		//
	}

	@Test
	void testExportTask2() throws Throwable {
		//
		// org.springframework.context.support.VoiceManager$ExportTask.clone(com.fasterxml.jackson.databind.ObjectMapper,java.lang.Class,java.lang.Object)
		//
		final Method clone = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("clone", ObjectMapper.class, Class.class, Object.class)
				: null;
		//
		if (clone != null) {
			//
			clone.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(clone, null, objectMapper, null, null));
		//
		Assertions.assertNull(invoke(clone, null, objectMapper, Object.class, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.setStringFieldDefaultValue(java.lang.Object)
		//
		final Method setStringFieldDefaultValue = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setStringFieldDefaultValue", Object.class)
				: null;
		//
		if (setStringFieldDefaultValue != null) {
			//
			setStringFieldDefaultValue.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setStringFieldDefaultValue, null, new Voice()));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.replaceTextContent(java.lang.Object)
		//
		final Method replaceTextContent = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("replaceTextContent", CLASS_OBJECT_MAP, Map.class)
				: null;
		//
		if (replaceTextContent != null) {
			//
			replaceTextContent.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(replaceTextContent, null, null, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.info(org.slf4j.Logger,java.lang.String)
		//
		final Method info = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("info", Logger.class, String.class)
				: null;
		//
		if (info != null) {
			//
			info.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(info, null, null, null));
		//
		Assertions.assertNull(invoke(info, null, logger, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.removeCustomShapeByName(org.springframework.context.support.VoiceManager$ObjectMap,java.lang.String)
		//
		final Method removeCustomShapeByName = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("removeCustomShapeByName", CLASS_OBJECT_MAP, String.class)
				: null;
		//
		if (removeCustomShapeByName != null) {
			//
			removeCustomShapeByName.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(removeCustomShapeByName, null, null, null));
		//
		Assertions.assertNull(invoke(removeCustomShapeByName, null, null, ""));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.setPassword(org.odftoolkit.odfdom.pkg.OdfPackage,java.lang.String)
		//
		final Method setPassword = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setPassword", OdfPackage.class, String.class)
				: null;
		//
		if (setPassword != null) {
			//
			setPassword.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setPassword, null, null, null));
		//
		final Constructor<?> constructor = getDeclaredConstructor(OdfPackage.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setPassword, null, newInstance(constructor), null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.getProgressBarExport(org.springframework.context.support.VoiceManager)
		//
		final Method getProgressBarExport = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("getProgressBarExport", VoiceManager.class)
				: null;
		//
		if (getProgressBarExport != null) {
			//
			getProgressBarExport.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getProgressBarExport, null, instance));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.setNodeValue(org.w3c.dom.Node,java.lang.String)
		//
		final Method setNodeValue = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("setNodeValue", Node.class, String.class)
				: null;
		//
		if (setNodeValue != null) {
			//
			setNodeValue.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setNodeValue, null, null, null));
		//
		Assertions.assertNull(invoke(setNodeValue, null, node, null));
		//
		// org.springframework.context.support.VoiceManager$ExportTask.getNodeValue(org.w3c.dom.Node)
		//
		final Method getNodeValue = CLASS_EXPORT_TASK != null
				? CLASS_EXPORT_TASK.getDeclaredMethod("getNodeValue", Node.class)
				: null;
		//
		if (getNodeValue != null) {
			//
			getNodeValue.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getNodeValue, null, (Object) null));
		//
		Assertions.assertNull(invoke(getNodeValue, null, node));
		//
	}

	@Test
	void testVoiceIdListCellRenderer() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(
				Util.forName("org.springframework.context.support.VoiceManager$VoiceIdListCellRenderer"),
				VoiceManager.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final ListCellRenderer<?> listCellRenderer1 = Util.cast(ListCellRenderer.class,
				newInstance(constructor, this.instance));
		//
		if (listCellRenderer1 != null) {
			//
			Assertions.assertNull(listCellRenderer1.getListCellRendererComponent(null, null, 0, false, false));
			//
			FieldUtils.writeDeclaredField(instance, "speechApi", speechApi, true);
			//
			Assertions.assertNull(listCellRenderer1.getListCellRendererComponent(null, null, 0, false, false));
			//
			FieldUtils.writeDeclaredField(instance, "speechApi", null, true);
			//
			FieldUtils.writeDeclaredField(listCellRenderer1, "listCellRenderer",
					Reflection.newProxy(ListCellRenderer.class, ih), true);
			//
			Assertions.assertNull(listCellRenderer1.getListCellRendererComponent(null, null, 0, false, false));
			//
			final SpeechApiImpl speechApiImpl = new SpeechApiImpl();
			//
			FieldUtils.writeDeclaredField(instance, "speechApi", speechApiImpl, true);
			//
			Assertions.assertNull(listCellRenderer1.getListCellRendererComponent(null, null, 0, false, false));
			//
			FieldUtils.writeDeclaredField(speechApiImpl, "instance", new SpeechApiSystemSpeechImpl(), true);
			//
			Assertions.assertNull(listCellRenderer1.getListCellRendererComponent(null, null, 0, false, false));
			//
		} // if
			//
	}

	@Test
	void testMicrosoftAccessFileFormatListCellRenderer() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(Util
				.forName("org.springframework.context.support.VoiceManager$MicrosoftAccessFileFormatListCellRenderer"));
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final ListCellRenderer<?> listCellRenderer = Util.cast(ListCellRenderer.class, newInstance(constructor));
		//
		if (listCellRenderer != null) {
			//
			Assertions.assertNull(listCellRenderer.getListCellRendererComponent(null, null, 0, false, false));
			//
			final FileFormat fileFormat = FileFormat.V2000;
			//
			Assertions.assertNull(((ListCellRenderer) listCellRenderer).getListCellRendererComponent(null, fileFormat,
					0, false, false));
			//
			FieldUtils.writeDeclaredField(listCellRenderer, "commonPrefix", "VERSION_", true);
			//
			Assertions.assertNull(((ListCellRenderer) listCellRenderer).getListCellRendererComponent(null, fileFormat,
					0, false, false));
			//
		} // if
			//
	}

	@Test
	void testAudioToFlacByteConverter() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.VoiceManager$AudioToFlacByteConverter");
		//
		final Constructor<?> constructor = getDeclaredConstructor(clz);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Object instance = newInstance(constructor);
		//
		// setAudioStreamEncoderByteArrayLength(java.lang.Object)
		//
		final Method setAudioStreamEncoderByteArrayLength = clz != null
				? clz.getDeclaredMethod("setAudioStreamEncoderByteArrayLength", Object.class)
				: null;
		//
		final Field audioStreamEncoderByteArrayLength = Util.getDeclaredField(clz, "audioStreamEncoderByteArrayLength");
		//
		if (audioStreamEncoderByteArrayLength != null) {
			//
			audioStreamEncoderByteArrayLength.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> invoke(setAudioStreamEncoderByteArrayLength, instance, (Object) null));
		//
		Assertions.assertNull(get(audioStreamEncoderByteArrayLength, instance));
		//
		final Integer one = Integer.valueOf(1);
		//
		Assertions.assertDoesNotThrow(() -> invoke(setAudioStreamEncoderByteArrayLength, instance, one));
		//
		Assertions.assertSame(one, get(audioStreamEncoderByteArrayLength, instance));
		//
		Assertions.assertDoesNotThrow(
				() -> invoke(setAudioStreamEncoderByteArrayLength, instance, Long.valueOf(one.longValue())));
		//
		Assertions.assertSame(one, get(audioStreamEncoderByteArrayLength, instance));
		//
		// convert(byte[])
		//
		final Method convert = clz != null ? clz.getDeclaredMethod("convert", byte[].class) : null;
		//
		Assertions.assertTrue(Objects.deepEquals(new byte[] {}, invoke(convert, instance, (Object) null)));
		//
		Assertions.assertTrue(Objects.deepEquals(new byte[] {}, invoke(convert, instance, new byte[] {})));
		//
		// createStreamConfiguration(javax.sound.sampled.AudioFormat)
		//
		Assertions.assertEquals(
				"StreamConfiguration[bitsPerSample=1,channelCount=2,maxBlockSize=4096,minBlockSize=4096,sampleRate=0,validConfig=true]",
				ToStringBuilder.reflectionToString(
						invoke(clz != null ? clz.getDeclaredMethod("createStreamConfiguration", AudioFormat.class)
								: null, null, new AudioFormat(ZERO, ONE, TWO, true, false)),
						ToStringStyle.SHORT_PREFIX_STYLE));
		//
		// org.springframework.context.support.VoiceManager.AudioToFlacByteConverter.getFormat(javax.sound.sampled.AudioInputStream)
		//
		final Method getFormat = clz != null ? clz.getDeclaredMethod("getFormat", AudioInputStream.class) : null;
		//
		if (getFormat != null) {
			//
			getFormat.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getFormat, null, Narcissus.allocateInstance(AudioInputStream.class)));
		//
	}

	@Test
	void testAudioToMp3ByteConverter() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.VoiceManager$AudioToMp3ByteConverter");
		//
		final Constructor<?> constructor = getDeclaredConstructor(clz);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Object instance = newInstance(constructor);
		//
		// convert(byte[])
		//
		final Method convert = clz != null ? clz.getDeclaredMethod("convert", byte[].class) : null;
		//
		Assertions.assertNull(invoke(convert, instance, (Object) null));
		//
		Assertions.assertNull(invoke(convert, instance, new byte[] {}));
		//
		AssertionsUtil.assertThrowsAndEquals(RuntimeException.class,
				"{localizedMessage=javax.sound.sampled.UnsupportedAudioFileException: Stream of unsupported format, message=javax.sound.sampled.UnsupportedAudioFileException: Stream of unsupported format}",
				() -> {
					//
					try {
						//
						invoke(convert, instance, new byte[] { 0 });
						//
					} catch (final InvocationTargetException e) {
						//
						throw e.getTargetException();
						//
					} // try
						//
				});
		//
		// setBitRate(java.lang.Object)
		//
		Assertions.assertNull(invoke(clz != null ? clz.getDeclaredMethod("setBitRate", Object.class) : null, instance,
				(Object) null));
		//
		// setQuality(java.lang.Object)
		//
		final Method setQuality = clz != null ? clz.getDeclaredMethod("setQuality", Object.class) : null;
		//
		Assertions.assertNull(invoke(setQuality, instance, (Object) null));
		//
		Assertions.assertNull(invoke(setQuality, instance, "lowest"));
		//
		Assertions.assertNull(invoke(setQuality, instance, "Lowest"));
		//
		// setVbr(java.lang.Object)
		//
		final Method setVbr = clz != null ? clz.getDeclaredMethod("setVbr", Object.class) : null;
		//
		Assertions.assertNull(invoke(setVbr, instance, ""));
		//
		// afterPropertiesSet()
		//
		final Method afterPropertiesSet = clz != null ? clz.getDeclaredMethod("afterPropertiesSet") : null;
		//
		Assertions.assertNull(invoke(afterPropertiesSet, instance));
		//
		Assertions.assertNull(invoke(setVbr, instance, "true"));
		//
		Assertions.assertNull(invoke(afterPropertiesSet, instance));
		//
		Assertions.assertNull(invoke(setQuality, instance, "-1"));
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
				"{localizedMessage=Under VBR,\"quality\" cound be with in 0 to 9, message=Under VBR,\"quality\" cound be with in 0 to 9}",
				() -> {
					//
					try {
						//
						invoke(afterPropertiesSet, instance);
						//
					} catch (final InvocationTargetException e) {
						//
						throw e.getTargetException();
						//
					} // try
						//
				});
		//
		// createQuality(org.apache.bcel.generic.Instruction[])
		//
		final Method createQuality = clz != null ? clz.getDeclaredMethod("createQuality", Instruction[].class) : null;
		//
		Assertions.assertNull(invoke(createQuality, instance, (Object) null));
		//
		Assertions.assertNull(invoke(createQuality, instance, new Instruction[] { null }));
		//
		// getValue(org.apache.bcel.generic.ConstantPushInstruction)
		//
		Assertions.assertNull(
				invoke(clz != null ? clz.getDeclaredMethod("getValue", ConstantPushInstruction.class) : null, instance,
						(Object) null));
		//
		// createQualityMap(org.apache.bcel.classfile.ConstantPool,org.apache.bcel.generic.Instruction[])
		//
		final Method createQualityMap = clz != null
				? clz.getDeclaredMethod("createQualityMap", ConstantPool.class, Instruction[].class)
				: null;
		//
		Assertions.assertNull(invoke(createQualityMap, instance, null, null));
		//
		Assertions.assertNull(invoke(createQualityMap, instance, null, new Instruction[] { null }));
		//
		// getValue(org.apache.bcel.generic.LDC,org.apache.bcel.generic.ConstantPoolGen)
		//
		final Method getValue = clz != null ? clz.getDeclaredMethod("getValue", LDC.class, ConstantPoolGen.class)
				: null;
		//
		Assertions.assertNull(invoke(getValue, instance, null, null));
		//
		final LDC ldc = new LDC(0);
		//
		Assertions.assertNull(invoke(getValue, instance, ldc, null));
		//
		if (Util.forName("org.apache.bcel.classfile.InvalidMethodSignatureException") == null) {
			//
			Assertions.assertNull(invoke(getValue, instance, ldc, new ConstantPoolGen()));
			//
		} // if
			//
	}

	@Test
	void testJLabelLink() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.VoiceManager$JLabelLink");
		//
		final Constructor<?> constructor = getDeclaredConstructor(clz, ATag.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final MouseListener[] mouseListeners = getMouseListeners(
				Util.cast(Component.class, newInstance(constructor, (Object) null)));
		//
		for (int i = 0; mouseListeners != null && i < mouseListeners.length; i++) {
			//
			final MouseListener ml = mouseListeners[i];
			//
			if (ml == null) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertDoesNotThrow(() -> ml.mouseClicked(null));
			//
		} // for
			//
			// org.springframework.context.support.VoiceManager$JLabelLink.darker(java.awt.Color)
			//
		final Method darker = clz != null ? clz.getDeclaredMethod("darker", Color.class) : null;
		//
		if (darker != null) {
			//
			darker.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(darker, null, (Object) null));
		//
		// org.springframework.context.support.VoiceManager$JLabelLink.getName(j2html.attributes.Attribute)
		//
		final Method getName = clz != null ? clz.getDeclaredMethod("getName", Attribute.class) : null;
		//
		if (getName != null) {
			//
			getName.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(getName, null, (Object) null));
		//
		Assertions.assertNull(invoke(getName, null, Narcissus.allocateInstance(Attribute.class)));
		//
	}

	private static MouseListener[] getMouseListeners(final Component instance) {
		return instance != null ? instance.getMouseListeners() : null;
	}

	@Test
	void testJTabbedPaneChangeListener() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(
				Util.forName("org.springframework.context.support.VoiceManager$JTabbedPaneChangeListener"));
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(
				() -> stateChanged(Util.cast(ChangeListener.class, newInstance(constructor)), null));
		//
	}

	@Test
	void testBooleanMap() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.VoiceManager$BooleanMap");
		//
		// org.springframework.context.support.VoiceManager$BooleanMap.setBoolean(org.springframework.context.support.VoiceManager$BooleanMap,java.lang.String,boolean)
		//
		final Method setBoolean = clz != null ? clz.getDeclaredMethod("setBoolean", clz, String.class, Boolean.TYPE)
				: null;
		//
		if (setBoolean != null) {
			//
			setBoolean.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(setBoolean, null, null, null, Boolean.FALSE));
		//
	}

	@Test
	void testVoiceThrowableMessageBiConsumer() throws Throwable {
		//
		final Class<?> clz = Util
				.forName("org.springframework.context.support.VoiceManager$VoiceThrowableMessageBiConsumer");
		//
		final Constructor<?> constructor = getDeclaredConstructor(clz, Boolean.TYPE, DefaultTableModel.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final BiConsumer<?, ?> biConsumer = Util.cast(BiConsumer.class, newInstance(constructor, Boolean.TRUE, null));
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			accept(biConsumer, null, null);
			//
		});
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			accept(biConsumer, null, null);
			//
		});
		//
		// errorOrPrintln(org.slf4j.Logger,java.io.PrintStream,java.lang.String)
		//
		final Method errorOrPrintln = clz != null
				? clz.getDeclaredMethod("errorOrPrintln", Logger.class, PrintStream.class, String.class)
				: null;
		//
		if (errorOrPrintln != null) {
			//
			errorOrPrintln.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(errorOrPrintln, null, null, null, null));
		//
		try (final OutputStream os = new ByteArrayOutputStream(); final PrintStream ps = new PrintStream(os)) {
			//
			Assertions.assertNull(invoke(errorOrPrintln, null, null, ps, null));
			//
		} // try
			//
	}

	private static <T, U> void accept(final BiConsumer<T, U> instance, final T t, final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	@Test
	void testTabFocusTraversalPolicy() throws Throwable {
		//
		final Constructor<?> constructor = getDeclaredConstructor(
				Util.forName("org.springframework.context.support.VoiceManager$TabFocusTraversalPolicy"), List.class);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final FocusTraversalPolicy focusTraversalPolicy = Util.cast(FocusTraversalPolicy.class,
				newInstance(constructor, (Object) null));
		//
		if (focusTraversalPolicy != null) {
			//
			Assertions.assertNull(focusTraversalPolicy.getComponentAfter(null, null));
			//
			Assertions.assertNull(focusTraversalPolicy.getComponentBefore(null, null));
			//
			Assertions.assertNull(focusTraversalPolicy.getDefaultComponent(null));
			//
			Assertions.assertNull(focusTraversalPolicy.getFirstComponent(null));
			//
			Assertions.assertNull(focusTraversalPolicy.getLastComponent(null));
			//
			FieldUtils.writeDeclaredField(focusTraversalPolicy, "components", Arrays.asList((Object) null), true);
			//
			Assertions.assertNull(focusTraversalPolicy.getComponentAfter(null, null));
			//
			Assertions.assertNull(focusTraversalPolicy.getComponentBefore(null, null));
			//
			Assertions.assertNull(focusTraversalPolicy.getDefaultComponent(null));
			//
			Assertions.assertNull(focusTraversalPolicy.getFirstComponent(null));
			//
			Assertions.assertNull(focusTraversalPolicy.getLastComponent(null));
			//
			// java.awt.FocusTraversalPolicy.getInitialComponent(java.awt.Window)
			//
			Assertions.assertNull(focusTraversalPolicy.getInitialComponent(null));
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				Assertions.assertNull(focusTraversalPolicy.getInitialComponent(instance));
				//
			} else {
				//
				Assertions.assertSame(instance, focusTraversalPolicy.getInitialComponent(instance));
				//
			} // if
				//
		} // if
			//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Method[] ms = VoiceManager.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> c = null;
		//
		Object[] os = null;
		//
		String name, toString = null;
		//
		Object invoke = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()
					|| Boolean.logicalAnd(Objects.equals(Util.getName(m), "main"),
							Arrays.equals(m.getParameterTypes(), new Class<?>[] { String[].class }))
					|| and(!GraphicsEnvironment.isHeadless(),
							Util.contains(Arrays.asList("init", "afterPropertiesSet"), Util.getName(m)),
							m.getParameterCount() == 0)
					|| and(!Objects.equals(Util.getName(Util.getClass(provider(FileSystems.getDefault()))),
							"sun.nio.fs.WindowsFileSystemProvider"),
							Util.contains(Arrays.asList("getOsVersionInfoEx", "getOsVersionInfoExMap",
									"IsWindows10OrGreater"), Util.getName(m)),
							m.getParameterCount() == 0)) {
				//
				continue;
				//
			} // if
				//
			parameterTypes = m.getParameterTypes();
			//
			if ((c = ObjectUtils.getIfNull(c, ArrayList::new)) != null) {
				//
				c.clear();
				//
			} // if
				//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = parameterTypes[j], Boolean.TYPE)) {
					//
					Util.add(c, Boolean.TRUE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(c, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Long.TYPE)) {
					//
					Util.add(c, Long.valueOf(0));
					//
				} else {
					//
					Util.add(c, null);
					//
				} // if
					//
			} // if
				//
			os = toArray(c);
			//
			name = Util.getName(m);
			//
			toString = Objects.toString(m);
			//
			if (Util.isStatic(m)) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (Util.contains(Arrays.asList(Integer.TYPE, Boolean.TYPE, Long.TYPE), m.getReturnType())
						|| Boolean.logicalAnd(Util.contains(
								Arrays.asList("getOsVersionInfoEx", "getOsVersionInfoExMap", "IsWindows10OrGreater"),
								name), m.getParameterCount() == 0)
						|| Boolean.logicalAnd(Objects.equals(name, "createMicrosoftWindowsCompatibilityWarningJPanel"),
								Arrays.equals(parameterTypes, new Class<?>[] { LayoutManager.class, String.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "getTitledComponentMap"),
								Arrays.equals(parameterTypes, new Class<?>[] { Map.class, String[].class }))
						|| Boolean.logicalAnd(Objects.equals(name, "getMimeTypeAndBase64EncodedString"),
								Arrays.equals(parameterTypes, new Class<?>[] { String.class, String.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "craeteSpeechApiInstallationWarningJPanel"),
								Arrays.equals(parameterTypes, new Class<?>[] { String.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "createFocusableComponentPredicate"),
								Arrays.equals(parameterTypes, new Class<?>[] { Collection.class }))) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
					//
			} else {
				//
				invoke = Narcissus.invokeMethod(instance, m, os);
				//
				if (Boolean.logicalAnd(Util.contains(Arrays.asList("getToolkit", "getObjectMapper"), name),
						m.getParameterCount() == 0)
						|| Boolean.logicalAnd(Objects.equals(name, "createImportResultPanel"),
								Arrays.equals(parameterTypes, new Class<?>[] { LayoutManager.class }))) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
					//
			} // if
				//
		} // for
			//
	}

	private static FileSystemProvider provider(final FileSystem instance) {
		return instance != null ? instance.provider() : null;
	}

	private static boolean and(final boolean a, final boolean b, final boolean... bs) {
		//
		boolean result = a && b;
		//
		if (!result) {
			//
			return false;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (!(result &= bs[i])) {
				//
				return false;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

}