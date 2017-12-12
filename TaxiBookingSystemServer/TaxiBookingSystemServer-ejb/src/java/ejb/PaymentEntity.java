/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Derian
 */
@Entity(name="Payment")

public class PaymentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int paymentID;
    private int[]  paymentTime = new int[6];
    private String cardType;
    private String cardNumber;
    private String cardHolderName;

    public PaymentEntity() {
    }

    public PaymentEntity(String type, String number, String name) {
        cardType=type;
        cardNumber=number;
        cardHolderName=name;
        Random generator = new Random();
        paymentID = generator.nextInt(1000);
        Calendar cal = Calendar.getInstance();
        paymentTime = new int[6];
        paymentTime[0] = cal.get(Calendar.HOUR_OF_DAY);
        paymentTime[1] = cal.get(Calendar.MINUTE);
        paymentTime[2] = cal.get(Calendar.SECOND);
        paymentTime[3] = cal.get(Calendar.DAY_OF_MONTH);
        paymentTime[4] = cal.get(Calendar.MONTH)+1;
        paymentTime[5] = cal.get(Calendar.YEAR);
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
        if (!(object instanceof PaymentEntity)) {
            return false;
        }
        PaymentEntity other = (PaymentEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        String payT = paymentTime[0]+": "+
                paymentTime[1]+": "+
                paymentTime[2]+" Date: "+
                paymentTime[3]+" / "+
                paymentTime[4]+" / "+
                paymentTime[5]+" ";
        String toPrint = "Payment ID: "+paymentID+" || "
                +"Time: "+payT+"\n"
                +"Card Type: "+cardType+" || "
                +"Card Number: "+cardNumber+" || "
                +"Cardholder Name: "+cardHolderName+" || ";
        return toPrint;
    }

    /**
     * @return the paymentID
     */
    public int getPaymentID() {
        return paymentID;
    }

    /**
     * @param paymentID the paymentID to set
     */
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    /**
     * @return the paymentTime
     */
    public int[]  getPaymentTime() {
        return paymentTime;
    }

    /**
     * @param paymentTime the paymentTime to set
     */
    public void setPaymentTime(int[]  paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * @return the cardType
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * @param cardType the cardType to set
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * @return the cardNumber
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @param cardNumber the cardNumber to set
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return the cardHolderName
     */
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * @param cardHolderName the cardHolderName to set
     */
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
    
}
