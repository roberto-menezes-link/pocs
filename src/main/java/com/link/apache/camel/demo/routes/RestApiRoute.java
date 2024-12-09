package com.link.apache.camel.demo.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestApiRoute extends RouteBuilder {

    @Value("${rest.api1}")
    private String restApi1;

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .maximumRedeliveries(3)
                .redeliveryDelay(1000)
                .handled(true)
                .log("An error occurred: ${exception.message}")
                .setHeader("errorMessage", simple("${exception.message}"))
                .setBody(simple("${exception.message}"))
                .to("activemq:queue:errorMessageQueue")
                .log("Sent to DLQ");

        from("direct:callApi1")
                .log("Calling API 1...")
                .to(restApi1)
                .log("Response from API 1: ${body}")
                .to("direct:processApi1Response");

    }
}