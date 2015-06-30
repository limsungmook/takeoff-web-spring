package com.sungmook.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Component
public class MessageHelper {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String id){
        return getMessage(id, null);
    }

    public String getMessage(String id, String noSuchMessage){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id, null, noSuchMessage, locale);

    }
}
