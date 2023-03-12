package org.springframework.context;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
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

import javax.annotation.Nullable;
import javax.sql.DataSource;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;
import org.springframework.beans.factory.ListableBeanFactoryUtil;
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
import org.springframework.core.io.ResourceUtil;

public class CustomBeanFactoryPostProcessor implements EnvironmentAware, BeanFactoryPostProcessor, Ordered,
		ApplicationListener<PayloadApplicationEvent<?>> {

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

	@Override
	public void onApplicationEvent(@Nullable final PayloadApplicationEvent<?> event) {
		//
		if (event != null && Objects.equals(event.getSource(), "ini")) {
			//
			final Object payload = event.getPayload();
			//
			if (LOG != null) {
				//
				LOG.info("iniSection={}", payload);
				//
			} else {
				//
				println("iniSection=" + payload);
				//
			} // if
				//
		} // if
			//
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
				ListableBeanFactoryUtil.getBeansOfType(beanFactory, PropertySourcesPlaceholderConfigurer.class));
		//
		try {
			//
			postProcessDatasources(ListableBeanFactoryUtil.getBeansOfType(beanFactory, DataSource.class), tableSql,
					tableSqlEncoding);
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
				TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(ObjectUtils.defaultIfNull(rootCause, e));
				//
			} // if
				//
		} // try
			//
	}

	private static void println(final Object object) {
		//
		final List<Field> fs = toList(
				filter(testAndApply(Objects::nonNull, System.class.getDeclaredFields(), Arrays::stream, null),
						f -> Objects.equals(getType(f), PrintStream.class) && Objects.equals(getName(f), "out")));
		//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		try {
			//
			final Object out = get(f, null);
			//
			final List<Method> ms = toList(
					filter(testAndApply(Objects::nonNull, getDeclaredMethods(getClass(out)), Arrays::stream, null),
							m -> Objects.equals(getName(m), "println")
									&& Arrays.equals(getParameterTypes(m), new Class<?>[] { Object.class })));
			//
			final int size = IterableUtils.size(ms);
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
				// System.out.println(java.lang.Object)
				//
			testAndAccept(x -> Boolean.logicalOr(isStatic(x), out != null), size > 0 ? IterableUtils.get(ms, 0) : null,
					x -> invoke(x, out, object));
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(ObjectUtils.firstNonNull(
					ExceptionUtils.getRootCause(targetException), targetException, ExceptionUtils.getRootCause(e), e));
			//
		} catch (final ReflectiveOperationException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
	}

	@Nullable
	private static Class<?> getType(@Nullable final Field field) {
		return field != null ? field.getType() : null;
	}

	@Nullable
	private static Object get(@Nullable final Field field, @Nullable final Object instance)
			throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Nullable
	private static Class<?>[] getParameterTypes(@Nullable final Executable instance) {
		return instance != null ? instance.getParameterTypes() : null;
	}

	private static void errorOrPrintStackTrace(final Logger logger, @Nullable final Throwable a,
			@Nullable final Throwable b) {
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
			testAndAccept(m -> Boolean.logicalOr(throwable != null, isStatic(m)), method, m -> invoke(m, throwable));
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

	private static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	@Nullable
	private static Method[] getDeclaredMethods(@Nullable final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	private static <T> Stream<T> filter(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	private static <T> List<T> toList(@Nullable final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, @Nullable final T value,
			@Nullable final FailableConsumer<T, E> consumer) throws E {
		//
		if (test(predicate, value) && consumer != null) {
			//
			consumer.accept(value);
			//
		} // if
			//
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static Object invoke(@Nullable final Method method, final Object instance, Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Nullable
	private static String getMessage(@Nullable final Throwable instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static void addPropertySourceToPropertySourcesToLast(final Environment environment,
			@Nullable final Map<?, PropertySourcesPlaceholderConfigurer> propertySourcesPlaceholderConfigurers) {
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
			@Nullable final Iterable<PropertySource<?>> propertySources) {
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

	@Nullable
	private static PropertySources getAppliedPropertySources(
			@Nullable final PropertySourcesPlaceholderConfigurer instance) throws IllegalStateException {
		return instance != null ? instance.getAppliedPropertySources() : null;
	}

	@Nullable
	private static <T> T getSource(@Nullable final PropertySource<T> instance) {
		return instance != null ? instance.getSource() : null;
	}

	private static void addLast(@Nullable final MutablePropertySources instance,
			@Nullable final PropertySource<?> propertySource) {
		if (instance != null) {
			instance.addLast(propertySource);
		}
	}

	private static void postProcessDatasources(@Nullable final Map<?, DataSource> dataSources, final Resource tableSql,
			final String tableSqlEncoding) throws SQLException, IOException {
		//
		if (dataSources != null && dataSources.values() != null) {
			//
			for (final DataSource dataSource : dataSources.values()) {
				//
				try (final Connection connection = getConnection(dataSource)) {
					//
					final Statement s = createStatement(connection);
					//
					if (s != null) {
						//
						testAndApply(Objects::nonNull,
								testAndApply(Objects::nonNull,
										ResourceUtil.exists(tableSql) ? InputStreamSourceUtil.getInputStream(tableSql)
												: null,
										x -> IOUtils.toString(x, tableSqlEncoding), null),
								s::execute, null);
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

	@Nullable
	private static Connection getConnection(@Nullable final DataSource instance) throws SQLException {
		return instance != null ? instance.getConnection() : null;
	}

	@Nullable
	private static Statement createStatement(@Nullable final Connection instance) throws SQLException {
		return instance != null ? instance.createStatement() : null;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

}