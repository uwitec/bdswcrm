<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageLsd");

String creatdate = (String)VS.findValue("creatdate");
String creatdate2 = (String)VS.findValue("creatdate2");
String client_name = (String)VS.findValue("client_name");
String state = (String)VS.findValue("state");
String xsry_name = (String)VS.findValue("xsry_name");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<html>
<head>
<title>零售单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewLsd.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delLsd.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.creatdate.value = "";
		document.myform.creatdate2.value = "";
		document.myform.client_name.value = "";
		document.myform.state.value = "";
		document.myform.xsry_name.value = "";
	}
	
	function add(){
		var destination = "addLsd.html";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';	
		window.open(destination,'添加销售单',fea);	
	}
	
	function edit(id){
		var destination = "editLsd.html?id=" + id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改销售单',fea);		
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
		document.myform.action = "listLsd.html";
		document.myform.submit();
	}
	
	function print(id){
		var destination = "printLsd.html?id=" + id;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'零售单打印',fea);				
	}	
			
</script>
</head>
<body>
<form name="myform" action="listLsd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>零售单</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="add();"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">
			<table width="100%" border="0" style="font-size: 12px">
				<tr>
					<td align="center">客户名称：</td>
					<td><input type="text" name="client_name" value="<%=client_name %>" size="25"></td>
					<td align="center">时　间：</td>
					<td>
						<input type="text" name="creatdate" id="creatdate" value="<%=creatdate %>" class="Wdate" onFocus="WdatePicker()"/>&nbsp;至&nbsp;
						<input type="text" name="creatdate2" id="creatdate2" value="<%=creatdate2 %>"  class="Wdate" onFocus="WdatePicker()"/>							
					</td>
					<td rowspan="2">
						<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
						<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">					
					</td>
				</tr>			
				<tr>
					<td align="center">状　　态：</td>
					<td>
						<select name="state">
							<option value=""></option>
							<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>已保存</option>
							<option value="已提交" <%if(state.equals("已提交")) out.print("selected"); %>>已成交</option>
						</select>
					</td>
					<td align="center">销售员：</td>
					<td><input type="text" name="xsry_name" value="<%=xsry_name %>" size="15"></td>
				</tr>
			</table>
		</td>
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">零售单编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">客户姓名<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<!--<td onclick="doSort('lxr');">联系人<%if(orderName.equals("lxr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		--><td onclick="doSort('lxdh');">联系电话<%if(orderName.equals("lxdh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lsdje');">金额<%if(orderName.equals("lsdje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('creatdate');">时间<%if(orderName.equals("creatdate")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('xsry');">销售人员<%if(orderName.equals("xsry")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('sp_state');">审批状态<%if(orderName.equals("sp_state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map lsd = (Map)it.next();
		double lsdje = lsd.get("lsdje")==null?0:((Double)lsd.get("lsdje")).doubleValue();
		
		String vl_state = StringUtils.nullToStr(lsd.get("state"));
		vl_state = vl_state.equals("已提交")?"已成交":vl_state;
		
		String sp_state = StringUtils.nullToStr(lsd.get("sp_state"));
		
		String strSpState = "";
		if(sp_state.equals("0")){
			strSpState = "自动审批";
		}else if(sp_state.equals("1")){
			strSpState = "需审批";
		}else if(sp_state.equals("2")){
			strSpState = "待审批";
		}else if(sp_state.equals("3")){
			strSpState = "审批通过";
		}else if(sp_state.equals("4")){
			strSpState = "审批不通过";
		}
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(lsd.get("id")) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(lsd.get("id")) %>');">
		<td><%=StringUtils.nullToStr(lsd.get("id")) %></td>
		<td><%=StringUtils.nullToStr(lsd.get("client_name")) %></td>
		<!--<td><%=StringUtils.nullToStr(lsd.get("lxr")) %></td>
		--><td><%=StringUtils.nullToStr(lsd.get("lxdh")) %></td>
		<td align="right"><%=JMath.round(lsdje,2) %>&nbsp;</td>
		<td><%=StringUtils.nullToStr(lsd.get("creatdate")) %></td>
		<td><%=vl_state %></td>
		<td><%=StringUtils.nullToStr(lsd.get("xsry")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(lsd.get("czr"))) %></td>
		<td><%=strSpState %></td>
		<td>
		<%
		if(StringUtils.nullToStr(lsd.get("state")).equals("已提交") || sp_state.equals("2")){
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(lsd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看零售单信息" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(lsd.get("id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改零售单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(lsd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看零售单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(lsd.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除该零售单" border="0" style="cursor:hand"></a>		
		<%	
		}		
		%>
		&nbsp;&nbsp;&nbsp;&nbsp;	
		<a href="#" onclick="print('<%=StringUtils.nullToStr(lsd.get("id")) %>');"><img src="images/print.png" align="absmiddle" title="打印零售单" border="0" style="cursor:hand"></a>	
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

<form name="descForm" action="descLsd.html" method="post" target="desc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" onload="dyniframesize('desc');" width="100%" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>

</body>
</html>
