package com.sungmook.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Entity
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value=Content.Discriminator.STORY_STRING)
public class Story extends Content{

}
