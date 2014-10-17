<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
	
<h1>
	Hello world!  
</h1>
<div style="text-align:right" ><a href="javascript:formSubmit()"> Logout</a></div>
<p>  The time on the server is ${serverTime}. </p>
<form:form action="generateReport.html" method="POST" commandName="reportForm" >
	Nombre:
	<form:input path="name"/><br/>
	<input type="submit" value="View" />
</form:form>

</body>
</html>
