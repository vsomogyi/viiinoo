<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Header start -->
	<div class="cell width-11 position-0">
		<div class="logo">
			<a href="/"><img src="/images/logo.png" alt="viiinoo" /></a>
		</div>
		
		<div class="header-search">
			<form action="/search" method="post">
		    	<input class="search-field" name="searchString"></input>
		   		<input class="search-btn" type="submit" value="Search" />
		    </form>
		</div>
	</div>
	
	<div class="header-menu cell width-3 position-11">
		<a href="/browse">Browse</a>
		
		<sec:authorize ifAnyGranted="USER">
			<div class="new-panel-bg"></div>
			<div class="new-panel">
				<a id="create-new-review" href="/wine/review">new review</a>
				<a id="create-new-wine" href="/wine">new wine</a>
				<a id="create-new-winery" href="/winery">new winery</a>	
			</div>
		
			<a id="create-new-button" href="/wine/review">Add New</a>
		
		</sec:authorize>
		
	</div>
	
	<div class="personal-tools">
		<c:choose>	
			<c:when test="${not empty username}"> 
				<a class="account" href="<c:url value="/account"/>" >
					${username}
					<span class="arrow"></span>
				</a>
			</c:when>
			<c:otherwise>
				<a class="login" href="<c:url value="/login"/>">
					Log In
				</a>
			</c:otherwise>
		</c:choose>
	</div>
	
<!-- Header end -->