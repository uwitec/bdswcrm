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
 * ���۵���ش���
 * ֻ���ڵ�ͳ��ʽ���ú���ȥ����ǿ�����к����
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
	 * ��ȡ���۵��б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getLsdList(String con,int curPage, int rowsPerPage){
		return lsdDao.getLsdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ȡ���������۵��б�
	 * @return
	 */
	public List getDspLsdList(){
		return lsdDao.getDspLsdList();
	}
	
	
	/**
	 * �������۵���Ϣ
	 * @param lsd
	 * @param lsdProducts
	 * @return
	 */
	public void saveLsd(Lsd lsd,List lsdProducts){
		
		//������۵����ύ����������
		if(lsdDao.isLsdSubmit(lsd.getId())){
			return;
		}
		
		lsdDao.saveLsd(lsd, lsdProducts);		
		
		if(lsd.getState().equals("���ύ")){
			
			//������Ӧ���ⵥ��Ϣ(״̬Ϊ�ѳ���)
			//ͬʱ����������кţ���ֻ����ϵͳ��ʽʹ�ú���ܴ������к�
			this.addCkd(lsd, lsdProducts);
			
			//���²�Ʒ���(���ܴ��ڸ����)
			this.updateProductKc(lsd, lsdProducts); //���²�Ʒ���
			
			this.saveXssk(lsd);//���������տ���Ϣ
			this.addAccountJe(lsd);//�޸��˻����
			
			if(lsd.getHas_yushk().equals("��")){ //���Ԥ�տ���Ϣ
				lsyskDao.updateLsyskType(lsd.getYushk_id(), "�ѳ��");
			}
			
			//������ʽΪˢ��������POS����Ų�Ϊ�գ��Զ����������Ϣ
			if(lsd.getFkfs().equals("ˢ��") && !lsd.getPos_id().equals("")){
				this.saveQtzc(lsd);
			}
		}
		
		//�������״̬Ϊ������������ϵͳ��Ϣ
		if(lsd.getSp_state() != null && lsd.getSp_state().equals("2")){
			this.saveMsg(lsd.getId(), lsd.getCzr());
		}
	}
	
	
	/**
	 * �������۵���Ϣ
	 * @param lsd
	 * @param lsdProducts
	 */
	public void updateLsd(Lsd lsd,List lsdProducts){
		
		//������۵����ύ����������
		if(lsdDao.isLsdSubmit(lsd.getId())){
			return;
		}
		
		lsdDao.updateLsd(lsd, lsdProducts);
		
		if(lsd.getState().equals("���ύ")){
			
			//������Ӧ���ⵥ��Ϣ(״̬Ϊ�ѳ���)
			//ͬʱ����������кţ���ֻ����ϵͳ��ʽʹ�ú��ܴ������к�
			this.addCkd(lsd, lsdProducts);

			//���²�Ʒ���
			this.updateProductKc(lsd, lsdProducts); 
			
			this.saveXssk(lsd);//���������տ���Ϣ
			this.addAccountJe(lsd);//�޸��˻����
			
			if(lsd.getHas_yushk().equals("��")){ //���Ԥ�տ���Ϣ
				lsyskDao.updateLsyskType(lsd.getYushk_id(), "�ѳ��");
			}
			
			//������ʽΪˢ��������POS����Ų�Ϊ�գ��Զ����������Ϣ
			if(lsd.getFkfs().equals("ˢ��") && !lsd.getPos_id().equals("")){
				this.saveQtzc(lsd);
			}
		}	
		
		//�������״̬Ϊ������������ϵͳ��Ϣ
		if(lsd.getSp_state() != null && lsd.getSp_state().equals("2")){
			this.saveMsg(lsd.getId(), lsd.getCzr());
		}
		
	} 
	
	
	/**
	 * �������۵�IDȡ���۵���Ϣ
	 * @param id
	 * @return
	 */
	public Object getLsd(String id){
		return lsdDao.getLsd(id);
	}
	
	
	/**
	 * ����lsd_idȡ�������Ʒ
	 * @param id
	 * @return
	 */
	public List getLsdProducts(String id){
		return lsdDao.getLsdProducts(id);
	}
	
	
	/**
	 * ɾ�����۵���Ϣ������������Ʒ��
	 * @param id
	 */
	public void delLsd(String id){
		
		//������۵����ύ����������
		if(lsdDao.isLsdSubmit(id)){
			return;
		}
		
		lsdDao.delLsd(id);
	}	
	
	
	/**
	 * ȡ��ǰ���õ����۵����
	 * @return
	 */
	public String updateLsdId(){
		return lsdDao.getLsdID();
	}
	
	
	
	/**
	 * <p>�������۵������Ӧ���ⵥ</p>
	 * <p>�����ǿ�����к������Ӧ���к���Ϣ</p?
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
		ckd.setState("�ѳ���");
		ckd.setSkzt("����");
		ckd.setMs("���۳��ⵥ�����۵���� [" + lsd.getId() + "]");
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
						
						
						//ֻ����ϵͳ��ʽʹ�ú��ȥ�޸Ĳ�Ʒ�Ŀ��ʹ������к�
						//ϵͳ����ǰҲ�������Ʒ���кţ�����Ӳ��ǿ�ƣ�������������к�ϵͳ������
						
						//if(sysInitSetDao.getQyFlag().equals("1")){
						//ǿ�����кŴ���
						
						if((lsdProduct.getQz_serial_num() != null) && (!lsdProduct.getQz_serial_num().equals(""))){
							String[] arryNums = (lsdProduct.getQz_serial_num()).split(",");
							
							SerialNumMng serialNumMng = new SerialNumMng();
							SerialNumFlow serialNumFlow = new SerialNumFlow();
							
							for(int k=0;k<arryNums.length;k++){
								serialNumMng.setProduct_id(lsdProduct.getProduct_id());
								serialNumMng.setProduct_name(lsdProduct.getProduct_name());
								serialNumMng.setProduct_xh(lsdProduct.getProduct_xh());
								serialNumMng.setSerial_num(arryNums[k]);
								serialNumMng.setState("����");
								serialNumMng.setStore_id("");
								serialNumMng.setYj_flag("0");
								serialNumDao.updateSerialNumState(serialNumMng); //�������к�״̬
								
								serialNumFlow.setClient_name(lsd.getClient_name());
								serialNumFlow.setTel(lsd.getLxdh() + " " + lsd.getMobile());
								serialNumFlow.setCzr(lsd.getCzr());
								serialNumFlow.setYwtype("����");
								serialNumFlow.setFs_date(lsd.getCreatdate());
								serialNumFlow.setJsr(StaticParamDo.getRealNameById(lsd.getXsry()));
								serialNumFlow.setKf_dj_id(ckd_id);
								serialNumFlow.setSerial_num(arryNums[k]);
								serialNumFlow.setKf_url("viewCkd.html?ckd_id=");
								serialNumFlow.setYw_dj_id(lsd.getId());
								serialNumFlow.setYw_url("viewLsd.html?id=");
								serialNumFlow.setXsdj(lsdProduct.getPrice());
								
								serialNumDao.saveSerialFlow(serialNumFlow);  //�������к���ת����
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
	 * ���������տ���Ϣ
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
		xssk.setState("���ύ");
		xssk.setCzr(lsd.getCzr());
		xssk.setRemark("�����տ���۵���� [" + lsd.getId() + "]");
		
		List xsskDescs = new ArrayList();
		
		XsskDesc xsskDesc = new XsskDesc();
		xsskDesc.setXsd_id(lsd.getId());
		xsskDesc.setFsje(lsd.getLsdje());
		xsskDesc.setXssk_id(xssk_id);
		xsskDesc.setBcsk(lsd.getSkje());
		xsskDesc.setRemark("���۵�");
		
		xsskDescs.add(xsskDesc);
		
		xsskDao.saveXssk(xssk, xsskDescs);
		
		this.saveAccountDzd(xssk.getSkzh(), xssk.getSkje(), xssk.getCzr(), xssk.getJsr(), "�����տ���[" + xssk.getId() + "]",xssk.getId());
	}
	
	
	/**
	 * ���²�Ʒ���
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
	 * ���տ������˻���
	 * @param lsd
	 */
	private void addAccountJe(Lsd lsd){
		String account_id = lsd.getSkzh();
		double je = lsd.getSkje();
		
		accountsDao.addAccountJe(account_id, je);
	}
	
	
	/**
	 * ����ʽ��׼�¼
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
	 * �жϿ�����Ƿ����������Ҫ
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
						
						//�ж���Ʒ�Ƿ��ǿ����Ʒ,ֻ�Կ����Ʒ�Ž��п�������ж�
						Product product = (Product)productDao.getProductById(product_id);
						if(product.getProp().equals("�����Ʒ")){						
							int cknums = lsdProduct.getNums();  //Ҫ��������						
							int kcnums = productKcDao.getKcNums(product_id, store_id);//�������
							
							if(cknums>kcnums){
								msg += "��Ʒ��" + lsdProduct.getProduct_name() + "��" + StaticParamDo.getStoreNameById(store_id) +" �е�ǰ���Ϊ��" + kcnums + "  �޷����⣬���۵��ѱ��棬���ȵ������ٳ��⣡<br>";
							}
						}
					}
				}
			}
		}
		
		return msg;
	}
	
	
	/**
	 * �Ƿ���ڵ��������޼۵Ĳ�Ʒ
	 * @param lsdProducts
	 * @return
	 */
	public boolean isExistLowLsxj(Lsd lsd,List lsdProducts){
		boolean is = false;
		double sd = lsdDao.getLssd();  //����˰��
		String fplx = lsd.getFplx();   //��Ʊ����
		if(lsdProducts != null && lsdProducts.size()>0){
			for(int i=0;i<lsdProducts.size();i++){
				LsdProduct lsdProduct = (LsdProduct)lsdProducts.get(i);
				if(lsdProduct != null){
					if(!lsdProduct.getProduct_id().equals("") && !lsdProduct.getProduct_name().equals("")){
						double price = lsdProduct.getPrice();  //��ǰ����
						double lsxj = productDao.getProductLsxj(lsdProduct.getProduct_id()); //�����޼�
						
						//�����Ʊ�����ǳ��ⵥ���޼ۿ���Ϊ�޼ۼ�ȥ˰��
						if(fplx.equals("���ⵥ")){
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
	 * ����ϵͳ��Ϣ
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
				msg.setMsg_body("��һ�����������۵����뼰ʱ���������۵����Ϊ��" + lsd_id);
				msg.setMobile_num(StringUtils.nullToStr(map.get("mobile")));
				
				sysMsgDao.saveMsg(msg);
			}
		}
	}
	
	
	/**
	 * ����ϵͳ��Ϣ
	 * @param reciever_id
	 * @param sender_id
	 * @param lsd_id
	 */
	public void saveMsg(String reciever_id,String sender_id,String lsd_id,String sp_state){
		
		SysMsg msg = new SysMsg();
		msg.setReciever_id(reciever_id);
		msg.setSender_id(sender_id);
		if(sp_state.equals("3")){
			msg.setMsg_body("���ύ�����۵���" + lsd_id + "��������ͨ����");
		}else{
			msg.setMsg_body("���ύ�����۵���" + lsd_id + "��û������ͨ����");
		}		
		msg.setMobile_num(((SysUser)userDao.getUser(sender_id)).getMobile());
		
		sysMsgDao.saveMsg(msg);
	}
	
	
	/**
	 * �������۵����������<BR>
	 * ���������ͨ�����ύ���۵�
	 * @param lsd_id
	 * @param sp_state
	 * @param spr
	 */
	public void saveSp(String lsd_id,String sp_state,String spr){
		
		//������۵����ύ����������
		if(lsdDao.isLsdSubmit(lsd_id)){
			return;
		}
		
		lsdDao.saveSp(lsd_id, sp_state, spr);
		
		Lsd lsd = (Lsd)lsdDao.getLsd(lsd_id); //���۵�
		List lsdProducts = lsdDao.getLsdProducts(lsd_id);
		
		//���������ͨ�������޸����۵�״̬
		if(sp_state.equals("3")){
			lsd.setState("���ύ");
			updateLsd(lsd,lsdProducts);
		}
		
		//����������Ϣ
		this.saveMsg(lsd.getCzr(), spr, lsd_id, sp_state);
	}
	
	
	/**
	 * ȡ����˰��
	 * @return
	 */
	public double getLssd(){
		return lsdDao.getLssd();
	}
	
	
	/**
	 * ��������˰��
	 * @param sd
	 */
	public void saveLssd(String sd){
		lsdDao.saveLssd(sd);
	}
	
	
	/**
	 * ȡ��ɱ���
	 * @return
	 */
	public Map getTcbl(){
		return lsdDao.getTcbl();
	}
	
	
	/**
	 * ������ɱ���
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
		qtzc.setRemark("ˢ�������ѣ������۵�[" + lsd.getId() + "]�Զ�����");
		qtzc.setCzr(lsd.getCzr());
		qtzc.setState("���ύ");
		qtzc.setYwy(lsd.getXsry());
		qtzc.setSqr(lsd.getXsry());
		qtzc.setYwy_dept(dept);
		qtzc.setZcxm("ˢ��������");
		qtzc.setFklx("ˢ��");
		qtzc.setFysq_id("��");
		
		qtzcDao.saveQtzc(qtzc);  //��������֧����һ����ã�
		
		accountsDao.updateAccountJe(lsd.getSkzh(),posTypeDao.getBrushCardfy(lsd.getPos_id(), lsd.getSkje())); //�޸��˻����
		
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
		accountDzd.setRemark("һ����ã����[" + id + "]");
		accountDzd.setCzr(lsd.getCzr());
		accountDzd.setJsr(lsd.getXsry());
		accountDzd.setAction_url("viewQtzc.html?id=" + id);		
		accountDzdDao.addDzd(accountDzd);   //����ʽ��׼�¼
		
	}
	
	
	/**
	 * �鿴���۵��Ƿ��Ѿ��ύ
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
