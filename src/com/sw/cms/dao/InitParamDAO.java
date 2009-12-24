package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

/**
 * 系统初始化类
 * 每天的0:0:30执行
 * @author liyt
 *
 */

public class InitParamDAO extends JdbcBaseDAO {
	
	/**
	 * 重置业务相关参数为1
	 *
	 */
	public void updateParam(){
		String sql = "update cms_all_seq set  bxdid=1, jhdid=1,cgyfkid=1,rkdid=1,kcpdid=1,ckdid=1,xsdid=1,yskid=1,lsdid=1," +
				"pzid=1,thdid=1,dbsqid=1,kfdbid=1,cgthdid=1,lsyskid=1,chtjid=1,qtsrid=1,qtzcid=1,nbzzid=1,lsthdid=1,yushoutoyingshouid=1,yufutoyingfuid=1,fysqid=1,txfkid=1,txfkdescid=1,jjdid=1,bxfhdid=1,fhkhdid=1,wxrkdid=1,sfdid=1,pgdid=1,wxcldid=1,ykrkid=1,ykckid=1";
		this.getJdbcTemplate().update(sql);
		log.info("初始化业务参数成功！");
	}
	
	
	/**
	 * 生成账户期初
	 *
	 */
	public void genAccountQc(){
		String qc_date = DateComFunc.getToday();
		
		String sql = "delete from account_qc where qc_date='" + qc_date + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into account_qc (select 0 as seq_id,id,'" + qc_date + "' as qc_date,dqje from accounts)";
		this.getJdbcTemplate().update(sql);
		
		log.info("生成账号期初成功");
	}
	
	
	/**
	 * 生成库存期初
	 * 昨天的剩余库存为今天的期初库存
	 *
	 */
	public void genKcQc(){
		String qc_date = DateComFunc.getToday();
		String sql = "delete from product_kc_qc where cdate='" + qc_date + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into product_kc_qc (select 0 as seq_id,a.product_id,a.store_id,a.nums,'" + qc_date + "' as cdate,b.price as price from product_kc a left join product b on b.product_id=a.product_id)";
		this.getJdbcTemplate().update(sql);
		
		log.info("生成库存期初成功");
	}
	
