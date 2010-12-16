package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.ProductSaleFlowDAO;
import com.sw.cms.dao.RkdDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.ThdDAO;
import com.sw.cms.dao.XsdDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.YushoukDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Page;
import com.sw.cms.model.ProductSaleFlow;
import com.sw.cms.model.Rkd;
import com.sw.cms.model.RkdProduct;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.Thd;
import com.sw.cms.model.ThdProduct;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.XsskDesc;
import com.sw.cms.model.Yushouk;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;

/**
 * <p>销售退货单处理</p>
 * 
 * @author liyt
 *
 */
public class ThdService {
	
	private ThdDAO thdDao;
	private ProductKcDAO productKcDao;
	private XsskDAO xsskDao;
	private RkdDAO rkdDao;
	private AccountsDAO accountsDao;
	private XsdDAO xsdDao;
	private AccountDzdDAO accountDzdDao;
	private SerialNumDAO serialNumDao;
	private YushoukDAO yushoukDao;
	private ProductSaleFlowDAO productSaleFlowDao;
	
	/**
	 * 查询退货单列表，带分页
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getThdList(String con,int curPage, int rowsPerPage){		
		return thdDao.getThdList(con, curPage, rowsPerPage);		
	}
	
	
	/**
	 * 保存退货单信息
	 * @param thd
	 * @param thdProducts
	 */
	public void saveThd(Thd thd,List thdProducts){
		
		//如果退货单已经提交或是已经入库，不做任何处理
		if(thdDao.isThdSubmit(thd.getThd_id())){
			return;
		}
		
		thdDao.saveThd(thd, thdProducts);		
		
		if(!thd.getState().equals("已保存")){
			this.saveRkd(thd, thdProducts); //生成相应入库单
			
			this.updateThdJe(thd);			//处理退货单金额相关内容
			
			this.addProductSaleFlow(thd, thdProducts);  //处理退货单商品交易情况
		}
	}
	
	
	
	/**
	 * 更新退货单信息
	 * @param thd
	 * @param thdProducts
	 */
	public void updateThd(Thd thd,List thdProducts){
		
		//如果退货单已经提交或是已经入库，不做任何处理
		if(thdDao.isThdSubmit(thd.getThd_id())){
			return;
		}
		
		thdDao.updateThd(thd, thdProducts);
		
		// 退货分为两种方式（现金、冲抵往来）
		if(!thd.getState().equals("已保存")){
			
			this.saveRkd(thd, thdProducts); //生成相应入库单	
			
			this.updateThdJe(thd);          //处理退货单金额相关内容
			
			this.addProductSaleFlow(thd, thdProducts);  //处理退货单商品交易情况
		}
		
	}
	
	
	/**
	 * 根据退货单ID返回商品明细
	 * @param thd_id
	 * @return
	 */
	public List getThdProducts(String thd_id){
		return thdDao.getThdProducts(thd_id);
	}
	
	
	/**
	 * 根据退货单ID返回退货单相关信息
	 */
	public Object getThd(String thd_id){
		return thdDao.getThd(thd_id);
	}

	/**
	 * 删除退货单相关信息
	 * @param thd_id
	 */
	public void delThd(String thd_id){
		
		//如果退货单已经提交或是已经入库，不做任何处理
		if(thdDao.isThdSubmit(thd_id)){
			return;
		}
		
		thdDao.delThd(thd_id);
	}
	
	
	/**
	 * 取当前可用的退货单号
	 * @return
	 */
	public String updateThdId(){
		return thdDao.getThdID();
	}
	
	
	
