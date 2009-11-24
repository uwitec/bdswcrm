package com.sw.cms.dao;

import com.sw.cms.dao.SfdDAO.SfdRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pgd;
import com.sw.cms.model.Sfd;
import com.sw.cms.util.DateComFunc;

public class PgdDAO extends JdbcBaseDAO
{
	
	/**
	 * ��ȡ�ɹ����б�����ҳ��
	 * @param con
	 * @param cruPage
	 * @param rowsPerPage
	 * @return
	 */
   public Page getPgdList(String con,int cruPage,int rowsPerPage)
   {
	   String sql="select * from pgd p left join sfd s on p.p_sfd_id=s.id   where 1=1 ";
	   if(!con.equals(""))
	   {
		   sql=sql+con;
	   }
	   return this.getResultByPage(sql, cruPage, rowsPerPage);
   }
   
   public void savePgd(Pgd pgd) 
   {
		String sql = "insert into pgd(p_id,p_sfd_id,p_gzlx,p_khxz,p_wx_type,p_wxpz,p_gzfx,p_pgr,p_wxr,p_cjr,p_state,p_wx_state,p_jd_date,p_cj_date,p_yy_date,p_ms)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[16];
		param[0] = pgd.getP_id();
		param[1] = pgd.getP_sfd_id();
		param[2] = pgd.getP_gzlx();
		param[3] = pgd.getP_khxz();
		param[4] = pgd.getP_wx_type();
		param[5] = pgd.getP_wxpz();
		param[6] = pgd.getP_gzfx();	 
		param[7] = pgd.getP_pgr();
		param[8] = pgd.getP_wxr();
		param[9] = pgd.getP_cjr();
		param[10]=pgd.getP_state();
		param[11]= pgd.getP_wx_state();
		param[12]= pgd.getP_jd_date();
		param[13]= DateComFunc.getToday();	
		param[14]= pgd.getP_yy_date();
		param[15]= pgd.getP_ms();
		getJdbcTemplate().update(sql,param);		 
  }
   
   /**
    * �޸��ɹ���      
    * @param pgd
    */
   public void updatePgd(Pgd pgd)
   {
	   String sql = "update pgd set p_sfd_id=?,p_gzlx=?,p_khxz=?,p_wx_type=?,p_wxpz=?,p_gzfx=?,p_pgr=?,p_wxr=?,p_cjr=?,p_state=?,p_wx_state=?,p_jd_date=?,p_cj_date=?,p_yy_date=?,p_ms=? where p_id=?";
		Object[] param = new Object[16];
		
		param[0] = pgd.getP_sfd_id();
		param[1] = pgd.getP_gzlx();
		param[2] = pgd.getP_khxz();
		param[3] = pgd.getP_wx_type();
		param[4] = pgd.getP_wxpz();
		param[5] = pgd.getP_gzfx();	 
		param[6] = pgd.getP_pgr();
		param[7] = pgd.getP_wxr();
		param[8] = pgd.getP_cjr();
		param[9]=pgd.getP_state();
		param[10]= pgd.getP_wx_state();
		param[11]= pgd.getP_jd_date();
		param[12]= DateComFunc.getToday();	
		param[13]= pgd.getP_yy_date();
		param[14]= pgd.getP_ms();
		param[15] = pgd.getP_id();
		getJdbcTemplate().update(sql,param);
   }
   
   /**
    * �����ɹ�����Ϣ
    * @param sfd_id
    * @return
    */
   public Object getPgdBySfdId(String sfd_id)
   {
	   String sql="select * from  pgd p left join sfd s on p.p_sfd_id=s.id where s.id='"+sfd_id+"'";
	   return this.getResultMap(sql);
   }
   /**
    * �ı��ɹ���ά��״̬
    * @param sfd
    */
   public void updatePgdState(Pgd pgd)
   {
		String sql="update pgd set p_wx_state=?,p_jd_date=? where p_id=?";
		Object[] param = new Object[3];
		param[0]=pgd.getP_wx_state();
		param[1]=pgd.getP_jd_date();
		param[2]=pgd.getP_id();
		getJdbcTemplate().update(sql, param);
   }
   
   /**
	 * �����ɹ����Ų�ѯ�ۺ����
	 * @param pgd_id
	 * @return
	 */
	public Object getSfdByPgdId(String pgd_id)
	{
		String sql="select * from pgd where p_id='"+pgd_id+"'";
		return this.getResultMap(sql);
	}
   
   /**
	 * �ɹ�������ID
	 * 
	 * @return
	 */
	public String updatePgdId() {
		String sql = "select pgdid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set pgdid=pgdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "PG" + day + "-" + curId;

	}
   
   
}
