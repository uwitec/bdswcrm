<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List nbggList = (List)VS.findValue("nbggList");
List dckList = (List)VS.findValue("dckList");
List drkList = (List)VS.findValue("drkList");

String isLsdSpRight = (String)VS.findValue("isLsdSpRight");
List dspLsdList = (List)VS.findValue("dspLsdList");

String isXsdSpRight = (String)VS.findValue("isXsdSpRight");
List dspXsdList = (List)VS.findValue("dspXsdList");

%>
<html>
<head>
<title>首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">	
	function openNbggWin(id){
		var destination = "viewNbgg.html?id="+id;
		var fea = 'width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'详细信息',fea);	
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
	
	function refreshPage(){
		document.myform.action = "listMain.html";
		document.myform.submit();
	}
	
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
</script>
</head>
<BODY>
<form name="myform" action="listMain.html" method="post">
<table width="100%">
	<tr>
		<td valign="top" width="40%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top" width="100%">
						<table width="100%" align="left"  class="chart_list" cellpadding="0" cellspacing="0">
							<tr>
								<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待出库单据</b></td>								
							</tr>
							<tr><td height="3">&nbsp;</td></tr>
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
					</td>
				</tr>
				
				<tr><td>&nbsp;</td></tr>
				
				<tr>
					<td valign="top" width="100%">
						<table width="100%" align="left"  class="chart_list" cellpadding="0" cellspacing="0">
							<tr>
								<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待入库单据</b></td>
							</tr>
							<tr><td height="3">&nbsp;</td></tr>
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
					</td>
				</tr>				
				
				<%
				if(isLsdSpRight.equals("1")){
				%>				
				<tr><td>&nbsp;</td></tr>
				
				<tr>
					<td width="100%">

						<table width="100%" align="left"  class="chart_list" cellpadding="0" cellspacing="0">
							<tr>
								<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待审批零售单</b></td>
							</tr>
							<tr><td height="3">&nbsp;</td></tr>
							<%
							if(dspLsdList != null && dspLsdList.size() > 0){
								for(int i=0;i<dspLsdList.size();i++){
									Map lsd = (Map)dspLsdList.get(i);
							%>			
							<tr>
								<td width="100%" height="23">&nbsp;<A class=xxlb title="点击打开审批页面" href="javascript:doSpLsd('<%=StringUtils.nullToStr(lsd.get("id")) %>');"><%=StringUtils.nullToStr(lsd.get("id")) %>&nbsp;&nbsp;<%=StringUtils.nullToStr(lsd.get("dept_name")) %>&nbsp;&nbsp;<%=StringUtils.nullToStr(lsd.get("real_name")) %></A></td>
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
							
					</td>
				</tr>	
				<%
				}
				%>
				
				<%
				if(isXsdSpRight.equals("1")){
				%>				
				<tr><td>&nbsp;</td></tr>
				
				<tr>
					<td width="100%">

						<table width="100%" align="left"  class="chart_list" cellpadding="0" cellspacing="0">
							<tr>
								<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待审批销售订单</b></td>
							</tr>
							<tr><td height="3">&nbsp;</td></tr>
							<%
							if(dspXsdList != null && dspXsdList.size() > 0){
								for(int i=0;i<dspXsdList.size();i++){
									Map xsd = (Map)dspXsdList.get(i);
							%>			
							<tr>
								<td width="100%" height="23">&nbsp;<A class=xxlb title="点击打开审批页面" href="javascript:doSpXsd('<%=StringUtils.nullToStr(xsd.get("id")) %>');"><%=StringUtils.nullToStr(xsd.get("id")) %>&nbsp;&nbsp;<%=StringUtils.nullToStr(xsd.get("dept_name")) %>&nbsp;&nbsp;<%=StringUtils.nullToStr(xsd.get("real_name")) %></A></td>
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
					</td>
				</tr>
				<%
				}
				%>							
			</table>		
		</td>
		<td width="20"></td>
		<td valign="top" width="60%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr><td>
				<table width="100%" align="left"  class="chart_list" cellpadding="0" cellspacing="0">
					<tr>
						<td class="csstitle" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;<b>内部公告</b></td>
						 <td class="csstitle" align="right"><img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷新首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr><td height="3" colspan="3">&nbsp;</td></tr>
					<%
					Iterator itNbgg = nbggList.iterator();
					while(itNbgg.hasNext()){
						XxfbNbgg info = (XxfbNbgg)itNbgg.next();
						
						String id = StringUtils.nullToStr(info.getId());
						String title = StringUtils.nullToStr(info.getTitle());
						String subTitle = title;
						if(title.length()>=30){
							subTitle = title.substring(0,30) + "...";
						}
						String pub_date = StringUtils.nullToStr(info.getPub_date());
						String jsr = StaticParamDo.getRealNameById(info.getCzr());
					%>				
					<tr>
						<td width="60% height="23">&nbsp;<A class=xxlb href="#" onclick="openNbggWin('<%=id %>');" title="<%=title %>"><%=subTitle %></A></td>
						<td width="20%" height="23"><%=jsr %></td>
						<td width="20%" height="23" colspan="2">【<%=pub_date %>】</td>
					</tr>
					<%
					}
					%>
					<tr>
					<TD align=right height="23" colspan="3"><a href="javascript:void(0);" onclick="parent.addtabFmMenu('公告列表','listGdNbgg.html','');" class=xxlb>更多...</a>&nbsp;&nbsp;&nbsp;</TD>	
					</tr>															
				</table>
				</td></tr>							
			</table>				
		</td>
	</tr>
</table>
</form>
</BODY>
</html>