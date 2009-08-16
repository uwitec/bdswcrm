package com.sw.cms.jfreechar.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

/**
 * ����3D��ͼ
 * @author jinyanni
 *
 */

public class GenerateWebPieChart3D {

	public static String getPieChart3D(Map map, String title, int width, int height, HttpSession session, PrintWriter pw) {
		String filename = null;
		Font font = null;

		DefaultPieDataset data = new DefaultPieDataset();
		Iterator it = null;
		it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			data.setValue(String.valueOf(entry.getKey()), Double.valueOf(String.valueOf(entry.getValue())));
		}
		
		try {
			JFreeChart chart = ChartFactory.createPieChart(title, data, true, true, false);
			PiePlot plot = (PiePlot) chart.getPlot();
			plot.setNoDataMessage("��ѯ����Ϊ��!");
			plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0%")));
			plot.setCircular(true);
					
			//�趨����͸���ȣ�0-1.0֮�䣩
			plot.setBackgroundAlpha(0.9f);			
			//�趨ǰ��͸���ȣ�0-1.0֮�䣩
			plot.setForegroundAlpha(0.50f);
			
			// ͼ����ʾ�ٷֱ�:�Զ��巽ʽ�� {0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ����                      
			plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
					
			//ȡ��ͳ��ͼ��ĵ�һ��ͼ��
			LegendTitle legend = chart.getLegend(0);
			//�޸�ͼ��������
			legend.setItemFont(new Font("����", Font.PLAIN, 12)); 


			font = new Font("simsun", Font.PLAIN, 12);// ����ط�������ͳ��ͼ���������ʹ�С
			plot.setLabelFont(font);

			chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

			TextTitle tt = new TextTitle(title);
			font = new Font("����", Font.PLAIN, 14);// ����ط�������ͳ��ͼ���������ʹ�С
			tt.setFont(font);
			chart.setTitle(tt);

			chart.setBackgroundPaint(Color.white); // ͳ��ͼƬ�ĵ�ɫ

			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
			
			// ��image map д�뵽 PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, true);
			pw.flush();
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return filename;
	}

}
