<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售发票管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function edit(id){
		var destination = "editXsfpFpxx.html?id=" + id;
		var fea ='width=950,height=600,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'销售发票开票',fea);		
	}
	
	function view(id){
		var destination = "viewXsfpFpxx.html?id=" + id;
		var fea ='width=950,height=600,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'序列号管理',fea);		
	}	
	
	function del(id){
		if(window.confirm("确认要删除该行数据吗？")){
			document.myform.action = "delXsfpFpxx.html?id=" + id;
			document.myform.submit();
			return;
		}
	}
	
	function doSort(order_name){
		if(myform.orderType.value=='asc'){
			myform.orderType.value='desc';
		}else{
			myform.orderType.value='asc';	
		}
		myform.orderName.value = order_name;
	    myform.submit();		
	}	
	
	function clearAll(){
		document.myform.start_date.value = "";
		document.myform.end_date.value = "";
		document.myform.state.value = "";
		document.myform.khmc.value = "";
	}
	
	function refreshPage(){
		document.myform.action = "listXsfpFpxx.html";
		document.myform.submit();
	}
	
	function descMx(id){
		document.descForm.id.value = id;
		document.descForm.submit();			
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
<form name="myform" action="listXsfpFpxx.html" method="post">
<ww:hidden name="orderName" id="orderName" theme="simple" value="%{orderName}" />
<ww:hidden name="orderType" id="orderType" theme="simple" value="%{orderType}" />
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>销售发票管理</b></td>	
		<td class="csstitle" width="20%" align="center"><img src="images/create.gif" align="absmiddle" border="0">&nbsp;
			<a href="javascript:void(0);" onclick="edit('');" class="xxlb"> 添 加 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>
		</td>				
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			客户名称：<input type="text" name="khmc" id="khmc" value="<ww:property value="%{khmc}" />" size="25" />&nbsp;&nbsp;&nbsp;
			开票日期：
			<input type="text" name="start_date" id="start_date" value="<ww:property value="%{start_date}" />" size="15" class="Wdate" onFocus="WdatePicker()"/> &nbsp;至&nbsp;
			<input type="text" name="end_date" id="end_date" value="<ww:property value="%{end_date}" />" size="15" class="Wdate" onFocus="WdatePicker()"/>&nbsp;	
			&nbsp;&nbsp;
			状态：
			<ww:select name="state" id="state" theme="simple" list="#{'已保存':'已保存','已提交':'已提交'}"  emptyOption="true" ></ww:select>
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td width="14%" onclick="doSort('id');">编号<ww:if test="orderName=='id'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td width="20%" onclick="doSort('khmc');">客户名称<ww:if test="orderName=='khmc'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>	
		<td width="10%" onclick="doSort('fplx');">发票类型<ww:if test="orderName=='fplx'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td width="8%" onclick="doSort('fpje');">金额<ww:if test="orderName=='fpje'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td width="12%" onclick="doSort('fph');">发票号<ww:if test="orderName=='fph'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td width="8%" onclick="doSort('kpr');">经手人<ww:if test="orderName=='kpr'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td width="10%" onclick="doSort('state');">状态<ww:if test="orderName=='state'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td width="8%" onclick="doSort('kprq');">开票日期<ww:if test="orderName=='kprq'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td width="10%">操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{xsfpFpxxPage.results}">
	<ww:if test="fpje_bdd >0">
		<tr class="a1" style="color: red"  onmousedown="trSelectChangeCss()" onclick="descMx('<ww:property value="%{id}" />');" onDblClick="view('<ww:property value="%{id}" />');";>
			<td><ww:property value="%{id}" /></td>
			<td align="left"><ww:property value="%{khmc}" /></td>
			<td><ww:property value="%{fplx}" /></td>
			<td align="right"><ww:text name="global.format.money"><ww:param value="fpje"/></ww:text>&nbsp;</td>
			<td><ww:property value="%{fph}" /></td>
			<td><ww:property value="%{getUserRealName(kpr)}" /></td>	
			<td><ww:property value="%{kprq}" /></td>
			<td><ww:property value="%{state}" /></td>
			<td>
				<ww:if test="state=='已保存'">
				<a href="#" onclick="edit('<ww:property value="%{id}" />');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;		
				<a href="#" onclick="view('<ww:property value="%{id}" />');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;
				<a href="#" onclick="del('<ww:property value="%{id}" />');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>				
				</ww:if>				
				<ww:else>
				<a href="#" onclick="view('<ww:property value="%{id}" />');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
				</ww:else>
			</td>
		</tr>	
	</ww:if>
	<ww:else>
		<tr class="a1"  onmousedown="trSelectChangeCss()" onclick="descMx('<ww:property value="%{id}" />');" onDblClick="view('<ww:property value="%{id}" />');";>
			<td><ww:property value="%{id}" /></td>
			<td align="left"><ww:property value="%{khmc}" /></td>
			<td><ww:property value="%{fplx}" /></td>
			<td align="right"><ww:text name="global.format.money"><ww:param value="fpje"/></ww:text>&nbsp;</td>
			<td><ww:property value="%{fph}" /></td>
			<td><ww:property value="%{getUserRealName(kpr)}" /></td>	
			<td><ww:property value="%{kprq}" /></td>
			<td><ww:property value="%{state}" /></td>
			<td>
				<ww:if test="state=='已保存'">
				<a href="#" onclick="edit('<ww:property value="%{id}" />');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;			
				<a href="#" onclick="view('<ww:property value="%{id}" />');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;
				<a href="#" onclick="del('<ww:property value="%{id}" />');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>				
				</ww:if>				
				<ww:else>
				<a href="#" onclick="view('<ww:property value="%{id}" />');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
				</ww:else>
			</td>
		</tr>	
	</ww:else>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{xsfpFpxxPage}"  formname="myform"/>
		</td>
	</tr>
</table>
</form>
说明：红色字体存在不对单开发票情况。
<form name="descForm" action="descFpmx.html" method="post" target="fpmxdesc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="fpmxdesc" name="fpmxdesc" width="100%" onload="dyniframesize('fpmxdesc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</div>
</body>
</html>