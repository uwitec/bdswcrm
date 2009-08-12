<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Product product = (Product)VS.findValue("product");
String curId = StringUtils.nullToStr(VS.findValue("curId"));

String[] jldw = (String[])VS.findValue("jldw");

List productKindList= (List)VS.findValue("productKindList");

String iscs_flag = StringUtils.nullToStr(VS.findValue("iscs_flag"));
%>

<html>
<head>
<title>产品信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.productForm.productName,1,"string",1,1,100,"产品名称")){	 return; }
		if(!InputValid(document.productForm.productXh,0,"string",0,1,100,"规格")){	 return; }
		if(document.productForm.prop.value == ""){
			alert("产品属性为必选项！");
			return;
		}
		if(!InputValid(document.productForm.price,0,"float",1,0,9999999,"移动加权价")){	 return; }
		if(!InputValid(document.productForm.khcbj,1,"float",1,0,9999999,"考核成本价")){	 return; }	
		if(!InputValid(document.productForm.lsbj,0,"float",0,0,9999999,"零售报价")){	 return; }
		if(!InputValid(document.productForm.fxbj,0,"float",0,0,9999999,"代理价")){	 return; }
		if(!InputValid(document.productForm.lsxj,0,"float",0,0,9999999,"零售限价")){	 return; }					
		if(!InputValid(document.productForm.fxxj,0,"float",0,0,9999999,"分销限价")){	 return; }
		if(!InputValid(document.productForm.gf,0,"float",0,0,9999999,"工分")){	 return; }
		if(!InputValid(document.productForm.dss,0,"float",0,0,9999999,"点杀数")){	 return; }
		if(!InputValid(document.productForm.sp_txm,0,"string",0,1,50,"商品条形码")){	 return; }
		if(!InputValid(document.productForm.ms,0,"string",0,1,500,"产品描述")){	 return; }
		
		document.productForm.submit();
	}
	
	function openWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
</script>
</head>
<body>
<form name="productForm" action="updateProduct.html" method="post">
<input type="hidden" name="productId" value="<%=StringUtils.nullToStr(product.getProductId()) %>">
<input type="hidden" name="curId" value="<%=curId %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">产品信息</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">产品名称</td>
		<td class="a2" width="35%"><input type="text" name="productName" value="<%=StringUtils.nullToStr(product.getProductName()) %>" size="20"></td>	
		<td class="a1" width="15%">规格</td>
		<td class="a2" width="35%"><input type="text" name="productXh" value="<%=StringUtils.nullToStr(product.getProductXh()) %>" size="20"></td>

	</tr>
	
	<tr>
		<td class="a1" width="15%">计量单位</td>
		<td class="a2" width="35%">
			<select name="dw">
				<option value=""></option>
				<%
				for(int i=0;i<jldw.length;i++){
				%>
					<option value="<%=jldw[i] %>" <%if(jldw[i].equals(StringUtils.nullToStr(product.getDw()))) out.print("selected"); %>><%=jldw[i] %></option>
				<%
				}
				%>
			</select></td>
		<td class="a1" width="15%">属性</td>
		<td class="a2" width="35%">
			<select name="prop">
				<option value="库存商品" <%if("库存商品".equals(StringUtils.nullToStr(product.getProp()))) out.print("selected"); %>>库存商品</option>
				<option value="服务/劳务" <%if("服务/劳务".equals(StringUtils.nullToStr(product.getProp()))) out.print("selected"); %>>服务/劳务</option>
			</select></td>
	</tr>
	<tr>
		<td class="a1" width="15%">移动加权价</td>
		<td class="a2" width="35%"><input type="text" name="price" value="<%=JMath.round(product.getPrice()) %>" size="20" <%if(iscs_flag.equals("1")) out.print("readonly"); %>></td>
		<td class="a1" width="15%">考核成本价</td>
		<td class="a2" width="35%"><input type="text" name="khcbj" value="<%=JMath.round(product.getKhcbj()) %>" size="20"></td>		

	</tr>	
	<tr>	
		<td class="a1" width="15%">零售报价</td>
		<td class="a2" width="35%"><input type="text" name="lsbj" value="<%=JMath.round(product.getLsbj()) %>" size="20"></td>
		<td class="a1" width="15%">代理价</td>
		<td class="a2" width="35%"><input type="text" name="fxbj" value="<%=JMath.round(product.getFxbj()) %>" size="20"></td>						
	</tr>
	<tr>
		<td class="a1" width="15%">零售限价</td>
		<td class="a2" width="35%"><input type="text" name="lsxj" value="<%=JMath.round(product.getLsxj()) %>" size="20"></td>		
		<td class="a1" width="15%">分销限价</td>
		<td class="a2" width="35%"><input type="text" name="fxxj" value="<%=JMath.round(product.getFxxj()) %>" size="20"></td>
	</tr>	
	<tr>		
		<td class="a1" width="15%">工分</td>
		<td class="a2" width="35%"><input type="text" name="gf" value="<%=JMath.round(product.getGf()) %>" size="20"></td>		
		<td class="a1" width="15%">点杀数</td>
		<td class="a2" width="35%"><input type="text" name="dss" value="<%=JMath.round(product.getDss()) %>" size="20"></td>
	</tr>	
	<tr>		
		<td class="a1" width="15%">库存下限</td>
		<td class="a2" width="35%"><input type="text" name="kcxx" value="<%=product.getKcxx() %>" size="20"></td>		
		<td class="a1" width="15%">库存上限</td>
		<td class="a2" width="35%"><input type="text" name="kcsx" value="<%=product.getKcsx() %>" size="20"></td>
	</tr>	
	<tr>		
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%">
			<select name="state">
				<option value="正常" <%if(StringUtils.nullToStr(product.getState()).equals("正常")) out.print("selected"); %>>正常</option>
				<option value="停售" <%if(StringUtils.nullToStr(product.getState()).equals("停售")) out.print("selected"); %>>停售</option>
			</select>
		</td>
		<td class="a1" width="15%">强制序列号</td>
		<td class="a2" width="35%">
			<select name="qz_serial_num">				
				<option value="否" <%if(StringUtils.nullToStr(product.getQz_serial_num()).equals("否")) out.print("selected"); %>>否</option>
				<option value="是" <%if(StringUtils.nullToStr(product.getQz_serial_num()).equals("是")) out.print("selected"); %>>是</option>
			</select>
		</td>			
	</tr>
	<tr>
		<td class="a1" width="15%">商品条形码</td>
		<td class="a2"><input type="text" name="sp_txm" value="<%=StringUtils.nullToStr(product.getSp_txm()) %>" size="25"></td>
		<td class="a1" width="15%">商品类别</td>
		<td class="a2">
			<select name="productKind">
				<option value=""></option>
		<%
		if(productKindList != null && productKindList.size() > 0){
			for(int i=0;i<productKindList.size();i++){
				Map map = (Map)productKindList.get(i);
				
				String id = StringUtils.nullToStr(map.get("id"));
				String name = StringUtils.nullToStr(map.get("name"));	
				
				for(int k=0;k<id.length()-2;k++){
					name = "　" + name;
				}
		%>
				<option value="<%=id %>" <%if(StringUtils.nullToStr(product.getProductKind()).equals(id)) out.print("selected"); %>><%=name %></option>
		<%
			}
		}
		%>
			</select>
		</td>		
	</tr>
	<tr height="50">
		<td class="a1">产品描述</td>
		<td class="a2" colspan="3">
			<textarea rows="2" cols="50" name="ms" style="width:80%"><%=StringUtils.nullToStr(product.getMs()) %></textarea>
		</td>
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
