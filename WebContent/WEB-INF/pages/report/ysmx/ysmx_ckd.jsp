<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List ckdList = (List)VS.findValue("ckdList");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>出库单列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewCkd.html?ckd_id="+id;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
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
<body oncontextmenu="return false;" >
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>出库单列表</b></td>			
	</tr>	
</table>
<br>
<TABLE align="center" cellSpacing=0 cellPadding=0 width="99%" border=0 style="BORDER-TOP: #000000 2px solid;BORDER-LEFT:#000000 1px solid">
	<thead>
	<tr>
		<td>出库单编号</td>
		<td>创建日期</td>
		<td>客户名称</td>
		<td>经手人</td>		
		<td>仓库</td>
		<td>出库时间</td>
		<td>操作</td>
	</tr>
	</thead>
	<%

	Iterator its = ckdList.iterator();
	
	while(its.hasNext()){
		Map ckd = (Map)its.next();
		
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onDblClick="openWin('<%=StringUtils.nullToStr(ckd.get("ckd_id")) %>');">
		<td><%=StringUtils.nullToStr(ckd.get("ckd_id")) %></td>
		<td><%=StringUtils.nullToStr(ckd.get("creatdate")) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(ckd.get("client_name"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(ckd.get("fzr"))) %></td>
		<td><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(ckd.get("store_id"))) %></td>		
		<td><%=StringUtils.nullToStr(ckd.get("ck_date")) %></td>
		<td><a href="#" onclick="openWin('<%=StringUtils.nullToStr(ckd.get("ckd_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看入库单信息" border="0" style="cursor:hand"></a>	</td>
	</tr>
	
	<%
	}
	%>
</table>

<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
