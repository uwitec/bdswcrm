package com.sw.cms.service;

import java.util.*;
import com.sw.cms.dao.HykzzDAO;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.model.Page;
import com.sw.cms.model.Hykzz;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.model.Hykda;
import com.sw.cms.dao.HykdaDAO;
import com.sw.cms.dao.ClientsLinkmanDAO;
public class HykdaService {
	
	private HykzzDAO hykzzDao;
	private HykdaDAO hykdaDao;
	private ClientsLinkmanDAO clientsLinkmanDao;
	private ClientsLinkman clientLinkman = new ClientsLinkman();
	private Hykda hykda=new Hykda();
	/**
	 * 取会员卡档案列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykdaList(int curPage, int rowsPerPage,String con){
		return hykdaDao.getHykdaList(curPage, rowsPerPage,con);
	}
	
	
	public String updateHykdaId() {
		return hykdaDao.getHykdaId();
	}
	
	
	/**
	 * 保存会员卡发卡信息
	 * @param info
	 */
	public void saveHykfk(Hykda info,String nums,String scfs,String fklx){

		if(scfs.equals("单个"))
		{
		   if(fklx.equals("到个人"))
		   {
			  info.setHymc(info.getLxrname());
		      hykdaDao.saveHykfk(info);
		   }
		   else
		   {
			   hykdaDao.saveHykfk(info);
		   }
		   
		}
		else
		{
		   String hykh=info.getHykh();
		   int hykhLength=hykh.length();
		   int hykhNums=Integer.parseInt(nums);
		   for(int i=0;i<hykhNums;i++)
		   {
			  if(i>0)
			  {   int hykh1=Integer.parseInt(hykh);
				  hykh1+=1;
				  hykh=Integer.toString(hykh1);
				  while(hykh.length()<hykhLength)
				  {
					  hykh="0"+hykh;
				  }
				  				  
				  info.setHykh(hykh);
			  }
			  if(fklx.equals("到个人"))
			   {
				  info.setHymc(info.getLxrname());
			      hykdaDao.saveHykfk(info);
			   }
			   else
			   {
				   hykdaDao.saveHykfk(info);
			   }		 
		   }
		}
		
	}
	
	/**
	 * 更新会员卡档案信息
	 * @param info
	 */
	public void updateHykda(Hykda info){
		
		hykdaDao.updateHykda(info);
	}
	
	/**
	 * 更新会员卡发卡信息
	 * @param info
	 */
	public void updateHykfk(Hykda info,String fklx){
	   if(fklx.equals("到个人"))
	   {
		  info.setHymc(info.getLxrname());
	   }
		
		  if(!info.getHybh().equals(""))
		  {
		   hykda= (Hykda) hykdaDao.getHykda(info.getHybh());
		   
		  if((!info.getHybh().equals(hykda.getHybh())) || (((info.getLxdh().equals(""))&& (info.getMobile().equals("")) && (info.getAddress().equals("")) && (info.getLxrname().equals("")) && (info.getMail().equals("")))))
		  {
		  clientLinkman = (ClientsLinkman) clientsLinkmanDao.getZClientsLinkman(info.getHybh());// 
		  info.setLxdh(clientLinkman.getGzdh());
		  info.setMobile(clientLinkman.getYddh());
		  info.setAddress(clientLinkman.getAddress());
		  info.setLxrname(clientLinkman.getName());
		  info.setMail(clientLinkman.getMail());
		  }
		
		 
		  hykdaDao.saveHykfk(info);
		}
		else
		{
		
		  hykdaDao.saveHykfk(info);
		}
	}
	
	/**
	 * 根据ID取会员卡档案信息
	 * @param hykh
	 * @return
	 */
	public Hykda getHykda(String hykh){
		return hykdaDao.getHykda(hykh);
	}
	
	 public Object getHykdaByHykh(String hykh)
	    {
	    	return hykdaDao.getHykdaByHykh(hykh);
	    }
	
	
	/**
	 * 取所有会员卡分类列表
	 * @return
	 */
	public List getAllHykflList(){
		return hykzzDao.getAllHykflList();
	}
	
	public HykdaDAO getHykdaDao() {
		return hykdaDao;
	}

	public void setHykdaDao(HykdaDAO hykdaDao) {
		this.hykdaDao = hykdaDao;
	}
	public ClientsLinkmanDAO getClientsLinkmanDao() {
		return clientsLinkmanDao;
	}

	public void setClientsLinkmanDao(ClientsLinkmanDAO clientsLinkmanDao) {
		this.clientsLinkmanDao = clientsLinkmanDao;
	}
}
