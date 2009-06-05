<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XstjXsryService xstjXsryService = (XstjXsryService)VS.findValue("xstjXsryService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String dj_id = StringUtils.nullToStr(request.getParameter("dj_id"));
String isShowZ = StringUtils.nullToStr(request.getParameter("isShowZ"));         //是否显示销售额为零客户

String[] xwTypes = request.getParameterValues("xwType");   //查看的业务类型

//销售人员列表
List userList = (List)VS.findValue("userList");


%>

<html>
<head>
<title>业务员销售毛利明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url,winTitle){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,winTitle,fea);	
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>业务员销售毛利明细</B></font><br>日期：<%=start_date %> 至 <%=end_date %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>日期</TD>
			<TD class=ReportHead>单据号</TD>
			<TD class=ReportHead>业务类型</TD>
			<TD class=ReportHead>客户</TD>
			<TD class=ReportHead>产品名称</TD>
			<TD class=ReportHead>型号</TD>
			<TD class=ReportHead>数量</TD>
			
			<TD class=ReportHead>单价</TD>
			<TD class=ReportHead>销售收入</TD>
			
			<TD class=ReportHead>单位成本</TD>
			<TD class=ReportHead>成本</TD>
			
			<TD class=ReportHead>毛利</TD>	
			<TD class=ReportHead>毛利率</TD>	
		</TR>
	</THEAD>
	<TBODY>
<%

