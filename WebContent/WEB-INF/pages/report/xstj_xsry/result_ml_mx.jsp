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
String user_id = StringUtils.nullToStr(request.getParameter("user_id"));        //部门

List results = xstjXsryService.getYwymlMx(start_date,end_date,user_id);

String strCon = "";

strCon = "日期：" + start_date + "至" + end_date;
if(!user_id.equals("")){
	strCon += "&nbsp;&nbsp;销售人员：" + StaticParamDo.getRealNameById(user_id);
}
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
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>业务员销售毛利明细</B></font><br><%=strCon %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead width="70">日期</TD>
			<TD class=ReportHead width="100">单据号</TD>
			<TD class=ReportHead width="50">业务类型</TD>
			<TD class=ReportHead width="120">客户</TD>
			<TD class=ReportHead>产品名称</TD>
			<TD class=ReportHead>型号</TD>
			<TD class=ReportHead>数量</TD>
			<TD class=ReportHead>单价</TD>
			<TD class=ReportHead>销售金额</TD>		
			<TD class=ReportHead>不含税金额金额</TD>	
			<TD class=ReportHead>单位成本</TD>
			<TD class=ReportHead>成本</TD>			
			<TD class=ReportHead>毛利</TD>	
			<TD class=ReportHead>毛利率</TD>	
		</TR>
	</THEAD>
	<TBODY>
<%

if(results != null && results.size()>0){
	
	String ywdj_id = "";
	
	int hj_nums = 0;
	double hj_xsje = 0;
	double hj_bhsje = 0;
	double hj_cb = 0;
	double hj_ml = 0;
	
	for(int i=0;i<results.size();i++){
		
		Map map = (Map)results.get(i);
		
		String id = StringUtils.nullToStr(map.get("id"));
		
		String strId = "";
		String strDate = "";
		String strYwtype = "";
		String strClientName = "";
		
		if(!id.equals(ywdj_id)){
			strId = id;
			strDate = StringUtils.nullToStr(map.get("cz_date"));
			strYwtype = StringUtils.nullToStr(map.get("yw_type"));
			strClientName = StaticParamDo.getClientNameById((String)map.get("client_name"));
		}
		
		ywdj_id = id;
		
		int nums = new Integer(StringUtils.nullToZero(map.get("nums"))).intValue(); //数量
		double price = map.get("price")==null?0:((Double)map.get("price")).doubleValue(); //单价
		double xsje = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();;   //销售金额
		double bhsje = map.get("bhsje")==null?0:((Double)map.get("bhsje")).doubleValue(); //不含税金额
		double dwcb = map.get("dwcb")==null?0:((Double)map.get("dwcb")).doubleValue(); //单位考核成本
		double cb = map.get("cb")==null?0:((Double)map.get("cb")).doubleValue(); //考核成本
		double ml = bhsje - cb;
		
		hj_nums += nums;
		hj_xsje += xsje;
		hj_bhsje += bhsje;
		hj_cb += cb;
		hj_ml += ml;
%>
		<TR>
			<TD class=ReportItemXH><%=strDate %>&nbsp;</TD>
			<TD class=ReportItemXH><%=strId %>&nbsp;</TD>
			<TD class=ReportItemXH><%=strYwtype %>&nbsp;</TD>
			<TD class=ReportItem><%=strClientName %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_name")) %>&nbsp;</TD>
			<TD class=ReportItem><%=StringUtils.nullToStr(map.get("product_xh")) %>&nbsp;</TD>								
			<TD class=ReportItemXH nowrap><%=nums %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.round(price,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.round(xsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.round(bhsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.round(dwcb,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.round(cb,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.round(ml,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.percent(ml,bhsje) %>&nbsp;</TD>
		</TR>
<%								
	}
%>
		<TR>
			<TD class=ReportItemXH><b>合 计</b>&nbsp;</TD>
			<TD class=ReportItemXH>&nbsp;</TD>
			<TD class=ReportItemXH>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>								
			<TD class=ReportItemXH nowrap><%=hj_nums %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.round(hj_xsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.round(hj_bhsje,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.round(hj_cb,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.round(hj_ml,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney nowrap><%=JMath.percent(hj_ml,hj_cb) %>&nbsp;</TD>
		</TR>
<%
}
%>

	</TBODY>
</TABLE>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：毛利 = 不含税金额 - 成本；毛利率 = 毛利 / 不含税金额 * 100%；点击单据编号可以查看原始单据。</td>
			<td colspan="3" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center class="Noprint">
	<input type="button" name="button_print" value=" 打 印 " onclick="window.print();"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="history.go(-1);"> 
</center>
</body>
</html>