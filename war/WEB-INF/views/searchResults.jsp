<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>



<div class="list-header row">
		<div class="cell width-10 position-0 first-header-elem">RESULT</div>
		<div class="cell width-6 position-10">DETAILS</div>
	</div>
    
	
	<div class="list-body">
		<c:forEach items="${wineries}" var="winery">
			<div class="list-item">
			
				<div class="winery-info row">
				
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
				
					<div class="cell width-7 position-3" id="${winery['wineryUrl']}">
						<div class="row winery-name"><a href="/winery/${winery['wineryUrl']}">${winery['wineryName']}</a></div>
						<div class="row winery-notes">${winery['wineryAbout']}</div>
					</div>
					
					<div class="cell width-5 position-10">
						<c:if test="${winery['wineryWebsite'] != ''}">
							<div class="row winery-website"><a href="${winery['wineryWebsite']}">${winery['wineryWebsite']}</a></div>
						</c:if>
						<c:if test="${winery['wineryEmail'] != ''}">
							<div class="row winery-email"><a href="mailto:${winery['wineryEmail']}">${winery['wineryEmail']}</a></div>
						</c:if>
						<c:if test="${winery['wineryAddress'] != ''}">
							<div class="row winery-address">${winery['wineryAddress']}, ${winery['wineryCity']}</div>
						</c:if>
						<c:if test="${winery['wineryPhone'] != ''}">
							<div class="row winery-phone">${winery['wineryPhone']}</div>
						</c:if>
					</div>
					
					<div class="actions cell width-1 position-15">
						<a id="${winery['wineryUrl']}-edit" href="/winery/${winery['wineryUrl']}/edit" class="edit-btn"></a>
						<a id="${winery['wineryUrl']}-delete" href="/winery/${winery['wineryUrl']}/delete_confirmation" class="delete-btn"></a>
					</div>
				</div>
			</div>
		</c:forEach>
		<div class="list-body">
		<c:forEach items="${wines}" var="wine">
			<div class="list-item">
				<div class="wine-info row">
				
					<div class="avatar-picture cell width-3 position-0">
					<c:choose>				
						<c:when test="${ empty wine['wineProfilePicture']}">
						<img alt="${wine['wineName']}" src="/images/winePic.png" />
						</c:when>					
						<c:otherwise>
						<img alt="${wine['wineName']}" src="${wine['wineProfilePicture']}">									
						</c:otherwise>					
					</c:choose>
					</div>
					
					
					<div class="cell width-7 position-3" id="${wine['wineUrl']}">
						<div class="row wine-name"><a href="/wine/${wine['wineUrl']}">${wine['wineName']}</a></div>
						<div class="row wine-winery">by <a href="/winery/${wine['wineryName']}">${wine['wineryName']}</a></div>
						<div class="row wine-notes ellipsis">${wine['wineNotes']}</div>
					</div>
					<div class="cell width-3 position-10 ${wine['wineStyle']}" id="${wine['wineUrl']}-style">${wine["wineStyle"]}</div>
					<div class="cell width-3 position-13 rating-icon rating-${wine['wineAverageRating']}" id="${wine['wineUrl']}-rating"><em>${wine['wineAverageRating']}</em> / 5</div>
					
					<div class="actions cell width-1 position-15">
						<a id="${wine['wineUrl']}-edit" href="/wine/${wine['wineUrl']}/edit" class="edit-btn"></a>
						<a id="${wine['wineUrl']}-delete" href="/wine/${wine['wineUrl']}/delete_confirmation" class="delete-btn"></a>
					</div>
				</div>
			</div>

		</c:forEach>
		
		<c:if test="${not empty didYouMean }">  
	Did you mean <a href="/search?searchString=${didYouMean}">${didYouMean} </a>?

	</c:if>
	<c:choose>	
		<c:when test="${ hitsNum == 1}">						
			Found 1 result.
		</c:when>
		<c:when test="${ hitsNum > 1}">						
			Found ${hitsNum}  result.
		</c:when>
		<c:otherwise>
		Found 0 results.
		</c:otherwise>
	</c:choose>
	</div>
</div>
	

