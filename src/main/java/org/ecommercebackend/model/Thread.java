package org.ecommercebackend.model;

import org.ecommercebackend.model.security.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "thread")
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "thread_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "userSender")
    private Customer user1;

    @ManyToOne
    @JoinColumn(name = "userDestination")
    private Customer user2;

    @NotNull
    @Column(name = "title")
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name ="last_sent_message")
    private Date lastSentMessage;

    @Column(name = "already_read")
    private Boolean alreadyRead;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getUser_1() {
        return user1;
    }

    public void setUser_1(Customer user_1) {
        this.user1 = user_1;
    }

    public Customer getUser_2() {
        return user2;
    }

    public void setUser_2(Customer user_2) {
        this.user2 = user_2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastSentMessage() {
        return lastSentMessage;
    }

    public void setLastSentMessage(Date lastSentMessage) {
        this.lastSentMessage = lastSentMessage;
    }

    public Boolean getAlreadyRead() {
        return alreadyRead;
    }

    public void setAlreadyRead(Boolean alreadyRead) {
        this.alreadyRead = alreadyRead;
    }
}
