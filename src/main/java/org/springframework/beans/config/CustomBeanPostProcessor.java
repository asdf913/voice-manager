package org.springframework.beans.config;

import java.awt.Component;
import java.awt.Frame;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.swing.JFrame;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertyResolverUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

import io.github.toolfactory.narcissus.Narcissus;

public class CustomBeanPostProcessor implements BeanPostProcessor, EnvironmentAware {

	private PropertyResolver propertyResolver = null;

	@Override
	public void setEnvironment(final Environment environment) {
		this.propertyResolver = environment;
	}

	/**
	 * @see javax.swing.JFrame#setDefaultCloseOperation(int)
	 */
	private Number defaultCloseOperation = null;

	public void setDefaultCloseOperation(final Object object) {
		//
		IValue0<Number> value = null;
		//
		IValue0<Exception> exception = null;
		//
		if (object == null) {
			//
			value = Unit.with(null);
			//
		} else if (object instanceof Number) {
			//
			value = Unit.with(Integer.valueOf(((Number) object).intValue()));
			//
		} else if (object instanceof CharSequence) {
			//
			try {
				//
				value = getDefaultCloseOperation((CharSequence) object);
				//
			} catch (final JsonProcessingException | IllegalAccessException e) {
				//
				exception = Unit.with(e);
				//
			} // try
				//
			final String string = toString(object);
			//
			try {
				//
				value = Unit.with(this.defaultCloseOperation = Integer.valueOf(string));
				//
			} catch (final NumberFormatException e) {
				//
				exception = Unit.with(e);
				//
			} // try
				//
		} // if
			//
		if (value != null) {
			//
			this.defaultCloseOperation = IValue0Util.getValue0(value);
			//
		} else if (exception != null) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(IValue0Util.getValue0(exception));
			//
		} else {
			//
			throw new IllegalArgumentException(toString(getClass(IValue0Util.getValue0(value))));
			//
		} // if
			//
	}

	private static IValue0<Number> getDefaultCloseOperation(final CharSequence cs)
			throws IllegalAccessException, JsonProcessingException {
		//
		if (cs == null) {
			//
			return Unit.with(null);
			//
		} // if
			//
		final String string = toString(cs);
		//
		// java.lang.Integer.valueOf(java.lang.String)
		//
		NumberFormatException nfe = null;
		//
		try {
			//
			return testAndApply(StringUtils::isNotBlank, string, x -> Unit.with(Integer.valueOf(x)), null);
			//
		} catch (final NumberFormatException e) {
			//
			nfe = e;
			//
		} // try
			//
		List<Field> fs = FieldUtils.getAllFieldsList(JFrame.class).stream()
				.filter(f -> StringUtils.equalsIgnoreCase(getName(f), string)).toList();
		//
		int size = IterableUtils.size(fs);
		//
		if (size != 1) {
			//
			size = IterableUtils.size(fs = Arrays.stream(JFrame.class.getInterfaces()).map(FieldUtils::getAllFieldsList)
					.flatMap(Collection::stream).filter(f -> StringUtils.equalsIgnoreCase(getName(f), string))
					.toList());
			//
		} // if
			//
		if (size > 1) {
			//
			throw new IllegalArgumentException("size=" + size);
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		IValue0<Number> value = null;
		//
		IllegalAccessException iae = null;
		//
		try {
			//
			final Object obj = isStatic(f) ? get(f, null) : null;
			//
			if (obj instanceof Number) {
				//
				return Unit.with(Integer.valueOf(((Number) obj).intValue()));
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			iae = e;
			//
		} // try
			//
		JsonProcessingException jpe = null;
		//
		try {
			//
			final Object obj = ObjectMapperUtil.readValue(new ObjectMapper(), string, Object.class);
			//
			if (obj instanceof Number) {
				//
				value = Unit.with(Integer.valueOf(((Number) cs).intValue()));
				//
			} else if (obj != null) {
				//
				throw new IllegalArgumentException(toString(getClass(obj)));
				//
			} // if
				//
		} catch (final JsonProcessingException e) {
			//
			jpe = e;
			//
		} // try
			//
		if (value != null) {
			//
			return value;
			//
		} else if (iae != null) {
			//
			throw iae;
			//
		} else if (jpe != null) {
			//
			throw jpe;
			//
		} else if (nfe != null) {
			//
			throw nfe;
			//
		} // if
			//
		throw new IllegalArgumentException(toString(getClass(IValue0Util.getValue0(value))));
		//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static String getName(final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static boolean isStatic(final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final Function<T, R> functionTrue, final Function<T, R> functionFalse) throws E {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <R, T> R apply(final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
		//
		// java.awt.Frame
		//
		setTitle(cast(JFrame.class, bean), propertyResolver);
		//
		// javax.swing.JFrame
		//
		setDefaultCloseOperation(cast(JFrame.class, bean), propertyResolver, defaultCloseOperation);
		//
		return bean;
		//
	}

	private static void setTitle(final Frame frame, final PropertyResolver propertyResolver) {
		//
		if (frame != null) {
			//
			ensureObjectLockNotNull(frame);
			//
			// If there is a "java.awt.Frame.title" property found in
			// "org.springframework.core.env.PropertyResolver" instance and the
			// corresponding value is not empty, pass the corresponding value to the
			// "java.awt.Frame.setTitle(java.lang.String)" method
			//
			final String defaultTitle = testAndApply(x -> PropertyResolverUtil.containsProperty(propertyResolver, x),
					"java.awt.Frame.title", x -> PropertyResolverUtil.getProperty(propertyResolver, x), null);
			//
			if (StringUtils.isNotEmpty(defaultTitle)) {
				//
				frame.setTitle(defaultTitle);
				//
				return;
				//
			} // if
				//
			final Class<?> clz = getClass(frame);
			//
			final StringBuilder sb = new StringBuilder(StringUtils.defaultString(getName(clz)));
			//
			if (StringUtils.isNotEmpty(sb)) {
				//
				sb.append('.');
				//
				sb.append("title");
				//
			} // if
				//
			final String key = toString(sb);
			//
			if (PropertyResolverUtil.containsProperty(propertyResolver, key)) {
				//
				frame.setTitle(PropertyResolverUtil.getProperty(propertyResolver, key));
				//
			} else if (isAnnotationPresent(clz, Title.class)) {
				//
				setTitle(frame, getAnnotation(clz, Title.class));
				//
			} // if
				//
		} // if
			//
	}

	/**
	 * 
	 * If the "instance" is a subclass of "java.awt.Component" and its instantiation
	 * is done by
	 * "io.github.toolfactory.narcissus.Narcissus.allocateInstance(java.lang.Class)"
	 * (i.e. no constructor is being called), the "objectLock" field in
	 * "java.awt.Component" class will be null and this method will assign a new
	 * "java.lang.Object" instance to the "objectLock" field in "java.awt.Component"
	 * 
	 */
	private static void ensureObjectLockNotNull(final Object instance) {
		//
		try {
			//
			final Field objectLock = Component.class.getDeclaredField("objectLock");
			//
			if (objectLock != null && Narcissus.getObjectField(instance, objectLock) == null) {
				//
				Narcissus.setObjectField(instance, objectLock, new Object());
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
	}

	private static void setTitle(final Frame frame, final Title title) {
		//
		if (frame != null && title != null) {
			//
			frame.setTitle(title.value());
			//
		} // if
			//
	}

	private static boolean isAnnotationPresent(final AnnotatedElement instance,
			final Class<? extends Annotation> annotationClass) {
		return instance != null && annotationClass != null && instance.isAnnotationPresent(annotationClass);
	}

	private static <T extends Annotation> T getAnnotation(final AnnotatedElement instance,
			final Class<T> annotationClass) {
		return instance != null && annotationClass != null ? instance.getAnnotation(annotationClass) : null;
	}

	private static void setDefaultCloseOperation(final JFrame jFrame, final PropertyResolver propertyResolver,
			final Number defaultCloseOperation) {
		//
		if (jFrame != null) {
			//
			final StringBuilder sb = new StringBuilder(StringUtils.defaultString(getName(getClass(jFrame))));
			//
			sb.append('.');
			//
			sb.append("defaultCloseOperation");
			//
			Exception exception = null;
			//
			Number defaultCloseOperationNumber = null;
			//
			try {
				//
				final String key = toString(sb);
				//
				defaultCloseOperationNumber = IValue0Util
						.getValue0(PropertyResolverUtil.containsProperty(propertyResolver, key)
								? getDefaultCloseOperation(PropertyResolverUtil.getProperty(propertyResolver, key))
								: null);
				//
			} catch (final JsonProcessingException | IllegalAccessException e) {
				//
				exception = e;
				//
			} // try
				//
			if (defaultCloseOperationNumber != null) {
				//
				jFrame.setDefaultCloseOperation(defaultCloseOperationNumber.intValue());
				//
			} else if (defaultCloseOperation != null) {
				//
				jFrame.setDefaultCloseOperation(defaultCloseOperation.intValue());
				//
			} else if (exception != null) {
				//
				if (exception instanceof RuntimeException) {
					//
					throw (RuntimeException) exception;
					//
				} // if
					//
				throw new RuntimeException(exception);
				//
			} // if
				//
		} // if
			//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}