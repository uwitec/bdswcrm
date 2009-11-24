<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Bxfhd bxfhd=(Bxfhd)VS.findValue("bxfhd");
BxfhdProduct bxfhdProduct=(BxfhdProduct)VS.findValue("bxfhdProduct");
String[] wxszd=(String[])VS.findValue("wxszd");


 %>
<html>
<head>
<title>报修返还单修改</title>
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
	   
	   
	   	      
	   document.myform.action="updateBxfhd.html";
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
<body onload="initFzrTip();">
<FORM  name="myform" action="updateBxfhd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4">报修返还单信息</td>
	</tr>
	</thead>
	 	
	<tr>
		<td class="a1" width="15%">报修返还单编号</td>
		<td class="a2" width="35%"><input type="text" name="bxfhd.id" id="id" value="<%=StringUtils.nullToStr(bxfhd.getId())%>" readonly></td>	
		 
		<td class="a1">创建时间</td>
		 
		<td class="a2"><input type="text" name="bxfhd.cj_date" id="cj_date" value="<%=StringUtils.nullToStr(bxfhd.getCj_date()) %>" readonly>
		<!--  <img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('cj_date')); return false;">-->
		</td>
	</tr>
	<tr>
		
		<td class="a1" width="15%">客户名称</td>
		<td class="a2">
		   <input  id="client_name"  type="text"   length="20"  readonly="readonly"  value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(bxfhd.getClient_name())) %>"/> 		    
		   <input name="bxfhd.client_name" type="hidden" value="<%=StringUtils.nullToStr(bxfhd.getClient_name())%>"/>
		</td>	
		<td class="a1" width="15%">联系人</td>
    	<td class="a2">	  
		 <input  name="bxfhd.lxr" id="lxr"  readonly="readonly" value="<%=StringUtils.nullToStr(bxfhd.getLxr()) %>"/> 
         	   
		</td>						
	</tr>
	
	<tr>
		
		<td class="a1" width="15%">电话</td>
		<td class="a2">
		   <input  name="bxfhd.lxdh" id="lxdh"  type="text"   length="20" readonly="readonly" value=""<%=StringUtils.nullToStr(bxfhd.getLxdh()) %>/> 
              
		</td>	
		<td class="a1" width="15%">地址</td>
    	<td class="a2">	  
		 <input  name="bxfhd.address" id="address"  readonly="readonly"  value="<%=StringUtils.nullToStr(bxfhd.getAddress())%>"/> 
	   
		</td>						
	</tr>
	<tr>
		
		<td class="a1" width="15%">报修单编号</td>
		<td class="a2">
		   <input name="bxfhd.bxd_id"  id="bxd_id"  type="text"   length="20" readonly="readonly"   value="<%=StringUtils.nullToStr(bxfhd.getBxd_id())%>"/> 	    
		</td>	
		<td class="a1" width="15%">报修人</td>
    	<td class="a2">	  
		 <input  id="bxr" type="text"  readonly="readonly"  value="<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxfhd.getBxr()))%>"/> 		   
		 <input  name="bxfhd.bxr" type="hidden" value="<%=StringUtils.nullToStr(bxfhd.getBxr())%>">
		</td>						
	</tr>
	<tr>
		
		<td class="a1" width="15%">工程师</td>
		<td class="a2">
		   <input  id="gcs"  type="text"   length="20"  readonly="readonly"  value="<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxfhd.getGcs()))%>"/> 
           <input name="bxfhd.gcs" type="hidden" value="<%=StringUtils.nullToStr(bxfhd.getGcs())%>">
		</td>	
		<td class="a1" width="15%">返还日期</td>
    	<td class="a2">	  
    	 <%
    	    String rq;
    	    if(StringUtils.nullToStr(bxfhd.getFh_date()).equals(""))
    	    {
    	       rq=DateComFunc.getToday();
    	    }
    	    else
    	    {
    	       rq=StringUtils.nullToStr(bxfhd.getFh_date());
    	    }
    	  %>
		 <input type="text" name="bxfhd.fh_date" id="fh_date" value="<%=rq%>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('fh_date')); return false;">		   
		</td>						
	</tr>
	<tr>
	    <td class="a1" width="15%">经手人</td>
		<td class="a2">
		   <input  id="brand"  type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(bxfhd.getJxr()) %>"/> 
           <div  id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <input type="hidden" name="bxfhd.jxr" id="fzr" value="<%=StringUtils.nullToStr(bxfhd.getJxr()) %>"/> 		    
		</td>	
		<td class="a1" width="15%">返还状态</td>
		<td class="a2" width="35%">
			<select name="bxfhd.state" id="state">
				<option value="待返还" <%if(StringUtils.nullToStr(bxfhd.getState()).equals("待返还"))out.print("selected");%>>待返还</option>
				<option value="已返还" <%if(StringUtils.nullToStr(bxfhd.getState()).equals("已返还"))out.print("selected");%>>已返还</option>
			</select>		
		</td>		
	</tr>
