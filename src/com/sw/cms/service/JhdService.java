package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.JhdDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.RkdDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.CgfkDesc;
import com.sw.cms.model.Jhd;
import com.sw.cms.model.JhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Rkd;
import com.sw.cms.model.RkdProduct;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;

public class JhdService {
	private JhdDAO jhdDao;
	private RkdDAO rkdDao;
	private CgfkDAO cgfkDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private SerialNumDAO serialNumDao;
	private ProductKcDAO productKcDao;
	
	/**
	 * ȡ�������б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getJhdList(String con,int curPage, int rowsPerPage){
		return jhdDao.getJhdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���ݽ�����IDȡ��������ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Object getJhd(String id){
		return jhdDao.getJhd(id);
	}
	
	
	/**
	 * �����������Ϣ
	 * @param jhd
	 * @return
	 */
	public void saveJhd(Jhd jhd,List jhdProducts){
		
		//����������(�Ѹ��������Ѹ���δ��)
		double jhdje = jhd.getTotal(); //�������ϼƽ��
		double fkje = jhd.getFkje();   //������
		
		if(jhdje <= 0){
			jhd.setFklx("�Ѹ�");
		}else{
			if(fkje > 0 && fkje < jhdje){
				jhd.setFklx("�����Ѹ�");
			}else if(fkje >= jhdje){
				jhd.setFklx("�Ѹ�");
			}else{
				jhd.setFklx("δ��");
			}
		}
		
		//����Ӧ������
		if(jhd.getFkfs().equals("�ֽ�")){
			jhd.setYfrq(jhd.getCg_date());
		}else{
			jhd.setYfrq(DateComFunc.formatDate(DateComFunc.addDay((DateComFunc.strToDate(jhd.getCg_date(), "yyyy-MM-dd")),jhd.getZq()),"yyyy-MM-dd"));
		}
		
		//����ɹ������������Ϣ
		jhdDao.saveJhd(jhd, jhdProducts);
		
		if(!jhd.getState().equals("�ѱ���")){ //���������״̬Ϊ������⡱�����ύ��
			
			//������ⵥ,�ı���,�������к�	
			saveRkd(jhd,jhdProducts); 
			
			//������������0������ɹ�������Ϣ,�����˻��������ʽ���ϸ
			if(jhd.getFkje() > 0 && !jhd.getFkzh().equals("")){			
				
				//����ɹ�������Ϣ�������ʽ���ת��ϸ
				saveCgfk(jhd);
				
				//�����˻����
				updateAccount(jhd);
			}
		}
		
	}
	
	
	
	/**
	 * �޸Ľ�������Ϣ
	 * @param jhd
	 * @return
	 */
	public void updateJhd(Jhd jhd,List jhdProducts){
		
		//����������(�Ѹ��������Ѹ���δ��)
		double jhdje = jhd.getTotal(); //�������ϼƽ��
		double fkje = jhd.getFkje();   //������
		
		if(jhdje <= 0){
			//�ɹ��������С�ڵ���0ʱ�����ø�����ʽΪ�Ѹ�
			jhd.setFklx("�Ѹ�");
		}else{
			if(fkje > 0 && fkje < jhdje){
				jhd.setFklx("�����Ѹ�");
			}else if(fkje >= jhdje){
				jhd.setFklx("�Ѹ�");
			}else{
				jhd.setFklx("δ��");
			}
		}
		
		//����Ӧ������
		if(jhd.getFkfs().equals("�ֽ�")){
			jhd.setYfrq(jhd.getCg_date());
		}else{
			jhd.setYfrq(DateComFunc.formatDate(DateComFunc.addDay((DateComFunc.strToDate(jhd.getCg_date(), "yyyy-MM-dd")),jhd.getZq()),"yyyy-MM-dd"));
		}
		
		//���½�������Ϣ
		jhdDao.updateJhd(jhd, jhdProducts);
		
		if(!jhd.getState().equals("�ѱ���")){ //���������״̬Ϊ������⡱�����ύ��
			
			//������ⵥ,�ı���	
			saveRkd(jhd,jhdProducts); 
			
			//������������0������ɹ�������Ϣ,�����˻��������ʽ���ϸ
			if(jhd.getFkje() > 0 && !jhd.getFkzh().equals("")){			
				//����ɹ�������Ϣ�������ʽ���ת��ϸ
				saveCgfk(jhd);
				//�����˻����
				updateAccount(jhd);
			}
		}
		
	}
	
	
	/**
	 * ���ݽ�������ȡ����������Ʒ
	 * @param jhd_id
	 * @return
	 */
	public List getJhdProducts(String jhd_id){
		return jhdDao.getJhdProducts(jhd_id);
	}	
	
	
	/**
	 * ɾ����������Ϣ
	 * @param id
	 * @return
	 */
	public void delJhd(String id){
		jhdDao.delJhd(id);
	}
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateJhdID() {
		return jhdDao.getJhdID();
	}
	
	
	/**
	 * ��ӽ�������زɹ�������Ϣ
	 * @param jhd
	 */
	private void saveCgfk(Jhd jhd){
		Cgfk cgfk = new Cgfk();
		
		String cgfk_id = cgfkDao.getCgfkID();
		
		cgfk.setId(cgfk_id);
		cgfk.setFk_date(jhd.getCg_date());
		cgfk.setGysbh(jhd.getGysbh());
		cgfk.setState("���ύ");
		cgfk.setFkje(jhd.getFkje());
		cgfk.setFkzh(jhd.getFkzh());
		cgfk.setJsr(jhd.getFzr());
		cgfk.setCzr(jhd.getCzr());
		cgfk.setRemark("����֧������������ [" + jhd.getId() + "]");
		cgfk.setDelete_key(jhd.getId());
		
		List cgfkDescs = new ArrayList();
		CgfkDesc cgfkDesc = new CgfkDesc();
		
		cgfkDesc.setJhd_id(jhd.getId());
		cgfkDesc.setBcfk(jhd.getFkje());
		cgfkDesc.setCgfk_id(cgfk_id);
		
		cgfkDescs.add(cgfkDesc);		
		cgfkDao.saveCgfk(cgfk, cgfkDescs);
		
		
		//����ʽ���ת��ϸ
		AccountDzd accountDzd = new AccountDzd();		
		double zhye = 0;
		Map map = accountsDao.getAccounts(cgfk.getFkzh());
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}
		
