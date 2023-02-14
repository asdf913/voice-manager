package org.springframework.beans.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

public class CustomBeanPostProcessor implements BeanPostProcessor {

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
			if (value == null) {
				//
				List<Field> fs = FieldUtils.getAllFieldsList(JFrame.class).stream()
						.filter(f -> f != null && StringUtils.equalsIgnoreCase(f.getName(), string)).toList();
				//
				int size = IterableUtils.size(fs);
				//
				if (size != 1) {
					//
					size = IterableUtils.size(fs = Arrays.stream(JFrame.class.getInterfaces())
							.map(FieldUtils::getAllFieldsList).flatMap(Collection::stream)
							.filter(f -> f != null && StringUtils.equalsIgnoreCase(f.getName(), string)).toList());
					//
				} // if
					//
				if (size > 1) {
					//
					throw new IllegalArgumentException("size=" + size);
					//
				} // if
					//
				final Field f = size == 1 ? IterableUtils.get(fs, 0) : null;
				//
				if (f != null && Modifier.isStatic(f.getModifiers())) {
					//
					try {
						//
						final Object obj = f.get(null);
						//
						if (obj instanceof Number) {
							//
							value = Unit.with(Integer.valueOf(((Number) obj).intValue()));
							//
						} // if
							//
					} catch (final IllegalAccessException e) {
						//
						exception = Unit.with(e);
						//
					} // try
						//
				} // if
					//
			} // if
				//
			if (value == null) {
				//
				try {
					//
					final Object obj = ObjectMapperUtil.readValue(new ObjectMapper(), string, Object.class);
					//
					if (obj instanceof Number) {
						//
						value = Unit.with(Integer.valueOf(((Number) object).intValue()));
						//
					} else if (obj != null) {
						//
						throw new IllegalArgumentException(obj != null ? toString(obj.getClass()) : null);
						//
					} // if
						//
				} catch (final JsonProcessingException e) {
					//
					exception = Unit.with(e);
					//
				} // try
					//
			} // if
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
			final Object obj = IValue0Util.getValue0(value);
			//
			throw new IllegalArgumentException(toString(obj != null ? obj.getClass() : null));
			//
		} // if
			//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
		//
		final JFrame jFrame = bean instanceof JFrame ? (JFrame) bean : null;
		//
		if (jFrame != null && defaultCloseOperation != null) {
			//
			jFrame.setDefaultCloseOperation(defaultCloseOperation.intValue());
			//
		} // if
			//
		return bean;
		//
	}

}