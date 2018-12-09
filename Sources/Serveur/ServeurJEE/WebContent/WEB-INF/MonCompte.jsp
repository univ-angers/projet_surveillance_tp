<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus" lang="en"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-focus" lang="en"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">

        <title>Mon compte</title>

       

        <!-- Stylesheets -->
        <!-- Web fonts -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400italic,600,700%7COpen+Sans:300,400,400italic,600,700">

        <!-- Bootstrap and OneUI CSS framework -->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" id="css-main" href="assets/css/oneui.css">

        <!-- You can include a specific file from css/themes/ folder to alter the default color theme of the template. eg: -->
        <!-- <link rel="stylesheet" id="css-theme" href="assets/css/themes/flat.min.css"> -->
        <!-- END Stylesheets -->
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
            <main id="main-container">
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
                            <li >
                                <a href="listeUtilisateurs">
                                    <i class="fa fa-users"></i>Liste des utilisateurs
                                </a>
                            </li>
                            <c:if test="${empty afficheParam}">
   								<li>
                              	  <a href="formExamen">
                                    <i class="fa fa-wrench"></i>Créer un examen
                             	  </a>
                         		</li>
							</c:if>
                            <c:if test="${not empty afficheParam}">
   								<li>
                              	  <a href="paramExam">
                                    <i class="fa fa-wrench"></i>Paramètres de l'examen
                             	  </a>
                         		</li>
							</c:if>
                            <li>
                                <a href="historique">
                                    <i class="fa fa-archive"></i>Historique
                                </a>
                            </li>
                  		</c:if>      
                            <li class="active">
                                <a href="">
                                    <i class="fa fa-user"></i>Mon compte
                                </a>
                            </li> 
                        </ul>
                    </div>
                </div>
                <!-- END Sub Header -->

                <!-- Page Content -->
                
                
                
                
                    <!-- Register Content -->
        <div class="content overflow-hidden" style="background-image: url('assets/img/photos/photo26@2x.jpg');"> 
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
                                            <input class="form-control" type="text" id="nom" name="nom" placeholder="Entrer votre nom ">
                                            <label for="nom">Nom</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <div class="form-material form-material-success">
                                            <input class="form-control" type="text" id="prenom" name="prenom" placeholder="Entrer votre prénom">
                                            <label for="prenom">Prénom</label>
                                        </div>
                                    </div>
                                </div>                                
                                <div class="form-group">
                                    <div class="col-xs-12 col-sm-6 col-md-5">
                                        <button class="btn btn-block btn-success" id="saveInfos" name="saveInfos" type="submit"><i class="si si-note"></i> Sauvegarde</button>
                                        
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
                               
                            </br>
                       
                            
                            <form class="js-validation-register form-horizontal push-50-t push-50" action="monCompte" method="post">
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <div class="form-material form-material-success">
                                            <input class="form-control" type="password" id="motPass" name="motPass" placeholder="Entrer votre mot de passe ">
                                            <label for="motPasse">Mot de passe</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <div class="form-material form-material-success">
                                            <input class="form-control" type="password" id="nouvPass" name="nouvPass" placeholder="Entrer votre nouveau mot de passe">
                                            <label for="nouvPasse">Nouveau mot de passe</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <div class="form-material form-material-success">
                                            <input class="form-control" type="password" id="confPass" name="confPass" placeholder="Confirmer votre mot de passe">
                                            <label for="confPasse">Confirmer votre mot de passe</label>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <div class="col-xs-12 col-sm-6 col-md-5">
                                        <button class="btn btn-block btn-success" id="saveMdp" name="saveMdp" type="submit"><i class="si si-note"></i> Sauvegarde</button>
                                        
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
            </main>
            <!-- END Main Container -->

            <!-- Footer -->
            <footer id="page-footer" class="bg-body font-s12">
                <div class="content-mini content-mini-full content-boxed clearfix push-15">
                    
                    <div class="pull-left">
                        <a class="font-w600" href="http://goo.gl/6LF10W" target="_blank">Enseignant</a> &copy; 2018/2019
                    </div>
                </div>
            </footer>
            <!-- END Footer -->
        </div>
        <!-- END Page Container -->

        <!-- OneUI Core JS: jQuery, Bootstrap, slimScroll, scrollLock, Appear, CountTo, Placeholder, Cookie and App.js -->
        <script src="assets/js/core/jquery.min.js"></script>
        <script src="assets/js/core/bootstrap.min.js"></script>
        <script src="assets/js/core/jquery.slimscroll.min.js"></script>
        <script src="assets/js/core/jquery.scrollLock.min.js"></script>
        <script src="assets/js/core/jquery.appear.min.js"></script>
        <script src="assets/js/core/jquery.countTo.min.js"></script>
        <script src="assets/js/core/jquery.placeholder.min.js"></script>
        <script src="assets/js/core/js.cookie.min.js"></script>
        <script src="assets/js/app.js"></script>

        <!-- Page Plugins -->
        <script src="assets/js/plugins/chartjs/Chart.min.js"></script>

        <!-- Page JS Code -->
        <script src="assets/js/pages/base_pages_dashboard_v2.js"></script>
        <script>
            jQuery(function () {
                // Init page helpers (CountTo plugin)
                App.initHelpers('appear-countTo');
            });
        </script>
    </body>
</html>