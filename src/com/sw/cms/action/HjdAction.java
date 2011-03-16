
package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Hjd;
import com.sw.cms.model.HjdProduct;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.HjdService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.EmployeeService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.ShkcService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StringUtils;

public class HjdAction extends BaseAction 
{
	private SjzdService sjzdService;
	private HjdService hjdService;
	private UserService userService;
	private ClientsService clientsService;
	private EmployeeService employeeService;
	private DeptService deptService;
	private ProductKcService productKcService;
	private ShkcService      shkcService;
	private ProductKindService productKindService;
	
	private Page ywyEmployeePage;
	private Page hjdPage;
	private Page productPage;
	private Page shkcPage;

	private String[] wxszd;
	private String[] positions;

	private List userList = new ArrayList();
	private List clientsList = new ArrayList();
	private List depts = new ArrayList();
	private List hjdProducts = new ArrayList();
	
	private Hjd hjd = new Hjd();
	private HjdProduct hjdProduct = new HjdProduct();

	private String[] fj = new String[7];
	private String product_serial_num = "";
	
	private String state = "";
	private String jsr = "";
	private String hj_date1 = "";
	private String hj_date2 = "";
	private String orderName = "";
	private String orderType = "";
	private String id = "";
	private int curPage = 1;
	private String dept;
	
