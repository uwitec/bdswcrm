package com.sw.cms.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;
import com.sw.cms.xml.productkc.ProductKc;
import com.sw.cms.xml.productkc.ProductKcQc;
import com.sw.cms.xml.productkc.ProductKcQcXmlDo;

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
				"pzid=1,thdid=1,dbsqid=1,kfdbid=1,cgthdid=1,lsyskid=1,chtjid=1,qtsrid=1,qtzcid=1,nbzzid=1,lsthdid=1,yushoutoyingshouid=1,yufutoyingfuid=1,fysqid=1,txfkid=1,txfkdescid=1,jjdid=1,bxfhdid=1,fhkhdid=1,wxrkdid=1,sfdid=1,"
		        +"pgdid=1,wxcldid=1,ykrkid=1,ykckid=1,cxdid=1,cnfkdid=1,zzdid=1,bfdid=1,hjdid=1,zxgdid=1,cgfpdid=1,"
		        +"jfgzid=1,hykflid=1,hykzzid=1,hykdaid=1,xsfpid=1";
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
	 * 生成库存期初
	 * 按新的业务逻辑生成，每天的每个仓库的库存信息存成一个XML文件
	 * 总库存生成一XML文件保存到数据库中，库房标示用“all”
	 */
	public void genKcQcNew(){
		try{
			//当前日期
			String qc_date = DateComFunc.getToday();
			
			//删除当店库存期初值，重新生成
			String dsql = "delete from product_kc_qc_xml where cdate='" + qc_date + "'";
			this.update(dsql);
			
			//取有效的库房列表，生成各库房的期初库存
			String sql = "select * from storehouse where id not like 'WX%' and flag='1'";
			List storeList = this.getResultList(sql);
			if(storeList != null && storeList.size() > 0){
				for(int i=0; i < storeList.size(); i++){
					Map map = (Map)storeList .get(i);
					String store_id = (String)map.get("id");  //库房编号
					
					String tempSql = "select a.product_id as productId,a.nums,b.price  from product_kc a left join product b on b.product_id=a.product_id where a.store_id='" + store_id + "'";
					List productList = this.getResultList(tempSql, new BeanRowMapper(ProductKc.class));
					
					ProductKcQc productKcQc = new ProductKcQc();
					productKcQc.setCdate(qc_date);
					productKcQc.setStoreId(store_id);
					productKcQc.setProducts(productList);
					
					ProductKcQcXmlDo xmlDo = new ProductKcQcXmlDo();
					String tempXml = xmlDo.toXml(productKcQc);  //库存XML文件
					
					//保存库存期初信息
					String isql = "insert into product_kc_qc_xml(store_id,cdate,xmlString,createDate) values('" + store_id + "','" + qc_date + "','" + tempXml + "',now())";
					this.update(isql);
				}
			}
			
			//生成总得库存期初值
			String zsql = "select a.product_id as productId,sum(a.nums) as nums,b.price from product_kc a left join product b on b.product_id=a.product_id GROUP by a.product_id,b.price";
			List productList = this.getResultList(zsql, new BeanRowMapper(ProductKc.class));
			ProductKcQc productKcQc = new ProductKcQc();
			productKcQc.setCdate(qc_date);
			productKcQc.setStoreId("all");
			productKcQc.setProducts(productList);
			
			ProductKcQcXmlDo xmlDo = new ProductKcQcXmlDo();
			String tempXml = xmlDo.toXml(productKcQc);  //库存XML文件
			
			//保存总库存期初信息
			String isql = "insert into product_kc_qc_xml(store_id,cdate,xmlString,createDate) values('all','" + qc_date + "','" + tempXml + "',now())";
			this.update(isql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 生成客户往来期初
	 * 昨天的期初加昨天的往来等于今天的期初
	 */
	public void genYsQc(){
		String today = DateComFunc.getToday();
		this.genClientWlqc(today);
		log.info("生成客户往来期初成功");
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
		String sql = "select sum(fkje) as fkje from cgfk where (state='已提交' or state='已支付') and gysbh='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')='" + cdate + "'";
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
		sql = "delete FROM sys_msg WHERE TO_DAYS(NOW())-TO_DAYS(send_time) > 30";
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
	 * 取用户期初，包括应收期初、应付期初
	 * @param cdate
	 * @return Map key:client_id+应收（应付） value:期初值
	 */
	private Map getClientQc(String cdate){
		Map<String,Object> qcMap = new HashMap<String,Object>();
		
		String sql = "select * from client_qc where cdate='" + cdate + "'";
		
		List list = this.getResultList(sql);
		
		String client_id = "";
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_name");
				
				qcMap.put(client_id+"应收", tempMap.get("ysqc"));
				qcMap.put(client_id+"应付", tempMap.get("yfqc"));
			}
		}
		
		return qcMap;
	}
	
	
	/**
	 * 取用户往来所有情况
	 * @param start_date
	 * @param end_date
	 * @param client_id
	 * @return map key:client_id+je_type  value:发生金额
	 */
	private Map getClientWlInfo(String start_date,String end_date,String client_id){
		Map<String,Object> map = new HashMap<String,Object>();
		
		String sql = "select client_id,je_type,sum(fsje) as fsje from view_client_wl_info where 1=1";
		
		if(!start_date.equals("")){
			sql += " and cdate>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and cdate<='" + end_date + "'";
		}
		if(!client_id.equals("")){
			sql += " and client_id='" + client_id + "'";
		}
		
		sql += " group by client_id,je_type";
		
		List list = this.getResultList(sql);
		
		String je_type = "";
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_id");
				je_type = (String)tempMap.get("je_type");
				
				map.put(client_id+je_type, tempMap.get("fsje"));
			}
		}
		
		return map;
	}
	
	
	/**
	 * 生成某一天的期初值
	 * @param cdate
	 */
	private void genClientWlqc(String cdate){
		
		Date curDate = DateComFunc.strToDate(cdate,"yyyy-MM-dd");	 //生成期初的日期
		String beforeDate =  DateComFunc.formatDate((DateComFunc.addDay(curDate,-1)),"yyyy-MM-dd");  //生成期初的前一天
			
		Map qcMap = this.getClientQc(beforeDate);  //前一天的期初值情况
		Map wlMap = this.getClientWlInfo(beforeDate, beforeDate, "");  //前一天的往来情况
		
		
		
		List clientList = getClientList();
		if(clientList != null && clientList.size()>0){
			String[] sqls = new String[clientList.size()];
			for(int i=0;i<clientList.size();i++){
				Map map = (Map)clientList.get(i);
				String client_id = StringUtils.nullToStr(map.get("id"));
				
				double qcys = qcMap.get(client_id+"应收")==null?0:((Double)qcMap.get(client_id+"应收")).doubleValue();  //昨日期初应收数		
				double bqfs = wlMap.get(client_id+"应收发生")==null?0:((Double)wlMap.get(client_id+"应收发生")).doubleValue();  //昨日应收发生
				double ysje = wlMap.get(client_id+"已收发生")==null?0:((Double)wlMap.get(client_id+"已收发生")).doubleValue();  //昨日已收发生
				
				double curYs = qcys + bqfs - ysje;  //今日期初应收数
				
				
				double qcyf = qcMap.get(client_id+"应付")==null?0:((Double)qcMap.get(client_id+"应付")).doubleValue();  //昨日期初应付数		
				double bqfsyf = wlMap.get(client_id+"应付发生")==null?0:((Double)wlMap.get(client_id+"应付发生")).doubleValue();  //本期发生
				double yfje = wlMap.get(client_id+"已付发生")==null?0:((Double)wlMap.get(client_id+"已付发生")).doubleValue();  //本期已收
				
				double curYf = qcyf + bqfsyf - yfje;  //今日期初应付数
				
				sqls[i] = "insert into client_qc(client_name,ysqc,yfqc,cdate) values('" + client_id + "'," + curYs + "," + curYf + ",'" + cdate + "')";
				
			}
			this.getJdbcTemplate().batchUpdate(sqls);
		}
	}
	
	
	/**
	 * 根据前一天账户的期初值，生成当前天的期初值
	 * @param cdate  要生成期初的日期
	 * @param cdat_1  期初前一天
	 */
	private void genAccountQc(String cdate,String cdat_1){
		
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
	public void updateShkcProductDay(){
		String sql = "update shkc set day_num=day_num+1";
        this.getJdbcTemplate().update(sql);
        log.info("更改售后库存商品在库天数成功！");
	}
	
	
	/**
	 * 批量生成客户期初信息，从开始日期到结束日期<BR>
	 * 必须保存开始日期前一天的期初信息是正确的，否则生成的所有期初期都是错误的<BR>
	 * 例如：要批量重新生成2009-09-01到2009-10-01的期初<BR>
	 * 则参数start_date=2009-09-01<BR>
	 * 参数end_date=2009-10-01<BR>
	 * 同时要保证2009-08-31的期初值是正确的<BR><BR>
	 * 2010-01-05添加
	 * @param start_date 开始日期
	 * @param end_date   结束日期
	 */
	public void updateBatchGenClientWlQc(String start_date,String end_date){
		//清除现有的期初值
		String sql = "delete from client_qc where cdate>='" + start_date + "'";
		this.getJdbcTemplate().update(sql);
		
		//插入新的期初值
		
		Date curDate = DateComFunc.strToDate(start_date,"yyyy-MM-dd");		
		while(DateComFunc.formatDate(curDate, "yyyy-MM-dd").compareToIgnoreCase(end_date) <= 0){
			
			String cdate = DateComFunc.formatDate(curDate, "yyyy-MM-dd");
			String cdat_1 =  DateComFunc.formatDate((DateComFunc.addDay(curDate,-1)),"yyyy-MM-dd");
			
			System.out.println("开始生成" + cdate + "客户往来期初");
			this.genClientWlqc(cdate);
			System.out.println("生成" + cdate + "客户往来期初成功");
			
			curDate = DateComFunc.addDay(curDate, 1);  //当前天数加1
		}
	}
	
	
	/**
	 * 批量生成账户期初信息，从开始日期到结束日期<BR>
	 * 必须保存开始日期前一天的期初信息是正确的，否则生成的所有期初期都是错误的<BR>
	 * 例如：要批量重新生成2009-09-01到2009-10-01的期初<BR>
	 * 则参数start_date=2009-09-01<BR>
	 * 参数end_date=2009-10-01<BR>
	 * 同时要保证2009-08-31的期初值是正确的<BR><BR>
	 * 2010-01-05添加
	 * @param start_date 开始日期
	 * @param end_date   结束日期
	 */
	public void updateBatchGenAccountQc(String start_date,String end_date){
		//清除现有的期初值
		String sql = "delete from account_qc where qc_date>='" + start_date + "'";
		this.getJdbcTemplate().update(sql);
		
		//插入新的期初值
		
		Date curDate = DateComFunc.strToDate(start_date,"yyyy-MM-dd");		
		while(DateComFunc.formatDate(curDate, "yyyy-MM-dd").compareToIgnoreCase(end_date) <= 0){
			
			String cdate = DateComFunc.formatDate(curDate, "yyyy-MM-dd");
			String cdat_1 =  DateComFunc.formatDate((DateComFunc.addDay(curDate,-1)),"yyyy-MM-dd");
			this.genAccountQc(cdate, cdat_1);
			log.debug("生成" + cdate + "账户期初成功");
			curDate = DateComFunc.addDay(curDate, 1);  //当前天数加1
		}
	}
	
	
	/**
	 * 插入库存期初值
	 * @param product_id
	 * @param store_id
	 * @param nums
	 * @param cdate
	 * @param price
	 */
	public void inserProductKcQc(String product_id,String store_id,int nums,String cdate,double price){
		String sql = "insert into product_kc_qc(product_id,store_id,nums,cdate,price) values('" + product_id + "','" + store_id + "'," + nums + ",'" + cdate + "'," + price + ")";
		this.getJdbcTemplate().update(sql);
	}
	

}
