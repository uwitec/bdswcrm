package com.sw.cms.service;

/**
 * 销售单管理（分销商专用）
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.ClientsDAO;
import com.sw.cms.dao.InitParamDAO;
import com.sw.cms.dao.PosTypeDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.QtzcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.SysMsgDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.dao.XsdDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.HykjfDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Clients;
import com.sw.cms.model.Fxdd;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.Qtzc;
import com.sw.cms.model.SysMsg;
import com.sw.cms.model.SysUser;
import com.sw.cms.model.Xsd;
import com.sw.cms.model.XsdProduct;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.XsskDesc;
import com.sw.cms.model.Hykjf;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

/**
 * 销售单处理
 * @author liyt
 *
 */
public class XsdService {
	
	private XsdDAO xsdDao;
	private CkdDAO ckdDao;
	private ClientsDAO clientsDao;
	private XsskDAO xsskDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private ProductKcDAO productKcDao;
	private SerialNumDAO serialNumDao;
	private ProductDAO productDao;
	private SysInitSetDAO sysInitSetDao;
	private InitParamDAO initParamDao;
	private UserDAO userDao;
	private SysMsgDAO sysMsgDao;
	private PosTypeDAO posTypeDao;
	private QtzcDAO qtzcDao;
	private HykjfDAO hykjfDao;
	
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
	
	public HykjfDAO getHykjfDao() {
		return hykjfDao;
	}


	public void setHykjfDao(HykjfDAO hykjfDao) {
		this.hykjfDao = hykjfDao;
	}

