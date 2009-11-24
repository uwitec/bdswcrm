<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Map wxclds=(Map)VS.findValue("wxclds");
Map wxcldProducts=(Map)VS.findValue("wxcldProducts");
String[] wxlx=(String[])VS.findValue("wxlx");
if(null==wxcldProducts)
{
  wxcldProducts=new HashMap();
}
 
 %>
<html>
<head>
<title>维修处理单</title>
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
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
			
      	
	function saveInfo()
	{
		if(document.getElementById("product_name").value=="")
	   {
	      alert("产品名称不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("product_wxlx").value=="")
	   {
	      alert("故障类型不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("product_gmts").value=="")
	   {
	      alert("购买天数不能为空，请填写！");
	      return;
	   }
	    if(document.getElementById("product_clfs").value=="")
	   {
	      alert("维修处理方式不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("product_serial_num").value=="")
	   {
	      alert("产品序列号不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("w_isfy").value=="是")
	   {
	      if(document.getElementById("skzh").value=="")
	      {
	        alert("收款账户不能为空，请填写！");
	         return;
	      }
	      
	     	      
	      var price = document.getElementById("w_skje");			
			if(price != null){
				if(!InputValid(price,0,"float",0,1,99999999,"收款金额")){
					price.focus();
					return;
				}
			}
	   }
	    	   	    
	   document.myform.action="updateWxcld.html";
	   document.myform.submit();
	}	

    function clearVl(){       
       	  document.getElementById("product_name").value="";
		  document.getElementById("product_id").value="";
		   document.getElementById("product_xh").value="";
		   document.getElementById("product_wxlx").value="";
		   document.getElementById("product_gmts").value="";
		  document.getElementById("product_clfs").value="";
		  document.getElementById("product_serial_num").value="";
		  document.getElementById("product_remark").value="";      
    }	
     
	function openWin(){
		var destination = "selWxProduct.html";		
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';		
		window.open(destination,"",fea);	
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
	function getfy()
	{
	   var fy='<%=StringUtils.nullToStr(wxclds.get("w_isfy"))%>';
	   if(fy=="是")
	   {
	      document.getElementById("fyid").style.display="";
	   }
	}		
</script>
</head>
<body onload="getfy()">
<FORM  name="myform" action="updateWxcld.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4">维修基础信息</td>
	</tr>
	</thead>
	 	
		<tr>
		<td class="a1" width="15%">维修处理单编号</td>
		<td class="a2" width="35%"><input type="text" name="wxcld.w_id" id="w_id" value="<%=StringUtils.nullToStr(wxclds.get("w_id")) %>" readonly></td>	
		 
		<td class="a1">维修日期</td>
		<%
		  String bxdate= StringUtils.nullToStr(wxclds.get("w_jx_date"));
		  String rq="";
		  if(!bxdate.equals(""))
		  {
		    rq=bxdate;
		  } 
		  else
		  {
		    rq=DateComFunc.getToday();
		  }
		 %>
		<td class="a2"><input type="text" name="wxcld.w_jx_date" id="w_jx_date" value="<%=rq %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('w_jx_date')); return false;"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		
		
		<td class="a1" width="15%">维修人</td>
    	<td class="a2">	  
		 <input  name="sqr_text" id="sqr_text"    value="<%=StaticParamDo.getRealNameById((String)wxclds.get("w_wxr")) %>" readonly/>  
         
        
		    <input type="hidden" name="wxcld.w_wxr" id="w_wxr"  value="<%=StringUtils.nullToStr(wxclds.get("w_wxr")) %>"/> 
		   
		</td>	
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%"  >
			<select name="wxcld.w_state" id="w_state">
				<option value="已保存" <%if(StringUtils.nullToStr(wxclds.get("w_state")).equals("已保存"))out.print("selected"); %> >已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(wxclds.get("w_state")).equals("已提交"))out.print("selected"); %> >已提交</option>
			</select>		
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
		<td class="a2"><input type="text" name=""   id="" value="<%=StringUtils.nullToStr(wxclds.get("id")) %>" maxlength="20" readonly></td>	
		 	
		<td class="a1">接待日期</td>
		<td class="a2"><input type="text"   id="" value="<%=StringUtils.nullToStr(wxclds.get("jx_date")) %>" readonly>
		 </td>						
	</tr>
	<tr>			
		<td class="a1" width="15%">往来单位</td>
		<td class="a2">
		<input type="text" id="client_name" name="wxcld.w_client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(wxclds.get("client_name")))%>"  maxlength="50" readonly>
		 </td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
		   <input  id="jxr"  type="text"   length="20"   value="<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(wxclds.get("jxr"))) %>" readonly/>  
           		    
		</td>
			
	</tr>
	<tr>
	
	   <td class="a1" width="15%">联系人</td>
		<td class="a2">
		 	<input id="linkman" name="wxcld.w_linkman" type="text" length="20" value="<%=StringUtils.nullToStr(wxclds.get("linkman")) %>" readonly/> 		    
		</td>
		<td class="a1" width="15%">电话</td>
		<td class="a2">
		 	<input  id="mobile" name="wxcld.w_mobile" type="text" length="20" value="<%=StringUtils.nullToStr(wxclds.get("mobile")) %>" readonly/> 
		</td>
			
	</tr>
	<tr>
	   <td class="a1" width="15%">地址</td>
		<td class="a2">
		 	<input  id="address" name="wxcld.w_address" type="text" length="20" value="<%=StringUtils.nullToStr(wxclds.get("address")) %>" readonly/> 
		 </td>
		<td class="a1" width="15%">求助方式</td>
		<td class="a2">
		 	<input  id="qzfs" type="text" length="20" value="<%=StringUtils.nullToStr(wxclds.get("qzfs")) %>" readonly/> 
		 </td>	
	</tr>
	<tr>
	 <td class="a1" width="15%">内容描述</td>
	 <td class="a2" width="85%" colspan="3">
			<textarea rows="2"  id="ms" style="width:75%" readonly> <%=StringUtils.nullToStr(wxclds.get("ms")) %> </textarea>
	</td> 
	</tr>			
</table>
 
 <br>
 
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">		  
	<thead>
	<tr>
		<td colspan="4">派工信息</td>
	</tr>
	</thead>
	
	<tr>
		<td class="a1" width="15%">派工单编号</td>
		<td class="a2"><input type="text" name="wxcld.w_pgd_id"   id="w_pgd_id" value="<%=StringUtils.nullToStr(wxclds.get("w_pgd_id")) %>" maxlength="20" readonly></td>	
		 	
		<td class="a1">派工日期</td>
		<td class="a2"><input type="text"   id="" value="<%=StringUtils.nullToStr(wxclds.get("p_yy_date")) %>" readonly>
		 </td>						
	</tr>
	 <tr>	
	   <td class="a1" width="15%">故障类型</td>
		<td class="a2" width="35%">
		 	<input type="text" name=""   id="" value="<%=StringUtils.nullToStr(wxclds.get("p_gzlx"))%>" maxlength="20" readonly>
		</td>
		<td class="a1" width="15%">客户性质</td>
		<td class="a2" width="35%">
		      <input type="text" name=""   id="" value="<%=StringUtils.nullToStr(wxclds.get("p_khxz"))%>" maxlength="20" readonly>
		</td>			
	</tr>
	
	<tr>	   
		<td class="a1" width="15%">维修类型</td>
		<td class="a2" width="35%">
		  
		 	   <input type="text" name=""   id="" value="<%=StringUtils.nullToStr(wxclds.get("p_wx_type"))%>" maxlength="20" readonly>
			         
		</td>
		<td class="a1" width="15%">维修凭证</td>
		<td class="a2" width="35%">
		 	   <input type="text" name=""   id="" value="<%=StringUtils.nullToStr(wxclds.get("p_wxpz"))%>" maxlength="20" readonly>		 	 
	   </td>		
	</tr>
	<tr>
	<td class="a1" width="15%">派工人</td>
		<td class="a2" colspan="3">
		   <input  id="brand"  type="text"   length="20"   value="<%=StaticParamDo.getRealNameById((String)wxclds.get("p_pgr")) %> " readonly/>  
           
		   <input type="hidden" name="wxcld.w_pgr" id="w_pgr" value="<%=StringUtils.nullToStr(wxclds.get("p_pgr")) %>"/> 
		    
		</td>		
	</tr>	
	<tr height="35">
		 <td class="a1" width="15%">故障及分析</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="" id="p_gzfx" style="width:75%" readonly><%=StringUtils.nullToStr(wxclds.get("p_gzfx"))%></textarea>
		</td>	
	</tr>		 
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">维修产品信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="lsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>产品规格</td> 
		<td>故障类型</td>
		<td>购买天数</td>
		<td>处理方式</td>	
		<td>序列号</td>	
		<td>备注</td>		 
	</tr>
	</thead>
 <tr>
		<td class="a2">
			<input type="text" id="product_name" name="wxcldProduct.product_name" value="<%=StringUtils.nullToStr(wxcldProducts.get("product_name")) %>" >
			<input type="hidden" id="product_id" name="wxcldProduct.product_id" value="<%=StringUtils.nullToStr(wxcldProducts.get("product_id")) %>">
				<input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin();">
		</td>
		<td class="a2">
			<input type="text" id="product_xh" name="wxcldProduct.product_xh" value="<%=StringUtils.nullToStr(wxcldProducts.get("product_xh")) %>" >
		</td>
	    
	    <td class="a2">
	        <select name="wxcldProduct.product_wxlx" id="product_wxlx">
			 <option value=""></option>
			  <%
			   if(wxlx != null && wxlx.length>0){
			     for(int i=0;i<wxlx.length;i++){
			       String szd=wxlx[i];
			 %>
			      <option value="<%=szd %>" <%if(!(StringUtils.nullToStr(wxcldProducts.get("product_wxlx")).equals("")))out.print("selected"); %>><%=szd %></option>
			 <%
			      }
			   }
			  %>
			</select>
	    </td>
	    <td class="a2">
			<input type="text" id="product_gmts" name="wxcldProduct.product_gmts" value="<%=StringUtils.nullToStr(wxcldProducts.get("product_gmts"))%>"  size="5">
		</td>
		<td class="a2">
			<select name="wxcldProduct.product_clfs" id="product_clfs">
		 	 <option value=""></option>
			     <option value="维修" <%if(StringUtils.nullToStr(wxcldProducts.get("product_clfs")).equals("维修"))out.print("selected"); %>>维修</option>                   
				 <option value="报修" <%if(StringUtils.nullToStr(wxcldProducts.get("product_clfs")).equals("报修"))out.print("selected"); %>>报修</option>                    		      
			</select>
		</td>		
		<td class="a2">
			<input type="text" id="product_serial_num" name="wxcldProduct.product_serial_num" value="<%=StringUtils.nullToStr(wxcldProducts.get("product_serial_num"))%>" >
		</td>	
			
		<td class="a2">
		    <input type="text" id="product_remark" name="wxcldProduct.product_remark" value="<%=StringUtils.nullToStr(wxcldProducts.get("product_remark"))%>">
		</td>
	</tr>
	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="清除" class="css_button2" onclick="clearVl();">
			 
		</td>
	</tr>
  <tr>	   
	      <td  class="a1" width="15%" >维修费用</td>
	      <td  class="a2"  colspan="3">
	       <select name="wxcld.w_isfy" id="w_isfy" onchange="showfy(this.value)">
	         <option value="否" <%if(StringUtils.nullToStr(wxclds.get("w_isfy")).equals("否"))out.print("selected"); %>>否</option>
	         <option value="是" <%if(StringUtils.nullToStr(wxclds.get("w_isfy")).equals("是"))out.print("selected"); %>>是</option>
	       </select>
	      </td>
	</tr>
	 
	<tr id="fyid" style="display:none">
	    <td class="a1" width="15%"  >实收费用</td>
		<td class="a2" width="35%"  ><input type="text"  name="wxcld.w_skje" id="w_skje" value="<%=StringUtils.nullToStr(wxclds.get("w_skje"))%>"/></td>
		<td class="a1" width="15%"  >收款账户</td>
		<td class="a2" width="35%"  ><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(wxclds.get("w_skzh"))) %>" readonly>
		<input type="hidden" id="skzh"  name="wxcld.w_skzh" value="<%=StringUtils.nullToStr(wxclds.get("w_skzh")) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand"><font color="red">*</font>
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
			<textarea rows="4" name="wxcld.w_ms" id="w_ms" style="width:75%"> <%=StringUtils.nullToStr(wxclds.get("w_ms")) %> </textarea>
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