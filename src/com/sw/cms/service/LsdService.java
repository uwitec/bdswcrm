package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.LsdDAO;
import com.sw.cms.dao.LsyskDAO;
import com.sw.cms.dao.PosTypeDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.QtzcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.SysMsgDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Lsd;
import com.sw.cms.model.LsdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.Qtzc;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.SysMsg;
import com.sw.cms.model.SysUser;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.XsskDesc;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

/**
 * 零售单相关处理
 * 只有在第统正式启用后再去处理强制序列号相关
 * @author liyt
 *
 */
public class LsdService {
	
	private LsdDAO lsdDao;
	private ProductKcDAO productKcDao;
	private XsskDAO xsskDao;
	private CkdDAO ckdDao;
	private AccountsDAO accountsDao;
	private LsyskDAO lsyskDao;
	private AccountDzdDAO accountDzdDao;
	private SerialNumDAO serialNumDao;
	private SysInitSetDAO sysInitSetDao;
	private ProductDAO productDao;
	private UserDAO userDao;
	private SysMsgDAO sysMsgDao;
	private PosTypeDAO posTypeDao;
	private QtzcDAO qtzcDao;
	
	/**
	 * 获取零售单列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getLsdList(String con,int curPage, int rowsPerPage){
		return lsdDao.getLsdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 取待审批零售单列表
	 * @return
	 */
	public List getDspLsdList(){
		return lsdDao.getDspLsdList();
	}
	
	
	/**
	 * 保存零售单信息
	 * @param lsd
	 * @param lsdProducts
	 * @return
	 */
	public void saveLsd(Lsd lsd,List lsdProducts){
		
		//如果零售单已提交，不做处理
		if(lsdDao.isLsdSubmit(lsd.getId())){
			return;
		}
		
		lsdDao.saveLsd(lsd, lsdProducts);		
		
		if(lsd.getState().equals("已提交")){
			
			//保存相应出库单信息(状态为已出库)
			//同时处理相关序列号，但只有在系统正式使用后才能处理序列号
			this.addCkd(lsd, lsdProducts);
			
			//更新产品库存(可能存在负库存)
			this.updateProductKc(lsd, lsdProducts); //更新产品库存
			
			this.saveXssk(lsd);//保存销售收款信息
			this.addAccountJe(lsd);//修改账户金额
			
			if(lsd.getHas_yushk().equals("是")){ //冲抵预收款信息
				lsyskDao.updateLsyskType(lsd.getYushk_id(), "已冲抵");
			}
			
			//如果付款方式为刷卡，并且POS机编号不为空，自动保存费用信息
			if(lsd.getFkfs().equals("刷卡") && !lsd.getPos_id().equals("")){
				this.saveQtzc(lsd);
			}
		}
		
		//如果审批状态为待审批，发送系统消息
		if(lsd.getSp_state() != null && lsd.getSp_state().equals("2")){
			this.saveMsg(lsd.getId(), lsd.getCzr());
		}
	}
	
	
	/**
	 * 更新零售单信息
	 * @param lsd
	 * @param lsdProducts
	 */
	public void updateLsd(Lsd lsd,List lsdProducts){
		
		//如果零售单已提交，不做处理
		if(lsdDao.isLsdSubmit(lsd.getId())){
			return;
		}
		
		lsdDao.updateLsd(lsd, lsdProducts);
		
		if(lsd.getState().equals("已提交")){
			
			//保存相应出库单信息(状态为已出库)
			//同时处理相关序列号，但只有在系统正式使用后能处理序列号
			this.addCkd(lsd, lsdProducts);

			//更新产品库存
			this.updateProductKc(lsd, lsdProducts); 
			
			this.saveXssk(lsd);//保存销售收款信息
			this.addAccountJe(lsd);//修改账户金额
			
			if(lsd.getHas_yushk().equals("是")){ //冲抵预收款信息
				lsyskDao.updateLsyskType(lsd.getYushk_id(), "已冲抵");
			}
			
			//如果付款方式为刷卡，并且POS机编号不为空，自动保存费用信息
			if(lsd.getFkfs().equals("刷卡") && !lsd.getPos_id().equals("")){
				this.saveQtzc(lsd);
			}
		}	
		
		//如果审批状态为待审批，发送系统消息
		if(lsd.getSp_state() != null && lsd.getSp_state().equals("2")){
			this.saveMsg(lsd.getId(), lsd.getCzr());
		}
		
	} 
	
	
	/**
	 * 根据零售单ID取零售单信息
	 * @param id
	 * @return
	 */
	public Object getLsd(String id){
		return lsdDao.getLsd(id);
	}
	
	
	/**
	 * 根据lsd_id取相关联产品
	 * @param id
	 * @return
	 */
	public List getLsdProducts(String id){
		return lsdDao.getLsdProducts(id);
	}
	
	
	/**
	 * 删除零售单信息（包括关联产品）
	 * @param id
	 */
	public void delLsd(String id){
		
		//如果零售单已提交，不做处理
		if(lsdDao.isLsdSubmit(id)){
			return;
		}
		
		lsdDao.delLsd(id);
	}	
	
	
	/**
	 * 取当前可用的零售单编号
	 * @return
	 */
	public String updateLsdId(){
		return lsdDao.getLsdID();
	}
	
	
	
