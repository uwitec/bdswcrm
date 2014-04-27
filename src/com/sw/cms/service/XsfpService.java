package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.XsfpDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.XsfpDkfp;
import com.sw.cms.model.XsfpFpmx;
import com.sw.cms.model.XsfpFpxx;

/**
 * 销售发票管理
 * @author liyt
 *
 */
public class XsfpService {
	
	private XsfpDAO xsfpDao;
	
	/**
	 * 取销售发票列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsfpFpxxPage(String con,int curPage, int rowsPerPage){
		return xsfpDao.getXsfpFpxxPage(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 取发票明细
	 * @param id
	 * @return
	 */
	public List getXsfpFpmxList(String id) {
		return xsfpDao.getXsfpFpmxList(id);
	}
	
	
	/**
	 * 取发票信息
	 * @param id
	 * @return
	 */
	public XsfpFpxx getXsfpFpxx(String id){
		return xsfpDao.getXsfpFpxx(id);
	}
	
	
	/**
	 * 取销售发票待开发票列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsfpDkfpPage(String con , int curPage, int rowsPerPage){
		return xsfpDao.getXsfpDkfpPage(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 取销售发票流水号
	 * @return
	 */
	public String updateXsfpID(){
		return xsfpDao.getXsfpID();
	}
	
	
	/**
	 * 编辑销售发票信息
	 * @param xsfpFpxx
	 * @param fpmxDescs
	 */
	public void updateXsfp(XsfpFpxx xsfpFpxx, List<XsfpFpmx> fpmxDescs) {
		xsfpDao.updateXsfp(xsfpFpxx, fpmxDescs);
		
		if(xsfpFpxx.getState().equals("已提交")) {
			//更新待开发票信息的状态
			if(fpmxDescs != null && fpmxDescs.size() > 0){
				for(XsfpFpmx xsfpFpmx : fpmxDescs) {
					//对应待开发票信息
					XsfpDkfp xsfpDkfp = xsfpDao.getXsfpDkfp(xsfpFpmx.getYw_id());
					if(xsfpDkfp != null) {
						//根据开票金额判断待开发票状态（待开、部分已开、已开）
						if(xsfpFpmx.getKpje_ying() - xsfpFpmx.getKpje_yi() > xsfpFpmx.getKpje_bc()){
							xsfpDkfp.setState("部分已开");
						} else if (xsfpFpmx.getKpje_ying() - xsfpFpmx.getKpje_yi() == xsfpFpmx.getKpje_bc()){
							xsfpDkfp.setState("已开");
						}
						xsfpDkfp.setYkpje(xsfpDkfp.getYkpje() + xsfpFpmx.getKpje_bc());
						xsfpDao.updateXsfpDkfp(xsfpDkfp);
					}
				}
			}
		}
	}
	
	/**
	 * 删除销售发票信息
	 * @param id
	 */
	public void delXsfp(String id){
		xsfpDao.delXsfp(id);
	}
	
	
	/**
	 * 销售发票统计，状态为已提交
	 * @param start_date
	 * @param end_date
	 * @param fplx
	 * @return
	 */
	public List getXsfpList(String start_date, String end_date, String fplx) {
		return xsfpDao.getXsfpList(start_date, end_date, fplx);
	}
	
	
	/**
	 * 销售发票通-明细信息
	 * @param start_date
	 * @param end_date
	 * @param fplx
	 * @return
	 */
	public Map getXsfpMxMap(String start_date, String end_date, String fplx) {
		return xsfpDao.getXsfpMxMap(start_date, end_date, fplx);
	}

	public XsfpDAO getXsfpDao() {
		return xsfpDao;
	}

	public void setXsfpDao(XsfpDAO xsfpDao) {
		this.xsfpDao = xsfpDao;
	}

}
