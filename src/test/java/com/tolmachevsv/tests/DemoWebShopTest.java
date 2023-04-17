package com.tolmachevsv.tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.tolmachevsv.filters.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class DemoWebShopTest {

    @Test
    public void addToCartTest() {
        step("Get cookie by api to set it for browser", () -> {
            String addingCookie =
                    given().
                            filter(customLogFilter().withCustomTemplates()).
                            contentType("application/json; charset=utf-8").
                            body("").
                            cookie("Nop.customer", "df01a09c-d858-4781-a620-52c3d7aecf06").
                            when().
                            log().uri().
                            log().body().
                            get("https://demowebshop.tricentis.com").
                            then().
                            statusCode(200).
                            extract().
                            cookie("NOP.CUSTOMER");

            step("Open minimal content for setting cookie", () ->
            open("https://demowebshop.tricentis.com/Themes/DefaultClean/Content/images/logo.png"));

            step("Set cookie for browser", () ->
            getWebDriver().manage().addCookie(
                    new Cookie("NOP.CUSTOMER", addingCookie)));
        });

        step("Open demowebshop's main page and check if quantity of items equals 0", () -> {
            open("https://demowebshop.tricentis.com");
            $(".cart-qty").shouldHave(text("(0)"));
        });

        step("Add item in the cart and check if counter increases by 1", () -> {
        given().
                contentType("application/json; charset=utf-8\"").
                body("").
                cookie("Nop.customer", "df01a09c-d858-4781-a620-52c3d7aecf06").
                when().
                post("https://demowebshop.tricentis.com/addproducttocart/catalog/31/1/1").
                then().
                body("updatetopcartsectionhtml", is("(1)")).
                statusCode(200).
                extract().response();
        });

        step("Remove items from cart for multiple test runs", () -> {
            $(".cart-label").click();
            $(byName("removefromcart")).click();
            $(byValue("Update shopping cart")).click();
        });
    }
}
