package com.opencsv;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import com.opencsv.exceptions.CsvValidationException;

import io.github.toolfactory.narcissus.Narcissus;

public class CSVReaderUtil {

	private static final Logger LOG = LoggerFactory.getLogger(CSVReaderUtil.class);

	private CSVReaderUtil() {
	}

	@Nullable
	public static String[] readNext(final CSVReader instance) throws CsvValidationException, IOException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (Narcissus.getField(instance, getDeclaredField(getClass(instance), "peekedLines")) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.readNext();
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static Field getDeclaredField(final Class<?> instance, final String name) throws NoSuchFieldException {
		return instance != null && name != null ? instance.getDeclaredField(name) : null;
	}

}