package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.smslib.InboundMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.FysqDAO.FysqRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Flinkman;
import com.sw.cms.model.Flx;
import com.sw.cms.model.Fsendsms;
import com.sw.cms.model.Page;
import com.sw.cms.model.SendMsgLog;

public class FsdxDAO extends JdbcBaseDAO {

	/**
	 * 返回联系人类型
	 * 
	 * @return
	 */
	public List getLxList() {
		String sql = "select *from lx";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 增加联系人类型
	 * 
	 * @param flx
	 */
	public void saveLx(Flx flx) {
		String sql = "insert into lx(name) values(?)";
		Object[] obj = new Object[1];
		obj[0] = flx.getName();
		this.getJdbcTemplate().update(sql, obj);

	}

	/**
	 * 更改之前加载
	 * 
	 * @param id
	 * @return
	 */
	public Map getLx(String id) {
		String sql = "select *from lx where id=" + id + "";
		return this.getJdbcTemplate().queryForMap(sql);
	}

	/**
	 * 更改联系人类型
	 * 
	 * @param flx
	 */
	public void updateLx(Flx flx) {
		String sql = "update lx set name='" + flx.getName() + "'where id="
				+ flx.getId() + "";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 删除联系人类型
	 * 
	 * @param id
	 */
	public void deleteLx(String id) {
		String sql = "delete from lx where id=" + id + "";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 返回联系人列表
	 * 
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getLinkmanList(String con, int curPage, int rowsPerPage) {
		String sql = "select *from linkman where 1=1 ";
		if (!con.equals("")) {
			sql = sql + " " + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}

	/**
	 * 保存联系人
	 * 
	 * @param linkman
	 */
	public void saveLinkman(Flinkman linkman) {
		String sql = "insert into linkman(name,yddh,lxid) values(?,?,?)";
		Object[] param = new Object[3];
		param[0] = linkman.getName();
		param[1] = linkman.getYddh();
		param[2] = linkman.getLxid();
		this.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 获取单个联系人
	 * 
	 * @param id
	 * @return
	 */
	public Map getLinkman(String id) {
		String sql = "select *from linkman where id=" + id + "";
		return this.getJdbcTemplate().queryForMap(sql);
	}
	
	/**
	 * 根据返回联系人列表
	 */
    public List getLinkmanById(String id)
    {
    	String ids="";
    	ids=id.substring(0, id.length()-1);
    	String sql="select *from linkman where id in("+ids+")";	
    	return  this.getJdbcTemplate().queryForList(sql);
    }
	/**
	 * 更改联系人
	 * 
	 * @param linkman
	 */
	public void updateLinkman(Flinkman linkman) {
		String sql = "update linkman set name=?,yddh=?,lxid=? where id=?";
		Object[] param = new Object[4];
		param[0] = linkman.getName();
		param[1] = linkman.getYddh();
		param[2] = linkman.getLxid();
		param[3] = linkman.getId();
		this.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 删除联系人
	 * 
	 * @param id
	 */
	public void deleteLinkman(String id) {
		String sql = "delete from linkman where id=" + id + "";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 把联系人插入到发送表里来
	 * 
	 * @param map
	 */
	public void insertFsendsms(Map map, String context, int cs) {
		String sql = "insert into sendsms(linkman,yddh,remark,cs) values(?,?,?,?)";
		Object param[] = new Object[4];
		param[0] = map.get("name");
		param[1] = map.get("yddh");
		param[2] = context;
		param[3] = cs;
		this.getJdbcTemplate().update(sql, param);

	}

	/**
	 * 返回第几次的发送次数
	 * 
	 * @return
	 */
	public int getCS() {
		String sql = "select csid from cms_all_seq";
		int cs = this.getJdbcTemplate().queryForInt(sql);
		sql = "update cms_all_seq set csid=csid+1";
		this.getJdbcTemplate().update(sql);
		return cs;
	}

	/**
	 * 查询本次要发送的待发列表
	 * 
	 * @param cs
	 * @return
	 */
	public List getsendSMS(int cs) {
		String sql = "select *from sendsms where cs=" + cs + "";
		return this.getJdbcTemplate().query(sql, new SendSMSRowMapper());
	}
	/**
	 * 改变发送成功状态
	 * @param id
	 */
	public void updateSendState(int id,int state)
	{
		String sql="";
		if(state==1)
		{
			sql="update sendsms set flog='1' where id="+id+"";
		}
		if(state==0)
		{
			sql="update sendsms set flog='2' where id="+id+"";
		}
		this.getJdbcTemplate().update(sql);
	}
	/**
	 * 插入回执短信
	 * @param msg
	 */
	public void insertReadSMS(InboundMessage msg)
	{
		String sql="insert into readsms(remark) values('"+msg.getText()+"')";
		this.getJdbcTemplate().update(sql);
	}
	/**
	 * 插入手机发送日志
	 * @param log
	 */
	public void insertSendMsgLog(List log)
	{
		Object []obj ;
		for(int i=0;i<log.size();i++)
		{
			String sql="insert into send_msg_log(user_name,mobile_num,send_time,error_log,flag) values(?,?,?,?,?)";
			SendMsgLog sendmslog=(SendMsgLog)log.get(i);
			obj=new Object[5];
			obj[0]=sendmslog.getUser_name();
			obj[1]=sendmslog.getMobile_num();
			obj[2]=sendmslog.getSend_time();
			obj[3]=sendmslog.getError_log();
			obj[4]=sendmslog.getFlag();
			this.getJdbcTemplate().update(sql,obj);
		}
	}

	class SendSMSRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Fsendsms sms = new Fsendsms();
			sms.setId(rs.getInt("id"));
			sms.setName(rs.getString("linkman"));
			sms.setYddh(rs.getString("yddh"));
			sms.setRemark(rs.getString("remark"));
			sms.setFlog(rs.getString("flog"));
			 sms.setCs(rs.getInt("cs"));
			return sms;
		}
	}
}
