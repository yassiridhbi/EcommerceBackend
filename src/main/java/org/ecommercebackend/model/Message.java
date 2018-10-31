package org.ecommercebackend.model;

import org.ecommercebackend.model.security.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "userSender")
    private Customer user_1;

    @ManyToOne
    @JoinColumn(name = "userDestination")
    private Customer user_2;

    @ManyToOne
    @JoinColumn(name = "thread")
    private Thread thread;

    @Lob
    @Column(name="CONTENT", length=512)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name ="date_sent")
    private Date datesent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getUser_1() {
        return user_1;
    }

    public void setUser_1(Customer user_1) {
        this.user_1 = user_1;
    }

    public Customer getUser_2() {
        return user_2;
    }

    public void setUser_2(Customer user_2) {
        this.user_2 = user_2;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Date getDate_sent() {
        return datesent;
    }

    public void setDate_sent(Date date_sent) {
        this.datesent = date_sent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
