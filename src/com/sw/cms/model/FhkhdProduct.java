package com.sw.cms.model;

/**
 * �����ͻ�����Ʒ
 * @author Administrator
 *2010-02-05�޸ģ���������Ϣ����š������ͻ�����š���Ʒ��š�   ��Ʒ���ơ�      ��Ʒ���     ��Ʒ����
 *                         id   fhkhd_id    product_id  product_name  product_xh   cpfj
 *                      ǿ�����кš�     ��ע��    ���ۡ�   ��        �ֿ��š�    ���״̬��     ����
 *                      qz_serial_num  remark   price   totalmoney   store_id    storestate   nums
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
	   
	   private double price;       //����
	   private double totalmoney;   //���
	   private String store_id;    //�ֿ���
	   private String storestate;  //���״̬
	   private int nums;           //����
	   private String cpfj;  //��Ʒ����
	   
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
	public String getCpfj() {
		return cpfj;
	}
	public void setCpfj(String cpfj) {
		this.cpfj = cpfj;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(double totalmoney) {
		this.totalmoney = totalmoney;
	}
	
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
}
