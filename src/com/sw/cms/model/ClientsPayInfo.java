package com.sw.cms.model;

/**
 * 往来单位支付信息
 * @author jinyanni
 *
 */
public class ClientsPayInfo {
	
	private int id;
	private String client_id;
	private String client_all_name;
	private String bank_no;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String clientId) {
		client_id = clientId;
	}
	public String getClient_all_name() {
		return client_all_name;
	}
	public void setClient_all_name(String clientAllName) {
		client_all_name = clientAllName;
	}
	public String getBank_no() {
		return bank_no;
	}
	public void setBank_no(String bankNo) {
		bank_no = bankNo;
	}

}
