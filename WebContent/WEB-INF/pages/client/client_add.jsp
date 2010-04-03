<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String[] wldwlx = (String[])VS.findValue("wldwlx");
List userList = (List)VS.findValue("userList");
String msg = StringUtils.nullToStr(session.getAttribute("MSG"));
session.removeAttribute("MSG");
%>
<html>
<head>
<title>往来单位</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	var allCount = 2;

	function saveInfo(){
		if(document.getElementById("name").value==""){
	        alert("往来单位不能为空！");
	        return;
	    }		
		if(document.getElementById("client_type").value == ""){
			alert("单位类型不能为空，请选择！");
			return;
		}		
		if(document.getElementById("fzr").value == ""){
			alert("客户经理不能为空，请选择！");
			return;
		}		
		
		if(!InputValid(document.getElementById("zq"),0,"int",0,0,999,"帐期")){	 return; }
		if(!InputValid(document.getElementById("xe"),0,"float",0,0,999999999,"限额")){	 return; }	
		//if(!InputValid(document.getElementById("cg_zq"),0,"int",0,0,999,"采购帐期")){	 return; }
		//if(!InputValid(document.getElementById("cg_xe"),0,"float",0,0,999999999,"采购限额")){	 return; }			
		
		if(document.getElementById("linkmanname").value == ""){
			alert("联系人姓名不能为空 ！");
			return;
		}			
		
		document.clientForm.submit();
		
	}

    function addTr(){
        var otr = document.getElementById("payTable").insertRow(-1); 
        
        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="client_all_name_'+curId+'" name="clientsPayInfos['+curId+'].client_all_name" style="width:100%" maxlength="100">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="bank_no_'+curId+'"  name="clientsPayInfos['+curId+'].bank_no" style="width:100%" maxlength="50">';
		
        otr.appendChild(otd0); 
        otr.appendChild(otd1);              
     }			
