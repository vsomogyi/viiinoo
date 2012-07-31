<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>


	<style type="text/css" media="screen">@import url(/stylesheets/edit-wine.css);</style>
	<script src="/js/vinofil.edit-wine.js"></script>

	<form:form action="/wine" modelAttribute="wineForm" >
	<div class="error" >${pictureError}</div>
	
		<form:hidden path="wine.url_name"/>
		<div class="row general-information">
    		<h2>General information</h2>
    		<div class="row field">
    			<div class="cell width-3 position-0 label"><label for="name" >Name:*</label></div>
    			<div class="cell width-9 position-3"><form:input path="wine.name" cssErrorClass="input-error" cssClass="validate[required,custom[onlyLetterNumberSpace]]" data-prompt-position="topRight:-100"/>
    			<form:errors path="wine.name" cssClass="error" /></div>
    		</div>
    		
    		<div class="row field winery-row">
    			<div class="cell width-3 position-0 label"><label for="winery" >Winery:*</label></div>
    			<div class="cell width-9 position-3">
	    			<div class="select-winery">
		    	        <form:select path="wineryName" cssErrorClass="input-error" cssClass="validate[required]" data-prompt-position="topRight:-100">
							<form:option value="" label="--Please Select"/>
							<form:options items="${wineryNames}" />
						</form:select>
		    			<form:errors path="wineryName" cssClass="error" />
		    		</div>
		    		<div class="new-winery">
		    			<form:input path="newWineryName" cssErrorClass="input-error" cssClass="validate[required,custom[onlyLetterSp]]" data-prompt-position="topRight:-100"/>
    					<form:errors path="newWineryName" cssClass="error" />
		    		</div>	
    			</div>
    			<div class="cell width-3 position-12">
    				<a class="add-field" href="#">new winery</a>
    			</div>
    		</div>
			
    		<div class="row field">
    			<div class="cell width-3 position-0 label"><label for="type" >Type:</label></div>
    			<div class="cell width-9 position-3">
    				<form:select path="type" cssClass="validate[custom[onlyLetterSp]]" data-prompt-position="topRight:-100">
						<form:options items="${types}" />
					</form:select>
    			</div>
    		</div>
    		
			<div class="row field">
    			<div class="cell width-3 position-0 label"><label for="vintage">Vintage:</label></div>
    			<div class="cell width-9 position-3"><form:input path="wine.vintage" cssErrorClass="input-error" cssClass="validate[future[NOW]]" data-prompt-position="topRight:-100"/>
    			<form:errors path="wine.vintage" cssClass="error" /> </div>
	 		</div>
			<div class="row field">
    			<div class="cell width-3 position-0 label"><label for="averagePrice">Price:</label></div>
    			<div class="cell width-9 position-3"><form:input path="wine.averagePrice" cssErrorClass="input-error" cssClass="validate[custom[positiveFloat]]" data-prompt-position="topRight:-100"/><div class="dollar">$</div>
    			<form:errors path="wine.averagePrice" cssClass="error" /></div>
	 		</div>
			<div class="row field">
    			<div class="cell width-3 position-0 label"><label for="alcohol">Alcohol:</label></div>
    			<div class="cell width-9 position-3"><form:input path="wine.alcohol" cssErrorClass="input-error" cssClass="validate[custom[positiveFloat],min[0],max[100]]" data-prompt-position="topRight:-100"/><div class="percent">%</div>
    			<form:errors path="wine.alcohol" cssClass="error" /></div>
	 		</div>
			<div class="row field">
    			<div class="cell width-3 position-0 label"><label for="bottleSize">Bottle Size:</label></div>
    			<div class="cell width-9 position-3"><form:input path="wine.productionDetails.bottleSize" cssErrorClass="input-error" cssClass="validate[custom[noSpecChar]]" data-prompt-position="topRight:-100"/>
    			<form:errors path="wine.productionDetails.bottleSize" cssClass="error" /></div>
    		</div>
				
    	</div>
    	
		<div class="row">
			<h2>Production details</h2>
			<div class="row production-header">				
				<div class="cell width-4 position-3 label"><label for="months">Months Aged</label></div>
				<div class="cell width-5 position-7 label"><label for="container">Container Type</label></div>
				<div class="cell width-4 position-12 label"><label for="comment" >Comments</label></div>
			</div>
			<%-- List testing code
			set import directives to test 
				<%@page import="java.util.List"%>
				<%@page import="com.alasdoo.entity.WineForm"%>
			<% List result = ((WineForm)request.getAttribute("wineForm")).getAgedList();
			System.out.println(result.size());%>
			<%=result.size() %> 
			--%>
			
			<c:forEach items="agedList" var="aged" varStatus="loop">
			<div class="row production-details">
				<div class="row field">	
			
					<div class="cell width-4 position-3">
						<form:input path="agedList[${loop.index}].months" cssErrorClass="input-error" cssClass="aged-months validate[custom[posInteger]]" data-prompt-position="topRight"/> Months in
					</div>
					
					<div class="cell width-5 position-7">
						<form:select path="agedList[${loop.index}].container" cssClass="container" cssErrorClass="input-error">
		    				<form:options items="${containers}"/>
		   				</form:select>
					</div>
					<div class="cell width-4 position-12">
						<form:input path="agedList[${loop.index}].comment" cssErrorClass="input-error" cssClass="aged-comment validate[custom[noSpecChar]]" data-prompt-position="topRight:-100"/>
					</div>
	
				</div>
				
				<div class="row cell position-3 multifield-error">
   					<form:errors path="agedList[${loop.index}].months" cssClass="error" />
   					<form:errors path="agedList[${loop.index}].comment" cssClass="error" />
   				</div>	
   			</div>
			</c:forEach> 	
   			
   			<div class="row">
   				<div class="cell width-13 position-3">
   					<a id="add-aging-line" href="#" class="add-new-line">Add another aging line</a>
   				</div>
   			</div>
   			
			<div class="row field">
    			<div class="cell width-3 position-0 label"><label for="amountProduced">Amount Produced:</label></div>
    			<div class="cell width-9 position-3"><form:input path="wine.productionDetails.amountProduced" cssErrorClass="input-error"  cssClass="validate[custom[noSpecChar]]" data-prompt-position="topRight:-100"/>
    			<form:errors path="wine.productionDetails.amountProduced" cssClass="error" /></div>
    		</div>
			
			<div class="row field">
    			<div class="cell width-3 position-0 label"><label for="closure">Closure type:</label></div>
    			<div class="cell width-9 position-3">
					<form:select path="wine.productionDetails.closure">
						<form:option value="" label="--Please Select"/>
						<form:options items="${closures}" />
					</form:select>
				</div>
    		</div>
		</div>

		<div class="row">
			<h2>Varietal composition</h2>
			<div class="row varietal-header">
				<div class="cell width-9 position-3 label"><label for="varietal">Varietal</label></div>
				<div class="cell width-4 position-12 label"><label for="percentage">Percentage</label></div>
			</div>
			
			
			<c:forEach items="compositionList" var="composition"  varStatus="loop">
				<div class="row varietal-composition">
					<div class="row field">
						<div class="cell width-9 position-3"><form:input path="compositionList[${loop.index}].varietal" name="varietal" cssErrorClass="input-error" cssClass="varietal validate[custom[noSpecChar]]" data-prompt-position="topRight:-100"/></div>
						<div class="cell width-4 position-12"><form:input path="compositionList[${loop.index}].percentage" cssErrorClass="input-error"  cssClass="varietal-percentage validate[custom[positiveFloat],min[0],max[100]]" data-prompt-position="topRight:-50"/><div class="percent">%</div></div>
					</div>
					<div class="row cell position-3 multifield-error">
						<form:errors path="compositionList[${loop.index}].percentage" cssClass="error" />
					</div>
				</div>
			</c:forEach>
			
			<div class="row">
   				<div class="cell width-13 position-3">
   					<a id="add-varietal-line" href="#" class="add-new-line">Add another varietal line</a>
   				</div>
   			</div>
   			
		</div>
	
	    <div class="row">
			<h2>Other details</h2>
			
			<div class="row field">
				<div class="cell width-3 position-0 label"><label for="notes">Note:</label></div>
				<div class="cell width-9 position-3"><form:textarea path="wine.notes" cssErrorClass="input-error" cssClass="validate[custom[noSpecChar]]" data-prompt-position="topRight:-100"/>
				<form:errors path="wine.notes" cssClass="error" /></div>
			</div>       	
	        	
        </div>
        
        <div class="row field additional-notes">
        	<c:forEach items="characterList" var="char"  varStatus="loop">
			
	        	<div class="cell width-3 position-0">
					<form:select path="characterList[${loop.index}].kind" cssErrorClass="input-error" cssClass="character-kind">
						<form:option value="" label="--Please Select"/>
						<form:options items="${characters}" />
					</form:select>
				</div>
				
				<div class="cell width-9 position-3">
	    	        <form:textarea path="characterList[${loop.index}].character" cssErrorClass="input-error" cssClass="validate[custom[noSpecChar]]" data-prompt-position="topRight:-100"/>
	        	</div>

	        </c:forEach>
       	</div>

        <div class="row">
   			<div class="cell width-9 position-3">
   				<a id="add-note-line" href="#" class="add-new-line">Add additional notes</a>
   			</div>
   		</div>
		
	</form:form>
