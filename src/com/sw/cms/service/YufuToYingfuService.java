package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.YufuToYingfuDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.YufuToYingfu;
import com.sw.cms.model.YufuToYingfuDesc;

/**
 * 预付冲应付处理
 * @author liyt
 *
 */
public class YufuToYingfuService {
	
	private YufuToYingfuDAO yufuToYingfuDao;
	private CgfkDAO cgfkDao;
	private YufukDAO yufukDao;
	
	
	/**
	 * 根据查询条件取预付冲应付结算列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getYufuToYingfuList(String con,int curPage, int rowsPerPage){
		return yufuToYingfuDao.getYufuToYingfuList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 更新预收转应收结算信息
	 * @param info
	 * @param descList
	 */
	public void updateYufuToYingfu(YufuToYingfu info,List descList){
		yufuToYingfuDao.updateYufuToYingfu(info, descList);
		
		if(info.getState().equals("已提交")){
			
			//更新进货单付款金额及付款方式
			yufuToYingfuDao.updateJhdFkje(info, descList);
			
			//预付款结算
			yufukDao.updateYufukJsje(info);
		}
	}
	
	
	/**
	 * 根据编号取预付冲应付基本信息
	 * @param id
	 * @return
	 */
	public YufuToYingfu getYufuToYingfu(String id){
		return yufuToYingfuDao.getYufuToYingfu(id);
	}
	
	
	/**
	 * 根据客户编号取应付款列表
	 * @param client_id
	 * @return
	 */
	public List getYingfukByClientId(String client_id){
		List<YufuToYingfuDesc> list = new ArrayList<YufuToYingfuDesc>();
		List yfkList = cgfkDao.getDfkDesc(client_id);
		if(yfkList != null && yfkList.size() > 0){
			for(int i=0;i<yfkList.size();i++){
				Map map = (Map)yfkList.get(i);
				
				double yfje = map.get("yfje")==null?0:((Double)map.get("yfje")).doubleValue();
				
				YufuToYingfuDesc info = new YufuToYingfuDesc();
				
				info.setJhd_id((String)map.get("jhd_id"));
				info.setYingfuje(yfje);
				
				list.add(info);
			}
		}
		return list;
	}
	
	
	/**
	 * 取客户合计预付款金额
	 * @param client_id
	 * @return
	 */
	public double getYufukjeByClientId(String client_id){
		return yufukDao.getYufukByClientId(client_id);
	}
	
	
	/**
	 * 根据单据编号取预付冲应付明细信息
	 * @param id
	 * @return
	 */
	public List getYufuToYingfuDesc(String id){
		return yufuToYingfuDao.getYufuToYingfuDesc(id);
	}
	
	
	/**
	 * 删除预付冲应付
	 * @param id
	 */
	public void delYufuToYingfu(String id){
		yufuToYingfuDao.delYufuToYingfu(id);
	}
	
	
	/**
	 * 更新进货单已付金额及付款状态
	 * @param cgfkDescs
	 */
	public void updateJhdFkje(YufuToYingfu info,List descList){
		yufuToYingfuDao.updateJhdFkje(info, descList);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String updateID() {
		return yufuToYingfuDao.getID();
	}
	
	
	public CgfkDAO getCgfkDao() {
		return cgfkDao;
	}
	public void setCgfkDao(CgfkDAO cgfkDao) {
		this.cgfkDao = cgfkDao;
	}
	public YufukDAO getYufukDao() {
		return yufukDao;
	}
	public void setYufukDao(YufukDAO yufukDao) {
		this.yufukDao = yufukDao;
	}
	public YufuToYingfuDAO getYufuToYingfuDao() {
		return yufuToYingfuDao;
	}
	public void setYufuToYingfuDao(YufuToYingfuDAO yufuToYingfuDao) {
		this.yufuToYingfuDao = yufuToYingfuDao;
	}

}
