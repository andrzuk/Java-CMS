<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="TopRow">
	<span id="Logo">
		<a href="/home">
			<img src="gallery/images/java_logo.png" title="Home Page" alt="" />
		</a>
	</span>
	<span id="Logged">
		<c:choose>
			<c:when test="${page.logged == null}">
		      Nie jesteś zalogowany
			</c:when>
			<c:otherwise>
		      Zalogowany: <b><c:out value="${page.logged}" /></b> (<a href="/logout" alt="logout">Wyloguj</a>)
			</c:otherwise>
		</c:choose>
	</span>
	<span id="Tools">
		<span class="ToolItem">
			<c:choose>
				<c:when test="${page.logged == null}">
					<a href="/login" alt="login"><img src="images/key.png" title="Zaloguj" />Zaloguj</a>
				</c:when>
				<c:otherwise>
					<a href="/admin" alt="admin"><img src="images/access.png" title="Admin Panel" />Admin Panel</a>
				</c:otherwise>
			</c:choose>
		</span>
		<span class="ToolItem">
			<a href="/contact" alt="contact"><img src="images/page_edit.png" title="Kontakt" />Kontakt</a>
		</span>
	</span>
</div>

<div id="Search">
	<form action="/search" method="post">
		<input type="search" name="search_value" placeholder="Znajdź" style="width: 150px;" />
		<input type="submit" name="search_button" value="Szukaj" />
	</form>
</div>
