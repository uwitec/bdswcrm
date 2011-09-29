<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String clients_id=(String)VS.findValue("id");

List   linkmanlist=(List)VS.findValue("descClientLinkman");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>跟进记录添加</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function saveInfo()
	{   
		if(document.getElementById("zt").value == ""){
			alert("主题不能为空，请填写！");
			return;
		}
		if(document.getElementById("linkman_id").value == "")
		{
		   alert("联系人不能为空，请选择！");
		   return;
		}
		if(document.getElementById("lxdate").value=="")
		{
		   alert("联系时间不能为空，请添加！");
		   return;
		}
		if(document.getElementById("lxdate").value!="")
		{
		    var TA= new Date();   
            var TB= new Date(document.getElementById("lxdate").value.replace(/\-/g, "\/"));        
             if(Date.parse(TB) > Date.parse(TA))
             {
                alert("联系时间不能在现在之后!");
                return;
             }
		}
		if(document.getElementById("nextdate").value!="")
		{
		    var TA= new Date();   
            var TB= new Date(document.getElementById("nextdate").value.replace(/\-/g, "\/"));        
             if(Date.parse(TB)< Date.parse(TA))
             {
                alert("下次联系时间不能在现在之前!");
                return;
             }
		}	
		 document.clientForm.submit();
	}	
</script>
</head>
<body oncontextmenu="return false;">
<form name="clientForm" action="saveFollow.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">跟进记录</td>
	</tr>
	</thead>
	<tr>
	    <input type="hidden" name="follow.clients_id" value="<%=clients_id %>"/>
		<td class="a1" width="15%">主题</td>
		<td class="a2" width="35%" colspan="3"><input type="text" name="follow.zt" id="zt" value="" size="78"><font color="red">*</font></td>
		 
	</tr>
	<tr>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%">
          <select name="follow.linkman_id" id="linkman_id">
				   <%
	                  Iterator it=linkmanlist.iterator();
	                    while(it.hasNext())
	                   {
	                       ClientsLinkman linkman=(ClientsLinkman)it.next();
	                    %>
	                     <option value="<%=linkman.getId()%>"><%=linkman.getName()%></option>
	                     
	                  <%} %>
			</select><font color="red">*</font>
        </td>
		<td class="a1" width="15%">联系类型</td>
		<td class="a2" width="35%"><input type="text" name="follow.lxlx" id="lxlx" value=""></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">联系时间</td>
		<td class="a2" width="35%"><input type="text" name="follow.lxdate" id="lxdate" class="Wdate" onFocus="WdatePicker()">
		<font color="red">*</font></td>
		<td class="a1" width="15%">下次联系时间</td>
		<td class="a2" width="35%"><input type="text" name="follow.nextdate" id="nextdate" class="Wdate" onFocus="WdatePicker()">
		</td>		
	</tr>
	<tr>
		<td class="a1" >备注</td>
		<td class="a2" colspan="3">
			<textarea rows="3" cols="50" name="follow.remark" id="remark" style="width:80%" maxlength="500"></textarea>
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
