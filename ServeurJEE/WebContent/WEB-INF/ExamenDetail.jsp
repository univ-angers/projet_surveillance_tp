	<meta http-equiv="Content-Type" content="text/html">
	<title>Détail de l'examen</title>
</head>
<body class="bg-image">
	<div class="content overflow-hidden">
		<div class="col-sm-6 col-md-4 col-lg-3">
			<a class="block block-rounded block-link-hover2" href="#" data-toggle="modal" data-target="#modal-terms">
				<div class="block-content block-content-full text-center bg-gray-dark ribbon ribbon-bookmark ribbon-crystal">
					<div class="item item-2x item-circle bg-crystal-op push-20-t push-20 animated fadeIn" data-toggle="appear" data-offset="50" data-class="animated fadeIn">
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
				<a class="block block-rounded block-link-hover2" href="#" data-toggle="modal" data-target="#modal-terms2">
					<div class="block-content block-content-full text-center bg-primary ribbon ribbon-bookmark ribbon-crystal">
						<div class="item item-2x item-circle bg-crystal-op push-20-t push-20 animated fadeIn" data-toggle="appear" data-offset="50" data-class="animated fadeIn">
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

	<!--Text content -->
	<div class="modal fade" id="modal-terms" tabindex="-1" role="dialog" aria-hidden="true">
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
					<div class="block-content examenDetail_log">
						<c:if test="${not empty log}">
							<p>${log}</p>
						</c:if>
					</div>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	<!-- END Text content -->

	<!--Video content -->
	<div class="modal fade" id="modal-terms2" tabindex="-1" role="dialog" aria-hidden="true">
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
						<p>Date de création: ${date_exam}</p>
						<p>Auteur: ${nomUtilisateur} ${prenomUtilisateur}</p>
						<p>Description: vidéo des actions réalisées par l'étudiant</p>
						<video autoplay loop muted>
							<source src="assets/img/videos/output.mp4" type="video/mp4">
							<source src="assets/img/videos/hero_tech.webm" type="video/webm">
							<source src="${video}" type="video/ogg">
						</video>
					</div>
				</div>
				<div class="modal-footer">
					<a class="btn btn-rounded btn-noborder btn-lg btn-success push-10-r push-5 animated fadeInLeft" data-toggle="appear" data-class="animated fadeInLeft"
						<c:if test="${not empty id_examen}">
							href="video?id_etud=${id_etud}&&id_examen=${id_examen}"
						</c:if>
						<c:if test="${not empty id_examen}">
							href="video?id_etud=${id_etud}&id_examen=${id_examen}"
						</c:if>
					>
						<i class="si si-control-play"></i>
					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- END Video content -->

	<!-- Page JS Plugins -->
	<script src="assets/js/plugins/jquery-validation/jquery.validate.min.js"></script>

	<!-- Page JS Code -->
	<script src="assets/js/pages/base_pages_login.js"></script>