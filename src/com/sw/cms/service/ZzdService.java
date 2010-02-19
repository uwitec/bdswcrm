package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.RkdDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.ZzdDAO;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.Rkd;
import com.sw.cms.model.RkdProduct;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.Zzd;
import com.sw.cms.model.ZzdProduct;
import com.sw.cms.util.StaticParamDo;

public class ZzdService {
	
	private ZzdDAO zzdDao;
	private SerialNumDAO serialNumDao;
	private CkdDAO ckdDao;
	private RkdDAO rkdDao;
	private ProductKcDAO productKcDao;
	private ProductDAO productDao;
	
	/**
	 * ȡ��װ���б�
	 * @param start_date
	 * @param end_date
	 * @param state
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getZzdList(String con,int curPage, int rowsPerPage){
		return zzdDao.getZzdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���ݱ��ȡ��װ����Ϣ
	 * @param id
	 * @return
	 */
	public Zzd editZzd(String id){
		return zzdDao.editZzd(id);
	}
	
	
	/**
	 * ���ݱ��ȡ��װ����Ʒ��ϸ��Ϣ
	 * @param cxd_id
	 * @return
	 */
	public List editZzdProducts(String zzd_id){
		return zzdDao.editZzdProducts(zzd_id);
	}
	
	
	/**
	 * ���ݱ����ж���װ���Ƿ����ύ
	 * @param cxd_id
	 * @return
	 */
	public boolean isZzdSubmit(String zzd_id){
		return zzdDao.isZzdSubmit(zzd_id);
	}
	
	
	/**
	 * ������װ����Ϣ
	 * @param cxd
	 * @param cxdProduct
	 */
	public void updateZzd(Zzd zzd,List zzdProducts){
		zzdDao.updateZzd(zzd, zzdProducts);
		
		// �����װ��״̬Ϊ���ύ����������Ӧ���ⵥ����ⵥ
		if (zzd.getState().equals("���ύ")) {		
			this.addCkd(zzd, zzdProducts);
			this.addRkd(zzd);
		}
	}
	
	
	/**
	 * ɾ����װ��
	 * @param id
	 */
	public void delZzd(String id){
		zzdDao.delZzd(id);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateZzdID() {
		return zzdDao.getZzdID();
	}
	
	
	/**
	 * �жϿ�����Ƿ����������Ҫ
	 * @param ckd
	 * @param ckdProducts
	 */
	public String checkKc(Zzd zzd,List zzdProducts){
		String msg = "";
		String store_id = zzd.getStore_id();
		
		if(zzdProducts != null && zzdProducts.size()>0){
			for(int i=0;i<zzdProducts.size();i++){
				ZzdProduct zzdProduct = (ZzdProduct)zzdProducts.get(i);
				if(zzdProduct != null){
					if(!zzdProduct.getProduct_id().equals("") && !zzdProduct.getProduct_name().equals("")){
						String product_id = zzdProduct.getProduct_id();
						int cknums = zzdProduct.getNums();  //Ҫ��������
						int kcnums = productKcDao.getKcNums(product_id, store_id);//�������						
						if(cknums>kcnums){
							msg += zzdProduct.getProduct_name() + " ��ǰ���Ϊ��" + kcnums + "  �޷���װ�����ȵ�����";
						}
					}
				}
			}
		}
		
		return msg;
	}
	
	
	/**
	 * <p>
	 * ������װ��������Ӧ��ⵥ
	 * </p>
	 * <p>
	 * ͬʱ�������к������Ϣ
	 * </p>
	 * 
	 * @param lsd
	 * @param lsdProducts
	 */
	private void addRkd(Zzd zzd) {
		Rkd rkd = new Rkd();          //��ⵥ������Ϣ
		
		String rkd_id = rkdDao.getRkdID();//��ǰ������ⵥ��
		
		rkd.setRkd_id(rkd_id); 
		rkd.setJhd_id(zzd.getId());
		rkd.setCreatdate(zzd.getCdate());
		rkd.setState("�����");
		rkd.setFzr(zzd.getJsr());
		rkd.setRk_date(zzd.getCdate());
		rkd.setMs("��װ��������ⵥ����װ����� [" + zzd.getId() + "]");
		rkd.setCgfzr(zzd.getJsr());
		rkd.setCzr(zzd.getCzr());
		rkd.setClient_name("");
		rkd.setStore_id(zzd.getStore_id());

		List<RkdProduct> rkdProducts = new ArrayList<RkdProduct>();

		RkdProduct rkdProduct = new RkdProduct();
		
		rkdProduct.setProduct_id(zzd.getProduct_id());
		rkdProduct.setProduct_name(zzd.getProduct_name());
		rkdProduct.setProduct_xh(zzd.getProduct_xh());
		rkdProduct.setPrice(zzd.getPrice());
		rkdProduct.setNums(zzd.getNums());
		rkdProduct.setRkd_id(rkd_id);
		rkdProduct.setRemark(zzd.getRemark());
		rkdProduct.setQz_serial_num(zzd.getSerial_nums());
		
		rkdProducts.add(rkdProduct);

		// �����������ж�����к�ϵͳ������
		if ((zzd.getSerial_nums() != null) && (!zzd.getSerial_nums().equals(""))) {
			String[] arryNums = (zzd.getSerial_nums()).split(",");

			SerialNumMng serialNumMng = new SerialNumMng();
			SerialNumFlow serialNumFlow = new SerialNumFlow();

			for (int k = 0; k < arryNums.length; k++) {

				serialNumMng.setProduct_id(zzd.getProduct_id());
				serialNumMng.setProduct_name(zzd.getProduct_name());
				serialNumMng.setProduct_xh(zzd.getProduct_xh());
				serialNumMng.setSerial_num(arryNums[k]);
				serialNumMng.setState("�ڿ�");
				serialNumMng.setStore_id(zzd.getStore_id());
				serialNumMng.setYj_flag("0");
				serialNumDao.updateSerialNumState(serialNumMng); // �������к�״̬

				serialNumFlow.setClient_name("");
				serialNumFlow.setTel("");
				serialNumFlow.setCzr(zzd.getCzr());
				serialNumFlow.setYwtype("��װ");
				serialNumFlow.setFs_date(zzd.getCdate());
				serialNumFlow.setJsr(StaticParamDo.getRealNameById(zzd.getJsr()));
				serialNumFlow.setKf_dj_id(rkd_id);
				serialNumFlow.setSerial_num(arryNums[k]);
				serialNumFlow.setKf_url("viewRkd.html?rkd_id=");
				serialNumFlow.setYw_dj_id(zzd.getId());
				serialNumFlow.setYw_url("viewZzd.html?id=");
				serialNumFlow.setXsdj(0);

				serialNumDao.saveSerialFlow(serialNumFlow); // �������к���ת����
			}
		}
		
		//������ⵥ
		rkdDao.saveRkd(rkd, rkdProducts);
		
		//���¿�������ɱ���
		productKcDao.updateProductKc(rkd, rkdProducts);	
	}
	
	
	
	/**
	 * ������س��ⵥ������
	 * @param jhd
	 * @param JhdProducts
	 */
	private void addCkd(Zzd zzd,List<ZzdProduct> zzdProducts){
		
		Ckd ckd = new Ckd();
		List<CkdProduct> ckdProducts = new ArrayList<CkdProduct>();  //���ⵥ��Ӧ��Ʒ�б�

		String ckd_id = ckdDao.getCkdID();
		ckd.setCkd_id(ckd_id);
		ckd.setFzr(zzd.getJsr());
		ckd.setXsd_id(zzd.getId());
		ckd.setCreatdate(zzd.getCdate());
		ckd.setCk_date(zzd.getCdate());
		ckd.setState("�ѳ���");
		ckd.setSkzt("����");
		ckd.setMs("��װ�����ɳ��ⵥ����װ����� [" + zzd.getId() + "]");
		ckd.setClient_name("");
		ckd.setXsry(zzd.getJsr());
		ckd.setStore_id(zzd.getStore_id());
		ckd.setCzr(zzd.getCzr());
		
		
		
		if(zzdProducts != null && zzdProducts.size()>0){
			for(int i=0;i<zzdProducts.size();i++){
				
				ZzdProduct zzdProduct = (ZzdProduct)zzdProducts.get(i);
				if(zzdProduct != null && zzdProduct.getProduct_id() != null && !zzdProduct.getProduct_id().equals("")){
					CkdProduct ckdProduct = new CkdProduct();

					ckdProduct.setCkd_id(ckd_id);
					ckdProduct.setProduct_id(zzdProduct.getProduct_id());
					ckdProduct.setProduct_name(zzdProduct.getProduct_name());
					ckdProduct.setProduct_xh(zzdProduct.getProduct_xh());
					ckdProduct.setNums(zzdProduct.getNums());
					ckdProduct.setRemark(zzdProduct.getRemark());
					ckdProduct.setPrice(zzdProduct.getPrice());
					ckdProduct.setCbj(zzdProduct.getPrice());
					ckdProduct.setJgtz(0);
					
					ckdProducts.add(ckdProduct);
					
					//�����
					productKcDao.updateProductKc(zzd.getStore_id(),ckdProduct.getProduct_id(), ckdProduct.getNums());
					
					// ������������к�ϵͳ������
					if ((zzdProduct.getQz_serial_num() != null) && (!zzdProduct.getQz_serial_num().equals(""))) {
						String[] arryNums = (zzdProduct.getQz_serial_num()).split(",");

						SerialNumMng serialNumMng = new SerialNumMng();
						SerialNumFlow serialNumFlow = new SerialNumFlow();

						for (int k = 0; k < arryNums.length; k++) {

							serialNumMng.setProduct_id(zzdProduct.getProduct_id());
							serialNumMng.setProduct_name(zzdProduct.getProduct_name());
							serialNumMng.setProduct_xh(zzdProduct.getProduct_xh());
							serialNumMng.setSerial_num(arryNums[k]);
							serialNumMng.setState("����װ");
							serialNumMng.setStore_id(zzd.getStore_id());
							serialNumMng.setYj_flag("0");
							serialNumDao.updateSerialNumState(serialNumMng); // �������к�״̬

							serialNumFlow.setClient_name("");
							serialNumFlow.setTel("");
							serialNumFlow.setCzr(zzd.getCzr());
							serialNumFlow.setYwtype("��װ");
							serialNumFlow.setFs_date(zzd.getCdate());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(zzd.getJsr()));
							serialNumFlow.setKf_dj_id(ckd_id);
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewCkd.html?ckd_id=");
							serialNumFlow.setYw_dj_id(zzd.getId());
							serialNumFlow.setYw_url("viewZzd.html?id=");
							serialNumFlow.setXsdj(0);

							serialNumDao.saveSerialFlow(serialNumFlow); // �������к���ת����
						}
					}
				}
			}
		}
		
		//������ⵥ
		ckdDao.saveCkd(ckd, ckdProducts);
		
	}


	public ZzdDAO getZzdDao() {
		return zzdDao;
	}


	public void setZzdDao(ZzdDAO zzdDao) {
		this.zzdDao = zzdDao;
	}


	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}


	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}


	public RkdDAO getRkdDao() {
		return rkdDao;
	}


	public void setRkdDao(RkdDAO rkdDao) {
		this.rkdDao = rkdDao;
	}


	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}


	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}


	public ProductDAO getProductDao() {
		return productDao;
	}


	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}


	public CkdDAO getCkdDao() {
		return ckdDao;
	}


	public void setCkdDao(CkdDAO ckdDao) {
		this.ckdDao = ckdDao;
	}


}
