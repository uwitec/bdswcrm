package com.sw.cms.model;

public class Xsd {
	
	private String id;         //���
	private String creatdate;  //����ʱ��
	private String fzr;        //�����ˣ������ˣ�
	private String client_name;//�ͻ����
	private String sklx;       //�տ����ͣ��ֽ����ڣ�
	private String skfs;       //�տʽ���ֽ�ˢ����ת�˵ȣ�
	private String pos_id;     //ˢ��POS�����
	private String state;      //״̬���ѱ��桢���ύ���ѳ��⣩	
	private double yhje;       //�Żݽ���Ч��
	private double xsdje;      //���۵����
	private double xsdcbj;     //���۵��ɱ���
	private double skje;       //�տ���
	private String skzh;       //�տ��˻�	
	private String skxs;       //δ�ա��������ա�����	
	private String skrq;       //�տ�����
	private String ysrq;       //Ӧ������	 
	private String ms;         //��������ע��
	private String czr;        //������	
	private String kh_address; //�ͻ���ַ
	private String kh_lxr;     //�ͻ���ϵ��
	private String kh_lxdh;    //�ͻ���ϵ�绰	
	
	private String store_id;   //�����ⷿ
	private String ck_jsr;     //���⾭����
	private String ck_date;
	
	
	private String ysfs;       //���䷽ʽ	
	private double xjd = 0;       //�ֽ��
	private double ysje = 0;      //Ԥ�ս��
	private int zq = 0;            //����
	private double sjcjje = 0;  //ʵ�ʳɽ����
	private String th_flag;     //�˻ر�־��0��������1���ⷿ�˻�
	private String cz_date;     //����ʱ��
	private double xsdkhcb;     //���۵����˳ɱ�	
	
	private String sp_state = "0"; //����״̬  0���Զ�������1����������2����������3������ͨ����4��������ͨ��
	private String sp_type = "0";  //��������  1�����ڿ�������2������ҵ����޼�������3������������4�������޼�������	
	private String spr;        //������
	private String sp_date;    //����ʱ��
	private String sp_opinion; //�������
	
	
	private String cx_tel;      //��ѯ�绰
	private String job_no;      //������
	private String send_time;   //����ʱ��
	
	//��Ʊ��Ϣ
	private String fplx;         //��Ʊ����
	private String kp_mc;         //��Ʊ����
	private String kp_address;   //��Ʊ��ַ
	private String kp_dh;        //��Ʊ�绰
	private String khhzh;        //�������˺�
	private String sh;           //˰��
	private String fpxx;         //��Ʊ��Ϣ
	
	private String yfzf_type;    //�˷�֧������
	
	private String hykh;  //��Ա����
	
	private int hyjf;     //��Ա����
	
	
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
