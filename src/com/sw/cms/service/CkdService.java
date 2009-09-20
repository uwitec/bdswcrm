package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.CgthdDAO;
import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.XsdDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.XsdProduct;
import com.sw.cms.model.Xssk;
import com.sw.cms.util.StaticParamDo;

/**
 * 出库单操作
 * @author jinyanni
 *
 */
public class CkdService {
	
	private CkdDAO ckdDao;
	private StoreDAO storeDao;
	private CgthdDAO cgthdDao;
	private ProductKcDAO productKcDao;
	private XsdDAO xsdDao;
	private SerialNumDAO serialNumDao;
	private SysInitSetDAO sysInitSetDao;
	private XsskDAO xsskDao;
	private AccountDzdDAO accountDzdDao;
	private AccountsDAO accountsDao;
	private CgfkDAO cgfkDao;
	private YufukDAO yufukDao;
	private ProductDAO productDao;
	

	
	/**
	 * 取列表（包括分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCkdList(String con,int curPage, int rowsPerPage){
		return ckdDao.getCkdList(con, curPage, rowsPerPage);
	}
	
	
	
	/**
	 * <p>保存出库单信息</p>
	 * 
	 * @param ckd
	 * @param ckdProducts
	 */
	public void saveCkd(Ckd ckd,List ckdProducts){
		
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct cdkProduct = (CkdProduct)ckdProducts.get(i);
				if(cdkProduct != null){
					if(!cdkProduct.getProduct_id().equals("")){
						XsdProduct xsdProduct = (XsdProduct)xsdDao.getXsdProductInfo(ckd.getXsd_id(), cdkProduct.getProduct_id());
						if(xsdProduct != null){
							cdkProduct.setCbj(xsdProduct.getCbj());
							cdkProduct.setPrice(xsdProduct.getPrice());
							cdkProduct.setJgtz(xsdProduct.getJgtz());
						}else{
							cdkProduct.setCbj(0);
							cdkProduct.setPrice(0);
							cdkProduct.setJgtz(0);
						}
					}
				}
			}
		}
		
		
		ckdDao.saveCkd(ckd, ckdProducts);
		
		if(ckd.getState().equals("已出库")){
			
			this.updateProductKc(ckd, ckdProducts); //修改库存
			
			//只有在系统正式使用后才去处理序列号
			//系统正式启用前，不对强制序列号做限制，但输入的序列号系统同样给予处理
			//if(sysInitSetDao.getQyFlag().equals("1")){				
			this.updateSerialNum(ckd, ckdProducts); //处理序列号
			//}
			
			this.updateXsdState(ckd.getXsd_id(), "已出库",ckd.getYsfs(),ckd.getCx_tel(),ckd.getJob_no(),ckd.getSend_time(),ckd.getStore_id(),ckd.getFzr(),ckd.getCk_date());
		}
	}
		
	
	
	/**
	 * 更新出库单信息
	 * @param ckd
	 * @param ckdProducts
	 */
	public void updateCkd(Ckd ckd,List ckdProducts){
		
		//设置出库单对应商品的成本价
		List xsdProducts = new ArrayList();
		
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct cdkProduct = (CkdProduct)ckdProducts.get(i);
				if(cdkProduct != null){
					if(!cdkProduct.getProduct_id().equals("")){
						XsdProduct xsdProduct = (XsdProduct)xsdDao.getXsdProductInfo(ckd.getXsd_id(), cdkProduct.getProduct_id());
						Product product = (Product)productDao.getProductById(cdkProduct.getProduct_id());
						
						xsdProduct.setCbj(product.getPrice());
						xsdProduct.setKh_cbj(product.getKhcbj());
						xsdProducts.add(xsdProduct);
						
						cdkProduct.setCbj(product.getPrice());  //成本价取当前库存的成本价
						if(xsdProduct != null){
							cdkProduct.setPrice(xsdProduct.getPrice());  //销售价格取销售订单相当价格
							cdkProduct.setJgtz(xsdProduct.getJgtz());
						}else{
							cdkProduct.setPrice(0);
							cdkProduct.setJgtz(0);
						}
					}
				}
			}
		}
		
		//更新出库单
		ckdDao.updateCkd(ckd, ckdProducts);
		
		if(ckd.getState().equals("已出库")){
			
			this.updateProductKc(ckd, ckdProducts); //修改库存
			
			//修改相应销售单实际成交信息及物流相关信息
			this.updateXsdSjcjInfo(ckd, ckdProducts);
			

			//系统正式启用前，不对强制序列号做限制，但输入的序列号系统同样给予处理			
			this.updateSerialNum(ckd, ckdProducts);   //处理序列号
			
			//如果是销售单，处理销售单状态
			if(ckd.getXsd_id().indexOf("XS") != -1){
				this.updateXsdState(ckd.getXsd_id(), "已出库",ckd.getYsfs(),ckd.getCx_tel(),ckd.getJob_no(),ckd.getSend_time(),ckd.getStore_id(),ckd.getFzr(),ckd.getCk_date());
				
				//修改销售单对应的成本价及考核成本价
				if(xsdProducts != null && xsdProducts.size() > 0){
					xsdDao.updateXsdProducts(xsdProducts);
				}
			}
			
			//更新相应分销订单的物流状态
			this.updateFxddState(ckd.getXsd_id(), "已出库",ckd.getYsfs(),ckd.getCx_tel(),ckd.getJob_no(),ckd.getSend_time());
			
			//如果采购退货单,修改采购退货单状态及退回标记
			if(ckd.getXsd_id().indexOf("CGTH") != -1){
				cgthdDao.updateCgthdState(ckd.getXsd_id(), "已出库");
			}
			

		}
	}

	
	
	/**
	 * 根据出库单ID获取产品列表
	 * @param ckd_id
	 * @return
	 */
	public List getCkdProducts(String ckd_id){
		return ckdDao.getCkdProducts(ckd_id);
	}
	
	
	/**
	 * 根据出库单ID获取出库单信息
	 * @param ckd_id
	 * @return
	 */
	public Object getCkd(String ckd_id){
		return ckdDao.getCkd(ckd_id);
	}
	
	
	/**
	 * 删除出库单信息
	 * @param ckd_id
	 */
	public void delCkd(String ckd_id){
		ckdDao.delCkd(ckd_id);
	}
	
	
	/**
	 * 出库单退回订单操作
	 * 两种情况：一、销售订单；二、采购退货单
	 * 对两种情况分别进行处理
	 * @param ckd
	 */
	public void doTh(Ckd ckd){
		
		//删除出相应出库单
		ckdDao.delCkd(ckd.getCkd_id());
		
		String xsd_id = ckd.getXsd_id();
		
		//如果是销售单，
		if(xsd_id.indexOf("XS") != -1){
			
			//修改销售单状态及退回标记
			xsdDao.updateXsdAfterCkdTh(xsd_id, "已保存", "1");
			
			//查看销售订单提交时，是否有直接收款
			//如果订单提交时有直接收款，需要删除收款信息信息
			Xssk xssk = xsskDao.getXsskByDeleteKey(xsd_id);	
			
			if(xssk != null){
				
				//删除销售收款信息
				xsskDao.delXssk(xssk.getId());
				
				//删除账户流水信息
				accountDzdDao.delDzd(xssk.getId());
				
				//减账户金额
				accountsDao.updateAccountJe(xssk.getSkzh(), xssk.getSkje());
			}
		}
		
		//如果采购退货单,修改采购退货单状态及退回标记
		if(xsd_id.indexOf("CGTH") != -1){
			cgthdDao.updateCgthdAfterCkdTh(xsd_id, "已保存", "1");
			
			//查看采购退货单提交时，是否现金退货
			//如果采购退货单是现金退货，需要删除相关付款信息
			Cgfk cgfk = cgfkDao.getCgfkByDeleteKey(xsd_id);
			
			if(cgfk != null){
				
				//删除采购付款信息
				cgfkDao.delCgfk(cgfk.getId());
				
				//删除账户流水信息
				accountDzdDao.delDzd(cgfk.getId());
				
				//加账户金额(负值)
				accountsDao.addAccountJe(cgfk.getFkzh(), cgfk.getFkje());
			}
			
			//如果存应付转预款信息则清除
			yufukDao.delYufuk(xsd_id);
		}
	}
	
	
	/**
	 * 根据对应销售单编号查看是否存在相应的出库单
	 * @param xsd_id
	 * @return
	 */
	public boolean isCkdExist(String xsd_id){
		return ckdDao.isCkdExist(xsd_id);
	}
	
	
	/**
	 * 查看出库单是否已经提交
	 * @param ckd_id
	 * @return
	 */
	public boolean isCkdSubmit(String ckd_id){
		return ckdDao.isCkdSubmit(ckd_id);
	}
	
	/**
	 * 取所有库房列表
	 * @return
	 */
	public List getStoreList(){
		return storeDao.getAllStoreList();
	}
	
	
	/**
	 * 取可用的出库单号
	 * @return
	 */
	public String updateCkdId(){
		return ckdDao.getCkdID();
	}
	
	
	/**
	 * 判断库存量是否满足出库需要
	 * @param ckd
	 * @param ckdProducts
	 */
	public String checkKc(Ckd ckd,List ckdProducts){
		String msg = "";
		String store_id = ckd.getStore_id();
		
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
				if(ckdProduct != null){
					if(!ckdProduct.getProduct_id().equals("") && !ckdProduct.getProduct_name().equals("")){
						String product_id = ckdProduct.getProduct_id();
						
						//判断商品是否是库存商品,只仍库存商品才进行库存数量判断
						Product product = (Product)productDao.getProductById(product_id);
						if(product.getProp().equals("库存商品")){	
							int cknums = ckdProduct.getNums();  //要出库数量
							int kcnums = productKcDao.getKcNums(product_id, store_id);//库存数量
							
							if(cknums>kcnums){
								msg += ckdProduct.getProduct_name() + " 当前库存为：" + kcnums + "  无法出库，请先调拨\n";
							}
						}
					}
				}
			}
		}
		
		return msg;
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
	 * 修改相应销售单实际成交数及金额
	 * @param ckd
	 * @param ckdProducts
	 */
	private void updateXsdSjcjInfo(Ckd ckd,List ckdProducts){
		double sjcjje = 0; //销售单实际成交金额
		String xsd_id = ckd.getXsd_id();
		
		//如果相关业务单据不是销售单时退出
		if(xsd_id.indexOf("XS") == -1){
			return;
		}
		
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
				if(ckdProduct != null){
					if(!ckdProduct.getProduct_id().equals("") && !ckdProduct.getProduct_name().equals("")){
						double sjcj_xj = ckdProduct.getNums() * (ckdProduct.getPrice() + ckdProduct.getJgtz());
						sjcjje += sjcj_xj;
						xsdDao.updateXsdSjcjNums(xsd_id, ckdProduct.getProduct_id(), ckdProduct.getNums(), sjcj_xj);
					}
				}
			}
		}
		xsdDao.updateXsdSjcjje(xsd_id, sjcjje);
	}
	
	
	/**
	 * 更新相应销售单状态
	 * @param xsd_id     销售单编号
	 * @param state      状态
	 * @param ysfs       运输方式
	 * @param cx_tel     查询电话
	 * @param job_no     货单号
	 * @param store_id   出货库房
	 * @param ck_jsr     出库经手人
	 * @param send_time  发货时间
	 */
	private void updateXsdState(String xsd_id,String state,String ysfs,String cx_tel,String job_no,String send_time,String store_id,String ck_jsr,String ck_date){
		xsdDao.updateXsdState(xsd_id, state,ysfs,cx_tel,job_no,send_time, store_id, ck_jsr,ck_date);
	}
	
	
	/**
	 * 更新相应分销订单状态
	 * @param fxdd_id
	 * @param state
	 */
	private void updateFxddState(String fxdd_id,String state,String ysfs,String cx_tel,String job_no,String send_time){
		xsdDao.updateFxddState(fxdd_id, state,ysfs,cx_tel,job_no,send_time);
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
							serialNumFlow.setTel(ckd.getTel());
							serialNumFlow.setCzr(ckd.getCzr());							
							serialNumFlow.setYwtype(yw_type);							
							serialNumFlow.setFs_date(ckd.getCk_date());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(ckd.getXsry()));
							serialNumFlow.setKf_dj_id(ckd.getCkd_id());
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewCkd.html?ckd_id=");
							serialNumFlow.setYw_dj_id(ckd.getXsd_id());							
							serialNumFlow.setYw_url(yw_url);
							serialNumFlow.setXsdj(ckdProduct.getPrice() + ckdProduct.getJgtz());
							
							serialNumDao.saveSerialFlow(serialNumFlow);  //保存序列号流转过程
						}
					}
				}
			}
		}
	}
	
	
	public void setCkdDao(CkdDAO ckdDao) {
		this.ckdDao = ckdDao;
	}
	public void setStoreDao(StoreDAO storeDao) {
		this.storeDao = storeDao;
	}

	public CkdDAO getCkdDao() {
		return ckdDao;
	}
	public StoreDAO getStoreDao() {
		return storeDao;
	}
	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}
	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}
	public XsdDAO getXsdDao() {
		return xsdDao;
	}
	public void setXsdDao(XsdDAO xsdDao) {
		this.xsdDao = xsdDao;
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



	public CgthdDAO getCgthdDao() {
		return cgthdDao;
	}



	public void setCgthdDao(CgthdDAO cgthdDao) {
		this.cgthdDao = cgthdDao;
	}



	public XsskDAO getXsskDao() {
		return xsskDao;
	}



	public void setXsskDao(XsskDAO xsskDao) {
		this.xsskDao = xsskDao;
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



	public CgfkDAO getCgfkDao() {
		return cgfkDao;
	}



	public void setCgfkDao(CgfkDAO cgfkDao) {
		this.cgfkDao = cgfkDao;
	}



	public YufukDAO getYufukDao() {
		return yufukDao;
	}



	public void setYufukDao(YufukDAO yufukDao) {
		this.yufukDao = yufukDao;
	}



	public ProductDAO getProductDao() {
		return productDao;
	}



	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}

}
