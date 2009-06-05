<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List depts = (List)VS.findValue("depts");

Map userMap = (Map)VS.findValue("userMap");

%>

<html>
<head>
<title>员工管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("gh"),1,"string",1,1,20,"工号")){	 return; }
		if(!InputValid(document.getElementById("real_name"),1,"string",1,1,20,"姓名")){	 return; }
		if(!InputValid(document.getElementById("nl"),1,"int",1,1,99,"年龄")){	 return; }
		if(!InputValid(document.getElementById("gs_phone"),1,"string",1,1,20,"联系电话")){	 return; }
		
		if(document.getElementById("dept").value == ""){
			alert("所在部门不能为空，请选择！");
			return;
		}
		if(document.getElementById("position").value == ""){
			alert("职位不能为空，请选择！");
			return;
		}
		if(document.getElementById("is_ywy").value == ""){
			alert("职位不能为空，请选择！");
			return;
		}
		
		document.userForm.submit();
	}
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="userForm" action="updateEmployee.html" method="post">
<input type="hidden" name="user.user_id" id="user_id" value="<%=StringUtils.nullToStr(userMap.get("user_id")) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">员工信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">工号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("gh")) %></td>	
		<td class="a1" width="15%">姓名</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("real_name")) %></td>		
	</tr>	
	<tr>		
		<td class="a1" width="15%">性别</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("sex")) %></td>
		<td class="a1" width="15%">年龄</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("nl")) %></td>							
	</tr>	
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("gs_phone")) %></td>
		<td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("mobile")) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">传真</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("fax")) %></td>
		<td class="a1" width="15%">家庭电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("jt_phone")) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("mail")) %></td>
		<td class="a1" width="15%">MSN</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("msn")) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">QQ</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("qq")) %></td>
		<td class="a1" width="15%">生日</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("csny")) %></td>		
	</tr>		
	<tr>
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("address")) %></td>
		<td class="a1" width="15%">邮编</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("p_code")) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">部门</td>
		<td class="a2" width="35%"><%=StaticParamDo.getDeptNameById(StringUtils.nullToStr(userMap.get("dept"))) %></td>
		<td class="a1" width="15%">职位</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("position")) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">是否业务员</td>
		<td class="a2"><%=StringUtils.nullToStr(userMap.get("is_ywy")) %></td>	
		<td class="a1" width="15%">排序</td>
		<td class="a2"><%=StringUtils.nullToStr(userMap.get("xh")) %></td>		
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>	
</table>
</form>
</body>
</html>
