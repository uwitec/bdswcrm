package com.sw.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sw.cms.service.InitParamService;

public class InitParamJob extends QuartzJobBean {
	
	private InitParamService initParamService;

	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		initParamService.updateParam();  //��ʼ������
		initParamService.insertAccountQc(); //�����˻��ڳ�
		
		//�����µķ�ʽ���ɿ���ڳ�ֵ���ɵķ�ʽ��ʱ�ϳ�
		//initParamService.insertKcQc();      //���ɿ���ڳ�
		
		initParamService.insertKcQcNew();     //�����µĿ���ڳ�ֵ������XML��ʽ���棩
		initParamService.insertClientQc();      //���������ڳ�
		initParamService.delExpireMsg(); //ɾ��������Ϣ
		initParamService.updateShkcProductDay();//�����ۺ�����Ʒ�ڿ�����
		initParamService.updateEmploeeGl();   //�޸�Ա������
		
	}

	public InitParamService getInitParamService() {
		return initParamService;
	}

	public void setInitParamService(InitParamService initParamService) {
		this.initParamService = initParamService;
	}
	

}
