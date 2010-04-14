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
<title>员工报表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
			
		if(document.getElementById("dept").value == ""){
			alert("所在部门不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("real_name").value == ""){
			alert("姓名不能为空，请填写！");
			return;
		}
		
		if(document.getElementById("id_card").value == ""){
			alert("身份证号码不能为空，请填写！");
			return;
		}
		
		if(document.getElementById("nl").value == ""){
			alert("年龄不能为空，请填写！");
			return;
		}
		
		if(document.getElementById("gs_phone").value == ""){
			alert("工作电话不能为空，请填写！");
			return;
		}
		
		if(document.getElementById("sfjh").value == ""){
			alert("婚否不能为空，请填写！");
			return;
		}
		
		if(document.getElementById("address").value == ""){
			alert("家庭住址不能为空，请填写！");
			return;
		}
		
		if(document.getElementById("lxr").value == ""){
			alert("联系人不能为空，请填写！");
			return;
		}
		
		if(document.getElementById("jt_phone").value == ""){
			alert("家庭电话不能为空，请填写！");
			return;
		}
		
		if(document.getElementById("position").value == ""){
			alert("职务不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("rzrq").value == ""){
			alert("入职日期不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("is_ywy").value == ""){
			alert("是否业务员不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("byxx").value == ""){
			alert("毕业学校不能为空，请填写！");
			return;
		}
		
		if(document.getElementById("major").value == ""){
			alert("专业不能为空，请填写！");
			return;
		}
		
		if(document.getElementById("xl").value == ""){
			alert("学历不能为空，请填写！");
			return;
		}		
		
		if(!InputValid(document.getElementById("real_name"),1,"string",1,1,20,"姓名")){	 return; }
		if(!InputValid(document.getElementById("nl"),1,"int",1,1,99,"年龄")){	 return; }
		if(!InputValid(document.getElementById("gs_phone"),1,"string",1,1,20,"工作电话")){	 return; }
		if(!InputValid(document.getElementById("sfjh"),1,"string",1,1,20,"婚否")){	 return; }
		if(!InputValid(document.getElementById("address"),1,"string",1,1,100,"家庭地址")){	 return; }
		if(!InputValid(document.getElementById("lxr"),1,"string",1,1,20,"联系人")){	 return; }
		if(!InputValid(document.getElementById("jt_phone"),1,"string",1,1,20,"家庭电话")){	 return; }
		if(!InputValid(document.getElementById("byxx"),1,"string",1,1,50,"毕业学校")){	 return; }
		if(!InputValid(document.getElementById("major"),1,"string",1,1,40,"专业")){	 return; }
		if(!InputValid(document.getElementById("xl"),1,"string",1,1,10,"学历")){	 return; }
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
		<td class="a1" width="15%">公司工号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("gh")) %></td>	
		<td class="a1" width="15%">显示排序</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("xh")) %></td>
	</tr>	
	<tr>
		<td class="a1" width="15%">姓名</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("real_name")) %></td>		
	    <td class="a1" width="15%">身份证号码</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("id_card")) %></td>
	</tr>	
	<tr>		
		<td class="a1" width="15%">性别</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("sex")) %></td>
		<td class="a1" width="15%">年龄</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("nl")) %></td>							
	</tr>	
	<tr>	    
	    <td class="a1" width="15%">生日</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("csny")) %></td>	
	    <td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("mobile")) %></td>	
	</tr>	
	<tr>
		<td class="a1" width="15%">工作电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("gs_phone")) %></td>
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("mail")) %></td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">MSN</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("msn")) %></td>		
	    <td class="a1" width="15%">QQ</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("qq")) %></td>
	</tr>
	<tr>    
	    <td class="a1" width="15%">民族</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("nation")) %></td>
	    <td class="a1" width="15%">政治面貌</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("zzmm")) %></td>
	</tr>
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">家庭信息</td>
	</tr>
	</thead>
	<tr>
	    <td class="a1" width="15%">家庭地址</td>
		<td class="a2" width="35%" colspan="3"><%=StringUtils.nullToStr(userMap.get("address")) %></td>
	</tr>
	<tr>		
	    <td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("lxr")) %></td>		
		<td class="a1" width="15%">家庭电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("jt_phone")) %></td>
	</tr>
	<tr>			
	    <td class="a1" width="15%">关系</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("relation")) %></td>
	    <td class="a1" width="15%">婚否</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("sfjh")) %></td>	
	</tr>	
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">档案信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">部门</td>
		<td class="a2" width="35%"><%=StaticParamDo.getDeptNameById(StringUtils.nullToStr(userMap.get("dept"))) %></td>
		<td class="a1" width="15%">职务</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("position")) %></td>		
	</tr>	
	<tr>
	    <td class="a1" width="15%">基本工资</td>
		<td class="a2" width="35%"><%=userMap.get("jbgz")%></td>
		<td class="a1" width="15%">入职日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("rzrq")) %></td>		
	</tr>		
	<tr>
		<td class="a1" width="15%">工龄</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("gl")) %></td>
		<td class="a1" width="15%">是否业务员</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("is_ywy")) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">毕业学校</td>
		<td class="a2" width="85%" colspan="3"><%=StringUtils.nullToStr(userMap.get("byxx")) %></td>
	</tr>	
	<tr>
		<td class="a1" width="15%">专业</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("major")) %></td>
		<td class="a1" width="15%">学历</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(userMap.get("xl")) %></td>	
	</tr>	
	<tr>	
		<td class="a1" width="15%">工作简历</td>	
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(userMap.get("gzjl")) %></td>			
	</tr>	
	<tr>	
		<td class="a1" width="15%">领导考核</td>	
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(userMap.get("ldkh")) %></td>			
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="35%" colspan="3"><%=StringUtils.nullToStr(userMap.get("remark")) %></td>
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
