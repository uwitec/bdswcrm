package com.sw.cms.dao;

import java.util.Map;

import com.sw.cms.dao.BfdDAO.BfdRowMapper;
import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Fysq;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;
import com.sw.cms.util.GB2Alpha;

/**
 * <p>
 * 员工管理
 * </p>
 * <p>
 * 与系统用户共用一个表(sys_user)
 * </p>
 * <p>
 * is_sys_user==0
 * </p>
 * 
 * @author liyt
 */

public class EmployeeDAO extends JdbcBaseDAO {

	/**
	 * 取员工列表（带分页）
	 * 
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserList(String con, int curPage, int rowsPerPage) {
		String sql = "select a.*,b.dept_name from sys_user a left join dept b on b.dept_id=a.dept where a.is_sys_user=0 and a.is_del='0'";

		if (!con.equals("")) {
			sql = sql + con;
		}

		return this.getResultByPage(sql, curPage, rowsPerPage);
	}

	/**
	 * 取业务员列表（带分页）
	 * 
	 * @param con
	 * @param curPage
	 * @param rowPerPage
	 * @return
	 */
	public Page getYwyEmployee(String con, int curPage, int rowPerPage) {
		String sql = "select a.*,b.dept_name from sys_user a left join dept b on b.dept_id=a.dept where a.is_sys_user=0 and a.is_ywy='是' and a.is_del='0'";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return getResultByPage(sql, curPage, rowPerPage);
	}


	/**
	 * 保存用户信息
	 * 
	 * @param user
	 */
	public void saveUser(SysUser user) {
		String sql = "insert into sys_user(real_name,csny,sex,gs_phone,mobile,jt_phone,fax,mail,msn,qq,address,p_code,dept,position,szkf,state,xh,is_ywy,gh,nl,china_py,user_id,"
				+"id_card,nation,lxr,relation,jbgz,rzrq,gl,byxx,major,xl,gzjl,ldkh,remark,zzmm,sfjh,jtcy) "
			     + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
			     +"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		String user_id = getUserID();
		//String user_id = user.getUser_id();
		Object[] param = new Object[38];

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

		GB2Alpha gb2Alpha = new GB2Alpha();
		param[20] = gb2Alpha.String2Alpha(user.getReal_name());

		param[21] = user_id;

		param[22] = user.getId_card();
		param[23] = user.getNation();
		param[24] = user.getLxr();
		param[25] = user.getRelation();
		param[26] = new Double(user.getJbgz());
		param[27] = user.getRzrq();
		param[28] = new Integer(user.getGl());
		param[29] = user.getByxx();
		param[30] = user.getMajor();
		param[31] = user.getXl();
		param[32] = user.getGzjl();
		param[33] = user.getLdkh();
		param[34] = user.getRemark();
		param[35] = user.getZzmm();
		param[36] = user.getSfjh();
		param[37] = user.getJtcy();
		this.getJdbcTemplate().update(sql, param);

	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 */
	public void updateUser(SysUser user) {
		String sql = "update sys_user set real_name=?,csny=?,sex=?,gs_phone=?,mobile=?,jt_phone=?,fax=?,mail=?,msn=?,qq=?,address=?,"
				+ "p_code=?,dept=?,position=?,szkf=?,state=?,xh=?,is_ywy=?,gh=?,nl=?,china_py=?,id_card=?,nation=?,lxr=?,"
				+"relation=?,jbgz=?,rzrq=?,gl=?,byxx=?,major=?,xl=?,gzjl=?,ldkh=?,remark=?,zzmm=?,sfjh=?,jtcy=? where user_id=?";

		Object[] param = new Object[38];

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

		GB2Alpha gb2Alpha = new GB2Alpha();
		param[20] = gb2Alpha.String2Alpha(user.getReal_name());

		param[21] = user.getId_card();
		param[22] = user.getNation();
		param[23] = user.getLxr();
		param[24] = user.getRelation();
		param[25] = new Double(user.getJbgz());
		param[26] = user.getRzrq();
		param[27] = new Integer(user.getGl());
		param[28] = user.getByxx();
		param[29] = user.getMajor();
		param[30] = user.getXl();
		param[31] = user.getGzjl();
		param[32] = user.getLdkh();
		param[33] = user.getRemark();
		param[34] = user.getZzmm();
		param[35] = user.getSfjh();
		param[36] = user.getJtcy();
		param[37] = user.getUser_id();

		this.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 根据user_id取用户信息
	 * 
	 * @param user_id
	 * @return
	 */
	public Map getUser(String user_id) {
		String sql = "select * from sys_user where user_id='" + user_id + "'";
		return this.getResultMap(sql);
	}

	
	/**
	 * 删除用户信息
	 * 
	 * @param user_id
	 */
	public void delUser(String user_id) {
		String sql = "update sys_user set is_del='1' where user_id='" + user_id + "'";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 取当前可用的序列号
	 * 
	 * @return
	 */
	public String getUserID() {
		String sql = "select userid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// 将序列号加1
		sql = "update cms_all_seq set userid=userid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 8; i++) {
			curId = "0" + curId;
		}

		return "YH" + curId;
	}

	
	/**
	 * 判断员工姓名是否相同
	 * 
	 * @param employeeName
	 * @return
	 */
	public int getEmployeeByNameIsExist(String employeeName) {
		String sql = "select count(*) as name from sys_user where is_sys_user=0 and is_ywy='是' and is_del='0' and real_name='"
				+ employeeName + "'";
		return this.getJdbcTemplate().queryForInt(sql);
	}

}
