package com.sw.cms.jfreechar.common;

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
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

/**
 * 生成3D饼图
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
			JFreeChart chart = ChartFactory.createPieChart3D(title, data, false, false, false);
			PiePlot plot = (PiePlot) chart.getPlot();
			plot.setNoDataMessage("查询数据为空!");
			plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
			plot.setForegroundAlpha(0.5f);
			plot.setCircular(false);

			font = new Font("simsun", Font.PLAIN, 12);// 这个地方是设置统计图标题的字体和大小
			plot.setLabelFont(font);

			chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

			TextTitle tt = new TextTitle(title);
			font = new Font("黑体", Font.CENTER_BASELINE, 20);// 这个地方是设置统计图标题的字体和大小
			tt.setFont(font);
			chart.setTitle(tt);

			chart.setBackgroundPaint(new java.awt.Color(244, 247, 251)); // 统计图片的底色

			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
			
			// 把image map 写入到 PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, true);
			pw.flush();
			
		} catch (Exception e) {
			System.out.println("Exception - " + e.toString());
			e.printStackTrace(System.out);
			filename = "public_error_500x300.png";
		}
		return filename;
	}

}
