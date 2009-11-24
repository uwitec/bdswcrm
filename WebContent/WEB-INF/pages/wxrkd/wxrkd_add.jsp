<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Wxrkd wxrkd=(Wxrkd)VS.findValue("wxrkd");
WxrkdProduct wxrkdProduct=(WxrkdProduct)VS.findValue("wxrkdProduct");
 List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
 %>
<html>
<head>
<title>维修入库单添加</title>
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
	   if(document.getElementById("brand").value=="")
	   {
	      alert('维修人不能为空 请填写！');
	      return;
	   }
	    
	   if(document.getElementById("product_name").value=="")
	   {
	      alert('产品名称不能为空 请填写！');
	      return ;
	   }	  
	   if(document.getElementById("product_serial_num").value=="")
	   {
	      alert('产品序列号不能为空 请填写！');
	      return ;
	   }
	   
	   if(document.getElementById("client_name").value=="")
	   {
	      alert("客户名称不能为空，请填写！");
	      return ;
	   }
	   if(document.getElementById("lxr").value=="")
	   {
	     alert("联系人不能为空，请填写!");
	     return ;
	   }
	   	      
	   document.wxrkdForm.action="saveWxrkd.html";
	   document.wxrkdForm.submit();
	}	

    function clearVl(){       
          document.getElementById("product_name").value="";
		  document.getElementById("product_id").value="";
		  document.getElementById("product_xh").value="";
		  document.getElementById("kf").value="";
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
	
	//处理返回产品对象
	function setProductInfo(product){
		  var name=  document.getElementById("product_name") ;
		  var gg= document.getElementById("product_gg") ;
		  var serial_num= document.getElementById("product_serial_num") ;
		  var premark= document.getElementById("product_remark") ;
		  var gzfx=document.getElementById("gzfx");
		  var pcgc=document.getElementById("pcgc");
		  var client_name= document.getElementById("client_name");
		  var client_id=document.getElementById("client_id");
		  var lxr=document.getElementById("lxr");
		  var lxdh=document.getElementById("lxdh");
		  var email=document.getElementById("email");
		  var address=document.getElementById("address");
		  var remark=document.getElementById("remark");
		  var id=document.getElementById("product_id");
		  name.value="";
		  gg.value="";
		  serial_num.value="";
		  premark.value="";
		  gzfx.value="";
		  pcgc.value="";
		  client_name.value="";
		  client_id.value="";
		  lxr.value="";
		  lxdh.value="";
		  email.value="";
		  address.value="";
		  remark.value="";
		  id.value="";
		         
		  var productArray=product.split("$");
		  var flog= productArray[0];
		  //维修记录
		  if(flog==1){
			name.value=productArray[1];
		    // xh.value=productArray[2];
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
		      id.value=productArray[15];     
		  } else if(flog==2)  {
		  	//零售记录
		     id.value=productArray[1];	
		     name.value=productArray[2];	   
			 gg.value=productArray[3];
			 serial_num.value=productArray[4]; 
			 lxr.value=productArray[5]; 
			 lxdh.value=productArray[6];
			 email.value=productArray[7];
			 address.value=productArray[8];
			 		  
		  }else if(flog==4){//销售记录
		     id.value=productArray[1];	
		     name.value=productArray[2];	   
			 gg.value=productArray[3];
			 serial_num.value=productArray[4];
			  client_id.value=productArray[5];
			 lxr.value=productArray[6];
			 address.value=productArray[7];
			 lxdh.value=productArray[8];
			  client_name.value=productArray[9];
			    
		  }
		  //没有记录
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
<body onload="initFzrTip();initClientTip()">
<FORM  name="wxrkdForm" action="listWxrkd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4"> 维修入库单信息</td>
	</tr>
	</thead>
	 <%
	 
	if(msg != null && msg.size() > 0){
		for(int i=0;i<msg.size();i++){
	%>
	<tr>
		<td colspan="4" class="a2"><font color="red"><%=StringUtils.nullToStr(msg.get(i)) %></font></td>
	</tr>
	<%
		}
	}
	%>		
	<tr>
		<td class="a1" width="15%">编  号</td>
		<td class="a2" width="35%"><input type="text" name="wxrkd.id" id="id" value="<%=StringUtils.nullToStr(wxrkd.getId()) %>" readonly></td>	
		 
		<td class="a1">维修时间</td>
		<%
		  String bxdate= StringUtils.nullToStr(wxrkd.getWx_date());
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
		<td class="a2"><input type="text" name="wxrkd.wx_date" id="wx_date" value="<%=rq %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('wx_date')); return false;"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		
		<td class="a1" width="15%">维修人</td>
		<td class="a2">
		   <input  id="brand"  type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(wxrkd.getWxr()) %>"/><font color="red">*</font> 
           <div  id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <input type="hidden" name="wxrkd.wxr" id="fzr" value="<%=StringUtils.nullToStr(wxrkd.getWxr()) %>"/> 
		    
		</td>	
		<td class="a1" width="15%">维修单状态</td>
		<td class="a2" width="35%" colspan="3">
			<select name="wxrkd.state" id="state">
				<option value="已保存"<%if(StringUtils.nullToStr(wxrkd.getState()).equals("已保存"))out.print("selected"); %>>已保存</option>
				<option value="已提交"<%if(StringUtils.nullToStr(wxrkd.getState()).equals("已提交"))out.print("selected"); %>>已提交</option>
			</select>		
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
		<td>目标库</td>
		<td>序列号</td>
		<td>备注</td>		 
	</tr>
	</thead>
 
	<tr>
		<td class="a2">
			<input type="text" id="product_name" name="wxrkdProduct.product_name" value="<%=StringUtils.nullToStr(wxrkdProduct.getProduct_name()) %>" size="35" readonly="readonly">
			<input type="hidden" id="product_id" name="wxrkdProduct.product_id" value="<%=StringUtils.nullToStr(wxrkdProduct.getProduct_id()) %>">
			<input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin();">
		</td>
		<td class="a2">
			<input type="text" id="product_xh" name="wxrkdProduct.product_xh" value="<%=StringUtils.nullToStr(wxrkdProduct.getProduct_xh()) %>" readonly="readonly">
		</td>
	    
	    <td class="a2">
	    <% String productname=StringUtils.nullToStr(wxrkdProduct.getProduct_name());
	      String kf="";
	      if(productname.equals(""))
	      {
	        kf="";
	      }
	      else
	      {
	        kf="好件库";
	      }
	     %>
	         <input type="text" id="kf" name="kf" value="<%=kf %>" size="7" readonly="readonly">
	    </td>
	    
		<td class="a2">
			<input type="text" id="product_serial_num" name="wxrkdProduct.qz_serial_num" value="<%=StringUtils.nullToStr(wxrkdProduct.getQz_serial_num()) %>" readonly="readonly">
		</td>	
			
		<td class="a2">
		    <input type="text" id="product_remark" name="wxrkdProduct.remark" value="<%=StringUtils.nullToStr(wxrkdProduct.getRemark()) %>">
		</td>
	</tr>
</table>
 
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="清除" class="css_button2" onclick="clearVl();">
			 
		</td>
	</tr>	
	<tr height="35">
		 <td class="a1" width="15%">故障及分析</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="wxrkdProduct.gzfx" id="gzfx" style="width:75%"><%=StringUtils.nullToStr(wxrkdProduct.getGzfx()) %> </textarea>
		</td>	
	</tr>	
	<tr height="35">
		 <td class="a1" width="15%">排除过程</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="wxrkdProduct.pcgc" id="pcgc" style="width:75%"><%=StringUtils.nullToStr(wxrkdProduct.getPcgc()) %> </textarea>
		</td>	
	</tr>	 
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">客户信息</td>
	</tr>
	</thead>			
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><input type="text" name="wxrkd.client_id"   id="client_name" value="<%=StaticParamDo.getClientNameById(wxrkd.getClient_name()) %>" size="30" maxlength="50" onblur="setClientValue();" >
		<input type="hidden" name="wxrkd.client_name" id="client_id" value="<%=StringUtils.nullToStr(wxrkd.getClient_name()) %>" ><div id="clientsTip" style="height:12px;position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>
		</td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="wxrkd.lxr" id="lxr" size="30" value="<%=StringUtils.nullToStr(wxrkd.getLxr()) %>"><font color="red">*</font></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><input type="text" name="wxrkd.mobile" id="mobile" value="<%=StringUtils.nullToStr(wxrkd.getMobile()) %>" size="30" maxlength="20"></td>	
		<td class="a1" width="15%">地址</td>
		<td class="a2" colspan="3"><input type="text" name="wxrkd.address" id="address" value="<%=StringUtils.nullToStr(wxrkd.getAddress()) %>" size="30" maxlength="100"></td>					
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
			<textarea rows="2" name="wxrkd.ms" id="ms" style="width:75%"><%=StringUtils.nullToStr(wxrkd.getMs()) %> </textarea>
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