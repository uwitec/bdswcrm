package com.sw.cms.model;

/**
 * �������
 * @author liyt
 *
 */
public class ChtjDesc {
	
	private String chtj_id;      //������۱��
	private String product_id;   //��Ʒ���
	private String product_name; //��Ʒ����
	private String product_xh;   //���ڹ��
	private double ysjg;         //ԭʼ�۸�
	private double tzjg;         //������۸�
	private int nums;            //����ʱ�������
	private String remark;       //��ע
	
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
