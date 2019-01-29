	<meta http-equiv="refresh" content="30" />
	<title>Liste d'étudiants</title>
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
							<a href=""><i class="fa fa-users push-5-r"></i>Liste des utilisateurs</a>
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
			<div class="content content-boxed">
				<!-- Section -->
				<div class="bg-image img-rounded overflow-hidden push listeUtilisateurs">
					<div class="bg-black-op">
						<div class="content">
							<div class="block block-transparent block-themed text-center">
								<div class="block-content">
									<h1 class="h1 font-w700 text-white animated fadeInDown push-5">Etudiants</h1>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- END Section -->
	
				<!-- Stats -->
				<div class="row text-uppercase">
					<div class="block block-opt-refresh-icon4">
						<div class="block-content">
							<table class="table table-borderless table-striped table-vcenter">
								<tbody>
									<tr>
										<th>Nom</th>
										<th>Prénom</th>
										<th>Alerte</th>
										<th>Critique</th>
										<th>Détails</th>
									</tr>
									<c:forEach var="u" items="${utilisateurs}">
										<tr>
											<td>${u.nomEt}</td>
											<td>${u.prenEt}</td>
											<td>${u.nbAlertes}</td>
											<td>${u.nbAlertesCritiques}</td>
											<td>
												<a class="btn btn-rounded btn-noborder btn-lg btn-success push-10-r push-5 animated fadeInLeft" data-toggle="appear" data-class="animated fadeInLeft"
													<c:if test="${empty id_examen}">
														href="detailExamen?id_etudiant=${u.id}"
													</c:if>
													<c:if test="${not empty id_examen}">
														href="detailExamen?id_etudiant=${u.id}&id_examen=${id_examen}"
													</c:if>
													<c:if test="${not empty idExamEnCours}">
														href="detailExamen?id_etudiant=${u.id}&id_examen=${idExamEnCours}"
													</c:if>
												>
													<i class="si si-control-play"></i>
												</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
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