<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List depts = (List)VS.findValue("depts");
String[] positions = (String[])VS.findValue("positions");

Map userMap = (Map)VS.findValue("userMap");

String msg = StringUtils.nullToStr(session.getAttribute("MSG"));
session.removeAttribute("MSG");

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
			alert("是否业务员不能为空，请选择！");
			return;
		}
		if(document.getElementById("dept").value == ""){
			alert("所在部门不能为空，请选择！");
			return;
		}
		document.userForm.submit();
	}
	
</script>
</head>
<body>
<form name="userForm" action="updateEmployee.html" method="post">
<center>
<font color="red" style="font-size:16px;"><%=msg %></font>
</center>
<input type="hidden" name="user.user_id" id="user_id" value="<%=StringUtils.nullToStr(userMap.get("user_id")) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">员工信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">工号</td>
		<td class="a2" width="35%"><input type="text" name="user.gh" id="gh" value="<%=StringUtils.nullToStr(userMap.get("gh")) %>"><font color="red">*</font></td>	
		<td class="a1" width="15%">姓名</td>
		<td class="a2" width="35%"><input type="text" name="user.real_name" id="real_name" value="<%=StringUtils.nullToStr(userMap.get("real_name")) %>"><font color="red">*</font></td>		
	</tr>	
	<tr>		
		<td class="a1" width="15%">性别</td>
		<td class="a2" width="35%">
			<select name="user.sex" id="sex">
				<option value="男" <%if(StringUtils.nullToStr(userMap.get("sex")).equals("男")) out.print("selected"); %>>男</option>
				<option value="女" <%if(StringUtils.nullToStr(userMap.get("sex")).equals("女")) out.print("selected"); %>>女</option>
			</select>			
			<font color="red">*</font>
		</td>
		<td class="a1" width="15%">年龄</td>
		<td class="a2" width="35%"><input type="text" name="user.nl" id="nl" value="<%=StringUtils.nullToStr(userMap.get("nl")) %>"><font color="red">*</font></td>							
	</tr>	
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><input type="text" name="user.gs_phone" id="gs_phone" value="<%=StringUtils.nullToStr(userMap.get("gs_phone")) %>" maxlength="10"><font color="red">*</font></td>
		<td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><input type="text" name="user.mobile" id="mobile" value="<%=StringUtils.nullToStr(userMap.get("mobile")) %>" maxlength="20"></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">传真</td>
		<td class="a2" width="35%"><input type="text" name="user.fax" id="fax" value="<%=StringUtils.nullToStr(userMap.get("fax")) %>" maxlength="10"></td>
		<td class="a1" width="15%">家庭电话</td>
		<td class="a2" width="35%"><input type="text" name="user.jt_phone" id="jt_phone" value="<%=StringUtils.nullToStr(userMap.get("jt_phone")) %>" maxlength="20"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2" width="35%"><input type="text" name="user.mail" id="mail" value="<%=StringUtils.nullToStr(userMap.get("mail")) %>" maxlength="50"></td>
		<td class="a1" width="15%">MSN</td>
		<td class="a2" width="35%"><input type="text" name="user.msn" id="msn" value="<%=StringUtils.nullToStr(userMap.get("msn")) %>" size="45" maxlength="20"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">QQ</td>
		<td class="a2" width="35%"><input type="text" name="user.qq" id="qq" value="<%=StringUtils.nullToStr(userMap.get("qq")) %>" maxlength="20"></td>
		<td class="a1" width="15%">生日</td>
		<td class="a2" width="35%"><input type="text" name="user.csny" id="csny" value="<%=StringUtils.nullToStr(userMap.get("csny")) %>"  maxlength="20"></td>		
	</tr>		
	<tr>
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><input type="text" name="user.address" id="address" value="<%=StringUtils.nullToStr(userMap.get("address")) %>" size="45" maxlength="50"></td>
		<td class="a1" width="15%">邮编</td>
		<td class="a2" width="35%"><input type="text" name="user.p_code" id="p_code" value="<%=StringUtils.nullToStr(userMap.get("p_code")) %>" maxlength="10"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">职位</td>
		<td class="a2" width="35%">
			<select name="user.position" id="position">
				<option value=""></option>
				<%
				String position = StringUtils.nullToStr(userMap.get("position"));
				if(positions != null && positions.length>0){
					for(int i=0;i<positions.length;i++){
				%>
					<option value="<%=StringUtils.nullToStr(positions[i]) %>" <%if(position.equals(StringUtils.nullToStr(positions[i]))) out.print("selected"); %>><%=StringUtils.nullToStr(positions[i]) %></option>
				<%
					}
				}
				%>			
			</select><font color="red">*</font>	
		</td>
		<td class="a1" width="15%">是否业务员</td>
		<td class="a2">
			<select name="user.is_ywy" id="is_ywy">
				<option value=""></option>
				<option value="是" <%if(StringUtils.nullToStr(userMap.get("is_ywy")).equals("是")) out.print("selected"); %>>是</option>
				<option value="否" <%if(StringUtils.nullToStr(userMap.get("is_ywy")).equals("否")) out.print("selected"); %>>否</option>
			</select><font color="red">*</font>
		</td>					
	</tr>	
	<tr>
		<td class="a1">所在部门</td>
		<td class="a4">
			<select name="user.dept" id="dept">
				<option value=""></option>
				<%
				if(depts != null &&  depts.size()>0){
					for(int i=0;i<depts.size();i++){
						Dept dept = (Dept)depts.get(i);
						
						String dept_id = dept.getDept_id();
						String dept_name = dept.getDept_name();
						
						for(int k=0;k<dept_id.length()-2;k++){
							dept_name = "　" + dept_name;
						}
				%>
				<option value="<%=dept_id %>" <%if(StringUtils.nullToStr(userMap.get("dept")).equals(dept_id)) out.print("selected"); %>><%=dept_name %></option>
				<%
					}
				}
				%>
			</select>
		</td>	
		<td class="a1" width="15%">排序</td>
		<td class="a2"><input type="text" name="user.xh" id="xh" value="<%=StringUtils.nullToStr(userMap.get("xh")) %>" size="5" maxlength="5"></td>		
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>	
</table>
</form>
</body>
</html>
