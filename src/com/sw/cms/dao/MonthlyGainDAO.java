package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.util.StringUtils;

/**
 * �¶����󱨱�
 * @author liyt
 *
 */

public class MonthlyGainDAO extends JdbcBaseDAO {
	
	/**
	 * ȡë��
	 * @param ny ë���·�
	 * @return
	 */
	public double getGrossProfit(String ny){
		double gross = 0;
		
		String sql = "select hjje,cb,prop from view_hpxshz_tj where cz_date>='" + ny + "-01" + "' and cz_date<='" + ny + "-31" + "'";
		
		List list = this.getResultList(sql);
		
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				double xssr = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue(); //��������
				double cb = map.get("cb")==null?0:((Double)map.get("cb")).doubleValue(); //�ɱ�
				
				String prop = StringUtils.nullToStr(map.get("prop"));   //��Ʒ����(����̿ڡ�����/����)
				
				if(prop.equals("����/����")){ //����Ƿ���/��������Ʒ���ɱ���Ϊ0
					cb = 0;
				}
				
				gross += (xssr-cb);
			}
		}
		
		return gross;
	}
	
	
	/**
	 * ȡ��������
	 * @param ny
	 * @return
	 */
	public double getOtherIncome(String ny){
		double income = 0;
		
		String sql = "select sum(skje) as income from qtsr where state='���ύ' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' " +
				"and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		
		Map map = this.getResultMap(sql);
		
		if(map != null){
			income = map.get("income")==null?0:((Double)map.get("income")).doubleValue();
		}
		
		return income;
	}
	
	
	/**
	 * ȡ����
	 * @param ny
	 * @return
	 */
	public double getCost(String ny){
		double cost = 0;
		
		String sql = "select sum(zcje) as cost from qtzc where state='���ύ' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + ny + "-01" + "' " +
				"and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + ny + "-31" + "'";
		
		Map map = this.getResultMap(sql);
		
		if(map != null){
			cost = map.get("cost")==null?0:((Double)map.get("cost")).doubleValue();
		}
		
		return 0-cost;
	}
	
	
	/**
	 * ��̯����
	 * @param ny
	 * @return
	 */
	public double getDeferredPayment(String ny){
		double deferredPayment = 0;
		
		String sql = "SELECT sum(je) as deferredPayment FROM  txfk_desc a join txfk b on b.id=a.txfk_id where b.state='���ύ'" +
				" and txrq>='" + ny + "-01" + "' and txrq<='" + ny + "-31" + "'";
		
		Map map = this.getResultMap(sql);
		
		if(map != null){
			deferredPayment = map.get("deferredPayment")==null?0:((Double)map.get("deferredPayment")).doubleValue();
		}
		
		return 0-deferredPayment;
	}
	
	
	/**
	 * ��Ʒ��������
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
	 * ��Ʒ����֧��
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
