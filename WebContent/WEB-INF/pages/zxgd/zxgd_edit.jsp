<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Map zxgd=(Map)VS.findValue("zxgd");
 %>
<html>
<head>
<title>咨询工单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	function saveInfo(vl)
	{ 
		if(vl == '1'){
			document.getElementById("state").value = "处理中";
		}else{
			document.getElementById("state").value = "已完成";
		}	
	      
	   if(document.getElementById("fzr").value=="")
	   {
	      alert("回复人不能为空，请填写！");
	      return;
	   }
	   
	   if(document.getElementById("hf_date").value=="")
	   {
	      alert("回复日期不能为空，请填写！");
	      return;
	   }
	   
	   if(document.getElementById("content").value=="")
	   {
	      alert("回复内容不能为空，请填写！");
	      return;
	   }
	   		   	      
	   document.myform.action="updateZxgd.html";
	   if(document.getElementById("state").value == "已完成")
	   {
			if(window.confirm("确认要结束该咨询吗，结单后将无法修改！")){				
				document.myform.submit();		
			}
 	     }
	     else
	     { 
	         document.myform.submit();	
	     }
	     
	}	

   	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}	
</script>
</head>
<body onload="initFzrTip();">
<FORM  name="myform" action="updateZxgd.html" method="post">
<input type="hidden" name="zxgds.state" id="state" value="<%=StringUtils.nullToStr(zxgd.get("state")) %>">
<input type="hidden" name="zxgds.id" id="id" value="<%=StringUtils.nullToStr(zxgd.get("id")) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4">咨询工单信息</td>
	</tr>
	</thead>
	 	
	<tr>
		<td class="a1" width="15%">回复人</td>
		<td class="a2">
		   <input  id="brand"  type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById((String)zxgd.get("hfr")) %>"> <font color="red">*</font>
           <div  id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <input type="hidden" name="zxgds.hfr" id="fzr" value="<%=StringUtils.nullToStr(zxgd.get("hfr")) %>"> 
		</td>
		<td class="a1">回复日期</td>
		<%
		  String hfdate= StringUtils.nullToStr(zxgd.get("hf_date"));
		  String rq="";
		  if(!hfdate.equals(""))
		  {
		    rq=hfdate;
		  } 
		  else
		  {
		    rq=DateComFunc.getToday();
		  }
		 %>
		<td class="a2"><input type="text" name="zxgds.hf_date" id="hf_date" value="<%=rq %>" size="15"  class="Wdate" onFocus="WdatePicker()">
		<font color="red">*</font>
		</td>
	</tr>
	<tr>
	  <td class="a1" width="15%">回复内容</td>
	  <td class="a2" width="85%" colspan="3">
		<textarea rows="4" name="zxgds.content" id="content" style="width:75%"><%=StringUtils.nullToStr(zxgd.get("content")) %></textarea>
	  </td> 
	</tr>
	<tr>
	  <td class="a1" width="15%">客户意见</td>
	  <td class="a2" width="85%" colspan="3">
		<textarea rows="4" name="zxgds.khyj" id="khyj" style="width:75%"><%=StringUtils.nullToStr(zxgd.get("khyj")) %></textarea>
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
		<td class="a2"><input type="text" name="zxgds.sfd_id"   id="sfd_id" value="<%=StringUtils.nullToStr(zxgd.get("sfd_id")) %>" maxlength="20" readonly></td>	
		 	
		<td class="a1">接待日期</td>
		<td class="a2"><input type="text"   id="jx_date" value="<%=StringUtils.nullToStr(zxgd.get("jx_date")) %>" readonly>
		 </td>						
	</tr>
	<tr>			
		<td class="a1" width="15%">往来单位</td>
		<td class="a2">
		<input type="text" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(zxgd.get("client_name")))%>"  maxlength="70" readonly>
		 </td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
		   <input  id="jxr"  type="text"   length="20"   value="<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(zxgd.get("jxr"))) %>" readonly/>  
		</td>
	</tr>
	<tr>
	
	   <td class="a1" width="15%">联系人</td>
		<td class="a2">
		 	<input id="linkman" type="text" length="20" value="<%=StringUtils.nullToStr(zxgd.get("linkman")) %>" readonly/> 		    
		</td>
		<td class="a1" width="15%">电话</td>
		<td class="a2">
		 	<input  id="mobile" type="text" length="20" value="<%=StringUtils.nullToStr(zxgd.get("mobile")) %>" readonly/> 
		</td>
			
	</tr>
	<tr>
	   <td class="a1" width="15%">地址</td>
		<td class="a2"  colspan="3">
		 	<input  id="address" type="text" length="60" value="<%=StringUtils.nullToStr(zxgd.get("address")) %>" readonly/> 
		 </td>
	</tr>
	<tr>
	 <td class="a1" width="15%">内容描述</td>
	 <td class="a2" width="85%" colspan="3">
			<textarea rows="4"  id="ms" style="width:75%" readonly> <%=StringUtils.nullToStr(zxgd.get("ms")) %> </textarea>
	</td> 
	</tr>			
</table> 
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="btnSub" value="结 单" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
<font color="red">注：“草稿”指咨询工单暂存，可修改；“结单”后咨询工单不可修改。</font>
<BR><BR>
</FORM>
</BODY>
</HTML>