package com.fedor;

import com.fedor.chart.LineChartPlot;
import com.fedor.chart.ScatterPlotExample;
import com.fedor.data.DollarRubData;
import com.fedor.data.provider.AirlinesMockDataProvider;
import com.fedor.data.provider.CsvDataProvider;
import com.fedor.data.provider.DataProvider;
import com.fedor.timeseries.arima.Arima;
import com.fedor.timeseries.arima.struct.ArimaParams;
import com.fedor.timeseries.arima.struct.ForecastResult;
import com.fedor.utils.DatasetParam;
import com.opencsv.exceptions.CsvException;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static com.fedor.utils.Utils.getFirst70Percent;
import static com.fedor.utils.Utils.getFirstPercent;

public class App
{
    public static void main(String[] args) throws IOException, InvocationTargetException, CsvException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        DataProvider dataProvider = new CsvDataProvider("/home/fedor/IdeaProjects/ARIMA/src/main/resources/datasets/rubdollar5y.csv", DollarRubData.class);

        double[] dataArray = dataProvider.getData();
        double[] dataForTest = getFirstPercent(dataArray, 0.75);

        int p = 30;
        int d = 0;
        int q = 30;
        int P = 1;
        int D = 1;
        int Q = 0;
        int m = 0;
        int forecastSize = 170;

        ArimaParams params = new ArimaParams(p, d, q, P, D, Q, m);
        ForecastResult forecastResult = Arima.forecast_arima(dataForTest, forecastSize, params);

        double[] forecastData = forecastResult.getForecast();
        double[] uppers = forecastResult.getForecastUpperConf();
        double[] lowers = forecastResult.getForecastLowerConf();
        double rmse = forecastResult.getRMSE();
        double maxNormalizedVariance = forecastResult.getMaxNormalizedVariance();

        System.out.println(Arrays.toString(dataArray));
        System.out.println(Arrays.toString(forecastData));
        System.out.println("rmse: " + rmse + " mnv: " + maxNormalizedVariance);

        String log = forecastResult.getLog();
        System.out.println(log);

        createChart(
                DatasetParam.create("dataset", dataArray),
                DatasetParam.create("forecast", forecastData, dataForTest.length)
                //DatasetParam.create("uppers", uppers, dataForTest.length),
                //DatasetParam.create("lowers", lowers, dataForTest.length)
        );
    }

    static void createChart(DatasetParam... params) {
        SwingUtilities.invokeLater(() -> {
            LineChartPlot chart = new LineChartPlot("Values");
            for (DatasetParam param : params) {
                chart.addToDataset(param.getName(), param.getValues());
            }
            chart.setSize(800, 600);
            chart.setLocationRelativeTo(null);
            chart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chart.setVisible(true);
        });
    }
}
