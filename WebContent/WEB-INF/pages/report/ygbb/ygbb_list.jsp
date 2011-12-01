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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>员工资料</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>

<script type="text/javascript">	
	function view(id){
		var destination = "viewYgbb.html?employee_id="+id;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	
	function clearAll(){
		document.myform.real_name.value = "";
		document.myform.position.value = "";
		document.myform.dept_name.value = "";
	}
	
	function refreshPage(){
		document.myform.action = "listYgbb.html";
		document.myform.submit();
	}

	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}		
</script>
</head>
<body >
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listYgbb.html" method="post">

<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>员工资料</b></td>
		<td class="csstitle" width="25%">
		  <img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
		    姓名：<input type="text" name="real_name" value="<%=real_name %>">&nbsp;&nbsp;
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
			</select>&nbsp;&nbsp;
					
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">&nbsp;
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
	    <td>ID</td>
		<td>姓名</td>
		<td>性别</td>
		<td>电话</td>
		<td>手机</td>
		<td>部门</td>
		<td>职位</td>
		<td>是否业务员</td>
		<td>Email</td>
		<td>QQ</td>
		<td>MSN</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = employeePage.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
	%>
	<tr  class="a1" title="双击查看详情" onmousedown="trSelectChangeCss()" onDblClick="view('<%=StringUtils.nullToStr(map.get("user_id")) %>');">
		<td><%=StringUtils.nullToStr(map.get("user_id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("real_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("sex")) %></td>
		<td><%=StringUtils.nullToStr(map.get("gs_phone")) %></td>
		<td><%=StringUtils.nullToStr(map.get("mobile")) %></td>
		<td><%=StringUtils.nullToStr(map.get("dept_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("position")) %></td>
		<td><%=StringUtils.nullToStr(map.get("is_ywy")) %></td>
		<td><%=StringUtils.nullToStr(map.get("mail")) %></td>
		<td><%=StringUtils.nullToStr(map.get("qq")) %></td>
		<td><%=StringUtils.nullToStr(map.get("msn")) %></td>
		<td>
			<a href="javascript:view('<%=StringUtils.nullToStr(map.get("user_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
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
</div>
</body>
</html>
