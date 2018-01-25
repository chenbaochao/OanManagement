package com.oan.management.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Oan on 25/01/2018.
 */

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String messageText;
    private Date date;

    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;

    public Message() {
    }

    public Message(String subject, String messageText, Date date, User sender, User receiver) {
        this.subject = subject;
        this.messageText = messageText;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
