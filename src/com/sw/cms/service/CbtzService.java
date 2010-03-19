package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.CbtzDAO;

public class CbtzService {
	
	private CbtzDAO cbtzDao;
	
	/**
	 * 返回单据对应的商品列表
	 * @param dj_id
	 * @param dj_type
	 * @return
	 */
	public List getProductList(String dj_id,String dj_type){
		return cbtzDao.getProductList(dj_id, dj_type);
	}
	
	/**
	 * 编辑成本信息
	 * @param id
	 * @param dj_type
	 * @return
	 */
	public Map getProduct(String id,String dj_type){
		return cbtzDao.getProduct(id, dj_type);
	}
	
	
	/**
	 * 更新考核成本
	 * @param id
	 * @param dj_type
	 * @param kh_cbj
	 */
	public void updateProduct(String id,String dj_type,double kh_cbj,double ygcbj,double lsxj,double gf,double ds){
		cbtzDao.updateProduct(id, dj_type, kh_cbj,ygcbj,lsxj,gf,ds);
	}

	public CbtzDAO getCbtzDao() {
		return cbtzDao;
	}

	public void setCbtzDao(CbtzDAO cbtzDao) {
		this.cbtzDao = cbtzDao;
	}

}
