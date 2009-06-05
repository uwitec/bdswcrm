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
	 * ��Ӳ�Ʒ���
	 * @return
	 */
	public String add(){
		return "success";
	}
	
	/**
	 * ȡ���������ַ���
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
	 * ȡ������Ϣ
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
	 * �����Ʒ��Ϣ
	 * @return
	 */
	public String save(){
		productKindService.saveProductKind(productKind);
		return "success";
	}
	
	
	/**
	 * ���²�Ʒ�����Ϣ
	 * @return
	 */
	public String update(){
		productKindService.updateProductKind(productKind);
		return "success";
	}
	
	
	/**
	 * ɾ����Ʒ������Ϣ
	 * @return
	 */
	public String del(){
		String id = StringUtils.nullToStr(getRequest().getParameter("id"));
		int i = 0;
		
		//�ж��Ƿ�������࣬���������Ĳ���ɾ��
		i = productKindService.getChildKindCount(id);
		if(i>0){
			getSession().setAttribute("MSG_KIND", "�����Ӳ�Ʒ��𣬲���ɾ����");
			return "success";
		}
		
		//�ж��Ƿ�����Ӳ�Ʒ�������Ӳ�Ʒ�Ĳ���ɾ��
		i = productKindService.getChildProductCount(id);
		if(i>0){
			getSession().setAttribute("MSG_KIND", "���ڹ�����Ʒ������ɾ����");
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
