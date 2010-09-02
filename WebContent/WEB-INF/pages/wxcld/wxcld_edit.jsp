<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Map wxclds=(Map)VS.findValue("wxclds");
List wxcldProducts=(List)VS.findValue("wxcldProducts");
String[] wxlx=(String[])VS.findValue("wxlx");
int counts = 3;

if(wxcldProducts != null && wxcldProducts.size()>0){
	counts = wxcldProducts.size();
} 
 %>
<html>
<head>
<title>维修处理单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type='text/javascript' src='dwr/util.js'></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
    var allCount = <%=counts %>;
    var sObj=null;
    
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}				
      	
	function saveInfo(vl){ 

		if(vl == '1'){
			document.getElementById("w_state").value = "已保存";
		}else{
			document.getElementById("w_state").value = "已提交";
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
	  
	   //判断是否存在没有输入序列号的商品
		for(var i=0;i<allCount;i++){			
			var qzserialnum = document.getElementById("product_serial_num_" + i); //序列号
			var pn = document.getElementById("product_name_" + i);           //商品名称
			var nqzserialnum = document.getElementById("n_product_serial_num_" + i); //新序列号
			var product_clfs = document.getElementById("product_clfs_" + i); //处理方式
			var qz_flag = document.getElementById("qz_flag_" + i); //是否强制序列号
			
			if(qz_flag != null)
			{
			    
				if(qz_flag.value == "是")
				{			     
				
			        if(qzserialnum.value == "")
			        {
			         //如果没有输入序列号提示用户输入序列号
			           alert("未输入商品" + pn.value + "序列号，请先输入序列号！");
			           qzserialnum.focus();
			           return;
			         }
			 
			        if(product_clfs.value=="换件")
			        {
			          if(nqzserialnum.value == "")
			          {
			            //如果没有输入序列号提示用户输入序列号
			            alert("未输入商品" + pn.value + "新序列号，请先输入新序列号！");
			            nqzserialnum.focus();
			            return;
			          }
			        }			      
			    }  
			 }
		} 	    	   	    
	   
	   
	   if(document.getElementById("w_state").value == "已提交"){
			if(window.confirm("确认要提交维修处理单吗，提交后将无法修改！")){				
				document.myform.submit();		
			}
 	     }
	     else
	     { 
	         document.myform.submit();	
	     }
	     document.myform.btnSave.disabled = true;
		 document.myform.btnSub.disabled = true;
	}	

    function clearVl(){ 
        var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.myform.proc_id.length;i++){
			var o = document.myform.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.myform.proc_id[i].value;
			}
		}
		if(k != 1){
			alert("请选择商品明细，且只能选择一条信息！");
			return;
		}      
       	  document.getElementById("product_name_"+ sel).value="";
		  document.getElementById("product_id_"+ sel).value="";
		  document.getElementById("product_xh_"+ sel).value="";
		  document.getElementById("product_wxlx_"+ sel).value="";
		  document.getElementById("product_gmts_"+ sel).value="";
		  document.getElementById("product_clfs_"+ sel).value="";
		  document.getElementById("product_serial_num_"+ sel).value="";
		  document.getElementById("qz_flag_"+ sel).value="";
		  document.getElementById("n_product_serial_num_"+ sel).value="";
		  document.getElementById("product_remark_"+ sel).value="";      
    }	
     
	function openWin(id){
		var destination = "selWxProduct.html?openerId="+id;		
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';		
		window.open(destination,"",fea);	
	}
	 
	//发送序列号
	function sendSerialNum(obj,id){
	    var product_id=dwr.util.getValue("product_id_"+id);
	   	sObj=obj;
	   	if(obj.value!="")
	   	{
		  dwrService.SerialIsExist(obj.value,product_id,checkSerial);
		}		
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
   
	//对返回的序列号进行校验
	function checkSerial(flag){
		if(flag == "false"){
			alert("该新序列号未存在在好件库中，请检查!");
			sObj.focus();
			return;
		}
	}
	
	function f_enter(obj,id){
	    if (window.event.keyCode==13){
	        sendSerialNum(obj,id);
	        event.returnValue = false;
	    }
	}	
	
	function chgClfsTyle(vD,i){	
	   var n_product_serial_num = document.getElementById("n_product_serial_num_"+i);
		
	  if(vD == "换件"){
	    n_product_serial_num.readonly=false;
	  }else if(vD == "维修"){
		n_product_serial_num.readonly=true;	
	 }
   }	
</script>
</head>
<body onload="getfy();">
<form  name="myform" action="updateWxcld.html" method="post">
<input type="hidden" name="wxcld.w_state" id="w_state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4">维修信息</td>
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
		<td class="a2"><input type="text" name="wxcld.w_jx_date" id="w_jx_date" value="<%=rq %>"  class="Wdate" onFocus="WdatePicker()" size="25"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">维修人</td>
    	<td class="a2" width="85%" colspan="3">	  
		 <input  name="sqr_text" id="sqr_text"    value="<%=StaticParamDo.getRealNameById((String)wxclds.get("w_wxr")) %>" readonly>  
		    <input type="hidden" name="wxcld.w_wxr" id="w_wxr"  value="<%=StringUtils.nullToStr(wxclds.get("w_wxr")) %>"> 
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">解决方法</td>    	
    	<td class="a2" width="85%" colspan="3">
			<textarea rows="2" name="wxcld.w_jjff" id="w_jjff" style="width:95%"><%=StringUtils.nullToStr(wxclds.get("w_jjff")) %></textarea>
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
		<td class="a2" width="35%"><input type="text" name=""   id="" value="<%=StringUtils.nullToStr(wxclds.get("id")) %>" style="width:90%" readonly></td>	
		 	
		<td class="a1" width="15%">接待日期</td>
		<td class="a2" width="35%"><input type="text"   id="" value="<%=StringUtils.nullToStr(wxclds.get("jx_date")) %>" readonly>
		 </td>						
	</tr>
	<tr>			
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%">
		<input type="text" id="client_name" name="wxcld.w_client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(wxclds.get("client_name")))%>"  style="width:90%" readonly>
		 </td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		   <input  id="jxr"  type="text"   style="width:90%"  value="<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(wxclds.get("jxr"))) %>" readonly/>  
           		    
		</td>
	</tr>
	<tr>
	   <td class="a1" width="15%">联系人</td>
		<td class="a2"  width="35%">
		 	<input id="linkman" name="wxcld.w_linkman" type="text" style="width:90%" value="<%=StringUtils.nullToStr(wxclds.get("linkman")) %>" readonly/> 		    
		</td>
		<td class="a1" width="15%">电话</td>
		<td class="a2" width="35%">
		 	<input  id="mobile" name="wxcld.w_mobile" type="text" style="width:90%" value="<%=StringUtils.nullToStr(wxclds.get("mobile")) %>" readonly/> 
		</td>
			
	</tr>
	<tr>
	   <td class="a1" width="15%">地址</td>
		<td class="a2" width="35%">
		 	<input  id="address" name="wxcld.w_address" type="text" style="width:90%" value="<%=StringUtils.nullToStr(wxclds.get("address")) %>" readonly/> 
		 </td>
		<td class="a1" width="15%">求助方式</td>
		<td class="a2" width="35%">
		 	<input  id="qzfs" type="text" style="width:90%"  value="<%=StringUtils.nullToStr(wxclds.get("qzfs")) %>" readonly/> 
		 </td>	
	</tr>
	<tr>
	 <td class="a1" width="15%">内容描述</td>
	 <td class="a2" width="85%" colspan="3">
			<textarea rows="2"  id="ms" style="width:95%" readonly> <%=StringUtils.nullToStr(wxclds.get("ms")) %> </textarea>
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
		<td class="a2" width="35%"><input type="text" name="wxcld.w_pgd_id"   id="w_pgd_id" value="<%=StringUtils.nullToStr(wxclds.get("w_pgd_id")) %>"  style="width:90%" readonly></td>	
		<td class="a1" width="15%">派工日期</td>
		<td class="a2" width="35%"><input type="text"   id="" value="<%=StringUtils.nullToStr(wxclds.get("p_yy_date")) %>" readonly>
		</td>						
	</tr>
    <tr>	
	    <td class="a1" width="15%">故障类型</td>
		<td class="a2" width="35%">
		 	<input type="text" name=""   id="" value="<%=StringUtils.nullToStr(wxclds.get("p_gzlx"))%>"  style="width:90%" readonly>
		</td>
		<td class="a1" width="15%">客户性质</td>
		<td class="a2" width="35%">
		      <input type="text" name=""   id="" value="<%=StringUtils.nullToStr(wxclds.get("p_khxz"))%>"  style="width:90%" readonly>
		</td>			
	</tr>
	<tr>	   
		<td class="a1" width="15%">维修类型</td>
		<td class="a2" width="35%">
		   <input type="text" name=""   id="" value="<%=StringUtils.nullToStr(wxclds.get("p_wx_type"))%>"  style="width:90%" readonly>
		</td>
		<td class="a1" width="15%">维修凭证</td>
		<td class="a2" width="35%">
		   <input type="text" name=""   id="" value="<%=StringUtils.nullToStr(wxclds.get("p_wxpz"))%>"  style="width:90%" readonly>		 	 
	   </td>		
	</tr>
	<tr>
	<td class="a1" width="15%">派工人</td>
		<td class="a2" colspan="3">
		   <input  id="brand"  type="text"   size="25"  value="<%=StaticParamDo.getRealNameById((String)wxclds.get("p_pgr")) %> " readonly/>  
 		   <input type="hidden" name="wxcld.w_pgr" id="w_pgr" value="<%=StringUtils.nullToStr(wxclds.get("p_pgr")) %>"/> 
		</td>		
	</tr>	
	<tr height="35">
		 <td class="a1" width="15%">故障及分析</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="" id="p_gzfx"  style="width:90%" readonly><%=StringUtils.nullToStr(wxclds.get("p_gzfx"))%></textarea>
		</td>	
	</tr>		 
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">维修商品信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="wxcldtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="5%">选择</td>
		<td width="15%">商品名称</td>
		<td width="15%">商品规格</td> 
		<td width="20%">故障类型</td>
		<td width="5%">购买天数</td>
		<td width="5%">处理方式</td>	
		<td width="12%">序列号</td>	
		<td width="12%">新序列号</td>
		<td width="10%">备注</td>		 
	</tr>
	</thead>
 <%
 if(wxcldProducts!=null&&wxcldProducts.size()>0)
 {
      for(int i=0;i<wxcldProducts.size();i++)
      {	
		         Map wxcldProduct= (Map)wxcldProducts.get(i);
 %>
 <tr>
       <td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
       <td class="a2">
			<input type="text" id="product_name_<%=i %>" name="wxcldProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(wxcldProduct.get("product_name")) %>" style="width:100%;">			
			<input type="hidden" id="product_id_<%=i %>" name="wxcldProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(wxcldProduct.get("product_id")) %>">				
		</td>
		<td class="a2">
			<input type="text" id="product_xh_<%=i %>" name="wxcldProducts[<%=i %>].product_xh" value="<%=StringUtils.nullToStr(wxcldProduct.get("product_xh")) %>" style="width:100%;">
		</td>
	    
	    <td class="a2">
	        <select name="wxcldProducts[<%=i %>].product_wxlx" id="product_wxlx_<%=i %>" style="width:90%;">
			 <option value=""></option>
			  <%
			   if(wxlx != null && wxlx.length>0){
			     for(int j=0;j<wxlx.length;j++){
			       String szd=wxlx[j];
			 %>
			      <option value="<%=szd %>" <%if(szd.equals(wxcldProduct.get("product_wxlx"))) out.print("selected"); %>><%=szd %></option>
			 <%
			      }
			   }
			  %>
			</select>
	    </td>
	    <td class="a2">
			<input type="text" id="product_gmts_<%=i %>" name="wxcldProducts[<%=i %>].product_gmts" value="<%=StringUtils.nullToStr(wxcldProduct.get("product_gmts"))%>"  size="3">
		</td>
		<td class="a2">
			<select name="wxcldProducts[<%=i %>].product_clfs" id="product_clfs_<%=i %>" onchange="chgClfsTyle(this.value,<%=i%>);">
		 	 <option value=""></option>
			     <option value="维修" <%if(StringUtils.nullToStr(wxcldProduct.get("product_clfs")).equals("维修"))out.print("selected"); %>>维修</option>                   
				 <option value="换件" <%if(StringUtils.nullToStr(wxcldProduct.get("product_clfs")).equals("换件"))out.print("selected"); %>>换件</option>                    		      
			</select>
		</td>		
		<td class="a2">
			<input type="text" id="product_serial_num_<%=i %>" name="wxcldProducts[<%=i %>].product_serial_num" value="<%=StringUtils.nullToStr(wxcldProduct.get("product_serial_num"))%>"  style="width:100%;">
		    <input type="hidden" id="qz_flag_<%=i %>" name="wxcldProducts[<%=i %>].qz_flag" value="<%=StringUtils.nullToStr(wxcldProduct.get("qz_flag")) %>">
		</td>	
		<td class="a2">
			<input type="text" id="n_product_serial_num_<%=i %>" name="wxcldProducts[<%=i %>].n_product_serial_num" value="<%=StringUtils.nullToStr(wxcldProduct.get("n_product_serial_num"))%>" style="width:100%;" onblur="sendSerialNum(this,<%=i%>);" onkeypress="javascript:f_enter(this,<%=i%>);" onkeydown="javascript:f_enter(this,<%=i%>);">
		</td>	
		<td class="a2">
		    <input type="text" id="product_remark_<%=i %>" name="wxcldProducts[<%=i %>].product_remark" value="<%=StringUtils.nullToStr(wxcldProduct.get("product_remark"))%>" style="width:100%;">
		</td>
	</tr>
	<%
}
 }
	else{
	for(int i=0;i<3;i++){ 
		%>
<tr>
       <td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
       <td class="a2">
			<input type="text" id="product_name_<%=i %>" name="wxcldProducts[<%=i %>].product_name"  style="width:100%;">
			<input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="wxcldProducts[<%=i %>].product_id" >				
		</td>
		<td class="a2">
			<input type="text" id="product_xh_<%=i %>" name="wxcldProducts[<%=i %>].product_xh"  style="width:100%;">
		</td>
	    
	    <td class="a2">
	        <select name="wxcldProducts[<%=i %>].product_wxlx" id="product_wxlx_<%=i %>" style="width:90%;">
			 <option value=""></option>
			  <%
			   if(wxlx != null && wxlx.length>0){
			     for(int j=0;j<wxlx.length;j++){
			       String szd=wxlx[j];
			 %>
			      <option value="<%=szd %>" ><%=szd %></option>
			 <%
			      }
			   }
			  %>
			</select>
	    </td>
	    <td class="a2">
			<input type="text" id="product_gmts_<%=i %>" name="wxcldProducts[<%=i %>].product_gmts"   size="5">
		</td>
		<td class="a2">
			<select name="wxcldProducts[<%=i %>].product_clfs" id="product_clfs_<%=i %>" onchange="chgClfsTyle(this.value,<%=i%>);" >
		 	 <option value=""></option>
			     <option value="维修">维修</option>                   
				 <option value="换件">换件</option>                    		      
			</select>
		</td>		
		<td class="a2">
			<input type="text" id="product_serial_num_<%=i %>" name="wxcldProducts[<%=i %>].product_serial_num"  style="width:100%;">
		</td>	
		<td class="a2">
			<input type="text" id="n_product_serial_num_<%=i %>" name="wxcldProducts[<%=i %>].n_product_serial_num"  style="width:100%;" onkeypress="javascript:f_enter(this,<%=i%>);" onkeydown="javascript:f_enter(this,<%=i%>);" onblur="sendSerialNum(this,<%=i%>);">
		    <input type="hidden" id="qz_flag_<%=i %>" name="wxcldProducts[<%=i %>].qz_flag">
		</td>	
		<td class="a2">
		    <input type="text" id="product_remark_<%=i %>" name="wxcldProducts[<%=i %>].product_remark"  style="width:100%;">
		</td>
	</tr>

<%
}
}
%>
</table>
<%
 if(wxcldProducts!=null&&wxcldProducts.size()>0)
 {} 
 else{
 %>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
			<input type="button" name="button2" value="清除商品" class="css_button3" onclick="clearVl();">
		</td>
	</tr>
</table>
<%} %>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">	
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

	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="4" name="wxcld.w_ms" id="w_ms" style="width:95%"> <%=StringUtils.nullToStr(wxclds.get("w_ms")) %> </textarea>
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">		    
			<input type="button" name="btnSave" value="草稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="btnSub" value="提交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>
<BR>
<font color="red">注：“草稿”指维修处理单暂存，可修改；“提交”后维修处理单不可修改。</font>
<BR><BR>
</FORM>
</BODY>
</HTML>