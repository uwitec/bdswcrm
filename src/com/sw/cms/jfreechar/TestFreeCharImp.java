package com.sw.cms.jfreechar;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jfree.data.category.DefaultCategoryDataset;

import com.sw.cms.jfreechar.common.GenerateWebBarChart3D;
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
		dataset.addValue(60, "�Ϻ�", "ƻ��");
		dataset.addValue(100, "����", "ƻ��");
		
		dataset.addValue(279, "����", "����");
		dataset.addValue(478, "�Ϻ�", "����");
		dataset.addValue(221, "����", "����");
		
		dataset.addValue(568, "����", "����");
		dataset.addValue(12, "�Ϻ�", "����");
		dataset.addValue(234, "����", "����");
		
		dataset.addValue(543, "����", "�㽶");
		dataset.addValue(890, "�Ϻ�", "�㽶");
		dataset.addValue(32, "����", "�㽶");
		
		dataset.addValue(453, "����", "��֦");
		dataset.addValue(36, "�Ϻ�", "��֦");
		dataset.addValue(132, "����", "��֦");
		
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
