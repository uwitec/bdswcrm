package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ChtjDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.model.Chtj;
import com.sw.cms.model.ChtjDesc;
import com.sw.cms.model.Page;

public class ChtjService {
	
	private ChtjDAO chtjDao;
	private ProductDAO productDao;
	
	/**
	 * ���ݲ�ѯ����ȡ�����б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getChtjList(String con,int curPage, int rowsPerPage){
		return chtjDao.getChtjList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���������������Ϣ
	 * @param chtj
	 * @param chtjDescs
	 */
	public void saveChtj(Chtj chtj,List chtjDescs){
		chtjDao.saveChtj(chtj, chtjDescs);
		
		if(chtj.getState().equals("���ύ")){
			this.updateProductPrice(chtjDescs);
		}
	}
	
	
	/**
	 * ���´�����������Ϣ
	 * @param chtj
	 * @param chtjDescs
	 */
	public void updateChtj(Chtj chtj,List chtjDescs){
		chtjDao.updateChtj(chtj, chtjDescs);
		
		if(chtj.getState().equals("���ύ")){
			this.updateProductPrice(chtjDescs);
		}
	}
	
	
	/**
	 * ȡ���������Ϣ
	 * @param id
	 * @return
	 */
	public Object getChtj(String id){
		return chtjDao.getChtj(id);
	}
	
	
	/**
	 * ȡ���������ϸ
	 * @param id
	 * @return
	 */
	public List getChtjDesc(String id){
		return chtjDao.getChtjDesc(id);
	}
	
	
	/**
	 * ɾ���������
	 * @param id
	 */
	public void delChtj(String id){
		chtjDao.delChtj(id);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateChtjID() {
		return chtjDao.getChtjID();
	}
	
	
	/**
	 * �޸Ĳ�Ʒ�ɱ���
	 * @param chtjDescs
	 */
	private void updateProductPrice(List chtjDescs){
		if(chtjDescs != null && chtjDescs.size()>0){
			for(int i=0;i<chtjDescs.size();i++){
				ChtjDesc chtjDesc = (ChtjDesc)chtjDescs.get(i);
				if(chtjDesc != null){
					if(!chtjDesc.getProduct_id().equals("") && !chtjDesc.getProduct_name().equals("")){
						productDao.updateProductPrice(chtjDesc.getProduct_id(), chtjDesc.getTzjg());						
					}
				}
			}
		}
	}


	public ChtjDAO getChtjDao() {
		return chtjDao;
	}


	public void setChtjDao(ChtjDAO chtjDao) {
		this.chtjDao = chtjDao;
	}


	public ProductDAO getProductDao() {
		return productDao;
	}


	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}

}
