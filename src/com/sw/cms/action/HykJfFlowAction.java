package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.HykJfFlowService;

/**
 * 会员卡积分管理
 * @author liyt
 *
 */
public class HykJfFlowAction extends BaseAction {
	
	private HykJfFlowService hykJfFlowService;
	
	/**
	 * 积分查询条件页面
	 * @return
	 */
	public String showJfcxCondition(){
		return SUCCESS;
	}
	
	/**
	 * 积分查询结果
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
