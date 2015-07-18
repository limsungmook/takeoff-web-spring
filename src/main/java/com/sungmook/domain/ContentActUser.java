package com.sungmook.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
@Entity
public class ContentActUser {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Content content;

    @ManyToOne
    private Act act;

    @ManyToOne
    private Sticker sticker;

    @CreatedBy
    @ManyToOne
    private User user;

    @CreatedDate
    private Date createdDate;

    /**
     * 일반적으로 Act 한 시간을 나타낸다.
     * 읽은 시각은 createdDate 로 본다.
     */
    @LastModifiedDate
    private Date modifiedDate;
}
