<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Clients client = (Clients)VS.findValue("client");
Page clientWlywPage = (Page)VS.findValue("clientWlywPage");
List  linkmanList=(List)VS.findValue("descClientLinkman");
List  clinetsFollowList=(List)VS.findValue("clientsFollow");		 
%>

<html>
<head>
<title>客户视图</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
    
    //隐藏层|显示层
    function showhidden(id,sort)
    {
      var ids= document.getElementById(id);
      var sortid=document.getElementById(sort);
       if(ids.style.display=="")
       {
          ids.style.display="none";   
       }
       else
       {
          ids.style.display="";
       }
       var imgsrc= sortid.src;
       var lastindex=imgsrc.lastIndexOf('/');
       var newsrc=imgsrc.substring(0,lastindex+1);
       if(sortid.src==newsrc+"desc.gif")
       {    
         sortid.src=newsrc+"asc.gif";     
       }
       else
       {      
         sortid.src=newsrc+"desc.gif";                 
       }   
    }
    //联系人添加
    function linkmanadd(id)
    {
        var destination = "addLinkman.html?id="+id+"";
		var fea = 'width=650,height=500,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'联系人添加',fea);	
    }
    //联系人删除
   function deletelinkman(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "deleteLinkman.html?id=" + id;
		}
	}
    //联系人修改
    function linkmanedit(id)
    {
        var destination = "editLinkman.html?id="+id+"";
		var fea = 'width=650,height=500,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改联系人',fea);	
    }
    //跟进记录添加
     function followadd(id)
    {
        var destination = "addFollow.html?id="+id+"";
		var fea = 'width=600,height=400,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'跟进记录',fea);	
    }
   
    //跟进记录修改
    function followedit(id,clients_id)
    {
        var destination = "editFollow.html?id="+id+"&clients_id="+clients_id+"";
		var fea = 'width=600,height=400,left=' + (screen.availWidth-650)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'跟进记录修改',fea);	
    }
    
     //跟进记录删除
    function followdel(id)
    {
        if(confirm("确定要删除该条记录吗！")){
			location.href = "deleteFollow.html?id=" + id;
		}
    }
    function trSelectChangeCss(selTable)
    {
       var tab= document.getElementById(selTable);
       
		if (event.srcElement.tagName=='TD')
		{
			for(i=0;i<tab.rows.length;i++)
			{
				tab.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}
    
	function vieYwdj(url){
		var fea = 'width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'修改单位',fea);		
	}    
    
</script>
</head>
<body oncontextmenu="return false;">
<form name="myform" action="viewClient.html" method="post">
<input type="hidden" name="id" value="<%=client.getId() %>">
<!-- 客户信息 -->
<table width="100%"  align="center"   cellpadding="0" cellspacing="0">
	<tr>
	  <td width="70%"  valign="top">
	     <table class="chart_info" width="100%"  cellpadding="0" cellspacing="0">
	      <thead>
	         <tr>
		       <td colspan="4" width="100%" style="text-align: center;">客户信息</td>
	        </tr>
	      </thead>
	        <tr>
	          <td class="a1" width="15%">客户名称</td>
	           <td class="a2" width="35%"><%=StringUtils.nullToStr(client.getName()) %></td>
	           <td class="a1" width="15%">客户类型</td>
	          <td class="a2" width="35%"><%=StringUtils.nullToStr(client.getClient_type()) %></td>
	        </tr>
	        <tr>
	          <td class="a1" width="15%">地址</td>
	           <td class="a2" width="35%"><%=StringUtils.nullToStr(client.getAddress()) %></td>
	           <td class="a1" width="15%">固定电话</td>
	          <td class="a2" width="35%"><%=StringUtils.nullToStr(client.getGzdh()) %></td>
	        </tr>
	        <tr>
	           <td class="a1" width="15%">账期</td>
	          <td class="a2" width="35%"><%=StringUtils.nullToStr(client.getZq()) %></td>
	          <td class="a1" width="15%">限额</td>
	           <td class="a2" width="35%"><%=StringUtils.nullToStr(client.getXe()) %></td>	          
	        </tr>
	        <tr>
	           <td class="a1" width="15%">传真</td>
	           <td class="a2" width="35%"><%=StringUtils.nullToStr(client.getCz()) %></td>		        
	           <td class="a1" width="15%">客户经理</td>
	           <td class="a2" width="35%"><%=StaticParamDo.getRealNameById(client.getKhjl()) %></td>
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
				<td class="a2" width="35%"><%=StringUtils.nullToStr(client.getKp_name()) %></td>
				<td class="a1" width="15%">地址</td>
				<td class="a2" width="35%"><%=StringUtils.nullToStr(client.getKp_address()) %></td>		
			</tr>
			<tr>
				<td class="a1" width="15%">电话</td>
				<td class="a2" width="35%"><%=StringUtils.nullToStr(client.getKp_tel()) %></td>
				<td class="a1" width="15%">税号</td>
				<td class="a2" width="35%"><%=StringUtils.nullToStr(client.getKp_sh()) %></td>	
			</tr>
			<tr>
				<td class="a1" width="15%">开户行帐号</td>
				<td class="a2" colspan="3"><%=StringUtils.nullToStr(client.getKp_khhzh()) %></td>		
			</tr>
	        <tr>
	          <td class="a1" width="15%">备注</td>
	           <td class="a2" colspan="3">
	           <textarea rows="3" cols="50" name="client.remark" id="remark" style="width:80%" maxlength="500" readonly><%=StringUtils.nullToStr(client.getRemark()) %></textarea>
	           </td>          
	        </tr>				
		</table>
			     
	  </td>	  
	  <td width="10">&nbsp;</td>	  
	  <td width="30%" valign="top">
	    <div>
			<table width="100%" align="left" valign="top" class="chart_info" cellpadding="0" cellspacing="0">
		      <thead>
		         <tr>
			       <td width="100%" style="text-align: center;">最近历史交易</td>
		        </tr>
		      </thead>				
				<%
				List results = clientWlywPage.getResults();
				Iterator itHis = results.iterator();				
				while(itHis.hasNext()){
					Map map = (Map)itHis.next();
					
					String xwtype = StringUtils.nullToStr(map.get("xwtype"));
					String dj_id = StringUtils.nullToStr(map.get("dj_id"));
					String jsr = StaticParamDo.getRealNameById((String)map.get("jsr"));
					String url = StringUtils.nullToStr(map.get("url"));
				%>
				<tr>
					<td width="100%" height="25">&nbsp;
						<A class=xxlb href="#" onclick="vieYwdj('<%=url+dj_id %>');" title="点击查看明细">[<%=xwtype %>]&nbsp;&nbsp;<%=dj_id %> &nbsp;&nbsp;(<%=jsr %>)</A>
					</td>
				</tr>	
				<%
				}
				%>					
			</table>
	    </div>
	  </td>
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	    <tr>
	      <td  width="99%" ondblclick="showhidden('linkmanid','lximg')">联系人</td>
	       <td  width="1%" style="text-align:right;" onclick="showhidden('linkmanid','lximg')"> <img id="lximg" src='images/desc.gif'></td>
	    </tr>
	</thead>
	<tr>
	  <td colspan="2">
	    <div id="linkmanid" style="display: ">
	      <table width="100%"   class="chart_info" cellpadding="0" cellspacing="0">
	        <tr>
	            <td class="a1" width="25%" style="text-align: left;">
			      <img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="linkmanadd('<%=StringUtils.nullToStr(client.getId())%>');" class="xxlb"> 添 加 </a> 
			   </td>
	        </tr>	 
	      </table>
	      <table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable1">
	        <thead>
	          <tr>
	           <td width="5%">操作</td>  
		       <td width="15%">联系人</td>
		       <td width="5%">类型</td>
		       <td width="15%">手机</td>
		       <td width="15%">座机</td>
		       <td width="15%">邮箱</td>
		       <td width="15%">其他联系方式</td>
		       <td width="15%">备注</td>
	          </tr>
	        </thead>
	        <%
	        Iterator it=linkmanList.iterator();
	        while(it.hasNext())
	        {
	            ClientsLinkman linkman=(ClientsLinkman)it.next();
	              
	         %>
	        <tr class="a1"  onmousedown="trSelectChangeCss('selTable1')" onDblClick="linkmanedit('<%=linkman.getId()%>');" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
		      <td>
			      <a href="#" onclick="linkmanedit('<%=linkman.getId()%>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			      <a href="#" onclick="deletelinkman('<%=linkman.getId()%>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>
		      </td>
		      <td><%=StringUtils.nullToStr(linkman.getName()) %></td>
		      <td><%=StringUtils.nullToStr(linkman.getLx()) %></td>
		      <td><%=StringUtils.nullToStr(linkman.getYddh()) %> </td>
		      <td><%=StringUtils.nullToStr(linkman.getGzdh()) %></td>
		      <td><%=StringUtils.nullToStr(linkman.getMail()) %></td>
		      <td><%=StringUtils.nullToStr(linkman.getQtlx()) %></td>
		      <td><%=StringUtils.nullToStr(linkman.getRemark()) %></td>
	        </tr>
	        <%
	         }
	         %>
	      </table>
	    </div>
	  </td>
	</tr>
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	    <tr>
	      <td  width="99%" ondblclick="showhidden('followid','gjimg')">跟进记录</td>
	       <td  width="1%" style="text-align:right;" onclick="showhidden('followid','gjimg')">  <img id="gjimg" src='images/desc.gif'></td>
	    </tr>
	</thead>
	<tr>
	  <td colspan="2">
	    <div id="followid" style="display: ">
	      <table width="100%"  class="chart_info" cellpadding="0" cellspacing="0">
	        <tr>
	            <td class="a1" width="25%" style="text-align: left;">
			      <img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="followadd('<%=StringUtils.nullToStr(client.getId()) %>');" class="xxlb"> 添 加 </a> 
			   </td>
	        </tr>	 
	      </table>
	      <table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable2">
	        <thead>
	          <tr>
	           <td width="5%">操作</td>
		       <td width="30%">主题</td>
		       <td width="15%">联系人 </td>
		       <td width="10%">联系类型</td>
		       <td width="10%">联系时间</td>
		       <td width="10%">下次联系时间</td>
		       <td width="20%">备注</td>
	         </tr>
	        </thead>
	        <%
	         for(int i=0;i<clinetsFollowList.size();i++)
	         {
	          
	           ClientsFollow follow=(ClientsFollow)clinetsFollowList.get(i);
	           if(i==0)
	           {
	         %>
	        <tr class="a1"   onmouseover="this.className='a2';" onmouseout="this.className='a1';" onDblClick="followedit('<%=follow.getId()%>','<%=StringUtils.nullToStr(client.getId())%>');">
		      <td width="5%">
			      <a href="#" onclick="followedit('<%=follow.getId()%>','<%=StringUtils.nullToStr(client.getId())%>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
		      </td>
		      <td><%=StringUtils.nullToStr(follow.getZt()) %></td>
		      <td><%=StringUtils.nullToStr(follow.getLinkman_id()) %></td>
		      <td><%=StringUtils.nullToStr(follow.getLxlx()) %></td>
		      <td><%=StringUtils.nullToStr(follow.getNextdate()) %></td>
		      <td><%=StringUtils.nullToStr(follow.getNextdate()) %></td>
		      <td><%=StringUtils.nullToStr(follow.getRemark()) %></td>
	        </tr>
	        <%}
	           else 
	           {%>
	            <tr class="a1"   onmouseover="this.className='a2';" onmouseout="this.className='a1';">
		      <td width="5%">
		      </td>
		      <td><%=StringUtils.nullToStr(follow.getZt()) %></td>
		      <td><%=StringUtils.nullToStr(follow.getLinkman_id()) %></td>
		      <td><%=StringUtils.nullToStr(follow.getLxlx()) %></td>
		      <td><%=StringUtils.nullToStr(follow.getLxdate())%></td>
		      <td><%=StringUtils.nullToStr(follow.getNextdate()) %></td>
		      <td><%=StringUtils.nullToStr(follow.getRemark()) %></td>
	        </tr>    
	          <%
	          }
	          }%>
	      </table>
	    </div>
	  </td>
	</tr>
</table><BR>
</form>
</body>
</html>
