package pl.edu.agh.controller;

import org.json.JSONArray;
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
public class ResourcesControllerTests extends TestsUtils {
    @Test
    public void defaultUserHas2Resources() throws Exception {
        JSONArray result = new JSONArray(request("/api/resources", HttpMethod.GET, requestEntity(null, USER_TOKEN)));
        Assert.assertEquals(2, result.length());
    }

    @Test
    public void otherUserHas1Resource() throws Exception {
        JSONArray result = new JSONArray(request("/api/resources", HttpMethod.GET, requestEntity(null, USER2_TOKEN)));
        Assert.assertEquals(1, result.length());
    }

    @Test
    public void addNewResource() throws Exception {
        JSONObject result = new JSONObject(request("/api/resources", HttpMethod.POST,
                requestEntity(resourceParams("some title", "some content"), USER3_TOKEN)));
        Assert.assertEquals("ok", result.get("result"));
    }

    @Test
    public void addNewResourceWithEmptyContent() throws Exception {
        JSONObject result = new JSONObject(request("/api/resources", HttpMethod.POST,
                requestEntity(resourceParams("some title", ""), USER3_TOKEN)));
        Assert.assertEquals("error", result.get("result"));
    }
}
