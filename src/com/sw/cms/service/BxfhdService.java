package com.sw.cms.service;

import com.sw.cms.dao.BxfhdDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Bxfhd;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.ShSerialNumFlow;
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
  
  
  public Object getBxfhdById(String bxfhd_id)
  {
	 return bxfhdDao.getBxfhdById(bxfhd_id);
  }
  
  public Object getBxfhdProductById(String bxfhd_id)
  {
	  return bxfhdDao.getBxfhdProductById(bxfhd_id);
  }
  
  public void updateBxfhd(Bxfhd bxfhd,BxfhdProduct bxfhdProduct)
  {
	  bxfhdDao.updateBxfhd(bxfhd, bxfhdProduct);
	  if(bxfhd.getState().equals("�ѷ���"))
	  {
		  if(!bxfhdProduct.getQz_serial_num().equals(""))
		  {
			 
		       //�޸Ŀ��״̬Ϊ2���ü���
			   shkcDao.updateShkcState(bxfhdProduct.getQz_serial_num(), "2");
			   
			    ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
				shSerialNumFlow.setCj_date(DateComFunc.getToday());
				shSerialNumFlow.setClient_name(bxfhd.getClient_name());
				shSerialNumFlow.setFs_date(bxfhd.getFh_date());
				shSerialNumFlow.setJsr(bxfhd.getJxr());
				shSerialNumFlow.setKf("�ü���");
				shSerialNumFlow.setLinkman(bxfhd.getLxr());
				shSerialNumFlow.setQz_serial_num(bxfhdProduct.getQz_serial_num());
				shSerialNumFlow.setRk_date(DateComFunc.getToday());
				shSerialNumFlow.setYw_dj_id(bxfhd.getId());
				shSerialNumFlow.setYw_url("viewBxfhd.html?id=");
				shSerialNumFlow.setYwtype("���޷���");
				//������к���ת��¼
				shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
	      }
	  }
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
