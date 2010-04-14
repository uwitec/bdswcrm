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

String position = (String)VS.findValue("position");
List deptList = (List)VS.findValue("depts");
%>

<html>
<head>
<title>员工报表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>

<script type="text/javascript">	
	function view(id){
		var destination = "viewYgbb.html?employee_id="+id;
		var fea ='width=800,height=660,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-660)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	
	function clearAll(){
		document.myform.real_name.value = "";
		document.myform.position.value = "";
		document.myform.dept_name.value = "";
	}
	
	function refreshPage(){
		document.myform.action = "listEmployee.html";
		document.myform.submit();
	}		
</script>
</head>
<body >
<form name="myform" action="listYgbb.html" method="post">

<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
		    姓名：<input type="text" name="real_name" value="<%=real_name %>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
		    部门：
		    <select name="dept_id">
				<option value=""></option>
				<%
				if(deptList != null &&  deptList.size()>0){
					for(int i=0;i<deptList.size();i++){
						Dept dept = (Dept)deptList.get(i);
						
						String dept_id = dept.getDept_id();
						String dept_name = dept.getDept_name();
						
						for(int k=0;k<dept_id.length()-2;k++){
							dept_name = "　" + dept_name;
						}
				%>
				<option value="<%=dept_id %>"><%=dept_name %></option>
				<%
					}
				}
				%>
			</select>
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
		<td class="a1"><%=StringUtils.nullToStr(map.get("real_name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("sex")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("gs_phone")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("mobile")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("dept_name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("position")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("is_ywy")) %></td>
		<td class="a1">
			<a href="#" onclick="view('<%=StringUtils.nullToStr(map.get("user_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
