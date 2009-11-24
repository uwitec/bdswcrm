<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Bxd bxd=(Bxd)VS.findValue("bxd");
BxdProduct bxdProduct=(BxdProduct)VS.findValue("bxdProduct");
String[] wxszd=(String[])VS.findValue("wxszd");
 List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
 %>
<html>
<head>
<title>报修单修改</title>
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
	      alert('报修人不能为空 请填写！');
	      return;
	   }
	   if(document.getElementById("sqr_text").value=="")
	   {
	      alert('工程师不能为空 请填写！');
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
	   if(document.getElementById("bxszd").value=="")
	   {
	     alert('报修所在地不能为空，请填写！');
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
	   	      
	   document.bxdForm.action="updateBxd.html";
	   document.bxdForm.submit();

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
<body onload="initFzrTip();initSqrTip();initClientTip()">
<FORM  name="bxdForm" action="listBxd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4"> 报修单信息</td>
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
		<td class="a2" width="35%"><input type="text" name="bxd.id" id="id" value="<%=StringUtils.nullToStr(bxd.getId()) %>" readonly></td>	
		 
		<td class="a1">报修时间</td>
		<%
		  String bxdate= StringUtils.nullToStr(bxd.getJxdate());
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
		<td class="a2"><input type="text" name="bxd.jxdate" id="jxdate" value="<%=rq %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('jxdate')); return false;"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		
		<td class="a1" width="15%">报修人</td>
		<td class="a2">
		   <input  id="brand"  type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(bxd.getJxr()) %>"/> <font color="red">*</font>
           <div  id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <input type="hidden" name="bxd.jxr" id="fzr" value="<%=StringUtils.nullToStr(bxd.getJxr()) %>"/> 
		    
		</td>	
		<td class="a1" width="15%">工程师</td>
    	<td class="a2">	  
		 <input  name="sqr_text" id="sqr_text"   onblur="setSqrValue();" value="<%=StaticParamDo.getRealNameById(bxd.getGcs()) %>"/> <font color="red">*</font>
         
        <div id="sqr_tips" style="height:12px;position:absolute;left:89px; top:82px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
         </div>
		    <input type="hidden" name="bxd.gcs" id="sqr"  value="<%=StringUtils.nullToStr(bxd.getGcs()) %>"/> 
		   
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">报修单状态</td>
		<td class="a2" width="35%" colspan="3">
			<select name="bxd.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(bxd.getState()).equals("已保存"))out.print("selected"); %> >已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(bxd.getState()).equals("已提交"))out.print("selected"); %> >已提交</option>
			</select>		
		</td>		
	</tr>
</table>
 <br>
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
		<td>目标库</td>
		<td>序列号</td>
		<td>备注</td>		 
	</tr>
	</thead>
 
	<tr>
		<td class="a2">
			<input type="text" id="product_name" name="bxdProduct.product_name" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_name()) %>" size="35" readonly="readonly">
			<input type="hidden" id="product_id" name="bxdProduct.product_id" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_id()) %>">
			<!-- <input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin();"> -->
		</td>
		<td class="a2">
			<input type="text" id="product_xh" name="bxdProduct.product_xh" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_xh()) %>" readonly="readonly">
		</td>
	    
	    <td class="a2">
	    <% String productname=StringUtils.nullToStr(bxdProduct.getProduct_name());
	      String kf="";
	      if(productname.equals(""))
	      {
	        kf="";
	      }
	      else
	      {
	        kf="在外库";
	      }
	     %>
	         <input type="text" id="kf" name="kf" value="<%=kf %>" size="7" readonly="readonly">
	    </td>
	    
		<td class="a2">
			<input type="text" id="product_serial_num" name="bxdProduct.product_serial_num" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_serial_num()) %>" >
		</td>	
			
		<td class="a2">
		    <input type="text" id="product_remark" name="bxdProduct.product_remark" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_remark()) %>">
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
		<td class="a1">报修所在地</td>
		<td class="a2"  >
			<select name="bxdProduct.bxaddress" id="bxszd">
			 <option value=""></option>
			<%
			   if(wxszd != null && wxszd.length>0){
			     for(int i=0;i<wxszd.length;i++){
			       String szd=wxszd[i];
			 %>
			      <option value="<%=szd %>" <%if(!(StringUtils.nullToStr(bxdProduct.getBxaddress()).equals("")))out.print("selected"); %>><%=szd %></option>
			 <%
			      }
			   }
			  %>
			</select>
			
		</td>
		<td class="a1">产品状态</td>
		<td class="a2"  >
			<input type="text" name="bxdProduct.bxstate" id="bxstate" value="<%=StringUtils.nullToStr(bxdProduct.getBxstate()) %>" >
		</td>
	</tr>
	
	 
	
	<tr >
	   <td class="a1" width="15%">随机附件</td>
	   <td class="a2" >
	   <%
	       String fj[]=StringUtils.nullToStr(bxdProduct.getFj()).split(",");
	    %>
	      <input type="checkbox" name="fj" value="硒鼓" <%for(int i=0;i<fj.length;i++){if(fj[i].equals("硒鼓"))out.print("checked");} %>>硒鼓&nbsp;&nbsp; 
	      <input type="checkbox" name="fj" value="墨盒" <%for(int i=0;i<fj.length;i++){if(fj[i].equals("墨盒"))out.print("checked");} %>>墨盒&nbsp;&nbsp; 
	      <input type="checkbox" name="fj" value="电源线"<%for(int i=0;i<fj.length;i++){if(fj[i].equals("电源线"))out.print("checked");} %>>电源线&nbsp;&nbsp; 
	      <input type="checkbox" name="fj" value="数据线"<%for(int i=0;i<fj.length;i++){if(fj[i].equals("数据线"))out.print("checked");} %>>数据线&nbsp;&nbsp; 
	      <input type="checkbox" name="fj" value="适配器"<%for(int i=0;i<fj.length;i++){if(fj[i].equals("适配器"))out.print("checked");} %>>适配器&nbsp;&nbsp; 
	      <input type="checkbox" name="fj" value="电池"<%for(int i=0;i<fj.length;i++){if(fj[i].equals("电池"))out.print("checked");} %>>电池&nbsp;&nbsp; 
	       <input type="checkbox" name="fj" value="包"<%for(int i=0;i<fj.length;i++){if(fj[i].equals("包"))out.print("checked");} %>>包
	   </td>
	   
	    <td class="a1" width="15%">其他附件</td>
	    <td class="a2">
	    <input type="hidden" name="bxdProduct.has_fj" value=""/>
	    <input type="text" name="bxdProduct.qtfj" id="qtfj" value="<%=StringUtils.nullToStr(bxdProduct.getQtfj()) %>" >
	   </td>
	</tr>
	 
	<tr height="35">
		 <td class="a1" width="15%">备件</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="bxdProduct.bj" id="bj" style="width:75%"><%=StringUtils.nullToStr(bxdProduct.getBj()) %> </textarea>
		</td>	
	</tr>
		
	
	<tr height="35">
		 <td class="a1" width="15%">故障及分析</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="bxdProduct.gzfx" id="gzfx" style="width:75%"><%=StringUtils.nullToStr(bxdProduct.getGzfx()) %> </textarea>
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
		<td class="a2" width="35%"><input type="text" name="jjd.client_id"   id="client_name" value="<%=StaticParamDo.getClientNameById(bxd.getClient_name()) %>" size="30" maxlength="50" onblur="setClientValue();" >
		<input type="hidden" name="bxd.client_name" id="client_id" value="<%=StringUtils.nullToStr(bxd.getClient_name()) %>" ><div id="clientsTip" style="height:12px;position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>
		</td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="bxd.lxr" id="lxr" size="30" value="<%=StringUtils.nullToStr(bxd.getLxr()) %>"></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><input type="text" name="bxd.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(bxd.getLxdh()) %>" size="30" maxlength="20"></td>	
		<td class="a1" width="15%">地址</td>
		<td class="a2" colspan="3"><input type="text" name="bxd.address" id="address" value="<%=StringUtils.nullToStr(bxd.getAddress()) %>" size="30" maxlength="100"></td>					
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
			<textarea rows="2" name="bxd.remark" id="remark" style="width:75%"><%=StringUtils.nullToStr(bxd.getRemark()) %> </textarea>
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