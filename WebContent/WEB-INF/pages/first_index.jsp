<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List nbggList = (List)VS.findValue("nbggList");

XxfbNbggService xxfbNbggService = (XxfbNbggService)VS.findValue("xxfbNbggService");

List bwlList = (List)VS.findValue("bwlList");
List clientList = (List)VS.findValue("clientList");

ClientWlStatService clientWlStatService = (ClientWlStatService)VS.findValue("clientWlStatService");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/switchCss.js"></script>
<style type="text/css">
.inner{
	width:48%;
	margin:2px; 
	border:1px solid #B8B8B8;
	text-align:left;
	display:inline ;
	float:left;	
}
</style>
<script type="text/javascript">	
	function openNbggWin(id){
		var destination = "viewNbgg.html?id="+id;
		var fea = 'width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'详细信息',fea);	
	}

   function openBwlWin(id){
		var destination = "viewBwl.html?id="+id;
		var fea = 'width=800,height=650,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-650)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'详细信息',fea);	
	}

	function refreshPage(){
		document.myform.action = "listMain.html";
		document.myform.submit();
	}	
</script>
</head>
<BODY>
<form name="myform" action="listMain.html" method="post">
<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="100%" align="center">
			<div class="inner">
				<table width="100%" align="left" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td height="27" colspan="4" align="left" style="background-image:url(images/head_top2bg.gif)">&nbsp;&nbsp;&nbsp;&nbsp;<b>内部公告</b></td>
					</tr>
					<tr><td height="3" colspan="4"></td></tr>
					<%
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Iterator itNbgg = nbggList.iterator();
					while(itNbgg.hasNext()){
						XxfbNbgg info = (XxfbNbgg)itNbgg.next();
						
						String id = StringUtils.nullToStr(info.getId());
						String title = StringUtils.nullToStr(info.getTitle());
						String subTitle = title;
						if(title.length()>22){
							subTitle = title.substring(0,22) + "...";
						}
						String pub_date = StringUtils.nullToStr(info.getPub_date());
						String jsr = StaticParamDo.getRealNameById(info.getCzr());
						String cz_date=StringUtils.nullToStr(sdf.format(info.getCz_date()));						
						String finalhfr=StaticParamDo.getRealNameById(xxfbNbggService.getFinalhfr(id));
					%>				
					<tr>
						<td width="55% height="23">&nbsp;<A class=xxlb href="#" onclick="openNbggWin('<%=id %>');" title="<%=title %>"><%=subTitle %></A></td>
						<td width="15%" nowrap="nowrap" height="23"><%=jsr %></td>
						<td width="15%" nowrap="nowrap" height="23"><%=finalhfr %></td>
						<td width="15%" nowrap="nowrap" height="23">【<%=pub_date %>】</td>
					</tr>
					<%
					}
					%>
					<tr>
					<TD align=right height="23" colspan="4"><a href="javascript:void(0);" onclick="parent.addtabFmMenu('公告列表','listGdNbgg.html','');" class=xxlb>更多...</a>&nbsp;&nbsp;&nbsp;</TD>	
					</tr>															
				</table>
			</div>
		
			<div class="inner">
				<table width="100%" align="left" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td height="27" colspan="3" align="left" style="background-image:url(images/head_top2bg.gif)">&nbsp;&nbsp;&nbsp;&nbsp;<b>备忘录</b></td>
					</tr>
					<tr><td height="3" colspan="3"></td></tr>
					<%
					Iterator itBwl = bwlList.iterator();
					while(itBwl.hasNext()){
						Bwl info = (Bwl)itBwl.next();
						
						String id = StringUtils.nullToStr(info.getId());
						String title = StringUtils.nullToStr(info.getTitle());
						String subTitle = title;
						if(title.length()>22){
							subTitle = title.substring(0,22) + "...";
						}
						String cz_date = StringUtils.nullToStr(info.getCz_date());
						String jsr = StaticParamDo.getRealNameById(info.getCzr());
					%>				
					<tr>
						<td width="60% height="23">&nbsp;<A class=xxlb href="#" onclick="openBwlWin('<%=id %>');" title="<%=title %>"><%=subTitle %></A></td>
						<td width="15%" nowrap="nowrap" height="23"><%=jsr %></td>
						<td width="15%" nowrap="nowrap" height="23">【<%=cz_date %>】</td>
					</tr>
					<%
					}
					%>
					<tr>
					<TD align=right height="23" colspan="3"><a href="javascript:void(0);" onclick="parent.addtabFmMenu('备忘录列表','listSyBwl.html','');" class=xxlb>更多...</a>&nbsp;&nbsp;&nbsp;</TD>	
					</tr>															
				</table>
			</div>
			
			<div class="inner">
				<table width="100%" align="left" cellpadding="0" cellspacing="0">
					<tr>
						<td height="27" colspan="7" align="left" style="background-image:url(images/head_top2bg.gif)">&nbsp;&nbsp;&nbsp;&nbsp;<b>客户应收汇总</b></td>								
					</tr>
					<tr>
											 
			           <TD width="45% height="23">客户名称</TD>
			           <TD width="14%" nowrap="nowrap" height="23">当前应收款</TD>			           
			           <TD width="14%" nowrap="nowrap" height="23">超期应收款</TD>	
			           <TD width="12%" nowrap="nowrap" height="23">预收款</TD>			           
			           <TD width="15%" nowrap="nowrap" height="23">最长超期天数</TD>
		           
					</tr>
					<tr><td height="7"></td></tr>
					<%
					double hj_dqys = 0;
                    double hj_wdqysk = 0;
                    double hj_cqysk = 0;
                    double hj_ysk = 0;
                    double hj_qtysk = 0;

                    Map qcMap = clientWlStatService.getClientQc(DateComFunc.getToday());
                    Map infoMap = clientWlStatService.getClientWlInfo(DateComFunc.getToday(),DateComFunc.getToday(),"");
                    Map maxCqtsMap = clientWlStatService.getClientMaxCqts("");  //最长超期天数
                    Map wdqyskMap = clientWlStatService.getClientWdqysk("");    //未到期应收款
                    Map cqyskMap = clientWlStatService.getClientCqysk("");      //超期应收款 

                    Map yskMap = clientWlStatService.getClientYushouk("");      //预收款
                    Map qcjsMap = clientWlStatService.getClientQcjsye("");      //期初结算
                    Map tzjsMap = clientWlStatService.getClientTzjsye("");      //调账（应收）结算

                    if(clientList != null && clientList.size() > 0){
	                for(int i=0;i<clientList.size();i++){
		            Map map = (Map)clientList.get(i);
		
		            String client_id = StringUtils.nullToStr(map.get("id"));
		            String client_name = StringUtils.nullToStr(map.get("name"));		            
		            
		            double qcs = qcMap.get(client_id+"应收")==null?0:((Double)qcMap.get(client_id+"应收")).doubleValue();  //期初数		
		            double bqfs = infoMap.get(client_id+"应收发生")==null?0:((Double)infoMap.get(client_id+"应收发生")).doubleValue();  //本期发生
		            double ysje = infoMap.get(client_id+"已收发生")==null?0:((Double)infoMap.get(client_id+"已收发生")).doubleValue();  //本期已收
		            String strCqts = StringUtils.nullToStr(maxCqtsMap.get(client_id));
		            int cqts = 0; //最长超期天数
		            if(!strCqts.equals("")){
			            cqts = new Integer(strCqts).intValue();
		            }
		            double wdqysk = wdqyskMap.get(client_id)==null?0:((Double)wdqyskMap.get(client_id)).doubleValue();  //未到期应收款 
		            double cqysk = cqyskMap.get(client_id)==null?0:((Double)cqyskMap.get(client_id)).doubleValue();  //超期应收款
		
		            double ysk = yskMap.get(client_id)==null?0:((Double)yskMap.get(client_id)).doubleValue();  //预收款
		            double qtysk = (qcjsMap.get(client_id)==null?0:((Double)qcjsMap.get(client_id)).doubleValue()) + (tzjsMap.get(client_id)==null?0:((Double)tzjsMap.get(client_id)).doubleValue()); //其它应收款 

		            double dqys = qcs + bqfs - ysje;  //当前应收
		 	        
		 	        
		            boolean bl = false;		 
			        if(dqys !=0 || cqysk!=0 || ysk!=0) {
			 	        bl = true;
			        }
		          else{
			          bl = false;
		           }
		 	        	 	        
		 	       if(bl){ 
			        hj_dqys += dqys;
			        hj_wdqysk += wdqysk;
			        hj_cqysk += cqysk;
			        hj_ysk += ysk;
			        hj_qtysk += qtysk;
			
%>			
                  <TR>					 
			           <TD width="45% height="23"><%=client_name %></TD>
			           <TD width="14%" nowrap="nowrap" height="23"><%=JMath.round(dqys,2) %></TD>			           
			           <TD width="14%" nowrap="nowrap" height="23"><%=JMath.round(cqysk,2) %></TD>	
			           <TD width="12%" nowrap="nowrap" height="23"><%=JMath.round(ysk,2) %></TD>			           
			           <TD width="15%" nowrap="nowrap" height="23"><%=cqts %></TD>
		            </TR>					

<%		
		}
	}
	
}
	
%>	
                   <TR>
		               <TD width="45% height="23">合计（金额）</TD>
		               <TD width="14%" nowrap="nowrap" height="23"><%=JMath.round(hj_dqys,2) %></TD>
		               <TD width="14%" nowrap="nowrap" height="23"><%=JMath.round(hj_cqysk,2) %></TD>
		               <TD width="12%" nowrap="nowrap" height="23"><%=JMath.round(hj_ysk,2) %></TD>		            
		               <TD width="15%" nowrap="nowrap" height="23">--</TD>
	                </TR>
                    																			
				</table>
			</div>
			
		</td>		
	</tr>
</table>
</form>
</BODY>
</html>