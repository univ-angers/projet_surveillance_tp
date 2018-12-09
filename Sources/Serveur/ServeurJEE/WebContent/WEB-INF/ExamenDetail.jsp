<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus" lang="en"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-focus" lang="en">
<!--<![endif]-->
<head>
<meta charset="utf-8">

<title>Détail de l'examen</title>



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

	<div class="content overflow-hidden">



		<div class="col-sm-6 col-md-4 col-lg-3">
			<a class="block block-rounded block-link-hover2" href="#"
				data-toggle="modal" data-target="#modal-terms">
				<div
					class="block-content block-content-full text-center bg-gray-dark ribbon ribbon-bookmark ribbon-crystal">

					<div
						class="item item-2x item-circle bg-crystal-op push-20-t push-20 animated fadeIn"
						data-toggle="appear" data-offset="50" data-class="animated fadeIn">
						<i class="si si-chemistry text-white-op"></i>
					</div>

				</div>
				<div class="block-content">

					<div class="font-s12 text-center push">Log</div>
				</div>
			</a>
		</div>



		<c:if test="${not empty video}">
			<div class="col-sm-6 col-md-4 col-lg-3">
				<a class="block block-rounded block-link-hover2" href="#"
					data-toggle="modal" data-target="#modal-terms2">
					<div
						class="block-content block-content-full text-center bg-primary ribbon ribbon-bookmark ribbon-crystal">

						<div
							class="item item-2x item-circle bg-crystal-op push-20-t push-20 animated fadeIn"
							data-toggle="appear" data-offset="50"
							data-class="animated fadeIn">
							<i class="si si-camcorder text-white-op"></i>
						</div>

					</div>
					<div class="block-content">

						<div class="font-s12 text-center push">Vidéo</div>
					</div>
				</a>
			</div>
		</c:if>








	</div>



	<div class="push-10-t text-center animated fadeInUp">
		<small class="text-muted font-w600">Enseignant&copy;2018/2019</small>
	</div>
	<!--Text content -->
	<div class="modal fade" id="modal-terms" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-popout">
			<div class="modal-content">
				<div class="block block-themed block-transparent remove-margin-b">
					<div class="block-header bg-primary-dark">
						<ul class="block-options">
							<li>
								<button data-dismiss="modal" type="button">
									<i class="si si-close"></i>
								</button>
							</li>
						</ul>
						<h3 class="block-title">Log</h3>
					</div>
					<div class="block-content" style="overflow: scroll;">

						<%if(request.getAttribute("log")!=null){ %>
						<p><%=request.getAttribute("log") %></p>
						<%} %>

					</div>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	<!-- END Text content -->

	<!--Video content -->
	<div class="modal fade" id="modal-terms2" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-popout">
			<div class="modal-content">
				<div class="block block-themed block-transparent remove-margin-b">
					<div class="block-header bg-primary-dark">
						<ul class="block-options">
							<li>
								<button data-dismiss="modal" type="button">
									<i class="si si-close"></i>
								</button>
							</li>
						</ul>
						<h3 class="block-title">Video content</h3>
					</div>
					<div class="block-content">


						<p>
							Date de création:
							<%=request.getAttribute("date_exam") %></p>
						<p>
							Auteur:
							<%=session.getAttribute("nomUtilisateur")%>
							<%=session.getAttribute("prenomUtilisateur") %></p>
						<p>Description: preuve de triche d'un étudiant au cours de la
							session d'examen</p>


						<video autoplay="" loop="" muted=""
							style="margin: auto; position: absolute; z-index: -1; top: 75%; left: 50%; transform: translate(-50%, -75%); visibility: visible; opacity: 1; width: 300px; height: auto;">
							<source src="assets/img/videos/output.mp4" type="video/mp4">
							<source src="assets/img/videos/hero_tech.webm" type="video/webm">
							<source src="<%=request.getAttribute("video") %>"
								type="video/ogg">
						</video>
					</div>

				</div>
				<div class="modal-footer">

					<a
						class="btn btn-rounded btn-noborder btn-lg btn-success push-10-r push-5 animated fadeInLeft"
						data-toggle="appear" data-class="animated fadeInLeft"
						<% if (request.getAttribute("id_examen") == null) {%>
						href=<%="\"video?id_etud=" +request.getAttribute("id_etud") + "\""%>
						<% } else {%>
						href=<%="\"video?id_etud=" +request.getAttribute("id_etud") + "&id_examen=" + request.getAttribute("id_examen") + "\""%>
						<% }%>> <i class="si si-control-play"></i>
					</a>
				</div>






			</div>
		</div>
	</div>
	<!-- END Video content -->



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