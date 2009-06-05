package com.sw.cms.dao;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.KcpdYkTbl;

/**
 * 库存盘点盈亏处理(账务变化)
 * @author liyt
 *
 */
public class KcpdYkTblDAO extends JdbcBaseDAO {
	
	/**
	 * 添加库存盘点盈亏表(账务变化表)
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
	 * 取当前可用的序列号
	 * @return
	 */
	private String getKcpdYkID() {
		String sql = "select kcpdyk_id from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// 将序列号加1
		sql = "update cms_all_seq set kcpdyk_id=kcpdyk_id+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 6; i++) {
			curId = "0" + curId;
		}

		return "YK" + curId;
	}

}
