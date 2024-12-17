package com.link.apache.camel.demo.routes.quartz;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class QuartzLoggingRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("quartz://myGroup/myTimer?cron=0/10+*+*+*+*+?")
            .routeId("quartzLoggingRoute")
            .process(exchange -> {
                    String currentTime = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    exchange.getMessage().setBody("Current Time: " + currentTime);
                })
            .log("${body}");
    }
}
