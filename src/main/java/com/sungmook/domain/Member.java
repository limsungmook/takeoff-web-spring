package com.sungmook.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Email
    @NotNull
    @Size(max=255)
    private String username;

    private String encryptedPassword;

    private boolean admin;


    @ManyToMany
    private List<Role> roles;



    public Member addRole(Role role){
        if( roles == null ) {
            roles = new ArrayList<Role>();
        }
        roles.add(role);
        return this;
    }

}
