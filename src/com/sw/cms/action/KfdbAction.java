package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.EmployeeService;
import com.sw.cms.service.KfdbService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

/**
 * �ⷿ����<BR>
 * ״̬�������ѱ��桢�ѳ��⡢���������<BR>
 * �����״̬��ȷ����ɺ��״̬��ȷ������������㴦��<BR>
 * @author liyt
 *
 */
public class KfdbAction extends BaseAction {
	
	private KfdbService kfdbService;
	private UserService userService;
	private StoreService storeService;
	private SysInitSetService sysInitSetService;
	
	private DeptService deptService;
	private SjzdService sjzdService;
	private EmployeeService employeeService;
	
	private String[] positions;
	
	private Page ywyEmployeePage;//�޸�
	
	private Page pageKfdb;
	private Kfdb kfdb = new Kfdb();
	private List kfdbProducts = new ArrayList();	
	private List storeList = new ArrayList();
	private List userList = new ArrayList();
	private List depts = new ArrayList();
	
	private String id = "";
	private String ck_date1 = DateComFunc.getToday();
	private String ck_date2 = DateComFunc.getToday();
	private String state = "";
	private String dckf = "";
	private String drkf = "";
	private String orderName ="";
	private String orderType ="";	
	private int curPage = 1;
	private String iscs_flag = "";  //ϵͳ�Ƿ��ʼ��ɱ�־
	
	private String msg = "";
	
	//��ӡ����
	private String db_date = "";
	private String ck_store = "";
	private String rk_store = "";
	private String foot_name = "";
	private String remark = "";
	private String sqr = "";
	private String dbr = "";
	private String title_name = "";
	
	
	
