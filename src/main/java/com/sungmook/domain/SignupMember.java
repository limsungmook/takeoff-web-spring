package com.sungmook.domain;

import com.sungmook.domain.validation.constrant.NoDuplicatedUsername;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public class SignupMember extends Member {

    @NoDuplicatedUsername
    @Override
    public String getUsername(){
        return super.getUsername();
    }
}
