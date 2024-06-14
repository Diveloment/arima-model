package com.fedor;

import com.fedor.chart.LineChartPlot;
import com.fedor.chart.ScatterPlotExample;
import com.fedor.timeseries.arima.Arima;
import com.fedor.timeseries.arima.struct.ArimaParams;
import com.fedor.timeseries.arima.struct.ForecastResult;
import com.fedor.utils.DatasetParam;

import javax.swing.*;
import java.util.Arrays;

import static com.fedor.utils.Utils.getFirst70Percent;
import static com.fedor.utils.testdata.NumberSequenceWithNoise.generateSequenceWithNoise;

public class App
{
    public static void main(String[] args) {
        //double[] dataArray = new double[] {1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768};
        //double[] dataArray = generateSequenceWithNoise(200, 1);
        /*double[] dataArray = new double[150];
        double k = 1;
        for (int i = 0; i < dataArray.length; i++) {
            k *= -1;
            dataArray[i] = k;
        }*/
        double[] dataArray = {
                112.0, 118.0, 132.0, 129.0, 121.0, 135.0, 148.0, 148.0, 136.0, 119.0, 104.0, 118.0,
                115.0, 126.0, 141.0, 135.0, 125.0, 149.0, 170.0, 170.0, 158.0, 133.0, 114.0, 140.0,
                145.0, 150.0, 178.0, 163.0, 172.0, 178.0, 199.0, 199.0, 184.0, 162.0, 146.0, 166.0,
                171.0, 180.0, 193.0, 181.0, 183.0, 218.0, 230.0, 242.0, 209.0, 191.0, 172.0, 194.0,
                196.0, 196.0, 236.0, 235.0, 229.0, 243.0, 264.0, 272.0, 237.0, 211.0, 180.0, 201.0,
                204.0, 188.0, 235.0, 227.0, 234.0, 264.0, 302.0, 293.0, 259.0, 229.0, 203.0, 242.0,
                233.0, 267.0, 269.0, 270.0, 315.0, 364.0, 347.0, 312.0, 274.0, 237.0, 278.0, 284.0,
                277.0, 317.0, 313.0, 318.0, 374.0, 413.0, 405.0, 355.0, 306.0, 271.0, 306.0, 315.0,
                301.0, 356.0, 348.0, 355.0, 422.0, 465.0, 467.0, 404.0, 347.0, 305.0, 336.0, 340.0,
                318.0, 362.0, 348.0, 363.0, 435.0, 491.0, 505.0, 404.0, 359.0, 310.0, 337.0, 360.0,
                342.0, 406.0, 396.0, 420.0, 472.0, 548.0, 559.0, 463.0, 407.0, 362.0, 405.0, 417.0,
                391.0, 419.0, 461.0, 472.0, 535.0, 622.0, 606.0, 508.0, 461.0, 390.0, 432.0
        };

        double[] dataForTest = getFirst70Percent(dataArray);

        int p = 30;
        int d = 0;
        int q = 30;
        int P = 1;
        int D = 1;
        int Q = 0;
        int m = 0;
        int forecastSize = 30;

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
                DatasetParam.create("forecast", forecastData, dataForTest.length),
                DatasetParam.create("uppers", uppers, dataForTest.length),
                DatasetParam.create("lowers", lowers, dataForTest.length)
        );
    }

    static void createChart(DatasetParam... params) {
        SwingUtilities.invokeLater(() -> {
            LineChartPlot chart = new LineChartPlot("Values");
            for (DatasetParam param : params) {
                chart.addToDataset(param.getName(), param.getValues());
            }
            chart.setSize(1200, 900);
            chart.setLocationRelativeTo(null);
            chart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chart.setVisible(true);
        });
    }
}
