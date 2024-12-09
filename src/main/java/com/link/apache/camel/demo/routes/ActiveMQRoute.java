package com.link.apache.camel.demo.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:sendToActiveMQ")
                .log("Sending message to ActiveMQ queue...")
                .log("Message ${body}")
                .to("activemq:queue:mainQueue")
                .log("Message sent to ActiveMQ successfully");
    }
}