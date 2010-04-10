package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.XstjClientDAO;

public class XstjClientService {
	
	private XstjClientDAO xstjClientDao;
	
	/**
	 * 取销售单列表
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjClientDao.getXsdList(start_date, end_date, xsry_id, client_name,dj_id);
	}
	
	
	/**
	 * 根据销售单编号取销售明细信息
	 * @param xsd_id
	 * @return
	 */
	public List getXsdMxList(String xsd_id){
		return xstjClientDao.getXsdMxList(xsd_id);
	}
	
	
	/**
	 * 根据查询条件取零售单列表
	 * @param start_date 开始时间
	 * @param end_date  截止时间
	 * @param client_id  客户名称
	 * @return
	 */
	public List getLsdList(String start_date,String end_date,String xsry_id,String dj_id){
		return xstjClientDao.getLsdList(start_date, end_date, xsry_id,dj_id);
	}
	
	
	/**
	 * 根据零售单编号取零售单元销售明细
	 * @param lsd_id
	 * @return
	 */
	public List getLsdMxList(String lsd_id){
		return xstjClientDao.getLsdMxList(lsd_id);
	}
	
	
	/**
	 * 取退货单列表
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getThdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjClientDao.getThdList(start_date, end_date, xsry_id, client_name,dj_id);
	}
	
	
	/**
	 * 根据退货单编号取退货单明细
	 * @param thd_id
	 * @return
	 */
	public List getThdMxList(String thd_id){
		return xstjClientDao.getThdMxList(thd_id);
	}	

	/**
	 * 取客户销售汇总总金额
	 * 包括销售订单、退货单
	 * 总金额=销售订单合计成交金额-退货单金额
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @param client_name
	 * @param dj_id
	 * @return
	 */
	public double getClientZje(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjClientDao.getClientZje(start_date, end_date, xsry_id, client_name, dj_id);
	}
	
	/**
	 * 汇总所有零售单金额做为一条记录
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @param dj_id
	 * @return
	 */
	public double getLsdZje(String start_date,String end_date,String xsry_id,String dj_id){
		return xstjClientDao.getLsdZje(start_date, end_date, xsry_id, dj_id);
	}
	
	
	/**
	 * 客户销售汇总
	 * 2010-04-10
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @param dj_id
	 * @return
	 */
	public List getXstjClientResult(String start_date, String end_date,String client_name, String xsry_id, String dj_id){
		return xstjClientDao.getXstjClientResult(start_date, end_date, client_name, xsry_id, dj_id);
	}
	
	public XstjClientDAO getXstjClientDao() {
		return xstjClientDao;
	}

	public void setXstjClientDao(XstjClientDAO xstjClientDao) {
		this.xstjClientDao = xstjClientDao;
	}
	
	

}
