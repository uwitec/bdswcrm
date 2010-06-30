package com.sw.cms.service;

/**
 * ���۵�����������ר�ã�
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
 * ���۵�����
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
	 * ȡ���۵��б�(����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsdList(String con,int curPage, int rowsPerPage){
		return xsdDao.getXsdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ��������������ҵ��->�ɹ��������б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getFxddList(String con,int curPage,int rowsPerPage){
		return xsdDao.getFxddList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * �������۵���Ϣ
	 * @param xsd
	 * @param xsdProducts
	 */
	public void updateXsd(Xsd xsd,List xsdProducts){
		
		//������۶�����Ӧ�ĳ��ⵥ�Ѿ����ɣ����ز�������
		if(ckdDao.isCkdExist(xsd.getId())){
			return;
		}
		
		//�жϲ��������۵����տ�״̬
		double xsdje = xsd.getXsdje(); //���۵��ϼƽ��
		double skje = xsd.getSkje();   //�տ���
		
		//�����۵��ܽ��С�ڵ���0���տ���ʽΪ����
		if(xsdje <= 0){
			xsd.setSkxs("����");
		}else{
			if(skje > 0 && skje < xsdje){
				//����տ������0������С�����۵����
				xsd.setSkxs("��������");
			}else if(skje >= xsdje){
				xsd.setSkxs("����");
			}else{
				xsd.setSkxs("δ��");
			}
		}
		
		//�жϲ������տ����ڼ�Ӧ������
		if(xsd.getSklx().equals("�ֽ�")){
			xsd.setSkrq(xsd.getCreatdate()); //�տ�����
			xsd.setYsrq(xsd.getCreatdate()); //Ӧ������
		}else{
			if(xsd.getSkje() != 0){
				xsd.setSkrq(xsd.getCreatdate()); //�緢���տ���浱ǰ����Ϊ�տ�����
			}

			String ysrq = xsd.getCreatdate();  //Ĭ��Ӧ������Ϊ��������
			long zq = new Long(xsd.getZq()+"").longValue(); //�ͻ�����
			
			if(ysrq != null && !ysrq.equals("")){
				ysrq = DateComFunc.formatDate(DateComFunc.addDay((DateComFunc.strToDate(ysrq, "yyyy-MM-dd")),zq),"yyyy-MM-dd");
			}
			xsd.setYsrq(ysrq);
		}
		
		//���ӻ�Ա����
		int hyjf;
		Map mp = (Map)hykjfDao.getHyxx(xsd.getHykh(),xsd.getClient_name());
		if(mp!=null && mp.size()>0)
		{
		String xfje = StringUtils.nullToStr(mp.get("xfje"));   //���۽��
		String dyjf = StringUtils.nullToStr(mp.get("dyjf"));   //��Ӧ����
		
		hyjf=0;
		hyjf=(int)(xsd.getXsdje())/Integer.parseInt(xfje)*Integer.parseInt(dyjf);
		}
		else
		{
		hyjf=0;
		}
		xsd.setHyjf(hyjf);
		
		xsdDao.updateXsd(xsd, xsdProducts);	
		
		if(!xsd.getState().equals("�ѱ���")){
			
			//�ڶ����������Ӧ���ⵥ
			this.addCkd(xsd, xsdProducts); //�����Ӧ���ⵥ
			
			if(xsd.getSkje() != 0){
				
				//����������������տ���Ϣ
				this.saveXssk(xsd);//��������տ���Ϣ
				
				//���Ĳ��������˻����
				this.addAccountJe(xsd);//�����˻����
			}
			
			//������ʽΪˢ��������POS����Ų�Ϊ�գ��Զ����������Ϣ
			if(xsd.getSkfs().equals("ˢ��") && !xsd.getPos_id().equals("")){
				this.saveQtzc(xsd);
			}
		}
		
		//�������״̬Ϊ������������ϵͳ��Ϣ
		if(xsd.getSp_state() != null && xsd.getSp_state().equals("2")){
			this.saveMsg(xsd.getId(), xsd.getCzr(),xsd.getSp_type());
		}
		
	}
	
	
	/**
	 * <p>���·���������Ϣ</p>
	 * <p>������Ӻ͸������ַ�ʽ</p>
	 * <p>���������Ŵ��������</p>
	 * <p>���������Ų����������</p>
	 * @param fxdd ��������
	 * @param xsdProudcts ����������Ʒ��ϸ
	 */
	public void updateFxdd(Fxdd fxdd,List xsdProducts){
		
		if(fxdd.getState().equals("���ύ")){
			fxdd.setWlzt("������");
		}
		
		if(fxdd.getFxdd_id().equals("")){
			//�������������ڣ����붩��
			fxdd.setFxdd_id(xsdDao.getXsdID());
			xsdDao.saveFxdd(fxdd, xsdProducts);
		}else{
			//�����������ڣ����¶���
			xsdDao.updateFxdd(fxdd, xsdProducts);
		}
		
		if(fxdd.getState().equals("���ύ")){
			//������Ӧ�����۶���,״̬Ϊ���ύ
			this.addXsdByFxdd(fxdd, xsdProducts);
			
			//������Ӧ�ĳ��ⵥ��״̬Ϊ������
			this.addCkdByFxdd(fxdd, xsdProducts);
		}
	}
	
	
	/**
	 * �������۶������������
	 * @param lsd_id
	 * @param sp_state
	 * @param spr
	 */
	public void saveSp(String xsd_id,String sp_state,String spr){
		
		//������۶�����Ӧ�ĳ��ⵥ�Ѿ����ɣ����ز�������
		if(ckdDao.isCkdExist(xsd_id)){
			return;
		}
		
		xsdDao.saveSp(xsd_id, sp_state, spr);
		
		Xsd xsd = (Xsd)xsdDao.getXsd(xsd_id); //���۵�
		List xsdProducts = xsdDao.getXsdProducts(xsd_id); //���۵������Ʒ
		
		//���������ͨ�������޸����۶���״̬
		if(sp_state.equals("3")){
			xsd.setState("���ύ");
			updateXsd(xsd,xsdProducts);
		}
		
		//����������Ϣ
		this.saveMsg(xsd.getCzr(), spr, xsd_id, sp_state);
	}
	
	
	/**
	 * ���ݱ�Ż�ȡ���۵���Ϣ
	 * @param id
	 * @return
	 */
	public Object getXsd(String id){
		return xsdDao.getXsd(id);
	}
	
	
	/**
	 * ���ݷ����������ȡ����������Ϣ
	 * @param fxdd_id
	 * @return
	 */
	public Object getFxdd(String fxdd_id){
		return xsdDao.getFxdd(fxdd_id);
	}
	
	
	/**
	 * ���ݱ�Ż�ȡ���������Ʒ
	 * @param id
	 * @return
	 */
	public List getXsdProducts(String id){
		return xsdDao.getXsdProducts(id);
	}
	
	
	/**
	 * ɾ�����۵���Ϣ
	 * @param id
	 */
	public void delXsd(String id){		
		
		//������۶�����Ӧ�ĳ��ⵥ�Ѿ����ɣ����ز�������
		if(ckdDao.isCkdExist(id)){
			return;
		}
		
		xsdDao.delXsd(id);
	}
	
	
	/**
	 * ɾ������������Ϣ
	 * @param fxdd_id
	 */
	public void delFxdd(String fxdd_id){
		xsdDao.delFxdd(fxdd_id);
	}
	
	
	/**
	 *�жϿͻ��Ƿ��г���Ӧ�տ� 
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
				
				if(curDate.compareTo((String)map.get("ysrq"))>0){ //����
					is = true;
					break;
				}
			}
		}
		return is;
	}
	
	
	/**
	 * �ж��Ƿ��г���Ӧ�տ�
	 * @param client_id
	 * @return
	 */
	public boolean isCeysk(String client_id,double bcysje){
		boolean is = false;
		
		//�ͻ���ǰӦ�տ�
		double ysk = initParamDao.getClientYinshou(client_id);
		
		Clients client = (Clients)clientsDao.getClient(client_id);
		
		double xe = client.getXe();
		
		if((ysk+bcysje) > xe){
			is = true;
		}
		return is;
	}
	
	
	/**
	 *�жϿͻ��Ƿ��д˻�Ա���� 
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
	 * ȡ�߳�������Ȩ�޽�ɫ�б�
	 * @return
	 */
	public String getCespRoles(){
		return xsdDao.getCespRoles();
	}
	
	
	/**
	 * ����ϵͳ��Ϣ
	 * @param xsd_id
	 * @param sender_id
	 * @param sp_type
	 */
	private void saveMsg(String xsd_id,String sender_id,String sp_type){
		
		List users = new ArrayList();
		
		//1�����ڿ�������2������ҵ����޼�������3������������4�������޼�������
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
				msg.setMsg_body("��һ�����������۶������뼰ʱ�������������Ϊ��" + xsd_id);
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
	private void saveMsg(String reciever_id,String sender_id,String xsd_id,String sp_state){
		
		SysMsg msg = new SysMsg();
		msg.setReciever_id(reciever_id);
		msg.setSender_id(sender_id);
		if(sp_state.equals("3")){
			msg.setMsg_body("���ύ�����۶�����" + xsd_id + "��������ͨ����");
		}else{
			msg.setMsg_body("���ύ�����۶�����" + xsd_id + "��û������ͨ����");
		}		
		msg.setMobile_num(((SysUser)userDao.getUser(sender_id)).getMobile());
		
		sysMsgDao.saveMsg(msg);
	}
	
	/**
	 * ���ݳ��ⵥ���ʹ�������Ϣ
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
				msg.setMsg_body("���µĴ����ⵥ�����Ϊ��" + ckd.getCkd_id());
				
				sysMsgDao.saveMsg(msg);
			}
		}
	}
	
	
	/**
	 * ȡ���������۵��б�
	 * @param con
	 * @return
	 */
	public List getDspXsdList(String con){
		return xsdDao.getDspXsdList(con);
	}

	
	/**
	 * �����Ӧ���ⵥ
	 * Ӧͬʱ���´����̲ɹ�����������״̬
	 * @param xsd
	 * @param xsdProducts
	 */
	private void addCkd(Xsd xsd,List xsdProducts){
		Ckd ckd = new Ckd();
		
		String ckd_id = ckdDao.getCkdID();
		
		String wlzt = "";
		String fxdd_id = xsd.getId();
		
		ckd.setClient_name(xsd.getClient_name());  //�ͻ����
		ckd.setTel(xsd.getKh_lxdh());  //�ͻ���ϵ�绰
		ckd.setCreatdate(xsd.getCreatdate());
		ckd.setXsry(xsd.getFzr());

		ckd.setState("������");
		wlzt = "������";
			
		ckd.setYsfs(xsd.getYsfs());  //���䷽ʽ
		ckd.setXsd_id(xsd.getId());
		ckd.setCkd_id(ckd_id);
		ckd.setCzr(xsd.getCzr());
		ckd.setSkzt(xsd.getSkxs());
		ckd.setMs("���۳��⣬���۵���� ["  + xsd.getId() + "]��" + xsd.getMs());
		
		ckd.setClient_lxr(xsd.getKh_lxr());
		ckd.setClient_lxr_address(xsd.getKh_address());
		ckd.setClient_lxr_tel(xsd.getKh_lxdh());
		
		ckd.setYfzf_type(xsd.getYfzf_type());
		
		//���·�����������״̬
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
		
		//���ʹ����ⵥ��Ϣ
		this.saveMsg(ckd);
	}
	
	
	
	
	/**
	 * ���ݷ���������Ϣ������Ӧ���ⵥ��״̬Ϊ������
	 * @param fxdd
	 * @param xsdProducts
	 */
	private void addCkdByFxdd(Fxdd fxdd, List xsdProducts){
		Ckd ckd = new Ckd();
		
		ckd.setCkd_id(ckdDao.getCkdID());
		ckd.setClient_name(fxdd.getClient_name());		
		ckd.setTel(fxdd.getLxdh());  //�ͻ���ϵ�绰
		ckd.setCreatdate(fxdd.getCreatdate());
		ckd.setXsry("");
		ckd.setState("������");
		ckd.setXsd_id(fxdd.getFxdd_id());
		ckd.setSkzt("δ��");
		ckd.setYsfs(fxdd.getYsfs());
		ckd.setMs("�����������ɳ��ⵥ��������� ["  + fxdd.getFxdd_id() + "]");
		
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
	 * ������Ʒ���
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
	 * ���������տ���Ϣ
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
		xssk.setState("���ύ");
		xssk.setCzr(xsd.getCzr());
		xssk.setRemark("�����տ���۵���� [" + xsd.getId() + "]");
		xssk.setDelete_key(xsd.getId());
		
		List<XsskDesc> xsskDescs = new ArrayList<XsskDesc>();
		
		XsskDesc xsskDesc = new XsskDesc();
		xsskDesc.setXsd_id(xsd.getId());
		xsskDesc.setXssk_id(xssk_id);
		xsskDesc.setBcsk(xsd.getSkje());
		xsskDesc.setRemark("�����տ���۵���� [" + xsd.getId() + "]");
		
		xsskDescs.add(xsskDesc);
		
		xsskDao.saveXssk(xssk, xsskDescs);
		
		//������˵���Ϣ
		this.saveAccountDzd(xssk.getSkzh(), xssk.getSkje(), xssk.getCzr(), xssk.getJsr(), "�����տ���[" + xssk.getId() + "]",xssk.getId());
	}
	
	/**
	 * ���տ������˻���
	 * @param lsd
	 */
	private void addAccountJe(Xsd xsd){
		String account_id = xsd.getSkzh();
		double je = xsd.getSkje();
		
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
	 * ���ݷ�������������Ӧ�����۵�
	 * @param fxdd
	 * @param xsdProducts
	 */
	private void addXsdByFxdd(Fxdd fxdd,List xsdProducts){
		Xsd xsd = new Xsd();
		
		xsd.setId(fxdd.getFxdd_id());     //�������
		xsd.setCreatdate(fxdd.getCreatdate());   //����ʱ��
		xsd.setClient_name(fxdd.getClient_name()); //�ͻ����
		xsd.setKh_address(fxdd.getAddress());
		xsd.setKh_lxr(fxdd.getLxr());
		xsd.setKh_lxdh(fxdd.getLxdh());
		xsd.setYsfs(fxdd.getYsfs());        //���䷽ʽ
		xsd.setSklx("����");        //��������
		xsd.setSkxs("δ��");
		xsd.setZq(1);
		
		xsd.setXsdje(fxdd.getHjje());        //�ϼƽ��
		xsd.setMs(fxdd.getRemark());      //��ע������Ϣ
		xsd.setState("���ύ");
		
		//������Ʒ��ϸ�ĳɱ���
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
		
		//�������۵���Ϣ
		xsdDao.saveXsd(xsd, xsdProducts2);
	}
	
	
	
	/**
	 * ȡ��ǰ���õ����ۺ�
	 * @return
	 */
	public String updateXsdId(){
		return xsdDao.getXsdID();
	}
	
	
	/**
	 * �������۵�����ж����۵��Ƿ����
	 * @param xsd_id
	 * @return
	 */
	public boolean isHasXsdByID(String xsd_id){
		return xsdDao.isHasXsdByID(xsd_id);
	}
	
	
	/**
	 * �ж����۵����Ƿ���ڸ���Ʒ
	 * @param xsd_id
	 * @param product_id
	 * @return
	 */
	public boolean isHasXsdProduct(String xsd_id,String product_id){
		return xsdDao.isHasXsdProduct(xsd_id, product_id);
	}
	
	
	/**
	 * �жϿ�����Ƿ����������Ҫ
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
						
						//�ж���Ʒ�Ƿ��ǿ����Ʒ,ֻ�Կ����Ʒ�Ž��п�������ж�
						Product product = (Product)productDao.getProductById(product_id);
						if(product.getProp().equals("�����Ʒ")){		
							int cknums = xsdProduct.getNums();  //Ҫ��������						
							int kcnums = productKcDao.getKcNums(product_id, store_id);//�������
							
							if(cknums>kcnums){
								msg += "��Ʒ��" + xsdProduct.getProduct_name() + "��" + StaticParamDo.getStoreNameById(store_id) +" �е�ǰ���Ϊ��" + kcnums + "  �޷����⣬���ȵ���<br>";
							}
						}
					}
				}
			}
		}
		
		return msg;
	}
	
	
	/**
	 * <p>�жϲ���Ʒ�ۼ����Ƿ���ڵ��ڷ����޼۵�</p>
	 * <p>�����Ʒ�����޼�=0���򲻱ؿ����޼ۣ��κμ۸񶼿���</p>
	 * @param xsdProducts
	 * @return true:���ڵ��ڷ����޼۵�;fasle:�����ڵ��ڷ����޼۵�
	 */
	public boolean checkFxxj(List xsdProducts){
		boolean is = false;
		
		if(xsdProducts != null && xsdProducts.size()>0){
			for(int i=0;i<xsdProducts.size();i++){
				XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
				if(xsdProduct != null){
					if(!xsdProduct.getProduct_id().equals("") && !xsdProduct.getProduct_name().equals("")){
						double price = xsdProduct.getPrice();  //���ۼ۸�
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
	 * �������۵���Ϣ���ˢ��֧������
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
		qtzc.setRemark("ˢ�������ѣ������۵�[" + xsd.getId() + "]�Զ�����");
		qtzc.setCzr(xsd.getCzr());
		qtzc.setState("���ύ");
		qtzc.setYwy(xsd.getFzr());
		qtzc.setSqr(xsd.getFzr());
		qtzc.setYwy_dept(dept);
		qtzc.setZcxm("ˢ��������");
		qtzc.setFklx("ˢ��");
		qtzc.setFysq_id("��");
		
		qtzcDao.saveQtzc(qtzc);  //��������֧����һ����ã�
		
		accountsDao.updateAccountJe(xsd.getSkzh(),posTypeDao.getBrushCardfy(xsd.getPos_id(), xsd.getSkje())); //�޸��˻����
		
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
		accountDzd.setRemark("һ����ã����[" + id + "]");
		accountDzd.setCzr(xsd.getCzr());
		accountDzd.setJsr(xsd.getFzr());
		accountDzd.setAction_url("viewQtzc.html?id=" + id);		
		accountDzdDao.addDzd(accountDzd);   //����ʽ��׼�¼
		
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
