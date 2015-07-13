package com.sungmook.service;

import com.sungmook.domain.AuthToken;
import com.sungmook.domain.mail.ConfirmMail;
import com.sungmook.domain.mail.ResetPasswordConfirmMail;
import com.sungmook.domain.mail.SignupConfirmMail;
import com.sungmook.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${mail.from}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendConfirmMail(final AuthToken authToken) {
        ConfirmMail confirmMail = null;

        switch(authToken.getType()){
            case SIGNUP:
                confirmMail = new SignupConfirmMail(authToken, templateEngine);
                break;

            case FIND_PASSWORD:
                confirmMail = new ResetPasswordConfirmMail(authToken, templateEngine);
                break;

            default:
                throw new CommonException("지원하지 않는 토큰 타입입니다");
        }

        MimeMessagePreparator preparator = getPreparator(confirmMail);

        try {
            logger.debug("Send mail");
            mailSender.send(preparator);
        }catch (MailException ex){
            logger.error("이메일을 보내는 도중 오류가 발생했습니다.", ex);
            throw new RuntimeException("이메일을 보내는 도중 오류가 발생했습니다.");
        }
    }


    private MimeMessagePreparator getPreparator(final ConfirmMail confirmMail){
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
                helper.setTo(new InternetAddress(confirmMail.getTo()));

                helper.setText(confirmMail.getContent(), true);
                helper.setFrom(new InternetAddress(from));
                helper.setSubject(confirmMail.getSubject());

            }
        };
        return preparator;
    }
}
