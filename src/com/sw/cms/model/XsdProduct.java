package com.sw.cms.model;

public class XsdProduct {
	
	private int id;                  //���
	private String xsd_id;           //���۵����
	private String product_id;       //��Ʒ���
	private String product_xh;       //��Ʒ���
	private String product_name;     //��Ʒ����
	private double price;            //�ۼ�
	private int nums;                //����
	private double xj;               //���С��
	private String remark;           //��ע
	private double jgtz;	         //�۸����
	private double cbj;              //�ɱ���
	private int sjcj_nums;           //ʵ�ʳɽ�����
	private double sjcj_xj;          //ʵ�ʳɽ�С��
	private double kh_cbj;           //���˳ɱ���
	private double gf;               //����(������ɱ)
	private String dw;               //��λ
	
	private double sd = 0;             //˰��
	private double ds = 0;             //����ɱ
	private double basic_ratio = 0;    //������ɱ���
	private double out_ratio = 0;      //������ɱ���
	private double lsxj = 0;           //�����޼�
	private double ygcbj = 0;          //Ԥ���ɱ���
	
	private String qz_serial_num;
	private String qz_flag;
	
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getXsd_id() {
		return xsd_id;
	}
	public void setXsd_id(String xsd_id) {
		this.xsd_id = xsd_id;
	}
	public double getJgtz() {
		return jgtz;
	}
	public void setJgtz(double jgtz) {
		this.jgtz = jgtz;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getXj() {
		return xj;
	}
	public void setXj(double xj) {
		this.xj = xj;
	}
	public double getCbj() {
		return cbj;
	}
	public void setCbj(double cbj) {
		this.cbj = cbj;
	}
	public String getQz_serial_num() {
		return qz_serial_num;
	}
	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}
	public String getQz_flag() {
		return qz_flag;
	}
	public void setQz_flag(String qz_flag) {
		this.qz_flag = qz_flag;
	}
	public int getSjcj_nums() {
		return sjcj_nums;
	}
	public void setSjcj_nums(int sjcj_nums) {
		this.sjcj_nums = sjcj_nums;
	}
	public double getSjcj_xj() {
		return sjcj_xj;
	}
	public void setSjcj_xj(double sjcj_xj) {
		this.sjcj_xj = sjcj_xj;
	}
	public double getKh_cbj() {
		return kh_cbj;
	}
	public void setKh_cbj(double kh_cbj) {
		this.kh_cbj = kh_cbj;
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
	public double getYgcbj() {
		return ygcbj;
	}
	public void setYgcbj(double ygcbj) {
		this.ygcbj = ygcbj;
	}

}
