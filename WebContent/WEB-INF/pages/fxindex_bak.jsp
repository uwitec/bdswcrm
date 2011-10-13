<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String cpy_name = (String)VS.findValue("cpy_name");
%>
<html>
<HEAD>
<TITLE><%=cpy_name %></TITLE>
<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
<LINK href="css/InitNav.css" type=text/css rel=stylesheet>
<LINK href="css/Portal.css" type=text/css rel=stylesheet>
<LINK href="css/Desktop.css" type=text/css rel=stylesheet>
<SCRIPT language=javascript src="script/Main.js"></SCRIPT>
<SCRIPT language=javascript src="script/RollScript.js"></SCRIPT>
<SCRIPT language=javascript src="script/eTab.js"></SCRIPT>
<SCRIPT language=javascript src="script/Menu.js"></SCRIPT>
<script type="text/javascript">
	function hidMenu(){
		if(tdService.style.display=='none'){
			tdService2.style.display='inline';
			tdService.style.display='inline';
			splitImg.src = "index_images/p_1.gif";
		} else {
			tdService.style.display='none';
			tdService2.style.display='none';
			splitImg.src = "index_images/p_2.gif";
		}	
	}
</script>
</HEAD>
<BODY id=Body bottomMargin=0 leftMargin=0 topMargin=0 align="left">
<TABLE cellSpacing=0 cellPadding=0 width="100%" height="100%" background="" border=0>
	<TBODY>
	<!-- 头开始 -->
	<TR vAlign=top height="60">
		<TD colspan=3><IFRAME id=ifrIndex2 style="WIDTH: 100%; height:100%; POSITION: relative" border=0  src="dls_index_top.html" frameBorder=0 scrolling=no></IFRAME></TD>
	<TR>
	<!-- 头结束 -->	
	
	<!-- 导航部分开始 -->
	<TR vAlign=top height=30>
		<TD id=tdService2 class=HeadTab_Bottom  vAlign=center align=left>
		<SPAN class=NormalTextBox id=lblDate></SPAN><SCRIPT>setInterval("lblDate.innerHTML=new Date().toLocaleString();",1000);</SCRIPT>
		</td>
		<td class=HeadTab_Bottom  vAlign=center align=left></td>
		<td class=HeadTab_Bottom  vAlign=center align=left>
			<table id="tabTable" summary="" border="0" width="812" cellpadding="0" cellspacing="0">
				<tr>
					<td nowrap>
						<div class="tabHeader_div">
						<ul id="tabHeader">	
							<li id="second">首页</li>
							<li></li>
						</ul>
						</div>
					</td>
				</tr>
			</table>
		</TD>
	<TR>
	<!-- 导航部分结束 -->
  	
	<TR>
		<!-- 左侧菜单区开始 -->
    	<TD id=tdService vAlign=top noWrap align=left width="156">

			<TABLE cellSpacing=0 cellPadding=0 width=156 align=center topMargin=8 border=0>
				<TBODY>
					<TR>
						<TD class=Menutitle>代理商业务</TD>
					</TR>
					<!--
					<TR>
						<TD class=Menuitem onmouseover="className='ActiveMenuItem'" onmouseout="className='Menuitem'">
						<IMG src="index_images/stock_m.gif"  align=absMiddle>
						<A href="javascript:addtabFmMenu('采购订单','listFxdd.html','');">采购订单</A></TD>
					</TR>
					-->
					<TR>
						<TD class=Menuitem onmouseover="className='ActiveMenuItem'" onmouseout="className='Menuitem'">
						<IMG src="index_images/funds.gif"  align=absMiddle>
						<A href="javascript:addtabFmMenu('对账单','showDlsDzdCondition.html','');">对账单</A></TD>
					</TR>				
				</TBODY>
			</TABLE>	
						
		</TD>
		<!-- 左侧菜单区结束 -->
		
		<TD class=Admin_BackGround vAlign=center align=middle width=5 onclick="hidMenu();"><IMG id="splitImg" src="index_images/p_1.gif"></TD>
		
		<!-- 右侧主工作区 -->
		<TD  valign="top" align="left" width="100%" id="tdContent" height="100%" >
			<div class="tabcontent_div">
			<ul id="tabContent">							
				<li><iframe id="ifrIndex2" src="listFxMain.html" scrolling="auto" border="0" style="width:100%;" onload="FrameChange('ifrIndex2');" frameborder="0"></iframe></li>
				<li></li>
			</ul>
			</div>
		</TD>
		<!-- 右侧主工作区 -->
	</TR>
	
</TBODY>					
<script language="javascript">
		document.getElementById("tabTable").width = screen.availWidth-200;
		DesktopInit();
</script>
</BODY>
</HTML>
                              