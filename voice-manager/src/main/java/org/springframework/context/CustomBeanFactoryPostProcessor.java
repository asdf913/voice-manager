package org.springframework.context;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.core.io.Resource;

public class CustomBeanFactoryPostProcessor implements EnvironmentAware, BeanFactoryPostProcessor {

	private static Logger LOG = LoggerFactory.getLogger(CustomBeanFactoryPostProcessor.class);

	private Environment environment = null;

	private Resource tableSql = null;

	private String tableSqlEncoding = null;

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
			if (GraphicsEnvironment.isHeadless()) {
				//
				if (LOG != null) {
					LOG.error(e.getMessage(), e);
				} else {
					e.printStackTrace();
				} // if
					//
			} else {
				//
				JOptionPane.showMessageDialog(null, e.getMessage());
				//
			} // if
				//
		} // try
			//
	}

	private static <T> Map<String, T> getBeansOfType(final ListableBeanFactory instance, final Class<T> type) {
		return instance != null ? instance.getBeansOfType(type) : null;
	}

	private static void addPropertySourceToPropertySourcesToLast(final Environment environment,
			final Map<?, PropertySourcesPlaceholderConfigurer> propertySourcesPlaceholderConfigurers) {
		//
		if (propertySourcesPlaceholderConfigurers != null && propertySourcesPlaceholderConfigurers.values() != null) {
			//
			PropertySources pss = null;
			//
			for (final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer : propertySourcesPlaceholderConfigurers
					.values()) {
				//
				if (propertySourcesPlaceholderConfigurer == null
						|| (pss = propertySourcesPlaceholderConfigurer.getAppliedPropertySources()) == null) {
					continue;
				} // if
					//
				for (final PropertySource<?> ps : pss) {
					//
					if (ps == null || ps.getSource() == environment) {
						continue;
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
			} // for
				//
		} // if
			//
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
				try (final Connection connection = dataSource != null ? dataSource.getConnection() : null) {
					//
					final Statement s = connection != null ? connection.createStatement() : null;
					//
					if (s != null) {
						//
						testAndApply(Objects::nonNull,
								testAndApply(Objects::nonNull,
										tableSql != null && tableSql.exists() ? tableSql.getInputStream() : null,
										x -> IOUtils.toString(x, tableSqlEncoding), null),
								x -> s.execute(x), null);
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

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> instance, final T value)
			throws E {
		return instance != null ? instance.apply(value) : null;
	}

}