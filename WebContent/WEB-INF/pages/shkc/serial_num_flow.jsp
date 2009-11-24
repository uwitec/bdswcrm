<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String qz_serial_num = StringUtils.nullToStr(VS.findValue("qz_serial_num"));

List serialFlowList = (List)VS.findValue("serialFlowList");
 
 
%>

<html>
<head>
<title>序列号查询</title>
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
	
	function clearTj(){
		document.reportForm.serial_num.value = "";
	}
</script>
</head>
<body align="center" >
<form name="reportForm" action="getShSerialFlow.html" method="post">
<TABLE  align="center" cellSpacing=0 cellPadding=0 width="99%" border=0>
	<TBODY>
		<TR style="BACKGROUND-COLOR: #dcdcdc;height:45;">
		    <TD align="center" width="100%"><font style="FONT-SIZE: 16px">
		    <B><br>序列号查询</B></font>
		    
		    </TD>
		</TR>
	</TBODY>
	<tr height="40">
		<td align="left">
			&nbsp;序列号：<input type="text" name="qz_serial_num"  value="<%=qz_serial_num %>">&nbsp;&nbsp;&nbsp;
			<input type="submit" name="button1" value=" 查询 " class="css_button2">&nbsp;&nbsp;
			<input type="button" name="button2" value=" 清空 " class="css_button2" onclick="clearTj();">
		</td>
	</tr>	
</TABLE>
<%
if(serialFlowList!=null && serialFlowList.size()>0){
%>
 
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<THEAD>
		<TR>
			<td class=ReportHead  >序号</td>
			<td class=ReportHead  >业务类型</td>
			<td class=ReportHead  >客户名称</td>
			<td class=ReportHead  >经手人</td>
			<td class=ReportHead  >单据编号</td>
			<td class=ReportHead  >目标库</td>
			<td class=ReportHead  >发生时间</td>			
		</TR>
		 	
	</THEAD>
	<tbody>
<%
    int wxcs=0;
	for(int i=0;i<serialFlowList.size();i++){
		Map map = (Map)serialFlowList.get(i);
		
		String yw_url = StringUtils.nullToStr(map.get("yw_url"));
		 
%>
 <% 
        String ywtype = StringUtils.nullToStr(map.get("ywtype"));
        if(ywtype.equals("接件"))
        {    
           wxcs=wxcs+1;
 %>
        <TR>
			<TD class=serialItme> 维修次数:<%=wxcs%></TD>		
			<TD class=serialItme colspan="6" >&nbsp;</TD>		 
		</TR>
     <% }%>
		<TR>
			<TD class=serialItme><%=i+1%>&nbsp;</TD>
			<TD class=serialItme><%=StringUtils.nullToStr(map.get("ywtype")) %>&nbsp;</TD>
			<TD class=serialItme><%=StringUtils.nullToStr(map.get("client_name")) %>&nbsp;</TD>
			<TD class=serialItme><%=StringUtils.nullToStr(map.get("jsr")) %>&nbsp;</TD>
			<TD class=serialItme><a href="#" title="单击查看原始单据" onclick="openWin('<%=yw_url+StringUtils.nullToStr(map.get("yw_dj_id")) %>');"><%=StringUtils.nullToStr(map.get("yw_dj_id")) %>&nbsp;</a></TD>
			<TD class=serialItme><%=StringUtils.nullToStr(map.get("kf")) %>&nbsp;</TD> 
			<TD class=serialItme><%=StringUtils.nullToStr(map.get("fs_date")) %>&nbsp;</TD>
		</TR>
<%
	}
%>
	
	</tbody>
</table>
<BR>
&nbsp;&nbsp;说明:点击单据编号可以查看原始单据。
<%
}
%>
</form>
</body>
</html>