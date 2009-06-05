package com.sw.cms.model;

/**
 * 盘点盈亏表(账务变化表)
 * @author liyt
 *
 */
public class KcpdYkTbl {
	
	private String id;          //盈亏编号
	private double je;          //盈亏金额
	private String kcpd_id;     //对应盘点编号
	private String type;        //类型(1、商品报溢收入；2、商品报损支出)
	private String remark;      //备注
	private String cz_date;     //操作时间
	private String czr;         //操作人
	private String jsr;         //经手人
	private String fs_date;     //发生日期
	
	public String getCz_date() {
		return cz_date;
	}
	public void setCz_date(String cz_date) {
		this.cz_date = cz_date;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getJe() {
		return je;
	}
	public void setJe(double je) {
		this.je = je;
	}
	public String getKcpd_id() {
		return kcpd_id;
	}
	public void setKcpd_id(String kcpd_id) {
		this.kcpd_id = kcpd_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFs_date() {
		return fs_date;
	}
	public void setFs_date(String fs_date) {
		this.fs_date = fs_date;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}

}
