<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List providers = (List)VS.findValue("providerList");

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
		if(!InputValid(document.getElementById("name"),1,"string",1,1,20,"仓库名称")){	 return; }
		
		document.storeHouseForm.submit();
	}
	
	 function setStoreState()
	{	    
	      var StoreState = document.getElementById("flag");
		  var o = document.storeHouseForm.proc_id;
		  if(o.checked){
		     StoreState.value = "0";
		   }
		  else
		  {
		    StoreState.value = "1";
		  }
	}	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="storeHouseForm" action="saveStore.html" method="post">
<input type="hidden" name="storeHouse.flag" id="flag" value="1">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">仓库信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">仓库名称</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.name" id="name" value="" style="width:170px" maxlength="50"> <font color="red">*</font></td>
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.address" id="address" value="" style="width:170px" maxlength="100"></td>
	</tr>
	<tr>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.lxr" id="lxr" value="" style="width:170px" maxlength="10"></td>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><input type="text" name="storeHouse.lxdh" id="lxdh" value="" style="width:170px" maxlength="20"></td>
	</tr>	
	
	<tr>
	    <td class="a1"  width="15%">状态</td>
	    <td class="a2" colspan="3">
	        <input type="checkbox"  name="proc_id"  value="停用" onclick="setStoreState();">停用	       
        </td>
    </tr> 
   
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">其    它</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="20%">备注</td>
		<td class="a2" width="80%">
			<textarea rows="3" cols="50" name="storeHouse.remark" id="remark" style="width:80%" maxlength="500"></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
