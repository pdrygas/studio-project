package pl.edu.agh.controller;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RestApiControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;
    private final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    private final String GOOD_TOKEN = "secret_token";

    @Test
    public void missingTokenCauses500Error() throws Exception {
        JSONObject result = new JSONObject(restTemplate.postForEntity("/test", null, String.class).getBody());
        Assert.assertEquals(500, result.get("status"));
    }

    @Test
    public void wrongTokenCauses500Error() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER_NAME, GOOD_TOKEN + "some_text");
        JSONObject result = new JSONObject(restTemplate.exchange("/test", HttpMethod.POST, new HttpEntity<>(null, headers), String.class).getBody());
        Assert.assertEquals(500, result.get("status"));
    }

    @Test
    public void goodToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER_NAME, GOOD_TOKEN);
        String result = restTemplate.exchange("/test", HttpMethod.POST, new HttpEntity<>(null, headers), String.class).getBody();
        Assert.assertEquals("workin biatch!", result);
    }
}
