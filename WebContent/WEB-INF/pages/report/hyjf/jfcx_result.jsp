<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
HykJfFlowService hykJfFlowService = (HykJfFlowService)VS.findValue("hykJfFlowService");

String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
String con_vl = StringUtils.nullToStr(request.getParameter("con_vl"));

List <Hykda> hykdaList = hykJfFlowService.getHykdaList(con_vl); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>积分查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<script type="text/javascript">
function openWin(url){
	var fea ='width=950,height=630,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-630)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(url,'详细信息',fea);	
}
</script>
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
</head>
<body align="center" >
<div class="rightContentDiv" id="divContent">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:30px;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px"><B>会员积分查询</B></font></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>

<%
if(hykdaList != null && hykdaList.size() > 0){
	for(Hykda hykda : hykdaList){
		Hykzz hykzz = hykJfFlowService.getHykzzInfo(hykda.getHykh());
%>
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<tr>
		<td align="center" width="80"><B>会员卡号：</B></td>
		<td align="left" width="170"><%=StringUtils.nullToStr(hykda.getHykh()) %></td>	
		<td align="center" width="80"><B>会员名称：</B></td>
		<td align="left" width="200"><%=StringUtils.nullToStr(hykda.getHymc()) %></td>
		<td align="center" width="80"><B>联系人：</B></td>
		<td align="left" width="170"><%=StringUtils.nullToStr(hykda.getLxrname()) %></td>		
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="center" width="80"><B>手机号码：</B></td>
		<td align="left" width="170"><%=StringUtils.nullToStr(hykda.getMobile()) %></td>	
		<td align="center" width="80"><B>发卡日期：</B></td>
		<td align="left" width="200"><%=StringUtils.nullToStr(hykda.getFkrq()) %></td>
		<td align="center" width="80"><B>卡片状态：</B></td>
		<td align="left" width="170"><%=StringUtils.nullToStr(hykda.getState()) %></td>		
		<td>&nbsp;</td>
	</tr>	
	<tr>
		<td align="center" width="80"><B>初始积分：</B></td>
		<td align="left" width="170"><%=StringUtils.nullToStr(hykzz.getCsjf()) %></td>	
		<td align="center" width="80"><B>全部积分：</B></td>
		<td align="left" width="200"><%=JMath.round(hykJfFlowService.getHyjf(hykda.getHykh(), "", "")) %></td>
		<td align="center" width="80"><B>本周期积分：</B></td>
		<td align="left" width="170"><%=JMath.round(hykJfFlowService.getHyjf(hykda.getHykh(), start_date, end_date)) %></td>		
		<td>&nbsp;</td>
	</tr>	
</TABLE>
<TABLE align="center" class="stripe" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<tr>
			<td class=ReportHead colspan="6">积分明细</td>
		</tr>
		<TR>
			<TD class=ReportHead width="19%">时间</TD>
			<TD class=ReportHead width="18%">交易类型</TD>
			<TD class=ReportHead width="19%">业务单据编号</TD>
			<TD class=ReportHead width="18%">交易金额</TD>
			<TD class=ReportHead width="18%">积分</TD>
			<TD class=ReportHead width="18%">经手人</TD>	
		</TR>
	</THEAD>
	<TBODY>
	<%
	List<HykJfFlow> jfFlowList = hykJfFlowService.getHyjfFlowList(start_date, end_date, hykda.getHykh());
	if(jfFlowList != null && jfFlowList.size() > 0){
		for(HykJfFlow hykJfFlow : jfFlowList){
			String ywdj_id = hykJfFlow.getYw_id();
			String yw_type = "零售";
			String url = "viewLsd.html?id=" + ywdj_id;
			if(ywdj_id.indexOf("TH") != -1){
				yw_type = "退货";
				url = "viewThd.html?thd_id=" + ywdj_id;
			}
	%>
		<TR>
			<TD class=ReportItemXh><%=hykJfFlow.getCz_date() %>&nbsp;</TD>
			<TD class=ReportItemXh><%=yw_type %>&nbsp;</TD>			
			<TD class=ReportItemXh><a href="javascript:openWin('<%=url %>');"><%=ywdj_id %></a>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(hykJfFlow.getXfje(),2) %>&nbsp;</TD>
			<TD class=ReportItemMoney><%=JMath.round(hykJfFlow.getJf()) %>&nbsp;</TD>
			<TD class=ReportItemXh><%=StaticParamDo.getRealNameById(hykJfFlow.getJsr()) %>&nbsp;</TD>
		</TR>
	<%		
		}
	}
	%>	
	</TBODY>
</TABLE>	
<BR><BR>
<%
	}
}
%>
<center class="Noprint">
    <input type="button" name="button_fh" value=" 返 回 " onclick="javascript:location.href='showJfcxCondition.html';"> 
</center>
</div>
</body>
</html>