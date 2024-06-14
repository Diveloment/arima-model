package com.fedor.utils;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static List<Pair> toListOfPair(double[] a, int startAt) {
        List<Pair> list = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            Pair<Double, Double> p = new Pair<>((double) i + startAt, a[i]);
            list.add(p);
        }

        return list;
    }

    public static double[] getFirst70Percent(double[] originalArray) {
        int newSize = (int) Math.ceil(originalArray.length * 0.7);
        double[] resultArray = Arrays.copyOf(originalArray, newSize);

        return resultArray;
    }
}
