package com.sw.cms.dao;

import java.util.HashMap;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 利润表<BR>
 * 包括本月及本年度累积
 * @author liyt
 *
 */

public class GainTblDAO extends JdbcBaseDAO {
	

	/**
	 * 统计主营业务收入<BR>
	 * 所有库存商品的销售收入（销售-退货）
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statZyywSr(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//本月
		String sql = "select sum(a.xj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='已出库' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdMap.get("xshj")==null?0:((Double)xsdMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.xj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='已提交' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdMap.get("lshj")==null?0:((Double)lsdMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.xj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='已入库' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdMap.get("thhj")==null?0:((Double)thdMap.get("thhj")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(a.xj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='已出库' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdAllMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdAllMap.get("xshj")==null?0:((Double)xsdAllMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.xj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='已提交' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdAllMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdAllMap.get("lshj")==null?0:((Double)lsdAllMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.xj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='已入库' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdAllMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdAllMap.get("thhj")==null?0:((Double)thdAllMap.get("thhj")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
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
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//本月
		String sql = "select sum(a.nums*a.cbj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='已出库' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdMap.get("xshj")==null?0:((Double)xsdMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.nums*a.cbj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='已提交' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdMap.get("lshj")==null?0:((Double)lsdMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.nums*a.cbj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='已入库' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdMap.get("thhj")==null?0:((Double)thdMap.get("thhj")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(a.nums*a.cbj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='已出库' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdAllMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdAllMap.get("xshj")==null?0:((Double)xsdAllMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.nums*a.cbj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='已提交' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdAllMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdAllMap.get("lshj")==null?0:((Double)lsdAllMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.nums*a.cbj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='已入库' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdAllMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdAllMap.get("thhj")==null?0:((Double)thdAllMap.get("thhj")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * 统计其它业务收入
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statQtywSr(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//本月
		String sql = "select sum(a.xj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='已出库' and c.prop='服务/劳务' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdMap.get("xshj")==null?0:((Double)xsdMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.xj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='已提交' and c.prop='服务/劳务' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdMap.get("lshj")==null?0:((Double)lsdMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.xj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='已入库' and c.prop='服务/劳务' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdMap.get("thhj")==null?0:((Double)thdMap.get("thhj")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(a.xj) as xshj  from xsd_product a join xsd b on b.id=a.xsd_id JOIN product c on c.product_id=a.product_id where b.state='已出库' and c.prop='服务/劳务' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map xsdAllMap = this.getResultMap(sql);
		if(xsdMap != null){
			cost += xsdAllMap.get("xshj")==null?0:((Double)xsdAllMap.get("xshj")).doubleValue();
		}
		sql = "select sum(a.xj) as lshj  from lsd_product a join lsd b on b.id=a.lsd_id JOIN product c on c.product_id=a.product_id where b.state='已提交' and c.prop='服务/劳务' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map lsdAllMap = this.getResultMap(sql);
		if(lsdMap != null){
			cost += lsdAllMap.get("lshj")==null?0:((Double)lsdAllMap.get("lshj")).doubleValue();
		}
		sql = "select sum(a.xj) as thhj  from thd_product a join thd b on b.thd_id=a.thd_id JOIN product c on c.product_id=a.product_id where b.state='已入库' and c.prop='服务/劳务' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map thdAllMap = this.getResultMap(sql);
		if(thdMap != null){
			cost -= thdAllMap.get("thhj")==null?0:((Double)thdAllMap.get("thhj")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * 统计其它业务成本
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statQtywCb(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//本月
		
		//服务类商品采购，其它业务成本增加
		String sql = "select sum(a.price*a.nums) as cghj  from jhd_product a join jhd b on b.id=a.jhd_id JOIN product c on c.product_id=a.product_id where b.state='已入库' and c.prop='服务/劳务' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map cgMap = this.getResultMap(sql);
		if(cgMap != null){
			cost += cgMap.get("cghj")==null?0:((Double)cgMap.get("cghj")).doubleValue();
		}	
		//服务类商品采购退货，其它业务成本减少
		sql = "select sum(a.xj) as cgthhj  from cgthd_product a join cgthd b on b.id=a.cgthd_id JOIN product c on c.product_id=a.product_id where b.state='已出库' and c.prop='服务/劳务' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map cgthMap = this.getResultMap(sql);
		if(cgthMap != null){
			cost -= cgthMap.get("cgthhj")==null?0:((Double)cgthMap.get("cgthhj")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		
		//服务类商品采购，其它业务成本增加
		sql = "select sum(a.price*a.nums) as cghj  from jhd_product a join jhd b on b.id=a.jhd_id JOIN product c on c.product_id=a.product_id where b.state='已入库' and c.prop='服务/劳务' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map cgAllMap = this.getResultMap(sql);
		if(cgAllMap != null){
			cost += cgAllMap.get("cghj")==null?0:((Double)cgAllMap.get("cghj")).doubleValue();
		}	
		//服务类商品采购退货，其它业务成本减少
		sql = "select sum(a.xj) as cgthhj  from cgthd_product a join cgthd b on b.id=a.cgthd_id JOIN product c on c.product_id=a.product_id where b.state='已出库' and c.prop='服务/劳务' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map cgthAllMap = this.getResultMap(sql);
		if(cgthAllMap != null){
			cost -= cgthAllMap.get("cgthhj")==null?0:((Double)cgthAllMap.get("cgthhj")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * 营业外收入
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statYywSr(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//本月
		String sql = "select sum(skje) as income from qtsr where state='已提交' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("income")==null?0:((Double)mapMonth.get("income")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(skje) as income from qtsr where state='已提交' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("income")==null?0:((Double)mapAllMonth.get("income")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * 营业费用<BR>
	 * 取自一般费用表<BR>
	 * 为了兼容历史做如下处理
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statYywZc(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//当月
		String sql = "select sum(zcje) as cost from qtzc where state='已提交' and (type not like '02%' and type not like '03%' and type not like '04%' and type not like '05%') and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("cost")==null?0:((Double)mapMonth.get("cost")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(zcje) as cost from qtzc where state='已提交' and (type not like '02%' and type not like '03%' and type not like '04%' and type not like '05%')  and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("cost")==null?0:((Double)mapAllMonth.get("cost")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * 财务费用<BR>
	 * 数据来源，一般费用表（qtzc)<BR>
	 * type=02
	 * @param ny
	 * @return
	 */
	public Map statCwfy(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//当月
		String sql = "select sum(zcje) as cost from qtzc where state='已提交' and type like '02%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("cost")==null?0:((Double)mapMonth.get("cost")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(zcje) as cost from qtzc where state='已提交'  and type like '02%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("cost")==null?0:((Double)mapAllMonth.get("cost")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * 管理费用<BR>
	 * 数据来源，一般费用表（qtzc)<BR>
	 * type 以03开头
	 * @param ny
	 * @return
	 */
	public Map statGlfy(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//当月
		String sql = "select sum(zcje) as cost from qtzc where state='已提交' and type like '03%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("cost")==null?0:((Double)mapMonth.get("cost")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(zcje) as cost from qtzc where state='已提交'  and type like '03%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("cost")==null?0:((Double)mapAllMonth.get("cost")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * 主营业务税金及附加<BR>
	 * 数据来源，一般费用表（qtzc)<BR>
	 * type 以04开头
	 * @param ny
	 * @return
	 */
	public Map statZyywsjjfj(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//当月
		String sql = "select sum(zcje) as cost from qtzc where state='已提交' and type like '04%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("cost")==null?0:((Double)mapMonth.get("cost")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(zcje) as cost from qtzc where state='已提交'  and type like '04%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("cost")==null?0:((Double)mapAllMonth.get("cost")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}	
	
	
	/**
	 * 所得税<BR>
	 * 数据来源，一般费用表（qtzc)<BR>
	 * type 以045开头
	 * @param ny
	 * @return
	 */
	public Map statSds(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//当月
		String sql = "select sum(zcje) as cost from qtzc where state='已提交' and type like '05%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("cost")==null?0:((Double)mapMonth.get("cost")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(zcje) as cost from qtzc where state='已提交'  and type like '05%' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("cost")==null?0:((Double)mapAllMonth.get("cost")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}	
	
	
	
	/**
	 * 商品报溢收入
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statSpbySr(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//当月
		String sql = "select sum(ykje) as googbysr from kcpd_yk_tbl where type='1' and fs_date>='" + ny + "-01" + "' and fs_date<='" + ny + "-31" + "'";		
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("goodbysr")==null?0:((Double)mapMonth.get("goodbysr")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(ykje) as googbysr from kcpd_yk_tbl where type='1' and fs_date>='" + this.getNdqs(ny) + "' and fs_date<='" + ny + "-31" + "'";		
		Map mapAllMonth = this.getResultMap(sql);
		if(mapAllMonth != null){
			cost = mapAllMonth.get("goodbysr")==null?0:((Double)mapAllMonth.get("goodbysr")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * 商品报损支出
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statSpbsZc(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//本月
		String sql = "select sum(ykje) as goodbszc from kcpd_yk_tbl where type='2' and fs_date>='" + ny + "-01" + "' and fs_date<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);		
		if(mapMonth != null){
			cost = mapMonth.get("goodbszc")==null?0:((Double)mapMonth.get("goodbszc")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(ykje) as goodbszc from kcpd_yk_tbl where type='2' and fs_date>='" + this.getNdqs(ny) + "' and fs_date<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);		
		if(mapAllMonth != null){
			cost = mapAllMonth.get("goodbszc")==null?0:((Double)mapAllMonth.get("goodbszc")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
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
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//本月
		String sql = "select sum(srje) as srje from((select sum(pzje) srje from pz where type='应收' and pzje>0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')";
		sql += "union (select sum(0-pzje) srje from pz where type='应付' and pzje<0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')) x";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("srje")==null?0:((Double)mapMonth.get("srje")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(srje) as srje from((select sum(pzje) srje from pz where type='应收' and pzje>0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')";
		sql += "union all (select sum(0-pzje) srje from pz where type='应付' and pzje<0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')) x";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapAllMonth.get("srje")==null?0:((Double)mapAllMonth.get("srje")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
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
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//本月
		String sql = "select sum(0-srje) as srje from((select sum(pzje) srje from pz where type='应收' and pzje<0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')";
		sql += "union (select sum(pzje) srje from pz where type='应付' and pzje>0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')) x";
		Map mapMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapMonth.get("srje")==null?0:((Double)mapMonth.get("srje")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum(0-srje) as srje from((select sum(pzje) srje from pz where type='应收' and pzje<0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')";
		sql += "union all (select sum(pzje) srje from pz where type='应付' and pzje>0 and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "')) x";
		Map mapAllMonth = this.getResultMap(sql);
		if(mapMonth != null){
			cost = mapAllMonth.get("srje")==null?0:((Double)mapAllMonth.get("srje")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * 待摊费用
	 * @param ny  当前年月
	 * @return  Map(key,value)  <BR>
	 * key=curMonth 当月结果； <BR>
	 * key=allMonth  本年度累积
	 */
	public Map statDtfy(String ny){
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//本月
		String sql = "SELECT sum(je) as deferredPayment FROM  txfk_desc a join txfk b on b.id=a.txfk_id where b.state='已提交' and txrq>='" + ny + "-01" + "' and txrq<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);		
		if(mapMonth != null){
			cost = mapMonth.get("deferredPayment")==null?0:((Double)mapMonth.get("deferredPayment")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "SELECT sum(je) as deferredPayment FROM  txfk_desc a join txfk b on b.id=a.txfk_id where b.state='已提交' and txrq>='" + this.getNdqs(ny) + "' and txrq<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);		
		if(mapAllMonth != null){
			cost = mapAllMonth.get("deferredPayment")==null?0:((Double)mapAllMonth.get("deferredPayment")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
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
		Map<String,Double> map = new HashMap<String,Double>();
		
		double cost = 0;
		
		//本月数
		String sql = "select sum((a.tzjg-a.ysjg)*a.nums) as tjlr from chtj_desc a join chtj b on b.id=a.chtj_id join product c on c.product_id=a.product_id where b.state='已提交' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapMonth = this.getResultMap(sql);		
		if(mapMonth != null){
			cost = mapMonth.get("tjlr")==null?0:((Double)mapMonth.get("tjlr")).doubleValue();
		}
		map.put("curMonth", cost);
		
		cost = 0;
		
		//本年累积
		sql = "select sum((a.tzjg-a.ysjg)*a.nums) as tjlr from chtj_desc a join chtj b on b.id=a.chtj_id join product c on c.product_id=a.product_id where b.state='已提交' and c.prop='库存商品' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + this.getNdqs(ny) + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		Map mapAllMonth = this.getResultMap(sql);		
		if(mapAllMonth != null){
			cost = mapAllMonth.get("tjlr")==null?0:((Double)mapAllMonth.get("tjlr")).doubleValue();
		}
		map.put("allMonth", cost);
		
		return map;
	}
	
	
	/**
	 * 由年月取出年度起始
	 * @param ny
	 * @return
	 */
	private String getNdqs(String ny){
		if(ny == null){
			return "2008-01-01";
		}else{
			return ny.substring(0, 4) + "-01-01";
		}
	}

}
