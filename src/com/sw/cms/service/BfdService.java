package com.sw.cms.service;

import com.sw.cms.dao.BfdDAO;

import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Bfd;
import com.sw.cms.model.BfdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BfdService {
	private BfdDAO bfdDao;
	private ShkcDAO shkcDao;
	private ShSerialNumFlowDAO shSerialNumFlowDao;
	
	
	public Page getBfdList(String con, int curPage, int rowsPerPage) {
		return bfdDao.getBfdList(con, curPage, rowsPerPage);
	}

	public String updateBfdId() {
		return bfdDao.getBfdId();
	}
	
	
	public void saveBfd(Bfd bfd,List bfdProducts)
	{
//		 如果报废单已提交，不做处理
		if(bfdDao.isBfdSubmit(bfd.getId())){
			return;
		}
		bfdDao.saveBfd(bfd, bfdProducts);
		if(bfd.getState().equals("已提交"))
		{
			   //修改库存状态为4：报废		
			this.addBfd(bfd,bfdProducts);
			if(bfdProducts != null && bfdProducts.size()>0)
			{
				for(int i=0;i<bfdProducts.size();i++)
				{
					BfdProduct bfdProduct = (BfdProduct)bfdProducts.get(i);
					if(bfdProduct != null)
					{
						if(!bfdProduct.getProduct_id().equals("") && !bfdProduct.getProduct_name().equals(""))
						{
			              shkcDao.updateBfShkcState(bfdProduct, "4");	
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * <p>如果是强制序列号添加相应序列号信息</p?
	 * @param bfd
	 * @param bfdProducts
	 */
	private void addBfd(Bfd bfd,List bfdProducts)
	{
		if(bfdProducts != null && bfdProducts.size()>0)
		{
			for(int i=0;i<bfdProducts.size();i++)
			{
				BfdProduct bfdProduct = (BfdProduct)bfdProducts.get(i);
				if(bfdProduct != null)
				{
					if(!bfdProduct.getProduct_id().equals("") && !bfdProduct.getProduct_name().equals(""))
					{				
						//只有在系统正式使用后才去修改商品的库存和处理序列号
						//系统启用前也可输入商品序列号，但不硬性强制，对于输入的序列号系统做处理
						
						//if(sysInitSetDao.getQyFlag().equals("1")){
						//强制序列号处理
						
						if((bfdProduct.getQz_serial_num() != null) && (!bfdProduct.getQz_serial_num().equals("")))
						{
							String[] arryNums = (bfdProduct.getQz_serial_num()).split(",");
							
							ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
							
							for(int k=0;k<arryNums.length;k++)
							{
								shSerialNumFlow.setCj_date(DateComFunc.getToday());			
								shSerialNumFlow.setFs_date(bfd.getBf_date());
								shSerialNumFlow.setJsr(bfd.getJsr());
								shSerialNumFlow.setKf("坏件库");
								shSerialNumFlow.setQz_serial_num(bfdProduct.getQz_serial_num());
								shSerialNumFlow.setRk_date(DateComFunc.getToday());
								shSerialNumFlow.setYw_dj_id(bfd.getId());
								shSerialNumFlow.setYw_url("viewBfd.html?id=");
								shSerialNumFlow.setYwtype("报废");
								
//								添加序列号流转记录
								shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
							}
						}
					}
				}
			}
		}
	}
	
	
	public Object getBfd(String bfd_id)
	{
		return bfdDao.getBfd(bfd_id);
	}
	
	public List getBfdProducts(String bfd_id)
	{
		return bfdDao.getBfdProducts(bfd_id);
	}
	
	public void updateBfd(Bfd bfd,List bfdProducts)
	{
		bfdDao.updateBfd(bfd, bfdProducts);
		if(bfd.getState().equals("已提交"))
		{
		       //修改库存状态为4：报废
			this.addBfd(bfd,bfdProducts);
			if(bfdProducts != null && bfdProducts.size()>0)
			{
				for(int i=0;i<bfdProducts.size();i++)
				{
					BfdProduct bfdProduct = (BfdProduct)bfdProducts.get(i);
					if(bfdProduct != null)
					{
						if(!bfdProduct.getProduct_id().equals("") && !bfdProduct.getProduct_name().equals(""))
						{
			               shkcDao.updateBfShkcState(bfdProduct, "4");
						}
					}
				}
			}
		}
	}
	
	public void delBfd(String bfd_id)
	{
		bfdDao.delBfd(bfd_id);
	}
	
	/**
	 * 检查报废中是否有该序列号
	 * @param bfdProducts
	 * @return
	 */
	public boolean isBfShkcExist(Bfd bfd,List bfdProducts)
	{
		boolean is = false;
		String message="";
		if(bfdProducts != null && bfdProducts.size()>0)
		{
			for(int i=0;i<bfdProducts.size();i++)
			{
				BfdProduct bfdProduct = (BfdProduct)bfdProducts.get(i);
				if(!bfdProduct.getQz_serial_num().equals(""))
			    {
				  int count=shkcDao.getBfShkcBySerialNum(bfdProduct.getQz_serial_num());
				  if(count==0)
			     {
					//message="坏件库已无序列号:"+bfdProduct.getQz_serial_num()+" 的商品,请检查！";
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

	public BfdDAO getBfdDao() {
		return bfdDao;
	}

	public void setBfdDao(BfdDAO bfdDao) {
		this.bfdDao = bfdDao;
	}

	public ShkcDAO getShkcDao() {
		return shkcDao;
	}

	public void setShkcDao(ShkcDAO shkcDao) {
		this.shkcDao = shkcDao;
	}

	
}