	/**
	 * 生成客户往来期初
	 * 昨天的期初加昨天的往来等于今天的期初
	 *
	 */
	public void genYsQc(){
		String today = DateComFunc.getToday();
		
		List clientList = getClientList();
		if(clientList != null && clientList.size()>0){
			for(int i=0;i<clientList.size();i++){
				Map map = (Map)clientList.get(i);
				String client_name = StringUtils.nullToStr(map.get("id"));
				this.insertQc(client_name, this.getQcys(client_name), this.getQcyf(client_name), today);
			}
		}
		log.info("生成应收期初成功");
	}
	
	
	private void insertQc(String client_name,double ysqc,double yfqc,String cdate){
		this.delQc(client_name, cdate);
		String sql = "insert into client_qc(client_name,ysqc,yfqc,cdate) values(?,?,?,?)";
		
		Object[] param = new Object[4];
		
		param[0] = client_name;
		param[1] = ysqc;
		param[2] = yfqc;
		param[3] = cdate;
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	private void delQc(String client_name,String cdate){
		String sql = "delete from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当日期初应收
	 * @param client_name
	 * @param cdate
	 * @return
	 */
	private double getQcys(String client_name){
		String cdate = DateComFunc.getYestoday();
		double qcys = 0;
		
		//今日期初应收 = 昨日期初应收 + 昨日发生应收 - 昨日已收
		qcys = this.getClientYsqc(client_name, cdate) + this.getSjysje(client_name, cdate) - this.getSjyishouje(client_name, cdate);
		
		return qcys;
	}
	
	
	/**
	 * 取客户应收当前应收
	 * @param client_name 客户编号
	 * @return
	 */
	public double getClientYinshou(String client_name){
		String cdate = DateComFunc.getToday();
		return this.getClientYsqc(client_name, cdate) + this.getSjysje(client_name, cdate) - this.getSjyishouje(client_name, cdate);
	}
	
	
	/**
	 * 取客户当前应付
	 * @param client_name 客户编号
	 * @return
	 */
	public double getClientYinfu(String client_name){
		String cdate = DateComFunc.getToday();
		return this.getClientYfqc(client_name, cdate) + this.getSjyfje(client_name, cdate) - this.getSjyifuje(client_name, cdate);
	}
	
	
	private double getQcyf(String client_name){
		String cdate = DateComFunc.getYestoday();
		double qcyf = 0;
		
		//今日期初应付 = 昨日期初应付 + 昨日发生应付 - 昨日已付
		qcyf = this.getClientYfqc(client_name, cdate) + this.getSjyfje(client_name, cdate) - this.getSjyifuje(client_name, cdate);
		
		return qcyf;
	}
	
	
	/**
	 * 返回所有往来客户列表
	 * @return
	 */
	private List getClientList(){
		String sql = "select * from clients";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据客户编号及日期取期初应收额
	 * @param client_name
	 * @param cdate
	 * @return  期初应收额，如无返回0
	 */
	private double getClientYsqc(String client_name,String cdate){
		double ysqc = 0;
		
		String sql = "select ysqc from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			ysqc = map.get("ysqc")==null?0:((Double)map.get("ysqc")).doubleValue();
		}
		
		return ysqc;
	}
	
	
	/**
	 * 根据客户编号及日期取期初应付额
	 * @param client_name
	 * @param cdate
	 * @return  期初应付额，如无返回0
	 */
	private double getClientYfqc(String client_name,String cdate){
		double yfqc = 0;
		
		String sql = "select yfqc from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			yfqc = map.get("yfqc")==null?0:((Double)map.get("yfqc")).doubleValue();
		}
		
		return yfqc;
	}
	
	
	/**
	 * 实际发生应收金额
	 * @param client_name
	 * @param cdate
	 * @return
	 */
	public double getFsYingshouJe(String client_name,String cdate){
		return this.getSjysje(client_name, cdate);
	}
	
	
	/**
	 * 实际发生已收金额
	 * @param client_name
	 * @param cdate
	 * @return
	 */
	public double getFsYishouJe(String client_name,String cdate){
		return this.getSjyishouje(client_name, cdate);
	}
	
	
	/**
	 * 实际发生应付金额
	 * @param client_name
	 * @param cdate
	 * @return
	 */
	public double getFsYingfuJe(String client_name,String cdate){
		return this.getSjyfje(client_name, cdate);
	}
	
	
	/**
	 * 实际发生已付金额
	 * @param client_name
	 * @param cdate
	 * @return
	 */
	public double getFsYifuJe(String client_name,String cdate){
		return this.getSjyifuje(client_name, cdate);
	}
	
	
	/**
	 * 根据客户编号及日期取实际发生应收额
	 * 退货只接加负值即可，不影响其它
	 * 退货时只有两种情况（现金退货、冲抵往来）都直接影响客户实际发生应收额
	 * @param xsd_id
	 * @param ckd_id
	 * @return 实际发生应收额，如无返回0
	 */
	private double getSjysje(String client_name,String cdate){
		double sjysje = 0;
		
		//发生的销售单情况
		String sql = "select sum(sjcjje) as xsdje from xsd where state='已出库' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			sjysje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
		}
		
		//发生的退货单情况
		sql = "select sum((0-thdje)) as thdje from thd where state='已入库' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			sjysje += map.get("thdje")==null?0:((Double)map.get("thdje")).doubleValue();
		}
		
