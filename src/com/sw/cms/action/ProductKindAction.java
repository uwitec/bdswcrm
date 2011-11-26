package com.sw.cms.action;

import java.util.ArrayList;
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
	private List productKindList = new ArrayList();
	
	private String old_parent_id = "";
	private String kind_id = "";
	
	public String getKind_id() {
		return kind_id;
	}


	public void setKind_id(String kindId) {
		kind_id = kindId;
	}


	private Page productPage = new Page();


	
	public String getStrTree() {
		return strTree;
	}
	
	
	/**
	 * 添加商品类别
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
		
		productKindList = productKindService.getAllProductKindList();
		return "success";
	}
	
	
	/**
	 * 保存商品信息
	 * @return
	 */
	public String save(){
		productKindService.saveProductKind(productKind);
		return "success";
	}
	
	
	/**
	 * 更新商品类别信息
	 * @return
	 */
	public String update(){
		productKindService.updateProductKind(productKind,old_parent_id);
		return "success";
	}
	
	
	/**
	 * 删除商品分类信息
	 * @return
	 */
	public String del(){
		String id = StringUtils.nullToStr(getRequest().getParameter("id"));
		int i = 0;
		
		//判断是否存在子类，存在子类别的不能删除
		i = productKindService.getChildKindCount(id);
		if(i>0){
			getSession().setAttribute("MSG_KIND", "存在子商品类别，不能删除！");
			return "success";
		}
		
		//判断是否存在子商品，存在子商品的不能删除
		i = productKindService.getChildProductCount(id);
		if(i>0){
			getSession().setAttribute("MSG_KIND", "存在关联商品，不能删除！");
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
	
	
	public void getChildKindNums(){
		try{
			int nums = productKindService.getChildKindCount(kind_id);
			this.writeStringToResponse(nums+"");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 检查类别下是否存在商品
	 */
	public void checkSubProduct(){
		try{
			int nums = productKindService.getChildProductCount(kind_id);
			if(nums > 0){
				this.writeStringToResponse("true");
			}else{
				this.writeStringToResponse("false");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
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


	public List getProductKindList() {
		return productKindList;
	}


	public void setProductKindList(List productKindList) {
		this.productKindList = productKindList;
	}


	public void setStrTree(String strTree) {
		this.strTree = strTree;
	}


	public void setProductPage(Page productPage) {
		this.productPage = productPage;
	}


	public String getOld_parent_id() {
		return old_parent_id;
	}


	public void setOld_parent_id(String oldParentId) {
		old_parent_id = oldParentId;
	}

	
}
