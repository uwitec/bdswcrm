package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Lsysk;
import com.sw.cms.model.Page;
import com.sw.cms.service.LsyskService;
import com.sw.cms.service.PosTypeService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.JMath;
import com.sw.cms.util.MoneyUtil;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

/**
 * 零售预收款处理
 * @author liyt
 *
 */
public class LsyskAction extends BaseAction {
	
	private LsyskService lsyskService;
	private UserService userService;
	private SjzdService sjzdService;
	private PosTypeService posTypeService;
	private SysInitSetService sysInitSetService;
	
	private Page lsyskPage;
	private Lsysk lsysk = new Lsysk();
	private List userList = new ArrayList();
	private String[] fkfs;
	private List posTypeList = new ArrayList();
	
	private String id = "";
	private String q_id = "";
	private String reportstyle ="";
	public String getQ_id() {
		return q_id;
	}


	public void setQ_id(String qId) {
		q_id = qId;
	}


	private String client_name = "";
	private String ys_date1 = "";
	private String ys_date2 = "";
	private String ys_type = "未冲抵";
	
	private String orderName ="";
	private String orderType ="";	
	private int curPage = 1;
	
	//打印零售预收单
	private String ys_date = "";
	private String skfs = "";
	private String skzh = "";
	private String skje = "";
	private String dxje = "";
	private String foot_name = "";
	private String title_name = "";
	private String jsr = "";
	
	
	private List lsyskList = new ArrayList();
	/**
	 * 零售预收款
	 * @return
	 */
	public String printLsysk(){
		try{
			
			lsysk = (Lsysk)lsyskService.getLsysk(id);
			lsyskList.add(lsysk);
			
			ys_date = lsysk.getYs_date();
			skfs = lsysk.getFkfs();
			skzh = StaticParamDo.getAccountNameById(lsysk.getSkzh());
			skje = JMath.round(lsysk.getYsje());
			dxje = MoneyUtil.toChinese(lsysk.getYsje() + "");
			client_name = StringUtils.nullToStr(lsysk.getClient_name()) + "　" + StringUtils.nullToStr(lsysk.getLxdh()) + "　" + StringUtils.nullToStr(lsysk.getMobile());
			jsr = StaticParamDo.getRealNameById(lsysk.getJsr());
			
			Map map = sysInitSetService.getReportSet();			
			if(map != null){
				title_name = StringUtils.nullToStr(map.get("title_name")) + "零售预收单";
				foot_name = StringUtils.nullToStr(map.get("foot_name"));	
			}
			
			return SUCCESS;
		}catch(Exception e){
			log.error("打印零售预收单出错，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 取零售预收款列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;
 
		reportstyle = userService.getReportStyle();
		String con = "";

		if(!client_name.equals("")){
			con += " and client_name like'%" + client_name + "%'";
		}
		if(!ys_date1.equals("")){
			con += " and ys_date>='" + ys_date1 + "'";
		}
		if(!ys_date2.equals("")){
			con += " and ys_date<='" + ys_date2 + "'";
		}
		if(!q_id.equals("")){
			con += " and id='" + q_id + "'";
		}
		if(!ys_type.equals("")){
			con += " and type='" + ys_type + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		lsyskPage = lsyskService.getLsyskList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 选择未冲抵的零售预收款
	 * @return
	 */
	public String selLsysk(){
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = " and type='未冲抵'";

		if(!client_name.equals("")){
			con += " and client_name like'%" + client_name + "%'";
		}
		if(!ys_date1.equals("")){
			con += " and ys_date>='" + ys_date1 + "'";
		}
		if(!ys_date2.equals("")){
			con += " and ys_date<='" + ys_date2 + "'";
		}
		if(!id.equals("")){
			con += " and id='" + id + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		lsyskPage = lsyskService.getLsyskList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	
	/**
	 * 打开添加零售预收款页面
	 * @return
	 */
	public String add(){
		userList = userService.getAllEmployeeList();
		id = lsyskService.updateLsyskId();
		fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		posTypeList = posTypeService.getPosTypeList();
		return "success";
	}
	
	
	/**
	 * 保存零售预收款信息
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		lsysk.setCzr(user_id);
		lsysk.setType("未冲抵");
		
		lsyskService.saveLsysk(lsysk);
		
		return "success";
	}
	
	
	/**
	 * 更新零售预收款信息
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		lsysk.setCzr(user_id);
		lsysk.setType("未冲抵");
		
		lsyskService.updateLsysk(lsysk);
		
		return "success";
	}
	
	
	/**
	 * 编辑零售预收款信息
	 * @return
	 */
	public String edit(){
		userList = userService.getAllEmployeeList();
		lsysk = (Lsysk)lsyskService.getLsysk(id);
		fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		posTypeList = posTypeService.getPosTypeList();
		return "success";
	}
	
	
	/**
	 * 删除零售预收款信息
	 * @return
	 */
	public String del(){
		lsyskService.delLsysk(id);
		
		return "success";
	}
	
	
	/**
	 * 退回零售预收款
	 * @return
	 */
	public String doTh(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			
			Lsysk lsysk = (Lsysk)lsyskService.getLsysk(id);
			lsysk.setCzr(user_id);
			lsyskService.updateLsyskTh(lsysk);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("退回零售预收款失败，原因：" + e);
			return ERROR;
		}
	}
	
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Lsysk getLsysk() {
		return lsysk;
	}
	public void setLsysk(Lsysk lsysk) {
		this.lsysk = lsysk;
	}
	public Page getLsyskPage() {
		return lsyskPage;
	}
	public void setLsyskPage(Page lsyskPage) {
		this.lsyskPage = lsyskPage;
	}
	public LsyskService getLsyskService() {
		return lsyskService;
	}
	public void setLsyskService(LsyskService lsyskService) {
		this.lsyskService = lsyskService;
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
	public List getUserList() {
		return userList;
	}
	public void setUserList(List userList) {
		this.userList = userList;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getYs_date1() {
		return ys_date1;
	}
	public void setYs_date1(String ys_date1) {
		this.ys_date1 = ys_date1;
	}
	public String getYs_date2() {
		return ys_date2;
	}
	public void setYs_date2(String ys_date2) {
		this.ys_date2 = ys_date2;
	}
	public String[] getFkfs() {
		return fkfs;
	}
	public void setFkfs(String[] fkfs) {
		this.fkfs = fkfs;
	}
	public SjzdService getSjzdService() {
		return sjzdService;
	}
	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}
	public PosTypeService getPosTypeService() {
		return posTypeService;
	}
	public void setPosTypeService(PosTypeService posTypeService) {
		this.posTypeService = posTypeService;
	}
	public List getPosTypeList() {
		return posTypeList;
	}
	public void setPosTypeList(List posTypeList) {
		this.posTypeList = posTypeList;
	}


	public String getDxje() {
		return dxje;
	}


	public void setDxje(String dxje) {
		this.dxje = dxje;
	}


	public String getFoot_name() {
		return foot_name;
	}


	public void setFoot_name(String foot_name) {
		this.foot_name = foot_name;
	}


	public String getSkfs() {
		return skfs;
	}


	public void setSkfs(String skfs) {
		this.skfs = skfs;
	}


	public String getSkje() {
		return skje;
	}


	public void setSkje(String skje) {
		this.skje = skje;
	}


	public String getSkzh() {
		return skzh;
	}


	public void setSkzh(String skzh) {
		this.skzh = skzh;
	}


	public String getTitle_name() {
		return title_name;
	}


	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}


	public String getYs_date() {
		return ys_date;
	}


	public void setYs_date(String ys_date) {
		this.ys_date = ys_date;
	}


	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}


	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}


	public List getLsyskList() {
		return lsyskList;
	}


	public void setLsyskList(List lsyskList) {
		this.lsyskList = lsyskList;
	}


	public String getJsr() {
		return jsr;
	}


	public void setJsr(String jsr) {
		this.jsr = jsr;
	}


	public String getYs_type() {
		return ys_type;
	}


	public void setYs_type(String ysType) {
		ys_type = ysType;
	}

	public String getReportstyle() {
		return reportstyle;
	}

	public void setReportstyle(String report_style) {
		this.reportstyle = report_style;
	}
}
