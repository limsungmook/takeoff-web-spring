package com.sungmook.controller.web;

import com.sungmook.Application;
import com.sungmook.domain.AuthToken;
import com.sungmook.domain.User;
import com.sungmook.domain.mail.SignupConfirmMail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.subethamail.wiser.Wiser;

import static com.sungmook.WiserAssertions.assertReceivedMessage;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
@DirtiesContext
@TestPropertySource(properties = {"spring.mail.port=25252",
                                    "spring.mail.host=localhost",
                                    "spring.mail.properties.mail.smtp.socketFactory.port=25252"})
public class AuthControllerTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${local.server.port}")
    private int port;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${mail.from}")
    private String from;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private Wiser wiser;

    @Before
    public void setUp() throws Exception {
        logger.debug("mail port : {}", mailPort);
        wiser = new Wiser();
        wiser.setPort(mailPort);
        wiser.start();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @After
    public void tearDown() throws Exception {
        wiser.stop();
    }


    /**
     * 제대로 DB 에 들어갔는지,
     * 제대로 인증 메일이 전송되었는지 확인
     * @throws Exception
     */
    @Test
    public void testSignup() throws Exception {

        mockMvc.perform(
                    post("/auth/signup")
                    .param("username", "test@test.com")
                    .param("password", "1234")
                    .param("reTypePassword", "1234")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors());

        User testUser = new User();
        testUser.setUsername("test@test.com");
        SignupConfirmMail signupConfirmMail = new SignupConfirmMail(new AuthToken(testUser), null);

        assertReceivedMessage(wiser)
                .from(from)
                .to(testUser.getUsername())
                .withSubject(signupConfirmMail.getSubject());


    }
}