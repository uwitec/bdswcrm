package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.SjzdDAO;
import com.sw.cms.model.SjzdJbxx;
import com.sw.cms.model.SjzdXmxx;

public class SjzdService {
	
	private SjzdDAO sjzdDao;
	
	/**
	 * ��ȡ�����ֵ�����ֶ�
	 * @return
	 */
	public List getAllSjzdJbxx(){
		return sjzdDao.getAllSjzdJbxx();
	}
	
	/**
	 * ��ȡ�ֵ������Ϣ
	 * @param zd_id
	 * @return
	 */
	public SjzdJbxx getSjzdJbxxByZdId(String zd_id){
		return sjzdDao.getSjzdJbxxByZdId(zd_id);
	}
	
	
	/**
	 * �����ֵ���ȡ�����ֵ���Ϣ
	 * @param zd_id
	 * @return
	 */
	public List getZdxmByZdId(String zd_id){
		return sjzdDao.getZdxmByZdId(zd_id);
	}
	
	
	/**
	 * �����ֵ���Ϣ
	 * @param sjzdXmxx
	 */
	public void updateZdxm(SjzdXmxx sjzdXmxx){
		if(sjzdXmxx.getXm_id() == 0){
			//�ֵ������--���
			sjzdDao.saveZdxm(sjzdXmxx);
		}else{
			//�ֵ������--����
			sjzdDao.updateZdxm(sjzdXmxx);
		}		
	}
	
	
	/**
	 * ɾ�ֵ���Ϣ
	 * @param xm_id
	 */
	public void delZdxm(String xm_id){
		sjzdDao.delZdxm(xm_id);
	}
	
	/**
	 * ������Ŀ���ȡ�ֵ���Ϣ
	 * @param xm_id
	 */
	public SjzdXmxx getZdxm(String xm_id){
		return sjzdDao.getZdxm(xm_id);
	}

	
	/**
	 * ���ؼ�����λ����
	 * @return
	 */
	public String[] getSjzdXmxxByZdId(String zd_id) {
		String[] jldw = null;
		List list = sjzdDao.getZdxmByZdId(zd_id);
		if(list != null && list.size()>0){
			jldw = new String[list.size()];
			for(int i=0;i<list.size();i++){
				jldw[i] = ((SjzdXmxx)list.get(i)).getXm_name();
			}
		}
		return jldw;
	}
	

	public SjzdDAO getSjzdDao() {
		return sjzdDao;
	}
	
	public void setSjzdDao(SjzdDAO sjzdDao) {
		this.sjzdDao = sjzdDao;
	}

}
