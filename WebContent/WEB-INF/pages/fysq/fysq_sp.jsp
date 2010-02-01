<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Fysq  fysq=(Fysq)VS.findValue("fysq");
List deptList = (List)VS.findValue("deptList");

List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
%>
<html>
<head>
<title>费用审批</title>
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
	
	//检查表单输入情况
	function chkForm(){
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
		if(!InputValid(document.getElementById("je"),1,"float",1,1,999999999,"金额")){	 return false; }	
		if(!InputValid(document.getElementById("remark"),1,"string",1,1,100,"详细说明")){	 return false; }	
		
		return true;
	}
	
	//打开账户列表选择账户
	function openAccount(){
		var destination = "selAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}
	
	
	//审批事项
	function doSp(flag){
		if(chkForm){
			document.getElementById("state").value = flag;
			
			document.frsqForm.btnTg.disabled = true;
			document.frsqForm.btnBtg.disabled = true;
			
			document.frsqForm.submit();
		}
	}
	
	function openFyType(){
		var destination = "selFyType.html";
		var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=yes,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'费用类别',fea);	
	}	
</script>
</head>
<body onload="initFzrTip();initSqrTip();">
<form name="frsqForm" action="doSpFysq.html" method="post">
<ww:hidden name="fysq.state" id="state" theme="simple" value=""/>
<ww:hidden name="fysq.czr" id="czr" theme="simple" value="%{fysq.czr}"/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">费用审批</td>
	</tr>
	</thead>
	<%
	if(msg != null && msg.size() > 0){
		for(int i=0;i<msg.size();i++){
	%>
	<tr>
		<td colspan="4" class="a2"><font color="red"><%=StringUtils.nullToStr(msg.get(i)) %></font></td>
	</tr>
	<%
		}
	}
	%>	
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.id" id="id" value="%{fysq.id}" theme="simple" readonly="true"/><span style="color:red">*</span>
		</td>
		<td class="a1" width="15%">申请日期</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.creatdate" id="creatdate" value="%{fysq.creatdate}" theme="simple" readonly="true"/><span style="color:red">*</span>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">费用申请人</td>
		<td class="a2" width="35%">
		 <ww:textfield name="sqr_text" id="sqr_text" theme="simple" onblur="setSqrValue();" value="%{getUserRealName(fysq.sqr)}"/>
         <div id="sqr_tips" style="height:12px;position:absolute;left:89px; top:82px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
         </div>
         <ww:hidden name="fysq.sqr" id="sqr" theme="simple" value="%{fysq.sqr}"/><font color="red">*</font>	
		</td>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.je" id="je" value="%{getText('global.format.double',{fysq.je})}" theme="simple"/><span style="color:red">*</span>
		</td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">费用使用部门</td>
		<td class="a2">
			<select name="fysq.ywy_dept" id="ywy_dept">
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
			</select><font color="red">*</font>	
		</td>
		<td class="a1" width="15%">费用使用人</td>
		<td class="a2" width="35%">
		 <ww:textfield name="brand" id="brand" theme="simple" onblur="setValue()" value="%{getUserRealName(fysq.ywy_id)}"/>
         <div id="brandTip" style="height:12px;position:absolute;left:89px; top:82px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
         </div>
         <ww:hidden name="fysq.ywy_id" id="fzr" theme="simple" value="%{fysq.ywy_id}"/>
		</td>								
	</tr>
	<tr>
		<td class="a1" width="15%">费用类型</td>
		<td class="a2" width="35%">
			<ww:textfield name="fy_type_show" id="fy_type_show" theme="simple" value="%{getFyTypeName(fysq.fy_type)}" readonly="true"/>
			<ww:hidden name="fysq.fy_type" id="fy_type" theme="simple" value="%{fysq.fy_type}"/>
			<img src="images/select.gif" align="absmiddle" title="选择费用类型" border="0" onclick="openFyType();" style="cursor:hand">
			<span style="color:red">*</span>
		</td>
		<td class="a1" width="15%">对应客户</td>
		<td class="a2" width="35%">
			<ww:textfield name="fysq.xgkh" id="xgkh" value="%{fysq.xgkh}" theme="simple" size="30" maxLength="100"/>
		</td>							
	</tr>
	<tr>
		<td class="a1" width="15%">支付方式</td>
		<td class="a2" width="35%">
			<ww:select name="fysq.fklx" id="fklx" theme="simple" list="%{fkfs}" emptyOption="true" />
		</td>	
		<td class="a1" width="15%">支付账户</td>
		<td class="a2" width="35%">
			<ww:textfield id="zhname"  name="zhname" value="%{getAccountName(fysq.zfzh)}" theme="simple" readonly="true"/>
			<ww:hidden name="fysq.zfzh" id="fkzh" value="%{fysq.zfzh}" theme="simple"/>
			<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>						
	</tr>	
	<tr>
		<td class="a1" width="15%">详细说明</td>
		<td class="a2" colspan="3">
			<ww:textarea name="fysq.remark" id="remark"  theme="simple" cssStyle="width:70%;height:70px"/><span style="color:red">*</span>
		</td>
	</tr>				
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnTg" value="审批通过" class="css_button3" onclick="doSp('审批通过');this.disabled=true;">&nbsp;
			<input type="reset" name="btnBtg" value="审批不通过" class="css_button3" onclick="doSp('审批不通过');this.disabled=true;">&nbsp;
			<input type="button" name="button1" value=" 关 闭 " class="css_button3" onclick="window.opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>