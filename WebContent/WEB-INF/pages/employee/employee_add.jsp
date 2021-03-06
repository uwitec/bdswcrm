<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List depts = (List)VS.findValue("depts");
String dept_id = (String)VS.findValue("dept_id");
String[] positions = (String[])VS.findValue("positions");

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
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language="JavaScript" src="js/keychange.js"></script>
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
<form name="userForm" action="saveEmployee.html" method="post">
<center>
<font color="red" style="font-size:16px;"><%=msg %></font>
</center>
<input type="hidden" name="user.dept" id="dept" value="<%=dept_id %>">
<input type="hidden" name="user.user_id" id="user_id" value="<%=StringUtils.nullToStr(user.getUser_id()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">员工信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">公司工号</td>
		<td class="a2" width="35%"><input type="text" name="user.gh" id="gh" value="" onkeyup="goNext(this.form,this.name);" style="width:80%"></td>	
	    <td class="a1" width="15%">显示排序</td>
		<td class="a2"><input type="text" name="user.xh" id="xh" value="0" size="5" maxlength="5" onkeyup="goNext(this.form,this.name);" style="width:80%"></td>
	</tr>
	<tr>		
		<td class="a1" width="15%">姓名</td>
		<td class="a2" width="35%"><input type="text" name="user.real_name" id="real_name" value="" onkeyup="goNext(this.form,this.name);" style="width:80%"><font color="red">*</font></td>		
	    <td class="a1" width="15%">身份证号码</td>
		<td class="a2" width="35%" colspan="3"><input type="text" name="user.id_card" id="id_card" value=""  style="width:80%" onkeyup="goNext(this.form,this.name);"><font color="red">*</font></td>
	</tr>	
	<tr>		
		<td class="a1" width="15%">性别</td>
		<td class="a2" width="35%" >
			<select name="user.sex" id="sex" onkeyup="goNext(this.form,this.name);" style="width:76%">
				<option value="男" >男</option>
				<option value="女" >女</option>
			</select>			
			<font color="red">*</font>
		</td>
		<td class="a1" width="15%">年龄</td>
		<td class="a2" width="35%"><input type="text" name="user.nl" id="nl" value="" onkeyup="goNext(this.form,this.name);" style="width:80%"><font color="red">*</font></td>							
	</tr>
	<tr>	    
	    <td class="a1" width="10%">生日</td>
		<td class="a2" width="25%"><input type="text" name="user.csny" id="csny" value=""  class="Wdate" onFocus="WdatePicker()" onkeyup="goNext(this.form,this.name);" style="width:80%"></td>	
	    <td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><input type="text" name="user.mobile" id="mobile" value="" maxlength="20" onkeyup="goNext(this.form,this.name);" style="width:80%"></td>	
	</tr>	
	<tr>
		<td class="a1" width="15%">工作电话</td>
		<td class="a2" width="35%"><input type="text" name="user.gs_phone" id="gs_phone" value="" maxlength="20" onkeyup="goNext(this.form,this.name);" style="width:80%"><font color="red">*</font></td>
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2" width="35%"><input type="text" name="user.mail" id="mail" value="" maxlength="50" onkeyup="goNext(this.form,this.name);" style="width:80%"><font color="red">*</font></td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">MSN</td>
		<td class="a2" width="35%"><input type="text" name="user.msn" id="msn" value=""  style="width:80%" maxlength="30" onkeyup="goNext(this.form,this.name);"></td>		
	    <td class="a1" width="15%">QQ</td>
		<td class="a2" width="35%"><input type="text" name="user.qq" id="qq" value=""  style="width:80%" onkeyup="goNext(this.form,this.name);"></td>
	</tr>
	<tr>    
	    <td class="a1" width="15%">民族</td>
		<td class="a2" width="35%"><input type="text" name="user.nation" id="nation" value=""  style="width:80%"onkeyup="goNext(this.form,this.name);"></td>
	    <td class="a1" width="15%">政治面貌</td>
		<td class="a2" width="35%"><input type="text" name="user.zzmm" id="zzmm" value=""  style="width:80%" onkeyup="goNext(this.form,this.name);"></td>
	</tr>
	<tr>
	    <td class="a1" width="15%">在职状态</td>
		<td class="a2" width="35%" >
			<select name="user.zzzt" id="zzzt" onkeyup="goNext(this.form,this.name);" style="width:76%">
				<option value="在职" >在职</option>
				<option value="离职" >离职</option>
			</select>			
			<font color="red">*</font>
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
		<td class="a2" width="35%" colspan="3"><input type="text" name="user.address" id="address" value=""  style="width:92%" maxlength="50" onkeyup="goNext(this.form,this.name);"><font color="red">*</font></td>		
	</tr>
	<tr>		
	    <td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="user.lxr" id="lxr" value=""  style="width:80%" maxlength="20" onkeyup="goNext(this.form,this.name);"><font color="red">*</font></td>	
		<td class="a1" width="15%">家庭电话</td>
		<td class="a2" width="35%"><input type="text" name="user.jt_phone" id="jt_phone" value="" style="width:80%" maxlength="20" onkeyup="goNext(this.form,this.name);"><font color="red">*</font></td>		
	</tr>
	<tr>   
	    <td class="a1" width="15%">关系</td>
		<td class="a2" width="35%"><input type="text" name="user.relation" id="relation" value="" style="width:80%" maxlength="10" onkeyup="goNext(this.form,this.name);"></td>
	    <td class="a1" width="15%">婚否</td>
		<td class="a2" width="35%" >
			<select name="user.sfjh" id="sfjh" style="width:76%">
				<option value=""></option>
				<option value="已婚">已婚</option>
				<option value="未婚">未婚</option>
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
		<td class="a1" width="15%">职务</td>
		<td class="a2" width="35%">
			<select name="user.position" id="position" onkeyup="goNext(this.form,this.name);" style="width:76%">
				<option value=""></option>
				<%
				if(positions != null && positions.length>0){
					for(int i=0;i<positions.length;i++){
				%>
					<option value="<%=StringUtils.nullToStr(positions[i]) %>"><%=StringUtils.nullToStr(positions[i]) %></option>
				<%
					}
				}
				%>				
			</select><font color="red">*</font>	
		</td>	
		<td class="a1" width="15%">基本工资</td>
		<td class="a2" width="35%"><input type="text" name="user.jbgz" id="jbgz" value="<%=JMath.round(user.getJbgz()) %>"  maxlength="20" onkeyup="goNext(this.form,this.name);" style="width:80%"></td>
	</tr>	
	<tr>	    
		<td class="a1" width="15%">入职日期</td>
		<td class="a2" width="35%"><input type="text" name="user.rzrq" id="rzrq" value=""  maxlength="20" class="Wdate" onFocus="WdatePicker()" style="width:80%"  onkeyup="goNext(this.form,this.name);"><font color="red">*</font></td>		
		<td class="a1" width="15%">是否业务员</td>
		<td class="a2">
			<select name="user.is_ywy" id="is_ywy" onkeyup="goNext(this.form,this.name);" style="width:76%">
				<option value=""></option>
				<option value="是" >是</option>
				<option value="否" >否</option>
			</select><font color="red">*</font>
		</td>					
	</tr>
	<tr>
		<td class="a1" width="15%">毕业学校</td>
		<td class="a2" width="85%" colspan="3"><input type="text" name="user.byxx" id="byxx" value=""  style="width:92%" maxlength="80" onkeyup="goNext(this.form,this.name);"><font color="red">*</font></td>
	</tr>	
	<tr>
		<td class="a1" width="15%">专业</td>
		<td class="a2" width="35%"><input type="text" name="user.major" id="major" value="" onkeyup="goNext(this.form,this.name);" style="width:80%"><font color="red" >*</font></td>
		<td class="a1" width="15%">学历</td>
		<td class="a2" width="35%"><input type="text" name="user.xl" id="xl" value="" onkeyup="goNext(this.form,this.name);" style="width:80%"><font color="red">*</font></td>	
	</tr>	
	<tr>	
		<td class="a1" width="15%">工作简历</td>	
		<td class="a2" colspan="3">
			<ww:textarea name="user.gzjl" id="gzjl" onkeyup="goNext(this.form,this.name);" theme="simple" value="" cssStyle="width:80%;height:70px"/>
		</td>			
	</tr>	
	<tr>	
		<td class="a1" width="15%">领导考核</td>	
		<td class="a2" colspan="3">
			<ww:textarea name="user.ldkh" id="ldkh" onkeyup="goNext(this.form,this.name);" theme="simple" value="" cssStyle="width:80%;height:70px"/>
		</td>			
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="35%" colspan="3"><input type="text" name="user.remark" id="remark" value=""  style="width:80%" onkeyup="goNext(this.form,this.name);"></td>
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
