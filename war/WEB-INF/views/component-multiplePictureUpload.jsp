<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false"%>

<style type="text/css" media="screen">@import url(/stylesheets/image-list.css);</style>

<script src="/js/jquery.tmpl.js"></script>

<script id="template-upload" type="text/x-jquery-tmpl">
    <li class="existing-image rich-image template-upload{{if error}} error-image{{/if}}">
        <div class="image-thumbnail preview"></div>
        <!-- div class="name">\${name}</div>
        <div class="size">\${sizef}</div -->
        {{if error}}
            <ul>
				<li></li>
				<li>Name: \${name}</li>
				<li>Size: \${sizef}</li>
				<li class="error-message">Error: 
                {{if error === 'maxFileSize'}}File is too big
                {{else error === 'minFileSize'}}File is too small
                {{else error === 'acceptFileTypes'}}Filetype not allowed
                {{else error === 'maxNumberOfFiles'}}Max number of files exceeded
                {{else}}\${error}
                {{/if}}
				</li>
            </ul>
		{{else}}	
 		<div class="image-input">
		</div>
        {{/if}}
		<div class="status">
		</div>
		<div class="button-area">
		    <div class="cancel image-button">
    	    	<label>Cancel</label>
			</div>
		</div>
   </li>
</script>

<script src="/js/vinofil.multiplePictureUpload.js"></script>


<!-- Picture list -->		
	<div class="row">
		<h2>Upload pictures</h2>
		<div class="cell width-15 position-0 picture-container">

			<ul class="image-list">
				<c:forEach var="item" varStatus="current" begin="0" end="${5 - fn:length(pictureList)}">		
				<li class="upload-image">
					<div class="fileinput-button">
						<label for="file">Picture :</label>
						<input type="file" name="file"/>
					</div>
				</li>
				</c:forEach>
				
				<c:forEach items="${pictureList}" var="picture" varStatus="current">
				<li class="existing-image">
					<div class="image-thumbnail">
						<img alt="${objectName}" src="${picture}" class="resize">
					</div>
					<div class="status"></div>
					<div class="button-area">
						<div class="default-select image-button">
							<label for="radio">Default</label><form:radiobutton path="defaultPicture" value="${picture}"/>
						</div>
						<div class="del-checkbox image-button">
							<label for="checkbox">Delete</label><form:checkbox path="picUrl" value="${picture}" />
						</div>
					</div>
				</li>
				</c:forEach>
			</ul>

		</div>
	</div>
