<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

ProductKind productKind = (ProductKind)VS.findValue("productKind");
List productKindList= (List)VS.findValue("productKindList");

String parent_id = StringUtils.nullToStr(productKind.getParent_id());
if(parent_id.equals("0")){
	parent_id = "";
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加商品</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if($("#id").val() == $("#parent_id").val()){
			alert("上层类别与商品类别相同，请重新选择！");
			return;
		}
		
		if(!InputValid(document.myform.kindName,1,"string",1,1,25,"商品类别名称")){	 return; }
		if(!InputValid(document.myform.ms,0,"string",0,1,500,"商品类别描述")){	 return; }

		window.opener.document.productKindForm.parent_id.value = document.myform.parent_id.value;
		window.opener.document.productKindForm.old_parent_id.value = document.myform.old_parent_id.value;
		window.opener.document.productKindForm.id.value = document.myform.id.value;
		window.opener.document.productKindForm.name.value = document.myform.kindName.value;
		window.opener.document.productKindForm.ms.value = document.myform.ms.value;
		window.opener.document.productKindForm.action = "updateProductKind.html";

		var parentId = document.myform.parent_id.value;

		if($("#old_parent_id").val() != $("#parent_id").val()){
			$.ajax({
				cache: false,
				url:"getChildKindNums.html",
				type: "POST",
				data:{kind_id:$("#id").val()},
				success: function(resText) {
					if(resText != "0"){
						alert("该类别下存在子类别，无法修改！请先移除子类别。");
						return;
					}else{
						$.ajax({
							cache: false,
							url:"checkSubProduct.html",
							type: "POST",
							data:{kind_id:parentId},
							success: function(result) {
								if(result == "true"){
									alert("该上层类别下存在商品，不能修改，请先移走该上层类别下的商品，再修改！");
									return;
								}else{
									if(window.confirm("确认提交修改吗！")){
										window.opener.document.productKindForm.submit();
										window.close();
									}
								}
							}
						});
					}
				}
			});
		}else{
			$.ajax({
				cache: false,
				url:"checkSubProduct.html",
				type: "POST",
				data:{kind_id:parentId},
				success: function(result) {
					if(result == "true"){
						alert("该上层类别下存在商品，不能修改，请先移走该上层类别下的商品，再修改！");
						return;
					}else{
						if(window.confirm("确认提交修改吗！")){
							window.opener.document.productKindForm.submit();
							window.close();
						}
					}
				}
			});
		}
	}
</script>
</head>
<body>
<form name="myform">
<input type="hidden" name="old_parent_id"  id="old_parent_id" value="<%=StringUtils.nullToStr(productKind.getParent_id()) %>">
<input type="hidden" name="id" id="id" value="<%=StringUtils.nullToStr(productKind.getId()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">修改商品类别</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">上层类别</td>
		<td class="a2"  width="85%">
			<select name="parent_id" id="parent_id" style="width:230px">
				<option value=""></option>
		<%
		if(productKindList != null && productKindList.size() > 0){
			for(int i=0;i<productKindList.size();i++){
				Map map = (Map)productKindList.get(i);
				
				String id = StringUtils.nullToStr(map.get("id"));
				String name = StringUtils.nullToStr(map.get("name"));	
				
				for(int k=0;k<id.length()-2;k++){
					name = "&nbsp;&nbsp;" + name;
				}
		%>
				<option value="<%=id %>" <%if(StringUtils.nullToStr(productKind.getParent_id()).equals(id)) out.print("selected"); %>><%=name %></option>
		<%
			}
		}
		%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="a1" width="15%">商品类别名称</td>
		<td class="a2" width="85%"><input type="text" name="kindName" value="<%=StringUtils.nullToStr(productKind.getName()) %>" style="width:230px"></td>
	</tr>
	<tr height="50">
		<td class="a1" width="15%">商品类别描述</td>
		<td class="a2" width="85%">
			<textarea rows="4" style="width:80%;" name="ms"><%=StringUtils.nullToStr(productKind.getMs()) %></textarea>
		</td>
	</tr>

	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="保 存" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
