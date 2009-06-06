package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.util.StringUtils;

/**
 * 客户联系人DAO
 * @author Administrator
 *
 */
public class ClientsLinkmanDAO  extends  JdbcBaseDAO 
{
	/**
	 * 获取联系人列表
	 * @param id
	 * @return
	 */
    public List getClientsLinkman(String id)
    {
    	String sql="select l.id,l.name,l.sex,l.lx,l.zw,l.dept,l.gzdh,l.yddh,l.mail,l.jtdh,l.qtlx,l.address,l.sr,l.ah,l.remark,c.name as clients_id from clients_linkman  l left join clients c on c.id=l.clients_id where c.id='"+id+"' ";
    	return this.getJdbcTemplate().query(sql, new ClientsLinkmanRowMapper());
    }
    /**
     * 根据联系人ID 获取联系人
     * @param id
     * @return
     */
    public Object getLinkmanById(String id)
    {
    	String sql="select l.id,l.name,l.sex,l.lx,l.zw,l.dept,l.gzdh,l.yddh,l.mail,l.jtdh,l.qtlx,l.address,l.sr,l.ah,l.remark,c.name as clients_id from clients_linkman  l left join clients c on c.id=l.clients_id where l.id='"+id+"' ";
    	return this.getJdbcTemplate().queryForObject(sql, new ClientsLinkmanRowMapper());
    }
    
    /**
     * 添加联系人
     * @param linkman
     */
    public void insertLinkman(ClientsLinkman linkman)
    {
    	String sql="insert into clients_linkman(name,sex,lx,zw,dept,gzdh,yddh,mail,jtdh,qtlx,address,sr,ah,remark,clients_id)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
    	Object []obj=new Object[15];   	
    	obj[0]=linkman.getName();
    	obj[1]=linkman.getSex();
    	obj[2]=linkman.getLx();
    	obj[3]=linkman.getZw();
    	obj[4]=linkman.getDept();
    	obj[5]=linkman.getGzdh();
    	obj[6]=linkman.getYddh();
    	obj[7]=linkman.getMail();
    	obj[8]=linkman.getJtdh();
    	obj[9]=linkman.getQtlx();
    	obj[10]=linkman.getAddress();
    	obj[11]=StringUtils.strToNull(linkman.getSr());
    	obj[12]=linkman.getAh();
    	obj[13]=linkman.getRemark();
    	obj[14]=linkman.getClients_id();
    	this.getJdbcTemplate().update(sql,obj);
    	
    }
    /**
     * 修改联系人
     * @param linkman
     */
    public void updateLinkman(ClientsLinkman linkman)
    {
    	String sql="update clients_linkman set name=?,sex=?,lx=?,zw=?,dept=?,gzdh=?,yddh=?,mail=?,jtdh=?,qtlx=?,address=?,sr=?,ah=?,remark=?  where id=?";
    	Object [] obj=new Object[15];
    	obj[0]=linkman.getName();
    	obj[1]=linkman.getSex();
    	obj[2]=linkman.getLx();
    	obj[3]=linkman.getZw();
    	obj[4]=linkman.getDept();
    	obj[5]=linkman.getGzdh();
    	obj[6]=linkman.getYddh();
    	obj[7]=linkman.getMail();
    	obj[8]=linkman.getJtdh();
    	obj[9]=linkman.getQtlx();
    	obj[10]=linkman.getAddress();
    	obj[11]=StringUtils.strToNull(linkman.getSr());
    	obj[12]=linkman.getAh();
    	obj[13]=linkman.getRemark();
    	 
    	obj[14]=linkman.getId();
    	this.getJdbcTemplate().update(sql,obj);
    }
    /**
     * 删除联系人
     * @param id
     */
    public String deleteLinkman(String id)
    {
    	String sql="select clients_id from clients_linkman where id="+id+"";
    	Map obj=this.getResultMap(sql);
    	 sql="delete from clients_linkman where id="+id+"";
    	this.getJdbcTemplate().update(sql);
    	
    	return  (String)obj.get("clients_id");
    }
    	
    
    /**
     * 包装实体 
     *
     */
    class ClientsLinkmanRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ClientsLinkman clientslinkman = new ClientsLinkman();
			clientslinkman.setId(rs.getInt("id"));
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
