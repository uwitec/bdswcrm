package com.sw.cms.jfreechar;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jfree.data.category.DefaultCategoryDataset;

import com.sw.cms.jfreechar.common.GenerateWebBarChart3D;
import com.sw.cms.jfreechar.common.GenerateWebLineChart;
import com.sw.cms.jfreechar.common.GenerateWebPieChart3D;
import com.sw.cms.jfreechar.common.IFreeChar;


/**
 * ����һ������ͼ�����
 * @author jinyanni
 *
 */
public class TestFreeCharImp implements IFreeChar {
	
	private int width;     //ͼ�ο��
	private int height;    //ͼ�θ߶�
	
	
	public TestFreeCharImp(){
		if(width == 0 ){ width = 380;}   
		if(height == 0){ height = 270;} 
	}

	public String getBarChart3D(String title, String xName, String yName, HttpSession session, PrintWriter pw) throws Exception {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		/*
		�������ʱ
		����1 �� ֵ��
		����2 �� ��key
		����3 �� ��key
		*/
		dataset.addValue(60, "����", "2009-01");
		dataset.addValue(80, "�Ϻ�", "2009-01");
		dataset.addValue(100, "����", "2009-01");
		
		dataset.addValue(279, "����", "2009-02");
		dataset.addValue(478, "�Ϻ�", "2009-02");
		dataset.addValue(221, "����", "2009-02");
		
		dataset.addValue(568, "����", "2009-03");
		dataset.addValue(12, "�Ϻ�", "2009-03");
		dataset.addValue(234, "����", "2009-03");
		
		dataset.addValue(543, "����", "2009-04");
		dataset.addValue(890, "�Ϻ�", "2009-04");
		dataset.addValue(32, "����", "2009-04");
		
		dataset.addValue(453, "����", "2009-05");
		dataset.addValue(36, "�Ϻ�", "2009-05");
		dataset.addValue(132, "����", "2009-05");
		
		return GenerateWebBarChart3D.getBarChart3D(dataset, title, xName, yName, width, height, session, pw);
	}

	public String getPieChart3D(String title, HttpSession session, PrintWriter pw) throws Exception {
		 Map map = new HashMap();
		 map.put("����", "30");
		 map.put("�Ϻ�", "20");
		 map.put("����", "10");
		 map.put("ʯ��ׯ", "80");
		 
		return GenerateWebPieChart3D.getPieChart3D(map, title, width, height, session, pw);
	}
	
	
	public String getLineChart(String title, String xName ,String yName, HttpSession session,PrintWriter pw) throws Exception{

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		/*
		�������ʱ
		����1 �� ֵ��
		����2 �� ��key
		����3 �� ��key
		*/
		dataset.addValue(350, "�Ϻ�", "2009-06");
		dataset.addValue(460, "�Ϻ�", "2009-07");
		dataset.addValue(123, "�Ϻ�", "2009-08");
		dataset.addValue(321, "�Ϻ�", "2009-09");
		dataset.addValue(579, "�Ϻ�", "2009-10");
		
		dataset.addValue(20, "����", "2009-06");
		dataset.addValue(345, "����", "2009-07");
		dataset.addValue(121, "����", "2009-08");
		dataset.addValue(780, "����", "2009-09");
		dataset.addValue(670, "����", "2009-10");
		
		dataset.addValue(60, "����", "2009-06");
		dataset.addValue(100, "����", "2009-07");
		dataset.addValue(45, "����", "2009-08");
		dataset.addValue(23, "����", "2009-09");
		dataset.addValue(8, "����", "2009-10");
		
		return GenerateWebLineChart.getLineChart(dataset, title, xName, yName, width, height, session, pw);
		
	}
	

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
