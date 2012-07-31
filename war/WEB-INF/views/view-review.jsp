<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false"%>
	
	<style type="text/css" media="screen">@import url(/stylesheets/view-review.css);</style>
	<script src="/js/vinofil.view-review.js"></script>

	<jsp:include page="component-wine-view-basic-data.jsp" />

	<div class="table row">

		<div class="row table-header">
			<div class="cell width-7 position-0 first-header-elem">REVIEW NOTE</div>
			<div class="cell width-2 position-7 center">AROMA</div>
			<div class="cell width-2 position-9 center">TASTE</div>
			<div class="cell width-2 position-11 center">COLOR</div>
			<div class="cell width-3 position-13 center">OVERALL RATING</div>
		</div>
	
		<div class="row review-information">
			<div class="cell width-7 position-0 rating-comment">${review['reviewText']}</div>
			<div class="cell width-2 position-7 center">
				<div class="rating-icon rating-value rating-${review['reviewAromaScore']}">
					<em>${review['reviewAromaScore']}</em> / 5
				</div>	
			</div>
			<div class="cell width-2 position-9 center">
				<div class="rating-icon rating-value rating-${review['reviewTasteScore']}">
					<em>${review['reviewTasteScore']}</em> / 5
				</div>
			</div>
			<div class="cell width-2 position-11 center">
				<div class="rating-icon rating-value rating-${review['reviewColorScore']}">
					<em>${review['reviewColorScore']}</em> / 5
				</div>
			</div>
			<div class="cell width-3 position-13 center overall-rating">
				<div class="rating-icon rating-value rating-${review['reviewScore']}">
					<em>${review['reviewScore']}</em> / 5
				</div>
			</div>
		</div>
		
		<div class="row review-information">
			<div class="cell width-7 position-0">
				<div class="label">REVIEWER:</div>
				<a href="/account/${review['reviewUserKey']}"> ${review['reviewUserName']} </a>
			</div>
			<div class="cell width-6 position-7">
				<div class="label">DATE:</div>
				<div class="date">${review['reviewDate']}</div>
			</div>
			<sec:authorize ifAnyGranted="USER">	
			<c:if test="${getLoggedInUser == review['reviewUserKey']}">			
							
			<div class="cell width-3 position-13">
				<a id="${wine['wineUrl']}-${review['reviewKey']}-edit" class="edit-btn" href="/wine/${wine['wineUrl']}/review/${review['reviewKey']}/edit">edit</a>
				<a id="${wine['wineUrl']}-${review['reviewKey']}-delete" class="delete-btn" href="/wine/${wine['wineUrl']}/review/${review['reviewKey']}/delete_confirmation"></a>	
			</div>
			</c:if>	
			</sec:authorize>	
		</div>
		
	</div>
