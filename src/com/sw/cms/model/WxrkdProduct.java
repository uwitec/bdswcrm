package com.sw.cms.model;
/**
 * ά����ⵥ_��Ʒ
 * @author Administrator
 * 2010-03-01�޸ģ���������Ϣ��id  ��ˮ��
 * wxrkd_id        ά����ⵥ���
 * product_id      ��Ʒ���
 * product_name    ��Ʒ����
 * product_xh      ��Ʒ���
 * qz_serial_num   ǿ�����к�
 * remark          ��ע
 * nums            ����
 * store_id       Ŀ��⡪���ü���
 * storestate     Ŀ���״̬����3
 */
public class WxrkdProduct 
{
	 private int id  ;//��ˮ��
     private String wxrkd_id ;//ά����ⵥID
     private String product_id; //��ƷID 
     private String  product_name  ;      //��Ʒ����
      
     private String product_xh   ;   //��Ʒ�ͺ� 
     private String qz_serial_num  ;//��Ʒ���к�
     private String remark ;//��Ʒ��ע    
     private int nums       ;//����
     private String store_id         ; //Ŀ���
     private String storestate          ;//Ŀ���״̬
          
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
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
	public String getStorestate() {
		return storestate;
	}
	public void setStorestate(String storestate) {
		this.storestate =storestate;
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
	public String getWxrkd_id() {
		return wxrkd_id;
	}
	public void setWxrkd_id(String wxrkd_id) {
		this.wxrkd_id = wxrkd_id;
	}
	
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
}
