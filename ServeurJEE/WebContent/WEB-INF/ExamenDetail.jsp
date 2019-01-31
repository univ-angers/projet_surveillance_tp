	<meta http-equiv="Content-Type" content="text/html">
	<title>Détail de l'examen</title>
	<link rel="stylesheet" type="text/css" href="assets/DataTables/datatables.min.css"/>
	<script type="text/javascript" src="assets/DataTables/datatables.min.js"></script>
</head>
<body>
	<div id="page-container" class="sidebar-l side-scroll header-navbar-fixed">
		<!-- Header -->
		<header id="header-navbar" class="content-mini content-mini-full">
			<!-- Header Navigation Right -->
			<ul class="nav-header pull-right">
				<li>
					<div class="btn-group">
						<button class="btn btn-default btn-image dropdown-toggle" data-toggle="dropdown" type="button" aria-expanded="false">
							<img src="assets/img/avatars/avatar10.jpg" alt="Avatar">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-menu-right">
							<li>
								<a tabindex="-1" href="logout"><i class="si si-logout pull-right"></i>Déconnexion</a>
							</li>
						</ul>
					</div>
				</li>
			</ul>
		</header>
		<!-- END Header -->
	
		<!-- Main Container -->
		<div id="main-container">
			<!-- Sub Header -->
			<div class="bg-gray-lighter visible-xs">
				<div class="content-mini content-boxed">
					<button class="btn btn-block btn-default visible-xs push" data-toggle="collapse" data-target="#sub-header-nav">
						<i class="fa fa-navicon push-5-r"></i>Menu
					</button>
				</div>
			</div>
			<div class="bg-primary-lighter collapse navbar-collapse remove-padding" id="sub-header-nav">
				<div class="content-mini content-boxed">
					<ul class="nav nav-pills nav-sub-header push">
						<li class="active">
							<a 
								<c:if test="${empty idExamEnCours}">
									href="listeUtilisateurs?id_examen=${id_examen}">
								</c:if>
								<c:if test="${not empty idExamEnCours}">
									href="listeUtilisateurs?id_examen=${idExamEnCours}">
								</c:if>
							><i class="fa fa-users push-5-r"></i>Liste des utilisateurs</a>
						</li>
						<c:if test="${empty afficheParam}">
							<li>
								<a href="formExamen"><i class="fa fa-wrench push-5-r"></i>Créer un examen</a>
							</li>
						</c:if>
						<c:if test="${not empty afficheParam}">
							<li>
								<a href="paramExam"><i class="fa fa-wrench push-5-r"></i>Paramètres de l'examen</a>
							</li>
						</c:if>
						<li>
							<a href="historique"><i class="fa fa-archive push-5-r"></i>Historique</a>
						</li>
						<li>
							<a href="monCompte"><i class="fa fa-user push-5-r"></i>Mon compte</a>
						</li>
					</ul>
				</div>
			</div>
			<!-- END Sub Header -->
	
			<!-- Page Content -->
			<div class="content content-boxed overflow-hidden">
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
		</div>

		<!--Text content -->
		<div class="modal fade" id="modal-terms" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-dialog-popout modal-lg">
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
								<table id="logTable"></table>
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
								href="video?id_etud=${id_etud}&id_examen=${id_examen}"
							</c:if>
							<c:if test="${empty id_examen}">
								href="video?id_etud=${id_etud}"
							</c:if>
						>
							<i class="si si-control-play"></i>
						</a>
					</div>
				</div>
			</div>
		</div>
		<!-- END Video content -->
	</div>

	<!-- Page JS Plugins -->
	<script src="assets/js/plugins/jquery-validation/jquery.validate.min.js"></script>

	<!-- Page JS Code -->
	<script src="assets/js/pages/base_pages_login.js"></script>
	
	<script type="text/javascript">
		$(window).on("load", function() {
			$('#logTable').DataTable({
				data: ${log},
				columns: [
					{title:"Horodatage", data:"horodatage"},
					{title:"Mail de l'étudiant", data:"mailEtudiant"},
					{title:"Id de l'examen", data:"IDexamen"},
					{title:"Type d'alerte", data:"type"},
					{title:"Détails", data:"info"}
				]<c:if test="${not empty video}">,
				"columnDefs": [{
					"render": function(data, type, row) {
						var id_examen="";
						<c:if test="${not empty id_examen}">
							id_examen="&id_examen=${id_examen}";
						</c:if>
						return '<a href="video?id_etud=${id_etud}'+id_examen+'&timecode='+data+'">'+data+'</a>';
					},
					"targets": 0
				}]</c:if>
			});
		});
	</script>