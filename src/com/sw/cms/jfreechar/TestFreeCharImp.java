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
		if(width == 0 ){ width = 550;}   
		if(height == 0){ height = 400;} 
	}

	public String getBarChart3D(String title, String xName, String yName, HttpSession session, PrintWriter pw) throws Exception {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(20, "zzzzzzz", "xxxxxxxxx");
		dataset.addValue(70, "fffffff", "ccccccccc");
		dataset.addValue(20, "zzzzzzz", "ffmmmmmm");
		dataset.addValue(70, "hhhhhhh", "2222mmm");
		
		
		dataset.addValue(50, "zzzzzzz", "ttttttttt");
		dataset.addValue(10, "fffffff", "mmmmmmmm");
		dataset.addValue(50, "zzzzzzz", "ssmmmmm");
		dataset.addValue(10, "hhhhhhh", "jjmmmmm");
		
		return GenerateWebBarChart3D.getBarChart3D(dataset, title, xName, yName, width, height, session, pw);
	}

	public String getPieChart3D(String title, HttpSession session, PrintWriter pw) throws Exception {
		 Map map = new HashMap();
		 map.put("test1", "30");
		 map.put("test2", "20");
		 map.put("test3", "10");
		 map.put("test4", "80");
		 map.put("test5", "90");
		 map.put("test6", "70");
		 
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
