<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <hgroup class="Navigator">
	    <nav class="navbar" role="navigation">
	      <div class="container">
	        <ul class="nav">
			<c:forEach items="${navigator}" var="navi" varStatus="iterator">
			  <c:choose>
			    <c:when test="${navi.id == current_category_id}">
		          <li class="nav_item_selected"><a href="<c:out value="${navi.link}" />" ><c:out value="${navi.caption}" /></a></li>
			    </c:when>
			    <c:otherwise>
		          <li class="nav_item"><a href="<c:out value="${navi.link}" />" ><c:out value="${navi.caption}" /></a></li>
			    </c:otherwise>
			  </c:choose>
			</c:forEach>
	        </ul>
	      </div>
	    </nav>
    </hgroup>
