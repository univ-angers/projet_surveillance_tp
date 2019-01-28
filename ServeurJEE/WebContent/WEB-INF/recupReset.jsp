	<meta http-equiv="Content-Type" content="text/html">
	<title>Authentification</title>
</head>
<body class="bg-image recupReset">
	<!-- Login Content -->
	<div class="content overflow-hidden">
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
				<!-- Login Block -->
				<div class="block block-themed animated fadeIn">
					<div class="block-header bg-primary">
						<h3 class="block-title">Récupération mot de passe</h3>
					</div>
					<div class="block-content block-content-full block-content-narrow">
						<!-- Login Form -->
						<!-- jQuery Validation (.js-validation-login class is initialized in js/pages/base_pages_login.js) -->
						<!-- For more examples you can check out https://github.com/jzaefferer/jquery-validation -->
						<form class="js-validation-login form-horizontal push-30-t push-50" action="" method="post">
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-success">
										<c:if test="${not empty mdp_egaux}">
											<label for="md_fail">Lien fourni non reconnus. Vérifiez la syntaxe.</label>
										</c:if> 
										<c:if test="${not empty mdp_egaux}">
											<label for="md_fail">Les mots de passe ne sont pas équivalents. Réessayez.</label>
										</c:if>                
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-primary floating">
										<input class="form-control" type="text" id="nouv_mdp" name="nouv_mdp">
										<label for="login-username">Nouveau mot de passe</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-primary floating">
										<input class="form-control" type="test" id="mdp_conf" name="mdp_conf">
										<label for="login-password">Confirmation</label>
									</div>
								</div>
							</div>                                    
							<input id="id_util" name="id_util" type="hidden" value="${id_util}">                            
							<div class="form-group">
								<div class="col-xs-12 col-sm-6 col-md-4">
									<button class="btn btn-block btn-primary" type="submit"><i class="si si-login pull-right"></i> Comfirmer</button>
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