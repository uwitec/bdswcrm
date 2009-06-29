<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Lsysk lsysk = (Lsysk)VS.findValue("lsysk");
String[] fkfs = (String[])VS.findValue("fkfs");

List userList = (List)VS.findValue("userList");

List posTypeList = (List)VS.findValue("posTypeList");

%>

<html>
<head>
<title>零售预收款</title>
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
		if(!InputValid(document.getElementById("id"),1,"string",1,1,20,"编号")){	 return; }
		if(!InputValid(document.getElementById("client_name"),1,"string",1,1,50,"客户名称")){	 return; }
		if(!InputValid(document.getElementById("lxdh"),1,"string",1,1,20,"联系电话")){	 return; }
		if(document.getElementById("jsr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}		
		if(!InputValid(document.getElementById("ysje"),1,"float",1,-999999999,999999999,"预收金额")){	 return; }
		
		if(document.getElementById("skzh").value == ""){
			alert("收款账户不能为空，请选择！");
			return;
		}	
			
		if(document.getElementById("fkfs").value == "刷卡"){
			if(document.getElementById("pos_id").value == ""){
				alert("请选择刷卡POS机！");
				return;
			}
		}			
		
		document.lsyskForm.submit();
	}

	
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择客户',fea);		
	}	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
	
	function selFkfs(vl){
		if(vl == "刷卡"){
			document.getElementById("pos_id").style.display = "";
		}else{
			document.getElementById("pos_id").style.display = "none";
			document.getElementById("pos_id").value = "";
		}
	}	
	
</script>
</head>
<body>
<form name="lsyskForm" action="updateLsysk.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">零售预收款</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.id" id="id" value="<%=StringUtils.nullToStr(lsysk.getId()) %>" readonly><font color="red">*</font></td>
		<td class="a1" width="15%">预收日期</td>
		<td class="a2">
			<input type="text" name="lsysk.ys_date" id="ys_date" value="<%=StringUtils.nullToStr(lsysk.getYs_date()) %>" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('ys_date')); return false;">
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.client_name" id="client_name" value="<%=StringUtils.nullToStr(lsysk.getClient_name()) %>"><font color="red">*</font></td>	
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.lxr" id="lxr" value="<%=StringUtils.nullToStr(lsysk.getLxr()) %>"></td>
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(lsysk.getLxdh()) %>"><font color="red">*</font></td>	
		<td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.mobile" id="mobile" value="<%=StringUtils.nullToStr(lsysk.getMobile()) %>"></td>		
	</tr>
	<tr>						
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
				 <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()"  value="<%=StaticParamDo.getRealNameById(lsysk.getJsr()) %>" /> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div   id="brandTip"  style="height:12px;position:absolute;left:117px; top:140px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="lsysk.jsr" id="jsr"  value="<%=lsysk.getJsr() %>" /> 
		<!--修改 --------------------------------------------------------------------------------------  --><font color="red">*</font>		
		</td>	
		<td class="a1" width="15%">预收金额</td>
		<td class="a2" width="35%"><input type="text" name="lsysk.ysje" id="ysje" value="<%=JMath.round(lsysk.getYsje()) %>"><font color="red">*</font></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">收款账户</td>
		<td class="a2" width="35%"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(lsysk.getSkzh())) %>" readonly><font color="red">*</font>
		<input type="hidden" id="skzh"  name="lsysk.skzh" value="<%=StringUtils.nullToStr(lsysk.getSkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
		<td class="a1" widht="20%">客户付款方式</td>
		<td class="a2">
			<select name="lsysk.fkfs" id="fkfs">
			<%
			if(fkfs != null && fkfs.length > 0){
				for(int i =0;i<fkfs.length;i++){
			%>
				<option value="<%=fkfs[i] %>" <%if(fkfs[i].equals(StringUtils.nullToStr(lsysk.getFkfs()))) out.print("selected"); %>><%=fkfs[i] %></option>
			<%
				}
			}
			
			String khfkfs = StringUtils.nullToStr(lsysk.getFkfs());
			String cssStyle = "display:none";
			if(khfkfs.equals("刷卡")){
				cssStyle = "";
			}
			%>
				
			</select>	
			<select name="lsysk.pos_id" id="pos_id" style="<%=cssStyle %>">
				<option value="">选择刷卡POS机</option>
			<%
			String pos_id = StringUtils.nullToStr(lsysk.getPos_id());
			if(posTypeList != null && posTypeList.size() > 0){
				for(int i =0;i<posTypeList.size();i++){
					PosType posType = (PosType)posTypeList.get(i);
			%>
				<option value="<%=posType.getId() %>" <%if(pos_id.equals(posType.getId()+"")) out.print("selected"); %>><%=posType.getName() %></option>
			<%
				}
			}
			%>
				
			</select>	<font color="red">*</font>					
		</td>
	</tr>
	<tr>				
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%">
			<select name="lsysk.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(lsysk.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(lsysk.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>		
		</td>		
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
			<textarea rows="3" cols="50" name="lsysk.remark" id="remark" style="width:90%" maxlength="500"><%=StringUtils.nullToStr(lsysk.getRemark()) %></textarea>
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
      document.getElementById("jsr").value=lists[brand];
     
    }
    else
    {
      alert("您所输入的经手人不在列表里!");
      document.getElementById("brand").value="";
      document.getElementById("jsr").value="";
      document.getElementById("brand").focus();
    }
  }
  if(document.getElementById("brand").value.length==0)
  {
      document.getElementById("jsr").value="";
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
