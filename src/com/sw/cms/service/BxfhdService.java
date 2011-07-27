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
	  
	  if(bxfhd.getState().equals("���ύ"))
	  {
		       //�޸Ŀ��Ϊ�ü���
			this.addBxfhd(bxfhd,bxfhdProducts);
			shkcDao.updateBxfhShkcState(bxfhdProducts, "3");			
	  }
  }
  
  public void saveBxfhd(Bxfhd bxfhd,List bxfhdProducts)
  {
//		 ������޵����ύ����������
		if(bxfhdDao.isBxfhdSubmit(bxfhd.getId())){
			return;
		}
	  bxfhdDao.saveBxfhd(bxfhd, bxfhdProducts);
	  if(bxfhd.getState().equals("���ύ"))
	  {
			   //�޸Ŀ��Ϊ�ü���		
			this.addBxfhd(bxfhd,bxfhdProducts);
			shkcDao.updateBxfhShkcState(bxfhdProducts, "3");								
	  }
 }
  
  /**
   * ������Ӧ�ı��޷���������Ʒ��Ϣ
   * @param bxfhd_id
   * @return
   */
   public List getBxfhdProducts(String bxfhd_id)
	{
		return bxfhdDao.getBxfhdProducts(bxfhd_id);
	}
  
  /**
	 * 
	 * <p>�����ǿ�����к������Ӧ���к���Ϣ</p?
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
						//ֻ����ϵͳ��ʽʹ�ú��ȥ�޸���Ʒ�Ŀ��ʹ������к�
						//ϵͳ����ǰҲ��������Ʒ���кţ�����Ӳ��ǿ�ƣ�������������к�ϵͳ������
						
						//if(sysInitSetDao.getQyFlag().equals("1")){
						//ǿ�����кŴ���
						
						if((bxfhdProduct.getQz_serial_num() != null) && (!bxfhdProduct.getQz_serial_num().equals("")))
						{
							String[] arryNums = (bxfhdProduct.getQz_serial_num()).split(",");
							
							ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
							
							for(int k=0;k<arryNums.length;k++)
							{
								shSerialNumFlow.setCj_date(DateComFunc.getToday());			
								shSerialNumFlow.setFs_date(bxfhd.getFh_date());
								shSerialNumFlow.setJsr(bxfhd.getJsr());
								shSerialNumFlow.setKf("�ü���");
								shSerialNumFlow.setQz_serial_num(arryNums[k]);
								shSerialNumFlow.setRk_date(DateComFunc.getToday());
								shSerialNumFlow.setYw_dj_id(bxfhd.getId());
								shSerialNumFlow.setYw_url("viewBxfhd.html?id=");
								shSerialNumFlow.setYwtype("���޷���");
								shSerialNumFlow.setClient_name(bxfhd.getBxcs());
//								������к���ת��¼
								shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
							}
						}
					}
				}
			}
		}
	}
  
  /**
	 * ���ü����Ƿ��и����к�
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
			message+="�ü�����ڸ����к�:"+serials+" ����Ʒ,���飡";
		}
		return message;
	} 
	
	
	/**
	 * ���������Ƿ��и����к�
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
			 message+="�����к�:"+serials+" ����Ʒδ��������,���飡";
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
	 * �жϿ�����Ƿ����㱨�޷�������
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
						
						//���п�������ж�
						int kcnums;
						if(bxfhdProduct.getQz_serial_num().equals("")){
						   Shkc shkc = (Shkc)shkcDao.getShkc(product_id,"2");
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
							kcnums= shkcDao.getShkcQz(product_id,"2");							
						}
						int cknums = bxfhdProduct.getNums();  //Ҫ��������
						if(cknums>kcnums){
							msg += bxfhdProduct.getProduct_name() + " ��ǰ���Ϊ��" + kcnums + "  �޷����б��޷�������\n";
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
