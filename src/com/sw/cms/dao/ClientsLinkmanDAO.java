package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.model.Page;
import com.sw.cms.util.StringUtils;

/**
 * �ͻ���ϵ��DAO
 * 
 * @author Administrator
 * 
 */
public class ClientsLinkmanDAO extends JdbcBaseDAO {
	/**
	 * ��ȡ��ϵ���б�(����ҳ)
	 * 
	 * @param id
	 * @return
	 */
	public List getClientsLinkman(String id) {
		String sql = "select l.id,l.name,l.sex,l.lx,l.zw,l.dept,l.gzdh,l.yddh,l.mail,l.jtdh,l.qtlx,l.address,l.sr,l.ah,l.remark,c.name as clients_id from clients_linkman  l left join clients c on c.id=l.clients_id where c.id='"
				+ id + "' ";
		return this.getJdbcTemplate().query(sql, new ClientsLinkmanRowMapper());
	}

	/**
	 * ��ȡ��ϵ���б���ҳ��
	 * 
	 * @return
	 */
	public Page getClinetsLinkman(String con, int curPage, int rowsPerPage) {
		String sql = "select l.id,l.name,l.sex,l.lx,l.zw,l.dept,l.yddh,l.mail,l.jtdh,l.gzdh,l.qtlx,l.address,l.sr,l.ah,l.remark,c.name as clients_name, c.id as clients_id,c.cz from clients_linkman  l left join clients c on c.id=l.clients_id where 1=1 ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}

	/**
	 * ������ϵ��ID ��ȡ��ϵ��
	 * 
	 * @param id
	 * @return
	 */
	public Object getLinkmanById(String id) {
		String sql = "select l.id,l.name,l.sex,l.lx,l.zw,l.dept,l.gzdh,l.yddh,l.mail,l.jtdh,l.qtlx,l.address,l.sr,l.ah,l.remark,c.name as clients_id from clients_linkman  l left join clients c on c.id=l.clients_id where l.id='"
				+ id + "' ";
		return this.getJdbcTemplate().queryForObject(sql,
				new ClientsLinkmanRowMapper());
	}

	/**
	 * �����ϵ��
	 * 
	 * @param linkman
	 */
	public void insertLinkman(ClientsLinkman linkman) {
		String sql = "insert into clients_linkman(id,name,sex,lx,zw,dept,gzdh,yddh,mail,jtdh,qtlx,address,sr,ah,remark,clients_id)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = new Object[16];
		obj[0] = getClientLinkmanId();
		obj[1] = linkman.getName();
		obj[2] = linkman.getSex();
		obj[3] = linkman.getLx();
		obj[4] = linkman.getZw();
		obj[5] = linkman.getDept();
		obj[6] = linkman.getGzdh();
		obj[7] = linkman.getYddh();
		obj[8] = linkman.getMail();
		obj[9] = linkman.getJtdh();
		obj[10] = linkman.getQtlx();
		obj[11] = linkman.getAddress();
		obj[12] = StringUtils.strToNull(linkman.getSr());
		obj[13] = linkman.getAh();
		obj[14] = linkman.getRemark();
		obj[15] = linkman.getClients_id();
		this.getJdbcTemplate().update(sql, obj);

	}

	/**
	 * �޸���ϵ��
	 * 
	 * @param linkman
	 */
	public void updateLinkman(ClientsLinkman linkman) {
		String sql = "update clients_linkman set name=?,sex=?,lx=?,zw=?,dept=?,gzdh=?,yddh=?,mail=?,jtdh=?,qtlx=?,sr=?,ah=?,remark=?where id=?";
		Object[] obj = new Object[14];
		obj[0] = linkman.getName();
		obj[1] = linkman.getSex();
		obj[2] = linkman.getLx();
		obj[3] = linkman.getZw();
		obj[4] = linkman.getDept();
		obj[5] = linkman.getGzdh();
		obj[6] = linkman.getYddh();
		obj[7] = linkman.getMail();
		obj[8] = linkman.getJtdh();
		obj[9] = linkman.getQtlx();
		obj[10] = StringUtils.strToNull(linkman.getSr());
		obj[11] = linkman.getAh();
		obj[12] = linkman.getRemark();
		obj[13] = linkman.getId();
		this.getJdbcTemplate().update(sql, obj);
	}

	/**
	 * ɾ����ϵ��
	 * 
	 * @param id
	 */
	public String deleteLinkman(String id) {
		String sql = "select clients_id from clients_linkman where id='" + id
				+ "'";
		Map obj = this.getResultMap(sql);
		sql = "delete from clients_linkman where id='" + id + "'";
		this.getJdbcTemplate().update(sql);

		String objs = (String) obj.get("clients_id");
		if (null != objs) {
			return objs;
		} else {
			return null;
		}
	}

	/**
	 * ���ݵ�λ����ɾ����ϵ��
	 * 
	 * @param id
	 */
	public void deleteLinkmanByClentsId(String id) {
		String sql = "delete from clients_linkman where clients_id='" + id
				+ "'";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * ��ȡ��ϵ��ID
	 * 
	 * @return
	 */
	private String getClientLinkmanId() {
		String sql = "select linkman_id from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// �����кż�1
		sql = "update cms_all_seq set linkman_id=linkman_id+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 8; i++) {
			curId = "0" + curId;
		}

		return "LXR" + curId;
	}

	/**
	 * ���ݵ�λ���Ͳ�ѯ��ϵ���ʼ���ַ
	 * 
	 * @param clientType
	 * @return
	 */
	public String getLxrMailByClientType(String clientType) {
		String sql = "select distinct * from clients where client_type='"
				+ clientType + "'";
		List clientList = this.getResultList(sql);
		String str = "";
		if (clientList != null && clientList.size() > 0) {
			for (int i = 0; i < clientList.size(); i++) {
				Map client = (Map) clientList.get(i);
				if (!client.get("id").equals("")) {
					sql = "select * from clients_linkman where clients_id='"
							+ client.get("id") + "'";
					List lxrList = this.getResultList(sql,
							new ClientsLinkmanRowMapper());
					if (lxrList != null && lxrList.size() > 0) {
						for (int j = 0; j < lxrList.size(); j++) {
							ClientsLinkman clientLinkman = (ClientsLinkman) lxrList
									.get(j);
							if (!clientLinkman.getMail().equals("")) {
								str += clientLinkman.getMail() + ",";
							}
						}
					}
				}
			}
		}
		return str;
	}
	

	/**
	 * ��װʵ��
	 * 
	 */
	class ClientsLinkmanRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ClientsLinkman clientslinkman = new ClientsLinkman();
			clientslinkman.setId(rs.getString("id"));
			clientslinkman.setName(rs.getString("name"));
			clientslinkman.setSex(rs.getString("sex"));
			clientslinkman.setLx(rs.getString("lx"));
			clientslinkman.setZw(rs.getString("zw"));
			clientslinkman.setDept(rs.getString("dept"));
			clientslinkman.setGzdh(rs.getString("gzdh"));
			clientslinkman.setYddh(rs.getString("yddh"));
			clientslinkman.setMail(rs.getString("mail"));
			clientslinkman.setJtdh(rs.getString("jtdh"));
			clientslinkman.setQtlx(rs.getString("qtlx"));
			clientslinkman.setAddress(rs.getString("address"));
			clientslinkman.setSr(rs.getString("sr"));
			clientslinkman.setAh(rs.getString("ah"));
			clientslinkman.setRemark(rs.getString("remark"));
			clientslinkman.setClients_id(rs.getString("clients_id"));
			return clientslinkman;
		}
	}
}
