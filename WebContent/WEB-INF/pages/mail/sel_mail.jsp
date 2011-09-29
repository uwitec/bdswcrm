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

List results = (List)VS.findValue("mailAll");
String mailStr="";
if(null!=results&&results.size()>0)
{
   for(int i=0;i<results.size();i++)
   {
     Map maps=(Map)results.get(i);
     if(mailStr.equals(""))
     {
       mailStr=(String)maps.get("mail");
     }
     else
     {
        mailStr+=","+maps.get("mail");
     }    
   }
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择发送人</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	
	function clearAll(){
		document.myform.client_type.value = "";
	}
	
	function chkAllMail(){
		var objs = document.getElementsByName("mail");
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
		var objs = document.getElementsByName("mail");
		if(objs == null){
			alert("当前无发送人！");
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
			alert("请选择发送人！");
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
	function allValue(str)
	{
	  var parentTo = window.opener.document.getElementById("to");
	  if(str=="")
	  {
	    alert("所选类型接收人为空！");
		return;
	  }
	  parentTo.value = str;
	  window.close(); 
	}

</script>
</head>
<body>
<form name="myform" action="selMail.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择发送人</b></td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			客户类别：<ww:select name="client_type" id="client_type" theme="simple" list="%{wldwlx}" listKey="xm_name" listValue="xm_name" emptyOption="true" ></ww:select>			
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td><ww:checkbox theme="simple" name="chkAll" id="chkAll" onclick="chkAllMail();"/></td>
		<td>联系人</td>	
		<td>单位名称</td>
		<td>客户类型</td>
		<td>E-Mail</td>
	</tr>
	</thead>
	<ww:iterator value="%{pageMail.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
			<td><INPUT type="checkbox" name="mail" id="mail" value="<ww:property value="mail"/>"/></td>
			<td><ww:property value="%{name}" /></td>
			<td><ww:property value="%{dwmc}" /></td>
			<td><ww:property value="%{client_type}" /></td>
			<td><ww:property value="%{mail}" /></td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="a1">
			<input type="button" name="btnSub" value=" 确认选择并关闭 " class="css_button4" onclick="chkValue('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value=" 确认并继续选择 " class="css_button4" onclick="chkValue('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value=" 类型全选并关闭 " class="css_button4" onclick="allValue('<%=StringUtils.nullToStr(mailStr)%>');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnClose" value=" 关闭 " class="css_button2" onclick="window.close();">			
		</td>
	</tr>
	<tr>
		<td class="page">
		<crm:page value="%{pageMail}" formname="myform"/>
		</td>
	</tr>	
</table>
</form>
</body>
</html>