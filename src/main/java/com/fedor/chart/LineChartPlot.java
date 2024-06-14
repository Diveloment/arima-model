package com.fedor.chart;

import org.apache.commons.math3.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LineChartPlot extends JFrame {

    XYSeriesCollection dataset;

    public LineChartPlot(String title) {
        super(title);

        dataset = new XYSeriesCollection();
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Scatter Plot Example", // chart title
                "X-axis", // x-axis label
                "Y-axis", // y-axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesLinesVisible(0, true); // Show lines for series 0
        renderer.setSeriesShapesVisible(0, true); // Show shapes (dots) for series 0
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-1.0, -1.0, 2.0, 2.0));
        plot.setRenderer(renderer);

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    public void addToDataset(String seriesName, List<Pair> dataSet) {
        XYSeries series = new XYSeries(seriesName);
        for (Pair pair : dataSet) {
            series.add((Number) pair.getFirst(), (double) pair.getSecond());
        }
        dataset.addSeries(series);
    }
}
