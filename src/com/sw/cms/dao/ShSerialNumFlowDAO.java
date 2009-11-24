package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.ShSerialNumFlow;

/**
 * 售后服务序列号流转记录
 * @author Administrator
 *
 */
public class ShSerialNumFlowDAO extends JdbcBaseDAO
{
	/**
	 * 保存售后序列号流转记录
	 * @param shSerialNumFlow
	 */
   public void saveShSerialNumFlow(ShSerialNumFlow shSerialNumFlow)
   {
	   
	   String sql="insert into sh_serial_num_flow(qz_serial_num,ywtype,client_name,jsr,yw_dj_id,fs_date,yw_url,cj_date,kf,rk_date,linkman)values(?,?,?,?,?,?,?,?,?,?,?)";
	   Object[]param=new Object[11];
	   param[0]=shSerialNumFlow.getQz_serial_num();
	   param[1]=shSerialNumFlow.getYwtype();
	   param[2]=shSerialNumFlow.getClient_name();
	   param[3]=shSerialNumFlow.getJsr();
	   param[4]=shSerialNumFlow.getYw_dj_id();
	   param[5]=shSerialNumFlow.getFs_date();
	   param[6]=shSerialNumFlow.getYw_url();
	   param[7]=shSerialNumFlow.getCj_date();
	   param[8]=shSerialNumFlow.getKf();
	   param[9]=shSerialNumFlow.getRk_date();
	   param[10]=shSerialNumFlow.getLinkman();
	   this.getJdbcTemplate().update(sql,param);
   }
   
   /**
	 * 查询序列号流程
	 * @param serial_num
	 * @return
	 */
	public List getSerialFlow(String serial_num)
	{
		String sql = "select * from sh_serial_num_flow where qz_serial_num='" + serial_num + "'";
		return this.getResultList(sql);
	}
	
   
   
}
