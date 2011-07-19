package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.KcpdDAO;
import com.sw.cms.dao.KcpdYkTblDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Kcpd;
import com.sw.cms.model.KcpdDesc;
import com.sw.cms.model.KcpdYkTbl;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.dao.SerialNumDAO;

/**
 * ����̵㴦��<BR>
 * ͬʱ�޸ĵ�ǰ���ֵ������̵�������Ϣ<BR>
 * ͬʱ��Ӱ��������ܡ����ɱ��仯<BR>
 * @author liyt
 *
 */
public class KcpdService {
	
	private KcpdDAO kcpdDao;
	private ProductKcDAO productKcDao;
	private ProductDAO productDao;
	private StoreDAO storeDao;
	private KcpdYkTblDAO kcpdYkTblDao;
	private SerialNumDAO serialNumDao;
	
	public void setKcpdDao(KcpdDAO kcpdDao) {
		this.kcpdDao = kcpdDao;
	}
	
	
	/**
	 * �̵���Ϣ�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getKcpds(String con,int curPage, int rowsPerPage){
		return kcpdDao.getKcpds(con, curPage, rowsPerPage);
	}

	
	/**
	 * ����IDȡ��ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Object getKcpd(String id){
		return kcpdDao.getKcpd(id);
	}
	
	
	/**
	 * �����̵���Ϣ
	 * @param kcpd
	 * @param KcpdDescs
	 */
	public void saveKcpd(Kcpd kcpd,List kcpdDescs){
		kcpdDao.saveKcpd(kcpd, kcpdDescs);
		
		if(kcpd.getState().equals("���ύ")){
			this.insertKcpdYkTbl(kcpd, kcpdDescs);
			this.updateSerialNum(kcpd, kcpdDescs); //�������к�
		}
	}
	
	
	/**
	 * �����̵���Ϣ
	 * @param kcpd
	 * @param kcpdDescs
	 */
	public void updateKcpd(Kcpd kcpd,List kcpdDescs){
		kcpdDao.updateKcpd(kcpd, kcpdDescs);
		
		if(kcpd.getState().equals("���ύ")){
			this.insertKcpdYkTbl(kcpd, kcpdDescs);
			this.updateSerialNum(kcpd, kcpdDescs); //�������к�
		}
	}
	
	/**
	 * �ж����к��Ƿ����������Ҫ
	 * @param kcpd
	 * @param kcpdDesc
	 */
	public String checkXlh(Kcpd kcpd,List kcpdDescs){
		String msg = "";
		String store_id = kcpd.getStore_id();
		
		if(kcpdDescs != null && kcpdDescs.size()>0){
			for(int i=0;i<kcpdDescs.size();i++){
				KcpdDesc kcpdDesc = (KcpdDesc)kcpdDescs.get(i);
				if(kcpdDesc != null){
					if(!kcpdDesc.getProduct_id().equals("") && !kcpdDesc.getProduct_name().equals("") && kcpdDesc.getQz_flag().equals("��")  && kcpdDesc.getYk()<0){
						String product_id = kcpdDesc.getProduct_id();
						String[] arryNums = (kcpdDesc.getQz_serial_num()).split(",");
						
						//�ж���Ʒ�Ƿ��ǿ����Ʒ,ֻ�Կ����Ʒ�Ž���ǿ�����к��ж�
						Product product = (Product)productDao.getProductById(product_id);
						if(product.getProp().equals("�����Ʒ")){	
							for(int k=0;k<arryNums.length;k++){
							  String serialNum = arryNums[k];  //Ҫ��������к�
							  boolean is_store = serialNumDao.getSerialNumState(product_id, store_id,serialNum);//���кŵ�״̬
							
							  if(is_store){								
							  }
							  else
							  {
								  msg += kcpdDesc.getProduct_name() + " ���к�Ϊ��" + serialNum + " �����ڣ���ȷ�Ϻ��ٽ����̵㴦��\n";
							  }
							}
						}
					}
				}
			}
		}
		
		return msg;
	}
	
	/**
	 * �鿴����̵㵥�Ƿ��Ѿ��ύ
	 * @param id
	 * @return
	 */
	public boolean isKcpdSubmit(String id){
		return kcpdDao.isKcpdSubmit(id);
	}
	
	
	/**
	 * �����̵�IDȡ�̵���ϸ
	 * @param pd_id
	 * @return
	 */
	public List getKcpdDescs(String pd_id){
		return kcpdDao.getKcpdDescs(pd_id);
	}
	
	
	/**
	 * ɾ���̵���Ϣ
	 * @param pd_id
	 */
	public void delKcpd(String pd_id){
		kcpdDao.delKcpd(pd_id);
	}

	
	
