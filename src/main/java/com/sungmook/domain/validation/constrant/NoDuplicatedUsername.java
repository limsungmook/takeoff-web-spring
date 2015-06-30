package com.sungmook.domain.validation.constrant;


import com.sungmook.domain.validation.validator.NoDuplicatedUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 입력받은 이메일이 이미 가입되었는지 체크
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Documented
@Constraint(validatedBy = NoDuplicatedUsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoDuplicatedUsername {


    String message() default "{NoDuplicatedUsername.username}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        NoDuplicatedUsername[] value();
    }

}
