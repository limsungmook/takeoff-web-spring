package com.sungmook.domain.mail;

import com.sungmook.domain.AuthToken;
import lombok.Data;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */

@Data
public abstract class ConfirmMail {

    public ConfirmMail(AuthToken authToken, TemplateEngine templateEngine){
        this.authToken = authToken;

        if( templateEngine == null ) {
            return;
        }
        final Context context = new Context();
        context.setVariable("authToken", authToken);

        this.content = templateEngine.process("/mail/" + authToken.getType().name(), context);
    }

    private String to;

    private String subject;

    private String content;

    private AuthToken authToken;

    private void setContent(String content){
        this.content = content;
    }


}
