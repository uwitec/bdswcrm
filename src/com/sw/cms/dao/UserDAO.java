package com.sw.cms.dao;

/**
 * author by liyt
 * ��ϵͳ�û���Ȩ�޲������е����ݿ�������ڴ����
 * 2008-03-27
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;
import com.sw.cms.util.GB2Alpha;

public class UserDAO extends JdbcBaseDAO {

	
	/**
	 * �����û���������ȡ�û���Ϣ����ǰ��Ч���û���
	 * ���ڵ�½��֤
	 * @param user_name
	 * @param password
	 * @return
	 */
	public Object getUserByNameAndPass(String user_name,String password){
		String sql = "select * from sys_user where is_sys_user='1' and is_del='0' and user_name=? and password=?";
		
		Object[] param = {user_name,password};
		
		return this.queryForObject(sql,param,new SysUserRowMapper());
	}
	
	
	/**
	 * �ж��û����Ƿ����
	 * @param user_name
	 * @return
	 */
	public int getUserByName(String user_name){
		String sql = "select count(*) as allcounts from sys_user where user_name='" + user_name + "'";
		return this.getJdbcTemplate().queryForInt(sql);
	}
	
	
	/**
	 * ��ȡ����ҵ��Ա�б�
	 * @return
	 */
	public List getAllUserList(){		
		//����Ϊҵ��Ա��Ա����Ϣ
		String sql = "select   user_id,real_name,china_py from sys_user where is_sys_user='0' and is_ywy='��' and is_del='0'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	
	/**
	 * ����������ȡ��������ʾ��Ϣ
	 * @param paramValue
	 * @return
	 */
	public List getUserListAjaxTip(String paramValue){
		//����Ϊҵ��Ա��Ա����Ϣ
		String sql = "select user_id,real_name,china_py from sys_user where is_sys_user='0' and is_ywy='��' and is_del='0'  and (zzzt='��ְ' or zzzt='' or zzzt is null) ";
		
		if(!paramValue.equals("")){
			sql += "and (real_name like '%" + paramValue + "%' or china_py like '%" + paramValue.toUpperCase() + "%')";
		}
		return this.getJdbcTemplate().queryForList(sql);
	}
	 
	
	
	/**
	 * ��������ȡҵ��Ա�б�
	 * @param con
	 * @return
	 */
	public List getYwryListByCon(String con){
		String sql = "select * from sys_user where is_sys_user='0' and is_ywy='��' and is_del='0'";
		
		if(!con.equals("")){
			sql += con;
		}
		
		return this.getResultList(sql, new SysUserRowMapper());
	}
	
	
	/**
	 * ��ȡ����Ա���б�
	 * @return
	 */
	public List getAllEmployeeList(){
		String sql = "select user_id,real_name,rzrq from sys_user where is_sys_user='0' and is_del='0'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ����ϵͳ�û��б�
	 * @return
	 */
	public List getAllSysUserList(){
		String sql = "select * from sys_user where is_sys_user='1' and is_del='0'";
		return this.getResultList(sql);
	}
	
	/**
	 * ȡϵͳ�����û��б����ؽ����ΪUSER����
	 * @return
	 */
	public List getAllSysUserListObj(){
		String sql = "select * from sys_user where is_sys_user='1' and is_del='0'";
		return this.getResultList(sql,new SysUserRowMapper());
	}
	
	
	/**
	 * ��������ȡҵ��Ա�б�
	 * @param condition
	 * @return
	 */
	public List getUserListByStr(String condition){
		String sql = "select user_id,real_name from sys_user where is_sys_user='0' and is_ywy='��' and is_del='0'";
		
		if(!condition.equals("") && !condition.equals("''")){
			sql = sql + " and user_id in (" + condition + ")";
		}
		
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	/**
	 * ȡ�û��б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserList(String con,int curPage, int rowsPerPage){
		String sql = "select a.*,b.dept_name from sys_user a left join dept b on b.dept_id=a.dept where a.is_sys_user='1' and a.is_del='0'";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	/**
	 * ȡ���е��û���Ϣ
	 * ȡ�û��б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserListAll(String con,int curPage, int rowsPerPage){
		String sql = "select a.*,b.dept_name from sys_user a left join dept b on b.dept_id=a.dept where a.is_sys_user='1' ";
		
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
		String sql = "insert into sys_user(user_name,password,real_name,csny,gs_phone,mobile,jt_phone,fax,mail,msn,qq,address,p_code,dept,position,szkf,state,xh,is_sys_user,is_dls,client_name,china_py,user_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		String user_id = getUserID();
		
		Object[] param = new Object[23];
		
		param[0] = user.getUser_name();
		param[1] = user.getPassword();
		param[2] = user.getReal_name();
		param[3] = user.getCsny();
		param[4] = user.getGs_phone();
		param[5] = user.getMobile();
		param[6] = user.getJt_phone();
		param[7] = user.getFax();
		param[8] = user.getMail();
		param[9] = user.getMsn();
		param[10] = user.getQq();
		param[11] = user.getAddress();
		param[12] = user.getP_code();
		param[13] = user.getDept();
		param[14] = user.getPosition();
		param[15] = user.getSzkf();
		param[16] = user.getState();
		param[17] = new Integer(user.getXh());
		param[18] = "1";
		param[19] = user.getIs_dls();
		param[20] = user.getClient_name();
		
		GB2Alpha gb2Alpha = new GB2Alpha();
		param[21] = gb2Alpha.String2Alpha(user.getReal_name());
		
		param[22] = user_id;
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * �����û���Ϣ
	 * @param user
	 */
	public void updateUser(SysUser user){
		String sql = "update sys_user set user_name=?,real_name=?,csny=?,gs_phone=?,mobile=?,jt_phone=?,fax=?,mail=?,msn=?,qq=?,address=?,p_code=?,dept=?,position=?,szkf=?,state=?,xh=?,is_dls=?,client_name=?,china_py=? where user_id=?";
		
		Object[] param = new Object[21];
		
		param[0] = user.getUser_name();
		param[1] = user.getReal_name();
		param[2] = user.getCsny();
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
		param[17] = user.getIs_dls();
		param[18] = user.getClient_name();
		
		GB2Alpha gb2Alpha = new GB2Alpha();
		param[19] = gb2Alpha.String2Alpha(user.getReal_name());
		
		param[20] = user.getUser_id();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ����user_idȡ�û���Ϣ
	 * @param user_id
	 * @return
	 */
	public Object getUser(String user_id){
		String sql = "select * from sys_user where user_id='" + user_id + "'";
		
		return this.queryForObject(sql, new SysUserRowMapper());
	}
	
	
	/**
	 * ɾ���û���Ϣ
	 * �û�ɾ�����ü�ɾ����ʽʵ��
	 * @param user_id
	 */
	public void delUser(String user_id){
		//String sql = "delete from sys_user where user_id='" + user_id + "'";
		String sql = "update sys_user set is_del='1' where user_id='" + user_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ��ԭɾ�����û���Ϣ
	 * @param flag
	 * @param user_id
	 */
	public void restoreUser(String user_id,String flag){
		String sql = "update sys_user set is_del='" + flag + "' where user_id='" + user_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * �޸�����
	 * @param user_name
	 * @param password
	 */
	public void updatePass(String user_name,String password){
		String sql = "update sys_user set password=? where user_name=?";
		
		Object[] param = new Object[2];
		param[0] = password;
		param[1] = user_name;
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * �����û�ID�����û�����
	 * @param user_id
	 * @return
	 */
	public String getRealNameById(String user_id){
		String name = "";
		String sql = "select real_name from sys_user where user_id='" + user_id + "'";
		
		Map map = this.getResultMap(sql);
		if(map != null){
			name = (String)map.get("real_name");
		}
		
		return name;
	}
	
	
	/**
	 * �����û�ĩ�ε�¼��Ϣ
	 * @param user_id
	 */
	public void updateUserLastLogin(String user_id,Date last_login_date,String last_login_ip){
		String sql = "update sys_user set last_login_time=?,last_login_ip=? where user_id=?";
		
		Object[] param = new Object[3];
		param[0] = last_login_date;
		param[1] = last_login_ip;
		param[2] = user_id;
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * �����û�ĩ���˳���Ϣ
	 * @param user_id
	 * @param last_logout_date
	 */
	public void updateUserLastLogout(String user_id,Date last_logout_date){
		String sql = "update sys_user set last_logout_time=? where user_id=?";
		
		Object[] param = new Object[2];
		param[0] = last_logout_date;
		param[1] = user_id;
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ���ؾ��з�������Ȩ�޵��û��б�
	 * @return
	 */
	public List getFysqUsers(){
		String sql = "select b.user_id,c.real_name,c.mobile from role_func a inner join user_role b on b.role_id=a.role_id inner join sys_user c on c.user_id=b.user_id where a.func_id='FC0069'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ���ؾ��м۸�����Ȩ�޵��û��б�
	 * @return
	 */
	public List getJgspUsers(){
		String sql = "select c.user_id,c.real_name,c.mobile from jgsp_right_roles a inner join user_role b on b.role_id=a.role_id inner join sys_user c on c.user_id=b.user_id";
		return this.getResultList(sql);
	}
	
	/**
	 * �ж��û��Ƿ��м۸�����Ȩ��
	 * @return
	 */
	public boolean isHasJgspRight(String user_id){
		boolean is = false;
		
		String sql = "select count(*) as counts from jgsp_right_roles a inner join user_role b on b.role_id=a.role_id inner join sys_user c on c.user_id=b.user_id where b.user_id='" + user_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		
		return is;
	}
	
	
	/**
	 * ���ؾ��г�������Ȩ�޵��û��б�
	 * @return
	 */
	public List getCqspUsers(){
		String sql = "select c.user_id,c.real_name,c.mobile from cqsp_right_roles a inner join user_role b on b.role_id=a.role_id inner join sys_user c on c.user_id=b.user_id";
		return this.getResultList(sql);
	}
	
	
	/**
	 * �ж��û��Ƿ��г�������Ȩ��
	 * @return
	 */
	public boolean isHasCqspRight(String user_id){
		boolean is = false;
		
		String sql = "select count(*) as counts from cqsp_right_roles a inner join user_role b on b.role_id=a.role_id inner join sys_user c on c.user_id=b.user_id where c.user_id='" + user_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		
		return is;
	}
	
	
	/**
	 * ���ؾ��г�������Ȩ�޵��û��б�
	 * @return
	 */
	public List getCespUsers(){
		String sql = "select c.user_id,c.real_name,c.mobile from cesp_right_roles a inner join user_role b on b.role_id=a.role_id inner join sys_user c on c.user_id=b.user_id";
		return this.getResultList(sql);
	}
	
	
	/**
	 * �ж��û��Ƿ��г�������Ȩ��
	 * @return
	 */
	public boolean isHasCespRight(String user_id){
		boolean is = false;
		
		String sql = "select count(*) as counts from cesp_right_roles a inner join user_role b on b.role_id=a.role_id inner join sys_user c on c.user_id=b.user_id where c.user_id='" + user_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		
		return is;
	}
	
	
	/**
	 * ���ؾ��г����������۸�����Ȩ�޵��û��б�
	 * @return
	 */
	public List getCeAndJgSpUsers(){
		String sql = "select d.user_id,d.real_name,d.mobile from cesp_right_roles a inner join jgsp_right_roles b on b.role_id=a.role_id inner join user_role c on c.role_id=a.role_id inner join sys_user d on d.user_id=c.user_id";
		return this.getResultList(sql);		
	}
	
	
	/**
	 * �ж��û��Ƿ��г��ڶ�۸�����Ȩ��
	 * @return
	 */
	public boolean isHasCeAndJgspRight(String user_id){
		boolean is = false;
		
		String sql = "select count(*) as counts from cesp_right_roles a inner join jgsp_right_roles b on b.role_id=a.role_id inner join user_role c on c.role_id=a.role_id inner join sys_user d on d.user_id=c.user_id where d.user_id='" + user_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		
		return is;
	}
	
	
	/**
	 * ��ǰ�û��Ƿ��вɹ���������Ȩ��
	 * @param con
	 * @return
	 */
	public boolean isUserInRole(String con){
		boolean is = false;
		String sql = "select a.user_id,a.user_name from sys_user a inner join user_role b on b.user_id=a.user_id  where a.is_sys_user='1'";
		if(!con.equals("")){
			sql += con;
		}
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * �ж��û��Ƿ���ĳ���ܲ���Ȩ��
	 * @param func_id
	 * @param user_id
	 * @return
	 */
	public boolean isHasRightFuncs(String func_id,String user_id){
		boolean is = false;
		String sql = "select count(*) as counts from user_role a join role_func b on b.role_id=a.role_id where b.func_id='" + func_id + "' and a.user_id='" + user_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * ���ݹ��ܱ��ȡ�û��б�
	 * @param func_id
	 * @param user_id
	 * @return
	 */
	public List getUserListByFuncId(String func_id){
		String sql = "select a.* from user_role a join role_func b on b.role_id=a.role_id where b.func_id='" + func_id + "'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ���ؽ�ɫ�������е��û�
	 * @param arryRole
	 * @return
	 */
	public List getUserByRoles(String[] arryRole){
		List result = new ArrayList();
		
		String sql = "select a.user_id,a.user_name from sys_user a inner join user_role b on b.user_id=a.user_id  where a.is_sys_user='1'";
		
		String con = "";
		if(arryRole != null && arryRole.length > 0){
			con += " and(";
			for(int i=0;i<arryRole.length;i++){
				if(i == 0){
					con += "b.role_id='" + arryRole[i] + "'";
				}else{
					con += " or b.role_id='" + arryRole[i] + "'";
				}
			}
			con += ")";
			
			sql += con;
			return this.getResultList(sql);
		}else{
			return null;
		}
		
	}

	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	private String getUserID() {
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
	
	
	/**
	 * ����user_idȡ��ɫ�б�
	 * @param user_id
	 * @return
	 */
	public List getUserRoles(String user_id){
		List userRoles = new ArrayList();
		String sql = "select * from user_role where user_id='" + user_id + "'";
		List list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				userRoles.add((String)map.get("role_id"));
			}
		}
		return userRoles;
	}
	
	
	/**
	 * �����û���ɫ
	 * @param user_id
	 * @param role_ids
	 */
	public void saveUserRoles(String user_id,String[] role_ids){
		String sql = "delete from user_role where user_id='" + user_id + "'";
		this.getJdbcTemplate().update(sql);
		
		if(role_ids != null && role_ids.length>0){
			for(int i=0;i<role_ids.length;i++){
				sql = "insert into user_role(user_id,role_id) values('" + user_id + "','" + role_ids[i] + "')";
				this.getJdbcTemplate().update(sql);
			}
		}
	}
	
	
	/**
	 * �������۵�����Ȩ�޽�ɫ��Ϣ
	 * @param role_id
	 */
	public void saveXsdSpRightRoles(String[] role_id){
		String sql = "delete from cesp_right_roles";
		this.getJdbcTemplate().update(sql);
		
		if(role_id != null && role_id.length>0){
			for(int i=0;i<role_id.length;i++){
				sql = "insert into cesp_right_roles(role_id) values('" + role_id[i] + "')";
				this.getJdbcTemplate().update(sql);
			}
		}
	}
	
	
	/**
	 * �����û���Ż�ȡ�û����ڿⷿ
	 * @param userId
	 * @return
	 */
	public String getUserSzkf(String userId){
		String szkf = "";
		String sql = "select szkf from sys_user where user_id='" + userId + "'";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list.get(0);
			szkf = (String)map.get("szkf");
		}
		return szkf;
	}
	
	public void updateUserGL(String user_id,int gl){
		String sql = "update sys_user set gl='" + gl + "' where user_id='" + user_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ��ȡǿ�����к������Ϣ
	 * @return
	 */
	public Map getQzxlhRight(){
		String sql = "select * from qzxlh_right ";
		return this.getResultMap(sql);
	}
	
	/**
	 * ����ǿ�����к������Ϣ
	 * @param sp_flag
	 * 
	 */
	public void saveQzxlhRight(String sp_flag){
		String sql = "delete from qzxlh_right";
		this.getJdbcTemplate().execute(sql);
		
		sql = "insert into qzxlh_right(sp_flag) values('" + sp_flag + "')";
		this.getJdbcTemplate().execute(sql);
	}
	
	
	/**
	 * ��װ����
	 * 
	 * @author liyt
	 * 
	 */
	class SysUserRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysUser user = new SysUser();

			if(SqlUtil.columnIsExist(rs,"user_id")) user.setUser_id(rs.getString("user_id"));
			if(SqlUtil.columnIsExist(rs,"user_name")) user.setUser_name(rs.getString("user_name"));
			if(SqlUtil.columnIsExist(rs,"password")) user.setPassword(rs.getString("password"));
			if(SqlUtil.columnIsExist(rs,"csny")) user.setCsny(rs.getString("csny"));
			if(SqlUtil.columnIsExist(rs,"real_name")) user.setReal_name(rs.getString("real_name"));
			
			if(SqlUtil.columnIsExist(rs,"gs_phone")) user.setGs_phone(rs.getString("gs_phone"));
			if(SqlUtil.columnIsExist(rs,"jt_phone")) user.setJt_phone(rs.getString("jt_phone"));
			if(SqlUtil.columnIsExist(rs,"fax")) user.setFax(rs.getString("fax"));
			if(SqlUtil.columnIsExist(rs,"mobile")) user.setMobile(rs.getString("mobile"));
			if(SqlUtil.columnIsExist(rs,"mail")) user.setMail(rs.getString("mail"));
			if(SqlUtil.columnIsExist(rs,"msn")) user.setMsn(rs.getString("msn"));
			if(SqlUtil.columnIsExist(rs,"qq")) user.setQq(rs.getString("qq"));
			if(SqlUtil.columnIsExist(rs,"address")) user.setAddress(rs.getString("address"));
			if(SqlUtil.columnIsExist(rs,"p_code")) user.setP_code(rs.getString("p_code"));
			
			if(SqlUtil.columnIsExist(rs,"dept")) user.setDept(rs.getString("dept"));
			if(SqlUtil.columnIsExist(rs,"position")) user.setPosition(rs.getString("position"));
			if(SqlUtil.columnIsExist(rs,"szkf")) user.setSzkf(rs.getString("szkf"));
			if(SqlUtil.columnIsExist(rs,"state")) user.setState(rs.getString("state"));
			
			if(SqlUtil.columnIsExist(rs,"xh")) user.setXh(rs.getInt("xh"));
			
			if(SqlUtil.columnIsExist(rs,"is_dls")) user.setIs_dls(rs.getString("is_dls"));
			if(SqlUtil.columnIsExist(rs,"client_name")) user.setClient_name(rs.getString("client_name"));
			if(SqlUtil.columnIsExist(rs,"china_py")) user.setChina_py(rs.getString("china_py"));
			
			return user;
		}
	}
}
