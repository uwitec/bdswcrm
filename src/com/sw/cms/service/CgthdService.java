package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.CgthdDAO;
import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.JhdDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.CgfkDesc;
import com.sw.cms.model.Cgthd;
import com.sw.cms.model.CgthdProduct;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.Yufuk;
import com.sw.cms.util.StaticParamDo;

/**
 * <p>�ɹ��˻�������</p>
 * @author liyt
 *
 */
public class CgthdService {
	
	private CgthdDAO cgthdDao;
	private JhdDAO jhdDao;
	private CgfkDAO cgfkDao;
	private CkdDAO ckdDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private SerialNumDAO serialNumDao;
	private SysInitSetDAO sysInitSetDao;
	private ProductKcDAO productKcDao;
	private YufukDAO yufukDao;
	
	/**
	 * ȡ�ɹ��˻����б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgthdList(String con,int curPage, int rowsPerPage){
		return cgthdDao.getCgthdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ����ɹ��˻��������Ϣ
	 * @param cgthd
	 * @param cgthdProducts
	 */
	public void saveCgthd(Cgthd cgthd,List cgthdProducts){
		cgthdDao.saveCgthd(cgthd, cgthdProducts);
		
		if(!cgthd.getState().equals("�ѱ���")){
			this.saveCkd(cgthd, cgthdProducts); //���ɲ�������Ӧ���ⵥ
			this.doCgtk(cgthd);
		}
	}
	
	
	/**
	 * ���²ɹ��˻��������Ϣ
	 * @param cgthd
	 * @param cgthdProducts
	 */
	public void updateCgthd(Cgthd cgthd,List cgthdProducts){
		
		//����ɹ��˻������ύ���ѳ��⣬�����κβ���
		if(cgthdDao.isCgthdSubmit(cgthd.getId())){
			return;
		}
		
		cgthdDao.updateCgthd(cgthd, cgthdProducts);
		if(!cgthd.getState().equals("�ѱ���")){
			this.saveCkd(cgthd, cgthdProducts); //���ɲ�������Ӧ���ⵥ
			this.doCgtk(cgthd);
		}
	}
	
	
	/**
	 * ȡ�ɹ��˻���
	 * @param id
	 * @return
	 */
	public Object getCgthd(String id){
		return cgthdDao.getCgthd(id);
	}
	
	
	/**
	 * ȡ�ɹ��˻�����Ʒ��ϸ
	 * @param id
	 * @return
	 */
	public List getCgthdProducts(String id){
		return cgthdDao.getCgthdProducts(id);
	}
	
	
	/**
	 * ɾ���ɹ��˻���
	 * @param id
	 */
	public void delCgthd(String id){
		
		//����ɹ��˻������ύ���ѳ��⣬�����κβ���
		if(cgthdDao.isCgthdSubmit(id)){
			return;
		}
		
		cgthdDao.delCgthd(id);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateCgthdID() {
		return cgthdDao.getCgthdID();
	}

	
	
	/**
	 * ����ɹ�������Ϣ
	 * @param cgthd
	 */
	private void saveCgfk(Cgthd cgthd){
		Cgfk cgfk = new Cgfk();
		
		String cgfk_id = cgfkDao.getCgfkID();
		
		cgfk.setId(cgfk_id);
		cgfk.setFk_date(cgthd.getTh_date());
		cgfk.setJsr(cgthd.getJsr());
		cgfk.setFkje(0 - cgthd.getTkzje());
		cgfk.setFkzh(cgthd.getZhmc());
		cgfk.setGysbh(cgthd.getProvider_name());
		cgfk.setState("���ύ");
		cgfk.setRemark("�ɹ��˻���,�˻������[" + cgthd.getId() + "]");
		cgfk.setCzr(cgthd.getCzr());
		cgfk.setDelete_key(cgthd.getId());
		
		List cgfkDescs = new ArrayList();
		
		CgfkDesc cgfkDesc = new CgfkDesc();
		cgfkDesc.setCgfk_id(cgfk_id);
		cgfkDesc.setJhd_id(cgthd.getId()); //����������д�ţ��ɹ��˻������
		cgfkDesc.setBcfk(cgthd.getTkzje());
		cgfkDesc.setRemark("�ɹ��˻���,�˻������[" + cgthd.getId() + "]");
		
		cgfkDescs.add(cgfkDesc);
		
		cgfkDao.saveCgfk(cgfk, cgfkDescs);
		this.saveAccountDzd(cgfk.getFkzh(), 0-cgfk.getFkje(), cgfk.getCzr(), cgfk.getJsr(), "�ɹ��˻�������[" + cgfk.getId() + "]",cgfk.getId());
	}
	
	
	/**
	 * ����ʽ��׼�¼
	 * @param cgfk
	 */
	private void saveAccountDzd(String account_id,double jyje,String czr,String jsr,String remark,String cgfk_id){
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
		accountDzd.setAction_url("viewCgfk.html?id=" + cgfk_id);
		
		accountDzdDao.addDzd(accountDzd);
	}
	
	
	/**
	 * ���ݲɹ��˻���������Ӧ���ⵥ
	 * @param cgthd
	 * @param cgthdProducts
	 */
	private void saveCkd(Cgthd cgthd,List cgthdProducts){
		
		Ckd ckd = new Ckd();
		List ckdProducts = new ArrayList();
		String ckd_id = ckdDao.getCkdID();
		
		ckd.setCkd_id(ckd_id);
		ckd.setClient_name(cgthd.getProvider_name());
		ckd.setCreatdate(cgthd.getTh_date());
		ckd.setCzr(cgthd.getCzr());
		ckd.setMs("�ɹ��˻����⣬�ɹ��˻������[" + cgthd.getId() + "]");
		if(cgthd.getState().equals("�ѳ���")){
			ckd.setState("�ѳ���");
		}else{
			ckd.setState("�ѱ���");
		}
		
		ckd.setXsd_id(cgthd.getId());
		ckd.setXsry(cgthd.getJsr());
		ckd.setCk_date(cgthd.getTh_date());
		ckd.setFzr(cgthd.getJsr());
		ckd.setStore_id(cgthd.getStore_id());
		
		if(cgthdProducts != null && cgthdProducts.size()>0){
			for(int i=0;i<cgthdProducts.size();i++){
				
				CgthdProduct cgthdProduct = (CgthdProduct)cgthdProducts.get(i);
				if(cgthdProduct != null){
					if(cgthdProduct.getProduct_name() != null && !cgthdProduct.getProduct_name().equals("")){
						CkdProduct ckdProduct = new CkdProduct();
						
						ckdProduct.setCkd_id(ckd_id);
						ckdProduct.setProduct_id(cgthdProduct.getProduct_id());
						ckdProduct.setProduct_name(cgthdProduct.getProduct_name());
						ckdProduct.setProduct_xh(cgthdProduct.getProduct_xh());
						ckdProduct.setNums(cgthdProduct.getNums());
						ckdProduct.setPrice(cgthdProduct.getTh_price());
						ckdProduct.setJgtz(0);
						ckdProduct.setCbj(0);
						ckdProduct.setQz_serial_num(cgthdProduct.getQz_serial_num());
						
						ckdProducts.add(ckdProduct);
					}
				}
			}
		}
		
		ckdDao.saveCkd(ckd, ckdProducts);
		
		if(ckd.getState().equals("�ѳ���")){
			this.updateProductKc(ckd, ckdProducts); //�޸Ŀ��
			
			//ֻ����ϵͳ��ʽʹ�ú��ȥ�������к�
			//if(sysInitSetDao.getQyFlag().equals("1")){			
			this.updateSerialNum(ckd, ckdProducts); //�������к�
			//}			
		}
		
	}
	
	
	/**
	 * <p>����ɹ��˻�����ز���</p>
	 * <p>��Ϊ���������1���ֽ�2�����������</p>
	 * @param cgthd
	 */
	private void doCgtk(Cgthd cgthd){
		
		if(cgthd.getTkfs().equals("�ֽ�")){  //������ֽ��˻�1:������Ӧ�ɹ�������Ϣ��2:������Ӧ�˺Ž��
			
			//������Ӧ�ɹ�������Ϣ
			this.saveCgfk(cgthd);
			
			//������Ӧ�˺Ž��
			accountsDao.addAccountJe(cgthd.getZhmc(), cgthd.getTkzje());
			
		}else{  
			
			//����ǳ���������ս�Ӧ����תΪԤ����
			this.addYufuk(cgthd);
		}
	}
	
	
	/**
	 * <p>�������к�</p>
	 * <p>�����������</p>
	 * <p>һ�����۳��⣻�����ɹ��˻�����</p>
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
							state = "����";
							yw_url = "viewXsd.html?id=";
							yw_type = "����";
						}else{
							state = "���˻�";
							yw_url = "viewCgthd.html?id=";
							yw_type = "�ɹ��˻�";
						}
						
						for(int k=0;k<arryNums.length;k++){
							serialNumMng.setProduct_id(ckdProduct.getProduct_id());
							serialNumMng.setProduct_name(ckdProduct.getProduct_name());
							serialNumMng.setProduct_xh(ckdProduct.getProduct_xh());
							serialNumMng.setSerial_num(arryNums[k]);
							serialNumMng.setState(state);
							serialNumMng.setStore_id("");
							serialNumMng.setYj_flag("0");
							serialNumDao.updateSerialNumState(serialNumMng); //�������к�״̬
							
							serialNumFlow.setClient_name(StaticParamDo.getClientNameById(ckd.getClient_name()));
							serialNumFlow.setCzr(ckd.getCzr());
							
							serialNumFlow.setYwtype(yw_type);
							
							serialNumFlow.setFs_date(ckd.getCk_date());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(ckd.getXsry()));
							serialNumFlow.setKf_dj_id(ckd.getCkd_id());
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewCkd.html?ckd_id=");
							serialNumFlow.setYw_dj_id(ckd.getXsd_id());							
							serialNumFlow.setYw_url(yw_url);
							
							serialNumDao.saveSerialFlow(serialNumFlow);  //�������к���ת����
						}
					}
				}
			}
		}
	}	
	
	
	/**
	 * ���²�Ʒ���
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
	 * ���Ԥ����
	 * @param yfk
	 */
	private void addYufuk(Cgthd cgthd){
		Yufuk yfk = new Yufuk();
		
		yfk.setClient_name(cgthd.getProvider_name());
		yfk.setHjje(cgthd.getTkzje());
		yfk.setJsje(0);
		yfk.setJs_date(cgthd.getTh_date());
		yfk.setYwdj_id(cgthd.getId());
		yfk.setYwdj_name("Ӧ��תԤ��");
		yfk.setCzr(cgthd.getCzr());
		yfk.setRemark("��������˻���Ӧ��תԤ�����ɹ��˻������[" + cgthd.getId() + "]");
		yfk.setUrl("viewCgthd.html?id=");
		
		yufukDao.saveYufuk(yfk);
	}
	

	public CgthdDAO getCgthdDao() {
		return cgthdDao;
	}


	public void setCgthdDao(CgthdDAO cgthdDao) {
		this.cgthdDao = cgthdDao;
	}


	public CgfkDAO getCgfkDao() {
		return cgfkDao;
	}


	public void setCgfkDao(CgfkDAO cgfkDao) {
		this.cgfkDao = cgfkDao;
	}


	public CkdDAO getCkdDao() {
		return ckdDao;
	}


	public void setCkdDao(CkdDAO ckdDao) {
		this.ckdDao = ckdDao;
	}


	public JhdDAO getJhdDao() {
		return jhdDao;
	}


	public void setJhdDao(JhdDAO jhdDao) {
		this.jhdDao = jhdDao;
	}


	public AccountsDAO getAccountsDao() {
		return accountsDao;
	}


	public void setAccountsDao(AccountsDAO accountsDao) {
		this.accountsDao = accountsDao;
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


	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}


	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}


	public YufukDAO getYufukDao() {
		return yufukDao;
	}


	public void setYufukDao(YufukDAO yufukDao) {
		this.yufukDao = yufukDao;
	}

}
