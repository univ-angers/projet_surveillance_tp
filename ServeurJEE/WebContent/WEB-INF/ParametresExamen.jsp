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
							<a tabindex="-1" href="logout">
								<i class="si si-logout pull-right"></i>Déconnexion
							</a>
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
					<li >
						<a href="listeUtilisateurs?id_examen=${idExamEnCours}">
							<i class="fa fa-users push-5-r"></i>Liste des utilisateurs
						</a>
					</li>
					<li class="active">
						<a href="">
							<i class="fa fa-wrench push-5-r"></i>Paramètres de l'examen
						</a>
					</li>
					<li>
						<a href="historique">
							<i class="fa fa-archive push-5-r"></i>Historique
						</a>
					</li>
					<li>
						<a href="monCompte">
							<i class="fa fa-user push-5-r"></i>Mon compte
						</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- END Sub Header -->
		
		<!-- Page Content -->
		<!-- Register Content -->
		<div class="bg-video parametreExamen_bgVideo" data-vide-bg="assets/img/videos/hero_tech" data-vide-options="posterType: jpg, position: 50% 75%">
			<div class="parametreExamen_videoContainer">
				<video class="parametreExamen_videoMain" autoplay loop muted>
					<source src="assets/img/videos/hero_tech.mp4" type="video/mp4">
					<source src="assets/img/videos/hero_tech.webm" type="video/webm">
					<source src="assets/img/videos/hero_tech.ogv" type="video/ogg">
				</video>
			</div>
			<div class="row">
				<div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
					<!-- Register Block -->
					<div class="block block-themed animated fadeIn" >
						<div class="block-content block-content-full block-content-narrow">
							<div class="center">
								<c:if test="${not empty Demarre}">
									<a class="btn btn-rounded btn-noborder btn-lg btn-success push-10-r push-5 animated fadeInLeft" data-toggle="appear" data-class="animated fadeInLeft" href="demarrageExamen">
										<i class="si si-control-play"></i>
									</a>
								</c:if>
								<c:if test="${empty Demarre}">
									<a class="btn btn-rounded btn-noborder btn-lg btn-primary push-5 animated fadeInRight bg-red" data-toggle="appear" data-class="animated fadeInRight" href="arretExamen">
										<i class="fa fa-power-off"></i>
									</a>
								</c:if>
							</div>
							<!-- Register Title -->
			
							<!-- Register Form -->
							<!-- jQuery Validation (.js-validation-register class is initialized in js/pages/base_pages_register.js) -->
							<!-- For more examples you can check out https://github.com/jzaefferer/jquery-validation -->
							<form class="js-validation-register form-horizontal push-50-t push-50" action="paramExam" method="post">
								<div class="form-group">
									<div class="col-xs-12">
										<div class="form-material form-material-success">
											<input class="form-control" type="text" id="examen-id" name="examen-id" disabled="disabled" value="${IDexam}" placeholder="Saisir l'ID de l'examen">
											<label for=examen-id>Id examen</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-xs-12">
										<div class="form-material form-material-success">
											<input class="form-control" type="text" id="server" name="server" disabled value="${IP}" placeholder="Saisir l'adresse du serveur">
											<label for="server2">Adresse du serveur</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-xs-12">
										<div class="form-material form-material-success">
											<label for=examen-id>MODIFICATIONS</label> 
										</div>
									</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12">
											<div class="form-material form-material-success">
												<label for=examen-id>Si vous sauvegardez, tous les éléments de l'examen seront remplacés par les informations ci dessous.</label>
											</div>
										</div>
									</div>
									<br>
									<div class="form-group">
										<div class="col-xs-12">
											<div class="form-material form-material-success">
												<label>Temps d'examen</label> Heure:
												<select id="duree-heure" name="duree-heure">
													<c:forEach var = "i" begin = "0" end = "5">
														<c:if test="${i eq nb_heure}">
															<option value="${i}" selected="selected"><c:out value="${i}"></c:out></option>
														</c:if>
														<c:if test="${i ne nb_heure}">
															<option value="${i}"><c:out value="${i}"></c:out></option>
														</c:if>
													</c:forEach>
												</select>
												Minute
												<select id="duree-minute" name="duree-minute">
													<c:forEach var = "j" begin = "00" end = "50" step="10">
														<c:if test="${j eq nb_minute}">
															<option value="${j}" selected="selected"><c:out value="${j}"></c:out></option>
														</c:if>
														<c:if test="${j ne nb_minute}">
															<option value="${j}"><c:out value="${j}"></c:out></option>
														</c:if>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12">
											<div class="form-material form-material-success">
												<input class="form-control" type="text" id="matiere" name="matiere" value="${Matiere}" required>
												<label for="matiere">Matiere</label>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12">
											<div class="form-material form-material-success">
												<input class="form-control" type="text" id="white-list" name="white-list" value="${liste_site}" placeholder="Saisir la liste des sites autorisés">
												<label for="white-list">La liste des sites autorisés</label>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12">
											<label class="css-input switch switch-sm switch-success">
												<c:if test="${watcher_fichier}">
									        		<input type="checkbox" id="bouton_fichier" name="bouton_fichier" checked="checked"><span></span>Opération de fichier surveillé 
									        	</c:if>
									        	<c:if test="${not watcher_fichier}">
									        		<input type="checkbox" id="bouton_fichier" name="bouton_fichier"><span></span>Opération de fichier surveillé 
									        	</c:if>
											</label>
										</div>
									</div>
									 <div class="form-group">
										<div class="col-xs-12">
											<label class="css-input switch switch-sm switch-success">
												<c:if test="${watcher_clavier}">
													<input type="checkbox" id="bouton_clavier" name="bouton_clavier" checked="checked"><span></span>Frappe de clavier surveillé 
									        	</c:if>
									        	<c:if test="${not watcher_clavier}">
									        		<input type="checkbox" id="bouton_clavier" name="bouton_clavier"><span></span>Frappe de clavier surveillé 
									        	</c:if>
												
											</label>
										</div>
									</div>
									 <div class="form-group">
										<div class="col-xs-12">
											<label class="css-input switch switch-sm switch-success">
												<c:if test="${watcher_usb}">
													<input type="checkbox" id="bouton_usb" name="bouton_usb" checked="checked"><span></span>Montage de dossier/USB surveillé
									        	</c:if>
									        	<c:if test="${not watcher_usb}">
									        		<input type="checkbox" id="bouton_usb" name="bouton_usb" ><span></span>Montage de dossier/USB surveillé
									        	</c:if>
											</label>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12">
											<label class="css-input switch switch-sm switch-success">
												<c:if test="${watcher_video}">
													<input type="checkbox" id="bouton_video" name="bouton_video" checked="checked"><span></span>Surveillance vidéo
									        	</c:if>
									        	<c:if test="${not watcher_video}">
									        		<input type="checkbox" id="bouton_video" name="bouton_video"><span></span>Surveillance vidéo
									        	</c:if>
											</label>
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-12 col-sm-6 col-md-5">
											<button class="btn btn-block btn-success" type="submit"><i class="si si-note push-5-r"></i>Sauvegarde</button>
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
			<!-- END Charts -->
			<!-- END Page Content -->
		</div>
		 <!-- END Main Container -->
	</div>
	<!-- END Page Container -->