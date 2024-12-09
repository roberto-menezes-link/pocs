package com.link.apache.camel.demo.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleVersionCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .log("Starting the process")
                //.to("http://api1.example.com/resource?httpMethod=GET")
                .log("First API response: ${body}")
                //.to("http://api2.example.com/resource?httpMethod=GET")
                .log("Second API response: ${body}")
                //.to("activemq:queue:myQueue")
                .log("Message sent to ActiveMQ queue");
    }
}