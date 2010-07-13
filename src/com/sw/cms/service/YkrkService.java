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
		 * ����ⷿ���������Ϣ
		 * @param kfdb
		 * @param kfdbProducts
		 */
		public void saveYkrk(Ykrk ykrk,List ykrkProducts){
//			 ����ƿ�������ύ����������
			if(ykrkDao.isYkrkdSubmit(ykrk.getId())){
				return;
			}
			ykrkDao.saveYkrk(ykrk, ykrkProducts);
			
			if(ykrk.getState().equals("���ύ")){ //�ı���ֵ
				 
				 
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
										    shSerialNumFlow=new ShSerialNumFlow();//���к���ת��¼										     
										    shSerialNumFlow.setCj_date(DateComFunc.getToday());
										    shSerialNumFlow.setFs_date(ykrk.getRk_date());
										    shSerialNumFlow.setJsr(ykrk.getJsr());										     
										    shSerialNumFlow.setQz_serial_num(ykrkProduct.getQz_serial_num());
										    shSerialNumFlow.setRk_date(DateComFunc.getToday());
										    shSerialNumFlow.setYw_dj_id(ykrk.getId());
										    shSerialNumFlow.setYw_url("viewYkrk.html?id=");
										    shSerialNumFlow.setYwtype("�ƿ����");
										    shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);	
										    
								 										
											serialNumDao.updateSerialNumStateDOA(ykrk,ykrkProduct); //�������к�״̬
											productKcDao.updateProductKcDOA(ykrk,ykrkProduct);
											 
											serialNumFlow.setCzr(ykrk.getCzr());											
											serialNumFlow.setYwtype("�ƿ����");											
											serialNumFlow.setFs_date(ykrk.getRk_date());
											serialNumFlow.setJsr(StaticParamDo.getRealNameById(ykrk.getJsr()));
											serialNumFlow.setKf_dj_id(ykrk.getRk_store_id());
											serialNumFlow.setSerial_num(ykrkProduct.getQz_serial_num());
											serialNumFlow.setYw_dj_id(ykrk.getId());
											serialNumFlow.setYw_url("viewYkrk.html?id=");								
											serialNumDao.saveSerialFlow(serialNumFlow);  //�������к���ת����											 
								   }
								   
							   }
						   }
					   }
				}
			 
		}
		
		/**
		 * �жϺü��������Ƿ����������Ҫ
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
							int cknums = ykrkProduct.getNums();  //Ҫ�������						
							int kcnums = productKcDao.getHaoKcNums(product_id);//�������
							
							if(cknums>kcnums){
								msg += ykrkProduct.getProduct_name() + " ��ǰ�ü����п��Ϊ��" + kcnums + "  �޷�����������󣬲��ܳ���\n";
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
					    		   message="�ü����������к�:"+ykrkProduct.getQz_serial_num()+" ����Ʒ,���飡";
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
			
			if(ykrk.getState().equals("���ύ")){ //�ı���ֵ
				//this.updateKc(kfdb, kfdbProducts);
				//this.updateSerialNum(kfdb, kfdbProducts);//�������к�
				
				 
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
								    shSerialNumFlow=new ShSerialNumFlow();//���к���ת��¼										     
								    shSerialNumFlow.setCj_date(DateComFunc.getToday());
								    shSerialNumFlow.setFs_date(ykrk.getRk_date());
								    shSerialNumFlow.setJsr(ykrk.getJsr());										     
								    shSerialNumFlow.setQz_serial_num(ykrkProduct.getQz_serial_num());
								    shSerialNumFlow.setRk_date(DateComFunc.getToday());
								    shSerialNumFlow.setYw_dj_id(ykrk.getId());
								    shSerialNumFlow.setYw_url("viewYkrk.html?id=");
								    shSerialNumFlow.setYwtype("�ƿ����");
								    shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);	
								    
						 										
									serialNumDao.updateSerialNumStateDOA(ykrk,ykrkProduct); //�������к�״̬
									productKcDao.updateProductKcDOA(ykrk,ykrkProduct);
									 
									serialNumFlow.setCzr(ykrk.getCzr());											
									serialNumFlow.setYwtype("�ƿ����");											
									serialNumFlow.setFs_date(ykrk.getRk_date());
									serialNumFlow.setJsr(StaticParamDo.getRealNameById(ykrk.getJsr()));
									serialNumFlow.setKf_dj_id(ykrk.getRk_store_id());
									serialNumFlow.setSerial_num(ykrkProduct.getQz_serial_num());
									serialNumFlow.setYw_dj_id(ykrk.getId());
									serialNumFlow.setYw_url("viewYkrk.html?id=");								
									serialNumDao.saveSerialFlow(serialNumFlow);  //�������к���ת����
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
