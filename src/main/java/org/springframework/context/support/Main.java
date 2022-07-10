package org.springframework.context.support;

import org.springframework.context.ConfigurableApplicationContext;

public class Main {

	private Main() {
	}

	public static void main(final String[] args) {
		//
		try (final ConfigurableApplicationContext beanFactory = new ClassPathXmlApplicationContext(
				"applicationContext.xml")) {
			//
			final VoiceManager instance = beanFactory.getBean(VoiceManager.class);
			//
			if (instance != null) {
				//
				instance.pack();
				//
				instance.setVisible(true);
				//
			} // if
				//
		}

	}

}