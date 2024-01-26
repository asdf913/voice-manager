package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.invoke.TypeDescriptor.OfField;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;
import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSDeclaration;
import com.helger.css.decl.CSSExpression;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class JouYouKanjiGuiTest {

	private static final String EMPTY = "";

	private static final int ONE = 1;

	private static Class<?> CLASS_OBJECT_MAP, CLASS_IH = null;

	private static Method METHOD_GET, METHOD_NAME, METHOD_GET_ECSS_VERSION_BY_MAJOR, METHOD_ADD_JOU_YOU_KAN_JI_SHEET,
			METHOD_GET_CSS_DECLARATION_BY_ATTRIBUTE_AND_CSS_PROPERTY, METHOD_SET_PREFERRED_WIDTH,
			METHOD_GET_PREFERRED_SIZE, METHOD_TO_LIST, METHOD_CONTAINS, METHOD_SET_SELECTED_ITEM,
			METHOD_GET_BOOLEAN_VALUES, METHOD_TO_ARRAY, METHOD_MATCHER, METHOD_GET_EXPRESSION_AS_CSS_STRING,
			METHOD_GET_INDEXED_COLORS, METHOD_GET_STYLES_SOURCE, METHOD_GET_PROPERTY, METHOD_INT_VALUE,
			METHOD_TO_MILLIS, METHOD_SET_FILL_BACK_GROUND_COLOR, METHOD_SET_FILL_PATTERN, METHOD_SPLITERATOR,
			METHOD_TEST_AND_ACCEPT3, METHOD_TEST_AND_ACCEPT4, METHOD_MAP_TO_INT, METHOD_MAX, METHOD_OR_ELSE,
			METHOD_SET_AUTO_FILTER, METHOD_GET_PHYSICAL_NUMBER_OF_ROWS = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = JouYouKanjiGui.class;
		//
		(METHOD_GET = clz.getDeclaredMethod("get", Field.class, Object.class)).setAccessible(true);
		//
		(METHOD_NAME = clz.getDeclaredMethod("name", Enum.class)).setAccessible(true);
		//
		(METHOD_GET_ECSS_VERSION_BY_MAJOR = clz.getDeclaredMethod("getECSSVersionByMajor", ECSSVersion[].class,
				Number.class)).setAccessible(true);
		//
		(METHOD_ADD_JOU_YOU_KAN_JI_SHEET = clz.getDeclaredMethod("addJouYouKanJiSheet",
				CLASS_OBJECT_MAP = Class.forName("org.springframework.context.support.JouYouKanjiGui$ObjectMap"),
				String.class)).setAccessible(true);
		//
		(METHOD_GET_CSS_DECLARATION_BY_ATTRIBUTE_AND_CSS_PROPERTY = clz.getDeclaredMethod(
				"getCSSDeclarationByAttributeAndCssProperty", Element.class, String.class, ECSSVersion.class,
				String.class)).setAccessible(true);
		//
		(METHOD_SET_PREFERRED_WIDTH = clz.getDeclaredMethod("setPreferredWidth", Integer.TYPE, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_GET_PREFERRED_SIZE = clz.getDeclaredMethod("getPreferredSize", Component.class)).setAccessible(true);
		//
		(METHOD_TO_LIST = clz.getDeclaredMethod("toList", Stream.class)).setAccessible(true);
		//
		(METHOD_CONTAINS = clz.getDeclaredMethod("contains", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_SET_SELECTED_ITEM = clz.getDeclaredMethod("setSelectedItem", ComboBoxModel.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_GET_BOOLEAN_VALUES = clz.getDeclaredMethod("getBooleanValues")).setAccessible(true);
		//
		(METHOD_TO_ARRAY = clz.getDeclaredMethod("toArray", Collection.class, Object[].class)).setAccessible(true);
		//
		(METHOD_MATCHER = clz.getDeclaredMethod("matcher", Pattern.class, CharSequence.class)).setAccessible(true);
		//
		(METHOD_GET_EXPRESSION_AS_CSS_STRING = clz.getDeclaredMethod("getExpressionAsCSSString", CSSDeclaration.class))
				.setAccessible(true);
		//
		(METHOD_GET_INDEXED_COLORS = clz.getDeclaredMethod("getIndexedColors", StylesTable.class)).setAccessible(true);
		//
		(METHOD_GET_STYLES_SOURCE = clz.getDeclaredMethod("getStylesSource", XSSFWorkbook.class)).setAccessible(true);
		//
		(METHOD_GET_PROPERTY = clz.getDeclaredMethod("getProperty", CSSDeclaration.class)).setAccessible(true);
		//
		(METHOD_INT_VALUE = clz.getDeclaredMethod("intValue", Number.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_TO_MILLIS = clz.getDeclaredMethod("toMillis", Duration.class)).setAccessible(true);
		//
		(METHOD_SET_FILL_BACK_GROUND_COLOR = clz.getDeclaredMethod("setFillBackgroundColor", CellStyle.class,
				org.apache.poi.ss.usermodel.Color.class)).setAccessible(true);
		//
		(METHOD_SET_FILL_PATTERN = clz.getDeclaredMethod("setFillPattern", CellStyle.class, FillPatternType.class))
				.setAccessible(true);
		//
		(METHOD_SPLITERATOR = clz.getDeclaredMethod("spliterator", Iterable.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3 = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				FailableConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_MAP_TO_INT = clz.getDeclaredMethod("mapToInt", Stream.class, ToIntFunction.class)).setAccessible(true);
		//
		(METHOD_MAX = clz.getDeclaredMethod("max", IntStream.class)).setAccessible(true);
		//
		(METHOD_OR_ELSE = clz.getDeclaredMethod("orElse", OptionalInt.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_SET_AUTO_FILTER = clz.getDeclaredMethod("setAutoFilter", Sheet.class)).setAccessible(true);
		//
		(METHOD_GET_PHYSICAL_NUMBER_OF_ROWS = clz.getDeclaredMethod("getPhysicalNumberOfRows", Sheet.class))
				.setAccessible(true);
		//
		CLASS_IH = Class.forName("org.springframework.context.support.JouYouKanjiGui$IH");
		//
	}

	private static class IH implements InvocationHandler {

		private Spliterator<?> spliterator = null;

		private IntStream intStream = null;

		private Integer firstRowNum, lastRowNum = null;

		private Row row = null;

		private Short firstCellNum = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(method != null ? method.getReturnType() : null, Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Iterable) {
				//
				if (Objects.equals(methodName, "spliterator")) {
					//
					return spliterator;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "mapToInt")) {
					//
					return intStream;
					//
				} // if
					//
			} else if (proxy instanceof Sheet) {
				//
				if (Objects.equals(methodName, "getFirstRowNum")) {
					//
					return firstRowNum;
					//
				} else if (Objects.equals(methodName, "getLastRowNum")) {
					//
					return lastRowNum;
					//
				} else if (Objects.equals(methodName, "getRow")) {
					//
					return row;
					//
				} // if
					//
			} else if (proxy instanceof Row) {
				//
				if (Objects.equals(methodName, "getFirstCellNum")) {
					//
					return firstCellNum;
					//
				} // if
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
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof Component) {
				//
				if (Objects.equals(methodName, "getPreferredSize")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private JouYouKanjiGui instance = null;

	private IH ih = null;

	private CellStyle cellStyle = null;

	private Stream<?> stream = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			final Constructor<JouYouKanjiGui> constructor = JouYouKanjiGui.class.getDeclaredConstructor();
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			instance = constructor != null ? constructor.newInstance() : null;
			//
		} else {
			//
			instance = Util.cast(JouYouKanjiGui.class, Narcissus.allocateInstance(JouYouKanjiGui.class));
			//
		} // if
			//
		cellStyle = Reflection.newProxy(CellStyle.class, ih = new IH());
		//
		stream = Reflection.newProxy(Stream.class, ih);
		//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
	}

	private static void afterPropertiesSet(final InitializingBean instance) throws Exception {
		if (instance != null) {
			instance.afterPropertiesSet();
		}
	}

	@Test
	void testActionPerformed() {
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(EMPTY, 0, null)));
		//
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent evt) {
		if (instance != null) {
			instance.actionPerformed(evt);
		}
	}

	@Test
	void testKeyTyped() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.keyTyped(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testKeyPressed() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.keyPressed(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testKeyReleased() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, null);
			//
		});
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, new KeyEvent(new JLabel(), 0, 0, 0, 0, (char) 0));
			//
		});
		//
		// tfText
		//
		final JTextComponent tfText = new JTextField(" ");
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfText", tfText, true);
			//
		} // if
			//
		final KeyEvent keyEvent = new KeyEvent(tfText, 0, 0, 0, 0, (char) 0);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, keyEvent);
			//
		});
		//
		// jouYouKanJiList
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jouYouKanJiList", Unit.with(Collections.singletonList(" ")), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, keyEvent);
			//
		});
		//
	}

	private static void keyReleased(final KeyListener instance, final KeyEvent e) {
		if (instance != null) {
			instance.keyReleased(e);
		}
	}

	@Test
	void testSetEcssVersion() throws Throwable {
		//
		final Field ecssVersion = JouYouKanjiGui.class.getDeclaredField("ecssVersion");
		//
		if (ecssVersion != null) {
			//
			ecssVersion.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> setEcssVersion(instance, null));
		//
		// com.helger.css.ECSSVersion
		//
		final ECSSVersion ev = ECSSVersion.CSS10;
		//
		Assertions.assertDoesNotThrow(() -> setEcssVersion(instance, ev));
		//
		Assertions.assertSame(ev, get(ecssVersion, instance));
		//
		// java.lang.String
		//
		Assertions.assertDoesNotThrow(() -> setEcssVersion(instance, "2"));
		//
		Assertions.assertSame(ECSSVersion.CSS21, get(ecssVersion, instance));
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}",
				() -> setEcssVersion(instance, "CSS"));
		//
		// java.lang.Integer
		//
		Assertions.assertDoesNotThrow(() -> setEcssVersion(instance, 3));
		//
		Assertions.assertSame(ECSSVersion.CSS30, get(ecssVersion, instance));
		//
	}

	private static void setEcssVersion(final JouYouKanjiGui instance, final Object object)
			throws NoSuchFieldException, IllegalAccessException {
		if (instance != null) {
			instance.setEcssVersion(object);
		}
	}

	@Test
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get(null, null));
		//
	}

	private static Object get(final Field field, final Object instance) throws Throwable {
		try {
			return METHOD_GET.invoke(null, field, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testName() throws Throwable {
		//
		Assertions.assertNull(name(null));
		//
	}

	private static String name(final Enum<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_NAME.invoke(null, instance);
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
	void testGetECSSVersionByMajor() throws Throwable {
		//
		Assertions.assertNull(getECSSVersionByMajor(null, null));
		//
		Assertions.assertNull(getECSSVersionByMajor(new ECSSVersion[] { null }, null));
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}",
				() -> getECSSVersionByMajor(new ECSSVersion[] { ECSSVersion.CSS30, ECSSVersion.CSS30 }, 3));
		//
	}

	private static IValue0<ECSSVersion> getECSSVersionByMajor(final ECSSVersion[] evs, final Number number)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_ECSS_VERSION_BY_MAJOR.invoke(null, evs, number);
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
	void testAddJouYouKanJiSheet() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> addJouYouKanJiSheet(null, null));
		//
		final Object objectMap = createObjectMap();
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
				"{localizedMessage=Key [interface org.apache.poi.ss.usermodel.Workbook] Not Found, message=Key [interface org.apache.poi.ss.usermodel.Workbook] Not Found}",
				() -> addJouYouKanJiSheet(objectMap, null));
		//
	}

	@Test
	void testObjectMap() throws Throwable {
		//
		final Object objectMap = createObjectMap();
		//
		// org.springframework.context.support.JouYouKanjiGui$ObjectMap.setObject(org.springframework.context.support.JouYouKanjiGui$ObjectMap,java.lang.Class,java.lang.Object)
		//
		Method method = CLASS_OBJECT_MAP != null
				? CLASS_OBJECT_MAP.getDeclaredMethod("setObject", CLASS_OBJECT_MAP, Class.class, Object.class)
				: null;
		//
		if (method != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(method, null, null, null, null));
		//
		Assertions.assertNull(invoke(method, null, objectMap, null, null));
		//
		// org.springframework.context.support.JouYouKanjiGui$ObjectMap.getObject(org.springframework.context.support.JouYouKanjiGui$ObjectMap,java.lang.Class)
		//
		if ((method = CLASS_OBJECT_MAP != null
				? CLASS_OBJECT_MAP.getDeclaredMethod("getObject", CLASS_OBJECT_MAP, Class.class)
				: null) != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(method, null, objectMap, null));
		//
		final Method m = method;
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
				"{localizedMessage=Key [String[]] Not Found, message=Key [String[]] Not Found}",
				() -> invoke(m, null, objectMap, String[].class));
		//
	}

	@Test
	void testIh() throws Throwable {
		//
		final InvocationHandler ih = createInvocationHandler();
		//
		AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{}", () -> ih.invoke(null, null, null));
		//
		final Object objectMap = createObjectMap();
		//
		// org.springframework.context.support.JouYouKanjiGui$ObjectMap.getObject(java.lang.Class)
		//
		final Method getObject = CLASS_OBJECT_MAP != null ? CLASS_OBJECT_MAP.getDeclaredMethod("getObject", Class.class)
				: null;
		//
		AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=getObject, message=getObject}",
				() -> ih.invoke(objectMap, getObject, null));
		//
		AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=getObject, message=getObject}",
				() -> ih.invoke(objectMap, getObject, new Object[] {}));
		//
		// org.springframework.context.support.JouYouKanjiGui$ObjectMap.setObject(java.lang.Class,java.lang.Object)
		//
		final Method setObject = CLASS_OBJECT_MAP != null
				? CLASS_OBJECT_MAP.getDeclaredMethod("setObject", Class.class, Object.class)
				: null;
		//
		AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setObject, message=setObject}",
				() -> ih.invoke(objectMap, setObject, null));
		//
		AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{localizedMessage=setObject, message=setObject}",
				() -> ih.invoke(objectMap, setObject, new Object[] {}));
		//
		// org.springframework.context.support.JouYouKanjiGui$IH.containsKey(java.lang.Map,java.lang.Object)
		//
		Method method = CLASS_IH != null ? CLASS_IH.getDeclaredMethod("containsKey", Map.class, Object.class) : null;
		//
		if (method != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		Assertions.assertEquals(Boolean.FALSE, invoke(method, null, null, null));
		//
		// org.springframework.context.support.JouYouKanjiGui$IH.isArray(java.lang.Class)
		//
		if ((method = CLASS_IH != null ? CLASS_IH.getDeclaredMethod("isArray", OfField.class) : null) != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		Assertions.assertEquals(Boolean.FALSE, invoke(method, null, (Object) null));
		//
		// org.springframework.context.support.JouYouKanjiGui$IH.getSimpleName(java.lang.Class)
		//
		if ((method = CLASS_IH != null ? CLASS_IH.getDeclaredMethod("getSimpleName", Class.class) : null) != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(invoke(method, null, (Object) null));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args) throws Throwable {
		try {
			return method != null ? method.invoke(instance, args) : null;
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static InvocationHandler createInvocationHandler() throws Throwable {
		//
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		return Util.cast(InvocationHandler.class, constructor != null ? constructor.newInstance() : null);
		//
	}

	private static Object createObjectMap() throws Throwable {
		//
		return Reflection.newProxy(CLASS_OBJECT_MAP, createInvocationHandler());
		//
	}

	private static void addJouYouKanJiSheet(final Object objectMap, final String sheetName) throws Throwable {
		try {
			METHOD_ADD_JOU_YOU_KAN_JI_SHEET.invoke(null, objectMap, sheetName);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetCSSDeclarationByAttributeAndCssProperty() throws Throwable {
		//
		Assertions.assertNull(getCSSDeclarationByAttributeAndCssProperty(null, null, null, null));
		//
	}

	private static CSSDeclaration getCSSDeclarationByAttributeAndCssProperty(final Element element,
			final String attribute, final ECSSVersion ecssVersion, final String cssProperty) throws Throwable {
		try {
			final Object obj = METHOD_GET_CSS_DECLARATION_BY_ATTRIBUTE_AND_CSS_PROPERTY.invoke(null, element, attribute,
					ecssVersion, cssProperty);
			if (obj == null) {
				return null;
			} else if (obj instanceof CSSDeclaration) {
				return (CSSDeclaration) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetPreferredWidth()
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, null));
		//
		// java.awt.Component.getPreferredSize() return null
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(Component.class);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		final Object instance = constructor != null ? constructor.newInstance() : null;
		//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(new MH());
			//
		} // if
			//
		Assertions.assertDoesNotThrow(
				() -> setPreferredWidth(0, Arrays.asList(null, Util.cast(Component.class, instance))));
		//
	}

	private static void setPreferredWidth(final int width, final Iterable<Component> cs) throws Throwable {
		try {
			METHOD_SET_PREFERRED_WIDTH.invoke(null, width, cs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPreferredSize() throws Throwable {
		//
		Assertions.assertNull(getPreferredSize(null));
		//
	}

	private static Dimension getPreferredSize(final Component instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PREFERRED_SIZE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Dimension) {
				return (Dimension) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToList() throws Throwable {
		//
		Assertions.assertNull(toList(null));
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_LIST.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(contains(null, null));
		//
		Assertions.assertFalse(contains(Collections.emptyList(), null));
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS.invoke(null, items, item);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetSelectedItem() {
		//
		Assertions.assertDoesNotThrow(() -> setSelectedItem(new DefaultComboBoxModel<>(), null));
		//
	}

	private static void setSelectedItem(final ComboBoxModel<?> instance, final Object selectedItem) throws Throwable {
		try {
			METHOD_SET_SELECTED_ITEM.invoke(null, instance, selectedItem);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetBooleanValues() throws Throwable {
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertNotNull(getBooleanValues());
			//
		} // if
	}

	private static List<Boolean> getBooleanValues() throws Throwable {
		try {
			final Object obj = METHOD_GET_BOOLEAN_VALUES.invoke(null);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToArray() throws Throwable {
		//
		Assertions.assertNull(toArray(null, null));
		//
		Assertions.assertNull(toArray(Collections.emptyList(), null));
		//
		final Object[] array = new Object[] {};
		//
		Assertions.assertArrayEquals(array, toArray(Collections.emptyList(), array));
		//
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) throws Throwable {
		try {
			final Object obj = METHOD_TO_ARRAY.invoke(null, instance, array);
			if (obj == null) {
				return null;
			}
			return (T[]) obj;
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMatcher() throws Throwable {
		//
		Assertions.assertNull(matcher(null, null));
		//
		final Pattern pattern = Pattern.compile("\\d+");
		//
		Assertions.assertNull(matcher(pattern, null));
		//
		Assertions.assertNotNull(matcher(pattern, EMPTY));
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
	void testGetExpressionAsCSSString() throws Throwable {
		//
		Assertions.assertNull(getExpressionAsCSSString(null));
		//
		final CSSDeclaration cssDeclaration = Util.cast(CSSDeclaration.class,
				Narcissus.allocateInstance(CSSDeclaration.class));
		//
		Assertions.assertNull(getExpressionAsCSSString(cssDeclaration));
		//
		Assertions.assertNull(getExpressionAsCSSString(setExpression(cssDeclaration,
				Util.cast(CSSExpression.class, Narcissus.allocateInstance(CSSExpression.class)))));
		//
		Assertions.assertEquals(EMPTY, getExpressionAsCSSString(setExpression(cssDeclaration, new CSSExpression())));
		//
	}

	private static CSSDeclaration setExpression(final CSSDeclaration instance, final CSSExpression aExpression) {
		return instance != null ? instance.setExpression(aExpression) : null;
	}

	private static String getExpressionAsCSSString(final CSSDeclaration instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_EXPRESSION_AS_CSS_STRING.invoke(null, instance);
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
	void testGetIndexedColors() throws Throwable {
		//
		Assertions.assertNull(
				getIndexedColors(Util.cast(StylesTable.class, Narcissus.allocateInstance(StylesTable.class))));
		//
	}

	private static IndexedColorMap getIndexedColors(final StylesTable instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_INDEXED_COLORS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (IndexedColorMap) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetStylesSource() throws Throwable {
		//
		Assertions.assertNull(
				getStylesSource(Util.cast(XSSFWorkbook.class, Narcissus.allocateInstance(XSSFWorkbook.class))));
		//
	}

	private static StylesTable getStylesSource(final XSSFWorkbook instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_STYLES_SOURCE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof StylesTable) {
				return (StylesTable) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetProperty() throws Throwable {
		//
		Assertions.assertNull(getProperty(null));
		//
		Assertions.assertNull(
				getProperty(Util.cast(CSSDeclaration.class, Narcissus.allocateInstance(CSSDeclaration.class))));
		//
	}

	private static String getProperty(final CSSDeclaration instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROPERTY.invoke(null, instance);
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
	void testIntValue() throws Throwable {
		//
		Assertions.assertEquals(ONE, intValue(null, ONE));
		//
	}

	private static int intValue(final Number instance, final int defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_INT_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMillis() throws Throwable {
		//
		Assertions.assertNull(toMillis(null));
		//
		Assertions.assertEquals(Long.valueOf(0),
				toMillis(Util.cast(Duration.class, Narcissus.allocateInstance(Duration.class))));
		//
	}

	private static Long toMillis(final Duration instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_MILLIS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Long) {
				return (Long) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetFillBackgroundColor() {
		//
		Assertions.assertDoesNotThrow(() -> setFillBackgroundColor(null, null));
		//
		Assertions.assertDoesNotThrow(() -> setFillBackgroundColor(cellStyle, null));
		//
	}

	private static void setFillBackgroundColor(final CellStyle instance, final org.apache.poi.ss.usermodel.Color color)
			throws Throwable {
		try {
			METHOD_SET_FILL_BACK_GROUND_COLOR.invoke(null, instance, color);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetFillPattern() {
		//
		Assertions.assertDoesNotThrow(() -> setFillPattern(null, null));
		//
		Assertions.assertDoesNotThrow(() -> setFillPattern(cellStyle, null));
		//
	}

	private static void setFillPattern(final CellStyle instance, final FillPatternType fillPatternType)
			throws Throwable {
		try {
			METHOD_SET_FILL_PATTERN.invoke(null, instance, fillPatternType);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSpliterator() throws Throwable {
		//
		Assertions.assertNull(spliterator(null));
		//
		Assertions.assertNull(spliterator(Reflection.newProxy(Iterable.class, ih)));
		//
	}

	private static <T> Spliterator<T> spliterator(final Iterable<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_SPLITERATOR.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Spliterator) {
				return (Spliterator) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, null, (a, b) -> {
			}));
			//
		} else {
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> false, null, null, null));
			//
		} // if
			//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT3.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiConsumer<T, U> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT4.invoke(null, predicate, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMapToInt() throws Throwable {
		//
		Assertions.assertNull(mapToInt(null, null));
		//
		Assertions.assertNull(mapToInt(Stream.empty(), null));
		//
		Assertions.assertNull(mapToInt(stream, null));
		//
	}

	private static <T> IntStream mapToInt(final Stream<T> instance, final ToIntFunction<? super T> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP_TO_INT.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof IntStream) {
				return (IntStream) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMax() throws Throwable {
		//
		Assertions.assertNull(max(null));
		//
	}

	private static OptionalInt max(final IntStream instance) throws Throwable {
		try {
			final Object obj = METHOD_MAX.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof OptionalInt) {
				return (OptionalInt) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOrElse() throws Throwable {
		//
		Assertions.assertEquals(ONE, orElse(null, ONE));
		//
	}

	private static int orElse(final OptionalInt instance, final int other) throws Throwable {
		try {
			final Object obj = METHOD_OR_ELSE.invoke(null, instance, other);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetAutoFilter() throws Throwable {
		//
		try (final Workbook wb = new XSSFWorkbook()) {
			//
			final Sheet sheet = wb.createSheet();
			//
			Assertions.assertDoesNotThrow(() -> setAutoFilter(sheet));
			//
			SheetUtil.createRow(sheet, getPhysicalNumberOfRows(sheet));
			//
			Assertions.assertDoesNotThrow(() -> setAutoFilter(sheet));
			//
			SheetUtil.createRow(sheet, getPhysicalNumberOfRows(sheet));
			//
			final Row row = sheet != null ? sheet.getRow(sheet.getLastRowNum()) : null;
			//
			if (row != null) {
				//
				row.createCell(row.getPhysicalNumberOfCells());
				//
			} // if
				//
			Assertions.assertDoesNotThrow(() -> setAutoFilter(sheet));
			//
		} // try
			//
		if (ih != null) {
			//
			ih.row = Reflection.newProxy(Row.class, ih);
			//
			ih.firstRowNum = Integer.valueOf(intValue(ih.lastRowNum = Integer.valueOf(ONE), 0) - 1);
			//
			ih.firstCellNum = Short.valueOf((short) -1);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> setAutoFilter(Reflection.newProxy(Sheet.class, ih)));
		//
	}

	private static void setAutoFilter(final Sheet sheet) throws Throwable {
		try {
			METHOD_SET_AUTO_FILTER.invoke(null, sheet);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPhysicalNumberOfRows() throws Throwable {
		//
		Assertions.assertNull(getPhysicalNumberOfRows(null));
		//
	}

	private static Integer getPhysicalNumberOfRows(final Sheet instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PHYSICAL_NUMBER_OF_ROWS.invoke(null, instance);
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

}