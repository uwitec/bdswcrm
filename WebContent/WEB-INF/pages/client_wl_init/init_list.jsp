<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("clientWlInitPage");

String client_name = (String)VS.findValue("client_name");

%>

<html>
<head>
<title>往来初始列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewClient.html?id="+id;
		var fea = 'width=650,height=500,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function add(){
		var destination = "addClientWlInit.html";
		var fea = 'width=500,height=350,left=' + (screen.availWidth-500)/2 + ',top=' + (screen.availHeight-350)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'往来初始',fea);	
	}
	
	
	function clearAll(){
		document.myform.client_name.value = "";
	}	
	
	function refreshPage(){
		document.myform.action = "listClientWlInit.html";
		document.myform.submit();
	}	
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delClientWlInit.html?seq_id=" + id;
		}
	}	
	
</script>
</head>
<body >
<form name="myform" action="listClientWlInit.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>往来初始</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			单位名称：<input type="text" name="client_name" value="<%=client_name %>">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>单位名称</td>
		<td>应收期初</td>
		<td>应付期初</td>
		<td>录入人</td>
		<td>录入时间</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		String clientName = StringUtils.nullToStr(map.get("client_name"));
		double ysqc = map.get("ysqc")==null?0:((Double)map.get("ysqc")).doubleValue();
		double yfqc = map.get("yfqc")==null?0:((Double)map.get("yfqc")).doubleValue();
		String czr = StringUtils.nullToStr(map.get("czr"));
		String cz_date = StringUtils.nullToStr(map.get("cz_date"));
	%>
	<tr class="a1">
		<td class="a1"><%=StaticParamDo.getClientNameById(clientName) %></td>
		<td class="a1"><%=JMath.round(ysqc,2) %></td>
		<td class="a1"><%=JMath.round(yfqc,2) %></td>
		<td class="a1"><%=StaticParamDo.getRealNameById(czr) %></td>
		<td class="a1"><%=cz_date.substring(0,10) %></td>
		<td class="a1"><a href="#" onclick="del('<%=StringUtils.nullToStr(map.get("seq_id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a></td>
	</tr>
	
	<%
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=results.getPageScript() %></td>
	</tr>
</table>
</form>
</body>
</html>
