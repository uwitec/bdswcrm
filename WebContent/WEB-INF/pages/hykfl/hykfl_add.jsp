<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Hykfl hykfl = (Hykfl)VS.findValue("hykfl");
String id = (String)VS.findValue("id");
List jfgzList = (List)VS.findValue("jfgzList");
%>
<html>
<head>
<title>会员卡分类</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/validateform.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if (doValidate(myform)){		
			document.myform.submit();
		}
	}	
</script>
</head>
<body >
<form name="myform" action="saveHykfl.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="2">会员卡分类</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">编号</td>
		<td class="a2" width="75%"><input type="text" name="hykfl.id" id="id" value="<%=id %>" style="width:180px" readonly><font color="red">*</font></td>
	</tr>
	<tr>
		<td class="a1">名称</td>
		<td class="a2">
			<input type="text" id="name" name="hykfl.name"  value="" style="width:180px" maxlength="25" notNull='true' vdisp='名称'  vtype='string'><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1">积分方式</td>
		<td class="a2">
			<select name="hykfl.jffs" id="jffs" style="width:180px" notNull='true' vdisp='积分方式'  vtype='string'>
				<option value=""></option>
				<%
				if(jfgzList != null && jfgzList.size()>0){
					for(int i=0;i<jfgzList.size();i++){
						Jfgz jfgz = (Jfgz)jfgzList.get(i);
				%>
				<option value="<%=StringUtils.nullToStr(jfgz.getId()) %>"><%=StringUtils.nullToStr(jfgz.getJfff()) %></option>
				<%
					}
				}
				%>
			</select><font color="red">*</font>
		</td>	  
	</tr>
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提交" class="css_button" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value="重置" class="css_button">&nbsp;
			<input type="button" name="button3" value="关闭" class="css_button" onclick="window.close();">
		</td>
	</tr>	
</table>
</form>
</body>
</html>