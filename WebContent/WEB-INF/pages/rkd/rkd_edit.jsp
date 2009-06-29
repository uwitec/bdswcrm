<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List storeList = (List)VS.findValue("storeList");
Rkd rkd = (Rkd)VS.findValue("rkd");
List rkdProducts = (List)VS.findValue("rkdProducts");

List userList = (List)VS.findValue("userList");

int counts = 0;
if(rkdProducts != null && rkdProducts.size()>0){
	counts = rkdProducts.size();
}

%>

<html>
<head>
<title>编辑入库单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">

	var allCount = <%=counts %>;
	
	function saveInfo(){
	    

		if(document.getElementById("fzr").value == ""){
			alert("仓管员不能为空，请选择！");
			return;
		}
		if(document.getElementById("state").value == "已入库"){
			if(document.getElementById("store_id").value == ""){
				alert("仓库不能为空，请选择！");
				return;
			}
			if(document.getElementById("rk_date").value == ""){
				alert("入库时间不能为空，请选择！");
				return;
			}			
		}
		
		//判断是否存在强制输入序列号的产品没有输入序列号
		for(var i=0;i<allCount;i++){
			var qzflag = document.getElementById("qz_flag_" + i);            //标志是否强制输入
			var qzserialnum = document.getElementById("qz_serial_num_" + i); //序列号
			var pn = document.getElementById("product_name_" + i);           //产品名称
			
			if(qzflag != null){
				if(qzflag.value == "是"){
					if(qzserialnum.value == ""){
						//如果没有输入序列号提示用户输入序列号
						alert("产品" + pn.value + "强制序列号，请先输入序列号！");
						qzserialnum.focus();
						return;
					}else{
						//校验输入数量与产品数是否相同
						var serial = document.getElementById("qz_serial_num_" + i).value;
						var arrySerial = serial.split(",");
						
						var nms = document.getElementById("nums_" + i).value;
						
						if(parseInt(nms) != arrySerial.length){
							alert("产品" + pn.value + "输入序列号数量与产品数量不符，请检查！");
							qzserialnum.focus();
							return;
						}
					
					}
				}
			}
		}			
		
		document.rkdForm.btnSub.disabled = true;
		document.rkdForm.submit();
	}
	
	function openSerialWin(vl){
		var pn = document.getElementById("product_name_" + vl).value;
		var nm = document.getElementById("nums_" + vl).value;
		
		if(pn == ""){
			alert("请选择产品，再输入序列号！");
			return;
		}
		if(nm == "" || nm == "0"){
			alert("请设置产品数量，再输入序列号！");
			return;
		}
		
		var qzserialnum = document.getElementById("qz_serial_num_" + vl).value;
		
		var url = "importRkSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}	
	
	function $(id) {return document.getElementById(id);}
	function $F(name){return document.getElementsByTagName(name);}     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     
	
	
	function openWin(id){
		var destination = "selectProduct.html?openerId="+id;
		var fea ='width=800,height=500,left=100,top=50,directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}
	function opencgfzrWin()
	{
	    var destination = "selCgfzr.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'采购负责人',fea);	
	}	
	
	//退回订单
	function doTh(){
		if(window.confirm("确认要退回订单吗？")){
			document.rkdForm.action = "doRkdTh.html";
			document.rkdForm.btnTh.disabled = true;
			document.rkdForm.submit();
		}
	}		
	
</script>
</head>
<body >
<form name="rkdForm" action="updateRkd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">入库单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">入库单编号</td>
		<td class="a2" width="35%"><input type="text" name="rkd.rkd_id" id="rkd_id" value="<%=StringUtils.nullToStr(rkd.getRkd_id()) %>" readonly>
		</td>	
		<%
		String rksj = StringUtils.nullToStr(rkd.getRk_date());
		if(rksj.equals("")){
			rksj = DateComFunc.getToday();
		}
		%>
		<td class="a1">入库时间</td>
		<td class="a2"><input type="text" name="rkd.rk_date" id="rk_date" value="<%=rksj %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('rk_date')); return false;">
		</td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">仓库</td>
		<td class="a2" width="35%">
			<select name="rkd.store_id" id="store_id">
				<option value=""></option>
			<%
			String store_id = StringUtils.nullToStr(rkd.getStore_id());
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHose = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(storeHose.getId());
					String name = StringUtils.nullToStr(storeHose.getName());
			%>
				<option value="<%=id %>" <%if(store_id.equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>		
		</td>
		<td class="a1" width="15%">仓管员</td>
		<td class="a2" width="35%">
			 
			  <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(rkd.getFzr()) %>"/> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div   id="brandTip"  style="height:12px;position:absolute;left:580px; top:86px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="rkd.fzr" id="fzr" value="<%=rkd.getFzr()%>"/> 
		<!--修改 --------------------------------------------------------------------------------------  --><font color="red">*</font>	
		</td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%">
			<select name="rkd.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(rkd.getState()).equals("已保存")) out.print("selected");%>>已保存</option>
				<option value="已入库" <%if(StringUtils.nullToStr(rkd.getState()).equals("已入库")) out.print("selected");%>>已入库</option>
			</select>		
		</td>
		<%
		String cjsj = StringUtils.nullToStr(rkd.getCreatdate());
		if(cjsj.equals("")){
			cjsj = DateComFunc.getToday();
		}
		%>		
		<td class="a1">创建时间</td>
		<td class="a2"><input type="text" name="rkd.creatdate" id="creatdate" value="<%=cjsj %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('creatdate')); return false;">
		</td>			
	</tr>
	<tr>
		<td class="a1" width="15%">进货单编号</td>
		<td class="a2"><input type="text" name="rkd.jhd_id" id="jhd_id" value="<%=StringUtils.nullToStr(rkd.getJhd_id()) %>" size="30" maxlength="20"></td>	
		<td class="a1" width="15%">采购负责人</td>
		<td class="a2">
			 
			  <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="bian"  type="text"   length="20"  onblur="setvalues()" value="<%=StaticParamDo.getRealNameById(rkd.getCgfzr()) %>"/> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="opencgfzrWin();" style="cursor:hand">
          <div   id="bianTip"  style="height:12px;position:absolute;left:580px; top:140px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="rkd.cgfzr" id="cgfzr" value="<%=rkd.getCgfzr()%>"/> 
		<!--修改 --------------------------------------------------------------------------------------  --> 
		</td>			
	</tr>
	<tr>			
		<td class="a1" width="15%">供货单位</td>
		<td class="a2" colspan="3">
		<input type="text" name="rkd.client_id" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(rkd.getClient_name())) %>" size="30" maxlength="50" readonly>
		<input type="hidden" name="rkd.client_name" id="client_id" value="<%=StringUtils.nullToStr(rkd.getClient_name()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openProvider();" style="cursor:hand">
		</td>	
	</tr>			
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">产品详细信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="rktable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>产品规格</td>
		<td>进货价</td>
		<td>数量</td>
		<td>强制序列号</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(rkdProducts != null && rkdProducts.size()>0){
	for(int i=0;i<rkdProducts.size();i++){
		Map rkdProduct = (Map)rkdProducts.get(i);
		
		double price = rkdProduct.get("price")==null?0:((Double)rkdProduct.get("price")).doubleValue();
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="rkdProducts[<%=i %>].product_name" readonly value="<%=StringUtils.nullToStr(rkdProduct.get("product_name")) %>">
			<input type="hidden" id="product_id_<%=i %>" name="rkdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(rkdProduct.get("product_id")) %>">
		</td>
		<td class="a2"><input type="text" size="10" id="product_xh_<%=i %>" name="rkdProducts[<%=i %>].product_xh" value="<%=StringUtils.nullToStr(rkdProduct.get("product_xh")) %>" readonly></td>
		<td class="a2"><input type="text" size="10" id="price_<%=i %>" name="rkdProducts[<%=i %>].price" value="<%=JMath.round(price) %>" readonly></td>
		<td class="a2"><input type="text" size="5" id="nums_<%=i %>" name="rkdProducts[<%=i %>].nums"  value="<%=StringUtils.nullToStr(rkdProduct.get("nums")) %>" readonly><input type="hidden" id="xj_<%=i %>" name="rkdProducts[<%=i %>].xj" readonly></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="rkdProducts[<%=i %>].qz_serial_num"  value="<%=StringUtils.nullToStr(rkdProduct.get("qz_serial_num")) %>" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="rkdProducts[<%=i %>].qz_flag"  value="<%=StringUtils.nullToStr(rkdProduct.get("qz_flag")) %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" size="10" id="remark_<%=i %>" name="rkdProducts[<%=i %>].remark" value="<%=StringUtils.nullToStr(rkdProduct.get("remark")) %>"></td>
	</tr>
<%
	}
}
%>	
</table>	
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a1" width="15%">描述信息</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="rkd.ms" id="ms" style="width:75%"><%=StringUtils.nullToStr(rkd.getMs()) %></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnTh" value="退回订单" class="css_button3" onclick="doTh();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
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
      document.getElementById("fzr").value=lists[brand];
     
    }
    else
    {
      alert("您所输入的经手人不在列表里!");
      document.getElementById("brand").value="";
      document.getElementById("fzr").value="";
      document.getElementById("brand").focus();
    }
  }
  if(document.getElementById("brand").value.length==0)
  {
      document.getElementById("fzr").value="";
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
function setvalues()
{
    
  if(document.getElementById("bian").value!="")
  {
    var brand =document.getElementById("bian").value;
    
    brand=brand.trim();
    if(brand in listss)
    {
      document.getElementById("cgfzr").value=listss[brand];     
    }
    else
    {
      alert("您所输入的经手人不在列表里!!");
      document.getElementById("bian").value="";
      document.getElementById("cgfzr").value="";
      document.getElementById("bian").focus();
    }
  }
  if(document.getElementById("bian").value.length==0)
  {
      document.getElementById("cgfzr").value="";
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
