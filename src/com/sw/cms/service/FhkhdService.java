package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.FhkhdDAO;
import com.sw.cms.dao.QtsrDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.Fhkhd;
import com.sw.cms.model.FhkhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtsr;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.model.Shkc;
import com.sw.cms.util.DateComFunc;

public class FhkhdService 
{
   private FhkhdDAO fhkhdDao;
   private ShkcDAO shkcDao;
   private QtsrDAO qtsrDao;
   private ShSerialNumFlowDAO shSerialNumFlowDao;
   private QtsrService qtsrService;
 
public QtsrService getQtsrService() {
	return qtsrService;
}

public void setQtsrService(QtsrService qtsrService) {
	this.qtsrService = qtsrService;
}

public Page getFhkhdList(String con,int curPage,int rowsPerPage)
   {
	  return fhkhdDao.getFhkhdList(con,curPage,rowsPerPage);
   }
   
public String updatefhkhdId()
{
	  return fhkhdDao.getfhkhdId();
}

   public String updateFhkhdId()
   {
	   return fhkhdDao.updateFhkhdId();
   }
   
   public String isHaoShkcExist(List fhkhdProducts)
   {
	   String message="";
	   String serials="";
	   String serialNumStr[]=getSerialNum(fhkhdProducts).toString().split(",");	
	   if(!serialNumStr.equals(""))
		{
			
			for(int i=0;i<serialNumStr.length;i++)
			{
			  if(!serialNumStr[i].equals(""))
			  {
		    	   int count=shkcDao.getHaoShkcBySerialNum(serialNumStr[i]);
		    	   if(count==0)
		    	   {
		    		   serials+=serialNumStr[i]+" ";
		    	   }
		       }
	       }
	   }
	   if(!serials.equals(""))
		{
		   message="�ü����������к�:"+serials+" ����Ʒ,���飡";
		}   
	   return message;
   }
   
