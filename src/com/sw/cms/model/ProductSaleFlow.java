package com.sw.cms.model;

import java.sql.Timestamp;

/**
 * ��Ʒ������ˮ
 * @author jinyanni
 *
 */
public class ProductSaleFlow {
	
	private int seq_id;        //��ˮ��
	private String id;         //���ݱ��
	private String yw_type;    //ҵ������
	private String client_name;//�ͻ���ţ����ƣ�
	private String xsry;       //������Ա���
	private String xsry_dept;  //������Ա���ű��
	private String cz_date;    //�ɽ�����
	private String product_id; //��Ʒ���
	private int nums;          //����
	private double price;      //����
	private double hjje;       //�ϼƽ��
	private double dwcb;       //��λ�ɱ�
	private double cb;         //�ɱ�
	private double dwkhcb;     //��λ���˳ɱ�
	private double khcb;       //���˳ɱ�
	private double dwygcb;     //��λԤ���ɱ�
	private double ygcb;       //Ԥ���ɱ�
	private double sd;         //˰��
	private double bhsje;      //����˰���
	private double gf;         //����(������ɱ)
	private double ds;         //����ɱ
	private double basic_ratio;//������ɱ���
	private double out_ratio;  //������ɱ���
	private double lsxj;       //�����޼�
	private String sfcytc;     //�Ƿ�������
	private Timestamp jy_time; //����ʱ��
	
	public int getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(int seqId) {
		seq_id = seqId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYw_type() {
		return yw_type;
	}
	public void setYw_type(String ywType) {
		yw_type = ywType;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String productId) {
		product_id = productId;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String clientName) {
		client_name = clientName;
	}
	public String getXsry() {
		return xsry;
	}
	public void setXsry(String xsry) {
		this.xsry = xsry;
	}
	public String getCz_date() {
		return cz_date;
	}
	public void setCz_date(String czDate) {
		cz_date = czDate;
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
	public double getHjje() {
		return hjje;
	}
	public void setHjje(double hjje) {
		this.hjje = hjje;
	}
	public double getDwcb() {
		return dwcb;
	}
	public void setDwcb(double dwcb) {
		this.dwcb = dwcb;
	}
	public double getCb() {
		return cb;
	}
	public void setCb(double cb) {
		this.cb = cb;
	}
	public double getDwkhcb() {
		return dwkhcb;
	}
	public void setDwkhcb(double dwkhcb) {
		this.dwkhcb = dwkhcb;
	}
	public double getKhcb() {
		return khcb;
	}
	public void setKhcb(double khcb) {
		this.khcb = khcb;
	}
	public double getDwygcb() {
		return dwygcb;
	}
	public void setDwygcb(double dwygcb) {
		this.dwygcb = dwygcb;
	}
	public double getYgcb() {
		return ygcb;
	}
	public void setYgcb(double ygcb) {
		this.ygcb = ygcb;
	}
	public double getSd() {
		return sd;
	}
	public void setSd(double sd) {
		this.sd = sd;
	}
	public double getBhsje() {
		return bhsje;
	}
	public void setBhsje(double bhsje) {
		this.bhsje = bhsje;
	}
	public double getGf() {
		return gf;
	}
	public void setGf(double gf) {
		this.gf = gf;
	}
	public double getDs() {
		return ds;
	}
	public void setDs(double ds) {
		this.ds = ds;
	}
	public double getBasic_ratio() {
		return basic_ratio;
	}
	public void setBasic_ratio(double basicRatio) {
		basic_ratio = basicRatio;
	}
	public double getOut_ratio() {
		return out_ratio;
	}
	public void setOut_ratio(double outRatio) {
		out_ratio = outRatio;
	}
	public double getLsxj() {
		return lsxj;
	}
	public void setLsxj(double lsxj) {
		this.lsxj = lsxj;
	}
	public Timestamp getJy_time() {
		return jy_time;
	}
	public void setJy_time(Timestamp jyTime) {
		jy_time = jyTime;
	}
	public String getSfcytc() {
		return sfcytc;
	}
	public void setSfcytc(String sfcytc) {
		this.sfcytc = sfcytc;
	}
	public String getXsry_dept() {
		return xsry_dept;
	}
	public void setXsry_dept(String xsryDept) {
		xsry_dept = xsryDept;
	}
	
}
