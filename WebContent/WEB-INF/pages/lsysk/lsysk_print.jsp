<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Lsysk lsysk = (Lsysk)VS.findValue("lsysk");

String ys_date = lsysk.getYs_date();

if(ys_date.length()>=10){
	ys_date = ys_date.substring(0,4) + "年" + ys_date.substring(5,7) + "月" + ys_date.substring(8,10) + "日";
}
%>

<html>
<head>
<title>打印收据</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style media=print>  
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style> 
<script type="text/javascript">
function printIframe(){
	parent.focus();
	parent.print();
}
</script>
</head>
<body oncontextmenu="return false;" >
<center>
<table width="660" border="0">
	<tr>
	    <td align="left">&nbsp;</td>
	    <td height="21" colspan="2">
	    	<br>
	    	<P CLASS="cjk" ALIGN=CENTER STYLE="text-indent: 0cm; margin-bottom: 0.28cm; text-align: center"><FONT FACE="宋体"><FONT STYLE="font-size: 26pt"><B>收 据</B></FONT></FONT></P>
	    	<BR>
	    	<P CLASS="cjk" ALIGN=CENTER STYLE="text-indent: 0cm; margin-bottom: 0.28cm; text-align: center"><FONT FACE="宋体"><FONT STYLE="font-size: 18pt"><%=StringUtils.nullToStr(lsysk.getId()) %></FONT></FONT></P>
	    	<BR>
	    </td>
	    <td align="left">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="4">
			<table width="660" border="1" cellpadding="0" cellspacing="0" style="border:medium solid;border-width:1px;border-collapse:collapse;">
			<tr height="40">
				<td align="center" width="110" nowrap><FONT FACE="宋体" STYLE="font-size: 16pt">客户名称</FONT></td>
			    <td width="275" align="left"><FONT FACE="宋体" STYLE="font-size: 16pt"><%=StringUtils.nullToStr(lsysk.getClient_name()) %></FONT></td>
			    <td align="center" width="110" nowrap><FONT FACE="宋体" STYLE="font-size: 16pt">项　　目</FONT></td>
			    <td width="275" align="left"><FONT FACE="宋体" STYLE="font-size: 16pt">预收货款</FONT></td>
			</tr>
			<tr height="40">
				<td align="center" width="110" nowrap><FONT  FACE="宋体" STYLE="font-size: 16pt">金　　额</FONT></td>
			    <td width="275" align="left"><FONT  FACE="宋体" STYLE="font-size: 16pt"><%=JMath.round(lsysk.getYsje(),2) %></FONT></td>
			    <td align="center" width="100" nowrap><FONT  FACE="宋体" STYLE="font-size: 16pt">收款方式</FONT></td>
			    <td width="275" align="left"><FONT  FACE="宋体" STYLE="font-size: 16pt"><%=StringUtils.nullToStr(lsysk.getFkfs()) %></FONT></td>
			</tr>
			<tr height="40">
				<td align="center" width="110" nowrap><FONT  FACE="宋体" STYLE="font-size: 16pt">大写金额</FONT></td>
			    <td colspan="3"><FONT  FACE="宋体" STYLE="font-size: 16pt"><%=MoneyUtil.toChinese(JMath.round(lsysk.getYsje())) %></FONT></td>
			</tr>			
			</table>
		</td>
	</tr>
	<tr>
	    <td colspan="4">
	    	<br><br>
			<table width="660" border="0">
			<tr height="40">
				<td width="400">&nbsp;</td>
				<td width="260">	    	
			    	<P CLASS="cjk" STYLE="text-indent: 0cm; margin-bottom: 0.28cm; text-align: center"><FONT FACE="宋体"><FONT STYLE="font-size: 16pt">经手人：<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(lsysk.getJsr())) %></FONT></FONT></P>
			    	<P CLASS="cjk" STYLE="text-indent: 0cm; margin-bottom: 0.28cm; text-align: center"><FONT FACE="宋体"><FONT STYLE="font-size: 16pt"><%=ys_date %></FONT></FONT></P>
			    </td>
			</tr>
			</table>
	    </td>
	</tr>  
</table>
</center>
<BR>
<center class="Noprint">
<input type=button class="css_button2" value=' 打印 ' onclick="printIframe();">
<input type=button class="css_button2" value=' 关闭 ' onclick="window.close();">
</center>
</body>
</html>
