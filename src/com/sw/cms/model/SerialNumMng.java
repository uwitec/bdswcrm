package com.sw.cms.model;

public class SerialNumMng {
	
	private int seq_id;          //��ˮ��
	private String serial_num;   //���к�
	private String product_id;   //��Ʒ���
	private String product_name; //��Ʒ����
	private String product_xh;   //��Ʒ���
	private String state;        //���к�״̬�����ڿ⡢���ۡ����˻�)
	private String store_id;     //�ⷿ���
	private String store_name;   //�ⷿ����(���ݿ��в����棬ͨ��store_id�ڻ�ȡ��Ϣ��ֵ)
	private String yj_flag;      //������ǣ�0,��1���ǣ�
	
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
	public int getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(int seq_id) {
		this.seq_id = seq_id;
	}
	public String getSerial_num() {
		return serial_num;
	}
	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getYj_flag() {
		return yj_flag;
	}
	public void setYj_flag(String yj_flag) {
		this.yj_flag = yj_flag;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

}
