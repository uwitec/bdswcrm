package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork.ModelDriven;
import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.ProductService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.util.Constant;

public class ProductAction extends BaseAction implements ModelDriven{
	
	private ProductService productService;
	private ProductKindService productKindService;
	private SysInitSetService sysInitSetService;

	private Page productPage;  //带分页对象列表	
	private Product product = new Product();
	private List providerList;
	private SjzdService sjzdService;
	private String[] jldw;
	private List productKindList = new ArrayList();
	
	private List products = new ArrayList();
	
	//查询条件
	private String curId = "";
	private int curPage = 1;
	private int rowsPerPage = Constant.PAGE_SIZE2;
	private String product_name = "";
	private String product_kind = "";
	private String product_prop = "";
	private String state = "";
	private String product_xh = "";
	private String product_state = "正常";
	private String iscs_flag = "";  //系统是否初始完成标志
	private String product_id = "";
	private String sp_txm1 = "";
	
	public Page getProductPage() {
		return productPage;
	}

	
	/**
	 * 显示商品维护大页面，当前无操作
	 * 后期添加权限控制
	 * @return
	 */
	public String showProductMain(){
		return "success";
	}
	
	
	/**
	 * 取得商品列表
	 * @param curId
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public String getProductList(){
		String con = "";
		if(!curId.equals("")){
			con += " and product_kind='" + curId + "'";
		}
		if(!product_name.equals("")){
			con += " and product_name like '%" + product_name + "%'";
		}
		if(!product_xh.equals("")){
			con += " and product_xh like '%" + product_xh + "%'";
		}
		
		if(!product_id.equals("")){
			con += " and product_id like '%" + product_id + "%'";
		}
		
		if(!sp_txm1.equals("")){
			con += " and a.sp_txm like '%" + sp_txm1 + "%'";
		}
		
		if(!product_state.equals("")){
			con += " and state='" + product_state + "'";
		}
		productPage = productService.getProductList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 转到添加商品页面
	 * @return
	 */
	public String addProduct(){
		jldw = sjzdService.getSjzdXmxxByZdId("SJZD_JLDW");
		return "success";
	}
	
	/**
	 * 保存商品信息
	 * @return
	 */
	public String saveProduct()throws Exception {
		productService.saveProductInfo(product);		
		return "success";
	}
	
	/**
	 * 编辑或查看商品信息
	 * @return
	 */
	public String editProduct(){
		if(!product_id.equals("")){
			Object obj = productService.getProductById(product_id);
			if(obj != null){
				setProduct((Product)obj);
			}
		}

		jldw = sjzdService.getSjzdXmxxByZdId("SJZD_JLDW");
		productKindList = productKindService.getAllProductKindList();
		iscs_flag = sysInitSetService.getQyFlag();  //是否完成系统初始化
		
		
		return "success";
	}
	
	/**
	 * 更新商品信息
	 * @return
	 */
	public String updateProduct()throws Exception{
		productService.updateProductInfo(product);
		return "success";
	}	
	
	/**
	 * 删除商品信息
	 * @return
	 */
	public String delProduct(){
		productService.delProductById(product_id);		
		return "success";
	}	
	
	/**
	 * 判断商品是否能够被删除
	 */
	public void checkProductCanDel(){
		if(!productService.isCanDel(product_id)){
			this.writeStringToResponse("false");
		}else{
			this.writeStringToResponse("true");
		}
	}
	
	
	/**
	 * 批量修改商品信息，查询条件页面
	 * @return
	 */
	public String queryProductCon(){
		return SUCCESS;
	}
	
	
	/**
	 * 查询商品信息
	 * @return
	 */
	public String queryProduct(){
		try{
			String con = " and state='正常'";
			//处理商品类别
			if(!product_kind.equals("")){
				String[] arryItems = product_kind.split(",");
				
				if(arryItems != null && arryItems.length >0){
					con += " and (";
					for(int i=0;i<arryItems.length;i++){
						if(i == 0){
							con += " product_kind like '" + arryItems[i] + "%'";
						}else{
							con += " or product_kind like '" + arryItems[i] + "%'";
						}
					}
					con += ")";
				}
				
			}
			if(!product_name.equals("")){
				con += " and product_name like '%" + product_name + "%'";
			}
			if(!product_prop.equals("")){
				con += " and prop='" + product_prop + "'";
			}
			
			products = productService.getProductKcList(con);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("查询商品信息出错，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 批量更新商品信息
	 * @return
	 */
	public String batchUpdateProduct(){
		try{
			productService.updateProducts(products);
			return SUCCESS;
		}catch(Exception e){
			log.error("查询商品信息出错，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 检查产品类别下是否存在子类别
	 */
	public void checkProductKind(){
		int nums = productKindService.getChildKindCount(curId);
		if(nums > 0){
			this.writeStringToResponse("true");
		}else{
			this.writeStringToResponse("false");
		}
	}

	
	public String getCurId() {
		return curId;
	}

	public void setCurId(String curId) {
		this.curId = curId;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

    public Object getModel() {
    	return product;
    }

	public List getProviderList() {
		return providerList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setProductPage(Page productPage) {
		this.productPage = productPage;
	}

	public void setProviderList(List providerList) {
		this.providerList = providerList;
	}


	public void setProductService(ProductService productService) {
		this.productService = productService;
	}


	public ProductService getProductService() {
		return productService;
	}

	public String[] getJldw() {
		return jldw;
	}


	public void setJldw(String[] jldw) {
		this.jldw = jldw;
	}


	public SjzdService getSjzdService() {
		return sjzdService;
	}


	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}


	public String getProduct_name() {
		return product_name;
	}


	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}


	public String getProduct_xh() {
		return product_xh;
	}


	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

	public String getSp_txm1() {
		return sp_txm1;
	}
	public void setSp_txm1(String sp_txm1) {
		this.sp_txm1 = sp_txm1;
	}
	
	public String getProduct_state() {
		return product_state;
	}


	public void setProduct_state(String product_state) {
		this.product_state = product_state;
	}


	public List getProductKindList() {
		return productKindList;
	}


	public void setProductKindList(List productKindList) {
		this.productKindList = productKindList;
	}


	public ProductKindService getProductKindService() {
		return productKindService;
	}


	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}


	public String getIscs_flag() {
		return iscs_flag;
	}


	public void setIscs_flag(String iscs_flag) {
		this.iscs_flag = iscs_flag;
	}


	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}


	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}

	public String getProduct_kind() {
		return product_kind;
	}


	public void setProduct_kind(String productKind) {
		product_kind = productKind;
	}


	public List getProducts() {
		return products;
	}


	public void setProducts(List products) {
		this.products = products;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getProduct_prop() {
		return product_prop;
	}


	public void setProduct_prop(String productProp) {
		product_prop = productProp;
	}


	public String getProduct_id() {
		return product_id;
	}


	public void setProduct_id(String productId) {
		product_id = productId;
	}
}
