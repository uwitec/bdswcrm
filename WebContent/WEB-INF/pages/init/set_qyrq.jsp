<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>系统初始化导航</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function setQyrq(){
		if(document.getElementById("qyrq").value == ""){
			alert("系统启用日期不能为空，请选择！");
			return;
		}
		if(window.confirm("设置系统启用日期，确认吗？")){
			document.myform.action = "setQyrq.html";
			document.myform.submit();
		}
	}
	
	function setCswc(){
		if(window.confirm("确认要完成初始化工作吗？")){
			document.myform.action = "setWccs.html";
			document.myform.submit();		
		}
	}

	function clearData(){
		if(window.confirm("确认要清空系统数据吗？")){
			document.myform.action = "clearSysData.html";
			document.myform.submit();
		}
	}
</script>
</head>
<body>
<form name="myform" action="setQyrq.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>系统初始化导航</b></td>			
	</tr>
</table>
<FONT color="red"><ww:property value="%{msg}"/></FONT>
<table width="100%"  align="center"  border="1"   class="chart_info" cellpadding="0" cellspacing="0">
		<tr>
			<td class="a2"><b>第一步</b>、首先清空系统历史数据，清空历史数据请点击“清空系统数据”链接。&nbsp;<a href='#' onclick="clearData();">清空系统数据</a></td>			
		</tr>
		<tr>
			<td class="a2"><b>第二步</b>、维护产品基础数据，包括产品分类及产品资料，要维护产品基础数请点击“产品维护”链接。&nbsp;<a href="javascript:void(0);" onclick="parent.addtabFmMenu('产品维护','product.html','');">产品维护</a></td>			
		</tr>
		<tr>
			<td class="a2"><b>第三步</b>、维护往来单位信息，要维护往来单位信息请点击“往来单位”链接。&nbsp;<a href="javascript:void(0);" onclick="parent.addtabFmMenu('往来单位','listClient.html','');">往来单位</a></td>			
		</tr>

		<tr>
			<td class="a2"><b>第四步</b>、录入仓房资料，要录入仓房资料信息请点击“仓房资料”链接。&nbsp;<a href="javascript:void(0);" onclick="parent.addtabFmMenu('仓房资料','listStore.html','');">仓房资料</a></td>			
		</tr>
		<tr>
			<td class="a2"><b>第五步</b>、录入账户资料，要录入账户资料请点击“账户资料”链接。&nbsp;<a href="javascript:void(0);" onclick="parent.addtabFmMenu('账户资料','listAccount.html','');">账户资料</a></td>			
		</tr>

		<tr>
			<td class="a2"><b>第六步</b>、录入员工信息，要录入员工信息请点击“员工管理”链接。&nbsp;<a href="javascript:void(0);" onclick="parent.addtabFmMenu('员工管理','showEmployeeFrame.html','');">员工管理</a></td>			
		</tr>
		<tr>
			<td class="a2"><b>第七步</b>、初始账户金额，初始账号金额请点击“账户初始”链接。&nbsp;<a href="javascript:void(0);" onclick="parent.addtabFmMenu('账号初始','initAccount.html','');">账户初始</a></td>			
		</tr>		
		<tr>
			<td class="a2">
				<b>第八步</b>、设置系统启用日期，选择启用日期后点击“确定”按钮设置启用日期。启用日期设置后不能再修改。设置启用日期后系统即可正式启用。&nbsp;&nbsp;
				<ww:textfield name="sysInitSet.qyrq" id="qyrq" theme="simple" value="%{sysInitSet.qyrq}" readonly="true" />
				<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('qyrq')); return false;">
				&nbsp;&nbsp;&nbsp;
				<ww:if test="sysInitSet.qyrq == null">
				<input type="button" name="buttonQd" value=" 确定 " onclick="setQyrq();" class="css_button">
				</ww:if>
			</td>
		</tr>		
		<tr>
			<td class="a2"><b>第九步</b>、往来初始，要维护往来初始信息请点击“往来初始”链接。&nbsp;<a href="javascript:void(0);" onclick="parent.addtabFmMenu('往来初始','listClientWlInit.html','');">往来初始</a></td>			
		</tr>
		<tr>
			<td class="a2"><b>第十步</b>、初始化产品库存，要初始化产品库存信息请点击“库存初始”链接，库存初始可以与系统启用同步进行。&nbsp;<a href="javascript:void(0);" onclick="parent.addtabFmMenu('库存初始','listInit.html','');">库存初始</a></td>			
		</tr>
		<tr>
			<td class="a2"><b>第十一步</b>、完成所有初始化工作，要完成系统初始工作请点击“完成初始化”链接。&nbsp;
			<ww:if test="flag == 0">
			<a href="#" onclick="setCswc();">完成初始化</a>
			</ww:if>
			</td>	
		</tr>
</table>
</form>
注：系统的初始工作必须按照初始导航所描述的步骤进行，在完成第八步“设置系统启用日期”后，系统即可正始使用，往来初始、库存初始可以和使用同时进行，所有的初始工作完成后，请点击第十一步的“完成初始化”链接，完成系统的初始化工作。
<iframe name="hiddenFrm" width="0" height="0"/>
</body>
</html>