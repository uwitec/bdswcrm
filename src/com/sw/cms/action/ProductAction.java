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
import com.sw.cms.util.StringUtils;

public class ProductAction extends BaseAction implements ModelDriven{
	
	private ProductService productService;
	private ProductKindService productKindService;
	private SysInitSetService sysInitSetService;

	private Page productPage;  //����ҳ�����б�	
	private Product product = new Product();
	private List providerList;
	private SjzdService sjzdService;
	private String[] jldw;
	private List productKindList = new ArrayList();
	
	private List products = new ArrayList();
	
	//��ѯ����
	private String curId = "";
	private int curPage = 1;
	private int rowsPerPage = Constant.PAGE_SIZE2;
	private String product_name = "";
	private String product_kind = "";
	private String prop = "";
	private String state = "";
	private String product_xh = "";
	private String product_state = "����";
	private String iscs_flag = "";  //ϵͳ�Ƿ��ʼ��ɱ�־

	public Page getProductPage() {
		return productPage;
	}

	
	/**
	 * ��ʾ��Ʒά����ҳ�棬��ǰ�޲���
	 * �������Ȩ�޿���
	 * @return
	 */
	public String showProductMain(){
		return "success";
	}
	
	
	/**
	 * ȡ�ò�Ʒ�б�
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
		if(!product_state.equals("")){
			con += " and state='" + product_state + "'";
		}
		productPage = productService.getProductList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * ת����Ӳ�Ʒҳ��
	 * @return
	 */
	public String addProduct(){
		jldw = sjzdService.getSjzdXmxxByZdId("SJZD_JLDW");
		return "success";
	}
	
	/**
	 * �����Ʒ��Ϣ
	 * @return
	 */
	public String saveProduct()throws Exception {
		productService.saveProductInfo(product);		
		return "success";
	}
	
	/**
	 * �༭��鿴��Ʒ��Ϣ
	 * @return
	 */
	public String editProduct(){
		
		String productId = StringUtils.nullToStr(getRequest().getParameter("productId"));
		
		if(!productId.equals("")){
			Object obj = productService.getProductById(productId);
			if(obj != null){
				setProduct((Product)obj);
			}
		}

		jldw = sjzdService.getSjzdXmxxByZdId("SJZD_JLDW");
		productKindList = productKindService.getAllProductKindList();
		iscs_flag = sysInitSetService.getQyFlag();  //�Ƿ����ϵͳ��ʼ��
		
		
		return "success";
	}
	
	/**
	 * ���²�Ʒ��Ϣ
	 * @return
	 */
	public String updateProduct()throws Exception{
		productService.updateProductInfo(product);
		return "success";
	}	
	
	/**
	 * ɾ����Ʒ��Ϣ
	 * @return
	 */
	public String delProduct(){
		
		String productId = StringUtils.nullToStr(getRequest().getParameter("productId"));	
		
		if(!productService.isCanDel(productId)){
			this.setMsg("����Ʒ�ѷ���ҵ������������ɾ����");
			return "notDel";
		}
		productService.delProductById(productId);
		return "success";
	}	
	
	
	/**
	 * �����޸���Ʒ��Ϣ����ѯ����ҳ��
	 * @return
	 */
	public String queryProductCon(){
		return SUCCESS;
	}
	
	
	/**
	 * ��ѯ��Ʒ��Ϣ
	 * @return
	 */
	public String queryProduct(){
		try{
			String con = "";
			//������Ʒ���
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
			if(!prop.equals("")){
				con += " and prop='" + prop + "'";
			}
			if(!state.equals("")){
				con += " and state='" + state + "'";
			}
			
			products = productService.getProductKcList(con);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("��ѯ��Ʒ��Ϣ����ԭ��" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * ����������Ʒ��Ϣ
	 * @return
	 */
	public String batchUpdateProduct(){
		try{
			productService.updateProducts(products);
			return SUCCESS;
		}catch(Exception e){
			log.error("��ѯ��Ʒ��Ϣ����ԭ��" + e.getMessage());
			e.printStackTrace();
			return ERROR;
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


	public String getProp() {
		return prop;
	}


	public void setProp(String prop) {
		this.prop = prop;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}
	
}
