<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>日程安排</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}

	function editActive(id){
		var destination = "editPlan.html?id="+id;
		var fea = 'width=650,height=400,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'',fea);	
	}	

	function delActive(id){
		if(window.confirm("确认要删除吗？")){
			location.href = "delPlan.html?id=" + id + "&cdate=" + document.getElementById("cdate").value;
		}
	}

	function descMx(){
		document.descForm.submit();		
	}			
</script>
</head>
<body>
<form name="descForm" action="listPlan.html" method="post" target="desc">
<input type="hidden" name="cdate" id="cdate" value="<ww:property value="%{cdate}"/>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2"><ww:property value="%{cdate}"/>日程安排</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  border="1" class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td width="15%">时间</td>
		<td width="20%">地点</td>
		<td width="45%">内容</td>
		<td width="10%">紧急程度</td>
		<td width="10%">操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{calendarPlanList}" status="li">
		<tr class="a1" onmousedown="trSelectChangeCss()">
			<td align="center"><ww:property value="%{start_time}" />&nbsp;至&nbsp;<ww:property value="%{end_time}" /></td>
			<td align="left"><ww:property value="%{address}" /></td>
			<td align="left"><ww:property value="%{content}" /></td>
			<td align="center">
				<ww:if test="%{grade==1}">一般</ww:if>
				<ww:else>紧急</ww:else>
			</td>
			<td align="center">
				<a href="javascript:editActive('<ww:property value="%{id}"/>');">修改</a>
				<a href="javascript:delActive('<ww:property value="%{id}"/>');">删除</a>
			</td>
		</tr>
	</ww:iterator>
</table>
</form>
</body>
</html>