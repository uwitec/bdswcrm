package com.sw.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.model.Product;
import com.sw.cms.model.SysInitSet;
import com.sw.cms.util.DateComFunc;

public class DwrService {
	
	private ProductDAO productDao;
	private ProductKcDAO productKcDao;
	private SysInitSetDAO sysInitSetDao;
	private StoreDAO storeDao;
	private SerialNumDAO serialNumDao;
	private UserDAO     userDao;

	/**
	 * �������кŲ�ѯ��Ʒ����
	 * @param serial_num
	 * @return
	 */
	public Product getProductObjBySerialNum(String serial_num){
		return productDao.getProductBySerialNum(serial_num);
	}
	
	
	/**
	 * �������кż���Ʒ��ſ���Ʒ�Ƿ����
	 * @param serial_num
	 * @param product_id
	 * @return
	 */
	public String productIsExist(String serial_num,String product_id){
		return productDao.productIsExist(serial_num,product_id);
	}
	
	
	/**
	 * ����ʼʱִ�и��¿�漰�������кŲ���
	 * @param product_id
	 * @param store_id
	 * @param serial_num
	 */
	public void updateProductKc(String product_id,String store_id,String serial_num){
		
		//���ݲ�Ʒ���ȡ��Ʒ��Ϣ
		Product product = (Product)productDao.getProductById(product_id);
		
		//�������к�
		productKcDao.updateSerialNum(product, store_id, serial_num);
	}
	
	
	/**
	 * ȡ����Ʒ����
	 * @param product_id
	 * @param store_id
	 * @return
	 */
	public String getProudctKcNums(String product_id,String store_id){
		return productKcDao.getKcNums(product_id, store_id) + "";
	}	
	
	/**
	 * ���ݵ�ǰ���ֵ���ɴӵ�ǰ�쵽ϵͳ�������ڸ�����ڳ�ֵ
	 * @param product_id
	 * @param store_id
	 * @param nums
	 */
	public void genKcqc(String product_id,String store_id,String nums){
		
		//����������
		productKcDao.saveProductKc(product_id, store_id, nums);
		
		SysInitSet sysInitSet = sysInitSetDao.getSysInitSet();		
		String strQyrq = sysInitSet.getQyrq();		
		Date qyrq = DateComFunc.strToDate(strQyrq,"yyyy-MM-dd"); //ϵͳ��������
				
		Date curDate = new Date();     //��ǰʱ��
		
		int kc_nums = new Integer(nums);  //���ֵ
		
		int curKcqc = 0; //����ڳ�ֵ
		
		while(DateComFunc.formatDate(curDate, "yyyy-MM-dd").compareToIgnoreCase(DateComFunc.formatDate(qyrq, "yyyy-MM-dd")) >= 0){
			
			//��ǰ�����ڳ�ֵ = ���ֵ + �������� - �������
			//���磺2008-10-12�Ŀ���ڳ�ֵ  = 2008-10-13�Ŀ���ڳ�ֵ + 2008-10-12�ĳ������� - 2008-10-12���������
			int ck_nums = productKcDao.getCkNums(product_id, store_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			int rk_nums = productKcDao.getRkNums(product_id, store_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			curKcqc = kc_nums + ck_nums - rk_nums;
			
			productKcDao.genKcqc(product_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"), store_id, curKcqc);
			
			kc_nums = curKcqc;
			curDate = DateComFunc.addDay(curDate, -1);  //��ǰ������1
		}
	}
	
	
	/**
	 * �ж����к��Ƿ��ڿ�
	 * @param serial_num
	 * @return
	 */
	public String serialNumIsExist(String serial_num){
		return productDao.productIsExist(serial_num);
	}
	
	
	/**
	 * �ж����к��Ƿ����
	 * @param serial_num
	 * @return
	 */
	public boolean chkSerialNumIsExist(String serial_num){
		return serialNumDao.chkSerialNum(serial_num);
	}
	
	/**
	 * �ȶ�ҵ��Ա�������(Ajax)
	 * @param con
	 * @return
	 */
    //�޸���	-----------------------------------------------------------------------
	public List getAllUserList(String con)
	{  
		List allUsersList= userDao.getAllUserList();
		List<String> result=new ArrayList<String>();
		List<String> userIdList=new ArrayList<String>();
		List<List> list=new ArrayList<List>();	
		if(!con.trim().equals(""))
		{
			String regEx=con;   
			Pattern p=Pattern.compile(regEx);
		    Matcher m=null;
		for(int i=0;i<allUsersList.size();i++)
		{   
			   Map map=(Map)allUsersList.get(i);
			   m=p.matcher(map.get("real_name").toString()); 
			   if(m.find())
			   {
				  result.add(map.get("real_name").toString());
			   }
		}
		for(int i=0;i<allUsersList.size();i++)
		{
			  Map map=(Map)allUsersList.get(i);
			  m=p.matcher(map.get("real_name").toString()); 
			 if(m.find())
			 {
			    userIdList.add(map.get("user_id").toString());	
			 }
		}
		}
		list.add(result);
		list.add(userIdList);
		return list;
	}
   //�޸���	-----------------------------------------------------------------------
	
	
	public ProductDAO getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}


	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}


	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}


	public StoreDAO getStoreDao() {
		return storeDao;
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


	public UserDAO getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
}
