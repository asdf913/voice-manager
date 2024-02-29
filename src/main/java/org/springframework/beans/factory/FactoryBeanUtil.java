package org.springframework.beans.factory;

import javax.annotation.Nullable;

import org.apache.commons.lang3.reflect.FieldUtils;

public interface FactoryBeanUtil {

	@Nullable
	static Class<?> getObjectType(@Nullable final FactoryBean<?> instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			final Class<?> clz = Util.getClass(instance);
			//
			if ((Util.isAssignableFrom(clz, Class.forName("org.springframework.aop.framework.ProxyFactoryBean"))
					&& FieldUtils.readField(instance, "listeners", true) == null)
					|| (Util.isAssignableFrom(clz,
							Class.forName("org.springframework.aop.scope.ScopedProxyFactoryBean"))
							&& FieldUtils.readField(instance, "proxy", true) == null
							&& FieldUtils.readField(instance, "scopedTargetSource", true) == null)
					|| (Util.isAssignableFrom(clz,
							Class.forName("org.springframework.transaction.config.JtaTransactionManagerFactoryBean"))
							&& FieldUtils.readField(instance, "transactionManager", true) == null)) {
				//
				return null;
				//
			} // if
				//
		} catch (final ClassNotFoundException | IllegalAccessException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		return instance.getObjectType();
		//
	}

}