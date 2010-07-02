package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;
import com.sw.cms.model.Djhpsz;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.ProductService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.DjhpszService;
import com.sw.cms.source.SysSource;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

/**
 * 兑奖货品设置
 * @author zuohj
 *
 */

public class DjhpszAction extends BaseAction {
	
	private DjhpszService djhpszService;
	private UserService userService;
	private ProductKcService productKcService;
	private ProductService productService;
	private ProductKindService productKindService;
	private SysSource sysSource;
	private Page pageDjhpsz;
	private Page productPage;
	private Djhpsz djhpsz = new Djhpsz();
	private List djhpszProducts = new ArrayList();
	private List kindList = new ArrayList();
	private String creatdate1 = "";
	private String creatdate2 = "";
	private String product_kind = "";
	private String product_xh = "";
	private String product_name = "";
	private String product_id = "";
	private String orderName ="";
	private String orderType ="";	
	private int curPage = 1;

	private int rowsPerPage = Constant.PAGE_SIZE;
	
	
	/**
	 * 取兑奖货品设置列表
	 * @return
	 */
	public String list(){

		String con = "";
		if(creatdate1.equals(""))
		{
			creatdate1 = DateComFunc.getToday();
		}
		if(creatdate2.equals(""))
		{
			creatdate2 = DateComFunc.getToday();
		}
		
		if(!product_name.equals("")){
			con += " and product_name like '%" + product_name + "%'";
		}
		if(!product_xh.equals("")){
			con += " and product_xh like '%" + product_xh + "%'";
		}
		if(!creatdate1.equals("")){
			con += " and cz_date>='" + creatdate1 + "'";
		}
		if(!creatdate2.equals("")){
			con += " and cz_date<='" + (creatdate2+ " 23:59:59") + "'";
		}
		
		
		if(orderName.equals("")){
			orderName = "product_id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		con += " order by " + orderName + " " + orderType;
		
		pageDjhpsz = djhpszService.getDjhpszList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	/**
	 * 打开添加兑奖货品设置页面
	 * @return
	 */
	public String add(){
		return "success";
	}
	
	/**
	 * 获取兑奖货品设置信息
	 * @return
	 */
	public String edit(){
		djhpsz = (Djhpsz)djhpszService.getDjhpsz(product_id);
		return "success";
	}
	
	/**
	 * 保存兑奖货品设置信息
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		String czr=user_id;
		djhpszService.saveDjhpsz(djhpszProducts,czr);
		return SUCCESS;
	}
	
	/**
	 * 更新兑奖货品设置信息
	 * @return
	 */
	public String update(){
		//设置当前操作人
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		djhpsz.setCzr(user_id);
		djhpszService.updateDjhpsz(djhpsz);
		return SUCCESS;
	}
	
	
	/**
	 * 删除兑奖货品设置信息
	 * @return
	 */
	public String del(){
		djhpszService.delDjhpszd(product_id);
		return "success";
	}
	
		
	
	/**
	 * 兑奖货品设置选择库存商品表
	 * @return
	 */
	public String selDjhpszProc(){
		
		int rowsPerPage = 15;
		
		String con = " and a.state='正常'";
		if(!product_name.equals("")){
			con += " and (a.product_name like '%" + product_name + "%' or a.product_xh like '%" + product_name + "%')";
		}
		if(!product_kind.equals("")){
			con += " and a.product_kind like '" + product_kind + "%'";
		}
		
		productPage = productKcService.getProductKcList(con, curPage, rowsPerPage);
		kindList = productKindService.getAllProductKindList();
		
		return "success";
	}

	public List getDjhpszProducts() {
		return djhpszProducts;
	}
	public void setDjhpszProducts(List djhpszProducts) {
		this.djhpszProducts = djhpszProducts;
	}
	
	public Page getPageDjhpsz() {
		return pageDjhpsz;
	}
	
	public void setPageDjhpsz(Page pageDjhpsz) {
		this.pageDjhpsz = pageDjhpsz;
	}
	
	public Djhpsz getDjhpsz() {
		return djhpsz;
	}
	
	public void setDjhpsz(Djhpsz djhpsz) {
		this.djhpsz = djhpsz;
	}
	
	public void setDjhpszService(DjhpszService djhpszService) {
		this.djhpszService = djhpszService;
	}

	public DjhpszService getDjhpszService() {
		return djhpszService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}
	
	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_kind() {
		return product_kind;
	}

	public void setProduct_kind(String product_kind) {
		this.product_kind = product_kind;
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

	public SysSource getSysSource() {
		return sysSource;
	}

	public void setSysSource(SysSource sysSource) {
		this.sysSource = sysSource;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ProductKindService getProductKindService() {
		return productKindService;
	}

	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}

	public List getKindList() {
		return kindList;
	}

	public void setKindList(List kindList) {
		this.kindList = kindList;
	}
}
