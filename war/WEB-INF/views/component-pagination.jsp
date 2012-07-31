<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div id="pagination" class="item row">
	<c:if test="${not empty cursorPrevious}">
		<a id="${selected}-previous" class="previous" href="/${selected}?cursor=${cursorPrevious}&order=${order}">Previous</a>
	</c:if>					

	<c:if  test="${not empty cursorNext}">
		<a id="${selected}-next" class="next" href="/${selected}?cursor=${cursorNext}&order=${order}">Next</a>
	</c:if>					
</div>
