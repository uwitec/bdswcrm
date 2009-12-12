<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("wxcldPage");

String w_cj_date1 = (String)VS.findValue("w_cj_date1");
String w_cj_date2 = (String)VS.findValue("w_cj_date2");
 
String w_state = (String)VS.findValue("w_state");
String w_linkman = (String)VS.findValue("w_linkman");
String w_wx_state=(String)VS.findValue("w_wx_state");
String product_serial_num=(String)VS.findValue("product_serial_num");
 

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<html>
<head>
<title>维修处理单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id,wxcld_id){
		var destination = "viewWxcld.html?id="+id+"&wxcld_id="+wxcld_id+"";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delSfd.html?id="+id;
		}
	}
	
	function clearAll(){
		document.myform.w_linkman.value = "";
		document.myform.w_cj_date1.value = "";
		document.myform.w_cj_date2.value = "";
		document.myform.w_wx_state.value = "";
		document.myform.w_state.value = "";
		document.myform.product_serial_num.value = "";
	}
	
	function add(){
		var destination = "addSfd.html";
			var fea ='width=750,height=400,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'售后服务单',fea);	
	}	
	function edit(id,wxcld_id){
		var destination ="editWxcld.html?id="+id+"&wxcld_id="+wxcld_id+"";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);			
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
		document.myform.action = "listWxcld.html";
		document.myform.submit();
	}
			
</script>
</head>
<body>
<form name="myform" action="listWxcld.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>维修处理单</b></td>
		<td class="csstitle" width="25%">
		 
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			联系人：<input type="text" name="w_linkman" value="<%=w_linkman%>">&nbsp;&nbsp;
			时间：<input type="text" name="w_cj_date1" value="<%=w_cj_date1%>" size="8" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.cj_date1); return false;">&nbsp;至&nbsp;
			<input type="text" name="w_cj_date2" value="<%=w_cj_date2%>" size="8" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.cj_date2); return false;">			
			&nbsp;&nbsp;
			状态：<select name="w_state">
				<option value=""></option>
				<option value="已保存" <%if(w_state.equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(w_state.equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>&nbsp;&nbsp;
			维修状态：<select name="w_wx_state">
				<option value=""></option>
				<option value="待处理" <%if(w_wx_state.equals("待处理")) out.print("selected"); %>>待处理</option>
				<option value="已处理" <%if(w_wx_state.equals("已处理")) out.print("selected"); %>>已处理</option>
			</select>&nbsp;&nbsp;
			 序列号：<input type="text" name="product_serial_num" value="<%=product_serial_num%>">
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('w_id');">维修处理单编号<%if(orderName.equals("w_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('w_client_name');">往来单位<%if(orderName.equals("w_client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('w_linkman');">联系人<%if(orderName.equals("w_linkman")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('w_mobile');">联系电话<%if(orderName.equals("w_mobile")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('w_jx_date');">维修时间<%if(orderName.equals("w_jx_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('w_state');">状态<%if(orderName.equals("w_state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td onclick="doSort('w_wx_state');">维修状态<%if(orderName.equals("w_wx_state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('w_pgr');">派工人<%if(orderName.equals("w_pgr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>				
		<td onclick="doSort('w_wxr');">维修人<%if(orderName.equals("w_wxr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>						
		<td onclick="doSort('w_cjr');">操作员<%if(orderName.equals("w_cjr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map pgd = (Map)it.next();		
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(pgd.get("w_id"))%>');"  onDblClick="openWin('<%=StringUtils.nullToStr(pgd.get("w_pgd_id"))%>','<%=StringUtils.nullToStr(pgd.get("w_id"))%>');">
		<td><%=StringUtils.nullToStr(pgd.get("w_id")) %></td>
		<td><%=StringUtils.nullToStr(StaticParamDo.getClientNameById((String)pgd.get("w_client_name"))) %></td>
		<td><%=StringUtils.nullToStr(pgd.get("w_linkman")) %></td>
		<td><%=StringUtils.nullToStr(pgd.get("w_mobile")) %></td>
		<td><%=StringUtils.nullToStr(pgd.get("w_jx_date")) %></td>
		<td><%=StringUtils.nullToStr(pgd.get("w_state")) %></td>	
		<td><%=StringUtils.nullToStr(pgd.get("w_wx_state")) %></td>	 		 
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(pgd.get("w_pgr"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(pgd.get("w_wxr"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(pgd.get("w_cjr"))) %></td>
		<td>
		<%
		if(StringUtils.nullToStr(pgd.get("w_state")).equals("已提交"))
		{
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(pgd.get("w_pgd_id")) %>','<%=StringUtils.nullToStr(pgd.get("w_id"))%>');"><img src="images/view.gif" align="absmiddle" title="查看派工单信息" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(pgd.get("w_pgd_id"))%>','<%=StringUtils.nullToStr(pgd.get("w_id"))%>');"><img src="images/modify.gif" align="absmiddle" title="修改派工单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(pgd.get("w_pgd_id")) %>','<%=StringUtils.nullToStr(pgd.get("w_id"))%>');"><img src="images/view.gif" align="absmiddle" title="查看派工单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;			 
		<%	
		}		
		%>
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

<form name="descForm" action="descWxcld.html" method="post" target="desc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" onload="dyniframesize('desc');" width="100%" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</body>
</html>