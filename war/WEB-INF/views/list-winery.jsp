<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false"%>


    <style type="text/css" media="screen">@import url(/stylesheets/list-winery.css);</style>
	
	<div class="row subheader">

		<div class="cell width-12 position-0">
			<h2>List of wineries</h2>
			
			<div class="description">
				Good wine is a good familiar creature if it be well used.
			</div>
		</div>
		<sec:authorize ifAnyGranted="USER">
					<div class="cell width-4 position-12">	
			<a href="./winery" class="add-button"> new winery</a>
		</div>
		</sec:authorize>
	</div>


	<div class="list-header row">
		<div class="cell width-11 position-0 first-header-elem">WINERY NAME & DESCRIPTION</div>
		<div class="cell width-5 position-11">CONTACT</div>
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
				
					<div class="cell width-8 position-3" id="${winery['wineryUrl']}">
						<div class="row winery-name"><a href="/winery/${winery['wineryUrl']}">${winery['wineryName']}</a></div>
						<div class="row winery-notes">${winery['wineryAbout']}</div>
					</div>
					
					<div class="cell width-4 position-11 winery-contact">
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
					<sec:authorize ifAnyGranted="USER">
						<div class="actions cell width-1 position-15">
							<a id="${winery['wineryUrl']}-edit" href="/winery/${winery['wineryUrl']}/edit" class="edit-btn"></a>
							<a id="${winery['wineryUrl']}-delete" href="/winery/${winery['wineryUrl']}/delete_confirmation" class="delete-btn"></a>
						</div>
					</sec:authorize>
				</div>
			</div>
		</c:forEach>

		<jsp:include page="component-pagination.jsp" />
		

	</div>	
	
