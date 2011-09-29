<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List clientsList=(List)VS.findValue("clientsList");
List userList=(List)VS.findValue("user_list");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>货品报修汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function opengcsWin()
	{
	    var destination = "selGcs.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'工程师',fea);	
	}	
	
	function openWin(){
		var destination = "selBxProcCkd.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	function submits()
	{
	  document.reportForm.submit();
	}
     
	
</script>
</head>
<body onload="initxy();">
<form   name="reportForm" action="getHpbxHzCondition.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">货品报修汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>" class="Wdate" onFocus="WdatePicker()"></td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>" class="Wdate" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1">客户名称</td>
		<td class="a4">
		<input type="text" name="clientId" id="client_name" value=""  onblur="setvalues()"  size="30" maxlength="50">
		<input type="hidden" name="clientName" id="client_id" value="">
		<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openClientWin();" style="cursor:hand">
		<div id="clientsTip" style="position:absolute;left:100px; top:87px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>	
		<td class="a1" width="15%">联系人</td>
		<td class="a4" width="35%">
			<input type="text" name="lxr" id="lxr" value="" size="30"> 
		</td>			
	</tr>
	<tr>
		<td class="a1">商品名称</td>
		<td class="a4">
		<input type="text" name="product_name" id="product_name" value=""  size="30" >
		<img src="images/select.gif" align="absmiddle" title="选择商品" border="0" onclick="openWin()" style="cursor:hand">
	
		</td>	
		<td class="a1" width="15%">工程师</td>
    	<td class="a2">	  
		 <input  id="bian"  type="text"   length="20"  onblur="setvaluess()"  /> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="opengcsWin();" style="cursor:hand">
          <div   id="bianTip"  style="position:absolute; left:700px; top:115px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="gcs" id="gcs"  /> 
		</td>			
	</tr>
			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1"  onclick="submits()" value="提 交" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
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