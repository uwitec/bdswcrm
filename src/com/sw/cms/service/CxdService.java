package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.CxdDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.RkdDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Cxd;
import com.sw.cms.model.CxdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.Rkd;
import com.sw.cms.model.RkdProduct;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.util.StaticParamDo;

/**
 * 拆卸单处理
 * 
 * @author liyt
 * 
 */
public class CxdService {

	private CxdDAO cxdDao;
	private CkdDAO ckdDao;
	private SerialNumDAO serialNumDao;
	private RkdDAO rkdDao;
	private ProductKcDAO productKcDao;
	private ProductDAO productDao;

	/**
	 * 取拆卸单列表
	 * 
	 * @param start_date
	 * @param end_date
	 * @param state
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCxdList(String con,int curPage, int rowsPerPage) {
		return cxdDao.getCxdList(con, curPage,rowsPerPage);
	}

	/**
	 * 根据编号取拆卸单信息
	 * 
	 * @param id
	 * @return
	 */
	public Cxd editCxd(String id) {
		return cxdDao.editCxd(id);
	}
	
	
	/**
	 * 根据编号取拆卸单商品明细信息
	 * @param cxd_id
	 * @return
	 */
	public List editCxdProducts(String cxd_id){
		return cxdDao.editCxdProducts(cxd_id);
	}
	
	
	/**
	 * 根据编与判断拆卸单是否已提交
	 * @param cxd_id
	 * @return
	 */
	public boolean isCxdSubmit(String cxd_id){
		return cxdDao.isCxdSubmit(cxd_id);
	}
	

	/**
	 * 保存拆卸单信息
	 * 
	 * @param cxd
	 * @param cxdProduct
	 */
	public void updateCxd(Cxd cxd, List<CxdProduct> cxdProducts) {
		cxdDao.updateCxd(cxd, cxdProducts);

		// 如果拆卸单状态为已提交，则生成相应出库单及入库单
		if (cxd.getState().equals("已提交")) {
			this.addCkd(cxd);		
			this.addRkd(cxd, cxdProducts);
		}
	}

	/**
	 * 删除拆卸单
	 * 
	 * @param id
	 */
	public void delCxd(String id) {
		cxdDao.delCxd(id);
	}
	

	/**
	 * 取当前可用的序列号并更新序列号
	 * 
	 * @return
	 */
	public String updateCxdID() {
		return cxdDao.getCxdID();
	}
	
	
	/**
	 * 判断库存量是否满足出库需要
	 * @param cxd
	 * @param cxdProducts
	 */
	public String checkKc(Cxd cxd){
		String msg = "";
		String store_id = cxd.getStore_id();
		String product_id = cxd.getProduct_id();
		
		//判断商品是否是库存商品,只仍库存商品才进行库存数量判断
		Product product = (Product)productDao.getProductById(product_id);
		if(product.getProp().equals("库存商品")){						
			int cknums = cxd.getNums();  //要出库数量						
			int kcnums = productKcDao.getKcNums(product_id, store_id);//库存数量
			
			if(cknums>kcnums){
				msg += "产品：" + cxd.getProduct_name() + "在" + StaticParamDo.getStoreNameById(store_id) +" 中当前库存为：" + kcnums + "  无法拆卸，拆卸单已保存，请先调拨后再拆卸！";
			}
		}
		return msg;
	}

	
	/**
	 * <p>
	 * 根据拆卸单生成相应出库单
	 * </p>
	 * <p>
	 * 同时处理序列号相关信息
	 * </p>
	 * 
	 * @param lsd
	 * @param lsdProducts
	 */
	private void addCkd(Cxd cxd) {
		Ckd ckd = new Ckd();

		String ckd_id = ckdDao.getCkdID();
		ckd.setCkd_id(ckd_id);
		ckd.setFzr(cxd.getJsr());
		ckd.setXsd_id(cxd.getId());
		ckd.setCreatdate(cxd.getCdate());
		ckd.setCk_date(cxd.getCdate());
		ckd.setState("已出库");
		ckd.setSkzt("已收");
		ckd.setMs("拆卸单生成出库单，拆卸单编号 [" + cxd.getId() + "]");
		ckd.setClient_name("");
		ckd.setXsry(cxd.getJsr());
		ckd.setStore_id(cxd.getStore_id());
		ckd.setCzr(cxd.getCzr());

		List<CkdProduct> ckdProducts = new ArrayList<CkdProduct>();

		CkdProduct ckdProduct = new CkdProduct();

		ckdProduct.setCkd_id(ckd_id);
		ckdProduct.setProduct_id(cxd.getProduct_id());
		ckdProduct.setProduct_name(cxd.getProduct_name());
		ckdProduct.setProduct_xh(cxd.getProduct_xh());
		ckdProduct.setNums(cxd.getNums());
		ckdProduct.setRemark(cxd.getRemark());
		ckdProduct.setPrice(cxd.getPrice());
		ckdProduct.setCbj(cxd.getPrice());
		ckdProduct.setJgtz(0);

		// 对于输入待拆卸的序列号系统做处理
		if ((cxd.getSerial_nums() != null) && (!cxd.getSerial_nums().equals(""))) {
			String[] arryNums = (cxd.getSerial_nums()).split(",");

			SerialNumMng serialNumMng = new SerialNumMng();
			SerialNumFlow serialNumFlow = new SerialNumFlow();

			for (int k = 0; k < arryNums.length; k++) {

				serialNumMng.setProduct_id(cxd.getProduct_id());
				serialNumMng.setProduct_name(cxd.getProduct_name());
				serialNumMng.setProduct_xh(cxd.getProduct_xh());
				serialNumMng.setSerial_num(arryNums[k]);
				serialNumMng.setState("已拆卸");
				serialNumMng.setStore_id("");
				serialNumMng.setYj_flag("0");
				serialNumDao.updateSerialNumState(serialNumMng); // 更新序列号状态

				serialNumFlow.setClient_name("");
				serialNumFlow.setTel("");
				serialNumFlow.setCzr(cxd.getCzr());
				serialNumFlow.setYwtype("拆卸");
				serialNumFlow.setFs_date(cxd.getCdate());
				serialNumFlow.setJsr(StaticParamDo.getRealNameById(cxd.getJsr()));
				serialNumFlow.setKf_dj_id(ckd_id);
				serialNumFlow.setSerial_num(arryNums[k]);
				serialNumFlow.setKf_url("viewCkd.html?ckd_id=");
				serialNumFlow.setYw_dj_id(cxd.getId());
				serialNumFlow.setYw_url("viewCxd.html?id=");
				serialNumFlow.setXsdj(0);

				serialNumDao.saveSerialFlow(serialNumFlow); // 保存序列号流转过程
			}
		}

		ckdProducts.add(ckdProduct);
		
		//保存出库单
		ckdDao.saveCkd(ckd, ckdProducts);
		
		//减库存
		productKcDao.updateProductKc(cxd.getStore_id(),cxd.getProduct_id(), cxd.getNums());
	}
	
	
	
