<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统字典维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script>
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
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>系统字典维护</b></td>			
	</tr>	
</table>
<table width="100%" align="center"  border="1" class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td>字典编码</td>
		<td>字典名称</td>	
		<td>字典描述</td>
		<td>操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{sjzdJbxxList}" status="rowstatus">
		<tr class="a1" onmousedown="trSelectChangeCss();">
			<td><ww:property value="%{zd_id}" /></td>
			<td><ww:property value="%{zd_name}" /></td>
			<td><ww:property value="%{zdms}" /></td>			
			<td><a href='listZdxx.html?zd_id=<ww:property value="%{zd_id}" />'>维护子项</a></td>
		</tr>
	</ww:iterator>
</table>
</div>
</body>
</html>