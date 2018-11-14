
<!DOCTYPE html>
<!--[if IE 9]>         <html class="ie9 no-focus" lang="en"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-focus" lang="en"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">

        <title>Home</title>

        

      

        <!-- Stylesheets -->
        <!-- Web fonts -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400italic,600,700%7COpen+Sans:300,400,400italic,600,700">

        <!-- Bootstrap and OneUI CSS framework -->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" id="css-main" href="assets/css/oneui.css">

    </head>
    <body>
        
        <div id="page-container" class="sidebar-l side-scroll header-navbar-fixed">
            <!-- Side Overlay-->
            <aside id="side-overlay">
                <!-- Side Overlay Scroll Container -->
                <div id="side-overlay-scroll">
                    <!-- Side Header -->
                    <div class="side-header side-content">
                        <!-- Layout API, functionality initialized in App() -> uiLayoutApi() -->
                        <button class="btn btn-default pull-right" type="button" data-toggle="layout" data-action="side_overlay_close">
                            <i class="fa fa-times"></i>
                        </button>
                        <span class="font-w600">John Parker</span>
                    </div>
                    <!-- END Side Header -->

                    <!-- Side Content -->
                    <div class="side-content remove-padding-t">
                        <!-- Account -->
                        <div class="block pull-r-l">
                            <div class="block-header bg-gray-lighter">
                                <ul class="block-options">
                                    <li>
                                        <button type="button" data-toggle="block-option" data-action="content_toggle"></button>
                                    </li>
                                </ul>
                                <h3 class="block-title">Account</h3>
                            </div>
                            <div class="block-content">
                                <form class="form-horizontal" action="bd_dashboard.html" method="post" onsubmit="return false;">
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <label>Username</label>
                                            <div class="form-control-static font-w700">johnpar</div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <label for="bd-qsettings-name">Name</label>
                                            <input class="form-control" type="text" id="bd-qsettings-name" name="bd-qsettings-name" placeholder="Enter your name.." value="John Parker">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <label for="bd-qsettings-email">Email</label>
                                            <input class="form-control" type="email" id="bd-qsettings-email" name="bd-qsettings-email" placeholder="Enter your email.." value="john.parker@example.com">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <label for="bd-qsettings-password">New Password</label>
                                            <input class="form-control" type="password" id="bd-qsettings-password" name="bd-qsettings-new-password" placeholder="Enter a new password..">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <label for="bd-qsettings-password2">Confirm New Password</label>
                                            <input class="form-control" type="password" id="bd-qsettings-password2" name="bd-qsettings-new-password2" placeholder="Confirm your new password..">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <button class="btn btn-sm btn-minw btn-rounded btn-primary" type="submit">
                                                <i class="fa fa-check push-5-r"></i>Update
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <!-- END Account -->

                        <!-- Quick Settings -->
                        <div class="block pull-r-l">
                            <div class="block-header bg-gray-lighter">
                                <ul class="block-options">
                                    <li>
                                        <button type="button" data-toggle="block-option" data-action="content_toggle"></button>
                                    </li>
                                </ul>
                                <h3 class="block-title">Quick Settings</h3>
                            </div>
                            <div class="block-content">
                                <!-- Quick Settings Form -->
                                <form class="form-bordered" action="base_pages_dashboard.html" method="post" onsubmit="return false;">
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-8">
                                                <div class="font-s13 font-w600">Online Status</div>
                                                <div class="font-s13 font-w400 text-muted">Show your status to all</div>
                                            </div>
                                            <div class="col-xs-4 text-right">
                                                <label class="css-input switch switch-sm switch-primary push-10-t">
                                                    <input type="checkbox" checked><span></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-8">
                                                <div class="font-s13 font-w600">Auto Updates</div>
                                                <div class="font-s13 font-w400 text-muted">Keep up to date</div>
                                            </div>
                                            <div class="col-xs-4 text-right">
                                                <label class="css-input switch switch-sm switch-primary push-10-t">
                                                    <input type="checkbox" checked><span></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-8">
                                                <div class="font-s13 font-w600">Notifications</div>
                                                <div class="font-s13 font-w400 text-muted">Do you need them?</div>
                                            </div>
                                            <div class="col-xs-4 text-right">
                                                <label class="css-input switch switch-sm switch-primary push-10-t">
                                                    <input type="checkbox"><span></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-xs-8">
                                                <div class="font-s13 font-w600">API Access</div>
                                                <div class="font-s13 font-w400 text-muted">Enable/Disable access</div>
                                            </div>
                                            <div class="col-xs-4 text-right">
                                                <label class="css-input switch switch-sm switch-primary push-10-t">
                                                    <input type="checkbox"><span></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <!-- END Quick Settings Form -->
                            </div>
                        </div>
                        <!-- END Quick Settings -->
                    </div>
                    <!-- END Side Content -->
                </div>
                <!-- END Side Overlay Scroll Container -->
            </aside>
            <!-- END Side Overlay -->

            <!-- Header -->
            <header id="header-navbar">
                <div class="content-mini content-mini-full content-boxed">
                    <!-- Header Navigation Right -->
                    <ul class="nav-header pull-right">
                      
                        <li>
                            <!-- Layout API, functionality initialized in App() -> uiLayoutApi() -->
                            <button class="btn btn-default btn-image" data-toggle="layout" data-action="side_overlay_toggle" type="button">
                                <img src="assets/img/avatars/avatar9.jpg" alt="Avatar">
                                <i class="fa fa-ellipsis-v"></i>
                            </button>
                        </li>
                    </ul>
                    <!-- END Header Navigation Right -->

                    <!-- Header Navigation Left -->
                   
                    <!-- END Header Navigation Left -->
                </div>
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
                            <li class="active">
                                <a href="bd_dashboard.html">
                                    <i class="fa fa-users"></i>Liste des utilisateurs
                                </a>
                            </li>
                            <li>
                                <a href="ParametresExamen.jsp">
                                    <i class="fa fa-wrench"></i>Paramètres de l'examen
                                </a>
                            </li>
                            <li>
                                <a href="Historique.jsp">
                                    <i class="fa fa-archive"></i>Historique
                                </a>
                            </li>
                            <li>
                                <a href="MonCompte.jsp">
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
                    <div class="bg-image img-rounded overflow-hidden push" style="background-image: url('assets/img/photos/camera.jpg');">
                        <div class="bg-black-op">
                            <div class="content">
                                <div class="block block-transparent block-themed text-center">
                                    <div class="block-content">
                                        <h1 class="h1 font-w700 text-white animated fadeInDown push-5">Users</h1>
                                        
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
                                        	<th>Streaming</th>
                                        	</tr>
                                        	<%
                                               for(int i=0;i<21;i++){
                                            	   %>
                                               
                                            <tr>
                                                <td>Nom</td>
                                                <td>Prénom</td>
                                                <td> 0</td>
                                                <td> 0</td>
                                                <td> <a class="btn btn-rounded btn-noborder btn-lg btn-success push-10-r push-5 animated fadeInLeft" data-toggle="appear" data-class="animated fadeInLeft" href="">
                                        <i class="si si-control-play"></i>
                                    </a></td>
                                                
                                            </tr>
                                            <%} %>
                                            
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        
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