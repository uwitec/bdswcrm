package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.SerialNumPd;

public class SerialNumService {

	private SerialNumDAO serialNumDao;

	/**
	 * 根据查询条件取序列号列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getSerialNumPage(String con, int curPage, int rowsPerPage){
		return serialNumDao.getSerialNumPage(con, curPage, rowsPerPage);
	}
	
	/**
	 * <p>判断序列号是否存在</p>
	 * @param serial_num 序列号
	 * @return 存在返回true;不存在返回false
	 */
	public boolean chkSerialNum(String serial_num){
		return serialNumDao.chkSerialNum(serial_num);
	}
	
	
	/**
	 * <p>保存、更新序列号</p>
	 * <p>如果序列号存在则更新</p>
	 * <p>如果序列号不存在则插入</p>
	 * @param serialNumMng
	 */
	public void updateSerialNumState(SerialNumMng serialNumMng){
		serialNumDao.updateSerialNumState(serialNumMng);
	}
	
	
	/**
	 * 编辑序列号
	 * @param serial_num
	 * @return
	 */
	public SerialNumMng editSerialNumMng(String serial_num){
		return serialNumDao.editSerialNumMng(serial_num);
	}
	
	
	/**
	 * 删除序列号
	 * @param serial_num
	 */
	public void delSerialNum(String serial_num){
		serialNumDao.delSerialNum(serial_num);
	}
	
	
	/**
	 * 查询序列号流程
	 * @param serial_num
	 * @return
	 */
	public List getSerialFlow(String serial_num){
		return serialNumDao.getSerialFlow(serial_num);
	}
	
	
	/**
	 * 查询序列号当前状态
	 * 对应商品相关信息
	 * @param serial_num
	 * @return
	 */
	public Map getSerialState(String serial_num){
		return serialNumDao.getSerialState(serial_num);
	}
	
	
	/**
	 * 根据库房ID取序列号列表
	 * @param store_id
	 * @return
	 */
	public List getSerialNumMngListByStoreId(String store_id){
		return serialNumDao.getSerialNumMngListByStoreId(store_id);
	}
	
	/**
	 * 保存序列号盘点结果
	 * @param info
	 */
	public void insertSerialNumPd(SerialNumPd info){
		serialNumDao.insertSerialNumPd(info);
	}
	
	/**
	 * 盘点是否以保存
	 * @param cdate
	 * @param store_id
	 * @return
	 */
	public boolean isSerialNumPdExist(String cdate,String store_id){
		return serialNumDao.isSerialNumPdExist(cdate, store_id);
	}
	
	/**
	 * 序列号盘点记录
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getSerialNumPdPage(String con,int curPage, int rowsPerPage){
		return serialNumDao.getSerialNumPdPage(con, curPage, rowsPerPage);
	}
	
	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}

	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}	
}
