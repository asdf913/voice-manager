package org.apache.ibatis.session;

public interface SqlSessionFactoryUtil {

	static Configuration getConfiguration(final SqlSessionFactory instance) {
		return instance != null ? instance.getConfiguration() : null;
	}

	static SqlSession openSession(final SqlSessionFactory instance) {
		return instance != null ? instance.openSession() : null;
	}

}