package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Zzd;
import com.sw.cms.service.ProductService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.ZzdService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;

/**
 * ��װ������
 * @author liyt
 *
 */
public class ZzdAction extends BaseAction {
	
	private ZzdService zzdService;
	private UserService userService;
	private StoreService storeService;
	private SysInitSetService sysInitSetService;
	private ProductService productService;
	
	private Page pageZzd;
	private Zzd zzd = new Zzd();
	private List zzdProducts = new ArrayList();
	private List userList = new ArrayList();
	private List storeList = new ArrayList();
	
	private String id = "";
	
	private String start_date = "";  //��ѯ����--��ʼʱ��
	private String end_date = "";    //��ѯ����--����ʱ��
	private String state = "";       //��ѯ����--״̬
	
	private int curPage = 1;
	
	private String iscs_flag = "";  //ϵͳ�Ƿ��ʼ��ɱ�־
	
	/**
	 * �б�չʾ������װ��
	 * @return
	 */
	public String list(){
		try{
			int rowsPerPage = Constant.PAGE_SIZE;

			String con = "";
			
			if(!start_date.equals("")){
				con += " and cdate>='" + start_date + "'";
			}
			if(!end_date.equals("")){
				con += " and cdate<='" + (end_date + " 23:59:59") + "'";
			}
			if(!state.equals("")){
				con += " and state='" + state + "'";
			}

			con += " order by id desc";
			
			pageZzd = zzdService.getZzdList(con, curPage, rowsPerPage);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("��ѯ��װ���б����ԭ��" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}

	
	/**
	 * �༭��װ����Ϣ
	 * @return
	 */
	public String edit(){
		try{
			storeList = storeService.getAllStoreList();   //�ⷿ�б�
			userList = userService.getAllEmployeeList();  //ҵ��Ա�б�
			
			if(!id.equals("")){
				zzd = zzdService.editZzd(id);
				zzdProducts = zzdService.editZzdProducts(id);
			}else{
				zzd.setId(zzdService.updateZzdID());
				zzd.setCdate(DateComFunc.getToday());
			}
			
			return SUCCESS;
		}catch(Exception e){
			log.error("�༭��װ����Ϣ����ԭ��" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * ɾ����װ��
	 * @return
	 */
	public String del(){
		try{
			zzdService.delZzd(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("ɾ����װ������ԭ��" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * ��ѯ��װ����ϸ��Ϣ
	 * @return
	 */
	public String desc(){
		try{
			zzdProducts = zzdService.editZzdProducts(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("��ʾ��װ����ϸ����ԭ��" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * ������װ����Ϣ
	 * @return
	 */
	public String update(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			zzd.setCzr(user_id);
			
			iscs_flag = sysInitSetService.getQyFlag();  //�Ƿ���ɳ�ʼ��־
			
			//���״̬�����ύ���жϿ���Ƿ�����
			//ֻ������ɳ�ʼ��������������Ƿ����������ж�
			if(iscs_flag.equals("1") && zzd.getState().equals("���ύ")){
				msg = zzdService.checkKc(zzd,zzdProducts);
				
				if(!msg.equals("")){
					zzd.setState("�ѱ���");
					zzdService.updateZzd(zzd, zzdProducts);
					
					storeList = storeService.getAllStoreList();   //�ⷿ�б�
					userList = userService.getAllEmployeeList();  //ҵ��Ա�б�
					
					return INPUT;
				}
			}
			
			//������ݿ��д��ڸò�ж��������״̬Ϊ���ύ�������κδ�����
			if(zzdService.isZzdSubmit(zzd.getId())){
				return SUCCESS;
			}
			
			zzdService.updateZzd(zzd, zzdProducts);
			return SUCCESS;
		}catch(Exception e){
			log.error("������װ����Ϣ����ԭ��" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public ZzdService getZzdService() {
		return zzdService;
	}

	public void setZzdService(ZzdService zzdService) {
		this.zzdService = zzdService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public StoreService getStoreService() {
		return storeService;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}

	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public Zzd getZzd() {
		return zzd;
	}

	public void setZzd(Zzd zzd) {
		this.zzd = zzd;
	}

	public List getZzdProducts() {
		return zzdProducts;
	}

	public void setZzdProducts(List zzdProducts) {
		this.zzdProducts = zzdProducts;
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public List getStoreList() {
		return storeList;
	}

	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String startDate) {
		start_date = startDate;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String endDate) {
		end_date = endDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getIscs_flag() {
		return iscs_flag;
	}

	public void setIscs_flag(String iscsFlag) {
		iscs_flag = iscsFlag;
	}

	public Page getPageZzd() {
		return pageZzd;
	}

	public void setPageZzd(Page pageZzd) {
		this.pageZzd = pageZzd;
	}

}