	/**
	 * �ⷿ�����б�
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		
		//��ѯ����
		if(!ck_date1.equals("")){
			con += " and ck_date>='" + ck_date1 + "'";
		}
		if(!ck_date2.equals("")){
			con += " and ck_date<='" + ck_date2 + "'";
		}
		if(!state.equals("")){
			con += " and state='" + state + "'";
		}
		
		if(!dckf.equals("")){
			con += " and ck_store_id='" + dckf + "'";
		}
		
		if(!drkf.equals("")){
			con += " and rk_store_id='" + drkf + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		pageKfdb = kfdbService.getKfdbList(con, curPage, rowsPerPage);
		storeList = storeService.getAllStoreList();
		return "success";
	}
	
	
	public String selSqr(){
		try
		{
			int curPage = ParameterUtility.getIntParameter(getRequest(), "curPage",1);
			String ywyname=ParameterUtility.getStringParameter(getRequest(), "name","");
			String ywydept=ParameterUtility.getStringParameter(getRequest(), "dept","");
			String ywyposition=ParameterUtility.getStringParameter(getRequest(),"position","");
			int rowsPerPage = 15;
			String con="";
			if(!ywyname.equals(""))
			{
				con+=" and a.real_name like '%"+ywyname+"%'";
			}
			if(!ywydept.equals(""))
			{
				con+=" and b.dept_id='"+ywydept+"'";
			}
			if(!ywyposition.equals(""))
			{
				con+=" and a.position='"+ywyposition+"'";
			}
			ywyEmployeePage=employeeService.getYwyEmployee(con, curPage, rowsPerPage);
			positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
			depts = deptService.getDepts();
			return "success";
		}
		catch(Exception e)
		{
			log.error("��ȡҵ��Ա�б�  ʧ��ԭ��"+e.getMessage());
			return "error";
		}
	}
	
	
	/**
	 * �ⷿ�������ҳ��
	 * @return
	 */
	public String add(){
		kfdb.setId(kfdbService.updateKfdbID());
		
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * �����������
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		kfdb.setCzr(user_id);
		
		iscs_flag = sysInitSetService.getQyFlag();
		//ֻ����ϵͳ��ʽ���ú��ȥ�жϿ���Ƿ���������
		if(iscs_flag.equals("1")){
			if(kfdb.getState().equals("�ѳ���")){
				msg = kfdbService.checkKc(kfdb, kfdbProducts);
				if(!msg.equals("")){
					kfdb.setState("�ѱ���");
					kfdbService.saveKfdb(kfdb, kfdbProducts);
					
					return "input";
				}
			}
		}
		
		
		kfdbService.saveKfdb(kfdb, kfdbProducts);
		return "success";
	}
	
	
	/**
	 * �༭�ⷿ����
	 * @return
	 */
	public String edit(){
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		
		kfdb = (Kfdb)kfdbService.getKfdb(id);
		kfdbProducts = kfdbService.getKfdbProducts(id);
		return "success";
	}
	
	
	/**
	 * ���¿ⷿ����
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		kfdb.setCzr(user_id);
		
		iscs_flag = sysInitSetService.getQyFlag();
		//ֻ����ϵͳ��ʽ���ú��ȥ�жϿ���Ƿ���������
		if(iscs_flag.equals("1")){
			if(kfdb.getState().equals("�ѳ���")){
				msg = kfdbService.checkKc(kfdb, kfdbProducts);
				if(!msg.equals("")){
					kfdb.setState("�ѱ���");
					kfdbService.updateKfdb(kfdb, kfdbProducts);
					storeList = storeService.getAllStoreList();
					kfdbProducts = kfdbService.getKfdbProducts(kfdb.getId());
					return "input";
				}
			}
		}
		
		kfdbService.updateKfdb(kfdb, kfdbProducts);
		return "success";
	}
	
	
	/**
	 * ȷ�����
	 * @return
	 */
	public String confirm(){
		try{
			//�жϵ������Ƿ��Ѿ��ύ������Ѿ��ύ���سɹ��������κβ���
			if(kfdbService.isDbFinish(id)){
				return SUCCESS;
			}
			
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			
			kfdb = (Kfdb)kfdbService.getKfdb(id);
			kfdbProducts = kfdbService.getKfdbProductsObj(id);
			
			kfdb.setState("�����");
			kfdb.setCzr(user_id);
			
			iscs_flag = sysInitSetService.getQyFlag();
			//ֻ����ϵͳ��ʽ���ú��ȥ�жϿ���Ƿ���������
			if(iscs_flag.equals("1")){
				if(kfdb.getState().equals("�����")){
					msg = kfdbService.checkKc(kfdb, kfdbProducts);
					if(!msg.equals("")){
						kfdb.setState("�ѳ���");
						kfdbService.updateKfdb(kfdb, kfdbProducts);
						storeList = storeService.getAllStoreList();
						return "input";
					}
				}
			}
			
			kfdbService.updateKfdb(kfdb, kfdbProducts);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("ȷ�Ͽⷿ��������������ԭ��" + e);
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * ��ȷ�Ͽⷿ�������˻�
	 * @return
	 */
	public String doTh(){
		try{
			//�жϵ������Ƿ��Ѿ��ύ������Ѿ��ύ���سɹ��������κβ���
			if(kfdbService.isDbFinish(id)){
				return SUCCESS;
			}
			
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			
			kfdb = (Kfdb)kfdbService.getKfdb(id);
			kfdbProducts = kfdbService.getKfdbProductsObj(id);
			
			kfdb.setState("���˻�");
			kfdb.setCzr(user_id);
			
			kfdbService.updateKfdb(kfdb, kfdbProducts);
			
			return SUCCESS;
		}catch(Exception e){
			return ERROR;
		}
	}
	
	
	/**
	 * ɾ���ⷿ����
	 * @return
	 */
	public String del(){
		kfdbService.delKfdb(id);
		return "success";
	}	
	
	
	/**
	 * �ⷿ������Ʒ��ϸ
	 * @return
	 */
	public String descKfdb(){
		kfdbProducts = kfdbService.getKfdbProducts(id);
		return "success";
	}
	
	
	/**
	 * ��ӡ�ⷿ������
	 * @return
	 */
	public String printKfdb(){
		try{
			kfdb = (Kfdb)kfdbService.getKfdb(id);
			kfdbProducts = kfdbService.getKfdbProductsObj(id);
			
			db_date = StringUtils.nullToStr(kfdb.getCk_date());
			ck_store = StaticParamDo.getStoreNameById(kfdb.getCk_store_id());
			rk_store = StaticParamDo.getStoreNameById(kfdb.getRk_store_id());
			remark = StringUtils.nullToStr(kfdb.getRemark());
			sqr = StaticParamDo.getRealNameById(kfdb.getSqr());
			dbr = StaticParamDo.getRealNameById(kfdb.getJsr());
			
			Map map = sysInitSetService.getReportSet();
			
			if(map != null){
				title_name = StringUtils.nullToStr(map.get("title_name")) + "������";
				foot_name = StringUtils.nullToStr(map.get("foot_name"));	
			}
			
			return SUCCESS;
		}catch(Exception e){
			log.error("��ӡ�ⷿ��������ԭ��" + e);
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	public String getCk_date1() {
		return ck_date1;
	}
	public void setCk_date1(String ck_date1) {
		this.ck_date1 = ck_date1;
	}
	public String getCk_date2() {
		return ck_date2;
	}
	public void setCk_date2(String ck_date2) {
		this.ck_date2 = ck_date2;
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
	public Kfdb getKfdb() {
		return kfdb;
	}
	public void setKfdb(Kfdb kfdb) {
		this.kfdb = kfdb;
	}
	public List getKfdbProducts() {
		return kfdbProducts;
	}
	public void setKfdbProducts(List kfdbProducts) {
		this.kfdbProducts = kfdbProducts;
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
	public Page getPageKfdb() {
		return pageKfdb;
	}
	public void setPageKfdb(Page pageKfdb) {
		this.pageKfdb = pageKfdb;
	}
	public List getStoreList() {
		return storeList;
	}
	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}
	public StoreService getStoreService() {
		return storeService;
	}
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
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
	public KfdbService getKfdbService() {
		return kfdbService;
	}
	public void setKfdbService(KfdbService kfdbService) {
		this.kfdbService = kfdbService;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}


	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}
	public List getDepts() {
		return depts;
	}
	public void setDepts(List depts) {
		this.depts = depts;
	}
	public DeptService getDeptService() {
		return deptService;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public String getIscs_flag() {
		return iscs_flag;
	}
	public void setIscs_flag(String iscs_flag) {
		this.iscs_flag = iscs_flag;
	}
	public String[] getPositions() {
		return positions;
	}
	public void setPositions(String[] positions) {
		this.positions = positions;
	}
	public SjzdService getSjzdService() {
		return sjzdService;
	}
	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}
	public Page getYwyEmployeePage() {
		return ywyEmployeePage;
	}
	public void setYwyEmployeePage(Page ywyEmployeePage) {
		this.ywyEmployeePage = ywyEmployeePage;
	}


	public String getCk_store() {
		return ck_store;
	}


	public void setCk_store(String ck_store) {
		this.ck_store = ck_store;
	}


	public String getDb_date() {
		return db_date;
	}


	public void setDb_date(String db_date) {
		this.db_date = db_date;
	}


	public String getDbr() {
		return dbr;
	}


	public void setDbr(String dbr) {
		this.dbr = dbr;
	}


	public String getFoot_name() {
		return foot_name;
	}


	public void setFoot_name(String foot_name) {
		this.foot_name = foot_name;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getRk_store() {
		return rk_store;
	}


	public void setRk_store(String rk_store) {
		this.rk_store = rk_store;
	}


	public String getSqr() {
		return sqr;
	}


	public void setSqr(String sqr) {
		this.sqr = sqr;
	}


	public String getTitle_name() {
		return title_name;
	}


	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}

	public String getDckf() {
		return dckf;
	}


	public void setDckf(String dckf) {
		this.dckf = dckf;
	}
	
	public String getDrkf() {
		return drkf;
	}


	public void setDrkf(String drkf) {
		this.drkf = drkf;
	}
}
