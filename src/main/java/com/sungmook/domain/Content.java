package com.sungmook.domain;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Type;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="discriminator")
@DiscriminatorValue(value= Content.Discriminator.CONTENT_STRING)
@ToString
public abstract class Content {

    public enum Discriminator {
        STORY, COMMENT, CONTENT;

        public static final String STORY_STRING="STORY";
        public static final String CONTENT_STRING="CONTENT";
        public static final String COMMENT_STRING="COMMENT";
    }

    protected Content(){

    }

    /**
     * @DiscriminatorColumn 로써 상속 관계의 구분자를 설정하는데,
     * 묵시적으로 DB 에서만 구분하는 것이 아닌, 가끔 content.getDiscriminator 로써 필요할 때가 있다.
     */
    @Getter
    @Column(name = "discriminator", updatable = false, insertable = false)
    @Enumerated(EnumType.STRING)
    private Discriminator discriminator;

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private int readCount;

    @Type(type="text")
    private String rawText;

    @CreatedDate
    private Date createdDate;

    @CreatedBy
    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "content")
    private List<ContentReadUser> contentReadUser;


}
