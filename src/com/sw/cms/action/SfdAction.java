package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Sfd;
import com.sw.cms.service.SfdService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;


public class SfdAction extends BaseAction
{
   private SfdService  sfdService;
   private SjzdService sjzdService;
   private Page sfdPage;
   private String orderName="";
   private String orderType="";
   private int curPage = 1;
   private String jx_date1 = "";
   private String jx_date2 = "";
   private String linkman="";
   private String wx_state="�ѱ���";
   private String state="";
   private String jxr="";
   private Sfd  sfd=new  Sfd();
   private String[] bxyy;
/**
    * �ۺ�����б�
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
				con += " and f.linkman like '%" +linkman +"%'";
			}			
			if (!jx_date1.trim().equals("")) {
				con += " and f.jx_date>='" + jx_date1 + "'";
			}
			if (!jx_date2.trim().equals("")) {
				con += " and f.jx_date<='" + (jx_date2 + " 23:59:59") + "'";
			}
			if (!state.equals("")) {
				con += " and f.state='" + state + "'";
			}
			if (!wx_state.trim().equals("")) {
				con += " and f.wx_state='" + wx_state + "'";
			}
			
			if (!jxr.trim().equals("")) {
				con += " and s.real_name like '%" + jxr + "%'";
			}
			if (orderName.equals("")) {
				orderName = "id";
			}
			if (orderType.equals("")) {
				orderType = "desc";
			}
			con += " order by " + orderName + " " + orderType + "";
			sfdPage = sfdService.getSfdList(con, curPage, rowsPerPage);
			return "success";  
		 
       } 
	   catch (Exception e)
       {
		 log.error("��ȡ�ۺ�����б�   ʧ��ԭ��:"+e.getMessage());  
		 return  "error";
	   }
	   
   }
   
   /**
    * ����ۺ����
    * @return
    * @throws Exception
    */
   public String add()throws Exception
   {
	   try
	   {   
		   bxyy = sjzdService.getSjzdXmxxByZdId("SJZD_BXYY");
		   sfd.setId(sfdService.updateSfdId());
		   return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("������ۺ����ҳ��  ����ԭ��"+e.getMessage());
		   return "error";
	   }
   }
   
   /**
    * ����ۺ����
    * @return
    * @throws Exception
    */
   public String save()throws Exception
   {
	   try
	   {
		   LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			sfd.setCjr(user_id);
			if(sfd.getState().equals("�ѱ���"))
			{
				sfd.setWx_state("�ѱ���");
				
				sfdService.saveSfd(sfd);
			}
			else
			{
				sfdService.updateSfd(sfd);
			}
		 	
			this.saveMessage("�����ɹ���");
		   return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("�����ۺ����  ����ԭ��"+e.getMessage());
		   return "error";
	   }
   }
   
   /**
    * �޸��ۺ����
    * @return
    * @throws Exception
    */
   public String edit()throws Exception
   {
	   try
	   {  
		   bxyy = sjzdService.getSjzdXmxxByZdId("SJZD_BXYY");		   
		   String sfd_id = ParameterUtility.getStringParameter(getRequest(),
					"id", "");		  
		   sfd=(Sfd)sfdService.getSfdById(sfd_id);
		   return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("���޸��ۺ����ҳ��  ����ԭ��"+e.getMessage());
		   return "error";
	   }
   }
   
   /**
    * �����޸��ۺ����
    * @return
    * @throws Exception
    */
   public String update()throws Exception
   {
	   try
	   {	
		    bxyy = sjzdService.getSjzdXmxxByZdId("SJZD_BXYY");		   
		    LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			sfd.setCjr(user_id);
			if(sfd.getState().equals("�ѱ���"))
			{
				sfd.setWx_state("�ѱ���");
				 sfdService.updateSfd(sfd);
			}
			else
			{
				 sfdService.updateSfd(sfd);
			}
		 			 
		   return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("�����޸��ۺ����  ����ԭ��"+e.getMessage());
		   return "error";
	   }
   }
   
   /**
    * ɾ���ۺ����
    * @return
    * @throws Exception
    */
   public String del()throws Exception
   {
	   try
	   {	
		   String sfd_id = ParameterUtility.getStringParameter(getRequest(),
					"id", "");
		   sfdService.deleteSfd(sfd_id);
		   return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("ɾ���ۺ����  ����ԭ��"+e.getMessage());
		   return "error";
	   }
   }
public int getCurPage() {
	return curPage;
}
public void setCurPage(int curPage) {
	this.curPage = curPage;
}
 
public String getJx_date1() {
	return jx_date1;
}
public void setJx_date1(String jx_date1) {
	this.jx_date1 = jx_date1;
}
public String getJx_date2() {
	return jx_date2;
}
public void setJx_date2(String jx_date2) {
	this.jx_date2 = jx_date2;
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
public Page getSfdPage() {
	return sfdPage;
}
public void setSfdPage(Page sfdPage) {
	this.sfdPage = sfdPage;
}
public SfdService getSfdService() {
	return sfdService;
}
public void setSfdService(SfdService sfdService) {
	this.sfdService = sfdService;
}
 
 
public String getJxr() {
	return jxr;
}

public void setJxr(String jxr) {
	this.jxr = jxr;
}

public String getLinkman() {
	return linkman;
}
public void setLinkman(String linkman) {
	this.linkman = linkman;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getWx_state() {
	return wx_state;
}
public void setWx_state(String wx_state) {
	this.wx_state = wx_state;
}

public Sfd getSfd() {
	return sfd;
}

public void setSfd(Sfd sfd) {
	this.sfd = sfd;
}
public SjzdService getSjzdService() {
	return sjzdService;
}

public void setSjzdService(SjzdService sjzdService) {
	this.sjzdService = sjzdService;
}

public String[] getBxyy() {
	return bxyy;
}

public void setBxyy(String[] bxyy) {
	this.bxyy = bxyy;
}
}
