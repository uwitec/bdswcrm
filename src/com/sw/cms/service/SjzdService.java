package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.SjzdDAO;
import com.sw.cms.model.SjzdJbxx;
import com.sw.cms.model.SjzdXmxx;

public class SjzdService {
	
	private SjzdDAO sjzdDao;
	
	/**
	 * 获取所有字典基本字段
	 * @return
	 */
	public List getAllSjzdJbxx(){
		return sjzdDao.getAllSjzdJbxx();
	}
	
	/**
	 * 获取字典基本信息
	 * @param zd_id
	 * @return
	 */
	public SjzdJbxx getSjzdJbxxByZdId(String zd_id){
		return sjzdDao.getSjzdJbxxByZdId(zd_id);
	}
	
	
	/**
	 * 根据字典编号取所有字典信息
	 * @param zd_id
	 * @return
	 */
	public List getZdxmByZdId(String zd_id){
		return sjzdDao.getZdxmByZdId(zd_id);
	}
	
	
	/**
	 * 更新字典信息
	 * @param sjzdXmxx
	 */
	public void updateZdxm(SjzdXmxx sjzdXmxx){
		if(sjzdXmxx.getXm_id() == 0){
			//字典项不存在--添加
			sjzdDao.saveZdxm(sjzdXmxx);
		}else{
			//字典项存在--更新
			sjzdDao.updateZdxm(sjzdXmxx);
		}		
	}
	
	
	/**
	 * 删字典信息
	 * @param xm_id
	 */
	public void delZdxm(String xm_id){
		sjzdDao.delZdxm(xm_id);
	}
	
	/**
	 * 根据项目编号取字典信息
	 * @param xm_id
	 */
	public SjzdXmxx getZdxm(String xm_id){
		return sjzdDao.getZdxm(xm_id);
	}

	
	/**
	 * 返回计量单位数组
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
