package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;

import com.sw.cms.model.Page;
import com.sw.cms.model.Hykzz;
import com.sw.cms.model.Hykfl;
import com.sw.cms.model.Hykda;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;

/**
 * ��Ա������
 * @author zuohj
 *
 */

public class HykzzDAO extends JdbcBaseDAO {
	
	
	
	/**
	 * ȡ��Ա���б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykzzList(int curPage, int rowsPerPage,String con){
		
		String sql = "select * from hykzz where 1=1 ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(Hykzz.class));
	}
	
	/**
	 * ȡ���л�Ա�������б�
	 * @return
	 */
	public List getAllHykflList(){
		String sql = "select * from hykfl";
		
		return this.getJdbcTemplate().query(sql, new BeanRowMapper(Hykfl.class));
	}
		
	/**
	 * �����Ա����Ϣ
	 * @param info
	 */
	public void saveHykzz(Hykzz info){
		String sql = "insert into hykzz(card_type,dept,csjf,yxrq,sxrq,hykh,ssfl,cz_date,id,czr) values(?,?,?,?,?,?,?,now(),?,?)";
		Object[] param = new Object[9];
		
		param[0] = info.getCard_type();
		param[1] = info.getDept();
		param[2] = info.getCsjf();	
		param[3] = info.getYxrq();
		param[4] = info.getSxrq();
		param[5] = info.getHykh();	
		param[6] = info.getSsfl();
		param[7] = info.getId();
		param[8] = info.getCzr();
		
		this.getJdbcTemplate().update(sql,param);	
		
	}
	
	
	/**
	 * ���»�Ա����Ϣ
	 * @param info
	 */
	public void updateHykzz(Hykzz info){
		String sql = "update hykzz set card_type=?,dept=?,csjf=?,yxrq=?,sxrq=?,hykh=?,ssfl=?,cz_date=now(),czr=? where id=?";
		Object[] param = new Object[9];
		
		param[0] = info.getCard_type();
		param[1] = info.getDept();
		param[2] = info.getCsjf();
		param[3] = info.getYxrq();
		param[4] = info.getSxrq();
		param[5] = info.getHykh();
		param[6] = info.getSsfl();
		param[7] = info.getCzr();
		param[8] = info.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ����IDȡ��Ա����Ϣ
	 * @param id
	 * @return
	 */
	public Hykzz getHykzz(String id){
		Hykzz info = new Hykzz();
		String sql = "select * from hykzz where id='" + id + "'";		
		Object obj =this.queryForObject(sql, new BeanRowMapper(Hykzz.class));
		if(obj != null){
			info = (Hykzz)obj;
		}
		return info;
	}
	
	
	/**
	 * ����IDȡ��Ա��������Ϣ
	 * @param hykh
	 * @return
	 */
	public Hykzz getHykzzda(String hykh){
		Hykzz info = new Hykzz();
		String sql = "select * from hykzz where hykh='" + hykh + "'";		
		Object obj =this.queryForObject(sql, new BeanRowMapper(Hykzz.class));
		if(obj != null){
			info = (Hykzz)obj;
		}
		return info;
	}
	
	/**
	 * ɾ����Ա����Ϣ
	 * @param id
	 */
	public void delHykzz(String hykh){
		String sql = "delete from hykzz where hykh='" + hykh + "'";
		this.getJdbcTemplate().update(sql);
		
	}


	/**
	 * �鿴��Ա���Ƿ��Ѿ��ύ
	 * @param hykzz_id
	 * @return
	 */
	public boolean isHykzzSubmit(String hykzz_id){
		boolean is = false;
		String sql = "select count(*) from hykzz where id='" + hykzz_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	/**
	 * ���ػ�Ա������ID
	 * 
	 * @return
	 */
	public String getHykzzId() {
		String sql = "select hykzzid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set hykzzid=hykzzid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "HYZZ" + day + "-" + curId;

	}
	
	
}
