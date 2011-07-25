package com.sw.cms.service;

import com.sw.cms.dao.BxdDAO;
import com.sw.cms.dao.BxfhdDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.BfdProduct;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Bxfhd;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Lsd;
import com.sw.cms.model.LsdProduct;
import com.sw.cms.model.Product;
import com.sw.cms.model.Shkc;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.model.Xsd;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BxdService {
	private BxdDAO bxdDao;
	private ShkcDAO shkcDao;
	private ShSerialNumFlowDAO shSerialNumFlowDao;
	private BxfhdDAO bxfhdDao;

	
	public Page getBxdList(String con, int curPage, int rowsPerPage) {
		return bxdDao.getBxdList(con, curPage, rowsPerPage);
	}

	public String updateBxdId() {
		return bxdDao.getBxdId();
	}
	
	
	public void saveBxd(Bxd bxd,List bxdProducts)
	{//		 ������޵����ύ����������
		if(bxdDao.isBxdSubmit(bxd.getId())){
			return;
		}
		bxdDao.saveBxd(bxd, bxdProducts);
		if(bxd.getState().equals("���ύ"))
		{
			   //�޸Ŀ��״̬Ϊ2�������		
			this.addBxd(bxd,bxdProducts);
			if(bxdProducts != null && bxdProducts.size()>0)
			{
				for(int i=0;i<bxdProducts.size();i++)
				{
					BxdProduct bxdProduct = (BxdProduct)bxdProducts.get(i);
					if(bxdProduct != null)
					{
						if(!bxdProduct.getProduct_id().equals("") && !bxdProduct.getProduct_name().equals(""))
						{
							if(!bxdProduct.getQz_serial_num().equals(""))
							{
						    	String[] arryNums = (bxdProduct.getQz_serial_num()).split(",");
							
							    for(int k=0;k<arryNums.length;k++)
						        {	
                                  shkcDao.updateShkcState(arryNums[k], "2");
						        }
							}
							else
							{
								Shkc shkc = (Shkc)shkcDao.getShkc(bxdProduct.getProduct_id(),"1");
								if((shkc.getNums()-bxdProduct.getNums())==0)
								{
								    shkcDao.updateShkcStateAll(bxdProduct.getProduct_id(),bxdProduct.getNums(),"2","1");
								}
								else
								{
									shkcDao.updateShkcNums(bxdProduct.getProduct_id(),bxdProduct.getNums(),"2","1");
									shkcDao.updateShkcStateNums(bxdProduct.getProduct_id(),bxdProduct.getNums(),"2","1");
								}
								
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * �жϿ�����Ƿ����㱨�޴���
	 * @param bxd
	 * @param bxdProducts
	 */
	public String checkKc(Bxd bxd,List bxdProducts){
		String msg = "";
		
		if(bxdProducts != null && bxdProducts.size()>0){
			for(int i=0;i<bxdProducts.size();i++){
				BxdProduct bxdProduct = (BxdProduct)bxdProducts.get(i);
				if(bxdProduct != null){
					if(!bxdProduct.getProduct_id().equals("") && !bxdProduct.getProduct_name().equals("")){
						String product_id = bxdProduct.getProduct_id();
						
						//���п�������ж�
						int kcnums;
						if(bxdProduct.getQz_serial_num().equals("")){
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
						
						
						int cknums = bxdProduct.getNums();  //Ҫ��������
														
						if(cknums>kcnums){
								msg += bxdProduct.getProduct_name() + " ��ǰ���Ϊ��" + kcnums + "  �޷����б��޴���\n";
						}						
					}
				}
			}
		}
		
		return msg;
	}
	
	/**
	 * 
	 * <p>�����ǿ�����к������Ӧ���к���Ϣ</p?
	 * @param bxd
	 * @param bxdProducts
	 */
	private void addBxd(Bxd bxd,List bxdProducts)
	{
		if(bxdProducts != null && bxdProducts.size()>0)
		{
			for(int i=0;i<bxdProducts.size();i++)
			{
				BxdProduct bxdProduct = (BxdProduct)bxdProducts.get(i);
				if(bxdProduct != null)
				{
					if(!bxdProduct.getProduct_id().equals("") && !bxdProduct.getProduct_name().equals(""))
					{				
						//ֻ����ϵͳ��ʽʹ�ú��ȥ�޸���Ʒ�Ŀ��ʹ������к�
						//ϵͳ����ǰҲ��������Ʒ���кţ�����Ӳ��ǿ�ƣ�������������к�ϵͳ������
						
						//if(sysInitSetDao.getQyFlag().equals("1")){
						//ǿ�����кŴ���
						
						if((bxdProduct.getQz_serial_num() != null) && (!bxdProduct.getQz_serial_num().equals("")))
						{
							String[] arryNums = (bxdProduct.getQz_serial_num()).split(",");
							
							ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
							
							for(int k=0;k<arryNums.length;k++)
							{
								shSerialNumFlow.setCj_date(DateComFunc.getToday());			
								shSerialNumFlow.setFs_date(bxd.getBxdate());
								shSerialNumFlow.setJsr(bxd.getJsr());
								shSerialNumFlow.setKf("�����");
								shSerialNumFlow.setQz_serial_num(arryNums[k]);
								shSerialNumFlow.setRk_date(DateComFunc.getToday());
								shSerialNumFlow.setYw_dj_id(bxd.getId());
								shSerialNumFlow.setYw_url("viewBxd.html?id=");
								shSerialNumFlow.setYwtype("����");
								shSerialNumFlow.setClient_name(bxd.getBxcs());
								
//								������к���ת��¼
								shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
							}
						}
					}
				}
			}
		}
	}
	
	
	public Object getBxd(String bxd_id)
	{
		return bxdDao.getBxd(bxd_id);
	}
	
	public List getBxdProducts(String bxd_id)
	{
		return bxdDao.getBxdProducts(bxd_id);
	}
	
	public void updateBxd(Bxd bxd,List bxdProducts)
	{
		bxdDao.updateBxd(bxd, bxdProducts);
		if(bxd.getState().equals("���ύ"))
		{
		       //�޸Ŀ��״̬Ϊ2�������
			this.addBxd(bxd,bxdProducts);
			if(bxdProducts != null && bxdProducts.size()>0)
			{
				for(int i=0;i<bxdProducts.size();i++)
				{
					BxdProduct bxdProduct = (BxdProduct)bxdProducts.get(i);
					if(bxdProduct != null)
					{
						if(!bxdProduct.getProduct_id().equals("") && !bxdProduct.getProduct_name().equals(""))
						{  
							if((bxdProduct.getQz_serial_num() != null) && (!bxdProduct.getQz_serial_num().equals("")))
						   {
                              String[] arryNums = (bxdProduct.getQz_serial_num()).split(",");
							   
							  for(int k=0;k<arryNums.length;k++)
						      {
                                shkcDao.updateShkcState(arryNums[k], "2");
						      } 
						    }
							else
							{
								Shkc shkc = (Shkc)shkcDao.getShkc(bxdProduct.getProduct_id(),"1");
								if((shkc.getNums()-bxdProduct.getNums())==0)
								{
								    shkcDao.updateShkcStateAll(bxdProduct.getProduct_id(),bxdProduct.getNums(),"2","1");
								}
								else
								{
									shkcDao.updateShkcNums(bxdProduct.getProduct_id(),bxdProduct.getNums(),"2","1");
									shkcDao.updateShkcStateNums(bxdProduct.getProduct_id(),bxdProduct.getNums(),"2","1");
								}
								
							}
							
						}
					}
				}
			}			
		}
	}
	
	public void delBxd(String bxd_id)
	{
		bxdDao.delBxd(bxd_id);
	}
	
	/**
	 * ���������Ƿ��и����к�
	 * @param bxdProducts
	 * @return
	 */
	public String isZyShkcExist(Bxd bxd,List bxdProducts)
	{		
		String message="";
		String serials="";
		String serialNumStr[]=getSerialNum(bxdProducts).toString().split(",");	
		if(!serialNumStr.equals(""))
		{
			
			for(int i=0;i<serialNumStr.length;i++)
			{
			  if(!serialNumStr[i].equals(""))
			  {
				  int count=shkcDao.getWeiShkcBySerialNum(serialNumStr[i]);
				  if(count!=0)
			      {
					  serials+=serialNumStr[i]+" ";					 
				  }
			    }
		    }
		}	
		if(!serials.equals(""))
		{
			 message+="�����к�:"+serials+" ����Ʒ�Ѿ�����,���飡";
		}
		
		return message;
	}

	/**
	 * ��黵�����Ƿ��и����к�
	 * @param bxdProducts
	 * @return
	 */
	public String isBadShkcExist(Bxd bxd,List bxdProducts)
	{		
		String message="";
		String serials="";
		String serialNumStr[]=getSerialNum(bxdProducts).toString().split(",");	
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
	
	public String getSerialNum(List bxdProducts)
	{
		 
		String serialNumList="";
		if(bxdProducts!=null&&bxdProducts.size()>0)
		{
		   for(int i=0;i<bxdProducts.size();i++)
		  {
			  BxdProduct bxdProduct = (BxdProduct)bxdProducts.get(i);
			 if(bxdProduct!=null)
			 {
				if(!bxdProduct.getProduct_name().equals("")&&!bxdProduct.getProduct_id().equals(""))
				{	
					if(!bxdProduct.getQz_serial_num().equals(""))
					{
			          String serialNum[]=bxdProduct.getQz_serial_num().toString().split(",");
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

	public BxdDAO getBxdDao() {
		return bxdDao;
	}

	public void setBxdDao(BxdDAO bxdDao) {
		this.bxdDao = bxdDao;
	}

	public ShkcDAO getShkcDao() {
		return shkcDao;
	}

	public void setShkcDao(ShkcDAO shkcDao) {
		this.shkcDao = shkcDao;
	}

	public BxfhdDAO getBxfhdDao() {
		return bxfhdDao;
	}

	public void setBxfhdDao(BxfhdDAO bxfhdDao) {
		this.bxfhdDao = bxfhdDao;
	}
}
