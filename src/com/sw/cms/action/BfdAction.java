
package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Bfd;
import com.sw.cms.model.BfdProduct;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.BfdService;
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

public class BfdAction extends BaseAction 
{
	private SjzdService sjzdService;
	private BfdService bfdService;
	private UserService userService;
	private ClientsService clientsService;
	private EmployeeService employeeService;
	private DeptService deptService;
	private ProductKcService productKcService;
	private ShkcService      shkcService;
	private ProductKindService productKindService;
	
	private Page ywyEmployeePage;
	private Page bfdPage;
	private Page productPage;
	private Page shkcPage;

	private String[] wxszd;
	private String[] positions;

	private List userList = new ArrayList();
	private List clientsList = new ArrayList();
	private List depts = new ArrayList();
	private List bfdProducts = new ArrayList();
	
	private Bfd bfd = new Bfd();
	private BfdProduct bfdProduct = new BfdProduct();

	private String[] fj = new String[7];
	private String product_serial_num = "";
	
	private String state = "";
	private String jsr = "";
	private String bf_date1 = "";
	private String bf_date2 = "";
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
	 * 报废单列表（带分页）
	 * 
	 * @return
	 */
	public String list()throws Exception
	{
		try {
			int rowsPerPage = Constant.PAGE_SIZE2;
			String con = "";
			
			if (!bf_date1.trim().equals("")) {
				con += " and b.bf_date>='" + bf_date1 + "'";
			}
			if (!bf_date2.trim().equals("")) {
				con += " and b.bf_date<='" + (bf_date2 + " 23:59:59") + "'";
			}
			if (!state.trim().equals("")) {
				con += " and b.state='" + state + "'";
			}
			if (orderName.equals("")) {
				orderName = "id";
			}
			if (orderType.equals("")) {
				orderType = "desc";
			}
			con += " order by " + orderName + " " + orderType + ""; 
			
			bfdPage = bfdService.getBfdList(con, curPage, rowsPerPage);
			
			return "success";
		} catch (Exception e) {
			log.error("返回报废单列表  错误原因" + e.getMessage());
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

			if(!product_kind.equals("")){
				con += " and a.product_kind like '" + product_kind + "%'";
			}
			
			shkcPage = shkcService.getShkuIsBadProduct(con, curPage, rowsPerPage);	
						
			//kindList = productKindService.getAllProductKindList();
			
			return "success";
		} catch (Exception e) {
			log.error("获取坏件库列表 错误原因:" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 加载报废单页面
	 * 
	 * @return
	 */
	public String add() {
		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
		userList = userService.getAllEmployeeList();
		bfd.setId(bfdService.updateBfdId());
		clientsList = clientsService.getClientList("");
		return "success";
	}

	/**
	 * 保存报废单
	 * 
	 * @return
	 */
	public String save() throws Exception
	{
		try {
			LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			bfd.setCjr(user_id);
						
            if(bfd.getState().equals("已提交"))
            {
            	//判断提交的报废商品是否在报废库里
            	msg=bfdService.isBfShkcExist(bfd,bfdProducts);
            	if(!msg.equals(""))
            	{
            		bfd.setState("已保存");
            		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
            		this.saveMessage(msg);
            		return "input";
            	} 
            	
            	//判断提交的报废商品是否满足库存要求
		    	msg=bfdService.checkKc(bfd, bfdProducts);
            	if(!msg.equals(""))
            	{
            		bfd.setState("已保存");
            		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
            		this.saveMessage(msg);
            		return "input";
            	} 
            	//保存信息
            	bfdService.saveBfd(bfd, bfdProducts);
            }
            if(bfd.getState().equals("已保存"))
            {
            	bfdService.saveBfd(bfd, bfdProducts);
            }
			
			return "success";
		} catch (Exception e) {
			log.error("保存报废单  失败原因" + e.getMessage());
			return "error";
		}
	}

	/**
	 * 修改报废单前加载报废单
	 * 
	 * @return
	 */
	public String edit()throws Exception
	{
		try {
			wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
			userList = userService.getAllEmployeeList();
			clientsList = clientsService.getClientList("");
			
			
			bfd = (Bfd) bfdService.getBfd(id);
			bfdProducts = bfdService.getBfdProducts(id);
			return "success";
		} catch (Exception e) {
			log.error("加载修改报废单 失败原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 修改报废单
	 * 
	 * @return
	 */
	public String update()throws Exception
	{
		try 
		{   LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		    String user_id = info.getUser_id();
		    bfd.setCjr(user_id);		
		    userList = userService.getAllEmployeeList();
				 
			    if(bfd.getState().equals("已提交"))
	            {
	            	//判断提交的报废商品是否在报废库里
			    	msg=bfdService.isBfShkcExist(bfd,bfdProducts);
	            	if(!msg.equals(""))
	            	{
	            		bfd.setState("已保存");
	            		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
	            		this.saveMessage(msg);
	            		return "input";
	            	} 
	            	
                    //判断提交的报废商品是否满足库存要求
			    	msg=bfdService.checkKc(bfd, bfdProducts);
	            	if(!msg.equals(""))
	            	{
	            		bfd.setState("已保存");
	            		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
	            		this.saveMessage(msg);
	            		return "input";
	            	} 
	            	//保存信息
	            	bfdService.updateBfd(bfd, bfdProducts);
	            }
	            if(bfd.getState().equals("已保存"))
	            {
	            	bfdService.updateBfd(bfd, bfdProducts);
	            }		
			return "success";
		} catch (Exception e) {
			log.error("保存更改报废单  失败原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 删除报废单
	 * 
	 * @return
	 */
	public String del() {
		try {
			String bfd_id = ParameterUtility.getStringParameter(getRequest(),
					"id", "");
			bfdService.delBfd(bfd_id);
			return "success";
		} catch (Exception e) {
			log.error("删除报废单  失败原因" + e.getMessage());
			return "error";
		}
	}
	
	/**
	 * 单击报废单查看报废商品
	 * @return
	 * @throws Exception
	 */
	public String desc()throws Exception
	{
		try
		{		
			 bfdProducts=bfdService.getBfdProducts(id);
		    return "success";
		}
		catch(Exception e)
		{
			log.error("单击报废单查看商品  失败原因："+e.getMessage());
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
	
	
	public String getBf_date1() {
		return bf_date1;
	}

	public void setBf_date1(String bf_date1) {
		this.bf_date1 = bf_date1;
	}

	public String getBf_date2() {
		return bf_date2;
	}

	public void setBf_date2(String bf_date2) {
		this.bf_date2 = bf_date2;
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

	public Page getBfdPage() {
		return bfdPage;
	}

	public void setBfdPage(Page bfdPage) {
		this.bfdPage = bfdPage;
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

	public BfdService getBfdService() {
		return bfdService;
	}

	public void setBfdService(BfdService bfdService) {
		this.bfdService = bfdService;
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

	public Bfd getBfd() {
		return bfd;
	}

	public void setBfd(Bfd bfd) {
		this.bfd = bfd;
	}

	public BfdProduct getBfdProduct() {
		return bfdProduct;
	}

	public void setBfdProduct(BfdProduct bfdProduct) {
		this.bfdProduct = bfdProduct;
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
	
	public List getBfdProducts() {
		return bfdProducts;
	}
	public void setBfdProducts(List bfdProducts) {
		this.bfdProducts = bfdProducts;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
