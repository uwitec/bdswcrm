package com.sw.cms.model;

public class Xsd {
	
	private String id;         //编号
	private String creatdate;  //创建时间
	private String fzr;        //负责人（经手人）
	private String client_name;//客户编号
	private String sklx;       //收款类型（现金、账期）
	private String skfs;       //收款方式（现金、刷卡、转账等）
	private String pos_id;     //刷卡POS机编号
	private String state;      //状态（已保存、已提交、已出库）	
	private double yhje;       //优惠金额（无效）
	private double xsdje;      //销售单金额
	private double xsdcbj;     //销售单成本价
	private double skje;       //收款金额
	private String skzh;       //收款账户	
	private String skxs;       //未收、部分已收、已收	
	private String skrq;       //收款日期
	private String ysrq;       //应收日期	 
	private String ms;         //描述（备注）
	private String czr;        //操作人	
	private String kh_address; //客户地址
	private String kh_lxr;     //客户联系人
	private String kh_lxdh;    //客户联系电话	
	
	private String store_id;   //出货库房
	private String ck_jsr;     //出库经手人
	private String ck_date;
	
	
	private String ysfs;       //运输方式	
	private double xjd = 0;       //现金点
	private double ysje = 0;      //预收金额
	private int zq = 0;            //账期
	private double sjcjje = 0;  //实际成交金额
	private String th_flag;     //退回标志（0：正常；1：库房退回
	private String cz_date;     //操作时间
	private double xsdkhcb;     //销售单考核成本	
	
	private String sp_state = "0"; //审批状态  0：自动审批；1：需审批；2：待审批；3：审批通过；4：审批不通过
	private String sp_type = "0";  //审批类型  1：超期款审批；2：超额并且低于限价审批；3：超额审批；4：低于限价审批；	
	private String spr;        //审批人
	private String sp_date;    //审批时间
	private String sp_opinion; //审批意见
	
	
	private String cx_tel;      //查询电话
	private String job_no;      //货单号
	private String send_time;   //发货时间
	
	//开票信息
	private String fplx;         //发票类型
	private String kp_mc;         //开票名称
	private String kp_address;   //开票地址
	private String kp_dh;        //开票电话
	private String khhzh;        //开户行账号
	private String sh;           //税号
	private String fpxx;         //发票信息
	
	private String yfzf_type;    //运费支付类型
	
	private String hykh;  //会员卡号
	
	private int hyjf;     //会员积分
	
	
	public String getHykh() {
		return hykh;
	}
	public void setHykh(String hykh) {
		this.hykh = hykh;
	}
	public int getHyjf() {
		return hyjf;
	}
	
