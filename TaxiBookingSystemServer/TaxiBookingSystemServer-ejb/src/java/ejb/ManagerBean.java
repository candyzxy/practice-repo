/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Derian
 */
@Stateless
public class ManagerBean implements ManagerBeanRemote {
    @PersistenceContext
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void addUser(String userName, String password, int contactNumber,
            String address, String email) throws Exception{
        Query query = em.createQuery(
                "FROM SystemUser u WHERE u.userName=:UN");
        query.setParameter("UN", userName);
        List<UserEntity> duplicates = query.getResultList();
        if(duplicates.isEmpty()){//no one with the userName has been
            //registered yet
            UserEntity user =
                    new UserEntity(userName, encryptPassword(password),
                            contactNumber, address, email);
            em.persist(user);
        }else{
            throw new Exception("\tUser Name has already been taken! "
                    + "Please enter another one");
        }
    }
    @Override
    public void deleteUser(String userName) throws Exception{
        Query query = em.createQuery(
                "FROM SystemUser u WHERE u.userName=:UN");
        query.setParameter("UN", userName);
        List result = query.getResultList();
        if(!result.isEmpty()){//User exists
            Query query2 = em.createQuery(
                    "SELECT u FROM SystemUser u WHERE u.userName=:UN "
                            + "AND u.payments IS EMPTY "
                            + "AND u.rides IS EMPTY "
                            + "AND u.feedbacks IS EMPTY "
                            + "AND u.messages is EMPTY ");
            query2.setParameter("UN", userName);
            List result2 = query2.getResultList();
            if(result2.isEmpty()){//user is associated with payments/rides/feedbacks/messages
                throw new Exception("\tUser cannot be deleted from TBS. "
                        + "User has already performed functions in TBS");
            }else{//remove user from database
                Iterator it = result2.iterator();
                UserEntity userToDelete = (UserEntity) it.next();
                em.remove(userToDelete);
            }
        }else{//User does not exist
            throw new Exception("\tNo such User exists in the TBS. "
                    + "Please register the User first.");
        }
    }
    @Override
    public void addCar(String registrationNumber, String carBrand,
            String carModel, int manufacturedYear) throws Exception{
        Query query = em.createQuery(
                "FROM Car c WHERE c.registrationNumber=:PlateNum");
        query.setParameter("PlateNum", registrationNumber);
        List<CarEntity> duplicates = query.getResultList();
        if(duplicates.isEmpty()){//no car with the plate number has been
            //registered yet
            CarEntity car =
                    new CarEntity(registrationNumber, carBrand,
                            carModel, manufacturedYear);
            em.persist(car);
        }else{
            throw new Exception("\tCar has already been registered in TBS! "
                    + "Please enter another registration number");
        }
    }
    @Override
    public void deleteCar(String reg) throws Exception {
        Query query = em.createQuery(
                "FROM Car c WHERE c.registrationNumber=:PlateNum");
        query.setParameter("PlateNum", reg);
        List result = query.getResultList();
        if(!result.isEmpty()){//Car exists
            Iterator it = result.iterator();
            CarEntity carToDelete = (CarEntity) it.next();
            if(carToDelete.getDriver()!=null){//Car is associated with a driver
                throw new Exception("\tCar is already associated with driver and cannot be deleted from TBS");
            }else{//remove car from database
                em.remove(carToDelete);
            }
        }else{//Car does not exist
            throw new Exception("\tNo such Car exists in TBS");
        }
    }
    @Override
    public void addDriver(String drivingLicenseNumber, String name,
            int contactNumber, String address,
            int currLatitude, int currLongitude,
            String carRegNum) throws Exception{
        Query query = em.createQuery(
                "FROM Driver d WHERE d.drivingLicenseNumber=:LicNum");
        query.setParameter("LicNum", drivingLicenseNumber);
        List<DriverEntity> duplicates = query.getResultList();
        Query query2 = em.createQuery(
                "FROM Car c WHERE c.registrationNumber=:RegNum");
        query2.setParameter("RegNum", carRegNum);
        List<CarEntity> cars = query2.getResultList();
        if(duplicates.isEmpty()){//no driver with the license number has been
            //registered yet
            if(!cars.isEmpty()){//there is a car with that registration
                //number registered.
                Iterator it = cars.iterator();
                CarEntity car = (CarEntity)it.next();
                if(car.getDriver()==null){//car is not associated with any driver
                    DriverEntity driver =
                            new DriverEntity(drivingLicenseNumber, name, contactNumber,
                                    address, currLatitude, currLongitude,car);
                    em.persist(driver);
                    car.setDriver(driver);
                }else{//car is already associated with a driver
                    throw new Exception("\t This car is already associated with another driver!");
                }
            }else{
                throw new Exception("\t Driver's car has not been "
                        + "registered into TBS! Please register it first!");
            }
        }else{
            throw new Exception("\tDriver has already been registered! "
                    + "Please enter another license number");
        }
    }
    @Override
    public String updateDriver(String drivingLicenseNumber, String newName,
            int newContact, String newAddress,
            int newLat, int newLong,
            String newLicNum, String newCarRegNum) throws Exception {
        String status = "";
        Query query = em.createQuery(
                "FROM Driver d WHERE d.drivingLicenseNumber=:LicNum");
        query.setParameter("LicNum", drivingLicenseNumber);
        List<DriverEntity> result = query.getResultList();
        if(!result.isEmpty()){//driver with the license number exists
            Iterator it = result.iterator();
            DriverEntity driver = (DriverEntity) it.next();
            if(driver.getRides().isEmpty()){//driver is not associated with any rides
                if(newLicNum!=null&&!newLicNum.equals(""))
                    driver.setDrivingLicenseNumber(newLicNum);//1:change all fields except Car
                if(newName!=null&&!newName.equals(""))
                    driver.setName(newName);
                if(newContact!=-1)
                    driver.setContactNumber(newContact);
                if(newAddress!=null&&!newAddress.equals(""))
                    driver.setAddress(newAddress);
                if(newLat!=-1)
                    driver.setCurrentLatitude(newLat);
                if(newLong!=-1)
                    driver.setCurrentLongitude(newLong);
                if(newCarRegNum!=null){
                    //Look up car in database
                    Query query2 = em.createQuery(
                            "FROM Car c WHERE c.registrationNumber=:PlateNum");
                    query2.setParameter("PlateNum", newCarRegNum);
                    List<CarEntity> result2 = query2.getResultList();
                    if(!result2.isEmpty()){
                        Iterator it2 = result2.iterator();
                        CarEntity newCar = (CarEntity) it2.next();
                        driver.setCar(newCar);//2:change associated car of this driver.
                    }else{//Tell admin to register Car first
                        throw new Exception("\t New Car is not registered in TBS. "
                                + "Please register it first.");
                    }
                }
                status = "DRIVER UPDATED.";
            }else{//driver associated with rides, change everything except LicNum and Associated Car
                if(newName!=null&&!newName.equals(""))
                    driver.setName(newName);
                if(newContact!=-1)
                    driver.setContactNumber(newContact);
                if(newAddress!=null&&!newAddress.equals(""))
                    driver.setAddress(newAddress);
                if(newLat!=-1)
                    driver.setCurrentLatitude(newLat);
                if(newLong!=-1)
                    driver.setCurrentLongitude(newLong);
                status = "Driver is associated with a ride, so driving license number and"
                        + " car could not be changed, if they were specified.";
            }
        }else{//driver does not exist
            throw new Exception("\tDriver has not been registered! "
                    + "Please enter another license number");
        }
        return status;
    }
    @Override
    public void deleteDriver(String licenseNumber) throws Exception {
        Query query = em.createQuery(
                "FROM Driver d WHERE d.drivingLicenseNumber=:LicNum");
        query.setParameter("LicNum", licenseNumber);
        List<DriverEntity> result = query.getResultList();
        if(!result.isEmpty()){//driver with the license number exists
            Iterator it = result.iterator();
            DriverEntity driverToDelete = (DriverEntity)it.next();
            if(driverToDelete.getCar().equals(null)&&driverToDelete.getRides().isEmpty())
                //if driver is not associated with a Car and any Rides, then delete
                em.remove(driverToDelete);
            else
                throw new Exception("\tDriver is associated with a car or rides! "
                        + "Cannot be deleted!");
        }else{//driver does not exist
            throw new Exception("\tDriver does not exist! "
                    + "Please enter another license number");
        }
    }
    @Override
    public int[] generateDriverFeedbackReport(String LicNum) throws Exception {
        
        Query query = em.createQuery(
                "SELECT d FROM Driver d WHERE d.drivingLicenseNumber=:LN");
        query.setParameter("LN", LicNum);
        int[] ratings = new int[5];
        List<DriverEntity> result = query.getResultList();
        if(!result.isEmpty()){//driver with the license number exists
            Iterator it2 = result.iterator();
            DriverEntity driver = (DriverEntity) it2.next();
            Collection<RideEntity> rides = driver.getRides();
            if(!rides.isEmpty()){
                //the driver has driven some rides
                for (RideEntity ride : rides) {
                    int rating = ride.getFeedback().getRating();
                    switch(rating){
                        case 1:
                            ratings[0]++;
                            break;
                        case 2:
                            ratings[1]++;
                            break;
                        case 3:
                            ratings[2]++;
                            break;
                        case 4:
                            ratings[3]++;
                            break;
                        case 5:
                            ratings[4]++;
                            break;
                        default:
                            break;
                    }
                }
            }else{
                throw new Exception("\tDriver does not have any rides "
                        + "registered under him!");
            }
        }else{//driver does not exist
            throw new Exception("\tDriver has not been registered! "
                    + "Please enter another license number");
        }
        return ratings;
    }
    @Override
    public ArrayList<String> generateIdleDrivers() throws Exception {
        //To store toPrint of all free driver entities.
        ArrayList<String> idleList = new ArrayList<>();
        
        ArrayList<DriverEntity> free = searchIdle();
        if(free.isEmpty()){
            throw new Exception("There are no idle drivers in TBS");
        }else{
            for (DriverEntity driver : free) {
                String row = driver.toString();
                idleList.add(row);
            }
        }
        return idleList;
    }
    @Override
    public ArrayList<String> generateMonthly() throws Exception {
        ArrayList<String> perfList = new ArrayList<>();
        Query query1 = em.createQuery(
                "SELECT d FROM Driver d");
        List<DriverEntity> drivers = query1.getResultList();
        if(!drivers.isEmpty()){//there are drivers in the system
            //loop through all the drivers in the system
            for (DriverEntity driver : drivers) {
                int hours = 0, minutes = 0, seconds = 0;
                double distance =0, rev=0;
                Collection<RideEntity> rides = driver.getRides();
                if(!rides.isEmpty()){
                    for (RideEntity ride : rides) {
                        //Calculate total time on road of each ride
                        //assuming all rides start and end on the same day
                        double h = ride.getEndTime()[0]-ride.getStartTime()[0];
                        if(h<0){
                            h += 24;
                        }
                        double m = ride.getEndTime()[1]-ride.getStartTime()[1];
                        if(m<0){//shave off 1 hour from the hour difference
                            h--;
                            m += 60;
                        }
                        double s = ride.getEndTime()[2]-ride.getStartTime()[2];
                        if(s<0){//shave off 1 minute from the minute difference
                            m--;
                            s += 60;
                        }
                        //Calculate total distance of each ride
                        double dist = calculateDist(ride.getStartLatitude(),
                                ride.getEndLatitude(),
                                ride.getStartLongitude(),
                                ride.getEndLongitude());
                        distance += dist;
                        
                        //Calculate total revenue of all rides
                        //if ride has ended, add the fee to total revenue
                        if(ride.getEndLatitude()!=-1 && ride.getEndLongitude()!=-1)
                            rev += ride.getFee();
                        //round up seconds into minutes
                        seconds+=s;
                        if(seconds>=60){
                            minutes += seconds/60;
                            seconds = seconds%60;
                        }
                        //round up minutes into hours
                        minutes += m;
                        if(minutes>=60){
                            hours += minutes/60;
                            minutes = minutes%60;
                        }
                        //add up hours
                        hours += h;
                    }
                }
                DecimalFormat df = new DecimalFormat("0.00");
                distance = Double.valueOf(df.format(distance));
                String row = "Name: "+driver.getName()+" || "
                        +"License Number: "+driver.getDrivingLicenseNumber()+"\n"
                        +"Total Revenue: "+rev+" || "
                        +"Total Number of Rides: "+driver.getRides().size()+" || "
                        +"Total Ride Time: "+hours+"h "+minutes+"m "+seconds+"s || "
                        +"Total Distance: "+distance+"\n";
                perfList.add(row);
            }
        }else{
            throw new Exception("There are no drivers registered in TBS!");
        }
        return perfList;
    }
    @Override
    public ArrayList<String> generateUnpaid() throws Exception {
        ArrayList<String> unpaidList = new ArrayList<>();
        ArrayList<RideEntity> unpaid = searchUnpaidRides();
        //if this runs, means there are unpaid rides
        if(!unpaid.isEmpty()){
            for (RideEntity ride : unpaid) {
                String row = ride.toString()+"\n"
                        +ride.getUser().toString()+"\n";
                unpaidList.add(row);
            }
        }else
            throw new Exception("There are no unpaid rides!");
        return unpaidList;
    }
    @Override
    public ArrayList<String> generateMessages() throws Exception {
        ArrayList<String> messageList = new ArrayList<>();
        //select all messages which are unread
        Query query1 = em.createQuery(
                "SELECT m FROM Message m");
        List<MessageEntity> messages = query1.getResultList();
        if(!messages.isEmpty()){
            for(MessageEntity m : messages){
                if(m.getStatus().equals("unread")){
                    String row = m.toString()+"\n";
                    messageList.add(row);
                }
            }
            if(messageList.isEmpty()){
                throw new Exception("There are no unread messages in TBS!");
            }
        }else{
            throw new Exception("There are no messages stored in TBS!");
        }
        return messageList;
    }
    @Override
    //HELPER METHODS
    public void updateMessage(String ID, String comments){
        
        Query query1 = em.createQuery(
                "SELECT m FROM Message m WHERE m.messageID=:MsgID");
        query1.setParameter("MsgID",Integer.valueOf(ID));
        try{
            MessageEntity msg =(MessageEntity) query1.getSingleResult();
            msg.setStatus("processed");
            msg.setComment(comments);
        }catch(EntityNotFoundException e){
            System.out.println("Error updating message: There is no such"
                    + "message with that MessageID");
        }catch(NonUniqueResultException e){
            System.out.println("Error updating message: There is more than one"
                    + "message with that MessageID");
        }
    }
    private double calculateDist(int startLat, int endLat, int startLong, int endLong){
        return Math.sqrt(Math.pow((double)endLat-(double)startLat, 2)
                +Math.pow((double)endLong-(double)startLong, 2));
    }
    private String encryptPassword(String pass) throws Exception{
        MessageDigest digester = MessageDigest.getInstance("MD5");
        digester.update(pass.getBytes("UTF-8"));
        byte[] d = digester.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : d) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
    private ArrayList<DriverEntity> searchIdle(){
        ArrayList<DriverEntity> free = new ArrayList<>();       
        //Get all drivers and assume they are free
        Query query2 = em.createQuery(
                "SELECT d FROM Driver d");
        List<DriverEntity> newbies = query2.getResultList();
        if(!newbies.isEmpty()){
            for(DriverEntity r : newbies){
                free.add(r);
            }
        }        
        //remove all drivers who are not free
        Query query1 = em.createQuery(
                "SELECT r FROM Ride r");
        List<RideEntity> rides = query1.getResultList();
        if(!rides.isEmpty()){
            //go through all rides that have not ended.
            //mark the driver of the ride as busy
            //remove this driver from the free list
            for (RideEntity r : rides) {
                if(r.getEndLatitude() == -1 && r.getEndLongitude() == -1){
                    DriverEntity busy = r.getDriver();
                    if(!free.isEmpty()){
                        Iterator<DriverEntity> it = free.iterator();
                        while(it.hasNext()){
                            DriverEntity d = it.next();
                            if(busy.equals(d))
                                it.remove();
                        }
                    }
                }                
            }
        }
        
        return free;
    }
    private ArrayList<RideEntity> searchUnpaidRides(){
        ArrayList<RideEntity> unpaid = new ArrayList<>();
        Query query1 = em.createQuery(
                "SELECT r FROM Ride r");
        List<RideEntity> rides = query1.getResultList();
        if(!rides.isEmpty()){
            for (RideEntity ride : rides) {
                //ride has not been paid
                if(ride.getPayment() == null){
                    unpaid.add(ride);
                }
            }
        }
        return unpaid;
    }
    //WEB BASED
    @Override
    public boolean login(String userName, String password) throws Exception {
        password = encryptPassword(password);
        Query query1 = em.createQuery(
                "SELECT u FROM SystemUser u WHERE u.userName=:UN");
        query1.setParameter("UN",userName);
        List<UserEntity> users = query1.getResultList();
        if(!users.isEmpty()){//user exists in the system.
            for(UserEntity u : users){
                return u.getPassword().equals(password);
            }
        }
        return false;
    }
    @Override
    public void updateUser(String email, String address,
            String pass, Integer contact, String currUser) throws Exception {
        Query query = em.createQuery(
                "FROM SystemUser u WHERE u.userName=:UN");
        query.setParameter("UN", currUser);
        try{
            UserEntity u = (UserEntity) query.getSingleResult();
            if(u!=null){
                if(!email.isEmpty())
                    u.setEmail(email);
                if(!pass.isEmpty())
                    u.setPassword(encryptPassword(pass));
                if(!address.isEmpty())
                    u.setAddress(address);
                if(contact!=-1)
                    u.setContactNumber(contact);
            }else{
                throw new Exception("No such user named "+currUser+" exists");
            }
        }catch(Exception e){
            System.out.println("Exception at managerBean.updateUser()");
            System.out.println(e.getMessage());
        }       
    }
    @Override
    public String bookRide(Integer latitude, Integer longitude, String userName) throws Exception {
        //check the current location of each driver. 
        //if the driver is the closest to the location assign him
        String status = "Error : ride not persisted";
        ArrayList<DriverEntity> free = searchIdle();
        DriverEntity assigned = null;
        double minDist = Integer.MAX_VALUE;
        for(DriverEntity d : free){
            double distance = calculateDist(d.getCurrentLatitude(),latitude,d.getCurrentLongitude(),longitude);
            if(distance < minDist){
                minDist = distance;
                assigned = d;
            }
        }
        if(assigned == null){
            return "No Driver available!";
        }
//check if user is currently on any ride
        boolean userNotOnRide = true;
        Query query1 = em.createQuery(
                "SELECT r FROM Ride r");
        List<RideEntity> rides = query1.getResultList();
        if(!rides.isEmpty()){
            //for all rides that have not ended, check if user is on it.
            for (RideEntity r : rides) {
                int[] end = r.getEndTime();
                if(end[0]==0 && end[1]==0 && end[2]==0
                        && r.getUser().getUserName().equals(userName)){
                    userNotOnRide = false;
                }
            }
        }
        if(userNotOnRide==false){
            return "You cannot book because you are currently on a ride!";
        }
        
        //check if any of the unpaid rides belong to this user
        boolean userPaidAll = true;
        ArrayList<RideEntity> unpaid = searchUnpaidRides();
        if(!unpaid.isEmpty()){
            for(RideEntity r : unpaid){
                if(r.getUser().getUserName().equals(userName))
                    userPaidAll = false;
            }
        }
        if(userPaidAll==false){
            return "You have unpaid rides! Please pay all rides before booking a new one";
        }
        
        Query query = em.createQuery(
                "FROM SystemUser u WHERE u.userName=:UN");
        query.setParameter("UN", userName);
        try{
            UserEntity u = (UserEntity) query.getSingleResult();
            RideEntity ride =
                    new RideEntity(latitude,
                            longitude);
            ride.setDriver(assigned);
            assigned.getRides().add(ride);
            ride.setUser(u);       
            u.getRides().add(ride);
            em.persist(ride);
            status = "BOOKING SUCCESSFUL";
        }catch(Exception e){
            System.out.println("Exception at mb.bookRide()");
            System.out.println(e.getMessage());
        }   
        return status;
    }
    @Override
    public ArrayList<String> viewRides(String userName) {
        ArrayList<String> rideInfo = new ArrayList<>();
        //search User
        Query query = em.createQuery(
                "FROM SystemUser u WHERE u.userName=:UN");
        query.setParameter("UN", userName);
        try{
            UserEntity u = (UserEntity) query.getSingleResult();
            //if User found
            if(u!=null){
                //Loop through all rides of this user, and
                //concatenate all the appropriate information
                int count = 0;
                ArrayList<RideEntity> rides = new ArrayList<>(u.getRides());
                for(RideEntity r : rides){
                    String toAdd = "--RIDE--\n"+r.toString()+"\n"+
                            "--DRIVER--\n"+r.getDriver().toString()+"\n";
                    if(r.getFeedback()!=null)
                        toAdd = toAdd.concat("--FEEDBACK--\n"+r.getFeedback().toString());
                    rideInfo.add(toAdd);
                    count++;
                }
                System.out.println("***************System detected "+count+" rides for this user");
            }
        }catch(Exception e){
            System.out.println("Exception at managerBean.viewRides");
            System.out.println(e.getMessage());
        }
        if(rideInfo.isEmpty()){
            rideInfo.add("You have not booked any rides with us!");
        }
        return rideInfo;
    }
    @Override
    public String cancelRide(String id) {
        int[] cancelTime = new int[3];
        Calendar cal = Calendar.getInstance();
        cancelTime[0] = cal.get(Calendar.HOUR_OF_DAY);
        cancelTime[1] = cal.get(Calendar.MINUTE);
        cancelTime[2] = cal.get(Calendar.SECOND);
        
        Query query = em.createQuery(
                "SELECT r FROM Ride r WHERE r.rideID=:ID");
        query.setParameter("ID", Integer.valueOf(id));
        try{
            RideEntity r = (RideEntity) query.getSingleResult();
            if(r!=null){
                int[] start=r.getStartTime();
                int[] end=r.getEndTime();
                //if ride has not ended
                if(end[0]==0 && end[1]==0 && end[2]==0){
                    //Calculate total time on road of each ride
                    //assuming all rides start and end on the same day
                    double h = r.getEndTime()[0]-r.getStartTime()[0];
                    double m = r.getEndTime()[1]-r.getStartTime()[1];
                    if(m<0){//shave off 1 hour from the hour difference
                        h--;
                        m += 60;
                    }
                    double s = r.getEndTime()[2]-r.getStartTime()[2];
                    if(s<0){//shave off 1 minute from the minute difference
                        m--;
                        s += 60;
                    }
                    //if ride duration beyond 2 minutes of start time
                    //charge 5 dollars
                    if(cancelTime[0]==start[0] && (cancelTime[1]-start[1] > 2))
                        r.setFee(5);
                    else{
                        //no cancellation fee. total fee is still zero
                        //associate a blank payment to the ride
                        PaymentEntity pay = new PaymentEntity("cancelled"
                                ,"cancelled","cancelled");
                        em.persist(pay);
                        //associate payment with ride
                        r.setPayment(pay);
                        Query query1 = em.createQuery(
                                "FROM SystemUser u WHERE u.userName=:UN");
                        query1.setParameter("UN", r.getUser().getUserName());
                        try{
                            UserEntity u = (UserEntity) query1.getSingleResult();
                            //associate payment with user
                            if(u!=null){
                                u.getPayments().add(pay);
                            }
                        }catch(Exception e){
                            System.out.println("Exception while trying to "
                                    + "clear a cancelled $0 ride");
                            System.out.println(e.getMessage());
                        }
                    }
                    r.setEndTime(cancelTime);
                    r.setEndLatitude(r.getStartLatitude());
                    r.setEndLongitude(r.getStartLongitude());                    
                }else
                    return "Ride has already ended. Please enter another ID.";
            }
        }catch(Exception e){
            return "Invalid Ride ID";
        }       
        return "Ride has been cancelled";
    }
    @Override
    public ArrayList<String> viewOutstanding(String userName) {
        ArrayList<String> outstanding = new ArrayList<>();
        //search User
        Query query = em.createQuery(
                "FROM SystemUser u WHERE u.userName=:UN");
        query.setParameter("UN", userName);
        try{
            UserEntity u = (UserEntity) query.getSingleResult();
            //if User found
            if(u!=null){
                //Loop through all rides of this user, and take the ones that
                //have ended and are not yet paid
                ArrayList<RideEntity> rides = new ArrayList<>(u.getRides());
                for(RideEntity r : rides){
                    if(r.getEndLatitude()!=-1 && r.getEndLongitude()!=-1
                            && r.getPayment()==null)
                        outstanding.add(r.toString());
                }
            }
        }catch(Exception e){
            System.out.println("Exception at managerBean.viewOutstanding()");
            System.out.println(e.getMessage());
        }
        if(outstanding.isEmpty()){
            outstanding.add("You have no outstanding rides!");
        }
        return outstanding;
    }
    @Override
    public ArrayList<String> viewPaymentHistory(String userName) {
        ArrayList<String> payments = new ArrayList<>();
        //search User
        Query query = em.createQuery(
                "FROM SystemUser u WHERE u.userName=:UN");
        query.setParameter("UN", userName);
        try{
            UserEntity u = (UserEntity) query.getSingleResult();
            //if User found
            if(u!=null){
                //Loop through all payments of this user. and add the toString
                ArrayList<PaymentEntity> paymentHistory = new ArrayList<>(u.getPayments());
                for(PaymentEntity p : paymentHistory){
                    payments.add(p.toString());
                }
            }
        }catch(Exception e){
            System.out.println("Exception at viewPaymentHistory : userName returns null");
        }   
        if(payments.isEmpty()){
            payments.add("You have not paid for any rides!");
        }
        return payments;
    }
    @Override
    public String payRide(String userName, String cardName, 
            String cardType, String cardNumber, Integer rideID, String payAmount) {
        String status = "PAYMENT SUCCESSFUL";
        Query query = em.createQuery(
                "SELECT r FROM Ride r WHERE r.rideID=:ID");
        query.setParameter("ID", rideID);
        try{
            RideEntity r = (RideEntity) query.getSingleResult();
            if(r.getPayment()==null){
                //System.out.println("FEE IN SYSTEM IS "+r.getFee());
                //System.out.println("PAY AMOUNT ENTERED IS "+payAmount);
                if(payAmount.equals(String.valueOf(r.getFee()))){
                    PaymentEntity pay = new PaymentEntity(cardType,cardNumber,cardName);
                    em.persist(pay);
                    //associate payment with ride
                    r.setPayment(pay);
                    Query query1 = em.createQuery(
                            "FROM SystemUser u WHERE u.userName=:UN");
                    query1.setParameter("UN", userName);
                    UserEntity u = (UserEntity) query1.getSingleResult();
                    //associate payment with user
                    if(u!=null){
                        u.getPayments().add(pay);
                    }
                }else
                    return "Incorrect pay amount. "
                            + "Please make sure you enter the exact outstanding fee"
                            + " for this ride";
            }else
                return "This ride has already been paid for!";
        }catch(Exception e){
            return "Invalid Ride ID!";
        }   
        return status;
    }
    @Override
    public String postFeedback(String userName, Integer rideID, Integer rating, String comment) {
        String status= "FEEDBACK POSTED";
        Query query = em.createQuery(
                "SELECT r FROM Ride r WHERE r.rideID=:ID");
        query.setParameter("ID", rideID);
        try{
            RideEntity r = (RideEntity) query.getSingleResult();
            if(r!=null){
                //if ride has ended
                if(r.getEndLatitude()!=-1 && r.getEndLongitude()!=-1){
                    //has been paid
                    if(r.getPayment()!=null){
                        //and does not have feedback
                        if(r.getFeedback() == null){
                            FeedbackEntity fb = new FeedbackEntity(rating, comment);
                            em.persist(fb);
                            //associate Ride with feedback
                            r.setFeedback(fb);
                            //search User
                            Query query1 = em.createQuery(
                                    "FROM SystemUser u WHERE u.userName=:UN");
                            query1.setParameter("UN", userName);
                            UserEntity u = (UserEntity) query1.getSingleResult();
                            //associate User with Feedback
                            if(u!=null){
                                u.getFeedbacks().add(fb);
                            }
                        }else{
                            return "Feedback has already been posted for this ride!";
                        }
                    }else{
                        return "Ride has not been paid! Please pay before posting feedback.";
                    }
                }else{
                    return "Ride is still ongoing. Please end it by cancelling, "
                            + "or request your driver to end the ride";
                }
            }
        }catch(Exception e){
            return "Invalid Ride ID!";
        }        
        return status;
    }
    @Override
    public ArrayList<String> viewMessages(String userName) {
        ArrayList<String> messages = new ArrayList<>();
        //search User
        Query query = em.createQuery(
                "FROM SystemUser u WHERE u.userName=:UN");
        query.setParameter("UN", userName);
        try{
            UserEntity u = (UserEntity) query.getSingleResult();
            if(u!=null){
                ArrayList<MessageEntity> m = new ArrayList<>(u.getMessages());
                for(MessageEntity msg : m){
                    messages.add(msg.toString());
                }
            }
        }catch(Exception e){
            System.out.println("Exception at managerBean:viewMessages()");
            System.out.println(e.getMessage());
        }        
        if(messages.isEmpty()){
            messages.add("You have not sent any messages prior!");
        }
        return messages;
    }
    @Override
    public String sendMessage(String userName, String msg){
        MessageEntity m = new MessageEntity(msg);
        Query query = em.createQuery(
                "FROM SystemUser u WHERE u.userName=:UN");
        query.setParameter("UN", userName);
        try{
            UserEntity u = (UserEntity) query.getSingleResult();
            //if User found
            if(u!=null){
                m.setUser(u);
                u.getMessages().add(m);
            }
            System.out.println("----------user has been set");
        }catch(Exception e){
            System.out.println("Exception at managerBean:sendMessage()");
            System.out.println(e.getMessage());
            return "Invalid User Name";
        }
        em.persist(m);
        System.out.println("----------Message persisted");
        return "Message Sent.";
    }
}
