package pl.edu.agh.controller.rest;

import com.google.gson.Gson;
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
public class CategoriesControllerTest extends TestUtils {
    @Test
    public void addCategoryWithEmptyTitleFails() throws Exception {
        JSONObject result = new JSONObject(request("/api/categories", HttpMethod.POST,
                requestEntity(categoryParams(""), USER_TOKEN)));
        Assert.assertEquals("error", result.get("result"));
    }

    @Test
    public void addCategory() throws Exception {
        JSONObject result = new JSONObject(request("/api/categories", HttpMethod.POST,
                requestEntity(categoryParams("some_cat"), USER3_TOKEN)));
        Assert.assertEquals("ok", result.get("result"));
    }

    @Test
    public void defaultUserHas2Categories() throws Exception {
        JSONArray result = new JSONArray(request("/api/categories", HttpMethod.GET, requestEntity(null, USER_TOKEN)));
        Assert.assertEquals(2, result.length());
    }

    @Test
    public void notOwnedCategoriesAreNotReturned() throws Exception {
        Object result = request("/api/categories/" + getUser2CategoryId(), HttpMethod.GET, requestEntity(null, USER_TOKEN));
        Assert.assertEquals(String.valueOf("null"), result);
    }

    private int getUser2CategoryId() throws Exception {
        JSONArray result = new JSONArray(request("/api/categories/", HttpMethod.GET, requestEntity(null, USER2_TOKEN)));
        return result.getJSONObject(0).getInt("id");
    }
}
