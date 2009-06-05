package com.sw.cms.model;

public class Fysq {

	private String id;         //编号
	private String creatdate;  //申请日期
	
	private String ywy_id;     //业务员编号
	private String ywy_name;   //业务员姓名
	
	private String xgkh;       //相关客户
	private String fy_type;    //费用类型
	
	private double je;         //金额
	private String strJe;      //格式化后的金额(列表页面显示，例如：2,335.66)
	private String strJe2;     //格式化后的金额（数字,例如：2335.66）
	
	private String fklx;       //付款类型
	
	private String zfzh;       //支付账户编号
	private String zfzh_name;  //支付账户名称
	
	private String remark;     //详细说明
	private String state;      //状态 （保存、提交、审批通过、审批不通过、已支付）
	
	private String czr;        //操作人编号
	private String czr_name;   //操作人姓名
	private String cz_date;    //操作时间
	
	private String spr;        //审批人
	private String spr_name;   //审批人姓名
	private String sp_date;    //审批时间
	
	public String getCreatdate() {
		return creatdate;
	}
	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}
	public String getFklx() {
		return fklx;
	}
	public void setFklx(String fklx) {
		this.fklx = fklx;
	}
	public String getFy_type() {
		return fy_type;
	}
	public void setFy_type(String fy_type) {
		this.fy_type = fy_type;
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
	public String getXgkh() {
		return xgkh;
	}
	public void setXgkh(String xgkh) {
		this.xgkh = xgkh;
	}
	public String getYwy_id() {
		return ywy_id;
	}
	public void setYwy_id(String ywy_id) {
		this.ywy_id = ywy_id;
	}
	public String getZfzh() {
		return zfzh;
	}
	public void setZfzh(String zfzh) {
		this.zfzh = zfzh;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getCzr_name() {
		return czr_name;
	}
	public void setCzr_name(String czr_name) {
		this.czr_name = czr_name;
	}
	public String getYwy_name() {
		return ywy_name;
	}
	public void setYwy_name(String ywy_name) {
		this.ywy_name = ywy_name;
	}
	public String getZfzh_name() {
		return zfzh_name;
	}
	public void setZfzh_name(String zfzh_name) {
		this.zfzh_name = zfzh_name;
	}
	public String getStrJe() {
		return strJe;
	}
	public void setStrJe(String strJe) {
		this.strJe = strJe;
	}
	public String getStrJe2() {
		return strJe2;
	}
	public void setStrJe2(String strJe2) {
		this.strJe2 = strJe2;
	}
	public String getSpr() {
		return spr;
	}
	public void setSpr(String spr) {
		this.spr = spr;
	}
	public String getSpr_name() {
		return spr_name;
	}
	public void setSpr_name(String spr_name) {
		this.spr_name = spr_name;
	}
	public String getCz_date() {
		return cz_date;
	}
	public void setCz_date(String cz_date) {
		this.cz_date = cz_date;
	}
	public String getSp_date() {
		return sp_date;
	}
	public void setSp_date(String sp_date) {
		this.sp_date = sp_date;
	}
	
}
