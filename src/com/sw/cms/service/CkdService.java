package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.CgthdDAO;
import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.LsdDAO;
import com.sw.cms.dao.PosTypeDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.ProductSaleFlowDAO;
import com.sw.cms.dao.QtzcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.dao.XsdDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.ProductSaleFlow;
import com.sw.cms.model.Qtzc;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.SysUser;
import com.sw.cms.model.Xsd;
import com.sw.cms.model.XsdProduct;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.XsskDesc;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

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
	private LsdDAO lsdDao;
	private ProductSaleFlowDAO productSaleFlowDao;
	private UserDAO userDao;
	private PosTypeDAO posTypeDao;
	private QtzcDAO qtzcDao;

	
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
		
		//更新出库单
		ckdDao.updateCkd(ckd, ckdProducts);
		
		if(ckd.getState().equals("已出库")){
			
			//提成比例
			Map tcblMap = lsdDao.getTcbl();
			double basic_ratio = 0;
			double out_ratio = 0;
			double ds_ratio = 0;
			if(tcblMap != null){
				basic_ratio = tcblMap.get("basic_ratio")==null?0:((Double)tcblMap.get("basic_ratio")).doubleValue();
				out_ratio = tcblMap.get("out_ratio")==null?0:((Double)tcblMap.get("out_ratio")).doubleValue();
				ds_ratio = tcblMap.get("ds_ratio")==null?0:((Double)tcblMap.get("ds_ratio")).doubleValue();
			}
			
			//出库单对应的销售单信息
			Xsd xsd = (Xsd)xsdDao.getXsd(ckd.getXsd_id());
			
			//税点
			double sd = 0l;
			if(!xsd.getFplx().equals("出库单")){
				sd = lsdDao.getLssd();
			}
			
			//出库单对应的销售单商品明细
			List xsdProducts = new ArrayList();
			if(ckdProducts != null && ckdProducts.size()>0){
				for(int i=0;i<ckdProducts.size();i++){
					CkdProduct cdkProduct = (CkdProduct)ckdProducts.get(i);
					if(cdkProduct != null && !cdkProduct.getProduct_id().equals("")){
						XsdProduct xsdProduct = (XsdProduct)xsdDao.getXsdProductInfo(ckd.getXsd_id(), cdkProduct.getProduct_id());
						Product product = (Product)productDao.getProductById(cdkProduct.getProduct_id());
						
						//回写销售订单出库时的成本价等信息
						xsdProduct.setCbj(product.getPrice());     //成本价
						xsdProduct.setKh_cbj(product.getKhcbj());  //考核成本价
						xsdProduct.setYgcbj(product.getYgcbj());   //预估成本价
						xsdProduct.setGf(product.getGf());         //工分(比例点杀)
						xsdProduct.setBasic_ratio(basic_ratio);    //基本提成
						xsdProduct.setOut_ratio(out_ratio);        //超限提成
						xsdProduct.setSd(sd);
						xsdProduct.setSfcytc(product.getSfcytc());  //是否参与提成
						
						//不含税单价低于零售限价时 点杀需要乘以比例
						double ds = product.getDss();
						if((xsdProduct.getPrice()/ (1 + sd/100)) < product.getLsxj()){
							ds = ds * ds_ratio/100;
						}
						xsdProduct.setDs(ds);                     //金额点杀
						
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
			
			//修改库存
			this.updateProductKc(ckd, ckdProducts); 
			
			//系统正式启用前，不对强制序列号做限制，但输入的序列号系统同样给予处理			
			this.updateSerialNum(ckd, ckdProducts);
			
			//处理对应销售单状态
			this.updateXsdState(ckd.getXsd_id(), "已出库",ckd.getYsfs(),ckd.getCx_tel(),ckd.getJob_no(),ckd.getSend_time(),ckd.getStore_id(),ckd.getFzr(),ckd.getCk_date());
				
			//修改销售单对应的成本价及考核成本价
			if(xsdProducts != null && xsdProducts.size() > 0){
				xsdDao.updateXsdProducts(xsdProducts);
			}
			
			//如果对应销售单的收款类型为现金,并且收款金额不等于,则销售金额过账
			if(xsd.getSklx().equals("现结") && xsd.getSkje() != 0){
				
				//添加销售收款信息,及对账单信息
				this.saveXssk(xsd);//添加销售收款信息
				
				//更新账户金额
				this.addAccountJe(xsd);//更新账户金额
				
				//如果付款方式为刷卡，并且POS机编号不为空，自动保存费用信息
				if(xsd.getSkfs().equals("刷卡") && !xsd.getPos_id().equals("")){
					this.saveQtzc(xsd);
				}
			}
			
			//生成商品销售明细
			this.addProductSaleFlow(xsd, xsdProducts);
		}
	}

	
	
	/**
	 * 根据出库单ID获取商品列表
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
	 * 查看出库单是否可操作（出库、退回）
	 * @param ckd_id
	 * @return false:不能操作；true:能操作
	 */
	public boolean isCkdSubmit(String ckd_id){
		Ckd ckd = (Ckd)ckdDao.getCkd(ckd_id);
		if(ckd == null){
			//出库单已经不存在，不能操作
			return false;
		}else{
			if(ckd.getState().equals("已出库")){
				//出库单已出库，不能操作
				return false;
			}else{
				return true;
			}
		}
	}
	
	/**
	 * 出库单是否已经删除
	 * @param ckd_id
	 * @return
	 */
	public boolean isCkdDel(String ckd_id) {
		return ckdDao.isCkdDel(ckd_id);
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
	 * 判断序列号是否满足出库需要
	 * @param ckd
	 * @param ckdProducts
	 */
	public String checkXlh(Ckd ckd,List ckdProducts){
		String msg = "";
		String store_id = ckd.getStore_id();
		
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
				if(ckdProduct != null){
					if(!ckdProduct.getProduct_id().equals("") && !ckdProduct.getProduct_name().equals("") && ckdProduct.getQz_flag().equals("是")){
						String product_id = ckdProduct.getProduct_id();
						String[] arryNums = (ckdProduct.getQz_serial_num()).split(",");
						
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
								  msg += ckdProduct.getProduct_name() + " 序列号为：" + serialNum + " 不存在，请确认后再进行出库处理\n";
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
	 * 保存销售收款信息
	 * @param lsd
	 */
	private void saveXssk(Xsd xsd){
		Xssk xssk = new Xssk();
		
		String xssk_id = xsskDao.getXsskID();
		xssk.setId(xssk_id);
		xssk.setSk_date(xsd.getCreatdate());
		xssk.setClient_name(xsd.getClient_name());
		xssk.setJsr(xsd.getFzr());
		xssk.setSkzh(xsd.getSkzh());
		xssk.setSkje(xsd.getSkje());
		xssk.setState("已提交");
		xssk.setCzr(xsd.getCzr());
		xssk.setRemark("销售收款，销售单编号 [" + xsd.getId() + "]");
		xssk.setDelete_key(xsd.getId());
		
		List<XsskDesc> xsskDescs = new ArrayList<XsskDesc>();
		
		XsskDesc xsskDesc = new XsskDesc();
		xsskDesc.setXsd_id(xsd.getId());
		xsskDesc.setXssk_id(xssk_id);
		xsskDesc.setBcsk(xsd.getSkje());
		xsskDesc.setRemark("销售收款，销售单编号 [" + xsd.getId() + "]");
		
		xsskDescs.add(xsskDesc);
		
		xsskDao.saveXssk(xssk, xsskDescs);
		
		//保存对账单信息
		this.saveAccountDzd(xssk.getSkzh(), xssk.getSkje(), xssk.getCzr(), xssk.getJsr(), "销售收款，编号[" + xssk.getId() + "]",xssk.getId());
	}
	
	/**
	 * 将收款金额入账户中
	 * @param lsd
	 */
	private void addAccountJe(Xsd xsd){
		String account_id = xsd.getSkzh();
		double je = xsd.getSkje();
		
		accountsDao.addAccountJe(account_id, je);
	}
	
	/**
	 * 添加资金交易记录
	 * @param cgfk
	 */
	private void saveAccountDzd(String account_id,double jyje,String czr,String jsr,String remark,String xssk_id){
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
		accountDzd.setAction_url("viewXssk.html?id=" + xssk_id);
		
		accountDzdDao.addDzd(accountDzd);
	}
	/**
	 * 根据销售单信息添加刷卡支出费用
	 * @param xsd
	 */
	private void saveQtzc(Xsd xsd){
		Qtzc qtzc = new Qtzc();
		
		String id = qtzcDao.getQtzcID();
		
		String dept = "";
		if(!StringUtils.nullToStr(xsd.getFzr()).equals("")){
			dept = ((SysUser)userDao.getUser(xsd.getFzr())).getDept();
		}
		
		qtzc.setId(id);
		qtzc.setZc_date(xsd.getCreatdate());
		qtzc.setType("02");
		qtzc.setZcje(posTypeDao.getBrushCardfy(xsd.getPos_id(), xsd.getSkje()));
		qtzc.setZczh(xsd.getSkzh());
		qtzc.setJsr(xsd.getFzr());
		qtzc.setRemark("刷卡手续费，由销售单[" + xsd.getId() + "]自动生成");
		qtzc.setCzr(xsd.getCzr());
		qtzc.setState("已提交");
		qtzc.setYwy(xsd.getFzr());
		qtzc.setSqr(xsd.getFzr());
		qtzc.setYwy_dept(dept);
		qtzc.setZcxm("刷卡手续费");
		qtzc.setFklx("刷卡");
		qtzc.setFysq_id("无");
		
		qtzcDao.saveQtzc(qtzc);  //保存其它支出（一般费用）
		
		accountsDao.updateAccountJe(xsd.getSkzh(),posTypeDao.getBrushCardfy(xsd.getPos_id(), xsd.getSkje())); //修改账户金额
		
		double jyje = 0 - posTypeDao.getBrushCardfy(xsd.getPos_id(), xsd.getSkje());
		AccountDzd accountDzd = new AccountDzd();
		accountDzd.setAccount_id(xsd.getSkzh());
		accountDzd.setJyje(jyje);		
		double zhye = 0;
		Map map = accountsDao.getAccounts(xsd.getSkzh());
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}		
		accountDzd.setZhye(zhye + jyje);
		accountDzd.setRemark("一般费用，编号[" + id + "]");
		accountDzd.setCzr(xsd.getCzr());
		accountDzd.setJsr(xsd.getFzr());
		accountDzd.setAction_url("viewQtzc.html?id=" + id);		
		accountDzdDao.addDzd(accountDzd);   //添加资金交易记录
		
	}
	
	
	
	/**
	 * 更新商品库存
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
	
	
	/**
	 * 根据销售订单生成相应的商品销售流水信息
	 * @param xsd
	 * @param xsdProducts
	 */
	public void addProductSaleFlow(Xsd xsd, List xsdProducts){
		if(xsdProducts != null && xsdProducts.size()>0){
			for(int i=0;i<xsdProducts.size();i++){
				XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
				if(xsdProduct != null && !xsdProduct.getProduct_id().equals("") && !xsdProduct.getProduct_name().equals("")){
					ProductSaleFlow info = new ProductSaleFlow();
					
					info.setId(xsd.getId());
					info.setYw_type("销售单");
					info.setClient_name(xsd.getClient_name());
					info.setXsry(xsd.getFzr());
					info.setCz_date(DateComFunc.getToday());
					info.setProduct_id(xsdProduct.getProduct_id());
					info.setNums(xsdProduct.getNums());
					info.setPrice(xsdProduct.getPrice());
					info.setHjje(xsdProduct.getSjcj_xj());
					info.setDwcb(xsdProduct.getCbj());
					info.setCb(xsdProduct.getCbj()*xsdProduct.getNums());
					info.setDwkhcb(xsdProduct.getKh_cbj());
					info.setKhcb(xsdProduct.getKh_cbj()*xsdProduct.getNums());
					info.setDwygcb(xsdProduct.getYgcbj());
					info.setYgcb(xsdProduct.getYgcbj()*xsdProduct.getNums());
					info.setSd(xsdProduct.getSd());
					info.setBhsje(xsdProduct.getSjcj_xj() / (1 + xsdProduct.getSd()/100));
					info.setGf(xsdProduct.getGf());
					info.setDs(xsdProduct.getDs() * xsdProduct.getNums());
					info.setBasic_ratio(xsdProduct.getBasic_ratio());
					info.setOut_ratio(xsdProduct.getOut_ratio());
					info.setLsxj(xsdProduct.getLsxj() * xsdProduct.getNums());
					info.setSfcytc(xsdProduct.getSfcytc());
					
					productSaleFlowDao.insertProductSaleFlow(info);
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



	public LsdDAO getLsdDao() {
		return lsdDao;
	}



	public void setLsdDao(LsdDAO lsdDao) {
		this.lsdDao = lsdDao;
	}



	public ProductSaleFlowDAO getProductSaleFlowDao() {
		return productSaleFlowDao;
	}



	public void setProductSaleFlowDao(ProductSaleFlowDAO productSaleFlowDao) {
		this.productSaleFlowDao = productSaleFlowDao;
	}



	public UserDAO getUserDao() {
		return userDao;
	}



	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}



	public PosTypeDAO getPosTypeDao() {
		return posTypeDao;
	}



	public void setPosTypeDao(PosTypeDAO posTypeDao) {
		this.posTypeDao = posTypeDao;
	}



	public QtzcDAO getQtzcDao() {
		return qtzcDao;
	}



	public void setQtzcDao(QtzcDAO qtzcDao) {
		this.qtzcDao = qtzcDao;
	}

}
