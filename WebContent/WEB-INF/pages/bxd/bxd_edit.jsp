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
		//处理返回产品对象
	function setProductInfo(product){
		 
		  var name=  document.getElementById("product_name");
		 // var xh= document.getElementById("product_xh");
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
		 
		  if(flog==1)
		  {
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
		  }
		  else if(flog==2)
		  {
		     id.value=productArray[1];	
		     name.value=productArray[2];	   
			 gg.value=productArray[3];
			 serial_num.value=productArray[4]; 
			 lxr.value=productArray[5]; 
			 lxdh.value=productArray[6];
			 email.value=productArray[7];
			 address.value=productArray[8];
			 		  
		  }
		  else if(flog==4)
		  {
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
		<td class="a2" width="35%"><input type="text" name="bxd.id" id="id" value="<%=StringUtils.nullToStr(bxd.getId()) %>" readonly></td>	
		 
		<td class="a1">接修时间</td>
		<td class="a2"><input type="text" name="bxd.jxdate" id="jxdate" value="<%=StringUtils.nullToStr(bxd.getJxdate()) %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('jxdate')); return false;"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		
		<td class="a1" width="15%">接修人</td>
		<td class="a2">
		 
		 <input  id="brand"  type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(bxd.getJxr()) %>"/> 
         <img id="Loadingimg" src="images/indicator.gif" style="display:none"/>
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div  id="brandTip" style=" height:12px; position:absolute;left:150px;top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;"  >
          </div>
		    <input type="hidden" name="bxd.jxr" id="jxr" value="<%=StringUtils.nullToStr(bxd.getJxr()) %>"/> 
	 
		</td>	
		<td class="a1" width="15%">工程师</td>
    	<td class="a2">	  
		 <input  id="bian"  type="text"   length="20"  onblur="setvaluess()" value="<%=StaticParamDo.getRealNameById(bxd.getGcs()) %>"/> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="opengcsWin();" style="cursor:hand">
          <div   id="bianTip"  style="height:12px;position:absolute;left:612px; top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="bxd.gcs" id="gcs" value="<%=StringUtils.nullToStr(bxd.getGcs()) %>"  /> 
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">报修单状态</td>
		<td class="a2" width="35%" colspan="3">
			<select name="bxd.state" id="state">
				<option value="报修中" <%if(StringUtils.nullToStr(bxd.getState()).equals("报修中")) out.print("selected"); %>  >报修中</option>
				<option value="报修完" <%if(StringUtils.nullToStr(bxd.getState()).equals("报修完")) out.print("selected"); %> >报修完</option>
			</select>		
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
		<!--  <td>产品型号</td>-->
		<td>产品规格</td> 
		<td>序列号</td>
		<td>备注</td>
		 
	</tr>
	</thead>
 
	<tr>
		<td class="a2">
			<input type="text" id="product_name" name="bxdProduct.product_name" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_name()) %>" size="35">
			<input type="hidden" id="product_id" name="bxdProduct.product_id" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_id()) %>">
			<input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin();">
		</td>
		
	<!--  	<td class="a2">
		    <input type="text" id="product_xh" name="bxdProduct.product_xh"   value="<%=StringUtils.nullToStr(bxdProduct.getProduct_xh()) %>">
		</td>-->	
		
		<td class="a2">
			<input type="text" id="product_gg" name="bxdProduct.product_gg" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_gg()) %>"  >
		</td>
	
		<td class="a2">
			<input type="text" id="product_serial_num" name="bxdProduct.product_serial_num" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_serial_num()) %>" >
		</td>	
			
		<td class="a2">
		    <input type="text" id="product_remark" name="bxdProduct.product_remark" value="<%=StringUtils.nullToStr(bxdProduct.getProduct_remark())%>">
		</td>
	</tr>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="清除" class="css_button2" onclick="addTr();">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入序列号：<input type="text" name="s_nums" value="" onkeypress="javascript:f_enter()">
			<font color="red">注：输入产品序列号回车，可自动提取产品信息，客户信息，维修故障记录（最近一次）及购买记录</font>
		</td>
	</tr>
	<tr height="35">	
		<td class="a1">报修所在地</td>
		<td class="a2"  >
			<select name="bxdProduct.bxaddress">
			 <option value="" ></option>
			<%
			   if(wxszd.length>0)
			   {
			     for(int i=0;i<wxszd.length;i++)
			     {
			       String szd=wxszd[i];
			 %>
			      <option value="<%=szd %>" <%if(StringUtils.nullToStr(bxdProduct.getBxaddress()).equals(wxszd[i])) out.print("selected"); %>><%=szd %></option>
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
	   <% String fj[]= StringUtils.nullToStr(bxdProduct.getFj()).split(",");
	        
	        
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
			<textarea rows="2" name="bxdProduct.gzfx" id="gzfx" style="width:75%"><%=StringUtils.nullToStr(bxdProduct.getGzfx()) %>  </textarea>
		</td>	
	</tr>
		
	<tr>
		<td class="a1" width="15%">故障排除过程</td>
		<td class="a2" width="85%" colspan="3">
			<textarea  rows="2" name="bxdProduct.pcgc" id="pcgc" style="width:75%"> <%=StringUtils.nullToStr(bxdProduct.getPcgc()) %> </textarea>
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
		<input type="text" name="clientname" onblur="setvalues()" id="client_name"   size="30" maxlength="50"  value="<%=StaticParamDo.getClientNameById(bxd.getClient_name()) %>">
		<input type="hidden" name="bxd.client_name" id="client_id" value="<%=StringUtils.nullToStr(bxd.getClient_name()) %>" >
		<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openClientWin();" style="cursor:hand"> 
		<div id="clientsTip" style="height:12px;position:absolute;left:150px; top:498px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>
		</td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="bxd.lxr" id="lxr" size="30" value="<%=StringUtils.nullToStr(bxd.getLxr()) %> "></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><input type="text" name="bxd.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(bxd.getLxdh()) %>" size="30" maxlength="20" ></td>	
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2"><input type="text" name="bxd.email" id="email" value="<%=StringUtils.nullToStr(bxd.getEmail()) %>" maxlength="50" size="30"></td>			
	</tr>
	<tr>
		<td class="a1" width="15%">地址</td>
		<td class="a2" colspan="3"><input type="text" name="bxd.address" id="address" value="<%=StringUtils.nullToStr(bxd.getAddress()) %> " size="30" maxlength="100"></td>			
			
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
			<textarea rows="2" name="bxd.remark" id="remark" style="width:75%"> <%=StringUtils.nullToStr(bxd.getRemark()) %></textarea>
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

<!-- 修改 ----------------------------------------------------------------------------------------------------------------------->
<!-- searchBrand  查询相近的经手人-->
<!-- showResponse 展示-->
<!-- move 上下事件-->
<!-- down 鼠标按下事件-->
<!-- setValue 鼠标离开事件-->
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript">
var tip = "";
function searchBrand()
{
    
	var url = 'getBrands.do';
	var params = "prefix=" + $F('brand');
	var myAjax = new Ajax.Request(
	url,
	{
		method:'post',
		parameters: params,
		onComplete: showResponse,
		asynchronous:true
	});
}
var list;
function showResponse(originalRequest)
{   
	var brandLists = originalRequest.responseText.split("%");
	list=brandLists;
     
	var brandList=brandLists[0].split("$");
	 
	if ( brandList.length > 1)
	{
		var bt = $("brandTip");
		var s="";
		var flog=0;
		for(var i = 0 ; i <  brandList.length; i++)  
		{
		   if(flog==10)
		   {
		     break;
		   }
		    s += "<div onmouseover=\"this.className='selectTip';style.cursor='default'\"  onmouseout=\"this.className=null; style.cursor='default'\">" + brandList[i] + "</div>";
		   flog++;
		}
		 bt.innerHTML=s;
		 
		if( tip != $("brand").value)
		{
			Element.show('brandTip');
		}
	}
	else
	{
		var bt = $("brandTip");
		bt.innerHTML = "";
		Element.hide('brandTip');
	}
}
function move(event)
{
	 var srcEl = Event.element(event);
	 var tipEl = $(srcEl.id + "Tip");
     var a = tipEl.childNodes;
	 if (tipEl.style.display == "" )
	 {
		if(event.keyCode == 40 )
		{            
			if (tipEl.childNodes.length >= 1)
			{
				var bList = tipEl.childNodes;
				 
				if(tipEl.lastChild.className=="selectTip")
				{
				    tipEl.firstChild.className = "selectTip";
					tipEl.lastChild.className = "null";
					return ;
				}
				var s=0;
				for (var i = 0 ; i < bList.length; i++)
				{
					if (bList[i].className == "selectTip")
					{
					    s++;
						bList[i + 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				  tipEl.firstChild.className = "selectTip";
				}
				 
			}

		}
		else if(event.keyCode == 38)
		{
		   
			if (tipEl.childNodes.length >= 1)
			{
			   
			   	if(tipEl.firstChild.className == "selectTip")
				{
					tipEl.lastChild.className = "selectTip";
					tipEl.firstChild.className = "null";
					return ;
				}
				var s=0;
				var bList = tipEl.childNodes;
				for (var i = 0 ; i < bList.length ; i ++)
				{
					if (bList[i].className == "selectTip")
					{
					   s++;
						bList[i - 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				   tipEl.lastChild.className = "selectTip";
				}
			}
		}
		else if(event.keyCode == 13)
		{
			var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++)
			{
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;		
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];				 
					 Element.hide(tipEl);
					 return ;
				}
			}
		}
		
	}
}
function  down(event)
{
      var srcEl = Event.element(event);
	  var tipEl = $("brandTip");
      var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++)
			{   
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;	
					document.getElementById("brand").value=bList[i].innerHTML;				 
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];						
					 Element.hide(tipEl);
					 return;
				}
			}
}

