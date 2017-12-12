<%-- 
    Document   : ViewPayments
    Created on : Mar 14, 2017, 10:37:28 PM
    Author     : Derian
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Your Payments</title>
        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap-admin.css" rel="stylesheet">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <% 
            ArrayList<String> data = (ArrayList<String>) request.getAttribute("data"); 
            ArrayList<String> outstandingRides = (ArrayList<String>) request.getAttribute("outstandingRides");
            String prelimMsg = (String)request.getAttribute("message");
            String msg = "";
        %>
        
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
                        <li><a href="Profile.jsp"><span class="glyphicon glyphicon-user"></span>Profile</a></li>
                        <li><a href="Book.jsp"><span class="glyphicon glyphicon-flag"></span>Book</a></li>
                        <li><a href="Rides.jsp"><span class="glyphicon glyphicon-list-alt"></span>Rides</a></li>
                        <li class="active"><a href="Payments.jsp"><span class="glyphicon glyphicon-credit-card"></span>Payments</a></li>
                        <li><a href="Messages.jsp"><span class="glyphicon glyphicon-comment"></span>Messages</a></li> 
                    </ul>
                    <ul class="nav navbar-nav navbar-right">               
                        <li><a href="index.jsp"><span class="glyphicon glyphicon-off"></span>Logout</a></li>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="container" style="padding-top: 10px;">
            <h1 class="page-header">Your Payments</h1>
            <div class="row">
                <!-- edit form column -->
                <div class="col-md-8 col-sm-6 col-xs-12">
                    <!--VIEW OUTSTANDING FEES-->
                    <form class="form-horizontal" role="form" action="ControllerServlet" method=get>
                        <input type = "hidden" name="nextAction" value="View Outstanding Rides"/>
                        <input class="btn btn-primary" value="View Outstanding Rides" type="submit"/>                                                                                         
                    </form>
                    <label class="col-lg-3 control-label">Outstanding Rides:</label>  
                    <table>
                        <%  
                            if(outstandingRides!= null){
                                for(String s : outstandingRides){
                                    out.println(s);
                                }
                            }
                                                                      
                        %> 
                    </table>
                </div>
                <div class="col-md-8 col-sm-6 col-xs-12">
                <!--PAY FOR RIDE & PAYMENT STATUS-->
                <form class="form-horizontal" role="form" action="ControllerServlet" method=post>                       
                    <div class="form-group"> 
                        <label class="col-lg-3 control-label">Ride ID</label>
                        <div class="col-xs-6 col-xs-6">
                            <input class="form-control" type="number" name="rideID" required>
                        </div>                            
                    </div>
                    <div class="form-group"> 
                        <label class="col-lg-3 control-label">Pay Amount</label>
                        <div class="col-xs-6 col-xs-6">
                            <input class="form-control" type="number" step="any" name="payAmount" required>
                        </div>                            
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Card Type</label>
                        <div class="col-xs-6 col-xs-6">
                            <input class="form-control" type="text" name="cardType" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Card Number</label>
                        <div class="col-xs-6 col-xs-6">
                            <input class="form-control" type="number" name="cardNumber" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Cardholder Name</label>
                        <div class="col-xs-6 col-xs-6">
                            <input class="form-control" type="text" name="cardName" required>
                        </div>                                                       
                    </div>                           
                    <div class="form-group">
                        <div class="col-md-8">                        
                            <input type = "hidden" name="nextAction" value="Pay"/>
                            <input class="btn btn-primary" value="Pay" type="submit"/>                               
                            <input class="btn btn-default" value="Reset" type="reset"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Payment Status:</label>
                        <div class="col-xs-6 col-xs-6">
                            <% 
                                if(prelimMsg != null){
                                    msg = prelimMsg ;
                                }
                                out.println(msg);
                            %>                               
                        </div>
                    </div>
                </form>
                </div>
                    <div class="row">
                        <!-- edit form column -->
                        <div class="col-md-8 col-sm-6 col-xs-12">
                            <!--VIEW OUTSTANDING FEES-->
                            <form class ="form-horizontal" role="form" action="ControllerServlet" method=get>
                                <input type = "hidden" name="nextAction" value="View Payment History"/>
                                <input class="btn btn-primary" value="View Payment History" type="submit"/>
                            </form>
                            <table>                                                
                                <%  
                                    if(data != null){
                                        for(String s : data){
                                            out.println(s);
                                        }
                                    }
                                %>  
                            </table>           
                            <br>
                        </div>
                    </div>
            </div>
        </div>                                
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://code.jquery.com/jquery.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
