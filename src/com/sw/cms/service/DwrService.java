package com.sw.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sw.cms.dao.BxdDAO;
import com.sw.cms.dao.CkdDAO;
import com.sw.cms.dao.ClientsDAO;
import com.sw.cms.dao.LsdDAO;
import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.dao.XsdDAO;
import com.sw.cms.model.Product;
import com.sw.cms.model.SysInitSet;
import com.sw.cms.model.Xsd;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

public class DwrService {

	private ProductDAO productDao;
	private ProductKcDAO productKcDao;
	private SysInitSetDAO sysInitSetDao;
	private StoreDAO storeDao;
	private SerialNumDAO serialNumDao;
	private UserDAO userDao;
	private ClientsDAO clientsDao;
	private BxdDAO   bxdDao;
	private LsdDAO lsdDao;
	private XsdDAO xsdDao;
	private CkdDAO ckdDao;

	public LsdDAO getLsdDao() {
		return lsdDao;
	}

	public void setLsdDao(LsdDAO lsdDao) {
		this.lsdDao = lsdDao;
	}

	public XsdDAO getXsdDao() {
		return xsdDao;
	}

	public void setXsdDao(XsdDAO xsdDao) {
		this.xsdDao = xsdDao;
	}

	/**
	 * 根据序列号查询产品对象
	 * 
	 * @param serial_num
	 * @return
	 */
	public Product getProductObjBySerialNum(String serial_num) {
		return productDao.getProductBySerialNum(serial_num);
	}

	/**
	 * 根据序列号及产品编号看产品是否存在
	 * 
	 * @param serial_num
	 * @param product_id
	 * @return
	 */
	public String productIsExist(String serial_num, String product_id) {
		return productDao.productIsExist(serial_num, product_id);
	}

	/**
	 * 库存初始时执行更新库存及插入序列号操作
	 * 
	 * @param product_id
	 * @param store_id
	 * @param serial_num
	 */
	public void updateProductKc(String product_id, String store_id,
			String serial_num) {

		// 根据产品编号取产品信息
		Product product = (Product) productDao.getProductById(product_id);

		// 保存序列号
		productKcDao.updateSerialNum(product, store_id, serial_num);
	}

	/**
	 * 取库存产品数量
	 * 
	 * @param product_id
	 * @param store_id
	 * @return
	 */
	public String getProudctKcNums(String product_id, String store_id) {
		return productKcDao.getKcNums(product_id, store_id) + "";
	}

