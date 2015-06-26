package com.sungmook.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String encryptedPassword;

    @Transient
    private String password;

    private boolean admin;


    @ManyToMany
    private List<Role> roles;


    public void setPassword(String password){
        this.password = password;
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        this.setEncryptedPassword(encryptedPassword);
    }

    public Member addRole(Role role){
        if( roles == null ) {
            roles = new ArrayList<Role>();
        }
        roles.add(role);
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
