package com.sw.cms.util;

public class Constant {
	
	/**
	 * 每页记录数1
	 */
	public static int PAGE_SIZE = StaticParamDo.getPageSize(); 
	
	/**
	 * 每页记录数2
	 */
	public static int PAGE_SIZE2 = StaticParamDo.getPageSize2();
	
	/**
	 * 系统消息保留天数
	 */
	public static int MSG_EXPIRE_DAY = StaticParamDo.getMsgExpireDay();
	
	/**
	 * 系统限制用户数量，默认为0不限制，在init.properties中设置，
	 */
	public static int SYS_USER_NUMS = StaticParamDo.getUserNums();
}
