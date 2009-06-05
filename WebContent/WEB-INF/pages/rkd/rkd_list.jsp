<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("rkdPage");
List storeList = (List)VS.findValue("storeList");

String rkd_id = (String)VS.findValue("rkd_id");
String creatdate = (String)VS.findValue("creatdate");
String creatdate2 = (String)VS.findValue("creatdate2");
String state = (String)VS.findValue("state");
String store_id = (String)VS.findValue("store_id");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<html>
<head>
<title>入库单列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewRkd.html?rkd_id="+id;
		var fea ='width=900,height=700,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delRkd.html?rkd_id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.rkd_id.value = "";
		document.myform.creatdate.value = "";
		document.myform.creatdate2.value = "";
		document.myform.state.value = "";
		document.myform.store_id.value = "";
	}
	
	function add(){
		var destination = "addRkd.html";
		var fea ='width=900,height=700,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加进货单',fea);	
	}
	
	function edit(id){
		var destination = "editRkd.html?rkd_id=" + id;
		var fea ='width=900,height=700,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改进货单',fea);		
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
	
	function descMx(id){
		document.descForm.rkd_id.value = id;
		document.descForm.submit();		
	}
	
	function refreshPage(){
		document.myform.action = "listRkd.html";
		document.myform.submit();
	}			
</script>
</head>
<body>
<form name="myform" action="listRkd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>入库单</b></td>
		<td class="csstitle" width="25%">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#"  onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			入库单编号：<input type="text" name="rkd_id" value="<%=rkd_id %>" size="10">&nbsp;&nbsp;
			创建时间：<input type="text" name="creatdate" value="<%=creatdate %>" size="8" readonly>	
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate); return false;">&nbsp;至&nbsp;
			<input type="text" name="creatdate2" value="<%=creatdate2 %>" size="8" readonly>	
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate2); return false;">
			&nbsp;&nbsp;
			入库单状态：<select name="state">
				<option value=""></option>
				<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已入库" <%if(state.equals("已入库")) out.print("selected"); %>>已入库</option>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;
			仓库：<select name="store_id">
				<option value=""></option>
			<%
			if(storeList != null && storeList.size()>0){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHose = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(storeHose.getId());
					String name = StringUtils.nullToStr(storeHose.getName());
			%>
				<option value="<%=id %>" <%if(id.equals(store_id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" border="1" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('rkd_id');">入库单编号<%if(orderName.equals("rkd_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">供应商<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('creatdate');">创建日期<%if(orderName.equals("creatdate")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('store_id');">仓库<%if(orderName.equals("store_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('rk_date');">入库时间<%if(orderName.equals("rk_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('fzr');">库管员<%if(orderName.equals("fzr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map rkd = (Map)it.next();
		
		String rkd_state = StringUtils.nullToStr(rkd.get("state"));
	%>
	<tr class="a1"  title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(rkd.get("rkd_id")) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(rkd.get("rkd_id")) %>');">
		<td><%=StringUtils.nullToStr(rkd.get("rkd_id")) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(rkd.get("client_name"))) %></td>
		<td><%=StringUtils.nullToStr(rkd.get("creatdate")) %></td>
		<td><%=StringUtils.nullToStr(rkd.get("state")) %></td>
		<td><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(rkd.get("store_id"))) %></td>
		<td><%=StringUtils.nullToStr(rkd.get("rk_date")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(rkd.get("fzr"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(rkd.get("czr"))) %></td>
		<td>
			<%if(rkd_state.equals("已入库")){  //已入库的入库单不能修改%>
				<a href="#" onclick="openWin('<%=StringUtils.nullToStr(rkd.get("rkd_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看入库单信息" border="0" style="cursor:hand"></a>
			<%}else{%>
				<a href="#" onclick="edit('<%=StringUtils.nullToStr(rkd.get("rkd_id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改入库单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;			
				<a href="#" onclick="openWin('<%=StringUtils.nullToStr(rkd.get("rkd_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看入库单信息" border="0" style="cursor:hand"></a>
			<%} %>
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

<form name="descForm" action="descRkd.html" method="post" target="desc">
	<input type="hidden" name="rkd_id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" width="100%" onload="dyniframesize('desc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>

</body>
</html>
