	<meta http-equiv="Content-Type" content="text/html">
	<title>Mot de passe oublié</title>
</head>
<body class="bg-image recupaPass">
	<!-- Reminder Content -->
	<div class="content overflow-hidden">
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
				<!-- Reminder Block -->
				<div class="block block-themed animated fadeIn">
					<div class="block-header bg-primary">
						<ul class="block-options">
							<li>
								<a href="LoginRegister" data-toggle="tooltip" data-placement="left" title="Log In"><i class="si si-login"></i></a>
							</li>
						</ul>
						<h3 class="block-title">Recuperation mot de passe</h3>
					</div>
					<div class="block-content block-content-full block-content-narrow">
						<!-- Reminder Title -->
						<p>Entrer votre adresse email, et nous allons vous envoyer votre mot de passe</p>
						<!-- END Reminder Title -->
				
						<!-- Reminder Form -->
						<!-- jQuery Validation (.js-validation-reminder class is initialized in js/pages/base_pages_reminder.js) -->
						<!-- For more examples you can check out https://github.com/jzaefferer/jquery-validation -->
						<form class="js-validation-reminder form-horizontal push-30-t push-50" action="recupPass" method="post">
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-primary floating">
										<input class="form-control" type="email" id="reminder-email" name="reminder-email">
										<label for="reminder-email">Email</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12 col-sm-6 col-md-5">
									<button class="btn btn-block btn-primary" type="submit"><i class="si si-envelope-open pull-right"></i>Envoyer</button>
								</div>
							</div>
						</form>
						<div class="form-group">
							<div class="col-xs-12">
								<div class="form-material form-material-success">
									<c:if test="${not empty util_trouve}">
										<label for="mdp_succes">Un email a été envoyé à l'adresse correspondante.</label>
									</c:if>
									<c:if test="${not empty util_non_trouve}">
										<label for="md_fail">Cet email n'est pas connu.</label>
									</c:if>                
								</div>
							</div>
						</div>
						<!-- END Reminder Form -->
					</div>
				</div>
				<!-- END Reminder Block -->
			</div>
		</div>
	</div>
	<!-- END Reminder Content -->

	<!-- Page JS Plugins -->
	<script src="assets/js/plugins/jquery-validation/jquery.validate.min.js"></script>
	
	<!-- Page JS Code -->
	<script src="assets/js/pages/base_pages_reminder.js"></script>