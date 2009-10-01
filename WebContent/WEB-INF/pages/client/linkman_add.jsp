<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String clinetsid = (String)VS.findValue("id");
 
%>
<html>
<head>
<title>联系人添加</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function saveInfo()
	{
		if(document.getElementById("name").value == ""){
			alert("姓名不能为空，请选择！");
			return;
		}
		if(document.getElementById("mail").value!=""){
	      var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
          if(reg.test(document.getElementById("mail").value)==false){
             alert("邮箱格式不正确！");
             return ;
          }
	    }		
		document.clientForm.action="saveLinkman.html";
		document.clientForm.submit();
	}	
</script>
</head>
<body oncontextmenu="return false;">
<form name="clientForm" action="viewClient.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">联系人</td>
	</tr>
	</thead>
	<tr>
	    <input type="hidden" name="linkman.clients_id" value="<%=clinetsid%>"/>
		<td class="a1" width="15%">姓名</td>
		<td class="a2" width="35%"><input type="text" name="linkman.name" id="name" value=""><font color="red">*</font></td>
		<td class="a1" width="15%">类型</td>
		<td class="a2" width="35%">
			<select name="linkman.lx" id="lx">
				<option value="主联系人">主联系人</option>
				<option value="联系人">联系人</option>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">固定电话</td>
		<td class="a2" width="35%"><input type="text" name="linkman.gzdh" id="gzdh" value=""  ></td>
		<td class="a1" width="15%">移动电话</td>
		<td class="a2" width="35%"><input type="text" name="linkman.yddh" id="yddh" value=""  ></td>				
	</tr>
	<tr>
		<td class="a1" width="15%">家庭电话</td>
		<td class="a2" width="35%"><input type="text" name="linkman.jtdh" id="jtdh" value="" ></td>
		<td class="a1" width="15%">其他联系方式</td>
		<td class="a2" width="35%"><input type="text" name="linkman.qtlx" id="qtlx" value=""  ></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2" width="35%"><input type="text" name="linkman.mail" id="mail" value=""  ></td>		
		<td class="a1" width="15%">部门</td>
		<td class="a2" width="35%"><input type="text" name="linkman.dept" id="dept" value=""  ></td>
		
	</tr>
	<tr>
		<td class="a1" width="15%">性别</td>
		<td class="a2" width="35%">
          <select name="linkman.sex" id="sex">
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
        </td>
		<td class="a1" width="15%">职务</td>
		<td class="a2" width="35%"><input type="text" name="linkman.zw" id="zw" value=""  ></td>
	</tr>	
	<tr>
		<td class="a1" width="15%">生日</td>
		<td class="a2" width="35%"><input type="text" name="linkman.sr" id="sr" class="Wdate" onFocus="WdatePicker()">
		</td>	
		<td class="a1" width="15%">爱好</td>
		<td class="a2" width="35%"><input type="text" name="linkman.ah" id="ah"></td>	
	</tr>			
	<tr height="50">
		<td class="a1">备注</td>
		<td class="a2" colspan="3">
			<textarea rows="3" cols="50" name="linkman.remark" id="remark" style="width:80%" maxlength="500"></textarea>
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
