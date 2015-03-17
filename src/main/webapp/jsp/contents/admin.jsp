<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1 style="text-align: center;">${page['title']} &nbsp; <a href="/home"><img alt="close" src="images/exit.png" title="Zamknij"></a></h1>

<div id="AdminPanel">

<h3 class="Title">Statystyka</h3>

    <table align="center" cellpadding="25" cellspacing="20" style="border: 0px dotted #fff;">
      <tr class="AdminStats">
		<c:forEach items="${statistics}" var="item">
		  <td>
			<span class="Module"><a href="<c:out value="${item.link}" />"><c:out value="${item.title}" /></a></span>
			<br />
			<span class="Actives"><c:out value="${item.actives}" /></span>
			<span class="Separate">/</span>
			<span class="All"><c:out value="${item.all}" /></span>
		  </td>
		</c:forEach>
      </tr>
    </table>

<h3 class="Title">Moduły</h3>
	
    <table align="center" cellpadding="25" cellspacing="20" style="border: 0px dotted #fff;">
      <tr class="AdminModules">
        <td>
          <a href="/config"><img src="images/config.png" alt="control panel" title="Konfiguracja" /><br />Konfiguracja</a>
        </td>
        <td>
          <a href="/categories"><img src="images/folder_page.png" alt="control panel" title="Kategorie" /><br />Kategorie</a>
        </td>
        <td>
          <a href="/pages"><img src="images/page.png" alt="control panel" title="Podstrony" /><br />Podstrony</a>
        </td>
        <td>
          <a href="/comments"><img src="images/reports.png" alt="control panel" title="Komentarze" /><br />Komentarze</a>
        </td>
        <td>
          <a href="/messages"><img src="images/mail_message.png" alt="control panel" title="Wiadomości" /><br />Wiadomości</a>
        </td>
      </tr>
    </table>
    <table align="center" cellpadding="25" cellspacing="20" style="border: 0px dotted #fff;">
      <tr class="AdminModules">
        <td>
          <a href="/images"><img src="images/picture.png" alt="control panel" title="Galeria" /><br />Galeria</a>
        </td>
        <td>
          <a href="/users"><img src="images/user.png" alt="control panel" title="Użytkownicy" /><br />Użytkownicy</a>
        </td>
        <td>
          <a href="/acl"><img src="images/acl.png" alt="control panel" title="ACL" /><br />ACL</a>
        </td>
        <td>
          <a href="/visitors"><img src="images/internet.png" alt="control panel" title="Odwiedziny" /><br />Odwiedziny</a>
        </td>
        <td>
          <a href="/excludes"><img src="images/list_checked.png" alt="control panel" title="Wykluczenia" /><br />Wykluczenia</a>
        </td>
      </tr>
    </table>
    
</div>
    