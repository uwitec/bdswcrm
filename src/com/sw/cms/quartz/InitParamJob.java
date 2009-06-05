package com.sw.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sw.cms.service.InitParamService;

public class InitParamJob extends QuartzJobBean {
	
	private InitParamService initParamService;

	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		initParamService.updateParam();  //初始化参数
		initParamService.genAccountQc(); //生成账户期初
		initParamService.genKcQc();      //生成库存期初
		initParamService.genYsQc();      //生成往来期初
		initParamService.delExpireMsg(); //删除过期消息
	}

	public InitParamService getInitParamService() {
		return initParamService;
	}

	public void setInitParamService(InitParamService initParamService) {
		this.initParamService = initParamService;
	}
	

}