</table>
 <br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">报修返还产品信息</td>
	</tr>
	</thead>
</table>
  
<table width="100%"  align="center" id="lsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>产品规格</td> 
		<td>目标库</td>
		<td>序列号</td>
		<td>备注</td>		 
	</tr>
	</thead>
 
	<tr>
		<td class="a2">
			<input type="text" id="product_name" name="bxfhdProduct.product_name" value="<%=StringUtils.nullToStr(bxfhdProduct.getProduct_name()) %>" size="35" readonly="readonly">
			<input type="hidden" id="product_id" name="bxfhdProduct.product_id" value="<%=StringUtils.nullToStr(bxfhdProduct.getProduct_id()) %>">
			 
		</td>
		<td class="a2">
			<input type="text" id="product_xh" name="bxfhdProduct.product_xh" value="<%=StringUtils.nullToStr(bxfhdProduct.getProduct_xh()) %>" readonly="readonly">
		</td>
	    
	    <td class="a2">	   
	         <input type="text" id="kf" name="kf" value="好件库" size="7" readonly="readonly">
	    </td>
	    
		<td class="a2">
			<input type="text" id="qz_serial_num" name="bxfhdProduct.qz_serial_num" value="<%=StringUtils.nullToStr(bxfhdProduct.getQz_serial_num()) %>" readonly="readonly">
		</td>	
			
		<td class="a2">
		    <input type="text" id="remark" name="bxfhdProduct.remark" value="<%=StringUtils.nullToStr(bxfhdProduct.getRemark()) %>" readonly="readonly">
		</td>
	</tr>
</table>
 
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">	
<!--  
	<tr>    
	    <td class="a1">报修费用</td>
	     <td class="a2" colspan="3">
	       <select name="bxfhd.isfy" id="isfy" onchange="showfy(this.value)">
	         <option value="否" <%if(StringUtils.nullToStr(bxfhd.getIsfy()).equals("否"))out.print("selected"); %>>否</option>
	         <option value="是" <%if(StringUtils.nullToStr(bxfhd.getIsfy()).equals("是"))out.print("selected"); %>>是</option>
	       </select>
	     </td>
	</tr>
	<tr id="fyid" style="display:none">
	    <td class="a1">应付费用</td>
		<td class="a2"><input type="text"  name="bxfhd.ssje" id="ssje" value=""/></td>
		<td class="a1" widht="20%">付款账户</td>
		<td class="a2"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(bxfhd.getFkzh())) %>" readonly>
		<input type="hidden" id="skzh"  name="bxfhd.fkzh" value="<%=StringUtils.nullToStr(bxfhd.getFkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand"><font color="red">*</font>
	</tr>
