package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.YushouToYingshouDAO;
import com.sw.cms.dao.YushoukDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.XsskDesc;
import com.sw.cms.model.YushouToYingshou;
import com.sw.cms.model.YushouToYingshouDesc;

/**
 * Ԥ��תӦ��
 * @author liyt
 *
 */
public class YushouToYingshouService {
	
	private YushouToYingshouDAO yushouToYingshouDao;
	private XsskDAO xsskDao;
	private YushoukDAO yushoukDao;
	
	/**
	 * ���ݲ�ѯ����ȡԤ��תӦ�ս����б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getYushouToYingshouList(String con,int curPage, int rowsPerPage){
		return yushouToYingshouDao.getYushouToYingshouList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ����Ԥ��תӦ�ս�����Ϣ
	 * @param info
	 * @param descList
	 */
	public void updateYushouToYingshou(YushouToYingshou info,List descList){
		yushouToYingshouDao.updateYushouToYingshou(info, descList);
		
		//Ԥ��תӦ�տ����
		if(info.getState().equals("���ύ")){
			//���۵��տ����
			yushouToYingshouDao.updateXsdFk(info, descList);
			
			//Ԥ�տ����
			yushoukDao.updateYushoukJsje(info);
		}
	}
	
	
	/**
	 * ���ݱ��ȡӦԤ��תӦ�ջ�����Ϣ
	 * @param id
	 * @return
	 */
	public YushouToYingshou getYushouToYingshou(String id){
		return yushouToYingshouDao.getYushouToYingshou(id);
	}
	
	
	/**
	 * ȡ�ͻ�Ӧ�տ��б�
	 * @param client_id
	 * @return
	 */
	public List getYingshoukByClientId(String client_id){
		List<YushouToYingshouDesc> list = new ArrayList<YushouToYingshouDesc>();
		List yskList = xsskDao.getYskByClientId(client_id);
		if(yskList != null && yskList.size() > 0){
			for(int i=0;i<yskList.size();i++){
				Map map = (Map)yskList.get(i);
				
				double ysk = map.get("ysk")==null?0:((Double)map.get("ysk")).doubleValue();
				
				YushouToYingshouDesc info = new YushouToYingshouDesc();
				
				info.setXsd_id((String)map.get("xsd_id"));
				info.setYingshouje(ysk);
				
				list.add(info);
			}
		}
		return list;
	}
	
	
	/**
	 * ȡ�ͻ��ϼ�Ԥ�տ���
	 * @param client_id
	 * @return
	 */
	public double getYishoukjeByClientId(String client_id){
		return yushoukDao.getYushoukByClientId(client_id);
	}
	
	
	/**
	 * ���ݵ��ݱ��ȡԤ��תӦ����ϸ��Ϣ��ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public List getYushouToYingshouDesc(String id){
		return yushouToYingshouDao.getYushouToYingshouDesc(id);
	}
	
	
	/**
	 * ɾ��Ԥ��תӦ��
	 * @param id
	 */
	public void delYushouToYingshou(String id){
		yushouToYingshouDao.delYushouToYingshou(id);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateID() {
		return yushouToYingshouDao.getID();
	}	
	
	
	/**
	 * �ж��ύ��Ԥ�ճ�Ӧ����ϸ���Ƿ���������������տ��ͻ��������ڷ��ر�ţ������ڷ��ؿ�
	 * @param YushouToYingshou
	 * @param descList
	 * @return
	 */
	public String getExistYushouToYingshouDesc(YushouToYingshou info,List descList){
		String temp = "";
		
		String client_name = info.getClient_name();
		String yw_id = info.getId();
		
		if(descList != null && descList.size()>0){
			for(int i =0;i<descList.size();i++){
				YushouToYingshouDesc desc = (YushouToYingshouDesc)descList.get(i);
				if(desc != null && desc.getBcjs() != 0){
					String xsd_id = desc.getXsd_id();
					double bcjs=desc.getBcjs();
					if(yushouToYingshouDao.isXsskDescExist(yw_id, xsd_id, client_name,bcjs)){
						//������ڳ�ͻ�����¼��Ӧ���������
						if(temp.equals("")){
							temp = xsd_id;
						}else{
							temp += "," + xsd_id;
						}
					}
				}
			}
		}
		
		return temp;
	}
	
	public YushouToYingshouDAO getYushouToYingshouDao() {
		return yushouToYingshouDao;
	}
	public void setYushouToYingshouDao(YushouToYingshouDAO yushouToYingshouDao) {
		this.yushouToYingshouDao = yushouToYingshouDao;
	}
	public XsskDAO getXsskDao() {
		return xsskDao;
	}
	public void setXsskDao(XsskDAO xsskDao) {
		this.xsskDao = xsskDao;
	}


	public YushoukDAO getYushoukDao() {
		return yushoukDao;
	}


	public void setYushoukDao(YushoukDAO yushoukDao) {
		this.yushoukDao = yushoukDao;
	}

}
