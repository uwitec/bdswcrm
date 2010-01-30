package com.sw.cms.model;

public class Clients {
	
	private String id;
	private String name;
	private String lxr;
	private String lxdh;
	private String mobile;
	private String address;
	private String p_code;
	private String mail;
	private String msn;
	private String qq;
	private String zq;
	private double xe;	
	private String remark;
	private String ygs;
	private String gsxz;
	private String gzdh;//座机
	private String cz; //传真
	private String comaddress;//网址
	
	private String client_type;  //客户类型
	private String khjl;         //客户经理
	
	private String china_py;     //汉语拼音缩写
	
	private String kp_name;     //开票信息--单位全称
	private String kp_address;  //开票信息--地址
	private String kp_tel;      //开票信息--电话
	private String kp_khhzh;    //开票信息--开户行账号
	private String kp_sh;       //开票信息--税号
	
	private int cg_zq;          //采购账期
	private double cg_xe;       //采购限额
	
	public String getChina_py() {
		return china_py;
	}
	public void setChina_py(String china_py) {
		this.china_py = china_py;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGsxz() {
		return gsxz;
	}
	public void setGsxz(String gsxz) {
		this.gsxz = gsxz;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getP_code() {
		return p_code;
	}
	public void setP_code(String p_code) {
		this.p_code = p_code;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getXe() {
		return xe;
	}
	public void setXe(double xe) {
		this.xe = xe;
	}
	public String getYgs() {
		return ygs;
	}
	public void setYgs(String ygs) {
		this.ygs = ygs;
	}
	public String getZq() {
		return zq;
	}
	public void setZq(String zq) {
		this.zq = zq;
	}
	public String getClient_type() {
		return client_type;
	}
	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}
	public String getKhjl() {
		return khjl;
	}
	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}
	public String getComaddress() {
		return comaddress;
	}
	public void setComaddress(String comaddress) {
		this.comaddress = comaddress;
	}
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	public String getGzdh() {
		return gzdh;
	}
	public void setGzdh(String gzdh) {
		this.gzdh = gzdh;
	}
	public String getKp_address() {
		return kp_address;
	}
	public void setKp_address(String kp_address) {
		this.kp_address = kp_address;
	}
	public String getKp_khhzh() {
		return kp_khhzh;
	}
	public void setKp_khhzh(String kp_khhzh) {
		this.kp_khhzh = kp_khhzh;
	}
	public String getKp_name() {
		return kp_name;
	}
	public void setKp_name(String kp_name) {
		this.kp_name = kp_name;
	}
	public String getKp_sh() {
		return kp_sh;
	}
	public void setKp_sh(String kp_sh) {
		this.kp_sh = kp_sh;
	}
	public String getKp_tel() {
		return kp_tel;
	}
	public void setKp_tel(String kp_tel) {
		this.kp_tel = kp_tel;
	}
	public int getCg_zq() {
		return cg_zq;
	}
	public void setCg_zq(int cgZq) {
		cg_zq = cgZq;
	}
	public double getCg_xe() {
		return cg_xe;
	}
	public void setCg_xe(double cgXe) {
		cg_xe = cgXe;
	}
	
}
