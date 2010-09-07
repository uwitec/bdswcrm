package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.JjdDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Jjd;
import com.sw.cms.model.JjdProduct;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.KfdbProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.model.Shkc;
import com.sw.cms.util.DateComFunc;

public class JjdService {
	private JjdDAO jjdDao;
	private ShkcDAO shkcDao;
	private ShSerialNumFlowDAO shSerialNumFlowDao;
	private ProductDAO productDao;
	private ProductKcDAO productKcDao;
	
	public ShSerialNumFlowDAO getShSerialNumFlowDao() {
		return shSerialNumFlowDao;
	}

	public void setShSerialNumFlowDao(ShSerialNumFlowDAO shSerialNumFlowDao) {
		this.shSerialNumFlowDao = shSerialNumFlowDao;
	}

	/**
	 * ��ȡ�Ӽ����б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getJjdList(String con, int curPage, int rowsPerPage) 
	{
      return jjdDao.getJjdList(con, curPage, rowsPerPage);
	} 
	
	/**
	 * ��ȡ�Ӽ���ID
	 * @return
	 */
	public String updateJjdId()
	{
		return jjdDao.getJjdId();
	}
	
	/**
	 * ����Ӽ�����Ϣ
	 * @param jjd
	 * @param jjdProduct
	 */
	public void saveJjd(Jjd jjd,List jjdProducts)
	{
		jjdDao.saveJjd(jjd, jjdProducts);
		if(jjd.getState().equals("���ύ"))
		{
			saveShkc(jjd,jjdProducts);//�ѽӼ�����Ʒ���浽�ۺ��沢�ұ����ۺ����к���ת��¼
			shkcDao.insertJjShkcState(jjdProducts, "1");			
		}
	}
	
