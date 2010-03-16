package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.CnfkdDAO;
import com.sw.cms.dao.RoleDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.Cnfkd;
import com.sw.cms.model.Page;
import com.sw.cms.util.StringUtils;

public class CgfkService {

	private CgfkDAO cgfkDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private YufukDAO yufukDao;
	private RoleDAO roleDao;
	private CnfkdDAO cnfkdDao;

	/**
	 * ȡ�ɹ�Ӧ�����б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgfks(String con,int curPage, int rowsPerPage){
		return cgfkDao.getCgfks(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���ݲ�ѯ����ȡ�������������Ĳɹ�������Ϣ
	 * @param con
	 * @return
	 */
	public List getCgfks(String con){
		return cgfkDao.getCgfks(con);
	}
	
	
	/**
	 * ���ݲɹ�����IDȡ�����Ϣ
	 * @param yfk_id
	 * @return
	 */
	public Object getCgfk(String id){
		return cgfkDao.getCgfk(id);
	}
	
	
	/**
	 * ���ݲɹ�����IDȡ��ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public List getCgfkDesc(String id){
		return cgfkDao.getCgfkDesc(id);
	}

	
	/**
	 * ���ݹ�Ӧ�̱��ȡ����Ӧ�������
	 * @param gysbh
	 * @return
	 */
	public List getDfkDesc(String gysbh){
		return cgfkDao.getDfkDesc(gysbh);
	}
	
	
	/**
	 * ɾ���ɹ�������Ϣ���ݲɹ�����ID
	 * @param jhd_id
	 * @return
	 */
	public void delCgfk(String id){
		
		//����ɹ��������ύ�������κβ���
		if(cgfkDao.isCgfkSubmit(id)){
			return;
		}
		
		cgfkDao.delCgfk(id);
	}
	
	
	/**
	 * ����ɹ�������Ϣ��������ϸ��Ϣ
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void saveCgfk(Cgfk cgfk,List cgfkDescs){	
		
		Map map = roleDao.getSpRight("�ɹ�����");
		String sp_flag = "";
		String role_id = "";
		if(map != null){
			sp_flag = StringUtils.nullToStr(map.get("sp_flag"));
			role_id = StringUtils.nullToStr(map.get("role_id"));
		}
		
		//�ɹ������״̬���ѱ��桢��������������ͨ������֧������֧��
		if(cgfk.getState().equals("���ύ")){
			
			if(sp_flag.equals("00") || sp_flag.equals("") || role_id.equals("")){
				
				//����ɹ������Ҫ������ֱ��������Ӧ�ĳ��ɸ��	��ͬʱ�ɹ������״̬Ϊ��֧��				
				cgfk.setState("��֧��");	
				this.saveCnfkd(cgfk);

			}else{
				
				//����ɹ�������Ҫ������ֱ�ӽ��������뵥��״̬Ϊ������
				cgfk.setState("������");
			}
		}
		
		//����ɹ�������Ϣ
		cgfkDao.saveCgfk(cgfk, cgfkDescs);	
	}
	
	
	/**
	 * ���²ɹ����������Ϣ
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void updateCgfk(Cgfk cgfk,List cgfkDescs){
		
		//����ɹ��������ύ�������κβ���
		if(cgfkDao.isCgfkSubmit(cgfk.getId())){
			return;
		}
		
		Map map = roleDao.getSpRight("�ɹ�����");
		String sp_flag = "";
		String role_id = "";
		if(map != null){
			sp_flag = StringUtils.nullToStr(map.get("sp_flag"));
			role_id = StringUtils.nullToStr(map.get("role_id"));
		} 
		
		//�ɹ������״̬���ѱ��桢��������������ͨ������֧������֧��
		if(cgfk.getState().equals("���ύ")){
			
			if(sp_flag.equals("00") || sp_flag.equals("") || role_id.equals("")){
				//����ɹ������Ҫ������ֱ��������Ӧ�ĳ��ɸ��	��ͬʱ�ɹ������״̬Ϊ��֧��
				
				cgfk.setState("��֧��");
				this.saveCnfkd(cgfk);
				
			}else{
				
				//����ɹ�������Ҫ������ֱ�ӽ��������뵥��״̬Ϊ������
				cgfk.setState("������");
			}
		}
		
		//����ɹ�������Ϣ
		cgfkDao.updateCgfk(cgfk, cgfkDescs);
	}
	
	
	/**
	 * �ɹ���������
	 * @param id
	 * @param state
	 */
	public void doSp(String id,String state,String remark){
		Cgfk cgfk = (Cgfk)cgfkDao.getCgfk(id);
		cgfk.setRemark(remark);
		List cgfkDescs = cgfkDao.getCgfkDescObj(id);
		
		if(state.equals("ͨ��")){
			//����������Ϊͨ��
			cgfk.setState("��֧��");
			this.saveCnfkd(cgfk);
		}else{
			//����������Ϊ��ͨ��
			cgfk.setState("������ͨ��");
		}
		
		cgfkDao.updateCgfk(cgfk, cgfkDescs);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateCgfkID() {
		return cgfkDao.getCgfkID();
	}
	
	
	/**
	 * ���ݲɹ��������뵥������Ӧ�ĳ��ɸ��
	 * @param cgfk
	 */
	private void saveCnfkd(Cgfk cgfk){
		Cnfkd cnfkd = new Cnfkd();
		
		cnfkd.setBank_no(cgfk.getBank_no());
		cnfkd.setCgfk_id(cgfk.getId());
		cnfkd.setClient_name(cgfk.getGysbh());
		cnfkd.setClient_all_name(cgfk.getClient_all_name());
		cnfkd.setCzr(cgfk.getCzr());
		cnfkd.setFax(cgfk.getFax());
		if(cgfk.getIs_yfk().equals("��")){
			cnfkd.setFklx("Ԥ����");
		}else{
			cnfkd.setFklx("Ӧ����");
		}
		cnfkd.setFkje(cgfk.getFkje());
		cnfkd.setFkzh(cgfk.getFkzh());
		cnfkd.setLxr(cgfk.getKh_lxr());
		cnfkd.setRemark(cgfk.getRemark());
		cnfkd.setState("��֧��");
		cnfkd.setTel(cgfk.getTel());
		
		if(cnfkdDao.isExistCnfkdByCgfkId(cgfk.getId())){
			cnfkdDao.updateCnfkd(cnfkd);
		}else{
			cnfkdDao.insertCnfkd(cnfkd);
		}
		
	}


	public CgfkDAO getCgfkDao() {
		return cgfkDao;
	}


	public void setCgfkDao(CgfkDAO cgfkDao) {
		this.cgfkDao = cgfkDao;
	}


	public AccountsDAO getAccountsDao() {
		return accountsDao;
	}


	public void setAccountsDao(AccountsDAO accountsDao) {
		this.accountsDao = accountsDao;
	}


	public AccountDzdDAO getAccountDzdDao() {
		return accountDzdDao;
	}


	public void setAccountDzdDao(AccountDzdDAO accountDzdDao) {
		this.accountDzdDao = accountDzdDao;
	}


	public YufukDAO getYufukDao() {
		return yufukDao;
	}


	public void setYufukDao(YufukDAO yufukDao) {
		this.yufukDao = yufukDao;
	}


	public RoleDAO getRoleDao() {
		return roleDao;
	}


	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}


	public CnfkdDAO getCnfkdDao() {
		return cnfkdDao;
	}


	public void setCnfkdDao(CnfkdDAO cnfkdDao) {
		this.cnfkdDao = cnfkdDao;
	}
	
}
