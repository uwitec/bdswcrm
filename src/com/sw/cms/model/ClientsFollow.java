package com.sw.cms.model;

/**
 * 往来单位跟进记录
 * 
 * @author Administrator
 * 
 */
public class ClientsFollow 
{
	private int id;             //流水号

	private String zt;          //主题

	private String clients_id; //客户

	private String linkman_id; //联系人

	private String lxlx;      //联系类型 （比如 电话 ，登门拜访）

	private String lxdate;  //联系日期

	private String cjdate;  //创建日期

	private String cjr;    //创建人

	private String nextdate; //下次联系时间

	private String remark; //备注

	public String getCjdate() {
		return cjdate;
	}

	public void setCjdate(String cjdate) {
		this.cjdate = cjdate;
	}

	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public String getClients_id() {
		return clients_id;
	}

	public void setClients_id(String clients_id) {
		this.clients_id = clients_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLinkman_id() {
		return linkman_id;
	}

	public void setLinkman_id(String linkman_id) {
		this.linkman_id = linkman_id;
	}

	public String getLxdate() {
		return lxdate;
	}

	public void setLxdate(String lxdate) {
		this.lxdate = lxdate;
	}

	public String getLxlx() {
		return lxlx;
	}

	public void setLxlx(String lxlx) {
		this.lxlx = lxlx;
	}

	public String getNextdate() {
		return nextdate;
	}

	public void setNextdate(String nextdate) {
		this.nextdate = nextdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
}
