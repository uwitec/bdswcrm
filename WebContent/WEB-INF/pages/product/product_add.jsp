<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String curId = StringUtils.nullToStr(VS.findValue("curId"));
String[] jldw = (String[])VS.findValue("jldw");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加商品</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" src="js/keychange.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.productForm.productName,1,"string",1,1,100,"商品名称")){	 document.productForm.productName.focus();return; }
		if(!InputValid(document.productForm.productXh,0,"string",0,1,100,"规格")){	document.productForm.productXh.focus(); return; }
		if(document.productForm.prop.value == ""){
			alert("商品属性为必选项！");
			return;
		}
		if(!InputValid(document.productForm.price,0,"float",1,0,9999999,"移动加权价")){	 document.productForm.price.focus();return; }
		if(!InputValid(document.productForm.khcbj,1,"float",1,0,9999999,"考核成本价")){	 document.productForm.khcbj.focus();return; }	
		if(!InputValid(document.productForm.lsbj,0,"float",0,0,9999999,"零售报价")){	 document.productForm.lsbj.focus();return; }
		if(!InputValid(document.productForm.lsxj,0,"float",0,0,9999999,"零售限价")){	 document.productForm.lsxj.focus();return; }			
		if(!InputValid(document.productForm.fxbj,0,"float",0,0,9999999,"分销报价")){	 document.productForm.fxbj.focus();return; }
		if(!InputValid(document.productForm.fxxj,0,"float",0,0,9999999,"分销限价")){	 document.productForm.fxxj.focus();return; }
		if(!InputValid(document.productForm.gf,0,"float",0,0,100,"比例点杀")){	 document.productForm.gf.focus();return; }
		if(!InputValid(document.productForm.dss,0,"float",0,0,9999999,"金额点杀")){	 document.productForm.dss.focus();return; }
		if(!InputValid(document.productForm.ygcbj,0,"float",0,0,9999999,"预估成本价")){	 document.productForm.ygcbj.focus();return; }
		if(!InputValid(document.productForm.sp_txm,0,"string",0,1,50,"商品条形码")){	 document.productForm.sp_txm.focus();return; }
		if(!InputValid(document.productForm.ms,0,"string",0,1,500,"商品描述")){	 document.productForm.ms.focus();return; }
		
		var id = "<%=curId %>";
		$.ajax({
			cache: false,
			url:"checkProductKind.html",
			type: "POST",
			data:{curId:id},
			success: function(result) {
				if(result == "true"){
					alert("当前商品类别下存在子类别，不能添加商品，商品只能添加到最终的子类别下！");
				}else{
					document.productForm.submit();
				}
			}
		});	
	}
	
	function openWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}	
		
</script>
</head>
<body onload="document.productForm.productName.focus();">
<form name="productForm" action="saveProduct.html" method="post">
<input type="hidden" name="productKind" value="<%=curId %>">
<input type="hidden" name="curId" value="<%=curId %>">
<input type="hidden" name="kcsx" value="0" size="20">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">商品信息</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">商品名称</td>
		<td class="a2" width="35%"><input type="text" name="productName" value="" size="20" onkeyup="goNext(this.form,this.name);"></td>	
		<td class="a1" width="15%">规格</td>
		<td class="a2" width="35%"><input type="text" name="productXh" value="" size="20" onkeyup="goNext(this.form,this.name);"></td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">计量单位</td>
		<td class="a2" width="35%">
			<select name="dw" onkeyup="goNext(this.form,this.name);">
				<option value=""></option>
				<%
				for(int i=0;i<jldw.length;i++){
				%>
					<option value="<%=jldw[i] %>"><%=jldw[i] %></option>
				<%
				}
				%>
			</select>
		</td>
		<td class="a1" width="15%">移动加权价</td>
		<td class="a2" width="35%"><input type="text" name="price" value="0.00" size="20" onkeyup="goNext(this.form,this.name);"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">预估成本价</td>
		<td class="a2" width="35%"><input type="text" name="ygcbj" value="0.00" size="20" onkeyup="goNext(this.form,this.name);"></td>	
		<td class="a1" width="15%">考核成本价</td>
		<td class="a2" width="35%"><input type="text" name="khcbj" value="0.00" size="20" onkeyup="goNext(this.form,this.name);"></td>		
	
	</tr>	
	<tr>		
		<td class="a1" width="15%">零售报价</td>
		<td class="a2" width="35%"><input type="text" name="lsbj" value="0.00" size="20" onkeyup="goNext(this.form,this.name);"></td>	
		<td class="a1" width="15%">零售限价</td>
		<td class="a2" width="35%"><input type="text" name="lsxj" value="0.00" size="20" onkeyup="goNext(this.form,this.name);"></td>							
	</tr>
	<tr>
		<td class="a1" width="15%">分销报价</td>
		<td class="a2" width="35%"><input type="text" name="fxbj" value="0.00" size="20" onkeyup="goNext(this.form,this.name);"></td>		
		<td class="a1" width="15%">分销限价</td>
		<td class="a2" width="35%"><input type="text" name="fxxj" value="0.00" size="20" onkeyup="goNext(this.form,this.name);"></td>		
	</tr>		
	<tr>		
		<td class="a1" width="15%">比例点杀</td>
		<td class="a2" width="35%"><input type="text" name="gf" value="0.00" size="20" onkeyup="goNext(this.form,this.name);">&nbsp;%</td>		
		<td class="a1" width="15%">金额点杀</td>
		<td class="a2" width="35%"><input type="text" name="dss" value="0.00" size="20" onkeyup="goNext(this.form,this.name);"></td>
	</tr>
	 
	<tr>	
		<td class="a1" width="15%">库存下限</td>
		<td class="a2" width="35%"><input type="text" name="kcxx" value="0" size="20" onkeyup="goNext(this.form,this.name);"></td>	
		<td class="a1" width="15%">库存上限</td>
		<td class="a2" width="35%"><input type="text" name="kcsx" value="0" size="20" onkeyup="goNext(this.form,this.name);"></td>	
	</tr>		
	<tr>	
		<td class="a1" width="15%">强制序列号</td>
		<td class="a2" width="35%">
			<select name="qz_serial_num" onkeyup="goNext(this.form,this.name);">				
				<option value="否">否</option>
				<option value="是">是</option>
			</select>
		</td>	
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%">
			<select name="state" onkeyup="goNext(this.form,this.name);">
				<option value="正常">正常</option>
				<option value="停售">停售</option>
			</select>
		</td>
	</tr>		
	<tr>
	    <td class="a1" width="15%">条形码</td>
		<td class="a2"><input type="text" name="sp_txm" value="" size="20" onkeyup="goNext(this.form,this.name);"></td>	
		
		<td class="a1" width="15%">属性</td>
		<td class="a2" width="35%">
			<select name="prop" onkeyup="goNext(this.form,this.name);">
				<option value="库存商品">库存商品</option>
				<option value="服务/劳务">服务/劳务</option>
			</select>
		</td>			
	</tr>
	<tr>
	  <td class="a1" width="15%">是否参与提成</td>
		<td class="a2" colspan="3">
			<select name="sfcytc" onkeyup="goNext(this.form,this.name);">
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		</td>
	</tr>
	<tr>
	    <td class="a1">商品描述</td>
		<td class="a2" colspan="3"><input type="text"  name="ms" value=""  style="width:90%" onkeyup="goNext(this.form,this.name);"></td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
