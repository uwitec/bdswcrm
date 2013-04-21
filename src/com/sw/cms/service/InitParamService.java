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
	 * 重置业务相关参数为1
	 *
	 */
	public void updateParam(){
		initParamDao.updateParam();
	}
	
	/**
	 * 生成账户期初
	 *
	 */
	public void insertAccountQc(){
		initParamDao.genAccountQc();
	}
	
	
	/**
	 * 生成库存期初
	 *
	 */
	public void insertKcQc(){
		initParamDao.genKcQc();
	}
	
	
	/**
	 * 生成客户往来期初
	 * 昨天的期初加昨天的往来等于今天的期初
	 *
	 */
	public void insertClientQc(){
		initParamDao.genYsQc();
	}
	
	
	/**
	 * 删除超期限的消息
	 *
	 */
	public void delExpireMsg(){
		initParamDao.delExpireMsg();
	}
	
	/**
	 * 更改售后库存商品在库天数
	 *
	 */
	
	public void updateShkcProductDay(){
		initParamDao.updateShkcProductDay();
	}
	
	
	/**
	 * 批量生成客户期初信息，从开始日期到结束日期<BR>
	 * 必须保存开始日期前一天的期初信息是正确的，否则生成的所有期初期都是错误的<BR>
	 * 例如：要批量重新生成2009-09-01到2009-10-01的期初<BR>
	 * 则参数start_date=2009-09-01<BR>
	 * 参数end_date=2009-10-01<BR>
	 * 同时要保证2009-08-31的期初值是正确的<BR><BR>
	 * 2010-01-05添加
	 * @param start_date 开始日期
	 * @param end_date   结束日期
	 */
	public void updateBatchGenClientWlQc(String start_date,String end_date){
		initParamDao.updateBatchGenClientWlQc(start_date, end_date);
	}
	
	
	/**
	 * 批量生成账户期初信息，从开始日期到结束日期<BR>
	 * 必须保存开始日期前一天的期初信息是正确的，否则生成的所有期初期都是错误的<BR>
	 * 例如：要批量重新生成2009-09-01到2009-10-01的期初<BR>
	 * 则参数start_date=2009-09-01<BR>
	 * 参数end_date=2009-10-01<BR>
	 * 同时要保证2009-08-31的期初值是正确的<BR><BR>
	 * 2010-01-05添加
	 * @param start_date 开始日期
	 * @param end_date   结束日期
	 */
	public void updateBatchGenAccountQc(String start_date,String end_date){
		initParamDao.updateBatchGenAccountQc(start_date, end_date);
	}
	
	
	/**
	 * 批量生成商品库存期初信息，从开始日期到结束日期<BR>
	 * 必须保存开始日期前一天的期初信息是正确的，否则生成的所有期初期都是错误的<BR>
	 * 例如：要批量重新生成2009-09-01到2009-10-01的期初<BR>
	 * 则参数start_date=2009-09-01<BR>
	 * 参数end_date=2009-10-01<BR>
	 * 同时要保证2009-08-31的期初值是正确的<BR><BR>
	 * 2010-01-05添加
	 * @param start_date 开始日期
	 * @param end_date   结束日期
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

			for(int i=0;i<storeList.size();i++){ //对所有库房做循环
				StoreHouse store = (StoreHouse)storeList.get(i);
				
				productList = productDao.getProducts(" and prop='库存商品'");
				
				if(productList != null && productList.size() > 0){
					Map map = (Map)productList.get(i);
					String product_id = StringUtils.nullToStr(map.get("product_id"));
					
					//根据商品编号、开始时间、库房编号取前一天的库存期初情况
					Map qcMap = kcMxReportDao.getKcqcMxMap(product_id,cdat_1,store.getId());	
					String strNums = "0";
					if(qcMap != null){
						strNums = StringUtils.nullToStr(qcMap.get("nums"));
						if(strNums.equals("")){
							strNums = "0";
						}
					}
					
					int qc_nums = new Integer(strNums).intValue();  //期初数
					
					int rk_nums = kcMxReportDao.getRkNums(product_id,start_date,cdat_1,store.getId()); //收入数量
					int ck_nums = kcMxReportDao.getCkNums(product_id,start_date,cdat_1,store.getId()); //发出数量
					
					int jc_nums = qc_nums + rk_nums - ck_nums;   //结存数量
					
					//还需要添加结存成本
				}
			}
			
			curDate = DateComFunc.addDay(curDate, 1);  //当前天数加1
		}
	}
	
	/**
	 * 更新员工的工龄
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
		
		log.info("更新员工工龄成功");
	}
	
	public void insertKcQcNew(){
		initParamDao.genKcQcNew();
	}
	
	
	/**
	 * 批量添加库存期初信息（XML）
	 * 例如：要批量添加2009-09-01到2009-10-01的期初<BR>
	 * 则参数start_date=2009-09-01<BR>
	 * 参数end_date=2009-10-01<BR>
	 * @param start_date 开始日期
	 * @param end_date   结束日期
	 */
	public void insertBatchProductKcQcXml(String start_date,String end_date){
		
		System.out.println("开始生成" + start_date + "至" + end_date + "库存信息");
		Date curDate = DateComFunc.strToDate(start_date,"yyyy-MM-dd");		
		
		List storeList = storeDao.getAllStoreList();
		if(storeList == null || storeList.size() <=0){
			return;
		}
		
		while(DateComFunc.formatDate(curDate, "yyyy-MM-dd").compareToIgnoreCase(end_date) <= 0){
			String cdat_1 =  DateComFunc.formatDate(curDate,"yyyy-MM-dd");
			System.out.println("开始生成" + cdat_1 + "库存");
			
			//删除当前库存，重新生成
			productKcQcXmlDao.deleteProductKcQcXml(cdat_1);
			
			//分仓库库存生成
			for(int i=0;i<storeList.size();i++){
				StoreHouse storeHouse = (StoreHouse)storeList.get(i);
				
				String store_id = storeHouse.getId();
				System.out.println("-----------------------------------------------------------");
				System.out.println("当前库房" + store_id + new Date());
				
				List kcList = productKcQcXmlDao.getProducts(store_id, cdat_1);
				
				ProductKcQc productKcQc = new ProductKcQc();
				productKcQc.setCdate(cdat_1);
				productKcQc.setStoreId(store_id);
				productKcQc.setProducts(kcList);
				
				ProductKcQcXmlDo xmlDo = new ProductKcQcXmlDo();
				String xmlString = "";
				try{
					xmlString = xmlDo.toXml(productKcQc);  //库存XML文件
				}catch(Exception e){
					e.printStackTrace();
				}
				
				//保存库存期初信息
				productKcQcXmlDao.insertProductKcQcXml(store_id, cdat_1, xmlString);
				System.out.println("库房" + store_id + "生成结束" + new Date());
			}
			
			System.out.println("-----------------------------------------------------------");
			System.out.println("开始生成总库存" + new Date());
			//生成总库存
			List kcList = productKcQcXmlDao.getProducts(cdat_1);
			ProductKcQc productKcQc = new ProductKcQc();
			productKcQc.setCdate(cdat_1);
			productKcQc.setStoreId("all");
			productKcQc.setProducts(kcList);
			
			ProductKcQcXmlDo xmlDo = new ProductKcQcXmlDo();
			String xmlString = "";
			try{
				xmlString = xmlDo.toXml(productKcQc);  //库存XML文件
			}catch(Exception e){
				e.printStackTrace();
			}			
			//保存库存期初信息
			productKcQcXmlDao.insertProductKcQcXml("all", cdat_1, xmlString);
			System.out.println("生成总库存结束" + new Date());

			curDate = DateComFunc.addDay(curDate, 1);  //当前天数加1
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
