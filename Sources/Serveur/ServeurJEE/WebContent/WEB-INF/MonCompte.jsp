	<meta http-equiv="Content-Type" content="text/html">
	<title>Mon compte</title>
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
                    <c:if test="${empty eleve}">    
                    	<li>
							<a href="listeUtilisateurs"><i class="fa fa-users push-5-r"></i>Liste des utilisateurs</a>
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
					</c:if>      
						<li class="active">
							<a href=""><i class="fa fa-user push-5-r"></i>Mon compte</a>
						</li> 
					</ul>
				</div>
			</div>
			<!-- END Sub Header -->

			<!-- Register Content -->
			<div class="content overflow-hidden monCompte"> 
				<div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
                <!-- Register Block -->
					<div class="block block-themed animated fadeIn" >
						<div class="block-content block-content-full block-content-narrow" >
							<!-- Register Title -->
							<!-- Register Form -->
							<!-- jQuery Validation (.js-validation-register class is initialized in js/pages/base_pages_register.js) -->
							<!-- For more examples you can check out https://github.com/jzaefferer/jquery-validation -->
							<c:if test="${not empty info_change}">
								<div class="form-group">
									<div class="col-xs-12">
										<div class="form-material form-material-success">
											<label for="mdp_succes">Vos données ont été changées avec succès.</label>
										</div>
									</div>
								</div>
							</c:if>
							<form class="js-validation-register form-horizontal push-50-t push-50" action="monCompte" method="post">
								<div class="form-group">
									<div class="col-xs-12">
										<div class="form-material form-material-success">
											<input class="form-control" type="text" id="nom" name="nom" placeholder="Entrer votre nom" value="${utilisateur.nom}">
											<label for="nom">Nom</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-xs-12">
										<div class="form-material form-material-success">
											<input class="form-control" type="text" id="prenom" name="prenom" placeholder="Entrer votre prénom" value="${utilisateur.prenom}">
											<label for="prenom">Prénom</label>
										</div>
									</div>
								</div>                                
								<div class="form-group">
									<div class="col-xs-12 col-sm-6 col-md-5">
										<button class="btn btn-block btn-success" id="saveInfos" name="saveInfos" type="submit"><i class="si si-note push-5-r"></i>Sauvegarde</button>
									</div>
								</div>
							</form>
	
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-success">
										<c:if test="${not empty mdp_change}">
											<label for="mdp_succes">Le mot de passe a été changé avec succes.</label>
										</c:if>
										<c:if test="${not empty mdp_fail}">
											<label for="md_fail">Mot de passe non correspondant.</label>
										</c:if>
									</div>
								</div>
							</div>
							<br>
	
							<form class="js-validation-register form-horizontal push-50-t push-50" action="monCompte" method="post">
								<div class="form-group">
									<div class="col-xs-12">
										<div class="form-material form-material-success">
											<input class="form-control" type="password" id="motPass" name="motPass" placeholder="Entrer votre mot de passe" required>
											<label for="motPasse">Mot de passe</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-xs-12">
										<div class="form-material form-material-success">
											<input class="form-control" type="password" id="nouvPass" name="nouvPass" placeholder="Entrer votre nouveau mot de passe" required>
											<label for="nouvPasse">Nouveau mot de passe</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-xs-12">
										<div class="form-material form-material-success">
											<input class="form-control" type="password" id="confPass" name="confPass" placeholder="Confirmer votre mot de passe" required>
											<label for="confPasse">Confirmer votre mot de passe</label>
										</div>
									</div>
								</div>
	
								<div class="form-group">
									<div class="col-xs-12 col-sm-6 col-md-5">
										<button class="btn btn-block btn-success" id="saveMdp" name="saveMdp" type="submit"><i class="si si-note push-5-r"></i>Sauvegarde</button>
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