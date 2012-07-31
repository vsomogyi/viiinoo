<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page isELIgnored="false"%>

<style type="text/css" media="screen">@import url(/stylesheets/edit-winery.css);</style>
<style type="text/css" media="screen">@import url(/stylesheets/validationEngine.jquery.css);</style>
<script src="/js/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
<script src="/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$(document).ready(function () {
		
		$('#deletePictureForm').validationEngine('attach', {promptPosition : "topRight"});
	
	});
</script>

<form:form action="/winery" commandName="deletePictureForm" enctype="multipart/form-data">
<fieldset>
	<form:form action="/winery" commandName="winery">
	
	<h1>Add Winery</h1>

		<form:hidden path="url_name"/>
	
		<div class="row">
		
			<div class="row field">
	   			<div class="cell width-3 position-0 label"><label for="name" >Name:*</label></div>
	   			<div class="cell width-9 position-3"><form:input path="name" cssErrorClass="input-error" cssClass="validate[required,custom[onlyLetterNumberSpace]]" data-prompt-position="topRight:-100"/>
	   			<form:errors path="name" cssClass="error" /></div>
	   		</div>
			<div class="row field">
	   			<div class="cell width-3 position-0 label"><label for="email" >E-mail:</label></div>
	   			<div class="cell width-9 position-3"><form:input path="email" cssErrorClass="input-error" cssClass="validate[custom[email]]" data-prompt-position="topRight:-100"/>
	   			<form:errors path="email" cssClass="error" /></div>
	   		</div>
			<div class="row field">
	   			<div class="cell width-3 position-0 label"><label for="website" >Website:</label></div>
	   			<div class="cell width-9 position-3"><form:input path="website" cssErrorClass="input-error" cssClass="validate[custom[url]]" data-prompt-position="topRight:-100"/>
	   			<form:errors path="website" cssClass="error" /></div>
	   		</div>
			<div class="row field">
	   			<div class="cell width-3 position-0 label"><label for="address" >Address:</label></div>
	   			<div class="cell width-9 position-3"><form:input path="address" cssErrorClass="input-error" cssClass="validate[custom[address]]" data-prompt-position="topRight:-100"/>
	   			<form:errors path="address" cssClass="error" /></div>
	   		</div>
	   		<div class="row field">
	   			<div class="cell width-3 position-0 label"><label for="city" >City:</label></div>
	   			<div class="cell width-9 position-3"><form:input path="city" cssErrorClass="input-error"  cssClass="validate[custom[onlyLetterSp]]" data-prompt-position="topRight:-100"/>
	   			<form:errors path="city" cssClass="error" /></div>
	   		</div>
	   		<div class="row field">
	   			<div class="cell width-3 position-0 label"><label for="phone" >Phone:</label></div>
	   			<div class="cell width-9 position-3"><form:input path="phone" cssErrorClass="input-error"  cssClass="validate[custom[phone]]" data-prompt-position="topRight:-100"/>
	   			<form:errors path="phone" cssClass="error" /></div>
	   		</div>
	   		<div class="row field">
	   			<div class="cell width-3 position-0 label"><label for="about" >About:</label></div>
	   			<div class="cell width-9 position-3"><form:textarea path="about" cssErrorClass="input-error" cssClass="validate[custom[noSpecChar]]" data-prompt-position="topRight:-100"/>
	   			<form:errors path="about" cssClass="error" /></div>
	   		</div>
	   	</div>	
	   	
	 
	</form:form>
  	<jsp:include page="component-multiplePictureUpload.jsp" />
		
	<div class="row">		
		<div class="cell width-6 position-3"><input id="submit-btn" type="submit" value="Save" class="save-btn" /></div>
	</div>
</fieldset>
</form:form>
