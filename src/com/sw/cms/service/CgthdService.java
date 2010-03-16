package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.CgthdDAO;
import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.JhdDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.CgfkDesc;
import com.sw.cms.model.Cgthd;
import com.sw.cms.model.CgthdProduct;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.Yufuk;
import com.sw.cms.util.StaticParamDo;

/**
 * <p>采购退货单处理</p>
 * @author liyt
 *
 */
public class CgthdService {
	
	private CgthdDAO cgthdDao;
	private JhdDAO jhdDao;
	private CgfkDAO cgfkDao;
	private CkdDAO ckdDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private SerialNumDAO serialNumDao;
	private SysInitSetDAO sysInitSetDao;
	private ProductKcDAO productKcDao;
	private YufukDAO yufukDao;
	
	/**
	 * 取采购退货单列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgthdList(String con,int curPage, int rowsPerPage){
		return cgthdDao.getCgthdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 保存采购退货单相关信息
	 * @param cgthd
	 * @param cgthdProducts
	 */
	public void saveCgthd(Cgthd cgthd,List cgthdProducts){
		cgthdDao.saveCgthd(cgthd, cgthdProducts);
		
		if(!cgthd.getState().equals("已保存")){
			this.saveCkd(cgthd, cgthdProducts); //生成并保存相应出库单
			this.doCgtk(cgthd);
		}
	}
	
	
	/**
	 * 更新采购退货单相关信息
	 * @param cgthd
	 * @param cgthdProducts
	 */
	public void updateCgthd(Cgthd cgthd,List cgthdProducts){
		
		//如果采购退货单已提交或已出库，不做任何操作
		if(cgthdDao.isCgthdSubmit(cgthd.getId())){
			return;
		}
		
		cgthdDao.updateCgthd(cgthd, cgthdProducts);
		if(!cgthd.getState().equals("已保存")){
			this.saveCkd(cgthd, cgthdProducts); //生成并保存相应出库单
			this.doCgtk(cgthd);
		}
	}
	
	
	/**
	 * 取采购退货单
	 * @param id
	 * @return
	 */
	public Object getCgthd(String id){
		return cgthdDao.getCgthd(id);
	}
	
	
	/**
	 * 取采购退货单产品明细
	 * @param id
	 * @return
	 */
	public List getCgthdProducts(String id){
		return cgthdDao.getCgthdProducts(id);
	}
	
	
	/**
	 * 删除采购退货单
	 * @param id
	 */
	public void delCgthd(String id){
		
		//如果采购退货单已提交或已出库，不做任何操作
		if(cgthdDao.isCgthdSubmit(id)){
			return;
		}
		
		cgthdDao.delCgthd(id);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String updateCgthdID() {
		return cgthdDao.getCgthdID();
	}

	
	
	/**
	 * 保存采购付款信息
	 * @param cgthd
	 */
	private void saveCgfk(Cgthd cgthd){
		Cgfk cgfk = new Cgfk();
		
		String cgfk_id = cgfkDao.getCgfkID();
		
		cgfk.setId(cgfk_id);
		cgfk.setFk_date(cgthd.getTh_date());
		cgfk.setJsr(cgthd.getJsr());
		cgfk.setFkje(0 - cgthd.getTkzje());
		cgfk.setFkzh(cgthd.getZhmc());
		cgfk.setGysbh(cgthd.getProvider_name());
		cgfk.setState("已提交");
		cgfk.setRemark("采购退货单,退货单编号[" + cgthd.getId() + "]");
		cgfk.setCzr(cgthd.getCzr());
		cgfk.setDelete_key(cgthd.getId());
		
		List cgfkDescs = new ArrayList();
		
		CgfkDesc cgfkDesc = new CgfkDesc();
		cgfkDesc.setCgfk_id(cgfk_id);
		cgfkDesc.setJhd_id(cgthd.getId()); //进货单编号中存放，采购退货单编号
		cgfkDesc.setBcfk(cgthd.getTkzje());
		cgfkDesc.setRemark("采购退货单,退货单编号[" + cgthd.getId() + "]");
		
		cgfkDescs.add(cgfkDesc);
		
		cgfkDao.saveCgfk(cgfk, cgfkDescs);
		this.saveAccountDzd(cgfk.getFkzh(), 0-cgfk.getFkje(), cgfk.getCzr(), cgfk.getJsr(), "采购退货付款，编号[" + cgfk.getId() + "]",cgfk.getId());
	}
	
	
	/**
	 * 添加资金交易记录
	 * @param cgfk
	 */
	private void saveAccountDzd(String account_id,double jyje,String czr,String jsr,String remark,String cgfk_id){
		AccountDzd accountDzd = new AccountDzd();
		
		double zhye = 0;
		Map map = accountsDao.getAccounts(account_id);
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}
		
		accountDzd.setAccount_id(account_id);
		accountDzd.setJyje(jyje);
		accountDzd.setZhye(zhye + jyje);
		accountDzd.setRemark(remark);
		accountDzd.setCzr(czr);
		accountDzd.setJsr(jsr);
		accountDzd.setAction_url("viewCgfk.html?id=" + cgfk_id);
		
		accountDzdDao.addDzd(accountDzd);
	}
	
	
	/**
	 * 根据采购退货单生成相应出库单
	 * @param cgthd
	 * @param cgthdProducts
	 */
	private void saveCkd(Cgthd cgthd,List cgthdProducts){
		
		Ckd ckd = new Ckd();
		List ckdProducts = new ArrayList();
		String ckd_id = ckdDao.getCkdID();
		
		ckd.setCkd_id(ckd_id);
		ckd.setClient_name(cgthd.getProvider_name());
		ckd.setCreatdate(cgthd.getTh_date());
		ckd.setCzr(cgthd.getCzr());
		ckd.setMs("采购退货出库，采购退货单编号[" + cgthd.getId() + "]");
		if(cgthd.getState().equals("已出库")){
			ckd.setState("已出库");
		}else{
			ckd.setState("已保存");
		}
		
		ckd.setXsd_id(cgthd.getId());
		ckd.setXsry(cgthd.getJsr());
		ckd.setCk_date(cgthd.getTh_date());
		ckd.setFzr(cgthd.getJsr());
		ckd.setStore_id(cgthd.getStore_id());
		
		if(cgthdProducts != null && cgthdProducts.size()>0){
			for(int i=0;i<cgthdProducts.size();i++){
				
				CgthdProduct cgthdProduct = (CgthdProduct)cgthdProducts.get(i);
				if(cgthdProduct != null){
					if(cgthdProduct.getProduct_name() != null && !cgthdProduct.getProduct_name().equals("")){
						CkdProduct ckdProduct = new CkdProduct();
						
						ckdProduct.setCkd_id(ckd_id);
						ckdProduct.setProduct_id(cgthdProduct.getProduct_id());
						ckdProduct.setProduct_name(cgthdProduct.getProduct_name());
						ckdProduct.setProduct_xh(cgthdProduct.getProduct_xh());
						ckdProduct.setNums(cgthdProduct.getNums());
						ckdProduct.setPrice(cgthdProduct.getTh_price());
						ckdProduct.setJgtz(0);
						ckdProduct.setCbj(0);
						ckdProduct.setQz_serial_num(cgthdProduct.getQz_serial_num());
						
						ckdProducts.add(ckdProduct);
					}
				}
			}
		}
		
		ckdDao.saveCkd(ckd, ckdProducts);
		
		if(ckd.getState().equals("已出库")){
			this.updateProductKc(ckd, ckdProducts); //修改库存
			
			//只有在系统正式使用后才去处理序列号
			//if(sysInitSetDao.getQyFlag().equals("1")){			
			this.updateSerialNum(ckd, ckdProducts); //处理序列号
			//}			
		}
		
	}
	
	
	/**
	 * <p>处理采购退货单相关操作</p>
	 * <p>分为两种情况“1：现金；2：冲抵往来”</p>
	 * @param cgthd
	 */
	private void doCgtk(Cgthd cgthd){
		
		if(cgthd.getTkfs().equals("现金")){  //如果是现金退货1:生成相应采购付款信息；2:更新相应账号金额
			
			//生成相应采购付款信息
			this.saveCgfk(cgthd);
			
			//更新相应账号金额
			accountsDao.addAccountJe(cgthd.getZhmc(), cgthd.getTkzje());
			
		}else{  
			
			//如果是冲抵往来，刚将应付款转为预付款
			this.addYufuk(cgthd);
		}
	}
	
	
	/**
	 * <p>处理序列号</p>
	 * <p>两种情况调用</p>
	 * <p>一、销售出库；二、采购退货出库</p>
	 * <p></p>
	 * @param ckd
	 * @param ckdProducts
	 */
	private void updateSerialNum(Ckd ckd,List ckdProducts){
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
				if(ckdProduct != null){
					if(!ckdProduct.getProduct_id().equals("") && !ckdProduct.getQz_serial_num().equals("")){
						String[] arryNums = (ckdProduct.getQz_serial_num()).split(",");
						
						SerialNumMng serialNumMng = new SerialNumMng();
						SerialNumFlow serialNumFlow = new SerialNumFlow();
						
						String state = "";
						String yw_url = "";
						String yw_type = "";
						String xsd_id = ckd.getXsd_id();
						if(xsd_id.indexOf("XS") != -1){
							state = "已售";
							yw_url = "viewXsd.html?id=";
							yw_type = "销售";
						}else{
							state = "已退货";
							yw_url = "viewCgthd.html?id=";
							yw_type = "采购退货";
						}
						
						for(int k=0;k<arryNums.length;k++){
							serialNumMng.setProduct_id(ckdProduct.getProduct_id());
							serialNumMng.setProduct_name(ckdProduct.getProduct_name());
							serialNumMng.setProduct_xh(ckdProduct.getProduct_xh());
							serialNumMng.setSerial_num(arryNums[k]);
							serialNumMng.setState(state);
							serialNumMng.setStore_id("");
							serialNumMng.setYj_flag("0");
							serialNumDao.updateSerialNumState(serialNumMng); //更新序列号状态
							
							serialNumFlow.setClient_name(StaticParamDo.getClientNameById(ckd.getClient_name()));
							serialNumFlow.setCzr(ckd.getCzr());
							
							serialNumFlow.setYwtype(yw_type);
							
							serialNumFlow.setFs_date(ckd.getCk_date());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(ckd.getXsry()));
							serialNumFlow.setKf_dj_id(ckd.getCkd_id());
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewCkd.html?ckd_id=");
							serialNumFlow.setYw_dj_id(ckd.getXsd_id());							
							serialNumFlow.setYw_url(yw_url);
							
							serialNumDao.saveSerialFlow(serialNumFlow);  //保存序列号流转过程
						}
					}
				}
			}
		}
	}	
	
	
	/**
	 * 更新产品库存
	 * @param lsd
	 * @param lsdProducts
	 */
	private void updateProductKc(Ckd ckd,List ckdProducts){
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
				if(ckdProduct != null){
					if(!ckdProduct.getProduct_id().equals("") && !ckdProduct.getProduct_name().equals("")){
						productKcDao.updateProductKc(ckd.getStore_id(),ckdProduct.getProduct_id(), ckdProduct.getNums());
					}
				}
			}
		}
	}
	
	
	/**
	 * 添加预付款
	 * @param yfk
	 */
	private void addYufuk(Cgthd cgthd){
		Yufuk yfk = new Yufuk();
		
		yfk.setClient_name(cgthd.getProvider_name());
		yfk.setHjje(cgthd.getTkzje());
		yfk.setJsje(0);
		yfk.setJs_date(cgthd.getTh_date());
		yfk.setYwdj_id(cgthd.getId());
		yfk.setYwdj_name("应付转预付");
		yfk.setCzr(cgthd.getCzr());
		yfk.setRemark("冲抵往来退货，应付转预付，采购退货单编号[" + cgthd.getId() + "]");
		yfk.setUrl("viewCgthd.html?id=");
		
		yufukDao.saveYufuk(yfk);
	}
	

	public CgthdDAO getCgthdDao() {
		return cgthdDao;
	}


	public void setCgthdDao(CgthdDAO cgthdDao) {
		this.cgthdDao = cgthdDao;
	}


	public CgfkDAO getCgfkDao() {
		return cgfkDao;
	}


	public void setCgfkDao(CgfkDAO cgfkDao) {
		this.cgfkDao = cgfkDao;
	}


	public CkdDAO getCkdDao() {
		return ckdDao;
	}


	public void setCkdDao(CkdDAO ckdDao) {
		this.ckdDao = ckdDao;
	}


	public JhdDAO getJhdDao() {
		return jhdDao;
	}


	public void setJhdDao(JhdDAO jhdDao) {
		this.jhdDao = jhdDao;
	}


	public AccountsDAO getAccountsDao() {
		return accountsDao;
	}


	public void setAccountsDao(AccountsDAO accountsDao) {
		this.accountsDao = accountsDao;
	}


	public AccountDzdDAO getAccountDzdDao() {
		return accountDzdDao;
	}


	public void setAccountDzdDao(AccountDzdDAO accountDzdDao) {
		this.accountDzdDao = accountDzdDao;
	}


	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}


	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}


	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}


	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}


	public YufukDAO getYufukDao() {
		return yufukDao;
	}


	public void setYufukDao(YufukDAO yufukDao) {
		this.yufukDao = yufukDao;
	}

}
