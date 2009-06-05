package com.sw.cms.util;

import java.math.BigDecimal;

public class Amount {
	

	/**
	 * ����double������ֵ���
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1,double v2){
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		return (a1.add(a2)).doubleValue();
	}
	
	public static double chu(double v1,double v2){
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		
		return (a1.divide(a2)).doubleValue();
	}
	
	public static double cheng(double v1,double v2){
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		
		return (a1.multiply(a2)).doubleValue();
	}
	
	/**
	 * ����float������ֵ���
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static float add(float v1,float v2){
		BigDecimal a1 = new BigDecimal(Float.toString(v1));
		BigDecimal a2 = new BigDecimal(Float.toString(v2));
		return (a1.add(a2)).floatValue();
	}	
	
	
	/**
	 * ����doubleҵ����ֵ���
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double subtract(double v1,double v2){
		BigDecimal a1 = new BigDecimal(Double.toString(v1));
		BigDecimal a2 = new BigDecimal(Double.toString(v2));
		return (a1.subtract(a2)).doubleValue();
	}
	
	
	/**
	 * ����float������ֵ���
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static float subtract(float v1,float v2){
		BigDecimal a1 = new BigDecimal(Float.toString(v1));
		BigDecimal a2 = new BigDecimal(Float.toString(v2));
		return (a1.subtract(a2)).floatValue();
	}	
	

}
