<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false"%>

    <style type="text/css" media="screen">@import url(/stylesheets/review-list.css);</style>
    <style type="text/css" media="screen">@import url(/stylesheets/wine-list.css);</style>
	<script src="/js/vinofil.wine-list.js"></script>

	<div class="list-header row">
		<div class="cell width-10 position-0 first-header-elem">WINE DETAILS & RATINGS</div>
		<div class="cell width-6 position-10">
			<div class="orderby">
				<span>Order by:</span>
				<span><a href="?order=WINECREATIONDATE" class="selected-order">Wines</a></span>
				<span><a href="?order=REVIEWCREATIONDATE">Reviews</a></span>
			</div>
			
		</div>
	</div>
	<div class="list-body">
		<c:forEach items="${reviewItems}" var="reviewItem">
			<div class="list-item">
				<div class="row wine-info">
					
					<!-- Wine details with overall score -->
					<div class="avatar-picture cell width-3 position-0">
					<c:choose>				
						<c:when test="${ empty reviewItem['wineProfilePicture']}">
							<img alt="${reviewItem['wineName']}" src="/images/winePic.png" />
						</c:when>					
						<c:otherwise>
							<img alt="${reviewItem['wineName']}" src="${reviewItem['wineProfilePicture']}">									
						</c:otherwise>					
					</c:choose>
					</div>
					<div class="cell width-7 position-3" id="${reviewItem['wineUrl']}">
						<div class="row wine-name"><a href="/wine/${reviewItem['wineUrl']}">${reviewItem['wineName']}</a></div>
						<div class="row wine-winery">by <a href="/winery/${reviewItem['wineryUrl']}">${reviewItem['wineryName']}</a></div>
						<div class="row wine-notes ellipsis">${reviewItem['wineNotes']}</div>
					</div>
					<div class="cell width-3 position-10 rating-value ${reviewItem['wineStyle']}" id="${reviewItem['wineUrl']}-style">${reviewItem["wineStyle"]}</div>
					<div class="cell width-3 position-13 rating-value rating-icon rating-${reviewItem['wineAverageRating']}" id="${reviewItem['wineUrl']}-rating"><em>${reviewItem['wineAverageRating']}</em> / 5 (${reviewItem['wineNumberOfReviews']})</div>
					
				</div> <!-- wine-info -->
				
				<div class="wine-reviews table">
				
					<div class="row">
						<c:choose>
							<c:when test="${selected != 'wines'  && selected != 'wineries'}">
								<div class="cell width-13 position-3 table-header">
									<div class="cell width-13 position-0 first-header-elem">REVIEW COMMENT</div>
									<div class="cell width-3 position-13 center">RATING</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="cell width-13 position-3 table-header">
									<div class="cell width-8 position-0 first-header-elem">REVIEW COMMENT</div>
									<div class="cell width-3 position-9 center">USER</div>
									<div class="cell width-3 position-13 center">RATING</div>
								</div>
							</c:otherwise>
							
						</c:choose>
					</div>
					
					<!-- Ratings list -->
					<div class="ratings row wine-review-ratings">
						<c:forEach items="${reviewItem['reviews']}" var="review">
							<div class="row">
								<div class="cell width-13 position-3 rating-cell">
									<!--  rating start-->							
									<sec:authorize ifAnyGranted="USER">	
										 <%-- The logic for security testing should be in the controller (somehow...) --%>
										 <c:if test="${getLoggedInUser == review['reviewUserKey']}">			
											<div class="actions">
												<a id="${reviewItem['wineUrl']}-${review['reviewKey']}-" href="/wine/${reviewItem['wineUrl']}/review/${review['reviewKey']}/edit">Edit</a>									
											</div>
										 </c:if> 
									</sec:authorize>
									<!--  rating end -->
										
									<c:choose>
										<c:when test="${selected == 'wines' || selected == 'wineries'}">
											<div class="cell width-8 position-0 rating-comment ellipsis">
										</c:when>
										<c:otherwise>
											<div class="cell width-13 position-0 rating-comment">
										</c:otherwise>
									</c:choose>
									<a id="${reviewItem['wineUrl']}-${review['reviewKey']}-details" href="/wine/${reviewItem['wineUrl']}/review/${review['reviewKey']}/view">
										${review['reviewText']}
									</a>
									</div>
									
									<c:if test="${selected == 'wines' || selected == 'wineries'}">
										<div class="cell width-3 position-9 center rating-user">
											<a href="/account/${review['reviewUserKey']}"> ${review['reviewUserName']} </a>
										</div>
									</c:if>
			
									<div class="cell width-3 position-13 center">
										<a id="${reviewItem['wineUrl']}-${review['reviewKey']}-details" href="/wine/${reviewItem['wineUrl']}/review/${review['reviewKey']}/view">
											${review['reviewScore']}
										</a>	
									</div>
								</div>
								
								
							</div>
						</c:forEach>
						<c:if test="${reviewItem['wineNumberOfReviews'] > 5}">
							<div class="row">
								<div class="cell width-13 position-3 more-reviews-cell center"><a href="/wine/${reviewItem['wineUrl']}#wine-reviews">More reviews...</a></div>
							</div>
						</c:if>
						<sec:authorize ifAnyGranted="USER">							
							<div class="row">
								<div class="cell width-13 position-3 new-review-cell">
									<a class="add-button" href="/wine/review?id=${reviewItem['wineUrl']}"> new review</a>
								</div>
							</div>
						</sec:authorize>
					</div>
					
				</div>
				<div class="clear"></div>
	
			</div><!-- list-item -->		
		</c:forEach>
		
		
	</div>
	
	<jsp:include page="component-pagination.jsp" />
