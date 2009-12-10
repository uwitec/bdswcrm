<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("lsdProducts");
String id = (String)VS.findValue("id");

%>

<html>
<head>
<title>进货单明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}
	
	function selectAll(){
		var flag = false;
		if(document.myform.allCheck.checked){
			flag = true;
		}
		for(var i=0;i<document.myform.elements.length;i++){
			var o = document.myform.elements[i];
			if(o.name == "selOption"){
				if(flag == true){
					o.checked = true;
				}else{
					o.checked = false;
				}
			}
		}
	}
	
	function sel(){
		var k = 0;
		var allCount = parent.window.opener.allCount;  //当前存在的最大行序号
		//alert("当前存在的最大行序号" + allCount);
		var startCount = 0;
		for(var x=0;x<=allCount;x++){
			var id = parent.window.opener.document.getElementById("product_id_"+x);
			var name = parent.window.opener.document.getElementById("product_name_"+x);
	
			startCount = x;
			
			if(id.value=="" && name.value==""){  //如果该行无值则跳出
				break;
			}
			
			if(startCount == allCount){
				startCount = startCount + 1;
			}			
		}
		
		k = startCount;  //从此位置开始添加
		//alert("从此位置开始添加" + k);
		
		for(var i=1;i<document.myform.selOption.length;i++){
					
			var o = document.myform.selOption[i];
			if(o.checked){
			
				var vl = document.myform.selOption[i].value;
				var arryStr = vl.split("|");
				
				var flag = false;
				for(var y=0;y<=allCount;y++){
					var s_id = parent.window.opener.document.getElementById("product_id_"+y);
					
					if(s_id.value == arryStr[0]){  //该值已存在
						flag = true;
					}
				}			
			
				if(flag == false){
					var id = parent.window.opener.document.getElementById("product_id_"+k);
					
					if(id == null){
						parent.window.opener.addTr();
					}
					
					id = parent.window.opener.document.getElementById("product_id_"+k);					
					var name = parent.window.opener.document.getElementById("product_name_"+k);
					var xh = parent.window.opener.document.getElementById("product_xh_"+k);					
					var pc = parent.window.opener.document.getElementById("th_price_"+k);
					var nums = parent.window.opener.document.getElementById("nums_"+k);
					var cb =  parent.window.opener.document.getElementById("cbj_"+k);
					var qz_flag =  parent.window.opener.document.getElementById("qz_flag_"+k);
					var khcbj = parent.window.opener.document.getElementById("kh_cbj_"+k);
				
					var sd = parent.window.opener.document.getElementById("sd_"+k);
					var gf = parent.window.opener.document.getElementById("gf_"+k);
					var ds = parent.window.opener.document.getElementById("ds_"+k);
					var basic_ratio = parent.window.opener.document.getElementById("basic_ratio_"+k);
					var out_ratio = parent.window.opener.document.getElementById("out_ratio_"+k);
				
					id.value = arryStr[0];
					name.value = arryStr[1];
					xh.value = arryStr[2];
					pc.value = arryStr[3];
					nums.value = arryStr[4];
					cb.value = arryStr[5];
					
					if(qz_flag != null){
						qz_flag.value = arryStr[6];
					}
					if(khcbj != null) khcbj.value = arryStr[7];
					if(sd != null) sd.value = arryStr[8];
					if(gf != null) gf.value = arryStr[9];
					if(ds != null) ds.value = arryStr[10];
					if(basic_ratio != null) basic_ratio.value = arryStr[11];
					if(out_ratio != null) out_ratio.value = arryStr[12];
					
					k++;	
				}	
			}

		}
		parent.window.opener.hj();
		parent.window.opener.document.getElementById("xsd_id").value = document.myform.id.value;
		parent.window.close();
	}				
</script>
</head>
<body>
<form name="myform" method="post">
<input type="hidden" name="selOption" value="">
<input type="hidden" name="id" value="<%=id %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">选择退货产品明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td><input type="checkbox" name="allCheck" onclick="selectAll();"></td>
		<td>产品名称</td>
		<td>规格</td>
		<td>采购价格</td>
		<td>数量</td>
		<td>备注</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		LsdProduct lsdProduct = (LsdProduct)it.next();
		String vl = StringUtils.nullToStr(lsdProduct.getProduct_id()) + "|" + StringUtils.nullToStr(lsdProduct.getProduct_name()) + "|" + StringUtils.nullToStr(lsdProduct.getProduct_xh()) + "|" + JMath.round(lsdProduct.getPrice()) + "|" + StringUtils.nullToStr(lsdProduct.getNums()) + "|" + JMath.round(lsdProduct.getCbj()) + "|" + StringUtils.nullToStr(lsdProduct.getQz_flag()) + "|" + JMath.round(lsdProduct.getKh_cbj());
		
		vl += "|" + StringUtils.nullToStr(lsdProduct.getSd());
		vl += "|" + StringUtils.nullToStr(lsdProduct.getGf());
		vl += "|" + StringUtils.nullToStr(lsdProduct.getDs());
		vl += "|" + StringUtils.nullToStr(lsdProduct.getBasic_ratio());
		vl += "|" + StringUtils.nullToStr(lsdProduct.getOut_ratio());
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><input type="checkbox" name="selOption" value="<%=vl %>"></td>
		<td><%=StringUtils.nullToStr(lsdProduct.getProduct_name()) %></td>
		<td><%=StringUtils.nullToStr(lsdProduct.getProduct_xh()) %></td>
		<td><%=JMath.round(lsdProduct.getPrice(),2) %></td>
		<td><%=StringUtils.nullToStr(lsdProduct.getNums()) %></td>
		<td><%=StringUtils.nullToStr(lsdProduct.getRemark()) %></td>
	</tr>
	
	<%
	}
	%>
</table>
<br>
<center><input type="button" name="button" value=" 确定 " class="css_button2" onclick="sel();"></center>
</form>
</body>
</html>
