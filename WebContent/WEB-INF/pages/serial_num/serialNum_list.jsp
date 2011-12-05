<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>序列号管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
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
			//location.href = "delSerialNum.html?serial_num=" + serial_num;
			document.myform.action = "delSerialNum.html?serial_num=" + serial_num;
			document.myform.submit();
			return;
		}
	}
	
	function clearAll(){
		document.myform.q_serial_num.value = "";
		document.myform.product_name.value = "";
		document.myform.state.value = "";
		document.myform.store_id.value = "";
	}
	
	function refreshPage(){
		document.myform.action = "listSerialNum.html";
		document.myform.submit();
	}	

	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
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
			<ww:textfield theme="simple" name="q_serial_num" size="12" id="q_serial_num" value="%{q_serial_num}"/> 
			&nbsp;&nbsp;		
			商品名称：
			<ww:textfield theme="simple" name="product_name" size="12" id="product_name" value="%{product_name}"/> 
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
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td width="16%">序列号</td>
		<td width="21%">商品名称</td>	
		<td width="19%">商品规格</td>
		<td width="8%">状态</td>
		<td width="15%">所在库房</td>
		<td width="8%">是否样机</td>
		<td width="13%">操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{pageSerialNum.results}">
		<tr class="a1" onmousedown="trSelectChangeCss()" onDblClick="view('<ww:property value="%{serial_num}" />');";>
			<td><ww:property value="%{serial_num}" /></td>
			<td align="left"><ww:property value="%{product_name}" /></td>
			<td align="left"><ww:property value="%{product_xh}" /></td>
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
</div>
</body>
</html>