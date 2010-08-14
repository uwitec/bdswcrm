package com.sw.cms.dao;

import java.util.Map;

import com.sw.cms.dao.SfdDAO.SfdRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Zxgd;
import com.sw.cms.model.Sfd;
import com.sw.cms.util.DateComFunc;

public class ZxgdDAO extends JdbcBaseDAO
{
	
	/**
	 * 获取派工单列表（带分页）
	 * @param con
	 * @param cruPage
	 * @param rowsPerPage
	 * @return
	 */
   public Page getZxgdList(String con,int cruPage,int rowsPerPage)
   {
	   String sql="select a.*,b.client_name,b.linkman,b.linkmanls,b.khlx,b.mobile,b.address from zxgd a left join sfd b on a.sfd_id=b.id   where 1=1 ";
	   if(!con.equals(""))
	   {
		   sql=sql+con;
	   }
	   return this.getResultByPage(sql, cruPage, rowsPerPage);
   }
   
   /**
    * 保存咨询工单      
    * @param zxgd
    */
   public void saveZxgd(Zxgd zxgd) 
   {
		String sql = "insert into zxgd(id,sfd_id,hfr,cz_date,czr,state)values(?,?,?,?,?,?)";
		Object[] param = new Object[6];
		param[0] = zxgd.getId();
		param[1] = zxgd.getSfd_id();
		param[2] = zxgd.getHfr();
		param[3] = DateComFunc.getToday();
		param[4] = zxgd.getCzr();
		param[5] = zxgd.getState();
		
		getJdbcTemplate().update(sql,param);		 
  }
   
   /**
    * 修改咨询工单      
    * @param pgd
    */
   public void updateZxgd(Zxgd zxgd)
   {
	   String sql = "update zxgd set hfr=?,hf_date=?,content=?,khyj=?,state=? where id=?";
		Object[] param = new Object[6];
		
		param[0] = zxgd.getHfr();
		param[1] = zxgd.getHf_date();
		param[2] = zxgd.getContent();
		param[3] = zxgd.getKhyj();
		param[4] = zxgd.getState();
		param[5] = zxgd.getId();	 
		
		getJdbcTemplate().update(sql,param);
   }
   
   /**
    * 返回咨询工单信息
    * @param sfd_id
    * @return
    */
   public Object getZxgdById(String id)
   {
	   String sql="select a.*,b.jx_date,b.client_name,b.jxr,b.linkman,b.mobile,b.address,b.ms from zxgd a left join sfd b on a.sfd_id=b.id where a.sfd_id='"+id+"'";
	   return this.getResultMap(sql);
   }
  
   
   /**
    * 根据售后服务编号判断是否存在在咨询工单中
    * @param id
    * @return
    */
   public int getZxgdNum(String id)
   {
	   String sql="select count(*) from zxgd where sfd_id='"+id+"'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
	 * 根据派工单号查询售后服单
	 * @param pgd_id
	 * @return
	 */
	public Object getSfdByPgdId(String pgd_id)
	{
		String sql="select * from pgd where p_id='"+pgd_id+"'";
		return this.getResultMap(sql);
	}
   
   /**
	 * 咨询工单可用ID
	 * 
	 * @return
	 */
	public String updateZxgdId() {
		String sql = "select zxgdid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set zxgdid=zxgdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "ZX" + day + "-" + curId;

	}
   
   
}
