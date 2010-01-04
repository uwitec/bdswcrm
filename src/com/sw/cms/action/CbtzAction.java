package com.sw.cms.action;

import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.CbtzService;

public class CbtzAction extends BaseAction {
	
	private CbtzService cbtzService;
	
	private List productList = null;
	private Map productMap = null;
	
	private String id = "";
	private String dj_id = "";
	private String dj_type = "";
	
	private double kh_cbj = 0;
	private double lsxj = 0;
	private double basic_ratio = 0;
	private double ds = 0;
	
	
	/**
	 * 取查询结果
	 * @return
	 */
	public String getResults(){
		try{			
			if(dj_id.equals("") || dj_type.equals("")){
				return SUCCESS;
			}			
			productList = cbtzService.getProductList(dj_id, dj_type);			
			return SUCCESS;
		}catch(Exception e){
			log.error("查询调整考核成本价商品列表出错，原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 编辑商品信息
	 * @return
	 */
	public String edit(){
		try{
			productMap = cbtzService.getProduct(id, dj_type);
			return SUCCESS;
		}catch(Exception e){
			log.error("查询调整考核成本价商品列表出错，原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 更新商品考核成本价
	 * @return
	 */
	public String update(){
		try{
			cbtzService.updateProduct(id, dj_type, kh_cbj,lsxj,basic_ratio,ds);
			return SUCCESS;
		}catch(Exception e){
			log.error("更新商品考核成本价，原因：" + e.getMessage());
			return ERROR;
		}
	}
	

	public CbtzService getCbtzService() {
		return cbtzService;
	}

	public void setCbtzService(CbtzService cbtzService) {
		this.cbtzService = cbtzService;
	}

	public String getDj_id() {
		return dj_id;
	}

	public void setDj_id(String dj_id) {
		this.dj_id = dj_id;
	}

	public String getDj_type() {
		return dj_type;
	}

	public void setDj_type(String dj_type) {
		this.dj_type = dj_type;
	}

	public List getProductList() {
		return productList;
	}

	public void setProductList(List productList) {
		this.productList = productList;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Map getProductMap() {
		return productMap;
	}


	public void setProductMap(Map productMap) {
		this.productMap = productMap;
	}


	public double getKh_cbj() {
		return kh_cbj;
	}


	public void setKh_cbj(double kh_cbj) {
		this.kh_cbj = kh_cbj;
	}


	public double getLsxj() {
		return lsxj;
	}


	public void setLsxj(double lsxj) {
		this.lsxj = lsxj;
	}


	public double getBasic_ratio() {
		return basic_ratio;
	}


	public void setBasic_ratio(double basicRatio) {
		basic_ratio = basicRatio;
	}


	public double getDs() {
		return ds;
	}


	public void setDs(double ds) {
		this.ds = ds;
	}

}
