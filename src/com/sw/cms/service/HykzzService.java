package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.HykdaDAO;
import com.sw.cms.dao.HykzzDAO;
import com.sw.cms.model.Hykda;
import com.sw.cms.model.Hykzz;
import com.sw.cms.model.Page;
import com.sw.cms.util.UUIDGenerator;


public class HykzzService {

	private HykzzDAO hykzzDao;
	private HykdaDAO hykdaDao;

	/**
	 * 取会员卡列表
	 * 
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykzzList(int curPage, int rowsPerPage, String con) {
		return hykzzDao.getHykzzList(curPage, rowsPerPage, con);
	}

	/**
	 * 保存会员卡信息
	 * @param info
	 */
	public void saveHykzz(Hykzz info, int nums, String zzfs) {
		if (zzfs.equals("单个增加")) {
			
			//单个增加,直接添加制卡信息
			info.setState("未使用");
			info.setId(UUIDGenerator.getUUID());
			hykzzDao.saveHykzz(info);
			
		} else {
			
			//批量增加
			String hykh = info.getHykh();
			String[] arrayTemp = hykh.split("%");
			
			String strSeq = arrayTemp[1];
			int tempIntHykh = Integer.parseInt(strSeq);
			
			for (int i = 0; i < nums; i++) {
				 
				String tempStrHykh = tempIntHykh + "";
				for (int k = tempStrHykh.length(); k < arrayTemp[1].length(); k++) {
					tempStrHykh = "0" + tempStrHykh;
				}

				info.setId(UUIDGenerator.getUUID());
				info.setHykh(arrayTemp[0] + tempStrHykh);
				info.setState("未使用");
				hykzzDao.saveHykzz(info);
				
				tempIntHykh++;
			}
		}
	}

	/**
	 * 更新会员卡信息
	 * @param info
	 */
	public void updateHykzz(Hykzz info) {
		hykzzDao.updateHykzz(info);
	}

	/**
	 * 根据ID取会员卡信息
	 * 
	 * @param id
	 * @return
	 */
	public Hykzz getHykzz(String id) {
		return hykzzDao.getHykzz(id);
	}

	/**
	 * 根据ID取会员卡档案信息
	 * 
	 * @param id
	 * @return
	 */
	public Hykzz getHykzzda(String hykh) {
		return hykzzDao.getHykzzda(hykh);
	}

	/**
	 * 删除会员卡信息
	 * 
	 * @param id
	 */
	public void delHykzz(String hykh) {
		hykzzDao.delHykzz(hykh);
		hykdaDao.delHykda(hykh);
	}

	/**
	 * 取所有会员卡分类列表
	 * 
	 * @return
	 */
	public List getAllHykflList() {
		return hykzzDao.getAllHykflList();
	}
	
	
	/**
	 * 判断会员卡是否存在
	 * @param info
	 * @param nums
	 * @param zzfs
	 * @return
	 */
	public boolean isExist(Hykzz info, int nums, String zzfs){
		boolean is = false;
		if (zzfs.equals("单个增加")) {
			//单个增加
			if(hykzzDao.getHykzzda(info.getHykh()) != null){
				is = true;
			}
		} else {
			//批量增加
			String hykh = info.getHykh();
			String[] arrayTemp = hykh.split("%");
			
			String strSeq = arrayTemp[1];
			int tempIntHykh = Integer.parseInt(strSeq);
			
			for (int i = 0; i < nums; i++) {
				 
				String tempStrHykh = tempIntHykh + "";
				for (int k = tempStrHykh.length(); k < arrayTemp[1].length(); k++) {
					tempStrHykh = "0" + tempStrHykh;
				}

				if(hykzzDao.getHykzzda(arrayTemp[0] + tempStrHykh) != null){
					is = true;
					break;
				}
				tempIntHykh++;
			}
		}
		return is;
	}
	
	
	/**
	 * 执行退回操作
	 * 将会员卡制证状态改为未使用
	 * 将对应的会员卡档案改为已注销
	 * @param hykzz
	 */
	public void updateHykzzDoth(String id){
		Hykzz hykzz = hykzzDao.getHykzz(id);
		hykzz.setState("未使用");
		
		hykzzDao.updateHykzz(hykzz);
		
		Hykda hykda = hykdaDao.getHykda(hykzz.getHykh());
		
		hykda.setState("已注销");
		hykdaDao.updateHykda(hykda);
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
