<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page isELIgnored="false"%>

<style type="text/css" media="screen">@import url(/stylesheets/view-winery.css);</style>
<style type="text/css" media="screen">@import url(/stylesheets/image-list.css);</style>
<script src="/js/vinofil.view-winery.js"></script>


<div class="row winery-info">
	<div class="avatar-picture cell width-3 position-0">
			<c:choose>				
				<c:when test="${ empty winery['wineryProfilePicture']}">
				<img alt="${winery['wineryName']}" src="/images/winery.png" />
				</c:when>					
				<c:otherwise>
				<img alt="${winery['wineryName']}" src="${winery['wineryProfilePicture']}">									
				</c:otherwise>					
			</c:choose>
	</div>

	<div class="cell width-13 position-3">
		<div class="row">
			<div id="name" class="cell width-16 position-0 winery-name">${winery['wineryName']}</div>
		</div>
		<div class="row">
			<div class="cell width-3 position-0"><label class="label" for="email">E-mail:</label></div>
			<div id="email" class="cell width-10 position-3 item-name">${winery['wineryEmail']}</div>
		</div>
		<div class="row">
			<div class="cell width-3 position-0"><label class="label" for="website">Website:</label></div>
			<div id="website" class="cell width-10 position-3 item-name">${winery['wineryWebsite']}</div>
		</div>
		<div class="row">
			<div class="cell width-3 position-0"><label class="label" for="phone">Phone:</label></div>
			<div id="phone" class="cell width-10 position-3 item-name">${winery['wineryPhone']}</div>
		</div>
		<div class="row">
			<div class="cell width-3 position-0"><label class="label" for="address">Address:</label></div>
			<c:if test="${winery['wineryAddress'] != ''}">
				<div id="address" class="cell width-10 position-3 item-name">${winery['wineryAddress']}, ${winery['wineryCity']}</div>
			</c:if>
		</div>
	</div>
</div>
<div class="row">
	<div id="about" class="item-name">${winery['wineryAbout']}</div>
</div>		

<div class="clear"></div>
<div id="tabs-container">
	<ul>
		<li><a href="#wine-list">Wines</a></li>
		<li><a href="#winery-photos">Photos</a></li>
	</ul>

	<div id="wine-list">
		<div class="row wine-list">
			<jsp:include page="component-wine-list.jsp" />
		</div>
	</div>
	
	<div id="winery-photos">
			<ul class="image-list">
			<c:forEach items="${winery['wineryPictures']}" var="picture">
				<li class="existing-image">
					<div class="image-thumbnail">
						<a href="${picture}" target="_blank">
							<img alt="${winery['wineryName']}" src="${picture}" class="resize">
						</a>
					</div>
				</li>		
			</c:forEach>
			</ul>
	</div>
</div>

<sec:authorize ifAnyGranted="USER">											
<div class="row">		
	<a id="${winery['wineryUrl']}-edit" href="/winery/${winery['wineryUrl']}/edit" class="big-edit-btn">Edit Winery</a>
</div>
</sec:authorize>