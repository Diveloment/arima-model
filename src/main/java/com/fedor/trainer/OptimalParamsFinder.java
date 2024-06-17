package com.fedor.trainer;

import com.fedor.timeseries.arima.Arima;
import com.fedor.timeseries.arima.struct.ArimaParams;
import com.fedor.timeseries.arima.struct.ForecastResult;

import java.util.Random;

public class OptimalParamsFinder {

    ArimaParams bestParams;
    double bestRMSE = -1.0;
    double bestMNV = -1.0;
    double[] dataForTest;
    int forecastSize = 30;
    static int noiseLevel = 10;

    public OptimalParamsFinder(double[] dataForTest) {
        this.dataForTest = dataForTest;
    }

    public ArimaParams run(ArimaParams initialParams, int steps) {

        bestParams = initialParams;
        for (int i = 0; i < steps; i++) {
            try {
                ArimaParams testParams = randomSalt(bestParams);
                ForecastResult forecastResult = Arima.forecast_arima(dataForTest, forecastSize, testParams);
                if (bestRMSE < 0 || bestMNV < 0) {
                    bestRMSE = forecastResult.getRMSE();
                    bestMNV = forecastResult.getMaxNormalizedVariance();
                    continue;
                }
                if (forecastResult.getRMSE() < bestRMSE) {
                    bestRMSE = forecastResult.getRMSE();
                    bestParams = testParams;
                }
                /*if (forecastResult.getMaxNormalizedVariance() < bestMNV) {
                    bestMNV = forecastResult.getMaxNormalizedVariance();
                    bestParams = testParams;
                }*/
            } catch (Exception ignored) {
            }

            if (i % (steps / 100) == 0) {
                System.out.println(i / (steps / 100) + "%");
            }
        }

        System.out.println("(OptimalParametersFinder): bestRMSE: " + bestRMSE + " bestParams: " + bestParams.toString());
        return bestParams;
    }

    private static ArimaParams randomSalt(ArimaParams params) {
        ArimaParams newParams = new ArimaParams(
                params.p + getNoise(),
                params.d + getNoise(1),
                params.q + getNoise(),
                params.P + getNoise(noiseLevel / 2),
                params.D + getNoise(noiseLevel / 2),
                params.Q + getNoise(noiseLevel / 2),
                params.m + getNoise(noiseLevel / 3)
        );
        return newParams;
    }

    private static int getNoise() {
        return getNoise(noiseLevel);
    }

    private static int getNoise(int level) {
        Random random = new Random();
        int noise = (random.nextInt()) % (level + 1);
        return noise;
    }
}
