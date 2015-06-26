package com.sungmook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Entity
public class Role {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";

    private Role(){}

    @Id
    private Integer id;

    private String role;

    @JsonIgnore
    public static Role asAdmin(){
        Role role = new Role();
        role.setId(1);
        role.setRole(ADMIN);
        return role;
    }

    @JsonIgnore
    public static Role asUser(){
        Role role = new Role();
        role.setId(2);
        role.setRole(USER);
        return role;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}