		//发生调账情况
		sql = "select sum(pzje) as je from pz where state='已提交' and type='应收' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map tzMap = (Map)list .get(0);
			sjysje += tzMap.get("je")==null?0:((Double)tzMap.get("je")).doubleValue();
		}
		
		return sjysje;
	}
	
	
	/**
	 * 根据客户编号及日期取实际发生应付额
	 * @param xsd_id
	 * @param ckd_id
	 * @return 实际发生应付额，如无返回0
	 */
	private double getSjyfje(String client_name,String cdate){
		double sjyfje = 0;
		
		//进货发生金额
		String sql = "select sum(total) as total from jhd where state='已入库' and gysbh='" + client_name + "'  and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			sjyfje = map.get("total")==null?0:((Double)map.get("total")).doubleValue();
		}
		
		//退货发生金额
		sql = "select sum(0-tkzje) as tkzje from cgthd where state='已出库' and provider_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			sjyfje += map.get("tkzje")==null?0:((Double)map.get("tkzje")).doubleValue();
		}
		
		//发生调账情况
		sql = "select sum(pzje) as je from pz where state='已提交' and type='应付' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map tzMap = (Map)list .get(0);
			sjyfje += tzMap.get("je")==null?0:((Double)tzMap.get("je")).doubleValue();
		}
		
		return sjyfje;
	}
	
	
	
	/**
	 * 根据客户编号及日期取实际已收金额
	 * @param xsd_id
	 * @return 已收金额,如无返回0
	 */
	private double getSjyishouje(String client_name,String cdate){
		
		//所有已收款
		double syys = 0;
		String sql = "select sum(skje) as skje from xssk where client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "' and state='已提交'";		
		List list = this.getResultList(sql);		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			syys = map.get("skje")==null?0:((Double)map.get("skje")).doubleValue();
		}
		
		return syys;
	}
	
	
	/**
	 * 根据客户编号及日期取实际已付金额
	 * @param xsd_id
	 * @return 已付金额,如无返回0
	 */
	private double getSjyifuje(String client_name,String cdate){
		double sjyifuje = 0;
		String sql = "select sum(fkje) as fkje from cgfk where state='已提交' and gysbh='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			sjyifuje = map.get("fkje")==null?0:((Double)map.get("fkje")).doubleValue();
		}
		
		return sjyifuje;
	}
	
	
	/**
	 * 删除超期限的消息
	 *
	 */
	public void delExpireMsg(){
		String sql = "delete FROM sys_msg WHERE TO_DAYS(NOW())-TO_DAYS(read_time) > " + Constant.MSG_EXPIRE_DAY;
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 返回当前所有账户
	 * @return
	 */
	public List getAllAccounts(){
		String sql = "select * from accounts";
		return this.getResultList(sql);
	}
	
	
	//以下为系统生成期初失败后调用调整期初时用到的函数
	
	/**
	 * 根据前一天客户应收应付期初值 生成某一天的期初值<BR>
	 * 使用在某一天客户期初出现问题时，从前一天的基础上来调整的情况<BR>
	 * 例如：2008-09-09的期初有问题，需2008-09-08的值是对，那应该是cdate=2008-09-09,cdat_1=2008-09-08<BR>
	 * @param cdate  期初日期
	 * @param cdat_1 期初前一天
	 */
	public void genCleintWlqc(String cdate,String cdat_1){
		List clientList = getClientList();
		if(clientList != null && clientList.size()>0){
			for(int i=0;i<clientList.size();i++){
				Map map = (Map)clientList.get(i);
				String client_name = StringUtils.nullToStr(map.get("id"));
							
				//今日期初应收 = 昨日期初应收 + 昨日发生应收 - 昨日已收
				double qcys = this.getClientYsqc(client_name, cdat_1) + this.getSjysje(client_name, cdat_1) - this.getSjyishouje(client_name, cdat_1);
				
				//今日期初应付 = 昨日期初应付 + 昨日发生应付 - 昨日已付
				double qcyf = this.getClientYfqc(client_name, cdat_1) + this.getSjyfje(client_name, cdat_1) - this.getSjyifuje(client_name, cdat_1);
				
				this.insertQc(client_name, qcys, qcyf, cdate);
			}
		}
		log.info("生成" + cdate + "往来期初成功");
	}
	
	
	
	/**
	 * 根据前一天账户的期初值，生成当前天的期初值
	 * @param cdate  要生成期初的日期
	 * @param cdat_1  期初前一天
	 */
	public void genAccountQc(String cdate,String cdat_1){
		
		List accountList = getAllAccounts();
		
		if(accountList != null && accountList.size() > 0){
			for(int i=0;i<accountList.size();i++){
				Map map = (Map)accountList.get(i);
				
				String account_id = StringUtils.nullToStr(map.get("id"));
				
				//取前一天账户的期初值
				double pre_qc_je = 0;
				String sql = "select * from account_qc where qc_date='" + cdat_1 + "' and account_id='" + account_id + "'";				
				Map qcMap = this.getResultMap(sql);
				if(qcMap != null){
					pre_qc_je = qcMap.get("qcje")==null?0:((Double)qcMap.get("qcje")).doubleValue();
				}
				
				
				//取交易金额合计
				double cur_day_jyje = 0;
				sql = "select * from account_dzd where account_id='" + account_id + "' and jy_date>='" + cdat_1 + "' and jy_date<='" + (cdat_1+" 23:59:59") + "'";
				List mxList = this.getResultList(sql);
				if(mxList != null && mxList.size()>0){
					for(int k=0;k<mxList.size();k++){
						Map mxMap = (Map)mxList.get(k);
						double jyje = mxMap.get("jyje")==null?0:((Double)mxMap.get("jyje")).doubleValue(); //交易金额						
						cur_day_jyje += jyje;
					}
				}
				
				//账户当天期初值
				double cur_qc_je = pre_qc_je + cur_day_jyje;
				
				sql = "insert into account_qc(account_id,qc_date,qcje) values('" + account_id + "','" + cdate + "'," + cur_qc_je + ")";
				this.getJdbcTemplate().update(sql);
				
				
			}
		}
		log.info("生成账户 "+ cdate + " 期初成功");
	}
	
	/**
	 * 更改售后库存商品在库天数
	 *
	 */
	public void updateShkcProductDay()
	{
		String sql = "update shkc set day_num=day_num+1";
        this.getJdbcTemplate().update(sql);
        log.info("更改售后库存商品在库天数成功！");
	}

}
