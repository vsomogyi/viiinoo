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
<div>
	<h1>Add wine review</h1>
    	
    	<form:hidden path="review.key"/>
    	
    	<div class="row field select-wine">
   			<div class="cell width-3 position-0 label"><label for="wineName" >Wine:</label></div>
   			<div class="cell width-13 position-3">
   				<form:select path="wineName" cssErrorClass="input-error" cssClass="validate[required]" data-prompt-position="topRight:-100">
					<form:option value="" label="--Please Select"/>
					<form:options items="${wineNames}" />
				</form:select>
   			</div>
   			<form:errors path="wineName" cssClass="error" />
   		</div>
   		
   		<div class="row">
			<div class="cell width-13 position-3">
				<a id="add-new-wine" href="#" class="add-new-line">Add new wine</a>
			</div>
		</div>

		<div class="row new-wine">
			<jsp:include page="component-edit-wine-form.jsp" />
		</div>
		<div class="row field">
   			<div class="cell width-3 position-0"><label for="name" >Score:*</label></div>
   			<div class="cell width-9 position-3 score-widget"><form:input path="review.score" cssClass="validate[required,custom[posInteger],min[1],max[5]]" data-prompt-position="topRight:-100"/>
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
   			<div class="cell width-9 position-3"><form:textarea path="review.reviewText" cssErrorClass="input-error" cssClass="validate[custom[noSpecChar]]" data-prompt-position="topRight:-100"/></div>
   		</div>
   		
   		<c:if test="${not empty reviewForm.review.date}">
   			<div class="label"><label for="reviewDate" >Creation date:</label></div>
			<div id="review-date" class="item-name">${reviewForm.review.date}</div>		
		</c:if>
   		
		<div class="row">		
			<div class="cell width-6 position-3"><input id="submit-btn" type="submit" value="Save" class="save-btn" /></div>
		</div>	 	
</div>
</form:form>