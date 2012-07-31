<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page isELIgnored="false"%>

	<style type="text/css" media="screen">@import url(/stylesheets/validationEngine.jquery.css);</style>
	<script src="/js/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(document).ready(function () {
			
			$('#content form').validationEngine('attach', {promptPosition : "topRight"});
		
		});
	</script>

   	<h1>Add wine</h1>
   	
	<form:form action="/wine" method="post" commandName="deletePictureForm" enctype="multipart/form-data">
	<fieldset>	
	
			<jsp:include page="component-edit-wine-form.jsp" />
			<jsp:include page="component-multiplePictureUpload.jsp" />
		
			<div class="row">		
				<div class="cell width-6 position-3"><input id="submit-btn" type="submit" value="Save" class="save-btn" /></div>
			</div>
	</fieldset>
	</form:form>

