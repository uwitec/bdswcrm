package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;

import com.sw.cms.model.Hykjf;
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

public class HykjfDAO extends JdbcBaseDAO {
	
	/**
	 * ��Ա����Ϣ
	 * @param hykh
	 * @param client_name
	 * 
	 * 
	 * 
	 */
	public Map getHyxx(String hykh,String client_name){
		String dateToday=DateComFunc.getToday();
		String sql = "select b.xfje,b.dyjf,a.hykh,a.yxrq,a.sxrq,d.state,d.sfty from hykzz a left join hykfl c on a.ssfl=c.id "+
		             "left join jfgz b on b.id=c.jffs  left join hykda d on a.hykh=d.hykh"+
		             " where a.hykh='"+hykh+"' and d.hybh='"+client_name+"' and d.state='������' and d.sfty<>'��' and "+
		             "'"+dateToday+"'>=a.yxrq and '"+dateToday+"'<=a.sxrq";
				
		return this.getResultMap(sql);
	}
	
	/**
	 * ��Ա����Ϣ(����)
	 * @param hykh
	 * @param client_name
	 * @param lxr
	 * 
	 * 
	 * 
	 */
	public Map getHyxxls(String hykh,String client_name,String lxr){
		String dateToday=DateComFunc.getToday();
		String sql = "select b.xfje,b.dyjf,a.hykh,a.yxrq,a.sxrq,d.state,d.sfty from hykzz a left join hykfl c on a.ssfl=c.id "+
		             "left join jfgz b on b.id=c.jffs  left join hykda d on a.hykh=d.hykh"+
		             " where a.hykh='"+hykh+"' and (d.hymc='"+client_name+"' or d.hymc='"+lxr+"') and d.state='������' and d.sfty<>'��' and "+
		             "'"+dateToday+"'>=a.yxrq and '"+dateToday+"'<=a.sxrq";
				
		return this.getResultMap(sql);
	}
	
	/**
	 * ���ݻ�Ա���Ż�ȡ��Ա������
	 * @param id
	 * @return
	 */
	public Object getHykjf(String hykh){
		String sql = "select * from hykjf where hykh='" + hykh + "'";
		return this.queryForObject(sql,new BeanRowMapper(Hykjf.class));
	}
	
	/**
	 * �����Ա������
	 * @param fykjf
	 * 
	 */
	public void saveHyxx(Hykjf hykjf){
        String hykh = hykjf.getHykh();
		
		String sql = "select * from hykjf where hykh='" + hykh + "'";
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			//���ڸ���
			sql = "update hykjf set hybh=?,hymc=?,zjf=?,ssjf=? where hykh=?";
		}
		else
		{
             //���������
			sql = "insert into hykjf(hybh,hymc,zjf,ssjf,hykh) values(?,?,?,?,?)";
		}
		Object[] param = new Object[5];
		param[0] = hykjf.getHybh();
		param[1] = hykjf.getHymc();
		param[2] = hykjf.getZjf();
		param[3] = hykjf.getSsjf();
		param[4] = hykjf.getHykh();
		this.getJdbcTemplate().update(sql,param);
	}
	
	/**
	 * �����Ա������(����)
	 * @param fykjf
	 * 
	 */
	public void saveHyxxls(Hykjf hykjf){
        String hykh = hykjf.getHykh();
		
		String sql = "select * from hykjf where hykh='" + hykh + "'";
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			//���ڸ���
			sql = "update hykjf set hymc=?,zjf=?,ssjf=? where hykh=?";
		}
		else
		{
             //���������
			sql = "insert into hykjf(hymc,zjf,ssjf,hykh) values(?,?,?,?)";
		}
		Object[] param = new Object[4];
		
		param[0] = hykjf.getHymc();
		param[1] = hykjf.getZjf();
		param[2] = hykjf.getSsjf();
		param[3] = hykjf.getHykh();
		this.getJdbcTemplate().update(sql,param);
	}
	
	
}