</script>
</head>
<body onload="initFzrTip();">
<form name="clientForm" action="saveClient.html" method="post">
<center>
<font color="red" style="font-size:16px;"><%=msg %></font>
</center>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">往来单位</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">单位名称</td>
		<td class="a2" width="35%"><input type="text" name="client.name" id="name" value="" style="width:85%"><font color="red">*</font></td>
		<td class="a1" width="15%">单位类型</td>
		<td class="a2" width="35%">
			<select name="client.client_type" id="client_type" style="width:85%">
				<option value=""></option>
				<%
				if(wldwlx != null && wldwlx.length > 0){ 
					for(int i=0;i<wldwlx.length;i++){
				%>
				<option value="<%=wldwlx[i] %>"><%=wldwlx[i] %></option>
				<%
					}
				}
				%>
			</select>
			<font color="red">*</font>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><input type="text" name="client.address" id="address" value="" style="width:85%" maxlength="50"></td>		
		<td class="a1" width="15%">固定电话</td>
		<td class="a2" width="35%"><input type="text" name="client.gzdh" id="gzdh" value="" style="width:85%" maxlength="20"></td>
	
	</tr>
	<tr>
		<td class="a1" width="15%">传真</td>
		<td class="a2" width="35%"><input type="text" name="client.cz" id="cz" value="" maxlength="20" style="width:85%"></td>	
		<td class="a1" width="15%">客户经理</td>
		<td class="a2" width="35%">
		    <input  id="brand"    type="text"   length="20"  onblur="setValue()"  style="width:85%"/> 
            <div   id="brandTip"  style="height:12px;position:absolute;left:514px; top:141px;  width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="client.khjl" id="fzr"  /> <font color="red">*</font>
		</td>
	</tr>
	 	
	<tr>
		<td class="a1" width="15%">账期</td>
		<td class="a2" width="35%"><input type="text" name="client.zq" id="zq" value="0" style="width:85%"> 天</td>
		<td class="a1" width="15%">限额</td>
		<td class="a2" width="35%"><input type="text" name="client.xe" id="xe" value="0.00" style="width:85%">元</td>
	</tr><!--
	<tr>
		<td class="a1" width="15%">采购账期</td>
		<td class="a2" width="35%"><input type="text" name="client.cg_zq" id="cg_zq" value="0" size="5"> 天</td>
		<td class="a1" width="15%">采购限额</td>
		<td class="a2" width="35%"><input type="text" name="client.cg_xe" id="cg_xe" value="0.00">元</td>
	</tr>	
	--><tr height="50">
		<td class="a1">备注</td>
		<td class="a2" colspan="3"><input type="text" name="client.remark" id="remark" style="width:85%" maxlength="500"></textarea>
		</td>
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">开票信息</td>
	</tr>
	</thead>
	<tr> 
	     
		<td class="a1" width="15%">单位全称</td>
		<td class="a2" width="35%"><input type="text" name="client.kp_name" id="kp_name" value="" style="width:85%" maxlength="100"></td>
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><input type="text" name="client.kp_address" id="kp_address" value="" style="width:85%" maxlength="100"></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">电话</td>
		<td class="a2" width="35%"><input type="text" name="client.kp_tel" id="kp_tel" value="" maxlength="20" style="width:85%"></td>
		<td class="a1" width="15%">税号</td>
		<td class="a2" width="35%"><input type="text" name="client.kp_sh" id="kp_sh" value="" maxlength="50" style="width:85%"></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">开户行帐号</td>
		<td class="a2" colspan="3"><input type="text" name="client.kp_khhzh" id="kp_khhzh" value="" maxlength="50" style="width:85%"></td>		
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">供应商办款信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="payTable" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="50%">单位全称</td>
		<td width="50%">开户行账号</td>
	</tr>
	</thead>	
	<%
	for(int i=0;i<3;i++){
	%>
	<tr>
		<td width="50%" class="a2"><input type="text" id="client_all_name_<%=i %>" name="clientsPayInfos[<%=i %>].client_all_name" style="width:100%" maxlength="100"></td>
		<td width="50%" class="a2"><input type="text" id="bank_no_<%=i %>"  name="clientsPayInfos[<%=i %>].bank_no" style="width:100%" maxlength="50"></td>
	</tr>	
	<%
	}
	%>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加一行" class="css_button2" onclick="addTr();">
		</td>
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">联系人</td>
	</tr>
	</thead>
	<tr> 
	     
		<td class="a1" width="15%">姓名</td>
		<td class="a2" width="35%"><input type="text" name="linkman.name" id="linkmanname" value="" style="width:85%"><font color="red">*</font></td>
		<td class="a1" width="15%">类型</td>
		<td class="a2" width="35%">
			<select name="linkman.lx" id="lx" style="width:85%">
				<option value="主联系人">主联系人</option>
				<option value="联系人">联系人</option>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">固定电话</td>
		<td class="a2" width="35%"><input type="text" name="linkman.gzdh" id="gzdh" value="" style="width:85%"></td>
		<td class="a1" width="15%">移动电话</td>
		<td class="a2" width="35%"><input type="text" name="linkman.yddh" id="yddh" value="" style="width:85%"></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">家庭电话</td>
		<td class="a2" width="35%"><input type="text" name="linkman.jtdh" id="jtdh" value="" style="width:85%"></td>	
		<td class="a1" width="15%">其他联系方式</td>
		<td class="a2" width="35%"><input type="text" name="linkman.qtlx" id="qtlx" value="" style="width:85%"></td>	
	</tr>
	<tr>	
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2" width="35%"><input type="text" name="linkman.mail" id="mail" value="" style="width:85%"></td>	
		<td class="a1" width="15%">部门</td>
		<td class="a2" width="35%"><input type="text" name="linkman.dept" id="dept" value="" style="width:85%"></td>
		
	</tr>
	<tr>
		<td class="a1" width="15%">性别</td>
		<td class="a2" width="35%">
          <select name="linkman.sex" id="sex" style="width:85%">
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
        </td>
		<td class="a1" width="15%">职务</td>
		<td class="a2" width="35%"><input type="text" name="linkman.zw" id="zw" value="" style="width:85%"></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">生日</td>
		<td class="a2" width="35%"><input type="text" name="linkman.sr" id="sr" class="Wdate" onFocus="WdatePicker()" style="width:85%">
		</td>	
		<td class="a1" width="15%">爱好</td>
		<td class="a2" width="35%"><input type="text" name="linkman.ah" id="ah" style="width:85%"></td>	
	</tr>			 
	<tr height="50">
		<td class="a1">备注</td>
		<td class="a2" colspan="3"><input type="text" name="linkman.remark" id="remark" style="width:85%" maxlength="500"></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>			
</table><BR>
</form>
</body>
</html>