	private String product_name = "";
	private String product_kind = "";
	private List kindList = new ArrayList();
	
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * 换件单列表（带分页）
	 * 
	 * @return
	 */
	public String list()throws Exception
	{
		try {
			int rowsPerPage = Constant.PAGE_SIZE2;
			String con = "";
			
			if (!hj_date1.trim().equals("")) {
				con += " and b.hj_date>='" + hj_date1 + "'";
			}
			if (!hj_date2.trim().equals("")) {
				con += " and b.hj_date<='" + (hj_date2 + " 23:59:59") + "'";
			}
			if (!state.equals("")) {
				con += " and b.state='" + state + "'";
			}
			if (orderName.equals("")) {
				orderName = "id";
			}
			if (orderType.equals("")) {
				orderType = "desc";
			}
			con += " order by " + orderName + " " + orderType + "";
			hjdPage = hjdService.getHjdList(con, curPage, rowsPerPage);
			return "success";
		} catch (Exception e) {
			log.error("返回换件单列表  错误原因" + e.getMessage());
			return "error";
		}

	}

	
	/**
	 * 打开选择库存商品列表
	 * 
	 * @return
	 */
	public String selKcProc()throws Exception
	{
		try {
			int rowsPerPage = 15;
				
			String con = "";
			if(!product_name.equals("")){
				con += " and (a.product_name like '%" + product_name + "%' or a.product_xh like '%" + product_name + "%')";
			}	
			shkcPage = shkcService.getShkcIsHaoProduct(con, curPage, rowsPerPage);	
			return "success";
		} catch (Exception e) {
			log.error("获取好件库列表 错误原因:" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 加载换件单页面
	 * 
	 * @return
	 */
	public String add() {
		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
		userList = userService.getAllEmployeeList();
		hjd.setId(hjdService.updateHjdId());
		clientsList = clientsService.getClientList("");
		return "success";
	}

	/**
	 * 保存换件单
	 * 
	 * @return
	 */
	public String save() throws Exception
	{
		try {
			LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			hjd.setCjr(user_id);
						
            if(hjd.getState().equals("已提交"))
            {
            	//判断提交的报修商品是否在好件库里            	
            	if(hjdService.isHaoShkcExist(hjd, hjdProducts))
            	{
            		hjd.setState("已保存");
            		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
            		return "input";
            	} 
            	//保存信息
            	hjdService.saveHjd(hjd, hjdProducts);
            }
            if(hjd.getState().equals("已保存"))
            {
            	hjdService.saveHjd(hjd, hjdProducts);
            }
			return "success";
		} catch (Exception e) {
			log.error("保存换件单  失败原因" + e.getMessage());
			return "error";
		}
	}

	/**
	 * 修改换件单前加载换件单
	 * 
	 * @return
	 */
	public String edit()throws Exception
	{
		try {
			wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
			userList = userService.getAllEmployeeList();
			clientsList = clientsService.getClientList("");
			
			hjd = (Hjd) hjdService.getHjd(id);
			hjdProducts = hjdService.getHjdProducts(id);
			return "success";
		} catch (Exception e) {
			log.error("加载修改换件单 失败原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 修改换件单
	 * 
	 * @return
	 */
	public String update()throws Exception
	{
		try 
		{   LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		    String user_id = info.getUser_id();
		    hjd.setCjr(user_id);		
		    userList = userService.getAllEmployeeList();
				 
			    if(hjd.getState().equals("已提交"))
	            {
	            	//判断提交的换件商品是否在好件库里
				 if(hjdService.isHaoShkcExist(hjd, hjdProducts))
	            	{
	            		hjd.setState("已保存");
	            		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
	            		return "input";
	            	} 
	            	//保存信息
	            	hjdService.updateHjd(hjd, hjdProducts);
	            }
	            if(hjd.getState().equals("已保存"))
	            {
	            	hjdService.updateHjd(hjd, hjdProducts);
	            }		
			return "success";
		} catch (Exception e) {
			log.error("保存更改换件单  失败原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 删除换件单
	 * 
	 * @return
	 */
	public String del() {
		try {
			String hjd_id = ParameterUtility.getStringParameter(getRequest(),
					"id", "");
			hjdService.delHjd(hjd_id);
			return "success";
		} catch (Exception e) {
			log.error("删除换件单  失败原因" + e.getMessage());
			return "error";
		}
	}
	
	/**
	 * 单击换件单查看换件商品
	 * @return
	 * @throws Exception
	 */
	public String desc()throws Exception
	{
		try
		{		
			 hjdProducts=hjdService.getHjdProducts(id);
		    return "success";
		}
		catch(Exception e)
		{
			log.error("单击换件单查看商品  失败原因："+e.getMessage());
			return "error";
		}
	}

	/**
	 * 打开输入序列号窗口
	 * @return
	 */
	public String importSerial(){
		return "success";
	}
	
	
	public String getHj_date1() {
		return hj_date1;
	}

	public void setHj_date1(String hj_date1) {
		this.hj_date1 = hj_date1;
	}

	public String getHj_date2() {
		return hj_date2;
	}

	public void setHj_date2(String hj_date2) {
		this.hj_date2 = hj_date2;
	}

	public String getJsr() {
		return jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
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

	public Page getHjdPage() {
		return hjdPage;
	}

	public void setHjdPage(Page hjdPage) {
		this.hjdPage = hjdPage;
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

	public HjdService getHjdService() {
		return hjdService;
	}

	public void setHjdService(HjdService hjdService) {
		this.hjdService = hjdService;
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

	public Hjd getHjd() {
		return hjd;
	}

	public void setHjd(Hjd hjd) {
		this.hjd = hjd;
	}

	public HjdProduct getHjdProduct() {
		return hjdProduct;
	}

	public void setHjdProduct(HjdProduct hjdProduct) {
		this.hjdProduct = hjdProduct;
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

	public ShkcService getShkcService() {
		return shkcService;
	}

	public void setShkcService(ShkcService shkcService) {
		this.shkcService = shkcService;
	}

	public Page getShkcPage() {
		return shkcPage;
	}

	public void setShkcPage(Page shkcPage) {
		this.shkcPage = shkcPage;
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
	
	public List getKindList() {
		return kindList;
	}


	public void setKindList(List kindList) {
		this.kindList = kindList;
	}
	
	public List getHjdProducts() {
		return hjdProducts;
	}
	public void setHjdProducts(List hjdProducts) {
		this.hjdProducts = hjdProducts;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
