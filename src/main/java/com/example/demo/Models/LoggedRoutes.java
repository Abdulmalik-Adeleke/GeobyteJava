package com.example.demo.Models;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table
public class LoggedRoutes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String email;
    
    @Lob
    @Type(type="org.hibernate.type.MaterializedClobType")
    private String chosenRouteObject;

    @Temporal(TemporalType.DATE)
    private Date loggedDate;


    public LoggedRoutes() {
    }

    public LoggedRoutes(String email, String chosenRouteObject, Date loggedDate) {
        this.email = email;
        this.chosenRouteObject = chosenRouteObject;
        this.loggedDate = loggedDate;
    }
    
    public long getId() {
        return this.Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChosenRouteObject() {
        return this.chosenRouteObject;
    }

    public void setChosenRouteObject(String chosenRouteObject) {
        this.chosenRouteObject = chosenRouteObject;
    }


}