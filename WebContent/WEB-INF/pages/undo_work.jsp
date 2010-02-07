<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

//出库单权限及相关列表
String isCkdRight = (String)VS.findValue("isCkdRight");
List dckList = (List)VS.findValue("dckList");

//入库单权限及相关列表
String isRkdRight = (String)VS.findValue("isRkdRight");
List drkList = (List)VS.findValue("drkList");

//零售单权限及相关列表
String isLsdSpRight = (String)VS.findValue("isLsdSpRight");
List dspLsdList = (List)VS.findValue("dspLsdList");

//销售单权限及相关列表
String isXsdSpRight = (String)VS.findValue("isXsdSpRight");
List dspXsdList = (List)VS.findValue("dspXsdList");

//付款申请权限及相关列表
String isCgfkSpRight = (String)VS.findValue("isCgfkSpRight");
List dspCgfkList = (List)VS.findValue("dspCgfkList");

//费用申请权限及相关列表
String isFysqSpRight = (String)VS.findValue("isFysqSpRight");
List dspFysqList = (List)VS.findValue("dspFysqList");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待办工作</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.inner{
	width:500px;
	margin:5px; 
	border:1px solid #B8B8B8;
	text-align:left;
	display:inline ;
	float:left;
}
</style>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function edit(id){
		var destination = "editCkd.html?ckd_id=" + id;
		var fea ='width=900,height=700,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'出库单',fea);		
	}	
	
	function editRkd(id){
		var destination = "editRkd.html?rkd_id=" + id;
		var fea ='width=900,height=700,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'入库单',fea);			
	}
	
	function doSpLsd(id){
		var destination = "spLsd.html?id=" + id;
		var fea ='width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'审批零售单',fea);
	}
	
	function doSpXsd(id){
		var destination = "spXsd.html?id=" + id;
		var fea ='width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'审批销售订单',fea);
	}	
	
	function doSpCgfk(id){
		var destination = "spCgfk.html?id=" + id;
		var fea ='width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'审批付款申请单',fea);
	}		
	
	function doSpFysq(id){
		var destination = "spFysq.html?id=" + id;
		var fea ='width=650,height=400,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'审批费用申请',fea);
	}		

	function refreshPage(){
		document.myform.action = "undoWork.html";
		document.myform.submit();
	}	
