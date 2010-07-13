package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.dao.YkckDAO;
import com.sw.cms.dao.YkrkDAO;
import com.sw.cms.model.FhkhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.model.Shkc;
import com.sw.cms.model.Ykck;
import com.sw.cms.model.YkckProduct;
import com.sw.cms.model.Ykrk;
import com.sw.cms.model.YkrkProduct;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;

public class YkrkService 
{
	 private YkrkDAO ykrkDao;
	  private ProductKcDAO productKcDao;
	  private ShSerialNumFlowDAO shSerialNumFlowDao;
	  private ShkcDAO shkcDao;
	  private SerialNumDAO serialNumDao;
	  
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
//			 如果移库入库已提交，不做处理
			if(ykrkDao.isYkrkdSubmit(ykrk.getId())){
				return;
			}
			ykrkDao.saveYkrk(ykrk, ykrkProducts);
			
			if(ykrk.getState().equals("已提交")){ //改变库存值
				 
				 
				 	if(ykrkProducts!=null&&ykrkProducts.size()>0)
					   {
						   for(int i=0;i<ykrkProducts.size();i++)
						   {
							   YkrkProduct  ykrkProduct=(YkrkProduct)ykrkProducts.get(i);
							   if(ykrkProduct!=null)
							   {
								   if(!ykrkProduct.getProduct_name().equals(""))
								   {									   
									        shkcDao.deleteShkcHaoById(ykrkProduct.getProduct_id(),ykrkProduct.getQz_serial_num());
									        ShSerialNumFlow shSerialNumFlow=new ShSerialNumFlow();	
									        SerialNumMng serialNumMng = new SerialNumMng();
											SerialNumFlow serialNumFlow = new SerialNumFlow();
										    shSerialNumFlow=new ShSerialNumFlow();//序列号流转记录										     
										    shSerialNumFlow.setCj_date(DateComFunc.getToday());
										    shSerialNumFlow.setFs_date(ykrk.getRk_date());
										    shSerialNumFlow.setJsr(ykrk.getJsr());										     
										    shSerialNumFlow.setQz_serial_num(ykrkProduct.getQz_serial_num());
										    shSerialNumFlow.setRk_date(DateComFunc.getToday());
										    shSerialNumFlow.setYw_dj_id(ykrk.getId());
										    shSerialNumFlow.setYw_url("viewYkrk.html?id=");
										    shSerialNumFlow.setYwtype("移库入库");
										    shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);	
										    
								 										
											serialNumDao.updateSerialNumStateDOA(ykrk,ykrkProduct); //更新序列号状态
											productKcDao.updateProductKcDOA(ykrk,ykrkProduct);
											 
											serialNumFlow.setCzr(ykrk.getCzr());											
											serialNumFlow.setYwtype("移库入库");											
											serialNumFlow.setFs_date(ykrk.getRk_date());
											serialNumFlow.setJsr(StaticParamDo.getRealNameById(ykrk.getJsr()));
											serialNumFlow.setKf_dj_id(ykrk.getRk_store_id());
											serialNumFlow.setSerial_num(ykrkProduct.getQz_serial_num());
											serialNumFlow.setYw_dj_id(ykrk.getId());
											serialNumFlow.setYw_url("viewYkrk.html?id=");								
											serialNumDao.saveSerialFlow(serialNumFlow);  //保存序列号流转过程											 
								   }
								   
							   }
						   }
					   }
				}
			 
		}
		
		/**
		 * 判断好件库库存量是否满足入库需要
		 * @param ckd
		 * @param ckdProducts
		 */
		public String checkHaoKc(Ykrk ykrk,List ykrkProducts){
			String msg = "";
						
			if(ykrkProducts != null && ykrkProducts.size()>0){
				for(int i=0;i<ykrkProducts.size();i++){
					YkrkProduct ykrkProduct = (YkrkProduct)ykrkProducts.get(i);
					if(ykrkProduct != null){
						if(!ykrkProduct.getProduct_id().equals("") && !ykrkProduct.getProduct_name().equals("")){
							String product_id = ykrkProduct.getProduct_id();
							int cknums = ykrkProduct.getNums();  //要入库数量						
							int kcnums = productKcDao.getHaoKcNums(product_id);//库存数量
							
							if(cknums>kcnums){
								msg += ykrkProduct.getProduct_name() + " 当前好件库中库存为：" + kcnums + "  无法满足调拨请求，不能出库\n";
							}
						}
					}
				}
			}
			
			return msg;
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
						if(!ykrkProduct.getProduct_name().equals("")&&!ykrkProduct.getQz_serial_num().equals(""))
						{
					      
							 
							   int count=shkcDao.getHaoShkcBySerialNum(ykrkProduct.getQz_serial_num());
					    	   if(count==0)
					    	   {
					    		   message="好件库已无序列号:"+ykrkProduct.getQz_serial_num()+" 的商品,请检查！";
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
								   shkcDao.deleteShkcHaoById(ykrkProduct.getProduct_id(),ykrkProduct.getQz_serial_num());
							        ShSerialNumFlow shSerialNumFlow=new ShSerialNumFlow();	
							        SerialNumMng serialNumMng = new SerialNumMng();
									SerialNumFlow serialNumFlow = new SerialNumFlow();
								    shSerialNumFlow=new ShSerialNumFlow();//序列号流转记录										     
								    shSerialNumFlow.setCj_date(DateComFunc.getToday());
								    shSerialNumFlow.setFs_date(ykrk.getRk_date());
								    shSerialNumFlow.setJsr(ykrk.getJsr());										     
								    shSerialNumFlow.setQz_serial_num(ykrkProduct.getQz_serial_num());
								    shSerialNumFlow.setRk_date(DateComFunc.getToday());
								    shSerialNumFlow.setYw_dj_id(ykrk.getId());
								    shSerialNumFlow.setYw_url("viewYkrk.html?id=");
								    shSerialNumFlow.setYwtype("移库入库");
								    shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);	
								    
						 										
									serialNumDao.updateSerialNumStateDOA(ykrk,ykrkProduct); //更新序列号状态
									productKcDao.updateProductKcDOA(ykrk,ykrkProduct);
									 
									serialNumFlow.setCzr(ykrk.getCzr());											
									serialNumFlow.setYwtype("移库入库");											
									serialNumFlow.setFs_date(ykrk.getRk_date());
									serialNumFlow.setJsr(StaticParamDo.getRealNameById(ykrk.getJsr()));
									serialNumFlow.setKf_dj_id(ykrk.getRk_store_id());
									serialNumFlow.setSerial_num(ykrkProduct.getQz_serial_num());
									serialNumFlow.setYw_dj_id(ykrk.getId());
									serialNumFlow.setYw_url("viewYkrk.html?id=");								
									serialNumDao.saveSerialFlow(serialNumFlow);  //保存序列号流转过程
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

	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}

	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}
}
