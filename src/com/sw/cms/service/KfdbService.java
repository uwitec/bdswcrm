package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.KfdbDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.KfdbProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.util.StaticParamDo;

public class KfdbService {
	
	private KfdbDAO kfdbDao;
	private ProductKcDAO productKcDao;
	private SerialNumDAO serialNumDao;
	
	/**
	 * ���ݲ�ѯ����ȡ�ⷿ�����б���Ϣ
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getKfdbList(String con,int curPage, int rowsPerPage){
		return kfdbDao.getKfdbList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ����ⷿ���������Ϣ
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public void saveKfdb(Kfdb kfdb,List kfdbProducts){
		kfdbDao.saveKfdb(kfdb, kfdbProducts);
		
//		if(kfdb.getState().equals("�ѳ���")){ //�ı���ֵ
//			this.updateKc(kfdb, kfdbProducts);
//			this.updateSerialNum(kfdb, kfdbProducts); //�������к�
//		}
	}
	
	
	/**
	 * ���¿ⷿ���������Ϣ
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public void updateKfdb(Kfdb kfdb,List kfdbProducts){
		kfdbDao.updateKfdb(kfdb, kfdbProducts);
		
//		if(kfdb.getState().equals("�ѳ���")){ //�ı���ֵ
//			this.updateKc(kfdb, kfdbProducts);
//			this.updateSerialNum(kfdb, kfdbProducts);//�������к�
//		}
		
		if(kfdb.getState().equals("�����")){ 
			//ȷ�����
			this.updateKc(kfdb, kfdbProducts);
			this.updateSerialNum(kfdb, kfdbProducts);//�������к�
		}
	}
	
	
	/**
	 * ��ȡ�û���ȷ�ϵĿⷿ�����б�
	 * @param user_id
	 * @return
	 */
	public List getConfirmKfdbList(String user_id){
		return kfdbDao.getConfirmKfdbList(user_id);
	}
	
	
	/**
	 * ȡ�ⷿ������Ϣ
	 * @param id
	 * @return
	 */
	public Object getKfdb(String id){
		return kfdbDao.getKfdb(id);
	}
	
	
	/**
	 * ȡ�ⷿ���������Ʒ��ϸ
	 * @param id
	 * @return
	 */
	public List getKfdbProducts(String id){
		return kfdbDao.getKfdbProducts(id);
	}
	
	/**
	 * ȡ�ⷿ���������Ʒ��ϸ
	 * @param id
	 * @return
	 */
	public List getKfdbProductsObj(String id){
		return kfdbDao.getKfdbProductsObj(id);
	}
	
	
	/**
	 * ɾ���ⷿ���������Ϣ
	 * @param id
	 */
	public void delKfdb(String id){
		kfdbDao.delKfdb(id);
	}
		

	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateKfdbID() {
		return kfdbDao.getKfdbID();
	}
	
	
	/**
	 * �жϵ������Ƿ��Ѿ��ύ
	 * @param id
	 * @return
	 */
	public boolean isDbFinish(String id) {
		return kfdbDao.isDbFinish(id);
	}

	
	/**
	 * �жϿ�����Ƿ����������Ҫ
	 * @param ckd
	 * @param ckdProducts
	 */
	public String checkKc(Kfdb kfdb,List kfdbProducts){
		String msg = "";
		String store_id = kfdb.getCk_store_id();
		
		if(kfdbProducts != null && kfdbProducts.size()>0){
			for(int i=0;i<kfdbProducts.size();i++){
				KfdbProduct kfdbProduct = (KfdbProduct)kfdbProducts.get(i);
				if(kfdbProduct != null){
					if(!kfdbProduct.getProduct_id().equals("") && !kfdbProduct.getProduct_name().equals("")){
						String product_id = kfdbProduct.getProduct_id();
						int cknums = kfdbProduct.getNums();  //Ҫ��������						
						int kcnums = productKcDao.getKcNums(product_id, store_id);//�������
						
						if(cknums>kcnums){
							msg += kfdbProduct.getProduct_name() + " ��ǰ���Ϊ��" + kcnums + "  �޷�����������󣬲��ܳ���\n";
						}
					}
				}
			}
		}
		
		return msg;
	}
	
	
	/**
	 * �ı���ֵ
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public void updateKc(Kfdb kfdb,List kfdbProducts){
		String ck_store_id = kfdb.getCk_store_id();
		String rk_store_id = kfdb.getRk_store_id();
		
		if(kfdbProducts != null && kfdbProducts.size()>0){
			for(int i=0;i<kfdbProducts.size();i++){
				KfdbProduct kfdbProduct = (KfdbProduct)kfdbProducts.get(i);
				if(kfdbProduct != null){
					if(!kfdbProduct.getProduct_id().equals("") && !kfdbProduct.getProduct_name().equals("")){
						
						String product_id = kfdbProduct.getProduct_id();
						int nums = kfdbProduct.getNums(); //��������
						
						productKcDao.updateProductKc(ck_store_id, product_id, nums);  //���ٳ����ֿ���
						productKcDao.addProductKc(rk_store_id, product_id, nums);   //��������ֿ���
					}
				}
			}
		}
	}
	
	
	/**
	 * �������к�״̬
	 * �������������Ϣ���ı����к����ڿⷿ
	 * @param kdfd
	 * @param kfdbProducts
	 */
	private void updateSerialNum(Kfdb kfdb,List kfdbProducts){
		if(kfdbProducts != null && kfdbProducts.size()>0){
			for(int i=0;i<kfdbProducts.size();i++){
				KfdbProduct kfdbProduct = (KfdbProduct)kfdbProducts.get(i);
				if(kfdbProduct != null){
					if(!kfdbProduct.getProduct_id().equals("") && !kfdbProduct.getQz_serial_num().equals("")){
						String[] arryNums = (kfdbProduct.getQz_serial_num()).split(",");

						for(int k=0;k<arryNums.length;k++){
							SerialNumFlow serialNumFlow = new SerialNumFlow();
							
							serialNumFlow.setClient_name("");
							serialNumFlow.setCzr(kfdb.getCzr());
							
							serialNumFlow.setYwtype("����");
							
							serialNumFlow.setFs_date(kfdb.getCk_date());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(kfdb.getJsr()));
							serialNumFlow.setKf_dj_id(kfdb.getId());
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewKfdb.html?id=");
							serialNumFlow.setYw_dj_id(kfdb.getDbsq_id());							
							serialNumFlow.setYw_url("viewDbsq.html?id=");	
							
							//�������к���ת����
							serialNumDao.saveSerialFlow(serialNumFlow);  
							
							//�������к����ڿⷿ
							serialNumDao.updateSerialNumStore(arryNums[k], kfdb.getRk_store_id());
						}
					}
				}
			}
		}
	}
	
	

	public KfdbDAO getKfdbDao() {
		return kfdbDao;
	}


	public void setKfdbDao(KfdbDAO kfdbDao) {
		this.kfdbDao = kfdbDao;
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

}
