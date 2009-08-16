package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.FyTypeDAO;
import com.sw.cms.model.FyType;

public class FyTypeService {
	
	private FyTypeDAO fyTypeDao;
	
	
	/**
	 * 取费用申请列表
	 * @return
	 */
	public List getFyTypeList(){
		return fyTypeDao.getFyTypeList();
	}
	
	
	/**
	 * 根据ID取费用类别名称
	 * @param id
	 * @return
	 */
	public FyType getFyType(String id){
		return fyTypeDao.getFyType(id);
	}
	
	
	/**
	 * 更新费用类型，存在更新，不存在插入
	 * @param fyType
	 */
	public void updateFyType(FyType fyType){
		fyTypeDao.updateFyType(fyType);
	}
	
	
	
	/**
	 * 删除费用类型
	 * @param id
	 */
	public void delFyType(String id){
		fyTypeDao.delFyType(id);
	}
	
	
	/**
	 * 判断一个类型下面是否有子类
	 * @param id
	 * @return
	 */
	public boolean isChildren(String id){
		return fyTypeDao.isChildren(id);
	}
	
	
	/**
	 * 判断费用类型是否可以删除<BR>
	 * 发生业务往来的费用不能删除
	 * @param id
	 * @return boolean true:可以；false:不可以
	 */
	public boolean isCanDel(String id){
		return fyTypeDao.isCanDel(id);
	}
	

	public FyTypeDAO getFyTypeDao() {
		return fyTypeDao;
	}

	public void setFyTypeDao(FyTypeDAO fyTypeDao) {
		this.fyTypeDao = fyTypeDao;
	}

}
