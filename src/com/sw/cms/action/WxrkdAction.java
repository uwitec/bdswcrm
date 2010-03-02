package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Wxrkd;
import com.sw.cms.model.WxrkdProduct;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.ShkcService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.WxrkdService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class WxrkdAction extends BaseAction 
{
  private WxrkdService wxrkdService;
  private ShkcService shkcService;
  private UserService userService;
  private ClientsService clientsService;
 
  private Page  wxrkdPage;
  private Page  shkcPage;
  private String orderName="";
  private String orderType="";
  private int curPage = 1;
  private String lxr="";
  private String  qz_serial_num="";
  private String state="";
  private String wxr="";
  private String wxrk_date1 = "";
  private String wxrk_date2 = "";
  private String product_name="";
  
  private Wxrkd wxrkd=new Wxrkd();
  private WxrkdProduct wxrkdProduct =new WxrkdProduct();
   
  private List wxrkdProducts = new ArrayList();
  private List userList = new ArrayList();
  private List clientsList = new ArrayList();
  /**
   * 返回维修入库单列表
   * @return
   * @throws Exception
   */
  public String list()throws Exception
  {
	  try
	  {
		int rowsPerPage = Constant.PAGE_SIZE2;
		String con = "";
		
		if (!wxrk_date1.trim().equals("")) {
			con += " and w.cj_date>='" + wxrk_date1 + "'";
		}
		if (!wxrk_date2.trim().equals("")) {
			con += " and w.cj_date<='" + (wxrk_date2 + " 23:59:59") + "'";
		}
		if (!state.equals("")) {
			con += " and w.state='" + state + "'";
		}
		
		if (orderName.equals("")) {
			orderName = "id";
		}
		if (orderType.equals("")) {
			orderType = "desc";
		}
		con += " order by " + orderName + " " + orderType + "";
		wxrkdPage = wxrkdService.getWxrkdList(con, curPage, rowsPerPage);
		return "success";
	  }
	  catch(Exception e)
	  {
		  log.error("获取维修入库单列表  错误原因"+e.getMessage());
		  return "error";
	  }
   }
  
  /**
   * 打开添加维修入库单添加页面
   * @return
   * @throws Exception
   */
  public String add()throws Exception
  {
	  try
	  {		
		    userList = userService.getAllEmployeeList();	 
			wxrkd.setId(wxrkdService.updateWxrkdId());		
			clientsList = clientsService.getClientList("");
		  return "success";
	  }
	  catch(Exception e)
	  {
		  log.error("打开添加维修入库单页面  错误原因"+e.getMessage());
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
			int rowsPerPage = 15;
				
			String con = "";
			if(!product_name.equals("")){
				con += " and (product_name like '%" + product_name + "%' or product_xh like '%" + product_name + "%')";
			}	
			
			shkcPage = shkcService.getShkuIsBadProduct(con, curPage, rowsPerPage);
			
			return "success";
		} catch (Exception e) {
			log.error("获取坏件库列表 错误原因:" + e.getMessage());
			return "error";
		}

	}
  
  /**
   * 保存维修入库单
   * @return
   * @throws Exception
   */
  public String save()throws Exception
  {
		try {
			LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
		    wxrkd.setCjr(user_id);			
            if(wxrkd.getState().equals("已提交"))
            {
            	//判断提交的报修产品是否在坏件库里
            	String msg=wxrkdService.isBadShkcExist(wxrkdProducts);
            	if(!msg.equals(""))
            	{
            		this.saveMessage(msg);
            		wxrkd.setState("已保存");           	 
            		return "input";
            	} 
            	//保存信息
            	wxrkdService.saveWxrkd(wxrkd, wxrkdProducts);
            }
            if(wxrkd.getState().equals("已保存"))
            {
            	wxrkdService.saveWxrkd(wxrkd, wxrkdProducts);
            }			
			return "success";
		} catch (Exception e) {
			log.error("保存维修入库单  失败原因" + e.getMessage());
			return "error";
		}
  }
  
  /**
   * 加载修改维修单
   * @return
   * @throws Exception
   */
	public String edit()throws Exception
	{
		try {
			String wxrkd_id = ParameterUtility.getStringParameter(getRequest(),
					"id", "");
			userList = userService.getAllEmployeeList();
			clientsList = clientsService.getClientList("");
			wxrkd = (Wxrkd) wxrkdService.getWxrkd(wxrkd_id);
			wxrkdProducts = wxrkdService.getWxrkdProducts(wxrkd_id);
			return "success";
		} catch (Exception e) {
			log.error("加载修改维修入库单 失败原因" + e.getMessage());
			return "error";
		}

	}
	/**
	 * 修改维修单
	 * 
	 * @return
	 */
	public String update()throws Exception
	{
		try 
		{	 LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
		     String user_id = info.getUser_id();
	         wxrkd.setCjr(user_id);			 
			 if(wxrkd.getState().equals("已提交"))
	            {
	            	//判断提交的报修产品是否在坏件库里
	            	String msg=wxrkdService.isBadShkcExist(wxrkdProducts);
	            	if(!msg.equals(""))
	            	{
	            		this.saveMessage(msg);
	            		wxrkd.setState("已保存");	            	 
	            		return "input";
	            	} 
	            	//保存信息
	            	wxrkdService.updateWxrkd(wxrkd, wxrkdProducts);
	            }
	            if(wxrkd.getState().equals("已保存"))
	            {
	            	wxrkdService.updateWxrkd(wxrkd, wxrkdProducts);
	            }		
			return "success";
		} catch (Exception e) {
			log.error("保存更改报修单  失败原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 删除维修单
	 * 
	 * @return
	 */
	public String del() {
		try {
			String wxrkd_id = ParameterUtility.getStringParameter(getRequest(),
					"id", "");
			wxrkdService.delWxrkd(wxrkd_id);
			return "success";
		} catch (Exception e) {
			log.error("删除维修单  失败原因" + e.getMessage());
			return "error";
		}
	}
	
	/**
	 * 单击维修单查看维修产品
	 * @return
	 * @throws Exception
	 */
	public String desc()throws Exception
	{
		try
		{
			String wxrkd_id = ParameterUtility.getStringParameter(getRequest(),
					"id", "");
			 wxrkdProducts=wxrkdService.getWxrkdProducts(wxrkd_id);
		    return "success";
		}
		catch(Exception e)
		{
			log.error("单击维修单查看产品  失败原因："+e.getMessage());
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

public WxrkdService getWxrkdService() {
	return wxrkdService;
}

public void setWxrkdService(WxrkdService wxrkdService) {
	this.wxrkdService = wxrkdService;
}

public String getWxrk_date1() {
	return wxrk_date1;
}

public void setWxrk_date1(String wxrk_date1) {
	this.wxrk_date1 = wxrk_date1;
}

public String getWxrk_date2() {
	return wxrk_date2;
}

public void setWxrk_date2(String wxrk_date2) {
	this.wxrk_date2 = wxrk_date2;
}

public int getCurPage() {
	return curPage;
}

public void setCurPage(int curPage) {
	this.curPage = curPage;
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

public Page getWxrkdPage() {
	return wxrkdPage;
}

public void setWxrkdPage(Page wxrkdPage) {
	this.wxrkdPage = wxrkdPage;
}

public String getLxr() {
	return lxr;
}

public void setLxr(String lxr) {
	this.lxr = lxr;
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

public String getWxr() {
	return wxr;
}

public void setWxr(String wxr) {
	this.wxr = wxr;
}

public Wxrkd getWxrkd() {
	return wxrkd;
}

public void setWxrkd(Wxrkd wxrkd) {
	this.wxrkd = wxrkd;
}

public WxrkdProduct getWxrkdProduct() {
	return wxrkdProduct;
}

public void setWxrkdProduct(WxrkdProduct wxrkdProduct) {
	this.wxrkdProduct = wxrkdProduct;
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

public List getWxrkdProducts() {
	return wxrkdProducts;
}
public void setWxrkdProducts(List wxrkdProducts) {
	this.wxrkdProducts = wxrkdProducts;
}
public String getProduct_name() {
	return product_name;
}


public void setProduct_name(String product_name) {
	this.product_name = product_name;
}

public List getClientsList() {
	return clientsList;
}

public void setClientsList(List clientsList) {
	this.clientsList = clientsList;
}

public ClientsService getClientsService() {
	return clientsService;
}

public void setClientsService(ClientsService clientsService) {
	this.clientsService = clientsService;
}

public UserService getUserService() {
	return userService;
}

public void setUserService(UserService userService) {
	this.userService = userService;
}

public List getUserList() {
	return userList;
}

public void setUserList(List userList) {
	this.userList = userList;
}
}
