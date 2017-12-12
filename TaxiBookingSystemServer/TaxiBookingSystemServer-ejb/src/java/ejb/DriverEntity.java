/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 *
 * @author Derian
 */
@Entity(name="Driver")
public class DriverEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String drivingLicenseNumber;
    private String name;
    private int contactNumber;
    private String address;
    private int currentLatitude=-1;
    private int currentLongitude=-1;
    
    @OneToOne(cascade = {CascadeType.ALL})
    private CarEntity car;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "driver")
    private Collection<RideEntity> rides = new ArrayList<>();    
    
    public DriverEntity(){
    }

    public DriverEntity(String drivingLicenseNumber, String name, 
            int contactNumber, String address, 
            int currLatitude, int currLongitude, CarEntity car) {
        this.drivingLicenseNumber=drivingLicenseNumber;
        this.name=name;
        this.contactNumber=contactNumber;
        this.address=address;
        this.currentLatitude=currLatitude;
        this.currentLongitude=currLongitude;
        this.car = car;
    }
    
    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
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
        if (!(object instanceof DriverEntity)) {
            return false;
        }
        DriverEntity other = (DriverEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String toPrint = "Name: "+getName()+" || "
                +"License Number: "+getDrivingLicenseNumber()+" || "
                +"Contact Number: "+getContactNumber()+" || "
                +"Location: ("+getCurrentLatitude()+", "+
                getCurrentLongitude()+")\n";
        return toPrint;
    }

    /**
     * @return the drivingLicenseNumber
     */
    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    /**
     * @param drivingLicenseNumber the drivingLicenseNumber to set
     */
    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the contactNumber
     */
    public int getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the currentLatitude
     */
    public int getCurrentLatitude() {
        return currentLatitude;
    }

    /**
     * @param currentLatitude the currentLatitude to set
     */
    public void setCurrentLatitude(int currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    /**
     * @return the currentLongitude
     */
    public int getCurrentLongitude() {
        return currentLongitude;
    }

    /**
     * @param currentLongitude the currentLongitude to set
     */
    public void setCurrentLongitude(int currentLongitude) {
        this.currentLongitude = currentLongitude;
    }
/**
     * @return the rides
     */
    public Collection<RideEntity> getRides() {
        return rides;
    }

    /**
     * @param rides the rides to set
     */
    public void setRides(Collection<RideEntity> rides) {
        this.rides = rides;
    }
   
}
