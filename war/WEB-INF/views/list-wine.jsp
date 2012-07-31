<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false"%>


	<div class="row subheader">
		<sec:authorize ifAnyGranted="USER">
					
		<div class="cell width-4 position-12">
			<a href="./wine" class="add-button"> new wine</a>
		</div>
		</sec:authorize>
		<div class="cell width-12 position-0">
			<h2>List of wines</h2>
			<div class="description">
				If food is the body of good living, wine is its soul.
			</div>
		</div>
	</div>
	<div>
		<jsp:include page="component-wine-list.jsp" />
	</div>
	