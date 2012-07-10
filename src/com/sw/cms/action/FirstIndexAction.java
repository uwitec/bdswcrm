package com.sw.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.BwlService;
import com.sw.cms.service.CgfkService;
import com.sw.cms.service.ClientWlStatService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.FirstIndexService;
import com.sw.cms.service.FysqService;
import com.sw.cms.service.KfdbService;
import com.sw.cms.service.LsdService;
import com.sw.cms.service.MenuService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsdService;
import com.sw.cms.service.XxfbNbggService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StringUtils;

public class FirstIndexAction extends BaseAction {
	
	private FirstIndexService firstIndexService;
	private XxfbNbggService xxfbNbggService;
	private BwlService bwlService;
	private MenuService menuService;
	private UserService userService;
	private LsdService lsdService;
	private XsdService xsdService;
	private CgfkService cgfkService;
	private FysqService fysqService;
	private KfdbService kfdbService;
	private ClientsService clientsService;
	
	private ClientWlStatService clientWlStatService;
	
	private List nbggList = new ArrayList();
	private List bwlList = new ArrayList();
	private List dckList = new ArrayList();
	private List drkList = new ArrayList();
	private List dspLsdList = new ArrayList();
	private List dspXsdList = new ArrayList();
	private List dspCgfkList = new ArrayList();
	private List dspFysqList = new ArrayList();
	private List confirmKfdbList = new ArrayList();
	private List clientList = new ArrayList();
	
	private String isCkdRight = "0"; 
	private String isRkdRight = "0";
	private String isLsdSpRight = "0"; 
	private String isXsdSpRight = "0";
	private String isCgfkSpRight = "0";
	private String isFysqSpRight = "0";
	private int curPage = 1;
		
