<%-- 
    Document   : WelcomePage
    Created on : Mar 28, 2017, 8:43:29 PM
    Author     : Derian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    
    <head>
        
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        
        <title>Welcome to TBS</title>
        
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-admin.css" rel="stylesheet">
        
        <!-- Custom Fonts -->
        <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">
        
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <%
        String userName = (String)request.getAttribute("userName");
                %>
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
                        <li><a href="Profile.jsp"><span class="glyphicon glyphicon-user"></span>Profile</a></li>
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
            <h1 class="page-header">
                Hello, 
                <%=userName%>
            </h1>
            <h2>Welcome to TBS</h2>
        </div>
        <!-- Header -->
        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
        
    </body>
    
</html>		

