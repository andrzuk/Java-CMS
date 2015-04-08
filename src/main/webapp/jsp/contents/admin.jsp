<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<div id="AdminPanel">

<h3 class="Title">Statystyka</h3>

    <div class="AdminStats">
      <c:forEach items="${statistics}" var="item">
        <span class="StatsElement">
		  <span class="Module"><a href="<c:out value="${item.link}" />"><c:out value="${item.title}" /></a></span>
		  <br />
		  <span class="Actives"><c:out value="${item.actives}" /></span>
		  <span class="Separate">/</span>
		  <span class="All"><c:out value="${item.all}" /></span>
        </span>
      </c:forEach>
    </div>

<h3 class="Title">Moduły</h3>
	
    <div class="AdminModules">
      <c:forEach items="${modules}" var="item">
        <span class="ModulesElement">
          <a href="<c:out value="${item.link}" />"><img src="<c:out value="${item.image}" />" alt="" title="<c:out value="${item.title}" />" /><br /><c:out value="${item.title}" /></a>
        </span>
      </c:forEach>
    </div>
    
</div>
