package com.sw.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sw.cms.dao.InitParamDAO;
import com.sw.cms.dao.KcMxReportDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcQcXmlDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.model.StoreHouse;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;
import com.sw.cms.xml.productkc.ProductKcQc;
import com.sw.cms.xml.productkc.ProductKcQcXmlDo;

public class InitParamService {
	
	private InitParamDAO initParamDao;
	private KcMxReportDAO kcMxReportDao;
	private StoreDAO storeDao;
	private ProductDAO productDao;
	private UserDAO userDao;
	private ProductKcQcXmlDAO productKcQcXmlDao;
	


	public ProductKcQcXmlDAO getProductKcQcXmlDao() {
		return productKcQcXmlDao;
	}

	public void setProductKcQcXmlDao(ProductKcQcXmlDAO productKcQcXmlDao) {
		this.productKcQcXmlDao = productKcQcXmlDao;
	}

	private Log log = LogFactory.getLog(getClass());
	
	/**
	 * ����ҵ����ز���Ϊ1
	 *
	 */
	public void updateParam(){
		initParamDao.updateParam();
	}
	
	/**
	 * �����˻��ڳ�
	 *
	 */
	public void insertAccountQc(){
		initParamDao.genAccountQc();
	}
	
	
	/**
	 * ���ɿ���ڳ�
	 *
	 */
	public void insertKcQc(){
		initParamDao.genKcQc();
	}
	
	
	/**
	 * ���ɿͻ������ڳ�
	 * ������ڳ���������������ڽ�����ڳ�
	 *
	 */
	public void insertClientQc(){
		initParamDao.genYsQc();
	}
	
	
	/**
	 * ɾ�������޵���Ϣ
	 *
	 */
	public void delExpireMsg(){
		initParamDao.delExpireMsg();
	}
	
	/**
	 * �����ۺ�����Ʒ�ڿ�����
	 *
	 */
	
	public void updateShkcProductDay(){
		initParamDao.updateShkcProductDay();
	}
	
	
	/**
	 * �������ɿͻ��ڳ���Ϣ���ӿ�ʼ���ڵ���������<BR>
	 * ���뱣�濪ʼ����ǰһ����ڳ���Ϣ����ȷ�ģ��������ɵ������ڳ��ڶ��Ǵ����<BR>
	 * ���磺Ҫ������������2009-09-01��2009-10-01���ڳ�<BR>
	 * �����start_date=2009-09-01<BR>
	 * ����end_date=2009-10-01<BR>
	 * ͬʱҪ��֤2009-08-31���ڳ�ֵ����ȷ��<BR><BR>
	 * 2010-01-05���
	 * @param start_date ��ʼ����
	 * @param end_date   ��������
	 */
	public void updateBatchGenClientWlQc(String start_date,String end_date){
		initParamDao.updateBatchGenClientWlQc(start_date, end_date);
	}
	
	
	/**
	 * ���������˻��ڳ���Ϣ���ӿ�ʼ���ڵ���������<BR>
	 * ���뱣�濪ʼ����ǰһ����ڳ���Ϣ����ȷ�ģ��������ɵ������ڳ��ڶ��Ǵ����<BR>
	 * ���磺Ҫ������������2009-09-01��2009-10-01���ڳ�<BR>
	 * �����start_date=2009-09-01<BR>
	 * ����end_date=2009-10-01<BR>
	 * ͬʱҪ��֤2009-08-31���ڳ�ֵ����ȷ��<BR><BR>
	 * 2010-01-05���
	 * @param start_date ��ʼ����
	 * @param end_date   ��������
	 */
	public void updateBatchGenAccountQc(String start_date,String end_date){
		initParamDao.updateBatchGenAccountQc(start_date, end_date);
	}
	
	
	/**
	 * ����������Ʒ����ڳ���Ϣ���ӿ�ʼ���ڵ���������<BR>
	 * ���뱣�濪ʼ����ǰһ����ڳ���Ϣ����ȷ�ģ��������ɵ������ڳ��ڶ��Ǵ����<BR>
	 * ���磺Ҫ������������2009-09-01��2009-10-01���ڳ�<BR>
	 * �����start_date=2009-09-01<BR>
	 * ����end_date=2009-10-01<BR>
	 * ͬʱҪ��֤2009-08-31���ڳ�ֵ����ȷ��<BR><BR>
	 * 2010-01-05���
	 * @param start_date ��ʼ����
	 * @param end_date   ��������
	 */
	public void updateBatchGenProductKcQc(String start_date,String end_date){
		Date curDate = DateComFunc.strToDate(start_date,"yyyy-MM-dd");		
		
		List storeList = storeDao.getAllStoreList();
		if(storeList == null || storeList.size() <=0){
			return;
		}
		
		List productList = new ArrayList();
		while(DateComFunc.formatDate(curDate, "yyyy-MM-dd").compareToIgnoreCase(end_date) <= 0){
			
			String cdate = DateComFunc.formatDate(curDate, "yyyy-MM-dd");
			String cdat_1 =  DateComFunc.formatDate((DateComFunc.addDay(curDate,-1)),"yyyy-MM-dd");

			for(int i=0;i<storeList.size();i++){ //�����пⷿ��ѭ��
				StoreHouse store = (StoreHouse)storeList.get(i);
				
				productList = productDao.getProducts(" and prop='�����Ʒ'");
				
				if(productList != null && productList.size() > 0){
					Map map = (Map)productList.get(i);
					String product_id = StringUtils.nullToStr(map.get("product_id"));
					
					//������Ʒ��š���ʼʱ�䡢�ⷿ���ȡǰһ��Ŀ���ڳ����
					Map qcMap = kcMxReportDao.getKcqcMxMap(product_id,cdat_1,store.getId());	
					String strNums = "0";
					if(qcMap != null){
						strNums = StringUtils.nullToStr(qcMap.get("nums"));
						if(strNums.equals("")){
							strNums = "0";
						}
					}
					
					int qc_nums = new Integer(strNums).intValue();  //�ڳ���
					
					int rk_nums = kcMxReportDao.getRkNums(product_id,start_date,cdat_1,store.getId()); //��������
					int ck_nums = kcMxReportDao.getCkNums(product_id,start_date,cdat_1,store.getId()); //��������
					
					int jc_nums = qc_nums + rk_nums - ck_nums;   //�������
					
					//����Ҫ��ӽ��ɱ�
				}
			}
			
			curDate = DateComFunc.addDay(curDate, 1);  //��ǰ������1
		}
	}
	
