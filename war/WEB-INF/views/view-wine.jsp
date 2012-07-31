<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false"%>

<div>	
	<jsp:include page="component-wine-view-detailed-data.jsp" />
	<sec:authorize ifAnyGranted="USER">										
	<div class="row">
		<a href="/wine/${wine['wineUrl']}/edit" class="big-edit-btn">Edit Wine</a>
	</div>
	</sec:authorize>
</div>		
