	<meta http-equiv="Content-Type" content="text/html">
	<title>Créer compte utilisateur</title>
</head>
<body class="bg-image">
	<!-- Register Content -->
	<div class="content overflow-hidden">
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
				<!-- Register Block -->
				<div class="block block-themed animated fadeIn">
					<div class="block-header bg-success">
						<ul class="block-options">
							<li>
								<a href="#" data-toggle="modal" data-target="#modal-terms">Voir les conditions</a>
							</li>
							<li>
								<a href="login" data-toggle="tooltip" data-placement="left" title="Log In"><i class="si si-login"></i></a>
							</li>
						</ul>
						<h3 class="block-title">Inscription</h3>
					</div>
					<div class="block-content block-content-full block-content-narrow">
						<!-- Register Form -->
						<!-- jQuery Validation (.js-validation-register class is initialized in js/pages/base_pages_register.js) -->
						<!-- For more examples you can check out https://github.com/jzaefferer/jquery-validation -->
						<form class="js-validation-register form-horizontal push-50-t push-50" action="" method="post" action="receptionFormUtilisateur">
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-success">
										<input class="form-control" type="text" id="register-userfirstname" name="register-userfirstname" placeholder="Entrez votre prénom">
										<label for="register-userfirstname">Prénom</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-success">
										<input class="form-control" type="text" id="register-username" name="register-username" placeholder="Entrez votre nom">
										<label for="register-username">Nom</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-success">
										<input class="form-control" type="email" id="register-email" name="register-email" placeholder="Entrez votre email">
										<label for="register-email">Email</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-success">
										<input class="form-control" type="password" id="register-password" name="register-password" placeholder="Choisissez un mot de passe fort..">
										<label for="register-password">Mot de passe</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<div class="form-material form-material-success">
										<input class="form-control" type="password" id="register-password2" name="register-password2" placeholder="..et comfirmez le">
										<label for="register-password2">Comfirmer mot de passe</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<label class="css-input switch switch-sm switch-success">
										<input type="checkbox" id="register-terms" name="register-terms"><span></span>J'accepte les conditions 
									</label>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12 col-sm-6 col-md-6">
									<button class="btn btn-block btn-success" type="submit"><i class="fa fa-plus pull-right"></i>Inscription</button>
								</div>
								<div class="col-xs-12 col-sm-6 col-md-6">
									<a class="btn btn-block btn-success" href="home"><i class="fa fa-home pull-right"></i>Retour</a>
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

	<!-- Terms Modal -->
	<div class="modal fade" id="modal-terms" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-dialog-popout">
			<div class="modal-content">
				<div class="block block-themed block-transparent remove-margin-b">
					<div class="block-header bg-primary-dark">
						<ul class="block-options">
							<li>
								<button data-dismiss="modal" type="button"><i class="si si-close"></i></button>
							</li>
						</ul>
						<h3 class="block-title">Terms &amp; Conditions</h3>
					</div>
					<div class="block-content">
						<p>Dolor posuere proin blandit accumsan senectus netus nullam curae, ornare laoreet adipiscing luctus mauris adipiscing pretium eget fermentum, tristique lobortis est ut metus lobortis tortor tincidunt himenaeos habitant quis dictumst proin odio sagittis purus mi, nec taciti vestibulum quis in sit varius lorem sit metus mi.</p>
						<p>Dolor posuere proin blandit accumsan senectus netus nullam curae, ornare laoreet adipiscing luctus mauris adipiscing pretium eget fermentum, tristique lobortis est ut metus lobortis tortor tincidunt himenaeos habitant quis dictumst proin odio sagittis purus mi, nec taciti vestibulum quis in sit varius lorem sit metus mi.</p>
						<p>Dolor posuere proin blandit accumsan senectus netus nullam curae, ornare laoreet adipiscing luctus mauris adipiscing pretium eget fermentum, tristique lobortis est ut metus lobortis tortor tincidunt himenaeos habitant quis dictumst proin odio sagittis purus mi, nec taciti vestibulum quis in sit varius lorem sit metus mi.</p>
						<p>Dolor posuere proin blandit accumsan senectus netus nullam curae, ornare laoreet adipiscing luctus mauris adipiscing pretium eget fermentum, tristique lobortis est ut metus lobortis tortor tincidunt himenaeos habitant quis dictumst proin odio sagittis purus mi, nec taciti vestibulum quis in sit varius lorem sit metus mi.</p>
						<p>Dolor posuere proin blandit accumsan senectus netus nullam curae, ornare laoreet adipiscing luctus mauris adipiscing pretium eget fermentum, tristique lobortis est ut metus lobortis tortor tincidunt himenaeos habitant quis dictumst proin odio sagittis purus mi, nec taciti vestibulum quis in sit varius lorem sit metus mi.</p>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-sm btn-default" type="button" data-dismiss="modal">Close</button>
					<button class="btn btn-sm btn-primary" type="button" data-dismiss="modal"><i class="fa fa-check push-5-r"></i>I agree</button>
				</div>
			</div>
		</div>
	</div>
	<!-- END Terms Modal -->

	<!-- Page JS Plugins -->
	<script src="assets/js/plugins/jquery-validation/jquery.validate.min.js"></script>
	
	<!-- Page JS Code -->
	<script src="assets/js/pages/base_pages_register.js"></script>