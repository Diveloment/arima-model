package com.fedor.data;

import java.util.Date;

public interface Data {

    void setDataLine(String[] line);

    Date getDateAsDate();
    double getOpen();
    double getClose();
    double getHigh();
    double getLow();
    double getAdjClose();
}
