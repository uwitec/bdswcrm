package com.sw.cms.service;

import com.sw.cms.dao.BxdDAO;
import com.sw.cms.dao.BxfhdDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Bxfhd;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.util.DateComFunc;

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
	
	
	public void saveBxd(Bxd bxd,BxdProduct bxdProduct)
	{
		bxdDao.saveBxd(bxd, bxdProduct);
		if(bxd.getState().equals("已提交"))
		{
			if(!bxdProduct.getProduct_serial_num().equals(""))
			{
		       //修改库存状态为1：在外库
			   shkcDao.updateShkcState(bxdProduct.getProduct_serial_num(), "1");
			}
			ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
			shSerialNumFlow.setCj_date(DateComFunc.getToday());
			shSerialNumFlow.setClient_name(bxd.getClient_name());
			shSerialNumFlow.setFs_date(bxd.getJxdate());
			shSerialNumFlow.setJsr(bxd.getJxr());
			shSerialNumFlow.setKf("在外库");
			shSerialNumFlow.setLinkman(bxd.getLxr());
			shSerialNumFlow.setQz_serial_num(bxdProduct.getProduct_serial_num());
			shSerialNumFlow.setRk_date(DateComFunc.getToday());
			shSerialNumFlow.setYw_dj_id(bxd.getId());
			shSerialNumFlow.setYw_url("viewBxd.html?id=");
			shSerialNumFlow.setYwtype("报修");
			//添加序列号流转记录
			shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
			//生成报修返还单
			Bxfhd bxfhd=new Bxfhd();
			bxfhd.setId(bxfhdDao.updateBxfhId());
			bxfhd.setAddress(bxd.getAddress());
			bxfhd.setBxd_id(bxd.getId());
			bxfhd.setBxr(bxd.getJxr());
			bxfhd.setCj_date(DateComFunc.getToday());
			bxfhd.setCjr(bxd.getCjr());
			bxfhd.setClient_name(bxd.getClient_name());
			bxfhd.setGcs(bxd.getGcs());
			bxfhd.setLxdh(bxd.getLxdh());
			bxfhd.setLxr(bxd.getLxr());
			bxfhd.setMs("报修单编号["+bxd.getId()+"]");
			bxfhd.setState("待返还");
			
			BxfhdProduct bxfhdProduct=new BxfhdProduct();
			bxfhdProduct.setBj(bxdProduct.getBj());
			bxfhdProduct.setBxaddress(bxdProduct.getBxaddress());
			bxfhdProduct.setBxfhd_id(bxfhd.getId());
			bxfhdProduct.setBxstate(bxdProduct.getBxstate());
			bxfhdProduct.setFj(bxdProduct.getFj());
			bxfhdProduct.setGzfx(bxdProduct.getGzfx());
			bxfhdProduct.setProduct_id(bxdProduct.getProduct_id());
			bxfhdProduct.setProduct_name(bxdProduct.getProduct_name());
			bxfhdProduct.setProduct_xh(bxdProduct.getProduct_xh());
			bxfhdProduct.setQtfj(bxdProduct.getQtfj());
			bxfhdProduct.setQz_serial_num(bxdProduct.getProduct_serial_num());
			bxfhdProduct.setRemark(bxdProduct.getProduct_remark());
			
			bxfhdDao.saveBxfhd(bxfhd, bxfhdProduct) ;
		}
	}
	
	public Object getBxd(String bxd_id)
	{
		return bxdDao.getBxd(bxd_id);
	}
	
	public Object getBxdProduct(String bxd_id)
	{
		return bxdDao.getBxdProduct(bxd_id);
	}
	
	public void updateBxd(Bxd bxd,BxdProduct bxdProduct)
	{
		bxdDao.updateBxd(bxd, bxdProduct);
		if(bxd.getState().equals("已提交"))
		{
			if(!bxdProduct.getProduct_serial_num().equals(""))
			{
		       //修改库存状态为1：在外库
			   shkcDao.updateShkcState(bxdProduct.getProduct_serial_num(), "1");
			}
			ShSerialNumFlow  shSerialNumFlow=new ShSerialNumFlow();
			shSerialNumFlow.setCj_date(DateComFunc.getToday());
			shSerialNumFlow.setClient_name(bxd.getClient_name());
			shSerialNumFlow.setFs_date(bxd.getJxdate());
			shSerialNumFlow.setJsr(bxd.getJxr());
			shSerialNumFlow.setKf("在外库");
			shSerialNumFlow.setLinkman(bxd.getLxr());
			shSerialNumFlow.setQz_serial_num(bxdProduct.getProduct_serial_num());
			shSerialNumFlow.setRk_date(DateComFunc.getToday());
			shSerialNumFlow.setYw_dj_id(bxd.getId());
			shSerialNumFlow.setYw_url("viewBxd.html?id=");
			shSerialNumFlow.setYwtype("报修");
			//添加序列号流转记录
			shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
			//生成报修返还单
			Bxfhd bxfhd=new Bxfhd();
			bxfhd.setId(bxfhdDao.updateBxfhId());
			bxfhd.setAddress(bxd.getAddress());
			bxfhd.setBxd_id(bxd.getId());
			bxfhd.setBxr(bxd.getJxr());
			bxfhd.setCj_date(DateComFunc.getCurDay());
			bxfhd.setCjr(bxd.getCjr());
			bxfhd.setClient_name(bxd.getClient_name());
			bxfhd.setGcs(bxd.getGcs());
			bxfhd.setLxdh(bxd.getLxdh());
			bxfhd.setLxr(bxd.getLxr());
			bxfhd.setMs("报修单编号["+bxd.getId()+"]");
			bxfhd.setState("待返还");
			
			BxfhdProduct bxfhdProduct=new BxfhdProduct();
			bxfhdProduct.setBj(bxdProduct.getBj());
			bxfhdProduct.setBxaddress(bxdProduct.getBxaddress());
			bxfhdProduct.setBxfhd_id(bxd.getId());
			bxfhdProduct.setBxstate(bxdProduct.getBxstate());
			bxfhdProduct.setFj(bxdProduct.getFj());
			bxfhdProduct.setGzfx(bxdProduct.getGzfx());
			bxfhdProduct.setProduct_id(bxdProduct.getProduct_id());
			bxfhdProduct.setProduct_name(bxdProduct.getProduct_name());
			bxfhdProduct.setProduct_xh(bxdProduct.getProduct_xh());
			bxfhdProduct.setQtfj(bxdProduct.getQtfj());
			bxfhdProduct.setQz_serial_num(bxdProduct.getProduct_serial_num());
			bxfhdProduct.setRemark(bxdProduct.getProduct_remark());
			
			bxfhdDao.saveBxfhd(bxfhd, bxfhdProduct) ;
		}
	}
	
	public void delBxd(String bxd_id)
	{
		bxdDao.delBxd(bxd_id);
	}
	
	/**
	 * 检查坏件库是否有该序列号
	 * @param bxdProduct
	 * @return
	 */
	public String isBadShkcExist(BxdProduct bxdProduct)
	{
		String message="";
		if(bxdProduct!=null)
		{
			if(!bxdProduct.getProduct_serial_num().equals(""))
			{
				int count=shkcDao.getBadShkcBySerialNum(bxdProduct.getProduct_serial_num());
				if(count==0)
				{
					message="坏件库已无序列号:"+bxdProduct.getProduct_serial_num()+" 的商品,请检查！";
				}
			}
		}
		
		return message;
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
