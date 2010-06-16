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
	 * 取会员卡列表
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
	 * 保存会员卡信息
	 * @param info
	 */
	public void saveHykzz(Hykzz info,String nums,String zzfs){
//		 如果会员卡已提交，不做处理
		if(hykzzDao.isHykzzSubmit(info.getId())){
			return;
		}
		if(zzfs.equals("单个增加"))
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
	 * 更新会员卡信息
	 * @param info
	 */
	public void updateHykzz(Hykzz info){
		hykzzDao.updateHykzz(info);
	}
	
	
	/**
	 * 根据ID取会员卡信息
	 * @param id
	 * @return
	 */
	public Hykzz getHykzz(String id){
		return hykzzDao.getHykzz(id);
	}
	
	/**
	 * 根据ID取会员卡档案信息
	 * @param id
	 * @return
	 */
	public Hykzz getHykzzda(String hykh){
		return hykzzDao.getHykzzda(hykh);
	}
	
	/**
	 * 删除会员卡信息
	 * @param id
	 */
	public void delHykzz(String hykh){
		hykzzDao.delHykzz(hykh);
		hykdaDao.delHykda(hykh);
	}
	
	 /**
	 * 取所有会员卡分类列表
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
