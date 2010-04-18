<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Cgfpd cgfpd=(Cgfpd)VS.findValue("cgfpd");
List cgfpDescs = (List)VS.findValue("cgfpDescs");

int counts=2;
if(cgfpDescs != null && cgfpDescs.size()>0){
	counts = cgfpDescs.size()-1;
}

String msg = StringUtils.nullToStr(VS.findValue("msg"));
%>

<html>
<head>
<title>采购发票</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	var allCount = <%=counts %>;
	var msg = "<%=msg %>";
	
	function saveInfo(){
		
		  	if(window.confirm("确认入库吗，入库后将不可修改！")){
				document.cgfpForm.submit();
			}else{
				return;
			}
	
		
		document.cgfpForm.btnSub.disabled = true;
	}
	
	function setFpstate()
	{
	    if(allCount==0)
	    {
	      var fpstate = document.getElementById("state_0");
		  var o = document.cgfpForm.proc_id;
		  if(o.checked){
		     fpstate.value = "已入库";
		   }
		  else
		  {
		    fpstate.value = "未入库";
		  }
	    }
	    else
	    {
	       for(var y=0;y<=allCount;y++)
		  	{
			  var fpstate = document.getElementById("state_"+y);
			  var o = document.cgfpForm.proc_id[y];
			  if(o.checked){
			     fpstate.value = "已入库";
			   }
			  else
			  {
			    fpstate.value = "未入库";
			  }
			}
		}
	}
		
</script>
</head>
<body>
<form name="cgfpForm" action="updateCgfp.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购发票</td>
	</tr>
	</thead>
	<tr>	
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%">
		<input type="text" name="cgfpd.gysmc" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(cgfpd.getGysbh())) %>" size="35" readonly>
		<input type="hidden" name="cgfpd.gysbh" id="client_id" value="<%=StringUtils.nullToStr(cgfpd.getGysbh()) %>">
		</td>			
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">采购发票明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="25%">发生日期</td>
		<td width="15%">进货单编号</td>
		<td width="25%">发生金额</td>
		<td width="15%">状态</td>
		<td width="10%">选择</td>
	</tr>
	</thead>
<%

if(cgfpDescs != null && cgfpDescs.size()>0){
	for(int i=0;i<cgfpDescs.size();i++){
	Map map = (Map)cgfpDescs.get(i);	
	double total = map.get("total")==null?0:((Double)map.get("total")).doubleValue();	
%>
	<tr>		
		<td class="a2"><input type="text" size="10" id="cg_date_<%=i %>" name="cgfpDescs[<%=i %>].cg_date" value="<%=StringUtils.nullToStr(map.get("cg_date")) %>" style="width:100%" readonly></td>
		<td class="a2"><input type="text" id="jhd_id_<%=i %>" name="cgfpDescs[<%=i %>].jhd_id" value="<%=StringUtils.nullToStr(map.get("jhd_id")) %>" style="width:100%" readonly></td>
		<td class="a2">
		    <input type="text" size="10" id="total_<%=i %>" name="cgfpDescs[<%=i %>].total" value="<%=JMath.round(total) %>" style="width:100%" readonly>
		</td>   
		<td class="a2">
		    <input type="text" id="state_<%=i %>" name="cgfpDescs[<%=i %>].state" value="<%=StringUtils.nullToStr(map.get("state")) %>" readonly="readonly">
	    </td>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>" onclick="setFpstate();"></td>
	</tr>
<%		
	}
}
%>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSub" value="入 库" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
</form>
</body>
</html>