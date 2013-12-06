package com.sw.cms.thread;

import org.springframework.context.ApplicationContext;

import com.sw.cms.service.ProductKcService;
import com.sw.cms.util.StaticParamDo;

/**
 * ���ϵͳ��ʼ����ִ������ϵͳ�ڳ����ܣ����ڳ���Ϣ�������XML�ļ��洢��product_kc_qc_xml����
 * @author liyt
 *
 */
public class GenProductQcXmlThread implements Runnable {


	public void run() {
		
		ApplicationContext ctx = StaticParamDo.getCtx();
		ProductKcService service = (ProductKcService)ctx.getBean("productKcService");

		service.saveProductKcQc();
	}

}
