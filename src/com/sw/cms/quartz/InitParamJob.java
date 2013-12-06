package com.sw.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sw.cms.service.InitParamService;

public class InitParamJob extends QuartzJobBean {
	
	private InitParamService initParamService;

	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		initParamService.updateParam();  //初始化参数
		initParamService.insertAccountQc(); //生成账户期初
		
		//采用新的方式生成库存期初值，旧的方式暂时废除
		//initParamService.insertKcQc();      //生成库存期初
		
		initParamService.insertKcQcNew();     //生成新的库存期初值（采用XML方式保存）
		initParamService.insertClientQc();      //生成往来期初
		initParamService.delExpireMsg(); //删除过期消息
		initParamService.updateShkcProductDay();//更改售后库存商品在库天数
		initParamService.updateEmploeeGl();   //修改员工工龄
		
	}

	public InitParamService getInitParamService() {
		return initParamService;
	}

	public void setInitParamService(InitParamService initParamService) {
		this.initParamService = initParamService;
	}
	

}
