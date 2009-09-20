package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.CgthdDAO;
import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.XsdDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.XsdProduct;
import com.sw.cms.model.Xssk;
import com.sw.cms.util.StaticParamDo;

/**
 * ���ⵥ����
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
	

	
	/**
	 * ȡ�б�������ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCkdList(String con,int curPage, int rowsPerPage){
		return ckdDao.getCkdList(con, curPage, rowsPerPage);
	}
	
	
	
	/**
	 * <p>������ⵥ��Ϣ</p>
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
		
		if(ckd.getState().equals("�ѳ���")){
			
			this.updateProductKc(ckd, ckdProducts); //�޸Ŀ��
			
			//ֻ����ϵͳ��ʽʹ�ú��ȥ�������к�
			//ϵͳ��ʽ����ǰ������ǿ�����к������ƣ�����������к�ϵͳͬ�����账��
			//if(sysInitSetDao.getQyFlag().equals("1")){				
			this.updateSerialNum(ckd, ckdProducts); //�������к�
			//}
			
			this.updateXsdState(ckd.getXsd_id(), "�ѳ���",ckd.getYsfs(),ckd.getCx_tel(),ckd.getJob_no(),ckd.getSend_time(),ckd.getStore_id(),ckd.getFzr(),ckd.getCk_date());
		}
	}
		
	
	
	/**
	 * ���³��ⵥ��Ϣ
	 * @param ckd
	 * @param ckdProducts
	 */
	public void updateCkd(Ckd ckd,List ckdProducts){
		
		//���ó��ⵥ��Ӧ��Ʒ�ĳɱ���
		List xsdProducts = new ArrayList();
		
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct cdkProduct = (CkdProduct)ckdProducts.get(i);
				if(cdkProduct != null){
					if(!cdkProduct.getProduct_id().equals("")){
						XsdProduct xsdProduct = (XsdProduct)xsdDao.getXsdProductInfo(ckd.getXsd_id(), cdkProduct.getProduct_id());
						Product product = (Product)productDao.getProductById(cdkProduct.getProduct_id());
						
						xsdProduct.setCbj(product.getPrice());
						xsdProduct.setKh_cbj(product.getKhcbj());
						xsdProducts.add(xsdProduct);
						
						cdkProduct.setCbj(product.getPrice());  //�ɱ���ȡ��ǰ���ĳɱ���
						if(xsdProduct != null){
							cdkProduct.setPrice(xsdProduct.getPrice());  //���ۼ۸�ȡ���۶����൱�۸�
							cdkProduct.setJgtz(xsdProduct.getJgtz());
						}else{
							cdkProduct.setPrice(0);
							cdkProduct.setJgtz(0);
						}
					}
				}
			}
		}
		
		//���³��ⵥ
		ckdDao.updateCkd(ckd, ckdProducts);
		
		if(ckd.getState().equals("�ѳ���")){
			
			this.updateProductKc(ckd, ckdProducts); //�޸Ŀ��
			
			//�޸���Ӧ���۵�ʵ�ʳɽ���Ϣ�����������Ϣ
			this.updateXsdSjcjInfo(ckd, ckdProducts);
			

			//ϵͳ��ʽ����ǰ������ǿ�����к������ƣ�����������к�ϵͳͬ�����账��			
			this.updateSerialNum(ckd, ckdProducts);   //�������к�
			
			//��������۵����������۵�״̬
			if(ckd.getXsd_id().indexOf("XS") != -1){
				this.updateXsdState(ckd.getXsd_id(), "�ѳ���",ckd.getYsfs(),ckd.getCx_tel(),ckd.getJob_no(),ckd.getSend_time(),ckd.getStore_id(),ckd.getFzr(),ckd.getCk_date());
				
				//�޸����۵���Ӧ�ĳɱ��ۼ����˳ɱ���
				if(xsdProducts != null && xsdProducts.size() > 0){
					xsdDao.updateXsdProducts(xsdProducts);
				}
			}
			
			//������Ӧ��������������״̬
			this.updateFxddState(ckd.getXsd_id(), "�ѳ���",ckd.getYsfs(),ckd.getCx_tel(),ckd.getJob_no(),ckd.getSend_time());
			
			//����ɹ��˻���,�޸Ĳɹ��˻���״̬���˻ر��
			if(ckd.getXsd_id().indexOf("CGTH") != -1){
				cgthdDao.updateCgthdState(ckd.getXsd_id(), "�ѳ���");
			}
			

		}
	}

	
	
	/**
	 * ���ݳ��ⵥID��ȡ��Ʒ�б�
	 * @param ckd_id
	 * @return
	 */
	public List getCkdProducts(String ckd_id){
		return ckdDao.getCkdProducts(ckd_id);
	}
	
	
	/**
	 * ���ݳ��ⵥID��ȡ���ⵥ��Ϣ
	 * @param ckd_id
	 * @return
	 */
	public Object getCkd(String ckd_id){
		return ckdDao.getCkd(ckd_id);
	}
	
	
	/**
	 * ɾ�����ⵥ��Ϣ
	 * @param ckd_id
	 */
	public void delCkd(String ckd_id){
		ckdDao.delCkd(ckd_id);
	}
	
	
	/**
	 * ���ⵥ�˻ض�������
	 * ���������һ�����۶����������ɹ��˻���
	 * ����������ֱ���д���
	 * @param ckd
	 */
	public void doTh(Ckd ckd){
		
		//ɾ������Ӧ���ⵥ
		ckdDao.delCkd(ckd.getCkd_id());
		
		String xsd_id = ckd.getXsd_id();
		
		//��������۵���
		if(xsd_id.indexOf("XS") != -1){
			
			//�޸����۵�״̬���˻ر��
			xsdDao.updateXsdAfterCkdTh(xsd_id, "�ѱ���", "1");
			
			//�鿴���۶����ύʱ���Ƿ���ֱ���տ�
			//��������ύʱ��ֱ���տ��Ҫɾ���տ���Ϣ��Ϣ
			Xssk xssk = xsskDao.getXsskByDeleteKey(xsd_id);	
			
			if(xssk != null){
				
				//ɾ�������տ���Ϣ
				xsskDao.delXssk(xssk.getId());
				
				//ɾ���˻���ˮ��Ϣ
				accountDzdDao.delDzd(xssk.getId());
				
				//���˻����
				accountsDao.updateAccountJe(xssk.getSkzh(), xssk.getSkje());
			}
		}
		
		//����ɹ��˻���,�޸Ĳɹ��˻���״̬���˻ر��
		if(xsd_id.indexOf("CGTH") != -1){
			cgthdDao.updateCgthdAfterCkdTh(xsd_id, "�ѱ���", "1");
			
			//�鿴�ɹ��˻����ύʱ���Ƿ��ֽ��˻�
			//����ɹ��˻������ֽ��˻�����Ҫɾ����ظ�����Ϣ
			Cgfk cgfk = cgfkDao.getCgfkByDeleteKey(xsd_id);
			
			if(cgfk != null){
				
				//ɾ���ɹ�������Ϣ
				cgfkDao.delCgfk(cgfk.getId());
				
				//ɾ���˻���ˮ��Ϣ
				accountDzdDao.delDzd(cgfk.getId());
				
				//���˻����(��ֵ)
				accountsDao.addAccountJe(cgfk.getFkzh(), cgfk.getFkje());
			}
			
			//�����Ӧ��תԤ����Ϣ�����
			yufukDao.delYufuk(xsd_id);
		}
	}
	
	
	/**
	 * ���ݶ�Ӧ���۵���Ų鿴�Ƿ������Ӧ�ĳ��ⵥ
	 * @param xsd_id
	 * @return
	 */
	public boolean isCkdExist(String xsd_id){
		return ckdDao.isCkdExist(xsd_id);
	}
	
	
	/**
	 * �鿴���ⵥ�Ƿ��Ѿ��ύ
	 * @param ckd_id
	 * @return
	 */
	public boolean isCkdSubmit(String ckd_id){
		return ckdDao.isCkdSubmit(ckd_id);
	}
	
	/**
	 * ȡ���пⷿ�б�
	 * @return
	 */
	public List getStoreList(){
		return storeDao.getAllStoreList();
	}
	
	
	/**
	 * ȡ���õĳ��ⵥ��
	 * @return
	 */
	public String updateCkdId(){
		return ckdDao.getCkdID();
	}
	
	
	/**
	 * �жϿ�����Ƿ����������Ҫ
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
						
						//�ж���Ʒ�Ƿ��ǿ����Ʒ,ֻ�Կ����Ʒ�Ž��п�������ж�
						Product product = (Product)productDao.getProductById(product_id);
						if(product.getProp().equals("�����Ʒ")){	
							int cknums = ckdProduct.getNums();  //Ҫ��������
							int kcnums = productKcDao.getKcNums(product_id, store_id);//�������
							
							if(cknums>kcnums){
								msg += ckdProduct.getProduct_name() + " ��ǰ���Ϊ��" + kcnums + "  �޷����⣬���ȵ���\n";
							}
						}
					}
				}
			}
		}
		
		return msg;
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
	 * �޸���Ӧ���۵�ʵ�ʳɽ��������
	 * @param ckd
	 * @param ckdProducts
	 */
	private void updateXsdSjcjInfo(Ckd ckd,List ckdProducts){
		double sjcjje = 0; //���۵�ʵ�ʳɽ����
		String xsd_id = ckd.getXsd_id();
		
		//������ҵ�񵥾ݲ������۵�ʱ�˳�
		if(xsd_id.indexOf("XS") == -1){
			return;
		}
		
		if(ckdProducts != null && ckdProducts.size()>0){
			for(int i=0;i<ckdProducts.size();i++){
				CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
				if(ckdProduct != null){
					if(!ckdProduct.getProduct_id().equals("") && !ckdProduct.getProduct_name().equals("")){
						double sjcj_xj = ckdProduct.getNums() * (ckdProduct.getPrice() + ckdProduct.getJgtz());
						sjcjje += sjcj_xj;
						xsdDao.updateXsdSjcjNums(xsd_id, ckdProduct.getProduct_id(), ckdProduct.getNums(), sjcj_xj);
					}
				}
			}
		}
		xsdDao.updateXsdSjcjje(xsd_id, sjcjje);
	}
	
	
	/**
	 * ������Ӧ���۵�״̬
	 * @param xsd_id     ���۵����
	 * @param state      ״̬
	 * @param ysfs       ���䷽ʽ
	 * @param cx_tel     ��ѯ�绰
	 * @param job_no     ������
	 * @param store_id   �����ⷿ
	 * @param ck_jsr     ���⾭����
	 * @param send_time  ����ʱ��
	 */
	private void updateXsdState(String xsd_id,String state,String ysfs,String cx_tel,String job_no,String send_time,String store_id,String ck_jsr,String ck_date){
		xsdDao.updateXsdState(xsd_id, state,ysfs,cx_tel,job_no,send_time, store_id, ck_jsr,ck_date);
	}
	
	
	/**
	 * ������Ӧ��������״̬
	 * @param fxdd_id
	 * @param state
	 */
	private void updateFxddState(String fxdd_id,String state,String ysfs,String cx_tel,String job_no,String send_time){
		xsdDao.updateFxddState(fxdd_id, state,ysfs,cx_tel,job_no,send_time);
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
							
							serialNumDao.saveSerialFlow(serialNumFlow);  //�������к���ת����
						}
					}
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

}
