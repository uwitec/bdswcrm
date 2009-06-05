package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.DlsDzdService;

public class DlsDzdAction extends BaseAction {
	
	private DlsDzdService dlsDzdService;
	
	
	/**
	 * �������б�ҳ��
	 * @return
	 */
	public String showCondition(){
		return "success";
	}
	
	/**
	 * ����ҳ��
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
