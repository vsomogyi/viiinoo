<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>


	<style type="text/css" media="screen">@import url(/stylesheets/image-list.css);</style>
	<style type="text/css" media="screen">@import url(/stylesheets/validationEngine.jquery.css);</style>
	<script src="/js/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/vinofil.edit-account.js" type="text/javascript"></script>

	<div class="error" >${pictureError}</div>
	<%-- System for hiding the continue param in link when continue url is empty --%>
	<c:choose>
	<c:when test="${not empty continue}">
		<c:set var="continueParam" value="?continue=${continue}"/>
	</c:when>
	<c:otherwise>
		<c:set var="continueParam" value=""/>	
	</c:otherwise>	
		</c:choose>
			<form:form action="/account${continueParam}" commandName="wineEnthusiast" method="post" enctype="multipart/form-data">
	
		<fieldset>	
	    <h1>Account information:</h1>
			
			<div class="row">
				<div class="cell width-5 position-0 avatar image-list">
					
					<div class="upload-image rich-image">
						<div class="fileinput-button">
							<div class="image-thumbnail preview">
							
							<c:choose>				
							<c:when test="${empty picture}">
							</c:when>					
							<c:otherwise>
							<div class="hide-parent">	
								<img alt="" src="${picture}" class="resize"/>
							</div>	
							</c:otherwise>
							</c:choose>		
							</div>			
							<label for="file">Upload profile picture</label>		
							<input type="file" name="file[0]"/>
						</div>			
					</div>
				</div>
			
			<div class="cell width-11 position-5 account-edit">
				
					<div class="row field">
						<div class="cell width-3 position-0 label"><label for="email">E-mail:</label></div>
						<div class="cell width-13 position-3 email-address">${wineEnthusiast.email}</div>
					</div>
					<div class="row field">
						<div class="cell width-3 position-0 label"><label for="name">Name:*</label></div>
						<div class="cell width-13 position-3"><form:input cssClass="validate[required,custom[onlyLetterNumberSpace]]" path="name" cssErrorClass="input-error" data-prompt-position="topRight:-100"/>
						<form:errors path="name" cssClass="error" /></div>
					</div>
					<div class="row field">
						<div class="cell width-3 position-0 label"><label for="website">Website:</label></div>
						<div class="cell width-13 position-3"><form:input cssClass="validate[custom[url]]" path="website" cssErrorClass="input-error" data-prompt-position="topRight:-100"/>
						<form:errors path="website" cssClass="error" /></div>
					</div>
					<div class="row field">
						<div class="cell width-3 position-0 label"><label for="address">Address:</label></div>
						<div class="cell width-13 position-3"><form:input path="address" cssClass="validate[custom[address]]" cssErrorClass="input-error" data-prompt-position="topRight:-100"/>
						<form:errors path="address" cssClass="error" /></div>
					</div>
					<div class="row field">
						<div class="cell width-3 position-0 label"><label for="bio">Bio:</label></div>
						<div class="cell width-13 position-3"><form:textarea cssClass="validate[custom[noSpecChar]]" path="bio" cssErrorClass="input-error" data-prompt-position="topRight:-100" />
						<form:errors path="bio" cssClass="error" /></div>
					</div>
					
				</div>
			</div>
		</fieldset> 		
		
		<div class="row">		
			<div class="cell width-6 position-5"><input id="submit-btn" type="submit" value="Save" class="save-btn" /></div>
		</div>
		
	</form:form>
