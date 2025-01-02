package com.link.apache.camel.demo.routes.rest;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SpeedRunRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:speedRun")
            .routeId("speedRun")
            .process(exchange -> exchange.setProperty("startTime", System.currentTimeMillis()))
            .log(LoggingLevel.DEBUG, "Starting process")
            .log("Calling API 1...")
            .to("https://linkmockapi.free.beeceptor.com/customers")
            .log("Response from API 1: ${body}")
            .log("Calling API 2 with transformed data: ${body}")
            .to("https://linkmockapi.free.beeceptor.com/payment")
            .log("Response from API 2: ${body}")
            .process(exchange -> {
                    long endTime = System.currentTimeMillis();
                    long startTime = exchange.getProperty("startTime", Long.class);
                    long processingTime = endTime - startTime;
                    exchange.getMessage().setBody("Processing time: " + processingTime + "ms");
                })
            .log("${body}");
    }
}