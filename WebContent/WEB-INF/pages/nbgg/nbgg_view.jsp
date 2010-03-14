<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>内部公告</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<LINK href="css/InitNav.css" type=text/css rel=stylesheet>
<LINK href="css/Portal.css" type=text/css rel=stylesheet>
<LINK href="css/Desktop.css" type=text/css rel=stylesheet>
<script type="text/javascript">
function saveInfo(){
	if(document.getElementById("content").value == ""){
		alert("回复内容不能为空！");
		document.getElementById("content").focus();
		return;
	}

	document.myform.submit();
}
</script>
</head>
<BODY id=Body bottomMargin=0 leftMargin=0 topMargin=0 align="center">

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
					<TD class=SubTab_LR vAlign=top align=middle height=550>
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
					                                	<TD class=Bottom_dotted align=center width="100%">
					                                		<B><FONT style="font-size: 15px;"><ww:property value="%{xxfbNbgg.title}"/></FONT></B>
					                                		<BR>发布人：<ww:property value="%{getUserRealName(xxfbNbgg.czr)}"/>&nbsp;&nbsp;&nbsp;&nbsp;
					                                		发布日期：<ww:property value="%{xxfbNbgg.pub_date}"/>
					                                	</TD>																						
													</TR>
					                                <TR>
					                                	<TD class="xx_content" align=left width="100%"><BR><ww:property value="%{xxfbNbgg.content}" escape="false"/></TD>
					                                </TR>
					                                <tr>
					                                	<TD class="xx_content" align="center" width="100%">
					                                	<BR><BR>
					                                	
					                                	<!-- 回复内容列表开始 -->	
					                                	<form name="myform" action="saveNbggReplay.html" method="post">
					                                	<ww:hidden name="parent_id" value="%{id}" theme="simple"></ww:hidden>
														<table width="99%" align="center" cellpadding="0" cellspacing="0" border="0" style="border-left:1px solid #B8B8B8;border-bottom: 1px solid #B8B8B8;border-right: 1px solid #B8B8B8;border-top: 1px solid #B8B8B8;">
															<tr>
																<td height="27" align="left" style="background-image:url(images/head_top2bg.gif);font-size: 12px">&nbsp;&nbsp;<b>评论回复</b></td>
															</tr>
															<ww:iterator value="%{xxfbNbggList}">
															<tr>
																<td width="100%" nowrap="nowrap" height="23" style="font-size: 12px">回复人：<ww:property value="%{getUserRealName(czr)}"/>&nbsp;&nbsp;&nbsp;&nbsp;回复时间：<ww:date name="%{cz_date}" format="yyyy-MM-dd HH:mm:ss"/></td>
															</tr>	
															<tr>
																<td width="100%" height="23" class=Bottom_dotted><ww:property value="%{content}" escape="false"/></td>
															</tr>																														
															</ww:iterator>	
															<tr>
																<td width="100%"><BR><ww:textarea name="content" id="content" value="" cssStyle="width:99%" rows="5" theme="simple"></ww:textarea></td>
															</tr>
															<tr>
																<td width="100%" align="center" height="30">
																	<input type="button" name="btnSave" value="提交" class="css_button2" onclick="saveInfo();"/>
																	<input type="reset" name="btnSave" value="重置" class="css_button2"/>
																	<input type="button" name="btnSave" value="关闭" class="css_button2" onclick="window.close();"/>		
																</td>
															</tr>																															
														</table>
														</form>
														<!-- 回复内容列表结束 -->
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
</BODY>
</HTML>