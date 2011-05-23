package com.sw.cms.model;

import java.sql.Timestamp;

/**
 * 商品销售流水
 * @author jinyanni
 *
 */
public class ProductSaleFlow {
	
	private int seq_id;        //流水号
	private String id;         //单据编号
	private String yw_type;    //业务类型
	private String client_name;//客户编号（名称）
	private String xsry;       //销售人员编号
	private String xsry_dept;  //销售人员部门编号
	private String cz_date;    //成交日期
	private String product_id; //商品编号
	private int nums;          //数量
	private double price;      //单价
	private double hjje;       //合计金额
	private double dwcb;       //单位成本
	private double cb;         //成本
	private double dwkhcb;     //单位考核成本
	private double khcb;       //考核成本
	private double dwygcb;     //单位预估成本
	private double ygcb;       //预估成本
	private double sd;         //税点
	private double bhsje;      //不含税金额
	private double gf;         //工分(比例点杀)
	private double ds;         //金额点杀
	private double basic_ratio;//基本提成比例
	private double out_ratio;  //超限提成比例
	private double lsxj;       //零售限价
	private String sfcytc;     //是否参与提成
	private Timestamp jy_time; //交易时间
	
	public int getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(int seqId) {
		seq_id = seqId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYw_type() {
		return yw_type;
	}
	public void setYw_type(String ywType) {
		yw_type = ywType;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String productId) {
		product_id = productId;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String clientName) {
		client_name = clientName;
	}
	public String getXsry() {
		return xsry;
	}
	public void setXsry(String xsry) {
		this.xsry = xsry;
	}
	public String getCz_date() {
		return cz_date;
	}
	public void setCz_date(String czDate) {
		cz_date = czDate;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getHjje() {
		return hjje;
	}
	public void setHjje(double hjje) {
		this.hjje = hjje;
	}
	public double getDwcb() {
		return dwcb;
	}
	public void setDwcb(double dwcb) {
		this.dwcb = dwcb;
	}
	public double getCb() {
		return cb;
	}
	public void setCb(double cb) {
		this.cb = cb;
	}
	public double getDwkhcb() {
		return dwkhcb;
	}
	public void setDwkhcb(double dwkhcb) {
		this.dwkhcb = dwkhcb;
	}
	public double getKhcb() {
		return khcb;
	}
	public void setKhcb(double khcb) {
		this.khcb = khcb;
	}
	public double getDwygcb() {
		return dwygcb;
	}
	public void setDwygcb(double dwygcb) {
		this.dwygcb = dwygcb;
	}
	public double getYgcb() {
		return ygcb;
	}
	public void setYgcb(double ygcb) {
		this.ygcb = ygcb;
	}
	public double getSd() {
		return sd;
	}
	public void setSd(double sd) {
		this.sd = sd;
	}
	public double getBhsje() {
		return bhsje;
	}
	public void setBhsje(double bhsje) {
		this.bhsje = bhsje;
	}
	public double getGf() {
		return gf;
	}
	public void setGf(double gf) {
		this.gf = gf;
	}
	public double getDs() {
		return ds;
	}
	public void setDs(double ds) {
		this.ds = ds;
	}
	public double getBasic_ratio() {
		return basic_ratio;
	}
	public void setBasic_ratio(double basicRatio) {
		basic_ratio = basicRatio;
	}
	public double getOut_ratio() {
		return out_ratio;
	}
	public void setOut_ratio(double outRatio) {
		out_ratio = outRatio;
	}
	public double getLsxj() {
		return lsxj;
	}
	public void setLsxj(double lsxj) {
		this.lsxj = lsxj;
	}
	public Timestamp getJy_time() {
		return jy_time;
	}
	public void setJy_time(Timestamp jyTime) {
		jy_time = jyTime;
	}
	public String getSfcytc() {
		return sfcytc;
	}
	public void setSfcytc(String sfcytc) {
		this.sfcytc = sfcytc;
	}
	public String getXsry_dept() {
		return xsry_dept;
	}
	public void setXsry_dept(String xsryDept) {
		xsry_dept = xsryDept;
	}
	
}
