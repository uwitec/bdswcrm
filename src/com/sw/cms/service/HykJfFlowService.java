package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.HykJfFlowDAO;
import com.sw.cms.model.Hykda;
import com.sw.cms.model.Hykzz;

public class HykJfFlowService {
	
	private HykJfFlowDAO hykJfFlowDao;
	
	/**
	 * ���ݻ�Ա���Ż������ֻ��ţ����Ƿ����ڲ�ѯ��Ա������ˮ
	 * @param start_date
	 * @param end_date
	 * @param con_vl
	 * @return
	 */
	public List getHyjfFlowList(String start_date,String end_date,String con_vl){
		return hykJfFlowDao.getHyjfFlowList(start_date, end_date, con_vl);
	}
	
	
	/**
	 * ���ݻ�Ա���/�ֻ���ȡ��Ա��Ϣ�б�
	 * @param hyk_id
	 * @return
	 */
	public List getHykdaList(String con_vl){
		return hykJfFlowDao.getHykdaList(con_vl);
	}
	
	
	/**
	 * ���ݻ�Ա����ȡ��Ա��������Ϣ����ʼ���֣�
	 */
	public Hykzz getHykzzInfo(String hyk_id){
		return hykJfFlowDao.getHykzzInfo(hyk_id);
	}
	
	/**
	 * ����ʱ��μ���Ա�����ȡ����
	 * @param hyk_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getHyjf(String hyk_id,String start_date,String end_date){
		return hykJfFlowDao.getHyjf(hyk_id, start_date, end_date);
	}
	

	public HykJfFlowDAO getHykJfFlowDao() {
		return hykJfFlowDao;
	}

	public void setHykJfFlowDao(HykJfFlowDAO hykJfFlowDao) {
		this.hykJfFlowDao = hykJfFlowDao;
	}

}
