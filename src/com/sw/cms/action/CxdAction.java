package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Cxd;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.CxdService;
import com.sw.cms.service.ProductService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;

/**
 * 拆卸单处理
 * @author liyt
 *
 */
public class CxdAction extends BaseAction {
	
	private CxdService cxdService;
	private UserService userService;
	private StoreService storeService;
	private SysInitSetService sysInitSetService;
	private ProductService productService;
	
	private Page pageCxd;
	private Cxd cxd = new Cxd();
	private List cxdProducts = new ArrayList();
	private List userList = new ArrayList();
	private List storeList = new ArrayList();
	private Page pageProduct;
	
	private String id = "";
	
	private String start_date = "";  //查询条件--开始时间
	private String end_date = "";    //查询条件--结束时间
	private String state = "";       //查询条件--状态
	
	private int curPage = 1;
	
	private String iscs_flag = "";  //系统是否初始完成标志
	
	private String product_con = "";  //选择商品，查询条件
	
	
	/**
	 * 列表展示所有拆卸单
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
			
			pageCxd = cxdService.getCxdList(con, curPage, rowsPerPage);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("查询拆卸单列表错误，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 编辑拆卸单信息
	 * @return
	 */
	public String edit(){
		try{
			storeList = storeService.getAllStoreList();   //库房列表
			userList = userService.getAllEmployeeList();  //业务员列表
			
			if(!id.equals("")){
				cxd = cxdService.editCxd(id);
				cxdProducts = cxdService.editCxdProducts(id);
			}else{
				cxd.setId(cxdService.updateCxdID());
				cxd.setCdate(DateComFunc.getToday());
			}
			
			return SUCCESS;
		}catch(Exception e){
			log.error("编辑拆卸单信息错误，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 更新拆卸单信息
	 * @return
	 */
	public String update(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			cxd.setCzr(user_id);
			
			iscs_flag = sysInitSetService.getQyFlag();  //是否完成初始标志
			storeList = storeService.getAllStoreList();   //库房列表
			userList = userService.getAllEmployeeList();  //业务员列表
			
			//如果状态是已提交需判断库存是否满足
			//只有在完成初始工作后再做库存是否满足需求判断
			if(iscs_flag.equals("1") && cxd.getState().equals("已提交")){
				msg = cxdService.checkKc(cxd);
				
				if(!msg.equals("")){
					cxd.setState("已保存");
					cxdService.updateCxd(cxd, cxdProducts);
					return INPUT;
				}
			}
			
			//如果数据库中存在该拆卸单，并且状态为已提交，提示用户拆卸单已提交
			if(cxdService.isCxdSubmit(cxd.getId())){
				msg = "拆卸单已提交，不能重复提交！";
				return INPUT;
			}
			
			cxdService.updateCxd(cxd, cxdProducts);
			return SUCCESS;
		}catch(Exception e){
			log.error("更新拆卸单信息错误，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 删除拆卸单
	 * @return
	 */
	public String del(){
		try{
			cxdService.delCxd(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("删除拆卸单错误，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 查询拆卸单明细信息
	 * @return
	 */
	public String desc(){
		try{
			cxdProducts = cxdService.editCxdProducts(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("显示拆卸单明细错误，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 选择商品
	 * 待拆卸商品必须是库存商品，并且状态为在售
	 * @return
	 */
	public String selProduct(){
		try{
			int rowsPerPage = Constant.PAGE_SIZE;
			String con = " and prop='库存商品' and state='正常'";
			
			if(!product_con.equals("")){
				con += " and (product_name like '%" + product_con + "%' or product_xh like '%" + product_con + "%')";
			}
			
			pageProduct = productService.getProducts(con, curPage, rowsPerPage);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("选择拆卸商品错误，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 打开序列号输入框
	 * @return
	 */
	public String importSerialNums(){
		return SUCCESS;
	}


	public CxdService getCxdService() {
		return cxdService;
	}


	public void setCxdService(CxdService cxdService) {
		this.cxdService = cxdService;
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


	public Page getPageCxd() {
		return pageCxd;
	}


	public void setPageCxd(Page pagCxd) {
		this.pageCxd = pagCxd;
	}


	public Cxd getCxd() {
		return cxd;
	}


	public void setCxd(Cxd cxd) {
		this.cxd = cxd;
	}


	public List getCxdProducts() {
		return cxdProducts;
	}


	public void setCxdProducts(List cxdProducts) {
		this.cxdProducts = cxdProducts;
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
	

	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}


	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}


	public String getIscs_flag() {
		return iscs_flag;
	}


	public void setIscs_flag(String iscsFlag) {
		iscs_flag = iscsFlag;
	}


	public ProductService getProductService() {
		return productService;
	}


	public void setProductService(ProductService productService) {
		this.productService = productService;
	}


	public Page getPageProduct() {
		return pageProduct;
	}


	public void setPageProduct(Page pageProduct) {
		this.pageProduct = pageProduct;
	}


	public String getProduct_con() {
		return product_con;
	}


	public void setProduct_con(String productCon) {
		product_con = productCon;
	}

}
