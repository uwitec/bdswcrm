package com.sw.cms.jfreechar.common;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

/**
 * ��������ͼ�η����ӿ��ļ�
 * @author jinyanni
 *
 */
public interface IFreeChar {

	/**
	 * ȡ�ñ�ͼ
	 * @param title    ����
	 * @param session  HttpSession
	 * @param pw       PrintWriter
	 * @return
	 * @throws Exception
	 */
	public String getPieChart3D(String title, HttpSession session,PrintWriter pw) throws Exception;   

	
	/**
	 * ȡ����״ͼ
	 * @param title    ����
	 * @param xName    X������
	 * @param yName    Y������
	 * @param session  HttpSession 
	 * @param pw       PrintWriter
	 * @return
	 * @throws Exception
	 */
	public String getBarChart3D(String title, String xName ,String yName, HttpSession session,PrintWriter pw) throws Exception;
	
	
	/**
	 * ȡ������ͼ
	 * @param title    ����
	 * @param xName    X������
	 * @param yName    Y������
	 * @param session  HttpSession 
	 * @param pw       PrintWriter
	 * @return
	 * @throws Exception
	 */
	public String getLineChart(String title, String xName ,String yName, HttpSession session,PrintWriter pw) throws Exception; 

	
}
