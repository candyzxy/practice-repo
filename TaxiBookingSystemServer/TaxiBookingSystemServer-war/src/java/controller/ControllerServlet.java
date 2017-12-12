/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.ManagerBeanRemote;
import java.io.IOException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Derian
 */
@WebServlet(name = "ControllerServlet")
public class ControllerServlet extends HttpServlet {

    @EJB
    private ManagerBeanRemote managerBean;
    
    private String status = null;
    private ArrayList data = new ArrayList();
    String userName = "";
    @Override
    public void init(){
        System.out.println("ControllerServlet: init()");
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
        System.out.println("ControllerServlet: processRequest()");
        try{
            String nextAction = request.getParameter("nextAction");            
            if("LogInStatus".equals(nextAction)){
                //log in successful
                if(logInUser(request)){
                    //extracts logged in username and sends it as a request to Profile.jsp
                    userName = (String)request.getParameter("userName");
                    nextAction = "WelcomePage";
                    request.setAttribute("userName", userName);
                }else{
                    //throw to login status page
                    status = "Login failed. Please make sure you have registered and have entered "
                            + "the correct login details.";
                    request.setAttribute("data", status);                                             
                }
            }else if("RegistrationStatus".equals(nextAction)){
                registerUser(request);
                request.setAttribute("data",status);
            }else if("Profile".equals(nextAction)){
                updateUser(request);
            }else if("Book A Ride".equals(nextAction)){
                nextAction = "Book";
                status = bookRide(request);
                request.setAttribute("message",status);               
            }else if("View Rides".equals(nextAction)){
                nextAction = "Rides";
                data = viewRides(request);
                request.setAttribute("data", data);
            }else if("Cancel This Ride".equals(nextAction)){
                nextAction = "Rides";
                status = cancelRide(request);
                request.setAttribute("cancelStatus",status); 
            }else if("View Outstanding Rides".equals(nextAction)){
                nextAction = "Payments";
                data = viewOutstanding(request);
                request.setAttribute("outstandingRides", data);
            }else if("View Payment History".equals(nextAction)){
                nextAction = "Payments";
                data = viewPaymentHistory(request);
                request.setAttribute("data", data);
            }else if("Pay".equals(nextAction)){
                nextAction = "Payments";
                status = payRide(request);
                request.setAttribute("message",status);
            }else if("Provide Feedback".equals(nextAction)){
                nextAction = "Rides";
                status = postFeedback(request);
                request.setAttribute("message",status);
            }else if("View Messages".equals(nextAction)){
                nextAction = "Messages";
                data = viewMessages(request);
                request.setAttribute("data",data);
            }else if("Send Message".equals(nextAction)){
                nextAction = "Messages";
                status = sendMessage(request);
                request.setAttribute("message", status);
            }            
            request.getRequestDispatcher("/"+nextAction+".jsp")
                    .forward(request, response); 
        }catch(Exception e){
            log("Exception in SecondBankServlet.processRequest()");
            System.out.println(e.getMessage());
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ControllerServlet: doGet");
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ControllerServlet: doPost");
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    private boolean logInUser(HttpServletRequest request){
        String userName = request.getParameter("userName");
        String pass = request.getParameter("password");
        boolean success=false;
        try{
        success = managerBean.login(userName,pass);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return success;
    }
    private void registerUser(HttpServletRequest request){
        String pass = request.getParameter("password");
        int contactNumber = Integer.valueOf(request.getParameter("contact"));
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String userID = request.getParameter("userName");
        try{
            managerBean.addUser(userID, pass, contactNumber, address, email);
            status = "User "+userID+" has been successfuly registered";
            
        }catch(Exception e){
            status = e.getMessage();
            request.setAttribute("data",status);
        }     
    }
    private void updateUser(HttpServletRequest request){   
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String pass = request.getParameter("password");
        Integer contact = -1;
        if(!request.getParameter("contact").isEmpty())
            contact = Integer.valueOf(request.getParameter("contact"));       
        String currUser = userName;
        try{
            managerBean.updateUser(email, address, pass, contact,currUser);
        }catch(Exception e){
            System.out.println("Exception at Controller Servlet: managerBean.updateUser()");
            System.out.println(e.getMessage());
        }
    }
    private String bookRide(HttpServletRequest request){
        /*
        Create a ride, take in latitude and longitude as input. 
        */
        String latitudeInput = request.getParameter("latitude");
        Integer latitude = -1;
        if(!latitudeInput.isEmpty())
            latitude = Integer.valueOf(latitudeInput);
        String longitudeInput = request.getParameter("longitude");
        Integer longitude = -1;
        if(!longitudeInput.isEmpty())
            longitude = Integer.valueOf(latitudeInput);
        
        if((latitude>100 || latitude<0) || (longitude>100 || longitude<0)){
            return "Error: Please make sure the latitude and longitude are between"
                    + " 0 and 100";
        }
        try{
            return managerBean.bookRide(latitude,longitude,userName);        
        }catch(Exception e){
            return e.getMessage();
        }
    }
    private ArrayList<String> viewRides(HttpServletRequest request){
        return managerBean.viewRides(userName);
    }
    private String cancelRide(HttpServletRequest request) {
        String id = request.getParameter("rideID");
        return managerBean.cancelRide(id);
    }
    private ArrayList<String> viewOutstanding(HttpServletRequest request) {
        return managerBean.viewOutstanding(userName);
    }
    private ArrayList<String> viewPaymentHistory(HttpServletRequest request) {
        return managerBean.viewPaymentHistory(userName);
    }
    private String payRide(HttpServletRequest request) {
        Integer rideID = Integer.valueOf(request.getParameter("rideID"));
        String cardName = (String)request.getParameter("cardName");
        String cardType = (String)request.getParameter("cardType");
        String cardNumber = (String)request.getParameter("cardNumber");
        String payAmount = (String)(request.getParameter("payAmount"));
        return managerBean.payRide(userName,cardName, 
                cardType, cardNumber, rideID, payAmount);
    }
    private String postFeedback(HttpServletRequest request) {
        Integer rideID = Integer.valueOf(request.getParameter("rideID"));
        Integer rating = Integer.valueOf(request.getParameter("rating"));
        String comment = (String) request.getParameter("comment");
        return managerBean.postFeedback(userName, rideID, rating, comment);
    }
    private ArrayList<String> viewMessages(HttpServletRequest request) {
        return managerBean.viewMessages(userName);
    }
    private String sendMessage(HttpServletRequest request) {
        String msg = (String)request.getParameter("message");
        return managerBean.sendMessage(userName,msg);
    }

}
