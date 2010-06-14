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
	 * ����user_id����real_name
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
	 * ����client_idȡclient_name
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
	 * ����store_idȡstore_name
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
	 * ����idȡ��Ʒ�������
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
	 * ���ݲ���IDȡ��������
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
	 * �����˻�IDȡ�˻�����
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
	 * ����POS�����ȡPOS������
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
	 * ���ݻ��ֹ���IDȡ���ֹ��򷽷�
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
	 * ���ݻ�Ա������IDȡ��Ա����������
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
	 * ���ݷ��������ȡ�����������
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
	 * ȡ�����ļ���ÿҳҳ��
	 * @return
	 */
	public static int getPageSize(){
		SysSource sysSource = (SysSource)ctx.getBean("sysSource");
		return sysSource.getSysPageSize();
	}
	
	
	/**
	 * ȡ�����ļ���ÿҳҳ��2
	 * @return
	 */
	public static int getPageSize2(){
		SysSource sysSource = (SysSource)ctx.getBean("sysSource");
		return sysSource.getSysPageSize2();
	}
	
	
	/**
	 * ȡϵͳ��Ϣ��������
	 * @return
	 */
	public static int getMsgExpireDay(){
		SysSource sysSource = (SysSource)ctx.getBean("sysSource");
		return sysSource.getSysMsgExpireDay();
	}
	
}
