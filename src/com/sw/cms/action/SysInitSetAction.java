package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.SysInitSet;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.util.DateComFunc;

public class SysInitSetAction extends BaseAction {

	private static final long serialVersionUID = 3380497448979207455L;
	
	private SysInitSetService sysInitSetService;
	private SysInitSet sysInitSet;
	
	private String flag;
	private String msg;
	
	
	/**
	 * 获取系统启用状态
	 * 打开设置系统启用页面
	 * @return
	 */
	public String getXgqyFlag(){
		sysInitSet = sysInitSetService.getSysInitSet();		
		flag = sysInitSetService.getQyFlag();
		return "success";
	}
	
	
	/**
	 * 设置系统启用日期
	 * @return
	 */
	public String setQyrq(){
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		log.info("设置系统启用日期开始，当前操作人：" + info.getReal_name());
		sysInitSet.setCzr(user_id);                   //设置当前操作人
		
		sysInitSetService.setQyrq(sysInitSet);
		
		msg = "系统启用日期设置成功！";		
		return "success";
	}
	
	
	/**
	 * 设置系统完成初始设置
	 * @return
	 */
	public String setWccs(){
		
		//判断是否存在负库存，如果存在负库存不能完成初始化
		if(sysInitSetService.isExistFkc()){
			msg = "存在负库存无法完成初始化，请检查商品库存！";
			return "success";
		}
		
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		log.info("设置系统完成初始标志及日期开始,当前操人：" + info.getReal_name());
		
		SysInitSet sysInitSet = new SysInitSet();
		sysInitSet.setQyrq(DateComFunc.getMonthFirstDay(DateComFunc.getToday()));  //默认启用日期为当前月的第一天
		sysInitSet.setCswcrq(DateComFunc.getToday());   //设置启用日期为当前日期
		sysInitSet.setFlag("1");
		sysInitSet.setCzr(user_id);                   //设置当前操作人
		
		sysInitSetService.setCswc(sysInitSet);
		
		msg = "系统初始化完成！";
		
		return "success";
	}
	
	/**
	 * 清空系统数据
	 * @return
	 */
	public String clearSysData(){
		sysInitSetService.updateSys_ClearData();
		msg = "清空系统数据成功！";
		return "success";
	}
	
	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}
	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlag() {
		return flag;
	}
	public SysInitSet getSysInitSet() {
		return sysInitSet;
	}
	public void setSysInitSet(SysInitSet sysInitSet) {
		this.sysInitSet = sysInitSet;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
