<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false"%>

<style type="text/css" media="screen">@import url(/stylesheets/wine-view-basic.css);</style>

<div class="row wine-info">
	<div class="avatar-picture cell width-5 position-0">
		<c:choose>				
			<c:when test="${ empty wine['wineProfilePicture']}">
				<img alt="${wine['wineName']}" src="/images/winePic.png" />
			</c:when>					
			<c:otherwise>
				<img alt="${wine['wineName']}" src="${wine['wineProfilePicture']}">									
			</c:otherwise>					
		</c:choose>
	</div>

	<div class="cell width-11 position-5">
		<div class="row">
			<div id="name" class="cell width-16 position-0 wine-name">${wine['wineName']} ${wine['wineVintage']}</div>
		</div>
		<div class="row">
			<div class="cell width-10 position-0">
				<div class="row">
					<div class="cell width-4 position-0"><label class="label" for="wineryName">Winery:</label></div>
					<div id="wineryName" class="cell width-12 position-4 item-name">${wine['wineryName']}</div>
				</div>
				
				<div class="row">
					<div class="cell width-4 position-0"><label class="label" for="type">Type:</label></div>
					<div id="type" class="cell width-12 position-4 item-name">${wine['wineType']}</div>
				</div>
				<div class="row">
					<div class="cell width-4 position-0"><label class="label" for="alcohol">Alcohol:</label></div>
					<div id="alcohol" class="cell width-12 position-4 item-name">${wine['wineAlcohol']}</div>
				</div>
				<div class="row">
					<div class="cell width-4 position-0"><label class="label" for="bottleSize">Bottle:</label></div>
					<div id="bottle-size" class="cell width-12 position-4 item-name">${wine['wineBottleSize']}</div>
				</div>
				<div class="row">
					<div class="cell width-4 position-0"><label class="label" for="averagePrice">Price:</label></div>
					<div id="average-price" class="cell width-12 position-4 item-name">${wine['wineAveragePrice']}</div>
				</div>
			</div>
			<sec:authorize ifAnyGranted="USER">				
			<div id="${wine['wineUrl']}-rating" class="cell width-5 position-10 review-box item-name rating-${wine['wineAverageRating']} rating-icon rating-value">
				<em>${wine['wineAverageRating']}</em> / 5 (${wine['wineNumberOfReviews']}) <div><a href="/wine/review?id=${wine['wineUrl']}" class="add-button"> new review</a></div>
			</div>
			</sec:authorize>
		</div>
	</div>
</div>

<div class="row wine-info">
	<div class="cell width-12 position-0 wine-notes">
		${wine['wineNotes']}
	</div>
</div>