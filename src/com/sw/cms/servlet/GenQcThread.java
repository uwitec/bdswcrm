package com.sw.cms.servlet;

import org.springframework.context.ApplicationContext;

import com.sw.cms.service.InitParamService;
import com.sw.cms.util.StaticParamDo;

public class GenQcThread implements Runnable {

	public void run() {
		ApplicationContext ctx = StaticParamDo.getCtx();
		InitParamService service = (InitParamService)ctx.getBean("initParamServiceNoTranc");
		
		//�������ɿͻ������ڳ�
//		System.out.println("�������ɿͻ������ڳ���ʼ��");
//		service.updateBatchGenClientWlQc("2011-03-01", "2011-03-03");
//		System.out.println("�������ɿͻ������ڳ�������");
//		
//		System.out.println("------------------------------------------");
			
		//���������˻��ڳ�
		//System.out.println("���������˻��ڳ���ʼ��");
		//service.updateBatchGenAccountQc("2009-09-02", "2010-01-05");
		//System.out.println("���������˻��ڳ�������");
		
		//���ɿ���ڳ�ֵ
//		service.insertKcQcNew();
		service.insertBatchProductKcQcXml("2013-04-20", "2013-04-21");
	}

}
