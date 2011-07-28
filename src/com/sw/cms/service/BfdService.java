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
//		 ������ϵ����ύ����������
		if(bfdDao.isBfdSubmit(bfd.getId())){
			return;
		}
		bfdDao.saveBfd(bfd, bfdProducts);
		if(bfd.getState().equals("���ύ"))
		{
			   //�޸Ŀ��״̬Ϊ4������		
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
	 * <p>�����ǿ�����к������Ӧ���к���Ϣ</p?
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
						//ֻ����ϵͳ��ʽʹ�ú��ȥ�޸���Ʒ�Ŀ��ʹ������к�
						//ϵͳ����ǰҲ��������Ʒ���кţ�����Ӳ��ǿ�ƣ�������������к�ϵͳ������
						
						//if(sysInitSetDao.getQyFlag().equals("1")){
						//ǿ�����кŴ���
						
						if((bfdProduct.getQz_serial_num() != null) && (!bfdProduct.getQz_serial_num().equals("")))
						{
							String[] arryNums = (bfdProduct.getQz_serial_num()).split(",");
							
							ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
							
							for(int k=0;k<arryNums.length;k++)
							{
								shSerialNumFlow.setCj_date(DateComFunc.getToday());			
								shSerialNumFlow.setFs_date(bfd.getBf_date());
								shSerialNumFlow.setJsr(bfd.getJsr());
								shSerialNumFlow.setKf("������");
								shSerialNumFlow.setQz_serial_num(bfdProduct.getQz_serial_num());
								shSerialNumFlow.setRk_date(DateComFunc.getToday());
								shSerialNumFlow.setYw_dj_id(bfd.getId());
								shSerialNumFlow.setYw_url("viewBfd.html?id=");
								shSerialNumFlow.setYwtype("����");
								
//								������к���ת��¼
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
		if(bfd.getState().equals("���ύ"))
		{
		       //�޸Ŀ��״̬Ϊ4������
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
	 * �жϿ�����Ƿ����㱨�ϴ���
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
						
						//���п�������ж�
						int kcnums;
						if(bfdProduct.getQz_serial_num().equals("")){
						  Shkc shkc = (Shkc)shkcDao.getShkc(product_id,"1");
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
							kcnums = shkcDao.getShkcQz(product_id,"1");						    
						}
							int cknums = bfdProduct.getNums();  //Ҫ��������		
							
							if(cknums>kcnums){
								msg += bfdProduct.getProduct_name() + " ��ǰ���Ϊ��" + kcnums + "  �޷����б��ϴ���\n";
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
	 * ��鱨�����Ƿ��и����к�
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
			message="�����к�:"+serials+" ����Ʒ�Ѿ�����,���飡";
		}
		return message;
	}

	/**
	 * ��黵�����Ƿ��и����к�
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
			 message+="�����к�:"+serials+" ����Ʒδ�ڻ�������,���飡";
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
	 * �������б�����ҳ��
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
