package com.sw.cms.jfreechar.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.TextAnchor;

/**
 * 3D柱状图的显示
 * 
 * @author jinyanni
 * 
 */

public class GenerateWebBarChart3D {

	public static String getBarChart3D(CategoryDataset dataset, String title, String xName, String yName, int width, int height, HttpSession session, PrintWriter pw) {

		String filename = null;
		Font font = null;

		JFreeChart chart = ChartFactory.createBarChart3D(title, xName, yName, dataset, PlotOrientation.VERTICAL, true, false, false);

		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

		//这个地方是设置统计图标题的字体和大小
		TextTitle tt = new TextTitle(title);
		font = new Font("宋体", Font.PLAIN, 14);
		tt.setFont(font);
		chart.setTitle(tt);

		//统计图片的底色
		chart.setBackgroundPaint(Color.white); 

		CategoryPlot categoryPlot = chart.getCategoryPlot();

		CategoryAxis domainAxis = categoryPlot.getDomainAxis();

		// 设置距离图片左端距离,参数为图片的百分比
		domainAxis.setLowerMargin(0.00);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.00);
		// 设置横坐标的坐标值的字体
		domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
		
		// 使横坐标设置生效
		categoryPlot.setDomainAxis(domainAxis);

		
		ValueAxis rangeAxis = categoryPlot.getRangeAxis();
		
		// 设置最高的一个柱与图片顶端的距离
		rangeAxis.setUpperMargin(0.05);
		// 设置最低的一个柱与图片底端的距离
		rangeAxis.setLowerMargin(0.05);
		// 设置竖坐标标签的旋转角度
		rangeAxis.setLabelAngle(0.05);
		
		categoryPlot.setRangeAxis(rangeAxis);
		
		//取得第一个图例
		LegendTitle legend = chart.getLegend(0);
		//修改图例的字体
		legend.setItemFont(new Font("宋体", Font.PLAIN, 12)); 
		
		//设置柱状图值的显示
		BarRenderer3D renderer = new BarRenderer3D(); 
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelPaint(Color.black);
		renderer.setBaseItemLabelFont(new Font("宋体", Font.PLAIN, 12));
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		renderer.setBaseItemLabelsVisible(true);
        categoryPlot.setRenderer(renderer);

		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
			// 把image map 写入到 PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, true);
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		pw.flush();

		return filename;
	}

}
