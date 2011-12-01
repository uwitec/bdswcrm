package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.service.ProductService;
import com.sw.cms.service.SerialNumService;
import com.sw.cms.service.StoreService;
import com.sw.cms.util.Constant;

public class SerialNumAction extends BaseAction {
	
	private SerialNumService serialNumService;
	private ProductService productService;
	private StoreService storeService;
	
	private String serial_num = "";
	private String q_serial_num = "";
	private String state = "";
	private String product_name = "";
	private String product_xh = "";
	private int curPage = 1;
	
	private Map serialStateMap = null;
	private List serialFlowList = new ArrayList();
	private List storeList = new ArrayList();
	private Page pageSerialNum;
	private Page pageProduct;
	private SerialNumMng serialNumMng = new SerialNumMng();
	private String msg = "";
	private String store_id = "";
	
	
	/**
	 * 序列号列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;
		String con = "";
		if(!q_serial_num.equals("")){
			con += " and serial_num='" + q_serial_num + "'";
		}
		if(!state.equals("")){
			con += " and state='" + state + "'";
		}
		if(!product_name.equals("")){
			con += " and product_name like'%" + product_name + "%'";
		}
		if(!store_id.equals("")){
			con += " and store_id='" + store_id + "'";
		}
		pageSerialNum = serialNumService.getSerialNumPage(con, curPage, rowsPerPage);
		storeList = storeService.getAllStoreList();
		return "success";
	}
	
	/**
	 * 编辑序列号信息
	 * @return
	 */
	public String edit(){
		if(!serial_num.equals("")){
			serialNumMng = serialNumService.editSerialNumMng(serial_num);
		}
		storeList = storeService.getAllStoreList();
		return "success";
	}
	
	
	/**
	 * <p>更新序列号信息</p>
	 * <p>如果序列存在则更新，如果不存在则插入</p>
	 * @return
	 */
	public String update(){
		serialNumService.updateSerialNumState(serialNumMng);
		return "success";
	}
	
	
	/**
	 * 删除序列号
	 * @return
	 */
	public String del(){
		serialNumService.delSerialNum(serial_num);
		return "success";
	}
	
	
	/**
	 * 取查询结果
	 * @return
	 */
	public String getSerialFlow(){
		if(!serial_num.equals("")){
			serialStateMap = serialNumService.getSerialState(serial_num);
			serialFlowList = serialNumService.getSerialFlow(serial_num);
		}
		return "success";
	}
	
	
	/**
	 * 取商品列表
	 * @return
	 */
	public String getProducts(){
		String con = "";
		int rowsPerPage = Constant.PAGE_SIZE;
		
		if(!product_name.equals("")){
			con += " and product_name like '%" + product_name + "%'";
		}
		if(!product_xh.equals("")){
			con += " and product_xh like '%" + product_xh + "%'";
		}
		
		pageProduct = productService.getProducts(con, curPage, rowsPerPage);
		
		return "success";
	}
	

	public SerialNumService getSerialNumService() {
		return serialNumService;
	}

	public void setSerialNumService(SerialNumService serialNumService) {
		this.serialNumService = serialNumService;
	}

	public String getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public List getSerialFlowList() {
		return serialFlowList;
	}

	public void setSerialFlowList(List serialFlowList) {
		this.serialFlowList = serialFlowList;
	}

	public Map getSerialStateMap() {
		return serialStateMap;
	}

	public void setSerialStateMap(Map serialStateMap) {
		this.serialStateMap = serialStateMap;
	}


	public Page getPageSerialNum() {
		return pageSerialNum;
	}


	public void setPageSerialNum(Page pageSerialNum) {
		this.pageSerialNum = pageSerialNum;
	}


	public SerialNumMng getSerialNumMng() {
		return serialNumMng;
	}


	public void setSerialNumMng(SerialNumMng serialNumMng) {
		this.serialNumMng = serialNumMng;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
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

	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
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

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getQ_serial_num() {
		return q_serial_num;
	}

	public void setQ_serial_num(String qSerialNum) {
		q_serial_num = qSerialNum;
	}

}
