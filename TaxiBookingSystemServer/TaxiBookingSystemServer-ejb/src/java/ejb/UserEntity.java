/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

/**
 *
 * @author Derian
 */
@Entity(name="SystemUser")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private int contactNumber;
    private String email;
    private String address;
    
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<PaymentEntity> payments = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<FeedbackEntity> feedbacks = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<MessageEntity> messages = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<RideEntity> rides = new ArrayList<>();
   
    public UserEntity(){
    }
    public UserEntity(String userName, String password, int contactNumber, String email, String address) {
        this.userName = userName;
        this.password = password;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
    }
    
    /**
     * Get the value of userName
     *
     * @return the value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the value of userName
     *
     * @param userName new value of userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String toPrint = "User Name: "+getUserName()+"\n"
                +"Contact Number: "+getContactNumber()+"\n"
                +"Email: "+getEmail()+"\n"
                +"Address: "+getAddress()+"\n";
        
        return toPrint;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the payments
     */
    public Collection<PaymentEntity> getPayments() {
        return payments;
    }

    /**
     * @param payments the payments to set
     */
    public void setPayments(Collection<PaymentEntity> payments) {
        this.payments = payments;
    }

    /**
     * @return the feedbacks
     */
    public Collection<FeedbackEntity> getFeedbacks() {
        return feedbacks;
    }

    /**
     * @param feedbacks the feedbacks to set
     */
    public void setFeedbacks(Collection<FeedbackEntity> feedbacks) {
        this.feedbacks = feedbacks;
    }

    /**
     * @return the messages
     */
    public Collection<MessageEntity> getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(Collection<MessageEntity> messages) {
        this.messages = messages;
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
