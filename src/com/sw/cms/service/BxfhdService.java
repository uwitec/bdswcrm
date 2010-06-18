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
	 * @param bxdProducts
	 * @return
	 */
	public boolean isHaoShkcExist(Bxfhd bxfhd,List bxfhdProducts)
	{
		boolean is = false;
		String message="";
		if(bxfhdProducts != null && bxfhdProducts.size()>0)
		{
			for(int i=0;i<bxfhdProducts.size();i++)
			{
				BxfhdProduct bxfhdProduct = (BxfhdProduct)bxfhdProducts.get(i);
				if(!bxfhdProduct.getQz_serial_num().equals(""))
			    {
				  int count=shkcDao.getHaoShkcBySerialNum(bxfhdProduct.getQz_serial_num());
				  if(count==0)
			     {
					message="好件库已无序列号:"+bxfhdProduct.getQz_serial_num()+" 的商品,请检查！";
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
