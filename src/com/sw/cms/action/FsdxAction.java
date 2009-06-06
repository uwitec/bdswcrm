package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Flinkman;
import com.sw.cms.model.Flx;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.FsdxService;
import com.sw.cms.service.SjzdService;

public class FsdxAction extends BaseAction {

	private FsdxService fsdxService;
	private SjzdService sjzdService;

	private List lxlist = new ArrayList();// ��ϵ������LIST
	
	private String []lxrlx;//�����ֵ������ϵ������

	private Flx lx = new Flx(); // ��ϵ������ʵ��

	private String id; // ��ϵ��ID

	private Map mapLx; // �޸�ǰ���ص���ϵ������

	private String orderName = "";

	private String orderType = "";

	private Page linkmanPage; // ������ϵ���б�

	private int curPage = 1; // ��ǰҳ��

	private String linkmanyddh = ""; // �ƶ��绰

	private String linkmanname = "";// ��ϵ������

	private String lxid = ""; // ��ϵ������

	private String sendLinkman; // Ҫ���͵���ϵ��

	private Flinkman linkman = new Flinkman();// ��ϵ��ʵ��

	private Map mapLinkman; // �޸�ǰ������ϵ��

	private String[] receptMobiles; // �����ֻ�������

	private String context;
	
	private List loglist=new ArrayList();//��־�б�

	/**
	 * ������ϵ�������б�
	 * 
	 * @return
	 */
	public String listlx() {
		lxlist = fsdxService.getLxList();
		return "success";
	}

	/**
	 * ���������ϵ������ҳ��
	 * 
	 * @return
	 */
	public String addlx() {
		return "success";
	}

	/**
	 * �����ϵ������
	 * 
	 */
	public String savelx() {

		fsdxService.saveLx(lx);
		return "success";
	}

	/**
	 * �����޸���ϵ������ҳ��
	 * 
	 * @return
	 */
	public String editlx() {
		mapLx = fsdxService.getLx(id);
		return "success";
	}

	/**
	 * ������ϵ������
	 * 
	 * @return
	 */
	public String updatelx() {
		fsdxService.updateLx(lx);
		return "success";
	}

	/**
	 * ɾ����ϵ������
	 * 
	 * @return
	 */
	public String deletelx() {
		fsdxService.deleteLx(id);
		return "success";
	}

	/**
	 * ��ϵ���б�
	 * 
	 * @return
	 */
	public String listlinkman() {
      
		int rowsPerPage = 30;
		String con = "";

		if (!linkmanname.equals("")) {
			con += " and name like '%" + linkmanname + "%'";
		}
		if (!linkmanyddh.equals("")) {
			con += " and yddh='" + linkmanyddh + "'";
		}
		if (!lxid.equals("")) {
			con += " and lxid='" + lxid + "'";
		}

		if (orderName.equals("")) {
			orderName = "id";
		}
		if (orderType.equals("")) {
			orderType = "desc";
		}

		con += " order by " + orderName + " " + orderType;

		linkmanPage = fsdxService.getLinkmanList(con, curPage, rowsPerPage);

		lxrlx  = sjzdService.getSjzdXmxxByZdId("SJZD_LXRLX");

		return "success";
	}

	/**
	 * ������ϵ�����ҳ��
	 * 
	 * @return
	 */
	public String addlinkman() {
		lxrlx  = sjzdService.getSjzdXmxxByZdId("SJZD_LXRLX");
		return "success";
	}

	/**
	 * ������ϵ��
	 * 
	 * @return
	 */
	public String savelinkman() {
		fsdxService.saveLinkman(linkman);
		return "success";
	}

	/**
	 * ������ϵ���޸�ҳ��
	 * 
	 * @return
	 */
	public String editlinkman() {
		mapLinkman = fsdxService.getLinkman(id);
		lxrlx  = sjzdService.getSjzdXmxxByZdId("SJZD_LXRLX");
		return "success";
	}

	/**
	 * ������ϵ��
	 * 
	 * @return
	 */
	public String updatelinkman() {
		fsdxService.updateLinkman(linkman);
		return "success";
	}

	/**
	 * ɾ����ϵ��
	 * 
	 * @return
	 */
	public String deletelinkman() {
		fsdxService.deleteLinkman(id);
		return "success";
	}

	/**
	 * ��Ӷ���
	 * 
	 * @return
	 */
	public String addSMS() {
		return "success";
	}

	/**
	 * ���Ͷ���
	 * 
	 * @return
	 */
	public String sendSMS() {
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		
		loglist=fsdxService.sendSMS(sendLinkman, context,info.getUser_id());

		return "success";
	}
	
	

	public Map getMapLinkman() {
		return mapLinkman;
	}

	public void setMapLinkman(Map mapLinkman) {
		this.mapLinkman = mapLinkman;
	}

	public FsdxService getFsdxService() {
		return fsdxService;
	}

	public void setFsdxService(FsdxService fsdxService) {
		this.fsdxService = fsdxService;
	}

	public List getLxlist() {
		return lxlist;
	}

	public void setLxlist(List lxlist) {
		this.lxlist = lxlist;
	}

	public Flx getLx() {
		return lx;
	}

	public void setLx(Flx lx) {
		this.lx = lx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map getMapLx() {
		return mapLx;
	}

	public void setMapLx(Map mapLx) {
		this.mapLx = mapLx;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getLxid() {
		return lxid;
	}

	public void setLxid(String lxid) {
		this.lxid = lxid;
	}

	public String getSendLinkman() {
		return sendLinkman;
	}

	public void setSendLinkman(String sendLinkman) {
		this.sendLinkman = sendLinkman;
	}

	public String getLinkmanname() {
		return linkmanname;
	}

	public void setLinkmanname(String linkmanname) {
		this.linkmanname = linkmanname;
	}

	public Page getLinkmanPage() {
		return linkmanPage;
	}

	public void setLinkmanPage(Page linkmanPage) {
		this.linkmanPage = linkmanPage;
	}

	public String getLinkmanyddh() {
		return linkmanyddh;
	}

	public void setLinkmanyddh(String linkmanyddh) {
		this.linkmanyddh = linkmanyddh;
	}

	public Flinkman getLinkman() {
		return linkman;
	}

	public void setLinkman(Flinkman linkman) {
		this.linkman = linkman;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String[] getReceptMobiles() {
		return receptMobiles;
	}

	public void setReceptMobiles(String[] receptMobiles) {
		this.receptMobiles = receptMobiles;
	}

	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}

	public String[] getLxrlx() {
		return lxrlx;
	}

	public void setLxrlx(String[] lxrlx) {
		this.lxrlx = lxrlx;
	}

	public List getLoglist() {
		return loglist;
	}

	public void setLoglist(List loglist) {
		this.loglist = loglist;
	}

	
}
