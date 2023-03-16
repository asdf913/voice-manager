package org.springframework.beans.factory;

import java.util.Map;

import org.javatuples.valueintf.IValue0;

public interface MapFromResourceFactoryBean<K, V> extends FactoryBean<Map<K, V>> {

	IValue0<Map<K, V>> getIvalue0();

	@Override
	default Class<?> getObjectType() {
		return Map.class;
	}

}