var lists=new Array();
<%
 if(userList!=null)
 {
  for(int i=0;i<userList.size();i++)
  {   
     Map map=(Map)userList.get(i); 
%>
   lists["<%=map.get("real_name")%>"]="<%=map.get("user_id")%>";
<%}}%>
function setValue()
{
  if(document.getElementById("brand").value!="")
  {
    var brand =document.getElementById("brand").value;
    brand=brand.trim();
    if(brand in lists)
    {
      document.getElementById("jxr").value=lists[brand];
     
    }
    else
    {
      alert("您所输入的经手人不在列表里!");
      document.getElementById("brand").value="";
      document.getElementById("jxr").value="";
      document.getElementById("brand").focus();
    }
  }
  if(document.getElementById("brand").value.length==0)
  {
      document.getElementById("jxr").value="";
  }
 
  Element.hide('brandTip')
}
String.prototype.trim = function()
{
   return this.replace(/(^\s+)|\s+$/g,"");
}
 


new Form.Element.Observer("brand",1, searchBrand);
Event.observe("brand", "keydown", move, false);
Event.observe("brandTip","mousedown",down,true);
 

</script>
<!-- 修改 ----------------------------------------------------------------------------------------------------------------------->
 <script type="text/javascript">
var tips = "";
function a()
{
    
	var url = 'getBrands.do';
	var params = "prefix=" + $F('bian');
	var myAjax = new Ajax.Request(
	url,
	{
		method:'post',
		parameters: params,
		onComplete: showResponses,
		asynchronous:true
	});
}
var lista;
function showResponses(originalRequest)
{   
	var brandLists = originalRequest.responseText.split("%");
	lista=brandLists;
     
	var brandList=brandLists[0].split("$");
	 
	if ( brandList.length > 1)
	{
		var bt = $("bianTip");
		var s="";
		var flog=0;
		for(var i = 0 ; i <  brandList.length; i++)  
		{
		   if(flog==10)
		   {
		     break;
		   }
		    s += "<div onmouseover=\"this.className='selectTip';style.cursor='default'\"  onmouseout=\"this.className=null; style.cursor='default'\">" + brandList[i] + "</div>";
		   flog++;
		}
		 bt.innerHTML=s;
		 
		if( tips != $("bian").value)
		{
			Element.show('bianTip');
		}
	}
	else
	{
		var bt = $("bianTip");
		bt.innerHTML = "";
		Element.hide('bianTip');
	}
}
function b(event)
{
	 var srcEl = Event.element(event);
	 var tipEl = $(srcEl.id + "Tip");
     var a = tipEl.childNodes;
	 if (tipEl.style.display == "" )
	 {
		if(event.keyCode == 40 )
		{            
			if (tipEl.childNodes.length >= 1)
			{
				var bList = tipEl.childNodes;
				 
				if(tipEl.lastChild.className=="selectTip")
				{
				    tipEl.firstChild.className = "selectTip";
					tipEl.lastChild.className = "null";
					return ;
				}
				var s=0;
				for (var i = 0 ; i < bList.length; i++)
				{
					if (bList[i].className == "selectTip")
					{
					    s++;
						bList[i + 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				  tipEl.firstChild.className = "selectTip";
				}
				 
			}

		}
		else if(event.keyCode == 38)
		{
		   
			if (tipEl.childNodes.length >= 1)
			{
			   
			   	if(tipEl.firstChild.className == "selectTip")
				{
					tipEl.lastChild.className = "selectTip";
					tipEl.firstChild.className = "null";
					return ;
				}
				var s=0;
				var bList = tipEl.childNodes;
				for (var i = 0 ; i < bList.length ; i ++)
				{
					if (bList[i].className == "selectTip")
					{
					   s++;
						bList[i - 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				   tipEl.lastChild.className = "selectTip";
				}
			}
		}
		else if(event.keyCode == 13)
		{
			var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++)
			{
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;		
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];				 
					 Element.hide(tipEl);
					 return ;
				}
			}
		}
		
	}
}
function  c(event)
{
      var srcEl = Event.element(event);
	  var tipEl = $("bianTip");
      var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++)
			{   
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;	
					document.getElementById("bian").value=bList[i].innerHTML;				 
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];						
					 Element.hide(tipEl);
					 return;
				}
			}
}

