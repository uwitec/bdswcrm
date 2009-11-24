package com.sw.cms.action;

import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pgd;
import com.sw.cms.model.Sfd;
import com.sw.cms.service.PgdService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class PgdAction extends BaseAction
{
	   private PgdService pgdService;
	   private SjzdService sjzdService;
	   
	   private String[] wxlx;
	   
	   private Page pgdPage;
	   private String orderName="";
	   private String orderType="";
	   private int curPage = 1;
	   private String p_cj_date1 = DateComFunc.getToday();
	   private String p_cj_date2 = DateComFunc.getToday();
	   private String linkman="";
	   private String p_wx_state="";
	   private String p_state="";	    
	   private Map  pgd;
	   private Pgd pgds=new Pgd();
	   
	   


	/**
	    * 售后服务单列表
	    * @return
	    */
	   public String list()
	   {
		   try 
		   {
			   int rowsPerPage = Constant.PAGE_SIZE2;
				String con = "";				 
				if (!linkman.trim().equals("")) 
				{
					con += " and linkman like '%" +linkman +"%'";
				}			
				if (!p_cj_date1.trim().equals("")) {
					con += " and p_cj_date>='" + p_cj_date1 + "'";
				}
				if (!p_cj_date2.trim().equals("")) {
					con += " and p_cj_date<='" + (p_cj_date2 + " 23:59:59") + "'";
				}
				if (!p_state.equals("")) {
					con += " and p_state='" + p_state + "'";
				}
				if (!p_wx_state.trim().equals("")) {
					con += " and p_wx_state='" + p_wx_state + "'";
				}			 
				if (orderName.equals("")) 
				{
					orderName = "p_id";
				}
				if (orderType.equals("")) {
					orderType = "desc";
				}
				con += " order by " + orderName + " " + orderType + "";
				pgdPage = pgdService.getPgdList(con, curPage, rowsPerPage);
				return "success";  			 
	       } 
		   catch (Exception e)
	       {
			 log.error("获取派工单列表   失败原因:"+e.getMessage());  
			 return  "error";
		   }
		   
	   }
	   
 
	   /**
	    * 修改售后服务单
	    * @return
	    * @throws Exception
	    */
	   public String edit()throws Exception
	   {
		   try
		   {			   
			   String sfd_id = ParameterUtility.getStringParameter(getRequest(),
						"id", "");		  
			   pgd=(Map)pgdService.getPgdBySfdId(sfd_id);
			   wxlx = sjzdService.getSjzdXmxxByZdId("SJZD_GZLX");	
			   
			   return "success";
		   }
		   catch(Exception e)
		   {
			   log.error("打开修改派工单页面  错误原因"+e.getMessage());
			   return "error";
		   }
	   }
	   
	   /**
	    * 保存修改派工服务单
	    * @return
	    * @throws Exception
	    */
	   public String update()throws Exception
	   {
		   try
		   {	
			   LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
			   String user_id = info.getUser_id();
			   pgds.setP_cjr(user_id);
			    if(pgds.getP_state().equals("已保存"))
				{
				   pgds.setP_wx_state("待处理");
				   pgdService.updatePgd(pgds);
				}
				if(pgds.getP_state().equals("已提交"))
				{
					if(pgds.getP_wx_state().equals("已处理"))
					{
						pgds.setP_jd_date(DateComFunc.getToday());
					}
					pgdService.updatePgd(pgds);
				}
			   
			   return "success";
		   }
		   catch(Exception e)
		   {
			   log.error("保存修改派工单  错误原因"+e.getMessage());
			   return "error";
		   }
	   }
	   
	  

	 
	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

 

	public Map getPgd() {
		return pgd;
	}


	public void setPgd(Map pgd) {
		this.pgd = pgd;
	}


	public Page getPgdPage() {
		return pgdPage;
	}

	public void setPgdPage(Page pgdPage) {
		this.pgdPage = pgdPage;
	}

	public PgdService getPgdService() {
		return pgdService;
	}

	public void setPgdService(PgdService pgdService) {
		this.pgdService = pgdService;
	}
 

	 

	public SjzdService getSjzdService() {
		return sjzdService;
	}


	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}


	public String[] getWxlx() {
		return wxlx;
	}


	public void setWxlx(String[] wxlx) {
		this.wxlx = wxlx;
	}


	public String getP_cj_date1() {
		return p_cj_date1;
	}


	public void setP_cj_date1(String p_cj_date1) {
		this.p_cj_date1 = p_cj_date1;
	}


	public String getP_cj_date2() {
		return p_cj_date2;
	}


	public void setP_cj_date2(String p_cj_date2) {
		this.p_cj_date2 = p_cj_date2;
	}


	public String getP_state() {
		return p_state;
	}


	public void setP_state(String p_state) {
		this.p_state = p_state;
	}


	public String getP_wx_state() {
		return p_wx_state;
	}


	public void setP_wx_state(String p_wx_state) {
		this.p_wx_state = p_wx_state;
	}


	public Pgd getPgds() {
		return pgds;
	}


	public void setPgds(Pgd pgds) {
		this.pgds = pgds;
	}


 
}