	/**
	 * ����Ա���Ĺ���
	 */
	public void updateEmploeeGl(){
		List list = userDao.getAllEmployeeList();
		
		String user_id = "";
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String rzrq = StringUtils.nullToStr(map.get("rzrq"));
				
				if(rzrq.equals(""))
					continue;
				
				int gl = DateComFunc.getYearSub(rzrq,DateComFunc.getToday());
				user_id = StringUtils.nullToStr(map.get("user_id"));
				
				userDao.updateUserGL(user_id, gl);
			}
		}
		
		log.info("����Ա������ɹ�");
	}
	
	public void insertKcQcNew(){
		initParamDao.genKcQcNew();
	}
	
	
	/**
	 * ������ӿ���ڳ���Ϣ��XML��
	 * ���磺Ҫ�������2009-09-01��2009-10-01���ڳ�<BR>
	 * �����start_date=2009-09-01<BR>
	 * ����end_date=2009-10-01<BR>
	 * @param start_date ��ʼ����
	 * @param end_date   ��������
	 */
	public void insertBatchProductKcQcXml(String start_date,String end_date){
		
		System.out.println("��ʼ����" + start_date + "��" + end_date + "�����Ϣ");
		Date curDate = DateComFunc.strToDate(start_date,"yyyy-MM-dd");		
		
		List storeList = storeDao.getAllStoreList();
		if(storeList == null || storeList.size() <=0){
			return;
		}
		
		while(DateComFunc.formatDate(curDate, "yyyy-MM-dd").compareToIgnoreCase(end_date) <= 0){
			String cdat_1 =  DateComFunc.formatDate(curDate,"yyyy-MM-dd");
			System.out.println("��ʼ����" + cdat_1 + "���");
			
			//ɾ����ǰ��棬��������
			productKcQcXmlDao.deleteProductKcQcXml(cdat_1);
			
			//�ֲֿ�������
			for(int i=0;i<storeList.size();i++){
				StoreHouse storeHouse = (StoreHouse)storeList.get(i);
				
				String store_id = storeHouse.getId();
				System.out.println("-----------------------------------------------------------");
				System.out.println("��ǰ�ⷿ" + store_id + new Date());
				
				List kcList = productKcQcXmlDao.getProducts(store_id, cdat_1);
				
				ProductKcQc productKcQc = new ProductKcQc();
				productKcQc.setCdate(cdat_1);
				productKcQc.setStoreId(store_id);
				productKcQc.setProducts(kcList);
				
				ProductKcQcXmlDo xmlDo = new ProductKcQcXmlDo();
				String xmlString = "";
				try{
					xmlString = xmlDo.toXml(productKcQc);  //���XML�ļ�
				}catch(Exception e){
					e.printStackTrace();
				}
				
				//�������ڳ���Ϣ
				productKcQcXmlDao.insertProductKcQcXml(store_id, cdat_1, xmlString);
				System.out.println("�ⷿ" + store_id + "���ɽ���" + new Date());
			}
			
			System.out.println("-----------------------------------------------------------");
			System.out.println("��ʼ�����ܿ��" + new Date());
			//�����ܿ��
			List kcList = productKcQcXmlDao.getProducts(cdat_1);
			ProductKcQc productKcQc = new ProductKcQc();
			productKcQc.setCdate(cdat_1);
			productKcQc.setStoreId("all");
			productKcQc.setProducts(kcList);
			
			ProductKcQcXmlDo xmlDo = new ProductKcQcXmlDo();
			String xmlString = "";
			try{
				xmlString = xmlDo.toXml(productKcQc);  //���XML�ļ�
			}catch(Exception e){
				e.printStackTrace();
			}			
			//�������ڳ���Ϣ
			productKcQcXmlDao.insertProductKcQcXml("all", cdat_1, xmlString);
			System.out.println("�����ܿ�����" + new Date());

			curDate = DateComFunc.addDay(curDate, 1);  //��ǰ������1
		}
	}
	

	public InitParamDAO getInitParamDao() {
		return initParamDao;
	}

	public void setInitParamDao(InitParamDAO initParamDao) {
		this.initParamDao = initParamDao;
	}

	public KcMxReportDAO getKcMxReportDao() {
		return kcMxReportDao;
	}

	public void setKcMxReportDao(KcMxReportDAO kcMxReportDao) {
		this.kcMxReportDao = kcMxReportDao;
	}

	public StoreDAO getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(StoreDAO storeDao) {
		this.storeDao = storeDao;
	}

	public ProductDAO getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

}
