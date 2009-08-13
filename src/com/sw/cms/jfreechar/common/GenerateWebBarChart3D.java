package com.sw.cms.jfreechar.common;

import java.awt.Font;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;

/**
 * 3D��״ͼ����ʾ
 * @author jinyanni
 *
 */

public class GenerateWebBarChart3D {

	public static String getBarChart3D(CategoryDataset dataset, String title, String xName, String yName, int width, int height, HttpSession session, PrintWriter pw) {

		String filename = null;
		Font font = null;

		JFreeChart chart = ChartFactory.createBarChart3D(title, xName, yName, dataset, PlotOrientation.VERTICAL, false, false, false);

		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

		TextTitle tt = new TextTitle(title);
		font = new Font("����", Font.CENTER_BASELINE, 20);// ����ط�������ͳ��ͼ���������ʹ�С
		tt.setFont(font);
		chart.setTitle(tt);

		chart.setBackgroundPaint(new java.awt.Color(244, 247, 251)); // ͳ��ͼƬ�ĵ�ɫ

		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
			// ��image map д�뵽 PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, true);
		} catch (IOException e) {
			System.out.println("Exception - " + e.toString());
			e.printStackTrace(System.out);
			filename = "public_error_500x300.png";
		}
		pw.flush();

		return filename;
	}

}
