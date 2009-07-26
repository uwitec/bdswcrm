package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
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
	 * ���ݿͻ���ʾ���������ʾ��Ϣ��ѯ��ؿͻ���Ϣ
	 * @param clientsName
	 * @return
	 */
	public List getClientListByAjaxParam(String clientsName){
		String sql = "select * from clients";
		
		if(!clientsName.equals("")){
			sql = sql + " where name like '%" + clientsName + "%' or china_py like '%" + clientsName.toUpperCase() + "%'";
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
		String sql = "insert into clients(name,lxr,lxdh,mobile,address,p_code,mail,msn,qq,zq,xe," +
				"remark,ygs,gsxz,client_type,khjl,id,gzdh,cz,comaddress,china_py," +
				"kp_name,kp_address,kp_tel,kp_khhzh,kp_sh) " +
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] param = new Object[26];
		
		
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
		
		param[21]= clients.getKp_name();
		param[22]= clients.getKp_address();
		param[23]= clients.getKp_tel();
		param[24]= clients.getKp_khhzh();
		param[25]= clients.getKp_sh();
		
		this.getJdbcTemplate().update(sql,param);
		return param[16];
		
	}
	
	
	
	/**
	 * ���¿ͻ���Ϣ
	 * @param clients
	 */
	public void updateClient(Clients clients){
		
		String sql = "update clients set name=?,lxr=?,lxdh=?,mobile=?,address=?,p_code=?,mail=?," +
				"msn=?,qq=?,zq=?,xe=?,remark=?,ygs=?,gsxz=?,client_type=?,khjl=?,gzdh=?,cz=?," +
				"comaddress=?,china_py=?,kp_name=?,kp_address=?,kp_tel=?,kp_khhzh=?,kp_sh=? where id=?";
		
		Object[] param = new Object[26];
		
		
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
		
		param[20]= clients.getKp_name();
		param[21]= clients.getKp_address();
		param[22]= clients.getKp_tel();
		param[23]= clients.getKp_khhzh();
		param[24]= clients.getKp_sh();
		
		param[25] = clients.getId();
		
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
	 * ����������λ���Ʒ���������λID
	 * @param client_name
	 * @return
	 */
	public Map getClientByClientName(String client_name)
	{
		String sql="select id from clients where name='"+client_name+"'";
		return this.getJdbcTemplate().queryForMap(sql);
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

			if(SqlUtil.columnIsExist(rs,"id")) clients.setId(rs.getString("id"));
			if(SqlUtil.columnIsExist(rs,"name")) clients.setName(rs.getString("name"));
			if(SqlUtil.columnIsExist(rs,"lxr")) clients.setLxr(rs.getString("lxr"));
			if(SqlUtil.columnIsExist(rs,"lxdh")) clients.setLxdh(rs.getString("lxdh"));
			if(SqlUtil.columnIsExist(rs,"mobile")) clients.setMobile(rs.getString("mobile"));
			if(SqlUtil.columnIsExist(rs,"address")) clients.setAddress(rs.getString("address"));
			if(SqlUtil.columnIsExist(rs,"p_code")) clients.setP_code(rs.getString("p_code"));
			if(SqlUtil.columnIsExist(rs,"mail")) clients.setMail(rs.getString("mail"));
			if(SqlUtil.columnIsExist(rs,"msn")) clients.setMsn(rs.getString("msn"));
			if(SqlUtil.columnIsExist(rs,"qq")) clients.setQq(rs.getString("qq"));
			if(SqlUtil.columnIsExist(rs,"zq")) clients.setZq(rs.getString("zq"));
			if(SqlUtil.columnIsExist(rs,"xe")) clients.setXe(rs.getDouble("xe"));
			if(SqlUtil.columnIsExist(rs,"remark")) clients.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"ygs")) clients.setYgs(rs.getString("ygs"));
			if(SqlUtil.columnIsExist(rs,"gsxz")) clients.setGsxz(rs.getString("gsxz"));
			if(SqlUtil.columnIsExist(rs,"client_type")) clients.setClient_type(rs.getString("client_type"));
			if(SqlUtil.columnIsExist(rs,"khjl")) clients.setKhjl(rs.getString("khjl"));
			if(SqlUtil.columnIsExist(rs,"gzdh")) clients.setGzdh(rs.getString("gzdh"));
			if(SqlUtil.columnIsExist(rs,"cz")) clients.setCz(rs.getString("cz"));
			if(SqlUtil.columnIsExist(rs,"comaddress")) clients.setComaddress(rs.getString("comaddress"));
			if(SqlUtil.columnIsExist(rs,"china_py")) clients.setChina_py(rs.getString("china_py"));
			
			if(SqlUtil.columnIsExist(rs,"kp_name")) clients.setKp_name(rs.getString("kp_name"));
			if(SqlUtil.columnIsExist(rs,"kp_address")) clients.setKp_address(rs.getString("kp_address"));
			if(SqlUtil.columnIsExist(rs,"kp_tel")) clients.setKp_tel(rs.getString("kp_tel"));
			if(SqlUtil.columnIsExist(rs,"kp_khhzh")) clients.setKp_khhzh(rs.getString("kp_khhzh"));
			if(SqlUtil.columnIsExist(rs,"kp_sh")) clients.setKp_sh(rs.getString("kp_sh"));
			
			return clients;
		}
	}	

}
