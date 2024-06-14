package com.fedor.utils.testdata;

import java.util.Random;

public class NumberSequenceWithNoise {

    public static double[] generateSequenceWithNoise(int n, double noiseLevel) {
        double[] sequence = new double[n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            // Генерируем число от 1 до n
            double baseValue = i + 1;
            // Добавляем шум
            double noise = (random.nextDouble() * 2 - 1) * noiseLevel;
            sequence[i] = baseValue + noise;
        }

        return sequence;
    }
}
