package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.JhdDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.RkdDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.dao.ThdDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.YushoukDAO;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.Page;
import com.sw.cms.model.Rkd;
import com.sw.cms.model.RkdProduct;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.util.StaticParamDo;


public class RkdService{
	
	private RkdDAO rkdDao;
	private ProductKcDAO productKcDao;
	private StoreDAO storeDao;
	private SerialNumDAO serialNumDao;
	private JhdDAO jhdDao;
	private ThdDAO thdDao;
	private CgfkDAO cgfkDao;
	private AccountDzdDAO accountDzdDao;
	private AccountsDAO accountsDao;
	private XsskDAO xsskDao;
	private YushoukDAO yushoukDao;
	
	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}
	public void setRkdDao(RkdDAO rkdDao) {
		this.rkdDao = rkdDao;
	}
	
	
	/**
	 * 取入库单列表(带分页标志)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getRkdList(String con,int curPage, int rowsPerPage){
		return rkdDao.getRkdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 保存入库单信息（包括关联产品信息）
	 * @param rkd
	 * @param rkdProducts
	 */
	public void saveRkd(Rkd rkd,List rkdProducts){
		rkdDao.saveRkd(rkd, rkdProducts);  //保存入库单及关联产品信息
		
		if((rkd.getState()).equals("已入库")){  //如果入库单状态为已入库，则需要更新库存信息
			productKcDao.updateProductKc(rkd, rkdProducts);
			jhdDao.updateJhdState(rkd.getJhd_id(), "已入库");
			thdDao.updateThdState(rkd.getJhd_id(), "已入库");
			this.updateSerialNum(rkd, rkdProducts);
		}
	}
	
	
	/**
	 * 更新入库单信息（包括关联产品信息）
	 * @param rkd
	 * @param rkdProducts
	 */
	public void updateRkd(Rkd rkd,List rkdProducts){
		rkdDao.updateRkd(rkd, rkdProducts);  //更新入库单及关联产品信息
		
		if((rkd.getState()).equals("已入库")){  //如果入库单状态为已入库，则需要更新库存信息
			productKcDao.updateProductKc(rkd, rkdProducts);
			
			//更新进货单实际成交信息
			this.updateJhdSjcjInfo(rkd, rkdProducts);
			
			//更新相关业务单据状态为已入库
			jhdDao.updateJhdState(rkd.getJhd_id(), "已入库");
			
			this.updateSerialNum(rkd, rkdProducts);
		}
	}
	
	
	/**
	 * 删除入库单信息（包括关联产品）
	 * @param rkd_id
	 */
	public void delRkd(String rkd_id){
		rkdDao.delRkd(rkd_id);
	}
	
	
	/**
	 * 取当前可用入库单编号
	 * @return
	 */
	public String updateRkdId(){
		return rkdDao.getRkdID();
	}
	
	
	/**
	 * 根据入库单ID取入库单信息
	 * @param rkd_id
	 * @return
	 */
	public Object getRkd(String rkd_id){
		return rkdDao.getRkd(rkd_id);
	}
	
	
	/**
	 * 根据入库单ID取入库单产品列表
	 * @param rkd_id
	 * @return
	 */
	public List getRkdProducts(String rkd_id){
		return rkdDao.getRkdProducts(rkd_id);
	}
	
	
	/**
	 * 取所有仓库列表
	 * @return
	 */
	public List getAllStoreList(){
		return storeDao.getAllStoreList();
	}
	
	
	/**
	 * 根据入库单编号查看入库单是否已经入库
	 * @param xsd_id
	 * @return
	 */
	public boolean isJhdSubmit(String rkd_id){
		return rkdDao.isJhdSubmit(rkd_id);
	}
	
	
	/**
	 * 处理入库序列号
	 * <p>包括两种情况</p>
	 * <p>一、采购；二、销售退货</p>
	 * @param rkd
	 * @param rkdProducts
	 */
	private void updateSerialNum(Rkd rkd,List rkdProducts){
		if(rkdProducts != null && rkdProducts.size()>0){
			for(int i=0;i<rkdProducts.size();i++){
				RkdProduct rkdProduct = (RkdProduct)rkdProducts.get(i);
				if(rkdProduct != null){
					if(!rkdProduct.getProduct_id().equals("") && !rkdProduct.getQz_serial_num().equals("")){
						String[] arryNums = (rkdProduct.getQz_serial_num()).split(",");
						
						SerialNumMng serialNumMng = new SerialNumMng();
						SerialNumFlow serialNumFlow = new SerialNumFlow();
						
						String state = "";
						String yw_url = "";
						String yw_type = "";
						String jhd_id = rkd.getJhd_id();
						if(jhd_id.indexOf("JH") != -1){
							state = "在库";
							yw_url = "viewJhd.html?id=";
							yw_type = "采购";
						}else{
							state = "在库";
							yw_url = "viewThd.html?thd_id=";
							yw_type = "销售退货";
						}
						
						for(int k=0;k<arryNums.length;k++){
							serialNumMng.setProduct_id(rkdProduct.getProduct_id());
							serialNumMng.setProduct_name(rkdProduct.getProduct_name());
							serialNumMng.setProduct_xh(rkdProduct.getProduct_xh());
							serialNumMng.setSerial_num(arryNums[k]);
							serialNumMng.setState(state);
							serialNumMng.setStore_id(rkd.getStore_id());
							serialNumMng.setYj_flag("0");
							
							serialNumDao.updateSerialNumState(serialNumMng); //更新序列号状态
							
							serialNumFlow.setClient_name(StaticParamDo.getClientNameById(rkd.getClient_name()));
							serialNumFlow.setCzr(rkd.getCzr());
							
							serialNumFlow.setYwtype(yw_type);
							
							serialNumFlow.setFs_date(rkd.getRk_date());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(rkd.getCgfzr()));
							serialNumFlow.setKf_dj_id(rkd.getRkd_id());
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewRkd.html?rkd_id=");
							serialNumFlow.setYw_dj_id(rkd.getJhd_id());							
							serialNumFlow.setYw_url(yw_url);
							
							serialNumDao.saveSerialFlow(serialNumFlow);  //保存序列号流转过程
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 入库单退回操作
	 * @param rkd
	 */
	public void doTh(Rkd rkd){
		
		//删除对应入库单
		rkdDao.delRkd(rkd.getRkd_id());
		
		String jhd_id = rkd.getJhd_id();
		
		//如果是采购订单，修改采购订单状态及退回标记
		if(jhd_id.indexOf("JH") != -1){
			jhdDao.updateJhdAfterRkdTh(jhd_id, "已保存", "1");
			
			//查看采购进货时，是否有付款发生
			//如果发生，在退回采购订单时需要处理付款、现金流水、账户金额
			Cgfk cgfk = cgfkDao.getCgfkByDeleteKey(jhd_id);
			
			if(cgfk != null){
				
				//删除采购付款信息
				cgfkDao.delCgfk(cgfk.getId());
				
				//删除现金流水信息
				accountDzdDao.delDzd(cgfk.getId());
				
				//更新账户金额
				accountsDao.addAccountJe(cgfk.getFkzh(), cgfk.getFkje());
			}
		}
	}
	
	
	/**
	 * 修改相应采购订单实际成交数及金额
	 * @param ckd
	 * @param ckdProducts
	 */
	private void updateJhdSjcjInfo(Rkd rkd,List rkdProducts){
		double sjcjje = 0; //采购订单实际成交金额
		String jhd_id = rkd.getJhd_id();
		
		//如果对应业务单据不是进货单，则退出
		if(jhd_id.indexOf("JH") == -1){
			return;
		}
		if(rkdProducts != null && rkdProducts.size()>0){
			for(int i=0;i<rkdProducts.size();i++){
				RkdProduct rkdProduct = (RkdProduct)rkdProducts.get(i);
				if(rkdProduct != null){
					if(!rkdProduct.getProduct_id().equals("") && !rkdProduct.getProduct_name().equals("")){
						double sjcj_xj = rkdProduct.getNums() * rkdProduct.getPrice();
						sjcjje += sjcj_xj;
						jhdDao.updateJhdSjcjNums(jhd_id, rkdProduct.getProduct_id(), rkdProduct.getNums());
					}
				}
			}
		}
		jhdDao.updateJhdSjcjje(jhd_id, sjcjje);
	}
	
	
	public void setStoreDao(StoreDAO storeDao) {
		this.storeDao = storeDao;
	}
	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}
	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}
	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}
	public RkdDAO getRkdDao() {
		return rkdDao;
	}
	public StoreDAO getStoreDao() {
		return storeDao;
	}
	public JhdDAO getJhdDao() {
		return jhdDao;
	}
	public void setJhdDao(JhdDAO jhdDao) {
		this.jhdDao = jhdDao;
	}
	public ThdDAO getThdDao() {
		return thdDao;
	}
	public void setThdDao(ThdDAO thdDao) {
		this.thdDao = thdDao;
	}
	public CgfkDAO getCgfkDao() {
		return cgfkDao;
	}
	public void setCgfkDao(CgfkDAO cgfkDao) {
		this.cgfkDao = cgfkDao;
	}
	public AccountDzdDAO getAccountDzdDao() {
		return accountDzdDao;
	}
	public void setAccountDzdDao(AccountDzdDAO accountDzdDao) {
		this.accountDzdDao = accountDzdDao;
	}
	public AccountsDAO getAccountsDao() {
		return accountsDao;
	}
	public void setAccountsDao(AccountsDAO accountsDao) {
		this.accountsDao = accountsDao;
	}
	public XsskDAO getXsskDao() {
		return xsskDao;
	}
	public void setXsskDao(XsskDAO xsskDao) {
		this.xsskDao = xsskDao;
	}
	public YushoukDAO getYushoukDao() {
		return yushoukDao;
	}
	public void setYushoukDao(YushoukDAO yushoukDao) {
		this.yushoukDao = yushoukDao;
	}

}
