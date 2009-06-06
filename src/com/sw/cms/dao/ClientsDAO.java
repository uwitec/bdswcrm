package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Clients;
import com.sw.cms.model.Page;

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
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new ClientsRowMapper());
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
	 * 保存客户信息
	 * @param clients
	 */
	public void saveClient(Clients clients){
		String sql = "insert into clients(name,lxr,lxdh,mobile,address,p_code,mail,msn,qq,zq,xe,remark,ygs,gsxz,client_type,khjl,id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] param = new Object[17];
		
		
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
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	
	/**
	 * 更新客户信息
	 * @param clients
	 */
	public void updateClient(Clients clients){
		
		String sql = "update clients set name=?,lxr=?,lxdh=?,mobile=?,address=?,p_code=?,mail=?,msn=?,qq=?,zq=?,xe=?,remark=?,ygs=?,gsxz=?,client_type=?,khjl=? where id=?";
		
		Object[] param = new Object[17];
		
		
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
		param[16] = clients.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	
	/**
	 * 根据ID取客户信息
	 * @param id
	 * @return
	 */
	public Object getClient(String id){
		String sql = "select * from clients where id='" + id + "'";
		
		return this.queryForObject(sql, new ClientsRowMapper());
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
	 * 取客户往来交易记录
	 * @param client_id
	 * @return
	 */
	public List getClientWlyw(String client_id){
		List list = null;
		
		return list;
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
	 * 包装对象(客户)
	 * 
	 * @author liyt
	 * 
	 */
	class ClientsRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Clients clients = new Clients();

			clients.setId(rs.getString("id"));
			clients.setName(rs.getString("name"));
			clients.setLxr(rs.getString("lxr"));
			clients.setLxdh(rs.getString("lxdh"));
			clients.setMobile(rs.getString("mobile"));
			clients.setAddress(rs.getString("address"));
			clients.setP_code(rs.getString("p_code"));
			clients.setMail(rs.getString("mail"));
			clients.setMsn(rs.getString("msn"));
			clients.setQq(rs.getString("qq"));
			clients.setZq(rs.getString("zq"));
			clients.setXe(rs.getDouble("xe"));
			clients.setRemark(rs.getString("remark"));
			clients.setYgs(rs.getString("ygs"));
			clients.setGsxz(rs.getString("gsxz"));
			clients.setClient_type(rs.getString("client_type"));
			clients.setKhjl(rs.getString("khjl"));
			
			return clients;
		}
	}	

}
