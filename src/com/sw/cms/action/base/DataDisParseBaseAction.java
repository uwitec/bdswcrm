package com.sw.cms.action.base;

import com.sw.cms.dao.DataDisParseDAO;

/**
 * 定义了通用ID转换为名称方法
 * @author liyt
 *
 */
public class DataDisParseBaseAction extends BaseAction {

	private static final long serialVersionUID = -1754259506751598958L;
	
	private DataDisParseDAO dataDisParseDao;
	
	/**
	 * 根据用户ID返回用户姓名
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
	 * 根据客户ID取客户名称
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
	 * 根据仓库ID取仓库名称
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
	 * 根据产品类别编号取产品类别名称
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
	 * 根据部门编号取部门名称
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
	 * 根据账户编号取账户名称
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
