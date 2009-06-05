package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.YufuToYingfuDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.YufuToYingfu;
import com.sw.cms.model.YufuToYingfuDesc;

/**
 * Ԥ����Ӧ������
 * @author liyt
 *
 */
public class YufuToYingfuService {
	
	private YufuToYingfuDAO yufuToYingfuDao;
	private CgfkDAO cgfkDao;
	private YufukDAO yufukDao;
	
	
	/**
	 * ���ݲ�ѯ����ȡԤ����Ӧ�������б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getYufuToYingfuList(String con,int curPage, int rowsPerPage){
		return yufuToYingfuDao.getYufuToYingfuList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ����Ԥ��תӦ�ս�����Ϣ
	 * @param info
	 * @param descList
	 */
	public void updateYufuToYingfu(YufuToYingfu info,List descList){
		yufuToYingfuDao.updateYufuToYingfu(info, descList);
		
		if(info.getState().equals("���ύ")){
			
			//���½�������������ʽ
			yufuToYingfuDao.updateJhdFkje(info, descList);
			
			//Ԥ�������
			yufukDao.updateYufukJsje(info);
		}
	}
	
	
	/**
	 * ���ݱ��ȡԤ����Ӧ��������Ϣ
	 * @param id
	 * @return
	 */
	public YufuToYingfu getYufuToYingfu(String id){
		return yufuToYingfuDao.getYufuToYingfu(id);
	}
	
	
	/**
	 * ���ݿͻ����ȡӦ�����б�
	 * @param client_id
	 * @return
	 */
	public List getYingfukByClientId(String client_id){
		List<YufuToYingfuDesc> list = new ArrayList<YufuToYingfuDesc>();
		List yfkList = cgfkDao.getDfkDesc(client_id);
		if(yfkList != null && yfkList.size() > 0){
			for(int i=0;i<yfkList.size();i++){
				Map map = (Map)yfkList.get(i);
				
				double yfje = map.get("yfje")==null?0:((Double)map.get("yfje")).doubleValue();
				
				YufuToYingfuDesc info = new YufuToYingfuDesc();
				
				info.setJhd_id((String)map.get("jhd_id"));
				info.setYingfuje(yfje);
				
				list.add(info);
			}
		}
		return list;
	}
	
	
	/**
	 * ȡ�ͻ��ϼ�Ԥ������
	 * @param client_id
	 * @return
	 */
	public double getYufukjeByClientId(String client_id){
		return yufukDao.getYufukByClientId(client_id);
	}
	
	
	/**
	 * ���ݵ��ݱ��ȡԤ����Ӧ����ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public List getYufuToYingfuDesc(String id){
		return yufuToYingfuDao.getYufuToYingfuDesc(id);
	}
	
	
	/**
	 * ɾ��Ԥ����Ӧ��
	 * @param id
	 */
	public void delYufuToYingfu(String id){
		yufuToYingfuDao.delYufuToYingfu(id);
	}
	
	
	/**
	 * ���½������Ѹ�������״̬
	 * @param cgfkDescs
	 */
	public void updateJhdFkje(YufuToYingfu info,List descList){
		yufuToYingfuDao.updateJhdFkje(info, descList);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateID() {
		return yufuToYingfuDao.getID();
	}
	
	
	public CgfkDAO getCgfkDao() {
		return cgfkDao;
	}
	public void setCgfkDao(CgfkDAO cgfkDao) {
		this.cgfkDao = cgfkDao;
	}
	public YufukDAO getYufukDao() {
		return yufukDao;
	}
	public void setYufukDao(YufukDAO yufukDao) {
		this.yufukDao = yufukDao;
	}
	public YufuToYingfuDAO getYufuToYingfuDao() {
		return yufuToYingfuDao;
	}
	public void setYufuToYingfuDao(YufuToYingfuDAO yufuToYingfuDao) {
		this.yufuToYingfuDao = yufuToYingfuDao;
	}

}
