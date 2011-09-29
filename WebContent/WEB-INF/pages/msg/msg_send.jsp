<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发送消息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	//保存序列号
	function saveInfo(){
		var objs = document.getElementsByName("msg_reciever");
		
		var flag = false;
		for(var i=0;i<objs.length;i++){
			if(objs[i].checked == true){
				flag = true;
				break;
			}
		}
		
		if(!flag){
			alert("请选择消息接收人！");
			return;
		}
		
		var content = document.getElementById("msg_body");
		if(!InputValid(content,1,"string",1,1,250,"消息内容")){
			content.focus();
			return;
		}	
		
		document.myform.submit();		
		
	}	
	
	//选择所有
	function chkAll(vl){
		var objs = document.getElementsByName("msg_reciever");
		
		for(var i=0;i<objs.length;i++){
			if(vl.checked == true){
				objs[i].checked = true;
			}else{
				objs[i].checked = false;
			}
		}
	}
</script>
</head>
<body>
<form name="myform" action="saveMsg.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">发送消息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">
		选择接收人
		<br><br>
		全选<ww:checkbox name="allChk" id="allChd" theme="simple" onclick="chkAll(this);"/>
		</td>
		<td class="a2" width="85%">
			<table style="font-size: 12px;" cellspacing="5" width="100%">
				<tr>
					<td width="25%">
					<ww:iterator value="%{userList}" status="index">
						<ww:checkbox name="msg_reciever" id="msg_reciever" theme="simple" fieldValue="%{user_id}"/>
						<ww:if test="isOnline==1"><font color="red"><ww:property value="%{real_name}"/>［在线］</font></ww:if>
						<ww:else><ww:property value="%{real_name}"/></ww:else>
						</td>						
						<ww:if test="%{#index.count%4 == 0}">
							</tr><tr><td width="25%">
						</ww:if>
						<ww:else>
							<td width="25%">
						</ww:else>				
					</ww:iterator>
					</td>
				</tr>
			</table>
		</td>					
	</tr>	
	<tr>
		<td class="a1" width="15%">消息内容</td>
		<td class="a2">
			<ww:textarea name="msg_body" id="msg_body"  theme="simple" cssStyle="width:80%" rows="5"/>
		</td>
	</tr>				
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="发 送" class="css_button2" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
