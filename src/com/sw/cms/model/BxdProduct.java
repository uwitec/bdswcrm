package com.sw.cms.model;

public class BxdProduct {
	private int id; // 流水号

	private String bxd_id; // 报修单ID

	private String product_id; // 产品ID

	private String product_name; // 产品名称

	private String product_gg; // 产品规格

	private String product_xh; // 产品型号

	private String product_serial_num; // 产品序号

	private String product_remark; // 产品备注

	private String bxaddress; // 产品所在地

	private String bxstate; // 产品状态

	private String has_fj; // 是否有附件

	private String fj; // 附件

	private String qtfj; // 其他附件

	private String bj; // 备件

	private String gzfx; // 故障及分析

	private String pcgc; // 排除过程

	private double gf; // 工分

	public String getBj() {
		return bj;
	}

	public void setBj(String bj) {
		this.bj = bj;
	}

	public String getBxaddress() {
		return bxaddress;
	}

	public void setBxaddress(String bxaddress) {
		this.bxaddress = bxaddress;
	}

	public String getBxd_id() {
		return bxd_id;
	}

	public void setBxd_id(String bxd_id) {
		this.bxd_id = bxd_id;
	}

	public String getBxstate() {
		return bxstate;
	}

	public void setBxstate(String bxstate) {
		this.bxstate = bxstate;
	}

	public String getFj() {
		return fj;
	}

	public void setFj(String fj) {
		this.fj = fj;
	}

	public double getGf() {
		return gf;
	}

	public void setGf(double gf) {
		this.gf = gf;
	}

	public String getGzfx() {
		return gzfx;
	}

	public void setGzfx(String gzfx) {
		this.gzfx = gzfx;
	}

	public String getHas_fj() {
		return has_fj;
	}

	public void setHas_fj(String has_fj) {
		this.has_fj = has_fj;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPcgc() {
		return pcgc;
	}

	public void setPcgc(String pcgc) {
		this.pcgc = pcgc;
	}

	public String getProduct_gg() {
		return product_gg;
	}

	public void setProduct_gg(String product_gg) {
		this.product_gg = product_gg;
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

	public String getProduct_serial_num() {
		return product_serial_num;
	}

	public void setProduct_serial_num(String product_serial_num) {
		this.product_serial_num = product_serial_num;
	}

	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

	public String getQtfj() {
		return qtfj;
	}

	public void setQtfj(String qtfj) {
		this.qtfj = qtfj;
	}
}
