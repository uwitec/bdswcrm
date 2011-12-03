<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("userPage");
String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
String real_name = (String)VS.findValue("real_name");
String is_del = (String)VS.findValue("is_del");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function openWin(id){
		var destination = "viewUser.html?user_id="+id;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			//location.href = "delUser.html?user_id=" + id;
			document.myform.action = "delUser.html?user_id=" + id;
			document.myform.submit();
		}
	}

   function restore(id){
		if(confirm("确定要恢复该条记录吗！")){
			$.ajax({
				cache: false,
				url:"checkUserLimit.html",
				type: "POST",
				success: function(result) {
					if(result == "true"){
						alert("超出用户数限制不能恢复该用户，如需恢复该用户请与服务商联系！");
						return;
					}else{
						//location.href = "restoreUser.html?user_id=" + id;
						document.myform.action = "restoreUser.html?user_id=" + id;
						document.myform.submit();
					}
				}
			});	
			
		}
	}

	function resetPass(userName){
		if(confirm("确定要重置该用户密码吗！")){
			document.descForm.user_name.value = userName;
			document.descForm.submit();
		}
	}
	
	function add(){
		var destination = "addUser.html";
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'添加系统用户',fea);	
	}
	
	function edit(userId){
		var destination = "editUser.html?user_id=" + userId;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'添加系统用户',fea);	
	}		
	
	function clearAll(){
		document.myform.real_name.value = "";
		document.myform.is_del.value = "";
	}
	
	function sel(){
		var k = 0;
		var objs = document.getElementsByName("user_id");
		for(var i=0;i<objs.length;i++){
			var o = objs[i];
			if(o.checked){
				k = k + 1;
			}
		}
		if(k != 1){
			alert("请选择一个用户！");
			return;
		}
		
		document.myform.action = "openRoles.html";
		document.myform.submit();
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
	
	function refreshPage(){
		document.myform.action = "listUser.html";
		document.myform.submit();
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
<body >
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listUser.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>用户管理</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="javascript:add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			真实姓名：<input type="text" name="real_name" value="<%=real_name %>">&nbsp;&nbsp;&nbsp;&nbsp;
			状态：
			<select name="is_del">
				<option value=""></option>
				<option value="0" <%if("0".equals(StringUtils.nullToStr(is_del))) out.print("selected"); %>>正常</option>
				<option value="1" <%if("1".equals(StringUtils.nullToStr(is_del))) out.print("selected"); %>>删除</option>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;		
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td>选择</td>
		<td onclick="doSort('user_id');">用户编号<%if(orderName.equals("user_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('user_name');">登录名<%if(orderName.equals("user_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('real_name');">真实姓名<%if(orderName.equals("real_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('gs_phone');">电话<%if(orderName.equals("gs_phone")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('mobile');">手机<%if(orderName.equals("mobile")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('mobile');">状态<%if(orderName.equals("is_del")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		String q_is_del = StringUtils.nullToStr(map.get("is_del"));
		if(q_is_del.equals("0")){
			q_is_del =  "正常";
		}else{
			q_is_del = "删除";
		}
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()" onDblClick="edit('<%=StringUtils.nullToStr(map.get("user_id")) %>');";>
		<td><input type="checkbox" name="user_id" value="<%=StringUtils.nullToStr(map.get("user_id")) %>"></td>
		<td><%=StringUtils.nullToStr(map.get("user_id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("user_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("real_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("gs_phone")) %></td>
		<td><%=StringUtils.nullToStr(map.get("mobile")) %></td>
		<td><%=q_is_del %></td>
		<td>
			<a href="javascript:edit('<%=StringUtils.nullToStr(map.get("user_id")) %>');">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<%if(StringUtils.nullToStr(map.get("is_del")).equals("0")){ %>
			  <a href="javascript:del('<%=StringUtils.nullToStr(map.get("user_id")) %>');">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<%}else{ %>
		      <a href="javascript:restore('<%=StringUtils.nullToStr(map.get("user_id")) %>');">还原</a>&nbsp;&nbsp;&nbsp;&nbsp;
		    <%} %>
			<a href="javascript:resetPass('<%=StringUtils.nullToStr(map.get("user_name")) %>');">重置密码</a>
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
	<tr>
		<td class="a2"><input type="button" class="css_button3" name="buttonx" value="设定用户角色" onclick="sel();"></td>
	</tr>	
</table>
</form>
<form name="descForm" action="resetPass.html" method="post" target="hiddenIframe">
	<input type="hidden" name="user_name" value="">
</form>
<iframe name="hiddenIframe" id="hiddenIframe" width="0" height="0"/>
</iframe>
</div>
</body>
</html>
