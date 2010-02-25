package com.sw.cms.service;

import com.sw.cms.dao.HjdDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Hjd;
import com.sw.cms.model.HjdProduct;

import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HjdService {
	private HjdDAO hjdDao;
	private ShkcDAO shkcDao;
	private ShSerialNumFlowDAO shSerialNumFlowDao;
		
	public Page getHjdList(String con, int curPage, int rowsPerPage) {
		return hjdDao.getHjdList(con, curPage, rowsPerPage);
	}

	public String updateHjdId() {
		return hjdDao.getHjdId();
	}
	
	
	public void saveHjd(Hjd hjd,List hjdProducts)
	{
		hjdDao.saveHjd(hjd, hjdProducts);
		if(hjd.getState().equals("���ύ"))
		{  	
			this.addHjd(hjd,hjdProducts);
            //�޸��ۺ����е����к�Ϊ�����к�	
			if(hjdProducts != null && hjdProducts.size()>0)
			{
				for(int i=0;i<hjdProducts.size();i++)
				{
					HjdProduct hjdProduct = (HjdProduct)hjdProducts.get(i);
					if(hjdProduct != null)
					{
						if(!hjdProduct.getProduct_id().equals("") && !hjdProduct.getProduct_name().equals(""))
						{
			                shkcDao.updateHjShkcState(hjdProduct);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * <p>�����ǿ�����к������Ӧ���к���Ϣ</p?
	 * @param hjd
	 * @param hjdProducts
	 */
	private void addHjd(Hjd hjd,List hjdProducts)
	{
		if(hjdProducts != null && hjdProducts.size()>0)
		{
			for(int i=0;i<hjdProducts.size();i++)
			{
				HjdProduct hjdProduct = (HjdProduct)hjdProducts.get(i);
				if(hjdProduct != null)
				{
					if(!hjdProduct.getProduct_id().equals("") && !hjdProduct.getProduct_name().equals(""))
					{				
						//ֻ����ϵͳ��ʽʹ�ú��ȥ�޸Ĳ�Ʒ�Ŀ��ʹ������к�
						//ϵͳ����ǰҲ�������Ʒ���кţ�����Ӳ��ǿ�ƣ�������������к�ϵͳ������
						
						//if(sysInitSetDao.getQyFlag().equals("1")){
						//ǿ�����кŴ���
						
						if((hjdProduct.getNqz_serial_num() != null) && (!hjdProduct.getNqz_serial_num().equals("")))
						{
							String[] arryNums = (hjdProduct.getNqz_serial_num()).split(",");
							
							ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
							
							for(int k=0;k<arryNums.length;k++)
							{
								shSerialNumFlow.setCj_date(DateComFunc.getToday());			
								shSerialNumFlow.setFs_date(hjd.getHj_date());
								shSerialNumFlow.setJsr(hjd.getJsr());
								shSerialNumFlow.setKf("�ü���");
								shSerialNumFlow.setQz_serial_num(arryNums[k]);
								shSerialNumFlow.setRk_date(DateComFunc.getToday());
								shSerialNumFlow.setYw_dj_id(hjd.getId());
								shSerialNumFlow.setYw_url("viewHjd.html?id=");
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
	
	
	public Object getHjd(String hjd_id)
	{
		return hjdDao.getHjd(hjd_id);
	}
	
	public List getHjdProducts(String hjd_id)
	{
		return hjdDao.getHjdProducts(hjd_id);
	}
	
	public void updateHjd(Hjd hjd,List hjdProducts)
	{
		hjdDao.updateHjd(hjd, hjdProducts);
		if(hjd.getState().equals("���ύ"))
		{
		       
			this.addHjd(hjd,hjdProducts);
            //�޸��ۺ����е����к�Ϊ�����к�
			if(hjdProducts != null && hjdProducts.size()>0)
			{
				for(int i=0;i<hjdProducts.size();i++)
				{
					HjdProduct hjdProduct = (HjdProduct)hjdProducts.get(i);
					if(hjdProduct != null)
					{
						if(!hjdProduct.getProduct_id().equals("") && !hjdProduct.getProduct_name().equals(""))
						{
			              shkcDao.updateHjShkcState(hjdProduct);
						}
					}
				}
			}
		}
	}
	
	public void delHjd(String hjd_id)
	{
		hjdDao.delHjd(hjd_id);
	}
	
	/**
	 * ���ü����Ƿ��и����к�
	 * @param bxdProducts
	 * @return
	 */
	public boolean isHaoShkcExist(Hjd hjd,List hjdProducts)
	{
		boolean is = false;
		String message="";
		if(hjdProducts != null && hjdProducts.size()>0)
		{
			for(int i=0;i<hjdProducts.size();i++)
			{
				HjdProduct hjdProduct = (HjdProduct)hjdProducts.get(i);
				if(!hjdProduct.getNqz_serial_num().equals(""))
			    {
				  int count=shkcDao.getHaoShkcBySerialNum(hjdProduct.getNqz_serial_num());
				  if(count==0)
			     {
					message="�ü����������к�:"+hjdProduct.getNqz_serial_num()+" ����Ʒ,���飡";
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

	public HjdDAO getHjdDao() {
		return hjdDao;
	}

	public void setHjdDao(HjdDAO hjdDao) {
		this.hjdDao = hjdDao;
	}

	public ShkcDAO getShkcDao() {
		return shkcDao;
	}

	public void setShkcDao(ShkcDAO shkcDao) {
		this.shkcDao = shkcDao;
	}
}
