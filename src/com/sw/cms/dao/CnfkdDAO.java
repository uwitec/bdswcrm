package com.sw.cms.dao;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Cnfkd;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

/**
 * ���ɸ��
 * @author jinyanni
 *
 */
public class CnfkdDAO extends JdbcBaseDAO {
	
	
	/**
	 * ���ݲ�ѯ����ȡ���ɸ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCnfkds(String con,int curPage, int rowsPerPage){
		String sql = "select * from cnfkd where 1=1";
		if(!con.equals("")){
			sql += con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(Cnfkd.class));
	}

	
	/**
	 * ������ɸ��
	 * @param cnfkd
	 */
	public void insertCnfkd(Cnfkd cnfkd){
		String sql = "insert into cnfkd(fk_date,client_name,bank_no,fkje,fklx,lxr,fax,tel,fkfs,fkzh,remark,state,czr,cz_date,cgfk_id,client_all_name,jsr,id) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?)";
		
		Object[] param = new Object[17];
		
		param[0] = cnfkd.getFk_date();
		param[1] = cnfkd.getClient_name();
		param[2] = cnfkd.getBank_no();
		param[3] = cnfkd.getFkje();
		param[4] = cnfkd.getFklx();
		param[5] = cnfkd.getLxr();
		param[6] = cnfkd.getFax();
		param[7] = cnfkd.getTel();
		param[8] = cnfkd.getFkfs();
		param[9] = cnfkd.getFkzh();
		param[10] = cnfkd.getRemark();
		param[11] = cnfkd.getState();
		param[12] = cnfkd.getCzr();
		param[13] = cnfkd.getCgfk_id();
		param[14] = cnfkd.getClient_all_name();
		param[15] = cnfkd.getJsr();
		param[16] = this.getCnfkdID();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ���³��ɸ��
	 * @param cnfkd
	 */
	public void updateCnfkd(Cnfkd cnfkd){
		String sql = "update cnfkd set fk_date=?,client_name=?,bank_no=?,fkje=?,fklx=?,lxr=?,fax=?,tel=?," +
				"fkfs=?,fkzh=?,remark=?,state=?,czr=?,cz_date=now(),cgfk_id=?,client_all_name=?,jsr=? where id=?";

		Object[] param = new Object[17];
		
		param[0] = cnfkd.getFk_date();
		param[1] = cnfkd.getClient_name();
		param[2] = cnfkd.getBank_no();
		param[3] = cnfkd.getFkje();
		param[4] = cnfkd.getFklx();
		param[5] = cnfkd.getLxr();
		param[6] = cnfkd.getFax();
		param[7] = cnfkd.getTel();
		param[8] = cnfkd.getFkfs();
		param[9] = cnfkd.getFkzh();
		param[10] = cnfkd.getRemark();
		param[11] = cnfkd.getState();
		param[12] = cnfkd.getCzr();
		param[13] = cnfkd.getCgfk_id();
		param[14] = cnfkd.getClient_all_name();
		param[15] = cnfkd.getJsr();
		param[16] = cnfkd.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ���ݱ��ȡ���ɸ�����ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Cnfkd getCnfkd(String id){
		String sql = "select * from cnfkd where id='" + id + "'";
		return (Cnfkd)this.queryForObject(sql, new BeanRowMapper(Cnfkd.class));
	}
	
	
	/**
	 * ���ݲɹ��������뵥����ж��Ƿ���ڳ��ɸ��
	 * @param cgfk_id
	 * @return
	 */
	public boolean isExistCnfkdByCgfkId(String cgfk_id){
		boolean is = false;
		String sql = "select count(*) as counts from cnfkd where cgfk_id='" + cgfk_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * ɾ�����ɸ��
	 * @param id
	 */
	public void delCnfkd(String id){
		String sql = "delete from cnfkd where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	private String getCnfkdID() {
		String sql = "select cnfkdid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set cnfkdid=cnfkdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "FK" + day + "-" + curId;
	}
}
