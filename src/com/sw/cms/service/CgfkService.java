package com.sw.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.CnfkdDAO;
import com.sw.cms.dao.RoleDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.CgfkDesc;
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
	 * 取采购应付款列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgfks(String con,int curPage, int rowsPerPage){
		return cgfkDao.getCgfks(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 根据查询条件取所有满足条件的采购付款信息
	 * @param con
	 * @return
	 */
	public List getCgfks(String con){
		return cgfkDao.getCgfks(con);
	}
	
	
	/**
	 * 根据采购付款ID取相关信息
	 * @param yfk_id
	 * @return
	 */
	public Object getCgfk(String id){
		return cgfkDao.getCgfk(id);
	}
	
	
	/**
	 * 根据采购付款ID取明细信息
	 * @param id
	 * @return
	 */
	public List getCgfkDesc(String id){
		return cgfkDao.getCgfkDesc(id);
	}

	
	/**
	 * 根据供应商编号取所有应付款情况
	 * @param gysbh
	 * @return
	 */
	public List getDfkDesc(String gysbh){
		return cgfkDao.getDfkDesc(gysbh);
	}
	
	
	/**
	 * 删除采购付款信息根据采购付款ID
	 * @param jhd_id
	 * @return
	 */
	public void delCgfk(String id){
		
		//如果采购付款已提交，则不做任何操作
		if(cgfkDao.isCgfkSubmit(id)){
			return;
		}
		
		cgfkDao.delCgfk(id);
	}
	
	
	/**
	 * 保存采购付款信息，包括明细信息
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void saveCgfk(Cgfk cgfk,List cgfkDescs){	
		
		Map map = roleDao.getSpRight("采购付款");
		String sp_flag = "";
		String role_id = "";
		if(map != null){
			sp_flag = StringUtils.nullToStr(map.get("sp_flag"));
			role_id = StringUtils.nullToStr(map.get("role_id"));
		}
		
		//采购付款各状态：已保存、待审批、审批不通过、待支付、已支付
		if(cgfk.getState().equals("已提交")){
			
			if(sp_flag.equals("00") || sp_flag.equals("") || role_id.equals("")){
				
				//如果采购付款不需要审批，直接生成相应的出纳付款单	，同时采购付款的状态为待支付				
				cgfk.setState("待支付");	
				this.saveCnfkd(cgfk);

			}else{
				
				//如果采购付款需要审批，直接将付款申请单的状态为待审批
				cgfk.setState("待审批");
			}
		}
		
		//保存采购付款信息
		cgfkDao.saveCgfk(cgfk, cgfkDescs);	
	}
	
	
	/**
	 * 更新采购付款相关信息
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void updateCgfk(Cgfk cgfk,List cgfkDescs){
		
		//如果采购付款已提交，则不做任何操作
		if(cgfkDao.isCgfkSubmit(cgfk.getId())){
			return;
		}
		
		Map map = roleDao.getSpRight("采购付款");
		String sp_flag = "";
		String role_id = "";
		if(map != null){
			sp_flag = StringUtils.nullToStr(map.get("sp_flag"));
			role_id = StringUtils.nullToStr(map.get("role_id"));
		} 
		
		//采购付款各状态：已保存、待审批、审批不通过、待支付、已支付
		if(cgfk.getState().equals("已提交")){
			
			if(sp_flag.equals("00") || sp_flag.equals("") || role_id.equals("")){
				//如果采购付款不需要审批，直接生成相应的出纳付款单	，同时采购付款的状态为待支付
				cgfk.setState("待支付");
				this.saveCnfkd(cgfk);
				
			}else{
				
				//如果采购付款需要审批，直接将付款申请单的状态为待审批
				cgfk.setState("待审批");
			}
		}
		
		//保存采购付款信息
		cgfkDao.updateCgfk(cgfk, cgfkDescs);
	}
	
	
	/**
	 * 采购付款审批
	 * @param id
	 * @param state
	 */
	public void doSp(String id,String state,String remark){
		Cgfk cgfk = (Cgfk)cgfkDao.getCgfk(id);
		cgfk.setRemark(remark);
		List cgfkDescs = cgfkDao.getCgfkDescObj(id);
		
		if(state.equals("通过")){
			//如果审批结果为通过
			cgfk.setState("待支付");
			this.saveCnfkd(cgfk);
		}else{
			//如果审批结果为不通过
			cgfk.setState("审批不通过");
		}
		
		cgfkDao.updateCgfk(cgfk, cgfkDescs);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String updateCgfkID() {
		return cgfkDao.getCgfkID();
	}
	
	
	/**
	 * 根据采购付款申请单生成相应的出纳付款单
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
		if(cgfk.getIs_yfk().equals("是")){
			cnfkd.setFklx("预付款");
		}else{
			cnfkd.setFklx("应付款");
		}
		cnfkd.setFkje(cgfk.getFkje());
		cnfkd.setFkzh(cgfk.getFkzh());
		cnfkd.setLxr(cgfk.getKh_lxr());
		cnfkd.setRemark(cgfk.getRemark());
		cnfkd.setState("待支付");
		cnfkd.setTel(cgfk.getTel());
		
		if(cnfkdDao.isExistCnfkdByCgfkId(cgfk.getId())){
			cnfkdDao.updateCnfkd(cnfkd);
		}else{
			cnfkdDao.insertCnfkd(cnfkd);
		}
		
	}
	
	
	/**
	 * 判断提交的付款明细中是否存在与其他付款申请冲突，如果存在返回编号，不存在返回空
	 * @param cgfk
	 * @param cgfkDescs
	 * @return
	 */
	public String getExistCgfkDesc(Cgfk cgfk,List cgfkDescs){
		String temp = "";
		
		String gysbh = cgfk.getGysbh();
		
		if(cgfkDescs != null && cgfkDescs.size()>0){
			for(int i =0;i<cgfkDescs.size();i++){
				CgfkDesc cgfkDesc = (CgfkDesc)cgfkDescs.get(i);
				if(cgfkDesc != null && cgfkDesc.getBcfk() != 0){
					String jhd_id = cgfkDesc.getJhd_id();
					
					if(cgfkDao.isCgfkDescExist(jhd_id, gysbh)){
						//如果存在冲突，则记录相应进货单编号
						if(temp.equals("")){
							temp = jhd_id;
						}else{
							temp += "," + jhd_id;
						}
					}
				}
			}
		}
		
		return temp;
	}
	
	
	public List cgfkDescObjToMap(List cgfkDescs){
		List list = new ArrayList();
		if(cgfkDescs != null && cgfkDescs.size()>0){
			for(int i =0;i<cgfkDescs.size();i++){
				CgfkDesc cgfkDesc = (CgfkDesc)cgfkDescs.get(i);
				if(cgfkDesc != null){
					
					String jhd_id = cgfkDesc.getJhd_id();
					String fsrq = cgfkDesc.getFsrq();
					double fsje = cgfkDesc.getFsje();
					double yfje = cgfkDesc.getYfje();
					double bcfk = cgfkDesc.getBcfk();
					
					Map map = new HashMap();
					map.put("jhd_id", jhd_id);
					map.put("fsrq", fsrq);
					map.put("fsje", fsje);
					map.put("yfje", yfje);
					map.put("bcfk", bcfk);
					
					list.add(map);
				}
			}
		}
		return list;
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
