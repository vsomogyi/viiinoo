<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false"%>

<style type="text/css" media="screen">@import url(/stylesheets/wine-view.css);</style>
<style type="text/css" media="screen">@import url(/stylesheets/image-list.css);</style>
<script src="/js/vinofil.wine-view.js"></script>

<jsp:include page="component-wine-view-basic-data.jsp" />

<div class="clear"></div>
<div id="tabs-container">
	<ul>
		<li><a href="#wine-details">Details</a></li>
		<li><a href="#wine-reviews">Reviews</a></li>
		<li><a href="#wine-photos">Photos</a></li>
	</ul>
	
	<div id="wine-details">
		<div class="row">
			<h2>Characteristics of the wine</h2>
			<c:forEach items="${wine['wineCharacters']}" var="character" >
				<div class="cell width-3 position-0 label">${character.kind}:</div>
				<div class="cell width-9 position-3">${character.character}</div>
			</c:forEach>
		</div>
		
		<div class="row">
			<h2>Production details</h2>
				<div class="row production-aged">
				
				<div class="row production-header">	
					<div class="cell width-4 position-3"><label class="label" for="months">Months Aged:</label></div>
					<div class="cell width-5 position-7"><label class="label" for="container">Container Type:</label></div>
					<div class="cell width-4 position-12"><label class="label" for="comment" >Comments:</label></div>
				</div>
		
				<c:forEach items="${wine['wineAged']}" var="aged" >
					<div class="row">	
						<div id="months" class="cell width-4 position-3 item-name">${aged.months} <span class="">Months</span></div>
						<div id="container" class="cell width-5 position-7 item-name">${aged.container}</div>
						<div id="comment" class="cell width-4 position-12 item-name">${aged.comment}</div>
					</div>
				</c:forEach>
				
				</div>
				
				<div class="row">
					<div class="cell width-4 position-0"><label class="label" for="amountProduced">Amount Produced:</label></div>
					<div id="amount-produced" class="cell width-8 position-4 item-name">${wine['amountProduced']}</div>
				</div>
				<div class="row">
					<div class="cell width-4 position-0"><label class="label" for="closure">Closure type:</label></div>
					<div id="closure" class="cell width-8 position-4 item-name">${wine['wineClosure']}</div>
				</div>
		</div>
		
		
		<div class="row varietal-composition">
			<h2>Varietal composition</h2>
			
				<div class="row">
					<div class="cell width-9 position-3"><label class="label" for="varietal">Varietal:</label></div>
					<div class="cell width-4 position-12"><label class="label" for="percentage">Percentage:</label></div>
				</div>
				<c:forEach items="${wine['wineComposition']}" var="composition">
					<div class="row">
						<div id="varietal" class="cell width-9 position-3 item-name">${composition.varietal}</div>
						<div id="percentage" class="cell width-4 position-12 item-name">${composition.percentage} %</div>
					</div>
				</c:forEach>
		</div>
	</div>

	<!-- Ratings list -->
	<div id="wine-reviews">

		<div class="list-header row">
			<div class="cell width-7 position-0 first-header-elem">REVIEW COMMENT</div>
			<div class="cell width-3 position-9 center">USER</div>
			<div class="cell width-2 position-12 center">RATING</div>
		</div>

		<div class="list-body row">
			<c:forEach items="${wine['reviews']}" var="review">
				<div class="list-item row">

						<div class="cell width-7 position-0 rating-comment ellipsis">
						
							<a id="${wine['wineUrl']}-${review['reviewKey']}-details" href="/wine/${wine['wineUrl']}/review/${review['reviewKey']}/view">
								${review['reviewText']}
							</a>
						</div>
						
						<div class="cell width-3 position-9 center">
							<a href="/account/${review['reviewUserKey']}"> ${review['reviewUserName']} </a>
						</div>
				
	
						<div class="cell width-2 position-12 center">
							<a id="${wine['wineUrl']}-${review['reviewKey']}-details" href="/wine/${wine['wineUrl']}/review/${review['reviewKey']}/view">
								${review['reviewScore']}
							</a>	
						</div>
						
						<sec:authorize ifAnyGranted="USER">																	
						<div class="actions cell width-1 position-14">
							<a id="${wine['wineUrl']}-${review['reviewKey']}-edit" href="/wine/${wine['wineUrl']}/review/${review['reviewKey']}/edit" class="edit-btn"></a> 
						</div>
						<div class="actions cell width-1 position-15">
							<a id="${wine['wineUrl']}-${review['reviewKey']}-delete" href="/wine/${wine['wineUrl']}/review/${review['reviewKey']}/delete_confirmation" class="delete-btn"></a>
						</div>
						</sec:authorize>

				</div>
						
		</c:forEach>
		</div>
	</div>
	
	<!-- Photos-->
	<div id="wine-photos">
		<ul class="image-list">
		<c:forEach items="${wine['winePictures']}" var="picture">
			<li class="existing-image">
				<div class="image-thumbnail">
					<a href="${picture}" target="_blank">
						<img alt="${wine['wineName']}" src="${picture}" class="resize">
					</a>
				</div>
			</li>		
		</c:forEach>
		</ul>
	</div>
	<div class="clear"></div>
</div>