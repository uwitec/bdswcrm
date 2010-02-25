package com.sw.cms.service;

import com.sw.cms.dao.HjdDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Hjd;
import com.sw.cms.model.HjdProduct;

import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HjdService {
	private HjdDAO hjdDao;
	private ShkcDAO shkcDao;
	private ShSerialNumFlowDAO shSerialNumFlowDao;
		
	public Page getHjdList(String con, int curPage, int rowsPerPage) {
		return hjdDao.getHjdList(con, curPage, rowsPerPage);
	}

	public String updateHjdId() {
		return hjdDao.getHjdId();
	}
	
	
	public void saveHjd(Hjd hjd,List hjdProducts)
	{
		hjdDao.saveHjd(hjd, hjdProducts);
		if(hjd.getState().equals("已提交"))
		{  	
			this.addHjd(hjd,hjdProducts);
            //修改售后库存中的序列号为新序列号	
			if(hjdProducts != null && hjdProducts.size()>0)
			{
				for(int i=0;i<hjdProducts.size();i++)
				{
					HjdProduct hjdProduct = (HjdProduct)hjdProducts.get(i);
					if(hjdProduct != null)
					{
						if(!hjdProduct.getProduct_id().equals("") && !hjdProduct.getProduct_name().equals(""))
						{
			                shkcDao.updateHjShkcState(hjdProduct);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * <p>如果是强制序列号添加相应序列号信息</p?
	 * @param hjd
	 * @param hjdProducts
	 */
	private void addHjd(Hjd hjd,List hjdProducts)
	{
		if(hjdProducts != null && hjdProducts.size()>0)
		{
			for(int i=0;i<hjdProducts.size();i++)
			{
				HjdProduct hjdProduct = (HjdProduct)hjdProducts.get(i);
				if(hjdProduct != null)
				{
					if(!hjdProduct.getProduct_id().equals("") && !hjdProduct.getProduct_name().equals(""))
					{				
						//只有在系统正式使用后才去修改产品的库存和处理序列号
						//系统启用前也可输入产品序列号，但不硬性强制，对于输入的序列号系统做处理
						
						//if(sysInitSetDao.getQyFlag().equals("1")){
						//强制序列号处理
						
						if((hjdProduct.getNqz_serial_num() != null) && (!hjdProduct.getNqz_serial_num().equals("")))
						{
							String[] arryNums = (hjdProduct.getNqz_serial_num()).split(",");
							
							ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
							
							for(int k=0;k<arryNums.length;k++)
							{
								shSerialNumFlow.setCj_date(DateComFunc.getToday());			
								shSerialNumFlow.setFs_date(hjd.getHj_date());
								shSerialNumFlow.setJsr(hjd.getJsr());
								shSerialNumFlow.setKf("好件库");
								shSerialNumFlow.setQz_serial_num(arryNums[k]);
								shSerialNumFlow.setRk_date(DateComFunc.getToday());
								shSerialNumFlow.setYw_dj_id(hjd.getId());
								shSerialNumFlow.setYw_url("viewHjd.html?id=");
								shSerialNumFlow.setYwtype("换件");
								
//								添加序列号流转记录
								shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
							}
						}
					}
				}
			}
		}
	}
	
	
	public Object getHjd(String hjd_id)
	{
		return hjdDao.getHjd(hjd_id);
	}
	
	public List getHjdProducts(String hjd_id)
	{
		return hjdDao.getHjdProducts(hjd_id);
	}
	
	public void updateHjd(Hjd hjd,List hjdProducts)
	{
		hjdDao.updateHjd(hjd, hjdProducts);
		if(hjd.getState().equals("已提交"))
		{
		       
			this.addHjd(hjd,hjdProducts);
            //修改售后库存中的序列号为新序列号
			if(hjdProducts != null && hjdProducts.size()>0)
			{
				for(int i=0;i<hjdProducts.size();i++)
				{
					HjdProduct hjdProduct = (HjdProduct)hjdProducts.get(i);
					if(hjdProduct != null)
					{
						if(!hjdProduct.getProduct_id().equals("") && !hjdProduct.getProduct_name().equals(""))
						{
			              shkcDao.updateHjShkcState(hjdProduct);
						}
					}
				}
			}
		}
	}
	
	public void delHjd(String hjd_id)
	{
		hjdDao.delHjd(hjd_id);
	}
	
	/**
	 * 检查好件库是否有该序列号
	 * @param bxdProducts
	 * @return
	 */
	public boolean isHaoShkcExist(Hjd hjd,List hjdProducts)
	{
		boolean is = false;
		String message="";
		if(hjdProducts != null && hjdProducts.size()>0)
		{
			for(int i=0;i<hjdProducts.size();i++)
			{
				HjdProduct hjdProduct = (HjdProduct)hjdProducts.get(i);
				if(!hjdProduct.getNqz_serial_num().equals(""))
			    {
				  int count=shkcDao.getHaoShkcBySerialNum(hjdProduct.getNqz_serial_num());
				  if(count==0)
			     {
					message="好件库已无序列号:"+hjdProduct.getNqz_serial_num()+" 的商品,请检查！";
				 }
				  else
				  {
					  is = true;
						break;
				  }
			    }
		    }
		}
		
		return is;
	}

	/**
	 * 坏件库列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
    public Page getShkuIsBadProduct(String con,int curPage,int rowsPerPage)
    {
    	return shkcDao.getShkuIsBadProduct(con,curPage,rowsPerPage);
    }
	
	public ShSerialNumFlowDAO getShSerialNumFlowDao() {
		return shSerialNumFlowDao;
	}

	public void setShSerialNumFlowDao(ShSerialNumFlowDAO shSerialNumFlowDao) {
		this.shSerialNumFlowDao = shSerialNumFlowDao;
	}

	public HjdDAO getHjdDao() {
		return hjdDao;
	}

	public void setHjdDao(HjdDAO hjdDao) {
		this.hjdDao = hjdDao;
	}

	public ShkcDAO getShkcDao() {
		return shkcDao;
	}

	public void setShkcDao(ShkcDAO shkcDao) {
		this.shkcDao = shkcDao;
	}
}
