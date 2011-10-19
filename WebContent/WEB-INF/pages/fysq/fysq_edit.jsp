<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List deptList = (List)VS.findValue("deptList");
Fysq fysq = (Fysq)VS.findValue("fysq");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>费用申请单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selSqr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	//保存费用申请单
	function saveInfo(vl){
		if(document.getElementById("sqr").value == ""){
			alert("费用申请人不能为空，请选择");
			return;
		}
		if(document.getElementById("ywy_dept").value == ""){
			alert("费用使用部门不能为空，请选择");
			return;
		}		
		if(document.getElementById("fy_type").value == ""){
			alert("费用类型不能为空，请选择");
			return;
		}
		if(!InputValid(document.getElementById("je"),1,"float",1,1,999999999,"金额")){	 return; }	
		if(!InputValid(document.getElementById("remark"),1,"string",1,1,100,"详细说明")){	 return; }	
		
		if(vl == "1"){
			document.getElementById("state").value = "保存";
			document.frsqForm.submit();
		}else{
			document.getElementById("state").value = "提交";

			if(window.confirm("确认提交申请吗，提交后将不可修改！")){
				document.frsqForm.submit();
			}
		}
		
		document.frsqForm.btnSub.disabled = true;
	}
	
	function openAccount(){
		var destination = "selAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}
	
	function openFyType(){
		var destination = "selFyType.html";
		var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=yes,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'费用类别',fea);	
	}
</script>
</head>
<body onload="initFzrTip();initSqrTip();">
<form name="frsqForm" action="updateFysq.html" method="post">
<ww:hidden name="fysq.state" id="state" theme="simple" value=""></ww:hidden>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">费用申请单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.id" id="id" value="%{fysq.id}" theme="simple" readonly="true" cssStyle="width:190px"/><span style="color:red">*</span>
		</td>
		<td class="a1" width="15%">申请日期</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.creatdate" id="creatdate" value="%{fysq.creatdate}" theme="simple" readonly="true" cssStyle="width:190px"/><span style="color:red">*</span>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">费用申请人</td>
		<td class="a2" width="35%">
		 <ww:textfield name="sqr_text" id="sqr_text" theme="simple" onblur="setSqrValue();" value="%{getUserRealName(fysq.sqr)}" cssStyle="width:190px"/><font color="red">*</font>	
         <div id="sqr_tips" style="position:absolute;left:89px; top:82px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
         </div>
         <ww:hidden name="fysq.sqr" id="sqr" theme="simple" value="%{fysq.sqr}"/>
		</td>
		<td class="a1" width="15%">对应客户</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.xgkh" id="xgkh" value="%{fysq.xgkh}" theme="simple" cssStyle="width:190px" maxLength="100"/>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">费用使用部门</td>
		<td class="a2">
			<select name="fysq.ywy_dept" id="ywy_dept" style="width:190px">
				<option value=""></option>
				<%
				if(deptList != null &&  deptList.size()>0){
					for(int i=0;i<deptList.size();i++){
						Dept dept = (Dept)deptList.get(i);
						
						String dept_id = dept.getDept_id();
						String dept_name = dept.getDept_name();
						
						for(int k=0;k<dept_id.length()-2;k++){
							dept_name = "　" + dept_name;
						}
				%>
				<option value="<%=dept_id %>" <%if(dept_id.equals(StringUtils.nullToStr(fysq.getYwy_dept()))) out.print("selected"); %>><%=dept_name %></option>
				<%
					}
				}
				%>
			</select>
			<font color="red">*</font>	
		</td>		
		<td class="a1" width="15%">费用使用人</td>
		<td class="a2" width="35%">
		 <ww:textfield name="brand" id="brand" theme="simple" onblur="setValue()" value="%{getUserRealName(fysq.ywy_id)}" cssStyle="width:190px"/>
         <div id="brandTip" style="position:absolute;left:89px; top:82px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
         </div>
         <ww:hidden name="fysq.ywy_id" id="fzr" theme="simple" value="%{fysq.ywy_id}"/>	
		</td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">费用类型</td>
		<td class="a2" width="35%">
			<ww:textfield name="fy_type_show" id="fy_type_show" theme="simple" onclick="openFyType();" value="%{getFyTypeName(fysq.fy_type)}" readonly="true" cssStyle="width:190px"/><span style="color:red">*</span>
			<ww:hidden name="fysq.fy_type" id="fy_type" theme="simple" value="%{fysq.fy_type}"/>
			<img src="images/select.gif" align="absmiddle" title="选择费用类型" border="0" onclick="openFyType();" style="cursor:hand">
			
		</td>	
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.je" id="je" value="%{getText('global.format.double',{fysq.je})}" theme="simple" cssStyle="width:190px"/><span style="color:red">*</span>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">支付方式</td>
		<td class="a2" width="35%">
			<ww:select name="fysq.fklx" id="fklx" theme="simple" list="%{fkfs}" emptyOption="true"  cssStyle="width:190px"/>
		</td>	
		<td class="a1" width="15%">支付账户</td>
		<td class="a2">
			<ww:textfield id="zhname"  name="zhname" value="%{getAccountName(fysq.zfzh)}" theme="simple" onclick="openAccount();" cssStyle="width:190px" readonly="true"/>
			<ww:hidden name="fysq.zfzh" id="fkzh" value="%{fysq.zfzh}" theme="simple"/>
			<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>									
	</tr>
	<tr>
	</tr>	
	<tr>
		<td class="a1" width="15%">详细说明</td>
		<td class="a2" colspan="3">
			<ww:textarea name="fysq.remark" id="remark"  theme="simple" cssStyle="width:70%;height:70px"/><span style="color:red">*</span>
		</td>
	</tr>				
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSub" value="草稿" class="css_button2" onclick="saveInfo('1');">&nbsp;
			<input type="button" name="button2" value="提交" class="css_button2" onclick="saveInfo('2');">&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
</form>
</body>
</html>