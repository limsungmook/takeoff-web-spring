package com.sungmook.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
@Entity
public class Sticker {

    public enum Type {
        DEFAULT
    }

    @Id
    @GeneratedValue
    private Long id;

    private String negativeImageUrl;
    private String normalImageUrl;
    private String positiveImageUrl;

    @Enumerated(EnumType.STRING)
    private Type type;

    @CreatedDate
    private Date createdDate;

    @OneToMany(mappedBy = "sticker")
    private List<ContentActUser> contentActUserList;

    @CreatedBy
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "sticker")
    private List<UserOwnSticker> userOwnStickerList;


    @ManyToMany
    @JoinTable(name="USER_OWN_STICKER",
            joinColumns=
            @JoinColumn(name="STICKER_ID", referencedColumnName="ID"),
            inverseJoinColumns=
            @JoinColumn(name="USER_ID", referencedColumnName="ID")
    )
    private List<User> ownerList;
}
