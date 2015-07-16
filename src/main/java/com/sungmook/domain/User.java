package com.sungmook.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */

@Data
@Entity
public class User {


    /**
     * SignupMember, SocialSignupMember 등의 자식들만 생성할 수 있음.
     */
    public User(){
    }

    public User(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 로그인에 사용되는 필드
     */
    @Email
    @NotNull
    @Size(max=255)
    private String username;

    private String name;

    private String profilePic;

    private String encryptedPassword;

    @CreatedDate
    private Date createdDate;

    @OneToMany(mappedBy = "user")
    private List<Content> contentList;

    @OneToMany(mappedBy = "user")
    private List<Scope> scopeList;


    public List<Scope> getScopeList(){
        if( this.scopeList != null ){
            this.scopeList.add(0, Scope.buildGlobal());
        }

        return this.scopeList;
    }

    private boolean admin;

    @ManyToMany(mappedBy = "joinUserList")
    private List<Scope> joinScopeList;

    @ManyToMany
    private Set<Role> roles;

    protected void setEncryptedPassword(String encryptedPassword){
        this.encryptedPassword = encryptedPassword;
    }

    public User addRole(Role role){
        if( roles == null ) {
            roles = new HashSet<Role>();
        }
        roles.add(role);
        return this;
    }

    public User removeRole(Role role) {
        if( roles == null ) {
            return this;
        }
        roles.remove(role);

        return this;
    }

    public void setEncryptedPasswordFromPassword(String password) {
        this.setEncryptedPassword(new BCryptPasswordEncoder().encode(password));
    }
}
