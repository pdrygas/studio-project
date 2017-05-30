package pl.edu.agh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TestsUtils {
    @Autowired
    private TestRestTemplate restTemplate;
    protected final String USER_TOKEN = "secret_token";
    protected final String USER2_TOKEN = "other_token";
    protected final String USER3_TOKEN = "other2_token";

    protected String request(String url, HttpMethod method, HttpEntity entity) {
        return restTemplate.exchange(url, method, entity, String.class).getBody();
    }

    protected MultiValueMap<String, String> resourceParams(String title, String content) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", title);
        params.add("content", content);
        return params;
    }

    protected HttpEntity requestEntity(MultiValueMap<String, String> params, String token) {
        final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER_NAME, token);
        return new HttpEntity<>(params, headers);
    }
}
