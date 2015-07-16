package com.sungmook.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
@Entity
public class Scope {


    public enum Type {
        GLOBAL, FRIEND, ME, CUSTOM
    }

    public static Scope buildGlobal(){
        Scope scope = new Scope(Type.GLOBAL);
        scope.setId((long) (Type.GLOBAL.ordinal() + 1));
        scope.setName("전체공개");
        return scope;
    }

    private Scope(){}

    public Scope(Type type){
        this.type = type;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "scope")
    private List<ScopeUser> scopeUserList;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(name="SCOPE_USER",
            joinColumns=
            @JoinColumn(name="SCOPE_ID", referencedColumnName="ID"),
            inverseJoinColumns=
            @JoinColumn(name="USER_ID", referencedColumnName="ID")
    )
    private List<User> joinUserList;

    @CreatedDate
    private Date createdDate;



}
