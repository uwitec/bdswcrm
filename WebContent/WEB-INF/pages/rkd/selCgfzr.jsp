<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page employeePage = (Page)VS.findValue("ywyEmployeePage");
String[] positions = (String[])VS.findValue("positions");
List  deptlist = (List)VS.findValue("depts");

String name = ParameterUtility.getStringParameter(request,"name", ""); 
String dept = ParameterUtility.getStringParameter(request,"dept", ""); 
String position = ParameterUtility.getStringParameter(request,"position", ""); 
    
%>

<html>
<head>
<title>业务员管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">	
    function sel(id,name)
    {
      var bian  =window.opener.document.getElementById("bian");
      var cgfzr  =window.opener.document.getElementById("cgfzr");
    
      
      bian.value=name;
      cgfzr.value=id;
      
       bian.focus();
      
        window.close();
    }
	function clearAll(){
		document.myform.name.value = "";
		document.myform.dept.value = "";
		document.myform.position.value="";
	}
</script>
</head>
<body >
<form name="myform" action="selCgfzr.html" method="post">
 
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>业务员查询</b></td>
		<td class="csstitle" width="25%">
					
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			性名：<input type="text" name="name" value="<%=name%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
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
			部门:
			<select name="dept">
			     <option value=""></option>
			      <%
			      if(deptlist!=null && deptlist.size()>0)
			      {
			         for(int i=0;i<deptlist.size();i++)
			         {
			                Dept depts=(Dept)deptlist.get(i);			             
			      %> 
			           <option value="<%=StringUtils.nullToStr(depts.getDept_id()) %>" <%if(StringUtils.nullToStr(depts.getDept_id()).equals(dept))out.print("selected"); %>><%=StringUtils.nullToStr(depts.getDept_name()) %></option>
			 
			      <%
			         }
			      }
			      %>
			</select>			
		<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
		<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>工号</td>
		<td>姓名</td>
		<td>性别</td>
		<td>电话</td>
		<td>手机</td>
		<td>部门</td>
		<td>职位</td>
	</tr>
	</thead>
	<%
	List list = employeePage.getResults();
	if(null!=list&&list.size()>0)
	{
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
	%>
	<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="左键单击选择产品" onclick="sel('<%=map.get("user_id") %>','<%=map.get("real_name")%>');">		
		<td><%=StringUtils.nullToStr(map.get("gh")) %></td>
		<td><%=StringUtils.nullToStr(map.get("real_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("sex")) %></td>
		<td><%=StringUtils.nullToStr(map.get("gs_phone")) %></td>
		<td><%=StringUtils.nullToStr(map.get("mobile")) %></td>
		<td><%=StringUtils.nullToStr(map.get("dept_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("position")) %></td>
	</tr>
	
	<%
	}
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
