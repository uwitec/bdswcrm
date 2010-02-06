<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List nbggList = (List)VS.findValue("nbggList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.inner{
	width:500px;
	margin:5px; 
	border:1px solid #B8B8B8;
	text-align:left;
}
* html .inner {display:inline}/* for ie*/
html>body #outer {display:table}/*for mozilla */
html>body .inner {display:table;float:left}/*for mozilla */
@media all and (min-width: 0px){/* opera 7 styles */
html>body .inner {display:inline-block;float:none;}
}
</style>
<script type="text/javascript">	
	function openNbggWin(id){
		var destination = "viewNbgg.html?id="+id;
		var fea = 'width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'详细信息',fea);	
	}

	function refreshPage(){
		document.myform.action = "listMain.html";
		document.myform.submit();
	}	
</script>
</head>
<BODY>
<form name="myform" action="listMain.html" method="post">
<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="100%" align="center">
			<div class="inner">
				<table width="100%" align="left" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td height="27" colspan="3" align="left" style="background-image:url(images/head_top2bg.gif)">&nbsp;&nbsp;&nbsp;&nbsp;<b>内部公告</b></td>
					</tr>
					<tr><td height="3" colspan="3"></td></tr>
					<%
					Iterator itNbgg = nbggList.iterator();
					while(itNbgg.hasNext()){
						XxfbNbgg info = (XxfbNbgg)itNbgg.next();
						
						String id = StringUtils.nullToStr(info.getId());
						String title = StringUtils.nullToStr(info.getTitle());
						String subTitle = title;
						if(title.length()>22){
							subTitle = title.substring(0,22) + "...";
						}
						String pub_date = StringUtils.nullToStr(info.getPub_date());
						String jsr = StaticParamDo.getRealNameById(info.getCzr());
					%>				
					<tr>
						<td width="60% height="23">&nbsp;<A class=xxlb href="#" onclick="openNbggWin('<%=id %>');" title="<%=title %>"><%=subTitle %></A></td>
						<td width="15%" nowrap="nowrap" height="23"><%=jsr %></td>
						<td width="15%" nowrap="nowrap" height="23">【<%=pub_date %>】</td>
					</tr>
					<%
					}
					%>
					<tr>
					<TD align=right height="23" colspan="3"><a href="javascript:void(0);" onclick="parent.addtabFmMenu('公告列表','listGdNbgg.html','');" class=xxlb>更多...</a>&nbsp;&nbsp;&nbsp;</TD>	
					</tr>															
				</table>
			</div>
		</td>
	</tr>
</table>
</form>
</BODY>
</html>