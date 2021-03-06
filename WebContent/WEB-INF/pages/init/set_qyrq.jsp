<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统初始化导航</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function setQyrq(){
		var curDate = "<ww:property value="%{sysInitSet.qyrq}"/>";
		if(document.getElementById("qyrq").value == ""){
			alert("系统启用日期不能为空，请选择！");
			return;
		}

		if(curDate == ""){
			var myDate = new Date();
			curDate = myDate.Format("yyyy-MM-dd");     //获取当前日期
		}
		if(document.getElementById("qyrq").value > curDate){
			alert("系统启用日期必须为当前日期及当前日期之前的日期！");
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
		if(window.confirm("确认要清空系统全部数据吗？，清空后将不可还原！")){
			document.myform.action = "clearSysData.html";
			document.myform.submit();
		}
	}

	function clearYwData(){
		if(window.confirm("确认要清空系统业务数据吗？，清空后将不可还原！")){
			document.myform.action = "clearSysYwData.html";
			document.myform.submit();
		}
	}	

	Date.prototype.Format = function(formatStr)   
	{   
	    var str = formatStr;   
	    var Week = ['日','一','二','三','四','五','六'];  
	  
	    str=str.replace(/yyyy|YYYY/,this.getFullYear());   
	    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));   
	  
	    str=str.replace(/MM/,(this.getMonth()+1)>=10?(this.getMonth()+1).toString():'0' + (this.getMonth() + 1));   
	    str=str.replace(/M/g,this.getMonth());   
	  
	    str=str.replace(/w|W/g,Week[this.getDay()]);   
	  
	    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
	    str=str.replace(/d|D/g,this.getDate());   
	  
	    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
	    str=str.replace(/h|H/g,this.getHours());   
	    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
	    str=str.replace(/m/g,this.getMinutes());   
	  
	    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
	    str=str.replace(/s|S/g,this.getSeconds());   
	  
	    return str;   
	}   	
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="setQyrq.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>系统初始化导航</b> <font color="red">(初始化必须按照以下步骤进行)</font> </td>			
	</tr>
