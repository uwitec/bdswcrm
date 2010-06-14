package com.sw.cms.util;

import org.springframework.context.ApplicationContext;

import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.ClientsDAO;
import com.sw.cms.dao.DataDisParseDAO;
import com.sw.cms.dao.DeptDAO;
import com.sw.cms.dao.PosTypeDAO;
import com.sw.cms.dao.ProductKindDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.dao.JfgzDAO;
import com.sw.cms.dao.HykflDAO;
import com.sw.cms.source.SysSource;

public class StaticParamDo{
	
	private static ApplicationContext ctx;

	public static ApplicationContext getCtx() {
		return ctx;
	}

	public static void setCtx(ApplicationContext ctx) {
		if(StaticParamDo.ctx == null){
			StaticParamDo.ctx = ctx;
		}		
	}

	/**
	 * 根据user_id返回real_name
	 * @param user_id
	 * @return
	 */
	public static String getRealNameById(String user_id){
		UserDAO dao = (UserDAO)ctx.getBean("userDao");	
		if(user_id == null || user_id.equals("")){
			return "";
		}
		return dao.getRealNameById(user_id);
	}
	
	
	/**
	 * 根据client_id取client_name
	 * @param client_id
	 * @return
	 */
	public static String getClientNameById(String client_id){
		ClientsDAO dao = (ClientsDAO)ctx.getBean("clientsDao");
		
		if(client_id==null || client_id.equals("")){
			return "";
		}
		return dao.getClientNameById(client_id);
	}
	
	
	/**
	 * 根据store_id取store_name
	 * @param store_id
	 * @return
	 */
	public static String getStoreNameById(String store_id){
		StoreDAO dao = (StoreDAO)ctx.getBean("storeDao");
		if(store_id==null || store_id.equals("")){
			return "";
		}
		return dao.getStoreNameById(store_id);
	}
	
	
	/**
	 * 根据id取商品类别名称
	 * @param id
	 * @return
	 */
	public static String getProductKindNameById(String id){
		ProductKindDAO dao = (ProductKindDAO)ctx.getBean("productKindDao");
		
		if(id == null || id.equals("")){
			return "";
		}
		return dao.getKindNameByKindId(id);
	}
	
	
	/**
	 * 根据部门ID取部门名称
	 * @param id
	 * @return
	 */
	public static String getDeptNameById(String id){
		DeptDAO dao = (DeptDAO)ctx.getBean("deptDao");
		if(id == null || id.equals("")){
			return "";
		}
		return dao.getDeptNameById(id);
	}
	
	
	/**
	 * 根据账户ID取账户名称
	 * @param id
	 * @return
	 */
	public static String getAccountNameById(String id){
		AccountsDAO dao = (AccountsDAO)ctx.getBean("accountsDao");
		
		if(id == null || id.equals("")){
			return "";
		}else{
			return dao.getNameById(id);
		}
	}
	
	
	/**
	 * 根据POS机编号取POS机名称
	 * @param id
	 * @return
	 */
	public static String getPosNameById(String id){
		PosTypeDAO dao = (PosTypeDAO)ctx.getBean("posTypeDao");
		
		if(id == null || id.equals("")){
			return "";
		}else{
			return dao.getPosNameById(id);
		}
	}
	
	/**
	 * 根据积分规则ID取积分规则方法
	 * @param id
	 * @return
	 */
	public static String getJfgzNameById(String id){
		JfgzDAO dao = (JfgzDAO)ctx.getBean("jfgzDao");
		if(id == null || id.equals("")){
			return "";
		}
		return dao.getJfgzNameById(id);
	}
	
	/**
	 * 根据会员卡分类ID取会员卡分类名称
	 * @param id
	 * @return
	 */
	public static String getHykflNameById(String id){
		HykflDAO dao = (HykflDAO)ctx.getBean("hykflDao");
		if(id == null || id.equals("")){
			return "";
		}
		return dao.getHykflNameById(id);
	}
	
	/**
	 * 根据费用类别编号取费用类别名称
	 * @param id
	 * @return
	 */
	public static String getFyTypeNameById(String id){
		DataDisParseDAO dao = (DataDisParseDAO)ctx.getBean("dataDisParseDao");
		if(id == null || id.equals("")){
			return "";
		}else{
			return dao.getFyTypeNameById(id);
		}
	}
	
	
	/**
	 * 取配置文件中每页页数
	 * @return
	 */
	public static int getPageSize(){
		SysSource sysSource = (SysSource)ctx.getBean("sysSource");
		return sysSource.getSysPageSize();
	}
	
	
	/**
	 * 取配置文件中每页页数2
	 * @return
	 */
	public static int getPageSize2(){
		SysSource sysSource = (SysSource)ctx.getBean("sysSource");
		return sysSource.getSysPageSize2();
	}
	
	
	/**
	 * 取系统消息保留天数
	 * @return
	 */
	public static int getMsgExpireDay(){
		SysSource sysSource = (SysSource)ctx.getBean("sysSource");
		return sysSource.getSysMsgExpireDay();
	}
	
}
