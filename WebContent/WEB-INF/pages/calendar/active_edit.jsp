<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>日程安排</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
function saveInfo(){

	document.getElementById("start_time").value = document.getElementById("startHour").value + ":" + document.getElementById("startMin").value;
	document.getElementById("end_time").value = document.getElementById("endHour").value + ":" + document.getElementById("endMin").value;
	
	if(!InputValid(document.getElementById("cdate"),1,"string",1,1,20,"日期")){return; }
	if(!InputValid(document.getElementById("address"),1,"string",1,1,100,"地点")){return; }
	if(!InputValid(document.getElementById("content"),1,"string",1,1,500,"内容")){return; }

	if(document.getElementById("start_time").value>=document.getElementById("end_time").value){
		alert("结束时间不能小于开始时间");
		return;
	}

	document.planForm.submit();
}

function chRemindType(vl){
	var obj = document.getElementById("trRemindTime");

	if(vl == '0'){
		obj.style.display = "none";
		obj.value = "";
	}else{
		obj.style.display = "";
		obj.value = "10";
	}
}

var startTime = '<ww:property value="%{calendarPlan.start_time}"/>';
var endTime = '<ww:property value="%{calendarPlan.end_time}"/>';

function loadTime(){
	if(startTime == "" || endTime == "") return;

	var arryStart = startTime.split(":");
	var arryEnd = endTime.split(":");

	var objStartHour = document.getElementById("startHour");
	for(var i=0;i<objStartHour.options.length;i++){
		if(objStartHour.options[i].value == arryStart[0]){
			objStartHour.options[i].selected = true;
			break;
		}
	}

	var objStartMin = document.getElementById("startMin");
	for(var i=0;i<objStartMin.options.length;i++){
		if(objStartMin.options[i].value == arryStart[1]){
			objStartMin.options[i].selected = true;
			break;
		}
	}

	var objEndHour = document.getElementById("endHour");
	for(var i=0;i<objEndHour.options.length;i++){
		if(objEndHour.options[i].value == arryEnd[0]){
			objEndHour.options[i].selected = true;
			break;
		}
	}

	var objEndMin = document.getElementById("endMin");
	for(var i=0;i<objEndMin.options.length;i++){
		if(objEndMin.options[i].value == arryEnd[1]){
			objEndMin.options[i].selected = true;
			break;
		}
	}
}
</script>
</head>
<body onload="loadTime();">
<form name="planForm" action="updatePlan.html" method="post">
<ww:hidden name="calendarPlan.start_time" id="start_time" value="" theme="simple"></ww:hidden>
<ww:hidden name="calendarPlan.end_time" id="end_time" value="" theme="simple"></ww:hidden>
<ww:hidden name="calendarPlan.id" id="id" value="%{calendarPlan.id}" theme="simple"></ww:hidden>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">日程安排</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="20%">日期</td>
		<td class="a2" width="80%">
			<input type="text" name="calendarPlan.cdate" id="cdate" value="<ww:property value="%{calendarPlan.cdate}"/>" class="Wdate" onFocus="WdatePicker()"/><span style="color:red">*</span>
		</td>				
	</tr>
	<tr>
		<td class="a1">时间</td>
		<td class="a2">
			<select name="startHour" id="startHour">
				<%
				for(int i=0;i<=23;i++){
					if(i<10){
				%>
					<option value="<%="0"+i %>"><%="0"+i %></option>
				<%
					}else{
				%>
					<option value="<%=i %>"><%=i %></option>
				<%	
					}
				}
				%>
			</select>：
			<select name="startMin" id="startMin">
				<%
				for(int i=0;i<=59;i++){
					if(i<10){
				%>
					<option value="<%="0"+i %>"><%="0"+i %></option>
				<%
					}else{
				%>
					<option value="<%=i %>"><%=i %></option>
				<%	
					}
				}
				%>
			</select>	&nbsp;&nbsp;至&nbsp;&nbsp;
			<select name="endHour" id="endHour">
				<%
				for(int i=0;i<=23;i++){
					if(i<10){
				%>
					<option value="<%="0"+i %>"><%="0"+i %></option>
				<%
					}else{
				%>
					<option value="<%=i %>"><%=i %></option>
				<%	
					}
				}
				%>
			</select>：
			<select name="endMin" id="endMin">
				<%
				for(int i=0;i<=59;i++){
					if(i<10){
				%>
					<option value="<%="0"+i %>"><%="0"+i %></option>
				<%
					}else{
				%>
					<option value="<%=i %>"><%=i %></option>
				<%	
					}
				}
				%>
			</select>
		</td>							
	</tr>
	<tr>
		<td class="a1" width="20%">地点</td>
		<td class="a2" width="80%">
			<input type="text" name="calendarPlan.address" id="address" value="<ww:property value="%{calendarPlan.address}"/>" style="width:85%" maxlength="100"/><span style="color:red">*</span>
		</td>
	</tr>
	<tr>
		<td class="a1" width="20%">内容</td>
		<td class="a2" width="80%">
			<textarea name="calendarPlan.content" id="content" style="width:85%" rows="4"/><ww:property value="%{calendarPlan.content}"/></textarea><span style="color:red">*</span>
		</td>
	</tr>
	<tr>
		<td class="a1" width="20%">紧急程度</td>
		<td class="a2" width="80%">
			<ww:radio name="calendarPlan.grade" id="grade" list="#{\"1\":'一般',\"2\":'紧急'}" theme="simple" ></ww:radio>
		</td>
	</tr>	
	<tr>
		<td class="a1" width="20%">是否添加提醒</td>
		<td class="a2" width="80%">
			<ww:radio name="calendarPlan.is_remind" id="is_remind" list="#{\"0\":'否',\"1\":'是'}" theme="simple" onclick="chRemindType(this.value);"></ww:radio>
		</td>
	</tr>
	<tr id="trRemindTime">
		<td class="a1" width="20%">提醒时间设置</td>
		<td class="a2" width="80%">
			提前<input type="text" name="calendarPlan.remind_time" id="remind_time" value="<ww:property value="%{calendarPlan.remind_time}"/>" size="3"/>分钟
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSave" value=" 保存 " class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>