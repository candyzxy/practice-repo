<%-- 
    Document   : index
    Created on : Mar 14, 2017, 10:27:59 PM
    Author     : Derian
--%>
    
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Taxi Booking System: Login</title>
        
        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-mixnmatch.css" rel="stylesheet">
        
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
            <div class="container">
                
            <div class="row" style="margin-top:20px">
                <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
                    <form action="ControllerServlet" method="POST">
                        <fieldset>
                            <h2><span class="glyphicon glyphicon-road"></span> Welcome to TBS! Please Sign In</h2>
                            <hr class="colorgraph">
                            <div class="form-group">
                                <input type="username" name="userName" id="userName" class="form-control input-lg" placeholder="User Name">
                            </div>
                            <div class="form-group">
                                <input type="password" name="password" id="password" class="form-control input-lg" placeholder="Password">
                            </div>
                            <hr class="colorgraph">
                            <div class="row">
                                <div class="col-xs-6 col-sm-6 col-md-6">
                                    <input type = "hidden" name="nextAction" value="LogInStatus"/>
                                    <input type = "submit" class="btn btn-lg btn-success btn-block" value="Sign In"/>
                                </div>
                                <div class="col-xs-6 col-sm-6 col-md-6">
                                    <a href="Registration.jsp" class="btn btn-lg btn-primary btn-block">Register</a>
                                </div>
                            </div>
                        </fieldset>
                    </form>                       
                </div>
            </div>           
        </div>
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://code.jquery.com/jquery.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>
        <script src="js/bootstrap-mixnmatch.js"></script>
    </body>
</html>