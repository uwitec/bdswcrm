<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("kcpdPage");
List storeList = (List)VS.findValue("storeList");

String pdrq1 = (String)VS.findValue("pdrq1");
String pdrq2 = (String)VS.findValue("pdrq2");
String store_id = (String)VS.findValue("store_id");


String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存盘点</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewKcpd.html?id="+id;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=yes';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			//location.href = "delKcpd.html?id=" + id;
			document.myform.action = "delKcpd.html?id=" + id;
			document.myform.submit();
		}
	}
	
	function clearAll(){
		document.myform.pdrq1.value = "";
		document.myform.pdrq2.value = "";
		document.myform.store_id.value = "";
	}
	
	function add(){
		var destination = "addKcpd.html";
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'库存盘点',fea);	
	}
	
	function edit(id){
		var destination = "editKcpd.html?id=" + id;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'库存盘点',fea);		
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
		document.descForm.id.value = id;
		document.descForm.submit();			
	}	
	
	function refreshPage(){
		document.myform.action = "listKcpd.html";
		document.myform.submit();
	}		
		
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listKcpd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>库存盘点</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;			
			盘点日期：<input type="text" name="pdrq1" value="<%=pdrq1 %>" size="15"  class="Wdate" onFocus="WdatePicker()">	
			&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
			<input type="text" name="pdrq2" value="<%=pdrq2 %>" size="15"  class="Wdate" onFocus="WdatePicker()">			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			仓库名称：
			<select name="store_id">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHouse = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(storeHouse.getId());
					String nm = StringUtils.nullToStr(storeHouse.getName());
			%>
				<option value="<%=id %>" <%if(store_id.equals(id)) out.print("selected"); %>><%=nm %></option>
			<%
				}
			}
			%>
			</select>				
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="12%" nowrap="nowrap" onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="23%" nowrap="nowrap" onclick="doSort('store_id');">仓库名称<%if(orderName.equals("store_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="13%" nowrap="nowrap" onclick="doSort('pdr');">盘点人<%if(orderName.equals("pdr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" nowrap="nowrap" onclick="doSort('pdrq');">盘点日期<%if(orderName.equals("pdrq")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%" nowrap="nowrap" onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="12%" nowrap="nowrap" onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" nowrap="nowrap">操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
	%>
	<tr class="a1"  title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(map.get("id")) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');">
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(map.get("store_id"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("pdr"))) %></td>
		<td><%=StringUtils.nullToStr(map.get("pdrq")) %></td>
		<td><%=StringUtils.nullToStr(map.get("state")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("czr"))) %></td>
		<td>
			<%if(StringUtils.nullToStr(map.get("state")).equals("已提交")) { %>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
			<%}else{ %>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>
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

<form name="descForm" action="descKcpd.html" method="post" target="kcpddesc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="kcpddesc" name="kcpddesc" width="100%" onload="dyniframesize('kcpddesc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</div>
</body>
</html>
