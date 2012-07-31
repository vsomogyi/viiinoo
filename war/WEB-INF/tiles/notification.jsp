<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Notification start -->

<c:if test="${not empty message}"> 
	<div class="messageType">${message.messageText}</div>
</c:if>

<!-- Notification end -->