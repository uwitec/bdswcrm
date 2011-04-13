package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.YushouToYingshouDAO;
import com.sw.cms.dao.YushoukDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.XsskDesc;
import com.sw.cms.model.YushouToYingshou;
import com.sw.cms.model.YushouToYingshouDesc;

/**
 * 预收转应收
 * @author liyt
 *
 */
public class YushouToYingshouService {
	
	private YushouToYingshouDAO yushouToYingshouDao;
	private XsskDAO xsskDao;
	private YushoukDAO yushoukDao;
	
	/**
	 * 根据查询条件取预收转应收结算列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getYushouToYingshouList(String con,int curPage, int rowsPerPage){
		return yushouToYingshouDao.getYushouToYingshouList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 更新预收转应收结算信息
	 * @param info
	 * @param descList
	 */
	public void updateYushouToYingshou(YushouToYingshou info,List descList){
		yushouToYingshouDao.updateYushouToYingshou(info, descList);
		
		//预收转应收款结算
		if(info.getState().equals("已提交")){
			//销售单收款结算
			yushouToYingshouDao.updateXsdFk(info, descList);
			
			//预收款结算
			yushoukDao.updateYushoukJsje(info);
		}
	}
	
	
	/**
	 * 根据编号取应预收转应收基本信息
	 * @param id
	 * @return
	 */
	public YushouToYingshou getYushouToYingshou(String id){
		return yushouToYingshouDao.getYushouToYingshou(id);
	}
	
	
	/**
	 * 取客户应收款列表
	 * @param client_id
	 * @return
	 */
	public List getYingshoukByClientId(String client_id){
		List<YushouToYingshouDesc> list = new ArrayList<YushouToYingshouDesc>();
		List yskList = xsskDao.getYskByClientId(client_id);
		if(yskList != null && yskList.size() > 0){
			for(int i=0;i<yskList.size();i++){
				Map map = (Map)yskList.get(i);
				
				double ysk = map.get("ysk")==null?0:((Double)map.get("ysk")).doubleValue();
				
				YushouToYingshouDesc info = new YushouToYingshouDesc();
				
				info.setXsd_id((String)map.get("xsd_id"));
				info.setYingshouje(ysk);
				
				list.add(info);
			}
		}
		return list;
	}
	
	
	/**
	 * 取客户合计预收款金额
	 * @param client_id
	 * @return
	 */
	public double getYishoukjeByClientId(String client_id){
		return yushoukDao.getYushoukByClientId(client_id);
	}
	
	
	/**
	 * 根据单据编号取预收转应收明细信息明细信息
	 * @param id
	 * @return
	 */
	public List getYushouToYingshouDesc(String id){
		return yushouToYingshouDao.getYushouToYingshouDesc(id);
	}
	
	
	/**
	 * 删除预收转应收
	 * @param id
	 */
	public void delYushouToYingshou(String id){
		yushouToYingshouDao.delYushouToYingshou(id);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String updateID() {
		return yushouToYingshouDao.getID();
	}	
	
	
	/**
	 * 判断提交的预收冲应收明细中是否存在与其他销售收款单冲突，如果存在返回编号，不存在返回空
	 * @param YushouToYingshou
	 * @param descList
	 * @return
	 */
	public String getExistYushouToYingshouDesc(YushouToYingshou info,List descList){
		String temp = "";
		
		String client_name = info.getClient_name();
		String yw_id = info.getId();
		
		if(descList != null && descList.size()>0){
			for(int i =0;i<descList.size();i++){
				YushouToYingshouDesc desc = (YushouToYingshouDesc)descList.get(i);
				if(desc != null && desc.getBcjs() != 0){
					String xsd_id = desc.getXsd_id();
					double bcjs=desc.getBcjs();
					if(yushouToYingshouDao.isXsskDescExist(yw_id, xsd_id, client_name,bcjs)){
						//如果存在冲突，则记录相应进货单编号
						if(temp.equals("")){
							temp = xsd_id;
						}else{
							temp += "," + xsd_id;
						}
					}
				}
			}
		}
		
		return temp;
	}
	
	public YushouToYingshouDAO getYushouToYingshouDao() {
		return yushouToYingshouDao;
	}
	public void setYushouToYingshouDao(YushouToYingshouDAO yushouToYingshouDao) {
		this.yushouToYingshouDao = yushouToYingshouDao;
	}
	public XsskDAO getXsskDao() {
		return xsskDao;
	}
	public void setXsskDao(XsskDAO xsskDao) {
		this.xsskDao = xsskDao;
	}


	public YushoukDAO getYushoukDao() {
		return yushoukDao;
	}


	public void setYushoukDao(YushoukDAO yushoukDao) {
		this.yushoukDao = yushoukDao;
	}

}
