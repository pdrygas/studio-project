package pl.edu.agh.controller;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RestApiControllerTest extends TestsUtils {
    @Test
    public void missingTokenCauses500Error() throws Exception {
        JSONObject result = new JSONObject(request("/api/test", HttpMethod.POST, null));
        Assert.assertEquals(500, result.get("status"));
    }

    @Test
    public void wrongTokenCauses500Error() throws Exception {
        JSONObject result = new JSONObject(request("/api/test", HttpMethod.POST, requestEntity(null, USER_TOKEN + "wrong")));
        Assert.assertEquals(500, result.get("status"));
    }
}
