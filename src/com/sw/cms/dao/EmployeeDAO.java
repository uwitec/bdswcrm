package com.sw.cms.dao;

import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;

/**
 * <p>Ա������</p>
 * <p>��ϵͳ�û�����һ����(sys_user)</p>
 * <p>is_sys_user==0</p>
 * @author liyt
 */

public class EmployeeDAO extends JdbcBaseDAO {

	/**
	 * ȡԱ���б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserList(String con,int curPage, int rowsPerPage){
		String sql = "select a.*,b.dept_name from sys_user a left join dept b on b.dept_id=a.dept where a.is_sys_user=0";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * �����û���Ϣ
	 * @param user
	 */
	public void saveUser(SysUser user){
		String sql = "insert into sys_user(real_name,csny,sex,gs_phone,mobile,jt_phone,fax,mail,msn,qq,address,p_code,dept,position,szkf,state,xh,is_ywy,gh,nl,user_id) " +
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		String user_id = getUserID();
		
		Object[] param = new Object[21];
		
		param[0] = user.getReal_name();
		param[1] = user.getCsny();
		param[2] = user.getSex();
		param[3] = user.getGs_phone();
		param[4] = user.getMobile();
		param[5] = user.getJt_phone();
		param[6] = user.getFax();
		param[7] = user.getMail();
		param[8] = user.getMsn();
		param[9] = user.getQq();
		param[10] = user.getAddress();
		param[11] = user.getP_code();
		param[12] = user.getDept();
		param[13] = user.getPosition();
		param[14] = user.getSzkf();
		param[15] = user.getState();
		param[16] = new Integer(user.getXh());
		param[17] = user.getIs_ywy();
		param[18] = user.getGh();
		param[19] = user.getNl();
		param[20] = user_id;
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * �����û���Ϣ
	 * @param user
	 */
	public void updateUser(SysUser user){
		String sql = "update sys_user set real_name=?,csny=?,sex=?,gs_phone=?,mobile=?,jt_phone=?,fax=?,mail=?,msn=?,qq=?,address=?," +
				"p_code=?,dept=?,position=?,szkf=?,state=?,xh=?,is_ywy=?,gh=?,nl=? where user_id=?";
		
		Object[] param = new Object[21];
		
		param[0] = user.getReal_name();
		param[1] = user.getCsny();
		param[2] = user.getSex();
		param[3] = user.getGs_phone();
		param[4] = user.getMobile();
		param[5] = user.getJt_phone();
		param[6] = user.getFax();
		param[7] = user.getMail();
		param[8] = user.getMsn();
		param[9] = user.getQq();
		param[10] = user.getAddress();
		param[11] = user.getP_code();
		param[12] = user.getDept();
		param[13] = user.getPosition();
		param[14] = user.getSzkf();
		param[15] = user.getState();
		param[16] = new Integer(user.getXh());
		param[17] = user.getIs_ywy();
		param[18] = user.getGh();
		param[19] = user.getNl();
		param[20] = user.getUser_id();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ����user_idȡ�û���Ϣ
	 * @param user_id
	 * @return
	 */
	public Map getUser(String user_id){
		String sql = "select * from sys_user where user_id='" + user_id + "'";		
		return this.getResultMap(sql);
	}
	
	
	/**
	 * ɾ���û���Ϣ
	 * @param user_id
	 */
	public void delUser(String user_id){
		String sql = "delete from sys_user where user_id='" + user_id + "'";		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	private String getUserID(){
		String sql = "select userid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// �����кż�1
		sql = "update cms_all_seq set userid=userid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 8; i++) {
			curId = "0" + curId;
		}

		return "YH" + curId;
	}
	
}
