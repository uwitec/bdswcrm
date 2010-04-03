package com.sw.cms.model;

/**
 * ���ϵ���Ʒ��Ϣ
 * 2010-02-23 ���ӣ���š����ϵ�ID����ƷID����Ʒ���ơ���Ʒ�ͺš���Ʒ���кš���Ʒ��ע���ֿ��š����״̬������
 * id    bfd_id    product_id  product_name product_xh qz_serial_num product_remark store_id storestate nums 
 */
public class BfdProduct {
	private int id; // ��ˮ��

	private String bfd_id; // ���ϵ�ID

	private String product_id; // ��ƷID

	private String product_name; // ��Ʒ����	

	private String product_xh; // ��Ʒ�ͺ�

	private String qz_serial_num; // ��Ʒ���к�

	private String product_remark; // ��Ʒ��ע

	private String store_id; // �ֿ���

	private String storestate; // �ֿ��ʶ��1-�������� 2-������У�ֻ�Ի�������б�ʶ

	private int nums; // ��Ʒ����


	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public String getBfd_id() {
		return bfd_id;
	}

	public void setBfd_id(String bfd_id) {
		this.bfd_id = bfd_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStorestate() {
		return storestate;
	}

	public void setStorestate(String storestate) {
		this.storestate = storestate;
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

	public String getQz_serial_num() {
		return qz_serial_num;
	}

	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}

	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

}
