package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.GainTblDAO;

public class GainTblService {
	
	private GainTblDAO gainTblDao;
	
	/**
	 * 统计主营业务收入<BR>
	 * 所有库存商品的销售收入（销售-退货）
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statZyywSr(String ny){
		return gainTblDao.statZyywSr(ny);
	}
	
	
	/**
	 * 统计主营业务成本<BR>
	 * 销售所有库存商品合计采购成本（销售-退货）
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statZyywCb(String ny){
		return gainTblDao.statZyywCb(ny);
	}
	
	
	/**
	 * 统计其它业务收入
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statQtywSr(String ny){
		return gainTblDao.statQtywSr(ny);
	}
	
	
	/**
	 * 统计其它业务成本
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statQtywCb(String ny){
		return gainTblDao.statQtywCb(ny);
	}
	
	
	/**
	 * 营业外收入
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statYywSr(String ny){
		return gainTblDao.statYywSr(ny);
	}
	
	
	/**
	 * 营业外支出<BR>
	 * 取自一般费用表<BR>
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statYywZc(String ny){
		return gainTblDao.statYywZc(ny);
	}
	
	
	/**
	 * 商品报溢收入
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statSpbySr(String ny){
		return gainTblDao.statSpbySr(ny);
	}
	
	
	/**
	 * 商品报损支出
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statSpbsZc(String ny){
		return gainTblDao.statSpbsZc(ny);
	}
	
	
	/**
	 * 往来调帐收入<BR>
	 * 应收加+应付减<BR>
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statWltzSr(String ny){
		return gainTblDao.statWltzSr(ny);
	}
	
	
	/**
	 * 往来调账支出<BR>
	 * 应收减+应付加<BR>
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statWltzZc(String ny){
		return gainTblDao.statWltzZc(ny);
	}
	
	
	/**
	 * 待摊费用
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statDtfy(String ny){
		return gainTblDao.statDtfy(ny);
	}


	public GainTblDAO getGainTblDao() {
		return gainTblDao;
	}


	public void setGainTblDao(GainTblDAO gainTblDao) {
		this.gainTblDao = gainTblDao;
	}

}
