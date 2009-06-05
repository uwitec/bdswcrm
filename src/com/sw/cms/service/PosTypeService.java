package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.PosTypeDAO;
import com.sw.cms.model.PosType;

/**
 * 刷卡POS机设定
 * @author liyt
 *
 */
public class PosTypeService {
	
	private PosTypeDAO posTypeDao;
	
	/**
	 * 取刷卡Pos机设定列表信息
	 * @return
	 */
	public List getPosTypeList(){
		return posTypeDao.getPosTypeList();
	}
	
	
	/**
	 * 保存刷卡Pos机设定信息
	 * @param posType
	 */
	public void updatePosType(PosType posType){
		posTypeDao.updatePosType(posType);
	}
	
	
	/**
	 * 根据编号取POS机设定信息
	 * @param id
	 * @return
	 */
	public PosType getPosType(String id){
		return posTypeDao.getPosType(id);
	}
	
	
	/**
	 * 删除刷卡POS机信息
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
