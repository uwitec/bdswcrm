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
%>

<html>
<head>
<title>员工管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">	
	function view(id){
		var destination = "viewEmployee.html?employee_id="+id;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delEmployee.html?employee_id=" + id;
		}
	}
	
	function add(){
		if(document.myform.dept_id.value == ""){
			alert("请先选择部门，再添加员工！");
			return;
		}
		var destination = "addEmployee.html?dept_id=" + document.myform.dept_id.value;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加',fea);	
	}
	
	function edit(userId){
		var destination = "editEmployee.html?employee_id=" + userId;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改',fea);	
	}		
	
	function clearAll(){
		document.myform.real_name.value = "";
		document.myform.position.value = "";
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
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			性名：<input type="text" name="real_name" value="<%=real_name %>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
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
			</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
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
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = employeePage.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
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
		<td class="a1">
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(map.get("user_id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="view('<%=StringUtils.nullToStr(map.get("user_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(map.get("user_id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>
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
