package com.sw.cms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ModelDriven;
import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.service.ProductService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.StringUtils;

public class ProductAction extends BaseAction implements ModelDriven{
	
	private ProductService productService;

	private Page productPage;  //带分页对象列表	
	private Product product = new Product();
	private List providerList;
	private SjzdService sjzdService;
	private String[] jldw;
	
	private File imgFile ;
	private String imgFileFileName;
	
	//查询条件
	private String curId = "";
	private int curPage = 1;
	private int rowsPerPage = Constant.PAGE_SIZE2;
	private String product_name = "";
	private String product_xh = "";


	public Page getProductPage() {
		return productPage;
	}

	
	/**
	 * 显示产品维护大页面，当前无操作
	 * 后期添加权限控制
	 * @return
	 */
	public String showProductMain(){
		return "success";
	}
	
	
	/**
	 * 取得产品列表
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
		productPage = productService.getProductList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 转到添加产品页面
	 * @return
	 */
	public String addProduct(){
		jldw = sjzdService.getSjzdXmxxByZdId("SJZD_JLDW");
		return "success";
	}
	
	/**
	 * 保存产品信息
	 * @return
	 */
	public String saveProduct()throws Exception {
		
		
		if(imgFileFileName != null && !imgFileFileName.equals("")){
			//获取当前的绝对路径
			String filePath = ServletActionContext.getServletContext().getRealPath("\\");			
			int i = imgFileFileName.lastIndexOf(".");
			String pix = imgFileFileName.substring(i,imgFileFileName.length());
			
			Calendar cl = Calendar.getInstance();
			String saveFileName = cl.getTimeInMillis() + pix;  //数据库中保存的文件名
			String imgPath = filePath + "upload\\" + saveFileName;
			
			//上传产品图片文件
			FileOutputStream outputStream = new FileOutputStream(imgPath);
			FileInputStream fileIn = new FileInputStream(imgFile);
			byte[] buffer = new byte[1024];
	
			int len;
			while ((len = fileIn.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			fileIn.close();		
			
			product.setImg(saveFileName);
		}
		
		productService.saveProductInfo(product);
		
		return "success";
	}
	
	/**
	 * 编辑或查看产品信息
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
		return "success";
	}
	
	/**
	 * 更新产品信息
	 * @return
	 */
	public String updateProduct()throws Exception{
		
				
		if(imgFileFileName != null && !imgFileFileName.equals("")){
			//获取当前的绝对路径
			String filePath = ServletActionContext.getServletContext().getRealPath("\\");
			
			int i = imgFileFileName.lastIndexOf(".");
			String pix = imgFileFileName.substring(i,imgFileFileName.length());
			
			Calendar cl = Calendar.getInstance();
			String saveFileName = cl.getTimeInMillis() + pix;  //数据库中保存的文件名
			String imgPath = filePath + "upload\\" + saveFileName;
			
			//上传产品图片文件
			FileOutputStream outputStream = new FileOutputStream(imgPath);
			FileInputStream fileIn = new FileInputStream(imgFile);
			byte[] buffer = new byte[1024];
	
			int len;
			while ((len = fileIn.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			fileIn.close();		
			
			product.setImg(saveFileName);
		}	
		
		setImgFileFileName("");
		
		productService.updateProductInfo(product);
		return "success";
	}	
	
	/**
	 * 编辑或查看产品信息
	 * @return
	 */
	public String delProduct(){
		
		String productId = StringUtils.nullToStr(getRequest().getParameter("productId"));		
		productService.delProductById(productId);
		
		return "success";
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

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
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


	public File getImgFile() {
		return imgFile;
	}


	public String getImgFileFileName() {
		return imgFileFileName;
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


	
}
