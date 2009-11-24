package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Bxfhd;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.BxfhdService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class BxfhdAction extends BaseAction
{
  private BxfhdService bxfhdService;
  private SjzdService  sjzdService;
  
  private Page   bxfhdPage;
  private String orderName="";
  private String orderType="";
  private int    curPage=1;
  private String lxr="";
  private String qz_serial_num="";
  private String cj_date1=DateComFunc.getToday();
  private String cj_date2=DateComFunc.getToday();
  private String state="";
  private String fhr="";
  
  private Bxfhd bxfhd=new Bxfhd();
  private BxfhdProduct bxfhdProduct=new BxfhdProduct();
  
  private String[] wxszd;
  private String[] fj = new String[7];



/**
   * 报修返还单列表（带分页）
   * @return
   * @throws Exception
   */
  public String list()throws Exception
  {
	  try
	  {
		  int rowsPerPage=Constant.PAGE_SIZE2;
		  String con="";
		  if(!lxr.equals(""))
		  {
			  con+=" and b.lxr like '%"+lxr+"%'";
		  }
		  if(!qz_serial_num.equals(""))
		  {
			  con+=" and p.qz_serial_num='"+qz_serial_num+"'";
		  }
		  if(!cj_date1.equals(""))
		  {
			  con+=" and b.cj_date>='"+cj_date1+"'";
		  }
		  if(!cj_date2.equals(""))
		  {
			  con+=" and b.cj_date<='"+cj_date2+"'";
		  }
		  if(!state.equals(""))
		  {
			  con+=" and b.state='"+state+"'";
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
		  con+=" order by "+orderName+" "+orderType+"";
		  bxfhdPage=bxfhdService.getBxfhdList(con, curPage, rowsPerPage);	  
		  return "success";
	  }
	  catch(Exception e)
	  {
		  log.error("报修返还单列表  错误原因："+e.getMessage());
		  return "error";
	  }
  }
  
  /**
   * 打开修改报修返还单
   * @return
   * @throws Exception
   */
  public String edit()throws Exception
  {
	  try
	  {
		  //报修返还单ID
		  String id = ParameterUtility.getStringParameter(getRequest(), "id", ""); 
		  
		  wxszd=sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
		  bxfhd=(Bxfhd)bxfhdService.getBxfhdById(id);
		  
		  bxfhdProduct=(BxfhdProduct)bxfhdService.getBxfhdProductById(id);
		 
		  return "success";
	  }
	  catch(Exception e)
	  {
		  log.error("打开修改报修返还单  错误原因："+e.getMessage());
		  return "error";
	  }
  }
  
  /**
   * 修改报修返还单
   * @return
   * @throws Exception
   */
  public String update()throws Exception
  {
	  try
	  {
			String str="";
			for (int i = 0; i < fj.length; i++) {
				str += fj[i] + ",";
			}
			LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			bxfhd.setCjr(user_id);
			bxfhdProduct.setFj(str);
			if(bxfhd.getState().equals("待返还"))
			{
				bxfhdService.updateBxfhd(bxfhd, bxfhdProduct);
			}
			if(bxfhd.getState().equals("已返还"))
			{
				bxfhdService.updateBxfhd(bxfhd, bxfhdProduct);
			}
		  
		  return "success";
	  }
	  catch(Exception e)
	  {
		  log.error("修改报修返还单 错误原因:"+e.getMessage());
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
		  bxfhdProduct=(BxfhdProduct)bxfhdService.getBxfhdProductById(id);
		  return "success";
	  }
	  catch(Exception e)
	  {
		  log.error("单击报修返还单查看报修返还信息 错误原因:"+e.getMessage());
		  return "error";
	  }
  }
  
  public Page getBxfhdPage() {
	return bxfhdPage;
}

public void setBxfhdPage(Page bxfhdPage) {
	this.bxfhdPage = bxfhdPage;
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
 
public BxfhdService getBxfhdService() {
	return bxfhdService;
}

public void setBxfhdService(BxfhdService bxfhdService) {
	this.bxfhdService = bxfhdService;
}

public String[] getWxszd() {
	return wxszd;
}

public void setWxszd(String[] wxszd) {
	this.wxszd = wxszd;
}

public SjzdService getSjzdService() {
	return sjzdService;
}

public void setSjzdService(SjzdService sjzdService) {
	this.sjzdService = sjzdService;
}

public Bxfhd getBxfhd() {
	return bxfhd;
}

public void setBxfhd(Bxfhd bxfhd) {
	this.bxfhd = bxfhd;
}

public BxfhdProduct getBxfhdProduct() {
	return bxfhdProduct;
}

public void setBxfhdProduct(BxfhdProduct bxfhdProduct) {
	this.bxfhdProduct = bxfhdProduct;
}

public String[] getFj() {
	return fj;
}

public void setFj(String[] fj) {
	this.fj = fj;
}
}
