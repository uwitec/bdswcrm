<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Hykfl hykfl = (Hykfl)VS.findValue("hykfl");
List jfgzList = (List)VS.findValue("jfgzList");
%>

<html>
<head>
<title>会员卡分类</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="xhEditor/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="xhEditor/xheditor.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	
    function saveInfo(){
		
	   
	    if(document.getElementById("name").value == ""){
			alert("名称不能为空，请选择！");
			return;
		}	
		
		if(document.getElementById("jffs").value == ""){
			alert("积分方式不能为空，请填写！");
			return;
		}
				
		document.myform.submit();
	}	
	
</script>
</head>
<body >
<form name="myform" action="updateHykfl.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">会员卡分类</td>
	</tr>
	</thead>
	
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="80%" colspan="3"><input type="text" name="hykfl.id" id="id" value="<%=StringUtils.nullToStr(hykfl.getId()) %>" size="45" readonly><font color="red">*</font></td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">名称</td>
		<td class="a2" colspan="3">
			<input type="text" id="name" name="hykfl.name"  value="<%=StringUtils.nullToStr(hykfl.getName()) %>" size="45"><font color="red">*</font>
		</td>
	</tr>
	<tr>
			
		<td class="a1" width="15%">积分方式</td>
		<td class="a2" colspan="3">
			<select name="hykfl.jffs" id="jffs" style="width:256px">
				<option value=""></option>
				<%
				if(jfgzList != null && jfgzList.size()>0){
					for(int i=0;i<jfgzList.size();i++){
						Jfgz jfgz = (Jfgz)jfgzList.get(i);
				%>
				<option value="<%=StringUtils.nullToStr(jfgz.getId()) %>" <%if(jfgz.getId().equals(hykfl.getJffs())) out.print("selected"); %>><%=StringUtils.nullToStr(jfgz.getJfff()) %></option>
				<%
					}
				}
				%>
			</select>
		</td>		
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
