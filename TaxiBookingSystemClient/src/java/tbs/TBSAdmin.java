/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tbs;

import ejb.ManagerBeanRemote;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.ejb.EJB;

/**
 *
 * @author Derian
 */
public class TBSAdmin {

    @EJB
    private static ManagerBeanRemote managerBean;
    
    public TBSAdmin(){}
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TBSAdmin client = new TBSAdmin();
        client.doTBS(args);
    }
    public void doTBS(String[] args){
        try{
            String choice = "";   
            while (!choice.equals("0")){
                System.out.println("\n~~~~~~Welcome to the Admin Client of TBS: Taxi Booking System~~~~~~");
                System.out.println("| A. Add User Accounts          | B. Delete User Accounts | C. Add Car");                     
                System.out.println("| D. Delete Car                 | E. Add Driver           | F. Update Driver");
                System.out.println("| G. Delete Driver              | H. Feedback Report      | I. View Idle Drivers");
                System.out.println("| J. Monthly Performance Report | K. View Unpaid rides    | L. Process Messages");
                System.out.println();
                System.out.println("| 0.  Exit");

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(System.in));
                System.out.print("\nEnter choice: ");
                try{
                    choice = br.readLine();
                    switch (choice) {
                        case "A":
                            displayAddUser();
                            break;
                        case "B":
                            displayDeleteUser();
                            break;
                        case "C":
                            displayAddCar();
                            break;
                        case "D":
                            displayDeleteCar();
                            break;
                        case "E":
                            displayAddDriver();
                            break;
                        case "F":
                            displayUpdateDriver();
                            break;
                        case "G":
                            displayDeleteDriver();
                            break;
                        case "H":
                            displayFeedbackReport();
                            break;
                        case "I":
                            displayViewIdleDrivers();
                            break;
                        case "J":
                            displayMonthlyPerfReport();
                            break;
                        case "K":
                            displayViewUnpaidRides();
                            break;
                        case "L":
                            displayProcessMessages();
                            break;
                        case "0":
                            System.out.println("~~~~~~Exiting~~~~~~");
                            break;
                        default:
                            System.out.println("Invalid choice! Please key in again.");
                            break;
                    }
                }catch(IOException io){
                            System.out.println("/n/t/tInvalid choice! Please key in again.");
                }
            }
        }
        catch(Exception ex){
            System.err.println("AN UNEXPECTED EXECPTION HAS OCCURED!!");
            ex.printStackTrace();
        }
    }
    /*
    HELPER METHOD
    Prompts & proccesses string input
    */
    private String processStringInput(String attribute) {
        Scanner sc = new Scanner(System.in);
        String input = null;       
        try{
            while(true){
                System.out.println("Enter "+attribute+":");               
                input = sc.nextLine().trim();
                if(input.isEmpty()){//if the user didn't type anything but pressed Enter
                    System.out.println("\n\tError: Invalid "+attribute+", please key in again.");       
                }
                else{
                    break;
                }
            }
        }catch(Exception ex){
            System.out.println("\n\t Error occured when processing your input: "+ex.getMessage()+"\n");
        }
        return input;
    }
    /*
    HELPER METHOD
    Prompts & processes number input
    */
    private int processNumberInput(String attribute) {
        Scanner sc = new Scanner(System.in);
        String input = null;
        int num=0;
        boolean reEnter=true;
        try{
            while(reEnter){
                System.out.println("Enter "+attribute+":");
                input = sc.nextLine().trim();
                if(input.isEmpty()){//if the user didn't type anything but pressed Enter
                    System.out.println("\n\tError: Invalid "+attribute+", please key in again.");       
                }else{
                    try{
                        num = Integer.parseInt(input);
                        if(num >= 0)
                            reEnter=false;
                        else
                            System.out.println("\n\tError: Cannot enter a negative value for this field");
                    }catch (NumberFormatException ex) {
                       System.out.println("\n\t Error: Please key in a non-negative integer");
                    }
                }
            }
        }catch(Exception e){
            System.out.println("\n\t Error occured when processing your input: "+e.getMessage()+"\n");
        }
        return num;
    }
    private void displayAddUser() {
        String userName,password,address,email;   
        int contactNumber; 

        try {
            System.out.println("\n\t\tAdd A User");
            userName = processStringInput("User Name");
            password = processStringInput("Password");
            contactNumber = processNumberInput("telNumber");
            email = processStringInput("email");
            address = processStringInput("address");
            managerBean.addUser(userName, password, contactNumber, address, email);
            System.out.println("\n\t\tUSER ADDED.");
        } catch(Exception ex) {
            System.out.println("\nFailed to add User because: " + 
                    ex.getMessage());
        }
    }    
    private void displayDeleteUser() {
        String userName;
        try{
            System.out.println("\n\t\tDelete A User");
            userName = processStringInput("User Name");
            managerBean.deleteUser(userName);
            System.out.println("\n\t\tUSER DELETED.");
        }catch(Exception ex){
            System.out.println("\n Failed to delete User because: "+ ex.getMessage());
        }
    }
    private void displayAddCar(){
        String registrationNumber;
        String carBrand;
        String carModel;
        int manufacturedYear;
        
        try{
            System.out.println("\n\t\tAdd A Car");
            registrationNumber = processStringInput("Registration Number");
            carBrand = processStringInput("Car Brand");
            carModel = processStringInput("Car Model");
            manufacturedYear = processNumberInput("Manufacured Year");
            managerBean.addCar(registrationNumber, carBrand, carModel, manufacturedYear);
            System.out.println("\n\t\tCAR ADDED.");
        }catch(Exception ex){
            System.out.println("\n Failed to add Car because: "
                    + ex.getMessage());       
        }
    }
    private void displayDeleteCar(){
        String reg;       
        try{
            System.out.println("\n\t\tDelete A Car");
            reg = processStringInput("Registration Number");
            managerBean.deleteCar(reg);
            System.out.println("\n\t\tCAR DELETED.");
        }catch(Exception ex){
            System.out.println("\n Failed to delete Car because: "+ ex.getMessage());
        }
    }
    private void displayAddDriver(){
        String drivingLicenseNumber,name,address,carRegNum;
        int contactNumber=0,currLatitude=0,currLongitude=0;
        
        try{
            System.out.println("\n\t\tAdd A Driver");
            drivingLicenseNumber = processStringInput("Driving License Number");
            name = processStringInput("Name");
            contactNumber = processNumberInput("Contact Number");
            address = processStringInput("Address");           
            //check if latitude is valid
            boolean invalidLat = true;
            while(invalidLat){
                currLatitude = processNumberInput("Current Latitude");
                if(currLatitude>=0 && currLatitude<=100)
                    invalidLat = false;
                else
                    System.out.println("Invalid latitude. Please key in again!");
            }
            //check if longitude is valid
            boolean invalidLong = true;
            while(invalidLong){
                currLongitude = processNumberInput("Current Longitude");
                if(currLongitude>=0 && currLongitude<=100)
                    invalidLong = false;
                else
                    System.out.println("Invalid longitude. Please key in again!");
            }
            carRegNum = processStringInput("Car Registration Number");
            managerBean.addDriver(drivingLicenseNumber, name, contactNumber, 
                    address, currLatitude, currLongitude, carRegNum);
            System.out.println("\n\t\tDRIVER ADDED.");
        }catch(Exception ex){
            System.out.println("\n Failed to add Driver because: "
                    + ex.getMessage());       
        }
    }
    private void displayUpdateDriver() {
        String drivingLicenseNumber, newName=null, newAddress=null,
                newLicenseNumber=null,newCarRegNum=null;
        int newContact=-1,newLat=-1,newLong=-1;
        try{

            System.out.println("\n\t\tUpdate A Driver");
            drivingLicenseNumber = processStringInput("Driving License Number");
            Scanner sc = new Scanner(System.in);

            System.out.println("Update License Number? (Y/N)");
            while(true){
                String choice = sc.nextLine().trim();
                if(!choice.equals("Y") && !choice.equals("N"))
                    System.out.println("Please choose whether to update or not"
                            + " by entering the Y or N");
                else if(choice.equals("Y")){
                    newLicenseNumber = processStringInput("New License Number");
                    break;
                }else
                    break;
            }
            

            System.out.println("Update name? (Y/N)");
            while(true){
                String choice = sc.nextLine().trim();
                if(!choice.equals("Y") && !choice.equals("N"))
                    System.out.println("Please choose whether to update or not"
                            + " by entering the Y or N");
                else if(choice.equals("Y")){
                    newName = processStringInput("New Name");
                    break;
                }else
                    break;
            }

            System.out.println("Update contact number? (Y/N)");
            while(true){
                String choice = sc.nextLine().trim();
                if(!choice.equals("Y") && !choice.equals("N"))
                    System.out.println("Please choose whether to update or not"
                            + " by entering the Y or N");
                else if(choice.equals("Y")){
                    newContact = processNumberInput("New Contact");
                    break;
                }else
                    break;
            }

            System.out.println("Update address? (Y/N)");
            while(true){
                String choice = sc.nextLine().trim();
                if(!choice.equals("Y") && !choice.equals("N"))
                    System.out.println("Please choose whether to update or not"
                            + " by entering the Y or N");
                else if(choice.equals("Y")){
                    newAddress = processStringInput("New Address");
                    break;
                }else
                    break;
            }
   
            System.out.println("Update latitude? (Y/N)");
            while(true){
                String choice = sc.nextLine().trim();
                if(!choice.equals("Y") && !choice.equals("N"))
                    System.out.println("Please choose whether to update or not"
                            + " by entering the Y or N");
                else if(choice.equals("Y")){
                    while(true){
                        newLat = processNumberInput("New Latitude");
                        if(newLat>=0 && newLat<=100)
                            break;
                        else
                            System.out.println("Invalid latitude. Please key in again!");
                    }
                    break;
                }else
                    break;
            }            
            
            System.out.println("Update longitude? (Y/N)");
            while(true){
                String choice = sc.nextLine().trim();
                if(!choice.equals("Y") && !choice.equals("N"))
                    System.out.println("Please choose whether to update or not"
                            + " by entering the Y or N");
                else if(choice.equals("Y")){
                    while(true){
                        newLong = processNumberInput("New Longitude");
                        if(newLong>=0 && newLong<=100)
                            break;
                        else
                            System.out.println("Invalid longitude. Please key in again!");
                    }
                    break;
                }else
                    break;
            }               
            
            System.out.println("Update car registration number? (Y/N)");
            while(true){
                String choice = sc.nextLine().trim();
                if(!choice.equals("Y") && !choice.equals("N"))
                    System.out.println("Please choose whether to update or not"
                            + " by entering the Y or N");
                else if(choice.equals("Y")){
                    newCarRegNum = processStringInput("New Car Registration Number");
                    break;
                }else
                    break;
            }
            //send to bean
            String status = managerBean.updateDriver(drivingLicenseNumber,
                    newName, newContact, newAddress, newLat, 
                    newLong,newLicenseNumber,newCarRegNum);
            System.out.println("\n\t\t"+ status);
            
        }catch(Exception ex){
            System.out.println("\n Failed to update Driver because: "
                    + ex.getMessage());
        
        }
    }
    private void displayDeleteDriver() {
        String licenseNumber;  
        try{
            System.out.println("\n\t\tDelete A Driver");
            licenseNumber = processStringInput("License Number");
            managerBean.deleteDriver(licenseNumber);
            System.out.println("\n\t\tDRIVER DELETED.");
        }catch(Exception ex){
            System.out.println("\n Failed to delete Driver because: "+ ex.getMessage());
        }
    }
    private void displayFeedbackReport() {
        String licNum;
        try{
            System.out.println("\n\t\tDisplay Summarised Feedback Report"
                    + " Of Driver");
            licNum = processStringInput("License Number");
            System.out.println("\tFEEDBACK REPORT OF DRIVER "+licNum);
            int[] ratings=managerBean.generateDriverFeedbackReport(licNum);
            String star = "*";
            for(int i=0; i<5; i++){
                System.out.println(star+" Feedbacks: "+ratings[i]);
                star += " *";
            }
            System.out.println("\n\t\tREPORT GENERATED.");
        }catch(Exception ex){
            System.out.println("\n Failed to generate feedback report because: "+ ex.getMessage());
        }
    }
    private void displayViewIdleDrivers() {
         try{
            System.out.println("\n\t\tView Idle Drivers");
            System.out.println("\n\tList of Idle Drivers");
            ArrayList<String> idleList=managerBean.generateIdleDrivers();
            for(int i=0; i<idleList.size(); i++){
                System.out.println(idleList.get(i));
            }
            System.out.println("\n\t\tLIST GENERATED.");
        }catch(Exception ex){
            System.out.println("\n Failed to generate "
                    + "list of idle drivers because: "+ ex.getMessage());
        }
    }
    private void displayMonthlyPerfReport() {
        try{
            System.out.println("\n\t\tView Monthly Performance Report");
            System.out.println("\n\tPerformance of Drivers this month");
            ArrayList<String> perfList=managerBean.generateMonthly();
            for(int i=0; i<perfList.size(); i++){
                System.out.println(perfList.get(i));
            }
            System.out.println("\n\t\tLIST GENERATED.");
        }catch(Exception ex){
            System.out.println("\n Failed to generate "
                    + "list of idle drivers because: "+ ex.getMessage());
        }
    }
    private void displayViewUnpaidRides() {
        try{
            System.out.println("\n\t\tView Unpaid Rides");
            System.out.println("\n\tList of Unpaid Rides:");
            ArrayList<String> unpaidList=managerBean.generateUnpaid();
            for(int i=0; i<unpaidList.size(); i++){
                System.out.println(unpaidList.get(i));
            }
            System.out.println("\n\t\tLIST GENERATED.");
        }catch(Exception ex){
            System.out.println("\n Failed to generate "
                    + "list of unpaid rides because: "+ ex.getMessage());
        }
    }
    private void displayProcessMessages() {
        try{
            System.out.println("\n\t\tProcess Messages from Users");
            System.out.println("\n\tList of Unread Messages: ");
            ArrayList<String> unpaidList=managerBean.generateMessages();
            for(int i=0; i<unpaidList.size(); i++){
                System.out.println(unpaidList.get(i));
            }
            System.out.println("\n\t\tLIST GENERATED.");
            while(true){
                System.out.println("Would you like"
                    + " to process any messages? (Y/N)");
                String choice = processStringInput("choice");
                if(choice.equals("Y")){
                    System.out.println("Please select message ID of the message you"
                            + " wish to process");
                    String ID = processStringInput("Message ID");
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Enter your comments, if any:");
                    String comments = sc.nextLine();
                    managerBean.updateMessage(ID,comments);
                }else if(choice.equals("N")){
                    break;
                }else{
                    System.out.println("Please type Y or N");
                }
            }
        }catch(Exception ex){
            System.out.println("\n Failed to generate "
                    + "list of pending messages or update message because: "+ ex.getMessage());
        }
    }
}

    
