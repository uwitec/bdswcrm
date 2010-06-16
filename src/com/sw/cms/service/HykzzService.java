package com.sw.cms.service;

import java.util.*;
import com.sw.cms.dao.HykzzDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Hykzz;
import com.sw.cms.model.Wxcld;
import com.sw.cms.util.StringUtils;
import com.sw.cms.dao.HykdaDAO;
import com.sw.cms.model.Hykda;
public class HykzzService {
	
	private HykzzDAO hykzzDao;
	private HykdaDAO hykdaDao;
	
	/**
	 * ȡ��Ա���б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykzzList(int curPage, int rowsPerPage,String con){
		return hykzzDao.getHykzzList(curPage, rowsPerPage,con);
	}
	
	
	public String updateHykzzId() {
		return hykzzDao.getHykzzId();
	}
	
	/**
	 * �����Ա����Ϣ
	 * @param info
	 */
	public void saveHykzz(Hykzz info,String nums,String zzfs){
//		 �����Ա�����ύ����������
		if(hykzzDao.isHykzzSubmit(info.getId())){
			return;
		}
		if(zzfs.equals("��������"))
		{
		   if(hykzzDao.isHykzzHykhSubmit(info.getHykh()))
		   {
			   return;
		   }
		   hykzzDao.saveHykzz(info);
		   
		   Hykda hykda=new Hykda();
		   if(hykdaDao.isHykdaSubmit(info.getHykh())){
				return;
			}    		 
		   hykda.setId(hykdaDao.getHykdaId());
		   hykda.setHykh(info.getHykh());
		   hykda.setCzr(info.getCzr());
		   hykdaDao.saveHykda(hykda);
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
				  
				  info.setId(hykzzDao.getHykzzId());
				  info.setHykh(hykh);
			  }
			  if(hykzzDao.isHykzzHykhSubmit(info.getHykh()))
			   {
				   return;
			   }
			  hykzzDao.saveHykzz(info);	
			  Hykda hykda=new Hykda();
			   if(hykdaDao.isHykdaSubmit(info.getHykh())){
					return;
				}    		 
			   hykda.setId(hykdaDao.getHykdaId());
			   hykda.setHykh(info.getHykh());
			   hykda.setCzr(info.getCzr());
			   hykdaDao.saveHykda(hykda);
		   }
		}
		
	}
	
	
	/**
	 * ���»�Ա����Ϣ
	 * @param info
	 */
	public void updateHykzz(Hykzz info){
		hykzzDao.updateHykzz(info);
	}
	
	
	/**
	 * ����IDȡ��Ա����Ϣ
	 * @param id
	 * @return
	 */
	public Hykzz getHykzz(String id){
		return hykzzDao.getHykzz(id);
	}
	
	/**
	 * ����IDȡ��Ա��������Ϣ
	 * @param id
	 * @return
	 */
	public Hykzz getHykzzda(String hykh){
		return hykzzDao.getHykzzda(hykh);
	}
	
	/**
	 * ɾ����Ա����Ϣ
	 * @param id
	 */
	public void delHykzz(String hykh){
		hykzzDao.delHykzz(hykh);
		hykdaDao.delHykda(hykh);
	}
	
	 /**
	 * ȡ���л�Ա�������б�
	 * @return
	 */
	public List getAllHykflList(){
		return hykzzDao.getAllHykflList();
	}
	
	public HykzzDAO getHykzzDao() {
		return hykzzDao;
	}

	public void setHykzzDao(HykzzDAO hykzzDao) {
		this.hykzzDao = hykzzDao;
	}
	
	public HykdaDAO getHykdaDao() {
		return hykdaDao;
	}

	public void setHykdaDao(HykdaDAO hykdaDao) {
		this.hykdaDao = hykdaDao;
	}
}
