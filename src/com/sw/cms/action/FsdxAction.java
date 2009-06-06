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

	private List lxlist = new ArrayList();// 联系人类型LIST
	
	private String []lxrlx;//数据字典里的联系人类型

	private Flx lx = new Flx(); // 联系人类型实体

	private String id; // 联系人ID

	private Map mapLx; // 修改前加载的联系人类型

	private String orderName = "";

	private String orderType = "";

	private Page linkmanPage; // 储存联系人列表

	private int curPage = 1; // 当前页面

	private String linkmanyddh = ""; // 移动电话

	private String linkmanname = "";// 联系人姓名

	private String lxid = ""; // 联系人类型

	private String sendLinkman; // 要发送的联系人

	private Flinkman linkman = new Flinkman();// 联系人实体

	private Map mapLinkman; // 修改前加载联系人

	private String[] receptMobiles; // 发送手机号码组

	private String context;
	
	private List loglist=new ArrayList();//日志列表

	/**
	 * 返回联系人类型列表
	 * 
	 * @return
	 */
	public String listlx() {
		lxlist = fsdxService.getLxList();
		return "success";
	}

	/**
	 * 返回添加联系人类型页面
	 * 
	 * @return
	 */
	public String addlx() {
		return "success";
	}

	/**
	 * 添加联系人类型
	 * 
	 */
	public String savelx() {

		fsdxService.saveLx(lx);
		return "success";
	}

	/**
	 * 返回修改联系人类型页面
	 * 
	 * @return
	 */
	public String editlx() {
		mapLx = fsdxService.getLx(id);
		return "success";
	}

	/**
	 * 更改联系人类型
	 * 
	 * @return
	 */
	public String updatelx() {
		fsdxService.updateLx(lx);
		return "success";
	}

	/**
	 * 删除联系人类型
	 * 
	 * @return
	 */
	public String deletelx() {
		fsdxService.deleteLx(id);
		return "success";
	}

	/**
	 * 联系人列表
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
	 * 返回联系人添加页面
	 * 
	 * @return
	 */
	public String addlinkman() {
		lxrlx  = sjzdService.getSjzdXmxxByZdId("SJZD_LXRLX");
		return "success";
	}

	/**
	 * 保存联系人
	 * 
	 * @return
	 */
	public String savelinkman() {
		fsdxService.saveLinkman(linkman);
		return "success";
	}

	/**
	 * 返回联系人修改页面
	 * 
	 * @return
	 */
	public String editlinkman() {
		mapLinkman = fsdxService.getLinkman(id);
		lxrlx  = sjzdService.getSjzdXmxxByZdId("SJZD_LXRLX");
		return "success";
	}

	/**
	 * 更改联系人
	 * 
	 * @return
	 */
	public String updatelinkman() {
		fsdxService.updateLinkman(linkman);
		return "success";
	}

	/**
	 * 删除联系人
	 * 
	 * @return
	 */
	public String deletelinkman() {
		fsdxService.deleteLinkman(id);
		return "success";
	}

	/**
	 * 添加短信
	 * 
	 * @return
	 */
	public String addSMS() {
		return "success";
	}

	/**
	 * 发送短信
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
