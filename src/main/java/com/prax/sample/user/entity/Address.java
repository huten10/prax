package com.prax.sample.user.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.prax.framework.base.model.Persistent;

/**
 * @author Alvin.Hu
 * 
 */

@Entity
@Table(name = "S_ADDRESS")
public class Address extends Persistent {

    private static final long serialVersionUID = 1L;

    private User user;

    private String city;
    private String country;
    private String line;
    private String postalCode;
    private String stateOrProvince;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userUuid")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

}
