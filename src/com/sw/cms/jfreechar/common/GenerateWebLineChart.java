package com.sw.cms.jfreechar.common;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;

public class GenerateWebLineChart {
	
	public static String getLineChart(CategoryDataset dataset, String title, String xName, String yName, int width, int height, HttpSession session, PrintWriter pw){
		
		String filename = null;
		Font font = null;
		
		JFreeChart lineChart = ChartFactory.createLineChart(title, xName, yName,  // range axis label
				dataset, // data
			    PlotOrientation.VERTICAL, // orientation
			    true, // include legend
			    false, // tooltips
			    false // urls
			    );
		
		
		//这个地方是设置统计图标题的字体和大小
		TextTitle tt = new TextTitle(title);
		font = new Font("宋体", Font.PLAIN, 14);
		tt.setFont(font);
		lineChart.setTitle(tt);

		//统计图片的底色
		lineChart.setBackgroundPaint(Color.white); 
		
		CategoryPlot plot = lineChart.getCategoryPlot();
		
		//下面的代码负责获得横轴对象，并设置相关的绘图属性
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setAxisLineStroke(new BasicStroke(1.0f));     // 设置轴线粗细
		domainAxis.setAxisLinePaint(Color.BLACK);     // 设置轴线颜色
		domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
		domainAxis.setLabelPaint(Color.BLACK);     // 设置坐标轴标题颜色
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD); //设置坐标轴标题旋转角度，这里并未旋转
		plot.setDomainAxis(domainAxis);

		//下面的代码负责获得纵轴对象，并设置相关的绘图属性
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setAxisLineStroke(new BasicStroke(1.0f));     // 设置轴线粗细
		rangeAxis.setAxisLinePaint(Color.BLACK);     // 设置轴线颜色
		rangeAxis.setTickMarkStroke(new BasicStroke(1.0f));     // 设置坐标标记大小
		rangeAxis.setTickMarkPaint(Color.BLACK);     // 设置坐标标记颜色
		rangeAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));    // 设置坐标轴标题字体
		rangeAxis.setLabelPaint(Color.BLACK);     // 设置坐标轴标题颜色
		rangeAxis.setLabelAngle(Math.PI / 2);     // 设置坐标轴标题旋转角度
		plot.setRangeAxis(rangeAxis);
		
		//取得第一个图例
		LegendTitle legend = lineChart.getLegend(0);
		//修改图例的字体
		legend.setItemFont(new Font("宋体", Font.PLAIN, 12)); 
		
		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());		
		
		try {
			filename = ServletUtilities.saveChartAsPNG(lineChart, width, height, info, session);
			// 把image map 写入到 PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, true);
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		pw.flush();

		return filename;
		
	}

}
