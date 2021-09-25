import io.restassured.specification.RequestSpecification;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class APIRequests {

    @Test
    public void GetAllPosts() {

        //Hit URL
        baseURI = "https://jsonplaceholder.typicode.com/posts";
        Response response = given().get();

        //Assert Status code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        System.out.println("Status Code for GetAllPosts is " + response.getStatusCode());

        //Assert Response Body
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("\"userId\": 1"));
        Assert.assertTrue(responseBody.toLowerCase().contains("title"));
        Assert.assertTrue(responseBody.toLowerCase().contains("body"));
    }

    @Test
    public void GetPostByID() {

        //Hit URL
        baseURI = "https://jsonplaceholder.typicode.com/posts/4";
        Response response = given().get();

        //Assert Status code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        System.out.println("Status Code for GetPostByID is  " + response.getStatusCode());

        //Assert Response Body
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("\"userId\": 1"));
        Assert.assertTrue(responseBody.contains("\"id\": 4"));
        Assert.assertTrue(responseBody.toLowerCase().contains("title"));
        Assert.assertEquals(responseBody.toLowerCase().contains("body"), true);
    }

    @Test
    public void CreatePost() {

        //Hit URL
        RestAssured.baseURI ="https://jsonplaceholder.typicode.com/posts";
        RequestSpecification request = RestAssured.given();

        //Send parameters
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "foo");
        requestParams.put("body", "bar");
        requestParams.put("userId", "1");

        // Add a header stating the Request body is a JSON
        request.header("Content-Type", "application/json");

        // Add the Json to the body of the request
        request.body(requestParams.toJSONString());

        // Post the request
        Response response = request.post();

        //Assert Status code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201);
        System.out.println("Status Code for CreatePost is  " + response.getStatusCode());

        //Assert Response Body
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.toLowerCase().contains("foo"));
        Assert.assertTrue(responseBody.toLowerCase().contains("bar"));
    }

    @Test
    //TC: Can't accept sending keys with Null value
    public void ErrorCase1(){
        //Hit URL
        RestAssured.baseURI ="https://jsonplaceholder.typicode.com/posts";
        RequestSpecification request = RestAssured.given();

        //Send parameters with Null Values
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "");
        requestParams.put("body", "");

        // Add a header stating the Request body is a JSON
        request.header("Content-Type", "application/json");

        // Add the Json to the body of the request
        request.body(requestParams.toJSONString());

        // Post the request
        Response response = request.post();

        //Assert Status code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 406);
        System.out.println("Status Code for CreatePost is  " + response.getStatusCode());
    }

    @Test
    //TC:Can't accept posting with user id doesn't exist
    public void ErrorCase2(){
        //Hit URL
        RestAssured.baseURI ="https://jsonplaceholder.typicode.com/posts";
        RequestSpecification request = RestAssured.given();

        //Send parameters with Null Values
        JSONObject requestParams = new JSONObject();
        requestParams.put("userId", "10000000009");


        // Add a header stating the Request body is a JSON
        request.header("Content-Type", "application/json");

        // Add the Json to the body of the request
        request.body(requestParams.toJSONString());

        // Post the request
        Response response = request.post();

        //Assert Status code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 405);
        System.out.println("Status Code for CreatePost is  " + response.getStatusCode());
    }

}