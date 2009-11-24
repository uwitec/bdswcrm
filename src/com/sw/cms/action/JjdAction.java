package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Jjd;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.JjdService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;
 

public class JjdAction extends BaseAction
{ 
   private JjdService jjdService;
   private ProductKcService productKcService;
   private ProductKindService productKindService;
   
   private Page jjdPage;
   private Page productPage;
   
   private String orderName="";
   private String orderType="";
   private int curPage=1;
   private String jj_date1 = DateComFunc.getToday();
   private String jj_date2 = DateComFunc.getToday();
   private String linkman="";
   private String qz_serial_num="";
   private String state="";
   private String jjr="";
   
   private Jjd  jjd=new Jjd();
   private List jjdProducts=new ArrayList();
   private List kindList=new ArrayList();
   
   private String product_name="";
   private String product_kind="";
   private String id;
   
   public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
/**
    * 返回接件单列表
    * @return
    */
   public String list()throws Exception
   {
	   try
	   {
		 int rowsPerPage = Constant.PAGE_SIZE2;
		 String con="";
		 if(!linkman.equals(""))
		 {
			 con+=" and j.linkman like '%"+linkman+"%'";
		 }	
		 if(!qz_serial_num.equals(""))
		 {
			 con+=" and j.id in (select jjd_id from jjd_product where qz_serial_num='"+qz_serial_num+"')";
		 }
		 if(!jj_date1.equals(""))
		 {
			 con+=" and j.cj_date>='"+jj_date1+"'";
		 }
		 if(!jj_date2.equals(""))
		 {
			 con+=" and j.cj_date<='"+jj_date2+"'";
		 }
		 if(!state.equals(""))
		 {
			 con+=" and j.state='"+state+"'";
		 }
		 if(!jjr.equals(""))
		 {
			 con+=" and s.real_name like '%"+jjr+"%'";
		 }
		 if(orderName.equals(""))
		 {
			 orderName="id"; 
		 }
		 if(orderType.equals(""))
		 {
			 orderType="desc";
		 }
		 con+=" order by "+orderName+"  "+orderType+" ";
		 jjdPage=jjdService.getJjdList(con, curPage, rowsPerPage);
		 return "success";   
	   }
	   catch(Exception e)
	   {
		   log.error("获取接件列表  失败原因:"+e.getMessage());
		   return  "error";
	   }
   }
   /**
    * 打开添加页面
    * @return
    */
   public String add()throws Exception
   {
	   try
	   {
		   jjd.setId(jjdService.updateJjdId());
		   return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("打开接件单添加页面  失败原因："+e.getMessage());
		   return "error";
	   }
   }
   
   /**
    * 保存接件单
    * @return
    */
   public String save()throws Exception
   {
	  try
	  {
		  LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		  String user_id = info.getUser_id();
		  jjd.setCjr(user_id);
		  
		  if(jjd.getState().equals("已提交"))
		  {
			  //判断提交的序列号是否存在一样的
			 String msg= jjdService.isSerialNumExist(jjdProducts);
			 if(!msg.equals(""))
			 {  
				 this.saveMessage(msg);
				 jjd.setState("已保存");
				 return "input";
			 }
			 //判断提交的序列号在售后库存中存在
			 msg=jjdService.isSerialNumInKcExist(jjdProducts);
			 if(!msg.equals(""))
			 { 
				 this.saveMessage(msg);
				 jjd.setState("已保存");
				 return "input";
			 }	
			 //保存信息
			 jjdService.saveJjd(jjd, jjdProducts);
		  }
		  if(jjd.getState().equals("已保存"))
		  {
			   
			  jjdService.saveJjd(jjd, jjdProducts);
		  }		  
		  return "success";
	  }
	  catch(Exception e)
	  {
		  log.error("保存接件单  失败原因："+e.getMessage());
		  return "error";
	  }
   }
   
   /**
    * 修改接件单
    * @return
    * @throws Exception
    */
   public String edit()throws Exception
   {
	   try
	   {
		   jjd=(Jjd)jjdService.getJjd(id);
		   jjdProducts=jjdService.getJjdProducts(id);
		   return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("打开修改接件单  失败原因:"+e.getMessage());
		   return "error";
	   }
   }
   
