package com.sw.cms.model;

public class WxrkdProduct 
{
	 private int id  ;//流水号
     private String wxrkd_id ;//维修入库单ID
     private String product_id; //产品ID 
     private String  product_name  ;      //产品名称
      
     private String product_xh   ;   //产品型号 
     private String qz_serial_num  ;//产品序列号
     private String remark ;//产品备注    
     private String bxaddress       ;//报修所在地
     private String bxstate         ; //保修产品状态
     private String has_fj          ;//是否有附件
     private String  fj     ;//附件  
     private String qtfj     ;  //其他附件  
     private String bj     ; //备件   
     private String gzfx   ;  //故障分析   
     private String pcgc   ;  //排除过程    
	public String getBj() {
		return bj;
	}
	public void setBj(String bj) {
		this.bj = bj;
	}
	public String getBxaddress() {
		return bxaddress;
	}
	public void setBxaddress(String bxaddress) {
		this.bxaddress = bxaddress;
	}
	public String getBxstate() {
		return bxstate;
	}
	public void setBxstate(String bxstate) {
		this.bxstate = bxstate;
	}
	public String getFj() {
		return fj;
	}
	public void setFj(String fj) {
		this.fj = fj;
	}
	public String getGzfx() {
		return gzfx;
	}
	public void setGzfx(String gzfx) {
		this.gzfx = gzfx;
	}
	public String getHas_fj() {
		return has_fj;
	}
	public void setHas_fj(String has_fj) {
		this.has_fj = has_fj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPcgc() {
		return pcgc;
	}
	public void setPcgc(String pcgc) {
		this.pcgc = pcgc;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_xh() {
		return product_xh;
	}
	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}
	public String getQtfj() {
		return qtfj;
	}
	public void setQtfj(String qtfj) {
		this.qtfj = qtfj;
	}
	public String getQz_serial_num() {
		return qz_serial_num;
	}
	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWxrkd_id() {
		return wxrkd_id;
	}
	public void setWxrkd_id(String wxrkd_id) {
		this.wxrkd_id = wxrkd_id;
	}
}
