<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Qtzc qtzc = (Qtzc)VS.findValue("qtzc");
String[] fkfs = (String[])VS.findValue("fkfs");

List userList = (List)VS.findValue("userList");

%>

<html>
<head>
<title>一般费用</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(document.getElementById("type").value == ""){
			alert("支出类型不能为空，请选择！");
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
		if(document.getElementById("jsr").value == ""){
			alert("出纳不能为空，请选择！");
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
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);
	}	
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="qtzcForm" action="updateQtzc.html" method="post">
<input type="hidden" name="qtzc.fysq_id" id="fysq_id" value="<%=StringUtils.nullToStr(qtzc.getFysq_id()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">一般费用</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><input type="text" name="qtzc.id" id="id" value="<%=StringUtils.nullToStr(qtzc.getId()) %>" readonly></td>
		<td class="a1" width="15%">支出日期</td>
		<td class="a2">
			<input type="text" name="qtzc.zc_date" id="zc_date" value="<%=StringUtils.nullToStr(qtzc.getZc_date()) %>" readonly>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">费用类型</td>
		<td class="a2" width="35%"><input type="text" name="qtzc.type" id="type" value="<%=StringUtils.nullToStr(qtzc.getType()) %>" readonly></td>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><input type="text" name="qtzc.zcje" id="zcje" value="<%=JMath.round(qtzc.getZcje()) %>" readonly></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">支出账号</td>
		<td class="a2" width="35%"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(qtzc.getZczh())) %>" readonly>
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
		<td class="a1" width="15%">出纳</td>
		<td class="a2">
			<select name="qtzc.jsr" id="jsr">
				<option value=""></option>
			<%
			if(userList != null){
				Iterator it = userList.iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					String strid = StringUtils.nullToStr(map.get("user_id"));
					String name = StringUtils.nullToStr(map.get("real_name"));
			%>
				<option value="<%=strid %>" <%if(StringUtils.nullToStr(qtzc.getJsr()).equals(strid)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select><span style="color:red">*</span>	
		</td>	
		<td class="a1" width="15%">业务员</td>
		<td class="a2">
			<input type="hidden" name="qtzc.ywy" id="ywy" value="<%=StringUtils.nullToStr(qtzc.getYwy()) %>" readonly>
			<input type="text" name="ywy_name" id="ywy_name" value="<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtzc.getYwy())) %>" readonly>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">相关客户</td>
		<td class="a2" width="35%">
			<input type="text" id="zcxm"  name="qtzc.zcxm" value="<%=StringUtils.nullToStr(qtzc.getZcxm()) %>" size="15" readonly maxlength="100">
		</td>	
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%">
			<select name="qtzc.state" id="state">
				<option value="已保存" <%if(StringUtils.nullToStr(qtzc.getState()).equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(qtzc.getState()).equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>		
		</td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">审批人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtzc.getSpr())) %></td>
		<td class="a1" width="15%">审批时间</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getSp_date()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">费用申请单编号</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(qtzc.getFysq_id()) %></td>
	</tr>	
	<tr height="50">
		<td class="a1">详细说明</td>
		<td class="a2" colspan="3">
			<textarea rows="2" cols="50" name="qtzc.remark" id="remark" style="width:80%" maxlength="500"><%=StringUtils.nullToStr(qtzc.getRemark()) %>
			</textarea><span style="color:red">*</span>
		</td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
