package org.springframework.context;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.oxbow.swingbits.dialog.task.TaskDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;

public class CustomBeanFactoryPostProcessor implements EnvironmentAware, BeanFactoryPostProcessor, Ordered {

	private static Logger LOG = LoggerFactory.getLogger(CustomBeanFactoryPostProcessor.class);

	private Environment environment = null;

	private Resource tableSql = null;

	private String tableSqlEncoding = null;

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

	@Override
	public void setEnvironment(final Environment environment) {
		this.environment = environment;
	}

	public void setTableSql(final Resource tableSql) {
		this.tableSql = tableSql;
	}

	public void setTableSqlEncoding(final String tableSqlEncoding) {
		this.tableSqlEncoding = tableSqlEncoding;
	}

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) {
		//
		addPropertySourceToPropertySourcesToLast(environment,
				getBeansOfType(beanFactory, PropertySourcesPlaceholderConfigurer.class));
		//
		try {
			//
			postProcessDatasources(getBeansOfType(beanFactory, DataSource.class), tableSql, tableSqlEncoding);
			//
		} catch (final SQLException | IOException e) {
			//
			final Throwable rootCause = ObjectUtils.defaultIfNull(ExceptionUtils.getRootCause(e), e);
			//
			if (GraphicsEnvironment.isHeadless()) {
				//
				errorOrPrintStackTrace(LOG, rootCause, e);
				//
			} else {
				//
				if (rootCause != null) {
					//
					TaskDialogs.showException(rootCause);
					//
				} else if (e != null) {
					//
					TaskDialogs.showException(e);
					//
				} // if
					//
			} // if
				//
		} // try
			//
	}

	private static void errorOrPrintStackTrace(final Logger logger, final Throwable a, final Throwable b) {
		//
		if (Boolean.logicalAnd(logger != null, !LoggerUtil.isNOPLogger(logger))) {
			//
			if (a != null) {
				//
				LoggerUtil.error(logger, getMessage(a), a);
				//
			} else if (b != null) {
				//
				LoggerUtil.error(logger, getMessage(b), b);
				//
			} // if
				//
		} else if (a != null) {
			//
			printStackTrace(a);
			//
		} else if (b != null) {
			//
			printStackTrace(b);
			//
		} // if
			//
	}

	private static void printStackTrace(final Throwable throwable) {
		//
		final List<Method> ms = toList(filter(
				testAndApply(Objects::nonNull, getDeclaredMethods(Throwable.class), Arrays::stream, null),
				m -> m != null && StringUtils.equals(getName(m), "printStackTrace") && m.getParameterCount() == 0));
		//
		final Method method = testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null);
		//
		if (method != null) {
			//
			method.setAccessible(true);
			//
		} // if
			//
		try {
			//
			testAndAccept(m -> throwable != null || isStatic(m), method, m -> invoke(m, throwable));
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			printStackTrace(ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException), targetException,
					ExceptionUtils.getRootCause(e), e));
			//
		} catch (final ReflectiveOperationException e) {
			//
			printStackTrace(throwable);
			//
		} // try
			//
	}

	private static boolean isStatic(final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws E {
		//
		if (test(predicate, value) && consumer != null) {
			//
			consumer.accept(value);
			//
		} // if
			//
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Object invoke(final Method method, final Object instance, Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	private static String getMessage(final Throwable instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static <T> Map<String, T> getBeansOfType(final ListableBeanFactory instance, final Class<T> type) {
		return instance != null ? instance.getBeansOfType(type) : null;
	}

	private static void addPropertySourceToPropertySourcesToLast(final Environment environment,
			final Map<?, PropertySourcesPlaceholderConfigurer> propertySourcesPlaceholderConfigurers) {
		//
		if (propertySourcesPlaceholderConfigurers != null && propertySourcesPlaceholderConfigurers.values() != null) {
			//
			for (final PropertySourcesPlaceholderConfigurer pspc : propertySourcesPlaceholderConfigurers.values()) {
				//
				addPropertySourceToPropertySourcesToLast(environment, getAppliedPropertySources(pspc));
				//
			} // for
				//
		} // if
			//
	}

	private static void addPropertySourceToPropertySourcesToLast(final Environment environment,
			final Iterable<PropertySource<?>> propertySources) {
		//
		if (propertySources != null && propertySources.iterator() != null) {
			//
			for (final PropertySource<?> ps : propertySources) {
				//
				if (Boolean.logicalOr(ps == null, getSource(ps) == environment)) {
					//
					continue;
					//
				} // if
					//
				if (environment instanceof ConfigurableEnvironment) {
					//
					addLast(((ConfigurableEnvironment) environment).getPropertySources(), ps);
					//
				} // if
					//
			} // for
				//
		} // if
			//
	}

	private static PropertySources getAppliedPropertySources(final PropertySourcesPlaceholderConfigurer instance)
			throws IllegalStateException {
		return instance != null ? instance.getAppliedPropertySources() : null;
	}

	private static <T> T getSource(final PropertySource<T> instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static void addLast(final MutablePropertySources instance, final PropertySource<?> propertySource) {
		if (instance != null) {
			instance.addLast(propertySource);
		}
	}

	private static void postProcessDatasources(final Map<?, DataSource> dataSources, final Resource tableSql,
			final String tableSqlEncoding) throws SQLException, IOException {
		//
		if (dataSources != null && dataSources.values() != null) {
			//
			for (final DataSource dataSource : dataSources.values()) {
				//
				if (dataSource == null) {
					continue;
				} // if
					//
				try (final Connection connection = getConnection(dataSource)) {
					//
					final Statement s = createStatement(connection);
					//
					if (s != null) {
						//
						testAndApply(Objects::nonNull, testAndApply(Objects::nonNull,
								tableSql != null && tableSql.exists() ? InputStreamSourceUtil.getInputStream(tableSql)
										: null,
								x -> IOUtils.toString(x, tableSqlEncoding), null), x -> s.execute(x), null);
						//
					} // if
						//
				} // try
					//
			} // for
				//
		} // if
			//
	}

	private static Connection getConnection(final DataSource instance) throws SQLException {
		return instance != null ? instance.getConnection() : null;
	}

	private static Statement createStatement(final Connection instance) throws SQLException {
		return instance != null ? instance.createStatement() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> instance, final T value)
			throws E {
		return instance != null ? instance.apply(value) : null;
	}

}