	/**
	 * 生成相关入库单并保存
	 * @param jhd
	 * @param JhdProducts
	 */
	private void saveRkd(Thd thd,List thdProducts){
		Rkd rkd = new Rkd();
		List rkdProducts = new ArrayList();
		
		String rkd_id = rkdDao.getRkdID();//当前可用入库单号
		
		rkd.setRkd_id(rkd_id); 
		rkd.setJhd_id(thd.getThd_id());//将退货单编号做为进货单号
		rkd.setCreatdate(thd.getTh_date());
		rkd.setState("已入库");		
		rkd.setMs("退货入库，退货日期：" + thd.getTh_date() + ",退货单编号 [" + thd.getThd_id() + "]");
		rkd.setCgfzr(thd.getTh_fzr());
		rkd.setCzr(thd.getCzr());
		rkd.setClient_name(thd.getClient_name());
		rkd.setRk_date(thd.getTh_date());
		rkd.setFzr(thd.getTh_fzr());
		rkd.setStore_id(thd.getStore_id());
		
		if(thdProducts != null && thdProducts.size()>0){
			for(int i=0;i<thdProducts.size();i++){
				
				ThdProduct thdProduct = (ThdProduct)thdProducts.get(i);
				if(thdProduct != null){
					if(thdProduct.getProduct_name() != null && !thdProduct.getProduct_name().equals("")){
						RkdProduct rkdProduct = new RkdProduct();
						
						rkdProduct.setProduct_id(thdProduct.getProduct_id());
						rkdProduct.setProduct_name(thdProduct.getProduct_name());
						rkdProduct.setProduct_xh(thdProduct.getProduct_xh());
						
						//设置入库商品的价格
						//一、如果销售退货与原单关联时，退货成本取原销售出库时成本
						//二、取销售退回前的结余平均成本价
						//三、如结余价为0，则取其前最后一次入库成本
						//四、还为0，则取退回价
						
						double price = thdProduct.getCbj(); //取退货成本价，如果是关联销售单或零售单则取当时销售时的成本价，如果不关联则取退货时库存成本价
						
						//如果价格还为0，则取最后一次入库时的价格
						if(price == 0){
							price = productKcDao.getLastProductRkCbj(thdProduct.getProduct_id());
						}
						
						//如果还为0，则取退回价
						if(price == 0){
							price = thdProduct.getTh_price();
						}
						
						
						rkdProduct.setPrice(price);
						
						rkdProduct.setNums(thdProduct.getNums());
						rkdProduct.setRkd_id(rkd_id);
						rkdProduct.setRemark(thdProduct.getRemark());
						rkdProduct.setQz_serial_num(thdProduct.getQz_serial_num());
						
						rkdProducts.add(rkdProduct);
					}
				}
			}
		}
		
		rkdDao.saveRkd(rkd, rkdProducts);
		
		//更新库存数及成本价
		productKcDao.updateProductKc(rkd, rkdProducts);			
		//处理序列号
		this.updateSerialNum(rkd, rkdProducts);		
	}
	
	
	/**
	 * 根据退货单生成商品销售流水
	 * @param thd
	 * @param thdProducts
	 */
	private void addProductSaleFlow(Thd thd,List thdProducts){
		if(thdProducts != null && thdProducts.size()>0){
			for(int i=0;i<thdProducts.size();i++){
				
				ThdProduct thdProduct = (ThdProduct)thdProducts.get(i);
				if(thdProduct != null && thdProduct.getProduct_name() != null && !thdProduct.getProduct_name().equals("")){
					
					ProductSaleFlow info = new ProductSaleFlow();
					
					info.setId(thd.getThd_id());
					info.setYw_type("退货单");
					info.setClient_name(thd.getClient_name());
					info.setXsry(thd.getTh_fzr());
					info.setCz_date(DateComFunc.getToday());
					info.setProduct_id(thdProduct.getProduct_id());
					info.setNums(0-thdProduct.getNums());
					info.setPrice(thdProduct.getTh_price());
					info.setHjje(0-thdProduct.getXj());
					info.setDwcb(thdProduct.getCbj());
					info.setCb(0-(thdProduct.getCbj()*thdProduct.getNums()));
					info.setDwkhcb(thdProduct.getKh_cbj());
					info.setKhcb(0-(thdProduct.getKh_cbj()*thdProduct.getNums()));
					info.setDwygcb(thdProduct.getYgcbj());
					info.setYgcb(0-(thdProduct.getYgcbj()*thdProduct.getNums()));
					info.setSd(thdProduct.getSd());
					info.setBhsje(0-(thdProduct.getXj() / (1 + thdProduct.getSd()/100)));
					info.setGf(thdProduct.getGf());
					info.setDs((0-thdProduct.getDs())*thdProduct.getNums());
					info.setBasic_ratio(thdProduct.getBasic_ratio());
					info.setOut_ratio(thdProduct.getOut_ratio());
					info.setLsxj(0-(thdProduct.getLsxj()*thdProduct.getNums()));
					info.setSfcytc(thdProduct.getSfcytc());
					
					productSaleFlowDao.insertProductSaleFlow(info);
				}
			}
		}
	}
	
	
	/**
	 * 退单单金额处理
	 * 只真正处理账户金额时采添加销售收款信息
	 * @param thd
	 */
	private void updateThdJe(Thd thd){
		//销售订单
		if(thd.getYw_type().equals("1"))
		{
		  if(thd.getType().equals("现金")){  //现金退货
			
			//添加相应销售收款信息，负值
			this.saveXssk(thd);
			
			//更新相应账号余额
			this.updateAccountJe(thd);
			
		  }else{ //冲抵往来退货
			
			//应收转预收
			this.addYushouk(thd);
		  }
		}
		//零售单
		if(thd.getYw_type().equals("2"))
		{
		  if(thd.getTypeLs().equals("现金")){  //现金退货
			
			//添加相应销售收款信息，负值
			this.saveXssk(thd);
			
			//更新相应账号余额
			this.updateAccountJe(thd);
			
		  }else{ //冲抵往来退货
			
			//应收转预收
			this.addYushouk(thd);
		  }
		}
	}
	
	
	/**
	 * 保存销售收款信息
	 * @param lsd
	 */
	private void saveXssk(Thd thd){
		Xssk xssk = new Xssk();
		
		String xssk_id = xsskDao.getXsskID();
		xssk.setId(xssk_id);
		xssk.setSk_date(thd.getTh_date());
		xssk.setClient_name(thd.getClient_name());
		xssk.setJsr(thd.getTh_fzr());
		xssk.setSkzh(thd.getTkzh());
		xssk.setSkje(0-thd.getThdje());
		xssk.setState("已提交");
		xssk.setCzr(thd.getCzr());
		xssk.setRemark(thd.getTh_date() + "退货单，退货单编号 [" + thd.getThd_id() + "]");
		xssk.setDelete_key(thd.getThd_id());
		
		List xsskDescs = new ArrayList();
		
		XsskDesc xsskDesc = new XsskDesc();
		xsskDesc.setXsd_id(thd.getThd_id());
		xsskDesc.setXssk_id(xssk_id);
		xsskDesc.setBcsk(thd.getThdje());
		xsskDesc.setRemark( "退货单，退货单编号 [" + thd.getThd_id() + "]");
		
		xsskDescs.add(xsskDesc);
		
		xsskDao.saveXssk(xssk, xsskDescs);
		
		this.saveAccountDzd(xssk.getId(),xssk.getSkzh(), xssk.getSkje(), xssk.getCzr(), xssk.getJsr(), "销售收款，编号[" + xssk.getId() + "]");
		
	}
	
	
	
