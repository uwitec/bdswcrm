<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List nbggList = (List)VS.findValue("nbggList");
%>
<html>
<head>
<title>首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
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
<table width="60%" align="left">
	<tr>
		<td valign="top" width="100%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr><td>
				<table width="100%" align="left"  class="chart_list" cellpadding="0" cellspacing="0">
					<tr>
						<td class="csstitle" align="left">&nbsp;&nbsp;&nbsp;&nbsp;<b>内部公告</b></td>
						 <td class="csstitle" align="right" colspan="2"><img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷新首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr><td height="3" colspan="3">&nbsp;</td></tr>
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
						<td width="20%" nowrap="nowrap" height="23"><%=jsr %></td>
						<td width="20%" nowrap="nowrap" height="23" colspan="2">【<%=pub_date %>】</td>
					</tr>
					<%
					}
					%>
					<tr>
					<TD align=right height="23" colspan="3"><a href="javascript:void(0);" onclick="parent.addtabFmMenu('公告列表','listGdNbgg.html','');" class=xxlb>更多...</a>&nbsp;&nbsp;&nbsp;</TD>	
					</tr>															
				</table>
				</td></tr>							
			</table>				
		</td>
	</tr>
</table>
</form>
</BODY>
</html>