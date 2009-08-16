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
		
		
		//����ط�������ͳ��ͼ���������ʹ�С
		TextTitle tt = new TextTitle(title);
		font = new Font("����", Font.PLAIN, 14);
		tt.setFont(font);
		lineChart.setTitle(tt);

		//ͳ��ͼƬ�ĵ�ɫ
		lineChart.setBackgroundPaint(Color.white); 
		
		CategoryPlot plot = lineChart.getCategoryPlot();
		
		//����Ĵ��븺���ú�����󣬲�������صĻ�ͼ����
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setAxisLineStroke(new BasicStroke(1.0f));     // �������ߴ�ϸ
		domainAxis.setAxisLinePaint(Color.BLACK);     // ����������ɫ
		domainAxis.setTickLabelFont(new Font("����", Font.PLAIN, 12));
		domainAxis.setLabelPaint(Color.BLACK);     // ���������������ɫ
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD); //���������������ת�Ƕȣ����ﲢδ��ת
		plot.setDomainAxis(domainAxis);

		//����Ĵ��븺����������󣬲�������صĻ�ͼ����
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setAxisLineStroke(new BasicStroke(1.0f));     // �������ߴ�ϸ
		rangeAxis.setAxisLinePaint(Color.BLACK);     // ����������ɫ
		rangeAxis.setTickMarkStroke(new BasicStroke(1.0f));     // ���������Ǵ�С
		rangeAxis.setTickMarkPaint(Color.BLACK);     // ������������ɫ
		rangeAxis.setTickLabelFont(new Font("����", Font.PLAIN, 12));    // �����������������
		rangeAxis.setLabelPaint(Color.BLACK);     // ���������������ɫ
		rangeAxis.setLabelAngle(Math.PI / 2);     // ���������������ת�Ƕ�
		plot.setRangeAxis(rangeAxis);
		
		//ȡ�õ�һ��ͼ��
		LegendTitle legend = lineChart.getLegend(0);
		//�޸�ͼ��������
		legend.setItemFont(new Font("����", Font.PLAIN, 12)); 
		
		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());		
		
		try {
			filename = ServletUtilities.saveChartAsPNG(lineChart, width, height, info, session);
			// ��image map д�뵽 PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, true);
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		pw.flush();

		return filename;
		
	}

}
