package com.fedor;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.Arrays;

public class PACF {

    public static double[] calculateACF(double[] data, int maxLag) {
        double[] acf = new double[maxLag + 1];
        for (int lag = 0; lag <= maxLag; lag++) {
            double correlation = pearsonCorrelation(data, lag);
            acf[lag] = correlation;
        }
        return acf;
    }

    private static double pearsonCorrelation(double[] data, int lag) {
        int n = data.length - lag;
        double[] x = Arrays.copyOfRange(data, 0, n);
        double[] y = Arrays.copyOfRange(data, lag, data.length);
        PearsonsCorrelation pc = new PearsonsCorrelation();
        return pc.correlation(x, y);
    }

    public static double[] calculatePACF(double[] data, int maxLag) {
        double[] pacf = new double[maxLag + 1];

        // Вычисление PACF с использованием рекурсии или метода Юла-Вокера
        for (int k = 1; k <= maxLag; k++) {
            double[] partialCorrelations = new double[k + 1];
            for (int j = 1; j <= k; j++) {
                double[] Y = new double[data.length - j];
                double[] X = new double[data.length - j];
                for (int i = 0; i < Y.length; i++) {
                    Y[i] = data[i + j];
                    X[i] = data[i];
                }
                PearsonsCorrelation pc = new PearsonsCorrelation();
                partialCorrelations[j] = pc.correlation(X, Y);
            }
            double numerator = partialCorrelations[k];
            for (int i = 1; i <= k - 1; i++) {
                numerator -= partialCorrelations[i] * pacf[k - i];
            }
            double denominator = 1.0;
            for (int i = 1; i <= k - 1; i++) {
                denominator -= partialCorrelations[i] * pacf[i];
            }
            pacf[k] = numerator / denominator;
        }

        return pacf;
    }

    public static int determineP(double[] pacf) {
        for (int lag = 1; lag < pacf.length; lag++) {
            if (Math.abs(pacf[lag]) < 1.96 / Math.sqrt(pacf.length)) {
                return lag - 1;
            }
        }
        return pacf.length - 1;
    }

    public static int determineQ(double[] acf) {
        for (int lag = 1; lag < acf.length; lag++) {
            if (Math.abs(acf[lag]) < 1.96 / Math.sqrt(acf.length)) {
                return lag - 1;
            }
        }
        return acf.length - 1;
    }

    public static int determineP(double[] timeSeries, int maxLag) {
        double[] pacfValues = calculatePACF(timeSeries, maxLag);
        int p = determineP(pacfValues);
        return p;
    }

    public static int determineQ(double[] timeSeries, int maxLag) {
        double[] acfValues = calculateACF(timeSeries, maxLag);
        int q = determineQ(acfValues);
        return q;
    }
}
