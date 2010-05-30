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

<html>
<head>
<title>选择商品</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">

	function sel(product_id,product_xh,product_name,qz_serial_num,cbj,khcbj,ygcbj,gf,ds,lsxj,sfcytc){
		var id = window.opener.document.getElementById("product_id_<%=openerId%>");
		var xh = window.opener.document.getElementById("product_xh_<%=openerId%>");
		var name = window.opener.document.getElementById("product_name_<%=openerId%>");
		var cbj_obj = window.opener.document.getElementById("cbj_<%=openerId%>");
		var khcbj_obj = window.opener.document.getElementById("kh_cbj_<%=openerId%>");
		
		var qz_flag = window.opener.document.getElementById("qz_flag_<%=openerId%>");
		var ygcbj_obj = window.opener.document.getElementById("ygcbj_<%=openerId%>");
		var gf_obj = parent.window.opener.document.getElementById("gf_<%=openerId%>");
		var ds_obj = parent.window.opener.document.getElementById("ds_<%=openerId%>");
		var lsxj_obj = parent.window.opener.document.getElementById("lsxj_<%=openerId%>");

		var sd_obj = parent.window.opener.document.getElementById("sd_<%=openerId%>");
		var basic_ratio_obj = parent.window.opener.document.getElementById("basic_ratio_<%=openerId%>");
		var out_ratio_obj = parent.window.opener.document.getElementById("out_ratio_<%=openerId%>");
		var sfcytc_obj = parent.window.opener.document.getElementById("sfcytc_<%=openerId%>");

		if(id != null) id.value = product_id;
		if(xh != null) xh.value = product_xh;
		if(name != null) name.value = product_name;
		if(qz_flag != null) qz_flag.value = qz_serial_num;
		if(cbj_obj != null) cbj_obj.value = cbj;
		if(khcbj_obj != null) khcbj_obj.value = khcbj;
		if(ygcbj_obj != null) ygcbj_obj.value = ygcbj;
		if(gf_obj != null) gf_obj.value = gf;
		if(ds_obj != null) ds_obj.value = ds;
		if(lsxj_obj != null) lsxj_obj.value = lsxj;
		if(sd_obj != null) sd_obj.value = "<%=sd %>";
		if(basic_ratio_obj != null) basic_ratio_obj.value = "<%=basic_ratio %>";
		if(out_ratio_obj != null) out_ratio_obj.value = "<%=out_ratio %>";
		if(sfcytc_obj != null) sfcytc_obj.value = sfcytc;
		
		window.close();	
	}

	function clearAll(){
		document.myform.product_kind.value = "";
		document.myform.product_name.value = "";
	}
</script> 

</head>

<body align="center">
<form name="myform" action="selThdProc.html" method="post">
<input type="hidden" name="openerId" id="openerId" value="<%=openerId %>">	
<table width="100%" border="0" align="center" class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择商品</b></td>
	</tr>
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

<table width="100%" border="0" align="center" class="chart_info" cellpadding="0" cellspacing="0" height="450">
<tr>	
	<td width="80%" class="a2"  valign="top">
		<table width="100%"  align="center"  class="chart_list" border="1" cellpadding="0" cellspacing="0">
			<thead>
			<tr>
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
				
			%>
			<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="左键点击选择" onclick="sel('<%=StringUtils.nullToStr(map.get("product_id")) %>','<%=StringUtils.nullToStr(map.get("product_xh")) %>','<%=StringUtils.nullToStr(map.get("product_name")) %>','<%=StringUtils.nullToStr(map.get("qz_serial_num")) %>','<%=JMath.round(price) %>','<%=JMath.round(khcbj) %>','<%=JMath.round(ygcbj) %>','<%=JMath.round(gf) %>','<%=JMath.round(dss) %>','<%=JMath.round(lsxj) %>','<%=StringUtils.nullToStr(map.get("sfcytc")) %>');">
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
				<td class="page" colspan="8"><%=results.getPageScript() %></td>
			</tr>			
		</table>	
	</td>
</tr>
</table>
</form>	
</body>
</html>
