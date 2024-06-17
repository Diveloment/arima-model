package com.fedor.data.provider;

import com.fedor.data.Data;
import com.fedor.parser.CSVParser;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class CsvDataProvider implements DataProvider {

    List<Data> data;
    CSVParser parser;

    public CsvDataProvider(String pathToCsv, Class<? extends Data> dataClass) throws IOException, InvocationTargetException, CsvException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.parser = new CSVParser();
        this.data = parser.parseCSV(pathToCsv, dataClass);
    }

    @Override
    public double[] getData() {
        return this.parser.extractPrices(this.data);
    }
}
