<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Page results = (Page)VS.findValue("nbggPage");

List nbggList = results.getResults();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>内部公告</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<LINK href="css/InitNav.css" type=text/css rel=stylesheet>
<LINK href="css/Portal.css" type=text/css rel=stylesheet>
<LINK href="css/Desktop.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function openWin(id){
		var destination = "viewNbgg.html?id="+id;
		var fea = 'width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
</script>
</head>
<BODY align="center">
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listGdNbgg.html" method="post">
<TABLE cellSpacing=0 cellPadding=10 width="100%" border=0>
	<TBODY>
	
	<TR>
		<TD vAlign=top width="100%" align="center">
		
			<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
				<TBODY>
				<TR>
					<TD vAlign=bottom>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
							<TR>
								<TD width=10 background="index_images/subTab_topleftL2.gif">&nbsp;</TD>
								<TD class="SubTab_top " align=right>&nbsp;</TD>
								<TD vAlign=bottom align=right width=10><IMG src="index_images/subTab_topright.gif"></TD>
							</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				<TR>
					<TD class=SubTab_LR vAlign=top align=middle>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
							
							<TR>
								<TD vAlign=top align=middle colSpan=3>
									<TABLE id=_ctl1_News_lstAnnouncements style="WIDTH: 95%; BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=2 border=0>
										<TBODY>
										<TR>
											<TD>
												<TABLE cellSpacing=0 cellPadding=1 width="100%" border=0>
													<TBODY>
					                                <TR>
					                                	<TD class=Bottom_dotted align=center width="10%"><B>序号</B></TD>	
					                                	<TD class=Bottom_dotted align=center width="65%"><B>标题</B></TD>
					                                	<TD class=Bottom_dotted align=center width="25%"><B>发布时间</B></TD>																					
													</TR>
													<%
													Iterator it = nbggList.iterator();
													int i = 0;
													while(it.hasNext()){
														XxfbNbgg info = (XxfbNbgg)it.next();
														i++;
													%>
					                                <TR>
					                                	<TD class=Bottom_dotted align=center width="10%"><%=i %></TD>	
					                                	<TD class=Bottom_dotted align=left width="65%"><a href="javascript:void(0);" onclick="openWin('<%=StringUtils.nullToStr(info.getId()) %>');"><%=StringUtils.nullToStr(info.getTitle()) %></a></TD>
					                                	<TD class=Bottom_dotted align=center width="25%"><%=StringUtils.nullToStr(info.getPub_date()) %></TD>																					
													</TR>
													<%
													}
													%>
					                                <TR>
					                                	<TD class=Bottom_no_dotted align=right colspan="3"><BR><%=results.getPageScript() %></TD>																					
													</TR>																																																																											
													</TBODY>
												</TABLE>
											</TD>
										</TR>
										</TBODY>
									</TABLE>
								</TD>
							</TR>			
							
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				
				<TR>
					<TD vAlign=top align=left height=10>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" background=index_images/subTab_footcenter.gif border=0>
							<TBODY>
							<TR>
								<TD vAlign=bottom align=left width=10><IMG src="index_images/subTab_footleft.gif"></TD>
								<TD class=SubTab_bottom width="100%" height=10></TD>
								<TD vAlign=bottom align=right width=10><IMG src="index_images/subTab_footright.gif"></TD>
							</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>					
				
				</TBODY>
			</TABLE>
			
		</TD>
	</TR>	
	
	</TBODY>
</TABLE>
</form>
</div>
</BODY>
</html>