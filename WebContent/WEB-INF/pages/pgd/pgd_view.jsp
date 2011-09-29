<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Map pgd=(Map)VS.findValue("pgd");
String[] wxlx=(String[])VS.findValue("wxlx");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>派工单</title>
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
	function saveInfo()
	{
	   if(document.getElementById("fzr").value=="")
	   {
	      alert("经手人不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("sqr").value=="")
	   {
	      alert("维修人不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("p_gzlx").value=="")
	   {
	      alert("故障类型不能为空，请填写！");
	      return;
	   }
	    if(document.getElementById("p_khxz").value=="")
	   {
	      alert("客户性质不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("p_wx_type").value=="")
	   {
	      alert("维修类型不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("p_wxpz").value=="")
	   {
	      alert("维修凭证不能为空，请填写！");
	      return;
	   }
	    if(document.getElementById("p_gzfx").value=="")
	   {
	      alert("故障分析不能为空，请填写！");
	      return;
	   }			   	      
	   document.myform.action="updatePgd.html";
	   document.myform.submit();
	}	

    function clearVl(){       
       	  document.getElementById("product_name").value="";
		  document.getElementById("product_gg").value="";
		  document.getElementById("product_serial_num").value="";
		  document.getElementById("product_remark").value="";      
    }	
     
	function openWin(){
		var destination = "selBxProcCkd.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	 
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}
	
	 
	function opengcsWin(){
	    var destination = "selGcs.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'工程师',fea);	
	}		
	
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}

	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
	
	function f_enter(){
	    if (window.event.keyCode==13){
	        sendSerialNum();
	    }
	}
	
	//发送序列号
	function sendSerialNum(){
		var serialNum = dwr.util.getValue("s_nums");
		if(serialNum == ""){
		   
			return;
		}
		dwrService.getBxdRecordorBuyRecord(serialNum,setProductInfo);		
	}
	
	function showfy(fyidvalue)
	{
	  if(fyidvalue=='否')
	  {
	    document.getElementById("fyid").style.display="none";
	  }
	  else
	  {
	    document.getElementById("fyid").style.display=""; 
	  }
	}		
</script>
</head>
<body >
<FORM  name="myform" action="updatePgd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4">派工信息</td>
	</tr>
	</thead>
	 	
		<tr>
		<td class="a1" width="15%">派工单编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(pgd.get("p_id"))%></td>	
		 
		<td class="a1">派工日期</td>
		
		<td class="a2"><%=StringUtils.nullToStr(pgd.get("p_yy_date"))%>
		
		</td>
	</tr>
	<tr>
		
		<td class="a1" width="15%">派工人</td>
		<td class="a2">
		   <%=StaticParamDo.getRealNameById((String)pgd.get("p_pgr"))%>		    
		</td>	
		<td class="a1" width="15%">维修人</td>
    	<td class="a2">	  
		<%=StaticParamDo.getRealNameById((String)pgd.get("p_wxr"))%>      
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%">			 
		<%=StringUtils.nullToStr(pgd.get("p_state"))%>				
		</td>
		<td class="a1" width="15%">维修状态</td>
		<td class="a2" width="35%">
		<%=StringUtils.nullToStr(pgd.get("p_wx_state"))%>
		</td>			
	</tr>
	
	<tr>
		<td class="a1" width="15%">结单日期</td>
		<td class="a2" width="35%" colspan="3">			 
		<%=StringUtils.nullToStr(pgd.get("p_jd_date"))%>				
		</td>
		 		
	</tr>	 
</table> 
 <br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">售服信息</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">售服单编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(pgd.get("p_sfd_id")) %>
		</td>	
		 	
		<td class="a1" width="15%">接待日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(pgd.get("jx_date")) %>
		 </td>						
	</tr>
	<tr>			
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%">
		<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(pgd.get("client_name")))%>
		 </td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		 <%=StaticParamDo.getRealNameById(StringUtils.nullToStr(pgd.get("jxr")))%>         		    
		</td>			
	</tr>
	<tr>
	
	   <td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%">
		<%=StringUtils.nullToStr(pgd.get("linkman"))%>	    
		</td>
		<td class="a1" width="15%">电话</td>
		<td class="a2" width="35%">
		 <%=StringUtils.nullToStr(pgd.get("mobile"))%>
		</td>
			
	</tr>
	<tr>
	   <td class="a1" width="15%">地址</td>
		<td class="a2" width="35%">
		 <%=StringUtils.nullToStr(pgd.get("address"))%>
		 </td>
		<td class="a1" width="15%">求助方式</td>
		<td class="a2" width="35%">
		 <%=StringUtils.nullToStr(pgd.get("qzfs"))%>
		 </td>	
	</tr>
	<tr>
	 <td class="a1" width="15%">内容描述</td>
	 <td class="a2" width="85%" colspan="3">
			<textarea rows="2"  id="ms" style="width:75%" readonly="readonly"><%=StringUtils.nullToStr(pgd.get("ms")) %></textarea>
	</td> 
	</tr>			
</table>
 
 <br>
 
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">		  
	<thead>
	<tr>
		<td colspan="4">维修信息</td>
	</tr>
	</thead>
	 <tr>	
	   <td class="a1" width="15%">故障类型</td>
		<td class="a2" width="35%">
		  <%=StringUtils.nullToStr(pgd.get("p_gzlx"))%>		
		</td>
		<td class="a1" width="15%">客户性质</td>
		<td class="a2" width="35%">		   
		  <%=StringUtils.nullToStr(pgd.get("p_khxz"))%>				
		</td>			
	</tr>
	<tr>	   
		<td class="a1" width="15%">维修类型</td>
		<td class="a2" width="35%">
		 	<%=StringUtils.nullToStr(pgd.get("p_wx_type"))%>				     
		</td>
		<td class="a1" width="15%">维修凭证</td>
		<td class="a2" width="35%">
		 	<%=StringUtils.nullToStr(pgd.get("p_wxpz"))%>
		</td>		
	</tr>
		
	<tr height="35">
		 <td class="a1" width="15%">故障及分析</td>
		 <td class="a2" colspan="3">
			<textarea rows="2" name="pgds.p_gzfx" id="p_gzfx" readonly="readonly" style="width:75%"><%=StringUtils.nullToStr(pgd.get("p_gzfx"))%></textarea>
	     </td>	
	</tr>		 
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">备注</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="4" name="pgds.p_ms" id="p_ms" readonly="readonly" style="width:75%"><%=StringUtils.nullToStr(pgd.get("p_ms"))%></textarea>
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">		
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</FORM>
</BODY>
</HTML>