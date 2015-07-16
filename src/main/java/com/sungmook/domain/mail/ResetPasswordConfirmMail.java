package com.sungmook.domain.mail;

import com.sungmook.domain.AuthToken;
import lombok.Data;
import org.thymeleaf.TemplateEngine;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
public class ResetPasswordConfirmMail extends ConfirmMail {

    public ResetPasswordConfirmMail(AuthToken authToken, TemplateEngine templateEngine){
        super(authToken, templateEngine);
        this.setTo(authToken.getUser().getUsername());
        this.setSubject("비밀번호 재설정 확인 메일입니다.");
    }
}
