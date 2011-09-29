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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>会员卡制作</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/validateform.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if (doValidate(myform)){	
			document.myform.submit();
		}
	}	
	
</script>
</head>
<body>
<form name="myform" action="updateHykzz.html" method="post">
<input type="hidden" name="hykzz.id" id="id" value="<%=StringUtils.nullToStr(hykzz.getId()) %>">
<input type="hidden" name="hykzz.state" id="state" value="<%=StringUtils.nullToStr(hykzz.getState()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="2">会员卡制作</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">制卡单位</td>
		<td class="a2" width="75%">
		   <input type="text" name="hykzz.dept" id="dept" value="<%=StringUtils.nullToStr(hykzz.getDept()) %>" style="width:200px" maxlength="25" notNull='true' vdisp='制卡单位'  vtype='string'><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1">卡类型</td>
	    <td class="a2">
	        <input type="radio"  name="hykzz.card_type" value="条码卡" <%if(StringUtils.nullToStr(hykzz.getCard_type()).equals("条码卡")) out.print("checked"); %>>条码卡
	        <input type="radio"  name="hykzz.card_type" value="磁卡" <%if(StringUtils.nullToStr(hykzz.getCard_type()).equals("磁卡")) out.print("checked"); %>>磁卡
	        <input type="radio"  name="hykzz.card_type" value="IC卡" <%if(StringUtils.nullToStr(hykzz.getCard_type()).equals("IC卡")) out.print("checked"); %>>IC卡
	         <font color="red">*</font>
        </td>
	</tr>
	<tr>
		<td class="a1">所属分类</td>
		<td class="a2">
		  <select name="hykzz.ssfl" id="ssfl" style="width:200px" notNull='true' vdisp='所属分类'  vtype='string'>
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
		<td class="a1">会员卡号</td>
		<td class="a2">
		   <input type="text" name="hykzz.hykh" id="hykh" value="<%=StringUtils.nullToStr(hykzz.getHykh())%>" style="width:200px" notNull='true' vdisp='会员卡号'  vtype='notsstring' maxlength="50" readonly><font color="red">*</font>
		</td>
	</tr>
	 
	<tr>
		<td class="a1">有效日期</td>
		<td class="a2">
		   <input type="text" name="hykzz.yxrq" id="yxrq" value="<%=StringUtils.nullToStr(hykzz.getYxrq()) %>"  class="Wdate" onFocus="WdatePicker()" style="width:200px"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1">失效日期</td>
	    <td class="a2">
	       <input type="text" name="hykzz.sxrq" id="sxrq" value="<%=StringUtils.nullToStr(hykzz.getSxrq()) %>"  class="Wdate" onFocus="WdatePicker()" style="width:200px"><font color="red">*</font>
        </td>
	</tr>
	<tr>
		<td class="a1">初始积分</td>
		<td class="a2">
		   <input type="text" name="hykzz.csjf" id="csjf" value="<%=StringUtils.nullToStr(hykzz.getCsjf()) %>" style="width:200px" notNull='false' vdisp='初始积分'  vtype='int' maxlength="9">
		</td>
	</tr>
	<tr>
		<td class="a1" >初始密码</td>
	    <td class="a2">
	       <input type="text" name="hykzz.csmm" id="csmm" value="<%=StringUtils.nullToStr(hykzz.getCsmm()) %>" style="width:200px" notNull='false' vdisp='初始密码'  vtype='string' maxlength="20">
        </td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提交" class="css_button" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value="重置" class="css_button">&nbsp;
			<input type="button" name="button3" value="关闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