   /**
    * 保存修改接件单
    * @return
    * @throws Exception
    */
   public String update()throws Exception
   {
	   try
		  {
			  LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			  String user_id = info.getUser_id();
			  jjd.setCjr(user_id);
			  
			  if(jjd.getState().equals("已提交"))
			  {
                 //判断提交的序列号是否存在一样的
				 String msg= jjdService.isSerialNumExist(jjdProducts);
				 if(!msg.equals(""))
				 {  
					 this.saveMessage(msg);
					 jjd.setState("已保存");
					 return "input";
				 }
                 // 判断提交的序列号在售后库存中存在
				 msg=jjdService.isSerialNumInKcExist(jjdProducts);
				 if(!msg.equals(""))
				 { 
					 this.saveMessage(msg);
					 jjd.setState("已保存");
					 return "input";
				 }			 
				 jjdService.updateJjd(jjd, jjdProducts);
			  }
			  if(jjd.getState().equals("已保存"))
			  {
				   
				  jjdService.updateJjd(jjd, jjdProducts);
			  }		  
			  return "success";
		  }
	   catch(Exception e)
	   {
		   log.error("保存修改接件单  失败原因："+e.getMessage());
		   return "error";
	   }
   }
   
   
   /**
    * 删除接件单
    * @return
    * @throws Exception
    */
   public String delete()throws Exception
   {
	   try 
	   {
		   String id = ParameterUtility.getStringParameter(getRequest(),"id", "");
		   jjdService.delJjd(id);
		   return "success";
	   }
	   catch (Exception e) 
	   {
		  log.error("删除接件单 失败原因:"+e.getMessage());
		  return "error";
	   }
   }
   /**
    * 选择库存商品
    * @return
    */
   public String selJjProduct()throws Exception
   {
	   try
	   {
		   int rowsPerPage = 15;
			
			String con = "";
			if(!product_name.equals("")){
				con += " and (a.product_name like '%" + product_name + "%' or a.product_xh like '%" + product_name + "%')";
			}
			if(!product_kind.equals("")){
				con += " and a.product_kind like '" + product_kind + "%'";
			}			 			
			productPage = productKcService.getProductKcList(con, curPage, rowsPerPage);		 
			kindList = productKindService.getAllProductKindList();
						 
	       return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("接件单选择库存商品 失败原因："+e.getMessage());
		   return "error";
	   }
   }
   
   /**
    * 导入序列号
    * @return
    */
   public String importSerial()throws Exception
   {
	   try 
	   {		
		   
		   
		  return "success";
	   }
	   catch (Exception e) 
	   {
		  log.error("导入序列号  失败原因:"+e.getMessage()); 
		  return "error";
	   }
   }
   
   /**
    * 单击接件单查看产品明细
    * @return
    * @throws Exception
    */
   public String descJjd()throws Exception
   {
	   try
	   {
		   jjdProducts=jjdService.getJjdProducts(id);
		   return "success";
	   }
	   catch(Exception e)
	   {
		   log.error("单击接件单查看产品明细  失败原因："+e.getMessage());
		   return "error";
	   }
   }

public int getCurPage() {
	return curPage;
}

public void setCurPage(int curPage) {
	this.curPage = curPage;
}

public String getJj_date1() {
	return jj_date1;
}

public void setJj_date1(String jj_date1) {
	this.jj_date1 = jj_date1;
}

public String getJj_date2() {
	return jj_date2;
}

public void setJj_date2(String jj_date2) {
	this.jj_date2 = jj_date2;
}

public Page getJjdPage() {
	return jjdPage;
}

public void setJjdPage(Page jjdPage) {
	this.jjdPage = jjdPage;
}

 

public JjdService getJjdService() {
	return jjdService;
}

public void setJjdService(JjdService jjdService) {
	this.jjdService = jjdService;
}

public String getJjr() {
	return jjr;
}

public void setJjr(String jjr) {
	this.jjr = jjr;
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
public Jjd getJjd() {
	return jjd;
}
public void setJjd(Jjd jjd) {
	this.jjd = jjd;
}
public List getJjdProducts() {
	return jjdProducts;
}
public void setJjdProducts(List jjdProducts) {
	this.jjdProducts = jjdProducts;
}
public Page getProductPage() {
	return productPage;
}
public void setProductPage(Page productPage) {
	this.productPage = productPage;
}
public List getKindList() {
	return kindList;
}
public void setKindList(List kindList) {
	this.kindList = kindList;
}
public String getProduct_kind() {
	return product_kind;
}
public void setProduct_kind(String product_kind) {
	this.product_kind = product_kind;
}
public String getProduct_name() {
	return product_name;
}
public void setProduct_name(String product_name) {
	this.product_name = product_name;
}
public ProductKcService getProductKcService() {
	return productKcService;
}
public void setProductKcService(ProductKcService productKcService) {
	this.productKcService = productKcService;
}
public ProductKindService getProductKindService() {
	return productKindService;
}
public void setProductKindService(ProductKindService productKindService) {
	this.productKindService = productKindService;
}
   
}
