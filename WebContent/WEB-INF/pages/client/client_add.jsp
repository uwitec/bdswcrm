<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String[] wldwlx = (String[])VS.findValue("wldwlx");
List userList = (List)VS.findValue("userList");
%>
<html>
<head>
<title>客户管理</title>
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
		if(!InputValid(document.getElementById("name"),1,"string",1,1,50,"单位名称")){	 return; }
		
		if(document.getElementById("client_type").value == ""){
			alert("单位类型不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("khjl").value == ""){
			alert("客户经理不能为空，请选择！");
			return;
		}		
		
		if(!InputValid(document.getElementById("zq"),0,"int",0,1,999,"帐期")){	 return; }
		if(!InputValid(document.getElementById("xe"),0,"float",999999999,"限额")){	 return; }	
		
		
		document.clientForm.submit();
	}
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}		
</script>
</head>
<body oncontextmenu="return false;" >
<form name="clientForm" action="saveClient.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">往来单位</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">单位名称</td>
		<td class="a2" width="35%"><input type="text" name="client.name" id="name" value=""><font color="red">*</font></td>
		<td class="a1" width="15%">单位类型</td>
		<td class="a2" width="35%">
			<select name="client.client_type" id="client_type">
				<option value=""></option>
				<%
				if(wldwlx != null && wldwlx.length > 0){ 
					for(int i=0;i<wldwlx.length;i++){
				%>
				<option value="<%=wldwlx[i] %>"><%=wldwlx[i] %></option>
				<%
					}
				}
				%>
			</select>
			<font color="red">*</font>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="client.lxr" id="lxr" value="" maxlength="10"></td>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><input type="text" name="client.lxdh" id="lxdh" value="" maxlength="20"></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">移动电话</td>
		<td class="a2" width="35%"><input type="text" name="client.mobile" id="mobile" value="" maxlength="20"></td>
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><input type="text" name="client.address" id="address" value="" maxlength="50"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">客户经理</td>
		<td class="a2" width="35%">
			 
			 <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()" /> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div   id="brandTip"  style="height:12px;position:absolute;left:102px; top:141px;  width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="client.khjl" id="khjl"  /> 
		<!--修改 --------------------------------------------------------------------------------------  --><font color="red">*</font>
		</td>
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2" width="35%"><input type="text" name="client.mail" id="mail" value="" maxlength="50"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">MSN</td>
		<td class="a2" width="35%"><input type="text" name="client.msn" id="msn" value="" maxlength="20"></td>
		<td class="a1" width="15%">QQ</td>
		<td class="a2" width="35%"><input type="text" name="client.qq" id="qq" value="" maxlength="20"></td>		
	</tr>		
	<tr>
		<td class="a1" width="15%">账期</td>
		<td class="a2" width="35%"><input type="text" name="client.zq" id="zq" value="0" size="5"> 天<font color="red">*</font>	</td>
		<td class="a1" width="15%">限额</td>
		<td class="a2" width="35%"><input type="text" name="client.xe" id="xe" value="0.00">元<font color="red">*</font>	</td>
	</tr>		
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">备 注</td>
	</tr>
	</thead>
	<tr height="50">
		<td class="a1" width="20%">备注</td>
		<td class="a2" width="80%">
			<textarea rows="3" cols="50" name="client.remark" id="remark" style="width:80%" maxlength="500"></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
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
  for(int i=0;i<userList.size();i++)
  {   
     Map map=(Map)userList.get(i); 
%>
   lists["<%=map.get("real_name")%>"]="<%=map.get("user_id")%>";
<%}%>
function setValue()
{
  if(document.getElementById("brand").value!="")
  {
    var brand =document.getElementById("brand").value;
    brand=brand.trim();
    if(brand in lists)
    {
      document.getElementById("khjl").value=lists[brand];
     
    }
    else
    {
      alert("您所输入的经手人不在列表里!");
      document.getElementById("brand").value="";
      document.getElementById("khjl").value="";
      document.getElementById("brand").focus();
    }
  }
  if(document.getElementById("brand").value.length==0)
  {
      document.getElementById("khjl").value="";
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

