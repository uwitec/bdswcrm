package com.sw.cms.servlet;

import org.springframework.context.ApplicationContext;

import com.sw.cms.service.InitParamService;
import com.sw.cms.util.StaticParamDo;

public class GenQcThread implements Runnable {

	public void run() {
		ApplicationContext ctx = StaticParamDo.getCtx();
		InitParamService service = (InitParamService)ctx.getBean("initParamServiceNoTranc");
		
		//批量生成客户往来期初
//		System.out.println("批量生成客户往来期初开始！");
//		service.updateBatchGenClientWlQc("2011-03-01", "2011-03-03");
//		System.out.println("批量生成客户往来期初结束！");
//		
//		System.out.println("------------------------------------------");
			
		//批量生成账户期初
		//System.out.println("批量生成账户期初开始！");
		//service.updateBatchGenAccountQc("2009-09-02", "2010-01-05");
		//System.out.println("批量生成账户期初结束！");
		
		//生成库存期初值
//		service.insertKcQcNew();
		service.insertBatchProductKcQcXml("2013-04-20", "2013-04-21");
	}

}
