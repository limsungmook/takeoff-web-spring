package com.sungmook.domain;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */

@Entity
@Data
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
}
