<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center; color: #d00;">
	<c:out value="${site.title}" />
	<div class="Modified">
		<span class="Date"><img src="images/date.png" /><c:out value="${site.modified_short}" /></span>
		<span class="User"><img src="images/author.png" /><c:out value="${site.login}" /></span>
	</div>
	<div class="Comments">
		<a href="#comments">Komentarzy: <b><c:out value="${count.comments_count}" /></b></a>		
	</div>
</h1>

<p>
	<article><c:out value="${site.contents}" escapeXml="false" /></article>
</p>

<div class="Separator">&nbsp;</div>

<h2 style="text-align: center; color: #aaa;">Komentarze:</h2>

<p id="comments">
	<c:forEach items="${comments}" var="comment" varStatus="iterator">
		<div class="comment">
			<span class="comment_date"><img src="images/author.png" /><c:out value="${comment.nick}" /></span> ~
			<span class="comment_email"><a href="mailto:<c:out value="${comment.email}" />"><c:out value="${comment.email}" /></a></span>
			<span class="comment_nick"><img src="images/date.png" /><c:out value="${comment.modified_short}" /></span> ~
			<span class="comment_ip"><c:out value="${comment.ip}" /></span>
			<section class="comment_text"><c:out value="${comment.comment}" escapeXml="true" /></section>
		</div>
	</c:forEach>
</p>

<h2 id="comment" style="text-align: center; color: #aaa;">Napisz komentarz:</h2>

<p>
	<form action="/comment" method="post">
		<table align="center" width="75%" cellpadding="5" cellspacing="5">
			<tr>
				<td>
					Imię:<br><input type="text" name="nick" required />
				</td>
				<td>
					E-mail:<br><input type="email" name="email" required />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					Komentarz:<br><textarea name="comment" rows="10" required></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="hidden" name="page_id" value="<c:out value="${site.id}" />" />
					<input type="hidden" name="visible" value="passive" />
					<input type="submit" name="send" value="Wyślij" />
				</td>
			</tr>
		</table>
	</form>
</p>