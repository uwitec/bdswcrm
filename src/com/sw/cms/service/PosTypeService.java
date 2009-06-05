package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.PosTypeDAO;
import com.sw.cms.model.PosType;

/**
 * ˢ��POS���趨
 * @author liyt
 *
 */
public class PosTypeService {
	
	private PosTypeDAO posTypeDao;
	
	/**
	 * ȡˢ��Pos���趨�б���Ϣ
	 * @return
	 */
	public List getPosTypeList(){
		return posTypeDao.getPosTypeList();
	}
	
	
	/**
	 * ����ˢ��Pos���趨��Ϣ
	 * @param posType
	 */
	public void updatePosType(PosType posType){
		posTypeDao.updatePosType(posType);
	}
	
	
	/**
	 * ���ݱ��ȡPOS���趨��Ϣ
	 * @param id
	 * @return
	 */
	public PosType getPosType(String id){
		return posTypeDao.getPosType(id);
	}
	
	
	/**
	 * ɾ��ˢ��POS����Ϣ
	 * @param id
	 */
	public void delPosType(String id){
		posTypeDao.delPosType(id);
	}
	

	public PosTypeDAO getPosTypeDao() {
		return posTypeDao;
	}

	public void setPosTypeDao(PosTypeDAO posTypeDao) {
		this.posTypeDao = posTypeDao;
	}

}
