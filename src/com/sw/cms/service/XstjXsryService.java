package com.sw.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.XstjXsryDAO;
import com.sw.cms.util.StringUtils;

public class XstjXsryService {
	
	private XstjXsryDAO xstjXsryDao;
	
	/**
	 * 取销售单列表
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjXsryDao.getXsdList(start_date, end_date, xsry_id,client_name,dj_id);
	}
	
	
	/**
	 * 根据销售单编号取销售明细信息
	 * @param xsd_id
	 * @return
	 */
	public List getXsdMxList(String xsd_id){
		return xstjXsryDao.getXsdMxList(xsd_id);
	}
	
	
	/**
	 * 根据查询条件取零售单列表
	 * @param start_date 开始时间
	 * @param end_date  截止时间
	 * @param client_id  客户名称
	 * @return
	 */
	public List getLsdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjXsryDao.getLsdList(start_date, end_date, xsry_id,client_name,dj_id);
	}
	
	
	/**
	 * 根据零售单编号取零售单元销售明细
	 * @param lsd_id
	 * @return
	 */
	public List getLsdMxList(String lsd_id){
		return xstjXsryDao.getLsdMxList(lsd_id);
	}
	
	
	/**
	 * 取退货单列表
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getThdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjXsryDao.getThdList(start_date, end_date, xsry_id,client_name,dj_id);
	}
	
	
	/**
	 * 根据退货单编号取退货单明细
	 * @param thd_id
	 * @return
	 */
	public List getThdMxList(String thd_id){
		return xstjXsryDao.getThdMxList(thd_id);
	}	
	
	
	/**
	 * 业务员毛利汇总(销售收入、成本、考核成本)
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public HashMap<String,Double> getMlHz(String xsry_id,String start_date,String end_date){
		return xstjXsryDao.getMlHz(xsry_id, start_date, end_date);
	}
	
	
	/**
	 * 业务员考核毛利汇总
	 * @param start_date   开始时间
	 * @param end_date     结束时间
	 * @param dept_id      部门编号
	 * @param user_id      销售人员
	 * @return
	 */
	public List getYwymlHz(String start_date,String end_date,String dept_id,String user_id){
		return xstjXsryDao.getYwymlHz(start_date, end_date, dept_id, user_id);
	}
	
	
	/**
	 * 业务员毛利汇总明细
	 * @param start_date
	 * @param end_date
	 * @param user_id
	 * @return
	 */
	public List getYwymlMx(String start_date,String end_date,String user_id){
		return xstjXsryDao.getYwymlMx(start_date, end_date, user_id);
	}
	
	
	/**
	 * 业务员提成统计
	 * @param start_date
	 * @param end_date
	 * @param dept_id
	 * @param user_id
	 * @return
	 */
	public Map getYwytcHz(String start_date,String end_date,String dept_id,String user_id){
		
		List list = xstjXsryDao.getYwytcHz(start_date, end_date, dept_id, user_id);
		Map map = new HashMap();
		
		try{
			double temp_khml = 0;
			double temp_jbtc = 0;
			double temp_blds = 0;
			double temp_jeds = 0;
			double temp_cxjl = 0;
			
			
			
			if(list != null && list.size() > 0){
				for(int i=0;i<list.size();i++){
					Map resultMap = (Map)list.get(i);				
					String xsry = StringUtils.nullToStr(resultMap.get("xsry"));
					String real_name = StringUtils.nullToStr(resultMap.get("real_name"));
					String dept = StringUtils.nullToStr(resultMap.get("dept"));		
					String yw_type = StringUtils.nullToStr(resultMap.get("yw_type"));
					
					double khml = resultMap.get("khml") == null?0:((Double)resultMap.get("khml")).doubleValue();   //考核毛利
					double jbtc = resultMap.get("jbtc") == null?0:((Double)resultMap.get("jbtc")).doubleValue();   //基本提成
					double blds = resultMap.get("blds") == null?0:((Double)resultMap.get("blds")).doubleValue();   //比例点杀
					double jeds = resultMap.get("jeds") == null?0:((Double)resultMap.get("jeds")).doubleValue();   //金额点杀
					double cxjl = resultMap.get("cxjl") == null?0:((Double)resultMap.get("cxjl")).doubleValue();   //超限奖励
					
					//如果是退货单    超限奖励大于时取0，比例点杀大于0时取0
					//其它单据零售单、销售订单，   超限奖励小于0时取0，比例点杀小于0时取0
					if(yw_type.equals("退货单")){
						if(cxjl > 0) cxjl = 0;
						if(blds > 0) blds = 0;
					}else{
						if(cxjl < 0) cxjl = 0;
						if(blds < 0) blds = 0;
					}
					
					Map mp = (Map)map.get(xsry);
					if(mp!=null && mp.size()>0){		
						temp_khml = (mp.get("khml") == null?0:((Double)mp.get("khml")).doubleValue()) + khml;   //考核毛利
						temp_jbtc = (mp.get("jbtc") == null?0:((Double)mp.get("jbtc")).doubleValue()) + jbtc;   //基本提成
						temp_blds = (mp.get("blds") == null?0:((Double)mp.get("blds")).doubleValue()) + blds;   //比例点杀
						temp_jeds = (mp.get("jeds") == null?0:((Double)mp.get("jeds")).doubleValue()) + jeds;   //金额点杀
						temp_cxjl = (mp.get("cxjl") == null?0:((Double)mp.get("cxjl")).doubleValue()) + cxjl;   //超限奖励
					}else{
						temp_khml = khml;
						temp_jbtc = jbtc;
						temp_blds = blds;
						temp_jeds = jeds;
						temp_cxjl = cxjl;
					}
					
					Map tempMap = new HashMap();
					tempMap.put("xsry", xsry);
					tempMap.put("real_name", real_name);
					tempMap.put("dept", dept);
					tempMap.put("yw_type", yw_type);
					tempMap.put("khml", temp_khml);
					tempMap.put("jbtc", temp_jbtc);
					tempMap.put("blds", temp_blds);
					tempMap.put("jeds", temp_jeds);
					tempMap.put("cxjl", temp_cxjl);
					
					map.put(xsry, tempMap);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 业务员提成统计--明细
	 * @param start_date
	 * @param end_date
	 * @param dept_id
	 * @param user_id
	 * @return
	 */
	public List getYwytcMx(String start_date,String end_date,String user_id){
		return xstjXsryDao.getYwytcMx(start_date, end_date, user_id);
	}
	

	public XstjXsryDAO getXstjXsryDao() {
		return xstjXsryDao;
	}

	public void setXstjXsryDao(XstjXsryDAO xstjXsryDao) {
		this.xstjXsryDao = xstjXsryDao;
	}

}
