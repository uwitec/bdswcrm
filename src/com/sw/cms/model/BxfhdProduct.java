package com.sw.cms.model;

/**
 * ���޷�������Ʒ
 * 
 * @author Administrator
 * 2010-02-04�޸ģ���������Ϣ����ˮ��\���޷�����ID\��ƷID\��Ʒ����\��Ʒ�ͺ�\��Ʒ���к�\�ֿ���\���״̬\����\��Ʒ����\����\���\��Ʒ��ע
 *         id\bxfhd_id\product_id\product_name\product_xh\qz_serial_num\store_id\storestate\nums\cpfj\price\totalmoney\remark
 * 
 */
public class BxfhdProduct 
{
	private int id;// ��ˮ��
	private String bxfhd_id; // ���޷�����ID
	private String product_id; // ��ƷID
	private String product_name; // ��Ʒ����
	private String product_xh; // ��Ʒ�ͺ�
	private String qz_serial_num;// ��Ʒ���к�
	private String remark;// ��Ʒ��ע
	private String store_id; // �ֿ���
	private String storeState; // �ֿ��ʶ���ֿ���Ϊ��������ʱʹ�ã�1���ڻ�������  2���������
	private int nums; // ��Ʒ����
	private String cpfj;// ��Ʒ����
	private double price; // ��Ʒ�۸�
	private double totalMoney; // ��Ʒ�ܽ��
	 

	public String getStord_id() {
		return store_id;
	}

	public void setStord_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStorestate() {
		return storeState;
	}

	public void setStorestate(String storeState) {
		this.storeState = storeState;
	}

	public String getBxfhd_id() {
		return bxfhd_id;
	}

	public void setBxfhd_id(String bxfhd_id) {
		this.bxfhd_id = bxfhd_id;
	}

	public String getCpfj() {
		return cpfj;
	}

	public void setCpfj(String cpfj) {
		this.cpfj = cpfj;
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotalmoney() {
		return totalMoney;
	}

	public void setTotalmoney(double totalMoney) {
		this.totalMoney = totalMoney;
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
