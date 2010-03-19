package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.CbtzDAO;

public class CbtzService {
	
	private CbtzDAO cbtzDao;
	
	/**
	 * ���ص��ݶ�Ӧ����Ʒ�б�
	 * @param dj_id
	 * @param dj_type
	 * @return
	 */
	public List getProductList(String dj_id,String dj_type){
		return cbtzDao.getProductList(dj_id, dj_type);
	}
	
	/**
	 * �༭�ɱ���Ϣ
	 * @param id
	 * @param dj_type
	 * @return
	 */
	public Map getProduct(String id,String dj_type){
		return cbtzDao.getProduct(id, dj_type);
	}
	
	
	/**
	 * ���¿��˳ɱ�
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
