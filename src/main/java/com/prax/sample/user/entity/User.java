/**
 * 
 */
package com.prax.sample.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.prax.framework.base.model.Persistent;

/**
 * @author Huanan
 * 
 */
@Entity(name="com.prax.sample.user.entity.User")
@Table(name = "S_USER")
public class User extends Persistent {

    private static final long serialVersionUID = -7642877577787484738L;

    private String loginName;
    private String password;
    private String name;
    private String email;


    private List<Address> addresses = new ArrayList<Address>();

    @Column(name = "loginName", length = 40, nullable = false, unique = true)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}
