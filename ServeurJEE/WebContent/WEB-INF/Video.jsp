<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus" lang="en"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-focus" lang="en">
<!--<![endif]-->
<head>
<meta charset="utf-8" http-equiv="Cache-control" content="no-cache">


<title>Video</title>

<!-- Stylesheets -->
<!-- Web fonts -->
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400italic,600,700%7COpen+Sans:300,400,400italic,600,700">

<!-- Bootstrap and  CSS framework -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" id="css-main" href="assets/css/oneui.css">

<!-- You can include a specific file from css/themes/ folder to alter the default color theme of the template. eg: -->
<!-- <link rel="stylesheet" id="css-theme" href="assets/css/themes/flat.min.css"> -->
<!-- END Stylesheets -->
</head>

<body class="bg-image"
	style="background-image: url('assets/img/photos/photo17@2x.jpg');">

	<div>
		<video id="video" width="1366" height="768" style="display:block; margin: 0 auto;" controls>
			<source src="assets/img/videos/output.mp4"></source>
		</video>
	</div>

	<div class="push-10-t text-center animated fadeInUp">
		<h3 sty>Si la vidéo ne démarre pas, veuillez recharger la page au bout de quelques secondes.</h3>
	</div>


	<div class="push-10-t text-center animated fadeInUp">
		<small class="text-muted font-w600">Enseignant&copy;2018/2019</small>
	</div>




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

	<!-- Page JS Plugins -->
	<script
		src="assets/js/plugins/jquery-validation/jquery.validate.min.js"></script>

	<!-- Page JS Code -->
	<script src="assets/js/pages/base_pages_login.js"></script>
</body>
</html>