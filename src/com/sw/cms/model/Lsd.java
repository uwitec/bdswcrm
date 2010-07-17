package com.sw.cms.model;

public class Lsd {
	
	private String id; //编号
	private String creatdate; //日期
	private String xsry;//经手人
	private String fkfs;//付款方式
	private String pos_id;  //POS机编号
	private String state;//状态
	private String store_id;//库房
	
	private String client_name;//客户名称
	private String lxr;//联系人
	private String lxdh;//电话
	private String mobile;//手机
	private String mail;//邮件
	private String msn;//MSN/QQ
	private String address;//地址
	private String p_code;//邮编
	

	private String fplx;//发票类型
	private String khhzh;//开户行账号
	private String sh;//税号
	private String fpxx;//发票信息摘要

	private double yhje;//优惠金额
	private double lsdje;//合计金额
	private double lsdcbj;//零售单成本价
	private double skje;//本次实收金额
	private String skzh;//收款账号
	
	private String ms;//描术信息	
	private String czr;//操作人
	
	private String has_yushk; //是否存在预收款
	private String yushk_id;  //预收款ID
	private double yushkje;// 预收金额
	
	private String kp_mc;  //开票名称
	private String kp_address;  //开票地址
	private String kp_dh;  //开票电话
	
	private double lsdkhcb;  //零售单考核成本
	
	private String sp_state = "0";   //审批状态（0：自动审批；1：需审批；2：待审批；3：审批通过；4：审批不通过）
	private String spr;        //审批人
	private String sp_date;    //审批时间
	private String sp_opinion; //审批意见

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getCreatdate() {
		return creatdate;
	}

	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}

	public String getCzr() {
		return czr;
	}

	public void setCzr(String czr) {
		this.czr = czr;
	}

	public String getFkfs() {
		return fkfs;
	}

	public void setFkfs(String fkfs) {
		this.fkfs = fkfs;
	}

	public String getFplx() {
		return fplx;
	}

	public void setFplx(String fplx) {
		this.fplx = fplx;
	}

	public String getFpxx() {
		return fpxx;
	}

	public void setFpxx(String fpxx) {
		this.fpxx = fpxx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKhhzh() {
		return khhzh;
	}

	public void setKhhzh(String khhzh) {
		this.khhzh = khhzh;
	}

	public double getLsdcbj() {
		return lsdcbj;
	}

	public void setLsdcbj(double lsdcbj) {
		this.lsdcbj = lsdcbj;
	}

	public double getLsdje() {
		return lsdje;
	}

	public void setLsdje(double lsdje) {
		this.lsdje = lsdje;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getLxr() {
		return lxr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
	}

	public String getSh() {
		return sh;
	}

	public void setSh(String sh) {
		this.sh = sh;
	}

	public double getSkje() {
		return skje;
	}

	public void setSkje(double skje) {
		this.skje = skje;
	}

	public String getSkzh() {
		return skzh;
	}

	public void setSkzh(String skzh) {
		this.skzh = skzh;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getXsry() {
		return xsry;
	}

	public void setXsry(String xsry) {
		this.xsry = xsry;
	}

	public double getYhje() {
		return yhje;
	}

	public void setYhje(double yhje) {
		this.yhje = yhje;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getHas_yushk() {
		return has_yushk;
	}

	public void setHas_yushk(String has_yushk) {
		this.has_yushk = has_yushk;
	}

	public String getYushk_id() {
		return yushk_id;
	}

	public void setYushk_id(String yushk_id) {
		this.yushk_id = yushk_id;
	}

	public double getYushkje() {
		return yushkje;
	}

	public void setYushkje(double yushkje) {
		this.yushkje = yushkje;
	}

	public String getKp_dh() {
		return kp_dh;
	}

	public void setKp_dh(String kp_dh) {
		this.kp_dh = kp_dh;
	}

	public String getKp_address() {
		return kp_address;
	}

	public void setKp_address(String kp_address) {
		this.kp_address = kp_address;
	}

	public String getKp_mc() {
		return kp_mc;
	}

	public void setKp_mc(String kp_mc) {
		this.kp_mc = kp_mc;
	}

	public double getLsdkhcb() {
		return lsdkhcb;
	}

	public void setLsdkhcb(double lsdkhcb) {
		this.lsdkhcb = lsdkhcb;
	}

	public String getSp_date() {
		return sp_date;
	}

	public void setSp_date(String sp_date) {
		this.sp_date = sp_date;
	}

	public String getSp_opinion() {
		return sp_opinion;
	}

	public void setSp_opinion(String sp_opinion) {
		this.sp_opinion = sp_opinion;
	}

	public String getSp_state() {
		return sp_state;
	}

	public void setSp_state(String sp_state) {
		this.sp_state = sp_state;
	}

	public String getSpr() {
		return spr;
	}

	public void setSpr(String spr) {
		this.spr = spr;
	}

	public String getPos_id() {
		return pos_id;
	}

	public void setPos_id(String pos_id) {
		this.pos_id = pos_id;
	}
	
	
}
