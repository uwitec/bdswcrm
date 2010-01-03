package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.CxdDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.RkdDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Cxd;
import com.sw.cms.model.CxdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.Rkd;
import com.sw.cms.model.RkdProduct;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.util.StaticParamDo;

/**
 * ��ж������
 * 
 * @author liyt
 * 
 */
public class CxdService {

	private CxdDAO cxdDao;
	private CkdDAO ckdDao;
	private SerialNumDAO serialNumDao;
	private RkdDAO rkdDao;
	private ProductKcDAO productKcDao;
	private ProductDAO productDao;

	/**
	 * ȡ��ж���б�
	 * 
	 * @param start_date
	 * @param end_date
	 * @param state
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCxdList(String con,int curPage, int rowsPerPage) {
		return cxdDao.getCxdList(con, curPage,rowsPerPage);
	}

	/**
	 * ���ݱ��ȡ��ж����Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public Cxd editCxd(String id) {
		return cxdDao.editCxd(id);
	}
	
	
	/**
	 * ���ݱ��ȡ��ж����Ʒ��ϸ��Ϣ
	 * @param cxd_id
	 * @return
	 */
	public List editCxdProducts(String cxd_id){
		return cxdDao.editCxdProducts(cxd_id);
	}
	
	
	/**
	 * ���ݱ����жϲ�ж���Ƿ����ύ
	 * @param cxd_id
	 * @return
	 */
	public boolean isCxdSubmit(String cxd_id){
		return cxdDao.isCxdSubmit(cxd_id);
	}
	

	/**
	 * �����ж����Ϣ
	 * 
	 * @param cxd
	 * @param cxdProduct
	 */
	public void updateCxd(Cxd cxd, List<CxdProduct> cxdProducts) {
		cxdDao.updateCxd(cxd, cxdProducts);

		// �����ж��״̬Ϊ���ύ����������Ӧ���ⵥ����ⵥ
		if (cxd.getState().equals("���ύ")) {
			this.addCkd(cxd);		
			this.addRkd(cxd, cxdProducts);
		}
	}

	/**
	 * ɾ����ж��
	 * 
	 * @param id
	 */
	public void delCxd(String id) {
		cxdDao.delCxd(id);
	}
	

	/**
	 * ȡ��ǰ���õ����кŲ��������к�
	 * 
	 * @return
	 */
	public String updateCxdID() {
		return cxdDao.getCxdID();
	}
	
	
	/**
	 * �жϿ�����Ƿ����������Ҫ
	 * @param cxd
	 * @param cxdProducts
	 */
	public String checkKc(Cxd cxd){
		String msg = "";
		String store_id = cxd.getStore_id();
		String product_id = cxd.getProduct_id();
		
		//�ж���Ʒ�Ƿ��ǿ����Ʒ,ֻ�Կ����Ʒ�Ž��п�������ж�
		Product product = (Product)productDao.getProductById(product_id);
		if(product.getProp().equals("�����Ʒ")){						
			int cknums = cxd.getNums();  //Ҫ��������						
			int kcnums = productKcDao.getKcNums(product_id, store_id);//�������
			
			if(cknums>kcnums){
				msg += "��Ʒ��" + cxd.getProduct_name() + "��" + StaticParamDo.getStoreNameById(store_id) +" �е�ǰ���Ϊ��" + kcnums + "  �޷���ж����ж���ѱ��棬���ȵ������ٲ�ж��";
			}
		}
		return msg;
	}

	
	/**
	 * <p>
	 * ���ݲ�ж��������Ӧ���ⵥ
	 * </p>
	 * <p>
	 * ͬʱ�������к������Ϣ
	 * </p>
	 * 
	 * @param lsd
	 * @param lsdProducts
	 */
	private void addCkd(Cxd cxd) {
		Ckd ckd = new Ckd();

		String ckd_id = ckdDao.getCkdID();
		ckd.setCkd_id(ckd_id);
		ckd.setFzr(cxd.getJsr());
		ckd.setXsd_id(cxd.getId());
		ckd.setCreatdate(cxd.getCdate());
		ckd.setCk_date(cxd.getCdate());
		ckd.setState("�ѳ���");
		ckd.setSkzt("����");
		ckd.setMs("��ж�����ɳ��ⵥ����ж����� [" + cxd.getId() + "]");
		ckd.setClient_name("");
		ckd.setXsry(cxd.getJsr());
		ckd.setStore_id(cxd.getStore_id());
		ckd.setCzr(cxd.getCzr());

		List<CkdProduct> ckdProducts = new ArrayList<CkdProduct>();

		CkdProduct ckdProduct = new CkdProduct();

		ckdProduct.setCkd_id(ckd_id);
		ckdProduct.setProduct_id(cxd.getProduct_id());
		ckdProduct.setProduct_name(cxd.getProduct_name());
		ckdProduct.setProduct_xh(cxd.getProduct_xh());
		ckdProduct.setNums(cxd.getNums());
		ckdProduct.setRemark(cxd.getRemark());
		ckdProduct.setPrice(cxd.getPrice());
		ckdProduct.setCbj(cxd.getPrice());
		ckdProduct.setJgtz(0);

		// �����������ж�����к�ϵͳ������
		if ((cxd.getSerial_nums() != null) && (!cxd.getSerial_nums().equals(""))) {
			String[] arryNums = (cxd.getSerial_nums()).split(",");

			SerialNumMng serialNumMng = new SerialNumMng();
			SerialNumFlow serialNumFlow = new SerialNumFlow();

			for (int k = 0; k < arryNums.length; k++) {

				serialNumMng.setProduct_id(cxd.getProduct_id());
				serialNumMng.setProduct_name(cxd.getProduct_name());
				serialNumMng.setProduct_xh(cxd.getProduct_xh());
				serialNumMng.setSerial_num(arryNums[k]);
				serialNumMng.setState("�Ѳ�ж");
				serialNumMng.setStore_id("");
				serialNumMng.setYj_flag("0");
				serialNumDao.updateSerialNumState(serialNumMng); // �������к�״̬

				serialNumFlow.setClient_name("");
				serialNumFlow.setTel("");
				serialNumFlow.setCzr(cxd.getCzr());
				serialNumFlow.setYwtype("��ж");
				serialNumFlow.setFs_date(cxd.getCdate());
				serialNumFlow.setJsr(StaticParamDo.getRealNameById(cxd.getJsr()));
				serialNumFlow.setKf_dj_id(ckd_id);
				serialNumFlow.setSerial_num(arryNums[k]);
				serialNumFlow.setKf_url("viewCkd.html?ckd_id=");
				serialNumFlow.setYw_dj_id(cxd.getId());
				serialNumFlow.setYw_url("viewCxd.html?id=");
				serialNumFlow.setXsdj(0);

				serialNumDao.saveSerialFlow(serialNumFlow); // �������к���ת����
			}
		}

		ckdProducts.add(ckdProduct);
		
		//������ⵥ
		ckdDao.saveCkd(ckd, ckdProducts);
		
		//�����
		productKcDao.updateProductKc(cxd.getStore_id(),cxd.getProduct_id(), cxd.getNums());
	}
	
	
	
