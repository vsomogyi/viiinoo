<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

	<style type="text/css" media="screen">@import url(/stylesheets/edit-review.css);</style>
	<style type="text/css" media="screen">@import url(/stylesheets/validationEngine.jquery.css);</style>
	<script src="/js/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/vinofil.score-widget.js"></script>
	<script src="/js/vinofil.edit-review.js"></script>

<form:form action="/wine/review"  commandName="reviewForm" enctype="multipart/form-data">
	<fieldset>
	<h1>Edit wine review</h1>
    	
    	<form:hidden path="review.key"/>
    	<form:hidden path="wineUrl"/>
 <%-- Existing wine details --%>   	
    	<fieldset>
	<legend>Wine details</legend>

<div class="error">${pictureError}</div>
	
		<div class="row">
			<div class="wine-picture cell width-5 position-0">
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
					<div id="name" class="cell width-16 position-0 wine-name">
						<a href="/wine/${wine['wineUrl']}">
							${wine['wineName']} ${wine['wineVintage']}
						</a>
					</div>
				</div>
				<div class="row">
					<div class="cell width-10 position-0">
						<div class="row">
							<div class="cell width-6 position-0"><label class="label" for="wineryName">Winery:</label></div>
							<div id="wineryName" class="cell width-10 position-6 item-name">${wine['wineryName']}</div>
						</div>
						
						<div class="row">
							<div class="cell width-6 position-0"><label class="label" for="type">Type:</label></div>
							<div id="type" class="cell width-10 position-6 item-name">${wine['wineType']}</div>
						</div>
					</div>
					<div class="cell width-6 position-10">
						<div class="item-name row rating-${wine['wineAverageRating']} rating-icon rating-value" id="${wine['wineUrl']}-rating"><em>${wine['wineAverageRating']}</em> / 5 (${wine['wineNumberOfReviews']})</div>
					</div>
				</div>
			</div>
		</div>
	
	</fieldset>
<%-- Edit review scores --%>			

		<div class="row field">
   			<div class="cell width-3 position-0"><label for="name" >Score:*</label></div>
   			<div class="cell width-9 position-3 score-widget"><form:input path="review.score" cssErrorClass="input-error" cssClass="validate[required,custom[posInteger],min[1],max[5]]" data-prompt-position="topRight:-100"/>
   			<form:errors path="review.score" cssClass="error" /></div>
   		</div>
		<div class="row field">
   			<div class="cell width-3 position-0"><label for="name" >Aroma:</label></div>
   			<div class="cell width-9 position-3 score-widget"><form:input path="review.aromaScore" cssErrorClass="input-error" cssClass="validate[custom[posInteger],min[1],max[5]]" data-prompt-position="topRight:-100"/>
   			<form:errors path="review.aromaScore" cssClass="error" /></div>
   		</div>
   		<div class="row field">
   			<div class="cell width-3 position-0"><label for="name" >Taste:</label></div>
   			<div class="cell width-9 position-3 score-widget"><form:input path="review.tasteScore" cssErrorClass="input-error" cssClass="validate[custom[posInteger],min[1],max[5]]" data-prompt-position="topRight:-100"/>
   			<form:errors path="review.tasteScore" cssClass="error" /></div>
   		</div>
   		<div class="row field">
   			<div class="cell width-3 position-0"><label for="name" >Color:</label></div>
   			<div class="cell width-9 position-3 score-widget"><form:input path="review.colorScore" cssErrorClass="input-error" cssClass="validate[custom[posInteger],min[1],max[5]]" data-prompt-position="topRight:-100"/>
   			<form:errors path="review.colorScore" cssClass="error" /></div>
   		</div>
   		<div class="row field">
   			<div class="cell width-3 position-0"><label for="name" >Review notes:</label></div>
   			<div class="cell width-9 position-3"><form:textarea path="review.reviewText" cssErrorClass="input-error"  cssClass="validate[custom[noSpecChar]]" data-prompt-position="topRight:-100"/></div>
   		</div>
   		
   		<c:if test="${not empty reviewForm.review.date}">
   			<div class="label"><label for="reviewDate" >Creation date:</label></div>
			<div id="review-date" class="item-name">${reviewForm.review.date}</div>		
		</c:if>
   		
		<div class="row">		
			<div class="cell width-6 position-3"><input id="submit-btn" type="submit" value="Save" class="save-btn" /></div>
		</div>	 	
		
	</fieldset>
</form:form>