package org.springframework.context;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.VoiceManager;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

public class CustomBeanFactoryPostProcessor implements EnvironmentAware, BeanFactoryPostProcessor {

	private static Logger LOG = LoggerFactory.getLogger(CustomBeanFactoryPostProcessor.class);

	private Environment environment = null;

	@Override
	public void setEnvironment(final Environment environment) {
		this.environment = environment;
	}

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
		//
		addPropertySourceToPropertySourcesToLast(environment,
				beanFactory != null ? beanFactory.getBeansOfType(PropertySourcesPlaceholderConfigurer.class) : null);
		//
		try {
			//
			postProcessDatasources(beanFactory != null ? beanFactory.getBeansOfType(DataSource.class) : null);
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

	private static void postProcessDatasources(final Map<?, DataSource> dataSources) throws SQLException, IOException {
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
						s.execute(IOUtils.toString(VoiceManager.class.getResource("/table.sql"), "utf-8"));
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

}