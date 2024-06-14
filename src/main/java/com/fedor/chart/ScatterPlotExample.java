package com.fedor.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class ScatterPlotExample extends JFrame {

    public ScatterPlotExample(String title) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset();

        // Create scatter plot
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

        // Get XY plot to customize renderer
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesLinesVisible(0, true); // Show lines for series 0
        renderer.setSeriesShapesVisible(0, true); // Show shapes (dots) for series 0
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-1.0, -1.0, 2.0, 2.0));
        plot.setRenderer(renderer);

        // Create a panel to display chart
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Adding data to the dataset
        XYSeries series = new XYSeries("Data Series");
        series.add(1.0, 1.0);
        series.add(2.0, 4.0);
        series.add(3.0, 3.0);
        series.add(4.0, 5.0);
        series.add(5.0, 2.0);
        dataset.addSeries(series);

        return dataset;
    }
}
