package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.BxfhdDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Bxfhd;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.model.Shkc;
import com.sw.cms.util.DateComFunc;

public class BxfhdService 
{
  private BxfhdDAO bxfhdDao;
  private ShkcDAO  shkcDao;
  private ShSerialNumFlowDAO shSerialNumFlowDao;
  
  
  public Page getBxfhdList(String con,int curPage,int rowsPerPage)
  {
	 return bxfhdDao.getBxfhdList(con, curPage, rowsPerPage);
  }
  
  public void delBxfhd(String bxfhd_id)
  {
	 bxfhdDao.delBxfhd(bxfhd_id);
  }
  
  public Object getBxfhd(String bxfhd_id)
  {
		return bxfhdDao.getBxfhd(bxfhd_id);
  }  
  
  public String updateBxfhdId()
  {
	  return bxfhdDao.getBxfhdId();
  }
  
  public Object getBxfhdProductById(String bxfhd_id)
  {
	  return bxfhdDao.getBxfhdProductById(bxfhd_id);
  }
  
  public void updateBxfhd(Bxfhd bxfhd,List bxfhdProducts)
  {
	  bxfhdDao.updateBxfhd(bxfhd, bxfhdProducts);
	  
	  if(bxfhd.getState().equals("已提交"))
	  {
		       //修改库存为好件库
			this.addBxfhd(bxfhd,bxfhdProducts);
			shkcDao.updateBxfhShkcState(bxfhdProducts, "3");			
	  }
  }
  
  public void saveBxfhd(Bxfhd bxfhd,List bxfhdProducts)
  {
//		 如果报修单已提交，不做处理
		if(bxfhdDao.isBxfhdSubmit(bxfhd.getId())){
			return;
		}
	  bxfhdDao.saveBxfhd(bxfhd, bxfhdProducts);
	  if(bxfhd.getState().equals("已提交"))
	  {
			   //修改库存为好件库		
			this.addBxfhd(bxfhd,bxfhdProducts);
			shkcDao.updateBxfhShkcState(bxfhdProducts, "3");								
	  }
 }
  
  /**
   * 返还对应的报修返还单的商品信息
   * @param bxfhd_id
   * @return
   */
   public List getBxfhdProducts(String bxfhd_id)
	{
		return bxfhdDao.getBxfhdProducts(bxfhd_id);
	}
  
  /**
	 * 
	 * <p>如果是强制序列号添加相应序列号信息</p?
	 * @param bxd
	 * @param bxdProducts
	 */
	private void addBxfhd(Bxfhd bxfhd,List bxfhdProducts)
	{
		if(bxfhdProducts != null && bxfhdProducts.size()>0)
		{
			for(int i=0;i<bxfhdProducts.size();i++)
			{
				BxfhdProduct bxfhdProduct = (BxfhdProduct)bxfhdProducts.get(i);
				if(bxfhdProduct != null)
				{
					if(!bxfhdProduct.getProduct_id().equals("") && !bxfhdProduct.getProduct_name().equals(""))
					{				
						//只有在系统正式使用后才去修改商品的库存和处理序列号
						//系统启用前也可输入商品序列号，但不硬性强制，对于输入的序列号系统做处理
						
						//if(sysInitSetDao.getQyFlag().equals("1")){
						//强制序列号处理
						
						if((bxfhdProduct.getQz_serial_num() != null) && (!bxfhdProduct.getQz_serial_num().equals("")))
						{
							String[] arryNums = (bxfhdProduct.getQz_serial_num()).split(",");
							
							ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
							
							for(int k=0;k<arryNums.length;k++)
							{
								shSerialNumFlow.setCj_date(DateComFunc.getToday());			
								shSerialNumFlow.setFs_date(bxfhd.getFh_date());
								shSerialNumFlow.setJsr(bxfhd.getJsr());
								shSerialNumFlow.setKf("好件库");
								shSerialNumFlow.setQz_serial_num(arryNums[k]);
								shSerialNumFlow.setRk_date(DateComFunc.getToday());
								shSerialNumFlow.setYw_dj_id(bxfhd.getId());
								shSerialNumFlow.setYw_url("viewBxfhd.html?id=");
								shSerialNumFlow.setYwtype("报修返还");
								shSerialNumFlow.setClient_name(bxfhd.getBxcs());
//								添加序列号流转记录
								shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
							}
						}
					}
				}
			}
		}
	}
  
