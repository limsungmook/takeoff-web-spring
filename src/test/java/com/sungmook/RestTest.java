package com.sungmook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
@DirtiesContext
public class RestTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void testRestTemplate() throws Exception {
        ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
                "http://localhost:" + port, String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
//        assertTrue("Wrong body:\n" + entity.getBody(),
//                entity.getBody().contains("Hello, Andy"));
    }

    @Test
    public void testFreeMarkerErrorTemplate() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

        ResponseEntity<String> responseEntity = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/does-not-exist", HttpMethod.GET,
                requestEntity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        assertTrue("Wrong body:\n" + responseEntity.getBody(), responseEntity.getBody()
//                .contains("Something went wrong: 404 Not Found"));
    }

}