		accountDzd.setAccount_id(cgfk.getFkzh());
		accountDzd.setJyje(0-cgfk.getFkje());
		accountDzd.setZhye(zhye - cgfk.getFkje());
		accountDzd.setRemark("�ɹ�������[" + cgfk.getId() + "]");
		accountDzd.setCzr(cgfk.getCzr());
		accountDzd.setJsr(cgfk.getJsr());
		accountDzd.setAction_url("viewCgfk.html?id=" + cgfk.getId());
		accountDzdDao.addDzd(accountDzd);

	}
	
	
	/**
	 * ���������ⵥ������
	 * @param jhd
	 * @param JhdProducts
	 */
	private void saveRkd(Jhd jhd,List jhdProducts){
		Rkd rkd = new Rkd();
		List rkdProducts = new ArrayList();
		
		String rkd_id = rkdDao.getRkdID();//��ǰ������ⵥ��
		
		rkd.setRkd_id(rkd_id); 
		rkd.setJhd_id(jhd.getId());
		rkd.setCreatdate(jhd.getCg_date());
		if(jhd.getState().equals("�����")){
			rkd.setState("�����");
			rkd.setFzr(jhd.getFzr());
			rkd.setRk_date(jhd.getCg_date());
		}else{
			rkd.setState("�ѱ���");
		}
		
		rkd.setMs(jhd.getCg_date() + "�������� [" + jhd.getId() + "]");
		rkd.setCgfzr(jhd.getFzr());
		rkd.setCzr(jhd.getCzr());
		rkd.setClient_name(jhd.getGysbh());
		rkd.setStore_id(jhd.getStore_id());
		
		
		
		if(jhdProducts != null && jhdProducts.size()>0){
			for(int i=0;i<jhdProducts.size();i++){
				
				JhdProduct jhdProduct = (JhdProduct)jhdProducts.get(i);
				if(jhdProduct != null){
					if(jhdProduct.getProduct_name() != null && !jhdProduct.getProduct_name().equals("")){
						RkdProduct rkdProduct = new RkdProduct();
						
						rkdProduct.setProduct_id(jhdProduct.getProduct_id());
						rkdProduct.setProduct_name(jhdProduct.getProduct_name());
						rkdProduct.setProduct_xh(jhdProduct.getProduct_xh());
						rkdProduct.setPrice(jhdProduct.getPrice());
						rkdProduct.setNums(jhdProduct.getNums());
						rkdProduct.setRkd_id(rkd_id);
						rkdProduct.setRemark(jhdProduct.getRemark());
						rkdProduct.setQz_serial_num(jhdProduct.getQz_serial_num());
						
						rkdProducts.add(rkdProduct);
					}
				}
			}
		}
		
		//������ⵥ
		rkdDao.saveRkd(rkd, rkdProducts);
		
		if(rkd.getState().equals("�����")){
			//���¿�������ɱ���
			productKcDao.updateProductKc(rkd, rkdProducts);		
			//�������к�
			this.updateSerialNum(rkd, rkdProducts);
		}
		
	}
	
	
	private void updateAccount(Jhd jhd){
		accountsDao.updateAccountJe(jhd.getFkzh(), jhd.getFkje());
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
	
	
	public void setJhdDao(JhdDAO jhdDao) {
		this.jhdDao = jhdDao;
	}
	public JhdDAO getJhdDao() {
		return jhdDao;
	}
	public void setRkdDao(RkdDAO rkdDao) {
		this.rkdDao = rkdDao;
	}


	public RkdDAO getRkdDao() {
		return rkdDao;
	}


	public CgfkDAO getCgfkDao() {
		return cgfkDao;
	}


	public void setCgfkDao(CgfkDAO cgfkDao) {
		this.cgfkDao = cgfkDao;
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


	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}


	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}


}
