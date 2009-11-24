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
<title>报修返还单</title>
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
	   
	   if(document.getElementById("isfy").value=="是")
	   {
	      var price = document.getElementById("ssje");
		  if(price.value=="")
		  {
		     alert("应付费用不能为空,请填写！");
		     return;
		  }
	    if(price != null)
	    {
			if(!InputValid(price,0,"float",0,1,99999999,"应付费用"))
				{
					price.focus();
					return;
				}
		}
		   
		if(!InputValid(document.getElementById("skzh"),1,"string",0,1,100,"收款账户"))
		{
		   return;
		}
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
		<td class="a2" width="35%"><%=StringUtils.nullToStr(bxfhd.getId())%></td>	
		 
		<td class="a1">创建时间</td>
		 
		<td class="a2"><%=StringUtils.nullToStr(bxfhd.getCj_date()) %>
		</td>
	</tr>
	<tr>
		
		<td class="a1" width="15%">客户名称</td>
		<td class="a2">
		  <%=StaticParamDo.getClientNameById(StringUtils.nullToStr(bxfhd.getClient_name())) %>	    
		</td>	
		<td class="a1" width="15%">联系人</td>
    	<td class="a2">	  
		<%=StringUtils.nullToStr(bxfhd.getLxr()) %>
         	   
		</td>						
	</tr>
	
	<tr>
		
		<td class="a1" width="15%">电话</td>
		<td class="a2">
		    <%=StringUtils.nullToStr(bxfhd.getLxdh())%> 
              
		</td>	
		<td class="a1" width="15%">地址</td>
    	<td class="a2">	  
		 <%=StringUtils.nullToStr(bxfhd.getAddress())%> 
	   
		</td>						
	</tr>
	<tr>
		
		<td class="a1" width="15%">报修单编号</td>
		<td class="a2">
		 <%=StringUtils.nullToStr(bxfhd.getBxd_id()) %> 	    
		</td>	
		<td class="a1" width="15%">报修人</td>
    	<td class="a2">	  
		<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxfhd.getBxr())) %> 		   
		</td>						
	</tr>
	<tr>
		
		<td class="a1" width="15%">工程师</td>
		<td class="a2">
		<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(bxfhd.getGcs())) %>       
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
		<%=rq%> 
		</td>						
	</tr>
	<tr>
	    <td class="a1" width="15%">经手人</td>
		<td class="a2">
		  <%=StaticParamDo.getRealNameById(bxfhd.getJxr()) %> 
           <div  id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <input type="hidden" name="bxfhd.jxr" id="fzr" value="<%=StringUtils.nullToStr(bxfhd.getJxr()) %>"/> 		    
		</td>	
		<td class="a1" width="15%">返还状态</td>
		<td class="a2" width="35%">
			 <%=StringUtils.nullToStr(bxfhd.getState())  %>
				 	
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
			<%=StringUtils.nullToStr(bxfhdProduct.getProduct_name()) %> 
			<input type="hidden" id="product_id" name="bxfhdProduct.product_id" value="<%=StringUtils.nullToStr(bxfhdProduct.getProduct_id()) %>">
			 
		</td>
		<td class="a2">
			<%=StringUtils.nullToStr(bxfhdProduct.getProduct_xh()) %> 
		</td>
	    
	    <td class="a2">	   
	        <%out.print("好件库"); %>
	    </td>
	    
		<td class="a2">
		    <%=StringUtils.nullToStr(bxfhdProduct.getQz_serial_num()) %>
		</td>	
			
		<td class="a2">
		   <%=StringUtils.nullToStr(bxfhdProduct.getRemark()) %>
		</td>
	</tr>
</table>
 
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">	
 
		
	  
	<tr height="35">	
		<td class="a1">报修所在地</td>
		<td class="a2"  >
		
			
			 <%=StringUtils.nullToStr(bxfhdProduct.getBxaddress()) %>
			
			 
			
			
		</td>
		<td class="a1">产品状态</td>
		<td class="a2"  >
			<%=StringUtils.nullToStr(bxfhdProduct.getBxstate())%>
		</td>
	</tr>

	 
	
	<tr >
	   <td class="a1" width="15%">随机附件</td>
	   <td class="a2" >
	      <% String fj[]=StringUtils.nullToStr(bxfhdProduct.getFj()).split(",");
	         for(int i=0;i<fj.length;i++)
	         {
	            out.print(fj[i]);
	         }
	       %>
	     
	   </td>
	   
	    <td class="a1" width="15%">其他附件</td>
	    <td class="a2">
	   
	   <%=StringUtils.nullToStr(bxfhdProduct.getQtfj()) %>
	   </td>
	</tr>
	 
	<tr height="35">
		 <td class="a1" width="15%">备件</td>
		<td class="a2" colspan="3">
			<%=StringUtils.nullToStr(bxfhdProduct.getBj()) %>
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
			
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</FORM>

</BODY>
</HTML>