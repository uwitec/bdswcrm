package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.KlHzReportService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.StoreService;

public class KlHzReportAction extends BaseAction
{
    private KlHzReportService klHzReportService;
    private StoreService storeService;
	private ProductKindService productKindService;
	
	private List store_list = new ArrayList();
	private List productKindList = new ArrayList();
	private List productList=new ArrayList();
  
	private String product_kind = "";
	private String product_name = "";
	private String store_id = "";
	private String kl_day = "";

	public String showCondition(){
		store_list = storeService.getAllStoreList();
		productKindList = productKindService.getAllProductKindList();
		return "success";
	}
	
	/**
	 * ¿âÁä»ã×Ü½á¹û
	 * @return
	 */
	public String getResult(){
		productList = klHzReportService.getKlResults(product_kind, product_name, store_id,kl_day);
		return "success";
	}
	
  
public KlHzReportService getKlHzReportService() {
	return klHzReportService;
}

public void setKlHzReportService(KlHzReportService klHzReportService) {
	this.klHzReportService = klHzReportService;
}

public List getProductKindList() {
	return productKindList;
}

public void setProductKindList(List productKindList) {
	this.productKindList = productKindList;
}

public List getStore_list() {
	return store_list;
}

public void setStore_list(List store_list) {
	this.store_list = store_list;
}

public ProductKindService getProductKindService() {
	return productKindService;
}

public void setProductKindService(ProductKindService productKindService) {
	this.productKindService = productKindService;
}

public StoreService getStoreService() {
	return storeService;
}

public void setStoreService(StoreService storeService) {
	this.storeService = storeService;
}

public String getKl_day() {
	return kl_day;
}

public void setKl_day(String kl_day) {
	this.kl_day = kl_day;
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
}
