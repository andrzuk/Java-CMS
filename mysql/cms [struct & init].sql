-- phpMyAdmin SQL Dump
-- version 4.0.10.8
-- http://www.phpmyadmin.net
--
-- Host: 127.11.37.130:3306
-- Czas wygenerowania: 11 Mar 2015, 10:24
-- Wersja serwera: 5.5.41
-- Wersja PHP: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `cms`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `admin_functions`
--

CREATE TABLE IF NOT EXISTS `admin_functions` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `module` varchar(32) NOT NULL,
  `meaning` varchar(512) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `module` (`module`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `archives`
--

CREATE TABLE IF NOT EXISTS `archives` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `page_id` int(11) unsigned NOT NULL,
  `type` varchar(16) NOT NULL,
  `category_id` int(11) unsigned NOT NULL,
  `title` varchar(512) NOT NULL,
  `contents` longtext,
  `description` varchar(1024) DEFAULT NULL,
  `author_id` int(11) unsigned NOT NULL,
  `visible` tinyint(1) NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `page_id` (`page_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `categories`
--

CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) unsigned NOT NULL,
  `item_order` int(11) NOT NULL,
  `caption` varchar(128) NOT NULL,
  `link` varchar(1024) NOT NULL,
  `visible` tinyint(1) NOT NULL,
  `author_id` int(11) unsigned NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `page_id` int(11) unsigned NOT NULL,
  `nick` varchar(32) NOT NULL,
  `email` varchar(64) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `comment` longtext,
  `visible` tinyint(1) NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `page_id` (`page_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `configuration`
--

CREATE TABLE IF NOT EXISTS `configuration` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `key_name` varchar(64) NOT NULL,
  `key_value` text NOT NULL,
  `meaning` varchar(255) DEFAULT NULL,
  `field_type` tinyint(4) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `debug`
--

CREATE TABLE IF NOT EXISTS `debug` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `debug_key` varchar(30) NOT NULL,
  `debug_value` text NOT NULL,
  `saved` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `excludes`
--

CREATE TABLE IF NOT EXISTS `excludes` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `visitor_ip` varchar(20) NOT NULL,
  `active` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `visitor_ip` (`visitor_ip`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `images`
--

CREATE TABLE IF NOT EXISTS `images` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL,
  `file_size` int(11) NOT NULL,
  `width` int(11) NOT NULL,
  `height` int(11) NOT NULL,
  `author_id` int(11) unsigned NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `file_name` (`file_name`),
  KEY `fk_images_users` (`author_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `logins`
--

CREATE TABLE IF NOT EXISTS `logins` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `agent` varchar(255) NOT NULL,
  `user_ip` varchar(20) NOT NULL,
  `user_id` int(11) unsigned NOT NULL,
  `login` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `login_time` datetime NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `messages`
--

CREATE TABLE IF NOT EXISTS `messages` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nick` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `message` longtext,
  `visible` tinyint(1) NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pages`
--

CREATE TABLE IF NOT EXISTS `pages` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(16) NOT NULL,
  `category_id` int(11) unsigned NOT NULL,
  `title` varchar(512) NOT NULL,
  `contents` longtext,
  `description` varchar(1024) DEFAULT NULL,
  `author_id` int(11) unsigned NOT NULL,
  `visible` tinyint(1) NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  KEY `fk_pages_users` (`author_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `password` varchar(48) DEFAULT NULL,
  `first_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `last_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `email` varchar(128) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '3',
  `registered` datetime NOT NULL,
  `logged_in` datetime NOT NULL,
  `modified` datetime NOT NULL,
  `logged_out` datetime NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_login` (`login`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_roles`
--

CREATE TABLE IF NOT EXISTS `user_roles` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `function_id` int(11) unsigned NOT NULL,
  `access` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_function` (`user_id`,`function_id`),
  KEY `fk_roles_users` (`user_id`),
  KEY `fk_roles_functions` (`function_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `views`
--

CREATE TABLE IF NOT EXISTS `views` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `page_id` int(11) unsigned NOT NULL,
  `counter` int(11) unsigned NOT NULL,
  `visited` datetime NOT NULL,
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `page_id` (`page_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `visitors`
--

CREATE TABLE IF NOT EXISTS `visitors` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `visitor_ip` varchar(20) NOT NULL,
  `http_referer` text,
  `request_uri` text NOT NULL,
  `visited` datetime NOT NULL,
  UNIQUE KEY `id` (`id`),
  KEY `visitor_ip` (`visitor_ip`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Zrzut danych tabeli `admin_functions`
--

INSERT INTO `admin_functions` (`id`, `module`, `meaning`) VALUES
(1, 'admin', 'Admin Panel'),
(2, 'config', 'Konfiguracja'),
(3, 'styles', 'Wygląd'),
(4, 'categories', 'Kategorie'),
(5, 'pages', 'Podstrony'),
(6, 'images', 'Galeria'),
(7, 'users', 'Użytkownicy'),
(8, 'acl', 'Access Control List'),
(9, 'logins', 'Logowania'),
(10, 'comments', 'Komentarze'),
(11, 'messages', 'Wiadomości'),
(12, 'visitors', 'Odwiedziny'),
(13, 'excludes', 'Wykluczenia');

--
-- Zrzut danych tabeli `configuration`
--

INSERT INTO `configuration` (`id`, `key_name`, `key_value`, `meaning`, `field_type`, `active`, `modified`) VALUES
(1, 'service_name', 'JavaCMS', 'główna nazwa aplikacji', 1, 1, '2015-03-03 05:57:40'),
(2, 'main_title', 'Java Servlets Application', 'tytuł strony internetowej', 2, 1, '2015-03-16 07:29:13'),
(3, 'main_description', 'Aplikacja webowa na platformie OpenShift w technologii Java Servlets & JSP / JSTL', 'meta tag descriptions nagłówka strony', 2, 1, '2015-02-25 04:58:34'),
(4, 'main_keywords', 'Java, web, application, servlets, jsp, jstl, openshift, mvc, framework', 'meta dane keywords strony', 2, 1, '2015-02-25 05:01:15'),
(5, 'main_favicon', 'gallery/favicons/iconhost.ico', 'favicon strony', 1, 1, '2015-03-08 09:47:32'),
(6, 'main_footer', '&copy; 2015\r\n<a href="https://plus.google.com/113303165754486219878?rel=author" class="FooterLink" target="_blank">Andrzej Żukowski</a>\r\n▪\r\n<a href="http://mvc.net.pl" target="_blank">FineCMS</a>\r\n', 'footer strony', 2, 1, '2015-03-09 03:17:43'),
(7, 'main_author', 'application logic & design: Andrzej Żukowski', 'autor logiki biznesowej i designu serwisu', 1, 1, '2015-03-16 05:54:17'),
(8, 'main_style', 'css/default.css', 'głowny plik stylów strony', 1, 1, '2015-03-08 09:47:32'),
(9, 'custom_style', 'custom.css', 'dodatkowy plik stylów strony', 1, 1, '2015-03-08 09:47:32'),
(10, 'logo_image', 'images/java_logo.png', 'obrazek logo w nagłówku strony', 1, 1, '2015-02-25 05:41:35'),
(11, 'site_verification', '-YwX1hsGJi30rLk1AsM3xlQ41iZN08yc18FdQ5SpNcU', 'kod weryfikacji strony w narzędziach webmastera', 1, 1, '2015-02-25 05:41:35'),
(12, 'time_offset_active', 'true', 'przesunięcie czasowe daty serwera aktywne', 3, 1, '2015-03-11 10:53:25'),
(13, 'time_offset_hours', '5', 'przesunięcie czasowe daty serwera w godzinach', 1, 1, '2015-03-11 10:38:55'),
(14, 'date_year_first', 'true', 'format daty z rokiem na początku', 3, 1, '2015-03-12 05:16:42'),
(15, 'description_words_comments', '20', 'liczba wyrazów w skróconym opisie na liście komentarzy', 1, 1, '2015-03-12 10:44:26'),
(16, 'description_words_messages', '20', 'liczba wyrazów w skróconym opisie na liście wiadomości', 1, 1, '2015-03-12 05:21:12'),
(17, 'description_words_pages', '100', 'liczba wyrazów w skróconym opisie na liście stron', 1, 1, '2015-03-04 04:56:12'),
(18, 'description_words_search', '100', 'liczba wyrazów w skróconym opisie na liście znalezionych', 1, 1, '2015-03-04 06:27:58'),
(19, 'http_referer_length', '64', 'maksymalna długość http_referer na liście visitors', 1, 1, '2015-03-23 10:41:53'),
(20, 'preview_image_list_width', '100px', 'szerokość obrazków podglądu na liście', 1, 1, '2015-03-05 05:16:42'),
(21, 'preview_image_list_height', '100px', 'wysokość obrazków podglądu na liście', 1, 1, '2015-03-05 05:16:33'),
(22, 'code_highlight_theme', 'github.css', 'nazwa pliku stylu dla kolorowania składni kodu', 1, 1, '2015-03-13 06:41:15'),
(23, 'send_message_report', 'false', 'czy wysyłać mailem raporty o wydarzeniach w serwisie', 3, 1, '2015-03-12 07:52:17'),
(24, 'email_admin_address', 'andrzuk@gmail.com', 'adres e-mail administratora serwisu', 1, 1, '2015-03-03 05:29:12'),
(25, 'email_sender_address', 'cms_mailer@gazeta.pl', 'adres konta e-mailowego serwisu', 1, 1, '2015-03-03 04:45:13'),
(26, 'email_sender_host', 'smtp.poczta.gazeta.pl', 'host serwera pocztowego', 1, 1, '2015-03-03 04:51:44'),
(27, 'email_sender_user', 'cms_mailer', 'login konta pocztowego nadawcy', 1, 1, '2015-03-03 04:44:32'),
(28, 'email_sender_password', 'pwd_java_2015', 'hasło konta pocztowego nadawcy', 1, 1, '2015-03-03 04:45:49'),
(29, 'email_subject', 'Mail Manager - New comment was received', 'temat maila raportującego nadesłanie komantarza', 1, 1, '2015-03-03 04:27:07');

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `login`, `password`, `first_name`, `last_name`, `email`, `status`, `registered`, `logged_in`, `modified`, `logged_out`, `active`) VALUES
(1, 'admin', '86bf621e63216a5c5dc761c6560b72d5', 'Andrzej', 'Żukowski', 'andrzuk@wp.pl', 1, '2015-02-03 10:55:50', '2015-02-03 10:55:50', '2015-03-11 04:41:32', '2015-02-03 10:55:50', 1);

--
-- Zrzut danych tabeli `user_roles`
--

INSERT INTO `user_roles` (`id`, `user_id`, `function_id`, `access`) VALUES
(1, 1, 1, 1),
(2, 1, 2, 1),
(3, 1, 3, 1),
(4, 1, 4, 1),
(5, 1, 5, 1),
(6, 1, 6, 1),
(7, 1, 7, 1),
(8, 1, 8, 1),
(9, 1, 9, 1),
(10, 1, 10, 1),
(11, 1, 11, 1),
(12, 1, 12, 1),
(13, 1, 13, 1);

--
-- Zrzut danych tabeli `categories`
--

INSERT INTO `categories` (`id`, `parent_id`, `item_order`, `caption`, `link`, `visible`, `author_id`, `modified`) VALUES
(1, 0, 1, 'Index', '/home', 0, 1, '2015-02-24 13:38:46'),
(2, 0, 2, 'Contact', '/contact', 0, 1, '2015-02-24 13:38:43');

--
-- Zrzut danych tabeli `pages`
--

INSERT INTO `pages` (`id`, `type`, `category_id`, `title`, `contents`, `description`, `author_id`, `visible`, `modified`) VALUES
(1, 'standard', 1, 'Strona główna', '<article>Strona główna</article>', 'Strona startowa serwisu', 1, 1, '2015-03-10 10:34:14'),
(2, 'standard', 2, 'Kontakt', '<style>\r\ntd { vertical-align: top; }\r\ndiv.msg { margin-bottom: 20px; }\r\ndiv.adr { font-size: 1.0em; font-weight: bold; }\r\ndiv.map { padding-top: 0px; }\r\ndiv.panel-heading { margin: 15 0 15 0; }\r\n</style>\r\n\r\n<table width="100%" cellpadding="10" cellspacing="10" style="border: 0px;">\r\n<tr>\r\n<td width="20%">\r\n<div class="msg">Zachęcam Państwa do kontaktu z serwisem. Proszę śmiało do mnie pisać, korzystając z zamieszczonego tu formularza. Każda wiadomość będzie uważnie przeczytana i rozpatrzona.</div>\r\n<div class="adr">e-mail: <a href="mailto:andrzuk@tlen.pl">andrzuk@tlen.pl</a></div>\r\n<div class="adr">#GG: <a href="gg:5684331">5684331</a></div>\r\n</td>\r\n<td width="80%">\r\n<div class="map">\r\n<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d4867.701649478604!2d16.91226795315743!3d52.40937977092189!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0000000000000000%3A0x6201d17fe9c37f41!2sDZ+Bank+Polska+S.A.+Centrum+Bankowo%C5%9Bci+Korporacyjnej!5e0!3m2!1spl!2sus!4v1417506974043" width="100%" height="400" frameborder="0" style="border: #aaa 1px solid;"></iframe>\r\n</div>\r\n</td>\r\n</tr>\r\n</table>\r\n', 'strona kontaktowa serwisu', 1, 1, '2015-03-06 10:00:53');

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `archives`
--
ALTER TABLE `archives`
  ADD CONSTRAINT `fk_archives_pages` FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`);
  
--
-- Ograniczenia dla tabeli `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `fk_categories_users` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`);

--
-- Ograniczenia dla tabeli `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `fk_comments_pages` FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`);

--
-- Ograniczenia dla tabeli `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `fk_images_users` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`);

--
-- Ograniczenia dla tabeli `pages`
--
ALTER TABLE `pages`
  ADD CONSTRAINT `fk_pages_categories` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `fk_pages_users` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`);

--
-- Ograniczenia dla tabeli `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `fk_roles_functions` FOREIGN KEY (`function_id`) REFERENCES `admin_functions` (`id`),
  ADD CONSTRAINT `fk_roles_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Ograniczenia dla tabeli `views`
--
ALTER TABLE `views`
  ADD CONSTRAINT `views_pages` FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`);
  

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
