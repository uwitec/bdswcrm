package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Clients;
import com.sw.cms.model.Page;
import com.sw.cms.util.GB2Alpha;

public class ClientsDAO extends JdbcBaseDAO {
	
	
	/**
	 * ȡ�ͻ��б�����ҳ��
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
	 * ���ݿͻ�����ģ����ѯ
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
	 * ���ؿͻ��б����Ӧ���Ԥ���Ԥ�տ�(����ҳ)
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
	 * ����ͻ���Ϣ
	 * @param clients
	 */
	public Object saveClient(Clients clients){
		String sql = "insert into clients(name,lxr,lxdh,mobile,address,p_code,mail,msn,qq,zq,xe,remark,ygs,gsxz,client_type,khjl,id,gzdh,cz,comaddress,china_py) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] param = new Object[21];
		
		
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
		param[17]=  clients.getGzdh();
		param[18]=  clients.getCz();
		param[19]= clients.getComaddress();
		
		GB2Alpha gb2Alpha = new GB2Alpha();
		param[20] = gb2Alpha.String2Alpha(clients.getName());
		
		this.getJdbcTemplate().update(sql,param);
		return param[16];
		
	}
	
	
	
	/**
	 * ���¿ͻ���Ϣ
	 * @param clients
	 */
	public void updateClient(Clients clients){
		
		String sql = "update clients set name=?,lxr=?,lxdh=?,mobile=?,address=?,p_code=?,mail=?,msn=?,qq=?,zq=?,xe=?,remark=?,ygs=?,gsxz=?,client_type=?,khjl=?,gzdh=?,cz=?,comaddress=?,china_py=? where id=?";
		
		Object[] param = new Object[21];
		
		
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
		
		param[16]=  clients.getGzdh();
		param[17]=  clients.getCz();
		param[18]=  clients.getComaddress();
		
		GB2Alpha gb2Alpha = new GB2Alpha();
		param[19] = gb2Alpha.String2Alpha(clients.getName());
		
		param[20] = clients.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	
	/**
	 * ����IDȡ�ͻ���Ϣ
	 * @param id
	 * @return
	 */
	public Object getClient(String id){
		String sql = "select * from clients where id='" + id + "'";
		
		return this.queryForObject(sql, new ClientsRowMapper());
	}
	
	
	
	/**
	 * ɾ���ͻ���Ϣ
	 * @param id
	 */
	public void delClient(String id){
		String sql = "delete from clients where id='" + id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * �����û��˺������ӣ�
	 * @param client_id
	 * @param pzye
	 */
	public void updateClientZhye(String client_id,double pzye){
		String sql = "update clients set zhye=zhye+" + pzye + " where id='" + client_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * �����û��˺����޵��ӣ�
	 * @param client_id
	 * @param zhye
	 */
	public void updateClientZhyeAll(String client_id,double zhye){
		String sql = "update clients set zhye=" + zhye + " where id='" + client_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ���ݿͻ�IDȡ�ͻ�����
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
	 * �ж�������λ�Ƿ����
	 * @param name
	 * @return
	 */
	public int getClientsIsExist(String name)
	{
	    String sql="select count(*) from clients  where name='"+name+"'";
	    return this.getJdbcTemplate().queryForInt(sql);
	}
	
	
	/**
	 * ȡ�ͻ��������׼�¼(ǰ5��)
	 * @param client_id
	 * @return
	 */
	public Page getClientWlyw(String client_id){
		
		//���۵��б�
		String xsd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'����' as xwtype,id as dj_id,sjcjje as je,fzr as jsr,cz_date,'viewXsd.html?id=' as url from xsd where state='�ѳ���' and client_name='" + client_id + "'";

		//�����˻�
		String thd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'�����˻�' as xwtype,thd_id as dj_id,(0-thdje) as je,th_fzr as jsr,cz_date ,'viewThd.html?thd_id=' as url from thd where state='�����' and client_name='" + client_id + "'";
		
		//�ɹ�������
		String jhd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'�ɹ�' as xwtype,id as dj_id,total as je,fzr as jsr,cz_date,'viewJhd.html?id=' as url from jhd where state='�����' and gysbh='" + client_id + "'";
		
		//�ɹ��˻���
		String cgthd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'�ɹ��˻�' as xwtype,id as dj_id,(0-tkzje) as je,jsr,cz_date,'viewCgthd.html?id=' as url from cgthd where state='�ѳ���' and provider_name='" + client_id + "'";

		String sql = "select * from ((" + xsd_sql + ") union (" + thd_sql + ") union (" + jhd_sql + ") union(" + cgthd_sql + ")) x order by cz_date desc";
		
		return this.getResultByPage(sql, 1, 7);
	}
	
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	private String getClientId() {
		String sql = "select clientid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// �����кż�1
		sql = "update cms_all_seq set clientid=clientid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 8; i++) {
			curId = "0" + curId;
		}

		return "CL" + curId;
	}
	
	
	
	
	
	
	/**
	 * ��װ����(�ͻ�)
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
			clients.setGzdh(rs.getString("gzdh"));
			clients.setCz(rs.getString("cz"));
			clients.setComaddress(rs.getString("comaddress"));
			clients.setChina_py(rs.getString("china_py"));
			
			return clients;
		}
	}	

}
