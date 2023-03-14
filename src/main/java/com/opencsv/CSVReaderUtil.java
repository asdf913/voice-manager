package com.opencsv;

import java.io.IOException;

import com.opencsv.exceptions.CsvValidationException;

public interface CSVReaderUtil {

	static String[] readNext(final CSVReader instance) throws CsvValidationException, IOException {
		return instance != null ? instance.readNext() : null;
	}

}