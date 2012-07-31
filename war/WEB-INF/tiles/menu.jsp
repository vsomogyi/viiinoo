<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Menu start -->
<ul class="navigation" id="main-nav">
	<li id="portaltab-diary" class="<c:if test="${selected == 'diary'}">selected</c:if>" >
		<a href="<c:url value="/diary"/>">Diary</a>
	</li>
	<li id="portaltab-wines" class="<c:if test="${selected == 'wines'}">selected</c:if>" >
		<a href="<c:url value="/wines"/>" >Wines</a>
	</li>
	<li id="portaltab-wineries" class="<c:if test="${selected == 'wineries'}">selected</c:if>" >
		<a href="<c:url value="/wineries"/>" >Wineries</a>
	</li>
</ul>
<!-- Menu end -->


 


