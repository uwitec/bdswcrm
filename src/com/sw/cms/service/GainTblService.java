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
	 * 营业费用<BR>
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
	 * 财务费用<BR>
	 * 数据来源，一般费用表（qtzc)<BR>
	 * type=02
	 * @param ny
	 * @return
	 */
	public Map statCwfy(String ny){
		return gainTblDao.statCwfy(ny);
	}
	
	
	/**
	 * 管理费用<BR>
	 * 数据来源，一般费用表（qtzc)<BR>
	 * type=02
	 * @param ny
	 * @return
	 */
	public Map statGlfy(String ny){
		return gainTblDao.statGlfy(ny);
	}
	
	
	/**
	 * 主营业务税金及附加<BR>
	 * 数据来源，一般费用表（qtzc)<BR>
	 * type 以04开头
	 * @param ny
	 * @return
	 */
	public Map statZyywsjjfj(String ny){
		return gainTblDao.statZyywsjjfj(ny);
	}
	
	
	/**
	 * 所得税<BR>
	 * 数据来源，一般费用表（qtzc)<BR>
	 * type 以045开头
	 * @param ny
	 * @return
	 */
	public Map statSds(String ny){
		return gainTblDao.statSds(ny);
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
	
	
	/**
	 * 统计调价利润<BR>
	 * 调高利润增加<BR>
	 * 调低利润减少<BR>
	 * 此处只计算库存商品，服务/劳务类商品不计；
	 * @param ny
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statTjlr(String ny){
		return gainTblDao.statTjlr(ny);
	}


	public GainTblDAO getGainTblDao() {
		return gainTblDao;
	}


	public void setGainTblDao(GainTblDAO gainTblDao) {
		this.gainTblDao = gainTblDao;
	}

}