	/**
	 * �޸ĽӼ�����Ϣ
	 * @param jjd
	 * @param jjdProduct
	 */
	public void updateJjd(Jjd jjd,List jjdProducts)
	{
		jjdDao.updateJjd(jjd, jjdProducts);
		if(jjd.getState().equals("���ύ"))
		{
			saveShkc(jjd,jjdProducts);//�ѽӼ�����Ʒ���浽�ۺ��沢�ұ����ۺ����к���ת��¼
			shkcDao.insertJjShkcState(jjdProducts, "1");
		}
	}
	/**
	 * �ѽӼ�����Ʒ���浽�ۺ��沢�ұ����ۺ����к���ת��¼
	 * @param jjd
	 * @param jjdProducts
	 */
	public void saveShkc(Jjd jjd,List jjdProducts)
	{
		if(jjdProducts!=null&&jjdProducts.size()>0)
		   {
			   for(int i=0;i<jjdProducts.size();i++)
			   {
				   JjdProduct  jjdProduct=(JjdProduct)jjdProducts.get(i);
				   if(jjdProduct!=null)
				   {
					   if(!jjdProduct.getProduct_id().equals("") && !jjdProduct.getProduct_name().equals(""))
						{				
							//ֻ����ϵͳ��ʽʹ�ú��ȥ�޸���Ʒ�Ŀ��ʹ������к�
							//ϵͳ����ǰҲ��������Ʒ���кţ�����Ӳ��ǿ�ƣ�������������к�ϵͳ������
							
							//if(sysInitSetDao.getQyFlag().equals("1")){
							//ǿ�����кŴ���
							
							if((jjdProduct.getQz_serial_num() != null) && (!jjdProduct.getQz_serial_num().equals("")))
							{
								String[] arryNums = (jjdProduct.getQz_serial_num()).split(",");
								
								ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();//���к���ת��¼
								
								for(int k=0;k<arryNums.length;k++)
							   {
								 
							     shSerialNumFlow.setClient_name(jjd.getClient_name());
							     shSerialNumFlow.setLinkman(jjd.getLinkman());
							     shSerialNumFlow.setCj_date(DateComFunc.getToday());
							     shSerialNumFlow.setFs_date(jjd.getJj_date());
							     shSerialNumFlow.setJsr(jjd.getJjr());
							     shSerialNumFlow.setKf("������");
							     shSerialNumFlow.setQz_serial_num(arryNums[k]);
							     shSerialNumFlow.setRk_date(DateComFunc.getToday());
							     shSerialNumFlow.setYw_dj_id(jjd.getId());
							     shSerialNumFlow.setYw_url("viewJjd.html?id=");
							     shSerialNumFlow.setYwtype("�Ӽ�");
							     shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
							   }
						    }
					   }
					   
				   }
			   }
		   }
	}
	
	 
	/**
	 * ��ȡ���к�
	 * @param jjdProducts
	 * @return
	 */
	public String getSerialNum(List jjdProducts)
	{
		 
		String serialNumList="";
		if(jjdProducts!=null&&jjdProducts.size()>0)
		{
		   for(int i=0;i<jjdProducts.size();i++)
		  {
			JjdProduct jjdProduct=(JjdProduct)jjdProducts.get(i);
			 if(jjdProduct!=null)
			 {
				if(!jjdProduct.getProduct_name().equals("")&&!jjdProduct.getQz_serial_num().equals(""))
				{
			     int count=jjdProduct.getNums();
			     if(count>1)
			     {
			    	 String serialNum[]=jjdProduct.getQz_serial_num().toString().split(",");
				    for(int j=0;j<count;j++)
				    {
				    	serialNumList+=serialNum[j]+",";
				    }
		 	     }
			     else
			     {
				   serialNumList+=jjdProduct.getQz_serial_num()+",";
			     }
				}
			 }
		  }
		}
		return serialNumList;
	}
	/**
	 * �ж��ύ�ĽӼ�����Ʒ������к���û���ظ���
	 * @param jjdProducts
	 */
	public String isSerialNumExist(List jjdProducts)	
	{
		String message="";
		boolean flag=false;
		String serialNumList=getSerialNum(jjdProducts);
	    
		if(!serialNumList.equals(""))
		{
			String[]serialNumArray=serialNumList.split(",");
			 
			for(int i=0;i<serialNumArray.length;i++)
			{				 
				for(int j=i+1;j<serialNumArray.length;j++)
				{
					if(serialNumArray[i].equals(serialNumArray[j]))
					{
						flag=true;
						message="���ύ�ĽӼ�����Ʒ���к����ظ�������!";
						break;
					}
				}
			}
		}
		return message;
	}
	/**
	 * �ж��ύ�ĽӼ�����Ʒ�Ƿ���ά�޿����ӵ��
	 * @param jjdProducts
	 * @return
	 */
	public String isSerialNumInKcExist(List jjdProducts)
	{
		String message="";
		if(!getSerialNum(jjdProducts).toString().equals(""))
		{
		  String serialNumStr[]=getSerialNum(jjdProducts).toString().split(",");	
		 
		  if(serialNumStr.length!=0)
		  {
			
			for(int i=0;i<serialNumStr.length;i++)
			{
			   int count=shkcDao.getShkcBySerialNum(serialNumStr[i]);
			   if(count>0)
			   {
				   message="�ύ�ĽӼ�����Ʒ���к������ۺ�����ӵ�У����飡";
				   break;
			   }
			}
		  }
		}
		 
		return message;
	}
	
		
	
	public List getJjdProducts(String id)
	{
		return jjdDao.getJjdProducts(id);
	}
	
	public Object getJjd(String id)
	{
		return jjdDao.getJjd(id);
	}
	
	public void delJjd(String id)
	{
	  	jjdDao.delJjd(id);
	}

	public JjdDAO getJjdDao() {
		return jjdDao;
	}

	public void setJjdDao(JjdDAO jjdDao) {
		this.jjdDao = jjdDao;
	}

	public ShkcDAO getShkcDao() {
		return shkcDao;
	}

	public void setShkcDao(ShkcDAO shkcDao) {
		this.shkcDao = shkcDao;
	}
}
