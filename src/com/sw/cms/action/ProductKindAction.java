package com.sw.cms.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork.ModelDriven;
import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Page;
import com.sw.cms.model.ProductKind;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.ProductService;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StringUtils;

public class ProductKindAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 2672446431658415798L;
	
	private ProductKindService productKindService;
	private ProductService productService;
	
	private ProductKind productKind = new ProductKind();
	private String strTree = "";
	
	private Page productPage = new Page();


	
	public String getStrTree() {
		return strTree;
	}
	
	
	/**
	 * 添加产品类别
	 * @return
	 */
	public String add(){
		return "success";
	}
	
	/**
	 * 取生成树的字符串
	 * @return
	 */
	public String list(){
				
		List results = productKindService.getAllProductKindList();
		
		Iterator it = results.iterator();
		
		while(it.hasNext()){
			Map map = (Map)it.next();
			
			String id = (String)map.get("id");
			String name = (String)map.get("name");
			String parent_id = (String)map.get("parent_id");
			
			strTree += "add_item(\"" + id + "\",\"" + parent_id + "\",\"" + name + "\",\"\",\"\",\"product_list.html\",\"right\");\n";
		}
		
		return "success";
		
	}

	
	/**
	 * 取分类信息
	 * @return
	 */
	public String edit(){
		String id = StringUtils.nullToStr(getRequest().getParameter("id"));
		Object obj = productKindService.getProductKindInfoById(id);
		if(obj != null){
			productKind = (ProductKind)obj;
		}
		return "success";
	}
	
	
	/**
	 * 保存产品信息
	 * @return
	 */
	public String save(){
		productKindService.saveProductKind(productKind);
		return "success";
	}
	
	
	/**
	 * 更新产品类别信息
	 * @return
	 */
	public String update(){
		productKindService.updateProductKind(productKind);
		return "success";
	}
	
	
	/**
	 * 删除产品分类信息
	 * @return
	 */
	public String del(){
		String id = StringUtils.nullToStr(getRequest().getParameter("id"));
		int i = 0;
		
		//判断是否存在子类，存在子类别的不能删除
		i = productKindService.getChildKindCount(id);
		if(i>0){
			getSession().setAttribute("MSG_KIND", "存在子产品类别，不能删除！");
			return "success";
		}
		
		//判断是否存在子产品，存在子产品的不能删除
		i = productKindService.getChildProductCount(id);
		if(i>0){
			getSession().setAttribute("MSG_KIND", "存在关联产品，不能删除！");
			return "success";
		}
		
		productKindService.delProductKind(id);
		return "success";
	}
	
	public String selectProduct(){
		List results = productKindService.getAllProductKindList();
		
		Iterator it = results.iterator();
		
		String product_kind_id = ParameterUtility.getStringParameter(getRequest(), "product_kind_id","");
		String openerId = ParameterUtility.getStringParameter(getRequest(), "openerId","");
		
		while(it.hasNext()){
			Map map = (Map)it.next();
			
			String id = (String)map.get("id");
			String name = (String)map.get("name");
			String parent_id = (String)map.get("parent_id");
			
			strTree += "add_item(\"" + id + "\",\"" + parent_id + "\",\"" + name + "\",\"\",\"\",\"selectProduct.html\",\"" + openerId + "\");\n";
		}
		
		int rowsPerPage = 10;


		int curPage = ParameterUtility.getIntParameter(getRequest(), "curPage",1);
		
		productPage = productService.getProductListByID(product_kind_id, curPage, rowsPerPage);
		
		return "success";
	}
	
    public Object getModel() {
    	return productKind;
    }

	public ProductKind getProductKind() {
		return productKind;
	}

	public void setProductKind(ProductKind productKind) {
		this.productKind = productKind;
	}

	public Page getProductPage() {
		return productPage;
	}


	public ProductKindService getProductKindService() {
		return productKindService;
	}


	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}


	public ProductService getProductService() {
		return productService;
	}


	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	
}
