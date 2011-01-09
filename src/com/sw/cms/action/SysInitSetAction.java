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
	 * 获取系统启用状态
	 * 打开设置系统启用页面
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
		return SUCCESS;
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
		
		return SUCCESS;
	}
	
	/**
	 * 清空系统数据(全部清空)
	 * @return
	 */
	public String clearSysData(){
		sysInitSetService.updateSys_ClearData();
		msg = "清空系统全部数据成功！";
		return SUCCESS;
	}
	
	
	/**
	 * 清空系统数据(清空业务数据)
	 * @return
	 */
	public String clearSysYwData(){
		sysInitSetService.updateSys_ClearYwData();
		msg = "清空系统业务数据成功！";
		return SUCCESS;
	}
	
	
	/**
	 * 编辑系统LOGO
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
			log.error("取系统初始LOGO失败，原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 保存系统LOGO信息
	 * @return
	 */
	public String saveLogo(){
		try{
			
			if(logoFileFileName != null && !logoFileFileName.equals("")){
				//获取当前的绝对路径
				String filePath = ServletActionContext.getServletContext().getRealPath("/");
				String imgPath = filePath + "logo/" + logoFileFileName;
				
				//上传商品图片文件
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
			log.error("保存系统LOGO失败，原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 取打印设置内容
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
			log.error("编辑打印设置失败，原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 保存打印设置
	 * @return
	 */
	public String saveReportSet(){
		try{
			sysInitSetService.saveReportSet(title_name, foot_name);
			this.setMsg("单据打印设置保存成功！");
			return SUCCESS;
		}catch(Exception e){
			log.error("保存打印设置失败，原因：" + e.getMessage());
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
