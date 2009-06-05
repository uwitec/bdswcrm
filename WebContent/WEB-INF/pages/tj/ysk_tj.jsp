<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("yskTjList");

String flag = StringUtils.nullToStr(VS.findValue("flag"));
String s_date = StringUtils.nullToStr(VS.findValue("s_date"));
String e_date = StringUtils.nullToStr(VS.findValue("e_date"));

%>

<html>
<head>
<title>应收款统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openDesc(id){
		document.myform.client_id.value = id;
		document.myform.action = "listYskDesc.html";
		document.myform.submit();		
	}
	
	
	function subTj(){
		document.myform.action = "listYskTj.html";
		document.myform.submit();	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delStore.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.flag.value = "";
		document.myform.s_date.value = "";
		document.myform.e_date.value = "";
	}
		
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="listYskTj.html" method="post">
<input type="hidden" name="client_id" value="">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>统计报表</b> &gt;&gt; <a href="listYskTj.html" class="xxlb"><b>应收款统计</b></a></td>
		<td class="csstitle" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb"> 导出EXCEL </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			应收款状态：
			<select name="flag">
				<option value="全部" <%if(flag.equals("全部")) out.print("selected"); %>>全部</option>
				<option value="超期" <%if(flag.equals("超期")) out.print("selected"); %>>超期</option>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			应收日期：<input type="text" name="s_date" value="<%=s_date %>" size="10" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.s_date); return false;"> &nbsp;&nbsp;至&nbsp;&nbsp;
			<input type="text" name="e_date" value="<%=e_date %>" size="10" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.e_date); return false;">			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="buttonCx" value=" 查询 " class="css_button2" onclick="subTj();">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td >客户名称</td>
		<td >应收金额</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		double ysje = map.get("ysje")==null?0:((Double)map.get("ysje")).doubleValue();
	%>
	<tr>
		<td class="a1"><a href="#"class="xxlb" style="cursor:hand" title="点击查看明细信息" onclick="openDesc('<%=StringUtils.nullToStr(map.get("client_id")) %>');"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_id"))) %></a></td>
		<td class="a1"><%=JMath.round(ysje,2) %></td>
	</tr>
	
	<%
	}
	%>
</table>
</form>
</body>
</html>
