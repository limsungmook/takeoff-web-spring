package com.sungmook.domain.mail;

import com.sungmook.domain.AuthToken;
import lombok.Data;
import org.thymeleaf.TemplateEngine;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
public class SignupConfirmMail extends ConfirmMail {

    public SignupConfirmMail(AuthToken authToken, TemplateEngine templateEngine){
        super(authToken, templateEngine);
        this.setTo(authToken.getMember().getUsername());
        this.setSubject("가입 확인 메일입니다.");
    }
}
