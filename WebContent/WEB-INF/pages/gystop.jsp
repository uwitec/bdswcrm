<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%
LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String real_name = info.getReal_name();

OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String logo_url = (String)VS.findValue("logo_url");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>top</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<LINK href="css/InitNav.css" type=text/css rel=stylesheet>
<LINK href="css/Portal.css" type=text/css rel=stylesheet>
<LINK href="css/ddcolortabs.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="js/dropdowntabs.js"></script>
<script type='text/javascript' src='dwr/interface/msgService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>

<script>
	//退出系统
	function  logout(){
		if(confirm("确认退出管理系统?")){
			window.top.location='login.jsp';
		}
	}			
</script>
</head>
<body>
<TABLE cellSpacing=0 cellPadding=0 width=100% height=100% align=center border=0 bgColor=#FBFBFB>
	<TBODY>
	<tr>
		<td width="30%">
			<TABLE cellSpacing=0 cellPadding=0 border=0>
				<TBODY>
           				<TR>
					<TD>&nbsp;&nbsp;<IMG src="logo/<%=logo_url %>" width="140" height="49"></TD>
				</TR>						
				</TBODY>
			</TABLE>					
		</td>
		<td width="300" width="40%" height=100%  vAlign=bottom  align=right>&nbsp;
		</td>
		<td vAlign=top align=right width="30%">
			<TABLE cellSpacing=0 cellPadding=0 border=0>
				<TBODY>
           		<TR>
					<TD>&nbsp;</TD>
					<TD align=middle width=10><IMG src="index_images/head_right.gif"></TD>
					<TD noWrap align=middle width=85 bgColor=#bcc2d4><IMG  height=15  src="index_images/index_home.gif" width=15 align=absMiddle> <A class=TitleMenu  href="#" onclick="parent.location='gys_index.html';">首 页</A></TD>
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>	
					<TD class=NormalWhite noWrap align=middle width=85 bgColor=#bcc2d4><IMG height=15 src="index_images/index_clock.gif" width=15 align=absMiddle> <A class=TitleMenu href="http://hi.baidu.com/bdthinking" target="_blank">系统帮助</A></TD>
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>									
					<TD class=NormalWhite noWrap align=middle width=85 bgColor=#bcc2d4><IMG height=15 src="index_images/out_system.gif" width=15 align=absMiddle> <A class=TitleMenu href="#" onclick="logout();">安全退出</A></TD>
				</TR>
				</TBODY>
			</TABLE>
			<font style="font-size: 12px;">欢迎<%=real_name %>光临系统&nbsp;</font>
		</td>
	</tr>
	</TBODY>
</TABLE>
</body>
</html>
