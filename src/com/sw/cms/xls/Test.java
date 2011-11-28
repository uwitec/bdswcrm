package com.sw.cms.xls;

import com.sw.cms.util.JMath;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double max = 0;
		double min = 0;
		double maxIndex = 0;
		double minIndex = 0;
		for(double i=1;i<999;i++){
			double x = new Double(JMath.round(i * (new Double(JMath.round(10/i)).doubleValue())));
			if(x>=max){
				max = x;
				maxIndex = i;
			}
			
			if(x<=min){
				min = x;
				minIndex = i;
			}
		}
		
		System.out.println(maxIndex + "------" + max);
		System.out.println(minIndex + "------" + min);
	}

}
