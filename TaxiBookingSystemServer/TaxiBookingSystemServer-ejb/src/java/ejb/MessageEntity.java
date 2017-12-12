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
import javax.persistence.ManyToOne;

/**
 *
 * @author Derian
 */
@Entity(name="Message")

public class MessageEntity implements Serializable {

    @ManyToOne
    private UserEntity user = new UserEntity();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int messageID;
    private int[]  messageTime = new int[6];
    private String content; 
    private String status;
    private String comment;

    public MessageEntity() {
    }

    public MessageEntity(String msg) {
        content = msg;
        status = "unread";
        Random generator = new Random();
        messageID = generator.nextInt(1000);
        Calendar cal = Calendar.getInstance();
        messageTime[0] = cal.get(Calendar.HOUR_OF_DAY);
        messageTime[1] = cal.get(Calendar.MINUTE);
        messageTime[2] = cal.get(Calendar.SECOND);
        messageTime[3] = cal.get(Calendar.DAY_OF_MONTH);
        messageTime[4] = cal.get(Calendar.MONTH)+1;
        messageTime[5] = cal.get(Calendar.YEAR);
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
        if (!(object instanceof MessageEntity)) {
            return false;
        }
        MessageEntity other = (MessageEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String time = messageTime[0]+": "+
                messageTime[1]+": "+
                messageTime[2]+" Date: "+
                messageTime[3]+" / "+
                messageTime[4]+" / "+
                messageTime[5]+" ";
        String toPrint = "Message ID: "+getMessageID()+" || "
                +"Time: "+time+" || "
                +"Status: "+getStatus()+" ||\n"
                +"Message: "+getContent()+" ||\n"
                +"Admin Comment: "+getComment()+" ||\n";
        return toPrint;
    }

    /**
     * @return the messageID
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * @param messageID the messageID to set
     */
    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    /**
     * @return the messageTime
     */
    public int[]  getMessageTime() {
        return messageTime;
    }

    /**
     * @param messageTime the messageTime to set
     */
    public void setMessageTime(int[]  messageTime) {
        this.messageTime = messageTime;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
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
    
}
