package com.sw.cms.dao;

/**
 * 采购发票数据库操作
 * author by liyt
 * 2008-03-27
 */

import java.util.List;
import java.util.Map;
import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Jhd;
import com.sw.cms.model.Cgfpd;

import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class CgfpDAO extends JdbcBaseDAO {
	
	
	/**
	 * 取采购发票列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgfps(String con,int curPage, int rowsPerPage){
		String sql = "select count(a.jhd_id) as cgnums,sum(a.total) as cgmoney,b.name as gysmc,a.gysbh from cgfpd a  left join clients b on a.gysbh=b.id where 1=1  ";
		
		if(!con.equals("")){
			sql = sql + " " + con+" group by a.gysbh,b.name";
		}
				
		return this.getResultByPage(sql, curPage, rowsPerPage);

	}
	
	
	
	/**
	 * 根据供应商编号取相关信息
	 * @param gysbh
	 * @return
	 */
	public Object getCgfp(String gysbh){
		String sql = "select * from cgfpd where gysbh='" + gysbh + "'";
		return this.queryForObject(sql,new BeanRowMapper(Cgfpd.class));
	}
	
	
	/**
	 * 根据供应商编号取明细信息
	 * @param gysbh
	 * @return
	 */
	public List getCgfpDesc(String gysbh){
		String sql = "select * from cgfpd where gysbh='" + gysbh + "' and state='未入库'";
		return this.getResultList(sql);
	}
	
	
	
	/**更新采购发票状态信息
	 * 
	 * @param cgfpDescs
	 * 
	 */
	public void updateCgfp(Cgfpd cgfpd,List cgfpDescs)
	{
		this.updateCgfpdesc(cgfpDescs,cgfpd.getGysbh()); //添加采购发票单新的关联发票
	}
	
	private void updateCgfpdesc(List cgfpDescs,String gysbh)
	{
		String sql = "";
		Object[] param = new Object[3];
		
		if(cgfpDescs != null && cgfpDescs.size()>0)
		{
			for(int i =0;i<cgfpDescs.size();i++)
			{
				Cgfpd cgfpd = (Cgfpd)cgfpDescs.get(i);
				if(cgfpd != null)
				{
						sql = "update cgfpd set state=? where jhd_id=? and gysbh=?";
						param[0] = cgfpd.getState();
						param[1] = cgfpd.getJhd_id();
						param[2] = cgfpd.getGysbh();
						
				       this.getJdbcTemplate().update(sql,param); 
				}
						
				
			}
		}
	}
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getCgfpdID() {
		String sql = "select cgfpdid from cms_all_seq";
		
		String day = DateComFunc.getCurDay();

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// 将序列号加1
		sql = "update cms_all_seq set cgfpdid=cgfpdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "CGFP" + day + "-" + curId;
	}
	
	/**
	 * 保存采购发票单信息
	 * @param cgfpd
	 * 
	 */
	public void saveCgfpd(Cgfpd cgfpd){
		String sql = "insert into cgfpd(id,jhd_id,cg_date,state,ms,czr,cz_date,gysbh,total) values(?,?,?,?,?,?,now(),?,?)";
		
		String cgfpd_id = cgfpd.getId();
		Object[] param = new Object[8];
		param[0] = cgfpd_id;
		param[1] = cgfpd.getJhd_id();
		param[2] = cgfpd.getCg_date();
		param[3] = cgfpd.getState();
		param[4] = cgfpd.getMs();
		param[5] = cgfpd.getCzr();
		param[6] = cgfpd.getGysbh();
		param[7] = new Double(cgfpd.getTotal());
		
		this.getJdbcTemplate().update(sql,param);  //添加采购发票单信息
	}
}
