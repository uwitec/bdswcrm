<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List userList = (List)VS.findValue("userList");
List storeList = (List)VS.findValue("storeList");

String id = (String)VS.findValue("id");

LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_id = info.getUser_id();

//是否完成初始标志
//0：未完成；1：已完成
String iscs_flag = StringUtils.nullToStr(VS.findValue("iscs_flag"));

%>

<html>
<head>
<title>采购退货单</title>
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

	var allCount = 2;
	var iscs_flag = '<%=iscs_flag %>';
	
	function saveInfo(){
		if(document.getElementById("id").value == ""){
			alert("编号不能为空！");
			return;
		}
			
		if(document.getElementById("client_id").value == ""){
			alert("供货单位不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("tkfs").value == "现金"){
			if(document.getElementById("skzh").value == ""){
				alert("账号不能为空，请选择！");
				return;
			}		
		}
		

		
		if(document.getElementById("state").value == "已出库"){
			if(document.getElementById("store_id").value == ""){
				alert("出货仓库不能为空，请选择！");
				return;
			}		
			if(iscs_flag == "1"){  //初始化完成后再做强制序列号校验
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
			}
		}			
				
		hj();
		document.cgthdForm.submit();
	}
	
	function chgState(vl){
		if(vl == "已出库"){
			document.getElementById("store_id").style.display = "";
		}else{
			document.getElementById("store_id").style.display = "none";
			document.getElementById("store_id").value = "";
		}
	}
	
	function $(id) {return document.getElementById(id);}
	function $F(name){return document.getElementsByTagName(name);}
      	
    function addTr(){
        var otr = document.getElementById("thtable").insertRow(-1);

       // var curId = ($('xsdtable').rows.length-2);
        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="cgthdProducts['+curId+'].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin('+curId+');"><input type="hidden" id="product_id_'+curId+'" name="cgthdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="cgthdProducts['+curId+'].product_xh" size="10" readonly>';
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" id="th_price_'+curId+'" name="cgthdProducts['+curId+'].th_price" size="10" value="0.00" onblur="hj();;">';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="nums_'+curId+'" name="cgthdProducts['+curId+'].nums" size="5" value="0" onblur="hj();">';
        
		var otd7 = document.createElement("td");
		otd7.className = "a2";
		otd7.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="cgthdProducts['+curId+'].qz_serial_num" size="15" readonly><input type="hidden" id="qz_flag_'+curId+'" name="cgthdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';           
		       
       
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" id="xj_'+curId+'" name="cgthdProducts['+curId+'].xj" size="10" value="0.00" readonly>';  
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="remark_'+curId+'" name="cgthdProducts['+curId+'].remark"  maxlength="50">';                       

		var otd6 = document.createElement("td");
		otd6.className = "a2";
		otd6.innerHTML = '<input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);">';
		
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3); 
        otr.appendChild(otd7); 
        otr.appendChild(otd4); 
        otr.appendChild(otd5);
        otr.appendChild(otd6);               
     }     
     
    //打开输入序列号窗口
	function openSerialWin(vl){
		var pn = document.getElementById("product_id_" + vl).value;
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
		
		var url = "importSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum + "&product_id=" + pn;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     

	
	function openWin(id){
		var destination = "selThdProc.html?openerId="+id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function openProvider(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}
	
	function selJhd(){
		var destination = "selJhd.htm";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'关联进货单',fea);		
	}	
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}		

	
	function hj(){
		var length = ($('thtable').rows.length-2);
		
		var hjz = 0;
		for(var i=0;i<=allCount;i++){			
			var price = document.getElementById("th_price_" + i);
			
			if(price != null){
				if(!InputValid(price,0,"float",0,1,99999999,"退货价格")){
					price.focus();
					return;
				}
			}	
			
			var nums = document.getElementById("nums_" + i);
			if(nums != null){
				if(!InputValid(nums,0,"int",0,1,99999999,"数量")){
					nums.focus();
					return;
				}
			}
			
			
			var xj = document.getElementById("xj_" + i);			
			
			if(xj != null){
				xj.value = parseFloat(price.value)  * parseFloat(nums.value);				
				hjz = parseFloat(hjz) + parseFloat(xj.value);
			}
		}	
		
		var tkzje = document.getElementById("tkzje");
		tkzje.value = hjz;
		
	}	
	
