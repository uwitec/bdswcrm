<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

XsmxReportService xsmxReportService = (XsmxReportService)VS.findValue("xsmxReportService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String xsry_id = StringUtils.nullToStr(request.getParameter("xsry_id"));
String dj_id = StringUtils.nullToStr(request.getParameter("dj_id"));
String dept_id = StringUtils.nullToStr(request.getParameter("dept_id"));

String flag = StringUtils.nullToStr(request.getParameter("flag")); //是否显示产品明细列表0:不显示；1：显示

String[] xwTypes = request.getParameterValues("xwType");   //查看的业务类型

//销售单列表
List xsdList = null;
if(StringUtils.isStrInArray(xwTypes,"1")){
	xsdList = xsmxReportService.getXsdList(start_date,end_date,client_name,xsry_id,dj_id,dept_id);
}
//零售单列表
List lsdList = null;
if(StringUtils.isStrInArray(xwTypes,"2")){
	lsdList = xsmxReportService.getLsdList(start_date,end_date,client_name,xsry_id,dj_id,dept_id);
}

//退货单列表
List thdList = null;
if(StringUtils.isStrInArray(xwTypes,"3")){
	thdList = xsmxReportService.getThdList(start_date,end_date,client_name,xsry_id,dj_id,dept_id);
}

String strCon = "";

strCon = "日期：" + start_date + "至" + end_date;

if(!dept_id.equals("")){
	strCon += "&nbsp;&nbsp;部门：" + StaticParamDo.getDeptNameById(dept_id);
}

if(!xsry_id.equals("")){
	strCon += "&nbsp;&nbsp;业务员：" + StaticParamDo.getRealNameById(xsry_id);
}
%>

<html>
<head>
<title>销售明细统计——统计结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
	
	function pageRefresh(){
		document.reportForm.submit();
	}
</script>
</head>
<body align="center" >
<form name="reportForm" action="getXsmxReportResult.html" method="post">
	<input type="hidden" name="start_date" value="<%=start_date %>">
	<input type="hidden" name="end_date" value="<%=end_date %>">
	<input type="hidden" name="client_name" value="<%=client_name %>">
	<input type="hidden" name="xsry_id" value="<%=xsry_id %>">
	<input type="hidden" name="dj_id" value="<%=dj_id %>">
	<input type="hidden" name="dept_id" value="<%=dept_id %>">
	<input type="hidden" name="flag" value="<%=flag %>">
	
	<%
	if(xwTypes != null && xwTypes.length > 0){
		for(int i=0;i<xwTypes.length;i++){
	%>
		<input type="hidden" name="xwType" value="<%=xwTypes[i] %>">	
	<%
		}
	}
	%>
	
</form>
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>销售明细统计报表</B></font><br><%=strCon %></TD>
		    <TD align="center"><input type="button" name="button_refresh" value=" 刷新 " onclick="pageRefresh();"></TD>
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
			<TD class=ReportHead>销售人员</TD>
			
			<TD class=ReportHead>产品名称</TD>
			<TD class=ReportHead>型号</TD>
			<TD class=ReportHead>单价</TD>
			<TD class=ReportHead>数量</TD>
			
			<TD class=ReportHead>金额</TD>		
		</TR>
	</THEAD>
	<TBODY>
<%
int hj_nums = 0;
double hj_je = 0;

//销售单列表及明细部分
int xsd_nums_xj = 0;
double xsd_je_xj = 0;

if(xsdList != null && xsdList.size()>0){
	for(int i=0;i<xsdList.size();i++){
		Map xsdMap = (Map)xsdList.get(i);
		
		
		
		String creatdate = StringUtils.nullToStr(xsdMap.get("creatdate"));
		String xsd_id = StringUtils.nullToStr(xsdMap.get("id"));
		String name = StringUtils.nullToStr(xsdMap.get("client_name"));
		String xsry = StaticParamDo.getRealNameById(StringUtils.nullToStr(xsdMap.get("fzr")));
		double xsdje = xsdMap.get("xsdje")==null?0:((Double)xsdMap.get("xsdje")).doubleValue();
		
%>
		<TR>
			<TD class=ReportItem><%=creatdate %></TD>
			<TD class=ReportItem>
				<a href="javascript:void(0);" onclick="openWin('viewXsd.html?id=<%=xsd_id %>');" title="点击查看原始单据"><%=xsd_id %></a>&nbsp;
			</TD>
			<TD class=ReportItem>销售单&nbsp;</TD>
			<TD class=ReportItem><%=name %>&nbsp;</TD>			
			<TD class=ReportItem><%=xsry %>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(xsdje,2) %>&nbsp;</TD>
		</TR>
	
<%
		xsd_je_xj += xsdje; //销售单合计金额

		if(flag.equals("1")){
			List mxList = xsmxReportService.getXsdMxList(xsd_id);
			
	
			if(mxList != null && mxList.size()>0){
				for(int k=0;k<mxList.size();k++){
					Map mxMap = (Map)mxList.get(k);	
					
					String product_name = StringUtils.nullToStr(mxMap.get("product_name"));
					String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
					
					double price = mxMap.get("price")==null?0:((Double)mxMap.get("price")).doubleValue();
					double jgtz = mxMap.get("jgtz")==null?0:((Double)mxMap.get("jgtz")).doubleValue();
					price = price + jgtz;  //实际售价等于 单价+价格调整
					
					int nums = 0;
					if(!"".equals(StringUtils.nullToStr(mxMap.get("sjcj_nums")))){
						nums = new Integer(StringUtils.nullToStr(mxMap.get("sjcj_nums"))).intValue();
					}
					
					double xj = mxMap.get("sjcj_xj")==null?0:((Double)mxMap.get("sjcj_xj")).doubleValue();
					
					xsd_nums_xj += nums;
					
	%>
			<TR>
				<TD class=ReportItem>&nbsp;</TD>
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
	String strNums = (xsd_nums_xj + "").equals("0")?"":xsd_nums_xj + "";
%>
		<TR>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>					
			<TD class=ReportItem style="font-weight:bold">销售订单小计（金额）&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=strNums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(xsd_je_xj,2) %>&nbsp;</TD>
		</TR>
<%
}
%>


<%
//零售单列表及明细部分
int lsd_nums_xj = 0;
double lsd_je_xj = 0;

if(lsdList != null && lsdList.size()>0){
	for(int i=0;i<lsdList.size();i++){
		Map map = (Map)lsdList.get(i);
		
		
		
		String creatdate = StringUtils.nullToStr(map.get("creatdate"));
		String id = StringUtils.nullToStr(map.get("id"));
		String name = StringUtils.nullToStr(map.get("client_name"));
		String xsry = StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("xsry")));
		double lsdje = map.get("lsdje")==null?0:((Double)map.get("lsdje")).doubleValue();
		
%>
		<TR>
			<TD class=ReportItem><%=creatdate %>&nbsp;</TD>
			<TD class=ReportItem><a href="javascript:void(0);" onclick="openWin('viewLsd.html?id=<%=id %>');" title="点击查看原始单据"><%=id %></a>&nbsp;</TD>
			<TD class=ReportItem>零售单&nbsp;</TD>
			<TD class=ReportItem><%=name %>&nbsp;</TD>			
			<TD class=ReportItem><%=xsry %>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(lsdje,2) %>&nbsp;</TD>
		</TR>
	
<%
		lsd_je_xj += lsdje;//零售单合计金额

		if(flag.equals("1")){
			List mxList = xsmxReportService.getLsdMxList(id);
			
	
			if(mxList != null && mxList.size()>0){
				for(int k=0;k<mxList.size();k++){
					Map mxMap = (Map)mxList.get(k);	
					
					String product_name = StringUtils.nullToStr(mxMap.get("product_name"));
					String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
					double price = mxMap.get("price")==null?0:((Double)mxMap.get("price")).doubleValue();
					int nums = new Integer(StringUtils.nullToStr(mxMap.get("nums"))).intValue();
					double xj = mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue();
					
					lsd_nums_xj += nums;
					
	%>
			<TR>
				<TD class=ReportItem>&nbsp;</TD>
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
	String strNums = (lsd_nums_xj + "").equals("0")?"":lsd_nums_xj + "";
%>
		<TR>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>					
			<TD class=ReportItem style="font-weight:bold">零售小计（金额）&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=strNums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(lsd_je_xj,2) %>&nbsp;</TD>
		</TR>
<%
}
%>



<%
//退货单列表及明细部分
int thd_nums_xj = 0;
double thd_je_xj = 0;

if(thdList != null && thdList.size()>0){
	for(int i=0;i<thdList.size();i++){
		Map map = (Map)thdList.get(i);
		
		
		
		String th_date = StringUtils.nullToStr(map.get("th_date"));
		String thd_id = StringUtils.nullToStr(map.get("thd_id"));
		String name = StringUtils.nullToStr(map.get("client_name"));
		String th_fzr = StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("th_fzr")));
		double thdje = 0 - (map.get("thdje")==null?0:((Double)map.get("thdje")).doubleValue());
		
%>
		<TR>
			<TD class=ReportItem><%=th_date %>&nbsp;</TD>
			<TD class=ReportItem><a href="javascript:void(0);" onclick="openWin('viewThd.html?thd_id=<%=thd_id %>');" title="点击查看原始单据"><%=thd_id %></a>&nbsp;</TD>
			<TD class=ReportItem>退货单&nbsp;</TD>
			<TD class=ReportItem><%=name %>&nbsp;</TD>			
			<TD class=ReportItem><%=th_fzr %>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(thdje,2) %>&nbsp;</TD>
		</TR>
	
<%
		thd_je_xj += thdje;

		if(flag.equals("1")){		

			List mxList = xsmxReportService.getThdMxList(thd_id);
			
	
			if(mxList != null && mxList.size()>0){
				for(int k=0;k<mxList.size();k++){
					Map mxMap = (Map)mxList.get(k);	
					
					String product_name = StringUtils.nullToStr(mxMap.get("product_name"));
					String product_xh = StringUtils.nullToStr(mxMap.get("product_xh"));
					double price = mxMap.get("th_price")==null?0:((Double)mxMap.get("th_price")).doubleValue();
					int nums = new Integer(StringUtils.nullToStr(mxMap.get("nums"))).intValue();
					double xj = 0 - (mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue());
					
					thd_nums_xj += nums;
					
	%>
			<TR>
				<TD class=ReportItem>&nbsp;</TD>
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
	String strNums = (thd_nums_xj + "").equals("0")?"":thd_nums_xj + "";
%>
		<TR>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>					
			<TD class=ReportItem style="font-weight:bold">退货小计（金额）</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=strNums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(thd_je_xj,2) %>&nbsp;</TD>
		</TR>
<%
}

hj_nums = xsd_nums_xj + lsd_nums_xj + thd_nums_xj;
hj_je = xsd_je_xj + lsd_je_xj + thd_je_xj;

String strNums = (hj_nums + "").equals("0")?"":hj_nums + "";
%>

		<TR>
			<TD class=ReportItem style="font-weight:bold">合计（金额）</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>		
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=strNums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<table width="100%">
		<tr>
			<td width="70%" height="30">注：点击单据编号可以查看原始单据。</td>
			<td align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
	<input type="button" name="button_refresh" value=" 刷 新 " onclick="pageRefresh();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showXsmxReportCondition.html';"> 
</center>
</body>
</html>