	/**
	 * <p>根据零售单添加相应出库单</p>
	 * <p>如果是强制序列号添加相应序列号信息</p?
	 * @param lsd
	 * @param lsdProducts
	 */
	private void addCkd(Lsd lsd,List lsdProducts){
		Ckd ckd = new Ckd();
		
		String ckd_id= ckdDao.getCkdID();
		ckd.setCkd_id(ckd_id);
		ckd.setFzr(lsd.getXsry());
		ckd.setXsd_id(lsd.getId());
		ckd.setCreatdate(lsd.getCreatdate());
		ckd.setCk_date(lsd.getCreatdate());
		ckd.setState("已出库");
		ckd.setSkzt("已收");
		ckd.setMs("零售出库单，零售单编号 [" + lsd.getId() + "]");
		ckd.setClient_name(lsd.getClient_name());
		ckd.setXsry(lsd.getXsry());
		ckd.setStore_id(lsd.getStore_id());
		ckd.setCzr(lsd.getCzr());

		
		List ckdProducts = new ArrayList();
		
		if(lsdProducts != null && lsdProducts.size()>0){
			for(int i=0;i<lsdProducts.size();i++){
				LsdProduct lsdProduct = (LsdProduct)lsdProducts.get(i);
				if(lsdProduct != null){
					if(!lsdProduct.getProduct_id().equals("") && !lsdProduct.getProduct_name().equals("")){
						
						CkdProduct ckdProduct = new CkdProduct();
						
						ckdProduct.setCkd_id(ckd_id);
						ckdProduct.setProduct_id(lsdProduct.getProduct_id());
						ckdProduct.setProduct_name(lsdProduct.getProduct_name());
						ckdProduct.setProduct_xh(lsdProduct.getProduct_xh());
						ckdProduct.setNums(lsdProduct.getNums());
						ckdProduct.setRemark(lsdProduct.getRemark());
						ckdProduct.setPrice(lsdProduct.getPrice());
						ckdProduct.setCbj(lsdProduct.getCbj());
						ckdProduct.setJgtz(0);
						
						
						//只有在系统正式使用后才去修改产品的库存和处理序列号
						//系统启用前也可输入产品序列号，但不硬性强制，对于输入的序列号系统做处理
						
						//if(sysInitSetDao.getQyFlag().equals("1")){
						//强制序列号处理
						
						if((lsdProduct.getQz_serial_num() != null) && (!lsdProduct.getQz_serial_num().equals(""))){
							String[] arryNums = (lsdProduct.getQz_serial_num()).split(",");
							
							SerialNumMng serialNumMng = new SerialNumMng();
							SerialNumFlow serialNumFlow = new SerialNumFlow();
							
							for(int k=0;k<arryNums.length;k++){
								serialNumMng.setProduct_id(lsdProduct.getProduct_id());
								serialNumMng.setProduct_name(lsdProduct.getProduct_name());
								serialNumMng.setProduct_xh(lsdProduct.getProduct_xh());
								serialNumMng.setSerial_num(arryNums[k]);
								serialNumMng.setState("已售");
								serialNumMng.setStore_id("");
								serialNumMng.setYj_flag("0");
								serialNumDao.updateSerialNumState(serialNumMng); //更新序列号状态
								
								serialNumFlow.setClient_name(lsd.getClient_name());
								serialNumFlow.setTel(lsd.getLxdh() + " " + lsd.getMobile());
								serialNumFlow.setCzr(lsd.getCzr());
								serialNumFlow.setYwtype("零售");
								serialNumFlow.setFs_date(lsd.getCreatdate());
								serialNumFlow.setJsr(StaticParamDo.getRealNameById(lsd.getXsry()));
								serialNumFlow.setKf_dj_id(ckd_id);
								serialNumFlow.setSerial_num(arryNums[k]);
								serialNumFlow.setKf_url("viewCkd.html?ckd_id=");
								serialNumFlow.setYw_dj_id(lsd.getId());
								serialNumFlow.setYw_url("viewLsd.html?id=");
								serialNumFlow.setXsdj(lsdProduct.getPrice());
								
								serialNumDao.saveSerialFlow(serialNumFlow);  //保存序列号流转过程
							}
						}
						//}
						
						ckdProducts.add(ckdProduct);
						
					}
				}
			}
		}
		
		ckdDao.saveCkd(ckd, ckdProducts);
	}
	
	
	/**
	 * 保存销售收款信息
	 * @param lsd
	 */
	private void saveXssk(Lsd lsd){
		Xssk xssk = new Xssk();
		
		String xssk_id = xsskDao.getXsskID();
		xssk.setId(xssk_id);
		xssk.setSk_date(lsd.getCreatdate());
		xssk.setClient_name(lsd.getClient_name());
		xssk.setJsr(lsd.getXsry());
		xssk.setSkzh(lsd.getSkzh());
		xssk.setSkje(lsd.getSkje());
		xssk.setState("已提交");
		xssk.setCzr(lsd.getCzr());
		xssk.setRemark("零售收款，零售单编号 [" + lsd.getId() + "]");
		
		List xsskDescs = new ArrayList();
		
		XsskDesc xsskDesc = new XsskDesc();
		xsskDesc.setXsd_id(lsd.getId());
		xsskDesc.setFsje(lsd.getLsdje());
		xsskDesc.setXssk_id(xssk_id);
		xsskDesc.setBcsk(lsd.getSkje());
		xsskDesc.setRemark("零售单");
		
		xsskDescs.add(xsskDesc);
		
		xsskDao.saveXssk(xssk, xsskDescs);
		
		this.saveAccountDzd(xssk.getSkzh(), xssk.getSkje(), xssk.getCzr(), xssk.getJsr(), "零售收款，编号[" + xssk.getId() + "]",xssk.getId());
	}
	
	
	/**
	 * 更新产品库存
	 * @param lsd
	 * @param lsdProducts
	 */
	private void updateProductKc(Lsd lsd,List lsdProducts){
		if(lsdProducts != null && lsdProducts.size()>0){
			for(int i=0;i<lsdProducts.size();i++){
				LsdProduct lsdProduct = (LsdProduct)lsdProducts.get(i);
				if(lsdProduct != null){
					if(!lsdProduct.getProduct_id().equals("") && !lsdProduct.getProduct_name().equals("")){
						productKcDao.updateProductKc(lsd.getStore_id(), lsdProduct.getProduct_id(), lsdProduct.getNums());
					}
				}
			}
		}
	}

	
	/**
	 * 将收款金额入账户中
	 * @param lsd
	 */
	private void addAccountJe(Lsd lsd){
		String account_id = lsd.getSkzh();
		double je = lsd.getSkje();
		
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
	 * 判断库存量是否满足出库需要
	 * @param ckd
	 * @param ckdProducts
	 */
	public String checkKc(Lsd lsd,List lsdProducts){
		String msg = "";
		String store_id = lsd.getStore_id();
		
		if(lsdProducts != null && lsdProducts.size()>0){
			for(int i=0;i<lsdProducts.size();i++){
				LsdProduct lsdProduct = (LsdProduct)lsdProducts.get(i);
				if(lsdProduct != null){
					if(!lsdProduct.getProduct_id().equals("") && !lsdProduct.getProduct_name().equals("")){
						String product_id = lsdProduct.getProduct_id();
						
						//判断商品是否是库存商品,只仍库存商品才进行库存数量判断
						Product product = (Product)productDao.getProductById(product_id);
						if(product.getProp().equals("库存商品")){						
							int cknums = lsdProduct.getNums();  //要出库数量						
							int kcnums = productKcDao.getKcNums(product_id, store_id);//库存数量
							
							if(cknums>kcnums){
								msg += "产品：" + lsdProduct.getProduct_name() + "在" + StaticParamDo.getStoreNameById(store_id) +" 中当前库存为：" + kcnums + "  无法出库，零售单已保存，请先调拨后再出库！<br>";
							}
						}
					}
				}
			}
		}
		
		return msg;
	}
	
	
	/**
	 * 是否存在低于零售限价的产品
	 * @param lsdProducts
	 * @return
	 */
	public boolean isExistLowLsxj(Lsd lsd,List lsdProducts){
		boolean is = false;
		double sd = lsdDao.getLssd();  //零售税点
		String fplx = lsd.getFplx();   //发票类型
		if(lsdProducts != null && lsdProducts.size()>0){
			for(int i=0;i<lsdProducts.size();i++){
				LsdProduct lsdProduct = (LsdProduct)lsdProducts.get(i);
				if(lsdProduct != null){
					if(!lsdProduct.getProduct_id().equals("") && !lsdProduct.getProduct_name().equals("")){
						double price = lsdProduct.getPrice();  //当前报价
						double lsxj = productDao.getProductLsxj(lsdProduct.getProduct_id()); //零售限价
						
						//如果发票类型是出库单则限价可以为限价减去税点
						if(fplx.equals("出库单")){
							lsxj = lsxj * (1 - sd/100);
						}
						
						if(lsxj !=0 && price<lsxj){
							is = true;
							break;
						}
					}
				}
			}
		}
		return is;
	}
	
	
	/**
	 * 发送系统消息
	 * @param lsd_id
	 * @param sender_id
	 */
	private void saveMsg(String lsd_id,String sender_id){
		List list = userDao.getJgspUsers();
		
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				
				SysMsg msg = new SysMsg();
				msg.setReciever_id(StringUtils.nullToStr(map.get("user_id")));
				msg.setSender_id(sender_id);
				msg.setMsg_body("有一条待审批零售单，请及时审批，零售单编号为：" + lsd_id);
				msg.setMobile_num(StringUtils.nullToStr(map.get("mobile")));
				
				sysMsgDao.saveMsg(msg);
			}
		}
	}
	
	
	/**
	 * 发送系统消息
	 * @param reciever_id
	 * @param sender_id
	 * @param lsd_id
	 */
	public void saveMsg(String reciever_id,String sender_id,String lsd_id,String sp_state){
		
		SysMsg msg = new SysMsg();
		msg.setReciever_id(reciever_id);
		msg.setSender_id(sender_id);
		if(sp_state.equals("3")){
			msg.setMsg_body("您提交的零售单［" + lsd_id + "］已审批通过！");
		}else{
			msg.setMsg_body("您提交的零售单［" + lsd_id + "］没有审批通过！");
		}		
		msg.setMobile_num(((SysUser)userDao.getUser(sender_id)).getMobile());
		
		sysMsgDao.saveMsg(msg);
	}
	
	
	/**
	 * 保存零售单的审批结果<BR>
	 * 如果是审批通过则提交零售单
	 * @param lsd_id
	 * @param sp_state
	 * @param spr
	 */
	public void saveSp(String lsd_id,String sp_state,String spr){
		
		//如果零售单已提交，不做处理
		if(lsdDao.isLsdSubmit(lsd_id)){
			return;
		}
		
		lsdDao.saveSp(lsd_id, sp_state, spr);
		
		Lsd lsd = (Lsd)lsdDao.getLsd(lsd_id); //零售单
		List lsdProducts = lsdDao.getLsdProducts(lsd_id);
		
		//如果是审批通过，则修改零售单状态
		if(sp_state.equals("3")){
			lsd.setState("已提交");
			updateLsd(lsd,lsdProducts);
		}
		
		//发送审批消息
		this.saveMsg(lsd.getCzr(), spr, lsd_id, sp_state);
	}
	
	
	/**
	 * 取零售税点
	 * @return
	 */
	public double getLssd(){
		return lsdDao.getLssd();
	}
	
	
	/**
	 * 保存零售税点
	 * @param sd
	 */
	public void saveLssd(String sd){
		lsdDao.saveLssd(sd);
	}
	
	
	/**
	 * 取提成比例
	 * @return
	 */
	public Map getTcbl(){
		return lsdDao.getTcbl();
	}
	
	
	/**
	 * 保存提成比例
	 * @param basic_ratio
	 * @param out_ratio
	 */
	public void saveTcbl(String basic_ratio,String out_ratio,String ds_ratio){
		lsdDao.saveTcbl(basic_ratio, out_ratio, ds_ratio);
	}
	
	
	private void saveQtzc(Lsd lsd){
		Qtzc qtzc = new Qtzc();
		
		String id = qtzcDao.getQtzcID();
		
		String dept = "";
		if(!StringUtils.nullToStr(lsd.getXsry()).equals("")){
			dept = ((SysUser)userDao.getUser(lsd.getXsry())).getDept();
		}
		
		qtzc.setId(id);
		qtzc.setZc_date(lsd.getCreatdate());
		qtzc.setType("02");
		qtzc.setZcje(posTypeDao.getBrushCardfy(lsd.getPos_id(), lsd.getSkje()));
		qtzc.setZczh(lsd.getSkzh());
		qtzc.setJsr(lsd.getXsry());
		qtzc.setRemark("刷卡手续费，由零售单[" + lsd.getId() + "]自动生成");
		qtzc.setCzr(lsd.getCzr());
		qtzc.setState("已提交");
		qtzc.setYwy(lsd.getXsry());
		qtzc.setSqr(lsd.getXsry());
		qtzc.setYwy_dept(dept);
		qtzc.setZcxm("刷卡手续费");
		qtzc.setFklx("刷卡");
		qtzc.setFysq_id("无");
		
		qtzcDao.saveQtzc(qtzc);  //保存其它支出（一般费用）
		
		accountsDao.updateAccountJe(lsd.getSkzh(),posTypeDao.getBrushCardfy(lsd.getPos_id(), lsd.getSkje())); //修改账户金额
		
		double jyje = 0 - posTypeDao.getBrushCardfy(lsd.getPos_id(), lsd.getSkje());
		AccountDzd accountDzd = new AccountDzd();
		accountDzd.setAccount_id(lsd.getSkzh());
		accountDzd.setJyje(jyje);
		double zhye = 0;
		Map map = accountsDao.getAccounts(lsd.getSkzh());
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}		
		accountDzd.setZhye(zhye + jyje);
		accountDzd.setRemark("一般费用，编号[" + id + "]");
		accountDzd.setCzr(lsd.getCzr());
		accountDzd.setJsr(lsd.getXsry());
		accountDzd.setAction_url("viewQtzc.html?id=" + id);		
		accountDzdDao.addDzd(accountDzd);   //添加资金交易记录
		
	}
	
	
	/**
	 * 查看零售单是否已经提交
	 * @param ckd_id
	 * @return
	 */
	public boolean isLsdSubmit(String lsd_id){
		return lsdDao.isLsdSubmit(lsd_id);
	}
	
	
	public void setLsdDao(LsdDAO lsdDao) {
		this.lsdDao = lsdDao;
	}

	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}

	public XsskDAO getXsskDao() {
		return xsskDao;
	}

	public void setXsskDao(XsskDAO xsskDao) {
		this.xsskDao = xsskDao;
	}

	public LsdDAO getLsdDao() {
		return lsdDao;
	}

	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}

	public CkdDAO getCkdDao() {
		return ckdDao;
	}

	public void setCkdDao(CkdDAO ckdDao) {
		this.ckdDao = ckdDao;
	}

	public AccountsDAO getAccountsDao() {
		return accountsDao;
	}

	public void setAccountsDao(AccountsDAO accountsDao) {
		this.accountsDao = accountsDao;
	}

	public LsyskDAO getLsyskDao() {
		return lsyskDao;
	}

	public void setLsyskDao(LsyskDAO lsyskDao) {
		this.lsyskDao = lsyskDao;
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

	public ProductDAO getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public SysMsgDAO getSysMsgDao() {
		return sysMsgDao;
	}

	public void setSysMsgDao(SysMsgDAO sysMsgDao) {
		this.sysMsgDao = sysMsgDao;
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
