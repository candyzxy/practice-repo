/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Derian
 */
@Entity(name="Ride")

public class RideEntity implements Serializable {

    @ManyToOne
    private DriverEntity driver = new DriverEntity();

    @ManyToOne
    private UserEntity user = new UserEntity();
    
    @OneToOne(cascade={CascadeType.ALL})
    private PaymentEntity payment;
    
    @OneToOne(cascade={CascadeType.ALL})
    private FeedbackEntity feedback;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rideID;
    private int[] startTime = new int[3];
    private int startLatitude=-1;
    private int startLongitude=-1;
    private int[]  endTime = new int[3];
    private int endLatitude=-1;
    private int endLongitude=-1;
    private double fee;
    
    public RideEntity() {
    }
    public RideEntity(int startLat, int startLong){
        Random generator = new Random();
        rideID = generator.nextInt(1000);
        Calendar cal = Calendar.getInstance();
        startTime[0] = cal.get(Calendar.HOUR_OF_DAY);
        startTime[1] = cal.get(Calendar.MINUTE);
        startTime[2] = cal.get(Calendar.SECOND);
        startLatitude = startLat;
        startLongitude = startLong;
        fee = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        fee = Double.valueOf(df.format(fee));
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RideEntity)) {
            return false;
        }
        RideEntity other = (RideEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String startT = getStartTime()[0]+": "+
                getStartTime()[1]+": "+
                getStartTime()[2];
        String endT = getEndTime()[0]+": "+
                getEndTime()[1]+": "+
                getEndTime()[2];
        String toPrint = "Ride ID: "+getRideID()+" ||\n"
                +"Start Time: "+startT+" || "
                +"Start Location: ("+getStartLatitude()+", "+
                getStartLongitude()+") ||\n"
                +"End Time: "+endT+" || "
                +"End Location: ("+getEndLatitude()+", "+
                getEndLongitude()+") ||\n"
                +"Fee: $ "+getFee()+"\n";
        return toPrint;
    }

    /**
     * @return the rideID
     */
    public int getRideID() {
        return rideID;
    }

    /**
     * @param rideID the rideID to set
     */
    public void setRideID(int rideID) {
        this.rideID = rideID;
    }

    /**
     * @return the startTime
     */
    public int[]  getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(int[] startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the startLatitude
     */
    public int getStartLatitude() {
        return startLatitude;
    }

    /**
     * @param startLatitude the startLatitude to set
     */
    public void setStartLatitude(int startLatitude) {
        this.startLatitude = startLatitude;
    }

    /**
     * @return the startLongitude
     */
    public int getStartLongitude() {
        return startLongitude;
    }

    /**
     * @param startLongitude the startLongitude to set
     */
    public void setStartLongitude(int startLongitude) {
        this.startLongitude = startLongitude;
    }

    /**
     * @return the endTime
     */
    public int[]  getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(int[]  endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the endLatitude
     */
    public int getEndLatitude() {
        return endLatitude;
    }

    /**
     * @param endLatitude the endLatitude to set
     */
    public void setEndLatitude(int endLatitude) {
        this.endLatitude = endLatitude;
    }

    /**
     * @return the endLongitude
     */
    public int getEndLongitude() {
        return endLongitude;
    }

    /**
     * @param endLongitude the endLongitude to set
     */
    public void setEndLongitude(int endLongitude) {
        this.endLongitude = endLongitude;
    }

    /**
     * @return the fee
     */
    public double getFee() {
        return fee;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(double fee) {
        this.fee = fee;
    }

    /**
     * @return the user
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * @return the payment
     */
    public PaymentEntity getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    /**
     * @return the feedback
     */
    public FeedbackEntity getFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(FeedbackEntity feedback) {
        this.feedback = feedback;
    }

    /**
     * @return the driver
     */
    public DriverEntity getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(DriverEntity driver) {
        this.driver = driver;
    }
    
}
