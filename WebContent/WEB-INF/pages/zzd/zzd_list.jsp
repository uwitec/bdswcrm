<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商品组装单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>

<script type="text/javascript">
	function edit(id){
		var destination = "editZzd.html?id=" + id;
		var fea ='width=900,height=600,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'拆卸单',fea);		
	}
	
	function view(id){
		var destination = "viewZzd.html?id=" + id;
		var fea ='width=900,height=600,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'拆卸单',fea);		
	}	
	
	function del(id){
		if(window.confirm("确认要删除该行数据吗？")){
			//location.href = "delZzd.html?id=" + id;
			document.myform.action = "delZzd.html?id=" + id;
			document.myform.submit();
		}
	}
	
	function clearAll(){
		document.myform.start_date.value = "";
		document.myform.end_date.value = "";
		document.myform.state.value = "";
	}
	
	function refreshPage(){
		document.myform.action = "listZzd.html";
		document.myform.submit();
	}	

	function descMx(id){
		document.descForm.id.value = id;
		document.descForm.submit();		
	}	
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listZzd.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>商品组装单</b></td>	
		<td class="csstitle" width="20%" align="center"><img src="images/create.gif" align="absmiddle" border="0">&nbsp;
			<a href="javascript:void(0);" onclick="edit('');" class="xxlb"> 添 加 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>
		</td>				
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			创建时间：<input type="text" name="start_date" value="<ww:property value="%{start_date}"/>" size="15"  class="Wdate" onFocus="WdatePicker()">	&nbsp;至&nbsp;
			<input type="text" name="end_date" value="<ww:property value="%{end_date}"/>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;
			状态：
			<ww:select name="state" id="state" theme="simple" list="#{'已保存':'已保存','已提交':'已提交'}"  emptyOption="true" ></ww:select>
			&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>编号</td>
		<td>商品编号</td>
		<td>商品名称</td>	
		<td>商品规格</td>
		<td>单价</td>
		<td>数量</td>
		<td>金额</td>
		<td>库房</td>
		<td>经手人</td>
		<td>日期</td>
		<td>状态</td>
		<td>操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{pageZzd.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" onclick="descMx('<ww:property value="%{id}" />');" onDblClick="view('<ww:property value="%{id}" />');";>
			<td><ww:property value="%{id}" /></td>
			<td><ww:property value="%{product_id}" /></td>
			<td align="left"><ww:property value="%{product_name}" /></td>
			<td align="left"><ww:property value="%{product_xh}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{price})}" /></td>
			<td><ww:property value="%{nums}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{hjje})}" /></td>
			<td><ww:property value="%{getStoreName(store_id)}" /></td>
			<td><ww:property value="%{getUserRealName(jsr)}" /></td>
			<td><ww:property value="%{cdate}" /></td>		
			<td><ww:property value="%{state}" /></td>		
			<td>
				<ww:if test="%{state=='已保存'}">
				<a href="#" onclick="edit('<ww:property value="%{id}" />');"><img src="images/modify.gif" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</ww:if>			
				<a href="#" onclick="view('<ww:property value="%{id}" />');"><img src="images/view.gif" title="查看" border="0" style="cursor:hand"></a>
				<ww:if test="%{state=='已保存'}">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onclick="del('<ww:property value="%{id}" />');"><img src="images/del.gif" title="删除该行数据" border="0" style="cursor:hand"></a>
				</ww:if>			
			</td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{pageZzd}" formname="myform"/>
		</td>
	</tr>
</table>
</form>

<form name="descForm" action="descZzd.html" method="post" target="zzddesc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="zzddesc" name="zzddesc" width="100%" onload="dyniframesize('zzddesc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</div>
</body>
</html>