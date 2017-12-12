/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author Derian
 */
@Remote
public interface ManagerBeanRemote {

    public void addUser(String userName, String password, int contactNumber, String address, String email) throws Exception;

    public void deleteUser(String userName) throws Exception;

    public void addCar(String registrationNumber, String carBrand, 
            String carModel, int manufacturedYear) throws Exception;

    public void deleteCar(String reg) throws Exception;

    public void addDriver(String drivingLicenseNumber, String name, 
            int contactNumber, String address, int currLatitude, 
            int currLongitude, String carRegNum) throws Exception;

    public String updateDriver(String drivingLicenseNumber, String newName, 
            int newContact, String newAddress, int newLat, int newLong, 
            String newLicNum, String newCarRegNum) throws Exception;

    public void deleteDriver(String licenseNumber) throws Exception;

    public int[] generateDriverFeedbackReport(String LicNum) throws Exception;

    public ArrayList<String> generateIdleDrivers() throws Exception;

    public ArrayList<String> generateMonthly() throws Exception;

    public ArrayList<String> generateUnpaid() throws Exception;

    public ArrayList<String> generateMessages() throws Exception;

    public void updateMessage(String ID, String comments) throws Exception;

    public boolean login(String userName, String password) throws Exception;

    public void updateUser(String email, String address, 
            String pass, Integer contact, String currUser) throws Exception;

    public String bookRide(Integer latitude, Integer longitude, String userName) throws Exception;

    public ArrayList<String> viewRides(String userName);

    public String cancelRide(String id);

    public ArrayList<String> viewOutstanding(String userName);

    public ArrayList<String> viewPaymentHistory(String userName);

    public String payRide(String userName, String cardName, String cardType, String cardNumber, Integer rideID, String payAmount);

    public String postFeedback(String userName, Integer rideID, Integer rating, String comment);

    public ArrayList<String> viewMessages(String userName);

    public String sendMessage(String userName, String msg);
    
}
