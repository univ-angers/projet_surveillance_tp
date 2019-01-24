	<meta http-equiv="Content-Type" content="text/html">
	<title>Historique</title>
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
						<li >
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
						<li class="active">
							<a href=""><i class="fa fa-archive push-5-r"></i>Historique</a>
						</li>
						<li>
							<a href="monCompte"><i class="fa fa-user push-5-r"></i>Mon compte</a>
						</li>
					</ul>
				</div>
			</div>
			<!-- END Sub Header -->

			<!-- Page Content -->
			<div class="content content-boxed">
				<!-- Section -->
				<div class="bg-image img-rounded overflow-hidden push historique">
					<div class="bg-black-op">
						<div class="content">
							<div class="block block-transparent block-themed text-center">
								<div class="block-content">
									<h1 class="h1 font-w700 text-white animated fadeInDown push-5">Archive</h1>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- END Section -->

				<!-- Stats -->
				<div class="row text-uppercase">
					<c:forEach var="e" items="${Archive}">
						<div class="col-sm-6 col-md-4 col-lg-3">
							<a class="block block-rounded block-link-hover2" href="listeUtilisateurs?id_examen=${e.idExam}">
								<div class="block-content block-content-full text-center bg-primary ribbon ribbon-bookmark ribbon-crystal">
									<div class="ribbon-box font-w600"></div>
									<div class="item item-2x item-circle bg-crystal-op push-20-t push-20 animated fadeIn" data-toggle="appear" data-offset="50" data-class="animated fadeIn">
										<i class="si si-camcorder text-white-op"></i>
									</div>
								</div>
								<div class="block-content">
									<div class="font-s12 text-center push">${e.matiere} / ${e.heureDebut}</div>
								</div>
							</a>
						</div>
					</c:forEach>
				</div>
				<!-- END Stats -->

				<!-- Charts -->
				<div class="col-md-6"></div>
			</div>
			<!-- END Charts -->
		</div>
		<!-- END Page Content -->
	</div>
	<!-- END Main Container -->