  /**
	 * 检查好件库是否有该序列号
	 * @param bxfhdProducts
	 * @param bxfhd
	 * @return
	 */
	public String isHaoShkcExist(Bxfhd bxfhd,List bxfhdProducts)
	{		
		String message="";
		String serials="";
		String serialNumStr[]=getSerialNum(bxfhdProducts).toString().split(",");	
		if(!serialNumStr.equals(""))
		{
			
			for(int i=0;i<serialNumStr.length;i++)
			{
			  if(!serialNumStr[i].equals(""))
			  {
				  int count=shkcDao.getHaoShkcBySerialNum(serialNumStr[i]);
				  if(count!=0)
			      {	
					serials+=serialNumStr[i]+" ";		 
				  }				 
			    }
		    }
		}
		
		if(!serials.equals(""))
		{
			message+="好件库存在该序列号:"+serials+" 的商品,请检查！";
		}
		return message;
	} 
	
	
	/**
	 * 检查在外库是否有该序列号
	 * @param bxfhdProducts
	 * @return
	 */
	public String isZyShkcExist(Bxfhd bxfhd,List bxfhdProducts)
	{		
		String message="";
		String serials="";
		String serialNumStr[]=getSerialNum(bxfhdProducts).toString().split(",");	
		if(!serialNumStr.equals(""))
		{
			
			for(int i=0;i<serialNumStr.length;i++)
			{
			  if(!serialNumStr[i].equals(""))
			  {
				  int count=shkcDao.getWeiShkcBySerialNum(serialNumStr[i]);
				  if(count==0)
			      {
					  serials+=serialNumStr[i]+" ";					 
				  }
			    }
		    }
		}	
		if(!serials.equals(""))
		{
			 message+="该序列号:"+serials+" 的商品未送修在外,请检查！";
		}
		
		return message;
	}
	
	public String getSerialNum(List bxfhdProducts)
	{
		 
		String serialNumList="";
		if(bxfhdProducts!=null&&bxfhdProducts.size()>0)
		{
		   for(int i=0;i<bxfhdProducts.size();i++)
		  {
			  BxfhdProduct bxfhdProduct = (BxfhdProduct)bxfhdProducts.get(i);
			 if(bxfhdProduct!=null)
			 {
				if(!bxfhdProduct.getProduct_name().equals("")&&!bxfhdProduct.getProduct_id().equals(""))
				{	
					if(!bxfhdProduct.getQz_serial_num().equals(""))
					{
			          String serialNum[]=bxfhdProduct.getQz_serial_num().toString().split(",");
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
	 * 判断库存量是否满足报修返还处理
	 * @param bxfhd
	 * @param bxfhdProducts
	 */
	public String checkKc(Bxfhd bxfhd,List bxfhdProducts){
		String msg = "";
		
		if(bxfhdProducts != null && bxfhdProducts.size()>0){
			for(int i=0;i<bxfhdProducts.size();i++){
				BxfhdProduct bxfhdProduct = (BxfhdProduct)bxfhdProducts.get(i);
				if(bxfhdProduct != null){
					if(!bxfhdProduct.getProduct_id().equals("") && !bxfhdProduct.getProduct_name().equals("")){
						String product_id = bxfhdProduct.getProduct_id();
						
						//进行库存数量判断
						int kcnums;
						if(bxfhdProduct.getQz_serial_num().equals("")){
						   Shkc shkc = (Shkc)shkcDao.getShkc(product_id,"2");
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
							kcnums= shkcDao.getShkcQz(product_id,"2");							
						}
						int cknums = bxfhdProduct.getNums();  //要报修数量
						if(cknums>kcnums){
							msg += bxfhdProduct.getProduct_name() + " 当前库存为：" + kcnums + "  无法进行报修返还处理\n";
						}						
					}
				}
			}
		}
		
		return msg;
	}
	
	
public BxfhdDAO getBxfhdDao() {
	return bxfhdDao;
}

public void setBxfhdDao(BxfhdDAO bxfhdDao) {
	this.bxfhdDao = bxfhdDao;
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
