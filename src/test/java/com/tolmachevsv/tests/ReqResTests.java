package com.tolmachevsv.tests;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class ReqResTests {

    @Test
    public void postNewUser() {
         ValidatableResponse response =
        given()
//                .contentType("application/json")
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"neo\", \"job\": \"chosen\" }")
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is("neo"));
        System.out.println(response.extract().asString());
    }

    @Test
    public void updateNewUser() {
        String response = given()
                .contentType(ContentType.JSON)
                .body(" { \"name\": \"Smith\", \"job\": \"agent\" } ")
                .when()
                .put("https://reqres.in/api/users/278")
                .then()
                .statusCode(200)
                .extract()
                .asString();
        System.out.println(response);

        String r = get("https://reqres.in/api/users/278").then().extract().asString();
        // why 278 id is empty?
        System.out.println(r);
    }

    @Test
    public void SingleUserNotFound() {
        String response1 = get("https://reqres.in/api/users/278")
                .then()
                .statusCode(404)
                .extract().asString();
        System.out.println(response1);
    }

    @Test
    public void getUsersWith12Entries() {
        String response2 = get("https://reqres.in/api/users?per_page=12")
                .then()
                .body("per_page", is(12))
                .statusCode(200)
                .extract().asString();
        System.out.println(response2);
    }

    @Test
    public void GetSingleResource() {
        String response3 = get("https://reqres.in/api/unknown/2")
                .then()
                .body("data.name", is("fuchsia rose"))
                .statusCode(200)
                .extract().asString();
        System.out.println(response3);
    }
}
