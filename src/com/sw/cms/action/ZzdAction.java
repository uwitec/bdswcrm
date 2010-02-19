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
 * 组装单处理
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
	
	private String start_date = "";  //查询条件--开始时间
	private String end_date = "";    //查询条件--结束时间
	private String state = "";       //查询条件--状态
	
	private int curPage = 1;
	
	private String iscs_flag = "";  //系统是否初始完成标志
	
	/**
	 * 列表展示所有组装单
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
			log.error("查询组装单列表错误，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}

	
	/**
	 * 编辑组装单信息
	 * @return
	 */
	public String edit(){
		try{
			storeList = storeService.getAllStoreList();   //库房列表
			userList = userService.getAllEmployeeList();  //业务员列表
			
			if(!id.equals("")){
				zzd = zzdService.editZzd(id);
				zzdProducts = zzdService.editZzdProducts(id);
			}else{
				zzd.setId(zzdService.updateZzdID());
				zzd.setCdate(DateComFunc.getToday());
			}
			
			return SUCCESS;
		}catch(Exception e){
			log.error("编辑组装单信息错误，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 删除组装单
	 * @return
	 */
	public String del(){
		try{
			zzdService.delZzd(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("删除组装单错误，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 查询组装单明细信息
	 * @return
	 */
	public String desc(){
		try{
			zzdProducts = zzdService.editZzdProducts(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("显示组装单明细错误，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 更新组装单信息
	 * @return
	 */
	public String update(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			zzd.setCzr(user_id);
			
			iscs_flag = sysInitSetService.getQyFlag();  //是否完成初始标志
			
			//如果状态是已提交需判断库存是否满足
			//只有在完成初始工作后再做库存是否满足需求判断
			if(iscs_flag.equals("1") && zzd.getState().equals("已提交")){
				msg = zzdService.checkKc(zzd,zzdProducts);
				
				if(!msg.equals("")){
					zzd.setState("已保存");
					zzdService.updateZzd(zzd, zzdProducts);
					
					storeList = storeService.getAllStoreList();   //库房列表
					userList = userService.getAllEmployeeList();  //业务员列表
					
					return INPUT;
				}
			}
			
			//如果数据库中存在该拆卸单，并且状态为已提交，不做任何处理返回
			if(zzdService.isZzdSubmit(zzd.getId())){
				return SUCCESS;
			}
			
			zzdService.updateZzd(zzd, zzdProducts);
			return SUCCESS;
		}catch(Exception e){
			log.error("更新组装单信息错误，原因：" + e.getMessage());
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