</script>
</head>
<body>
<form name="myform" action="undoWork.html" method="post">
<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="100%" align="center">
		
			<%
			if(isCkdRight.equals("1")){
			%>		
			<div class="inner">
				<table width="100%" align="left" cellpadding="0" cellspacing="0">
					<tr>
						<td height="27" style="background-image:url(images/head_top2bg.gif)" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待出库单据</b></td>								
					</tr>
					<tr><td height="5"></td></tr>
					<%
					if(dckList!=null && dckList.size()>0){
						for(int i=0;i<dckList.size();i++){
							Map info = (Map)dckList.get(i);
							
							String id = StringUtils.nullToStr(info.get("ckd_id"));
							String client_name = StaticParamDo.getClientNameById((String)info.get("client_name"));
							String creatdate = StringUtils.nullToStr(info.get("creatdate"));
							String xsry = StaticParamDo.getRealNameById((String)info.get("xsry"));
					%>				
					<tr>
						<td width="100%" height="23">&nbsp;
							<A class=xxlb href="#" onclick="edit('<%=id %>');" title="点击查看待出库单据详情"><%=id %>&nbsp;&nbsp;&nbsp;<%=client_name %>&nbsp;&nbsp;&nbsp;<%=xsry %>&nbsp;&nbsp;&nbsp;【<%=creatdate %>】</A>
						</td>
					</tr>
					<%
						}
					}else{
					%>
					<tr>
						<td width="100%" height="23" align="left">&nbsp;无待出库单据！</td>
					</tr>							
					<%
					}
					%>													
				</table>				
			</div>
			<%
			}
			if(isRkdRight.equals("1")){
			%>
			<div class="inner">
				<table width="100%" align="left" cellpadding="0" cellspacing="0">
					<tr>
						<td height="27" align="left" width="100%" style="background-image:url(images/head_top2bg.gif)">&nbsp;&nbsp;&nbsp;&nbsp;<b>待入库单据</b></td>
					</tr>
					<tr><td height="5"></td></tr>
					<%
					if(drkList!=null && drkList.size()>0){
						for(int i=0;i<drkList.size();i++){
							Map info = (Map)drkList.get(i);
							
							String id = StringUtils.nullToStr(info.get("rkd_id"));
							String client_name = StaticParamDo.getClientNameById((String)info.get("client_name"));
							String creatdate = StringUtils.nullToStr(info.get("creatdate"));
							String cgfzr = StaticParamDo.getRealNameById((String)info.get("cgfzr"));
					%>				
					<tr>
						<td width="100%" height="23">&nbsp;
							<A class=xxlb href="#" onclick="editRkd('<%=id %>');" title="点击查看待入库单据详情"><%=id %>&nbsp;&nbsp;&nbsp;<%=client_name %>&nbsp;&nbsp;&nbsp;<%=cgfzr %>&nbsp;&nbsp;&nbsp;【<%=creatdate %>】</A>
						</td>
					</tr>
					<%
						}
					}else{
					%>
					<tr>
						<td width="100%" height="23" align="left">&nbsp;无待入库单据！</td>
					</tr>							
					<%
					}
					%>													
				</table>
			</div>	
			<%
			}
			if(isLsdSpRight.equals("1")){
			%>	
			<div class="inner">			
				<table width="100%" align="left" cellpadding="0" cellspacing="0">
					<tr>
						<td align="left" height="27" width="100%" style="background-image:url(images/head_top2bg.gif)">&nbsp;&nbsp;&nbsp;&nbsp;<b>待审批零售单</b></td>
					</tr>
					<tr><td height="5"></td></tr>
					<%
					if(dspLsdList != null && dspLsdList.size() > 0){
						for(int i=0;i<dspLsdList.size();i++){
							Map lsd = (Map)dspLsdList.get(i);
					%>			
					<tr>
						<td width="100%" height="23">&nbsp;<A class=xxlb title="点击打开审批页面" href="javascript:doSpLsd('<%=StringUtils.nullToStr(lsd.get("id")) %>');"><%=StringUtils.nullToStr(lsd.get("id")) %>&nbsp;&nbsp;<%=StringUtils.nullToStr(lsd.get("dept_name")) %>&nbsp;&nbsp;<%=StringUtils.nullToStr(lsd.get("real_name")) %>&nbsp;&nbsp;【<%=lsd.get("creatdate") %>】</A></td>
					</tr>
					<%
						}
					}else{
					%>
					<tr>
						<td width="100%" height="23" align="left">&nbsp;无待审批零售单！</td>
					</tr>					
					<%
					}
					%>															
				</table>	
			</div>	
			<%
			}
			if(isXsdSpRight.equals("1")){
			%>	
			<div class="inner">		
				<table width="100%" align="left" cellpadding="0" cellspacing="0">
					<tr>
						<td height="27" style="background-image:url(images/head_top2bg.gif)" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待审批销售订单</b></td>
					</tr>
					<tr><td height="5"></td></tr>
					<%
					if(dspXsdList != null && dspXsdList.size() > 0){
						for(int i=0;i<dspXsdList.size();i++){
							Map xsd = (Map)dspXsdList.get(i);
					%>			
					<tr>
						<td width="100%" height="23">&nbsp;<A class=xxlb title="点击打开审批页面" href="javascript:doSpXsd('<%=StringUtils.nullToStr(xsd.get("id")) %>');"><%=StringUtils.nullToStr(xsd.get("id")) %>&nbsp;&nbsp;<%=StringUtils.nullToStr(xsd.get("dept_name")) %>&nbsp;&nbsp;<%=StringUtils.nullToStr(xsd.get("real_name")) %>&nbsp;&nbsp;【<%=StringUtils.nullToStr(xsd.get("creatdate")) %>】</A></td>
					</tr>
					<%
						}
					}else{
					%>
					<tr>
						<td width="100%" height="23" align="left">&nbsp;无待审批销售订单！</td>
					</tr>					
					<%
					}
					%>															
				</table>	
			</div>	
			<%
			}
			if(isCgfkSpRight.equals("1")){
			%>	
			<div class="inner">	
				<table width="100%" align="left" cellpadding="0" cellspacing="0">
					<tr>
						<td height="27" style="background-image:url(images/head_top2bg.gif)" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待审批付款申请单</b></td>
					</tr>
					<tr><td height="5"></td></tr>
					<%
					if(dspCgfkList != null && dspCgfkList.size() > 0){
						for(int i=0;i<dspCgfkList.size();i++){
							Cgfk cgfk = (Cgfk)dspCgfkList.get(i);
					%>			
					<tr>
						<td width="100%" height="23">&nbsp;<A class=xxlb title="点击打开审批页面" href="javascript:doSpCgfk('<%=StringUtils.nullToStr(cgfk.getId()) %>');">
						<%=StringUtils.nullToStr(cgfk.getId()) %>&nbsp;&nbsp;<%=StaticParamDo.getClientNameById(cgfk.getGysbh()) %>&nbsp;&nbsp;【<%=StringUtils.nullToStr(cgfk.getFk_date()) %>】</A></td>
					</tr>
					<%
						}
					}else{
					%>
					<tr>
						<td width="100%" height="23" align="left">&nbsp;无待审批付款申请单！</td>
					</tr>					
					<%
					}
					%>															
				</table>	
			</div>	
			<%
			}
			if(isFysqSpRight.equals("1")){
				%>	
				<div class="inner">	
					<table width="100%" align="left" cellpadding="0" cellspacing="0">
						<tr>
							<td height="27" style="background-image:url(images/head_top2bg.gif)" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待审批费用申请</b></td>
						</tr>
						<tr><td height="5"></td></tr>
						<%
						if(dspFysqList != null && dspFysqList.size() > 0){
							for(int i=0;i<dspFysqList.size();i++){
								Fysq fysq = (Fysq)dspFysqList.get(i);
						%>			
						<tr>
							<td width="100%" height="23">&nbsp;<A class=xxlb title="点击打开审批页面" href="javascript:doSpFysq('<%=StringUtils.nullToStr(fysq.getId()) %>');">
							<%=StringUtils.nullToStr(fysq.getId()) %>&nbsp;&nbsp;<%=StaticParamDo.getDeptNameById(fysq.getYwy_dept()) %>&nbsp;&nbsp;<%=StaticParamDo.getRealNameById(fysq.getSqr()) %>&nbsp;&nbsp;【<%=StringUtils.nullToStr(fysq.getCreatdate()) %>】</A></td>
						</tr>
						<%
							}
						}else{
						%>
						<tr>
							<td width="100%" height="23" align="left">&nbsp;无待审批费用申请！</td>
						</tr>					
						<%
						}
						%>															
					</table>	
				</div>	
				<%
				}
				%>				
		</td>
	</tr>
</table>

</form>
</body>
</html>