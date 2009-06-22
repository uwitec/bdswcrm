<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Qtzc qtzc = (Qtzc)VS.findValue("qtzc");
String[] fkfs = (String[])VS.findValue("fkfs");

List userList = (List)VS.findValue("userList");

%>

<html>
<head>
<title>一般费用</title>
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
		if(document.getElementById("type").value == ""){
			alert("支出类型不能为空，请选择！");
			return;
		}		
		if(document.getElementById("skzh").value == ""){
			alert("支出账号不能为空，请选择！");
			return;
		}
		if(document.getElementById("fklx").value == ""){
			alert("付款方式不能为空，请选择！");
			return;
		}			
		if(document.getElementById("jsr").value == ""){
			alert("出纳不能为空，请选择！");
			return;
		}
		if(document.getElementById("remark").value == ""){
			alert("详细说明不能为空，请填写！");
			return;
		}					
		document.qtzcForm.btnSub.disabled = true;
		document.qtzcForm.submit();
	}

	
	function openAccount(){
		var destination = "selSkAccount.html";
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
<body oncontextmenu="return false;" >
<form name="qtzcForm" action="updateQtzc.html" method="post">
<input type="hidden" name="qtzc.fysq_id" id="fysq_id" value="<%=StringUtils.nullToStr(qtzc.getFysq_id()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">一般费用</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="qtzc.id" id="id" value="<%=StringUtils.nullToStr(qtzc.getId()) %>" readonly></td>
		<td class="a1" width="15%">支出日期</td>
		<td class="a2">
			<input type="text" name="qtzc.zc_date" id="zc_date" value="<%=StringUtils.nullToStr(qtzc.getZc_date()) %>" readonly>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">费用类型</td>
		<td class="a2" width="35%"><input type="text" name="qtzc.type" id="type" value="<%=StringUtils.nullToStr(qtzc.getType()) %>" readonly></td>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><input type="text" name="qtzc.zcje" id="zcje" value="<%=JMath.round(qtzc.getZcje()) %>" readonly></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">支出账号</td>
		<td class="a2" width="35%"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(qtzc.getZczh())) %>" readonly>
		<input type="hidden" id="skzh"  name="qtzc.zczh" value="<%=StringUtils.nullToStr(qtzc.getZczh()) %>"><span style="color:red">*</span>
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>	
		<td class="a1" width="15%">付款方式</td>
		<td class="a2" width="35%">
			<select name="qtzc.fklx" id="fklx">
				<option value=""></option>
			<%
			if(fkfs != null && fkfs.length>0){
				for(int i=0;i<fkfs.length;i++){
			%>
			<option value="<%=fkfs[i] %>" <%if(StringUtils.nullToStr(qtzc.getFklx()).equals(fkfs[i])) out.print("selected"); %>><%=fkfs[i] %></option>
			<%		
				}
			}
			%>
			</select><span style="color:red">*</span>
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">出纳</td>
		<td class="a2">
			 
				  <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%for(int i=0;i<userList.size();i++){Map map=(Map)userList.get(i); if(map.get("user_id").toString().equals(qtzc.getJsr())) out.print(map.get("real_name"));} %>"/> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div   id="brandTip"  style="height:12px;position:absolute;left:95px; top:141px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="qtzc.jsr" id="jsr" value="<%=qtzc.getJsr()%>"/> 
		<!--修改 --------------------------------------------------------------------------------------  --><font color="red">*</font>	
		</td>	
		<td class="a1" width="15%">业务员</td>
		<td class="a2">
			<input type="hidden" name="qtzc.ywy" id="ywy" value="<%=StringUtils.nullToStr(qtzc.getYwy()) %>" readonly>
			<input type="text" name="ywy_name" id="ywy_name" value="<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtzc.getYwy())) %>" readonly>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">相关客户</td>
		<td class="a2" width="35%">
			<input type="text" id="zcxm"  name="qtzc.zcxm" value="<%=StringUtils.nullToStr(qtzc.getZcxm()) %>" size="15" readonly maxlength="100">
		</td>	
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%">
			<select name="qtzc.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(qtzc.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(qtzc.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>		
		</td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">审批人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtzc.getSpr())) %></td>
		<td class="a1" width="15%">审批时间</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getSp_date()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">费用申请单编号</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(qtzc.getFysq_id()) %></td>
	</tr>	
	<tr height="50">
		<td class="a1">详细说明</td>
		<td class="a2" colspan="3">
			<textarea rows="2" cols="50" name="qtzc.remark" id="remark" style="width:80%" maxlength="500"><%=StringUtils.nullToStr(qtzc.getRemark()) %>
			</textarea><span style="color:red">*</span>
		</td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
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