</script>
</head>
<body >
<form name="cgthdForm" action="saveCgthd.html" method="post">
<input type="hidden" name="cgthd.jhd_id" id="jhd_id" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购退货单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">退货单编号</td>
		<td class="a2">
		<input type="text" name="cgthd.id" id="id" value="<%=id %>" size="30" maxlength="50" readonly>
		</td>	
		<td class="a1">退货日期</td>
		<td class="a2"><input type="text" name="cgthd.th_date" id="th_date" value="<%=DateComFunc.getToday() %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('th_date')); return false;">
		</td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">供货单位</td>
		<td class="a2">
		<input type="text" name="cgthd.provider_id" id="client_name" value="" size="30" maxlength="50" readonly>
		<input type="hidden" name="cgthd.provider_name" id="client_id" value="">
		<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openProvider();" style="cursor:hand">
		</td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
            <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()" /> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div   id="brandTip"  style="height:12px;position:absolute;left:610px; top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="cgthd.jsr" id="jsr"  /> 
		<!--修改 --------------------------------------------------------------------------------------  --><font color="red">*</font>	
		</td>
	</tr>
	<tr>
		<td class="a1">退款方式</td>
		<td class="a2">
			<select name="cgthd.tkfs" id="tkfs">
				<option value="现金">现金</option>
				<option value="冲抵往来">冲抵往来</option>
			</select>			
		</td>
		<td class="a1">状态</td>
		<td class="a2">
			<select name="cgthd.state" id="state" onchange="chgState(this.value);">
				<option value="已保存">保存</option>
				<option value="已提交">提交</option>
				<option value="已出库">出库</option>
			</select>	
			
			<select name="cgthd.store_id" id="store_id" style="display:none">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse sh = (StoreHouse)it.next();
			%>
				<option value="<%=StringUtils.nullToStr(sh.getId()) %>"><%=StringUtils.nullToStr(sh.getName()) %></option>
			<%
				}
			}
			%>
			</select>					
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
<table width="100%"  align="center" id="thtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>退货价格</td>
		<td>数量</td>
		<td>强制序列号</td>
		<td>小计</td>
		<td>备注</td>
		<td></td>
	</tr>
	</thead>
<%
for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="cgthdProducts[<%=i %>].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="cgthdProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="cgthdProducts[<%=i %>].product_xh" size="10" readonly></td>
		<td class="a2"><input type="text" id="th_price_<%=i %>" name="cgthdProducts[<%=i %>].th_price" size="10" value="0.00" onblur="hj();"></td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="cgthdProducts[<%=i %>].nums" value="0" size="5" onblur="hj();"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="cgthdProducts[<%=i %>].qz_serial_num" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="cgthdProducts[<%=i %>].qz_flag"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>
		<td class="a2"><input type="text" id="xj_<%=i %>" name="cgthdProducts[<%=i %>].xj" value="0.00" size="10" readonly></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="cgthdProducts[<%=i %>].remark" maxlength="50"></td>
		<%if (i>0){ %>		
		<td class="a2"><input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);"></td>
		<%}else{ %>
		<td class="a2">&nbsp;</td>
		<%} %>
	</tr>
<%
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加一行" class="css_button2" onclick="addTr();">
			<input type="button" name="button1" value="关联进货单" class="css_button3" onclick="selJhd();">
		</td>
	</tr>
	<tr>
		<td class="a1" widht="20%">账户名称</td>
		<td class="a2"><input type="text" id="zhname"  name="zhname" value="" readonly>
		<input type="hidden" id="skzh"  name="cgthd.zhmc" value="">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
		<td class="a1">合计金额</td>
		<td class="a2"><input type="text" id="tkzje"  name="cgthd.tkzje" value="0.00" readonly></td>
	</tr>		
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">备注</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="cgthd.remark" id="remark" style="width:75%" maxlength="500"></textarea>
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
