<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String real_name = StringUtils.nullToStr((String)VS.findValue("real_name"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择查看备忘录的人</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	
	function clearAll(){
		document.myform.real_name.value = "";
	}
	
	function selectAll(){
		var objs = document.getElementsByName("real_name");
		if(objs != null && objs.length > 0){
			for(var i=0;i<objs.length;i++){
				if(document.getElementById("chkAll").checked){
					objs[i].checked = true;
				}else{
					objs[i].checked = false;
				}
			}
		}
	}
		
   function chkValue(flag){
		var objs = document.getElementsByName("real_name");
		if(objs == null){
			alert("当前未设置查看备忘录的人！");
			return;
		}
		
		var parentTo = window.opener.document.getElementById("to");
		
		var vl = "";
		for(var i=0;i<objs.length;i++){
			if(objs[i].checked){
				if(vl == ""){
					vl = objs[i].value;
				}else{
					vl += "," + objs[i].value;
				}
			}
		}		
		if(vl == ""){
			alert("请选择查看备忘录的人！");
			return;
		}	
		
		if(parentTo.value == ""){
			parentTo.value = vl;
		}else{
			parentTo.value = parentTo.value + "," + vl;
		}
		
		if(flag == "1"){
			window.close();
		}
	}
</script>
</head>
<body>
<form name="myform" action="selMan.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择查看备忘录的人</b></td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			真实名称：<input type="text" name="real_name" value="<%=real_name %>" size="20">&nbsp;&nbsp;		
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td><ww:checkbox theme="simple" name="chkAll" id="chkAll" onclick="selectAll();"/></td>
		<td>工号</td>	
		<td>名称</td>
		<td>联系电话</td>
		<td>部门</td>
		<td>职位</td>
	</tr>
	</thead>
	<ww:iterator value="%{pageMan.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
		    <td><INPUT type="checkbox" name="real_name" id="real_name" value="<ww:property value="real_name"/>"/></td>	
			<td><ww:property value="%{user_id}" /></td>
			<td><ww:property value="%{real_name}" /></td>
			<td><ww:property value="%{mobile}" /></td>
			<td><ww:property value="%{dept_name}" /></td>
			<td><ww:property value="%{position}" /></td>
		</tr>
	</ww:iterator>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="a1">
			<input type="button" name="btnSub" value=" 确认选择并关闭 " class="css_button4" onclick="chkValue('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value=" 确认并继续选择 " class="css_button4" onclick="chkValue('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnClose" value=" 关闭 " class="css_button2" onclick="window.close();">			
        </td>
	</tr>
	<tr>
	   <td class="page">
          <crm:page value="%{pageMan}" formname="myform"/>
       </td>		
	</tr>
</table>
</form>
</body>
</html>