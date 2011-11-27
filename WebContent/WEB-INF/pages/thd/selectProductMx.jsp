<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("productPage");

String openerId = ParameterUtility.getStringParameter(request, "openerId","");

String product_name = StringUtils.nullToStr((String)VS.findValue("product_name"));
String product_kind = StringUtils.nullToStr((String)VS.findValue("product_kind"));
List kindList = (List)VS.findValue("kindList");

String sd = StringUtils.nullToStr((String)VS.findValue("sd"));
String basic_ratio = StringUtils.nullToStr((String)VS.findValue("basic_ratio"));
String out_ratio = StringUtils.nullToStr((String)VS.findValue("out_ratio"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择商品</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">
	function sel(sel_flag){
		var k = 0;
		var allCount = window.opener.allCount;  //当前存在的最大行序号
		//alert("当前存在的最大行序号" + allCount);
		var startCount = 0;
		for(var x=0;x<=allCount;x++){
			var id = window.opener.document.getElementById("product_id_"+x);
			var name = window.opener.document.getElementById("product_name_"+x);
	
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
		
		for(var i=1;i<document.myform.chk_id.length;i++){
					
			var o = document.myform.chk_id[i];
			if(o.checked){
			
				var vl = document.myform.chk_id[i].value;
				var arryStr = vl.split("|");
				
				var flag = false;
				for(var y=0;y<=allCount;y++){
					var s_id = window.opener.document.getElementById("product_id_"+y);
					
					if(s_id.value == arryStr[0]){  //该值已存在
						flag = true;
					}
				}			
			
				if(flag == false){
					var id = window.opener.document.getElementById("product_id_"+k);
					
					if(id == null){
						window.opener.addTr();
					}
					
					id = window.opener.document.getElementById("product_id_" + k);
					var xh = window.opener.document.getElementById("product_xh_" + k);
					var name = window.opener.document.getElementById("product_name_" + k);
					var cbj_obj = window.opener.document.getElementById("cbj_" + k);
					var khcbj_obj = window.opener.document.getElementById("kh_cbj_" + k);
					
					var qz_flag = window.opener.document.getElementById("qz_flag_" + k);
					var ygcbj_obj = window.opener.document.getElementById("ygcbj_" + k);
					var gf_obj = parent.window.opener.document.getElementById("gf_" + k);
					var ds_obj = parent.window.opener.document.getElementById("ds_" + k);
					var lsxj_obj = parent.window.opener.document.getElementById("lsxj_" + k);

					var sd_obj = parent.window.opener.document.getElementById("sd_" + k);
					var basic_ratio_obj = parent.window.opener.document.getElementById("basic_ratio_" + k);
					var out_ratio_obj = parent.window.opener.document.getElementById("out_ratio_" + k);
					var sfcytc_obj = parent.window.opener.document.getElementById("sfcytc_" + k);

					if(id != null) id.value = arryStr[0];
					if(xh != null) xh.value = arryStr[1];
					if(name != null) name.value = arryStr[2];
					if(qz_flag != null) qz_flag.value = arryStr[3];
					if(cbj_obj != null) cbj_obj.value = arryStr[4];
					
					if(khcbj_obj != null) khcbj_obj.value = arryStr[5];
					if(ygcbj_obj != null) ygcbj_obj.value = arryStr[6];
					if(gf_obj != null) gf_obj.value = arryStr[7];
					if(ds_obj != null) ds_obj.value = arryStr[8];
					if(lsxj_obj != null) lsxj_obj.value = arryStr[9];
					if(sd_obj != null) sd_obj.value = "<%=sd %>";
					if(basic_ratio_obj != null) basic_ratio_obj.value = "<%=basic_ratio %>";
					if(out_ratio_obj != null) out_ratio_obj.value = "<%=out_ratio %>";
					if(sfcytc_obj != null) sfcytc_obj.value = arryStr[10];
					
					k++;	
				}	
			}

		}
		
		if(sel_flag == "2"){
			window.close();
		}
	}	

	function clearAll(){
		document.myform.product_kind.value = "";
		document.myform.product_name.value = "";
	}

	function selectAll(){
		var flag = false;
		if(document.myform.allCheck.checked){
			flag = true;
		}
		for(var i=0;i<document.myform.elements.length;i++){
			var o = document.myform.elements[i];
			if(o.name == "chk_id"){
				if(flag == true){
					o.checked = true;
				}else{
					o.checked = false;
				}
			}
		}
	}	
</script> 

</head>

<body align="center">
<form name="myform" action="selThdProcMx.html" method="post">
<input type="hidden" name="openerId" id="openerId" value="<%=openerId %>">	
<input type="hidden" name="chk_id" value="">
<table width="100%" border="0" align="center" class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			商品：<input type="text" name="product_name" value="<%=product_name %>" size="20">&nbsp;&nbsp;
			类别：
			<select name="product_kind">
				<option value=""></option>
				<%
				if(kindList != null &&  kindList.size()>0){
					for(int i=0;i<kindList.size();i++){
						Map map = (Map)kindList.get(i);
						String id = StringUtils.nullToStr(map.get("id"));
						String name = StringUtils.nullToStr(map.get("name"));
						for(int k=0;k<id.length()-3;k++){
							name = "　" + name;
						}
				%>
				<option value="<%=id %>" <%if(product_kind.equals(id)) out.print("selected"); %>><%=name %></option>
				<%
					}
				}
				%>
			</select>&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>

<table width="100%" border="0" align="center" class="chart_info" cellpadding="0" cellspacing="0">
<tr>	
	<td width="80%" class="a2"  valign="top">
		<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0">
			<thead>
			<tr>
				<td><input type="checkbox" name="allCheck" onclick="selectAll();"></td>	
				<td>商品编号</td>
				<td>商品名称</td>
				<td>商品规格</td>
				<td>考核成本</td>
				<td>零售报价</td>
				<td>零售限价</td>
				<td>代理价</td>
				<td>分销限价</td>
			</tr>
			</thead>
			<%
			List list = results.getResults();
			Iterator it = list.iterator();
			
			while(it.hasNext()){
				Map map = (Map)it.next();
				
				double price = map.get("price")==null?0:((Double)map.get("price")).doubleValue();
				
				double fxxj = map.get("fxxj")==null?0:((Double)map.get("fxxj")).doubleValue();
				double fxbj = map.get("fxbj")==null?0:((Double)map.get("fxbj")).doubleValue();
				
				double lsxj = map.get("lsxj")==null?0:((Double)map.get("lsxj")).doubleValue();
				double lsbj = map.get("lsbj")==null?0:((Double)map.get("lsbj")).doubleValue();
				
				double khcbj = map.get("khcbj")==null?0:((Double)map.get("khcbj")).doubleValue();				
				double ygcbj = map.get("ygcbj")==null?0:((Double)map.get("ygcbj")).doubleValue();
				double gf = map.get("gf")==null?0:((Double)map.get("gf")).doubleValue();
				double dss = map.get("dss")==null?0:((Double)map.get("dss")).doubleValue();
				
				
				String vl = StringUtils.nullToStr(map.get("product_id")) + "|" + StringUtils.nullToStr(map.get("product_xh")) + "|"
							+ StringUtils.nullToStr(map.get("product_name")) + "|"
							+ StringUtils.nullToStr(map.get("qz_serial_num")) + "|"
							+ price + "|" + khcbj + "|" + ygcbj + "|" + gf + "|" + dss + "|" + lsxj + "|" + StringUtils.nullToStr(map.get("sfcytc"));
				
			%>
			<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
				<td><input type="checkbox" name="chk_id" value="<%=vl %>"></td>
				<td><%=StringUtils.nullToStr(map.get("product_id")) %></td>
				<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
				<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
				<td><%=JMath.round(khcbj,2) %></td>
				<td><%=JMath.round(lsbj,2) %></td>
				<td><%=JMath.round(lsxj,2) %></td>
				<td><%=JMath.round(fxbj,2) %></td>
				<td><%=JMath.round(fxxj,2) %></td>
			</tr>
			
			<%
			}
			%>
			<tr>
				<td class="page" colspan="9"><%=results.getPageScript() %></td>
			</tr>			
		</table>	
	</td>
</tr>
</table>
<center>
	<input type="button" name="buttonQd" value="确认并继续选择" onclick="sel('1');" class="css_button4">
	<input type="button" name="buttonQd" value="确认选择并关闭" onclick="sel('2');" class="css_button4">
	<input type="button" name="buttonQd" value=" 关闭 " onclick="window.close();" class="css_button2">
</center>
</form>	
</body>
</html>
