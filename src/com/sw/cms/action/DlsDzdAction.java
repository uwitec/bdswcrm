package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.DlsDzdService;

public class DlsDzdAction extends BaseAction {
	
	private DlsDzdService dlsDzdService;
	
	
	/**
	 * 打开条件列表页面
	 * @return
	 */
	public String showCondition(){
		return "success";
	}
	
	/**
	 * 汇总页面
	 * @return
	 */
	public String getResult(){
		return "success";
	}
	

	public DlsDzdService getDlsDzdService() {
		return dlsDzdService;
	}

	public void setDlsDzdService(DlsDzdService dlsDzdService) {
		this.dlsDzdService = dlsDzdService;
	}

}
