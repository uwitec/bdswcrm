package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.util.StringUtils;

/**
 * 月度利润报表
 * @author liyt
 *
 */

public class MonthlyGainDAO extends JdbcBaseDAO {
	
	/**
	 * 取毛利
	 * @param ny 毛利月份
	 * @return
	 */
	public double getGrossProfit(String ny){
		double gross = 0;
		
		String sql = "select hjje,cb,prop from view_hpxshz_tj where cz_date>='" + ny + "-01" + "' and cz_date<='" + ny + "-31" + "'";
		
		List list = this.getResultList(sql);
		
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				double xssr = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue(); //销售收入
				double cb = map.get("cb")==null?0:((Double)map.get("cb")).doubleValue(); //成本
				
				String prop = StringUtils.nullToStr(map.get("prop"));   //商品属性(库存商口、劳务/服务)
				
				if(prop.equals("服务/劳务")){ //如果是服务/劳务类商品，成本价为0
					cb = 0;
				}
				
				gross += (xssr-cb);
			}
		}
		
		return gross;
	}
	
	
	/**
	 * 取其它收入
	 * @param ny
	 * @return
	 */
	public double getOtherIncome(String ny){
		double income = 0;
		
		String sql = "select sum(skje) as income from qtsr where state='已提交' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' " +
				"and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		
		Map map = this.getResultMap(sql);
		
		if(map != null){
			income = map.get("income")==null?0:((Double)map.get("income")).doubleValue();
		}
		
		return income;
	}
	
	
	/**
	 * 取费用
	 * @param ny
	 * @return
	 */
	public double getCost(String ny){
		double cost = 0;
		
		String sql = "select sum(zcje) as cost from qtzc where state='已提交' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' " +
				"and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		
		Map map = this.getResultMap(sql);
		
		if(map != null){
			cost = map.get("cost")==null?0:((Double)map.get("cost")).doubleValue();
		}
		
		return 0-cost;
	}
	
	
	/**
	 * 待摊费用
	 * @param ny
	 * @return
	 */
	public double getDeferredPayment(String ny){
		double deferredPayment = 0;
		
		String sql = "SELECT sum(je) as deferredPayment FROM  txfk_desc a join txfk b on b.id=a.txfk_id where b.state='已提交'" +
				" and txrq>='" + ny + "-01" + "' and txrq<='" + ny + "-31" + "'";
		
		Map map = this.getResultMap(sql);
		
		if(map != null){
			deferredPayment = map.get("deferredPayment")==null?0:((Double)map.get("deferredPayment")).doubleValue();
		}
		
		return 0-deferredPayment;
	}
	
	
	/**
	 * 商品报溢收入
	 * @param ny
	 * @return
	 */
	public double getGoodBysr(String ny){
		double goodbysr = 0;
		
		String sql = "select sum(ykje) as googbysr from kcpd_yk_tbl where type='1' " +
				"and fs_date>='" + ny + "-01" + "' and fs_date<='" + ny + "-31" + "'";
		
		Map map = this.getResultMap(sql);
		
		if(map != null){
			goodbysr = map.get("goodbysr")==null?0:((Double)map.get("goodbysr")).doubleValue();
		}
		
		return goodbysr;
	}
	
	
	/**
	 * 商品报损支出
	 * @param ny
	 * @return
	 */
	public double getGoodBszc(String ny){
		double goodbszc = 0;
		
		String sql = "select sum(ykje) as goodbszc from kcpd_yk_tbl where type='2' " +
		"and fs_date>='" + ny + "-01" + "' and fs_date<='" + ny + "-31" + "'";

		Map map = this.getResultMap(sql);
		
		if(map != null){
			goodbszc = map.get("goodbszc")==null?0:((Double)map.get("goodbszc")).doubleValue();
		}
		
		return goodbszc;
	}

}
