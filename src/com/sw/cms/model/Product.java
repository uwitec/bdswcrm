package com.sw.cms.model;

/**
 * ��Ʒ��
 * @author liyt
 *
 */
public class Product {

	private String productId;  //��Ʒϵͳ����
	private String productXh;  //��Ʒ�ͺ�
	private String productName;//��Ʒ����
	private String productKind;//��Ʒ����
	
	private String gysbh;      //��Ӧ�̱��
	private String gysmc;      //��Ӧ������
	
	private String img;        //��ƷͼƬ��ַ
	private String ms;         //��Ʒ����
	private String prop;       //��Ʒ����(�����Ʒ������/����)
	
	private String dw;
	
	private double price;      //�ƶ���Ȩ��
	private double khcbj;      //���˳ɱ���
	private double ygcbj;      //Ԥ���ɱ���
	
	private double lsbj;       //���۱���
	private double lsxj;       //�����޼�
	
	private double fxbj;       //��������
	private double fxxj;       //�����޼�
	
	private double gf;         //������ɱ
	private double dss;        //����ɱ
		
	
	
	private String sp_txm;      //��Ʒ������

	private int kcxx;          //�������
	private int kcsx;          //�������
	private String state;          //״̬
	
	private String qz_serial_num; //ǿ�����к�
	
	public String getGysbh() {
		return gysbh;
	}
	public void setGysbh(String gysbh) {
		this.gysbh = gysbh;
	}
	public String getGysmc() {
		return gysmc;
	}
	public void setGysmc(String gysmc) {
		this.gysmc = gysmc;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductKind() {
		return productKind;
	}
	public void setProductKind(String productKind) {
		this.productKind = productKind;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String getProductXh() {
		return productXh;
	}
	public void setProductXh(String productXh) {
		this.productXh = productXh;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDss() {
		return dss;
	}
	public void setDss(double dss) {
		this.dss = dss;
	}
	public double getFxxj() {
		return fxxj;
	}
	public void setFxxj(double fxxj) {
		this.fxxj = fxxj;
	}
	public double getGf() {
		return gf;
	}
	public void setGf(double gf) {
		this.gf = gf;
	}
	public double getLsxj() {
		return lsxj;
	}
	public void setLsxj(double lsxj) {
		this.lsxj = lsxj;
	}
	public int getKcsx() {
		return kcsx;
	}
	public void setKcsx(int kcsx) {
		this.kcsx = kcsx;
	}
	public int getKcxx() {
		return kcxx;
	}
	public void setKcxx(int kcxx) {
		this.kcxx = kcxx;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public double getLsbj() {
		return lsbj;
	}
	public void setLsbj(double lsbj) {
		this.lsbj = lsbj;
	}
	public String getQz_serial_num() {
		return qz_serial_num;
	}
	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}
	public double getFxbj() {
		return fxbj;
	}
	public void setFxbj(double fxbj) {
		this.fxbj = fxbj;
	}
	public double getKhcbj() {
		return khcbj;
	}
	public void setKhcbj(double khcbj) {
		this.khcbj = khcbj;
	}
	public String getSp_txm() {
		return sp_txm;
	}
	public void setSp_txm(String sp_txm) {
		this.sp_txm = sp_txm;
	}
	public double getYgcbj() {
		return ygcbj;
	}
	public void setYgcbj(double ygcbj) {
		this.ygcbj = ygcbj;
	}
	
}
