<%-- 
    Document   : Profile
    Created on : Mar 14, 2017, 10:31:00 PM
    Author     : Derian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Profile</title>
        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-admin.css" rel="stylesheet">
        
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        
    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-default" role="navigation">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span><span
                            class="icon-bar"></span><span class="icon-bar"></span>
                    </button>
                    <a href="WelcomePage.jsp" class="navbar-brand"><span class="glyphicon glyphicon-road"></span>Taxi Booking System</a>
                </div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="Profile.jsp"><span class="glyphicon glyphicon-user"></span>Profile</a></li>
                        <li><a href="Book.jsp"><span class="glyphicon glyphicon-flag"></span>Book</a></li>
                        <li><a href="Rides.jsp"><span class="glyphicon glyphicon-list-alt"></span>Rides</a></li>
                        <li><a href="Payments.jsp"><span class="glyphicon glyphicon-credit-card"></span>Payments</a></li>
                        <li><a href="Messages.jsp"><span class="glyphicon glyphicon-comment"></span>Messages</a></li> 
                    </ul>
                    <ul class="nav navbar-nav navbar-right">               
                        <li><a href="index.jsp"><span class="glyphicon glyphicon-off"></span>Logout</a></li>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="container" style="padding-top: 10px;">
            <h1 class="page-header">Edit Profile</h1>
            <div class="row">
                <!-- edit form column -->
                <div class="col-md-8 col-sm-6 col-xs-12 personal-info">
                    <h3>Personal info</h3>
                    <form class="form-horizontal" role="form" 
                          action="ControllerServlet" method=post>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">New Email(if any):</label>
                            <div class="col-lg-8">
                                <input class="form-control" type="text" name="email">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">New Address(if any):</label>
                            <div class="col-lg-8">
                                <input class="form-control" type="text" name="address">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">New Contact Number<br>(if any):</label>
                            <div class="col-lg-8">
                                +65<input class="form-control" placeholder="e.g. 97432648 or 62748311" type="number" name="contact">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">New Password(if any):</label>
                            <div class="col-md-8">
                                <input class="form-control" name ="password" type="password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"></label>
                            <div class="col-md-8">                        
                                <input type = "hidden" name="nextAction" value="Profile"/>
                                <input class="btn btn-primary" value="Save Changes" type="submit" onclick="showAlert()"/>
                                <script type="text/javascript">
                                    function showAlert(){
                                        alert("Profile updated!");
                                    }
                                                                </script>
                                <input class="btn btn-default" value="Cancel" type="reset"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
        
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://code.jquery.com/jquery.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
