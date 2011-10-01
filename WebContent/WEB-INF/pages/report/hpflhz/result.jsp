<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List resultList = (List)VS.findValue("resultList");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String dj = StringUtils.nullToStr(request.getParameter("dj"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
String xsry = StringUtils.nullToStr(request.getParameter("xsry"));
String dept = StringUtils.nullToStr(request.getParameter("dept"));
String[] xwType = request.getParameterValues("xwType");

String con = "日期：" + start_date + "至" + end_date;

if(!dept.equals("")){
	con += "&nbsp; 部门：" + StaticParamDo.getDeptNameById(dept);
}
if(!xsry.equals("")){
	con += "&nbsp; 销售人员：" + StaticParamDo.getRealNameById(xsry);
}
if(!client_name.equals("")){
	con += "&nbsp; 客户名称：" + StaticParamDo.getClientNameById(client_name);
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>货品销售分类汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(product_id,client_name,xsry_id){
		document.myform.product_id.value = product_id;
		document.myform.submit();
	}
	function pageRefresh(){
		document.refreshForm.submit();
	}
	function openMx(product_kind,start_date,end_date,client_name,xsry,dept){
		location.href="getHpflMxResult.html?product_kind=" + product_kind + "&start_date=" + start_date + "&end_date=" + end_date + "&client_name=" + client_name + "&xsry=" + xsry + "&dept=" + dept;
	}
	function onloadTotal(){
		var objs = document.getElementsByName("fz_vl");

		var hjml_vl = parseFloat(document.getElementById("fm").value);
		
		for(var i=0;i<objs.length;i++){
			var objId = objs[i].id;
			var arryTemp = objId.split("_");
			
			var zbId = "zb_" + arryTemp[1];
			try{
				document.getElementById(zbId).innerText = ((parseFloat(document.getElementById(objId).value)/hjml_vl*100).toFixed(2)) + "%";
			}catch(e){
				document.getElementById(zbId).innerText = "0.00%";
			}
		}
	}
</script>
</head>
<body align="center" onload="onloadTotal();">
<div class="rightContentDiv" id="divContent">
<form name="refreshForm" action="getHpflHzResult.html" method="post">
<input type="hidden" name="client_name" value="<%=client_name %>">
<input type="hidden" name="start_date" value="<%=start_date %>">
<input type="hidden" name="end_date" value="<%=end_date %>">
<input type="hidden" name="xsry" value="<%=xsry %>">
<input type="hidden" name="dj" value="<%=dj %>">
<%
if(xwType != null && xwType.length > 0){
	for(int i=0;i<xwType.length;i++){
%>
<input type="hidden" name="xwType" value="<%=xwType[i] %>">
<%		
	}
}
%>
</form>
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>货品销售分类汇总</B></font><br><%=con %></TD>
		    <TD align="center">
		    	<input type="button" name="btnRes" value="刷新" onclick="pageRefresh();">
		    </TD>		    
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>商品类别名称</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>金额</TD>
			<TD class=ReportHead>占比</TD>			
		</TR>
	</THEAD>
	<TBODY>
<%
int hj_nums = 0;    //合计数量
double hj_je = 0;   //合计金额

if(resultList != null && resultList.size()>0){
	for(int i=0;i<resultList.size();i++){
		Map map = (Map)resultList.get(i);
		
		String id = StringUtils.nullToStr(map.get("id"));
		String name = StringUtils.nullToStr(map.get("name"));
		
		String strBlank = "";
		for(int k=0;k<id.length()/2;k++){
			strBlank += "　　　";
		}
		
		double je = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		
		String strNums = StringUtils.nullToStr(map.get("nums"));		
		int nums = 0;
		if(!strNums.equals("")){
			nums = new Integer(strNums).intValue();
		}
		
		if(id.length() == 2){
			hj_nums += nums;
			hj_je += je;	
		}
		
		String stl = "BACKGROUND-COLOR: #FFFFFF";
		if(id.length() == 2){
			stl = "BACKGROUND-COLOR: #E8E8E8";
		}else if(id.length() == 4){
			stl = "BACKGROUND-COLOR: #FBFBFB";
		}
		
		if(nums != 0 || je != 0){
%>
		<TR style="<%=stl %>">
			<TD class=ReportItem><%=strBlank %><a href="javascript:openMx('<%=id %>','<%=start_date %>','<%=end_date %>','<%=client_name %>','<%=xsry %>','<%=dept %>');"><%=name %></a>&nbsp;</TD>
			<TD class=ReportItemMoney><%=nums %>&nbsp;</TD>
			<TD class=ReportItemMoney><input type="hidden" name="fz_vl" id="fz_<%=id %>" value="<%=je %>"><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><span id="zb_<%=id %>"></span>&nbsp;</TD>
		</TR>
	
<%
		}
	}
}
%>
		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合计</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=hj_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><input type="hidden" name="fm" id="fm" value="<%=hj_je %>"><%=JMath.round(hj_je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">--&nbsp;</TD>
		</TR>
		
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：类别名称可查看明细信息。</td>
			<td align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='showHpflHzCon.html';"> 
</center>
</div>
</body>
</html>