<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

SysUser user = (SysUser)VS.findValue("user");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function init(){
		var vl = "<%=StringUtils.nullToStr(user.getIs_dls()) %>";
		var obj = document.getElementById("selUserType");
		var obj2 = document.getElementById("selName");
		if(vl == "1"){
			obj.innerHTML = document.getElementById("div_dls").innerHTML;
			obj2.innerHTML = "代理商";
			document.getElementById("trSzkf").style.display = "none";
		}else if(vl == "2"){
			obj.innerHTML = document.getElementById("div_gys").innerHTML;
			obj2.innerHTML = "供应商品类别";
			document.getElementById("trSzkf").style.display = "none";
		}else{
			obj.innerHTML = document.getElementById("div_ywy").innerHTML;
			obj2.innerHTML = "业务员";
			document.getElementById("trSzkf").style.display = "";
		}
	}
</script>
</head>
<body onload="init();">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">用户信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">登录名</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(user.getUser_name()) %></td>
		<td class="a1" width="15%">真实姓名</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(user.getReal_name()) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(user.getGs_phone()) %></td>
		<td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(user.getMobile()) %></td>		
	</tr>
	<%
	String is_dls = StringUtils.nullToStr(user.getIs_dls());
	if(is_dls.equals("0")){
		is_dls = "业务员(普通)";
	}else if(is_dls.equals("1")){
		is_dls = "代理商";
	}else if(is_dls.equals("2")){
		is_dls = "供应商";
	}else if(is_dls.equals("3")){
		is_dls = "业务员(零售)";
	}else if(is_dls.equals("4")){
		is_dls = "业务员(渠道)";
	}
	%>
	<tr>
		<td class="a1" width="15%">用户类别</td>
		<td class="a2" width="35%"><%=is_dls %></td>
		<td class="a1" width="15%" id="selName"></td>
		<td class="a2" width="35%" id="selUserType"></td>		
	</tr>
	<tr id="trSzkf">
		<td class="a1" width="15%">所在库房</td>
		<td class="a2" colspan="3"><%=StaticParamDo.getStoreNameById(user.getSzkf()) %></td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button2" value="关闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>	
</table>
<div id="div_ywy" style="display:none"><%=StaticParamDo.getRealNameById(user.getClient_name()) %></div>
<div id="div_dls" style="display:none"><%=StaticParamDo.getClientNameById(user.getClient_name()) %></div>
<%
String product_kind = StringUtils.nullToStr(user.getClient_name());
String kind_name = "";
if(!product_kind.equals("")){
	String[] arryItems = product_kind.split(",");
	if(arryItems != null && arryItems.length >0){
		for(int i=0;i<arryItems.length;i++){
			if(kind_name.equals("")){
				kind_name = StaticParamDo.getProductKindNameById(arryItems[i]);
			}else{
				kind_name += "," + StaticParamDo.getProductKindNameById(arryItems[i]);
			}
			
		}
	}
}
%>
<div id="div_gys" style="display:none"><%=kind_name %></div>
</body>
</html>
