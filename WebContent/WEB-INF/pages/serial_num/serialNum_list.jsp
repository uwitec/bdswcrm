<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>序列号管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function edit(serial_num){
		var destination = "editSerialNum.html?serial_num=" + serial_num;
		var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'序列号管理',fea);		
	}
	
	function view(serial_num){
		var destination = "viewSerialNum.html?serial_num=" + serial_num;
		var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'序列号管理',fea);		
	}	
	
	function del(serial_num){
		if(window.confirm("确认要删除该行数据吗？")){
			location.href = "delSerialNum.html?serial_num=" + serial_num;
			return;
		}
	}
	
	function clearAll(){
		document.myform.serial_num.value = "";
		document.myform.product_name.value = "";
		document.myform.state.value = "";
		document.myform.store_id.value = "";
	}
	
	function refreshPage(){
		document.myform.action = "listSerialNum.html";
		document.myform.submit();
	}	
</script>
</head>
<body>
<form name="myform" action="listSerialNum.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>序列号管理</b></td>	
		<td class="csstitle" width="20%" align="center"><img src="images/create.gif" align="absmiddle" border="0">&nbsp;
			<a href="javascript:void(0);" onclick="edit('');" class="xxlb"> 添 加 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>
		</td>				
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			序列号：
			<ww:textfield theme="simple" name="serial_num" id="serial_num" value="%{serial_num}"/> 
			&nbsp;&nbsp;		
			产品名称：
			<ww:textfield theme="simple" name="product_name" id="product_name" value="%{product_name}"/> 
			&nbsp;&nbsp;
			状态：
			<ww:select name="state" id="state" theme="simple" list="#{'在库':'在库','已售':'已售','已退货':'已退货'}"  emptyOption="true" ></ww:select>
			&nbsp;&nbsp;
			库房：
			<ww:select name="store_id" id="store_id" theme="simple" list="%{storeList}" listKey="id" listValue="name" emptyOption="true" ></ww:select>			
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>序列号</td>
		<td>产品名称</td>	
		<td>产品规格</td>
		<td>状态</td>
		<td>所在库房</td>
		<td>是否样机</td>
		<td>操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{pageSerialNum.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" onDblClick="view('<ww:property value="%{serial_num}" />');";>
			<td><ww:property value="%{serial_num}" /></td>
			<td><ww:property value="%{product_name}" /></td>
			<td><ww:property value="%{product_xh}" /></td>
			<td><ww:property value="%{state}" /></td>
			<td><ww:property value="%{store_name}" /></td>
			<td><ww:if test="yj_flag==1">是</ww:if><ww:else>否</ww:else></td>		
			<td>
				<a href="#" onclick="edit('<ww:property value="%{serial_num}" />');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;			
				<a href="#" onclick="view('<ww:property value="%{serial_num}" />');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onclick="del('<ww:property value="%{serial_num}" />');"><img src="images/del.gif" align="absmiddle" title="删除该行数据" border="0" style="cursor:hand"></a>			
			</td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{pageSerialNum}" formname="myform"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>