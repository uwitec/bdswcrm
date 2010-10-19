package com.sw.cms.service;

import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.dao.WxrkdDAO;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.model.Shkc;
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
			    	String[] arryNums = (wxrkddProduct.getQz_serial_num()).split(",");
					
					for(int k=0;k<arryNums.length;k++)
				   {
				     int count=shkcDao.getBadShkcBySerialNum(arryNums[k]);
				     if(count==0)
				     {
				    	message+="坏件库已无序列号:"+wxrkddProduct.getQz_serial_num()+" 的商品,请检查！";
				     }
				   }
			    }
			}
		}
		
		return message;
   }
  
  /**
	 * 判断库存量是否满足维修入库处理
	 * @param wxrkd
	 * @param wxrkdProducts
	 */
	public String checkKc(Wxrkd wxrkd,List wxrkdProducts){
		String msg = "";
		
		if(wxrkdProducts != null && wxrkdProducts.size()>0){
			for(int i=0;i<wxrkdProducts.size();i++){
				WxrkdProduct wxrkdProduct = (WxrkdProduct)wxrkdProducts.get(i);
				if(wxrkdProduct != null){
					if(!wxrkdProduct.getProduct_id().equals("") && !wxrkdProduct.getProduct_name().equals("")){
						String product_id = wxrkdProduct.getProduct_id();
						
						//进行库存数量判断
						Shkc shkc = (Shkc)shkcDao.getShkc(product_id,"1");
						
							int cknums = wxrkdProduct.getNums();  //要维修入库数量
							int kcnums;
							if(shkc != null){
							   kcnums = shkc.getNums();//库存数量
							}
							else
							{
							   kcnums =0;
							}
							
							if(cknums>kcnums){
								msg += wxrkdProduct.getProduct_name() + " 当前库存为：" + kcnums + "  无法进行维修入库\n";
							}						
					}
				}
			}
		}
		
		return msg;
	}
  
  
  public void updateWxrkd(Wxrkd wxrkd, List wxrkdProducts)
  {
	  wxrkdDao.updateWxrkd(wxrkd,wxrkdProducts);
	  if(wxrkd.getState().equals("已提交"))
		{
		  saveStoreFlow(wxrkd,wxrkdProducts);				 
		}
  }
    
  public void saveWxrkd(Wxrkd wxrkd,List wxrkdProducts)
  {     //		 如果维修入库单已提交，不做处理
		if(wxrkdDao.isWxrkdSubmit(wxrkd.getId())){
			return;
		}
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
				WxrkdProduct wxrkdProduct = (WxrkdProduct)wxrkdProducts.get(i);
				if(wxrkdProduct != null)
				{
					if(!wxrkdProduct.getProduct_id().equals("") && !wxrkdProduct.getProduct_name().equals(""))
					{
						if(!wxrkdProduct.getQz_serial_num().equals(""))
						{						
					    	String[] arryNums = (wxrkdProduct.getQz_serial_num()).split(",");
						
						    for(int k=0;k<arryNums.length;k++)
					       {
	                           //修改库存状态为1：好件库
		                       shkcDao.updateWxShkcState(arryNums[k], "3");
					   
		                       ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
		                       shSerialNumFlow.setCj_date(DateComFunc.getToday());
		                       shSerialNumFlow.setFs_date(wxrkd.getWxrk_date());
		                       shSerialNumFlow.setJsr(wxrkd.getJsr());
		                       shSerialNumFlow.setKf("好件库");
		                       shSerialNumFlow.setQz_serial_num(arryNums[k]); 
		                       shSerialNumFlow.setRk_date(DateComFunc.getToday());
		                       shSerialNumFlow.setYw_dj_id(wxrkd.getId());
		                       shSerialNumFlow.setYw_url("viewWxrkd.html?id=");
		                       shSerialNumFlow.setYwtype("维修入库");
		                       //添加序列号流转记录
		                       shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);	
					        }
						}
						else
						{
							Shkc shkc = (Shkc)shkcDao.getShkc(wxrkdProduct.getProduct_id(),"1");
							if((shkc.getNums()-wxrkdProduct.getNums())==0)
							{
								shkcDao.updateShkcStateAll(wxrkdProduct.getProduct_id(),wxrkdProduct.getNums(),"3","1");
							}
							else
							{
								shkcDao.updateShkcNums(wxrkdProduct.getProduct_id(),wxrkdProduct.getNums(),"3","1");
								shkcDao.updateShkcStateNums(wxrkdProduct.getProduct_id(),wxrkdProduct.getNums(),"3","1");
							}
						}
		             }
				}
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
