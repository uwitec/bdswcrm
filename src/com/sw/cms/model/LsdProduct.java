package com.sw.cms.model;

public class LsdProduct {
	
	private int id;                    //���
	private String lsd_id;             //���۵����
	private String product_id;         //��Ʒ���
	private String product_xh;         //��Ʒ���
	private String product_name;       //��Ʒ����
	private double price = 0;          //�ۼ�
	private int nums = 0;              //����
	private double xj = 0;             //���
	private String remark;             //��ע
	private double cbj;                //�ɱ�����
	private String qz_serial_num;      //���к�
	private double kh_cbj;             //���˳ɱ�����
	private String qz_flag;            //ǿ�����к�
	private double gf = 0;             //����(������ɱ)
	private String dw = "";            //��λ
	
	private double sd = 0;             //˰��
	private double ds = 0;             //����ɱ
	private double basic_ratio = 0;    //������ɱ���
	private double out_ratio = 0;      //������ɱ���
	private double lsxj = 0;           //�����޼�
	
	public double getCbj() {
		return cbj;
	}
	public void setCbj(double cbj) {
		this.cbj = cbj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLsd_id() {
		return lsd_id;
	}
	public void setLsd_id(String lsd_id) {
		this.lsd_id = lsd_id;
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
	public double getXj() {
		return xj;
	}
	public void setXj(double xj) {
		this.xj = xj;
	}
	public String getQz_serial_num() {
		return qz_serial_num;
	}
	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}
	public double getKh_cbj() {
		return kh_cbj;
	}
	public void setKh_cbj(double kh_cbj) {
		this.kh_cbj = kh_cbj;
	}
	public String getQz_flag() {
		return qz_flag;
	}
	public void setQz_flag(String qz_flag) {
		this.qz_flag = qz_flag;
	}
	public double getGf() {
		return gf;
	}
	public void setGf(double gf) {
		this.gf = gf;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public double getBasic_ratio() {
		return basic_ratio;
	}
	public void setBasic_ratio(double basic_ratio) {
		this.basic_ratio = basic_ratio;
	}
	public double getDs() {
		return ds;
	}
	public void setDs(double ds) {
		this.ds = ds;
	}
	public double getOut_ratio() {
		return out_ratio;
	}
	public void setOut_ratio(double out_ratio) {
		this.out_ratio = out_ratio;
	}
	public double getSd() {
		return sd;
	}
	public void setSd(double sd) {
		this.sd = sd;
	}
	public double getLsxj() {
		return lsxj;
	}
	public void setLsxj(double lsxj) {
		this.lsxj = lsxj;
	}
	
}
