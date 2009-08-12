package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Fysq;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StaticParamDo;

/**
 * ��������
 * @author liyt
 *
 */
public class FysqDAO extends JdbcBaseDAO {
	
	
	/**
	 * ���ݲ�ѯ����ȡ�������뵥�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getFysqList(String con,int curPage, int rowsPerPage){
		String sql = "select * from fysq where 1=1";
		if(!con.equals("")){
			sql += con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage, new FysqRowMapper());
	}
	
	
	/**
	 * ȡ�������������뵥�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getDspFysqList(String con,int curPage, int rowsPerPage){
		String sql = "select * from fysq where state='�ύ'";
		if(!con.equals("")){
			sql += con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage, new FysqRowMapper());
	}
	
	
	/**
	 * ���·���������Ϣ
	 * @param fysq
	 */
	public void updateFysq(Fysq fysq){
		String sql = "select * from fysq where id='" + fysq.getId() + "'";
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			//���ݴ��ڣ�����
			sql = "update fysq set creatdate=?,ywy_id=?,xgkh=?,fy_type=?,je=?,fklx=?,zfzh=?,remark=?,state=?,czr=?,spr=?,sp_date=now(),cz_date=now(),sqr=?,ywy_dept=? where id=?";
		}else{
			//���ݲ����ڣ�����
			sql = "insert into fysq(creatdate,ywy_id,xgkh,fy_type,je,fklx,zfzh,remark,state,czr,spr,sp_date,cz_date,sqr,ywy_dept,id) values(?,?,?,?,?,?,?,?,?,?,?,now(),now(),?,?,?)";
		}
		
		Object[] param = new Object[14];
		param[0] = fysq.getCreatdate();
		param[1] = fysq.getYwy_id();
		param[2] = fysq.getXgkh();
		param[3] = fysq.getFy_type();
		param[4] = fysq.getJe();
		param[5] = fysq.getFklx();
		param[6] = fysq.getZfzh();
		param[7] = fysq.getRemark();
		param[8] = fysq.getState();
		param[9] = fysq.getCzr();
		param[10] = fysq.getSpr();
		param[11] = fysq.getSqr();
		param[12] = fysq.getYwy_dept();
		param[13] = fysq.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ȡ�������������Ϣ
	 * @param id
	 * @return
	 */
	public Fysq getFysq(String id){
		String sql = "select * from fysq where id='" + id + "'";
		return (Fysq)this.queryForObject(sql, new FysqRowMapper());
	}
	
	
	/**
	 * ɾ������������Ϣ
	 * @param id
	 */
	public void delFysq(String id){
		String sql = "delete from fysq where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ���·������뵥״̬
	 * @param id
	 * @param state
	 */
	public void updateFysqState(String id,String state){
		String sql = "update fysq set state='" + state + "' where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getFysqID() {
		String sql = "select fysqid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();
		
		// �����кż�1
		sql = "update cms_all_seq set fysqid=fysqid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "FYSQ" + day + "-" + curId;
	}
	
	
	/**
	 * ���������Ƿ��������
	 * @param id
	 * @return
	 */
	public boolean isFinishSp(String id){
		boolean is = false;
		
		String sql = "select count(*) from fysq where id='" + id + "' and (state='����ͨ��' or state='������ͨ��' or state='��֧��')";
		
		int count = this.getJdbcTemplate().queryForInt(sql);
		
		if(count > 0){
			is = true;
		}
		
		return is;
	}
	
	
	/**
	 * ��װ����(��������)
	 * 
	 * @author liyt
	 * 
	 */
	class FysqRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Fysq fysq = new Fysq();

			if(SqlUtil.columnIsExist(rs,"id")) fysq.setId(rs.getString("id"));
			if(SqlUtil.columnIsExist(rs,"creatdate")) fysq.setCreatdate(rs.getString("creatdate"));
			if(SqlUtil.columnIsExist(rs,"fklx")) fysq.setFklx(rs.getString("fklx"));
			if(SqlUtil.columnIsExist(rs,"fy_type")) fysq.setFy_type(rs.getString("fy_type"));
			if(SqlUtil.columnIsExist(rs,"je")){
				fysq.setJe(rs.getDouble("je"));
				fysq.setStrJe(JMath.round(rs.getDouble("je"),2));
				fysq.setStrJe2(JMath.round(rs.getDouble("je")));
			}
			if(SqlUtil.columnIsExist(rs,"remark")) fysq.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"state")) fysq.setState(rs.getString("state"));
			if(SqlUtil.columnIsExist(rs,"xgkh")) fysq.setXgkh(rs.getString("xgkh"));
			if(SqlUtil.columnIsExist(rs,"ywy_id")) {
				fysq.setYwy_id(rs.getString("ywy_id"));
				fysq.setYwy_name(StaticParamDo.getRealNameById(rs.getString("ywy_id")));
			}
			if(SqlUtil.columnIsExist(rs,"zfzh")){
				fysq.setZfzh(rs.getString("zfzh"));
				fysq.setZfzh_name(StaticParamDo.getAccountNameById(rs.getString("zfzh")));
			}
			if(SqlUtil.columnIsExist(rs,"czr")){
				fysq.setCzr(rs.getString("czr"));
				fysq.setCzr_name(StaticParamDo.getRealNameById(rs.getString("czr")));
			}
			if(SqlUtil.columnIsExist(rs,"spr")){
				fysq.setSpr(rs.getString("spr"));
				fysq.setSpr_name(StaticParamDo.getRealNameById(rs.getString("spr")));
			}
			if(SqlUtil.columnIsExist(rs,"cz_date") && rs.getTimestamp("cz_date") != null){
				fysq.setCz_date(rs.getTimestamp("cz_date").toString());
			}
			if(SqlUtil.columnIsExist(rs,"sp_date") && rs.getTimestamp("sp_date") != null){				
				fysq.setSp_date(rs.getTimestamp("sp_date").toString());
			}
			
			if(SqlUtil.columnIsExist(rs,"sqr")) fysq.setSqr(rs.getString("sqr"));
			if(SqlUtil.columnIsExist(rs,"ywy_dept")) fysq.setYwy_dept(rs.getString("ywy_dept"));
			
			return fysq;
		}
	}

}
