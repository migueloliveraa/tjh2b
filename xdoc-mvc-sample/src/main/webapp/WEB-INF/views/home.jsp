<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<p>  The time on the server is ${serverTime}. </p>
<form:form action="generateReport.html" method="POST" commandName="reportForm" >
	Nombre:
	<form:input path="name"/><br/>
	<input type="submit" value="View" />
</form:form>

</body>
</html>
