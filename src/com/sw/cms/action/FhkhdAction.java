package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.Fhkhd;
import com.sw.cms.model.FhkhdProduct;
import com.sw.cms.model.Jjd;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.FhkhdService;
import com.sw.cms.service.ShkcService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class FhkhdAction extends BaseAction
{
   private FhkhdService fhkhdService;
   private ShkcService  shkcService;
   
   private Page fhkhdPage;
   private Page shkcPage;
   private int  curPage=1;
   private String orderName="";
   private String orderType="";
   private String lxr="";
   private String cj_date1=DateComFunc.getToday();
   private String cj_date2=DateComFunc.getToday();
   private String qz_serial_num="";
   private String state="";
   private String fhr="";
   private String id="";
   
   private Fhkhd fhkhd=new Fhkhd();
   
   private List fhkhdProducts=new ArrayList();
   
   /**
    * 返还客户单列表
    * @return
    * @throws Exception
    */
   public String list()throws Exception
   {
	   try
	   {
		   int rowsPerPage = Constant.PAGE_SIZE2;
		   String con="";
		   if(!lxr.equals(""))
		   {
			   con+=" and f.lxr like '%"+lxr+"%'";
		   }
		   if(!qz_serial_num.equals(""))
		   {
			   con+=" and f.id in (select fhkhd_id from fhkhd_product where qz_serial_num='"+qz_serial_num+"')";
		   }
		   if(!cj_date1.equals(""))
		   {
			   con+=" and f.cj_date>='"+cj_date1+"'";
		   }
		   if(!cj_date2.equals(""))
		   {
			   con+=" and f.cj_date<='"+cj_date2+"'";
		   }
		   if(!state.equals(""))
		   {
			   con+=" and f.state='"+state+"'";
		   }
		   if(!fhr.equals(""))
		   {
			   con+=" and s.real_name like '%"+fhr+"%'";
		   }
		   if(orderName.equals(""))
		   {
			   orderName="id";
		   }
		   if(orderType.equals(""))
		   {
			   orderType="desc";
		   }		   
		   con=con+" order by "+orderName+"  "+orderType+"";
		   fhkhdPage=fhkhdService.getFhkhdList(con, curPage, rowsPerPage);
		   return "success";  
	   }
	   catch(Exception e)
	   {
		   log.error("返还客户单列表 错误原因："+e.getMessage());
		   return "error";
	   }
   }
   
   /**
    * 打开添加返还客户单页面
    * @return
    * @throws Exception
    */
   public String add()throws Exception
   {
	   try
	   {
		   fhkhd.setId(fhkhdService.updateFhkhdId());
		  return "success"; 
	   }
	   catch(Exception e)
	   {
		   log.error("打开添加返还客户单 错误原因："+e.getMessage());
		   return "error";
	   }
   }
   
   /**
	 * 打开选择库存产品列表
	 * 
	 * @return
	 */
	public String selKcProc()throws Exception
	{
		try {
			String product_serial_num = ParameterUtility.getStringParameter(
					getRequest(), "product_serial_num", "");
			            
			int rowsPerPage = 15;

			String con = "";
		 
			if (!product_serial_num.equals("")) {
				con += " and qz_serial_num='" + product_serial_num + "'";
			}

			shkcPage = shkcService.getShkcIsHaoProduct(con, curPage, rowsPerPage);

			return "success";
		} catch (Exception e) {
			log.error("获取好件库列表 错误原因:" + e.getMessage());
			return "error";
		}

	}
   
   /**
    * 保存返还客户单
    * @return
    * @throws Exception
    */
   public String save()throws Exception
   {
	   try
	   {
		   LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		   String user_id = info.getUser_id();
		   fhkhd.setCjr(user_id);
		   
		   if(fhkhd.getState().equals("已提交"))
		   {
			   String msg=fhkhdService.isHaoShkcExist(fhkhdProducts);
			   if(!msg.equals(""))
			   {
				     this.saveMessage(msg);
					 fhkhd.setState("已保存");
					 return "input";
			   }
			  fhkhdService.savefhkhd(fhkhd, fhkhdProducts); 
		   }
		   if(fhkhd.getState().equals("已保存"))
		   {
              fhkhdService.savefhkhd(fhkhd, fhkhdProducts);
		   }
		   return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("保存返还客户单 错误原因:"+e.getMessage());
		   return "error";
	   }
   }
   /**
    * 单击报修返还单查看报修返还信息
    * @return
    * @throws Exception
    */
   public String desc()throws Exception
   {
 	  try
 	  {
 		  String id = ParameterUtility.getStringParameter(getRequest(), "id", ""); 
 		  fhkhdProducts=fhkhdService.getFhkhdProductById(id);
 		  return "success";
 	  }
 	  catch(Exception e)
 	  {
 		  log.error("单击返还客户单查看报修返还信息 错误原因:"+e.getMessage());
 		  return "error";
 	  }
   }
   
   /**
	 * 打开修改返还客户单页面
	 * 
	 * @return
	 */
   public String edit()throws Exception
   {
	   try
	   {
		   fhkhd=(Fhkhd)fhkhdService.getFhkhd(id);
		   fhkhdProducts=fhkhdService.getFhkhdProductById(id);
		  
		   return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("打开修改返还客户单  错误原因"+e.getMessage());
		   return "error";
	   }
   }
   
   /**
    * 修改返还客户单
    * @return
    * @throws Exception
    */
   public String update()throws Exception
   {
	   try
	   {
		   LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		   String user_id = info.getUser_id();
		   fhkhd.setCjr(user_id);
		   
		   if(fhkhd.getState().equals("已提交"))
		   {
			   String msg=fhkhdService.isHaoShkcExist(fhkhdProducts);
			   if(!msg.equals(""))
			   {
				     this.saveMessage(msg);
					 fhkhd.setState("已保存");
					 return "input";
			   }
			  fhkhdService.updatefhkhd(fhkhd, fhkhdProducts); 
		   }
		   if(fhkhd.getState().equals("已保存"))
		   {
              fhkhdService.updatefhkhd(fhkhd, fhkhdProducts);
		   }
		   return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("修改返还客户单 错误原因:"+e.getMessage());
		   return "error";
	   }
   }
   
   /**
    * 删除返还客户单
    * @return
    * @throws Exception
    */
   public String del()throws Exception
   {
	   try
	   {
		   String id = ParameterUtility.getStringParameter(getRequest(), "id", ""); 
		   fhkhdService.delFhkhd(id);
		   return "success";   
	   }
	   catch(Exception e)
	   {
		   log.error("删除返还客户单失败  错误原因"+e.getMessage());
		   return "error";
	   }
   }
   
   
   
   

public String getCj_date1() {
	return cj_date1;
}

public void setCj_date1(String cj_date1) {
	this.cj_date1 = cj_date1;
}

public String getCj_date2() {
	return cj_date2;
}

public void setCj_date2(String cj_date2) {
	this.cj_date2 = cj_date2;
}

public String getFhr() {
	return fhr;
}

public void setFhr(String fhr) {
	this.fhr = fhr;
}

public String getLxr() {
	return lxr;
}

public void setLxr(String lxr) {
	this.lxr = lxr;
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

public String getQz_serial_num() {
	return qz_serial_num;
}

public void setQz_serial_num(String qz_serial_num) {
	this.qz_serial_num = qz_serial_num;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public int getCurPage() {
	return curPage;
}

public void setCurPage(int curPage) {
	this.curPage = curPage;
}

public FhkhdService getFhkhdService() {
	return fhkhdService;
}

public void setFhkhdService(FhkhdService fhkhdService) {
	this.fhkhdService = fhkhdService;
}

public Page getFhkhdPage() {
	return fhkhdPage;
}

public void setFhkhdPage(Page fhkhdPage) {
	this.fhkhdPage = fhkhdPage;
}

public Fhkhd getFhkhd() {
	return fhkhd;
}

public void setFhkhd(Fhkhd fhkhd) {
	this.fhkhd = fhkhd;
}

public List getFhkhdProducts() {
	return fhkhdProducts;
}

public void setFhkhdProducts(List fhkhdProducts) {
	this.fhkhdProducts = fhkhdProducts;
}

public Page getShkcPage() {
	return shkcPage;
}

public void setShkcPage(Page shkcPage) {
	this.shkcPage = shkcPage;
}

public ShkcService getShkcService() {
	return shkcService;
}

public void setShkcService(ShkcService shkcService) {
	this.shkcService = shkcService;
}

 

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}
}
