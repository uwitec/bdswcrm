package com.sw.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sw.cms.service.InitParamService;

public class InitParamJob extends QuartzJobBean {
	
	private InitParamService initParamService;

	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		initParamService.updateParam();  //��ʼ������
		initParamService.genAccountQc(); //�����˻��ڳ�
		initParamService.genKcQc();      //���ɿ���ڳ�
		initParamService.genYsQc();      //���������ڳ�
		initParamService.delExpireMsg(); //ɾ��������Ϣ
	}

	public InitParamService getInitParamService() {
		return initParamService;
	}

	public void setInitParamService(InitParamService initParamService) {
		this.initParamService = initParamService;
	}
	

}
