<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

	<div class="row subheader">
		<div class="cell width-4 position-12">
			<a href="/wine/review" class="add-button"> new review</a>
		</div>
		<div class="cell width-12 position-0">
			<h2>Wines in diary</h2>
			
			<div class="description">
				What is the definition of a good wine? It should start and end with a smile.
			</div>
		</div>
	</div>
	<div>	
		<jsp:include page="component-wine-list.jsp" />
	</div>



