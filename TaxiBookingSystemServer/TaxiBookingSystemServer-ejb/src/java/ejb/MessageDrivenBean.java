/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Derian
 */
@MessageDriven(mappedName = "jms/Topic", activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "MessageDrivenBean"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "NonDurable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "MessageDrivenBean"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class MessageDrivenBean implements MessageListener {

    @PersistenceContext(unitName = "TaxiBookingSystemServer-ejbPU")
    private EntityManager em;
    
    public MessageDrivenBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        MapMessage m = null;     
        try{
            if(message instanceof MapMessage){
                m = (MapMessage) message;
                processMessage(m);
            }else{
                System.out.println("Error at MessageDrivenBean.onMessage(): "
                        + "Message is of wrong type: "+ message.getJMSType());
            }
        }catch(Exception e){
            System.out.println("Error at MessageDrivenBean.onMessage(): "+
                    e.toString());
        }
    }
    private void processMessage(MapMessage m) {
        String purpose = null;
        String license = null;
        int latitude = -1;
        int longitude = -1;
                
        try {
            latitude = m.getInt("latitude");
            longitude = m.getInt("longitude");
            license = m.getString("license");
            purpose = m.getString("purpose");
        } catch (Exception ex) {
            Logger.getLogger(MessageDrivenBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if("updateLocation".equals(purpose)){          
            updateLocation(latitude,longitude,license);          
        }else if("endRide".equals(purpose)){            
            endRide(latitude,longitude,license);            
        }
    }
    private void updateLocation(int latitude, int longitude, String license){
        Query query = em.createQuery(
                "SELECT d FROM Driver d WHERE d.drivingLicenseNumber=:licNum");
        query.setParameter("licNum", license);
        List<DriverEntity> drivers = query.getResultList();
        if(!drivers.isEmpty()){
            for(DriverEntity d : drivers){
                if(d!=null){
                    d.setCurrentLatitude(latitude);
                    d.setCurrentLongitude(longitude);
                }
            }
        }       
    }
    private void endRide(int latitude, int longitude, String license){
        //check all rides which have not ended. If any of them has driver with 
        //this license number, then end that ride by
        //giving an end time, end location, and calculate the fee
        //Also update the driver location
        Calendar cal = Calendar.getInstance();
        int[] end = new int[3];
        end[0] = cal.get(Calendar.HOUR_OF_DAY);
        end[1] = cal.get(Calendar.MINUTE);
        end[2] = cal.get(Calendar.SECOND);
        Query query = em.createQuery(
                "SELECT d FROM Driver d WHERE d.drivingLicenseNumber=:licNum");
        query.setParameter("licNum", license);
        List<DriverEntity> drivers = query.getResultList();
        if(!drivers.isEmpty()){
            for(DriverEntity d : drivers){
                if(d!=null){                   
                    ArrayList<RideEntity> rides = new ArrayList<>(d.getRides());
                    if(!rides.isEmpty()){
                        for(RideEntity r : rides){
                            //if ride has not ended, then end location will be (-1,-1)
                            if(r.getEndLatitude()==-1 && r.getEndLongitude()==-1){
                                r.setEndLatitude(latitude);
                                r.setEndLongitude(longitude);                         
                                r.setEndTime(end);
                                double distance = Math.sqrt(
                                        Math.pow((double)latitude-(double)r.getStartLatitude(), 2)
                                                +Math.pow((double)longitude-(double)r.getStartLongitude(), 2));
                                //assuming all rides end within one day
                                double h = end[0]-r.getStartTime()[0];
                                if(h<0){
                                    h += 24;
                                }
                                double m = end[1]-r.getStartTime()[1];
                                if(m<0){//shave off 1 hour from the hour difference
                                    h--;
                                    m += 60;
                                }
                                double s = end[2]-r.getStartTime()[2];
                                if(s<0){//shave off 1 minute from the minute difference
                                    m--;
                                    s += 60;
                                }
                                DecimalFormat df = new DecimalFormat("#.##");
                                double fee = distance + ((h*60.0)+m+(s/60.0))*0.1;
                                fee = Double.valueOf(df.format(fee));
                                r.setFee(fee);
                                d.setCurrentLatitude(latitude);
                                d.setCurrentLongitude(longitude);
                            }
                        }
                    }
                }
            }  
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
}

