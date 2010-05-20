package com.sw.cms.action;

import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Zxgd;
import com.sw.cms.model.Sfd;
import com.sw.cms.service.ZxgdService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.SfdService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class ZxgdAction extends BaseAction
{
	   private ZxgdService zxgdService;
	   private SjzdService sjzdService;
	   private SfdService sfdService;
	   private String[] wxlx;
	   
	   private Page zxgdPage;
	   private String orderName="";
	   private String orderType="";
	   private int curPage = 1;
	   private String hf_date1 = "";
	   private String hf_date2 = "";
	   private String hfr="";
	   private String client_name="";	
	   private String state="处理中";	    
	   private Map  zxgd;
	   private Zxgd zxgds=new Zxgd();
	   
	   


	/**
	    * 咨询工单列表
	    * @return
	    * 未输入条件时，默认显示所有的未处理的记录
	    */
	   public String list()
	   {
		   try 
		   {
			   int rowsPerPage = Constant.PAGE_SIZE2;
				String con = "";				 
				
				if (!client_name.trim().equals("")) {
					con += " and b.client_name>='" + client_name + "'";
				}
				
				if (!hf_date1.trim().equals("")) {
					con += " and a.hf_date>='" + hf_date1 + "'";
				}
				if (!hf_date2.trim().equals("")) {
					con += " and a.hf_date<='" + (hf_date2 + " 23:59:59") + "'";
				}
				if (!state.equals("")) {
					con += " and a.state='" + state + "'";
				}
				
				if (orderName.equals("")) 
				{
					orderName = "id";
				}
				if (orderType.equals("")) {
					orderType = "desc";
				}
				con += " order by " + orderName + " " + orderType + "";
				zxgdPage = zxgdService.getZxgdList(con, curPage, rowsPerPage);
				return "success";  			 
	       } 
		   catch (Exception e)
	       {
			 log.error("获取咨询工单列表   失败原因:"+e.getMessage());  
			 return  "error";
		   }
		   
	   }
	   
 
	   /**
	    * 修改咨询工单
	    * @return
	    * @throws Exception
	    */
	   public String edit()throws Exception
	   {
		   try
		   {			   
			   String id = ParameterUtility.getStringParameter(getRequest(),
						"id", "");
			  
			   if(zxgdService.isNotExist(id))
			   {
			      zxgdService.saveZxgd(zxgds,id);
			   }
			   zxgd=(Map)zxgdService.getZxgdById(id); 
			   return "success";
		   }
		   catch(Exception e)
		   {
			   log.error("打开修改咨询工单页面  错误原因"+e.getMessage());
			   return "error";
		   }
	   }
	   
	   /**
	    * 查看咨询工单
	    * @return
	    * @throws Exception
	    */
	   public String view()throws Exception
	   {
		   try
		   {			   
			   String id = ParameterUtility.getStringParameter(getRequest(),
						"id", "");		  
			   zxgd=(Map)zxgdService.getZxgdById(id);
			   return "success";
		   }
		   catch(Exception e)
		   {
			   log.error("打开咨询工单页面  错误原因"+e.getMessage());
			   return "error";
		   }
	   }
	   
	   /**
	    * 保存修改咨询工单
	    * @return
	    * @throws Exception
	    */
	   public String update()throws Exception
	   {
		   try
		   {	
			   LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
			   String user_id = info.getUser_id();
			   zxgds.setCzr(user_id);

     		   zxgdService.updateZxgd(zxgds);
			   return "success";
		   }
		   catch(Exception e)
		   {
			   log.error("保存修改咨询工单  错误原因"+e.getMessage());
			   return "error";
		   }
	   }
	   
	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getHfr() {
		return hfr;
	}

	public void setHfr(String hfr) {
		this.hfr = hfr;
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

	public Map getZxgd() {
		return zxgd;
	}


	public void setZxgd(Map zxgd) {
		this.zxgd = zxgd;
	}


	public Page getZxgdPage() {
		return zxgdPage;
	}

	public void setZxgdPage(Page zxgdPage) {
		this.zxgdPage = zxgdPage;
	}

	public ZxgdService getZxgdService() {
		return zxgdService;
	}

	public void setZxgdService(ZxgdService zxgdService) {
		this.zxgdService = zxgdService;
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

	public String getHf_date1() {
		return hf_date1;
	}

	public void setHf_date1(String hf_date1) {
		this.hf_date1 = hf_date1;
	}

	public String getHf_date2() {
		return hf_date2;
	}

	public void setHf_date2(String hf_date2) {
		this.hf_date2 = hf_date2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public Zxgd getzxgds() {
		return zxgds;
	}

	public void setZxgds(Zxgd zxgds) {
		this.zxgds = zxgds;
	}
	
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getClient_name() {
		return client_name;
	}
}
