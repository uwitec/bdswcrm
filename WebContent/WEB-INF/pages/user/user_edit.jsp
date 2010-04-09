<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

SysUser user = (SysUser)VS.findValue("user");

List employList = (List)VS.findValue("employList");
%>

<html>
<head>
<title>用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.getElementById("user_name"),1,"string",1,1,20,"登录名")){	 return; }
		if(!InputValid(document.getElementById("real_name"),1,"string",1,1,20,"真实姓名")){	 return; }	
		
		if(document.getElementById("is_dls").value == "0"){
			//用户类型：业务员
			if(document.getElementById("sel_ywy").value == ""){
				alert("业务员不能为空，请选择！");
				return;
			}
		
		}else if(document.getElementById("is_dls").value == "1"){
			//用户类型：代理商
			if(document.getElementById("client_id").value == ""){
				alert("代理商不能为空，请选择！");
				return;
			}			
		}else if(document.getElementById("is_dls").value == "2"){
			//用户类型：供应商
			if(document.getElementById("product_kind").value == ""){
				alert("供应商品类别不能为空，请选择！");
				return;
			}			
		}			
	
		document.userForm.submit();
	}
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function openProductKindWin(){
		var destination = "selKind.html";
		var fea ='width=400,height=500,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择商品类别',fea);
	}
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
	
	function selType(vl){
		var obj = document.getElementById("selUserType");
		var obj2 = document.getElementById("selName");
		if(vl == "1"){
			obj.innerHTML = document.getElementById("div_dls").innerHTML;
			obj2.innerHTML = "代理商";
		}else if(vl == "2"){
			obj.innerHTML = document.getElementById("div_gys").innerHTML;
			obj2.innerHTML = "供应商品类别";
		}else{
			obj.innerHTML = document.getElementById("div_ywy").innerHTML;
			obj2.innerHTML = "业务员";
			new Form.Element.Observer("brand",1, searchBrand);
            Event.observe("brand", "keydown", move, false);
            Event.observe("brandTip","mousedown",down,true);
		}
	}
	
	function init(){
		var vl = "<%=StringUtils.nullToStr(user.getIs_dls()) %>";
		var obj = document.getElementById("selUserType");
		var obj2 = document.getElementById("selName");
		if(vl == "1"){
			obj.innerHTML = document.getElementById("div_dls").innerHTML;
			obj2.innerHTML = "代理商";
		}else if(vl == "2"){
			obj.innerHTML = document.getElementById("div_gys").innerHTML;
			obj2.innerHTML = "供应商品类别";
		}else{
			obj.innerHTML = document.getElementById("div_ywy").innerHTML;
			obj2.innerHTML = "业务员";
			new Form.Element.Observer("brand",1, searchBrand);
            Event.observe("brand", "keydown", move, false);
            Event.observe("brandTip","mousedown",down,true);
		}
	}	
	
</script>
</head>
<body onload="init();">
<form name="userForm" action="updateUser.html" method="post">
<input type="hidden" name="user.user_id" id="user_id" value="<%=StringUtils.nullToStr(user.getUser_id()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">用户信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">登录名</td>
		<td class="a2" width="35%"><input type="text" name="user.user_name" id="user_name" value="<%=StringUtils.nullToStr(user.getUser_name()) %>" readonly><font color="red">*</font></td>
		<td class="a1" width="15%">真实姓名</td>
		<td class="a2" width="35%"><input type="text" name="user.real_name" id="real_name" value="<%=StringUtils.nullToStr(user.getReal_name()) %>"><font color="red">*</font></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">电话</td>
		<td class="a2" width="35%"><input type="text" name="user.gs_phone" id="gs_phone" value="<%=StringUtils.nullToStr(user.getGs_phone()) %>" maxlength="10"></td>
		<td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><input type="text" name="user.mobile" id="mobile" value="<%=StringUtils.nullToStr(user.getMobile()) %>" maxlength="20"></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">用户类别</td>
		<td class="a2" width="35%">
			<select name="user.is_dls" id="is_dls" onchange="selType(this.value);">
				<option value="0" <%if(StringUtils.nullToStr(user.getIs_dls()).equals("0")) out.print("selected"); %>>业务员</option>
				<option value="3" <%if(StringUtils.nullToStr(user.getIs_dls()).equals("3")) out.print("selected"); %>>业务员(零售)</option>
				<option value="1" <%if(StringUtils.nullToStr(user.getIs_dls()).equals("1")) out.print("selected"); %>>代理商</option>
				<option value="2" <%if(StringUtils.nullToStr(user.getIs_dls()).equals("2")) out.print("selected"); %>>供应商</option>
			</select><font color="red">*</font>
		</td>
		<td class="a1" width="15%" id="selName">业务员</td>
		<td class="a2" width="35%" id="selUserType">
			 <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"  type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(user.getClient_name() ) %>"/> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div   id="brandTip"  style="height:12px;position:absolute;left:385px; top:110px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="user.client_name" id="sel_ywy" value="<%=user.getClient_name() %>" /> 
		<!--修改 --------------------------------------------------------------------------------------  --><font color="red">*</font>	 
		</td>	
	</tr>	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>	
</table>
</form>
<div id="div_ywy" style="display:none">
	 <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"  type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(user.getClient_name() ) %>"/> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div   id="brandTip"  style="height:12px;position:absolute;left:385px; top:110px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="user.client_name" id="sel_ywy"  value="<%=user.getClient_name() %>"/> 
		<!--修改 --------------------------------------------------------------------------------------  --><font color="red">*</font>	
</div>
<div id="div_dls" style="display:none">
	<input type="text" name="user.client_id" id="client_name" value="<%=StaticParamDo.getClientNameById(user.getClient_name()) %>" maxlength="50" readonly>
	<input type="hidden" name="user.client_name" id="client_id" value="<%=StringUtils.nullToStr(user.getClient_name()) %>">
	<img src="images/select.gif" align="absmiddle" title="点击选择代理商" border="0" onclick="openClientWin();" style="cursor:hand"><font color="red">*</font>
</div>
<%
String product_kind = StringUtils.nullToStr(user.getClient_name());
String kind_name = "";
if(!product_kind.equals("")){
	String[] arryItems = product_kind.split(",");
	if(arryItems != null && arryItems.length >0){
		for(int i=0;i<arryItems.length;i++){
			if(kind_name.equals("")){
				kind_name = StaticParamDo.getProductKindNameById(arryItems[i]);
			}else{
				kind_name += "," + StaticParamDo.getProductKindNameById(arryItems[i]);
			}
			
		}
	}
}
%>
<div id="div_gys" style="display:none">
	<input type="text" name="kind_name" id="kind_name" value="<%=kind_name %>" readonly>
	<input type="hidden" name="user.client_name" id="product_kind" value="<%=StringUtils.nullToStr(user.getClient_name()) %>">
	<img src="images/select.gif" align="absmiddle" title="点击选择类别" border="0" onclick="openProductKindWin();" style="cursor:hand"><font color="red">*</font>
</div>
</body>
</html>

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
  if(employList!=null)
  {
  for(int i=0;i<employList.size();i++)
  {   
     Map map=(Map)employList.get(i); 
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
      document.getElementById("sel_ywy").value=lists[brand];
     
    }
    else
    {
      alert("您所输入的经手人不在列表里!");
      document.getElementById("brand").value="";
      document.getElementById("sel_ywy").value="";
      document.getElementById("brand").focus();
    }
  }
  if(document.getElementById("brand").value.length==0)
  {
      document.getElementById("sel_ywy").value="";
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
