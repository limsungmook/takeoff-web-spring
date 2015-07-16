package com.sungmook.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
@Entity
@Table(name = "SCOPE_USER")
public class ScopeUser {

    private ScopeUser(){}

    public ScopeUser(Scope scope, User user){
        this.scope = scope;
        this.user = user;
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Scope scope;

    @ManyToOne
    private User user;

    @CreatedDate
    private Date createdDate;

}
