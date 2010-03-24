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
	 * ȡ��ⵥ�б�(����ҳ��־)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getRkdList(String con,int curPage, int rowsPerPage){
		return rkdDao.getRkdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ������ⵥ��Ϣ������������Ʒ��Ϣ��
	 * @param rkd
	 * @param rkdProducts
	 */
	public void saveRkd(Rkd rkd,List rkdProducts){
		rkdDao.saveRkd(rkd, rkdProducts);  //������ⵥ��������Ʒ��Ϣ
		
		if((rkd.getState()).equals("�����")){  //�����ⵥ״̬Ϊ����⣬����Ҫ���¿����Ϣ
			productKcDao.updateProductKc(rkd, rkdProducts);
			jhdDao.updateJhdState(rkd.getJhd_id(), "�����");
			thdDao.updateThdState(rkd.getJhd_id(), "�����");
			this.updateSerialNum(rkd, rkdProducts);
		}
	}
	
	
	/**
	 * ������ⵥ��Ϣ������������Ʒ��Ϣ��
	 * @param rkd
	 * @param rkdProducts
	 */
	public void updateRkd(Rkd rkd,List rkdProducts){
		rkdDao.updateRkd(rkd, rkdProducts);  //������ⵥ��������Ʒ��Ϣ
		
		if((rkd.getState()).equals("�����")){  //�����ⵥ״̬Ϊ����⣬����Ҫ���¿����Ϣ
			productKcDao.updateProductKc(rkd, rkdProducts);
			
			//���½�����ʵ�ʳɽ���Ϣ
			this.updateJhdSjcjInfo(rkd, rkdProducts);
			
			//�������ҵ�񵥾�״̬Ϊ�����
			jhdDao.updateJhdState(rkd.getJhd_id(), "�����");
			
			this.updateSerialNum(rkd, rkdProducts);
		}
	}
	
	
	/**
	 * ɾ����ⵥ��Ϣ������������Ʒ��
	 * @param rkd_id
	 */
	public void delRkd(String rkd_id){
		rkdDao.delRkd(rkd_id);
	}
	
	
	/**
	 * ȡ��ǰ������ⵥ���
	 * @return
	 */
	public String updateRkdId(){
		return rkdDao.getRkdID();
	}
	
	
	/**
	 * ������ⵥIDȡ��ⵥ��Ϣ
	 * @param rkd_id
	 * @return
	 */
	public Object getRkd(String rkd_id){
		return rkdDao.getRkd(rkd_id);
	}
	
	
	/**
	 * ������ⵥIDȡ��ⵥ��Ʒ�б�
	 * @param rkd_id
	 * @return
	 */
	public List getRkdProducts(String rkd_id){
		return rkdDao.getRkdProducts(rkd_id);
	}
	
	
	/**
	 * ȡ���вֿ��б�
	 * @return
	 */
	public List getAllStoreList(){
		return storeDao.getAllStoreList();
	}
	
	
	/**
	 * ������ⵥ��Ų鿴��ⵥ�Ƿ��Ѿ����
	 * @param xsd_id
	 * @return
	 */
	public boolean isJhdSubmit(String rkd_id){
		return rkdDao.isJhdSubmit(rkd_id);
	}
	
	
	/**
	 * ����������к�
	 * <p>�����������</p>
	 * <p>һ���ɹ������������˻�</p>
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
							state = "�ڿ�";
							yw_url = "viewJhd.html?id=";
							yw_type = "�ɹ�";
						}else{
							state = "�ڿ�";
							yw_url = "viewThd.html?thd_id=";
							yw_type = "�����˻�";
						}
						
						for(int k=0;k<arryNums.length;k++){
							serialNumMng.setProduct_id(rkdProduct.getProduct_id());
							serialNumMng.setProduct_name(rkdProduct.getProduct_name());
							serialNumMng.setProduct_xh(rkdProduct.getProduct_xh());
							serialNumMng.setSerial_num(arryNums[k]);
							serialNumMng.setState(state);
							serialNumMng.setStore_id(rkd.getStore_id());
							serialNumMng.setYj_flag("0");
							
							serialNumDao.updateSerialNumState(serialNumMng); //�������к�״̬
							
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
							
							serialNumDao.saveSerialFlow(serialNumFlow);  //�������к���ת����
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * ��ⵥ�˻ز���
	 * @param rkd
	 */
	public void doTh(Rkd rkd){
		
		//ɾ����Ӧ��ⵥ
		rkdDao.delRkd(rkd.getRkd_id());
		
		String jhd_id = rkd.getJhd_id();
		
		//����ǲɹ��������޸Ĳɹ�����״̬���˻ر��
		if(jhd_id.indexOf("JH") != -1){
			jhdDao.updateJhdAfterRkdTh(jhd_id, "�ѱ���", "1");
			
			//�鿴�ɹ�����ʱ���Ƿ��и����
			//������������˻زɹ�����ʱ��Ҫ������ֽ���ˮ���˻����
			Cgfk cgfk = cgfkDao.getCgfkByDeleteKey(jhd_id);
			
			if(cgfk != null){
				
				//ɾ���ɹ�������Ϣ
				cgfkDao.delCgfk(cgfk.getId());
				
				//ɾ���ֽ���ˮ��Ϣ
				accountDzdDao.delDzd(cgfk.getId());
				
				//�����˻����
				accountsDao.addAccountJe(cgfk.getFkzh(), cgfk.getFkje());
			}
		}
	}
	
	
	/**
	 * �޸���Ӧ�ɹ�����ʵ�ʳɽ��������
	 * @param ckd
	 * @param ckdProducts
	 */
	private void updateJhdSjcjInfo(Rkd rkd,List rkdProducts){
		double sjcjje = 0; //�ɹ�����ʵ�ʳɽ����
		String jhd_id = rkd.getJhd_id();
		
		//�����Ӧҵ�񵥾ݲ��ǽ����������˳�
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