	/**
	 * ���������ⵥ������
	 * @param jhd
	 * @param JhdProducts
	 */
	private void addRkd(Cxd cxd,List<CxdProduct> cxdProducts){
		
		Rkd rkd = new Rkd();          //��ⵥ������Ϣ
		List<RkdProduct> rkdProducts = new ArrayList<RkdProduct>();  //��ⵥ��Ӧ��Ʒ�б�
		
		String rkd_id = rkdDao.getRkdID();//��ǰ������ⵥ��
		
		rkd.setRkd_id(rkd_id); 
		rkd.setJhd_id(cxd.getId());
		rkd.setCreatdate(cxd.getCdate());
		rkd.setState("�����");
		rkd.setFzr(cxd.getJsr());
		rkd.setRk_date(cxd.getCdate());
		rkd.setMs("��ж��������ⵥ����ж����� [" + cxd.getId() + "]");
		rkd.setCgfzr(cxd.getJsr());
		rkd.setCzr(cxd.getCzr());
		rkd.setClient_name("");
		rkd.setStore_id(cxd.getStore_id());
		
		
		
		if(cxdProducts != null && cxdProducts.size()>0){
			for(int i=0;i<cxdProducts.size();i++){
				
				CxdProduct cxdProduct = (CxdProduct)cxdProducts.get(i);
				if(cxdProduct != null && cxdProduct.getProduct_id() != null && !cxdProduct.getProduct_id().equals("")){
					RkdProduct rkdProduct = new RkdProduct();
					
					rkdProduct.setProduct_id(cxdProduct.getProduct_id());
					rkdProduct.setProduct_name(cxdProduct.getProduct_name());
					rkdProduct.setProduct_xh(cxdProduct.getProduct_xh());
					rkdProduct.setPrice(cxdProduct.getPrice());
					rkdProduct.setNums(cxdProduct.getNums());
					rkdProduct.setRkd_id(rkd_id);
					rkdProduct.setRemark(cxdProduct.getRemark());
					rkdProduct.setQz_serial_num(cxdProduct.getQz_serial_num());
					
					rkdProducts.add(rkdProduct);
					
					// ������������к�ϵͳ������
					if ((cxdProduct.getQz_serial_num() != null) && (!cxdProduct.getQz_serial_num().equals(""))) {
						String[] arryNums = (cxdProduct.getQz_serial_num()).split(",");

						SerialNumMng serialNumMng = new SerialNumMng();
						SerialNumFlow serialNumFlow = new SerialNumFlow();

						for (int k = 0; k < arryNums.length; k++) {

							serialNumMng.setProduct_id(cxdProduct.getProduct_id());
							serialNumMng.setProduct_name(cxdProduct.getProduct_name());
							serialNumMng.setProduct_xh(cxdProduct.getProduct_xh());
							serialNumMng.setSerial_num(arryNums[k]);
							serialNumMng.setState("�ڿ�");
							serialNumMng.setStore_id(cxd.getStore_id());
							serialNumMng.setYj_flag("0");
							serialNumDao.updateSerialNumState(serialNumMng); // �������к�״̬

							serialNumFlow.setClient_name("");
							serialNumFlow.setTel("");
							serialNumFlow.setCzr(cxd.getCzr());
							serialNumFlow.setYwtype("��ж");
							serialNumFlow.setFs_date(cxd.getCdate());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(cxd.getJsr()));
							serialNumFlow.setKf_dj_id(rkd_id);
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewRkd.html?rkd_id=");
							serialNumFlow.setYw_dj_id(cxd.getId());
							serialNumFlow.setYw_url("viewCxd.html?id=");
							serialNumFlow.setXsdj(0);

							serialNumDao.saveSerialFlow(serialNumFlow); // �������к���ת����
						}
					}
				}
			}
		}
		
		//������ⵥ
		rkdDao.saveRkd(rkd, rkdProducts);
		
		//���¿�������ɱ���
		productKcDao.updateProductKc(rkd, rkdProducts);		
		
	}

	
	public CxdDAO getCxdDao() {
		return cxdDao;
	}

	public void setCxdDao(CxdDAO cxdDao) {
		this.cxdDao = cxdDao;
	}

	public CkdDAO getCkdDao() {
		return ckdDao;
	}

	public void setCkdDao(CkdDAO ckdDao) {
		this.ckdDao = ckdDao;
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

}
