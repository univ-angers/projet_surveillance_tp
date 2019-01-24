<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus" lang="fr"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-focus" lang="fr"> <!--<![endif]-->
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="<c:url value="assets/css/style.css"/>">
	<link rel="icon" type="image/png" href="assets/img/favicons/favicon2.png">
	<!-- Stylesheets -->
	<!-- Web fonts -->
	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400italic,600,700%7COpen+Sans:300,400,400italic,600,700">
	
	<!-- Bootstrap and  CSS framework -->
	<link rel="stylesheet" href="<c:url value="assets/css/bootstrap.min.css"/>">
	<link rel="stylesheet" id="css-main" href="<c:url value="assets/css/oneui.css"/>">
	
	<!-- You can include a specific file from css/themes/ folder to alter the default color theme of the template. eg: -->
	<!-- <link rel="stylesheet" id="css-theme" href="assets/css/themes/flat.min.css"> -->
	<!-- END Stylesheets -->
	
	<!-- OneUI Core JS: jQuery, Bootstrap, slimScroll, scrollLock, Appear, CountTo, Placeholder, Cookie and App.js -->
	<script src="assets/js/core/jquery.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	<script src="assets/js/core/jquery.slimscroll.min.js"></script>
	<script src="assets/js/core/jquery.scrollLock.min.js"></script>
	<script src="assets/js/core/jquery.appear.min.js"></script>
	<script src="assets/js/core/jquery.countTo.min.js"></script>
	<script src="assets/js/core/jquery.placeholder.min.js"></script>
	<script src="assets/js/core/js.cookie.min.js"></script>
	<script src="assets/js/app.js"></script>
	
	<!-- Page Plugins -->
	<script src="assets/js/plugins/chartjs/Chart.min.js"></script>
	
	<!-- Page JS Code -->
	<script src="assets/js/pages/base_pages_dashboard_v2.js"></script>
	<script>
	jQuery(function () {
		// Init page helpers (CountTo plugin)
		App.initHelpers('appear-countTo');
	});
	</script>
	<!-- END header.jsp -->