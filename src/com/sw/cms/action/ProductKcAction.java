package com.sw.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.ProductKc;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.ProductService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.util.Constant;

public class ProductKcAction extends BaseAction {

	private ProductKcService productKcService;
	private ProductService productService;
	private SysInitSetService sysInitSetService;

	private Page productKcPage;

	private ProductKc productKc;
	
	private Product product = new Product();

	private List storeList;
	
	private List productKcList = new ArrayList();

	private String product_xh = "";

	private String product_name = "";

	private String product_id = "";

	private String orderName = "";

	private String orderType = "";

	private int kc_nums = 0;
	
	private HashMap kcMap = null;

	private int curPage = 1;

	private String store_id;

	private String nums;
	
	private String kc_con = "";
	
	private String iscs_flag = "0";

	/**
	 * 根据查询条件取库存列表
	 * 
	 * @return
	 */
	public String list() {

		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		if (!product_xh.equals("")) {
			con += " and product_xh like '%" + product_xh + "%'";
		}
		if (!product_name.equals("")) {
			con += " and product_name like '%" + product_name + "%'";
		}

		if (orderName.equals("")) {
			orderName = "product_id";
		}
		if (orderType.equals("")) {
			orderType = "asc";
		}

		con += " order by " + orderName + " " + orderType;

		productKcPage = productKcService.getProductInitList(con,"", curPage, rowsPerPage);
		iscs_flag = sysInitSetService.getQyFlag();

		return "success";
	}

	/**
	 * 打开库存添加页面
	 * 
	 * @return
	 */
	public String add() {
		kc_nums = productKcService.getProductKcByProductId(product_id,store_id);
		product = (Product)productService.getProductById(product_id);
		return "success";
	}
	
	/**
	 * 返回分库明细
	 * @return
	 */
	public String fcmx(){
		storeList = productKcService.getStoreList();
		kcMap = productKcService.getProductKcByProductId(product_id);
		return "success";
	}

	/**
	 * 保存库存信息
	 * 
	 * @return
	 */
	public String save() {
		productKcService.saveProductKc(product_id, store_id, kc_nums+"");
		return "success";
	}
	
	
	/**
	 * 根据查询条件取库存明细列表
	 * @return
	 */
	public String getKcMx(){
		int rowsPerPage = Constant.PAGE_SIZE;
		productKcPage = productKcService.getProductKcMx(kc_con, curPage, rowsPerPage);
		return "success";
	}
	
	
	public String getKcMxList(){
		int rowsPerPage = Constant.PAGE_SIZE2;
		productKcPage = productKcService.getProductKcMxWts(kc_con, curPage, rowsPerPage);
		return "success";		
	}

	public ProductKc getProductKc() {
		return productKc;
	}

	public void setProductKc(ProductKc productKc) {
		this.productKc = productKc;
	}

	public Page getProductKcPage() {
		return productKcPage;
	}

	public void setProductKcService(ProductKcService productKcService) {
		this.productKcService = productKcService;
	}

	public List getStoreList() {
		return storeList;
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

	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

	public ProductKcService getProductKcService() {
		return productKcService;
	}

	public void setProductKcPage(Page productKcPage) {
		this.productKcPage = productKcPage;
	}

	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public List getProductKcList() {
		return productKcList;
	}

	public void setProductKcList(List productKcList) {
		this.productKcList = productKcList;
	}

	public String getKc_con() {
		return kc_con;
	}

	public void setKc_con(String kc_con) {
		this.kc_con = kc_con;
	}

	public String getIscs_flag() {
		return iscs_flag;
	}

	public void setIscs_flag(String iscs_flag) {
		this.iscs_flag = iscs_flag;
	}

	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}

	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}

	public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public int getKc_nums() {
		return kc_nums;
	}

	public void setKc_nums(int kc_nums) {
		this.kc_nums = kc_nums;
	}

	public HashMap getKcMap() {
		return kcMap;
	}

	public void setKcMap(HashMap kcMap) {
		this.kcMap = kcMap;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

}
