package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Lsd;
import com.sw.cms.model.Page;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.EmployeeService;
import com.sw.cms.service.LsdService;
import com.sw.cms.service.PosTypeService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

/**
 * ���۵�����
 * @author liyt
 *
 */
public class LsdAction extends BaseAction {
	
	private LsdService lsdService;	
	private UserService userService;
	private StoreService storeService;
	private ProductKcService productKcService;
	private SysInitSetService sysInitSetService;
	private SjzdService sjzdService;
	private PosTypeService posTypeService;
 
	
	private List userList = new ArrayList();
	private List storeList = new ArrayList();
	private List posTypeList = new ArrayList();
 
	private Page productPage;
 
	private String[] ysfs;
 
	
	private Page pageLsd;
	private Lsd lsd = new Lsd();
	
	private List lsdProducts = new ArrayList();
	
	private String client_name = "";
	private String creatdate = DateComFunc.getToday();
	private String creatdate2 = DateComFunc.getToday();
	private String state = "";
	private String orderName ="";
	private String orderType ="";
	private String iscs_flag = "";  //ϵͳ�Ƿ��ʼ��ɱ�־
	private String flag = "";
	private String xsry_name = "";
	
	private int curPage = 1;
	
	private String id = "";
	private String sp_state = "";
	
	private String sd = "0";
	
