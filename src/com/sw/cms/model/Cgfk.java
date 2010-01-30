package com.sw.cms.model;

import java.util.Date;

public class Cgfk {
	
	private String id;   //采购付款编号
	private String fk_date; //付款日期
	private String gysbh;//供应商编号
	private String fkzh; //付款账户
	private String jsr;  //经手人
	private double fkje; //付款金额
	private String remark; //备注
	private String state;  //状态	已保存、已提交（待审批）、审批通过、审批不通过、已付款
	private String is_yfk; //是否预付款
	private double yfk_ye; //预付款余额
	private String czr;    //操作人
	private Date cz_date;
	
	//删除关键字
	//在采购退货发生提交时如果是现金退货，需要将采购退货单ID写入
	//在发生采购退货现金退货时，需要将采购退货单ID写入
	private String delete_key = "";
	
	private String client_all_name;
	private String bank_no;
	private String kh_lxr;
	private String fax;
	private String tel;
	
	public String getFk_date() {
		return fk_date;
	}
	public void setFk_date(String fk_date) {
		this.fk_date = fk_date;
	}
	public double getFkje() {
		return fkje;
	}
	public void setFkje(double fkje) {
		this.fkje = fkje;
	}
	public String getFkzh() {
		return fkzh;
	}
	public void setFkzh(String fkzh) {
		this.fkzh = fkzh;
	}
	public String getGysbh() {
		return gysbh;
	}
	public void setGysbh(String gysbh) {
		this.gysbh = gysbh;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getIs_yfk() {
		return is_yfk;
	}
	public void setIs_yfk(String is_yfk) {
		this.is_yfk = is_yfk;
	}
	public double getYfk_ye() {
		return yfk_ye;
	}
	public void setYfk_ye(double yfk_ye) {
		this.yfk_ye = yfk_ye;
	}
	public String getDelete_key() {
		return delete_key;
	}
	public void setDelete_key(String delete_key) {
		this.delete_key = delete_key;
	}
	public Date getCz_date() {
		return cz_date;
	}
	public void setCz_date(Date czDate) {
		cz_date = czDate;
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
	public String getKh_lxr() {
		return kh_lxr;
	}
	public void setKh_lxr(String khLxr) {
		kh_lxr = khLxr;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

}
