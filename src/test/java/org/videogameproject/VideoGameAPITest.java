package org.videogameproject;

import io.restassured.response.Response;
import javafx.scene.layout.Priority;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class VideoGameAPITest {

    @Test(priority = 1)
    public void test_getAllVideoGames()
    {
        given()
        .when()
        .get("http://localhost:8080/app/videogames")
        .then()
            .statusCode(200);
    }

    @Test(priority = 2)
    public void test_addNewVideoGame()
    {
        HashMap data = new HashMap();
        data.put("id", "101");
        data.put("name", "Abju");
        data.put("releaseDate", "2021-03-23T19:58:43.509Z");
        data.put("reviewScore", "5");
        data.put("category", "Adventure");
        data.put("rating", "Universal");

        Response res =

            given()
                .contentType("application/json")
                .body(data)
            .when()
                .post("http://localhost:8080/app/videogames")
            .then()
                .statusCode(200)
                .log().body()
                .extract().response();
        String jsonString = res.asString();
        Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);

    }

    @Test(priority = 3)
    public void test_getVideoGame()
    {
        given()
        .when()
            .get("http://localhost:8080/app/videogames/101")
        .then()
            .statusCode(200)
            .body("videoGame.id", equalTo("101"))
            .body("videoGame.name", equalTo("Abju"));
    }

    @Test(priority = 4)
    public void test_updateVideoGame()
    {
        HashMap data = new HashMap();
        data.put("id", "101");
        data.put("name", "Daddy");
        data.put("releaseDate", "2021-03-23T19:58:43.509Z");
        data.put("reviewScore", "5");
        data.put("category", "Family");
        data.put("rating", "Kids");

        given()
            .contentType("application/json")
            .body(data)
        .when()
            .put("http://localhost:8080/app/videogames/101")
        .then()
            .statusCode(200)
            .log().body()
            .body("videoGame.id", equalTo("101"))
            .body("videoGame.name", equalTo("Daddy"));
    }


    @Test(priority = 5)
    public void test_deleteVideoGame()
    {
        Response res =
            given()
            .when()
                .delete("http://localhost:8080/app/videogames/101")
            .then()
                .statusCode(200)
                .log().body()
                .extract().response();

        String jsonString = res.asString();
        Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
    }
}
