package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.dao.DjhpszDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.Djhpsz;
import com.sw.cms.model.SysUser;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

/**
 * �ҽ���Ʒ����
 * @author zuohj
 *
 */
public class DjhpszService {
	
	private DjhpszDAO djhpszDao;
	private ProductKcDAO productKcDao;
	private ProductDAO productDao;
	private UserDAO userDao;
		

	public DjhpszDAO getDjhpszDao() {
		return djhpszDao;
	}

	public void setDjhpszDao(DjhpszDAO djhpszDao) {
		this.djhpszDao = djhpszDao;
	}
	
	
	/**
	 * ȡ�ҽ���Ʒ�����б�(����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getDjhpszList(String con,int curPage, int rowsPerPage){
		return djhpszDao.getDjhpszList(con, curPage, rowsPerPage);
	}
	
	
	
	/**
	 * ���¶ҽ���Ʒ������Ϣ
	 * @param djhpsz
	 * 
	 */
	public void updateDjhpsz(Djhpsz djhpsz){
		djhpszDao.updateDjhpsz(djhpsz);	
	}
	
	
	/**
	 * ����ҽ���Ʒ������Ϣ
	 * @param djhpszProducts
	 * @param czr
	 */
	public void saveDjhpsz(List djhpszProducts,String czr){
		djhpszDao.saveDjhpsz(djhpszProducts,czr);	
	}
	
		
	/**
	 * ���ݱ�Ż�ȡ�ҽ���Ʒ������Ϣ
	 * @param product_id
	 * @return
	 */
	public Object getDjhpsz(String product_id){
		return djhpszDao.getDjhpsz(product_id);
	}
	
			
	/**
	 * ɾ���ҽ���Ʒ������Ϣ
	 * @param product_id
	 */
	public void delDjhpszd(String product_id){		
		
		djhpszDao.delDjhpsz(product_id);
	}
	
	
	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}


	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}



	public ProductDAO getProductDao() {
		return productDao;
	}


	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}


	
	public UserDAO getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}


	
	
}
