package com.sw.cms.service;

import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.dao.WxrkdDAO;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Bxfhd;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.model.Wxrkd;
import com.sw.cms.model.WxrkdProduct;
import com.sw.cms.util.DateComFunc;

public class WxrkdService 
{
  private WxrkdDAO  wxrkdDao;
  private ShkcDAO  shkcDao;
  private ShSerialNumFlowDAO shSerialNumFlowDao;
  
  public Page getWxrkdList(String con, int curPage, int rowsPerPage)
  {
	  return wxrkdDao.getWxrkdList(con,curPage,rowsPerPage);
  }
  
  public String updateWxrkdId()
  {
	  return wxrkdDao.updateWxrkdId();
  }
  
  public Object getWxrkd(String wxrkd_id) {
	  return wxrkdDao.getWxrkd(wxrkd_id);
  }
  
  public Object getWxrkdProduct(String wxrkd_id) {
	  return wxrkdDao.getWxrkdProduct(wxrkd_id);
  }
  
  public String isBadShkcExist(WxrkdProduct wxrkddProduct)
  {
		String message="";
		if(wxrkddProduct!=null)
		{
			if(!wxrkddProduct.getQz_serial_num().equals(""))
			{
				int count=shkcDao.getBadShkcBySerialNum(wxrkddProduct.getQz_serial_num());
				if(count==0)
				{
					message="坏件库已无序列号:"+wxrkddProduct.getQz_serial_num()+" 的商品,请检查！";
				}
			}
		}
		
		return message;
   }
  
  public void updateWxrkd(Wxrkd wxrkd, WxrkdProduct wxrkdProduct)
  {
	  wxrkdDao.updateWxrkd(wxrkd,wxrkdProduct);
	  if(wxrkd.getState().equals("已提交"))
		{
			if(!wxrkdProduct.getQz_serial_num().equals(""))
			{
		       //修改库存状态为1：好件库
			   shkcDao.updateWxShkcState(wxrkdProduct.getQz_serial_num(), "3");
			}
			ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
			shSerialNumFlow.setCj_date(DateComFunc.getToday());
			shSerialNumFlow.setClient_name(wxrkd.getClient_name());
			shSerialNumFlow.setFs_date(wxrkd.getWx_date());
			shSerialNumFlow.setJsr(wxrkd.getWxr());
			shSerialNumFlow.setKf("好件库");
			shSerialNumFlow.setLinkman(wxrkd.getLxr());
			shSerialNumFlow.setQz_serial_num(wxrkdProduct.getQz_serial_num()); 
			shSerialNumFlow.setRk_date(DateComFunc.getToday());
			shSerialNumFlow.setYw_dj_id(wxrkd.getId());
			shSerialNumFlow.setYw_url("viewWxrkd.html?id=");
			shSerialNumFlow.setYwtype("维修");
			//添加序列号流转记录
			shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);				 
		}
  }
    
  public void saveWxrkd(Wxrkd wxrkd,WxrkdProduct wxrkdProduct)
  {
		wxrkdDao.saveWxrkd(wxrkd, wxrkdProduct);
		if(wxrkd.getState().equals("已提交"))
		{
			if(!wxrkdProduct.getQz_serial_num().equals(""))
			{
		       //修改库存状态为1：好件库
			   shkcDao.updateWxShkcState(wxrkdProduct.getQz_serial_num(), "3");
			}
			ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
			shSerialNumFlow.setCj_date(DateComFunc.getToday());
			shSerialNumFlow.setClient_name(wxrkd.getClient_name());
			shSerialNumFlow.setFs_date(wxrkd.getWx_date());
			shSerialNumFlow.setJsr(wxrkd.getWxr());
			shSerialNumFlow.setKf("好件库");
			shSerialNumFlow.setLinkman(wxrkd.getLxr());
			shSerialNumFlow.setQz_serial_num(wxrkdProduct.getQz_serial_num()); 
			shSerialNumFlow.setRk_date(DateComFunc.getToday());
			shSerialNumFlow.setYw_dj_id(wxrkd.getId());
			shSerialNumFlow.setYw_url("viewWxrkd.html?id=");
			shSerialNumFlow.setYwtype("维修");
			//添加序列号流转记录
			shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);				 
		}
  }
  
  public void delWxrkd(String id)
  {
	  wxrkdDao.delWxrkd(id);
  }
public WxrkdDAO getWxrkdDao() {
	return wxrkdDao;
}

public void setWxrkdDao(WxrkdDAO wxrkdDao) {
	this.wxrkdDao = wxrkdDao;
}

public ShkcDAO getShkcDao() {
	return shkcDao;
}

public void setShkcDao(ShkcDAO shkcDao) {
	this.shkcDao = shkcDao;
}

public ShSerialNumFlowDAO getShSerialNumFlowDao() {
	return shSerialNumFlowDao;
}

public void setShSerialNumFlowDao(ShSerialNumFlowDAO shSerialNumFlowDao) {
	this.shSerialNumFlowDao = shSerialNumFlowDao;
}
}
