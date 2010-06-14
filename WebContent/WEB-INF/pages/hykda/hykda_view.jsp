<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Map hykdas = (Map)VS.findValue("hykdas");

%>

<html>
<head>
<title>会员卡档案</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
</script>
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">卡信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">会员卡号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("hykh")) %>
		</td>
	
		<td class="a1" width="15%">卡类型</td>
	    <td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("card_type")) %>
        </td>
	</tr>
	<tr>
		<td class="a1" width="15%">所属分类</td>
		<td class="a2" width="35%"><%=StaticParamDo.getHykflNameById(StringUtils.nullToStr(hykdas.get("ssfl"))) %>
		</td>
	
		<td class="a1" width="15%">初始积分</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("csjf")) %>
		</td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">有效日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("yxrq")) %>
		</td>
	
		<td class="a1"  width="15%">失效日期</td>
	    <td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("sxrq")) %>
        </td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">领用情况</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("state")) %>		   
		</td>
	
		<td class="a1"  width="15%">是否停用</td>
	    <td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("sfty")) %>	
        </td>
	</tr> 
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">会员信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">会员姓名</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("hymc")) %>
		</td>
	
		<td class="a1" width="15%">联系人</td>
	    <td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("lxrname")) %>
        </td>
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("lxdh")) %>
		</td>
	
		<td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("mobile")) %>
		</td>
	</tr>
	 
	<tr>
		<td class="a1" width="15%">身份证号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("sfzh")) %>
		</td>
	
		<td class="a1"  width="15%">E-Mail</td>
	    <td class="a2" width="35%"><%=StringUtils.nullToStr(hykdas.get("mail")) %>
        </td>
	</tr>	
	<tr>
		<td class="a1" width="15%">地址</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(hykdas.get("address")) %>
		</td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