	/**
	 *��������ȡ�����Ʒ�б�
	 * @param con
	 * @return
	 */
	public Page getAllProductKc(String con,int curPage, int rowsPerPage){
		return productKcDao.getAllProductKc(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ȡ���п����Ʒ�б�����������Ʒ��<BR>
	 * ����̵�ʱ���ã��������û�������
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getAllProductKcIncludeZero(String con,String store_id,int curPage, int rowsPerPage){
		return productKcDao.getAllProductKcIncludeZero(con, store_id, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���ص�ǰ���õ����к�
	 * @return
	 */
	public String updateKcpdId(){
		return kcpdDao.getKcpdID();
	}
	
	
	/**
	 * ���ݿ���̵���Ϣ���¿��������ͬʱ������Ӧ���̵�ӯ����Ϣ
	 * @param kcpd
	 * @param kcpdDescs
	 */
	private void insertKcpdYkTbl(Kcpd kcpd,List kcpdDescs){
		KcpdYkTbl kcpdYkTbl = new KcpdYkTbl();
		
		double je = 0;
		String type = "0";
		if(kcpdDescs != null && kcpdDescs.size()>0){
			for(int i=0;i<kcpdDescs.size();i++){
				KcpdDesc kcpdDesc = (KcpdDesc)kcpdDescs.get(i);
				if(kcpdDesc != null && !kcpdDesc.getProduct_name().equals("")){
					int yk_nums = kcpdDesc.getYk();					
					Product product = (Product)productDao.getProductById(kcpdDesc.getProduct_id());					
					double price = product.getPrice();
					
					productKcDao.updateProductKcNums(kcpdDesc.getProduct_id(), kcpd.getStore_id(), kcpdDesc.getSj_nums());
					
					je += price * yk_nums;
				}
			}
		}
		
		if(je > 0){  //��������
			type = "1";
		}else if(je < 0){ //����֧��
			type = "2";
		}
		
		//�������磬�򱣴�������Ϣ
		if(!type.equals("0")){		
			kcpdYkTbl.setJe(je);
			kcpdYkTbl.setType(type);
			kcpdYkTbl.setKcpd_id(kcpd.getId());
			kcpdYkTbl.setRemark("���̵���Ϣ��������������̵���[" + kcpd.getId() + "]");
			kcpdYkTbl.setCzr(kcpd.getCzr());
			kcpdYkTbl.setJsr(kcpd.getPdr());
			kcpdYkTbl.setFs_date(kcpd.getPdrq());
			
			kcpdYkTblDao.insertKcpdYkTbl(kcpdYkTbl);
		}
	}
	
	
	/**
	 * <p>�������к�</p>
	 * <p>�����������</p>
	 * <p>һ�����۳��⣻�����ɹ��˻�����</p>
	 * <p></p>
	 * @param kcpd
	 * @param kcpdDescs
	 */
	private void updateSerialNum(Kcpd kcpd,List kcpdDescs){
		if(kcpdDescs != null && kcpdDescs.size()>0){
			for(int i=0;i<kcpdDescs.size();i++){
				KcpdDesc kcpdDesc = (KcpdDesc)kcpdDescs.get(i);
				if(kcpdDesc != null){
					if(!kcpdDesc.getProduct_id().equals("") && !kcpdDesc.getQz_serial_num().equals("")){
						String[] arryNums = (kcpdDesc.getQz_serial_num()).split(",");
						SerialNumMng serialNumMng = new SerialNumMng();
						SerialNumFlow serialNumFlow = new SerialNumFlow();
						
						String state = "";
						
						int yk = kcpdDesc.getYk();
						if(yk<0){
							state = "";							
						}else{
							state = "�ڿ�";							
						}
						
						for(int k=0;k<arryNums.length;k++){
							
							serialNumMng.setProduct_id(kcpdDesc.getProduct_id());
							serialNumMng.setProduct_name(kcpdDesc.getProduct_name());
							serialNumMng.setProduct_xh(kcpdDesc.getProduct_xh());
							serialNumMng.setSerial_num(arryNums[k]);
							serialNumMng.setState(state);
							serialNumMng.setStore_id(kcpd.getStore_id());
							serialNumMng.setYj_flag("0");
							
                            serialNumDao.updateSerialNumState(serialNumMng); //�������к�״̬
							
							serialNumFlow.setClient_name("");
							serialNumFlow.setCzr(kcpd.getCzr());							
							serialNumFlow.setYwtype("�̵�");							
							serialNumFlow.setFs_date(kcpd.getPdrq());
							serialNumFlow.setJsr(StaticParamDo.getRealNameById(kcpd.getPdr()));
							serialNumFlow.setKf_dj_id(kcpd.getId());
							serialNumFlow.setSerial_num(arryNums[k]);
							serialNumFlow.setKf_url("viewKcpd.html?id=");
							serialNumFlow.setYw_dj_id("");							
							serialNumFlow.setYw_url("");	                            
													
							serialNumDao.saveSerialFlow(serialNumFlow);  //�������к���ת����
						}
					}
				}
			}
		}
	}
	

	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}

	public void setStoreDao(StoreDAO storeDao) {
		this.storeDao = storeDao;
	}

	public KcpdDAO getKcpdDao() {
		return kcpdDao;
	}

	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}

	public StoreDAO getStoreDao() {
		return storeDao;
	}

	public KcpdYkTblDAO getKcpdYkTblDao() {
		return kcpdYkTblDao;
	}

	public void setKcpdYkTblDao(KcpdYkTblDAO kcpdYkTblDao) {
		this.kcpdYkTblDao = kcpdYkTblDao;
	}

	public ProductDAO getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}
	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}
	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}
}
