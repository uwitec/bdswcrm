<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
try{
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

DlsDzdService dlsDzdService = (DlsDzdService)VS.findValue("dlsDzdService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String flag = StringUtils.nullToStr(request.getParameter("flag")); //显示明细

LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String client_name = info.getClient_name();

String con = "";
con = "日期：" + start_date + "至" + end_date;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>对账单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>对账单</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead rowspan="2">日期</TD>
			<TD class=ReportHead rowspan="2">业务类型</TD>
			<TD class=ReportHead rowspan="2">单据编号</TD>
			<TD class=ReportHead rowspan="2">商品名称</TD>
			<TD class=ReportHead rowspan="2">商品规格</TD>
			<TD class=ReportHead rowspan="2">数量</TD>									
			<TD class=ReportHead colspan="3">应付</TD>
		</TR>
		<TR>
			<TD class=ReportHead>应付款</TD>
			<TD class=ReportHead>付款</TD>
			<TD class=ReportHead>余额</TD>
		</TR>		
	</THEAD>
	<TBODY>
<%
//处理期初
Map qcMap = dlsDzdService.getDlsQcInfo(client_name,start_date);
double ysqc = 0;
if(qcMap != null){
	ysqc = qcMap.get("ysqc")==null?0:((Double)qcMap.get("ysqc")).doubleValue();
}
%>
		<tr>
			<TD class=ReportItem>期初</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>				
			<TD class=ReportItemMoney><%=JMath.round(ysqc,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ysqc,2) %>&nbsp;</TD>	
		</tr>
<%

double hj_ys = 0; //合计应付
double hj_sk = 0; //合计付款

double ys_ye = ysqc; //应收余额

List list = dlsDzdService.getDlsDzdList(start_date,end_date,client_name);
if(list != null && list.size()>0){
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		
		
		
		String creatdate = StringUtils.nullToStr(map.get("creatdate"));
		String xwtype = StringUtils.nullToStr(map.get("xwtype"));
		String dj_id = StringUtils.nullToStr(map.get("dj_id"));  //单据编号
		String cdid = StringUtils.nullToStr(map.get("cdid"));  //从单编号
		
		double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();
%>
		<TR>
			<TD class=ReportItem><%=creatdate %></TD>			
<%
		if(xwtype.equals("采购") || xwtype.equals("采购退货")){			
			hj_ys += je;
			ys_ye += je;
		%>
			<TD class=ReportItem><%=xwtype %></TD>
			<TD class=ReportItem><%=dj_id %></TD>
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
					
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ys_ye,2) %>&nbsp;</TD>
		<%			
		}else if(xwtype.equals("付款")){
			hj_sk += je;
			ys_ye -= je;
		%>
			<TD class=ReportItem><%=xwtype %></TD>
			<TD class=ReportItem><%=dj_id %></TD>
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>		
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ys_ye,2) %>&nbsp;</TD>
		<%
		}else if(xwtype.equals("调账")){
			hj_ys += je;
			ys_ye += je;
		%>
			<TD class=ReportItem><%=xwtype %></TD>
			<TD class=ReportItem><%=dj_id %></TD>
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
					
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ys_ye,2) %>&nbsp;</TD>	
		<%
		}

%>
		</TR>
	
<%
		if(flag.equals("是")){
			if(xwtype.equals("采购") || xwtype.equals("采购退货")){
				List mxList = dlsDzdService.getDjMxList(dj_id,cdid,xwtype);
				if(mxList != null && mxList.size()>0){
					for(int k=0;k<mxList.size();k++){
						Map mxMap = (Map)mxList.get(k);
						
						double xj = mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue();
						
						if(xwtype.equals("采购") || xwtype.equals("采购退货")){
%>
						<tr>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							
							<TD class=ReportItem><%=StringUtils.nullToStr(mxMap.get("product_name")) %>&nbsp;</TD>
							<TD class=ReportItem><%=StringUtils.nullToStr(mxMap.get("product_xh")) %>&nbsp;</TD>
							<TD class=ReportItemMoney><%=StringUtils.nullToStr(mxMap.get("nums")) %>&nbsp;</TD>									
							<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
							<TD class=ReportItemMoney>&nbsp;</TD>
							<TD class=ReportItemMoney>&nbsp;</TD>	
						</tr>
<%
							
						}
					}
				}
			}
		}
	}
}
%>
		<tr>
			<TD class=ReportItem style="font-weight:bold">合计：</TD>
			<TD class=ReportItem style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItem style="font-weight:bold">&nbsp;</TD>	

			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>	
							
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_ys,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_sk,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(ys_ye,2) %>&nbsp;</TD>	
		</tr>		
	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30"></td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="printDiv('divContent');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</div>
</body>
</html>
<%
}catch(Exception e){
	e.printStackTrace();
}
%>