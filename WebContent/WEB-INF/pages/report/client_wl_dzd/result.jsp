<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

ClientWlDzdService clientWlDzdService = (ClientWlDzdService)VS.findValue("clientWlDzdService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String client_name = StringUtils.nullToStr(request.getParameter("clientName"));
String flag = StringUtils.nullToStr(request.getParameter("flag")); //显示明细

String con = "";
con = "日期：" + start_date + "至" + end_date + "&nbsp;&nbsp; 客户名称：" + StaticParamDo.getClientNameById(client_name);

%>
<html>
<head>
<title>客户往来对账单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
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
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>客户往来对账单</B></font><br><%=con %></TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead rowspan="2">日期</TD>
			<TD class=ReportHead rowspan="2">业务类型</TD>
			<TD class=ReportHead rowspan="2">单据编号</TD>
			<TD class=ReportHead rowspan="2">商品名称</TD>
			<TD class=ReportHead rowspan="2">商品规格</TD>
			<TD class=ReportHead rowspan="2">数量</TD>	
			<TD class=ReportHead rowspan="2">价格</TD>								
			<TD class=ReportHead colspan="3">应收</TD>
			<TD class=ReportHead colspan="3">应付</TD>	
		</TR>
		<TR>
			<TD class=ReportHead>应收款</TD>
			<TD class=ReportHead>收款</TD>
			<TD class=ReportHead>余额</TD>
			<TD class=ReportHead>应付款</TD>
			<TD class=ReportHead>付款</TD>
			<TD class=ReportHead>余额</TD>	
		</TR>		
	</THEAD>
	<TBODY>
<%
//处理期初
Map qcMap = clientWlDzdService.getClientQcInfo(client_name,start_date);
double ysqc = 0;
double yfqc = 0;
if(qcMap != null){
	ysqc = qcMap.get("ysqc")==null?0:((Double)qcMap.get("ysqc")).doubleValue();
	yfqc = qcMap.get("yfqc")==null?0:((Double)qcMap.get("yfqc")).doubleValue();
}
%>
		<tr>
			<TD class=ReportItem>期初</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>	
			<TD class=ReportItem>&nbsp;</TD>			
			<TD class=ReportItemMoney><%=JMath.round(ysqc,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ysqc,2) %>&nbsp;</TD>
			
			<TD class=ReportItemMoney><%=JMath.round(yfqc,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(yfqc,2) %>&nbsp;</TD>		
		</tr>
<%

double hj_ys = 0; //合计应收
double hj_sk = 0; //合计收款

double ys_ye = ysqc; //应收余额

double hj_yf = 0; //合计应付
double hj_fk = 0; //合计付款

double yf_ye = yfqc; //应付余额

List list = clientWlDzdService.getClientWlDzdList(start_date,end_date,client_name);
if(list != null && list.size()>0){
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		
		
		
		String creatdate = StringUtils.nullToStr(map.get("creatdate"));
		String xwtype = StringUtils.nullToStr(map.get("xwtype"));
		String dj_id = StringUtils.nullToStr(map.get("dj_id"));  //单据编号
		
		double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();

		if(flag.equals("是"))
		{		
%>
		<TR style="BACKGROUND-COLOR: #E8E8E8">
		<% 
		}
		else
		{
		%>
		<TR style="BACKGROUND-COLOR: #FFFFFF">
	    <% 
	    }
	    %>
			<TD class=ReportItem><%=creatdate %></TD>			
<%			
		String url = "";
		if(xwtype.equals("销售") || xwtype.equals("销售退货")){
			if(xwtype.equals("销售")){
				url = "viewXsd.html?id=" + dj_id;
			}
			if(xwtype.equals("销售退货")){
				url = "viewThd.html?thd_id=" + dj_id;
			}
			
			hj_ys += je;
			ys_ye += je;
		%>
			<TD class=ReportItem><%=xwtype %></TD>
			<TD class=ReportItem><a href="#" onclick="openWin('<%=url %>');"><%=dj_id %></TD>
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>		
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ys_ye,2) %>&nbsp;</TD>
			
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>	
		<%			
		}else if(xwtype.equals("收款")){
			url = "viewXssk.html?id=" + dj_id;
			hj_sk += je;
			ys_ye -= je;
		%>
			<TD class=ReportItem><%=xwtype %></TD>
			<TD class=ReportItem><a href="#" onclick="openWin('<%=url %>');"><%=dj_id %></a></TD>
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>	
			<TD class=ReportItem>&nbsp;</TD>	
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ys_ye,2) %>&nbsp;</TD>
			
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>				
		<%			
		}else if(xwtype.equals("采购") || xwtype.equals("采购退货")){
			if(xwtype.equals("采购")){
				url = "viewJhd.html?id=" + dj_id;
			}
			if(xwtype.equals("采购退货")){
				url = "viewCgthd.html?id=" + dj_id;
			}
			hj_yf += je;
			yf_ye += je;
		%>
			<TD class=ReportItem><%=xwtype %></TD>
			<TD class=ReportItem><a href="#" onclick="openWin('<%=url %>');"><%=dj_id %></a></TD>
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>		
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(yf_ye,2) %>&nbsp;</TD>			
		<%			
		}else if(xwtype.equals("付款")){
			url = "viewCgfk.html?id=" + dj_id;
			hj_fk += je;
			yf_ye -= je;
		%>
			<TD class=ReportItem><%=xwtype %></TD>
			<TD class=ReportItem><a href="#" onclick="openWin('<%=url %>');"><%=dj_id %></a></TD>
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>		
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(yf_ye,2) %>&nbsp;</TD>
		<%
		}else if(xwtype.equals("往来调账(应收)")){
			url = "viewPz.html?id=" + dj_id;
			hj_ys += je;
			ys_ye += je;
		%>
			<TD class=ReportItem><%=xwtype %></TD>
			<TD class=ReportItem><a href="#" onclick="openWin('<%=url %>');"><%=dj_id %></TD>
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>		
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(ys_ye,2) %>&nbsp;</TD>
			
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>			
		
		<%
		}else if(xwtype.equals("往来调账(应付)")){
			url = "viewPz.html?id=" + dj_id;
			hj_yf += je;
			yf_ye += je;
		%>
		
			<TD class=ReportItem><%=xwtype %></TD>
			<TD class=ReportItem><a href="#" onclick="openWin('<%=url %>');"><%=dj_id %></a></TD>
			
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>		
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			
			<TD class=ReportItemMoney><%=JMath.round(je,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(yf_ye,2) %>&nbsp;</TD>				
		<%
		}

%>
		</TR>
	
<%
		if(flag.equals("是")){
			if(xwtype.equals("销售") || xwtype.equals("销售退货") || xwtype.equals("采购") || xwtype.equals("采购退货")){
				List mxList = clientWlDzdService.getDjMxList(dj_id,xwtype);
				if(mxList != null && mxList.size()>0){
					for(int k=0;k<mxList.size();k++){
						Map mxMap = (Map)mxList.get(k);
						
						double xj = mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue();
						
						if(xwtype.equals("销售") || xwtype.equals("销售退货")){
%>
						<tr>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							
							<TD class=ReportItem><%=StringUtils.nullToStr(mxMap.get("product_name")) %>&nbsp;</TD>
							<TD class=ReportItem><%=StringUtils.nullToStr(mxMap.get("product_xh")) %>&nbsp;</TD>
							<TD class=ReportItemMoney><%=StringUtils.nullToStr(mxMap.get("nums")) %>&nbsp;</TD>	
							<TD class=ReportItemMoney><%=StringUtils.nullToStr(mxMap.get("price")) %>&nbsp;</TD>									
							<TD class=ReportItemMoney><%=JMath.round(xj,2) %>&nbsp;</TD>
							<TD class=ReportItemMoney>&nbsp;</TD>
							<TD class=ReportItemMoney>&nbsp;</TD>
							
							<TD class=ReportItemMoney>&nbsp;</TD>
							<TD class=ReportItemMoney>&nbsp;</TD>
							<TD class=ReportItemMoney>&nbsp;</TD>		
						</tr>
<%
							
						}else if(xwtype.equals("采购") || xwtype.equals("采购退货")){
%>
						<tr>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							<TD class=ReportItem>&nbsp;</TD>
							
							<TD class=ReportItem><%=StringUtils.nullToStr(mxMap.get("product_name")) %>&nbsp;</TD>
							<TD class=ReportItem><%=StringUtils.nullToStr(mxMap.get("product_xh")) %>&nbsp;</TD>
							<TD class=ReportItemMoney><%=StringUtils.nullToStr(mxMap.get("nums")) %>&nbsp;</TD>		
							<TD class=ReportItemMoney><%=StringUtils.nullToStr(mxMap.get("price")) %>&nbsp;</TD>								
							<TD class=ReportItemMoney>&nbsp;</TD>
							<TD class=ReportItemMoney>&nbsp;</TD>
							<TD class=ReportItemMoney>&nbsp;</TD>
							
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
			<TD class=ReportItem>&nbsp;</TD>				
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_ys,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_sk,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(ys_ye,2) %>&nbsp;</TD>
			
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
	<input type="button" name="button_print" value=" 导 出 " onclick="document.reportForm.submit();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
<form name="reportForm" action="DownLoadXlsServlet" method="post">
<input type="hidden" name="action" value="exportClientWlDzdResult">
<input type="hidden" name="start_date" value="<%=start_date %>">
<input type="hidden" name="end_date" value="<%=end_date %>">
<input type="hidden" name="client_name" value="<%=client_name %>">
<input type="hidden" name="flag" value="<%=flag %>">
</form>
</body>
</html>