package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.DlsDzdDAO;

public class DlsDzdService {
	
	private DlsDzdDAO dlsDzdDao;
	
	/**
	 * 根据查询条件取代理商对账单
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getDlsDzdList(String start_date,String end_date,String client_name){
		return dlsDzdDao.getDlsDzdList(start_date, end_date, client_name);
	}
	
	
	/**
	 * 获取单据明细信息
	 * @param dj_id 单据编号
	 * @param cdid  从单编号
	 * @param xwtype 业务类型
	 * @return
	 */
	public List getDjMxList(String dj_id,String cdid,String xwtype){
		return dlsDzdDao.getDjMxList(dj_id, cdid, xwtype);
	}
	
	
	/**
	 * 取客户期初信息
	 * @param client_name
	 * @param qc_date
	 * @return
	 */
	public Map getDlsQcInfo(String client_name,String cdate){
		return dlsDzdDao.getDlsQcInfo(client_name, cdate);
	}


	public DlsDzdDAO getDlsDzdDao() {
		return dlsDzdDao;
	}


	public void setDlsDzdDao(DlsDzdDAO dlsDzdDao) {
		this.dlsDzdDao = dlsDzdDao;
	}

}