	public void setHyjf(int hyjf) {
		this.hyjf = hyjf;
	}
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getCreatdate() {
		return creatdate;
	}
	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getFzr() {
		return fzr;
	}
	public void setFzr(String fzr) {
		this.fzr = fzr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public double getSkje() {
		return skje;
	}
	public void setSkje(double skje) {
		this.skje = skje;
	}
	public String getSklx() {
		return sklx;
	}
	public void setSklx(String sklx) {
		this.sklx = sklx;
	}
	public String getSkzh() {
		return skzh;
	}
	public void setSkzh(String skzh) {
		this.skzh = skzh;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public double getXsdcbj() {
		return xsdcbj;
	}
	public void setXsdcbj(double xsdcbj) {
		this.xsdcbj = xsdcbj;
	}
	public double getXsdje() {
		return xsdje;
	}
	public void setXsdje(double xsdje) {
		this.xsdje = xsdje;
	}
	public double getYhje() {
		return yhje;
	}
	public void setYhje(double yhje) {
		this.yhje = yhje;
	}
	public String getSkxs() {
		return skxs;
	}
	public void setSkxs(String skxs) {
		this.skxs = skxs;
	}
	public String getSkrq() {
		return skrq;
	}
	public void setSkrq(String skrq) {
		this.skrq = skrq;
	}
	public String getYsrq() {
		return ysrq;
	}
	public void setYsrq(String ysrq) {
		this.ysrq = ysrq;
	}
	public String getKh_address() {
		return kh_address;
	}
	public void setKh_address(String kh_address) {
		this.kh_address = kh_address;
	}
	public String getKh_lxdh() {
		return kh_lxdh;
	}
	public void setKh_lxdh(String kh_lxdh) {
		this.kh_lxdh = kh_lxdh;
	}
	public String getKh_lxr() {
		return kh_lxr;
	}
	public void setKh_lxr(String kh_lxr) {
		this.kh_lxr = kh_lxr;
	}
	public String getYsfs() {
		return ysfs;
	}
	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public double getXjd() {
		return xjd;
	}
	public void setXjd(double xjd) {
		this.xjd = xjd;
	}
	public double getYsje() {
		return ysje;
	}
	public void setYsje(double ysje) {
		this.ysje = ysje;
	}
	public int getZq() {
		return zq;
	}
	public void setZq(int zq) {
		this.zq = zq;
	}
	public double getSjcjje() {
		return sjcjje;
	}
	public void setSjcjje(double sjcjje) {
		this.sjcjje = sjcjje;
	}
	public String getTh_flag() {
		return th_flag;
	}
	public void setTh_flag(String th_flag) {
		this.th_flag = th_flag;
	}
	public String getCz_date() {
		return cz_date;
	}
	public void setCz_date(String cz_date) {
		this.cz_date = cz_date;
	}
	public double getXsdkhcb() {
		return xsdkhcb;
	}
	public void setXsdkhcb(double xsdkhcb) {
		this.xsdkhcb = xsdkhcb;
	}
	public String getSp_date() {
		return sp_date;
	}
	public void setSp_date(String sp_date) {
		this.sp_date = sp_date;
	}
	public String getSp_opinion() {
		return sp_opinion;
	}
	public void setSp_opinion(String sp_opinion) {
		this.sp_opinion = sp_opinion;
	}
	public String getSp_state() {
		return sp_state;
	}
	public void setSp_state(String sp_state) {
		this.sp_state = sp_state;
	}
	public String getSpr() {
		return spr;
	}
	public void setSpr(String spr) {
		this.spr = spr;
	}
	public String getSp_type() {
		return sp_type;
	}
	public void setSp_type(String sp_type) {
		this.sp_type = sp_type;
	}
	public String getCx_tel() {
		return cx_tel;
	}
	public void setCx_tel(String cx_tel) {
		this.cx_tel = cx_tel;
	}
	public String getJob_no() {
		return job_no;
	}
	public void setJob_no(String job_no) {
		this.job_no = job_no;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getPos_id() {
		return pos_id;
	}
	public void setPos_id(String pos_id) {
		this.pos_id = pos_id;
	}
	public String getSkfs() {
		return skfs;
	}
	public void setSkfs(String skfs) {
		this.skfs = skfs;
	}
	public String getFplx() {
		return fplx;
	}
	public void setFplx(String fplx) {
		this.fplx = fplx;
	}
	public String getFpxx() {
		return fpxx;
	}
	public void setFpxx(String fpxx) {
		this.fpxx = fpxx;
	}
	public String getKhhzh() {
		return khhzh;
	}
	public void setKhhzh(String khhzh) {
		this.khhzh = khhzh;
	}
	public String getKp_address() {
		return kp_address;
	}
	public void setKp_address(String kp_address) {
		this.kp_address = kp_address;
	}
	public String getKp_dh() {
		return kp_dh;
	}
	public void setKp_dh(String kp_dh) {
		this.kp_dh = kp_dh;
	}
	public String getSh() {
		return sh;
	}
	public void setSh(String sh) {
		this.sh = sh;
	}
	public String getKp_mc() {
		return kp_mc;
	}
	public void setKp_mc(String kp_mc) {
		this.kp_mc = kp_mc;
	}
	public String getCk_jsr() {
		return ck_jsr;
	}
	public void setCk_jsr(String ck_jsr) {
		this.ck_jsr = ck_jsr;
	}
	public String getCk_date() {
		return ck_date;
	}
	public void setCk_date(String ck_date) {
		this.ck_date = ck_date;
	}
	public String getYfzf_type() {
		return yfzf_type;
	}
	public void setYfzf_type(String yfzfType) {
		yfzf_type = yfzfType;
	}

}
