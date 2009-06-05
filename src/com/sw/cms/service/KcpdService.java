package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.KcpdDAO;
import com.sw.cms.dao.KcpdYkTblDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.model.Kcpd;
import com.sw.cms.model.KcpdDesc;
import com.sw.cms.model.KcpdYkTbl;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;

/**
 * 库存盘点处理<BR>
 * 同时修改当前库存值，添加盘点账务信息<BR>
 * 同时会影响出入库汇总、库存成本变化<BR>
 * @author liyt
 *
 */
public class KcpdService {
	
	private KcpdDAO kcpdDao;
	private ProductKcDAO productKcDao;
	private ProductDAO productDao;
	private StoreDAO storeDao;
	private KcpdYkTblDAO kcpdYkTblDao;

	public void setKcpdDao(KcpdDAO kcpdDao) {
		this.kcpdDao = kcpdDao;
	}
	
	
	/**
	 * 盘点信息列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getKcpds(String con,int curPage, int rowsPerPage){
		return kcpdDao.getKcpds(con, curPage, rowsPerPage);
	}

	
	/**
	 * 根据ID取详细信息
	 * @param id
	 * @return
	 */
	public Object getKcpd(String id){
		return kcpdDao.getKcpd(id);
	}
	
	
	/**
	 * 保存盘点信息
	 * @param kcpd
	 * @param KcpdDescs
	 */
	public void saveKcpd(Kcpd kcpd,List kcpdDescs){
		kcpdDao.saveKcpd(kcpd, kcpdDescs);
		
		if(kcpd.getState().equals("已提交")){
			this.insertKcpdYkTbl(kcpd, kcpdDescs);
		}
	}
	
	
	/**
	 * 更新盘点信息
	 * @param kcpd
	 * @param kcpdDescs
	 */
	public void updateKcpd(Kcpd kcpd,List kcpdDescs){
		kcpdDao.updateKcpd(kcpd, kcpdDescs);
		
		if(kcpd.getState().equals("已提交")){
			this.insertKcpdYkTbl(kcpd, kcpdDescs);
		}
	}
	
	
	
	/**
	 * 根据盘点ID取盘点详细
	 * @param pd_id
	 * @return
	 */
	public List getKcpdDescs(String pd_id){
		return kcpdDao.getKcpdDescs(pd_id);
	}
	
	
	/**
	 * 删除盘点信息
	 * @param pd_id
	 */
	public void delKcpd(String pd_id){
		kcpdDao.delKcpd(pd_id);
	}

	
	
	/**
	 *根据条件取库存产品列表
	 * @param con
	 * @return
	 */
	public Page getAllProductKc(String con,int curPage, int rowsPerPage){
		return productKcDao.getAllProductKc(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 返回当前可用的序列号
	 * @return
	 */
	public String updateKcpdId(){
		return kcpdDao.getKcpdID();
	}
	
	
	/**
	 * 根据库存盘点信息更新库存数量，同时生成相应的盘点盈亏信息
	 * @param kcpd
	 * @param kcpdDescs
	 */
	private void insertKcpdYkTbl(Kcpd kcpd,List kcpdDescs){
		KcpdYkTbl kcpdYkTbl = new KcpdYkTbl();
		
		double je = 0;
		String type = "0";
		if(kcpdDescs != null && kcpdDescs.size()>0){
			for(int i=0;i<kcpdDescs.size();i++){
				KcpdDesc kcpdDesc = (KcpdDesc)kcpdDescs.get(i);
				if(kcpdDesc != null && !kcpdDesc.getProduct_name().equals("")){
					int yk_nums = kcpdDesc.getYk();					
					Product product = (Product)productDao.getProductById(kcpdDesc.getProduct_id());					
					double price = product.getPrice();
					
					productKcDao.updateProductKcNums(kcpdDesc.getProduct_id(), kcpd.getStore_id(), kcpdDesc.getSj_nums());
					
					je += price * yk_nums;
				}
			}
		}
		
		if(je > 0){  //报溢收入
			type = "1";
		}else if(je < 0){ //报损支出
			type = "2";
		}
		
		//出现损溢，则保存损溢信息
		if(!type.equals("0")){		
			kcpdYkTbl.setJe(je);
			kcpdYkTbl.setType(type);
			kcpdYkTbl.setKcpd_id(kcpd.getId());
			kcpdYkTbl.setRemark("由盘点信息生成损溢情况，盘点编号[" + kcpd.getId() + "]");
			kcpdYkTbl.setCzr(kcpd.getCzr());
			kcpdYkTbl.setJsr(kcpd.getPdr());
			kcpdYkTbl.setFs_date(kcpd.getPdrq());
			
			kcpdYkTblDao.insertKcpdYkTbl(kcpdYkTbl);
		}
	}
	

	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}

	public void setStoreDao(StoreDAO storeDao) {
		this.storeDao = storeDao;
	}

	public KcpdDAO getKcpdDao() {
		return kcpdDao;
	}

	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}

	public StoreDAO getStoreDao() {
		return storeDao;
	}


	public KcpdYkTblDAO getKcpdYkTblDao() {
		return kcpdYkTblDao;
	}


	public void setKcpdYkTblDao(KcpdYkTblDAO kcpdYkTblDao) {
		this.kcpdYkTblDao = kcpdYkTblDao;
	}


	public ProductDAO getProductDao() {
		return productDao;
	}


	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}
	
}
