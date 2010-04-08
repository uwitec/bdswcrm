<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String[] lxrnld = (String[])VS.findValue("lxrnld"); 
%>
<html>
<head>
<title>联系人添加</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script  type="text/javascript">
     function saveInfo()
     {
	    
	    if(document.getElementById("client_id").value=="")
	    {
	        alert("往来单位不能为空！");
	        return;
	    }
		if(document.getElementById("name").value == "")
		{
			alert("联系人姓名不能为空 ！");
			return;
		}
		if(document.getElementById("mail").value!="")
	    {
	      var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
          if(reg.test(document.getElementById("mail").value)==false)
          {
             alert("邮箱格式不正确！");
             return ;
          }
	    }
	    
	    if(document.getElementById("dept").value == ""){
			alert("联系人的部门不能为空 ！");
			return;
		}
		if(document.getElementById("zw").value == ""){
			alert("联系人的职务不能为空 ！");
			return;
		}
		
		if((document.getElementById("gzdh").value == "") && (document.getElementById("yddh").value == "")){
			alert("办公电话和移动电话不能都为空，至少要填写一个！");
			return;
		}
	    document.myform.action="saveLxr.html";
		document.myform.submit(); 
	   
	   
    }
		
	function openClientWin()
	{  
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'详细信息',fea);		 
	}	
</script>
 
</head>
<body   oncontextmenu="return false;">
<form name="myform" action="addLxr.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">联系人</td>
	</tr>
	</thead>
	<tr>
	   <td class="a1" width="15%">往来单位</td>
	   <td class="a2"  width="35%" colspan="3">
	      <input type="text"   id="client_name"   size="37" maxlength="50" readonly="readonly" />
	      <input type="hidden" name="linkman.clients_id" id="client_id"/>
	      <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openClientWin()" style="cursor:hand">
	      <font color="red">*</font>
	    </td>
	</tr>
	<tr> 
	     
		<td class="a1" width="15%">姓名</td>
		<td class="a2" width="35%"><input type="text" name="linkman.name" id="name" value=""><font color="red">*</font></td>
		<td class="a1" width="15%">称呼</td>
		<td class="a2" width="35%"><input type="text" name="linkman.ch" id="ch" value="" style="width:85%"></td>			
	</tr>
	<tr>
	    <td class="a1" width="15%">部门</td>
		<td class="a2" width="35%"><input type="text" name="linkman.dept" id="dept" value="" style="width:85%"><font color="red">*</font></td>
		<td class="a1" width="15%">职务</td>
		<td class="a2" width="35%"><input type="text" name="linkman.zw" id="zw" value="" style="width:85%"><font color="red">*</font></td>
	</tr>
	<tr>
		<td class="a1" width="15%">办公电话</td>
		<td class="a2" width="35%"><input type="text" name="linkman.gzdh" id="gzdh" value=""  ></td>
		<td class="a1" width="15%">移动电话</td>
		<td class="a2" width="35%"><input type="text" name="linkman.yddh" id="yddh" value=""  ></td>				
	</tr>
	<tr>
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2"  colspan="3"><input type="text" name="linkman.mail" id="mail" value=""   size="50" ></td>
	</tr>
	<tr>
		<td class="a1" width="15%">QQ</td>
		<td class="a2" width="35%"><input type="text" name="linkman.qq" id="qq" value="" style="width:85%"></td>	
		<td class="a1" width="15%">MSN</td>
		<td class="a2" width="35%"><input type="text" name="linkman.msn" id="msn" value="" style="width:85%"></td>	
	</tr>
	
	<tr>
		<td class="a1" width="15%">性别</td>
		<td class="a2" width="35%">
          <select name="linkman.sex" id="sex">
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
        </td>
		<td class="a1" width="15%">年龄段</td>
		<td class="a2" width="35%">
			<select name="linkman.nld" id="nld" style="width:85%">
				<option value=""></option>
				<%
				if(lxrnld != null && lxrnld.length > 0){ 
					for(int i=0;i<lxrnld.length;i++){
				%>
				<option value="<%=lxrnld[i] %>"><%=lxrnld[i] %></option>
				<%
					}
				}
				%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="a1" width="15%">类型</td>
		<td class="a2" width="35%">
			<select name="linkman.lx" id="lx">
				<option value="主联系人">主联系人</option>
				<option value="联系人">联系人</option>
			</select>
		</td>
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
