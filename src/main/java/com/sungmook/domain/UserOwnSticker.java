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
@Table(name = "USER_OWN_STICKER")
public class UserOwnSticker {

    private UserOwnSticker(){}

    public UserOwnSticker(User user, Sticker sticker){
        this.sticker = sticker;
        this.user = user;
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Sticker sticker;

    @ManyToOne
    private User user;

    @CreatedDate
    private Date createdDate;

}
