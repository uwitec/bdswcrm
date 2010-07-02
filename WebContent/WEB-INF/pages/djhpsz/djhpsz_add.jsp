<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List djhpszProducts = (List)VS.findValue("djhpszProducts");
%>
<html>
<head>
<title>兑奖货品设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<LINK href="css/ddcolortabs.css" type=text/css rel=stylesheet>
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
    var allCount = 2;
	function saveInfo()
	{
								
			var product_id = document.getElementById("product_id_" + 0);
			if((product_id == null)||(product_id.value== ""))
			{
				alert("货品名称不能为空，请选择！");
				return;				
			}
		
		document.djhpszForm.submit();
	}

      	
    function addTr(){
        var otr = document.getElementById("djhpsztable").insertRow(-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd=document.createElement("td");
        otd.className = "a2";
        otd.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="djhpszProducts['+curId+'].product_name" style="width:100%" readonly><input type="hidden" id="product_id_'+curId+'" name="djhpszProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" size="10"  id="product_xh_'+curId+'"  name="djhpszProducts['+curId+'].product_xh" style="width:100%" readonly>';
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" size="10"  id="dwjf_'+curId+'"  name="djhpszProducts['+curId+'].dwjf" value="0">';

		otr.appendChild(otd);
        otr.appendChild(otd0); 
        otr.appendChild(otd1);   
        otr.appendChild(otd2);        
    }	
     
 	
	function openWin(id){
		var destination = "selDjhpProc.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.jhdForm.proc_id.length;i++){
			var o = document.jhdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.jhdForm.proc_id[i].value;
			}
		}
		if(k != 1){
			alert("请选择商品明细，且只能选择一条信息！");
			return;
		}
		
		document.getElementById("product_name_" + sel).value = "";
		document.getElementById("product_id_" + sel).value = "";
		document.getElementById("product_xh_" + sel).value = "";
		document.getElementById("dwjf_" + sel).value = "0";		
	}	
	
	function checkDwjf()
	{		
		for(var i=0;i<=allCount;i++)
		{			
						
			var dwjf = document.getElementById("dwjf_" + i);
			if(dwjf != null)
			{
				if(!InputValid(dwjf,0,"string",0,1,99999999,"单位积分"))
				{
					dwjf.focus();
					return;
				}
			}
		}
	}	
</script>
</head>
<body>
<form name="djhpszForm" action="saveDjhpsz.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">兑奖货品设置</td>
	</tr>
	</thead>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">商品设置的详细信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="djhpsztable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>		
	    <td width="5%">选择</td>	    
		<td width="35%">商品名称</td>
		<td width="30%">规格</td>
		<td width="10%">单位积分</td>
	</tr>
	</thead>
<%
if(djhpszProducts!=null && djhpszProducts.size()>0){
	for(int i=0;i<djhpszProducts.size();i++){
		Djhpsz djhpsz = (Djhpsz)djhpszProducts.get(i);
%>
	<tr>
	    <td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
	    
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="djhpszProducts[<%=i %>].product_name" style="width:100%" value="<%=StringUtils.nullToStr(djhpsz.getProduct_name()) %>" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="djhpszProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(djhpsz.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="djhpszProducts[<%=i %>].product_xh" style="width:100%" size="10" value="<%=StringUtils.nullToStr(djhpsz.getProduct_xh()) %>" readonly></td>
		
		<td class="a2"><input type="text" id="dwjf_<%=i %>" name="djhpszProducts[<%=i %>].dwjf" value="<%=StringUtils.nullToStr(djhpsz.getDwjf())%>" size="10" onblur="checkDwjf();"></td>
		
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
	    <td class="a2"><input type="checkbox" name="proc_id" id="proc_id" ></td>
		
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" style="width:100%" name="djhpszProducts[<%=i %>].product_name" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="djhpszProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" style="width:100%" name="djhpszProducts[<%=i %>].product_xh" size="10" readonly></td>
		
		<td class="a2"><input type="text" id="dwjf_<%=i %>" name="djhpszProducts[<%=i %>].dwjf" value="0" size="10" onblur="checkDwjf();"></td>
			
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4" width="100%">&nbsp;
			<input type="button" name="button1" value="添加商品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除商品" class="css_button3" onclick="delDesc();">
		</td>
	</tr>
</table>

<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="4">
		    <input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table><BR>
</form>
</body>
</html>