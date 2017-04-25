package pl.edu.agh.controller;

import org.json.JSONArray;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import pl.edu.agh.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RestApiControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;
    private final String USER_TOKEN = "secret_token";
    private final String USER2_TOKEN = "other_token";
    private final String USER3_TOKEN = "other2_token";

    @Test
    public void missingTokenCauses500Error() throws Exception {
        JSONObject result = new JSONObject(request("/test", HttpMethod.POST, null));
        Assert.assertEquals(500, result.get("status"));
    }

    @Test
    public void wrongTokenCauses500Error() throws Exception {
        JSONObject result = new JSONObject(request("/test", HttpMethod.POST, requestEntity(null, USER_TOKEN + "wrong")));
        Assert.assertEquals(500, result.get("status"));
    }

    @Test
    public void goodToken() {
        String result = request("/test", HttpMethod.POST, requestEntity(null, USER_TOKEN));
        Assert.assertEquals("workin biatch!", result);
    }

    @Test
    public void defaultUserHas2Resources() throws Exception {
        JSONArray result = new JSONArray(request("/resources", HttpMethod.GET, requestEntity(null, USER_TOKEN)));
        Assert.assertEquals(2, result.length());
    }

    @Test
    public void otherUserHas1Resource() throws Exception {
        JSONArray result = new JSONArray(request("/resources", HttpMethod.GET, requestEntity(null, USER2_TOKEN)));
        Assert.assertEquals(1, result.length());
    }

    @Test
    public void addNewResource() throws Exception {
        JSONObject result = new JSONObject(request("/resources", HttpMethod.POST,
                                           requestEntity(resourceParams("some title", "some content"), USER3_TOKEN)));
        Assert.assertEquals("ok", result.get("result"));
    }

    private String request(String url, HttpMethod method, HttpEntity entity) {
        return restTemplate.exchange(url, method, entity, String.class).getBody();
    }

    private MultiValueMap<String, String> resourceParams(String title, String content) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", title);
        params.add("content", content);
        return params;
    }

    private HttpEntity requestEntity(MultiValueMap<String, String> params, String token) {
        final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER_NAME, token);
        return new HttpEntity<>(params, headers);
    }
}
