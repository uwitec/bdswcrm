package com.sw.cms.model;

/**
 * ��������Ʒ��Ϣ
 * 2010-02-24 ���ӣ���������Ϣ����š�������ID����ƷID����Ʒ���ơ���Ʒ�ͺš��ɲ�Ʒ���кš��²�Ʒ���кš���Ʒ��ע
 *           id    hjd_id    product_id  product_name product_xh oqz_serial_num  nqz_serial_num  product_remark 
 */
public class HjdProduct {
	private int id; // ��ˮ��

	private String hjd_id; // ������ID

	private String product_id; // ��ƷID

	private String product_name; // ��Ʒ����	

	private String product_xh; // ��Ʒ�ͺ�

	private String oqz_serial_num; // �ɲ�Ʒ���к�

	private String product_remark; // ��Ʒ��ע

	private String nqz_serial_num; // �²�Ʒ���к�


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
