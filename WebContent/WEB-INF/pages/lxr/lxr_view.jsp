<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
ClientsLinkman linkman=(ClientsLinkman)VS.findValue("linkman");
 %>
<html>
<head>
<title>联系人视图 </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
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
<form name="myform" action="viewClient.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">联系人</td>
	</tr>
	</thead>
	<tr>
	   <td class="a1" width="15%">往来单位</td>
	   <td class="a2"  width="35%" colspan="3">
	      <%=linkman.getClients_id() %>  
	    </td>
	</tr>
		 
	</tr>
	<tr> 
	     
		<td class="a1" width="15%">姓名</td>
		<td class="a2" width="35%"><%=linkman.getName() %></td>
		<td class="a1" width="15%">类型</td>
		<td class="a2" width="35%">
			 <%=linkman.getLx() %>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">性别</td>
		<td class="a2" width="35%">
          <%=linkman.getSex()%>
        </td>
		<td class="a1" width="15%">职务</td>
		<td class="a2" width="35%"><%=linkman.getZw()%></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">部门</td>
		<td class="a2" width="35%"><%=linkman.getDept()%></td>
		<td class="a1" width="15%">工作电话</td>
		<td class="a2" width="35%"><%=linkman.getGzdh()%></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">爱好</td>
		<td class="a2" width="35%"><%=linkman.getAh() %></td>	
		<td class="a1" width="15%">生日</td>
		<td class="a2" width="35%"><%if(null!=linkman.getSr()) out.print(linkman.getSr().substring(0,11)); %>
		   
		</td>
	</tr>		
	<tr>
		<td class="a1" width="15%">移动电话</td>
		<td class="a2" width="35%"><%=linkman.getYddh() %></td>	
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2" width="35%"><%=linkman.getMail() %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">家庭电话</td>
		<td class="a2" width="35%"><%=linkman.getJtdh() %></td>
		<td class="a1" width="15%">其他联系方式</td>
		<td class="a2" width="35%"><%=linkman.getQtlx() %></td>		
	</tr>		
	
	 
	<tr height="50">
		<td class="a1">备注</td>
		<td class="a2" colspan="3">
			<textarea rows="3" cols="50" name="linkman.remark" id="remark" style="width:80%" maxlength="500"><%=linkman.getRemark() %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>			
</table>
</form>
</body>
</html>
