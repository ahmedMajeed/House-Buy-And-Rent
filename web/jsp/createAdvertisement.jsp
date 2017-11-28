<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="Models.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Create Advertisement</title>
	</head>
<body>
	<form action="AddAdvertisement" method="POST">
		<label>Title</label>
		<input type="text" name="Title" />
		<label>Size</label>
		<input type="number" name="Size" />
		<label>Floor</label>
		<input type="number" name="Floor" />
		
		<label>Status</label>
		<select name="Status">
			<%
				ArrayList<Status> statuses = (ArrayList)request.getAttribute("Statuses");
				for(Status status : statuses){
			%>
			<option value="<%=status.ID%>"><%=status.Name %></option>
			
			<%	}
			%>
		
		</select>
		
		<label>Type</label>
		<select name="Status">
			<%
				ArrayList<Type> types = (ArrayList)request.getAttribute("Types");
				for(Type type : types){
			%>
			<option value="<%=type.ID%>"><%=type.Name %></option>
			
			<%	}
			%>
		
		</select>
		
		<label>Description</label>
		<textarea name="Description"></textarea>
		
	</form>
</body>
</html>