	/**
	 * 根据当前库存值生成从当前天到系统启用日期各天的期初值
	 * 
	 * @param product_id
	 * @param store_id
	 * @param nums
	 */
	public void genKcqc(String product_id, String store_id, String nums) {

		// 保存库存数量
		productKcDao.saveProductKc(product_id, store_id, nums);

		SysInitSet sysInitSet = sysInitSetDao.getSysInitSet();
		String strQyrq = sysInitSet.getQyrq();
		Date qyrq = DateComFunc.strToDate(strQyrq, "yyyy-MM-dd"); // 系统启用日期

		Date curDate = new Date(); // 当前时间

		int kc_nums = new Integer(nums); // 库存值

		int curKcqc = 0; // 库存期初值

		while (DateComFunc
				.formatDate(curDate, "yyyy-MM-dd")
				.compareToIgnoreCase(DateComFunc.formatDate(qyrq, "yyyy-MM-dd")) >= 0) {

			// 当前天库存期初值 = 库存值 + 出库数量 - 入库数量
			// 例如：2008-10-12的库存期初值 = 2008-10-13的库存期初值 + 2008-10-12的出库数量 -
			// 2008-10-12的入库数量
			int ck_nums = productKcDao.getCkNums(product_id, store_id,
					DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			int rk_nums = productKcDao.getRkNums(product_id, store_id,
					DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			curKcqc = kc_nums + ck_nums - rk_nums;

			productKcDao.genKcqc(product_id, DateComFunc.formatDate(curDate,
					"yyyy-MM-dd"), store_id, curKcqc);

			kc_nums = curKcqc;
			curDate = DateComFunc.addDay(curDate, -1); // 当前天数减1
		}
	}

	/**
	 * 判断序列号是否在库
	 * 
	 * @param serial_num
	 * @return
	 */
	public String serialNumIsExist(String serial_num) {
		return productDao.productIsExist(serial_num);
	}

	/**
	 * 判断序列号是否存在
	 * 
	 * @param serial_num
	 * @return
	 */
	public boolean chkSerialNumIsExist(String serial_num) {
		return serialNumDao.chkSerialNum(serial_num);
	}

	/**
	 * 比对业务员姓名填充(Ajax)
	 * 
	 * @param con
	 * @return
	 */

	public List getAllUserList(String con) {
		List allUsersList = userDao.getAllUserList();
		List<String> result = new ArrayList<String>();
		List<String> userIdList = new ArrayList<String>();
		List<List> list = new ArrayList<List>();
		if (!con.trim().equals("")) {
			String regEx = con;
			Pattern p = Pattern.compile(regEx.toUpperCase(), Pattern.LITERAL);
			
			Matcher mt_real_name = null;  //匹配员工真实姓名
			Matcher mt_china_py = null;   //匹配员工真实姓名拼音简写
			
			for (int i = 0; i < allUsersList.size(); i++) {
				Map map = (Map) allUsersList.get(i);
				
				mt_real_name = p.matcher(StringUtils.nullToStr(map.get("real_name")));
				mt_china_py = p.matcher(StringUtils.nullToStr(map.get("china_py")));
					
				if (mt_real_name.find() || mt_china_py.find()) {
					result.add(map.get("real_name").toString());
					userIdList.add(map.get("user_id").toString());
				}
			}
			
		}
		list.add(result);
		list.add(userIdList);
		return list;
	}

	/**
	 * 比对客户填充（Ajax）
	 * 
	 * @param con
	 * @return
	 */
	public List getAllClientsList(String con) {
		List allClientsList = clientsDao.getClientList("");
		List<String> clientName = new ArrayList<String>();
		List<String> clinetId = new ArrayList<String>();
		List<List> list = new ArrayList<List>();
		if (!con.trim().equals("")) {
			String regEx = con;
			Pattern p = Pattern.compile(regEx.toUpperCase(), Pattern.LITERAL);
			
			Matcher mt_client_name = null;
			Matcher mt_china_py = null;
			
			for (int i = 0; i < allClientsList.size(); i++) {
				Map map = (Map) allClientsList.get(i);
				
				mt_client_name = p.matcher(StringUtils.nullToStr(map.get("name")));
				mt_china_py = p.matcher(StringUtils.nullToStr(map.get("china_py")));
				
				if (mt_client_name.find() || mt_china_py.find()) {
					clientName.add(map.get("name").toString());
					clinetId.add(map.get("id").toString());
				}
			}
		}
		
		list.add(clientName);
		list.add(clinetId);
		return list;
	}
	
	/**
	 * 根据往来单位名称返回往来单位
	 * @param clients_name
	 * @return
	 */
	public Map getClientByClientName(String clients_name)
	{
		return  clientsDao.getClientByClientName(clients_name);
	}
	
	/**
	 * 查询维修记录或购买记录
	 * @return
	 */
	public String getBxdRecordorBuyRecord(String num)
	{
		
		String str="";
		String []xx=new String[15];
		Map map=(Map)bxdDao.getBxdRecord(num);	
		String flog="";
		//如果所输入的序列号在维修记录里提取最近一次维修记录
		
		if(null!=map)
		{
			flog="1";
			xx[0]=StringUtils.nullToStr(map.get("product_name"));
			xx[1]=StringUtils.nullToStr(map.get("product_xh"));
			xx[2]=StringUtils.nullToStr(map.get("product_gg"));
			xx[3]=StringUtils.nullToStr(map.get("product_serial_num"));
			xx[4]=StringUtils.nullToStr(map.get("product_remark"));
			xx[5]=StringUtils.nullToStr(map.get("gzfx"));
			xx[6]=StringUtils.nullToStr(map.get("pcgc"));
			xx[7]=StringUtils.nullToStr(map.get("client_name"));
			xx[8]=StringUtils.nullToStr(map.get("lxr"));
			xx[9]=StringUtils.nullToStr(map.get("lxdh"));
			xx[10]=StringUtils.nullToStr(map.get("email"));
			xx[11]=StringUtils.nullToStr(map.get("address"));
			xx[12]=StringUtils.nullToStr(map.get("remark"));
			if(null!=StaticParamDo.getClientNameById((String)map.get("client_name")))
			{
				xx[13]=StaticParamDo.getClientNameById((String)map.get("client_name"));
			}
			else
			{
				xx[13]="";
			}
			xx[14]=StringUtils.nullToStr(map.get("product_id"));
			 
		}
		else 
		{
			 
			//查询销售记录里有没有该销售序列号
			Map xsmap=(Map)serialNumDao.getXsRecord(num);
			//如果输入的序列号在购买记录里
			if(null!=xsmap)
			{
				
				xx=new String[9];
				String type=(String)xsmap.get("ywtype");
				//如果记录的类型是“零售单”
				if(type.equals("零售"))
				{		
					flog="2"; 
					Map lsmap=(Map)lsdDao.getLsdByIdBySerailNum((String)xsmap.get("yw_dj_id"),(String)xsmap.get("serial_num"));
					 
					  
					 
					  xx[0]=StringUtils.nullToStr(lsmap.get("product_id"));
					  xx[1]=StringUtils.nullToStr(lsmap.get("product_name"));
					  xx[2]=StringUtils.nullToStr(lsmap.get("product_xh"));
					  String shuzu[]=((String)lsmap.get("qz_serial_num")).split(",");
					  int s=0;
					  for(int i=0;i<shuzu.length;i++)
					  {
						  if(shuzu[i].equals((String)xsmap.get("serial_num")))
						  {
							  s=s+1;
							  xx[3]=StringUtils.nullToStr(shuzu[i]);
							  break;
						  }
					  }
					  if(s==0)
					  {
						  xx[3]="";
					  } 
					  xx[4]=StringUtils.nullToStr(lsmap.get("client_name"));
					  xx[5]=StringUtils.nullToStr(lsmap.get("lxdh"));
					  xx[6]=StringUtils.nullToStr(lsmap.get("mail"));
					  xx[7]=StringUtils.nullToStr(lsmap.get("address"));
					  xx[8]="";
					
					 
				}
                //如果记录的类型是“销售单”
				else if(type.equals("销售"))
				{
					
					//查询”销售单“里的销售产品有没有序列号
					Map xsdmap=(Map)xsdDao.getXsdByIdBySerailNum((String)xsmap.get("yw_dj_id"), (String)xsmap.get("serial_num"));
					//如果“销售单”里的销售产品中没有记录该序列号
					if(null!=xsdmap)
					{
						flog="4"; 
						  xx[0]=StringUtils.nullToStr(xsdmap.get("product_id"));
						  xx[1]=StringUtils.nullToStr(xsdmap.get("product_name"));
						  xx[2]=StringUtils.nullToStr(xsdmap.get("product_xh"));
						  String shuzu[]=((String)xsdmap.get("qz_serial_num")).split(",");
						  int s=0;
						  for(int i=0;i<shuzu.length;i++)
						  {
							  if(shuzu[i].equals((String)xsmap.get("serial_num")))
							  {
								  s=s+1;
								  xx[3]=StringUtils.nullToStr(shuzu[i]);
								  break;
							  }
						  }
						  if(s==0)
						  {
							  xx[3]="";
						  }
						  xx[4]=StringUtils.nullToStr(xsdmap.get("client_name"));
						  xx[5]=StringUtils.nullToStr(xsdmap.get("kh_lxr"));
						  xx[6]=StringUtils.nullToStr(xsdmap.get("kh_address"));
						  xx[7]=StringUtils.nullToStr(xsdmap.get("kh_lxdh"));
						  xx[8]=StringUtils.nullToStr(StaticParamDo.getClientNameById((String)xsdmap.get("client_name")));
						  
						 
						
					}
					//在“出库单”里的销售产品查询该序列号
					else
					{
						
						  Map maps=(Map)ckdDao.getCkdByXsdId((String)xsmap.get("yw_dj_id"));
						 
					      Map ckdmap=(Map)ckdDao.getCkdByIdBySerailNum((String)maps.get("ckd_id"), (String)xsmap.get("serial_num"));
					     if(null!=ckdmap)
					     {
						 				 
					    	 flog="4"; 
						       xx[0]=StringUtils.nullToStr(ckdmap.get("product_id"));
						       xx[1]=StringUtils.nullToStr(ckdmap.get("product_name"));
						       xx[2]=StringUtils.nullToStr(ckdmap.get("product_xh"));
						       String shuzu[]=((String)ckdmap.get("qz_serial_num")).split(",");
						       int s=0;
						       for(int i=0;i<shuzu.length;i++)
						       {
							     if(shuzu[i].equals((String)xsmap.get("serial_num")))
							     {
								  s=s+1;
								  xx[3]=StringUtils.nullToStr(shuzu[i]);
								  break;
							     }
						       }
						       if(s==0)
						       {
						       xx[3]="";
						       }
						       Xsd xsdClientXx=(Xsd)xsdDao.getXsd((String)xsmap.get("yw_dj_id"));
						       if(null!=xsdClientXx)
						       {
							      xx[4]=StringUtils.nullToStr(xsdClientXx.getClient_name());
							      xx[5]=StringUtils.nullToStr(xsdClientXx.getKh_lxr());
							      xx[6]=StringUtils.nullToStr(xsdClientXx.getKh_address());
							      xx[7]=StringUtils.nullToStr(xsdClientXx.getKh_lxdh());
							      xx[8]=StringUtils.nullToStr(StaticParamDo.getClientNameById(xsdClientXx.getClient_name()));
						       }
						       else
						       {
						    	   xx[4]= "";
								   xx[5]= "";
								   xx[6]= "";
								   xx[7]= "";
								   xx[8]= "";
						       }
						  
					     }
					     else
					     {
					    	 flog="3";
							 str="";
					     }
					}
					 
				}
				//如果销售类型是其他类型
				else
				{
					flog="3";
					str="";
				}
				
			}
			//如果输入的序列号不存在销售记录里
			else
			{
				flog="3";
				str="";
			}
		  
		}
		for(int i=0;i<xx.length;i++)
		{
			 
			str+=xx[i]+"$";
		}
		 
		return flog+"$"+str;
		
	}


	
	
	
	public ClientsDAO getClientsDao() {
		return clientsDao;
	}

	public void setClientsDao(ClientsDAO clientsDao) {
		this.clientsDao = clientsDao;
	}

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

	public BxdDAO getBxdDao() {
		return bxdDao;
	}

	public void setBxdDao(BxdDAO bxdDao) {
		this.bxdDao = bxdDao;
	}

	public CkdDAO getCkdDao() {
		return ckdDao;
	}

	public void setCkdDao(CkdDAO ckdDao) {
		this.ckdDao = ckdDao;
	}

}
