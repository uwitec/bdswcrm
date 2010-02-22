package com.sw.cms.model;

/**
 * 接件单商品
 * @author Administrator
 *
 */
public class JjdProduct 
{
	  private int  id  ;
	  private String  jjd_id ;
	  private String  product_id ;
	  private String  product_name ;
	  private String  product_xh ;
	  private String  qz_serial_num ;
	  private String  remark ;
	  private int     nums ;
	  private String  stord_id ;
	  private String  storestate ;
	  private String  cpfj ;
	  private int     fxts ;
	  private String  qz_flag;
	public String getCpfj() {
		return cpfj;
	}
	public void setCpfj(String cpfj) {
		this.cpfj = cpfj;
	}
	public int getFxts() {
		return fxts;
	}
	public void setFxts(int fxts) {
		this.fxts = fxts;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJjd_id() {
		return jjd_id;
	}
	public void setJjd_id(String jjd_id) {
		this.jjd_id = jjd_id;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
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
	public String getQz_flag() {
		return qz_flag;
	}
	public void setQz_flag(String qz_flag) {
		this.qz_flag = qz_flag;
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
	public String getStord_id() {
		return stord_id;
	}
	public void setStord_id(String stord_id) {
		this.stord_id = stord_id;
	}
	public String getStorestate() {
		return storestate;
	}
	public void setStorestate(String storestate) {
		this.storestate = storestate;
	}
	 
}
