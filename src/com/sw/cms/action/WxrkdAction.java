package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
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

 
  private Page  wxrkdPage;
  private Page  shkcPage;
  private String orderName="";
  private String orderType="";
  private int curPage = 1;
  private String lxr="";
  private String  qz_serial_num="";
  private String state="";
  private String wxr="";
  private String cj_date1 = DateComFunc.getToday();
  private String cj_date2 = DateComFunc.getToday();
  
  private Wxrkd wxrkd=new Wxrkd();
  private WxrkdProduct wxrkdProduct =new WxrkdProduct();
   
 

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
		if (!lxr.trim().equals("")) {
			con = " and w.lxr like '%" + lxr + "%'";
		}
		if (!qz_serial_num.trim().equals("")) {
			con += " and p.qz_serial_num='" + qz_serial_num + "'";
		}
		if (!cj_date1.trim().equals("")) {
			con += " and w.cj_date>='" + cj_date1 + "'";
		}
		if (!cj_date2.trim().equals("")) {
			con += " and w.cj_date<='" + (cj_date2 + " 23:59:59") + "'";
		}
		if (!state.equals("")) {
			con += " and w.state='" + state + "'";
		}
		if (!wxr.trim().equals("")) {
			con += " and s.real_name like '%" + wxr + "%'";
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
		  log.error("维修入库单列表  错误原因"+e.getMessage());
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
			wxrkd.setId(wxrkdService.updateWxrkdId());			 
		  return "success";
	  }
	  catch(Exception e)
	  {
		  log.error("打开添加维修入库单页面  错误原因"+e.getMessage());
		  return "error";
	  }
  }
  
  /**
   * 选择库存列表
   * @return
   * @throws Exception
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
            	String msg=wxrkdService.isBadShkcExist(wxrkdProduct);
            	if(!msg.equals(""))
            	{
            		this.saveMessage(msg);
            		wxrkd.setState("已保存");           	 
            		return "input";
            	} 
            	//保存信息
            	wxrkdService.saveWxrkd(wxrkd, wxrkdProduct);
            }
            if(wxrkd.getState().equals("已保存"))
            {
            	wxrkdService.saveWxrkd(wxrkd, wxrkdProduct);
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
			wxrkd = (Wxrkd) wxrkdService.getWxrkd(wxrkd_id);
			wxrkdProduct = (WxrkdProduct) wxrkdService.getWxrkdProduct(wxrkd_id);
			return "success";
		} catch (Exception e) {
			log.error("加载修改维修单 失败原因" + e.getMessage());
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
		{			 
			 if(wxrkd.getState().equals("已提交"))
	            {
	            	//判断提交的报修产品是否在坏件库里
	            	String msg=wxrkdService.isBadShkcExist(wxrkdProduct);
	            	if(!msg.equals(""))
	            	{
	            		this.saveMessage(msg);
	            		wxrkd.setState("已保存");	            	 
	            		return "input";
	            	} 
	            	//保存信息
	            	wxrkdService.updateWxrkd(wxrkd, wxrkdProduct);
	            }
	            if(wxrkd.getState().equals("已保存"))
	            {
	            	wxrkdService.updateWxrkd(wxrkd, wxrkdProduct);
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
			 wxrkdProduct=(WxrkdProduct)wxrkdService.getWxrkdProduct(wxrkd_id);
		    return "success";
		}
		catch(Exception e)
		{
			log.error("单击维修单查看产品  失败原因："+e.getMessage());
			return "error";
		}
	}
  
  

public WxrkdService getWxrkdService() {
	return wxrkdService;
}

public void setWxrkdService(WxrkdService wxrkdService) {
	this.wxrkdService = wxrkdService;
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
}
