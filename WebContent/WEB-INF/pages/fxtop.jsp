<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%
LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String real_name = info.getReal_name();
String user_id = info.getUser_id();

OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String logo_url = (String)VS.findValue("logo_url");
%>
<html>
<head>
<title>top</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<LINK href="css/InitNav.css" type=text/css rel=stylesheet>
<LINK href="css/Portal.css" type=text/css rel=stylesheet>

<script>
	
	//退出系统
	function  logout(){
		if(confirm("确认退出管理系统?")){
			window.top.location='login.jsp';
		}
	}
    
    //查询库存
	function openWin(){
		var destination = "fxtop.htm";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	//触发点击回车事件
	function f_enter(){
	    if (window.event.keyCode==13){
	        openWin();
	        event.returnValue = false;
	    }
	}	
</script>
</head>
<body onload="document.myform.kc_con.focus();">
<form name="myform" action="queryKcMx.html" method="post" target="_blank">
<TABLE cellSpacing=0 cellPadding=0 width=100% height=100% align=center border=0 bgColor=#FBFBFB>
	<TBODY>
	<tr>
		<td width="30%">
			<TABLE cellSpacing=0 cellPadding=0 border=0>
				<TBODY>
           				<TR>
					<TD>&nbsp;&nbsp;<IMG src="logo/<%=logo_url %>"></TD>
				</TR>						
				</TBODY>
			</TABLE>					
		</td>
		<td width="300" width="40%" height=100%  vAlign=bottom  align=right><BR>
			<form name="myForm" method="post" action="queryKcInfo.html">
			<font style="font-size: 12px;">库存查询：</font><input type="text" name="kc_con"  onkeypress="f_enter();"  size="15">
			<input type="button" name="button1" value=" 查询 " class="css_button2" onclick="openWin();">
			</form>
		</td>
		<td vAlign=top align=right width="30%">
			<TABLE cellSpacing=0 cellPadding=0 border=0>
				<TBODY>
           		<TR>
					<TD>&nbsp;</TD>
					<TD align=middle width=10><IMG src="index_images/head_right.gif"></TD>
					<TD noWrap align=middle width=85 bgColor=#bcc2d4><IMG  height=15  src="index_images/index_home.gif" width=15 align=absMiddle> <A class=TitleMenu  href="#" onclick="parent.location='dls_index.html';">首 页</A></TD>
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>					
					<TD class=NormalWhite noWrap align=middle width=85 bgColor=#bcc2d4><IMG height=15 src="index_images/index_clock.gif" width=15 align=absMiddle><A class=TitleMenu href="#" onclick="parent.addtabFmMenu('修改密码','changePass.html','');">修改密码</A></TD>
					<TD class=NormalWhite vlign=middle width=10 bgColor=#bcc2d4>|</TD>
					<TD class=NormalWhite noWrap align=middle width=85 bgColor=#bcc2d4><IMG height=15 src="index_images/out_system.gif" width=15 align=absMiddle><A class=TitleMenu href="#" onclick="logout();">安全退出</A></TD>
				</TR>
				</TBODY>
			</TABLE>
			<font style="font-size: 12px;">欢迎<%=real_name %>光临系统&nbsp;</font>
		</td>
	</tr>
	</TBODY>
</TABLE>
</form>
</body>
</html>
