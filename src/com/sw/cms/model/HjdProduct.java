package com.sw.cms.model;

/**
 * 换件单产品信息
 * 2010-02-24 增加，有以下信息：编号、换件单ID、产品ID、产品名称、产品型号、旧产品序列号、新产品序列号、产品备注
 *           id    hjd_id    product_id  product_name product_xh oqz_serial_num  nqz_serial_num  product_remark 
 */
public class HjdProduct {
	private int id; // 流水号

	private String hjd_id; // 换件单ID

	private String product_id; // 产品ID

	private String product_name; // 产品名称	

	private String product_xh; // 产品型号

	private String oqz_serial_num; // 旧产品序列号

	private String product_remark; // 产品备注

	private String nqz_serial_num; // 新产品序列号


	public String getHjd_id() {
		return hjd_id;
	}

	public void setHjd_id(String hjd_id) {
		this.hjd_id = hjd_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getProduct_remark() {
		return product_remark;
	}

	public void setProduct_remark(String product_remark) {
		this.product_remark = product_remark;
	}

	public String getOqz_serial_num() {
		return oqz_serial_num;
	}

	public void setOqz_serial_num(String oqz_serial_num) {
		this.oqz_serial_num = oqz_serial_num;
	}

	public String getNqz_serial_num() {
		return nqz_serial_num;
	}

	public void setNqz_serial_num(String nqz_serial_num) {
		this.nqz_serial_num = nqz_serial_num;
	}
	
	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

}
