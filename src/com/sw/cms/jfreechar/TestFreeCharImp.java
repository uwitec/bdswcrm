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
 * 测试一个生成图表操作
 * @author jinyanni
 *
 */
public class TestFreeCharImp implements IFreeChar {
	
	private int width;     //图形宽度
	private int height;    //图形高度
	
	
	public TestFreeCharImp(){
		if(width == 0 ){ width = 380;}   
		if(height == 0){ height = 270;} 
	}

	public String getBarChart3D(String title, String xName, String yName, HttpSession session, PrintWriter pw) throws Exception {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		/*
		添加数据时
		参数1 － 值。
		参数2 － 行key
		参数3 － 列key
		*/
		dataset.addValue(60, "上海", "苹果");
		dataset.addValue(100, "广州", "苹果");
		
		dataset.addValue(279, "北京", "梨子");
		dataset.addValue(478, "上海", "梨子");
		dataset.addValue(221, "广州", "梨子");
		
		dataset.addValue(568, "北京", "葡萄");
		dataset.addValue(12, "上海", "葡萄");
		dataset.addValue(234, "广州", "葡萄");
		
		dataset.addValue(543, "北京", "香蕉");
		dataset.addValue(890, "上海", "香蕉");
		dataset.addValue(32, "广州", "香蕉");
		
		dataset.addValue(453, "北京", "荔枝");
		dataset.addValue(36, "上海", "荔枝");
		dataset.addValue(132, "广州", "荔枝");
		
		return GenerateWebBarChart3D.getBarChart3D(dataset, title, xName, yName, width, height, session, pw);
	}

	public String getPieChart3D(String title, HttpSession session, PrintWriter pw) throws Exception {
		 Map map = new HashMap();
		 map.put("北京", "30");
		 map.put("上海", "20");
		 map.put("广州", "10");
		 map.put("石家庄", "80");
		 
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
