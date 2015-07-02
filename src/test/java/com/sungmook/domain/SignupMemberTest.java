package com.sungmook.domain;

import com.sungmook.Application;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@DirtiesContext
@WebAppConfiguration
public class SignupMemberTest {

    private static Validator validator;

    public final Log log = LogFactory.getLog(getClass());

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void test_이메일이_비어있습니다() {

        SignupMember signupMember = new SignupMember();
        signupMember.setPassword("test");

        Set<ConstraintViolation<SignupMember>> constraintViolations =
                validator.validate(signupMember);

        log.debug(constraintViolations.toString());

        assertEquals(1, constraintViolations.size());
    }

}