package com.sungmook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Entity
@Data
@EqualsAndHashCode
public class Role implements GrantedAuthority {

    /**
     * Role 을 추가해야되면 Enum 에 추가하면됨.
     * DB 에 자동으로 추가됨
     **/
    public enum Value {
        ADMIN, USER, SOCIAL, INACTIVE_USER
    }

    private Role(){}

    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Value role;

    /**
     * Enum 타입인 Value 기반의 인스턴스를 리턴하는 메소드
     * @param value
     * @return
     */
    @JsonIgnore
    public static Role buildFromValue(Value value){
        Role role = new Role();
        role.setId(value.ordinal());
        role.setRole(value);
        return role;
    }
    @Override
    public String getAuthority() {
        return this.role.name();
    }
}