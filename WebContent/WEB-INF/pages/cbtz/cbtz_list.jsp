<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>考核成本价调整</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function queryProduct(){
		if(document.getElementById("dj_id").value == ""){
			alert("编号不能为空，请填写！");
			return false;
		}
		if(document.getElementById("dj_type").value == ""){
			alert("类型不能为空，请选择！");
			return false;
		}
		
		document.myform.submit();
	}
	
	function edit(dj_type,id){
		if(dj_type == "零售单"){
			dj_type = "ls";
		}else{
			dj_type = "xs";
		}
		var destination = "editKhcb.html?dj_type=" + dj_type + "&id=" + id;
		var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'考核成本调整',fea);		
	}
	
	function clearAll(){
		document.myform.dj_id.value = "";
		document.myform.dj_type.value = "";
	}
</script>
</head>
<body>
<form name="myform" action="queryProduct.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>考核成本价调整</b></td>				
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			编号：
			<ww:textfield theme="simple" name="dj_id" id="dj_id" value="%{dj_id}"/> 
			&nbsp;&nbsp;
			类型：
			<ww:select name="dj_type" id="dj_type" theme="simple" list="#{'零售单':'零售单','销售订单':'销售订单'}"  emptyOption="true" ></ww:select>
			&nbsp;&nbsp;
			<input type="button" name="buttonCx" value=" 查询 " class="css_button" onclick="queryProduct();">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>商品编号</td>
		<td>商品名称</td>	
		<td>商品规格</td>
		<td>销售价格</td>
		<td>考核成本</td>
		<td>操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{productList}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" onDblClick="edit('<ww:property value="%{dj_type}" />','<ww:property value="%{id}" />');";>
			<td><ww:property value="%{product_id}" /></td>
			<td align="left">&nbsp;<ww:property value="%{product_name}" /></td>
			<td align="left">&nbsp;<ww:property value="%{product_xh}" /></td>
			<td align="right"><ww:property value="%{price}" />&nbsp;</td>
			<td align="right"><ww:property value="%{kh_cbj}" />&nbsp;</td>		
			<td><a href="javascript:edit('<ww:property value="%{dj_type}" />','<ww:property value="%{id}" />');">调整</a></td>
		</tr>
	</ww:iterator>
</table>
</form>
</body>
</html>