	/**
	 * 首页显示项
	 * @return
	 */
	public String listMain(){
		String user_id ="";
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		user_id = info.getUser_id();

		//内部公告
		Page page = xxfbNbggService.getNbggList("",1, 8);
		nbggList = page.getResults();

		//备忘录
		Page bwlpage = bwlService.getBwlShareList(curPage, 8,user_id);
		bwlList = bwlpage.getResults();
		
		return "success";
	}
	
	
	/**
	 * ajax客户应收汇总
	 */
	public void queryClientsYshz(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			clientList = clientsService.getSyClietsById(user_id);
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("<table width=\"100%\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\">");
			sb.append("<tr>");
			sb.append("	<td height=\"27\" colspan=\"7\" align=\"left\" style=\"background-image:url(images/head_top2bg.gif)\">&nbsp;&nbsp;&nbsp;&nbsp;<b>应收汇总</b></td>");								
			sb.append("</tr>");
			sb.append("<tr>");
									 
			sb.append("   <TD width=\"45%\" height=\"23\">客户名称</TD>");
			sb.append("   <TD width=\"14%\" nowrap=\"nowrap\" height=\"23\">当前应收款</TD>");
			sb.append("   <TD width=\"14%\" nowrap=\"nowrap\" height=\"23\">超期应收款</TD>");
			sb.append("   <TD width=\"12%\" nowrap=\"nowrap\" height=\"23\">预收款</TD>");			           
			sb.append("   <TD width=\"15%\" nowrap=\"nowrap\" height=\"23\">最长超期天数</TD>");
           
			sb.append("</tr>");
			sb.append("<tr><td height=\"3\"></td></tr>");

			double hj_dqys = 0;
            double hj_wdqysk = 0;
            double hj_cqysk = 0;
            double hj_ysk = 0;
            double hj_qtysk = 0;

            Map qcMap = clientWlStatService.getClientQc(DateComFunc.getToday());
            Map infoMap = clientWlStatService.getClientWlInfo(DateComFunc.getToday(),DateComFunc.getToday(),"");
            Map maxCqtsMap = clientWlStatService.getClientMaxCqts("");  //最长超期天数
            Map wdqyskMap = clientWlStatService.getClientWdqysk("");    //未到期应收款
            Map cqyskMap = clientWlStatService.getClientCqysk("");      //超期应收款 

            Map yskMap = clientWlStatService.getClientYushouk("");      //预收款
            Map qcjsMap = clientWlStatService.getClientQcjsye("");      //期初结算
            Map tzjsMap = clientWlStatService.getClientTzjsye("");      //调账（应收）结算

            if(clientList != null && clientList.size() > 0){
            	for(int i=0;i<clientList.size();i++){
            		Map map = (Map)clientList.get(i);

            		String client_id = StringUtils.nullToStr(map.get("id"));
            		String client_name = StringUtils.nullToStr(map.get("name"));		            
            
            		double qcs = qcMap.get(client_id+"应收")==null?0:((Double)qcMap.get(client_id+"应收")).doubleValue();  //期初数		
            		double bqfs = infoMap.get(client_id+"应收发生")==null?0:((Double)infoMap.get(client_id+"应收发生")).doubleValue();  //本期发生
           		 	double ysje = infoMap.get(client_id+"已收发生")==null?0:((Double)infoMap.get(client_id+"已收发生")).doubleValue();  //本期已收
            		String strCqts = StringUtils.nullToStr(maxCqtsMap.get(client_id));
		            int cqts = 0; //最长超期天数
		            if(!strCqts.equals("")){
			            cqts = new Integer(strCqts).intValue();
		            }
		            double wdqysk = wdqyskMap.get(client_id)==null?0:((Double)wdqyskMap.get(client_id)).doubleValue();  //未到期应收款 
		            double cqysk = cqyskMap.get(client_id)==null?0:((Double)cqyskMap.get(client_id)).doubleValue();  //超期应收款
		
		            double ysk = yskMap.get(client_id)==null?0:((Double)yskMap.get(client_id)).doubleValue();  //预收款
		            double qtysk = (qcjsMap.get(client_id)==null?0:((Double)qcjsMap.get(client_id)).doubleValue()) + (tzjsMap.get(client_id)==null?0:((Double)tzjsMap.get(client_id)).doubleValue()); //其它应收款 

		            double dqys = qcs + bqfs - ysje;  //当前应收
 	        
 	        
		            boolean bl = false;		 
			        if(dqys !=0 || cqysk!=0 || ysk!=0) {
			 	        bl = true;
			        }else{
			          bl = false;
		            }
 	        	 	        
		 	       if(bl){ 
				        hj_dqys += dqys;
				        hj_wdqysk += wdqysk;
				        hj_cqysk += cqysk;
				        hj_ysk += ysk;
				        hj_qtysk += qtysk;
	
				        sb.append("<TR>");			 
				        sb.append("   <TD width=\"45%\" height=\"23\" align=\"left\">" + client_name + "</TD>");
				        sb.append("   <TD width=\"14%\" nowrap=\"nowrap\" height=\"23\" align=\"right\">" + JMath.round(dqys,2) + "</TD>");			           
				        sb.append("   <TD width=\"14%\" nowrap=\"nowrap\" height=\"23\" align=\"right\">" + JMath.round(cqysk,2) + "</TD>");	
				        sb.append("    <TD width=\"12%\" nowrap=\"nowrap\" height=\"23\" align=\"right\">" + JMath.round(ysk,2) + "</TD>");			           
				        sb.append("    <TD width=\"15%\" nowrap=\"nowrap\" height=\"23\" align=\"center\">" + cqts + "</TD>");
				        sb.append("</TR>");					

					}
				}
	
			}
	
	        sb.append("<TR>");
	        sb.append("	<TD width=\"45%\" height=\"23\">合计（金额）</TD>");
	        sb.append("	<TD width=\"14%\" nowrap=\"nowrap\" height=\"23\" align=\"right\">" + JMath.round(hj_dqys,2) + "</TD>");
	        sb.append("	<TD width=\"14%\" nowrap=\"nowrap\" height=\"23\" align=\"right\">" + JMath.round(hj_cqysk,2) + "</TD>");
	        sb.append("	<TD width=\"12%\" nowrap=\"nowrap\" height=\"23\" align=\"right\">" + JMath.round(hj_ysk,2) + "</TD>");		            
	        sb.append("	<TD width=\"15%\" nowrap=\"nowrap\" height=\"23\">--</TD>");
	        sb.append("</TR>");            																			
	        sb.append("</table>");
              
	        this.writeStringToResponse(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Ajax请求待办工作数量
	 */
	public void getUndoWorkNums(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();  //当前登陆用户
		
		Map numsMap = new HashMap(); 
		
		//如果有操作出库单权限
		if(userService.isHasRightFuncs("FC0005", user_id)){
			isCkdRight = "1";
			dckList = firstIndexService.getDckdList();
			numsMap.put("dckListNums", dckList.size());
		}else{
			numsMap.put("dckListNums", 0);
		}
		
		//如果有操作入库单权限
		if(userService.isHasRightFuncs("FC0004", user_id)){
			isRkdRight = "1";
			drkList = firstIndexService.getDrkdList();
			numsMap.put("drkListNums", drkList.size());
		}else{
			numsMap.put("drkListNums", 0);
		}
		
		
		//待审批零售单
		List list = userService.getJgspUsers();		//具有价格审批权限的用户列表
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				
				String cur_user = (String)map.get("user_id");
				if(cur_user.equals(user_id)){
					isLsdSpRight = "1";
					dspLsdList = lsdService.getDspLsdList(); //待审批零售单列表
					break;
				}
			}
		}
		if(isLsdSpRight.equals("1")){
			numsMap.put("dspLsdNums", dspLsdList.size());
		}else{
			numsMap.put("dspLsdNums", 0);
		}
		
		
		String con = "";	
		//如果有价格审批权限
		if(userService.isHashJgspRight(user_id)){
			isXsdSpRight = "1";
			if(con.equals("")){
				con += "a.sp_type='4'";
			}else{
				con += " or a.sp_type='4'";
			}
		}
		//如果有超额审批权限
		if(userService.isHasCespRight(user_id)){
			isXsdSpRight = "1";
			if(con.equals("")){
				con += "a.sp_type='3'";
			}else{
				con += " or a.sp_type='3'";
			}
		}
		//如果有超期审批权限
		if(userService.isHasCqspRight(user_id)){
			isXsdSpRight = "1";
			if(con.equals("")){
				con += "a.sp_type='1'";
			}else{
				con += " or a.sp_type='1'";
			}
		}
		//如查有超额及价格审批权限
		if(userService.isHasCeAndJgspRight(user_id)){
			isXsdSpRight = "1";
			if(con.equals("")){
				con += "a.sp_type='2'";
			}else{
				con += " or a.sp_type='2'";
			}
		}
		//待审批销售订单
		if(isXsdSpRight.equals("1")){
			dspXsdList = xsdService.getDspXsdList(con);
		}
		if(isXsdSpRight.equals("1")){
			numsMap.put("dspXsdNums", dspXsdList.size());
		}else{
			numsMap.put("dspXsdNums", 0);
		}

		
		//采购付款审批权限设置
		Map cgfkMap = userService.getSpRight("采购付款");
		String sp_flag = "";
		String role_id = "";
		if(cgfkMap != null){
			sp_flag = StringUtils.nullToStr(cgfkMap.get("sp_flag"));
			role_id = StringUtils.nullToStr(cgfkMap.get("role_id"));
		}
		String[] roles = role_id.split(",");
		
		//当前用户有采购付款审批权限
		if(userService.isUserInRole(user_id, roles)){
			isCgfkSpRight = "1";
			dspCgfkList = cgfkService.getCgfks(" and state='待审批'");
		}		
		if(isCgfkSpRight.equals("1")){
			numsMap.put("dspCgfkNums", dspCgfkList.size());
		}else{
			numsMap.put("dspCgfkNums", 0);
		}
		
		
		//费用申请审批权限设置
		Map fysqMap = userService.getSpRight("费用申请");
		sp_flag = "";
		role_id = "";
		if(fysqMap != null){
			sp_flag = StringUtils.nullToStr(fysqMap.get("sp_flag"));
			role_id = StringUtils.nullToStr(fysqMap.get("role_id"));
		}
		String[] fysqRoles = role_id.split(",");
		
		//当前用户有费用申请审批权限
		if(userService.isUserInRole(user_id, fysqRoles)){
			isFysqSpRight = "1";
			dspFysqList = fysqService.getFysqList(" and (state='提交' or state='待审批')");
		}
		if(isFysqSpRight.equals("1")){
			numsMap.put("dspFysqNums", dspFysqList.size());
		}else{
			numsMap.put("dspFysqNums", 0);
		}
		
		confirmKfdbList = kfdbService.getConfirmKfdbList(user_id);
		numsMap.put("confirmKfdbNums", confirmKfdbList.size());
		
		//转化为json
		JSONObject json = JSONObject.fromObject(numsMap);
		this.writeJsonToResponse(json);
	}
	
	
	/**
	 * 显示分销首页
	 * @return
	 */
	public String listFxMain(){
		Page page = xxfbNbggService.getNbggList(1, 20 , "1");
		nbggList = page.getResults();
		return "success";
	}
	
	
	/**
	 * 显示供应商首页
	 * @return
	 */
	public String listGysMain(){
		return "success";
	}
	
	
	/**
	 * 待办工作列表
	 * @return
	 */
	public String listUndoWork(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();  //当前登陆用户
			
			//如果有操作出库单权限
			if(userService.isHasRightFuncs("FC0005", user_id)){
				isCkdRight = "1";
				dckList = firstIndexService.getDckdList();
			}
			
			//如果有操作入库单权限
			if(userService.isHasRightFuncs("FC0004", user_id)){
				isRkdRight = "1";
				drkList = firstIndexService.getDrkdList();
			}
			
			
			//待审批零售单
			List list = userService.getJgspUsers();		//具有价格审批权限的用户列表
			if(list != null && list.size() > 0){
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					
					String cur_user = (String)map.get("user_id");
					if(cur_user.equals(user_id)){
						isLsdSpRight = "1";
						dspLsdList = lsdService.getDspLsdList(); //待审批零售单列表
						break;
					}
				}
			}
			
