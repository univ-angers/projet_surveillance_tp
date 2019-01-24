	<meta http-equiv="Content-Type" content="text/html">
	<title>Authentification</title>
</head>
<body class="bg-image authentification">
	<!-- Login Content -->
	<div class="content overflow-hidden">
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
				<!-- Login Block -->
				<div class="block block-themed animated fadeIn">
					<div class="block-header bg-primary">
						<ul class="block-options">
							<li>
								<a href="recupPass">Mot de passe oublié ?</a>
							</li>
							<li>
								<a href="formUtilisateur" data-toggle="tooltip" data-placement="left" title="Nouveau compte"><i class="si si-plus"></i></a>
							</li>
						</ul>
						<h3 class="block-title">Authentification</h3>
					</div>
					<div class="block-content block-content-full block-content-narrow">
						<!-- Login Form -->
						<!-- jQuery Validation (.js-validation-login class is initialized in js/pages/base_pages_login.js) -->
						<!-- For more examples you can check out https://github.com/jzaefferer/jquery-validation -->
						<form class="js-validation-login form-horizontal push-30-t push-50" action="" method="post">
							<div class="form-group">
								<div class="col-xs-12">
									<c:if test="${not empty erreur}">
										<c:out value="${erreur}"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-primary floating">
										<input class="form-control" type="text" id="login-mail" name="login-mail">
										<label for="login-username">Mail</label>
									</div>
								</div>
								<div class="col-xs-12">
									Pas inscrit ? Cliquez <a href="formUtilisateur">ici</a> !
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-primary floating">
										<input class="form-control" type="password" id="login-password" name="login-password">
										<label for="login-password">Mot de passe</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12 col-sm-6 col-md-6">
									<button class="btn btn-block btn-primary" type="submit"><i class="si si-login pull-right"></i>S'authentifier</button>
								</div>
								<div class="col-xs-12 col-sm-6 col-md-6">
									<a class="btn btn-block btn-primary" href="home"><i class="si si-home pull-right"></i>Retour</a>
								</div>
							</div>
						</form>
						<!-- END Login Form -->
					</div>
				</div>
				<!-- END Login Block -->
			</div>
		</div>
	</div>
	<!-- END Login Content -->

	<!-- Page JS Plugins -->
	<script src="assets/js/plugins/jquery-validation/jquery.validate.min.js"></script>
	<!-- Page JS Code -->
	<script src="assets/js/pages/base_pages_login.js"></script>