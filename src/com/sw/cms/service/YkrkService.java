package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.dao.YkckDAO;
import com.sw.cms.dao.YkrkDAO;
import com.sw.cms.model.FhkhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.model.Shkc;
import com.sw.cms.model.Ykck;
import com.sw.cms.model.YkckProduct;
import com.sw.cms.model.Ykrk;
import com.sw.cms.model.YkrkProduct;
import com.sw.cms.util.DateComFunc;

public class YkrkService 
{
	 private YkrkDAO ykrkDao;
	  private ProductKcDAO productKcDao;
	  private ShSerialNumFlowDAO shSerialNumFlowDao;
	  private ShkcDAO shkcDao;
	  
	  public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}

	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}

	public Page getYkrkList(String con,int curPage, int rowsPerPage){
			return ykrkDao.getYkrkList(con, curPage, rowsPerPage);
		}
	  
	  public String updateYkrkID()
	  {
		  return ykrkDao.getYkrkID();
	  }
	  
		/**
		 * 保存库房调拨相关信息
		 * @param kfdb
		 * @param kfdbProducts
		 */
		public void saveYkrk(Ykrk ykrk,List ykrkProducts){
			ykrkDao.saveYkrk(ykrk, ykrkProducts);
			
			if(ykrk.getState().equals("已提交")){ //改变库存值
				//this.updateKc(ykck, ykckProducts);
				//this.updateSerialNum(ykck, ykckProducts); //处理序列号
				 
				 	if(ykrkProducts!=null&&ykrkProducts.size()>0)
					   {
						   for(int i=0;i<ykrkProducts.size();i++)
						   {
							   YkrkProduct  ykrkProduct=(YkrkProduct)ykrkProducts.get(i);
							   if(ykrkProduct!=null)
							   {
								   if(!ykrkProduct.getProduct_name().equals(""))
								   {									   
									        shkcDao.deleteShkcWaiById(ykrkProduct.getOqz_serial_num());
									        ShSerialNumFlow shSerialNumFlow=new ShSerialNumFlow();																		  										 										    
										    shSerialNumFlow=new ShSerialNumFlow();//序列号流转记录										     
										    shSerialNumFlow.setCj_date(DateComFunc.getToday());
										    shSerialNumFlow.setFs_date(ykrk.getRk_date());
										    shSerialNumFlow.setJsr(ykrk.getJsr());										     
										    shSerialNumFlow.setQz_serial_num(ykrkProduct.getOqz_serial_num());
										    shSerialNumFlow.setRk_date(DateComFunc.getToday());
										    shSerialNumFlow.setYw_dj_id(ykrk.getId());
										    shSerialNumFlow.setYw_url("viewYkrk.html?id=");
										    shSerialNumFlow.setYwtype("移库入库");
										    shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);									   
								   }
								   
							   }
						   }
					   }
				}
			 
		}
		
		public String isSerialNumInKcExist(List ykrkProducts)
		{
			String message="";	
			if(ykrkProducts!=null&&ykrkProducts.size()>0)
			{
				 for(int i=0;i<ykrkProducts.size();i++)
				  {
					   YkrkProduct ykrkProduct=(YkrkProduct)ykrkProducts.get(i);
					 if(ykrkProduct!=null)
					 {
						if(!ykrkProduct.getProduct_name().equals("")&&!ykrkProduct.getOqz_serial_num().equals(""))
						{
					      
							 
							   int count=shkcDao.getWeiShkcBySerialNum(ykrkProduct.getOqz_serial_num());
					    	   if(count==0)
					    	   {
					    		   message="在外库已无序列号:"+ykrkProduct.getOqz_serial_num()+" 的商品,请检查！";
					    		   break;
					    	   }
				        }
				 
					 }
				  }
			}
			 
			
			return message;
		}
		
	 
 
		
		public void updateYkrk(Ykrk ykrk,List ykrkProducts){
			ykrkDao.updateYkrk(ykrk, ykrkProducts);
			
			if(ykrk.getState().equals("已提交")){ //改变库存值
				//this.updateKc(kfdb, kfdbProducts);
				//this.updateSerialNum(kfdb, kfdbProducts);//处理序列号
				
				 
				if(ykrkProducts!=null&&ykrkProducts.size()>0)
				   {
					   for(int i=0;i<ykrkProducts.size();i++)
					   {
						   YkrkProduct  ykrkProduct=(YkrkProduct)ykrkProducts.get(i);
						   if(ykrkProduct!=null)
						   {
							   if(!ykrkProduct.getProduct_name().equals(""))
							   {									   
								        shkcDao.deleteShkcWaiById(ykrkProduct.getOqz_serial_num());
								        ShSerialNumFlow shSerialNumFlow=new ShSerialNumFlow();																		  										 										    
									    shSerialNumFlow=new ShSerialNumFlow();//序列号流转记录										     
									    shSerialNumFlow.setCj_date(DateComFunc.getToday());
									    shSerialNumFlow.setFs_date(ykrk.getRk_date());
									    shSerialNumFlow.setJsr(ykrk.getJsr());										     
									    shSerialNumFlow.setQz_serial_num(ykrkProduct.getOqz_serial_num());
									    shSerialNumFlow.setRk_date(DateComFunc.getToday());
									    shSerialNumFlow.setYw_dj_id(ykrk.getId());
									    shSerialNumFlow.setYw_url("viewYkrk.html?id=");
									    shSerialNumFlow.setYwtype("移库入库");
									    shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);									   
							   }
							   
						   }
					   }
				   }
				 
			}
		}
		
		public boolean isDbFinish(String id) {
			return ykrkDao.isDbFinish(id);
		}
		
		public void delYkrk(String id){
			ykrkDao.delYkrk(id);
		}
	
		public Object getYkrk(String id){
			return ykrkDao.getYkrk(id);
		}
		
		 
		public List getykrkProducts(String id){
			return ykrkDao.getykrkProducts(id);
		}
 
	public YkrkDAO getYkrkDao() {
			return ykrkDao;
		}

		public void setYkrkDao(YkrkDAO ykrkDao) {
			this.ykrkDao = ykrkDao;
		}

	public ShSerialNumFlowDAO getShSerialNumFlowDao() {
		return shSerialNumFlowDao;
	}

	public void setShSerialNumFlowDao(ShSerialNumFlowDAO shSerialNumFlowDao) {
		this.shSerialNumFlowDao = shSerialNumFlowDao;
	}

	public ShkcDAO getShkcDao() {
		return shkcDao;
	}

	public void setShkcDao(ShkcDAO shkcDao) {
		this.shkcDao = shkcDao;
	}
}