</table>
<FONT color="red"><ww:property value="%{msg}"/></FONT>
<table width="100%"  align="center"  border="0"   class="chart_info" cellpadding="0" cellspacing="0"><!--
		<tr>
			<td class="a2"><b>第一步</b>、首先清空系统历史数据，清空历史数据包括两类,1.清空系统全部数据请点击“清空系统全部数据”链接；2.只清空系统业务数据，请点击“清空系统业务数据”；请根据情况慎重选择，操作不可逆。&nbsp;
				
				<a href='#' onclick="clearData();">清空系统全部数据</a>&nbsp;&nbsp;
				<a href='#' onclick="clearYwData();">清空系统业务数据</a>
			</td>			
		</tr>
		-->
		<tr>
			<td class="a2" style="text-indent:0pt"><div style="float: left;width: 350px"><b>第一步</b>、设置系统运行参数，点击各按钮设置相关系统参数。&nbsp;</div>
			<div style="float: left;">
				<input type="button" name="buttonQd" value="系统LOGO设置" onclick="parent.addtabFmMenu('系统LOGO设置','editLogo.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="单据打印设置" onclick="parent.addtabFmMenu('单据打印设置','editReportSet.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="系统字典维护" onclick="parent.addtabFmMenu('系统字典维护','listJbxx.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="刷卡POS机设定" onclick="parent.addtabFmMenu('刷卡POS机设定','listPos.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="设置零售税点" onclick="parent.addtabFmMenu('设置零售税点','editLssd.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="设置采购税点" onclick="parent.addtabFmMenu('设置采购税点','editCgsd.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="提成比例设置" onclick="parent.addtabFmMenu('提成比例设置','editTcbl.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="强制序列号设置" onclick="parent.addtabFmMenu('强制序列号设置','openQzxlhRight.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="报表样式设置" onclick="parent.addtabFmMenu('报表样式设置','openStyle.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="费用申请审批设置" onclick="parent.addtabFmMenu('费用申请审批设置','openFysqSpRight.html','');" class="css_button4">
				
				<input type="button" name="buttonQd" value="费用申请审批设置" onclick="parent.addtabFmMenu('费用申请审批设置','openFysqSpRight.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="采购付款审批设置" onclick="parent.addtabFmMenu('采购付款审批设置','openCgfkSpRight.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="销售单超额审批设置" onclick="parent.addtabFmMenu('销售单超额审批设置','openXsdSpRightRoles.html','');" class="css_button4">
				
				<input type="button" name="buttonQd" value="价格审批设置" onclick="parent.addtabFmMenu('价格审批设置','openJgSpRightRoles.html','');" class="css_button4">
				<input type="button" name="buttonQd" value="超期审批设置" onclick="parent.addtabFmMenu('超期审批设置','openCqSpRightRoles.html','');" class="css_button4">
</div>				
			</td>			
		</tr>		
		<tr>
			<td class="a2"><b>第二步</b>、维护商品基础数据，包括商品分类及商品资料，要维护商品基础数请点击“商品维护”。&nbsp;<input type="button" name="buttonQd" value="商品维护" onclick="parent.addtabFmMenu('商品维护','product.html','');" class="css_button3"></td>			
		</tr>
		<tr>
			<td class="a2"><b>第三步</b>、录入员工信息，要录入员工信息请点击“员工管理”。&nbsp;
			<input type="button" name="buttonQd" value="员工管理" onclick="parent.addtabFmMenu('员工管理','showEmployeeFrame.html','');" class="css_button3">
			</input></td>
		</tr>

		<tr>
			<td class="a2"><b>第四步</b>、录入仓房资料，要录入仓房资料信息请点击“仓房资料”。&nbsp;
			<input type="button" name="buttonQd" value="仓房资料" onclick="parent.addtabFmMenu('仓房资料','listStore.html','');" class="css_button3">
			</input></td>
		</tr>
		<tr>
			<td class="a2"><b>第五步</b>、录入账户资料，要录入账户资料请点击“账户资料”。&nbsp;
			<input type="button" name="buttonQd" value="账户资料" onclick="parent.addtabFmMenu('账户资料','listAccount.html','');" class="css_button3">
			</input></td>
		</tr>

		<tr>
			<td class="a2"><b>第六步</b>、维护往来单位及相应联系人信息，要维护往来单位信息请点击“往来单位”。&nbsp;
			<input type="button" name="buttonQd" value="往来单位" onclick="parent.addtabFmMenu('往来单位','listClient.html','');" class="css_button3">
			</input></td>
		</tr>
		<tr>
			<td class="a2"><b>第七步</b>、初始账户金额，初始账号金额请点击“账户初始”。&nbsp;
			<input type="button" name="buttonQd" value="账号初始" onclick="parent.addtabFmMenu('账号初始','initAccount.html','');" class="css_button3">
			</input></td>			
		</tr>		
		<tr>
			<td class="a2">
				<b>第八步</b>、设置系统启用日期，选择启用日期后点击“确定”按钮设置启用日期。启用日期设置后不能再修改。设置启用日期后系统即可正式启用，可以录入业务单据，在此之前录入的业务单据无效（部分报表需到完成所有初始化工作后方可使用）。&nbsp;&nbsp;
				<input type="text" name="sysInitSet.qyrq" id="qyrq" value="<ww:property value="%{sysInitSet.qyrq}"/>" class="Wdate" onFocus="WdatePicker()"/>
				&nbsp;&nbsp;&nbsp;
				<ww:if test="sysInitSet.qyrq == null">
				<input type="button" name="buttonQd" value=" 确定 " onclick="setQyrq();" class="css_button"></input>
				</ww:if>
			</td>
		</tr>		
		<tr>
			<td class="a2"><b>第九步</b>、往来初始，要维护往来初始信息请点击“往来初始”链接。&nbsp;
			<input type="button" name="buttonQd" value="往来初始" onclick="parent.addtabFmMenu('往来初始','listClientWlInit.html','');" class="css_button3">
			</input></td>				
		</tr>
		<tr>
			<td class="a2"><b>第十步</b>、初始化商品库存，要初始化商品库存信息请点击“库存初始”，库存初始可以在系统启用后同步进行。&nbsp;
			<input type="button" name="buttonQd" value="库存初始" onclick="parent.addtabFmMenu('库存初始','listInit.html','');" class="css_button3">
			</input></td>				
		</tr>
		<tr>
			<td class="a2"><b>第十一步</b>、完成所有初始化工作，要完成系统初始工作请点击“完成初始化”。&nbsp;
			<ww:if test="flag == 0">
			<input type="button" name="buttonQd" value="完成初始化" onclick="setCswc();" class="css_button3"></input>
			</ww:if>
			<ww:else>
				<font color="red">系统已完成初始化</font>
			</ww:else>
			</td>	
		</tr>
</table>
</form>
<font color="red">注：系统的初始工作必须按照初始导航所描述的步骤进行，在完成第八步“设置系统启用日期”后，系统即可正始使用，录入业务单据。往来初始、库存初始可以和使用同时进行，所有的初始工作完成后，请点击第十一步的“完成初始化”，完成系统的初始化工作，系统初始化工作完成后所有报表统计均可正常使用。</font>
<iframe name="hiddenFrm" width="0" height="0"/>
</div>
</body>
</html>