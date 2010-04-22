<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Sfd sfd = (Sfd)VS.findValue("sfd");
LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_id = info.getUser_id();
 
%>

<html>
<head>
<title>售后服务单查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="js/selSqr.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">

	var allCount = 2;
	
	function saveInfo(){
		if(document.getElementById("id").value == ""){
			alert("编号不能为空！");
			return;
		}
			
		if(document.getElementById("client_id").value == ""){
			alert("客户名称不能为空，请选择！");
			return;
		}		
		if(document.getElementById("linkman").value == ""){
			alert("联系人不能为空，请选择！");
			return;
		}
		if(document.getElementById("khxz").value == ""){
			alert("客户性质不能为空，请选择！");
			return;
		}	
		if(document.getElementById("wx_type").value == ""){
			alert("维修类型不能为空，请选择！");
			return;
		}
		if(document.getElementById("wxpz").value == ""){
			alert("维修凭证不能为空，请选择！");
			return;
		}	
		if(document.getElementById("fzr").value == ""){
			alert("派工人不能为空，请选择！");
			return;
		}
		if(document.getElementById("sqr").value == ""){
			alert("维修人不能为空，请选择！");
			return;
		}						
	
		document.sfdForm.submit();
	}
      	
    
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     

	
	function openWin(id){  //与退货单使用一个商品选择
		var destination = "selThdProc.html?openerId="+id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
    function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
		

	
</script>
</head>
<body  >
<form name="sfdForm" action="saveSfd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">售后服务单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(sfd.getId())%></td>
				
		<td class="a1" width="15%">接待日期</td>
		<td class="a2" width="35%"> <%=StringUtils.nullToStr(sfd.getJx_date())%></td>							
	</tr>
	<tr>			
		<td class="a1" width="15%">客户名称</td>		
		<td class="a2" width="35%"><%=StringUtils.nullToStr(StaticParamDo.getClientNameById(sfd.getClient_name())) %> </td>  
		
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"> <%=StringUtils.nullToStr(sfd.getLinkman())%></td>   		              		
	</tr>
	<tr>			
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(sfd.getMobile())%></td>	
				
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(sfd.getAddress())%></td>     
		        		
	</tr>
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"> <%=StringUtils.nullToStr(StaticParamDo.getRealNameById(sfd.getJxr()))%> </td>	  
		            	    		
	    <td class="a1" width="15%">求助方式</td>
		<td class="a2" width="35%"> <%=StringUtils.nullToStr(sfd.getQzfs()) %> </td>
									
	</tr>
	<tr>
		<td class="a1">状态</td>
		<td class="a2"><%=StringUtils.nullToStr(sfd.getState())%></td> 	
		 		 	
		
		<td class="a1">维修状态</td>
		<td class="a2"> <%=StringUtils.nullToStr(sfd.getWx_state())%></td>		 
		   
				 	
					
	</tr>
	<tr>
		<td class="a1">结单日期</td>
		<td class="a2" colspan="3" > <%=StringUtils.nullToStr(sfd.getJd_date())%> </td>			 				
	</tr>
	 
	<tr>
		<td class="a1" width="15%">详细说明</td>
		<td class="a2" colspan="3">
			<textarea rows="6" name="sfd.ms" id="ms" style="width:75%" maxlength="500" readonly="readonly" ><%=StringUtils.nullToStr(sfd.getMs())%></textarea><span style="color:red">*</span>
		</td>
	</tr>				
	<tr height="35">
		<td class="a1" colspan="4">
			 
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>