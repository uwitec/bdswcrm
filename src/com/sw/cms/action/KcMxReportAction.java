package com.sw.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Product;
import com.sw.cms.service.KcMxReportService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.ProductService;
import com.sw.cms.service.StoreService;

public class KcMxReportAction extends BaseAction {

	private KcMxReportService kcMxReportService;
	private StoreService storeService;
	private ProductKindService productKindService;
	private ProductService productService;
	
	private List store_list = new ArrayList();
	private List productKindList = new ArrayList();
	private Product product = new Product();
	private List productList = new ArrayList();
	private Map fckcStatResult = new HashMap();
	
	private String product_id = "";
	
	private String product_kind = "";
	private String product_name = "";
	private String store_id = "";
	private String flag = "";
	private String state = "";
	private String px="";
	private String cdate = "";
	
	private int dj = 1;
	
	public String getPx() {
		return px;
	}

	public void setPx(String px) {
		this.px = px;
	}

	/**
	 * 打开条件页面
	 * @return
	 */
	public String showCondition(){
		store_list = storeService.getAllStoreList();
		productKindList = productKindService.getAllProductKindList();
		return "success";
	}
	
	/**
	 * 出入库汇总结果
	 * @return
	 */
	public String getResult(){
		return "success";
	}
	
	/**
	 * 出入库汇总明细
	 * @return
	 */
	public String getResultMx(){
		product = (Product)productService.getProductById(product_id);
		return "success";
	}
	
	/**
	 * 库存数量汇总结果
	 * @return
	 */
	public String getKcNumsResult(){
		productList = kcMxReportService.getKcNumsResults(product_kind, product_name, store_id,state, flag,px);
		return "success";
	}
	
	
	/**
	 * 库存金额 汇总结果
	 * @return
	 */
	public String getKcJesResult(){
		productList = kcMxReportService.getKcJesResults(dj, product_name, store_id,state, flag,px);
		return "success";
	}
	
	
	/**
	 * 明细信息
	 * @return
	 * @throws Exception
	 */
	public String getKcJeMxResults() throws Exception{
		try{
			productList = kcMxReportService.getKcJeMxResults(product_kind, product_name, store_id, state,flag);
			return SUCCESS;
		}catch(Exception e){
			log.error("库存金额汇总明细信息出错，错误原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	/**
	 * 历史库存汇总情况表
	 * @return
	 */
	public String getHisKcResult(){
		productList = kcMxReportService.getHisKcjeResults(product_kind, product_name, "", store_id, cdate);
		return "success";
	}
	
	
	/**
	 * 供应商库存汇总
	 * @return
	 */
	public String getGysKcNumsResult(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String productKind = info.getClient_name();  //分配给供应商的商品类别
		
		productList = kcMxReportService.getGysKcNumsResults(productKind);
		
		return "success";
	}
	
	
	/**
	 * 分仓库存数量汇总结果
	 */
	public String getFckcStatResultList(){
		try{
			productList = kcMxReportService.getProductList(product_kind, product_name, state);
			store_list = kcMxReportService.getStoreList(store_id);
			fckcStatResult = kcMxReportService.getKcStatResult(product_kind, product_name, state, store_id);
			return SUCCESS;
		}catch(Exception e){
			log.error("统计分仓库存数量出错，原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	

	public KcMxReportService getKcMxReportService() {
		return kcMxReportService;
	}

	public void setKcMxReportService(KcMxReportService kcMxReportService) {
		this.kcMxReportService = kcMxReportService;
	}

	public StoreService getStoreService() {
		return storeService;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public List getStore_list() {
		return store_list;
	}

	public void setStore_list(List store_list) {
		this.store_list = store_list;
	}

	public List getProductKindList() {
		return productKindList;
	}

	public void setProductKindList(List productKindList) {
		this.productKindList = productKindList;
	}

	public ProductKindService getProductKindService() {
		return productKindService;
	}

	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getProduct_kind() {
		return product_kind;
	}

	public void setProduct_kind(String product_kind) {
		this.product_kind = product_kind;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public List getProductList() {
		return productList;
	}

	public void setProductList(List productList) {
		this.productList = productList;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setFckcStatResult(Map fckcStatResult) {
		this.fckcStatResult = fckcStatResult;
	}

	public Map getFckcStatResult() {
		return fckcStatResult;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public int getDj() {
		return dj;
	}


	public void setDj(int dj) {
		this.dj = dj;
	}
}
