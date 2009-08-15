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
 * 3D��״ͼ����ʾ
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

		//����ط�������ͳ��ͼ���������ʹ�С
		TextTitle tt = new TextTitle(title);
		font = new Font("����", Font.PLAIN, 14);
		tt.setFont(font);
		chart.setTitle(tt);

		//ͳ��ͼƬ�ĵ�ɫ
		chart.setBackgroundPaint(Color.white); 

		CategoryPlot categoryPlot = chart.getCategoryPlot();

		CategoryAxis domainAxis = categoryPlot.getDomainAxis();

		// ���þ���ͼƬ��˾���,����ΪͼƬ�İٷֱ�
		domainAxis.setLowerMargin(0.00);
		// ���þ���ͼƬ�Ҷ˾���
		domainAxis.setUpperMargin(0.00);
		// ���ú����������ֵ������
		domainAxis.setTickLabelFont(new Font("����", Font.PLAIN, 12));
		
		// ʹ������������Ч
		categoryPlot.setDomainAxis(domainAxis);

		
		ValueAxis rangeAxis = categoryPlot.getRangeAxis();
		
		// ������ߵ�һ������ͼƬ���˵ľ���
		rangeAxis.setUpperMargin(0.05);
		// ������͵�һ������ͼƬ�׶˵ľ���
		rangeAxis.setLowerMargin(0.05);
		// �����������ǩ����ת�Ƕ�
		rangeAxis.setLabelAngle(0.05);
		
		categoryPlot.setRangeAxis(rangeAxis);
		
		//ȡ�õ�һ��ͼ��
		LegendTitle legend = chart.getLegend(0);
		//�޸�ͼ��������
		legend.setItemFont(new Font("����", Font.PLAIN, 12)); 
		
		//������״ͼֵ����ʾ
		BarRenderer3D renderer = new BarRenderer3D(); 
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelPaint(Color.black);
		renderer.setBaseItemLabelFont(new Font("����", Font.PLAIN, 12));
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		renderer.setBaseItemLabelsVisible(true);
        categoryPlot.setRenderer(renderer);

		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
			// ��image map д�뵽 PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, true);
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		pw.flush();

		return filename;
	}

}
