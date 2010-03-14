package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Fysq;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

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
		String sql = "select * from fysq where 1=1  ";
		if(!con.equals("")){
			sql += con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(Fysq.class));
	}
	

	/**
	 * ���ݲ�ѯ����ȡ�������뵥�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public List getFysqList(String con){
		String sql = "select * from fysq where 1=1  ";
		if(!con.equals("")){
			sql += con;
		}
		return this.getResultList(sql, new BeanRowMapper(Fysq.class));
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
		return (Fysq)this.queryForObject(sql, new BeanRowMapper(Fysq.class));
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

}
