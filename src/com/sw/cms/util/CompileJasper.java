package com.sw.cms.util;

import net.sf.jasperreports.engine.JasperCompileManager;

public class CompileJasper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		JasperCompileManager.compileReportToFile("D:/lsdTemplate.jrxml","D:/lsdTemplate.jasper");
		}catch(Exception e){
			System.out.println(e);
		}
	}

}
