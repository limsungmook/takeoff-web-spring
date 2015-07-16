package com.sungmook.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
@Entity
public class UserRelation {


    public enum Direction {
        ONEWAY, DUPLEX
    }

    @Id
    @GeneratedValue
    private Long id;

    @CreatedBy
    @ManyToOne
    private User createUser;

    @ManyToOne
    private User receiveUser;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    @CreatedDate
    private Date createdDate;

}
