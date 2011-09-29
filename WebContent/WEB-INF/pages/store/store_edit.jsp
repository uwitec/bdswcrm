<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

StoreHouse storeHouse = (StoreHouse)VS.findValue("storeHouse");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>仓库管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("name"),1,"string",1,1,25,"仓库名称")){	 return; }
		
		document.storeHouseForm.submit();
	}
	function setStoreState()
	{	    
	      var storeState = document.getElementById("flag");
		  var o = document.storeHouseForm.proc_id;
		  if(o.checked){
		     storeState.value = "0";
		   }
		  else
		  {
		    storeState.value = "1";
		  }
	}		
	function initStoreState(flag)
	{	 
		  var o = document.storeHouseForm.proc_id;
		  if(flag=="0"){
		    o.checked=true;
		  }
		  else
		  {
		    o.checked=false;
		  }
	}	
</script>
</head>
<body oncontextmenu="return false;initStoreState('<%=StringUtils.nullToStr(storeHouse.getFlag()) %>');" >
<form name="storeHouseForm" action="updateStore.html" method="post">
<input type="hidden" name="storeHouse.id" value="<%=StringUtils.nullToStr(storeHouse.getId()) %>">
<input type="hidden" name="storeHouse.flag" id="flag" value="<%=StringUtils.nullToStr(storeHouse.getFlag()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">仓库信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">仓库名称</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.name" id="name" value="<%=StringUtils.nullToStr(storeHouse.getName()) %>" size="30" maxlength="50"></td>
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.address" id="address" value="<%=StringUtils.nullToStr(storeHouse.getAddress()) %>" size="40" maxlength="100"></td>
	</tr>
	<tr>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.lxr" id="lxr" value="<%=StringUtils.nullToStr(storeHouse.getLxr()) %>" size="25" maxlength="10"></td>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(storeHouse.getLxdh()) %>" size="30" maxlength="20"></td>
	</tr>
	<% 
	if(!((storeHouse.getId().equals("WX00000001"))||(storeHouse.getId().equals("WX00000002"))))
	{
	%>	
	<tr>
	    <td class="a1"  width="15%">状态</td>
	    <td class="a2" colspan="3">
	        <input type="checkbox"  name="proc_id"  value="停用" <%if(StringUtils.nullToStr(storeHouse.getFlag()).equals("0")) out.print("checked"); %> onclick="setStoreState();">停用	       
        </td>
    </tr>
     <%
    }
     %>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">其    它</td>
	</tr>
	</thead>
	<tr height="50">
		<td class="a1" width="20%">备注</td>
		<td class="a2" width="80%">
			<textarea rows="6" cols="50" name="storeHouse.remark" id="remark" style="width:80%" maxlength="500"><%=StringUtils.nullToStr(storeHouse.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="返 回" class="css_button2" onclick="history.go(-1);">
		</td>
	</tr>
</table>

</form>
</body>
</html>
