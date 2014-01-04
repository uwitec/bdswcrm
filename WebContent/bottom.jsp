<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.sw.cms.model.LoginInfo" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%
LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String userName = "";
String cdate = "";
String cip = "";
int onlineNums = 0;
if(info != null){
	userName = info.getReal_name();
	cdate = DateComFunc.formatDate(info.getLast_login_time(),"yyyy-MM-dd HH:mm:ss");
	cip = info.getLast_login_ip();
}
List onlineList = (List)application.getAttribute("onlineUserList");
if(onlineList != null){
	onlineNums = onlineList.size();
}

String curYear = DateComFunc.getYear() + "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>bottom</title>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script>
	function freshOnlineUser(){
		var url = 'online_nums.jsp';
		var myAjax = new Ajax.Request(url,{
				method:'post',
				onComplete: showResponse,
				asynchronous:true
			});
	}

	function showResponse(originalRequest){
		var onlineNums = "0";
		if(originalRequest.responseText.trim() != null){
			onlineNums = originalRequest.responseText.trim();
		}
		document.getElementById("nums").innerText = onlineNums;
	}

	function getOnlineNums(){
		freshOnlineUser();
		setTimeout("getOnlineNums()",20*1000);
	}
	function isIE(){ //ie?
		  if (window.navigator.userAgent.toLowerCase().indexOf("msie") >= 1)
		  	return true;
		  else
		  	return false;
	}

	if(!isIE()){ //firefox innerText define
		  HTMLElement.prototype.__defineGetter__( "innerText",
			  function(){
				  var anyString = "";
				  var childS = this.childNodes;
				  for(var i=0; i <childS.length; i++) {
					  if(childS[i].nodeType==1)
					  	anyString += childS[i].tagName=="BR" ? '\n' : childS[i].innerText;
					  else if(childS[i].nodeType==3)
					  	anyString += childS[i].nodeValue;
				  }
				  return anyString;
			  }
		  );
		  HTMLElement.prototype.__defineSetter__( "innerText",
			  function(sText){
			  	this.textContent=sText;
			  }
		  );
	}		
</script>
</head>
<body leftmargin="0" topmargin="0" bgcolor="#e5e5e5" onload="getOnlineNums();">
<table width="100%" border="0" height="25" cellpadding="0" cellspacing="0" align="center">
  <tr align="center" height="25">
    <td bgcolor="#e5e5e5" width="50%" align="left"> &nbsp; &nbsp;当前用户：<%=userName %>&nbsp;&nbsp;登录时间：<%=cdate %>&nbsp;&nbsp;登录IP地址：<%=cip %>&nbsp;&nbsp;在线用户数：<span id="nums"><%=onlineNums %></span></td>
    <td bgcolor="#e5e5e5" width="50%" align="right">copyright <font style='font-family:Arial;'>&copy;</font> 2009-<%=curYear %>  保定思维 &nbsp;&nbsp; &nbsp;&nbsp;推荐使用IE8.0以上浏览器，或者是Chrome浏览器 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>		
  </tr>
</table>
</body>
</html>
