package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.dao.YkckDAO;
import com.sw.cms.model.Jjd;
import com.sw.cms.model.JjdProduct;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.KfdbProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.model.Shkc;
import com.sw.cms.model.Ykck;
import com.sw.cms.model.YkckProduct;
import com.sw.cms.util.DateComFunc;

public class YkckService 
{
  private YkckDAO ykckDao;
  private ProductKcDAO productKcDao;
  private ShSerialNumFlowDAO shSerialNumFlowDao;
  private ShkcDAO shkcDao;
  
  public ProductKcDAO getProductKcDao() {
	return productKcDao;
}

public void setProductKcDao(ProductKcDAO productKcDao) {
	this.productKcDao = productKcDao;
}

public Page getYkckList(String con,int curPage, int rowsPerPage){
		return ykckDao.getYkckList(con, curPage, rowsPerPage);
	}
  
  public String updateYkckID()
  {
	  return ykckDao.getYkckID();
  }
  
	/**
	 * 保存库房调拨相关信息
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public void saveYkck(Ykck ykck,List ykckProducts){
		ykckDao.saveYkck(ykck, ykckProducts);
		
		if(ykck.getState().equals("已提交")){ //改变库存值
			//this.updateKc(ykck, ykckProducts);
			//this.updateSerialNum(ykck, ykckProducts); //处理序列号
			 
			 	if(ykckProducts!=null&&ykckProducts.size()>0)
				   {
					   for(int i=0;i<ykckProducts.size();i++)
					   {
						   YkckProduct  ykckProduct=(YkckProduct)ykckProducts.get(i);
						   if(ykckProduct!=null)
						   {
							   if(!ykckProduct.getProduct_name().equals(""))
							   {
								    
								   int count=ykckProduct.getNums();
								  
								   Shkc shkc=new Shkc();
								   ShSerialNumFlow shSerialNumFlow=new ShSerialNumFlow();
								   if(count>0)
								   {
									  String serialStr[]=ykckProduct.getQz_serial_num().split(",");
									  
									  for(int j=0;j<count;j++)
									  {
										shkc=new Shkc();//售后库存
									    shkc.setProduct_id(ykckProduct.getProduct_id());
									    shkc.setProduct_name(ykckProduct.getProduct_name());
									    shkc.setProduct_xh(ykckProduct.getProduct_xh());
									    shkc.setQz_serial_num(serialStr[j]);
									    shkc.setRemark(ykckProduct.getProduct_remark());
									    shkc.setState("1");
									    
									    shkcDao.saveShkc(shkc);
									    shSerialNumFlow=new ShSerialNumFlow();//序列号流转记录
									     
									    shSerialNumFlow.setCj_date(DateComFunc.getToday());
									    shSerialNumFlow.setFs_date(ykck.getCk_date());
									    shSerialNumFlow.setJsr(ykck.getJsr());
									    shSerialNumFlow.setKf("在外库");
									    shSerialNumFlow.setQz_serial_num(serialStr[j]);
									    shSerialNumFlow.setRk_date(DateComFunc.getToday());
									    shSerialNumFlow.setYw_dj_id(ykck.getId());
									    shSerialNumFlow.setYw_url("viewYkck.html?id=");
									    shSerialNumFlow.setYwtype("换件");
									    shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
									  }
								   }
								   else
								   {
									   shkc=new Shkc();//售后库存
									    shkc.setProduct_id(ykckProduct.getProduct_id());
									    shkc.setProduct_name(ykckProduct.getProduct_name());
									    shkc.setProduct_xh(ykckProduct.getProduct_xh());
									    shkc.setQz_serial_num(ykckProduct.getQz_serial_num());
									    shkc.setRemark(ykckProduct.getProduct_remark());
									    shkc.setState("1");
									    
									    shkcDao.saveShkc(shkc);
									    shSerialNumFlow=new ShSerialNumFlow();//序列号流转记录
									     
									    shSerialNumFlow.setCj_date(DateComFunc.getToday());
									    shSerialNumFlow.setFs_date(ykck.getCk_date());
									    shSerialNumFlow.setJsr(ykck.getJsr());
									    shSerialNumFlow.setKf("在外库");
									    shSerialNumFlow.setQz_serial_num(ykckProduct.getQz_serial_num());
									    shSerialNumFlow.setRk_date(DateComFunc.getToday());
									    shSerialNumFlow.setYw_dj_id(ykck.getId());
									    shSerialNumFlow.setYw_url("viewYkck.html?id=");
									    shSerialNumFlow.setYwtype("换件");
									    shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
								   }
							   }
							   
						   }
					   }
				   }
			}
		 
	}
	
	public String isSerialNumInKcExist(List ykckProduct)
	{
		String message="";
		String serialNumStr[]=getSerialNum(ykckProduct).toString().split(",");	
		 
		if(serialNumStr.length!=0)
		{
			
			for(int i=0;i<serialNumStr.length;i++)
			{
			   int count=shkcDao.getShkcBySerialNum(serialNumStr[i]);
			   if(count>0)
			   {
				   message="提交的接件单产品序列号已在售后库存中拥有，请检查！";
				   break;
			   }
			}
		}
		 
		return message;
	}
	public String getSerialNum(List ykckProducts)
	{
		 
		String serialNumList="";
		if(ykckProducts!=null&&ykckProducts.size()>0)
		{
		   for(int i=0;i<ykckProducts.size();i++)
		  {
			   YkckProduct ykckProduct=(YkckProduct)ykckProducts.get(i);
			 if(ykckProduct!=null)
			 {
				if(!ykckProduct.getProduct_name().equals("")&&!ykckProduct.getQz_serial_num().equals(""))
				{
			     int count=ykckProduct.getNums();
			     if(count>1)
			     {
			    	 String serialNum[]=ykckProduct.getQz_serial_num().toString().split(",");
				    for(int j=0;j<count;j++)
				    {
				    	serialNumList+=serialNum[j]+",";
				    }
		 	     }
			     else
			     {
				   serialNumList+=ykckProduct.getQz_serial_num()+",";
			     }
				}
			 }
		  }
		}
		return serialNumList;
	}
	
	
	/**
	 * 判断库存量是否满足出库需要
	 * @param ckd
	 * @param ckdProducts
	 */
	public String checkKc(Ykck ykck,List ykckProducts){
		String msg = "";
		String store_id = ykck.getCk_store_id();
		
		if(ykckProducts != null && ykckProducts.size()>0){
			for(int i=0;i<ykckProducts.size();i++){
				YkckProduct ykckProduct = (YkckProduct)ykckProducts.get(i);
				if(ykckProduct != null){
					if(!ykckProduct.getProduct_id().equals("") && !ykckProduct.getProduct_name().equals("")){
						String product_id = ykckProduct.getProduct_id();
						int cknums = ykckProduct.getNums();  //要出库数量						
						int kcnums = productKcDao.getKcNums(product_id, store_id);//库存数量
						
						if(cknums>kcnums){
							msg += ykckProduct.getProduct_name() + " 当前库存为：" + kcnums + "  无法满足调拨请求，不能出库\n";
						}
					}
				}
			}
		}
		
		return msg;
	}
	
