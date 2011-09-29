<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

ClientWlDzdService clientWlDzdService = (ClientWlDzdService)VS.findValue("clientWlDzdService");
YfHzMxService yfHzMxService = (YfHzMxService)VS.findValue("yfHzMxService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("client_name"));

String con = "";
con = "日期：" + start_date + "至" + end_date + "&nbsp;&nbsp; 客户名称：" + StaticParamDo.getClientNameById(client_name);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户应付对账单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url){
		var fea ='width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
</script>
</head>
<body align="center" >
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户应付对账单</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead>日期</TD>
			<TD class=ReportHead>业务类型</TD>
			<TD class=ReportHead>单据编号</TD>
			<TD class=ReportHead>应付款</TD>
			<TD class=ReportHead>付款</TD>
			<TD class=ReportHead>余额</TD>
		</TR>		
	</THEAD>
	<TBODY>
<%
//处理期初
Map qcMap = clientWlDzdService.getClientQcInfo(client_name,start_date);
double yfqc = 0;
if(qcMap != null){
	yfqc = qcMap.get("yfqc")==null?0:((Double)qcMap.get("yfqc")).doubleValue();
}
%>
		<tr>
			<TD class=ReportItemXH>期初</TD>
			<TD class=ReportItemXH>&nbsp;</TD>
			<TD class=ReportItemXH>&nbsp;</TD>	
			
			<TD class=ReportItemMoney><%=JMath.round(yfqc,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(yfqc,2) %>&nbsp;</TD>		
		</tr>
<%
double hj_yf = 0; //合计应付
double hj_fk = 0; //合计付款

double yf_ye = yfqc; //应付余额

List list = yfHzMxService.getYfDzd(client_name,start_date,end_date);
if(list != null && list.size()>0){
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		
		
		
		String creatdate = StringUtils.nullToStr(map.get("creatdate"));
		String xwtype = StringUtils.nullToStr(map.get("xwtype"));
		String dj_id = StringUtils.nullToStr(map.get("dj_id"));  //单据编号
		
		double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();
%>
		<TR>
			<TD class=ReportItemXH><%=creatdate %></TD>			
<%			
		String url = "";
		if(xwtype.equals("采购") || xwtype.equals("采购退货")){
			if(xwtype.equals("采购")){
				url = "viewJhd.html?id=" + dj_id;
			}
			if(xwtype.equals("采购退货")){
				url = "viewCgthd.html?id=" + dj_id;
			}
			hj_yf += je;
			yf_ye += je;
		%>
			<TD class=ReportItemXH><%=xwtype %></TD>
			<TD class=ReportItemXH><a href="#" onclick="openWin('<%=url %>');"><%=dj_id %></a></TD>
			
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(yf_ye,2) %>&nbsp;</TD>			
		<%			
		}else if(xwtype.equals("付款")){
			url = "viewCgfk.html?id=" + dj_id;
			hj_fk += je;
			yf_ye -= je;
		%>
			<TD class=ReportItemXH><%=xwtype %></TD>
			<TD class=ReportItemXH><a href="#" onclick="openWin('<%=url %>');"><%=dj_id %></a></TD>
			
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(yf_ye,2) %>&nbsp;</TD>
		<%
		}else if(xwtype.equals("往来调账(应付)")){
			url = "viewPz.html?id=" + dj_id;
			hj_yf += je;
			yf_ye += je;
		%>
		
			<TD class=ReportItemXH><%=xwtype %></TD>
			<TD class=ReportItemXH><a href="#" onclick="openWin('<%=url %>');"><%=dj_id %></a></TD>
			
			
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(yf_ye,2) %>&nbsp;</TD>				
		<%
		}

%>
		</TR>
<%
	}
}
%>
		<tr>
			<TD class=ReportItemXH style="font-weight:bold">合计：</TD>
			<TD class=ReportItem style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItem style="font-weight:bold">&nbsp;</TD>
			
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_yf,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_fk,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(yf_ye,2) %>&nbsp;</TD>		
		</tr>		
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：点击商品编号可查看原始单据列表。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>