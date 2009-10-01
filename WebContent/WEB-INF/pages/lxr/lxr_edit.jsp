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
<title>联系人修改 </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script  type="text/javascript">
     function saveInfo()
     {
	    
	   
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
	    document.myform.action="updateLxr.html";
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
   <input type="hidden" name="linkman.id" value="<%=linkman.getId() %>">
	<thead>
	<tr>
		<td colspan="4">联系人</td>
	</tr>
	</thead>
	<tr>
	   <td class="a1" width="15%">往来单位</td>
	   <td class="a2"  width="35%" colspan="3">
	      <input type="text"   id="client_name"   size="37" maxlength="50" readonly="readonly" value="<%=linkman.getClients_id() %>"/>
	    </td>
	</tr>
	<tr> 
	     
		<td class="a1" width="15%">姓名</td>
		<td class="a2" width="35%"><input type="text" name="linkman.name" id="name" value="<%=linkman.getName() %>"><font color="red">*</font></td>
		<td class="a1" width="15%">类型</td>
		<td class="a2" width="35%">
			<select name="linkman.lx" id="lx">
				<option value="主联系人"  <%if(linkman.getLx().equals("主联系人"))out.print("selected"); %>>主联系人</option>
				<option value="联系人"   <%if(linkman.getLx().equals("联系人"))out.print("selected"); %>>联系人</option>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">性别</td>
		<td class="a2" width="35%">
          <select name="linkman.sex" id="sex">
				<option value="男" <%if(linkman.getSex().equals("男"))out.print("selected"); %>>男</option>
				<option value="女" <%if(linkman.getSex().equals("女"))out.print("selected"); %>>女</option>
			</select>
        </td>
		<td class="a1" width="15%">职务</td>
		<td class="a2" width="35%"><input type="text" name="linkman.zw" id="zw" value="<%=linkman.getZw() %>"  ></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">部门</td>
		<td class="a2" width="35%"><input type="text" name="linkman.dept" id="dept" value="<%=linkman.getDept() %>"  ></td>
		<td class="a1" width="15%">座机</td>
		<td class="a2" width="35%"><input type="text" name="linkman.gzdh" id="gzdh" value="<%=linkman.getGzdh()%>"  ></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">爱好</td>
		<td class="a2" width="35%"><input type="text" name="linkman.ah" id="ah" value="<%=linkman.getAh()%>"></td>	
		<td class="a1" width="15%">生日</td>
		<td class="a2" width="35%"><input type="text" name="linkman.sr" id="sr" value="<%=StringUtils.nullToStr(linkman.getSr())%>"class="Wdate" onFocus="WdatePicker()">
		</td>
	</tr>		
	<tr>
		<td class="a1" width="15%">移动电话</td>
		<td class="a2" width="35%"><input type="text" name="linkman.yddh" id="yddh" value="<%=linkman.getYddh()%>"  ></td>	
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2" width="35%"><input type="text" name="linkman.mail" id="mail" value="<%=linkman.getMail()%>"  ></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">家庭电话</td>
		<td class="a2" width="35%"><input type="text" name="linkman.jtdh" id="jtdh" value="<%=linkman.getJtdh()%>" ></td>
		<td class="a1" width="15%">其他联系方式</td>
		<td class="a2" width="35%"><input type="text" name="linkman.qtlx" id="qtlx" value="<%=linkman.getQtlx()%>"  ></td>		
	</tr>		
	
	 
	<tr height="50">
		<td class="a1">备注</td>
		<td class="a2" colspan="3">
			<textarea rows="3" cols="50" name="linkman.remark" id="remark" style="width:80%" maxlength="500"><%=linkman.getRemark()%></textarea>
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