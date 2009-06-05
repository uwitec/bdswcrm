package com.sw.cms.source;

public class SysSource {
	
	private String page_size = "0";
	private String page_size2 = "0";
	private String expireDay = "7";
	
	/**
	 * 返回配置文件中的总页数
	 * @return
	 */
	public int getSysPageSize() {
		return new Integer(page_size).intValue();
	}
	
	/**
	 * 返回配置文件中配置的总页数2
	 * @return
	 */
	public int getSysPageSize2(){
		return new Integer(page_size2).intValue();
	}
	
	
	/**
	 * 返回配置文件中配置的消息保留天数
	 * @return
	 */
	public int getSysMsgExpireDay(){
		return new Integer(expireDay).intValue();
	}
	

	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}

	public String getPage_size() {
		return page_size;
	}


	public String getPage_size2() {
		return page_size2;
	}


	public void setPage_size2(String page_size2) {
		this.page_size2 = page_size2;
	}

	public String getExpireDay() {
		return expireDay;
	}

	public void setExpireDay(String expireDay) {
		this.expireDay = expireDay;
	}

}
