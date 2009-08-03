package com.sw.cms.model;

/**
 * 存货调价
 * @author liyt
 *
 */
public class ChtjDesc {
	
	private String chtj_id;      //存货调价编号
	private String product_id;   //产品编号
	private String product_name; //产品名称
	private String product_xh;   //产口规格
	private double ysjg;         //原始价格
	private double tzjg;         //调整后价格
	private int nums;            //调价时库存数量
	private String remark;       //备注
	
	public String getChtj_id() {
		return chtj_id;
	}
	public void setChtj_id(String chtj_id) {
		this.chtj_id = chtj_id;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getTzjg() {
		return tzjg;
	}
	public void setTzjg(double tzjg) {
		this.tzjg = tzjg;
	}
	public double getYsjg() {
		return ysjg;
	}
	public void setYsjg(double ysjg) {
		this.ysjg = ysjg;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}

}
