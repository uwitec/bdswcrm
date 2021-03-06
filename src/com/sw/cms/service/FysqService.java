package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.FysqDAO;
import com.sw.cms.dao.QtzcDAO;
import com.sw.cms.dao.RoleDAO;
import com.sw.cms.dao.SysMsgDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.model.Fysq;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtzc;
import com.sw.cms.model.SysMsg;
import com.sw.cms.model.SysUser;
import com.sw.cms.util.StringUtils;

public class FysqService {
	
	private FysqDAO fysqDao;
	private QtzcDAO qtzcDao;
	private SysMsgDAO sysMsgDao;
	private UserDAO userDao;
	private RoleDAO roleDao;
	
	/**
	 * 根据查询条件取费用申请单列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getFysqList(String con,int curPage, int rowsPerPage){
		return fysqDao.getFysqList(con,curPage, rowsPerPage);
	}
	
	/**
	 * 根据查询条件取费用申请单列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public List getFysqList(String con){
		return fysqDao.getFysqList(con);
	}
	
	
	/**
	 * 更新费用申请信息
	 * @param fysq
	 */
	public void updateFysq(Fysq fysq){
		
		Map fysqMap = roleDao.getSpRight("费用申请");
		String sp_flag = "";
		String role_id = "";
		if(fysqMap != null){
			sp_flag = StringUtils.nullToStr(fysqMap.get("sp_flag"));
			role_id = StringUtils.nullToStr(fysqMap.get("role_id"));
		}
		
		if(fysq.getState().equals("提交")){
			
			if(sp_flag.equals("00") || sp_flag.equals("") || role_id.equals("")){
				//如果费用申请不需要审批,则在一般费用添加相应记录
				
				Qtzc qtzc = new Qtzc();
				
				qtzc.setId(qtzcDao.getQtzcID());       //编号
				qtzc.setYwy(fysq.getYwy_id());      //业务员
				qtzc.setState("已保存");  //状态
				qtzc.setType(fysq.getFy_type());   //费用类型
				qtzc.setZcje(fysq.getJe());   //金额
				qtzc.setFklx(fysq.getFklx());  //付款类型(方式)
				qtzc.setZcxm(fysq.getXgkh());   //相关客户
				qtzc.setZczh(fysq.getZfzh());   //支出账户
				qtzc.setSpr(fysq.getSpr());     //审批人
				qtzc.setRemark(fysq.getRemark()); //费用详情
				qtzc.setFysq_id(fysq.getId());   //相应费用申请单编号
				qtzc.setSqr(fysq.getSqr());      //申请人
				qtzc.setYwy_dept(fysq.getYwy_dept());  //业务员部门
				
				qtzcDao.saveQtzc(qtzc);
				
				fysq.setState("待支付");
				
			}else{
				//如果费用需要审批，则添加待审批的提醒
				
				String[] fysqRoles = role_id.split(",");
				List userList = userDao.getUserByRoles(fysqRoles);
				if(userList != null && userList.size() >0){
					for(int i=0;i<userList.size();i++){	
						Map tempMap =(Map)userList.get(i);
						SysMsg msg = new SysMsg();
						msg.setReciever_id(StringUtils.nullToStr(tempMap.get("user_id")));
						msg.setSender_id(fysq.getCzr());
						msg.setMsg_body("有一条待审批费用，请及时审批，费用申请单编号为：" + fysq.getId());						
						sysMsgDao.saveMsg(msg);
					}
				}
				
				fysq.setState("待审批");
			}
			
		}else if(fysq.getState().equals("审批通过")){
			//如果是审批通过，则在一般费用添加相应记录
			
			Qtzc qtzc = new Qtzc();
			
			qtzc.setId(qtzcDao.getQtzcID());       //编号
			qtzc.setYwy(fysq.getYwy_id());      //业务员
			qtzc.setState("已保存");  //状态
			qtzc.setType(fysq.getFy_type());   //费用类型
			qtzc.setZcje(fysq.getJe());   //金额
			qtzc.setFklx(fysq.getFklx());  //付款类型(方式)
			qtzc.setZcxm(fysq.getXgkh());   //相关客户
			qtzc.setZczh(fysq.getZfzh());   //支出账户
			qtzc.setSpr(fysq.getSpr());     //审批人
			qtzc.setRemark(fysq.getRemark()); //费用详情
			qtzc.setFysq_id(fysq.getId());   //相应费用申请单编号
			qtzc.setSqr(fysq.getSqr());      //申请人
			qtzc.setYwy_dept(fysq.getYwy_dept());  //业务员部门
			
			qtzcDao.saveQtzc(qtzc);
			
			SysMsg msg = new SysMsg();
			msg.setReciever_id(fysq.getCzr());
			msg.setSender_id(fysq.getSpr());
			if(userDao.getUser(fysq.getCzr()) != null){
				msg.setMobile_num(((SysUser)userDao.getUser(fysq.getCzr())).getMobile());
			}			
			msg.setMsg_body("您提交的费用申请单［" + fysq.getId() + "］已审批通过，请确认！");
			
			sysMsgDao.saveMsg(msg);
			
			fysq.setState("待支付");
			
		}else if(fysq.getState().equals("审批不通过")){
			SysMsg msg = new SysMsg();
			msg.setReciever_id(fysq.getCzr());
			msg.setSender_id(fysq.getSpr());
			if(userDao.getUser(fysq.getCzr()) != null){
				msg.setMobile_num(((SysUser)userDao.getUser(fysq.getCzr())).getMobile());
			}			
			msg.setMsg_body("您提交的费用申请单［" + fysq.getId() + "］没有审批通过，请确认！");
			
			sysMsgDao.saveMsg(msg);
		}
		
		fysqDao.updateFysq(fysq);
	}
	
	
	/**
	 * 取费用申请相关信息
	 * @param id
	 * @return
	 */
	public Fysq getFysq(String id){
		return fysqDao.getFysq(id);
	}
	
	
	/**
	 * 删除费用申请信息
	 * @param id
	 */
	public void delFysq(String id){
		fysqDao.delFysq(id);
	}
	
	
	/**
	 * 取当前可用的ID号
	 * @return
	 */
	public String updateFysqId(){
		return fysqDao.getFysqID();
	}
	
	
	/**
	 * 费用申请是否审批完成
	 * @param id
	 * @return
	 */
	public boolean isFinishSp(String id){
		return fysqDao.isFinishSp(id);
	}
	

	public FysqDAO getFysqDao() {
		return fysqDao;
	}

	public void setFysqDao(FysqDAO fysqDao) {
		this.fysqDao = fysqDao;
	}


	public QtzcDAO getQtzcDao() {
		return qtzcDao;
	}


	public void setQtzcDao(QtzcDAO qtzcDao) {
		this.qtzcDao = qtzcDao;
	}


	public SysMsgDAO getSysMsgDao() {
		return sysMsgDao;
	}


	public void setSysMsgDao(SysMsgDAO sysMsgDao) {
		this.sysMsgDao = sysMsgDao;
	}


	public UserDAO getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public RoleDAO getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}

}
