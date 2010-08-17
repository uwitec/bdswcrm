package com.sw.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Clients;
import com.sw.cms.model.ClientsPayInfo;
import com.sw.cms.model.Page;
import com.sw.cms.util.GB2Alpha;

public class ClientsDAO extends JdbcBaseDAO {
	
	
	/**
	 * 取客户列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getClients(String con,int curPage, int rowsPerPage){
		String sql = "select * from clients where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(Clients.class));
	}
	
	
	/**
	 * 根据客户名称模糊查询
	 * @param client_name
	 * @return
	 */
	public List getClientList(String client_name){
		String sql = "select * from clients where 1=1";
		
		if(!client_name.equals("")){
			sql = sql + " and name like '%" + client_name + "%'";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 客户列表
	 * @param client_id
	 * @return
	 */
	public List getClietsById(String client_id,String khjl){
		String sql = "select a.id,a.name,b.real_name as khjl from clients a left join sys_user b on b.user_id=a.khjl where 1=1";
		
		if(!client_id.equals("")){
			sql = sql + " and a.id='" + client_id + "'";
		}
		if(!khjl.equals("")){
			sql = sql + " and a.khjl='" + khjl + "'";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据客户提示框输入的提示信息查询相关客户信息
	 * @param clientsName
	 * @return
	 */
	public List getClientListByAjaxParam(String clientsName){
		String sql = "select * from clients  where flag='1'";
		
		if(!clientsName.equals("")){
			sql = sql + " and name like '%" + clientsName + "%' or china_py like '%" + clientsName.toUpperCase() + "%'";
		}
		return this.getResultList(sql);
	}
	
	
	
	/**
	 * 返回客户列表包括应付款、预付款、预收款(带分页)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getClientIncludYsk(String con,int curPage, int rowsPerPage){
		String sql = "select * from clients where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * 取用户期初，包括应收期初、应付期初
	 * @param cdate
	 * @return Map key:client_id+应收（应付） value:期初值
	 */
	public Map getClientQc(String con,String cdate){
		Map<String,Object> qcMap = new HashMap<String,Object>();
		
		String sql = "select a.* from client_qc a inner join clients b on b.id=a.client_name where a.cdate='" + cdate + "'";
		
		if(!con.equals("")){
			sql += con;
		}
		
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
	public Map getClientWlInfo(String con,String cdate){
		Map<String,Object> map = new HashMap<String,Object>();
		
		String sql = "select a.client_id,a.je_type,sum(a.fsje) as fsje from view_client_wl_info a inner join clients b on b.id=a.client_id where 1=1";
		
		if(!cdate.equals("")){
			sql += " and a.cdate='" + cdate + "'";
		}
		
		sql += " group by a.client_id,a.je_type";
		
		List list = this.getResultList(sql);
		
		String je_type = "";
		String client_id = "";
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
	 * 保存客户信息
	 * @param clients
	 */
	public Object saveClient(Clients clients,List clientsPayInfos){
		String sql = "insert into clients(name,lxr,lxdh,mobile,address,p_code,mail,msn,qq,zq,xe," +
				"remark,ygs,gsxz,client_type,khjl,id,gzdh,cz,comaddress,china_py," +
				"kp_name,kp_address,kp_tel,kp_khhzh,kp_sh,cg_zq,cg_xe,flag) " +
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] param = new Object[29];

		param[0] = clients.getName();
		param[1] = clients.getLxr();
		param[2] = clients.getLxdh();
		param[3] = clients.getMobile();
		param[4] = clients.getAddress();
		param[5] = clients.getP_code();
		param[6] = clients.getMail();
		param[7] = clients.getMsn();
		param[8] = clients.getQq();
		param[9] = clients.getZq();
		param[10] = new Double(clients.getXe());
		param[11] = clients.getRemark();
		param[12] = clients.getYgs();
		param[13] = clients.getGsxz();
		param[14] = clients.getClient_type();
		param[15] = clients.getKhjl();
		param[16] = getClientId();
		param[17] =  clients.getGzdh();
		param[18] =  clients.getCz();
		param[19] = clients.getComaddress();
		
		GB2Alpha gb2Alpha = new GB2Alpha();
		param[20] = gb2Alpha.String2Alpha(clients.getName());
		
		param[21] = clients.getKp_name();
		param[22] = clients.getKp_address();
		param[23] = clients.getKp_tel();
		param[24] = clients.getKp_khhzh();
		param[25] = clients.getKp_sh();
		param[26] = clients.getCg_zq();
		param[27] = clients.getCg_xe();
		param[28] = clients.getFlag();
		this.getJdbcTemplate().update(sql,param);
		
		this.addClientsPayInfos(param[16].toString(), clientsPayInfos);
		
		return param[16];
		
	}
	
	
	
	/**
	 * 更新客户信息
	 * @param clients
	 */
	public void updateClient(Clients clients,List clientsPayInfos){
		
		String sql = "update clients set name=?,lxr=?,lxdh=?,mobile=?,address=?,p_code=?,mail=?," +
				"msn=?,qq=?,zq=?,xe=?,remark=?,ygs=?,gsxz=?,client_type=?,khjl=?,gzdh=?,cz=?," +
				"comaddress=?,china_py=?,kp_name=?,kp_address=?,kp_tel=?,kp_khhzh=?,kp_sh=?,cg_zq=?,cg_xe=?,flag=? where id=?";
		
		Object[] param = new Object[29];
		
		
		param[0] = clients.getName();
		param[1] = clients.getLxr();
		param[2] = clients.getLxdh();
		param[3] = clients.getMobile();
		param[4] = clients.getAddress();
		param[5] = clients.getP_code();
		param[6] = clients.getMail();
		param[7] = clients.getMsn();
		param[8] = clients.getQq();
		param[9] = clients.getZq();
		param[10] = new Double(clients.getXe());
		param[11] = clients.getRemark();
		param[12] = clients.getYgs();
		param[13] = clients.getGsxz();
		param[14] = clients.getClient_type();
		param[15] = clients.getKhjl();
		
		param[16] =  clients.getGzdh();
		param[17] =  clients.getCz();
		param[18] =  clients.getComaddress();
		
		GB2Alpha gb2Alpha = new GB2Alpha();
		param[19] = gb2Alpha.String2Alpha(clients.getName());
		
		param[20] = clients.getKp_name();
		param[21] = clients.getKp_address();
		param[22] = clients.getKp_tel();
		param[23] = clients.getKp_khhzh();
		param[24] = clients.getKp_sh();
		param[25] = clients.getCg_zq();
		param[26] = clients.getCg_xe();
		param[27] = clients.getFlag();
		param[28] = clients.getId();
		
		this.delClientsPayInfos(clients.getId());
		this.addClientsPayInfos(clients.getId(), clientsPayInfos);
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * 根据客户编号取
	 * @param client_id
	 * @return
	 */
	public List getClientsPayInfos(String client_id){
		String sql = "select * from clients_pay_info where client_id='" + client_id + "'";
		return this.getResultList(sql, new BeanRowMapper(ClientsPayInfo.class));
	}
	
	
	/**
	 * 根据ID取客户信息
	 * @param id
	 * @return
	 */
	public Object getClient(String id){
		String sql = "select * from clients where id='" + id + "'";
		
		return this.queryForObject(sql, new BeanRowMapper(Clients.class));
	}
	
	
	
	/**
	 * 删除客户信息
	 * @param id
	 */
	public void delClient(String id){
		String sql = "delete from clients where id='" + id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 更新用户账号余额（叠加）
	 * @param client_id
	 * @param pzye
	 */
	public void updateClientZhye(String client_id,double pzye){
		String sql = "update clients set zhye=zhye+" + pzye + " where id='" + client_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 更新用户账号余额（无叠加）
	 * @param client_id
	 * @param zhye
	 */
	public void updateClientZhyeAll(String client_id,double zhye){
		String sql = "update clients set zhye=" + zhye + " where id='" + client_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据客户ID取客户名称
	 * @param client_id
	 * @return
	 */
	public String getClientNameById(String client_id){
		String name = client_id;
		
		String sql = "select * from clients where id='" + client_id + "'";
		
		List list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list.get(0);
			name = (String)map.get("name");
		}
		
		return name;
	}
	
	/**
	 * 判断往来单位是否可用
	 * @param name
	 * @return
	 */
	public int getClientsIsExist(String name)
	{
	    String sql="select count(*) from clients  where name='"+name+"'";
	    return this.getJdbcTemplate().queryForInt(sql);
	}
	
	/**
	 * 根据往来单位名称返回往来单位ID
	 * @param client_name
	 * @return
	 */
	public Map getClientByClientName(String client_name)
	{
		String sql="select id from clients where name='"+client_name+"'";
		return this.getJdbcTemplate().queryForMap(sql);
	}
	
	
	/**
	 * 取客户往来交易记录(前5条)
	 * @param client_id
	 * @return
	 */
	public Page getClientWlyw(String client_id){
		
		//销售单列表
		String xsd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'销售' as xwtype,id as dj_id,sjcjje as je,fzr as jsr,cz_date,'viewXsd.html?id=' as url from xsd where state='已出库' and client_name='" + client_id + "'";

		//销售退货
		String thd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'销售退货' as xwtype,thd_id as dj_id,(0-thdje) as je,th_fzr as jsr,cz_date ,'viewThd.html?thd_id=' as url from thd where state='已入库' and client_name='" + client_id + "'";
		
		//采购进货单
		String jhd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'采购' as xwtype,id as dj_id,total as je,fzr as jsr,cz_date,'viewJhd.html?id=' as url from jhd where state='已入库' and gysbh='" + client_id + "'";
		
		//采购退货单
		String cgthd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'采购退货' as xwtype,id as dj_id,(0-tkzje) as je,jsr,cz_date,'viewCgthd.html?id=' as url from cgthd where state='已出库' and provider_name='" + client_id + "'";

		String sql = "select * from ((" + xsd_sql + ") union (" + thd_sql + ") union (" + jhd_sql + ") union(" + cgthd_sql + ")) x order by cz_date desc";
		
		return this.getResultByPage(sql, 1, 5);
	}
	
	
	/**
	 * 判断客户信息是否可以删除<BR>
	 * 发生往来业务关系的客户信息不能删除<BR>
	 * 往来业务关系包括：销售、销售退货、采购、采购退货、往来调账、售后服务单、接件单、报修单
	 * @param client_id
	 * @return boolean true:可以；false:不可以
	 */
	public boolean isCanDel(String client_id){
		
		//判断是否存在销售记录
		String sql = "select count(*) from xsd where client_name='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否存在销售退货记录
		sql = "select count(*) from thd where client_name='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否存在采购记录
		sql = "select count(*) from jhd where gysbh='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否存在采购退货记录
		sql = "select count(*) from cgthd where provider_name='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否存在往来调账记录
		sql = "select count(*) from pz where client_name='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}	
		
        //判断是否存在售后服务单记录
		sql = "select count(*) from sfd where client_name='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
       //判断是否存在接件单记录
		sql = "select count(*) from jjd where client_name='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
        //判断是否存在报修单记录
		sql = "select count(*) from bxd where bxcs='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		return true;
	}
	
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	private String getClientId() {
		String sql = "select clientid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// 将序列号加1
		sql = "update cms_all_seq set clientid=clientid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 8; i++) {
			curId = "0" + curId;
		}

		return "CL" + curId;
	}
	
	
	/**
	 * 保存往来单付款相关信息
	 * @param client_id
	 * @param clietsPayInfos
	 */
	private void addClientsPayInfos(String client_id,List clietsPayInfos){
		if(clietsPayInfos != null && clietsPayInfos.size() > 0){
			for(int i=0;i<clietsPayInfos.size();i++){
				ClientsPayInfo info = (ClientsPayInfo)clietsPayInfos.get(i);
				if(info != null){
					if(info.getClient_all_name() != null && !info.getClient_all_name().equals("") && info.getBank_no() != null && !info.getBank_no().equals("")){
						String sql = "insert into clients_pay_info(client_id,client_all_name,bank_no) values('" + client_id + "','" + info.getClient_all_name() + "','" + info.getBank_no() + "')";
						this.getJdbcTemplate().update(sql);
					}
				}
			}
		}
	}
	
	
	/**
	 * 删除往来单位付款信息
	 * @param client_id
	 */
	private void delClientsPayInfos(String client_id){
		String sql = "delete from clients_pay_info where client_id='" + client_id + "'";
		this.getJdbcTemplate().update(sql);
	}

}
