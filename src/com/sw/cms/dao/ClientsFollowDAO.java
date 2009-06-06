package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.ClientsFollow;
import com.sw.cms.util.StringUtils;
/**
 * 客户跟进记录DAO
 * @author Administrator
 *
 */
public class ClientsFollowDAO  extends  JdbcBaseDAO 
{
	
    public List getClientsFollow(String id)
    {
    	String sql="select f.id,f.zt,l.name as linkman_id,c.name as clients_id,f.lxlx,f.lxdate,f.cjdate,f.cjr,f.nextdate,f.remark from clients_follow f left join clients_linkman l on f.linkman_id = l.id left join  clients c  on f.clients_id = c.id  where c.id='"+id+"' order by f.id desc";
    	return this.getResultList(sql, new ClientsFollowRowMapper());
    }
    
    /**
     * 添加跟进记录
     * @param follow
     */
    public void insertFollow(ClientsFollow follow)
    {
    	 String sql="insert into clients_follow(zt,clients_id,linkman_id,lxlx,lxdate,cjdate,cjr,nextdate,remark)values(?,?,?,?,?,now(),?,?,?)";
    	 Object []param=new Object[8];
    	 param[0]=follow.getZt();
    	 param[1]=follow.getClients_id();
    	 param[2]=follow.getLinkman_id();
    	 param[3]=follow.getLxlx();
    	 param[4]=follow.getLxdate();
    	 param[5]=follow.getCjr();
    	 param[6]=StringUtils.strToNull(follow.getNextdate());
    	 param[7]=follow.getRemark();
    	 this.getJdbcTemplate().update(sql,param);
    }
    /**
     * 根据跟进ID获取跟进记录
     * @param id
     * @return
     */
    public Object getClientsFollowById(String id)
    {
    	String sql="select *from clients_follow where id="+id+"";
    	return this.getJdbcTemplate().queryForObject(sql, new ClientsFollowRowMapper());
    }
    
    /**
     * 保存修改跟进记录
     * @param follow
     */
    public void updateFollow(ClientsFollow follow)
    {
    	 String sql="update clients_follow set zt=?,linkman_id=?,lxlx=?,lxdate=?,nextdate=?,remark=? where id=?";
    	 Object []param=new Object[7];
    	 param[0]=follow.getZt(); 
    	 param[1]=follow.getLinkman_id();
    	 param[2]=follow.getLxlx();
    	 param[3]=follow.getLxdate();   	  
    	 param[4]=StringUtils.strToNull(follow.getNextdate());
    	 param[5]=follow.getRemark();
    	 param[6]=follow.getId();
    	 this.getJdbcTemplate().update(sql,param);
    }
    
    
    class ClientsFollowRowMapper implements RowMapper 
    {
		public Object mapRow(ResultSet rs, int index) throws SQLException 
		{
			ClientsFollow cf=new ClientsFollow();
			cf.setId(rs.getInt("id"));
			cf.setZt(rs.getString("zt"));
			cf.setLinkman_id(rs.getString("linkman_id"));
			cf.setClients_id(rs.getString("clients_id"));
			cf.setLxlx(rs.getString("lxlx"));
			cf.setLxdate(rs.getString("lxdate"));
			cf.setCjdate(rs.getString("cjdate"));
			cf.setCjr(rs.getString("cjr"));
			cf.setNextdate(rs.getString("nextdate"));
			cf.setRemark(rs.getString("remark"));
			return cf;
		}
  
    }
}