	/**
	 * ���۵��б�
	 * @return
	 */
	public String list(){
		
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		if(!creatdate.equals("")){
			con += " and a.creatdate>='" + creatdate + "'";
		}
		if(!creatdate2.equals("")){
			con += " and a.creatdate<='" + (creatdate2 + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			con += " and a.client_name like'%" + client_name + "%'";
		}
		if(!state.equals("")){
			con += " and a.state='" + state + "'";
		}
		if(!xsry_name.equals("")){
			con += " and b.real_name like '%" + xsry_name + "%'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		pageLsd = lsdService.getLsdList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * �ѳɽ����۵��б��˻�ʱѡ��
	 * @return
	 */
	public String listCjLsd(){
		
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = " and a.state='���ύ'";
		
		if(!creatdate.equals("")){
			con += " and a.creatdate>='" + creatdate + "'";
		}
		if(!creatdate2.equals("")){
			con += " and a.creatdate<='" + (creatdate2 + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			con += " and a.client_name like'%" + client_name + "%'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		pageLsd = lsdService.getLsdList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * ��������۵�ҳ��
	 * @return
	 */
	public String add(){
		userList = userService.getAllEmployeeList();
		
		//�������۵����
		lsd.setId(lsdService.updateLsdId());
		
		storeList = storeService.getAllStoreList();
		
		iscs_flag = sysInitSetService.getQyFlag();  //�Ƿ���ɳ�ʼ��־
		
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		
		posTypeList = posTypeService.getPosTypeList();
		
		return "success";
	}
	
	
	/**
	 * �������۵���Ϣ
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		lsd.setCzr(user_id);
		
		//�ж��Ƿ���ڳ��ͼ���Ʒ�����������Ҫ����
		if(lsd.getState().equals("���ύ")){
			if(lsdService.isExistLowLsxj(lsd,lsdProducts)){
				
				//������ڵ��������޼���Ʒ
				lsd.setState("�ѱ���");
				lsd.setSp_state("1");  //��Ҫ����
				lsdService.saveLsd(lsd, lsdProducts);
				
				//ҳ���ʼ����
				userList = userService.getAllEmployeeList();
				storeList = storeService.getAllStoreList();
				iscs_flag = sysInitSetService.getQyFlag();  //�Ƿ���ɳ�ʼ��־
				ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
				posTypeList = posTypeService.getPosTypeList();
				
				return "input";
			}
		}
		
		//�Ƿ���ɳ�ʼ��־
		iscs_flag = sysInitSetService.getQyFlag();
		
		//ֻ������ɳ�ʼ��������������Ƿ����������ж�
		if(iscs_flag.equals("1")){
			
			//������۵�״̬Ϊ���ύ��Ҫ�жϿ���Ƿ������������
			//�����������������򱣴����۵���ͬʱ��������ҳ�棬��ʾ�û�
			if(lsd.getState().equals("���ύ")){
				String msg = lsdService.checkKc(lsd, lsdProducts);
				if(!msg.equals("")){
					this.saveMessage(msg);
					lsd.setState("�ѱ���");
					lsdService.saveLsd(lsd, lsdProducts);
					return "input";
				}
			}
			
		}
		
		lsdService.saveLsd(lsd, lsdProducts);
		return "success";
	}
	
	
	/**
	 * ���޸����۵�ҳ��
	 * @return
	 */
	public String edit(){
		
		lsd = (Lsd)lsdService.getLsd(id);
		lsdProducts = lsdService.getLsdProducts(id);
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		iscs_flag = sysInitSetService.getQyFlag();
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		posTypeList = posTypeService.getPosTypeList();
		return "success";
	}
	
	
	/**
	 * �������۵���Ϣ
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		lsd.setCzr(user_id);
		
		iscs_flag = sysInitSetService.getQyFlag();
		
		id = lsd.getId();
		userList = userService.getAllEmployeeList();
		storeList = storeService.getAllStoreList();
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		
		
		if(lsd.getState().equals("���ύ")){
			
			if(!lsd.getSp_state().equals("3")){
				//������۵���������ͨ���ģ�����Ҫ�ж��Ƿ���ڳ��ͼ���Ʒ�����������Ҫ����		
				if(lsdService.isExistLowLsxj(lsd,lsdProducts)){
					
					//������ڵ��������޼���Ʒ
					lsd.setState("�ѱ���");
					lsd.setSp_state("1");  //��Ҫ����
					lsdService.updateLsd(lsd, lsdProducts);
					
					//ҳ���ʼ����
					userList = userService.getAllEmployeeList();
					storeList = storeService.getAllStoreList();
					iscs_flag = sysInitSetService.getQyFlag();  //�Ƿ���ɳ�ʼ��־
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
					posTypeList = posTypeService.getPosTypeList();
					
					return "input";
				}else{
					lsd.setSp_state("0");
				}
			}
		}
		
		//ֻ������ɳ�ʼ��������������Ƿ����������ж�
		if(iscs_flag.equals("1")){
			
			//������۵�״̬Ϊ���ύ��Ҫ�жϿ���Ƿ������������
			//�����������������򱣴����۵���ͬʱ��������ҳ�棬��ʾ�û�
			if(lsd.getState().equals("���ύ")){
				String msg = lsdService.checkKc(lsd, lsdProducts);
				if(!msg.equals("")){
					this.saveMessage(msg);
					
					lsd.setState("�ѱ���");
					lsdService.updateLsd(lsd, lsdProducts);
					return "input";
				}
			}
		}
		
		lsdService.updateLsd(lsd, lsdProducts);
		return "success";
	}
	
	
	/**
	 * �ύ����
	 * @return
	 */
	public String submitInfo(){		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		lsd.setCzr(user_id);
		
		lsd.setSp_state("2");  //�޸����۵�Ϊ������״̬
		lsd.setState("�ѱ���");      //�޸����۵�״̬Ϊ�ѱ���
		
		lsdService.updateLsd(lsd, lsdProducts);
		
		//����ϵͳ��Ϣ����������
		lsdService.saveMsg(lsd.getId(), user_id);
		
		return "success";
		
	}
	
	
	/**
	 * ɾ�����۵�
	 * @return
	 */
	public String del(){
		String id = ParameterUtility.getStringParameter(getRequest(),"id", "");
		lsdService.delLsd(id);
		return "success";
	}
	
	
	
	/**
	 * ��ѡ�����Ʒ�б�
	 * @return
	 */
	public String selKcProc(){
		String product_xh = ParameterUtility.getStringParameter(getRequest(),"product_xh", "");
		String product_name = ParameterUtility.getStringParameter(getRequest(),"product_name", "");
		String prop = ParameterUtility.getStringParameter(getRequest(),"prop", "");	
		
		int rowsPerPage = 15;
		
		String con = " and state='����'";
		if(!product_xh.equals("")){
			con += " and product_xh like '%" + product_xh + "%'";
		}
		if(!product_name.equals("")){
			con += " and product_name like '%" + product_name + "%'";
		}
		if(!prop.equals("")){
			con += " and prop='" + prop + "'";
		}
		
		productPage = productKcService.getProductKcList(con, curPage, rowsPerPage);
		storeList = storeService.getAllStoreList();
		
		return "success";
	}
	
	
	
	/**
	 * ���������кŴ���
	 * @return
	 */
	public String importSerial(){
		return "success";
	}
	
	
	/**
	 * ���۵���ϸ
	 * @return
	 */
	public String descLsd(){
		lsdProducts = lsdService.getLsdProducts(id);
		return "success";
	}
	
	
	/**
	 * ���۵�����
	 * @return
	 */
	public String doSp(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		lsd = (Lsd)lsdService.getLsd(id); //���۵�
		lsdProducts = lsdService.getLsdProducts(id);
		
		//ֻ��������ͨ��������ɳ�ʼ�������жϿ���Ƿ�����
		if(sp_state.equals("3") && iscs_flag.equals("1")){	
			//����ͨ��,��Ҫ�жϿ���Ƿ�����			
			String msg = lsdService.checkKc(lsd, lsdProducts);
			if(!msg.equals("")){
				this.saveMessage(msg);
				return "input";
			}
		}
		
		//�����������
		lsdService.saveSp(id, sp_state, user_id);
		
		//����ϵͳ��Ϣ
		Lsd curLsd = (Lsd)lsdService.getLsd(id);
		lsdService.saveMsg(curLsd.getCzr(), user_id, id,sp_state);
		
		return "success";
	}
	
	/**
	 * �༭����˰��
	 * @return
	 */
	public String editLssd(){
		sd = lsdService.getLssd() + "";
		return "success";
	}
	
	/**
	 * ��������˰��
	 * @return
	 */
	public String saveLssd(){
		lsdService.saveLssd(sd);
		return "success";
	}
	
	
	public Lsd getLsd() {
		return lsd;
	}
	public void setLsd(Lsd lsd) {
		this.lsd = lsd;
	}
	public LsdService getLsdService() {
		return lsdService;
	}
	public void setLsdService(LsdService lsdService) {
		this.lsdService = lsdService;
	}
	public Page getPageLsd() {
		return pageLsd;
	}
	public void setPageLsd(Page pageLsd) {
		this.pageLsd = pageLsd;
	}
	public List getLsdProducts() {
		return lsdProducts;
	}
	public void setLsdProducts(List lsdProducts) {
		this.lsdProducts = lsdProducts;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List getUserList() {
		return userList;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserList(List userList) {
		this.userList = userList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public StoreService getStoreService() {
		return storeService;
	}
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}
	public List getStoreList() {
		return storeList;
	}
	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}
	public ProductKcService getProductKcService() {
		return productKcService;
	}
	public void setProductKcService(ProductKcService productKcService) {
		this.productKcService = productKcService;
	}
	public Page getProductPage() {
		return productPage;
	}
	public void setProductPage(Page productPage) {
		this.productPage = productPage;
	}
	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}
	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}
	public String getIscs_flag() {
		return iscs_flag;
	}
	public void setIscs_flag(String iscs_flag) {
		this.iscs_flag = iscs_flag;
	}
	public SjzdService getSjzdService() {
		return sjzdService;
	}
	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}
	public String[] getYsfs() {
		return ysfs;
	}
	public void setYsfs(String[] ysfs) {
		this.ysfs = ysfs;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getCreatdate2() {
		return creatdate2;
	}


	public void setCreatdate2(String creatdate2) {
		this.creatdate2 = creatdate2;
	}


	public String getXsry_name() {
		return xsry_name;
	}


	public void setXsry_name(String xsry_name) {
		this.xsry_name = xsry_name;
	}


	public String getSp_state() {
		return sp_state;
	}


	public void setSp_state(String sp_state) {
		this.sp_state = sp_state;
	}


	public String getSd() {
		return sd;
	}


	public void setSd(String sd) {
		this.sd = sd;
	}


	public List getPosTypeList() {
		return posTypeList;
	}


	public void setPosTypeList(List posTypeList) {
		this.posTypeList = posTypeList;
	}


	public PosTypeService getPosTypeService() {
		return posTypeService;
	}


	public void setPosTypeService(PosTypeService posTypeService) {
		this.posTypeService = posTypeService;
	}



	
}
