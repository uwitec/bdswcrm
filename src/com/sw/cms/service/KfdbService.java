package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.KfdbDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.KfdbProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.util.StaticParamDo;

public class KfdbService {
	
	private KfdbDAO kfdbDao;
	private ProductKcDAO productKcDao;
	private SerialNumDAO serialNumDao;
	private ProductDAO productDao;
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
	 * 确认调拨时是否可操作（）
	 * @param id
	 * @return false:不能操作；true:可操作
	 */
	public boolean isDbFinish(String id) {
		Kfdb kfdb = (Kfdb)kfdbDao.getKfdb(id);
		if(kfdb == null){
			//调拨已经删除，不能操作
			return false;
		}else{
			if(kfdb.getState().equals("已入库")){
				//调拨已经入库，不能操作
				return false;
			}else if(kfdb.getState().equals("已退回")){
				//调拨已经退回，不能操作
				return false;
			}else if(kfdb.getState().equals("已保存")){
				//调拨尚处在以保存状态，不能确认做确认操作中的任何操作
				return false;
			}else{
				return true;
			}
		}
	}
	
	
	/**
	 * 库房调拨草稿、出库、删除时，是否可操作
	 * @param id
	 * @return false:不能操作；true:能操作
	 */
	public boolean isDoKfdb(String id){
		Kfdb kfdb = (Kfdb)kfdbDao.getKfdb(id);
		if(kfdb == null){
			//调拨已经删除，不能操作
			return false;
		}else{
			if(kfdb.getState().equals("已出库")){
				//调拨已经出库，不能操作
				return false;
			}else if(kfdb.getState().equals("已入库")){
				//调拨已入库，不能操作
				return false;
			}else{
				return true;
			}
		}
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
	 * 判断序列号是否满足出库需要
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public String checkXlh(Kfdb kfdb,List kfdbProducts){
		String msg = "";
		String store_id = kfdb.getCk_store_id();
		
		if(kfdbProducts != null && kfdbProducts.size()>0){
			for(int i=0;i<kfdbProducts.size();i++){
				KfdbProduct kfdbProduct = (KfdbProduct)kfdbProducts.get(i);
				if(kfdbProduct != null){
					if(!kfdbProduct.getProduct_id().equals("") && !kfdbProduct.getProduct_name().equals("") ){
					  if(!kfdbProduct.getQz_serial_num().equals("")){
						String product_id = kfdbProduct.getProduct_id();
						String[] arryNums = (kfdbProduct.getQz_serial_num()).split(",");
						
						//判断商品是否是库存商品,只仍库存商品才进行强制序列号判断
						Product product = (Product)productDao.getProductById(product_id);
						if(product.getProp().equals("库存商品")){	
							for(int k=0;k<arryNums.length;k++){
							  String serialNum = arryNums[k];  //要出库的序列号
							  boolean is_store = serialNumDao.getSerialNumState(product_id, store_id,serialNum);//序列号的状态
							
							  if(is_store){								
							  }
							  else
							  {
								  msg += kfdbProduct.getProduct_name() + " 序列号为：" + serialNum + " 不存在，请确认后再进行出库处理\n";
							  }
							}
						}
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
	
	public ProductDAO getProductDao() {
		return productDao;
	}


	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}

}
