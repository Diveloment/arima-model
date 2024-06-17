package com.fedor.data;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class DollarRubData implements com.fedor.data.Data {
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double adjClose;
    private int volume;

    @Override
    public void setDataLine(String[] line) {
        this.date = line[0];
        this.open = Double.parseDouble(line[1]);
        this.high = Double.parseDouble(line[2]);
        this.low = Double.parseDouble(line[3]);
        this.close = Double.parseDouble(line[4]);
        this.adjClose = Double.parseDouble(line[5]);
        this.volume = Integer.parseInt(line[6]);
    }

    public Date getDateAsDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(this.date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
