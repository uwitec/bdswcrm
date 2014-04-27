<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List deptList = (List)VS.findValue("deptList");
String q_dept_id = StringUtils.nullToStr(request.getParameter("dept_id"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择待开发票</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function clearAll(){
		document.myform.start_date.value = "";
		document.myform.end_date.value = "";
		document.myform.khmc.value = "";
		document.myform.xsry_name.value = "";
		document.myform.dept_id.value = "";		
	}
	
	function refreshPage(){
		document.myform.action = "listDkfp.html";
		document.myform.submit();
	}
	
	function openWin(id,yw_type){
		var destination = "";
		var fea ='width=980,height=650,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		if(yw_type == "销售单"){
			destination = "viewXsd.html?id="+id;
		}else{
			destination = "viewLsd.html?id="+id;
		}
		window.open(destination,'详细信息',fea);	
	}	
</script>
</head>
<body>
<form name="myform" action="listDkfp.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>销售待开发票</b></td>	
		<td class="csstitle" width="20%" align="center">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>
		</td>				
	</tr>	
	<tr>
		<td class="search"  colspan="2">
<table>
				<tr>
					<td width="8%">&nbsp;客户名称：</td>
					<td width="25%"><input type="text" name="khmc" id="khmc" value="<ww:property value="%{khmc}" />" /></td>
					<td width="8%">开票日期：</td>
					<td width="35%"><input type="text" name="start_date" id="start_date" value="<ww:property value="%{start_date}" />"  class="Wdate" onFocus="WdatePicker()"/> &nbsp;至&nbsp;
							<input type="text" name="end_date" id="end_date" value="<ww:property value="%{end_date}" />"   class="Wdate" onFocus="WdatePicker()"/>&nbsp;	</td>	
					<td rowspan="2" width="14%">			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">		</td>				
				</tr>
				<tr>
					<td>&nbsp;销售部门：</td>
					<td><select name="dept_id" >
				<option value=""></option>
				<%
				if(deptList != null &&  deptList.size()>0){
					for(int i=0;i<deptList.size();i++){
						Dept dept = (Dept)deptList.get(i);
						
						String dept_id = dept.getDept_id();
						String dept_name = dept.getDept_name();
						
						for(int k=0;k<dept_id.length()-2;k++){
							dept_name = "&nbsp;&nbsp;" + dept_name;
						}
				%>
				<option value="<%=dept_id %>" <%if (q_dept_id.equals(dept_id)) out.print("selected"); %>><%=dept_name %></option>
				<%
					}
				}
				%>
			</select></td>
					<td>销售人员：</td>
					<td><input type="text" name="xsry_name" id="xsry_name" value="<ww:property value="xsry_name" />" /></td>
				</tr>
			</table>		
		</td>				
	</tr>		
</table>
<table width="100%" align="center" border="1" class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td width="18%">客户名称</td>
		<td width="10%">对应业务</td>
		<td width="10%">业务编号</td>
		<td width="10%">发票类型</td>
		<td width="8%">状态</td>
		<td width="8%">订单金额</td>
		<td width="10%">已开发票金额</td>
		<td width="10%">销售部门</td>
		<td width="8%">销售人员</td>
		<td width="8%">交易日期</td>
	</tr>
	</thead>
	<ww:iterator value="%{xsfpFpxxPage.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
			<td><ww:property value="%{khmc}" /></td>
			<td><ww:property value="%{yw_type}" /></td>
			<td><a href="javascript:openWin('<ww:property value="%{yw_id}" />','<ww:property value="%{yw_type}" />');"><ww:property value="%{yw_id}" /></a></td>
			<td><ww:property value="%{fplx}" /></td>
			<td><ww:property value="%{state}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{ddje})}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{ykpje})}" /></td>
			<td><ww:property value="%{gerDeptNameByUserId(jy_jsr)}" /></td>
			<td><ww:property value="%{getUserRealName(jy_jsr)}" /></td>
			<td><ww:property value="%{jy_date}" /></td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{xsfpFpxxPage}" formname="myform"/>
		</td>
	</tr>
</table>
</form>
注：点击业务单据编号可以查看原始单据。
</body>
</html>