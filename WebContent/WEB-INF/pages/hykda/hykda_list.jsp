<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("hykdaPage");
String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType"); 
String hykh = (String)VS.findValue("hykh");
%>

<html>
<head>
<title>会员卡档案</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(hykh){
		var destination = "viewHykda.html?hykh="+hykh;
		var fea = 'width=750,height=400,left=' + (screen.availWidth-550)/2 + ',top=' + (screen.availHeight-350)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(hykh){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delHykda.html?hykh=" + hykh;
		}
	}
	
	function add(){
		var destination = "addHykda.html";
		var fea = 'width=750,height=400,left=' + (screen.availWidth-550)/2 + ',top=' + (screen.availHeight-350)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加会员卡档案',fea);	
	}
	
	function edit(hykh){
		var destination = "editHykda.html?hykh=" + hykh;
		var fea = 'width=750,height=400,left=' + (screen.availWidth-550)/2 + ',top=' + (screen.availHeight-350)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改会员卡档案',fea);		
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
		document.myform.action = "listHykda.html";
		document.myform.submit();
	}
	
	function clearAll(){
		document.myform.hykh.value = "";	
	}
</script>
</head>
<body >
<form name="myform" action="listHykda.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>会员卡档案</b></td>
		<td class="csstitle" width="25%">
			<!--  <img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> -->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			会员卡号：<input type="text" name="hykh" value="<%=hykh %>" size="25" >	
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0"  border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('hykh');">会员卡号<%if(orderName.equals("hykh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('dwmc');">单位名称<%if(orderName.equals("dwmc")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td onclick="doSort('lxrname');">联系人<%if(orderName.equals("lxrname")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
		<td onclick="doSort('lxdh');">联系电话<%if(orderName.equals("lxdh")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>
        <td onclick="doSort('mobile');">手机<%if(orderName.equals("mobile")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('address');">地址<%if(orderName.equals("address")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('czr');">创建人<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %> </td>			
		<td>操作</td>		
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	int i = 0;
	while(it.hasNext()){
		Hykda info = (Hykda)it.next();
		i++;
	%>
	<tr>
		<td class="a1"><%=StringUtils.nullToStr(info.getHykh()) %></td>
		<td class="a1"  align="left"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(info.getHymc())) %></td>
		<td class="a1"  align="right"><%=StringUtils.nullToStr(info.getLxrname())%> </td>
		<td class="a1"  align="left"><%=StringUtils.nullToStr(info.getLxdh()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(info.getMobile()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(info.getAddress()) %></td>
		<td class="a1"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(info.getCzr())) %></td>
		<td class="a1">
			<a href="javascript:void(0);" onclick="edit('<%=StringUtils.nullToStr(info.getHykh()) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" onclick="openWin('<%=StringUtils.nullToStr(info.getHykh()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
</body>
</html>
