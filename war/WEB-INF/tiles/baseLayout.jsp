<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="DC.creator" content="ALAS doo"/>
		<meta name="DC.format" content="text/plain"/>
		<meta name="DC.language" content="en"/>
		<meta name="DC.date.modified" content="2011-2-5 17:12:24"/>
		<meta name="DC.date.created" content="2011-2-5 17:12:24"/>
		<meta name="DC.type" content="Page"/>
		<meta name="DC.distribution" content="Global"/>
		<meta name="robots" content="ALL"/>
		<meta name="distribution" content="Global"/>
		<link rel="canonical" href="http://viiinoo.com/" />
        
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
        
        <style type="text/css" media="screen">@import url(/stylesheets/reset.css);</style>
        <style type="text/css" media="screen">@import url(/stylesheets/decogrids-16.css);</style>
        <style type="text/css" media="screen">@import url(/stylesheets/main.css);</style>

        <style type="text/css" media="screen">@import url(/stylesheets/jquery-ui-1.8.13.custom.css);</style>
        <style type="text/css" media="screen">@import url(/stylesheets/vinofil.custom-ui.css);</style>
       
		<script src="/js/jquery-1.6.1.js"></script>
		<script src="/js/jquery-ui-1.8.13.custom.min.js"></script>
		<script src="/js/jquery.text-overflow.js"></script>
		<script src="/js/vinofil.main.js"></script>
		<script src="/js/vinofil.resize.js"></script>
         
        
       <!-- <script src="http://cdn.jquerytools.org/1.2.5/jquery.tools.min.js"></script> -->
        
    </head>
    <body>
        <div id="header" class="row">
        	<div id="header-inner">
       			<tiles:insertAttribute name="header" />
       		</div>
        </div>
        <div id="account" class="row">
        	<div id="account-inner">
       			<tiles:insertAttribute name="account" />
       		</div>
        </div>
        
       	<div id="content-wrapper">
        	<div class="row">
        	
	        	<div id="content" class="cell width-11 position-0">
	        		<div id="menu">
	        			<tiles:insertAttribute name="menu" />
	        		</div>
	        		
	        		<tiles:insertAttribute name="notification" />
	        		
	        		<tiles:insertAttribute name="body" />
	        	</div>
	        	
	        	<div id="sidebar" class="cell width-5 position-11">
	        		<tiles:insertAttribute name="sidebar" />
	        	</div>
        	</div>
        </div>
        <div id="footer" class="row">
        	<tiles:insertAttribute name="footer" />
        </div>
    </body>
</html>
