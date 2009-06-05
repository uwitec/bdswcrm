<%@ taglib uri="/webwork" prefix="ww" %>
<%@ page import="java.util.*" %>
<div>
<%
List messages = (List)session.getAttribute("messages");
session.removeAttribute("messages");
if(messages != null && !messages.isEmpty()){
	for(int i=0;i<messages.size();i++){
		String message = (String)messages.get(i);
		out.print("<font color=red><li>" + message + "</li></font>");
	}
}
%>
</div>
