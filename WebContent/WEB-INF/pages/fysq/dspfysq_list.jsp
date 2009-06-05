<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>待审批费用</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function sp(id){
		var destination = "spFysq.html?id=" + id;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
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
			<ww:textfield theme="simple" name="creatdate1" id="creatdate1" value="%{creatdate1}" readonly="true" size="10"/> 
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate1); return false;">&nbsp;至&nbsp;
			<ww:textfield theme="simple" name="creatdate2" id="creatdate2" value="%{creatdate2}" readonly="true" size="10"/> 
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate2); return false;">&nbsp;&nbsp;&nbsp;
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
		<td>业务员</td>
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
			<td><ww:property value="%{ywy_name}" /></td>
			<td><ww:property value="%{fy_type}" /></td>
			<td><ww:property value="%{strJe}" /></td>
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
</body>
</html>