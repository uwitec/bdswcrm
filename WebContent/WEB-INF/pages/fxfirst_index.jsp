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
<table width="490" border="0">
	<tr>
		<td valign="top">
				<table width="100%" align="left"  class="chart_list" cellpadding="0" cellspacing="0">
					<tr>
						<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>内部公告</b></td>
					</tr>
					<tr><td height="3">&nbsp;</td></tr>
					<%
					Iterator itNbgg = nbggList.iterator();
					while(itNbgg.hasNext()){
						XxfbNbgg info = (XxfbNbgg)itNbgg.next();
						
						String id = StringUtils.nullToStr(info.getId());
						String title = StringUtils.nullToStr(info.getTitle());
						String subTitle = title;
						if(title.length()>=15){
							subTitle = title.substring(0,15) + "...";
						}
						String pub_date = StringUtils.nullToStr(info.getPub_date());
					%>				
					<tr>
						<td width="100%" height="23">&nbsp;<A class=xxlb href="#" onclick="openNbggWin('<%=id %>');" title="<%=title %>"><%=subTitle %>【<%=pub_date %>】</A></td>
					</tr>
					<%
					}
					%>														
				</table>		
		</td>
	</tr>
</table>
</form>
</BODY>
</html>