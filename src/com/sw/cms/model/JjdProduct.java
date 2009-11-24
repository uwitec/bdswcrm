package com.sw.cms.model;

/**
 * 接件单商品
 * @author Administrator
 *
 */
public class JjdProduct 
{
	  private int      id       ;    //流水号
	  private String   jjd_id      ;//接件单ID
	  private String   product_id  ;//接件单产品ID
	  private String   product_name ;//接件单产品名称
	  private String   product_xh  ;//接件单产品规格
	  private String   qz_serial_num ;//接件单产品序列号
	  private int      nums;          //数量
	  private String   remark       ;//备注
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
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
 
}
