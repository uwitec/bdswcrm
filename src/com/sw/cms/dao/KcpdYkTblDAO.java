package com.sw.cms.dao;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.KcpdYkTbl;

/**
 * ����̵�ӯ������(����仯)
 * @author liyt
 *
 */
public class KcpdYkTblDAO extends JdbcBaseDAO {
	
	/**
	 * ��ӿ���̵�ӯ����(����仯��)
	 * @param kcpdYkTbl
	 * @throws Exception
	 */
	public void insertKcpdYkTbl(KcpdYkTbl kcpdYkTbl){
		String sql = "insert into kcpd_yk_tbl(id,ykje,kcpd_id,type,remark,czr,cz_date,jsr,fs_date) " +
				"values(?,?,?,?,?,?,now(),?,?)";
		
		Object[] param = new Object[8];
		
		param[0] = getKcpdYkID();
		param[1] = kcpdYkTbl.getJe();
		param[2] = kcpdYkTbl.getKcpd_id();
		param[3] = kcpdYkTbl.getType();
		param[4] = kcpdYkTbl.getRemark();
		param[5] = kcpdYkTbl.getCzr();
		param[6] = kcpdYkTbl.getJsr();
		param[7] = kcpdYkTbl.getFs_date();
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	private String getKcpdYkID() {
		String sql = "select kcpdyk_id from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// �����кż�1
		sql = "update cms_all_seq set kcpdyk_id=kcpdyk_id+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 6; i++) {
			curId = "0" + curId;
		}

		return "YK" + curId;
	}

}
