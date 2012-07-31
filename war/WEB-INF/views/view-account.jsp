<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

	    <h1>Account information:</h1>
			
			<div class="row">
				<div class="cell width-5 position-0 avatar">
					
					<c:choose>				
						<c:when test="${ empty user['userProfilePicture']}">
							<img alt="${user['userName']}" src="/images/avatar.png" />			
						</c:when>					
						<c:otherwise>
							<div class="row"><img alt="" src="${user['userProfilePicture']}" /></div>										
						</c:otherwise>					
					</c:choose>
			</div>
				<div class="cell width-11 position-5">
						
					<div class="row">
						<div class="cell width-6 position-0"><label for="name">Name:</label></div>
						<div id="name" class="cell width-10 position-6 item-name">${user['userName']}</div>
					</div>
					<div class="row">
						<div class="cell width-6 position-0"><label for="website">Website:</label></div>
						<div id="website" class="cell width-10 position-6 item-name">${user['userWebsite']}</div>
					</div>					
					<div class="row">
						<div class="cell width-6 position-0"><label for="bio">Bio:</label></div>
						<div id="bio" class="cell width-10 position-6 item-name">${user['userBio']}</div>
					</div>
					
					
				</div>
			</div>
			
			<div>	
				<jsp:include page="component-wine-list.jsp" />
			</div>

