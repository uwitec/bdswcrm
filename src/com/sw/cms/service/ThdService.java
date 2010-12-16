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
 * <p>�����˻�������</p>
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
	 * ��ѯ�˻����б�����ҳ
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getThdList(String con,int curPage, int rowsPerPage){		
		return thdDao.getThdList(con, curPage, rowsPerPage);		
	}
	
	
	/**
	 * �����˻�����Ϣ
	 * @param thd
	 * @param thdProducts
	 */
	public void saveThd(Thd thd,List thdProducts){
		
		//����˻����Ѿ��ύ�����Ѿ���⣬�����κδ���
		if(thdDao.isThdSubmit(thd.getThd_id())){
			return;
		}
		
		thdDao.saveThd(thd, thdProducts);		
		
		if(!thd.getState().equals("�ѱ���")){
			this.saveRkd(thd, thdProducts); //������Ӧ��ⵥ
			
			this.updateThdJe(thd);			//�����˻�������������
			
			this.addProductSaleFlow(thd, thdProducts);  //�����˻�����Ʒ�������
		}
	}
	
	
	
	/**
	 * �����˻�����Ϣ
	 * @param thd
	 * @param thdProducts
	 */
	public void updateThd(Thd thd,List thdProducts){
		
		//����˻����Ѿ��ύ�����Ѿ���⣬�����κδ���
		if(thdDao.isThdSubmit(thd.getThd_id())){
			return;
		}
		
		thdDao.updateThd(thd, thdProducts);
		
		// �˻���Ϊ���ַ�ʽ���ֽ𡢳��������
		if(!thd.getState().equals("�ѱ���")){
			
			this.saveRkd(thd, thdProducts); //������Ӧ��ⵥ	
			
			this.updateThdJe(thd);          //�����˻�������������
			
			this.addProductSaleFlow(thd, thdProducts);  //�����˻�����Ʒ�������
		}
		
	}
	
	
	/**
	 * �����˻���ID������Ʒ��ϸ
	 * @param thd_id
	 * @return
	 */
	public List getThdProducts(String thd_id){
		return thdDao.getThdProducts(thd_id);
	}
	
	
	/**
	 * �����˻���ID�����˻��������Ϣ
	 */
	public Object getThd(String thd_id){
		return thdDao.getThd(thd_id);
	}

	/**
	 * ɾ���˻��������Ϣ
	 * @param thd_id
	 */
	public void delThd(String thd_id){
		
		//����˻����Ѿ��ύ�����Ѿ���⣬�����κδ���
		if(thdDao.isThdSubmit(thd_id)){
			return;
		}
		
		thdDao.delThd(thd_id);
	}
	
	
	/**
	 * ȡ��ǰ���õ��˻�����
	 * @return
	 */
	public String updateThdId(){
		return thdDao.getThdID();
	}
	
	
	
	/**
	 * ���������ⵥ������
	 * @param jhd
	 * @param JhdProducts
	 */
	private void saveRkd(Thd thd,List thdProducts){
		Rkd rkd = new Rkd();
		List rkdProducts = new ArrayList();
		
		String rkd_id = rkdDao.getRkdID();//��ǰ������ⵥ��
		
		rkd.setRkd_id(rkd_id); 
		rkd.setJhd_id(thd.getThd_id());//���˻��������Ϊ��������
		rkd.setCreatdate(thd.getTh_date());
		rkd.setState("�����");		
		rkd.setMs("�˻���⣬�˻����ڣ�" + thd.getTh_date() + ",�˻������ [" + thd.getThd_id() + "]");
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
						
						//���������Ʒ�ļ۸�
						//һ����������˻���ԭ������ʱ���˻��ɱ�ȡԭ���۳���ʱ�ɱ�
						//����ȡ�����˻�ǰ�Ľ���ƽ���ɱ���
						//����������Ϊ0����ȡ��ǰ���һ�����ɱ�
						//�ġ���Ϊ0����ȡ�˻ؼ�
						
						double price = thdProduct.getCbj(); //ȡ�˻��ɱ��ۣ�����ǹ������۵������۵���ȡ��ʱ����ʱ�ĳɱ��ۣ������������ȡ�˻�ʱ���ɱ���
						
						//����۸�Ϊ0����ȡ���һ�����ʱ�ļ۸�
						if(price == 0){
							price = productKcDao.getLastProductRkCbj(thdProduct.getProduct_id());
						}
						
						//�����Ϊ0����ȡ�˻ؼ�
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
		
		//���¿�������ɱ���
		productKcDao.updateProductKc(rkd, rkdProducts);			
		//�������к�
		this.updateSerialNum(rkd, rkdProducts);		
	}
	
	
	/**
	 * �����˻���������Ʒ������ˮ
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
					info.setYw_type("�˻���");
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
	 * �˵�������
	 * ֻ���������˻����ʱ����������տ���Ϣ
	 * @param thd
	 */
	private void updateThdJe(Thd thd){
		//���۶���
		if(thd.getYw_type().equals("1"))
		{
		  if(thd.getType().equals("�ֽ�")){  //�ֽ��˻�
			
			//�����Ӧ�����տ���Ϣ����ֵ
			this.saveXssk(thd);
			
			//������Ӧ�˺����
			this.updateAccountJe(thd);
			
		  }else{ //��������˻�
			
			//Ӧ��תԤ��
			this.addYushouk(thd);
		  }
		}
		//���۵�
		if(thd.getYw_type().equals("2"))
		{
		  if(thd.getTypeLs().equals("�ֽ�")){  //�ֽ��˻�
			
			//�����Ӧ�����տ���Ϣ����ֵ
			this.saveXssk(thd);
			
			//������Ӧ�˺����
			this.updateAccountJe(thd);
			
		  }else{ //��������˻�
			
			//Ӧ��תԤ��
			this.addYushouk(thd);
		  }
		}
	}
	
	
	/**
	 * ���������տ���Ϣ
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
		xssk.setState("���ύ");
		xssk.setCzr(thd.getCzr());
		xssk.setRemark(thd.getTh_date() + "�˻������˻������ [" + thd.getThd_id() + "]");
		xssk.setDelete_key(thd.getThd_id());
		
		List xsskDescs = new ArrayList();
		
		XsskDesc xsskDesc = new XsskDesc();
		xsskDesc.setXsd_id(thd.getThd_id());
		xsskDesc.setXssk_id(xssk_id);
		xsskDesc.setBcsk(thd.getThdje());
		xsskDesc.setRemark( "�˻������˻������ [" + thd.getThd_id() + "]");
		
		xsskDescs.add(xsskDesc);
		
		xsskDao.saveXssk(xssk, xsskDescs);
		
		this.saveAccountDzd(xssk.getId(),xssk.getSkzh(), xssk.getSkje(), xssk.getCzr(), xssk.getJsr(), "�����տ���[" + xssk.getId() + "]");
		
	}
	
	
	
	/**
	 * �����˻���Ϣ
	 * @param lsd
	 */
	private void updateAccountJe(Thd thd){
		String account_id = thd.getTkzh();
		double je = thd.getThdje();
		
		
		accountsDao.updateAccountJe(account_id, je);
	}
	
	
	
	/**
	 * ����ʽ��׼�¼
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
	 * ���Ԥ�տ�
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
		ysk.setYwdj_name("Ӧ��תԤ��");
		ysk.setCzr(thd.getCzr());
		ysk.setRemark("��������˻���Ӧ��תԤ�գ��˻������[" + thd.getThd_id() + "]");
		
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
