<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("lxrPage");

String name = (String)VS.findValue("clients_name");
String lxr = (String)VS.findValue("lxr");
String khjl = (String)VS.findValue("khjl");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
List userList = (List)VS.findValue("userList");
%>

<html>
<head>
<title>联系人列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	
	function openWin(id){
		  var destination = "viewLxr.html?id="+id;
		  var fea = 'width=650,height=500,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		  window.open(destination,'联系人视图',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delClient.html?id=" + id;
		}
	}
	
	function add()
	{
	    var destination = "addLxr.html";
		var fea = 'width=650,height=500,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'联系人添加',fea);	
	}
	function edit(id){
		var destination = "editLxr.html?id=" + id;
		var fea = 'width=650,height=500,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改联系人',fea);		
	}		
	
	function clearAll(){
		document.myform.clients_name.value = "";
		document.myform.lxr.value = "";
		document.myform.khjl.value = "";
		document.myform.brand.value="";
	}
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
		
	
	function doSort(order_name){
		if(myform.orderType.value=='asc'){
			myform.orderType.value='desc';
		}else{
			myform.orderType.value='asc';	
		}
		myform.orderName.value = order_name;
	    myform.submit();		
	}
	
	function refreshPage(){
		document.myform.action = "listLxr.html";
		document.myform.submit();
	}	
	function submits()
	{
	   document.myform.submit();
	}
	
	function check_all(obj,cName)
    {
    var checkboxs = document.getElementsByName(cName);
    for(var i=0;i<checkboxs.length;i++){checkboxs[i].checked = obj.checked;}
    }	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="listLxr.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>联系人</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
		    联系人：<input type="text" name="lxr" value="<%=lxr %>">&nbsp;&nbsp;&nbsp;&nbsp;
			单位名称：<input type="text" name="clients_name" value="<%=name %>">&nbsp;&nbsp;&nbsp;&nbsp;	
			客户经理：
			  <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%for(int i=0;i<userList.size();i++){ Map map=(Map)userList.get(i);if(map.get("user_id").toString().equals(khjl))out.print(map.get("real_name"));} %>"/> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div   id="brandTip"  style="height:12px;position:absolute;left:515px; top:60px; width:132px;border:1px solid menu;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="khjl" id="khjl" value="<%=khjl%>" /> 
		<!--修改 --------------------------------------------------------------------------------------  -->	
			<input type="button" name="buttonCx" value=" 查询 " onclick="submits()" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
	    <td width="1%"><input type="checkbox"  name="all" onclick="check_all(this,'lxrid')"  title="发送邮件"/></td>
		<td width="10%" onclick="doSort('id');">编号<%if(orderName.equals("id"))           out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%" onclick="doSort('name');">联系人<%if(orderName.equals("name"))    out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="29%" onclick="doSort('clients_name');">所属单位<%if(orderName.equals("clients_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%" onclick="doSort('gzdh');">座机<%if(orderName.equals("gzdh"))    out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%" onclick="doSort('yddh');">手机<%if(orderName.equals("yddh"))    out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="14%" onclick="doSort('mail')">邮箱<%if(orderName.equals("mail"))    out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%" onclick="doSort('cz')">传真<%if(orderName.equals("cz"))    out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="6%">操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map lxrs = (Map)it.next();
	%>
	    <tr class="a1"  title="双击查看详情" onDblClick="openWin('<%=StringUtils.nullToStr(lxrs.get("id")) %>');">
	    <td class="a1"><input type="checkbox" name="lxrid" value="<%=StringUtils.nullToStr(lxrs.get("id")) %>"/></td>
		<td class="a1"><%=StringUtils.nullToStr(lxrs.get("id")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(lxrs.get("name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(lxrs.get("clients_name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(lxrs.get("gzdh")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(lxrs.get("yddh")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(lxrs.get("mail")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(lxrs.get("cz")) %></td>
		<td class="a1">
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(lxrs.get("id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(lxrs.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(lxrs.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>
		</td>
	</tr>
	
	<%
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=results.getPageScript() %></td>
	</tr>
</table>
</form>
</body>
</html>

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

