<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <link rel="shortcut icon" href="<c:out value="${page.main_favicon}" />" type="image/x-icon" />
  <meta name="description" content="<c:out value="${page.main_description}" />" />
  <meta name="keywords" content="<c:out value="${page.main_keywords}" />" />
  <meta name="author" content="<c:out value="${page.main_author}" />" />
  <meta name="google-site-verification" content="<c:out value="${page.site_verification}" />" />
  <meta name="msvalidate.01" content="<c:out value="${page.bing_verification}" />" />
  <link rel="stylesheet" type="text/css" href="<c:out value="${page.main_style}" />" />
  <link rel="stylesheet" type="text/css" href="/custom/<c:out value="${page.custom_style}" />" />
  
  <c:if test="${not empty page.highlight_theme}">
    <link rel="stylesheet" type="text/css" href="assets/css/themes/<c:out value="${page.highlight_theme}" />" />
  </c:if>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="/custom/<c:out value="${page.custom_script}" />"></script>
  <script src="js/Chart.min.js"></script>
  <script src="js/custom.js"></script>
    
  <c:choose>
    <c:when test="${not empty page.main_title}">
  	  <title><c:out value="${page.main_title}" /></title>
    </c:when>
    <c:otherwise>
      <title>Java Servlets Application</title>
    </c:otherwise>
  </c:choose> 

