package edu.agh.pl.mobile;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by JaruuS on 2017-05-30.
 */

public class RequestHandler {

    String login(String host,String login,String password) throws IOException {
        HttpURLConnection urlConnection;

        URL url = new URL("http://"+host+":8080/login?username="+login+"&password="+password);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        String response;
        if(urlConnection.getResponseCode()==200){
            response = urlConnection.getHeaderField("X-AUTH-TOKEN");
        } else {
            response = "Login failed!!!";
        }
        urlConnection.disconnect();
        return response;
    }
    String executeRequest(URL url,String xAuthToken){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJsonStr;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("X-AUTH-TOKEN", xAuthToken);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            forecastJsonStr = buffer.toString();
            return forecastJsonStr;
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            return null;

        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
    }
    URL getCategories(String host) throws MalformedURLException {
        return new URL("http://"+host+":8080/api/categories");
    }
    URL getAllResources(String host) throws MalformedURLException {
        return new URL("http://"+host+":8080/api/resources");
    }
    URL getCategoryResources(String host, String categoryId) throws MalformedURLException {
        return new URL("http://"+host+":8080/api/categories/"+categoryId+"/resources");
    }
    URL getResource(String host, String resourceId) throws MalformedURLException {
        return new URL("http://"+host+":8080/api/resources/"+resourceId);
    }
}
