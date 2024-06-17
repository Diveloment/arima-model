package com.fedor.data.provider;

import static com.fedor.utils.testdata.NumberSequenceWithNoise.generateSequenceWithNoise;

public class RandomSeqProvider implements DataProvider {

    double[] dataArray;

    public RandomSeqProvider() {
        this.dataArray = generateSequenceWithNoise(200, 1);
    }

    public RandomSeqProvider(int n, double level) {
        this.dataArray = generateSequenceWithNoise(n, level);
    }


    @Override
    public double[] getData() {
        return this.dataArray;
    }
}
