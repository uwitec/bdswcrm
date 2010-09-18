<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String msg = StringUtils.nullToStr(VS.findValue("msg"));

if(msg.equals("")){
	msg = "重置密码失败，请与管理员联系！";
}
%>
<script>
	alert("<%=msg %>");
</script>