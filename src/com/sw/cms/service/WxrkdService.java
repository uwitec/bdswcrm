package com.sw.cms.service;

import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.dao.WxrkdDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.model.Wxrkd;
import com.sw.cms.model.WxrkdProduct;
import com.sw.cms.util.DateComFunc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
  
  public List getWxrkdProducts(String wxrkd_id) {
	  return wxrkdDao.getWxrkdProducts(wxrkd_id);
  }
  
  public String isBadShkcExist(List wxrkdProducts)
  {
		String message="";
		if(wxrkdProducts != null && wxrkdProducts.size()>0)
		{
			for(int i=0;i<wxrkdProducts.size();i++)
			{
				WxrkdProduct wxrkddProduct = (WxrkdProduct)wxrkdProducts.get(i);
			    if(!wxrkddProduct.getQz_serial_num().equals(""))
			    {
				   int count=shkcDao.getBadShkcBySerialNum(wxrkddProduct.getQz_serial_num());
				   if(count==0)
				   {
				    	message="坏件库已无序列号:"+wxrkddProduct.getQz_serial_num()+" 的商品,请检查！";
				   }
			    }
			}
		}
		
		return message;
   }
  
  public void updateWxrkd(Wxrkd wxrkd, List wxrkdProducts)
  {
	  wxrkdDao.updateWxrkd(wxrkd,wxrkdProducts);
	  if(wxrkd.getState().equals("已提交"))
		{
<<<<<<< .mine
		  saveStoreFlow(wxrkd,wxrkdProducts);				 
=======
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
>>>>>>> .r243
		}
  }
    
  public void saveWxrkd(Wxrkd wxrkd,List wxrkdProducts)
  {
		wxrkdDao.saveWxrkd(wxrkd, wxrkdProducts);
		if(wxrkd.getState().equals("已提交"))
		{
			saveStoreFlow(wxrkd,wxrkdProducts);
		}
  }
  
  public void saveStoreFlow(Wxrkd wxrkd,List wxrkdProducts)
  {
	  if(wxrkdProducts != null && wxrkdProducts.size()>0)
		{
			for(int i=0;i<wxrkdProducts.size();i++)
			{
<<<<<<< .mine
				WxrkdProduct wxrkdProduct = (WxrkdProduct)wxrkdProducts.get(i);
				if(wxrkdProduct != null)
				{
					if(!wxrkdProduct.getProduct_id().equals("") && !wxrkdProduct.getProduct_name().equals(""))
					{
	                     //修改库存状态为1：好件库
		                  shkcDao.updateWxShkcState(wxrkdProduct.getQz_serial_num(), "3");
		             }
		             ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
		             shSerialNumFlow.setCj_date(DateComFunc.getToday());
		             shSerialNumFlow.setFs_date(wxrkd.getWxrk_date());
		             shSerialNumFlow.setJsr(wxrkd.getJsr());
		             shSerialNumFlow.setKf("好件库");
		             shSerialNumFlow.setQz_serial_num(wxrkdProduct.getQz_serial_num()); 
		             shSerialNumFlow.setRk_date(DateComFunc.getToday());
		             shSerialNumFlow.setYw_dj_id(wxrkd.getId());
		             shSerialNumFlow.setYw_url("viewWxrkd.html?id=");
		             shSerialNumFlow.setYwtype("维修");
		             //添加序列号流转记录
		             shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);	
				}
=======
		       //修改库存状态为1：好件库
			   shkcDao.updateWxShkcState(wxrkdProduct.getQz_serial_num(), "3");
>>>>>>> .r243
			}
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
