<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page employeePage = (Page)VS.findValue("employeePage");
String[] positions = (String[])VS.findValue("positions");

String real_name = (String)VS.findValue("real_name");
String dept_id = (String)VS.findValue("dept_id");
String position = (String)VS.findValue("position");
String zzzt = (String)VS.findValue("zzzt");
String is_del = (String)VS.findValue("is_del");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>员工管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">	
	function view(id){
		var destination = "viewEmployee.html?employee_id="+id;
		var fea ='width=800,height=660,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-660)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delEmployee.html?employee_id=" + id;
		}
	}
	
	function restore(id){
		if(confirm("确定要还原该条记录吗！")){
			location.href = "restoreEmployee.html?employee_id=" + id;
		}
	}
	
	function add(){
		if(document.myform.dept_id.value == ""){
			alert("请先选择部门，再添加员工！");
			return;
		}
		var destination = "addEmployee.html?dept_id=" + document.myform.dept_id.value;
		var fea ='width=800,height=660,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-660)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加',fea);	
	}
	
	function edit(userId){
		var destination = "editEmployee.html?employee_id=" + userId;
		var fea ='width=800,height=660,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-660)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改',fea);	
	}		
	
	function clearAll(){
		document.myform.real_name.value = "";
		document.myform.position.value = "";
		document.myform.zzzt.value = "";
		document.myform.is_del.value = "";
	}
	
	function refreshPage(){
		document.myform.action = "listEmployee.html";
		document.myform.submit();
	}		
</script>
</head>
<body >
<form name="myform" action="listEmployee.html" method="post">
<input type="hidden" name="dept_id" value="<%=dept_id %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>员工管理</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; 姓名：<input type="text" name="real_name" value="<%=real_name %>">&nbsp;&nbsp;&nbsp;&nbsp;
			职位：
			<select name="position">
				<option value=""></option>
				<%
				if(positions != null && positions.length>0){
					for(int i=0;i<positions.length;i++){
				%>
					<option value="<%=StringUtils.nullToStr(positions[i]) %>" <%if(position.equals(StringUtils.nullToStr(positions[i]))) out.print("selected"); %>><%=StringUtils.nullToStr(positions[i]) %></option>
				<%
					}
				}
				%>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;
			在职状态：
			<select name="zzzt">
				<option value=""></option>
				<option value="在职" <%if("在职".equals(StringUtils.nullToStr(zzzt))) out.print("selected"); %>>在职</option>
				<option value="离职" <%if("离职".equals(StringUtils.nullToStr(zzzt))) out.print("selected"); %>>离职</option>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;
			状态：
			<select name="is_del">
				<option value=""></option>
				<option value="0" <%if("0".equals(StringUtils.nullToStr(is_del))) out.print("selected"); %>>正常</option>
				<option value="1" <%if("1".equals(StringUtils.nullToStr(is_del))) out.print("selected"); %>>删除</option>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">&nbsp;&nbsp;
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>工号</td>
		<td>姓名</td>
		<td>性别</td>
		<td>电话</td>
		<td>手机</td>
		<td>部门</td>
		<td>职位</td>
		<td>是否业务员</td>
		<td>在职状态</td>
		<td>状态</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = employeePage.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		String q_is_del = StringUtils.nullToStr(map.get("is_del"));
		if(q_is_del.equals("0")){
			q_is_del =  "正常";
		}else{
			q_is_del = "删除";
		}
	%>
	<tr>
		<td class="a1"><%=StringUtils.nullToStr(map.get("gh")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("real_name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("sex")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("gs_phone")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("mobile")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("dept_name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("position")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("is_ywy")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("zzzt")) %></td>
		<td class="a1"><%=q_is_del %></td>
		<td class="a1">
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(map.get("user_id")) %>');">修改</a>&nbsp;&nbsp;
			<a href="#" onclick="view('<%=StringUtils.nullToStr(map.get("user_id")) %>');">查看</a>&nbsp;&nbsp;
			<%if(StringUtils.nullToStr(map.get("is_del")).equals("0")){ %>
			  <a href="#" onclick="del('<%=StringUtils.nullToStr(map.get("user_id")) %>');">删除</a>
		    <%}else{ %>
		      <a href="#" onclick="restore('<%=StringUtils.nullToStr(map.get("user_id")) %>');">还原</a>
		    <%} %>
		</td>
	</tr>
	
	<%
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=employeePage.getPageScript() %></td>
	</tr>	
</table>
</form>
</body>
</html>
