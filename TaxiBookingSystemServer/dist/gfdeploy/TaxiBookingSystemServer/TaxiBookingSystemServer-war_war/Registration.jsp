<%-- 
    Document   : registration
    Created on : Mar 11, 2017, 1:42:50 PM
    Author     : Derian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Taxi Booking System: Registration</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-signup2.css" rel="stylesheet">
     
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-4 well well-sm">
            <legend><a href=""><i class="glyphicon glyphicon-road"></i></a> Welcome to TBS! <br>Please register below</legend>
            <form action="ControllerServlet" method="post" class="form" role="form">

            <label class="control-label">New Username</label>
                <input class="form-control" name="userName" placeholder="New Username" type="username" required autofocus/>
            <label class="control-label">New Password</label>
                <input class="form-control" name="password" placeholder="New Password" type="password" required/>            
            <div class="row">          
                <div class="col-xs-6 col-md-6">                  
                    <label class="control-label">Contact Number</label>                   
                    +65<input class="form-control" name="contact" 
                           placeholder="e.g. 97432648 or 
                           62748311" type="number" required />
                </div>
                <div class="col-xs-6 col-md-6">
                    <label class="control-label">Email</label>
                    <input class="form-control" name="email" placeholder="Email" type="text" required />
                </div>
            </div>
            <!-- address-line1 input-->
            <div class="control-group">
                <label class="control-label">Address</label>
                <div class="controls">
                    <input class="input-xlarge" name="address" required="" 
                           type="text" placeholder="address line"/>
                    <p class="help-block">Street name, Block number, Floor number, Postal code etc.</p>
                </div>
            </div>
            <br />
            <br />
            <input type = "hidden" name="nextAction" value="RegistrationStatus"/>
            <button class="btn btn-lg btn-primary btn-block" type="submit">
                Register</button>
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
