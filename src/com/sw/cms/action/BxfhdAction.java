package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Bxfhd;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.BxfhdService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.EmployeeService;
import com.sw.cms.service.ShkcService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class BxfhdAction extends BaseAction
{
  private BxfhdService bxfhdService;
  private SjzdService  sjzdService;
  private ClientsService clientsService;
  private EmployeeService employeeService;
  private UserService userService;
  private ShkcService      shkcService;
  
  private Page   bxfhdPage;
  private Page shkcPage;
  private Page productPage;
  
  private String orderName="";
  private String orderType="";
  private int    curPage=1;
  private String lxr="";
  private String qz_serial_num="";
  private String fh_date1="";
  private String fh_date2="";
  private String state="";  
  private String bxcs_name = "";
  private String product_name = "";
  private String id="";
   
  private Bxfhd bxfhd=new Bxfhd();
  private BxfhdProduct bxfhdProduct=new BxfhdProduct();
  
  private String[] wxszd;
  private String[] fj = new String[7];
  
  private List bxfhdProducts = new ArrayList();
  private List userList = new ArrayList();
  private List clientsList = new ArrayList();
  
/**
   * 报修返还单列表（带分页）
   * @return
   * @throws Exception
   */
  public String list()throws Exception
  {
	  try
	  {
		  int rowsPerPage=Constant.PAGE_SIZE2;
		  String con="";
		  if (!bxcs_name.trim().equals("")) 
		  {
			  con = " and c.name like '%" + bxcs_name + "%'";
		  }		  
		  if(!fh_date1.trim().equals(""))
		  {
			  con+=" and b.fh_date>='"+fh_date1+"'";
		  }
		  if(!fh_date2.trim().equals(""))
		  {
			  con+=" and b.fh_date<='"+fh_date2+"'";
		  }
		  if(!state.trim().equals(""))
		  {
			  con+=" and b.state='"+state+"'";
		  }
		  
		  if(orderName.equals(""))
		  {
			  orderName="id";
		  }
		  if(orderType.equals(""))
		  {
			  orderType="desc";
		  }
		  con+=" order by "+orderName+" "+orderType+"";
		  bxfhdPage=bxfhdService.getBxfhdList(con, curPage, rowsPerPage);	  
		  return "success";
	  }
	  catch(Exception e)
	  {
		  log.error("报修返还单列表  错误原因："+e.getMessage());
		  return "error";
	  }
  }
  
  /**
	 * 加载报修返还单页面
	 * 
	 * @return
	 */
	public String add() {
		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
		userList = userService.getAllEmployeeList();
		bxfhd.setId(bxfhdService.updateBxfhdId());
		clientsList = clientsService.getClientList("");
		return "success";
	}
  
  /**
	 * 删除报修返还单
	 * 
	 * @return
	 */
	public String del() {
		try {
			String bxfhd_id = ParameterUtility.getStringParameter(getRequest(),
					"id", "");
			bxfhdService.delBxfhd(bxfhd_id);
			return "success";
		} catch (Exception e) {
			log.error("删除报修单  失败原因" + e.getMessage());
			return "error";
		}
	}
  
  /**
   * 打开修改报修返还单
   * @return
   * @throws Exception
   */
  public String edit()throws Exception
  {
	  try
	  {
		  //报修返还单ID
		  String id = ParameterUtility.getStringParameter(getRequest(), "id", ""); 
		  
		  wxszd=sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
		  userList = userService.getAllEmployeeList();
		  clientsList = clientsService.getClientList("");
		  bxfhd=(Bxfhd)bxfhdService.getBxfhd(id);
		  
		  bxfhdProducts = bxfhdService.getBxfhdProducts(id);
		 
		  return "success";
	  }
	  catch(Exception e)
	  {
		  log.error("打开修改报修返还单  错误原因："+e.getMessage());
		  return "error";
	  }
  }
  
  /**
   * 修改报修返还单
   * @return
   * @throws Exception
   */
  public String update()throws Exception
  {
	  try 
		{   LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
	        String user_id = info.getUser_id();
	        bxfhd.setCjr(user_id);		
	        userList = userService.getAllEmployeeList();
			 
		    if(bxfhd.getState().equals("已提交"))
            {
              //判断提交的报修商品是否在好件库里
		    	msg = bxfhdService.isHaoShkcExist(bxfhd,bxfhdProducts);
			    if(!msg.equals(""))
            	{
            		bxfhd.setState("已保存");
            		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
            		this.saveMessage(msg);
            		return "input";
            	}
			 //判断库存是否满足要求            	
	         	msg = bxfhdService.checkKc(bxfhd,bxfhdProducts);
				if(!msg.equals(""))
				{
					bxfhd.setState("已保存");
	         	    wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
	         	    this.saveMessage(msg);
	         	    return "input";
	            }
         	
            	//保存信息
			 bxfhdService.updateBxfhd(bxfhd, bxfhdProducts);
           }
            if(bxfhd.getState().equals("已保存"))
            {
            	bxfhdService.updateBxfhd(bxfhd, bxfhdProducts);
            }		
		    return "success";
	  } 
	  catch (Exception e) 
	  {
		log.error("保存更改报修返还单  失败原因" + e.getMessage());
		return "error";
	  }
}
  
  /**
   * 保存报修返还单
   * @return
   * @throws Exception
   */
  public String save()throws Exception
  {
	  try 
		{   LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
	        String user_id = info.getUser_id();
	        bxfhd.setCjr(user_id);			        
			 
		    if(bxfhd.getState().equals("已提交"))
            {
            	//判断提交的报修商品是否在好件库里
		    	msg = bxfhdService.isHaoShkcExist(bxfhd,bxfhdProducts);
			    if(!msg.equals(""))
            	{
            		bxfhd.setState("已保存");
            		wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
            		this.saveMessage(msg);
            		return "input";
            	}
			 //判断库存是否满足要求            	
	         	msg = bxfhdService.checkKc(bxfhd,bxfhdProducts);
				if(!msg.equals(""))
				{
					bxfhd.setState("已保存");
	         	    wxszd = sjzdService.getSjzdXmxxByZdId("SJZD_WXSZD");
	         	    this.saveMessage(msg);
	         	    return "input";
	            }
            	//保存信息
			 bxfhdService.saveBxfhd(bxfhd, bxfhdProducts);
           }
            if(bxfhd.getState().equals("已保存"))
            {
            	bxfhdService.saveBxfhd(bxfhd, bxfhdProducts);
            }		
		    return "success";
	  } 
	  catch (Exception e) 
	  {
		log.error("保存报修返还单  失败原因" + e.getMessage());
		return "error";
	  }
} 
  /**
   * 单击报修返还单查看报修返还信息
   * @return
   * @throws Exception
   */
  public String desc()throws Exception
  {
	  try
	  {
		  String id = ParameterUtility.getStringParameter(getRequest(), "id", ""); 
		  bxfhdProducts=bxfhdService.getBxfhdProducts(id);
		  return "success";
	  }
	  catch(Exception e)
	  {
		  log.error("单击报修返还单查看报修返还信息 错误原因:"+e.getMessage());
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
				con += " and (product_name like '%" + product_name + "%' or product_xh like '%" + product_name + "%')";
			}	
				
			shkcPage = shkcService.getShkcIsWaiProduct(con, curPage, rowsPerPage);	
						
			//kindList = productKindService.getAllProductKindList();
			
			return "success";
		} catch (Exception e) {
			log.error("获取坏件库列表 错误原因:" + e.getMessage());
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
	
  public Page getBxfhdPage() {
	return bxfhdPage;
}

public void setBxfhdPage(Page bxfhdPage) {
	this.bxfhdPage = bxfhdPage;
}

public String getFh_date1() {
	return fh_date1;
}

public void setFh_date1(String fh_date1) {
	this.fh_date1 = fh_date1;
}

public String getFh_date2() {
	return fh_date2;
}

public void setFh_date2(String fh_date2) {
	this.fh_date2 = fh_date2;
}

public int getCurPage() {
	return curPage;
}

public void setCurPage(int curPage) {
	this.curPage = curPage;
}

public String getBxcs_name() {
	return bxcs_name;
}

public void setBxcs_name(String bxcs_name) {
	this.bxcs_name = bxcs_name;
}

public String getLxr() {
	return lxr;
}

public void setLxr(String lxr) {
	this.lxr = lxr;
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

public String getQz_serial_num() {
	return qz_serial_num;
}

public void setQz_serial_num(String qz_serial_num) {
	this.qz_serial_num = qz_serial_num;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}
 
public BxfhdService getBxfhdService() {
	return bxfhdService;
}

public void setBxfhdService(BxfhdService bxfhdService) {
	this.bxfhdService = bxfhdService;
}

public String[] getWxszd() {
	return wxszd;
}

public void setWxszd(String[] wxszd) {
	this.wxszd = wxszd;
}

public SjzdService getSjzdService() {
	return sjzdService;
}

public void setSjzdService(SjzdService sjzdService) {
	this.sjzdService = sjzdService;
}

public EmployeeService getEmployeeService() {
	return employeeService;
}

public void setEmployeeService(EmployeeService employeeService) {
	this.employeeService = employeeService;
}

public ClientsService getClientsService() {
	return clientsService;
}

public void setClientsService(ClientsService clientsService) {
	this.clientsService = clientsService;
}

public UserService getUserService() {
	return userService;
}

public void setUserService(UserService userService) {
	this.userService = userService;
}

public Bxfhd getBxfhd() {
	return bxfhd;
}

public void setBxfhd(Bxfhd bxfhd) {
	this.bxfhd = bxfhd;
}

public BxfhdProduct getBxfhdProduct() {
	return bxfhdProduct;
}

public void setBxfhdProduct(BxfhdProduct bxfhdProduct) {
	this.bxfhdProduct = bxfhdProduct;
}

public String[] getFj() {
	return fj;
}

public void setFj(String[] fj) {
	this.fj = fj;
}

public List getBxfhdProducts() {
	return bxfhdProducts;
}
public void setBxfhdProducts(List bxfhdProducts) {
	this.bxfhdProducts = bxfhdProducts;
}

public List getUserList() {
	return userList;
}

public void setUserList(List userList) {
	this.userList = userList;
}

public List getClientsList() {
	return clientsList;
}

public void setClientsList(List clientsList) {
	this.clientsList = clientsList;
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

public Page getProductPage() {
	return productPage;
}

public void setProductPage(Page productPage) {
	this.productPage = productPage;
}

public String getProduct_name() {
	return product_name;
}

public void setProduct_name(String product_name) {
	this.product_name = product_name;
}


public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
}
