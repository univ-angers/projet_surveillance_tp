	<meta http-equiv="Content-Type" content="text/html">
	<title>Paramètres</title>
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
						<li>
							<a href="listeUtilisateurs"><i class="fa fa-users push-5-r"></i>Liste des utilisateurs</a>
						</li>
						<!-- TODO: VERIFIER QU'UN UTILISATEUR A LES DROITS POUR AFFICHER CREATION EXAMEN -->
						<li class="active">
							<a href=""><i class="fa fa-wrench push-5-r"></i>Créer un examen</a>
						</li>
						<li>
							<a href="historique"><i class="fa fa-archive push-5-r"></i>Historique</a>
						</li>
						<li>
							<a href="monCompte"> <i class="fa fa-user push-5-r"></i>Mon compte</a>
						</li>
					</ul>
				</div>
			</div>
			<!-- END Sub Header -->

			<!-- Register Content -->
			<div class="bg-video" data-vide-bg="assets/img/videos/hero_tech" data-vide-options="posterType: jpg, position: 50% 75%">
				<div class="videoContainer">
					<video class="videoMain" autoplay loop muted>
						<source src="assets/img/videos/hero_tech.mp4" type="video/mp4">
						<source src="assets/img/videos/hero_tech.webm" type="video/webm">
						<source src="assets/img/videos/hero_tech.ogv" type="video/ogg">
					</video>
				</div>
				<div class="row">
					<div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
						<!-- Register Block -->
						<div class="block block-themed animated fadeIn">
							<div class="block-content block-content-full block-content-narrow">
								<!-- Register Title -->
								<!-- Register Form -->
								<!-- jQuery Validation (.js-validation-register class is initialized in js/pages/base_pages_register.js) -->
								<!-- For more examples you can check out https://github.com/jzaefferer/jquery-validation -->
								<form class="js-validation-register form-horizontal push-50-t push-50" action="formExamen" method="post">
									<div class="form-group">
										<div class="col-xs-12">
											<div class="form-material form-material-success">
												<label>Temps d'examen</label> Heure:
												<select id="duree-heure" name="duree-heure">
													<option value="0">0</option>
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
												</select> Minute <select id="duree-minute" name="duree-minute">
													<option value="0">00</option>
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="30">30</option>
													<option value="40">40</option>
													<option value="50">50</option>
												</select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12">
											<div class="form-material form-material-success">
												<input class="form-control" type="text" id="matiere" name="matiere" placeholder="Entrer une matiere">
												<label for="matiere">Matiere</label>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12">
											<div class="form-material form-material-success">
												<input class="form-control" type="text" id="white-list" name="white-list" placeholder="Saisir la liste des sites interdits">
												<label for="white-list">La liste des sites interdits</label>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12">
											<label class="css-input switch switch-sm switch-success">
												<input type="checkbox" id="bouton_fichier" name="bouton_fichier">
												<span></span>Opération de fichier surveillée
											</label>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12">
											<label class="css-input switch switch-sm switch-success">
												<input type="checkbox" id="bouton_clavier" name="bouton_clavier">
												<span></span>Frappe de clavier surveillée
											</label>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12">
											<label class="css-input switch switch-sm switch-success">
												<input type="checkbox" id="bouton_usb" name="bouton_usb">
												<span></span>Montage de dossier/USB surveillé
											</label>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12">
											<label class="css-input switch switch-sm switch-success">
												<input type="checkbox" id="bouton_video" name="bouton_video">
												<span></span>Surveillance vidéo
											</label>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12 col-sm-6 col-md-5">
											<button class="btn btn-block btn-success" type="submit">
												<i class="si si-note push-5-r"></i>Créer
											</button>
										</div>
									</div>
								</form>
								<!-- END Register Form -->
							</div>
						</div>
						<!-- END Register Block -->
					</div>
				</div>
			</div>
			<!-- END Register Content --> 
		</div>
		<!-- END Main Container -->
	</div>
	<!-- END Page Container -->