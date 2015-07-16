package com.sungmook.domain;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
@Entity
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value=Content.Discriminator.COMMENT_STRING)
public class Comment extends Content{

    @ManyToOne
    private Story story;

}
