<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.ArrayList"%> 
<%@page import="com.surveillance.tp.beans.Examen"%> 


<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus" lang="en"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-focus" lang="en"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">

        <title>Historique</title>


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
                                        <i class="si si-logout pull-right"></i>Log out
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
                            <li class="active">
                                <a href="">
                                    <i class="fa fa-archive"></i>Historique
                                </a>
                            </li>
                            <li>
                                <a href="monCompte">
                                    <i class="fa fa-user"></i>Mon compte
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- END Sub Header -->

                <!-- Page Content -->
                <div class="content content-boxed">
                    <!-- Section -->
                    <div class="bg-image img-rounded overflow-hidden push" style="background-image: url('assets/img/photos/archive.jpg');">
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
                    <%if(request.getAttribute("Archive")!=null){
                    	ArrayList<Examen>exams=(ArrayList)request.getAttribute("Archive");
                    	for(Examen e:exams){
                    		
                    		
                    	
                    	%>
                    
                    <div class="col-sm-6 col-md-4 col-lg-3">
                                <a class="block block-rounded block-link-hover2" href="<%="listeUtilisateurs?id_examen=" + String.valueOf(e.getIdExam()) %>">
                                    <div class="block-content block-content-full text-center bg-primary ribbon ribbon-bookmark ribbon-crystal">
                                        <div class="ribbon-box font-w600"></div>
                                        <div class="item item-2x item-circle bg-crystal-op push-20-t push-20 animated fadeIn" data-toggle="appear" data-offset="50" data-class="animated fadeIn">
                                            <i class="si si-camcorder text-white-op"></i>
                                        </div>
                                       
                                    </div>
                                    <div class="block-content">
                                        
                                        <div class="font-s12 text-center push"><%=e.getMatiere() + " / " + e.getHeureDebut()%></div>
                                    </div>
                                </a>
                            </div>
                            <%}} %>
                    </div>
                    <!-- END Stats -->

                    <!-- Charts -->
                    
                        <div class="col-md-6">
                            
                        </div>
                    </div>
                    <!-- END Charts -->
                </div>
                <!-- END Page Content -->
            </main>
            <!-- END Main Container -->

            <!-- Footer -->
            <footer id="page-footer" class="bg-body font-s12">
                <div class="content-mini content-mini-full content-boxed clearfix push-15">
                    
                    <div class="pull-left">
                      <center> <a class="font-w600" href="http://goo.gl/6LF10W" target="_blank">Enseignant</a> &copy; 2018/2019</center>
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