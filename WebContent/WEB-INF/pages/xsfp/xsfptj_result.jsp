<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List<XsfpFpxx> xsfpList = (List)VS.findValue("xsfpList");
Map xsfpMxMap = (Map)VS.findValue("xsfpMxMap");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String q_fplx = StringUtils.nullToStr(request.getParameter("fplx"));
String flag = StringUtils.nullToStr(request.getParameter("flag"));

String conStr = "";
if(xsfpList != null && xsfpList.size() > 0){
	conStr += "<b>日期：</b>" + start_date + "至" + end_date + "&nbsp;&nbsp;<B>发票类型</B>：" + q_fplx;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售发票统计——统计结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script language="javascript" src="print/LodopFuncs.js"></script>
<script language="javascript" src="print/print.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function openWin(url){
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}
	
	function openWin(id){
		var destination = "";
		var fea ='width=980,height=650,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		if(id.indexOf("XS") != -1){
			destination = "viewXsd.html?id="+id;
		}else{
			destination = "viewLsd.html?id="+id;
		}
		window.open(destination,'详细信息',fea);	
	}
</script>
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<div id="printDIV">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>销售发票统计</B></font><br><%=conStr %></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<TD class=ReportHead >类型</TD>
			<TD class=ReportHead >客户</TD>
			<TD class=ReportHead >编号</TD>
			<TD class=ReportHead >发票号</TD>
			<TD class=ReportHead >应开票金额</TD>
			<TD class=ReportHead >已开票金额</TD>
			<TD class=ReportHead >发票金额</TD>
			<TD class=ReportHead >开票人</TD>
			<TD class=ReportHead >开票日期</TD>
		</TR>
	</THEAD>
	<TBODY>
	<%
	double hj_fpje = 0;
	if(xsfpList != null && xsfpList.size() > 0){
		for(XsfpFpxx xsfpFpxx : xsfpList) {
			String id =  StringUtils.nullToStr(xsfpFpxx.getId());
			String khmc =  StringUtils.nullToStr(xsfpFpxx.getKhmc());
			String fplx =  StringUtils.nullToStr(xsfpFpxx.getFplx());
			String fph =  StringUtils.nullToStr(xsfpFpxx.getFph());
			double fpje = xsfpFpxx.getFpje();
			double fpje_bdd = xsfpFpxx.getFpje_bdd();
			String kpr =  StaticParamDo.getRealNameById(xsfpFpxx.getKpr());
			String kprq =  StringUtils.nullToStr(xsfpFpxx.getKprq());
			
			hj_fpje += fpje;
			
			String cl = "";
			if(fpje_bdd > 0) {
				cl = "color: red";
			}
	%>
		<TR style="BACKGROUND-COLOR: #E8E8E8;font-weight: bold;<%=cl%>">
			<TD class=ReportItemXh><%=fplx %>&nbsp;</TD>
			<TD class=ReportItem>　<%=khmc %>&nbsp;</TD>
			<TD class=ReportItem>　<%=id %></TD>			
			<TD class=ReportItemXh><%=fph %>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(fpje,2) %>&nbsp;</TD>
			<TD class=ReportItemXh><%=kpr %>&nbsp;</TD>
			<TD class=ReportItemXh><%=kprq %>&nbsp;</TD>
		</TR>	
	<%
			if(flag.equals("是")){
				if(fpje_bdd > 0) {
	%>
		<TR style="color: red;">
			<TD class=ReportItemXh>&nbsp;</TD>
			<TD class=ReportItemXh>&nbsp;</TD>			
			<TD class=ReportItem>　　　不对单&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(fpje_bdd,2) %>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
		</TR>		
	<%			
				}
			
				List <Map>xsfpMxList = (List)xsfpMxMap.get(id);
				if(xsfpMxList != null && xsfpMxList.size() > 0) {
					for(Map map : xsfpMxList){
						String yw_id = StringUtils.nullToStr(map.get("yw_id"));
						String khmc_mx = StringUtils.nullToStr(map.get("khmc"));
						double kpje_ying = map.get("kpje_ying")==null?0:((Double)map.get("kpje_ying")).doubleValue(); 
						double kpje_yi = map.get("kpje_yi")==null?0:((Double)map.get("kpje_yi")).doubleValue(); 
						double kpje_bc = map.get("kpje_bc")==null?0:((Double)map.get("kpje_bc")).doubleValue(); 
						String cdate = StringUtils.nullToStr(map.get("cdate"));
	%>
		<TR style="color: blue;">
			<TD class=ReportItemXh>&nbsp;</TD>
			<TD class=ReportItem>　　　<%=khmc_mx%>&nbsp;</TD>			
			<TD class=ReportItem>　　　<a href="javascript:openWin('<%=yw_id %>');"><%=yw_id  %></a>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(kpje_ying,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(kpje_yi,2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(kpje_bc,2) %>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItemXh><%=cdate %>&nbsp;</TD>
		</TR>		
	<%				
					}
				}
			}
		}
	}
	%>

		<TR>
			<TD class=ReportItemXh style="font-weight:bold">合计</TD>
			<TD class=ReportItem>&nbsp;</TD>
			<TD class=ReportItem>&nbsp;</TD>			
			
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold"><%=JMath.round(hj_fpje,2) %>&nbsp;</TD>
			
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
			<TD class=ReportItemMoney style="font-weight:bold">&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>
</div>
<br>
<table width="99%">
		<tr>
			<td width="70%" height="30">注：红色字体存在不对单开发票情况，点击业务单据编号可以查看原始单据。</td>
			<td colspan="2" align="right" height="30">生成报表时间：<%=DateComFunc.getToday() %>&nbsp;&nbsp;&nbsp;</td>
		</tr>
</table>
<center>
    <input type="button" name="button_printsetup" value=" 打印预览 "  onclick="printPre('2','A4','printDIV');"> &nbsp;&nbsp;
	<input type="button" name="button_print" value=" 打 印 " onclick="printReport('销售发票统计','2','A4','printDIV');"> &nbsp;&nbsp;
    <input type="button" name="button_fh" value=" 返 回 " onclick="location.href='xsfptjCondition.html';"> 
</center>
</div>
</body>
</html>