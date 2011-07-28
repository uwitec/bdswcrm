package com.sw.cms.service;

import com.sw.cms.dao.BfdDAO;

import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Bfd;
import com.sw.cms.model.BfdProduct;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.model.Shkc;
import com.sw.cms.model.YkckProduct;
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
							if(!bfdProduct.getQz_serial_num().equals(""))
							{
						    	String[] arryNums = (bfdProduct.getQz_serial_num()).split(",");
							
							    for(int k=0;k<arryNums.length;k++)
						        {	
                                  shkcDao.updateBfShkcState(arryNums[k], "4");
						        }
							}
							else
							{
								Shkc shkc = (Shkc)shkcDao.getShkc(bfdProduct.getProduct_id(),"1");
								if((shkc.getNums()-bfdProduct.getNums())==0)
								{
								    shkcDao.updateShkcStateAll(bfdProduct.getProduct_id(),bfdProduct.getNums(),"4","1");
								}
								else
								{
									shkcDao.updateShkcNums(bfdProduct.getProduct_id(),bfdProduct.getNums(),"4","1");
									shkcDao.updateShkcStateNums(bfdProduct.getProduct_id(),bfdProduct.getNums(),"4","1");
								}
								
							}	
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
							if(!bfdProduct.getQz_serial_num().equals(""))
							{
						    	String[] arryNums = (bfdProduct.getQz_serial_num()).split(",");
							
							    for(int k=0;k<arryNums.length;k++)
						        {	
                                  shkcDao.updateBfShkcState(arryNums[k], "4");
						        }
							}
							else
							{
								Shkc shkc = (Shkc)shkcDao.getShkc(bfdProduct.getProduct_id(),"1");
								if((shkc.getNums()-bfdProduct.getNums())==0)
								{
								    shkcDao.updateShkcStateAll(bfdProduct.getProduct_id(),bfdProduct.getNums(),"4","1");
								}
								else
								{
									shkcDao.updateShkcNums(bfdProduct.getProduct_id(),bfdProduct.getNums(),"4","1");
									shkcDao.updateShkcStateNums(bfdProduct.getProduct_id(),bfdProduct.getNums(),"4","1");
								}
								
							}	
						}
					}
				}
			}
		}
	}
	
	/**
	 * 判断库存量是否满足报废处理
	 * @param bfd
	 * @param bfdProducts
	 */
	public String checkKc(Bfd bfd,List bfdProducts){
		String msg = "";
		
		if(bfdProducts != null && bfdProducts.size()>0){
			for(int i=0;i<bfdProducts.size();i++){
				BfdProduct bfdProduct = (BfdProduct)bfdProducts.get(i);
				if(bfdProduct != null){
					if(!bfdProduct.getProduct_id().equals("") && !bfdProduct.getProduct_name().equals("")){
						String product_id = bfdProduct.getProduct_id();
						
						//进行库存数量判断
						int kcnums;
						if(bfdProduct.getQz_serial_num().equals("")){
						  Shkc shkc = (Shkc)shkcDao.getShkc(product_id,"1");
						  if(shkc != null){
						     kcnums = shkc.getNums();//库存数量
						  }
						  else
						  {
						     kcnums =0;
						  }
						}
						else
						{
							kcnums = shkcDao.getShkcQz(product_id,"1");						    
						}
							int cknums = bfdProduct.getNums();  //要报废数量		
							
							if(cknums>kcnums){
								msg += bfdProduct.getProduct_name() + " 当前库存为：" + kcnums + "  无法进行报废处理\n";
							}						
					}
				}
			}
		}
		
		return msg;
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
	public String isBfShkcExist(Bfd bfd,List bfdProducts)
	{
		
		String message="";
		String serials="";
		String serialNumStr[]=getSerialNum(bfdProducts).toString().split(",");	
		if(!serialNumStr.equals(""))
		{
			
			for(int i=0;i<serialNumStr.length;i++)
			{
			  if(!serialNumStr[i].equals(""))
			  {
				  int count=shkcDao.getBfShkcBySerialNum(serialNumStr[i]);
				  if(count!=0)
			      {
					serials+=serialNumStr[i]+" ";	
				  }				  
			    }
		    }
		}
		if(!serials.equals(""))
		{
			message="该序列号:"+serials+" 的商品已经报废,请检查！";
		}
		return message;
	}

	/**
	 * 检查坏件库是否有该序列号
	 * @param bfdProducts
	 * @return
	 */
	public String isBadShkcExist(Bfd bfd,List bfdProducts)
	{		
		String message="";
		String serials="";
		String serialNumStr[]=getSerialNum(bfdProducts).toString().split(",");	
		if(!serialNumStr.equals(""))
		{
			
			for(int i=0;i<serialNumStr.length;i++)
			{
			  if(!serialNumStr[i].equals(""))
			  {
				  int count=shkcDao.getBadShkcBySerialNum(serialNumStr[i]);
				  if(count==0)
			      {
					  serials+=serialNumStr[i]+" ";					 
				  }
			    }
		    }
		}	
		if(!serials.equals(""))
		{
			 message+="该序列号:"+serials+" 的商品未在坏件库中,请检查！";
		}
		
		return message;
	}
	
	public String getSerialNum(List bfdProducts)
	{
		 
		String serialNumList="";
		if(bfdProducts!=null&&bfdProducts.size()>0)
		{
		   for(int i=0;i<bfdProducts.size();i++)
		  {
			  BfdProduct bfdProduct = (BfdProduct)bfdProducts.get(i);
			 if(bfdProduct!=null)
			 {
				if(!bfdProduct.getProduct_name().equals("")&&!bfdProduct.getProduct_id().equals(""))
				{	
					if(!bfdProduct.getQz_serial_num().equals(""))
					{
			          String serialNum[]=bfdProduct.getQz_serial_num().toString().split(",");
				      for(int j=0;j<serialNum.length;j++)
				      {
				     	serialNumList+=serialNum[j]+",";
				      }
					}
		 	     }		    
			 }
		  }
		}
		return serialNumList;
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
