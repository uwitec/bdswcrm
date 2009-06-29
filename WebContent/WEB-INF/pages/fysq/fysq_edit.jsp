<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List userList = (List)VS.findValue("userList");
Fysq  fysq=(Fysq)VS.findValue("fysq");
%>
<html>
<head>
<title>费用申请单</title>
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
	//保存费用申请单
	function saveInfo(){
		if(document.getElementById("ywy_id").value == ""){
			alert("业务员不能为空，请选择");
			return;
		}
		if(document.getElementById("fy_type").value == ""){
			alert("费用类型不能为空，请选择");
			return;
		}
		if(document.getElementById("fy_type").value == ""){
			alert("费用类型不能为空，请选择");
			return;
		}	
		if(!InputValid(document.getElementById("je"),1,"float",1,1,999999999,"金额")){	 return; }	
		if(!InputValid(document.getElementById("remark"),1,"string",1,1,100,"详细说明")){	 return; }	
		
		document.frsqForm.btnSub.disabled = true;
		
		document.frsqForm.submit();
	}
	
	function openAccount(){
		var destination = "selAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
	
		function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
</script>
</head>
<body>
<form name="frsqForm" action="updateFysq.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">费用申请单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.id" id="id" value="%{fysq.id}" theme="simple" readonly="true"/><span style="color:red">*</span>
		</td>
		<td class="a1" width="15%">申请日期</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.creatdate" id="creatdate" value="%{fysq.creatdate}" theme="simple" readonly="true"/><span style="color:red">*</span>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">业务员</td>
		<td class="a2" width="35%">
		 
		
		 <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(fysq.getYwy_id()) %>"/> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div   id="brandTip"  style="height:12px;position:absolute;left:89px; top:82px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="fysq.ywy_id" id="ywy_id"  value="<%=fysq.getYwy_id() %>"/> 
		<!--修改 --------------------------------------------------------------------------------------  --><font color="red">*</font>	
		</td>
		<td class="a1" width="15%">相关客户</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.xgkh" id="xgkh" value="%{fysq.xgkh}" theme="simple" maxLength="100"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">费用类型</td>
		<td class="a2" width="35%">
			<ww:select name="fysq.fy_type" id="fy_type" theme="simple" list="%{fyzclx}"  emptyOption="true" /><span style="color:red">*</span>
		</td>
		<td class="a1" width="15%">付款方式</td>
		<td class="a2" width="35%">
			<ww:select name="fysq.fklx" id="fklx" theme="simple" list="%{fkfs}"   emptyOption="true" />
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.je" id="je" value="%{fysq.strJe2}" theme="simple"/><span style="color:red">*</span>
		</td>
		<td class="a1" width="15%">支付账户</td>
		<td class="a2" width="35%">
			<ww:textfield id="zhname"  name="zhname" value="%{fysq.zfzh_name}" theme="simple" readonly="true"/>
			<ww:hidden name="fysq.zfzh" id="fkzh" value="%{fysq.zfzh}" theme="simple"/>
			<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">状态</td>
		<td class="a2" colspan="3">
			<ww:select name="fysq.state" id="state" theme="simple" list="#{'保存':'保存','提交':'提交'}"  emptyOption="no"></ww:select>		
		</td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">详细说明</td>
		<td class="a2" colspan="3">
			<ww:textarea name="fysq.remark" id="remark"  theme="simple" cssStyle="width:70%"/><span style="color:red">*</span>
		</td>
	</tr>				
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
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
      document.getElementById("ywy_id").value=lists[brand];
     
    }
    else
    {
      alert("您所输入的经手人不在列表里!");
      document.getElementById("brand").value="";
      document.getElementById("ywy_id").value="";
      document.getElementById("brand").focus();
    }
  }
  if(document.getElementById("brand").value.length==0)
  {
      document.getElementById("ywy_id").value="";
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

