package com.sw.cms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Map;

import com.opensymphony.webwork.ServletActionContext;
import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.SysInitSet;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

public class SysInitSetAction extends BaseAction {

	private static final long serialVersionUID = 3380497448979207455L;
	
	private SysInitSetService sysInitSetService;
	private SysInitSet sysInitSet;
	
	private String flag;
	private String msg;
	
	private String cpy_name = "";
	private String logo_url = "";
	
	private File logoFile ;
	private String logoFileFileName;
	
	private String title_name = "";
	private String foot_name = "";
	
	
	/**
	 * ��ȡϵͳ����״̬
	 * ������ϵͳ����ҳ��
	 * @return
	 */
	public String getXgqyFlag(){
		sysInitSet = sysInitSetService.getSysInitSet();
		
		if("".equals(StringUtils.nullToStr(sysInitSet.getQyrq()))){
			sysInitSet.setQyrq(DateComFunc.getToday());			
		}
		
		flag = sysInitSetService.getQyFlag();
		return SUCCESS;
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
		return SUCCESS;
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
		
		return SUCCESS;
	}
	
	/**
	 * ���ϵͳ����(ȫ�����)
	 * @return
	 */
	public String clearSysData(){
		sysInitSetService.updateSys_ClearData();
		msg = "���ϵͳȫ�����ݳɹ���";
		return SUCCESS;
	}
	
	
	/**
	 * ���ϵͳ����(���ҵ������)
	 * @return
	 */
	public String clearSysYwData(){
		sysInitSetService.updateSys_ClearYwData();
		msg = "���ϵͳҵ�����ݳɹ���";
		return SUCCESS;
	}
	
	
	/**
	 * �༭ϵͳLOGO
	 * @return
	 */
	public String editLogo(){
		try{
			Map map = sysInitSetService.getSysLogo();
			
			if(map != null){
				cpy_name = StringUtils.nullToStr(map.get("cpy_name"));
				logo_url = StringUtils.nullToStr(map.get("logo_url"));
			}
			
			return SUCCESS;
		}catch(Exception e){
			log.error("ȡϵͳ��ʼLOGOʧ�ܣ�ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * ����ϵͳLOGO��Ϣ
	 * @return
	 */
	public String saveLogo(){
		try{
			
			if(logoFileFileName != null && !logoFileFileName.equals("")){
				//��ȡ��ǰ�ľ���·��
				String filePath = ServletActionContext.getServletContext().getRealPath("/");
				String imgPath = filePath + "logo/" + logoFileFileName;
				
				//�ϴ���ƷͼƬ�ļ�
				FileOutputStream outputStream = new FileOutputStream(imgPath);
				FileInputStream fileIn = new FileInputStream(logoFile);
				byte[] buffer = new byte[1024];
		
				int len;
				while ((len = fileIn.read(buffer)) > 0) {
					outputStream.write(buffer, 0, len);
				}
				outputStream.flush();
				outputStream.close();
				fileIn.close();		
			}
			
			sysInitSetService.saveSysLogo(cpy_name, logoFileFileName);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("����ϵͳLOGOʧ�ܣ�ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * ȡ��ӡ��������
	 * @return
	 */
	public String editReportSet(){
		try{
			Map map = sysInitSetService.getReportSet();
			
			if(map != null){
				title_name = StringUtils.nullToStr(map.get("title_name"));
				foot_name = StringUtils.nullToStr(map.get("foot_name"));
			}

			return SUCCESS;
		}catch(Exception e){
			log.error("�༭��ӡ����ʧ�ܣ�ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * �����ӡ����
	 * @return
	 */
	public String saveReportSet(){
		try{
			sysInitSetService.saveReportSet(title_name, foot_name);
			this.setMsg("���ݴ�ӡ���ñ���ɹ���");
			return SUCCESS;
		}catch(Exception e){
			log.error("�����ӡ����ʧ�ܣ�ԭ��" + e.getMessage());
			return ERROR;
		}
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


	public String getCpy_name() {
		return cpy_name;
	}


	public void setCpy_name(String cpy_name) {
		this.cpy_name = cpy_name;
	}


	public String getLogo_url() {
		return logo_url;
	}


	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}


	public File getLogoFile() {
		return logoFile;
	}


	public void setLogoFile(File logoFile) {
		this.logoFile = logoFile;
	}


	public String getLogoFileFileName() {
		return logoFileFileName;
	}


	public void setLogoFileFileName(String logoFileFileName) {
		this.logoFileFileName = logoFileFileName;
	}


	public String getFoot_name() {
		return foot_name;
	}


	public void setFoot_name(String foot_name) {
		this.foot_name = foot_name;
	}


	public String getTitle_name() {
		return title_name;
	}


	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}

}
