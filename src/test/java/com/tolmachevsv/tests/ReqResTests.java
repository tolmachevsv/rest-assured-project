package com.tolmachevsv.tests;

import com.tolmachevsv.lombok.ResourceData;
import com.tolmachevsv.specs.Specs;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqResTests {

    @Test
    public void postNewUser() {
        Specs.request
                .body("{ \"name\": \"neo\", \"job\": \"chosen\" }")
                .when()
                .post("/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", is("neo"));
    }

    @Test
    public void updateNewUser() {
        Specs.request
                .body(" { \"name\": \"Smith\", \"job\": \"agent\" } ")
                .when()
                .put("/users/278")
                .then()
                .statusCode(200)
                .extract()
                .asString();
    }

    @Test
    public void SingleUserNotFound() {
        Specs.request
                .get("/users/278")
                .then()
                .statusCode(404)
                .extract().asString();
    }

    @Test
    public void getUsersWith12Entries() {
        Specs.request
                .when()
                .get("/users?per_page=12")
                .then()
                .body("per_page", is(12))
                .statusCode(200)
                .extract().asString();
    }

    @Test
    public void GetSingleResource() {
        ResourceData resource = Specs.request
                .when()
                .get("/unknown/2")
                .then()
                .body("data.name", is("fuchsia rose"))
                .log().body()
                .statusCode(200)
                .extract().as(ResourceData.class);
        assertEquals(2001, resource.getResource().getYear());
    }

    @Test
    public void GetResourcesWithGroovy() {
        Specs.request
                .when()
                .get("/unknown")
                .then()
                .log().body()
                .body("data.findAll{it.year =~/[0-9]{4}/}.year.flatten()", hasItem(2005));
    }
}