			String con = "";	
			//如果有价格审批权限
			if(userService.isHashJgspRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='4'";
				}else{
					con += " or a.sp_type='4'";
				}
			}
			//如果有超额审批权限
			if(userService.isHasCespRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='3'";
				}else{
					con += " or a.sp_type='3'";
				}
			}
			//如果有超期审批权限
			if(userService.isHasCqspRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='1'";
				}else{
					con += " or a.sp_type='1'";
				}
			}
			//如查有超额及价格审批权限
			if(userService.isHasCeAndJgspRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='2'";
				}else{
					con += " or a.sp_type='2'";
				}
			}
			//待审批销售订单
			if(isXsdSpRight.equals("1")){
				dspXsdList = xsdService.getDspXsdList(con);
			}

			
			//采购付款审批权限设置
			Map cgfkMap = userService.getSpRight("采购付款");
			String sp_flag = "";
			String role_id = "";
			if(cgfkMap != null){
				sp_flag = StringUtils.nullToStr(cgfkMap.get("sp_flag"));
				role_id = StringUtils.nullToStr(cgfkMap.get("role_id"));
			}
			String[] roles = role_id.split(",");
			
			//当前用户有采购付款审批权限
			if(userService.isUserInRole(user_id, roles)){
				isCgfkSpRight = "1";
				dspCgfkList = cgfkService.getCgfks(" and state='待审批'");
			}
			
			
			//费用申请审批权限设置
			Map fysqMap = userService.getSpRight("费用申请");
			sp_flag = "";
			role_id = "";
			if(fysqMap != null){
				sp_flag = StringUtils.nullToStr(fysqMap.get("sp_flag"));
				role_id = StringUtils.nullToStr(fysqMap.get("role_id"));
			}
			String[] fysqRoles = role_id.split(",");
			
			//当前用户有费用申请审批权限
			if(userService.isUserInRole(user_id, fysqRoles)){
				isFysqSpRight = "1";
				dspFysqList = fysqService.getFysqList(" and (state='提交' or state='待审批')");
			}
			
			confirmKfdbList = kfdbService.getConfirmKfdbList(user_id);
			return SUCCESS;
		}catch(Exception e){
			log.error("取待办列表信息出错，错误原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	/**
	 * 待确认库房调拨
	 * @return
	 */
	public String queryDqrDfdbList(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();  //当前登陆用户
			
			confirmKfdbList = kfdbService.getConfirmKfdbList(user_id);
			
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return  ERROR;
		}
	}		
	
	
	/**
	 * 待审批费用申请
	 * @return
	 */
	public String queryDspFysqList(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();  //当前登陆用户
			
			Map fysqMap = userService.getSpRight("费用申请");
			String sp_flag = "";
			String role_id = "";
			if(fysqMap != null){
				sp_flag = StringUtils.nullToStr(fysqMap.get("sp_flag"));
				role_id = StringUtils.nullToStr(fysqMap.get("role_id"));
			}
			String[] fysqRoles = role_id.split(",");
			
			//当前用户有费用申请审批权限
			if(userService.isUserInRole(user_id, fysqRoles)){
				isFysqSpRight = "1";
				dspFysqList = fysqService.getFysqList(" and (state='提交' or state='待审批')");
			}
			
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return  ERROR;
		}
	}	
	
	
	/**
	 * 待审批付款申请
	 * @return
	 */
	public String queryDspFksqList(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();  //当前登陆用户
			
			//采购付款审批权限设置
			Map cgfkMap = userService.getSpRight("采购付款");
			String sp_flag = "";
			String role_id = "";
			if(cgfkMap != null){
				sp_flag = StringUtils.nullToStr(cgfkMap.get("sp_flag"));
				role_id = StringUtils.nullToStr(cgfkMap.get("role_id"));
			}
			String[] roles = role_id.split(",");
			
			//当前用户有采购付款审批权限
			if(userService.isUserInRole(user_id, roles)){
				isCgfkSpRight = "1";
				dspCgfkList = cgfkService.getCgfks(" and state='待审批'");
			}
			
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return  ERROR;
		}
	}
	
	
	/**
	 * 待出库出库单
	 * @return
	 */
	public String queryDckList(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();  //当前登陆用户
			
			//如果有操作出库单权限
			if(userService.isHasRightFuncs("FC0005", user_id)){
				isCkdRight = "1";
				dckList = firstIndexService.getDckdList();
			}
			
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return  ERROR;
		}
	}
	
	
	/**
	 * 待审批零售单
	 * @return
	 */
	public String queryDspLsdList(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();  //当前登陆用户
			
			//待审批零售单
			List list = userService.getJgspUsers();		//具有价格审批权限的用户列表
			if(list != null && list.size() > 0){
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					
					String cur_user = (String)map.get("user_id");
					if(cur_user.equals(user_id)){
						isLsdSpRight = "1";
						dspLsdList = lsdService.getDspLsdList(); //待审批零售单列表
						break;
					}
				}
			}
			
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return  ERROR;
		}
	}
	
	
	/**
	 * 待审批销售单
	 * @return
	 */
	public String queryDspXsdList(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();  //当前登陆用户
			
			String con = "";	
			//如果有价格审批权限
			if(userService.isHashJgspRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='4'";
				}else{
					con += " or a.sp_type='4'";
				}
			}
			//如果有超额审批权限
			if(userService.isHasCespRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='3'";
				}else{
					con += " or a.sp_type='3'";
				}
			}
			//如果有超期审批权限
			if(userService.isHasCqspRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='1'";
				}else{
					con += " or a.sp_type='1'";
				}
			}
			//如查有超额及价格审批权限
			if(userService.isHasCeAndJgspRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='2'";
				}else{
					con += " or a.sp_type='2'";
				}
			}
			//待审批销售订单
			if(isXsdSpRight.equals("1")){
				dspXsdList = xsdService.getDspXsdList(con);
			}
			
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return  ERROR;
		}
	}	
	
	
	/**
	 * 待入库单
	 * @return
	 */
	public String queryDrkList(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();  //当前登陆用户
			
			//如果有操作入库单权限
			if(userService.isHasRightFuncs("FC0004", user_id)){
				isRkdRight = "1";
				drkList = firstIndexService.getDrkdList();
			}
			
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return  ERROR;
		}
	}
	

	/**
	 * 显示备忘录
	 * @return
	 */
	public String listUndoMyWork(){
		int rowsPerPage = Constant.PAGE_SIZE2;
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();

		//备忘录
		Page page = bwlService.getBwlShareList(curPage, rowsPerPage,user_id);
		bwlList = page.getResults();

		return "success";
	}

	public FirstIndexService getFirstIndexService() {
		return firstIndexService;
	}


	public void setFirstIndexService(FirstIndexService firstIndexService) {
		this.firstIndexService = firstIndexService;
	}


	public XxfbNbggService getXxfbNbggService() {
		return xxfbNbggService;
	}


	public void setXxfbNbggService(XxfbNbggService xxfbNbggService) {
		this.xxfbNbggService = xxfbNbggService;
	}


	public MenuService getMenuService() {
		return menuService;
	}


	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}


	public ClientsService getClientsService() {
		return clientsService;
	}


	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}


	public List getNbggList() {
		return nbggList;
	}


	public void setNbggList(List nbggList) {
		this.nbggList = nbggList;
	}

	public LsdService getLsdService() {
		return lsdService;
	}


	public void setLsdService(LsdService lsdService) {
		this.lsdService = lsdService;
	}


	public XsdService getXsdService() {
		return xsdService;
	}


	public void setXsdService(XsdService xsdService) {
		this.xsdService = xsdService;
	}


	public CgfkService getCgfkService() {
		return cgfkService;
	}


	public void setCgfkService(CgfkService cgfkService) {
		this.cgfkService = cgfkService;
	}

	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	public List getDckList() {
		return dckList;
	}


	public void setDckList(List dckList) {
		this.dckList = dckList;
	}


	public List getDrkList() {
		return drkList;
	}


	public void setDrkList(List drkList) {
		this.drkList = drkList;
	}


	public List getDspLsdList() {
		return dspLsdList;
	}


	public void setDspLsdList(List dspLsdList) {
		this.dspLsdList = dspLsdList;
	}


	public List getDspXsdList() {
		return dspXsdList;
	}


	public void setDspXsdList(List dspXsdList) {
		this.dspXsdList = dspXsdList;
	}


	public List getDspCgfkList() {
		return dspCgfkList;
	}


	public void setDspCgfkList(List dspCgfkList) {
		this.dspCgfkList = dspCgfkList;
	}


	public List getDspFysqList() {
		return dspFysqList;
	}


	public void setDspFysqList(List dspFysqList) {
		this.dspFysqList = dspFysqList;
	}


	public String getIsCkdRight() {
		return isCkdRight;
	}


	public void setIsCkdRight(String isCkdRight) {
		this.isCkdRight = isCkdRight;
	}


	public String getIsRkdRight() {
		return isRkdRight;
	}


	public void setIsRkdRight(String isRkdRight) {
		this.isRkdRight = isRkdRight;
	}


	public String getIsLsdSpRight() {
		return isLsdSpRight;
	}


	public void setIsLsdSpRight(String isLsdSpRight) {
		this.isLsdSpRight = isLsdSpRight;
	}


	public String getIsXsdSpRight() {
		return isXsdSpRight;
	}


	public void setIsXsdSpRight(String isXsdSpRight) {
		this.isXsdSpRight = isXsdSpRight;
	}


	public String getIsCgfkSpRight() {
		return isCgfkSpRight;
	}


	public void setIsCgfkSpRight(String isCgfkSpRight) {
		this.isCgfkSpRight = isCgfkSpRight;
	}


	public String getIsFysqSpRight() {
		return isFysqSpRight;
	}


	public void setIsFysqSpRight(String isFysqSpRight) {
		this.isFysqSpRight = isFysqSpRight;
	}


	public FysqService getFysqService() {
		return fysqService;
	}


	public void setFysqService(FysqService fysqService) {
		this.fysqService = fysqService;
	}
	
	public List getBwlList() {
		return bwlList;
	}


	public void setBwlList(List bwlList) {
		this.bwlList = bwlList;
	}
	
	public BwlService getBwlService() {
		return bwlService;
	}


	public void setBwlService(BwlService bwlService) {
		this.bwlService = bwlService;
	}


	public List getConfirmKfdbList() {
		return confirmKfdbList;
	}


	public void setConfirmKfdbList(List confirmKfdbList) {
		this.confirmKfdbList = confirmKfdbList;
	}


	public KfdbService getKfdbService() {
		return kfdbService;
	}


	public void setKfdbService(KfdbService kfdbService) {
		this.kfdbService = kfdbService;
	}
	
	public List getClientList() {
		return clientList;
	}

	public void setClientList(List clientList) {
		this.clientList = clientList;
	}
	
	public ClientWlStatService getClientWlStatService() {
		return clientWlStatService;
	}

	public void setClientWlStatService(ClientWlStatService clientWlStatService) {
		this.clientWlStatService = clientWlStatService;
	}	
	
}
