package com.sungmook.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
@Entity
public class ContentReadUser {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Content content;

    @CreatedBy
    @ManyToOne
    private Member member;

    @CreatedDate
    private Date createdDate;

}
