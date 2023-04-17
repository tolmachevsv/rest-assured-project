package com.tolmachevsv.filters;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomLogFilter {

    private static final AllureRestAssured FILTER = new AllureRestAssured();

    private CustomLogFilter() {
    }

    public static CustomLogFilter customLogFilter() {
        return InitLogFilter.logFilter;
    }

    public AllureRestAssured withCustomTemplates() {
        FILTER.setRequestTemplate("tpl/request.ftl");
        FILTER.setResponseTemplate("tpl/response.ftl");
        return FILTER;

    }

    private static class InitLogFilter {
        private static final CustomLogFilter logFilter = new CustomLogFilter();
    }
}