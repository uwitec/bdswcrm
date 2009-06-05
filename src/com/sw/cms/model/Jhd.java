package com.sw.cms.model;


public class Jhd {
	
	private String id;  //编号
	private String gysbh; //供应商编号
	private String cg_date; //采购日期
	private String state;   //进货单状态
	private String fzr;     //负责人
	private String con;     //条件条款（无效）
	private String ms;      //备注
	private double shuil = 0;
	private double tzje = 0;
	private double total = 0;
	private double fkje = 0;
	private String fklx;	
	private String gysmc;
	private String czr;	
	private String fkzh;	
	private String yfrq;
	
	private String fkfs; //付款方式：现金，账期
	private double yfje; //预付金额
	private String store_id;//入库仓库
	private int zq;   //账期天数
	private String th_flag;
	
	private double sjcjje; //采购订单实际成交金额
	
	
	public String getCg_date() {
		return cg_date;
	}
	public void setCg_date(String cg_date) {
		this.cg_date = cg_date;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	public String getFzr() {
		return fzr;
	}
	public void setFzr(String fzr) {
		this.fzr = fzr;
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
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getGysmc() {
		return gysmc;
	}
	public void setGysmc(String gysmc) {
		this.gysmc = gysmc;
	}
	public double getShuil() {
		return shuil;
	}
	public void setShuil(double shuil) {
		this.shuil = shuil;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getTzje() {
		return tzje;
	}
	public void setTzje(double tzje) {
		this.tzje = tzje;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public double getFkje() {
		return fkje;
	}
	public void setFkje(double fkje) {
		this.fkje = fkje;
	}
	public String getFklx() {
		return fklx;
	}
	public void setFklx(String fklx) {
		this.fklx = fklx;
	}
	public String getFkzh() {
		return fkzh;
	}
	public void setFkzh(String fkzh) {
		this.fkzh = fkzh;
	}
	public String getYfrq() {
		return yfrq;
	}
	public void setYfrq(String yfrq) {
		this.yfrq = yfrq;
	}
	public String getFkfs() {
		return fkfs;
	}
	public void setFkfs(String fkfs) {
		this.fkfs = fkfs;
	}
	public double getYfje() {
		return yfje;
	}
	public void setYfje(double yfje) {
		this.yfje = yfje;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public int getZq() {
		return zq;
	}
	public void setZq(int zq) {
		this.zq = zq;
	}
	public String getTh_flag() {
		return th_flag;
	}
	public void setTh_flag(String th_flag) {
		this.th_flag = th_flag;
	}
	public double getSjcjje() {
		return sjcjje;
	}
	public void setSjcjje(double sjcjje) {
		this.sjcjje = sjcjje;
	}


}
