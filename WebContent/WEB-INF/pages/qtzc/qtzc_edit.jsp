<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Qtzc qtzc = (Qtzc)VS.findValue("qtzc");
String[] fkfs = (String[])VS.findValue("fkfs");

List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
%>

<html>
<head>
<title>一般费用</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	function saveInfo(){
		if(document.getElementById("fzr").value == ""){
			alert("出纳不能为空，请选择！");
			return;
		}		
		if(document.getElementById("skzh").value == ""){
			alert("支出账号不能为空，请选择！");
			return;
		}
		if(document.getElementById("fklx").value == ""){
			alert("付款方式不能为空，请选择！");
			return;
		}			

		if(document.getElementById("remark").value == ""){
			alert("详细说明不能为空，请填写！");
			return;
		}					
		document.qtzcForm.btnSub.disabled = true;
		document.qtzcForm.submit();
	}

	
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=yes,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);
	}
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	
		
	
</script>
</head>
<body onload="initFzrTip();">
<form name="qtzcForm" action="updateQtzc.html" method="post">
<input type="hidden" name="qtzc.fysq_id" id="fysq_id" value="<%=StringUtils.nullToStr(qtzc.getFysq_id()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">费用申请信息</td>
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
		<td class="a1" width="15%">费用申请单编号</td>
		<td class="a2"><%=StringUtils.nullToStr(qtzc.getFysq_id()) %></td>
		<td class="a1" width="15%">费用类型</td>
		<td class="a2" width="35%"><%=StaticParamDo.getFyTypeNameById(qtzc.getType()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">费用申请人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(qtzc.getSqr()) %></td>	
		<td class="a1" width="15%">费用使用人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(qtzc.getYwy()) %></td>						
	</tr>
	<tr>
		<td class="a1" width="15%">费用使用部门</td>
		<td class="a2"><%=StaticParamDo.getDeptNameById(qtzc.getYwy_dept()) %></td>				
		<td class="a1" width="15%">对应客户</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getZcxm()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">审批人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtzc.getSpr())) %></td>
		<td class="a1" width="15%">审批时间</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getSp_date()) %></td>
	</tr>
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">费用支出信息</td>
	</tr>
	</thead>					
	<tr>
		<td class="a1" width="15%">支出编号</td>
		<td class="a2" width="35%"><input type="text" name="qtzc.id" id="id" value="<%=StringUtils.nullToStr(qtzc.getId()) %>" readonly></td>
		<td class="a1" width="15%">支出日期</td>
		<td class="a2">
			<input type="text" name="qtzc.zc_date" id="zc_date" value="<%=StringUtils.nullToStr(qtzc.getZc_date()) %>" readonly>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">出纳</td>
		<td class="a2">
		    <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(qtzc.getJsr()) %>"/> 
            <div   id="brandTip"  style="height:12px;position:absolute;left:95px; top:141px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="qtzc.jsr" id="fzr" value="<%=StringUtils.nullToStr(qtzc.getJsr())%>"/><font color="red">*</font>	
		</td>	
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><input type="text" name="qtzc.zcje" id="zcje" value="<%=JMath.round(qtzc.getZcje()) %>" readonly></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">支出账号</td>
		<td class="a2" width="35%"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(qtzc.getZczh())) %>" size="30" readonly>
		<input type="hidden" id="skzh"  name="qtzc.zczh" value="<%=StringUtils.nullToStr(qtzc.getZczh()) %>"><span style="color:red">*</span>
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>	
		<td class="a1" width="15%">付款方式</td>
		<td class="a2" width="35%">
			<select name="qtzc.fklx" id="fklx">
				<option value=""></option>
			<%
			if(fkfs != null && fkfs.length>0){
				for(int i=0;i<fkfs.length;i++){
			%>
			<option value="<%=fkfs[i] %>" <%if(StringUtils.nullToStr(qtzc.getFklx()).equals(fkfs[i])) out.print("selected"); %>><%=fkfs[i] %></option>
			<%		
				}
			}
			%>
			</select><span style="color:red">*</span>
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%" colspan="3">
			<select name="qtzc.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(qtzc.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(qtzc.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>		
		</td>		
	</tr>
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">备注</td>
	</tr>
	</thead>	
	<tr height="50">
		<td class="a1">详细说明</td>
		<td class="a2" colspan="3">
			<textarea rows="4" cols="50" name="qtzc.remark" id="remark" style="width:80%" maxlength="500"><%=StringUtils.nullToStr(qtzc.getRemark()) %>
			</textarea><span style="color:red">*</span>
		</td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>