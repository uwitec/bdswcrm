<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageDjhpsz");

String product_name = StringUtils.nullToStr(VS.findValue("product_name"));
String creatdate1 = StringUtils.nullToStr(VS.findValue("creatdate1"));
String creatdate2 = StringUtils.nullToStr(VS.findValue("creatdate2"));
String product_xh = StringUtils.nullToStr(VS.findValue("product_xh"));

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>兑奖货品设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewDjhpsz.html?product_id="+id;
		var fea ='width=550,height=400,left=' + (screen.availWidth-450)/2 + ',top=' + (screen.availHeight-450)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delDjhpsz.html?product_id=" + id;
		}
	}
	
	function clearAll(){
		document.getElementById("product_name").value = "";
		document.getElementById("product_xh").value = "";
		document.myform.creatdate1.value = "";
		document.myform.creatdate2.value = "";
	}
	
	function add(){
		var destination = "addDjhpsz.html";
		var fea ='width=650,height=600,left=' + (screen.availWidth-550)/2 + ',top=' + (screen.availHeight-550)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加兑奖货品设置',fea);	
	}
	
	function edit(id){
		var destination = "editDjhpsz.html?product_id=" + id;
		var fea ='width=550,height=400,left=' + (screen.availWidth-450)/2 + ',top=' + (screen.availHeight-450)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改兑奖货品设置',fea);		
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
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}
	
	function refreshPage(){
		document.myform.action = "listDjhpsz.html";
		document.myform.submit();
	}
	
		
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listDjhpsz.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>兑奖货品设置</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2" width="100%">
			商品名称：<input type="text" name="product_name" id="product_name" value="<%=product_name %>"  size="40">								
			&nbsp;&nbsp;
			商品规格：<input type="text" name="product_xh" id="product_xh" value="<%=product_xh %>"  size="40">	
			&nbsp;&nbsp;&nbsp;&nbsp;
			日　　期：<input type="text" name="creatdate1" value="<%=creatdate1 %>" class="Wdate" onFocus="WdatePicker()">
						&nbsp;至&nbsp;
						<input type="text" name="creatdate2" value="<%=creatdate2 %>" class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;&nbsp;&nbsp;					
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%" align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('product_id');">产品编号<%if(orderName.equals("product_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('product_name');">产品名称<%if(orderName.equals("product_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('product_xh');">产品规格<%if(orderName.equals("product_xh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('dwjf');">单位积分<%if(orderName.equals("dwjf")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('cz_date');">操作日期<%if(orderName.equals("cz_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map djhpsz = (Map)it.next();
		
	%>
	<tr class="a1" title="双击查看详情"  onDblClick="openWin('<%=StringUtils.nullToStr(djhpsz.get("product_id")) %>');">
		<td><%=StringUtils.nullToStr(djhpsz.get("product_id")) %></td>
		<td align="left"><%=StringUtils.nullToStr(djhpsz.get("product_name")) %></td>
		<td><%=StringUtils.nullToStr(djhpsz.get("product_xh")) %></td>
		<td><%=StringUtils.nullToStr(djhpsz.get("dwjf")) %></td>
		<td><%=StringUtils.nullToStr(djhpsz.get("cz_date")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(djhpsz.get("czr"))) %></td>
		<td>
		<a href="#" onclick="edit('<%=StringUtils.nullToStr(djhpsz.get("product_id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改兑奖货品设置信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="openWin('<%=StringUtils.nullToStr(djhpsz.get("product_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看兑奖货品设置信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="del('<%=StringUtils.nullToStr(djhpsz.get("product_id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除该兑奖货品设置信息" border="0" style="cursor:hand"></a>		
		</td>
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
</div>
</body>
</html>
