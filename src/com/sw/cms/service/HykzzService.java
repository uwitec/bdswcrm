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
	 * ȡ��Ա���б�
	 * 
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykzzList(int curPage, int rowsPerPage, String con) {
		return hykzzDao.getHykzzList(curPage, rowsPerPage, con);
	}

	/**
	 * �����Ա����Ϣ
	 * @param info
	 */
	public void saveHykzz(Hykzz info, int nums, String zzfs) {
		if (zzfs.equals("��������")) {
			
			//��������,ֱ������ƿ���Ϣ
			info.setState("δʹ��");
			info.setId(UUIDGenerator.getUUID());
			hykzzDao.saveHykzz(info);
			
		} else {
			
			//��������
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
				info.setState("δʹ��");
				hykzzDao.saveHykzz(info);
				
				tempIntHykh++;
			}
		}
	}

	/**
	 * ���»�Ա����Ϣ
	 * @param info
	 */
	public void updateHykzz(Hykzz info) {
		hykzzDao.updateHykzz(info);
	}

	/**
	 * ����IDȡ��Ա����Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public Hykzz getHykzz(String id) {
		return hykzzDao.getHykzz(id);
	}

	/**
	 * ����IDȡ��Ա��������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public Hykzz getHykzzda(String hykh) {
		return hykzzDao.getHykzzda(hykh);
	}

	/**
	 * ɾ����Ա����Ϣ
	 * 
	 * @param id
	 */
	public void delHykzz(String hykh) {
		hykzzDao.delHykzz(hykh);
		hykdaDao.delHykda(hykh);
	}

	/**
	 * ȡ���л�Ա�������б�
	 * 
	 * @return
	 */
	public List getAllHykflList() {
		return hykzzDao.getAllHykflList();
	}
	
	
	/**
	 * �жϻ�Ա���Ƿ����
	 * @param info
	 * @param nums
	 * @param zzfs
	 * @return
	 */
	public boolean isExist(Hykzz info, int nums, String zzfs){
		boolean is = false;
		if (zzfs.equals("��������")) {
			//��������
			if(hykzzDao.getHykzzda(info.getHykh()) != null){
				is = true;
			}
		} else {
			//��������
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
	 * ִ���˻ز���
	 * ����Ա����֤״̬��Ϊδʹ��
	 * ����Ӧ�Ļ�Ա��������Ϊ��ע��
	 * @param hykzz
	 */
	public void updateHykzzDoth(String id){
		Hykzz hykzz = hykzzDao.getHykzz(id);
		hykzz.setState("δʹ��");
		
		hykzzDao.updateHykzz(hykzz);
		
		Hykda hykda = hykdaDao.getHykda(hykzz.getHykh());
		
		hykda.setState("��ע��");
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
