<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Account start -->
<sec:authorize ifAnyGranted="USER">
<div class="account-menu">
	<span class="name"><a href="<c:url value="/account/${getLoggedInUser}"/>">${username}</a></span>
	<a href="<c:url value="/account"/>">Account Settings</a>
	<a href="<c:url value="/logout"/>">Logout</a>
</div>
</sec:authorize>
<!-- Account end -->