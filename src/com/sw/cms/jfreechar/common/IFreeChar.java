package com.sw.cms.jfreechar.common;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

/**
 * 调用生成图形方法接口文件
 * @author jinyanni
 *
 */
public interface IFreeChar {

	/**
	 * 取得饼图
	 * @param title    标题
	 * @param session  HttpSession
	 * @param pw       PrintWriter
	 * @return
	 * @throws Exception
	 */
	public String getPieChart3D(String title, HttpSession session,PrintWriter pw) throws Exception;   

	
	/**
	 * 取得柱状图
	 * @param title    标题
	 * @param xName    X轴名称
	 * @param yName    Y轴名称
	 * @param session  HttpSession 
	 * @param pw       PrintWriter
	 * @return
	 * @throws Exception
	 */
	public String getBarChart3D(String title, String xName ,String yName, HttpSession session,PrintWriter pw) throws Exception;
	
	
	/**
	 * 取得折线图
	 * @param title    标题
	 * @param xName    X轴名称
	 * @param yName    Y轴名称
	 * @param session  HttpSession 
	 * @param pw       PrintWriter
	 * @return
	 * @throws Exception
	 */
	public String getLineChart(String title, String xName ,String yName, HttpSession session,PrintWriter pw) throws Exception; 

	
}