if(userList != null && userList.size()>0){
	
	for(int i=0;i<userList.size();i++){
		
		Map map = (Map)userList.get(i);
		
		String user_id = StringUtils.nullToStr(map.get("user_id"));
		String real_name = StringUtils.nullToStr(map.get("real_name"));
		
		//销售单列表
		List xsdList = null;

		if(StringUtils.isStrInArray(xwTypes,"1")){
			xsdList = xstjXsryService.getXsdList(start_date,end_date,user_id,client_name,dj_id);
		}
		
		//零售单列表
		List lsdList = null;
		if(StringUtils.isStrInArray(xwTypes,"2")){
			lsdList = xstjXsryService.getLsdList(start_date,end_date,user_id,client_name,dj_id);
		}
		
		//退货单列表
		List thdList = null;
		if(StringUtils.isStrInArray(xwTypes,"3")){
			thdList = xstjXsryService.getThdList(start_date,end_date,user_id,client_name,dj_id);
		}
		
		boolean hasXs = false;
		boolean hasLs = false;
		boolean hasTh = false;
		
		if(xsdList != null && xsdList.size()>0){
			hasXs = true;
		}
		if(lsdList != null && lsdList.size()>0){
			hasLs = true;
		}
		if(thdList != null && thdList.size()>0){
			hasTh = true;
		}
		
		if(hasXs || hasLs || hasTh || isShowZ.equals("是")){
			
			int xs_nums_zj = 0;  //数量总计
			double xssr_zj = 0; //销售收入总计
			double cb_zj = 0;  //成本总计
			double ml_zj = 0;  //毛利总计
		
%>
			<TR>
				<TD class=ReportItem colspan="13">业务人员姓名：<%=real_name %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业务人员编号：<%=user_id %></TD>
				
			</TR>
			
<%
	
			
			//销售单列表及明细部分		
			if(xsdList != null && xsdList.size()>0){
				for(int k=0;k<xsdList.size();k++){
					Map xsdMap = (Map)xsdList.get(k);
					
					
					
					String creatdate = StringUtils.nullToStr(xsdMap.get("creatdate"));
					String xsd_id = StringUtils.nullToStr(xsdMap.get("id"));
					String name = StaticParamDo.getClientNameById(StringUtils.nullToStr(xsdMap.get("client_name")));
			
%>
					<TR>
						<TD class=ReportItem><%=creatdate %>&nbsp;</TD>
						<TD class=ReportItem>
							<a href="#" onclick="openWin('viewXsd.html?id=<%=xsd_id %>','销售单');" title="点击查看原始单据"><%=xsd_id %></a>&nbsp;
						</TD>
						<TD class=ReportItem>销售单&nbsp;</TD>
						<TD class=ReportItem><%=name %>&nbsp;</TD>
		
<%
	
					List mxList = xstjXsryService.getXsdMxList(xsd_id);
					
			
					if(mxList != null && mxList.size()>0){
						int xs_nums_xj = 0;  //数量小计
						double xssr_xj = 0; //销售收入小计
						double cb_xj = 0;  //成本小计
						double ml_xj = 0;  //毛利小计
						for(int l=0;l<mxList.size();l++){
							Map mxMap = (Map)mxList.get(l);	
							
							String product_name = StringUtils.nullToStr(mxMap.get("product_name"));
							String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
							
							double price = mxMap.get("price")==null?0:((Double)mxMap.get("price")).doubleValue(); //单价
							double jgtz = mxMap.get("jgtz")==null?0:((Double)mxMap.get("jgtz")).doubleValue();
							price = price + jgtz;  //实际售价等于 单价+价格调整
							
							double dwcbj = mxMap.get("cbj")==null?0:((Double)mxMap.get("cbj")).doubleValue(); //单位成本
							
							int nums = new Integer(StringUtils.nullToStr(mxMap.get("sjcj_nums"))).intValue(); //数量
							double cb = dwcbj * nums;  //成本						
							
							//如果是服务/劳务类商品，成本计作0
							String prop = StringUtils.nullToStr(mxMap.get("prop"));
							if(prop.equals("服务/劳务")){
								cb = 0;
								dwcbj = 0;
							}
							
							double xj = mxMap.get("sjcj_xj")==null?0:((Double)mxMap.get("sjcj_xj")).doubleValue();   //销售收入
							double ml = xj - cb;   //毛利
							
							xs_nums_xj += nums;
							xssr_xj += xj;
							cb_xj += cb;
							ml_xj += ml;
							
							
							
							if(l == 0){
%>				
								<TD class=ReportItem><%=product_name %>&nbsp;</TD>
								<TD class=ReportItem><%=product_xh %>&nbsp;</TD>								
								<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(dwcbj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(cb,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(ml,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.percent(ml,xj) %>&nbsp;</TD>
							</TR>
<%								
							}else{
%>
							<TR>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem><%=product_name %>&nbsp;</TD>
								<TD class=ReportItem><%=product_xh %>&nbsp;</TD>								
								<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(dwcbj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(cb,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(ml,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.percent(ml,xj) %>&nbsp;</TD>
							</TR>
<%								
							}	
						}
						if(mxList.size()>1){  //如果明细信息多于一行时，添加一个小计
%>
							<TR>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem style="font-weight:bold">小计:&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>								
								<TD class=ReportItemMoney style="font-weight:bold"><%=xs_nums_xj %>&nbsp;</TD>
								<TD class=ReportItemMoney>&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(xssr_xj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney>&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(cb_xj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(ml_xj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.percent(ml_xj,xssr_xj) %>&nbsp;</TD>
							</TR>
<%	
						}
						
						xs_nums_zj += xs_nums_xj;
						xssr_zj += xssr_xj;
						cb_zj += cb_xj;
						ml_zj += ml_xj;
					}
					
				}
			}
%>
	
	
<%
	
			//零售单列表及明细部分			
			if(lsdList != null && lsdList.size()>0){
				for(int k=0;k<lsdList.size();k++){
					Map lsdMap = (Map)lsdList.get(k);
									
					String creatdate = StringUtils.nullToStr(lsdMap.get("creatdate"));
					String id = StringUtils.nullToStr(lsdMap.get("id"));
					String name = StringUtils.nullToStr(lsdMap.get("client_name"));
%>
							<TR>
								<TD class=ReportItem><%=creatdate %>&nbsp;</TD>
								<TD class=ReportItem><a  href="#" onclick="openWin('viewLsd.html?id=<%=id %>');" title="点击查看原始单据"><%=id %></a>&nbsp;</TD>
								<TD class=ReportItem>零售单&nbsp;</TD>
								<TD class=ReportItem><%=name %>&nbsp;</TD>
<%
	
					List mxList = xstjXsryService.getLsdMxList(id);
					
			
					if(mxList != null && mxList.size()>0){
						
						int xs_nums_xj = 0;  //数量小计
						double xssr_xj = 0; //销售收入小计
						double cb_xj = 0;  //成本小计
						double ml_xj = 0;  //毛利小计
						
						for(int l=0;l<mxList.size();l++){
							Map mxMap = (Map)mxList.get(l);	
							
							String product_name = StringUtils.nullToStr(mxMap.get("product_name"));
							String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
							double price = mxMap.get("price")==null?0:((Double)mxMap.get("price")).doubleValue(); //单价
							int nums = new Integer(StringUtils.nullToStr(mxMap.get("nums"))).intValue();   //数量
							double xj = mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue();  //销售收入
							double dwcb = mxMap.get("cbj")==null?0:((Double)mxMap.get("cbj")).doubleValue();  //单位成本
							double cb = dwcb * nums;  //成本
							
							//如果是服务/劳务类商品，成本计作0
							String prop = StringUtils.nullToStr(mxMap.get("prop"));
							if(prop.equals("服务/劳务")){
								cb = 0;
								dwcb = 0;
							}
							
							double ml = xj - cb;      //毛利
							
							xs_nums_xj += nums;
							xssr_xj += xj;
							cb_xj += cb;
							ml_xj += ml;
							
							if(l == 0){
%>
								<TD class=ReportItem><%=product_name %>&nbsp;</TD>
								<TD class=ReportItem><%=product_xh %>&nbsp;</TD>								
								<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
								
								<TD class=ReportItemMoney><%=JMath.round(dwcb,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(cb,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(ml,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.percent(ml,xj) %>&nbsp;</TD>
							</TR>
<%								
							}else{
%>
							<TR>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>					
								<TD class=ReportItem><%=product_name %>&nbsp;</TD>
								<TD class=ReportItem><%=product_xh %>&nbsp;</TD>								
								<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
								
								<TD class=ReportItemMoney><%=JMath.round(dwcb,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(cb,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(ml,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.percent(ml,xj) %>&nbsp;</TD>								
							</TR>
<%									
							}			
						}
						if(mxList.size()>1){  //如果明细信息多于一行时，添加一个小计
%>
							<TR>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem style="font-weight:bold">小计:&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>								
								<TD class=ReportItemMoney style="font-weight:bold"><%=xs_nums_xj %>&nbsp;</TD>
								<TD class=ReportItemMoney>&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(xssr_xj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney>&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(cb_xj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(ml_xj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.percent(ml_xj,xssr_xj) %>&nbsp;</TD>
							</TR>
<%	
						}
						xs_nums_zj += xs_nums_xj;
						xssr_zj += xssr_xj;
						cb_zj += cb_xj;
						ml_zj += ml_xj;
					}
				}
			}
%>
	
	
	
<%

	
			//退货单列表及明细部分
			//退货单退货成本目前取的是退货时库存
			//退货不计算毛利和毛利率,但影响最终的毛利及毛利率
			if(thdList != null && thdList.size()>0){
				for(int k=0;k<thdList.size();k++){
					Map thdMap = (Map)thdList.get(k);
					
					
					
					String th_date = StringUtils.nullToStr(thdMap.get("th_date"));
					String thd_id = StringUtils.nullToStr(thdMap.get("thd_id"));
					String name = StaticParamDo.getClientNameById(StringUtils.nullToStr(thdMap.get("client_name")));

%>
					<TR>
						<TD class=ReportItem><%=th_date %></TD>
						<TD class=ReportItem><a  href="#" onclick="openWin('viewThd.html?thd_id=<%=thd_id %>');" title="点击查看原始单据"><%=thd_id %></a></TD>
						<TD class=ReportItem>退货单</TD>
						<TD class=ReportItem><%=name %></TD>
		
<%	
					List mxList = xstjXsryService.getThdMxList(thd_id);
					
			
					if(mxList != null && mxList.size()>0){
						
						int xs_nums_xj = 0;  //数量小计
						double xssr_xj = 0; //销售收入小计
						double cb_xj = 0;  //成本小计
						double ml_xj = 0;  //毛利小计
						
						for(int l=0;l<mxList.size();l++){
							Map mxMap = (Map)mxList.get(l);	
							
							String product_name = StringUtils.nullToStr(mxMap.get("product_name"));
							String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
							double price = 0 - (mxMap.get("th_price")==null?0:((Double)mxMap.get("th_price")).doubleValue()); //单价
							int nums = 0 - new Integer(StringUtils.nullToStr(mxMap.get("nums"))).intValue();  //数量
							double xj = 0 - (mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue());	 //销售收入	
							double dwcb = 0 - (mxMap.get("cbj")==null?0:((Double)mxMap.get("cbj")).doubleValue());	 //单位成本
							double cb = 0 -( dwcb * nums);
							
							//如果是服务/劳务类商品，成本计作0
							String prop = StringUtils.nullToStr(mxMap.get("prop"));
							if(prop.equals("服务/劳务")){
								cb = 0;
								dwcb = 0;
							}
							
							double ml = xj - cb;
							
							xs_nums_xj += nums;
							xssr_xj += xj;
							cb_xj += cb;
							ml_xj += ml;
							
							if(l == 0){
%>
								<TD class=ReportItem><%=product_name %>&nbsp;</TD>
								<TD class=ReportItem><%=product_xh %>&nbsp;</TD>								
								<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
							
								<TD class=ReportItemMoney><%=JMath.round(dwcb,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(cb,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(ml,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.percent(ml,xj) %>&nbsp;</TD>									
							</TR>
<%		
							}else{
%>
							<TR>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>					
								<TD class=ReportItem><%=product_name %>&nbsp;</TD>
								<TD class=ReportItem><%=product_xh %>&nbsp;</TD>								
								<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
						
								<TD class=ReportItemMoney><%=JMath.round(dwcb,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(cb,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.round(ml,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney><%=JMath.percent(ml,xj) %>&nbsp;</TD>									
							</TR>
<%										
							}
						}
						if(mxList.size()>1){  //如果明细信息多于一行时，添加一个小计
%>
							<TR>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>
								<TD class=ReportItem style="font-weight:bold">小计:&nbsp;</TD>
								<TD class=ReportItem>&nbsp;</TD>								
								<TD class=ReportItemMoney style="font-weight:bold"><%=xs_nums_xj %>&nbsp;</TD>
								<TD class=ReportItemMoney>&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(xssr_xj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney>&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(cb_xj,2) %>&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
								<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
							</TR>
<%	
						}
						xs_nums_zj += xs_nums_xj;
						xssr_zj += xssr_xj;
						cb_zj += cb_xj;
						ml_zj += ml_xj;
					}
				}
			}
%>
			<TR>
				<TD class=ReportItem>&nbsp;</TD>
				<TD class=ReportItem>&nbsp;</TD>
				<TD class=ReportItem>&nbsp;</TD>
				<TD class=ReportItem>&nbsp;</TD>
				<TD class=ReportItem style="font-weight:bold">销售人员总计:&nbsp;</TD>
				<TD class=ReportItem>&nbsp;</TD>								
				<TD class=ReportItemMoney style="font-weight:bold"><%=xs_nums_zj %>&nbsp;</TD>
				<TD class=ReportItemMoney>&nbsp;</TD>
				<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(xssr_zj,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney>&nbsp;</TD>
				<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(cb_zj,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(ml_zj,2) %>&nbsp;</TD>
				<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.percent(ml_zj,xssr_zj) %>&nbsp;</TD>
			</TR>	
<%
		}		
	}
}
%>

	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击单据编号可以查看原始单据。</td>
			<td colspan="3" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>