package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.KfdbDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.KfdbProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.util.StaticParamDo;

public class KfdbService {
	
	private KfdbDAO kfdbDao;
	private ProductKcDAO productKcDao;
	private SerialNumDAO serialNumDao;
	
	/**
	 * 根据查询条件取库房调拨列表信息
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getKfdbList(String con,int curPage, int rowsPerPage){
		return kfdbDao.getKfdbList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 保存库房调拨相关信息
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public void saveKfdb(Kfdb kfdb,List kfdbProducts){
		kfdbDao.saveKfdb(kfdb, kfdbProducts);
		
//		if(kfdb.getState().equals("已出库")){ //改变库存值
//			this.updateKc(kfdb, kfdbProducts);
//			this.updateSerialNum(kfdb, kfdbProducts); //处理序列号
//		}
	}
	
	
	/**
	 * 更新库房调拨相关信息
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public void updateKfdb(Kfdb kfdb,List kfdbProducts){
		kfdbDao.updateKfdb(kfdb, kfdbProducts);
		
//		if(kfdb.getState().equals("已出库")){ //改变库存值
//			this.updateKc(kfdb, kfdbProducts);
//			this.updateSerialNum(kfdb, kfdbProducts);//处理序列号
//		}
		
		if(kfdb.getState().equals("已入库")){ 
			//确认入库
			this.updateKc(kfdb, kfdbProducts);
			this.updateSerialNum(kfdb, kfdbProducts);//处理序列号
		}
	}
	
	
	/**
	 * 获取用户待确认的库房调拨列表
	 * @param user_id
	 * @return
	 */
	public List getConfirmKfdbList(String user_id){
		return kfdbDao.getConfirmKfdbList(user_id);
	}
	
	
	/**
	 * 取库房调拨信息
	 * @param id
	 * @return
	 */
	public Object getKfdb(String id){
		return kfdbDao.getKfdb(id);
	}
	
	
	/**
	 * 取库房调拨相关商品明细
	 * @param id
	 * @return
	 */
	public List getKfdbProducts(String id){
		return kfdbDao.getKfdbProducts(id);
	}
	
	/**
	 * 取库房调拨相关商品明细
	 * @param id
	 * @return
	 */
	public List getKfdbProductsObj(String id){
		return kfdbDao.getKfdbProductsObj(id);
	}
	
	
	/**
	 * 删除库房调拨相关信息
	 * @param id
	 */
	public void delKfdb(String id){
		kfdbDao.delKfdb(id);
	}
		

	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String updateKfdbID() {
		return kfdbDao.getKfdbID();
	}
	
	
	/**
	 * 判断调拨单是否已经提交
	 * @param id
	 * @return
	 */
	public boolean isDbFinish(String id) {
		return kfdbDao.isDbFinish(id);
	}

	
	/**
	 * 判断库存量是否满足出库需要
	 * @param ckd
	 * @param ckdProducts
	 */
	public String checkKc(Kfdb kfdb,List kfdbProducts){
		String msg = "";
		String store_id = kfdb.getCk_store_id();
		
		if(kfdbProducts != null && kfdbProducts.size()>0){
			for(int i=0;i<kfdbProducts.size();i++){
				KfdbProduct kfdbProduct = (KfdbProduct)kfdbProducts.get(i);
				if(kfdbProduct != null){
					if(!kfdbProduct.getProduct_id().equals("") && !kfdbProduct.getProduct_name().equals("")){
						String product_id = kfdbProduct.getProduct_id();
						int cknums = kfdbProduct.getNums();  //要出库数量						
						int kcnums = productKcDao.getKcNums(product_id, store_id);//库存数量
						
						if(cknums>kcnums){
							msg += kfdbProduct.getProduct_name() + " 当前库存为：" + kcnums + "  无法满足调拨请求，不能出库\n";
						}
					}
				}
			}
		}
		
		return msg;
	}
	
	
	/**
	 * 改变库存值
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public void updateKc(Kfdb kfdb,List kfdbProducts){
		String ck_store_id = kfdb.getCk_store_id();
		String rk_store_id = kfdb.getRk_store_id();
		
		if(kfdbProducts != null && kfdbProducts.size()>0){
			for(int i=0;i<kfdbProducts.size();i++){
				KfdbProduct kfdbProduct = (KfdbProduct)kfdbProducts.get(i);
				if(kfdbProduct != null){
					if(!kfdbProduct.getProduct_id().equals("") && !kfdbProduct.getProduct_name().equals("")){
						
						String product_id = kfdbProduct.getProduct_id();
						int nums = kfdbProduct.getNums(); //调货数量
						
						productKcDao.updateProductKc(ck_store_id, product_id, nums);  //减少出货仓库库存
						productKcDao.addProductKc(rk_store_id, product_id, nums);   //增加入货仓库库存
					}
				}
			}
		}
	}
	
	
	/**
	 * 更新序列号状态
	 * 包括添加流程信息、改变序列号所在库房
	 * @param kdfd
	 * @param kfdbProducts
	 */
	private void updateSerialNum(Kfdb kfdb,List kfdbProducts){
		if(kfdbProducts != null && kfdbProducts.size()>0){
			for(int i=0;i<kfdbProducts.size();i++){
				KfdbProduct kfdbProduct = (KfdbProduct)kfdbProducts.get(i);
				if(kfdbProduct != null){
					if(!kfdbProduct.getProduct_id().equals("") && !kfdbProduct.getQz_serial_num().equals("")){
						String[] arryNums = (kfdbProduct.getQz_serial_num()).split(",");

						for(int k=0;k<arryNums.length;k++){
							SerialNumFlow serialNumFlow = new SerialNumFlow();
							
							serialNumFlow.setClient_name("");
							serialNumFlow.setCzr(kfdb.getCzr());
							
							serialNumFlow.setYwtype("调拨");
							
							serialNumFlow.setFs_date(kfdb.getCk_date());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(kfdb.getJsr()));
							serialNumFlow.setKf_dj_id(kfdb.getId());
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewKfdb.html?id=");
							serialNumFlow.setYw_dj_id(kfdb.getDbsq_id());							
							serialNumFlow.setYw_url("viewDbsq.html?id=");	
							
							//保存序列号流转过程
							serialNumDao.saveSerialFlow(serialNumFlow);  
							
							//更新序列号所在库房
							serialNumDao.updateSerialNumStore(arryNums[k], kfdb.getRk_store_id());
						}
					}
				}
			}
		}
	}
	
	

	public KfdbDAO getKfdbDao() {
		return kfdbDao;
	}


	public void setKfdbDao(KfdbDAO kfdbDao) {
		this.kfdbDao = kfdbDao;
	}


	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}


	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}


	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}


	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}

}
