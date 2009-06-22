package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.model.Page;
import com.sw.cms.service.LxrService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

public class LxrAction extends BaseAction 
{
    private LxrService lxrService;
    private SjzdService sjzdService;
	private UserService userService;
	
	private String lxr = "";
	private String clients_name = "";
	private String khjl = "";
	
	private Page  lxrPage;
	
	private String[] wldwlx;
	private String[] lxrid;
	
	private String orderName = "";
	private String orderType = "";
	
	private String id;
	
	private int curPage = 1;
	
	private List userList=new ArrayList();
	
	private ClientsLinkman linkman = new ClientsLinkman();

  /**
   * 联系人列表（带分页）
   * @return
   */
  public String list()
  {
	    int rowsPerPage = Constant.PAGE_SIZE2;
	    String con="";
	   
		if (!clients_name.equals("")) {
			con += " and c.name like'%" + clients_name + "%'";
		}
		if (!lxr.equals("")) {
			con += " and l.name like'%" + lxr + "%'";
		}
		if (!khjl.equals("")) {
			con += " and c.khjl='"+khjl+"'";
		}

		if (orderName.equals("")) {
			orderName = "id";
		}
		if (orderType.equals("")) {
			orderType = "desc";
		}

		con += " order by " + orderName + " " + orderType;

		lxrPage = lxrService.getClinetsLinkman(con, curPage, rowsPerPage);
		userList = userService.getAllEmployeeList();

		return "success";
	   
  }
  
  /**
   * 打开添加联系人页面
   * @return
   */
  public String add()
  {
	  try 
	  {
		  return "success";
	  }
	  catch (Exception e)
	  {
		log.error("打开添加联系人页面  错误原因："+e.getMessage());
		return "error";
	  }
	  
  }
  
  /**
   * 保存联系人
   * @return
   */
  public String save()
  {
	  try 
	  {
		lxrService.insertLinkman(linkman);
		return "success";
	  }
	  catch (Exception e) 
	  {
		log.error("保存联系人  错误原因："+e.getMessage());
		return "error";
	  }
	  
  }
  
  /**
   * 打开修改联系人页面
   * @return
   */
  public String edit()
  {
	  try 
	  {
		 linkman=(ClientsLinkman)lxrService.getLinkmanById(id); 
		 return "success";
	  }
	  catch (Exception e)
	  {
		log.error("打开修改联系人  错误原因"+e.getMessage());
		return "error";
      }
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

public LxrService getLxrService() {
	return lxrService;
}

public void setLxrService(LxrService lxrService) {
	this.lxrService = lxrService;
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

public int getCurPage() {
	return curPage;
}

public void setCurPage(int curPage) {
	this.curPage = curPage;
}

public String getKhjl() {
	return khjl;
}

public void setKhjl(String khjl) {
	this.khjl = khjl;
}

public ClientsLinkman getLinkman() {
	return linkman;
}

public void setLinkman(ClientsLinkman linkman) {
	this.linkman = linkman;
}

public String getLxr() {
	return lxr;
}

public void setLxr(String lxr) {
	this.lxr = lxr;
}

public Page getLxrPage() {
	return lxrPage;
}

public void setLxrPage(Page lxrPage) {
	this.lxrPage = lxrPage;
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

public String[] getWldwlx() {
	return wldwlx;
}

public void setWldwlx(String[] wldwlx) {
	this.wldwlx = wldwlx;
}
public String getClients_name() {
	return clients_name;
}
public void setClients_name(String clients_name) {
	this.clients_name = clients_name;
}
public List getUserList() {
	return userList;
}
public void setUserList(List userList) {
	this.userList = userList;
}






































public String[] getLxrid() {
	return lxrid;
}






































public void setLxrid(String[] lxrid) {
	this.lxrid = lxrid;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}
}
