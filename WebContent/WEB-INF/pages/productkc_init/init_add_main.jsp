<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List storeList = (List)VS.findValue("storeList");
List userList = (List)VS.findValue("userList");

String errorFlag = (String)session.getAttribute("errorFlag");
if(errorFlag == null){
	errorFlag = "0";
}
session.removeAttribute("errorFlag");

ProductKcInit productKcInit = (ProductKcInit)VS.findValue("productKcInit");

String flag = "";
String store_id = "";
String jsr = "";
String create_date = DateComFunc.getToday();

if(productKcInit != null){
	if(productKcInit.getStore_id() != null){
		flag = "disabled";
		store_id = productKcInit.getStore_id();
		jsr = productKcInit.getJsr();
		create_date = productKcInit.getCreate_date();
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存初始化</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	var flag = "<%=errorFlag%>";	
	function saveInfo(){
		if(document.getElementById("store_id").value == ""){
			alert("仓库名称不能为空，请选择！");
			return;
		}
		if(document.getElementById("jsr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}
		if(document.getElementById("create_date").value == ""){
			alert("初始日期不能为空，请选择！");
			return;
		}
		if(window.confirm("确认保存库存初始信息吗，保存后将不能修改！")){
			document.myform.submit();
		}						
	}
	window.opener.document.myform.submit();
	
	function loadFlag(){
		if(flag == "1"){
			alert("该库房初始信息已存在，请继续原有初始化！");
		}
	}
</script>
</head>

<body align="center" onload="loadFlag();">
<form name="myform" action="saveInit.html" method="post">
<table width="100%" height="495" border="0" align="left" cellpadding="0" class="chart_list" cellspacing="0">	
	<tr>
		<td class="search" align="left" colspan="2">
		&nbsp;仓库名称：
		<select name="productKcInit.store_id" id="store_id" <%=flag %>>
			<option value=""></option>
			<%
			if(storeList != null && storeList.size() > 0){
				for(int i=0;i<storeList.size();i++){
					StoreHouse info = (StoreHouse)storeList.get(i);
			%>
				<option value="<%=info.getId() %>" <%if(store_id.equals(info.getId())) out.print("selected"); %>><%=info.getName() %></option>
			<%
				}
			}
			%>
		</select>&nbsp;&nbsp;&nbsp;
		经手人：
		<select name="productKcInit.jsr" id="jsr" <%=flag %>>
			<option value=""></option>
		<%
		if(userList != null){
			Iterator it = userList.iterator();
			while(it.hasNext()){
				Map map = (Map)it.next();
				String id = StringUtils.nullToStr(map.get("user_id"));
				String name = StringUtils.nullToStr(map.get("real_name"));
		%>
			<option value="<%=id %>" <%if(jsr.equals(id)) out.print("selected"); %>><%=name %></option>
		<%
			}
		}
		%>
		</select>&nbsp;&nbsp;
		初始日期：<input type="text" name="productKcInit.create_date" id="create_date" value="<%=create_date %>" size="12" readonly <%=flag %>>
		
		<%if(flag.equals("")){ %>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('create_date')); return false;">		
		
		<input type="button" name="button1" value="保存" onclick="saveInfo();" class="css_button2">
		<%} %>
		</td>	
	</tr>
	<tr valign="top" height="450">
		<td width="200" class="a2">
			<iframe width="100%" height="100%" name="kindFrame" allowTransparency="true" src="initLeftKind.html?store_id=<%=store_id %>" border="0" frameborder="0" SCROLLING="auto"></iframe>
		</td>	
		<td width="600">
			<iframe width="100%" height="100%" name="right" allowTransparency="true" src="listInitProductKc.html?store_id=<%=store_id %>"  border="0" frameborder="0" SCROLLING="no"></iframe>			
		</td>
	</tr>
</table>
</form>
</body>
</html>
