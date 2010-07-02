<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<script>
	alert("操作成功！");
	parent.window.close();
	parent.opener.document.myform.submit();
</script>