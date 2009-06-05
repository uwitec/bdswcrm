package com.sw.cms.action.base;

import com.sw.cms.dao.DataDisParseDAO;

/**
 * ������ͨ��IDת��Ϊ���Ʒ���
 * @author liyt
 *
 */
public class DataDisParseBaseAction extends BaseAction {

	private static final long serialVersionUID = -1754259506751598958L;
	
	private DataDisParseDAO dataDisParseDao;
	
	/**
	 * �����û�ID�����û�����
	 * @param user_id
	 * @return
	 */
	public String getUserRealName(String user_id){
		if(user_id == null || user_id.equals("")){
			return "";
		}
		return dataDisParseDao.getRealNameById(user_id);
	}
	
	/**
	 * ���ݿͻ�IDȡ�ͻ�����
	 * @param client_id
	 * @return
	 */
	public String getClientName(String client_id){
		if(client_id == null || client_id.equals("")){
			return "";
		}
		return dataDisParseDao.getClientNameById(client_id);
	}
	
	
	/**
	 * ���ݲֿ�IDȡ�ֿ�����
	 * @param id
	 * @return
	 */
	public String getStoreName(String id){
		if(id == null || id.equals("")){
			return "";
		}
		return dataDisParseDao.getStoreNameById(id);
	}
	
	
	/**
	 * ���ݲ�Ʒ�����ȡ��Ʒ�������
	 * @param kind_id
	 * @return
	 */
	public String getProductKindName(String kind_id){
		if(kind_id == null || kind_id.equals("")){
			return "";
		}
		return dataDisParseDao.getKindNameByKindId(kind_id);
	}
	
	
	/**
	 * ���ݲ��ű��ȡ��������
	 * @param dept_id
	 * @return
	 */
	public String getDeptName(String dept_id){
		if(dept_id == null || dept_id.equals("")){
			return "";
		}
		return dataDisParseDao.getDeptNameById(dept_id);
	}
	
	
	/**
	 * �����˻����ȡ�˻�����
	 * @param id
	 * @return
	 */
	public String getAccountName(String id){
		if(id == null || id.equals("")){
			return "";
		}
		return dataDisParseDao.getAccountNameById(id);
	}
	

	public DataDisParseDAO getDataDisParseDao() {
		return dataDisParseDao;
	}
	public void setDataDisParseDao(DataDisParseDAO dataDisParseDao) {
		this.dataDisParseDao = dataDisParseDao;
	}

}
