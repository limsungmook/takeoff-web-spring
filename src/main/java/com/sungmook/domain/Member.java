package com.sungmook.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    private String password;

    private boolean admin;

}
