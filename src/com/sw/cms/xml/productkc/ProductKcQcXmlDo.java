package com.sw.cms.xml.productkc;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

/**
 * 库存期初XML操作
 * @author liyt
 *
 */
public class ProductKcQcXmlDo {
	
	//解析XML文件
	public ProductKcQc fromXml(String xmlString) throws Exception {
		ProductKcQc productKcQc = null;
		
		XStream xstream = new XStream();
		xstream.alias("ProductKc", ProductKcQc.class);
        xstream.alias("Product", ProductKc.class);
        
        productKcQc = (ProductKcQc)xstream.fromXML(xmlString);
		
		return productKcQc;
	}
	
	
	//生成XML文件
	public String toXml(ProductKcQc productKcQc) throws Exception {
		
		XStream xstream = new XStream();
		
		xstream.alias("ProductKc", ProductKcQc.class);
        xstream.alias("Product", ProductKc.class);

		
		return xstream.toXML(productKcQc);
		
	}
	
	
	public static void main(String[] args) {
		try{
			
			ProductKcQc productKcQc = new ProductKcQc();
			productKcQc.setCdate("2013-04-21");
			productKcQc.setStoreId("00001");
			
			List productList = new ArrayList();
			
			ProductKc productKc = new ProductKc();
			productKc.setProductId("0001");
			productKc.setNums(2);
			productKc.setPrice(new Double(4000));
			productList.add(productKc);
			
			productKc = new ProductKc();
			productKc.setProductId("0002");
			productKc.setNums(5);
			productKc.setPrice(new Double(4200));
			productList.add(productKc);
			
			productKcQc.setProducts(productList);
			
			ProductKcQcXmlDo xmlDo = new ProductKcQcXmlDo();
			System.out.println(xmlDo.toXml(productKcQc));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