-->		
	  
	<tr height="35">	
		<td class="a1">报修所在地</td>
		<td class="a2"  >
			<select name="bxfhdProduct.bxaddress" id="bxszd">
			 <option value=""></option>
			<%
			   if(wxszd != null && wxszd.length>0){
			     for(int i=0;i<wxszd.length;i++){
			       String szd=wxszd[i];
			 %>
			      <option value="<%=szd %>" <%if(!(StringUtils.nullToStr(bxfhdProduct.getBxaddress()).equals("")))out.print("selected"); %>><%=szd %></option>
			 <%
			      }
			   }
			  %>
			</select>
			
		</td>
		<td class="a1">产品状态</td>
		<td class="a2"  >
			<input type="text" name="bxfhdProduct.bxstate" id="bxstate" value="<%=StringUtils.nullToStr(bxfhdProduct.getBxstate())%>" >
		</td>
	</tr>

	 
	
	<tr >
	   <td class="a1" width="15%">随机附件</td>
	   <td class="a2" >
	      <% String fj[]=StringUtils.nullToStr(bxfhdProduct.getFj()).split(","); %>
	      <input type="checkbox" name="fj" value="硒鼓" <%for(int i=0;i<fj.length;i++){if(fj[i].equals("硒鼓"))out.print("checked");} %>>硒鼓&nbsp;&nbsp; 
	      <input type="checkbox" name="fj" value="墨盒" <%for(int i=0;i<fj.length;i++){if(fj[i].equals("墨盒"))out.print("checked");} %>>墨盒&nbsp;&nbsp; 
	      <input type="checkbox" name="fj"   value="电源线"<%for(int i=0;i<fj.length;i++){if(fj[i].equals("电源线"))out.print("checked");} %>>电源线&nbsp;&nbsp; 
	      <input type="checkbox" name="fj"  value="数据线"<%for(int i=0;i<fj.length;i++){if(fj[i].equals("数据线"))out.print("checked");} %>>数据线&nbsp;&nbsp; 
	      <input type="checkbox" name="fj"   value="适配器"<%for(int i=0;i<fj.length;i++){if(fj[i].equals("适配器"))out.print("checked");} %>>适配器&nbsp;&nbsp; 
	      <input type="checkbox" name="fj"   value="电池"<%for(int i=0;i<fj.length;i++){if(fj[i].equals("电池"))out.print("checked");} %>>电池&nbsp;&nbsp; 
	       <input type="checkbox" name="fj"   value="包"<%for(int i=0;i<fj.length;i++){if(fj[i].equals("包"))out.print("checked");} %>>包
	   </td>
	   
	    <td class="a1" width="15%">其他附件</td>
	    <td class="a2">
	   
	    <input type="text" name="bxfhdProduct.qtfj" readonly="readonly" id="qtfj" value="<%=StringUtils.nullToStr(bxfhdProduct.getQtfj()) %>"  >
	   </td>
	</tr>
	 
	<tr height="35">
		 <td class="a1" width="15%">备件</td>
		<td class="a2" colspan="3">
			<input type="text" name="bxfhdProduct.bj" id="bj" readonly="readonly" value="<%=StringUtils.nullToStr(bxfhdProduct.getBj()) %>">
		</td>	
	</tr>
		
	
	<tr height="35">
		 <td class="a1" width="15%">故障及分析</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="bxfhdProduct.gzfx" id="gzfx" style="width:75%"><%=StringUtils.nullToStr(bxfhdProduct.getGzfx()) %> </textarea>
		</td>	
	</tr>
	<tr height="35">
		 <td class="a1" width="15%">排除过程</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="bxfhdProduct.pcgc" id="pcgc" style="width:75%"><%=StringUtils.nullToStr(bxfhdProduct.getPcgc()) %> </textarea>
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
			<textarea rows="2" name="bxfhd.ms" id="ms" style="width:75%"> <%=StringUtils.nullToStr(bxfhd.getMs()) %> </textarea>
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSub" value="确 定" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</FORM>

</BODY>
</HTML>