   public String getSerialNum(List fhkhdProducts)
	{
		 
		String serialNumList="";
		if(fhkhdProducts!=null&&fhkhdProducts.size()>0)
		{
		   for(int i=0;i<fhkhdProducts.size();i++)
		  {
			   FhkhdProduct fhkhdProduct=(FhkhdProduct)fhkhdProducts.get(i);
			 if(fhkhdProduct!=null)
			 {
				if(!fhkhdProduct.getProduct_name().equals("")&&!fhkhdProduct.getProduct_id().equals(""))
				{	
					if(!fhkhdProduct.getQz_serial_num().equals(""))
					{
			          String serialNum[]=fhkhdProduct.getQz_serial_num().toString().split(",");
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
	 * �жϿ�����Ƿ����㷵�ؿͻ�����
	 * @param fhkhd
	 * @param fhkhdProducts
	 */
	public String checkKc(Fhkhd fhkhd,List fhkhdProducts){
		String msg = "";
		
		if(fhkhdProducts != null && fhkhdProducts.size()>0){
			for(int i=0;i<fhkhdProducts.size();i++){
				FhkhdProduct fhkhdProduct = (FhkhdProduct)fhkhdProducts.get(i);
				if(fhkhdProduct != null){
					if(!fhkhdProduct.getProduct_id().equals("") && !fhkhdProduct.getProduct_name().equals("")){
						String product_id = fhkhdProduct.getProduct_id();
						
						//���п�������ж�
						int kcnums;
						if(fhkhdProduct.getQz_serial_num().equals("")){
						   Shkc shkc = (Shkc)shkcDao.getShkc(product_id,"3");
						   if(shkc != null){
							   kcnums = shkc.getNums();//�������
							}
							else
							{
							   kcnums =0;
							}
						}
						else
						{
							kcnums = shkcDao.getShkcQz(product_id,"3");							
						}
						int cknums = fhkhdProduct.getNums();  //Ҫ��������							
						if(cknums>kcnums){
							msg += fhkhdProduct.getProduct_name() + " ��ǰ���Ϊ��" + kcnums + "  �޷����б��޴���\n";
						}						
					}
				}
			}
		}
		
		return msg;
	}
   
   public void savefhkhd(Fhkhd fhkhd,List fhkhdProducts)
   {	   
//		 ��������ͻ������ύ����������
		if(fhkhdDao.isFhkhdSubmit(fhkhd.getId())){
			return;
		}
	   fhkhdDao.saveFhkhd(fhkhd, fhkhdProducts);
	   if(fhkhd.getState().equals("���ύ"))
	   {
		   updateShkc(fhkhd,fhkhdProducts);//ɾ���ۺ���
		   saveSerialNumFlow(fhkhd,fhkhdProducts); //������к���ת��¼
		   if(!fhkhd.getSkzh().equals(""))//�����������
		   {
			   saveQtsr(fhkhd);//����ʽ�����
		   }
		 
	   }
   }
   public void updatefhkhd(Fhkhd fhkhd,List fhkhdProducts)
   {
	   fhkhdDao.updateFhkhd(fhkhd, fhkhdProducts);
	   if(fhkhd.getState().equals("���ύ"))
	   {
		   updateShkc(fhkhd,fhkhdProducts);//�����ۺ���
		   saveSerialNumFlow(fhkhd,fhkhdProducts); //������к���ת��¼
		   if(!fhkhd.getSkzh().equals(""))//�����������
		   {
			   saveQtsr(fhkhd);//����ʽ�����
		   }
	   }
   }
   
   /**
    * ɾ������еĲֿ���Ϣ
    * @param fhkhd
    * @param fhkhdProducts
    */
   public void updateShkc(Fhkhd fhkhd,List fhkhdProducts)
   {
	   if(fhkhdProducts!=null&&fhkhdProducts.size()>0)
	   {
		   for(int i=0;i<fhkhdProducts.size();i++)
		   {
			   FhkhdProduct fhkhdProduct=(FhkhdProduct)fhkhdProducts.get(i);
			   if(!fhkhdProduct.getProduct_id().equals("") && !fhkhdProduct.getProduct_name().equals(""))
			   {
				   if((fhkhdProduct.getQz_serial_num() != null) && (!fhkhdProduct.getQz_serial_num().equals("")))
				   {
				      String[] arryNums = (fhkhdProduct.getQz_serial_num()).split(",");
					
					  for(int k=0;k<arryNums.length;k++)
				      {
				         shkcDao.deleteShkcById(fhkhdProduct.getProduct_id(),arryNums[k]);//ɾ������еĲֿ���Ϣ
				      }
				   }					
				   else
				   {
						Shkc shkc = (Shkc)shkcDao.getShkc(fhkhdProduct.getProduct_id(),"3");
						if((shkc.getNums()-fhkhdProduct.getNums())==0)
						{
							shkcDao.updateShkcStateAll(fhkhdProduct.getProduct_id(),fhkhdProduct.getNums(),"","3");
						}
						else
						{
							shkcDao.updateShkcNums(fhkhdProduct.getProduct_id(),fhkhdProduct.getNums(),"","3");
							shkcDao.updateShkcStateNums(fhkhdProduct.getProduct_id(),fhkhdProduct.getNums(),"","3");
						}
					}
				   
			   }
			   
		   }
	   }	  
   }
   /**
    * ������к���ת��¼
    * @param fhkhd
    * @param fhkhdProducts
    */
   public void saveSerialNumFlow(Fhkhd fhkhd,List fhkhdProducts)
   {
	   ShSerialNumFlow  shSerialNumFlow=null;
	   if(fhkhdProducts!=null&&fhkhdProducts.size()>0)
	   {
		   for(int i=0;i<fhkhdProducts.size();i++)
		   {
			   FhkhdProduct fhkhdProduct=(FhkhdProduct)fhkhdProducts.get(i);
			   if(!(fhkhdProduct.getProduct_name().equals(""))&&!(fhkhdProduct.getQz_serial_num().equals("")))
			   {
				   String[] arryNums = (fhkhdProduct.getQz_serial_num()).split(",");
					
					for(int k=0;k<arryNums.length;k++)
				   {
				    shSerialNumFlow=new ShSerialNumFlow(); 
				    shSerialNumFlow.setCj_date(DateComFunc.getToday());
					shSerialNumFlow.setClient_name(fhkhd.getClient_id());
					shSerialNumFlow.setFs_date(fhkhd.getFh_date());
					shSerialNumFlow.setJsr(fhkhd.getJsr());					 
					shSerialNumFlow.setLinkman(fhkhd.getLxr());
					shSerialNumFlow.setQz_serial_num(arryNums[k]);					 
					shSerialNumFlow.setYw_dj_id(fhkhd.getId());
					shSerialNumFlow.setYw_url("viewFhkhd.html?id=");
					shSerialNumFlow.setYwtype("�����ͻ�");
					shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
				   }
			   }			  		   
		   }
	   }
   }
   
   /**
    * ����ʽ�����
    * @param fhkhd
    */
   public void saveQtsr(Fhkhd fhkhd)
   {
	   Qtsr qtsr=new Qtsr();
	   qtsr.setCzr(fhkhd.getCjr());
	   qtsr.setId(qtsrDao.getQtsrID());
	   qtsr.setJsr(fhkhd.getJsr());
	   qtsr.setRemark("�����ͻ���["+fhkhd.getId()+"] ��ȡ����:"+fhkhd.getSkje());
	   qtsr.setSkje(fhkhd.getSkje());
	   qtsr.setSkzh(fhkhd.getSkzh());
	   qtsr.setSr_date(DateComFunc.getToday());
	   qtsr.setState("���ύ");
	   qtsr.setType("����");
	   qtsrService.saveQtsr(qtsr);	   
   }

   public List getFhkhdProductById(String id)
   {
	  return  fhkhdDao.getFhkhdProductById(id);
   }
   
   public void delFhkhd(String id)
   {
	   fhkhdDao.delFhkhd(id);
   }
   
   public Object getFhkhd(String id)
   {
	   return fhkhdDao.getFhkhd(id);
   }
   
   
   
public FhkhdDAO getFhkhdDao() {
	return fhkhdDao;
}

public void setFhkhdDao(FhkhdDAO fhkhdDao) {
	this.fhkhdDao = fhkhdDao;
}

public ShkcDAO getShkcDao() {
	return shkcDao;
}

public void setShkcDao(ShkcDAO shkcDao) {
	this.shkcDao = shkcDao;
}

public QtsrDAO getQtsrDao() {
	return qtsrDao;
}

public void setQtsrDao(QtsrDAO qtsrDao) {
	this.qtsrDao = qtsrDao;
}

public ShSerialNumFlowDAO getShSerialNumFlowDao() {
	return shSerialNumFlowDao;
}

public void setShSerialNumFlowDao(ShSerialNumFlowDAO shSerialNumFlowDao) {
	this.shSerialNumFlowDao = shSerialNumFlowDao;
}
}
