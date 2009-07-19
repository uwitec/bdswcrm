package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Fysq;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.FysqService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;

/**
 * ��������Action�� 
 * @author liyt
 *
 */
public class FysqAction extends BaseAction {
	
	private FysqService fysqService;
	private UserService userService;
	private SjzdService sjzdService;
	
	private Page fysqPage;
	private Fysq fysq = new Fysq();
	private String id;
	
	private String[] fkfs;
	private List userList = new ArrayList();
	private String[] fyzclx;
	
	private String creatdate1 = "";
	private String creatdate2 = "";
	private String state = "";
	
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	
	/**
	 * ���������б�
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;

		String con = "";
		
		if(!creatdate1.equals("")){
			con += " and creatdate>='" + creatdate1 + "'";
		}
		if(!creatdate2.equals("")){
			con += " and creatdate<='" + creatdate2 + "'";
		}
		if(!state.equals("")){
			con += " and state='" + state + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		fysqPage = fysqService.getFysqList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * ȡ�����������б�
	 * @return
	 */
	public String dspList(){
		int rowsPerPage = Constant.PAGE_SIZE2;

		String con = "";
		
		if(!creatdate1.equals("")){
			con += " and creatdate>='" + creatdate1 + "'";
		}
		if(!creatdate2.equals("")){
			con += " and creatdate<='" + creatdate2 + "'";
		}
		if(!state.equals("")){
			con += " and state='" + state + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		fysqPage = fysqService.getDspFysqList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * �򿪷�������ҳ��
	 * @return
	 */
	public String edit(){
		userList = userService.getAllEmployeeList();    //ҵ��Ա�б�
		fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");   //���ʽ
		fyzclx = sjzdService.getSjzdXmxxByZdId("SJZD_ZCLX");  //����֧������
		
		if(!id.equals("")){
			fysq = fysqService.getFysq(id);
		}else{
			fysq.setId(fysqService.updateFysqId());
			fysq.setCreatdate(DateComFunc.getToday());
		}
		
		return "success";
	}
	
	
	/**
	 * ���·���������Ϣ
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		fysq.setCzr(user_id);
		fysqService.updateFysq(fysq);
		
		return "success";
	}
	
	
	/**
	 * ��������
	 * @return
	 */
	public String doSp(){
		
		//�жϷ����Ƿ�����
		if(fysqService.isFinishSp(fysq.getId())){
			this.saveMessage("���������Ѿ�������ɣ������ظ����������飡");
			
			fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");   //���ʽ
			fyzclx = sjzdService.getSjzdXmxxByZdId("SJZD_ZCLX");  //����֧������
			
			return "input";
		}
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		fysq.setSpr(user_id);
		fysqService.updateFysq(fysq);
		
		return "success";
	}
	
	
	/**
	 * ɾ������������Ϣ
	 * @return
	 */
	public String del(){
		fysqService.delFysq(id);
		return "success";
	}

	public String getCreatdate1() {
		return creatdate1;
	}

	public void setCreatdate1(String creatdate1) {
		this.creatdate1 = creatdate1;
	}

	public String getCreatdate2() {
		return creatdate2;
	}

	public void setCreatdate2(String creatdate2) {
		this.creatdate2 = creatdate2;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String[] getFkfs() {
		return fkfs;
	}

	public void setFkfs(String[] fkfs) {
		this.fkfs = fkfs;
	}

	public Fysq getFysq() {
		return fysq;
	}

	public void setFysq(Fysq fysq) {
		this.fysq = fysq;
	}

	public Page getFysqPage() {
		return fysqPage;
	}

	public void setFysqPage(Page fysqPage) {
		this.fysqPage = fysqPage;
	}

	public FysqService getFysqService() {
		return fysqService;
	}

	public void setFysqService(FysqService fysqService) {
		this.fysqService = fysqService;
	}

	public String[] getFyzclx() {
		return fyzclx;
	}

	public void setFyzclx(String[] fyzclx) {
		this.fyzclx = fyzclx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

}