	/**
	 * 更新账户信息
	 * @param lsd
	 */
	private void updateAccountJe(Thd thd){
		String account_id = thd.getTkzh();
		double je = thd.getThdje();
		
		
		accountsDao.updateAccountJe(account_id, je);
	}
	
	
	
	/**
	 * 添加资金交易记录
	 * @param cgfk
	 */
	private void saveAccountDzd(String id,String account_id,double jyje,String czr,String jsr,String remark){
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
		accountDzd.setAction_url("viewXssk.html?id=" + id);
		
		accountDzdDao.addDzd(accountDzd);
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
	 * 添加预收款
	 * @param ysk
	 */
	private void addYushouk(Thd thd){
		Yushouk ysk = new Yushouk();
		
		ysk.setClient_name(thd.getClient_name());
		ysk.setHjje(thd.getThdje());
		ysk.setJsje(0);
		ysk.setJs_date(thd.getTh_date());
		ysk.setUrl("viewThd.html?thd_id=");
		ysk.setYwdj_id(thd.getThd_id());
		ysk.setYwdj_name("应收转预收");
		ysk.setCzr(thd.getCzr());
		ysk.setRemark("冲抵往来退货，应收转预收，退货单编号[" + thd.getThd_id() + "]");
		
		yushoukDao.saveYushouk(ysk);
	}
	

	public ThdDAO getThdDao() {
		return thdDao;
	}

	public void setThdDao(ThdDAO thdDao) {
		this.thdDao = thdDao;
	}


	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}


	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}


	public RkdDAO getRkdDao() {
		return rkdDao;
	}


	public void setRkdDao(RkdDAO rkdDao) {
		this.rkdDao = rkdDao;
	}


	public XsskDAO getXsskDao() {
		return xsskDao;
	}


	public void setXsskDao(XsskDAO xsskDao) {
		this.xsskDao = xsskDao;
	}


	public AccountsDAO getAccountsDao() {
		return accountsDao;
	}


	public void setAccountsDao(AccountsDAO accountsDao) {
		this.accountsDao = accountsDao;
	}


	public XsdDAO getXsdDao() {
		return xsdDao;
	}


	public void setXsdDao(XsdDAO xsdDao) {
		this.xsdDao = xsdDao;
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


	public YushoukDAO getYushoukDao() {
		return yushoukDao;
	}


	public void setYushoukDao(YushoukDAO yushoukDao) {
		this.yushoukDao = yushoukDao;
	}


	public ProductSaleFlowDAO getProductSaleFlowDao() {
		return productSaleFlowDao;
	}


	public void setProductSaleFlowDao(ProductSaleFlowDAO productSaleFlowDao) {
		this.productSaleFlowDao = productSaleFlowDao;
	}

}
