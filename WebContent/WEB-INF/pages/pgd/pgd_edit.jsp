<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Map pgd=(Map)VS.findValue("pgd");
String[] wxlx=(String[])VS.findValue("wxlx");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>派工单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="js/selSqr.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	function saveInfo(vl)
	{ 
		if(vl == '1'){
			document.getElementById("p_state").value = "已保存";
		}else{
			document.getElementById("p_state").value = "已提交";
		}	
	      
	   if(document.getElementById("fzr").value=="")
	   {
	      alert("经手人不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("sqr").value=="")
	   {
	      alert("维修人不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("p_gzlx").value=="")
	   {
	      alert("故障类型不能为空，请填写！");
	      return;
	   }
	    if(document.getElementById("p_khxz").value=="")
	   {
	      alert("客户性质不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("p_wx_type").value=="")
	   {
	      alert("维修类型不能为空，请填写！");
	      return;
	   }
	   if(document.getElementById("p_wxpz").value=="")
	   {
	      alert("维修凭证不能为空，请填写！");
	      return;
	   }
	    if(document.getElementById("p_gzfx").value=="")
	   {
	      alert("故障分析不能为空，请填写！");
	      return;
	   }			   	      
	   document.myform.action="updatePgd.html";
	   if(document.getElementById("p_state").value == "已提交")
	   {
			if(window.confirm("确认要提交派工单吗，提交后将无法修改！")){				
				document.myform.submit();		
			}
 	     }
	     else
	     { 
	         document.myform.submit();	
	     }
	     document.myform.btnSave.disabled = true;
		 document.myform.btnSub.disabled = true;
	}	

    function clearVl(){       
       	  document.getElementById("product_name").value="";
		  document.getElementById("product_gg").value="";
		  document.getElementById("product_serial_num").value="";
		  document.getElementById("product_remark").value="";      
    }	
     
	function openWin(){
		var destination = "selBxProcCkd.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	 
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}
	
	 
	function opengcsWin(){
	    var destination = "selGcs.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'工程师',fea);	
	}		
	
	
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}

	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
	
	function f_enter(){
	    if (window.event.keyCode==13){
	        sendSerialNum();
	    }
	}
	
	//发送序列号
	function sendSerialNum(){
		var serialNum = dwr.util.getValue("s_nums");
		if(serialNum == ""){
		   
			return;
		}
		dwrService.getBxdRecordorBuyRecord(serialNum,setProductInfo);		
	}
	
	function showfy(fyidvalue)
	{
	  if(fyidvalue=='否')
	  {
	    document.getElementById("fyid").style.display="none";
	  }
	  else
	  {
	    document.getElementById("fyid").style.display=""; 
	  }
	}		
</script>
</head>
<body onload="initFzrTip();initSqrTip();">
<FORM  name="myform" action="updatePgd.html" method="post">
<input type="hidden" name="pgds.p_state" id="p_state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4">派工信息</td>
	</tr>
	</thead>
	 	
		<tr>
		<td class="a1" width="15%">派工单编号</td>
		<td class="a2" width="35%"><input type="text" name="pgds.p_id" id="p_id" value="<%=StringUtils.nullToStr(pgd.get("p_id")) %>" style="width:230px" readonly></td>	
		 
		<td class="a1">派工日期</td>
		<%
		  String bxdate= StringUtils.nullToStr(pgd.get("p_yy_date"));
		  String rq="";
		  if(!bxdate.equals(""))
		  {
		    rq=bxdate;
		  } 
		  else
		  {
		    rq=DateComFunc.getToday();
		  }
		 %>
		<td class="a2"><input type="text" name="pgds.p_yy_date" id="p_yy_date" value="<%=rq %>" size="15" style="width:230px" class="Wdate" onFocus="WdatePicker()">
		<font color="red">*</font>
		</td>
	</tr>
	<tr>
		
		<td class="a1" width="15%">派工人</td>
		<td class="a2" width="35%">
		   <input  id="brand" style="width:230px" type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById((String)pgd.get("p_pgr")) %>"/> <font color="red">*</font>
           <div  id="brandTip"  style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		   <input type="hidden" name="pgds.p_pgr" id="fzr" value="<%=StringUtils.nullToStr(pgd.get("p_pgr")) %>"/> 
		    
		</td>	
		<td class="a1" width="15%">维修人</td>
    	<td class="a2" width="35%">	  
		 <input  name="sqr_text" id="sqr_text"  style="width:230px" onblur="setSqrValue();" value="<%=StaticParamDo.getRealNameById((String)pgd.get("p_wxr")) %>"/> <font color="red">*</font>
         
        <div id="sqr_tips" style="position:absolute;left:89px; top:82px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
         </div>
		    <input type="hidden" name="pgds.p_wxr" id="sqr"  value="<%=StringUtils.nullToStr(pgd.get("p_wxr")) %>"/> 
		   
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">维修状态</td>
		<td class="a2" width="35%"  >
			<select name="pgds.p_wx_state" id="p_wx_state" style="width:232px">
				<option value="待处理" <%if(StringUtils.nullToStr(pgd.get("p_wx_state")).equals("待处理"))out.print("selected"); %>>待处理</option>
				<option value="已处理" <%if(StringUtils.nullToStr(pgd.get("p_wx_state")).equals("已处理"))out.print("selected"); %>>已处理</option>
			</select>		
		</td>			
	</tr>
	 
</table> 
 <br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">售服信息</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">售服单编号</td>
		<td class="a2" width="35%"><input type="text" name="pgds.p_sfd_id" style="width:232px"  id="sfd_id" value="<%=StringUtils.nullToStr(pgd.get("p_sfd_id")) %>" maxlength="45" readonly></td>	
		 	
		<td class="a1" width="15%">接待日期</td>
		<td class="a2" width="35%"><input type="text"  style="width:232px" id="jx_date" value="<%=StringUtils.nullToStr(pgd.get("jx_date")) %>" maxlength="45"  readonly>
		 </td>						
	</tr>
	<tr>			
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%">
		<input type="text" id="client_name" style="width:232px" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(pgd.get("client_name")))%>"  maxlength="45" readonly>
		 </td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		   <input  id="jxr"  type="text" style="width:232px"   value="<%=StaticParamDo.getRealNameById(StringUtils.nullToStr(pgd.get("jxr"))) %>" maxlength="45" readonly>  
           		    
		</td>
			
	</tr>
	<tr>
	   <td class="a1" width="15%">联系人</td>
	   <td class="a2" width="35%">
	  	 	<input id="linkman" type="text" length="45" style="width:232px" value="<%=StringUtils.nullToStr(pgd.get("linkman")) %>" readonly> 
		</td>
		<td class="a1" width="15%">电话</td>
		<td class="a2" width="35%">
		 	<input  id="mobile" type="text" length="45" style="width:232px" value="<%=StringUtils.nullToStr(pgd.get("mobile")) %>" readonly> 
		</td>
			
	</tr>
	<tr>
	   <td class="a1" width="15%">地址</td>
		<td class="a2" width="35%">
		 	<input  id="address" type="text" length="45" style="width:232px" value="<%=StringUtils.nullToStr(pgd.get("address")) %>" readonly> 
		 </td>
		<td class="a1" width="15%">求助方式</td>
		<td class="a2" width="35%">
		 	<input  id="qzfs" type="text" length="45" style="width:232px" value="<%=StringUtils.nullToStr(pgd.get("qzfs")) %>" readonly> 
		 </td>	
	</tr>
	<tr>
	 <td class="a1" width="15%">内容描述</td>
	 <td class="a2" width="85%" colspan="3">
			<textarea rows="2"  id="ms" style="width:86%"> <%=StringUtils.nullToStr(pgd.get("ms")) %> </textarea>
	</td> 
	</tr>			
</table> 
 <br> 
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">		  
	<thead>
	<tr>
		<td colspan="4">维修信息</td>
	</tr>
	</thead>
	 <tr>	
	   <td class="a1" width="15%">故障类型</td>
		<td class="a2" width="35%">
		 	<select name="pgds.p_gzlx" id="p_gzlx"  style="width:232px">
			 <option value=""></option>
			<%
			   if(wxlx != null && wxlx.length>0){
			     for(int i=0;i<wxlx.length;i++){
			       String szd=wxlx[i];
			 %>
			      <option value="<%=szd %>" <%if(!(StringUtils.nullToStr(pgd.get("p_gzlx")).equals("")))out.print("selected"); %>><%=szd %></option>
			 <%
			      }
			   }
			  %>
			</select><font color="red">*</font>
		</td>
		<td class="a1" width="15%">客户性质</td>
		<td class="a2" width="35%">
		    <select name="pgds.p_khxz" id="p_khxz"  style="width:232px">
		 	 <option value=""></option>
			     <option value="送修" <%if(StringUtils.nullToStr(pgd.get("p_khxz")).equals("送修"))out.print("selected"); %>>送修</option>
				 <option value="上门" <%if(StringUtils.nullToStr(pgd.get("p_khxz")).equals("上门"))out.print("selected"); %>>上门</option>
			     <option value="保内" <%if(StringUtils.nullToStr(pgd.get("p_khxz")).equals("保内"))out.print("selected"); %>>保内</option>
				 <option value="保外" <%if(StringUtils.nullToStr(pgd.get("p_khxz")).equals("保外"))out.print("selected"); %>>保外</option>
			</select><font color="red">*</font>
		</td>
			
	</tr>
	<tr>	   
		<td class="a1" width="15%">维修类型</td>
		<td class="a2" width="35%">
		 	<select name="pgds.p_wx_type" id="p_wx_type"  style="width:232px">
		 	 <option value=""></option>
			     <option value="个人" <%if(StringUtils.nullToStr(pgd.get("p_wx_type")).equals("个人"))out.print("selected"); %>>个人</option>
				 <option value="单位" <%if(StringUtils.nullToStr(pgd.get("p_wx_type")).equals("单位"))out.print("selected"); %>>单位</option>
			     
			</select><font color="red">*</font>		    
		</td>
		<td class="a1" width="15%">维修凭证</td>
		<td class="a2" width="35%">
		 	<select name="pgds.p_wxpz" id="p_wxpz" style="width:232px">
		 	 <option value=""></option>
			     <option value="保修卡" <%if(StringUtils.nullToStr(pgd.get("p_wxpz")).equals("保修卡"))out.print("selected"); %>>保修卡</option>
				 <option value="收款单据" <%if(StringUtils.nullToStr(pgd.get("p_wxpz")).equals("收款单据"))out.print("selected"); %>>收款单据</option>			      
			</select>		    
		<font color="red">*</font></td>		
	</tr>
		
	<tr height="35">
		 <td class="a1" width="15%">故障及分析</td>
		<td class="a2" colspan="3">
			<textarea rows="2" name="pgds.p_gzfx" id="p_gzfx" style="width:86%"><%=StringUtils.nullToStr(pgd.get("p_gzfx"))%></textarea>
		<font color="red">*</font></td>	
	</tr>		 
</table>

<br>

<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">备注</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="4" name="pgds.p_ms" id="p_ms" style="width:86%"> <%=StringUtils.nullToStr(pgd.get("p_ms")) %> </textarea>
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSave" value="草稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="btnSub" value="提交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
<font color="red">注：“草稿”指派工单暂存，可修改；“提交”后派工单不可修改。</font>
<BR><BR>
</FORM>
</BODY>
</HTML>