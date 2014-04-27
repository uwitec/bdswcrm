package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.XsfpDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.XsfpDkfp;
import com.sw.cms.model.XsfpFpmx;
import com.sw.cms.model.XsfpFpxx;

/**
 * ���۷�Ʊ����
 * @author liyt
 *
 */
public class XsfpService {
	
	private XsfpDAO xsfpDao;
	
	/**
	 * ȡ���۷�Ʊ�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsfpFpxxPage(String con,int curPage, int rowsPerPage){
		return xsfpDao.getXsfpFpxxPage(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ȡ��Ʊ��ϸ
	 * @param id
	 * @return
	 */
	public List getXsfpFpmxList(String id) {
		return xsfpDao.getXsfpFpmxList(id);
	}
	
	
	/**
	 * ȡ��Ʊ��Ϣ
	 * @param id
	 * @return
	 */
	public XsfpFpxx getXsfpFpxx(String id){
		return xsfpDao.getXsfpFpxx(id);
	}
	
	
	/**
	 * ȡ���۷�Ʊ������Ʊ�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsfpDkfpPage(String con , int curPage, int rowsPerPage){
		return xsfpDao.getXsfpDkfpPage(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ȡ���۷�Ʊ��ˮ��
	 * @return
	 */
	public String updateXsfpID(){
		return xsfpDao.getXsfpID();
	}
	
	
	/**
	 * �༭���۷�Ʊ��Ϣ
	 * @param xsfpFpxx
	 * @param fpmxDescs
	 */
	public void updateXsfp(XsfpFpxx xsfpFpxx, List<XsfpFpmx> fpmxDescs) {
		xsfpDao.updateXsfp(xsfpFpxx, fpmxDescs);
		
		if(xsfpFpxx.getState().equals("���ύ")) {
			//���´�����Ʊ��Ϣ��״̬
			if(fpmxDescs != null && fpmxDescs.size() > 0){
				for(XsfpFpmx xsfpFpmx : fpmxDescs) {
					//��Ӧ������Ʊ��Ϣ
					XsfpDkfp xsfpDkfp = xsfpDao.getXsfpDkfp(xsfpFpmx.getYw_id());
					if(xsfpDkfp != null) {
						//���ݿ�Ʊ����жϴ�����Ʊ״̬�������������ѿ����ѿ���
						if(xsfpFpmx.getKpje_ying() - xsfpFpmx.getKpje_yi() > xsfpFpmx.getKpje_bc()){
							xsfpDkfp.setState("�����ѿ�");
						} else if (xsfpFpmx.getKpje_ying() - xsfpFpmx.getKpje_yi() == xsfpFpmx.getKpje_bc()){
							xsfpDkfp.setState("�ѿ�");
						}
						xsfpDkfp.setYkpje(xsfpDkfp.getYkpje() + xsfpFpmx.getKpje_bc());
						xsfpDao.updateXsfpDkfp(xsfpDkfp);
					}
				}
			}
		}
	}
	
	/**
	 * ɾ�����۷�Ʊ��Ϣ
	 * @param id
	 */
	public void delXsfp(String id){
		xsfpDao.delXsfp(id);
	}
	
	
	/**
	 * ���۷�Ʊͳ�ƣ�״̬Ϊ���ύ
	 * @param start_date
	 * @param end_date
	 * @param fplx
	 * @return
	 */
	public List getXsfpList(String start_date, String end_date, String fplx) {
		return xsfpDao.getXsfpList(start_date, end_date, fplx);
	}
	
	
	/**
	 * ���۷�Ʊͨ-��ϸ��Ϣ
	 * @param start_date
	 * @param end_date
	 * @param fplx
	 * @return
	 */
	public Map getXsfpMxMap(String start_date, String end_date, String fplx) {
		return xsfpDao.getXsfpMxMap(start_date, end_date, fplx);
	}

	public XsfpDAO getXsfpDao() {
		return xsfpDao;
	}

	public void setXsfpDao(XsfpDAO xsfpDao) {
		this.xsfpDao = xsfpDao;
	}

}
