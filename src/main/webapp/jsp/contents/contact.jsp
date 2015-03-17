<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;"><c:out value="${site.title}" /></h1>

<p>
	<article><c:out value="${site.contents}" escapeXml="false" /></article>
</p>

<h2 id="user_message" style="text-align: center; color: #999;">Napisz wiadomość:</h2>

<p>
	<form action="/message" method="post">
		<table align="center" width="75%" cellpadding="5" cellspacing="5">
			<tr>
				<td>
					Imię lub nick:<br><input type="text" name="nick" required />
				</td>
				<td>
					E-mail:<br><input type="email" name="email" required />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					Wiadomość:<br><textarea name="message" rows="10" required></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="hidden" name="visible" value="passive" />
					<input type="submit" name="send" value="Wyślij" />
				</td>
			</tr>
		</table>
	</form>
</p>