package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Fhkhd;
import com.sw.cms.model.FhkhdProduct;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.FhkhdService;
import com.sw.cms.service.ShkcService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.EmployeeService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class FhkhdAction extends BaseAction
{
   private FhkhdService fhkhdService;
   private SjzdService  sjzdService;
   private ClientsService clientsService;   
   private UserService userService;
   private ShkcService      shkcService;
   private EmployeeService      employeeService;
   
   private Page fhkhdPage;
   private Page shkcPage;
   private int  curPage=1;
   private String orderName="";
   private String orderType="";
   private String lxr="";
   private String fh_date1="";
   private String fh_date2="";
   
   private String state="";
   private String client_name="";
   private String id="";
   
   private Fhkhd fhkhd=new Fhkhd();
   
   private List fhkhdProducts=new ArrayList();
   private String[] wxszd;     
   
   private List userList = new ArrayList();
   private List clientsList = new ArrayList();
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
		   if(!lxr.trim().equals(""))
		   {
			   con+=" and b.lxr like '%"+lxr+"%'";
		   }
		   
		   if(!fh_date1.trim().equals(""))
		   {
			   con+=" and b.fh_date>='"+fh_date1+"'";
		   }
		   if(!fh_date2.trim().equals(""))
		   {
			   con+=" and b.fh_date<='"+fh_date2+"'";
		   }
		   if(!state.trim().equals(""))
		   {
			   con+=" and b.state='"+state+"'";
		   }
		   if(!client_name.trim().equals(""))
		   {
			   con+=" and c.name like '%"+client_name+"%'";
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
		    //wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
			//userList = userService.getAllEmployeeList();
			fhkhd.setId(fhkhdService.updatefhkhdId());
			//clientsList = clientsService.getClientList("");
			return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("打开添加返还客户单界面 错误原因："+e.getMessage());
		   return "error";
	   }
   }
   
   /**
	 * 打开选择库存商品列表
	 * 
	 * @return
	 */
	public String selKcProc()throws Exception
	{
		try {
			String product_name = ParameterUtility.getStringParameter(
					getRequest(), "product_name", "");
			            
			int rowsPerPage = 15;

			String con = "";
		 
			if(!product_name.equals("")){
				con += " and (product_name like '%" + product_name + "%' or product_xh like '%" + product_name + "%')";
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
   
   /**
	 * 打开输入序列号窗口
	 * @return
	 */
	public String importSerial(){
		return "success";
	}   
   
   

public String getFh_date1() {
	return fh_date1;
}

public void setFh_date1(String fh_date1) {
	this.fh_date1 = fh_date1;
}

public String getFh_date2() {
	return fh_date2;
}

public void setFh_date2(String fh_date2) {
	this.fh_date2 = fh_date2;
}

public String getClient_name() {
	return client_name;
}

public void setClient_name(String client_name) {
	this.client_name = client_name;
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

public EmployeeService getEmployeeService() {
	return employeeService;
}

public void setEmployeeService(EmployeeService employeeService) {
	this.employeeService = employeeService;
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
