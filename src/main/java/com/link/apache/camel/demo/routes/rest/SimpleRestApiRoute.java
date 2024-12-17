package com.link.apache.camel.demo.routes.rest;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleRestApiRoute extends RouteBuilder {

    @Value("${rest.api2}")
    private String restApi2;

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

        from("direct:rest2rest")
            .routeId("rest2rest")
            .log(LoggingLevel.DEBUG, "Starting process")
            .log("Calling API 1...")
            .to("https://linkmockapi.free.beeceptor.com/customers")
            .log("Response from API 1: ${body}")
            .process(new Api1ResponseProcessor())
            .log("Calling API 2 with transformed data: ${body}")
            .to(restApi2)
            .log("Response from API 2: ${body}")
            .process(new Api2ResponseProcessor())
            .log("Sending message to ActiveMQ queue...")
            .log("Message ${body}")
            .to("activemq:queue:mainQueue")
            .log("Message sent to ActiveMQ successfully");

    }
}