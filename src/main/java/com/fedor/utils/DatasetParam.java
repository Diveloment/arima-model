package com.fedor.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.util.Pair;

import java.util.List;

import static com.fedor.utils.Utils.toListOfPair;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatasetParam {

    String name;
    List<Pair> values;

    public static DatasetParam create(String name, double[] values) {
        DatasetParam datasetParam = new DatasetParam();
        datasetParam.setName(name);
        datasetParam.setValues(toListOfPair(values, 0));
        return datasetParam;
    }

    public static DatasetParam create(String name, double[] values, int startAt) {
        DatasetParam datasetParam = new DatasetParam();
        datasetParam.setName(name);
        datasetParam.setValues(toListOfPair(values, startAt));
        return datasetParam;
    }
}
