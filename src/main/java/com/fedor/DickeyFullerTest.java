package com.fedor;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.util.FastMath;

public class DickeyFullerTest {

    public static boolean performDickeyFullerTest(double[] data) {
        // Определение параметров теста
        int n = data.length;
        double[] Y = new double[n - 1];
        double[] X = new double[n - 1];

        // Вычисление первых разностей ряда
        for (int i = 0; i < n - 1; i++) {
            Y[i] = data[i + 1];
            X[i] = data[i];
        }

        // Расчет коэффициентов регрессии
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < Y.length; i++) {
            regression.addData(X[i], Y[i]);
        }
        double slope = regression.getSlope();
        double intercept = regression.getIntercept();

        // Вычисление ошибки регрессии
        double[] errors = new double[Y.length];
        for (int i = 0; i < Y.length; i++) {
            double predicted = slope * X[i] + intercept;
            errors[i] = Y[i] - predicted;
        }

        // Вычисление стандартного отклонения ошибок
        double stdError = calculateStandardError(errors);

        // Вычисление статистики теста
        double testStatistic = slope / stdError;

        // Определение критического значения для теста Дики-Фуллера
        // Значения критических точек взяты для уровня значимости 5% (или 95% доверительного уровня)
        double criticalValue = -2.88; // Значение для автоматического уровня значимости 5%

        // Сравнение тестовой статистики с критическим значением
        return testStatistic < criticalValue;
    }

    private static double calculateStandardError(double[] errors) {
        double sumSquaredErrors = 0.0;
        for (double error : errors) {
            sumSquaredErrors += FastMath.pow(error, 2);
        }
        return FastMath.sqrt(sumSquaredErrors / (errors.length - 2));
    }

    public static double[] makeStationary(double[] data) {
        // Простой метод проверки на стационарность и дифференцирования
        if (performDickeyFullerTest(data)) {
            return data;
        } else {
            return difference(data);
        }
    }

    public static double[] difference(double[] data) {
        double[] differenced = new double[data.length - 1];
        for (int i = 1; i < data.length; i++) {
            differenced[i - 1] = data[i] - data[i - 1];
        }
        return differenced;
    }
}
