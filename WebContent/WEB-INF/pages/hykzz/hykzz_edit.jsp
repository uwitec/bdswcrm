<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Hykzz hykzz = (Hykzz)VS.findValue("hykzz");
List hykflList = (List)VS.findValue("hykflList");
%>

<html>
<head>
<title>会员卡制作</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript" src="xhEditor/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript" charset="UTF-8"  src="xhEditor/xheditor.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	
	function saveInfo(){
		if(document.getElementById("dept").value == ""){
			alert("制卡单位不能为空，请填写！");
			return;
		}
	   
	   		
		if(document.getElementById("ssfl").value == ""){
			alert("所属分类不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("hykh").value == ""){
			alert("会员卡号不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("yxrq").value == ""){
			alert("有效日期不能为空，请选择！");
			return;
		}
		
		if(document.getElementById("sxrq").value == ""){
			alert("失效日期不能为空，请选择！");
			return;
		}
		document.myform.submit();
	}	
	
</script>
</head>
<body >
<form name="myform" action="updateHykzz.html" method="post">
<input type="hidden" name="hykzz.id" id="id" value="<%=StringUtils.nullToStr(hykzz.getId()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">会员卡制作</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">制卡单位</td>
		<td class="a2" width="85%">
		   <input type="text" name="hykzz.dept" id="dept" value="<%=StringUtils.nullToStr(hykzz.getDept()) %>" size="45"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1"  width="15%">卡类型</td>
	    <td class="a2" width="85%"  style="width:80%">
	        <input type="radio"  name="hykzz.card_type" value="条码卡" <%if(StringUtils.nullToStr(hykzz.getCard_type()).equals("条码卡")) out.print("checked"); %>>条码卡
	        <input type="radio"  name="hykzz.card_type" value="磁卡" <%if(StringUtils.nullToStr(hykzz.getCard_type()).equals("磁卡")) out.print("checked"); %>>磁卡
	        <input type="radio"  name="hykzz.card_type" value="IC卡" <%if(StringUtils.nullToStr(hykzz.getCard_type()).equals("IC卡")) out.print("checked"); %>>IC卡
	         <font color="red">*</font>
        </td>
	</tr>
	<tr>
		<td class="a1" width="15%">所属分类</td>
		<td class="a2" width="85%">
		  <select name="hykzz.ssfl" id="ssfl" style="width:80%">
				<option value=""></option>
				<%
				if(hykflList != null && hykflList.size()>0){
					for(int i=0;i<hykflList.size();i++){
						Hykfl hykfl = (Hykfl)hykflList.get(i);
				%>
				<option value="<%=StringUtils.nullToStr(hykfl.getId()) %>" <%if(hykfl.getId().equals(hykzz.getSsfl())) out.print("selected"); %>><%=StringUtils.nullToStr(hykfl.getName()) %></option>
				<%
					}
				}
				%>
			</select><font color="red">*</font>
		</td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">会员卡号</td>
		<td class="a2" width="85%">
		   <input type="text" name="hykzz.hykh" id="hykh" value="<%=StringUtils.nullToStr(hykzz.getHykh())%>" size="45" maxlength="50"><font color="red">*</font>
		</td>
	</tr>
	 
	<tr>
		<td class="a1" width="15%">有效日期</td>
		<td class="a2" width="85%">
		   <input type="text" name="hykzz.yxrq" id="yxrq" value="<%=StringUtils.nullToStr(hykzz.getYxrq()) %>"  class="Wdate" onFocus="WdatePicker()" size="45"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1"  width="15%">失效日期</td>
	    <td class="a2" width="85%">
	       <input type="text" name="hykzz.sxrq" id="sxrq" value="<%=StringUtils.nullToStr(hykzz.getSxrq()) %>"  class="Wdate" onFocus="WdatePicker()" size="45"><font color="red">*</font>
        </td>
	</tr>
	<tr>
		<td class="a1" width="15%">初始积分</td>
		<td class="a2" width="85%">
		   <input type="text" name="hykzz.csjf" id="csjf" value="<%=StringUtils.nullToStr(hykzz.getCsjf()) %>"  size="45">
		</td>
	</tr>
	<tr>
		<td class="a1"  width="15%">初始密码</td>
	    <td class="a2" width="85%">
	       <input type="text" name="hykzz.csmm" id="csmm" value="<%=StringUtils.nullToStr(hykzz.getCsmm()) %>"  size="45">
        </td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
