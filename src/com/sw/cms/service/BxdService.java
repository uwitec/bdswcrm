package com.sw.cms.service;

import com.sw.cms.dao.BxdDAO;
import com.sw.cms.dao.BxfhdDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Bxfhd;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Lsd;
import com.sw.cms.model.LsdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.ShSerialNumFlow;
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
	{
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
                          shkcDao.updateShkcState(bxdProduct, "2");
						}
					}
				}
			}
		}
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
						//ֻ����ϵͳ��ʽʹ�ú��ȥ�޸Ĳ�Ʒ�Ŀ��ʹ������к�
						//ϵͳ����ǰҲ�������Ʒ���кţ�����Ӳ��ǿ�ƣ�������������к�ϵͳ������
						
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
								shSerialNumFlow.setQz_serial_num(bxdProduct.getQz_serial_num());
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
                          shkcDao.updateShkcState(bxdProduct, "2");
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
	public boolean isZyShkcExist(Bxd bxd,List bxdProducts)
	{
		boolean is = false;
		String message="";
		if(bxdProducts != null && bxdProducts.size()>0)
		{
			for(int i=0;i<bxdProducts.size();i++)
			{
				BxdProduct bxdProduct = (BxdProduct)bxdProducts.get(i);
				if(!bxdProduct.getQz_serial_num().equals(""))
			    {
				  int count=shkcDao.getWeiShkcBySerialNum(bxdProduct.getQz_serial_num());
				  if(count==0)
			     {
					message="�������������к�:"+bxdProduct.getQz_serial_num()+" ����Ʒ,���飡";
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
