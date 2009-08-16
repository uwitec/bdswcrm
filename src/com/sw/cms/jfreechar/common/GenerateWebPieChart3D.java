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
			JFreeChart chart = ChartFactory.createPieChart(title, data, true, true, false);
			PiePlot plot = (PiePlot) chart.getPlot();
			plot.setNoDataMessage("查询数据为空!");
			plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0%")));
			plot.setCircular(true);
					
			//设定背景透明度（0-1.0之间）
			plot.setBackgroundAlpha(0.9f);			
			//设定前景透明度（0-1.0之间）
			plot.setForegroundAlpha(0.50f);
			
			// 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例                      
			plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
					
			//取得统计图标的第一个图例
			LegendTitle legend = chart.getLegend(0);
			//修改图例的字体
			legend.setItemFont(new Font("宋体", Font.PLAIN, 12)); 


			font = new Font("simsun", Font.PLAIN, 12);// 这个地方是设置统计图标题的字体和大小
			plot.setLabelFont(font);

			chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

			TextTitle tt = new TextTitle(title);
			font = new Font("宋体", Font.PLAIN, 14);// 这个地方是设置统计图标题的字体和大小
			tt.setFont(font);
			chart.setTitle(tt);

			chart.setBackgroundPaint(Color.white); // 统计图片的底色

			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
			
			// 把image map 写入到 PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, true);
			pw.flush();
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return filename;
	}

}
