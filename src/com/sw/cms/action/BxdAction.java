package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.BxdService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.EmployeeService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StringUtils;

public class BxdAction extends BaseAction {
	private SjzdService sjzdService;

	private BxdService bxdService;

	private UserService userService;

	private ClientsService clientsService;

	private EmployeeService employeeService;

	private DeptService deptService;

	private ProductKcService productKcService;

	private Page ywyEmployeePage;

	private Page bxdPage;

	private Page productPage;

	private String[] wxszd;

	private String[] positions;

	private List userList = new ArrayList();

	private List clientsList = new ArrayList();

	private List depts = new ArrayList();

	private Bxd bxd = new Bxd();

	private BxdProduct bxdProduct = new BxdProduct();

	private String[] fj = new String[7];

	private String product_serial_num = "";

	private String lxr = "";

	private String state = "";

	private String gcs = "";

	private String jx_date1 = DateComFunc.getToday();

	private String jx_date2 = DateComFunc.getToday();

	private String orderName = "";

	private String orderType = "";

	private int curPage = 1;

	private String dept;

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * 报修单列表（带分页）
	 * 
	 * @return
	 */
	public String list() {
		try {
			int rowsPerPage = Constant.PAGE_SIZE2;
			String con = "";
			if (!lxr.trim().equals("")) {
				con = " and b.lxr='" + lxr + "'";
			}
			if (!product_serial_num.trim().equals("")) {
				con += " and p.product_serial_num='" + product_serial_num + "'";
			}
			if (!jx_date1.trim().equals("")) {
				con += " and b.jxdate>='" + jx_date1 + "'";
			}
			if (!jx_date2.trim().equals("")) {
				con += " and b.jxdate<='" + (jx_date2 + " 23:59:59") + "'";
			}
			if (!state.equals("")) {
				con += " and b.state='" + state + "'";
			}
			if (!gcs.trim().equals("")) {
				con += " and s.real_name like '%" + gcs + "%'";
			}
			if (orderName.equals("")) {
				orderName = "id";
			}
			if (orderType.equals("")) {
				orderType = "desc";
			}
			con += " order by " + orderName + " " + orderType + "";
			bxdPage = bxdService.getBxdList(con, curPage, rowsPerPage);
			return "success";
		} catch (Exception e) {
			log.error("返回报修单列表  错误原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 选择工程师
	 * 
	 * @return
	 */
	public String selGcs() {
		try {
			int curPage = ParameterUtility.getIntParameter(getRequest(),
					"curPage", 1);
			String ywyname = ParameterUtility.getStringParameter(getRequest(),
					"name", "");

			String ywyposition = ParameterUtility.getStringParameter(
					getRequest(), "position", "");
			String flog = ParameterUtility.getStringParameter(getRequest(),
					"flog", "");
			if (flog.equals("")) {
				List deptList = deptService.getDeptsByName("客户服务部");
				if (deptList != null && deptList.size() != 0) {
					Map map = (Map) deptList.get(0);
					dept = StringUtils.nullToStr((String) map.get("dept_id"));

				}

			}

			int rowsPerPage = 15;
			String con = "";
			if (!ywyname.equals("")) {
				con += " and a.real_name like '%" + ywyname + "%'";
			}
			if (!dept.equals("")) {
				con += " and b.dept_id='" + dept + "'";
			}
			if (!ywyposition.equals("")) {
				con += " and a.position='" + ywyposition + "'";
			}
			ywyEmployeePage = employeeService.getYwyEmployee(con, curPage,
					rowsPerPage);
			positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
			depts = deptService.getDepts();
			return "success";
		} catch (Exception e) {
			log.error("获取业务员列表  失败原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 打开选择库存产品列表
	 * 
	 * @return
	 */
	public String selKcProc() {
		try {
			String product_xh = ParameterUtility.getStringParameter(
					getRequest(), "product_xh", "");
			String product_name = ParameterUtility.getStringParameter(
					getRequest(), "product_name", "");
			String prop = ParameterUtility.getStringParameter(getRequest(),
					"prop", "");

			int rowsPerPage = 15;

			String con = "";
			if (!product_xh.equals("")) {
				con += " and product_xh like '%" + product_xh + "%'";
			}
			if (!product_name.equals("")) {
				con += " and product_name like '%" + product_name + "%'";
			}
			if (!prop.equals("")) {
				con += " and prop='" + prop + "'";
			}

			productPage = productKcService.getProductKcList(con, curPage,
					rowsPerPage);

			return "success";
		} catch (Exception e) {
			log.error("获取库存产品列表 错误原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 加载报修单页面
	 * 
	 * @return
	 */
	public String add() {
		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
		userList = userService.getAllEmployeeList();
		bxd.setId(bxdService.updateBxdId());
		clientsList = clientsService.getClientList("");
		return "success";
	}

	/**
	 * 保存报修单
	 * 
	 * @return
	 */
	public String save() {
		try {
			LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			bxd.setCjr(user_id);
			if (!bxd.getState().equals("") && bxd.getState().equals("报修完")) {
				bxd.setJddate(DateComFunc.getToday());
			}

			String str = "";
			for (int i = 0; i < fj.length; i++) {
				str += fj[i] + ",";
			}
			bxdProduct.setFj(str);

			bxdService.insertBxd(bxd, bxdProduct);
			return "success";
		} catch (Exception e) {
			log.error("保存报修单  失败原因" + e.getMessage());
			return "error";
		}
	}

	/**
	 * 修改报修单前加载报修单
	 * 
	 * @return
	 */
	public String edit() {
		try {
			wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
			userList = userService.getAllEmployeeList();
			clientsList = clientsService.getClientList("");
			String bxd_id = ParameterUtility.getStringParameter(getRequest(),
					"id", "");
			bxd = (Bxd) bxdService.getBxd(bxd_id);
			bxdProduct = (BxdProduct) bxdService.getBxdProduct(bxd_id);

			return "success";
		} catch (Exception e) {
			log.error("加载修改报修单 失败原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 修改报修单
	 * 
	 * @return
	 */
	public String update() {
		try {

			if (!bxd.getState().equals("") && bxd.getState().equals("报修完")) {
				bxd.setJddate(DateComFunc.getToday());
			}

			String str = "";
			for (int i = 0; i < fj.length; i++) {
				str += fj[i] + ",";
			}
			bxdProduct.setFj(str);

			bxdService.updateBxd(bxd, bxdProduct);
			return "success";
		} catch (Exception e) {
			log.error("保存更改报修单  失败原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 删除报修单
	 * 
	 * @return
	 */
	public String del() {
		try {
			String bxd_id = ParameterUtility.getStringParameter(getRequest(),
					"id", "");
			bxdService.delBxd(bxd_id);
			return "success";
		} catch (Exception e) {
			log.error("删除报修单  失败原因" + e.getMessage());
			return "error";
		}
	}

	public String getJx_date1() {
		return jx_date1;
	}

	public void setJx_date1(String jx_date1) {
		this.jx_date1 = jx_date1;
	}

	public String getJx_date2() {
		return jx_date2;
	}

	public void setJx_date2(String jx_date2) {
		this.jx_date2 = jx_date2;
	}

	public String getGcs() {
		return gcs;
	}

	public void setGcs(String gcs) {
		this.gcs = gcs;
	}

	public String getLxr() {
		return lxr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public String getProduct_serial_num() {
		return product_serial_num;
	}

	public void setProduct_serial_num(String product_serial_num) {
		this.product_serial_num = product_serial_num;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Page getBxdPage() {
		return bxdPage;
	}

	public void setBxdPage(Page bxdPage) {
		this.bxdPage = bxdPage;
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

	public BxdService getBxdService() {
		return bxdService;
	}

	public void setBxdService(BxdService bxdService) {
		this.bxdService = bxdService;
	}

	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String[] getWxszd() {
		return wxszd;
	}

	public void setWxszd(String[] wxszd) {
		this.wxszd = wxszd;
	}

	public ClientsService getClientsService() {
		return clientsService;
	}

	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public Bxd getBxd() {
		return bxd;
	}

	public void setBxd(Bxd bxd) {
		this.bxd = bxd;
	}

	public BxdProduct getBxdProduct() {
		return bxdProduct;
	}

	public void setBxdProduct(BxdProduct bxdProduct) {
		this.bxdProduct = bxdProduct;
	}

	public List getClientsList() {
		return clientsList;
	}

	public void setClientsList(List clientsList) {
		this.clientsList = clientsList;
	}

	public Page getYwyEmployeePage() {
		return ywyEmployeePage;
	}

	public void setYwyEmployeePage(Page ywyEmployeePage) {
		this.ywyEmployeePage = ywyEmployeePage;
	}

	public String[] getPositions() {
		return positions;
	}

	public void setPositions(String[] positions) {
		this.positions = positions;
	}

	public List getDepts() {
		return depts;
	}

	public void setDepts(List depts) {
		this.depts = depts;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public ProductKcService getProductKcService() {
		return productKcService;
	}

	public void setProductKcService(ProductKcService productKcService) {
		this.productKcService = productKcService;
	}

	public Page getProductPage() {
		return productPage;
	}

	public void setProductPage(Page productPage) {
		this.productPage = productPage;
	}

	public String[] getFj() {
		return fj;
	}

	public void setFj(String[] fj) {
		this.fj = fj;
	}

}
