<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List depts = (List)VS.findValue("depts");
String[] positions = (String[])VS.findValue("positions");

Map userMap = (Map)VS.findValue("userMap");

String msg = StringUtils.nullToStr(session.getAttribute("MSG"));
session.removeAttribute("MSG");
SysUser user = (SysUser)VS.findValue("user");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>员工管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
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
		
		if(document.getElementById("mail").value == ""){
			alert("E-Mail不能为空，请填写！");
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
		
		if(document.getElementById("zzzt").value == ""){
			alert("在职状态不能为空，请填写！");
			return;
		}
		
		if(!InputValid(document.getElementById("real_name"),1,"string",1,1,20,"姓名")){	 return; }
		if(!InputValid(document.getElementById("nl"),1,"int",1,1,99,"年龄")){	 return; }
		if(!InputValid(document.getElementById("gs_phone"),1,"string",1,1,20,"工作电话")){	 return; }
		if(!InputValid(document.getElementById("mail"),1,"string",1,1,100,"E-Mail")){	 return; }
		if(document.getElementById("sfjh").value == ""){
			alert("婚否不能为空，请选择！");
			return;
		}
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
		<td class="a1" width="15%">公司工号</td>
		<td class="a2" width="35%"><input type="text" name="user.gh" id="gh" value="<%=StringUtils.nullToStr(userMap.get("gh")) %>" style="width:80%"></td>	
	    <td class="a1" width="15%">显示排序</td>
		<td class="a2"><input type="text" name="user.xh" id="xh" value="<%=StringUtils.nullToStr(userMap.get("xh")) %>"  style="width:80%" maxlength="5"></td>
	</tr>
	<tr>		
		<td class="a1" width="15%">姓名</td>
		<td class="a2" width="35%"><input type="text" name="user.real_name" id="real_name" value="<%=StringUtils.nullToStr(userMap.get("real_name")) %>" style="width:80%"><font color="red">*</font></td>		
	    <td class="a1" width="15%">身份证号码</td>
		<td class="a2" width="35%"><input type="text" name="user.id_card" id="id_card" value="<%=StringUtils.nullToStr(userMap.get("id_card")) %>" style="width:80%"><font color="red">*</font></td>
	</tr>	
	<tr>		
		<td class="a1" width="15%">性别</td>
		<td class="a2" width="35%">
			<select name="user.sex" id="sex" style="width:76%">
				<option value="男" <%if(StringUtils.nullToStr(userMap.get("sex")).equals("男")) out.print("selected"); %>>男</option>
				<option value="女" <%if(StringUtils.nullToStr(userMap.get("sex")).equals("女")) out.print("selected"); %>>女</option>
			</select>			
			<font color="red">*</font>
		</td>
		<td class="a1" width="15%">年龄</td>
		<td class="a2" width="35%"><input type="text" name="user.nl" id="nl" value="<%=StringUtils.nullToStr(userMap.get("nl")) %>" style="width:80%"><font color="red">*</font></td>							
	</tr>
	<tr>	    
	    <td class="a1" width="10%">生日</td>
		<td class="a2" width="35%"><input type="text" name="user.csny" id="csny" value="<%=StringUtils.nullToStr(userMap.get("csny")) %>"  style="width:80%" class="Wdate" onFocus="WdatePicker()"></td>	
	    <td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><input type="text" name="user.mobile" id="mobile" value="<%=StringUtils.nullToStr(userMap.get("mobile")) %>" style="width:80%" maxlength="20"></td>	
	</tr>	
	<tr>
		<td class="a1" width="15%">工作电话</td>
		<td class="a2" width="35%"><input type="text" name="user.gs_phone" id="gs_phone" value="<%=StringUtils.nullToStr(userMap.get("gs_phone")) %>" maxlength="20" style="width:80%"><font color="red">*</font></td>
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2" width="35%"><input type="text" name="user.mail" id="mail" value="<%=StringUtils.nullToStr(userMap.get("mail")) %>" maxlength="50" style="width:80%"><font color="red">*</font></td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">MSN</td>
		<td class="a2" width="35%"><input type="text" name="user.msn" id="msn" value="<%=StringUtils.nullToStr(userMap.get("msn")) %>" style="width:80%" maxlength="30"></td>		
	    <td class="a1" width="15%">QQ</td>
		<td class="a2" width="35%"><input type="text" name="user.qq" id="qq" value="<%=StringUtils.nullToStr(userMap.get("qq")) %>" style="width:80%" maxlength="20"></td>
	</tr>
	<tr>    
	    <td class="a1" width="15%">民族</td>
		<td class="a2" width="35%"><input type="text" name="user.nation" id="nation" value="<%=StringUtils.nullToStr(userMap.get("nation")) %>" style="width:80%" maxlength="20"></td>
	    <td class="a1" width="15%">政治面貌</td>
		<td class="a2" width="35%"><input type="text" name="user.zzmm" id="zzmm" value="<%=StringUtils.nullToStr(userMap.get("zzmm")) %>" style="width:80%" maxlength="20"></td>
	</tr>
	<tr>
	<td class="a1" width="15%">在职状态</td>
		<td class="a2" width="35%">
			<select name="user.zzzt" id="zzzt" style="width:76%">
				<option value="在职" <%if(StringUtils.nullToStr(userMap.get("zzzt")).equals("在职")) out.print("selected"); %>>在职</option>
				<option value="离职" <%if(StringUtils.nullToStr(userMap.get("zzzt")).equals("离职")) out.print("selected"); %>>离职</option>
			</select><font color="red">*</font>
		</td>
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
		<td class="a2" width="35%" colspan="3"><input type="text" name="user.address" id="address" value="<%=StringUtils.nullToStr(userMap.get("address")) %>" style="width:92%" maxlength="50"><font color="red">*</font></td>
	</tr>
	<tr>		
	    <td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="user.lxr" id="lxr" value="<%=StringUtils.nullToStr(userMap.get("lxr")) %>" style="width:80%" maxlength="30"><font color="red">*</font></td>
		<td class="a1" width="15%">家庭电话</td>
		<td class="a2" width="35%"><input type="text" name="user.jt_phone" id="jt_phone" value="<%=StringUtils.nullToStr(userMap.get("jt_phone")) %>" style="width:80%" maxlength="20"><font color="red">*</font></td>		
	</tr>
	<tr>    
	    <td class="a1" width="15%">关系</td>
		<td class="a2" width="35%"><input type="text" name="user.relation" id="relation" value="<%=StringUtils.nullToStr(userMap.get("relation")) %>" style="width:80%" maxlength="10"></td>
	    <td class="a1" width="15%">婚否</td>
		<td class="a2" width="35%">
			<select name="user.sfjh" id="sfjh" style="width:76%">
				<option value=""></option>
				<option value="已婚" <%if(StringUtils.nullToStr(userMap.get("sfjh")).equals("已婚")) out.print("selected"); %>>已婚</option>
				<option value="未婚" <%if(StringUtils.nullToStr(userMap.get("sfjh")).equals("未婚")) out.print("selected"); %>>未婚</option>
			</select><font color="red">*</font>
		</td>
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
		<td class="a1">所在部门</td>
		<td class="a2">
			<select name="user.dept" id="dept" style="width:76%">
				<option value=""></option>
				<%
				if(depts != null &&  depts.size()>0){
					for(int i=0;i<depts.size();i++){
						Dept dept = (Dept)depts.get(i);
						
						String dept_id = dept.getDept_id();
						String dept_name = dept.getDept_name();
						
						for(int k=0;k<dept_id.length()-2;k++){
							dept_name = "&nbsp;&nbsp;" + dept_name;
						}
				%>
				<option value="<%=dept_id %>" <%if(StringUtils.nullToStr(userMap.get("dept")).equals(dept_id)) out.print("selected"); %>><%=dept_name %></option>
				<%
					}
				}
				%>
			</select><font color="red">*</font>	
		</td>	
		<td class="a1" width="15%">职务</td>
		<td class="a2" width="35%">
			<select name="user.position" id="position" style="width:76%">
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
	</tr>	
	<tr>
	    <td class="a1" width="15%">基本工资</td>
		<td class="a2" width="35%"><input type="text" name="user.jbgz" id="jbgz" value="<%=userMap.get("jbgz")%>" style="width:80%"  maxlength="20" ></td>
		<td class="a1" width="15%">入职日期</td>
		<td class="a2" width="35%"><input type="text" name="user.rzrq" id="rzrq" value="<%=StringUtils.nullToStr(userMap.get("rzrq")) %>" style="width:80%"  maxlength="20" class="Wdate" onFocus="WdatePicker()"><font color="red">*</font></td>		
	</tr>		
	<tr>		
		<td class="a1" width="15%">是否业务员</td>
		<td class="a2">
			<select name="user.is_ywy" id="is_ywy" style="width:76%">
				<option value=""></option>
				<option value="是" <%if(StringUtils.nullToStr(userMap.get("is_ywy")).equals("是")) out.print("selected"); %>>是</option>
				<option value="否" <%if(StringUtils.nullToStr(userMap.get("is_ywy")).equals("否")) out.print("selected"); %>>否</option>
			</select><font color="red">*</font>
		</td>
		<td class="a1" width="15%">毕业学校</td>
		<td class="a2" width="35%"><input type="text" name="user.byxx" id="byxx" value="<%=StringUtils.nullToStr(userMap.get("byxx")) %>" style="width:80%" maxlength="60"><font color="red">*</font></td>					
	</tr>		
	<tr>
		<td class="a1" width="15%">专业</td>
		<td class="a2" width="35%"><input type="text" name="user.major" id="major" value="<%=StringUtils.nullToStr(userMap.get("major")) %>" style="width:80%"><font color="red">*</font></td>
		<td class="a1" width="15%">学历</td>
		<td class="a2" width="35%"><input type="text" name="user.xl" id="xl" value="<%=StringUtils.nullToStr(userMap.get("xl")) %>" style="width:80%"><font color="red">*</font></td>	
	</tr>	
	<tr>	
		<td class="a1" width="15%">工作简历</td>	
		<td class="a2" colspan="3">
			<textarea  name="user.gzjl" id="gzjl"   style="width:80%;height:70px"><%=StringUtils.nullToStr(userMap.get("gzjl")) %></textarea>
		</td>			
	</tr>	
	<tr>	
		<td class="a1" width="15%">领导考核</td>	
		<td class="a2" colspan="3">
			<textarea name="user.ldkh" id="ldkh"  style="width:80%;height:70px"><%=StringUtils.nullToStr(userMap.get("ldkh")) %></textarea>
		</td>			
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="35%" colspan="3"><input type="text" name="user.remark" id="remark" value="<%=StringUtils.nullToStr(userMap.get("remark")) %>" style="width:80%"></td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>	
</table><BR>
</form>
</body>
</html>
