package com.sw.cms.model;

/**
 * �����ͻ�����Ʒ
 * @author Administrator
 *
 */
public class FhkhdProduct
{
	   private int id  ;//��ˮ��
	   private String  fhkhd_id     ;//�����ͻ���ID
	   private String product_id  ;//�����ͻ�����ƷID
	   private String product_name ;//�����ͻ�����Ʒ����
	   private String product_xh   ;//�����ͻ�����Ʒ���
	   private String qz_serial_num ;//�����ͻ�����Ʒ���к�
	   private String remark       ;//��ע
	public String getFhkhd_id() {
		return fhkhd_id;
	}
	public void setFhkhd_id(String fhkhd_id) {
		this.fhkhd_id = fhkhd_id;
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

}
