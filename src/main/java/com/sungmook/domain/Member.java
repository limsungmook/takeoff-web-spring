package com.sungmook.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */

@Entity
@Data
public class Member {


    /**
     * SignupMember, SocialSignupMember 등의 자식들만 생성할 수 있음.
     */
    public Member(){}

    public Member(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Email
    @NotNull
    @Size(max=255)
    private String username;

    private String name;

    private String profilePic;

    private String encryptedPassword;

    private boolean admin;


    @ManyToMany
    private Set<Role> roles;



    public Member addRole(Role role){
        if( roles == null ) {
            roles = new HashSet<Role>();
        }
        roles.add(role);
        return this;
    }

    public Member removeRole(Role role) {
        if( roles == null ) {
            return this;
        }
        roles.remove(role);

        return this;

    }
}
