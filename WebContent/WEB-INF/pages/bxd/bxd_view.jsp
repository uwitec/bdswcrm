<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String []wxszd=(String[])VS.findValue("wxszd");
List userList=(List)VS.findValue("userList");
List clientsList=(List)VS.findValue("clientsList");
Bxd bxd=(Bxd)VS.findValue("bxd");
BxdProduct bxdProduct=(BxdProduct)VS.findValue("bxdProduct");
        	

 %>
<html>
<head>
<title>报修单修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>

<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>


<script type="text/javascript">

	 
		
	function saveInfo()
	{
	   if(document.getElementById("jxr").value=="")
	   {
	      alert('接修人不能为空 请填写！');
	      return ;
	   }
	   if(document.getElementById("gcs").value=="")
	   {
	      alert('工程师不能为空 请填写！');
	      return ;
	   }
	   if(document.getElementById("product_name").value=="")
	   {
	      alert('产品名称不能为空 请填写！');
	      return ;
	   }
	   if(document.getElementById("product_xh").value=="")
	   {
	     alert('产品型号不能为空 请填写！');
	      return ;
	   }
	   if(document.getElementById("product_serial_num").value=="")
	   {
	      alert('产品序列号不能为空 请填写！');
	      return ;
	   }
	   
	   document.bxdForm.action="updateBxd.html";
	   document.bxdForm.submit();

	}	
	
	 
      	
    function addTr(){
       
       	  document.getElementById("product_name").value="";
		  document.getElementById("product_xh").value="";
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
	
	 
	function opengcsWin()
	{
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
	
	//处理返回产品对象
	function setProductInfo(product){
		 
		  var name=  document.getElementById("product_name");
		  var xh= document.getElementById("product_xh");
		  var gg= document.getElementById("product_gg");
		  var serial_num= document.getElementById("product_serial_num");
		  var premark= document.getElementById("product_remark");
		  var gzfx=document.getElementById("gzfx");
		  var pcgc=document.getElementById("pcgc");
		  var client_name= document.getElementById("client_name");
		  var client_id=document.getElementById("client_id");
		  var lxr=document.getElementById("lxr");
		  var lxdh=document.getElementById("lxdh");
		  var email=document.getElementById("email");
		  var address=document.getElementById("address");
		  var remark=document.getElementById("remark");
		         
		   var productArray=product.split("$");
		  var flog= productArray[0];
		 
		  if(flog==1)
		  {
		     name.value=productArray[1];
		     xh.value=productArray[2];
		     gg.value=productArray[3];
		     serial_num.value=productArray[4];
		     premark.value=productArray[5];
		     gzfx.value=productArray[6]; 
		     pcgc.value=productArray[7]; 
		    
		     client_id.value=productArray[8];
		     lxr.value=productArray[9];
		     lxdh.value=productArray[10]; 
		     email.value=productArray[11];
		     address.value=productArray[12];
		     remark.value=productArray[13];
		      client_name.value=productArray[14];
		       
		       
		      
		  }
		  else if(flog==2)
		  {
		      name.value=productArray[2];        
			  xh.value=	productArray[3]; 	   
			  gg.value=productArray[4]; 		 
			  serial_num.value=productArray[5]; 		   
					  
		  }
		  else if(flog==3)
		  {
		    alert("维修记录和销售记录没有该序列号!");
		  }
		  else 
		  {
		      alert("维修记录和销售记录没有该序列号!");
		  }
		 
			
		 
	}	
	
	 
	 
	 
</script>

</head>
<body >
<FORM  name="bxdForm" action="listBxd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4"> 报修单信息</td>
	</tr>
	</thead>
	
	<tr>
		<td class="a1" width="15%">编  号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(bxd.getId()) %></td>	
		 
		<td class="a1">接修时间</td>
		<td class="a2"> <%=StringUtils.nullToStr(bxd.getJxdate()) %>
		
		</td>
	</tr>
	<tr>
		
		<td class="a1" width="15%">接修人</td>
		<td class="a2">
		 
		<%=StaticParamDo.getRealNameById(bxd.getJxr()) %>
	 
		</td>	
		<td class="a1" width="15%">工程师</td>
    	<td class="a2">	  
		 <%=StaticParamDo.getRealNameById(bxd.getGcs()) %>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">报修单状态</td>
		<td class="a2"  >
			<%=StringUtils.nullToStr(bxd.getState())%>	
		</td>
		<td class="a1" width="15%">结单时间</td>
		<td class="a2"  >
			<%=StringUtils.nullToStr(bxd.getJddate())%>	
		</td>				
	</tr>
</table>
 
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">报修产品信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="lsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>产品规格</td> 
		<td>序列号</td>
		<td>备注</td>
		 
	</tr>
	</thead>
 
	<tr>
		<td class="a2">
			<%=StringUtils.nullToStr(bxdProduct.getProduct_name()) %> 
		</td>
		
	
		
		<td class="a2">
			<%=StringUtils.nullToStr(bxdProduct.getProduct_gg()) %>
		</td>
	
		<td class="a2">
			<%=StringUtils.nullToStr(bxdProduct.getProduct_serial_num()) %>
		</td>	
			
		<td class="a2">
		   <%=StringUtils.nullToStr(bxdProduct.getProduct_remark())%>
		</td>
	</tr>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	
	<tr >	
		<td class="a1" width="15%">报修所在地</td>
		<td class="a2" width="35%" >
			 <%=StringUtils.nullToStr(bxdProduct.getBxaddress())%>
		</td>
		<td class="a1" width="15%">产品状态</td>
		<td class="a2" width="35%" >
			<%=StringUtils.nullToStr(bxdProduct.getBxstate()) %>
		</td>
	</tr>
	
	 
	
	<tr >
	   <td class="a1" width="15%">随机附件</td>
	   <td class="a2" width="35%">
	   <% String fj[]= StringUtils.nullToStr(bxdProduct.getFj()).split(",");
	        
	        for(int i=0;i<fj.length;i++)
	        {
	           if(fj[i].equals("null"))
	           {
	              continue;
	           }
	           out.print(fj[i]+"&nbsp;&nbsp;&nbsp;");
	        }
	    %>
	   </td>
	   
	    <td class="a1" width="15%">其他附件</td>
	    <td class="a2" width="35%">
	  <%=StringUtils.nullToStr(bxdProduct.getQtfj()) %>
	   </td>
	</tr>
	 
	<tr height="35">
		 <td class="a1" width="15%">备件</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="bxdProduct.bj" id="bj" style="width:75%" readonly="readonly"><%=StringUtils.nullToStr(bxdProduct.getBj()) %> </textarea>
		</td>	
	</tr>
		
	
	<tr height="35">
		 <td class="a1" width="15%">故障及分析</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="bxdProduct.gzfx" id="gzfx" style="width:75%" readonly="readonly"><%=StringUtils.nullToStr(bxdProduct.getGzfx()) %>  </textarea>
		</td>	
	</tr>
		
	<tr>
		<td class="a1" width="15%">故障排除过程</td>
		<td class="a2" width="85%" colspan="3">
			<textarea  rows="2" name="bxdProduct.pcgc" id="pcgc" style="width:75%" readonly="readonly"> <%=StringUtils.nullToStr(bxdProduct.getPcgc()) %> </textarea>
		</td> 
	</tr>	
</table>

<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">客户信息</td>
	</tr>
	</thead>			
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2">
		<%=StaticParamDo.getClientNameById(bxd.getClient_name()) %>
		</td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(bxd.getLxr()) %> </td>	
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><%=StringUtils.nullToStr(bxd.getLxdh()) %></td>	
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2"><%=StringUtils.nullToStr(bxd.getEmail()) %></td>			
	</tr>
	<tr>
		<td class="a1" width="15%">地址</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(bxd.getAddress()) %></td>			
			
	</tr>	
</table>

<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">备注</td>
	</tr>
	</thead>
	
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="2" name="bxd.remark" id="remark" style="width:75%" readonly="readonly"> <%=StringUtils.nullToStr(bxd.getRemark()) %></textarea>
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
