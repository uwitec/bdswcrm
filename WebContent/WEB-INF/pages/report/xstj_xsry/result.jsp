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
String flag = StringUtils.nullToStr(request.getParameter("flag")); //是否显示商品明细列表0:不显示；1：显示

String[] xwTypes = request.getParameterValues("xwType");   //查看的业务类型

//销售人员列表
List userList = (List)VS.findValue("userList");


%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>业务员销售汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
	function openWin(url,winTitle){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,winTitle,fea);	
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>业务员销售汇总</B></font><br>日期：<%=start_date %> 至 <%=end_date %></TD>
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
			<TD class=ReportHead>商品名称</TD>
			<TD class=ReportHead>型号</TD>
			<TD class=ReportHead>单价</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>金额</TD>	
		</TR>
	</THEAD>
	<TBODY>
<%

if(userList != null && userList.size()>0){
	
	double hj_xsje = 0;
	int hj_xxs = 0;
	
	for(int i=0;i<userList.size();i++){
		
		double xsry_xsje_zj = 0;
		int xsry_xss_zj = 0;
		
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
		
%>
			<TR>
				<TD class=ReportItem colspan="9" style="font-weight:bold">
				销售人员编号：<%=user_id %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				销售人员姓名：<%=real_name %>
				</TD>
			</TR>
			
<%
	
			
			//销售单列表及明细部分
			double xsd_je_xj = 0;
			
			if(xsdList != null && xsdList.size()>0){
				for(int k=0;k<xsdList.size();k++){
					Map xsdMap = (Map)xsdList.get(k);
					
					
					
					String creatdate = StringUtils.nullToStr(xsdMap.get("creatdate"));
					String xsd_id = StringUtils.nullToStr(xsdMap.get("id"));
					String name = StringUtils.nullToStr(xsdMap.get("client_name"));
					double xsdje = xsdMap.get("sjcjje")==null?0:((Double)xsdMap.get("sjcjje")).doubleValue();
					
					xsry_xsje_zj += xsdje;
			
%>
					<TR>
						<TD class=ReportItem><%=creatdate %></TD>
						<TD class=ReportItem>
							<a href="javascript:void(0);" onclick="openWin('viewXsd.html?id=<%=xsd_id %>','销售单');" title="点击查看原始单据"><%=xsd_id %></a>&nbsp;
						</TD>
						<TD class=ReportItem>销售单&nbsp;</TD>
						<TD class=ReportItem><%=name %></TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItemMoney><%=JMath.round(xsdje,2) %></TD>
					</TR>
		
<%
					xsd_je_xj += xsdje; //销售单合计金额
	
					if(flag.equals("1")){
						List mxList = xstjXsryService.getXsdMxList(xsd_id);
						
				
						if(mxList != null && mxList.size()>0){
							for(int l=0;l<mxList.size();l++){
								Map mxMap = (Map)mxList.get(l);	
								
								String product_name = StringUtils.nullToStr(mxMap.get("product_name"));
								String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
								
								double price = mxMap.get("price")==null?0:((Double)mxMap.get("price")).doubleValue();
								double jgtz = mxMap.get("jgtz")==null?0:((Double)mxMap.get("jgtz")).doubleValue();
								price = price + jgtz;  //实际售价等于 单价+价格调整
								
								int nums = new Integer((StringUtils.nullToStr(mxMap.get("sjcj_nums"))).equals("")?"0":(StringUtils.nullToStr(mxMap.get("sjcj_nums")))).intValue();
								double xj = mxMap.get("sjcj_xj")==null?0:((Double)mxMap.get("sjcj_xj")).doubleValue();	
								
								xsry_xss_zj += nums;
%>
								<TR>
									<TD class=ReportItem>&nbsp;</TD>
									<TD class=ReportItem>&nbsp;</TD>
									<TD class=ReportItem>&nbsp;</TD>
									<TD class=ReportItem>&nbsp;</TD>					
									<TD class=ReportItem><%=product_name %>&nbsp;</TD>
									<TD class=ReportItem><%=product_xh %>&nbsp;</TD>
									<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
									<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
									<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
								</TR>
<%				
							}
						}
					}
%>
	
<%
				}
			}
%>
	
	
<%
	
			//零售单列表及明细部分
			double lsd_je_xj = 0;
			
			if(lsdList != null && lsdList.size()>0){
				for(int k=0;k<lsdList.size();k++){
					Map lsdMap = (Map)lsdList.get(k);
									
					String creatdate = StringUtils.nullToStr(lsdMap.get("creatdate"));
					String id = StringUtils.nullToStr(lsdMap.get("id"));
					String name = StringUtils.nullToStr(lsdMap.get("client_name"));
					double lsdje = lsdMap.get("lsdje")==null?0:((Double)lsdMap.get("lsdje")).doubleValue();
			
					xsry_xsje_zj += lsdje;
%>
					<TR>
						<TD class=ReportItem><%=creatdate %>&nbsp;</TD>
						<TD class=ReportItem><a href="javascript:openWin('viewLsd.html?id=<%=id %>');" title="点击查看原始单据"><%=id %></a>&nbsp;</TD>
						<TD class=ReportItem>零售单&nbsp;</TD>
						<TD class=ReportItem><%=name %>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItemMoney><%=JMath.round(lsdje,2) %>&nbsp;</TD>
					</TR>
		
<%
					lsd_je_xj += lsdje; //零售单合计金额
	
					if(flag.equals("1")){
						List mxList = xstjXsryService.getLsdMxList(id);
						
				
						if(mxList != null && mxList.size()>0){
							for(int l=0;l<mxList.size();l++){
								Map mxMap = (Map)mxList.get(l);	
								
								String product_name = StringUtils.nullToStr(mxMap.get("product_name"));
								String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
								double price = mxMap.get("price")==null?0:((Double)mxMap.get("price")).doubleValue();
								int nums = new Integer(StringUtils.nullToStr(mxMap.get("nums"))).intValue();
								double xj = mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue();
								
								xsry_xss_zj += nums;
%>
						<TR>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>					
							<TD class=ReportItem><%=product_name %>&nbsp;</TD>
							<TD class=ReportItem><%=product_xh %>&nbsp;</TD>
							<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
							<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
							<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
						</TR>
<%				
							}
						}
					}
%>
	
<%
				}
			}
%>
	
	
	
<%

	
			//退货单列表及明细部分
			double thd_je_xj = 0;
			
			if(thdList != null && thdList.size()>0){
				for(int k=0;k<thdList.size();k++){
					Map thdMap = (Map)thdList.get(k);
					
					
					
					String th_date = StringUtils.nullToStr(thdMap.get("th_date"));
					String thd_id = StringUtils.nullToStr(thdMap.get("thd_id"));
					String name = StringUtils.nullToStr(thdMap.get("client_name"));
					double thdje = 0 - (thdMap.get("thdje")==null?0:((Double)thdMap.get("thdje")).doubleValue());
			
					xsry_xsje_zj += thdje;
%>
					<TR>
						<TD class=ReportItem><%=th_date %>&nbsp;</TD>
						<TD class=ReportItem><a href="javascript:openWin('viewThd.html?thd_id=<%=thd_id %>');" title="点击查看原始单据"><%=thd_id %></a>&nbsp;</TD>
						<TD class=ReportItem>退货单&nbsp;</TD>
						<TD class=ReportItem><%=name %>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItemMoney><%=JMath.round(thdje,2) %></TD>
					</TR>
		
<%
					thd_je_xj += thdje; //退货单合计金额
	
					if(flag.equals("1")){
						List mxList = xstjXsryService.getThdMxList(thd_id);
						
				
						if(mxList != null && mxList.size()>0){
							for(int l=0;l<mxList.size();l++){
								Map mxMap = (Map)mxList.get(l);	
								
								String product_name = StringUtils.nullToStr(mxMap.get("product_name"));
								String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
								double price = mxMap.get("th_price")==null?0:((Double)mxMap.get("th_price")).doubleValue();
								int nums = new Integer(StringUtils.nullToStr(mxMap.get("nums"))).intValue();
								double xj = 0 - (mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue());		
								
								xsry_xss_zj += nums;
%>
						<TR>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>					
							<TD class=ReportItem><%=product_name %>&nbsp;</TD>
							<TD class=ReportItem><%=product_xh %>&nbsp;</TD>
							<TD class=ReportItemMoney><%=JMath.round(price,2) %>&nbsp;</TD>
							<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
							<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
						</TR>
<%				
							}
						}
					}
				}
			}
			
			String strNums = (xsry_xss_zj+"").equals("0")?"":(xsry_xss_zj+"");
%>
	
						<TR>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>											
							<TD class=ReportItem style="font-weight:bold">总计（金额）&nbsp;</TD>						
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItemMoney>&nbsp;</TD>
							<TD class=ReportItemMoney style="font-weight:bold"><%=strNums %>&nbsp;</TD>
							<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(xsry_xsje_zj,2) %>&nbsp;</TD>
						</TR>
	
<%
			hj_xsje += xsry_xsje_zj;
			hj_xxs += xsry_xss_zj;
		}
	}
	
	String strNums = (hj_xxs+"").equals("0")?"":(hj_xxs+"");

%>

					<TR>
						<TD class=ReportItem style="font-weight:bold">合计（金额）</TD>		
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItem>&nbsp;</TD>															
						<TD class=ReportItem>&nbsp;</TD>
						<TD class=ReportItemMoney>&nbsp;</TD>
						<TD class=ReportItemMoney style="font-weight:bold"><%=strNums %>&nbsp;</TD>
						<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_xsje,2) %>&nbsp;</TD>
					</TR>

<%
}
%>


	</TBODY>
</TABLE>
<table width="100%">
		<tr>
			<td width="60%" height="30">注：点击单据编号可以查看原始单据。</td>
			<td align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<br>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>