var listss=new Array();
<%
   if(userList!=null)
   {
  for(int i=0;i<userList.size();i++)
  {   
     Map map=(Map)userList.get(i); 
%>
   listss["<%=map.get("real_name")%>"]="<%=map.get("user_id")%>";
<%}}%>
function setvaluess()
{
    
  if(document.getElementById("bian").value!="")
  {
    var brand =document.getElementById("bian").value;
    
    brand=brand.trims();
    if(brand in listss)
    {
      document.getElementById("gcs").value=listss[brand];   
      
    }
    else
    {
      alert("您所输入的经手人不在列表里!!");
      document.getElementById("bian").value="";
      document.getElementById("gcs").value="";
      document.getElementById("bian").focus();
    }
  }
  if(document.getElementById("bian").value.length==0)
  {
      document.getElementById("gcs").value="";
  }
 
  Element.hide('bianTip')
}
String.prototype.trims = function()
{
   return this.replace(/(^\s+)|\s+$/g,"");
}
new Form.Element.Observer("bian",1, a);
Event.observe("bian", "keydown", b, false);
Event.observe("bianTip","mousedown",c,true);

</script>

<script type="text/javascript">
var tipc = "";
function ca()
{
    
	var url = 'getClients.do';
	var params = "clientsName=" + $F('client_name');
	var myAjax = new Ajax.Request(
	url,
	{
		method:'post',
		parameters: params,
		onComplete: showResponsesc,
		asynchronous:true
	});
}
var listc;
function showResponsesc(originalRequest)
{   
	var brandLists = originalRequest.responseText.split("%");
	listc=brandLists;
     
	var brandList=brandLists[0].split("$");
	 
	if ( brandList.length > 1)
	{
		var bt = $("clientsTip");
		var s="";
		var flog=0;
		for(var i = 0 ; i <  brandList.length; i++)  
		{
		   if(flog==10)
		   {
		     break;
		   }
		    s += "<div onmouseover=\"this.className='selectTip';style.cursor='default'\"  onmouseout=\"this.className=null; style.cursor='default'\">" + brandList[i] + "</div>";
		   flog++;
		}
		 bt.innerHTML=s;
		 
		if( tips != $("client_name").value)
		{
			Element.show('clientsTip');
		}
	}
	else
	{
		var bt = $("clientsTip");
		bt.innerHTML = "";
		Element.hide('clientsTip');
	}
}
function bc(event)
{
	  var srcEl = Event.element(event);
	// var tipEl = $(srcEl.id + "Tip");
	  var tipEl = $('clientsTip');
     var a = tipEl.childNodes;
	 if (tipEl.style.display == "" )
	 {
		if(event.keyCode == 40 )
		{            
			if (tipEl.childNodes.length >= 1)
			{
				var bList = tipEl.childNodes;
				 
				if(tipEl.lastChild.className=="selectTip")
				{
				    tipEl.firstChild.className = "selectTip";
					tipEl.lastChild.className = "null";
					return ;
				}
				var s=0;
				for (var i = 0 ; i < bList.length; i++)
				{
					if (bList[i].className == "selectTip")
					{
					    s++;
						bList[i + 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				  tipEl.firstChild.className = "selectTip";
				}
				 
			}

		}
		else if(event.keyCode == 38)
		{
		   
			if (tipEl.childNodes.length >= 1)
			{
			   
			   	if(tipEl.firstChild.className == "selectTip")
				{
					tipEl.lastChild.className = "selectTip";
					tipEl.firstChild.className = "null";
					return ;
				}
				var s=0;
				var bList = tipEl.childNodes;
				for (var i = 0 ; i < bList.length ; i ++)
				{
					if (bList[i].className == "selectTip")
					{
					   s++;
						bList[i - 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				   tipEl.lastChild.className = "selectTip";
				}
			}
		}
		else if(event.keyCode == 13)
		{
			var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++)
			{
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;		
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];				 
					 Element.hide(tipEl);
					 return ;
				}
			}
		}
		
	}
}
function  cc(event)
{
      var srcEl = Event.element(event);
	  var tipEl = $("clientsTip");
      var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++)
			{   
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;	
					document.getElementById("client_name").value=bList[i].innerHTML;				 
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];						
					 Element.hide(tipEl);
					 return;
				}
			}
}

var listb=new Array();
<%
  
   if(clientsList!=null)
   { 
  for(int i=0;i<clientsList.size();i++)
  {   
     Map map=(Map)clientsList.get(i); 
%>
   listb["<%=map.get("name")%>"]="<%=map.get("id")%>";
   
<%}}%>
function setvalues()
{
   
  if(document.getElementById("client_name").value!="")
  {
    var brand =document.getElementById("client_name").value;
    brand=brand.trimcs();  
    if(brand in listb)
    {
      document.getElementById("client_id").value=listb[brand];  
        
    }
    else
    {
      alert("您所输入客户名称不在列表里!");
      document.getElementById("client_name").value="";
      document.getElementById("client_id").value="";
      document.getElementById("client_name").focus();
    }
  }
  
  if(document.getElementById("client_name").value.length==0)
  {
      document.getElementById("client_id").value="";
  }
 Element.hide('clientsTip');
  
}
String.prototype.trimcs = function()
{
   return this.replace(/(^\s+)|\s+$/g,"");
}
new Form.Element.Observer("client_name",1, ca);
Event.observe("client_name", "keydown", bc, false);
Event.observe("clientsTip","mousedown",cc,true);

</script>
