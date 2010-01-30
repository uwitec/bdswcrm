package com.sw.cms.dao;

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
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(Clients.class));
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
	public Object saveClient(Clients clients,List clientsPayInfos){
		String sql = "insert into clients(name,lxr,lxdh,mobile,address,p_code,mail,msn,qq,zq,xe," +
				"remark,ygs,gsxz,client_type,khjl,id,gzdh,cz,comaddress,china_py," +
				"kp_name,kp_address,kp_tel,kp_khhzh,kp_sh,cg_zq,cg_xe) " +
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] param = new Object[28];

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
		
		this.getJdbcTemplate().update(sql,param);
		
		this.addClientsPayInfos(param[16].toString(), clientsPayInfos);
		
		return param[16];
		
	}
	
	
	
	/**
	 * ���¿ͻ���Ϣ
	 * @param clients
	 */
	public void updateClient(Clients clients,List clientsPayInfos){
		
		String sql = "update clients set name=?,lxr=?,lxdh=?,mobile=?,address=?,p_code=?,mail=?," +
				"msn=?,qq=?,zq=?,xe=?,remark=?,ygs=?,gsxz=?,client_type=?,khjl=?,gzdh=?,cz=?," +
				"comaddress=?,china_py=?,kp_name=?,kp_address=?,kp_tel=?,kp_khhzh=?,kp_sh=?,cg_zq=?,cg_xe=? where id=?";
		
		Object[] param = new Object[28];
		
		
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
		param[27] = clients.getId();
		
		this.delClientsPayInfos(clients.getId());
		this.addClientsPayInfos(clients.getId(), clientsPayInfos);
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * ���ݿͻ����ȡ
	 * @param client_id
	 * @return
	 */
	public List getClientsPayInfos(String client_id){
		String sql = "select * from clients_pay_info where client_id='" + client_id + "'";
		return this.getResultList(sql, new BeanRowMapper(ClientsPayInfo.class));
	}
	
	
	/**
	 * ����IDȡ�ͻ���Ϣ
	 * @param id
	 * @return
	 */
	public Object getClient(String id){
		String sql = "select * from clients where id='" + id + "'";
		
		return this.queryForObject(sql, new BeanRowMapper(Clients.class));
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
		
		return this.getResultByPage(sql, 1, 6);
	}
	
	
	/**
	 * �жϿͻ���Ϣ�Ƿ����ɾ��<BR>
	 * ��������ҵ���ϵ�Ŀͻ���Ϣ����ɾ��<BR>
	 * ����ҵ���ϵ���������ۡ������˻����ɹ����ɹ��˻�����������
	 * @param client_id
	 * @return boolean true:���ԣ�false:������
	 */
	public boolean isCanDel(String client_id){
		
		//�ж��Ƿ�������ۼ�¼
		String sql = "select count(*) from xsd where client_name='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ���������˻���¼
		sql = "select count(*) from thd where client_name='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ���ڲɹ���¼
		sql = "select count(*) from jhd where gysbh='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ���ڲɹ��˻���¼
		sql = "select count(*) from cgthd where provider_name='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ�����������˼�¼
		sql = "select count(*) from pz where client_name='" + client_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}	
		
		return true;
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
	 * �������������������Ϣ
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
	 * ɾ��������λ������Ϣ
	 * @param client_id
	 */
	private void delClientsPayInfos(String client_id){
		String sql = "delete from clients_pay_info where client_id='" + client_id + "'";
		this.getJdbcTemplate().update(sql);
	}

}
