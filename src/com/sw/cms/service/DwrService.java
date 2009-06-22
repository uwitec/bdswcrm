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
	 * 根据序列号查询产品对象
	 * @param serial_num
	 * @return
	 */
	public Product getProductObjBySerialNum(String serial_num){
		return productDao.getProductBySerialNum(serial_num);
	}
	
	
	/**
	 * 根据序列号及产品编号看产品是否存在
	 * @param serial_num
	 * @param product_id
	 * @return
	 */
	public String productIsExist(String serial_num,String product_id){
		return productDao.productIsExist(serial_num,product_id);
	}
	
	
	/**
	 * 库存初始时执行更新库存及插入序列号操作
	 * @param product_id
	 * @param store_id
	 * @param serial_num
	 */
	public void updateProductKc(String product_id,String store_id,String serial_num){
		
		//根据产品编号取产品信息
		Product product = (Product)productDao.getProductById(product_id);
		
		//保存序列号
		productKcDao.updateSerialNum(product, store_id, serial_num);
	}
	
	
	/**
	 * 取库存产品数量
	 * @param product_id
	 * @param store_id
	 * @return
	 */
	public String getProudctKcNums(String product_id,String store_id){
		return productKcDao.getKcNums(product_id, store_id) + "";
	}	
	
	/**
	 * 根据当前库存值生成从当前天到系统启用日期各天的期初值
	 * @param product_id
	 * @param store_id
	 * @param nums
	 */
	public void genKcqc(String product_id,String store_id,String nums){
		
		//保存库存数量
		productKcDao.saveProductKc(product_id, store_id, nums);
		
		SysInitSet sysInitSet = sysInitSetDao.getSysInitSet();		
		String strQyrq = sysInitSet.getQyrq();		
		Date qyrq = DateComFunc.strToDate(strQyrq,"yyyy-MM-dd"); //系统启用日期
				
		Date curDate = new Date();     //当前时间
		
		int kc_nums = new Integer(nums);  //库存值
		
		int curKcqc = 0; //库存期初值
		
		while(DateComFunc.formatDate(curDate, "yyyy-MM-dd").compareToIgnoreCase(DateComFunc.formatDate(qyrq, "yyyy-MM-dd")) >= 0){
			
			//当前天库存期初值 = 库存值 + 出库数量 - 入库数量
			//例如：2008-10-12的库存期初值  = 2008-10-13的库存期初值 + 2008-10-12的出库数量 - 2008-10-12的入库数量
			int ck_nums = productKcDao.getCkNums(product_id, store_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			int rk_nums = productKcDao.getRkNums(product_id, store_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			curKcqc = kc_nums + ck_nums - rk_nums;
			
			productKcDao.genKcqc(product_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"), store_id, curKcqc);
			
			kc_nums = curKcqc;
			curDate = DateComFunc.addDay(curDate, -1);  //当前天数减1
		}
	}
	
	
	/**
	 * 判断序列号是否在库
	 * @param serial_num
	 * @return
	 */
	public String serialNumIsExist(String serial_num){
		return productDao.productIsExist(serial_num);
	}
	
	
	/**
	 * 判断序列号是否存在
	 * @param serial_num
	 * @return
	 */
	public boolean chkSerialNumIsExist(String serial_num){
		return serialNumDao.chkSerialNum(serial_num);
	}
	
	/**
	 * 比对业务员姓名填充(Ajax)
	 * @param con
	 * @return
	 */
    //修改了	-----------------------------------------------------------------------
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
   //修改了	-----------------------------------------------------------------------
	
	
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
