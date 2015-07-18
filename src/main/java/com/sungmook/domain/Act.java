package com.sungmook.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
@Entity
public class Act {


    public enum Type {
        NEGATIVE, NORMAL, POSITIVE
    }

    @Id
    @GeneratedValue
    private Long id;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "act")
    private List<ContentActUser> contentActUser;

    @OneToOne
    private Sticker sticker;

}