	/**
	 * 取销售单列表(带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsdList(String con,int curPage, int rowsPerPage){
		return xsdDao.getXsdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 分销订单（分销业务->采购订单）列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getFxddList(String con,int curPage,int rowsPerPage){
		return xsdDao.getFxddList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 更新销售单信息
	 * @param xsd
	 * @param xsdProducts
	 */
	public void updateXsd(Xsd xsd,List xsdProducts){
		
		//如果销售订单相应的出库单已经生成，返回不做处理
		if(ckdDao.isCkdExist(xsd.getId())){
			return;
		}
		
		//判断并处理销售单的收款状态
		double xsdje = xsd.getXsdje(); //销售单合计金额
		double skje = xsd.getSkje();   //收款金额
		
		//如销售单总金额小于等于0则收款形式为已收
		if(xsdje <= 0){
			xsd.setSkxs("已收");
		}else{
			if(skje > 0 && skje < xsdje){
				//如果收款金额大于0，并且小于销售单金额
				xsd.setSkxs("部分已收");
			}else if(skje >= xsdje){
				xsd.setSkxs("已收");
			}else{
				xsd.setSkxs("未收");
			}
		}
		
		//判断并处理收款日期及应收日期
		if(xsd.getSklx().equals("现结")){
			xsd.setSkrq(xsd.getCreatdate()); //收款日期
			xsd.setYsrq(xsd.getCreatdate()); //应收日期
		}else{
			if(xsd.getSkje() != 0){
				xsd.setSkrq(xsd.getCreatdate()); //如发生收款则存当前日期为收款日期
			}

			String ysrq = xsd.getCreatdate();  //默认应收日期为创建日期
			long zq = new Long(xsd.getZq()+"").longValue(); //客户账期
			
			if(ysrq != null && !ysrq.equals("")){
				ysrq = DateComFunc.formatDate(DateComFunc.addDay((DateComFunc.strToDate(ysrq, "yyyy-MM-dd")),zq),"yyyy-MM-dd");
			}
			xsd.setYsrq(ysrq);
		}
		
		//增加会员积分
		int hyjf;
		Map mp = (Map)hykjfDao.getHyxx(xsd.getHykh(),xsd.getClient_name());
		if(mp!=null && mp.size()>0)
		{
		String xfje = StringUtils.nullToStr(mp.get("xfje"));   //销售金额
		String dyjf = StringUtils.nullToStr(mp.get("dyjf"));   //对应积分
		
		hyjf=0;
		hyjf=(int)(xsd.getXsdje())/Integer.parseInt(xfje)*Integer.parseInt(dyjf);
		}
		else
		{
		hyjf=0;
		}
		xsd.setHyjf(hyjf);
		
		xsdDao.updateXsd(xsd, xsdProducts);	
		
		if(!xsd.getState().equals("已保存")){
			
			//第二步，添加相应出库单
			this.addCkd(xsd, xsdProducts); //添加相应出库单
			
			if(xsd.getSkje() != 0){
				
				//第三步，添加销售收款信息
				this.saveXssk(xsd);//添加销售收款信息
				
				//第四步，更新账户金额
				this.addAccountJe(xsd);//更新账户金额
			}
			
			//如果付款方式为刷卡，并且POS机编号不为空，自动保存费用信息
			if(xsd.getSkfs().equals("刷卡") && !xsd.getPos_id().equals("")){
				this.saveQtzc(xsd);
			}
		}
		
		//如果审批状态为待审批，发送系统消息
		if(xsd.getSp_state() != null && xsd.getSp_state().equals("2")){
			this.saveMsg(xsd.getId(), xsd.getCzr(),xsd.getSp_type());
		}
		
	}
	
	
	/**
	 * <p>更新分销订单信息</p>
	 * <p>包括添加和更新两种方式</p>
	 * <p>如果订单编号存在则更新</p>
	 * <p>如果订单编号不存在则插入</p>
	 * @param fxdd 分销订单
	 * @param xsdProudcts 分销订单商品明细
	 */
	public void updateFxdd(Fxdd fxdd,List xsdProducts){
		
		if(fxdd.getState().equals("已提交")){
			fxdd.setWlzt("待出库");
		}
		
		if(fxdd.getFxdd_id().equals("")){
			//分销订单不存在，插入订单
			fxdd.setFxdd_id(xsdDao.getXsdID());
			xsdDao.saveFxdd(fxdd, xsdProducts);
		}else{
			//分销订单存在，更新订单
			xsdDao.updateFxdd(fxdd, xsdProducts);
		}
		
		if(fxdd.getState().equals("已提交")){
			//生成相应的销售订单,状态为已提交
			this.addXsdByFxdd(fxdd, xsdProducts);
			
			//生成相应的出库单，状态为待出库
			this.addCkdByFxdd(fxdd, xsdProducts);
		}
	}
	
	
	/**
	 * 保存销售订单的审批结果
	 * @param lsd_id
	 * @param sp_state
	 * @param spr
	 */
	public void saveSp(String xsd_id,String sp_state,String spr){
		
		//如果销售订单相应的出库单已经生成，返回不做处理
		if(ckdDao.isCkdExist(xsd_id)){
			return;
		}
		
		xsdDao.saveSp(xsd_id, sp_state, spr);
		
		Xsd xsd = (Xsd)xsdDao.getXsd(xsd_id); //销售单
		List xsdProducts = xsdDao.getXsdProducts(xsd_id); //销售单相关商品
		
		//如果是审批通过，则修改销售订单状态
		if(sp_state.equals("3")){
			xsd.setState("已提交");
			updateXsd(xsd,xsdProducts);
		}
		
		//发送审批消息
		this.saveMsg(xsd.getCzr(), spr, xsd_id, sp_state);
	}
	
	
	/**
	 * 根据编号获取销售单信息
	 * @param id
	 * @return
	 */
	public Object getXsd(String id){
		return xsdDao.getXsd(id);
	}
	
	
	/**
	 * 根据分销定单编号取分销订单信息
	 * @param fxdd_id
	 * @return
	 */
	public Object getFxdd(String fxdd_id){
		return xsdDao.getFxdd(fxdd_id);
	}
	
	
	/**
	 * 根据编号获取销售相关商品
	 * @param id
	 * @return
	 */
	public List getXsdProducts(String id){
		return xsdDao.getXsdProducts(id);
	}
	
	
	/**
	 * 删除销售单信息
	 * @param id
	 */
	public void delXsd(String id){		
		
		//如果销售订单相应的出库单已经生成，返回不做处理
		if(ckdDao.isCkdExist(id)){
			return;
		}
		
		xsdDao.delXsd(id);
	}
	
	
	/**
	 * 删除分销订单信息
	 * @param fxdd_id
	 */
	public void delFxdd(String fxdd_id){
		xsdDao.delFxdd(fxdd_id);
	}
	
	
	/**
	 *判断客户是否有超期应收款 
	 * @param xsd
	 * @param xsdProducts
	 * @return
	 */
	public boolean isCqysk(String client_id){
		boolean is = false;
		
		String curDate = DateComFunc.getToday();
		
		List yskList = xsdDao.getYskListByClientId(client_id);
		if(yskList!=null && yskList.size()>0){
			for(int i=0;i<yskList.size();i++){
				Map map = (Map)yskList.get(i);
				
				if(curDate.compareTo((String)map.get("ysrq"))>0){ //超期
					is = true;
					break;
				}
			}
		}
		return is;
	}
	
	
	/**
	 * 判断是否有超额应收款
	 * @param client_id
	 * @return
	 */
	public boolean isCeysk(String client_id,double bcysje){
		boolean is = false;
		
		//客户当前应收款
		double ysk = initParamDao.getClientYinshou(client_id);
		
		Clients client = (Clients)clientsDao.getClient(client_id);
		
		double xe = client.getXe();
		
		if((ysk+bcysje) > xe){
			is = true;
		}
		return is;
	}
	
	
	/**
	 *判断客户是否有此会员卡号 
	 * @param xsd
	 * @param
	 * @return
	 */
	public boolean isHykh(String client_id,String hykh){
		boolean is = false;
		
		Map hykMap =(Map)hykjfDao.getHyxx(hykh,client_id);
		if(hykMap!=null && hykMap.size()>0)
		{
			is = true;
		}
		
		return is;
	}
	
