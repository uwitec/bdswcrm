package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Bwl;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.model.Page;
import com.sw.cms.util.StringUtils;

/**
 * 客户联系人DAO
 * 
 * @author Administrator
 * 
 */
public class ClientsLinkmanDAO extends JdbcBaseDAO {
	/**
	 * 获取联系人列表(不翻页)
	 * 
	 * @param id
	 * @return
	 */
	public List getClientsLinkman(String id) {
		String sql = "select l.id,l.name,l.sex,l.lx,l.zw,l.dept,l.gzdh,l.yddh,l.mail,l.jtdh,l.qtlx,l.address,l.sr,l.ah,l.remark,c.name as clients_id,l.ch,l.qq,l.msn,l.nld from clients_linkman  l left join clients c on c.id=l.clients_id where c.id='"
				+ id + "' ";
		return this.getJdbcTemplate().query(sql, new ClientsLinkmanRowMapper());
	}

	/**
	 * 获取主联系人
	 * 
	 * @param id
	 * @return
	 */
	public ClientsLinkman getZClientsLinkman(String id) {
		ClientsLinkman info = new ClientsLinkman();
		String sql = "select * from clients_linkman  where lx='主联系人' and clients_id='"
				+ id + "' ";
		
		Object obj =this.queryForObject(sql, new BeanRowMapper(ClientsLinkman.class));
		if(obj != null){
			info = (ClientsLinkman)obj;
		}
		return info;
	}

	
	
	/**
	 * 获取联系人列表（翻页）
	 * 
	 * @return
	 */
	public Page getClinetsLinkman(String con, int curPage, int rowsPerPage) {
		String sql = "select l.id,l.name,l.sex,l.lx,l.zw,l.dept,l.yddh,l.mail,l.jtdh,l.gzdh,l.qtlx,l.address,l.sr,l.ah,l.remark,c.name as clients_name, c.id as clients_id,c.cz,l.ch,l.qq,l.msn,l.nld from clients_linkman  l left join clients c on c.id=l.clients_id left join sys_user s on s.user_id=c.khjl where 1=1 ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}

	/**
	 * 根据联系人ID 获取联系人
	 * 
	 * @param id
	 * @return
	 */
	public Object getLinkmanById(String id) {
		String sql = "select l.id,l.name,l.sex,l.lx,l.zw,l.dept,l.gzdh,l.yddh,l.mail,l.jtdh,l.qtlx,l.address,l.sr,l.ah,l.remark,c.name as clients_id,l.ch,l.qq,l.msn,l.nld from clients_linkman  l left join clients c on c.id=l.clients_id where l.id='"
				+ id + "' ";
		return this.getJdbcTemplate().queryForObject(sql,
				new ClientsLinkmanRowMapper());
	}

	/**
	 * 添加联系人
	 * 
	 * @param linkman
	 */
	public void insertLinkman(ClientsLinkman linkman) {
		String sql = "insert into clients_linkman(id,name,sex,lx,zw,dept,gzdh,yddh,mail,jtdh,qtlx,address,sr,ah,remark,clients_id,ch,qq,msn,nld)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = new Object[20];
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
		obj[12] = linkman.getSr();
		obj[13] = linkman.getAh();
		obj[14] = linkman.getRemark();
		obj[15] = linkman.getClients_id();
		obj[16] = linkman.getCh();
		obj[17] = linkman.getQq();
		obj[18] = linkman.getMsn();
		obj[19] = linkman.getNld();
		
		this.getJdbcTemplate().update(sql, obj);

	}

	/**
	 * 修改联系人
	 * 
	 * @param linkman
	 */
	public void updateLinkman(ClientsLinkman linkman) {
		String sql = "update clients_linkman set name=?,sex=?,lx=?,zw=?,dept=?,gzdh=?,yddh=?,mail=?,jtdh=?,qtlx=?,sr=?,ah=?,remark=?,"+
		             "ch=?,qq=?,msn=?,nld=?"+
		             " where id=?";
		Object[] obj = new Object[18];
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
		obj[10] = linkman.getSr();
		obj[11] = linkman.getAh();
		obj[12] = linkman.getRemark();
		obj[13] = linkman.getCh();
		obj[14] = linkman.getQq();
		obj[15] = linkman.getMsn();
		obj[16] = linkman.getNld();
		
		obj[17] = linkman.getId();
		
		this.getJdbcTemplate().update(sql, obj);
	}

	/**
	 * 删除联系人
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
	 * 根据单位名称删除联系人
	 * 
	 * @param id
	 */
	public void deleteLinkmanByClentsId(String id) {
		String sql = "delete from clients_linkman where clients_id='" + id
				+ "'";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 获取联系人ID
	 * 
	 * @return
	 */
	private String getClientLinkmanId() {
		String sql = "select linkman_id from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// 将序列号加1
		sql = "update cms_all_seq set linkman_id=linkman_id+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 8; i++) {
			curId = "0" + curId;
		}

		return "LXR" + curId;
	}

	/**
	 * 根据单位类型查询联系人邮件地址
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
	 * 包装实体
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
			clientslinkman.setCh(rs.getString("ch"));
			clientslinkman.setQq(rs.getString("qq"));
			clientslinkman.setMsn(rs.getString("msn"));
			clientslinkman.setNld(rs.getString("nld"));
			return clientslinkman;
		}
	}
}
