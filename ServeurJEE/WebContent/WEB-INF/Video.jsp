	<meta http-equiv="Cache-control" content="no-cache">
	<title>Video</title>
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
							<a href="listeUtilisateurs?id_examen=${param.id_examen}"><i class="fa fa-users push-5-r"></i>Liste des utilisateurs</a>
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
			<div class="content">
				<div>
					<video id="video" class="videoWatching_video" width="1366" height="768" controls>
						<source src="assets/img/videos/output.mp4"></source>
					</video>
				</div>
			
				<div class="push-10-t text-center animated fadeInUp">
					<h3>Si la vidéo ne démarre pas, veuillez recharger la page au bout de quelques secondes.</h3>
				</div>
			</div>
		</div>
	</div>

	<!-- Page JS Plugins -->
	<script src="assets/js/plugins/jquery-validation/jquery.validate.min.js"></script>

	<!-- Page JS Code -->
	<script src="assets/js/pages/base_pages_login.js"></script>

	<c:if test="${not empty param.timecode}">
	<script type="text/javascript">
		$(window).on("load", function() {
			var temp="${param.timecode}".split(':') // split it at the colons
			// minutes are worth 60 seconds. Hours are worth 60 minutes.
			document.getElementById("video").currentTime=(+temp[0])*60*60+(+temp[1])*60+(+temp[2]);
		});
	</script>
	</c:if>