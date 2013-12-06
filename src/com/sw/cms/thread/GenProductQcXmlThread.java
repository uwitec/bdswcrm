package com.sw.cms.thread;

import org.springframework.context.ApplicationContext;

import com.sw.cms.service.ProductKcService;
import com.sw.cms.util.StaticParamDo;

/**
 * 完成系统初始化是执行生成系统期初功能，将期初信息打包生成XML文件存储到product_kc_qc_xml表中
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