	/**
	 * 取具超额审批权限角色列表
	 * @return
	 */
	public String getCespRoles(){
		return xsdDao.getCespRoles();
	}
	
	
	/**
	 * 发送系统消息
	 * @param xsd_id
	 * @param sender_id
	 * @param sp_type
	 */
	private void saveMsg(String xsd_id,String sender_id,String sp_type){
		
		List users = new ArrayList();
		
		//1：超期款审批；2：超额并且低于限价审批；3：超额审批；4：低于限价审批；
		if(sp_type.equals("1")){
			users = userDao.getCqspUsers();
		}else if(sp_type.equals("2")){
			users = userDao.getCeAndJgSpUsers();
		}else if(sp_type.equals("3")){
			users = userDao.getCespUsers();
		}else if(sp_type.equals("4")){
			users = userDao.getJgspUsers();
		}
		
		if(users != null && users.size() > 0){
			for(int i=0;i<users.size();i++){
				Map map = (Map)users.get(i);
				
				SysMsg msg = new SysMsg();
				msg.setReciever_id(StringUtils.nullToStr(map.get("user_id")));
				msg.setSender_id(sender_id);
				msg.setMsg_body("有一条待审批销售订单，请及时审批，订单编号为：" + xsd_id);
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
	private void saveMsg(String reciever_id,String sender_id,String xsd_id,String sp_state){
		
		SysMsg msg = new SysMsg();
		msg.setReciever_id(reciever_id);
		msg.setSender_id(sender_id);
		if(sp_state.equals("3")){
			msg.setMsg_body("您提交的销售订单［" + xsd_id + "］已审批通过！");
		}else{
			msg.setMsg_body("您提交的销售订单［" + xsd_id + "］没有审批通过！");
		}		
		msg.setMobile_num(((SysUser)userDao.getUser(sender_id)).getMobile());
		
		sysMsgDao.saveMsg(msg);
	}
	
	/**
	 * 根据出库单发送待出库消息
	 * @param ckd
	 */
	private void saveMsg(Ckd ckd){
		List userList = userDao.getUserListByFuncId("FC0005");
		
		if(userList != null && userList.size() > 0){
			for(int i=0;i<userList.size();i++){
				Map map = (Map)userList.get(i);
				
				SysMsg msg = new SysMsg();
				msg.setReciever_id(StringUtils.nullToStr(map.get("user_id")));
				msg.setSender_id(ckd.getCzr());
				msg.setMsg_body("有新的待出库单，编号为：" + ckd.getCkd_id());
				
				sysMsgDao.saveMsg(msg);
			}
		}
	}
	
	
	/**
	 * 取待审批销售单列表
	 * @param con
	 * @return
	 */
	public List getDspXsdList(String con){
		return xsdDao.getDspXsdList(con);
	}

	
	/**
	 * 添加相应出库单
	 * 应同时更新代理商采购订单的物流状态
	 * @param xsd
	 * @param xsdProducts
	 */
	private void addCkd(Xsd xsd,List xsdProducts){
		Ckd ckd = new Ckd();
		
		String ckd_id = ckdDao.getCkdID();
		
		String wlzt = "";
		String fxdd_id = xsd.getId();
		
		ckd.setClient_name(xsd.getClient_name());  //客户编号
		ckd.setTel(xsd.getKh_lxdh());  //客户联系电话
		ckd.setCreatdate(xsd.getCreatdate());
		ckd.setXsry(xsd.getFzr());

		ckd.setState("待出库");
		wlzt = "待出库";
			
		ckd.setYsfs(xsd.getYsfs());  //运输方式
		ckd.setXsd_id(xsd.getId());
		ckd.setCkd_id(ckd_id);
		ckd.setCzr(xsd.getCzr());
		ckd.setSkzt(xsd.getSkxs());
		ckd.setMs("销售出库，销售单编号 ["  + xsd.getId() + "]。" + xsd.getMs());
		
		ckd.setClient_lxr(xsd.getKh_lxr());
		ckd.setClient_lxr_address(xsd.getKh_address());
		ckd.setClient_lxr_tel(xsd.getKh_lxdh());
		
		ckd.setYfzf_type(xsd.getYfzf_type());
		
		//更新分销订单物流状态
		xsdDao.updateFxddWlzt(fxdd_id, wlzt);
		
		List<CkdProduct> ckdProducts = new ArrayList<CkdProduct>();
		
		if(xsdProducts != null && xsdProducts.size()>0){
			for(int i=0;i<xsdProducts.size();i++){
				XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
				
				if(xsdProduct != null){
					if(!xsdProduct.getProduct_name().equals("") && !xsdProduct.getProduct_id().equals("")){
						CkdProduct ckdProduct = new CkdProduct();
						
						ckdProduct.setProduct_id(xsdProduct.getProduct_id());
						ckdProduct.setProduct_name(xsdProduct.getProduct_name());
						ckdProduct.setProduct_xh(xsdProduct.getProduct_xh());
						ckdProduct.setNums(xsdProduct.getNums());
						ckdProduct.setRemark(xsdProduct.getRemark());
						ckdProduct.setPrice(xsdProduct.getPrice());
						ckdProduct.setCbj(xsdProduct.getCbj());
						ckdProduct.setJgtz(xsdProduct.getJgtz());
						ckdProduct.setQz_serial_num(xsdProduct.getQz_serial_num());
						
						ckdProducts.add(ckdProduct);
					}
				}
			}
		}
		
		ckdDao.saveCkd(ckd, ckdProducts);
		
		//发送待出库单消息
		this.saveMsg(ckd);
	}
	
	
	
	
	/**
	 * 根据分销订单信息生成相应出库单，状态为待出库
	 * @param fxdd
	 * @param xsdProducts
	 */
	private void addCkdByFxdd(Fxdd fxdd, List xsdProducts){
		Ckd ckd = new Ckd();
		
		ckd.setCkd_id(ckdDao.getCkdID());
		ckd.setClient_name(fxdd.getClient_name());		
		ckd.setTel(fxdd.getLxdh());  //客户联系电话
		ckd.setCreatdate(fxdd.getCreatdate());
		ckd.setXsry("");
		ckd.setState("待出库");
		ckd.setXsd_id(fxdd.getFxdd_id());
		ckd.setSkzt("未收");
		ckd.setYsfs(fxdd.getYsfs());
		ckd.setMs("分销订单生成出库单，订单编号 ["  + fxdd.getFxdd_id() + "]");
		
		List<CkdProduct> ckdProducts = new ArrayList<CkdProduct>();
		
		if(xsdProducts != null && xsdProducts.size()>0){
			for(int i=0;i<xsdProducts.size();i++){
				XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
				
				if(xsdProduct != null){
					if(!xsdProduct.getProduct_name().equals("") && !xsdProduct.getProduct_id().equals("")){
						CkdProduct ckdProduct = new CkdProduct();
						
						ckdProduct.setProduct_id(xsdProduct.getProduct_id());
						ckdProduct.setProduct_name(xsdProduct.getProduct_name());
						ckdProduct.setProduct_xh(xsdProduct.getProduct_xh());
						ckdProduct.setNums(xsdProduct.getNums());
						ckdProduct.setRemark(xsdProduct.getRemark());
						ckdProduct.setPrice(xsdProduct.getPrice());
						ckdProduct.setCbj(xsdProduct.getCbj());
						ckdProduct.setJgtz(xsdProduct.getJgtz());
						ckdProduct.setQz_serial_num(xsdProduct.getQz_serial_num());
						
						Product product = (Product)productDao.getProductById(xsdProduct.getProduct_id());
						xsdProduct.setCbj(product.getPrice());
						
						ckdProducts.add(ckdProduct);
					}
				}
			}
		}
		ckdDao.saveCkd(ckd, ckdProducts);
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
	 * 根据分销订单生成相应的销售单
	 * @param fxdd
	 * @param xsdProducts
	 */
	private void addXsdByFxdd(Fxdd fxdd,List xsdProducts){
		Xsd xsd = new Xsd();
		
		xsd.setId(fxdd.getFxdd_id());     //订单编号
		xsd.setCreatdate(fxdd.getCreatdate());   //创建时间
		xsd.setClient_name(fxdd.getClient_name()); //客户编号
		xsd.setKh_address(fxdd.getAddress());
		xsd.setKh_lxr(fxdd.getLxr());
		xsd.setKh_lxdh(fxdd.getLxdh());
		xsd.setYsfs(fxdd.getYsfs());        //运输方式
		xsd.setSklx("账期");        //付款类型
		xsd.setSkxs("未收");
		xsd.setZq(1);
		
		xsd.setXsdje(fxdd.getHjje());        //合计金额
		xsd.setMs(fxdd.getRemark());      //备注描述信息
		xsd.setState("已提交");
		
		//处理商品明细的成本价
		List xsdProducts2 = new ArrayList();
		double cbjhj = 0;
		if(xsdProducts != null && xsdProducts.size()>0){
			for(int i=0;i<xsdProducts.size();i++){
				XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);				
				if(xsdProduct != null){
					if(!xsdProduct.getProduct_name().equals("") && !xsdProduct.getProduct_id().equals("")){
						Product product = (Product)productDao.getProductById(xsdProduct.getProduct_id());
						xsdProduct.setCbj(product.getPrice());
						cbjhj += product.getPrice() * xsdProduct.getNums();
						xsdProducts2.add(xsdProduct);
					}
				}
			}
		}
		xsd.setXsdcbj(cbjhj);
		
		//保存销售单信息
		xsdDao.saveXsd(xsd, xsdProducts2);
	}
	
	
	
	/**
	 * 取当前可用的销售号
	 * @return
	 */
	public String updateXsdId(){
		return xsdDao.getXsdID();
	}
	
	
	/**
	 * 根据销售单编号判断销售单是否存在
	 * @param xsd_id
	 * @return
	 */
	public boolean isHasXsdByID(String xsd_id){
		return xsdDao.isHasXsdByID(xsd_id);
	}
	
	
	/**
	 * 判断销售单中是否存在该商品
	 * @param xsd_id
	 * @param product_id
	 * @return
	 */
	public boolean isHasXsdProduct(String xsd_id,String product_id){
		return xsdDao.isHasXsdProduct(xsd_id, product_id);
	}
	
	
	/**
	 * 判断库存量是否满足出库需要
	 * @param ckd
	 * @param ckdProducts
	 */
	public String checkKc(Xsd xsd,List xsdProducts){
		String msg = "";
		String store_id = xsd.getStore_id();
		
		if(xsdProducts != null && xsdProducts.size()>0){
			for(int i=0;i<xsdProducts.size();i++){
				XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
				if(xsdProduct != null){
					if(!xsdProduct.getProduct_id().equals("") && !xsdProduct.getProduct_name().equals("")){
						String product_id = xsdProduct.getProduct_id();
						
						//判断商品是否是库存商品,只仍库存商品才进行库存数量判断
						Product product = (Product)productDao.getProductById(product_id);
						if(product.getProp().equals("库存商品")){		
							int cknums = xsdProduct.getNums();  //要出库数量						
							int kcnums = productKcDao.getKcNums(product_id, store_id);//库存数量
							
							if(cknums>kcnums){
								msg += "商品：" + xsdProduct.getProduct_name() + "在" + StaticParamDo.getStoreNameById(store_id) +" 中当前库存为：" + kcnums + "  无法出库，请先调拨<br>";
							}
						}
					}
				}
			}
		}
		
		return msg;
	}
	
	
	/**
	 * <p>判断产商品售价中是否存在低于分销限价的</p>
	 * <p>如果商品分销限价=0，则不必考虑限价，任何价格都可以</p>
	 * @param xsdProducts
	 * @return true:存在低于分销限价的;fasle:不存在低于分销限价的
	 */
	public boolean checkFxxj(List xsdProducts){
		boolean is = false;
		
		if(xsdProducts != null && xsdProducts.size()>0){
			for(int i=0;i<xsdProducts.size();i++){
				XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
				if(xsdProduct != null){
					if(!xsdProduct.getProduct_id().equals("") && !xsdProduct.getProduct_name().equals("")){
						double price = xsdProduct.getPrice();  //销售价格
						double fxxj = productDao.getProductFxxj(xsdProduct.getProduct_id());
						
						if(fxxj != 0 && price < fxxj){
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


	public CkdDAO getCkdDao() {
		return ckdDao;
	}


	public void setCkdDao(CkdDAO ckdDao) {
		this.ckdDao = ckdDao;
	}


	public ClientsDAO getClientsDao() {
		return clientsDao;
	}


	public void setClientsDao(ClientsDAO clientsDao) {
		this.clientsDao = clientsDao;
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


	public XsdDAO getXsdDao() {
		return xsdDao;
	}


	public void setXsdDao(XsdDAO xsdDao) {
		this.xsdDao = xsdDao;
	}


	public XsskDAO getXsskDao() {
		return xsskDao;
	}


	public void setXsskDao(XsskDAO xsskDao) {
		this.xsskDao = xsskDao;
	}


	public ProductDAO getProductDao() {
		return productDao;
	}


	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}


	public InitParamDAO getInitParamDao() {
		return initParamDao;
	}


	public void setInitParamDao(InitParamDAO initParamDao) {
		this.initParamDao = initParamDao;
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
	
}
