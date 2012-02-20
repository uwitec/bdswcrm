package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.service.StorexsHzService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.StoreService;
/**
 * 仓库销售汇总
 * 
 * @author zhj
 * 
 */
public class StorexsHzAction extends BaseAction {

	private UserService userService;
	private StorexsHzService storexsHzService;
	private SjzdService sjzdService;
	private StoreService storeService;
	
	private List user_list = new ArrayList();
	private List productKindList = new ArrayList();
	private List resultList = new ArrayList();
	private List store_list = new ArrayList();
	
	private String product_kind = "";
	private String product_name = "";
	private String product_xh = "";
	private String start_date = "";
	private String end_date = "";
	private String product_id = "";
	private String store_id = "";
	private String xsry = "";
	
	/**
	 * 打开仓库销售条件页面
	 * @return
	 */
	public String showStoreCondition(){
		store_list = storeService.getAllStoreList();
		return "success";
	}
	
	/**
	 * 仓库销售统计结果
	 * 
	 * @return
	 */
	public String getStorexshzTjResult() {
		resultList = storexsHzService.getStorexshzTjResult(product_kind,
				product_name, product_xh, start_date, end_date, store_id);
		return "success";
	}
	
	/**
	 * 统计结果(销售人员汇总)
	 * @return
	 */
	public String getStoreResultMx(){
		try{
			resultList = storexsHzService.getStoreResultMx(store_id, start_date, end_date, product_kind, product_name, product_xh);
			return SUCCESS;
		}catch(Exception e){
			log.error("仓库销售汇总出错，错误原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	/**
	 * 统计结果（商品汇总）
	 * @return
	 */
	public String getStorexsProductMxResult(){
		try{
			resultList = storexsHzService.getStorexsProductMxResult(xsry, start_date, end_date, store_id, product_kind, product_name);
			return SUCCESS;
		}catch(Exception e){
			log.error("仓库销售汇总--商品汇总出错，错误原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	public List getUser_list() {
		return user_list;
	}

	public void setUser_list(List user_list) {
		this.user_list = user_list;
	}

	public List getStore_list() {
		return store_list;
	}

	public void setStore_list(List store_list) {
		this.store_list = store_list;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List getProductKindList() {
		return productKindList;
	}

	public void setProductKindList(List productKindList) {
		this.productKindList = productKindList;
	}

	public StorexsHzService getStorexsHzService() {
		return storexsHzService;
	}

	public void setStorexsHzService(StorexsHzService storexsHzService) {
		this.storexsHzService = storexsHzService;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
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

	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	
	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	
	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}


	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}


	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}
	
	public StoreService getStoreService() {
		return storeService;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}
    
	public String getXsry() {
		return xsry;
	}
	public void setXsry(String xsry) {
		this.xsry = xsry;
	}
}