	/**
	 * 生成相关入库单并保存
	 * @param jhd
	 * @param JhdProducts
	 */
	private void addRkd(Cxd cxd,List<CxdProduct> cxdProducts){
		
		Rkd rkd = new Rkd();          //入库单基本信息
		List<RkdProduct> rkdProducts = new ArrayList<RkdProduct>();  //入库单相应商品列表
		
		String rkd_id = rkdDao.getRkdID();//当前可用入库单号
		
		rkd.setRkd_id(rkd_id); 
		rkd.setJhd_id(cxd.getId());
		rkd.setCreatdate(cxd.getCdate());
		rkd.setState("已入库");
		rkd.setFzr(cxd.getJsr());
		rkd.setRk_date(cxd.getCdate());
		rkd.setMs("拆卸单生成入库单，拆卸单编号 [" + cxd.getId() + "]");
		rkd.setCgfzr(cxd.getJsr());
		rkd.setCzr(cxd.getCzr());
		rkd.setClient_name("");
		rkd.setStore_id(cxd.getStore_id());
		
		
		
		if(cxdProducts != null && cxdProducts.size()>0){
			for(int i=0;i<cxdProducts.size();i++){
				
				CxdProduct cxdProduct = (CxdProduct)cxdProducts.get(i);
				if(cxdProduct != null && cxdProduct.getProduct_id() != null && !cxdProduct.getProduct_id().equals("")){
					RkdProduct rkdProduct = new RkdProduct();
					
					rkdProduct.setProduct_id(cxdProduct.getProduct_id());
					rkdProduct.setProduct_name(cxdProduct.getProduct_name());
					rkdProduct.setProduct_xh(cxdProduct.getProduct_xh());
					rkdProduct.setPrice(cxdProduct.getPrice());
					rkdProduct.setNums(cxdProduct.getNums());
					rkdProduct.setRkd_id(rkd_id);
					rkdProduct.setRemark(cxdProduct.getRemark());
					rkdProduct.setQz_serial_num(cxdProduct.getQz_serial_num());
					
					rkdProducts.add(rkdProduct);
					
					// 对于输入的序列号系统做处理
					if ((cxdProduct.getQz_serial_num() != null) && (!cxdProduct.getQz_serial_num().equals(""))) {
						String[] arryNums = (cxdProduct.getQz_serial_num()).split(",");

						SerialNumMng serialNumMng = new SerialNumMng();
						SerialNumFlow serialNumFlow = new SerialNumFlow();

						for (int k = 0; k < arryNums.length; k++) {

							serialNumMng.setProduct_id(cxdProduct.getProduct_id());
							serialNumMng.setProduct_name(cxdProduct.getProduct_name());
							serialNumMng.setProduct_xh(cxdProduct.getProduct_xh());
							serialNumMng.setSerial_num(arryNums[k]);
							serialNumMng.setState("在库");
							serialNumMng.setStore_id(cxd.getStore_id());
							serialNumMng.setYj_flag("0");
							serialNumDao.updateSerialNumState(serialNumMng); // 更新序列号状态

							serialNumFlow.setClient_name("");
							serialNumFlow.setTel("");
							serialNumFlow.setCzr(cxd.getCzr());
							serialNumFlow.setYwtype("拆卸");
							serialNumFlow.setFs_date(cxd.getCdate());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(cxd.getJsr()));
							serialNumFlow.setKf_dj_id(rkd_id);
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewRkd.html?rkd_id=");
							serialNumFlow.setYw_dj_id(cxd.getId());
							serialNumFlow.setYw_url("viewCxd.html?id=");
							serialNumFlow.setXsdj(0);

							serialNumDao.saveSerialFlow(serialNumFlow); // 保存序列号流转过程
						}
					}
				}
			}
		}
		
		//保存入库单
		rkdDao.saveRkd(rkd, rkdProducts);
		
		//更新库存数及成本价
		productKcDao.updateProductKc(rkd, rkdProducts);		
		
	}

	
	public CxdDAO getCxdDao() {
		return cxdDao;
	}

	public void setCxdDao(CxdDAO cxdDao) {
		this.cxdDao = cxdDao;
	}

	public CkdDAO getCkdDao() {
		return ckdDao;
	}

	public void setCkdDao(CkdDAO ckdDao) {
		this.ckdDao = ckdDao;
	}

	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}

	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}

	public RkdDAO getRkdDao() {
		return rkdDao;
	}

	public void setRkdDao(RkdDAO rkdDao) {
		this.rkdDao = rkdDao;
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

}
