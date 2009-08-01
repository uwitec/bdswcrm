package com.sw.cms.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class JMath {

	public static String round(float v) {
		String temp = "#0.0000";
		DecimalFormat df = new DecimalFormat(temp);
		return (df.format(v));
	}
	
	public static String round(float v, int scale) {
		String temp = "#,##0.0000";
//		for (int i = 0; i < scale; i++) {
//			temp += "0";
//		}
		DecimalFormat df = new DecimalFormat(temp);
		return (df.format(v));
	}
	
	
	public static String round(double v) {
		String temp = "#0.0000";
		DecimalFormat df = new DecimalFormat(temp);
		return (df.format(v));
	}
	
	public static String round(double v, int scale) {
		String temp = "#,##0.0000";
//		for (int i = 0; i < scale; i++) {
//			temp += "0";
//		}
		DecimalFormat df = new DecimalFormat(temp);
		return (df.format(v));
	}		

	public static String percent(int p1, int p2) throws Exception {
		String str;
		if (p2 == 0) {
			str = "0.00";
		} else {
			double p3 = ((double) p1 / (double) p2) * 100;
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			str = nf.format(p3);
		}
		return str;
	}
	
	public static String percent(double p1, double p2) throws Exception {
		String str;
		if (p2 == 0) {
			str = "0.00";
		} else {
			double p3 = ((double) p1 / (double) p2) * 100;
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			str = nf.format(p3);
		}
		return str + "%";
	}
}
