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
	 * ��ȡϵͳ����״̬
	 * ������ϵͳ����ҳ��
	 * @return
	 */
	public String getXgqyFlag(){
		sysInitSet = sysInitSetService.getSysInitSet();		
		flag = sysInitSetService.getQyFlag();
		return "success";
	}
	
	
	/**
	 * ����ϵͳ��������
	 * @return
	 */
	public String setQyrq(){
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		log.info("����ϵͳ�������ڿ�ʼ����ǰ�����ˣ�" + info.getReal_name());
		sysInitSet.setCzr(user_id);                   //���õ�ǰ������
		
		sysInitSetService.setQyrq(sysInitSet);
		
		msg = "ϵͳ�����������óɹ���";		
		return "success";
	}
	
	
	/**
	 * ����ϵͳ��ɳ�ʼ����
	 * @return
	 */
	public String setWccs(){
		
		//�ж��Ƿ���ڸ���棬������ڸ���治����ɳ�ʼ��
		if(sysInitSetService.isExistFkc()){
			msg = "���ڸ�����޷���ɳ�ʼ����������Ʒ��棡";
			return "success";
		}
		
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		log.info("����ϵͳ��ɳ�ʼ��־�����ڿ�ʼ,��ǰ���ˣ�" + info.getReal_name());
		
		SysInitSet sysInitSet = new SysInitSet();
		sysInitSet.setQyrq(DateComFunc.getMonthFirstDay(DateComFunc.getToday()));  //Ĭ����������Ϊ��ǰ�µĵ�һ��
		sysInitSet.setCswcrq(DateComFunc.getToday());   //������������Ϊ��ǰ����
		sysInitSet.setFlag("1");
		sysInitSet.setCzr(user_id);                   //���õ�ǰ������
		
		sysInitSetService.setCswc(sysInitSet);
		
		msg = "ϵͳ��ʼ����ɣ�";
		
		return "success";
	}
	
	/**
	 * ���ϵͳ����
	 * @return
	 */
	public String clearSysData(){
		sysInitSetService.updateSys_ClearData();
		msg = "���ϵͳ���ݳɹ���";
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
