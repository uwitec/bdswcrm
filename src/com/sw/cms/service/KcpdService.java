package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.KcpdDAO;
import com.sw.cms.dao.KcpdYkTblDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Kcpd;
import com.sw.cms.model.KcpdDesc;
import com.sw.cms.model.KcpdYkTbl;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.dao.SerialNumDAO;

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
	private SerialNumDAO serialNumDao;
	
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
			this.updateSerialNum(kcpd, kcpdDescs); //处理序列号
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
			this.updateSerialNum(kcpd, kcpdDescs); //处理序列号
		}
	}
	
	/**
	 * 判断序列号是否满足出库需要
	 * @param kcpd
	 * @param kcpdDesc
	 */
	public String checkXlh(Kcpd kcpd,List kcpdDescs){
		String msg = "";
		String store_id = kcpd.getStore_id();
		
		if(kcpdDescs != null && kcpdDescs.size()>0){
			for(int i=0;i<kcpdDescs.size();i++){
				KcpdDesc kcpdDesc = (KcpdDesc)kcpdDescs.get(i);
				if(kcpdDesc != null){
					if(!kcpdDesc.getProduct_id().equals("") && !kcpdDesc.getProduct_name().equals("") && kcpdDesc.getQz_flag().equals("是")  && kcpdDesc.getYk()<0){
						String product_id = kcpdDesc.getProduct_id();
						String[] arryNums = (kcpdDesc.getQz_serial_num()).split(",");
						
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
								  msg += kcpdDesc.getProduct_name() + " 序列号为：" + serialNum + " 不存在，请确认后再进行盘点处理\n";
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
	 * 查看库存盘点单是否已经提交
	 * @param id
	 * @return
	 */
	public boolean isKcpdSubmit(String id){
		return kcpdDao.isKcpdSubmit(id);
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
	 *根据条件取库存商品列表
	 * @param con
	 * @return
	 */
	public Page getAllProductKc(String con,int curPage, int rowsPerPage){
		return productKcDao.getAllProductKc(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 取所有库存商品列表（包括零库存商品）<BR>
	 * 库存盘点时调用，其它调用会有问题
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getAllProductKcIncludeZero(String con,String store_id,int curPage, int rowsPerPage){
		return productKcDao.getAllProductKcIncludeZero(con, store_id, curPage, rowsPerPage);
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
	
	
	/**
	 * <p>处理序列号</p>
	 * <p>两种情况调用</p>
	 * <p>一、销售出库；二、采购退货出库</p>
	 * <p></p>
	 * @param kcpd
	 * @param kcpdDescs
	 */
	private void updateSerialNum(Kcpd kcpd,List kcpdDescs){
		if(kcpdDescs != null && kcpdDescs.size()>0){
			for(int i=0;i<kcpdDescs.size();i++){
				KcpdDesc kcpdDesc = (KcpdDesc)kcpdDescs.get(i);
				if(kcpdDesc != null){
					if(!kcpdDesc.getProduct_id().equals("") && !kcpdDesc.getQz_serial_num().equals("")){
						String[] arryNums = (kcpdDesc.getQz_serial_num()).split(",");
						SerialNumMng serialNumMng = new SerialNumMng();
						SerialNumFlow serialNumFlow = new SerialNumFlow();
						
						String state = "";
						
						int yk = kcpdDesc.getYk();
						if(yk<0){
							state = "";							
						}else{
							state = "在库";							
						}
						
						for(int k=0;k<arryNums.length;k++){
							
							serialNumMng.setProduct_id(kcpdDesc.getProduct_id());
							serialNumMng.setProduct_name(kcpdDesc.getProduct_name());
							serialNumMng.setProduct_xh(kcpdDesc.getProduct_xh());
							serialNumMng.setSerial_num(arryNums[k]);
							serialNumMng.setState(state);
							serialNumMng.setStore_id(kcpd.getStore_id());
							serialNumMng.setYj_flag("0");
							
                            serialNumDao.updateSerialNumState(serialNumMng); //更新序列号状态
							
							serialNumFlow.setClient_name("");
							serialNumFlow.setCzr(kcpd.getCzr());							
							serialNumFlow.setYwtype("盘点");							
							serialNumFlow.setFs_date(kcpd.getPdrq());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(kcpd.getPdr()));
							serialNumFlow.setKf_dj_id(kcpd.getId());
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewKcpd.html?id=");
							serialNumFlow.setYw_dj_id("");							
							serialNumFlow.setYw_url("");	                            
													
							serialNumDao.saveSerialFlow(serialNumFlow);  //保存序列号流转过程
						}
					}
				}
			}
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
	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}
	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}
}
