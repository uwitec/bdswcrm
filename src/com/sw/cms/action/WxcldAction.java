package com.sw.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pgd;
import com.sw.cms.model.Wxcld;
import com.sw.cms.model.WxcldProduct;
import com.sw.cms.service.PgdService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.WxcldService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class WxcldAction extends BaseAction {
	private WxcldService wxcldService;
	private SjzdService sjzdService;
	private ProductKcService productKcService;
	private ProductKindService productKindService;

	private Page wxcldPage;
	private Page productPage;
	private String orderName = "";
	private String orderType = "";
	private int curPage = 1;
	private String w_cj_date1 = "";
	private String w_cj_date2 = "";
	private String w_linkman = "";
	private String w_wx_state = "待处理";
	private String w_state = "";
	private String product_serial_num = "";

	private List kindList = new ArrayList();

	private String product_name = "";
	private String product_kind = "";

	private String[] wxlx;

	private Wxcld wxcld = new Wxcld();;
	private WxcldProduct wxcldProduct = new WxcldProduct();
	private Map wxclds;
	private Map wxcldProducts;

	/**
	 * 售后服务单列表
	 * 
	 * @return
	 */
	public String list() {
		try {
			int rowsPerPage = Constant.PAGE_SIZE;
			String con = "";
			if (!w_linkman.trim().equals("")) {
				con += " and w_linkman like '%" + w_linkman + "%'";
			}
			if (!product_serial_num.trim().equals("")) {
				con += " and product_serial_num='" + product_serial_num + "'";
			}
			if (!w_cj_date1.trim().equals("")) {
				con += " and w_cj_date>='" + w_cj_date1 + "'";
			}
			if (!w_cj_date2.trim().equals("")) {
				con += " and w_cj_date<='" + (w_cj_date2 + " 23:59:59") + "'";
			}
			if (!w_state.equals("")) {
				con += " and w_state='" + w_state + "'";
			}
			if (!w_wx_state.trim().equals("")) {
				con += " and w_wx_state='" + w_wx_state + "'";
			}
			if (orderName.equals("")) {
				orderName = "w_id";
			}
			if (orderType.equals("")) {
				orderType = "desc";
			}
			con += " order by " + orderName + " " + orderType + "";
			wxcldPage = wxcldService.getWxcldList(con, curPage, rowsPerPage);
			return "success";
		} catch (Exception e) {
			log.error("获取维修处理单列表   失败原因:" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 修改售后服务单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		try {
			String sfd_id = ParameterUtility.getStringParameter(getRequest(),
					"id", "");
			String wxclid = ParameterUtility.getStringParameter(getRequest(),
					"wxcld_id", "");

			wxclds = (Map) wxcldService.getWxcldBySfdPgdId(sfd_id);
			wxcldProducts = (Map) wxcldService.getWxcldProduct(wxclid);
			wxlx = sjzdService.getSjzdXmxxByZdId("SJZD_GZLX");
			return "success";
		} catch (Exception e) {
			log.error("打开修改派工单页面  错误原因" + e.getMessage());
			return "error";
		}
	}

	/**
	 * 保存修改派工服务单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		try {
			LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			wxcld.setW_cjr(user_id);
			if (wxcld.getW_state().equals("已保存")) {
				wxcld.setW_wx_state("待处理");
				wxcldService.updateWxcld(wxcld, wxcldProduct);
			}
			if (wxcld.getW_state().equals("已提交")) {

				wxcld.setW_wx_state("已处理");
				wxcld.setW_jd_date(DateComFunc.getToday());
				wxcldService.updateWxcld(wxcld, wxcldProduct);
			}

			return "success";
		} catch (Exception e) {
			log.error("保存维修处理单  错误原因" + e.getMessage());
			return "error";
		}
	}

	/**
	 * 选择库存商品
	 * 
	 * @return
	 */
	public String selWxProduct() throws Exception {
		try {
			int rowsPerPage = 15;

			String con = "";
			if (!product_name.equals("")) {
				con += " and (a.product_name like '%" + product_name
						+ "%' or a.product_xh like '%" + product_name + "%')";
			}
			if (!product_kind.equals("")) {
				con += " and a.product_kind like '" + product_kind + "%'";
			}
			productPage = productKcService.getProductKcList(con, curPage,
					rowsPerPage);
			kindList = productKindService.getAllProductKindList();

			return "success";
		} catch (Exception e) {
			log.error("维修处理单选择库存商品 失败原因：" + e.getMessage());
			return "error";
		}
	}

	/**
	 * 单击查看明细
	 * 
	 * @return
	 * @throws Exception
	 */
	public String desc() throws Exception {
		try {
			String id = ParameterUtility.getStringParameter(getRequest(), "id",
					"");
			wxcldProducts = (Map) wxcldService.getWxcldProduct(id);
			return "success";
		} catch (Exception e) {
			log.error("单击返还客户单查看报修返还信息 错误原因:" + e.getMessage());
			return "error";
		}
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getProduct_serial_num() {
		return product_serial_num;
	}

	public void setProduct_serial_num(String product_serial_num) {
		this.product_serial_num = product_serial_num;
	}

	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}

	public String getW_cj_date1() {
		return w_cj_date1;
	}

	public void setW_cj_date1(String w_cj_date1) {
		this.w_cj_date1 = w_cj_date1;
	}

	public String getW_cj_date2() {
		return w_cj_date2;
	}

	public void setW_cj_date2(String w_cj_date2) {
		this.w_cj_date2 = w_cj_date2;
	}

	public String getW_linkman() {
		return w_linkman;
	}

	public void setW_linkman(String w_linkman) {
		this.w_linkman = w_linkman;
	}

	public String getW_state() {
		return w_state;
	}

	public void setW_state(String w_state) {
		this.w_state = w_state;
	}

	public String getW_wx_state() {
		return w_wx_state;
	}

	public void setW_wx_state(String w_wx_state) {
		this.w_wx_state = w_wx_state;
	}

	public Wxcld getWxcld() {
		return wxcld;
	}

	public void setWxcld(Wxcld wxcld) {
		this.wxcld = wxcld;
	}

	public Page getWxcldPage() {
		return wxcldPage;
	}

	public void setWxcldPage(Page wxcldPage) {
		this.wxcldPage = wxcldPage;
	}

	public WxcldProduct getWxcldProduct() {
		return wxcldProduct;
	}

	public void setWxcldProduct(WxcldProduct wxcldProduct) {
		this.wxcldProduct = wxcldProduct;
	}

	public Map getWxcldProducts() {
		return wxcldProducts;
	}

	public void setWxcldProducts(Map wxcldProducts) {
		this.wxcldProducts = wxcldProducts;
	}

	public Map getWxclds() {
		return wxclds;
	}

	public void setWxclds(Map wxclds) {
		this.wxclds = wxclds;
	}

	public WxcldService getWxcldService() {
		return wxcldService;
	}

	public void setWxcldService(WxcldService wxcldService) {
		this.wxcldService = wxcldService;
	}

	public String[] getWxlx() {
		return wxlx;
	}

	public void setWxlx(String[] wxlx) {
		this.wxlx = wxlx;
	}

	public List getKindList() {
		return kindList;
	}

	public void setKindList(List kindList) {
		this.kindList = kindList;
	}

	public String getProduct_kind() {
		return product_kind;
	}

	public void setProduct_kind(String product_kind) {
		this.product_kind = product_kind;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public ProductKcService getProductKcService() {
		return productKcService;
	}

	public void setProductKcService(ProductKcService productKcService) {
		this.productKcService = productKcService;
	}

	public ProductKindService getProductKindService() {
		return productKindService;
	}

	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}

	public Page getProductPage() {
		return productPage;
	}

	public void setProductPage(Page productPage) {
		this.productPage = productPage;
	}

}
