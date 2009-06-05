package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.SjzdJbxx;
import com.sw.cms.model.SjzdXmxx;
import com.sw.cms.service.SjzdService;

public class SjzdAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private SjzdService sjzdService;
	
	private List sjzdJbxxList = new ArrayList();
	private List sjzdXmxxList = new ArrayList();
	
	private SjzdXmxx sjzdXmxx = new SjzdXmxx();
	private SjzdJbxx sjzdJbxx = new SjzdJbxx();
	private String xm_id = "";
	private String zd_id = "";
	
	/**
	 * 取所有字典基本信息列表
	 * @return
	 */
	public String listSjzd(){
		sjzdJbxxList = sjzdService.getAllSjzdJbxx();
		return "success";
	}
	
	/**
	 * 根据字典编号取所有字典信息
	 * @return
	 */
	public String listZdxx(){
		sjzdJbxx = sjzdService.getSjzdJbxxByZdId(zd_id);
		sjzdXmxxList = sjzdService.getZdxmByZdId(zd_id);
		return "success";
	}
	
	/**
	 * 打开添加字典信息页面
	 * @return
	 */
	public String editZdxx(){
		if(!xm_id.equals("")){
			sjzdXmxx = sjzdService.getZdxm(xm_id);
		}else{
			sjzdXmxx.setZd_id(zd_id);
		}
		return "success";
	}
	
	/**
	 * 更新字典信息
	 * @return
	 */
	public String updateZdxx(){
		sjzdService.updateZdxm(sjzdXmxx);
		return "success";
	}
	
	/**
	 * 删除字典信息
	 * @return
	 */
	public String delZdxx(){
		sjzdService.delZdxm(xm_id);
		return "success";
	}
	
	public List getSjzdJbxxList() {
		return sjzdJbxxList;
	}
	public void setSjzdJbxxList(List sjzdJbxxList) {
		this.sjzdJbxxList = sjzdJbxxList;
	}
	public SjzdService getSjzdService() {
		return sjzdService;
	}
	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}
	public SjzdXmxx getSjzdXmxx() {
		return sjzdXmxx;
	}
	public void setSjzdXmxx(SjzdXmxx sjzdXmxx) {
		this.sjzdXmxx = sjzdXmxx;
	}
	public List getSjzdXmxxList() {
		return sjzdXmxxList;
	}
	public void setSjzdXmxxList(List sjzdXmxxList) {
		this.sjzdXmxxList = sjzdXmxxList;
	}
	public String getXm_id() {
		return xm_id;
	}
	public void setXm_id(String xm_id) {
		this.xm_id = xm_id;
	}
	public String getZd_id() {
		return zd_id;
	}
	public void setZd_id(String zd_id) {
		this.zd_id = zd_id;
	}

	public SjzdJbxx getSjzdJbxx() {
		return sjzdJbxx;
	}

	public void setSjzdJbxx(SjzdJbxx sjzdJbxx) {
		this.sjzdJbxx = sjzdJbxx;
	}

}
