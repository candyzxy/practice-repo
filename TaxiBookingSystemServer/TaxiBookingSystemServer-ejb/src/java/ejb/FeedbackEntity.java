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
@Entity(name="Feedback")
public class FeedbackEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    
    private int feedbackID;
    private int[]  feedbackTime;
    private int rating;
    private String comment; 

    public FeedbackEntity() {
    }

    public FeedbackEntity(int stars, String text) {
        Random generator = new Random();
        feedbackID = generator.nextInt(1000);
        Calendar cal = Calendar.getInstance();
        feedbackTime = new int[6];
        feedbackTime[0] = cal.get(Calendar.HOUR_OF_DAY);
        feedbackTime[1] = cal.get(Calendar.MINUTE);
        feedbackTime[2] = cal.get(Calendar.SECOND);
        feedbackTime[3] = cal.get(Calendar.DAY_OF_MONTH);
        feedbackTime[4] = cal.get(Calendar.MONTH)+1;
        feedbackTime[5] = cal.get(Calendar.YEAR);
        rating = stars;
        comment = text;
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
        if (!(object instanceof FeedbackEntity)) {
            return false;
        }
        FeedbackEntity other = (FeedbackEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String toPrint ="Time: "+feedbackTime[0]+": "+
                feedbackTime[1]+": "+
                feedbackTime[2]+" Date: "+
                feedbackTime[3]+" / "+
                feedbackTime[4]+" / "+
                feedbackTime[5]+"\n"+
                "Rating: "+rating+" || Comment: "+comment;
        return toPrint;
    }

    /**
     * @return the feedbackID
     */
    public int getFeedbackID() {
        return feedbackID;
    }

    /**
     * @param feedbackID the feedbackID to set
     */
    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    /**
     * @return the feedbackTime
     */
    public int[]  getFeedbackTime() {
        return feedbackTime;
    }

    /**
     * @param feedbackTime the feedbackTime to set
     */
    public void setFeedbackTime(int[]  feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    /**
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
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
    
}
