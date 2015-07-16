package com.sungmook.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
@Entity
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value=Content.Discriminator.STORY_STRING)
public class Story extends Content{

    public Story(){
        super();
        this.scope = new Scope(Scope.Type.GLOBAL);
        scope.setId((long) Scope.Type.GLOBAL.ordinal() + 1);
    }

    @OneToOne(fetch = FetchType.EAGER)
    private Scope scope;

    @OneToMany(mappedBy="story")
    private List<Comment> commentList;

}
