package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.HykJfFlowService;

/**
 * ��Ա�����ֹ���
 * @author liyt
 *
 */
public class HykJfFlowAction extends BaseAction {
	
	private HykJfFlowService hykJfFlowService;
	
	/**
	 * ���ֲ�ѯ����ҳ��
	 * @return
	 */
	public String showJfcxCondition(){
		return SUCCESS;
	}
	
	/**
	 * ���ֲ�ѯ���
	 * @return
	 */
	public String getJfcxResult(){
		return SUCCESS;
	}

	public HykJfFlowService getHykJfFlowService() {
		return hykJfFlowService;
	}

	public void setHykJfFlowService(HykJfFlowService hykJfFlowService) {
		this.hykJfFlowService = hykJfFlowService;
	}

}
