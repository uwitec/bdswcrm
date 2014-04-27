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

	function selectAll(){
		var flag = false;
		if(document.myform.allCheck.checked){
			flag = true;
		}
		for(var i=0;i<document.myform.elements.length;i++){
			var o = document.myform.elements[i];
			if(o.name == "chk_id"){
				if(flag == true){
					o.checked = true;
				}else{
					o.checked = false;
				}
			}
		}
	}	

	function sel(sel_flag){
		var k = 0;
		var allCount = window.opener.allCount;  //当前存在的最大行序号
		var startCount = 0;
		for(var x=0;x<=allCount;x++){
			var id = window.opener.document.getElementById("yw_id_"+x);
	
			startCount = x;
			
			if(id.value==""){  //如果该行无值则跳出
				break;
			}
			
			if(startCount == allCount){
				startCount = startCount + 1;
			}
		}
		
		k = startCount;  //从此位置开始添加
		//alert("从此位置开始添加" + k);

		var objs = document.getElementsByName("chk_id");
		for(var i=0;i<objs.length;i++){
					
			var o = objs[i];
			if(o.checked){
			
				var vl = objs[i].value;
				var arryStr = vl.split("|");
				
				var flag = false;
				for(var y=0;y<=allCount;y++){
					var s_yw_id = window.opener.document.getElementById("yw_id_"+y);
					
					if(s_yw_id.value == arryStr[0]){  //该值已存在
						flag = true;
					}
				}			
				if(flag == false){
					var yw_id = window.opener.document.getElementById("yw_id_"+k);
					if(yw_id == null){
						window.opener.addTr();
					}
					yw_id = window.opener.document.getElementById("yw_id_"+k);
					var khmc = window.opener.document.getElementById("khmc_"+k);
					var cdate = window.opener.document.getElementById("cdate_"+k);
					var kpje_ying = window.opener.document.getElementById("kpje_ying_"+k);
					var kpje_yi = window.opener.document.getElementById("kpje_yi_"+k);
					var kpje_bc = window.opener.document.getElementById("kpje_bc_"+k);
				
				
					yw_id.value = arryStr[0];
					khmc.value = arryStr[1];
					cdate.value = arryStr[2];
					kpje_ying.value = arryStr[3];
					kpje_yi.value = arryStr[4];
					kpje_bc.value = "0.00";
					
					k++;	
				}	
			}

		}
		window.opener.hj();
		if(sel_flag == "2"){
			window.close();
		}
	}	
</script>
</head>
<body>
<form name="myform" action="listXsfpDkfp.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="search" align="left">
			<table>
				<tr>
					<td width="8%">客户名称：</td>
					<td width="25%"><input type="text" name="khmc" id="khmc" value="<ww:property value="%{khmc}" />" /></td>
					<td width="8%">开票日期：</td>
					<td width="35%"><input type="text" name="start_date" id="start_date" value="<ww:property value="%{start_date}" />"  class="Wdate" onFocus="WdatePicker()"/> &nbsp;至&nbsp;
							<input type="text" name="end_date" id="end_date" value="<ww:property value="%{end_date}" />"   class="Wdate" onFocus="WdatePicker()"/>&nbsp;	</td>	
					<td rowspan="2" width="14%">			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">		</td>				
				</tr>
				<tr>
					<td>销售部门：</td>
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
		<td width="5%"><input type="checkbox" name="allCheck" onclick="selectAll();"></td>
		<td width="20%">客户名称</td>
		<td width="10%">对应业务</td>
		<td width="15%">业务编号</td>
		<td width="10%">订单金额</td>
		<td width="10%">已开发票金额</td>
		<td width="10%">销售部门</td>
		<td width="10%">销售人员</td>
		<td width="10%">交易日期</td>
	</tr>
	</thead>
	<ww:iterator value="%{xsfpFpxxPage.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
			<td><input type="checkbox" name="chk_id" value="<ww:property value="yw_id"/>|<ww:property value="khmc"/>|<ww:property value="jy_date"/>|<ww:property value="%{getText('global.format.double',{ddje})}"/>|<ww:property value="%{getText('global.format.double',{ykpje})}"/>"></td>
			<td><ww:property value="%{khmc}" /></td>
			<td><ww:property value="%{yw_type}" /></td>
			<td><ww:property value="%{yw_id}" /></td>
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
<center><BR>
	<input type="button" name="buttonQd" value="确认并继续选择" onclick="sel('1');" class="css_button4">
	<input type="button" name="buttonQd" value="确认选择并关闭" onclick="sel('2');" class="css_button4">
	<input type="button" name="buttonQd" value=" 关闭 " onclick="window.close();" class="css_button2">
</center>
</form>
</body>
</html>