	public void updateYkck(Ykck ykck,List ykckProducts){
		ykckDao.updateYkck(ykck, ykckProducts);
		
		if(ykck.getState().equals("已提交")){ //改变库存值
			//this.updateKc(kfdb, kfdbProducts);
			//this.updateSerialNum(kfdb, kfdbProducts);//处理序列号
			
			if(ykck.getState().equals("已提交")){ //改变库存值
				//this.updateKc(ykck, ykckProducts);
				//this.updateSerialNum(ykck, ykckProducts); //处理序列号
				 
				 	if(ykckProducts!=null&&ykckProducts.size()>0)
					   {
						   for(int i=0;i<ykckProducts.size();i++)
						   {
							   YkckProduct  ykckProduct=(YkckProduct)ykckProducts.get(i);
							   if(ykckProduct!=null)
							   {
								   if(!ykckProduct.getProduct_name().equals(""))
								   {
									    
									   int count=ykckProduct.getNums();
									  
									   Shkc shkc=new Shkc();
									   ShSerialNumFlow shSerialNumFlow=new ShSerialNumFlow();
									   if(count>0)
									   {
										  String serialStr[]=ykckProduct.getQz_serial_num().split(",");
										  
										  for(int j=0;j<count;j++)
										  {
											shkc=new Shkc();//售后库存
										    shkc.setProduct_id(ykckProduct.getProduct_id());
										    shkc.setProduct_name(ykckProduct.getProduct_name());
										    shkc.setProduct_xh(ykckProduct.getProduct_xh());
										    shkc.setQz_serial_num(serialStr[j]);
										    shkc.setRemark(ykckProduct.getProduct_remark());
										    shkc.setState("1");
										    
										    shkcDao.saveShkc(shkc);
										    shSerialNumFlow=new ShSerialNumFlow();//序列号流转记录
										     
										    shSerialNumFlow.setCj_date(DateComFunc.getToday());
										    shSerialNumFlow.setFs_date(ykck.getCk_date());
										    shSerialNumFlow.setJsr(ykck.getJsr());
										    shSerialNumFlow.setKf("在外库");
										    shSerialNumFlow.setQz_serial_num(serialStr[j]);
										    shSerialNumFlow.setRk_date(DateComFunc.getToday());
										    shSerialNumFlow.setYw_dj_id(ykck.getId());
										    shSerialNumFlow.setYw_url("viewYkck.html?id=");
										    shSerialNumFlow.setYwtype("换件");
										    shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
										  }
									   }
									   else
									   {
										   shkc=new Shkc();//售后库存
										    shkc.setProduct_id(ykckProduct.getProduct_id());
										    shkc.setProduct_name(ykckProduct.getProduct_name());
										    shkc.setProduct_xh(ykckProduct.getProduct_xh());
										    shkc.setQz_serial_num(ykckProduct.getQz_serial_num());
										    shkc.setRemark(ykckProduct.getProduct_remark());
										    shkc.setState("1");
										    
										    shkcDao.saveShkc(shkc);
										    shSerialNumFlow=new ShSerialNumFlow();//序列号流转记录
										     
										    shSerialNumFlow.setCj_date(DateComFunc.getToday());
										    shSerialNumFlow.setFs_date(ykck.getCk_date());
										    shSerialNumFlow.setJsr(ykck.getJsr());
										    shSerialNumFlow.setKf("在外库");
										    shSerialNumFlow.setQz_serial_num(ykckProduct.getQz_serial_num());
										    shSerialNumFlow.setRk_date(DateComFunc.getToday());
										    shSerialNumFlow.setYw_dj_id(ykck.getId());
										    shSerialNumFlow.setYw_url("viewYkck.html?id=");
										    shSerialNumFlow.setYwtype("换件");
										    shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
									   }
								   }
								   
							   }
						   }
					   }
				}
			 
		}
	}
	
	public boolean isDbFinish(String id) {
		return ykckDao.isDbFinish(id);
	}
	
	public void delYkck(String id){
		ykckDao.delYkck(id);
	}
		

	
	
	public Object getYkck(String id){
		return ykckDao.getYkck(id);
	}
	
	 
	public List getykckProducts(String id){
		return ykckDao.getykckProducts(id);
	}
	
	
	
public YkckDAO getYkckDao() {
	return ykckDao;
}

public void setYkckDao(YkckDAO ykckDao) {
	this.ykckDao = ykckDao;
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
