package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.dao.ChtjDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.model.Chtj;
import com.sw.cms.model.ChtjDesc;
import com.sw.cms.model.Page;

public class ChtjService {
	
	private ChtjDAO chtjDao;
	private ProductDAO productDao;
	private ProductKcDAO productKcDao;
	
	/**
	 * 根据查询条件取调价列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getChtjList(String con,int curPage, int rowsPerPage){
		return chtjDao.getChtjList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 保存存货调价相关信息
	 * @param chtj
	 * @param chtjDescs
	 */
	public void saveChtj(Chtj chtj,List chtjDescs){
		chtjDao.saveChtj(chtj, this.setChNums(chtjDescs));
		
		if(chtj.getState().equals("已提交")){
			this.updateProductPrice(chtjDescs);
		}
	}
	
	
	/**
	 * 更新存货调价相关信息
	 * @param chtj
	 * @param chtjDescs
	 */
	public void updateChtj(Chtj chtj,List chtjDescs){
		chtjDao.updateChtj(chtj, this.setChNums(chtjDescs));
		
		if(chtj.getState().equals("已提交")){
			this.updateProductPrice(chtjDescs);
		}
	}
	
	
	/**
	 * 取存货调价信息
	 * @param id
	 * @return
	 */
	public Object getChtj(String id){
		return chtjDao.getChtj(id);
	}
	
	
	/**
	 * 取存货调价明细
	 * @param id
	 * @return
	 */
	public List getChtjDesc(String id){
		return chtjDao.getChtjDesc(id);
	}
	
	
	/**
	 * 删除存货调价
	 * @param id
	 */
	public void delChtj(String id){
		chtjDao.delChtj(id);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String updateChtjID() {
		return chtjDao.getChtjID();
	}
	
	
	/**
	 * 修改产品成本价
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
	

	/**
	 * 设置调价时商品的数量
	 * @param chtjDescs
	 * @return
	 */
	private List setChNums(List chtjDescs){
		List chtjDescs2 = new ArrayList();
		if(chtjDescs != null && chtjDescs.size()>0){
			for(int i=0;i<chtjDescs.size();i++){
				ChtjDesc chtjDesc = (ChtjDesc)chtjDescs.get(i);
				if(chtjDesc != null){
					chtjDesc.setNums(productKcDao.getKcNums(chtjDesc.getProduct_id(), ""));
					chtjDescs2.add(chtjDesc);
				}
			}
		}
		return chtjDescs2;
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


	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}


	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}

}
