package com.fedor.parser;

import com.fedor.data.Data;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CSVParser {

    public <T extends Data> List<Data> parseCSV(String filePath, Class<T> type) throws IOException, CsvException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Data> dataList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> lines = reader.readAll();
            Constructor<T> constructor = type.getConstructor();

            for (int i = 1; i < lines.size(); i++) {
                T data = constructor.newInstance();
                data.setDataLine(lines.get(i));
                dataList.add(data);
            }
        }

        Collections.sort(dataList, Comparator.comparing(Data::getDateAsDate));

        return dataList;
    }

    public double[] extractPrices(List<Data> dataList) {
        return extractPrices(dataList, "open");
    }

    public double[] extractPrices(List<Data> dataList, String priceType) {
        double[] prices = new double[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            Data data = dataList.get(i);
            switch (priceType) {
                case "open":
                    prices[i] = data.getOpen();
                    break;
                case "high":
                    prices[i] = data.getHigh();
                    break;
                case "low":
                    prices[i] = data.getLow();
                    break;
                case "close":
                    prices[i] = data.getClose();
                    break;
                case "adjClose":
                    prices[i] = data.getAdjClose();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid price type: " + priceType);
            }
        }
        return prices;
    }
}
