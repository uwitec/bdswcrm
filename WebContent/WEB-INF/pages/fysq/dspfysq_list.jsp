<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待审批费用</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function sp(id){
		var destination = "spFysq.html?id=" + id;
		var fea ='width=750,height=400,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'待审批费用',fea);	
	}	
	
	function clearAll(){
		document.myform.creatdate1.value = "";
		document.myform.creatdate2.value = "";
	}
	
	function refreshPage(){
		document.myform.action = "listDspFysq.html";
		document.myform.submit();
	}	
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listDspFysq.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待审批费用</b></td>	
		<td class="csstitle" width="20%" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>
		</td>				
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			日期：
			<input type="text" name="creatdate1" id="creatdate1" value="<ww:property value="%{creatdate1}" />" class="Wdate" onFocus="WdatePicker()"/> &nbsp;至&nbsp;
			<input type="text" name="creatdate2" id="creatdate2" value="<ww:property value="%{creatdate2}" />" class="Wdate" onFocus="WdatePicker()"/> &nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>编号</td>
		<td>申请日期</td>	
		<td>申请人</td>
		<td>使用部门</td>
		<td>费用类型</td>
		<td>金额</td>
		<td>操作员</td>
		<td>操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{fysqPage.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" onDblClick="sp('<ww:property value="%{id}" />');";>
			<td><ww:property value="%{id}" /></td>
			<td><ww:property value="%{creatdate}" /></td>
			<td><ww:property value="%{getUserRealName(sqr)}" /></td>
			<td><ww:property value="%{getDeptName(ywy_dept)}" /></td>
			<td><ww:property value="%{getFyTypeName(fy_type)}" /></td>
			<td align="right"><ww:property value="%{strJe}" />&nbsp;</td>
			<td><ww:property value="%{czr_name}" /></td>
			<td>
				<a href="#" onclick="sp('<ww:property value="%{id}" />');">审批</a>
			</td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{fysqPage}" formname="myform"/>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>