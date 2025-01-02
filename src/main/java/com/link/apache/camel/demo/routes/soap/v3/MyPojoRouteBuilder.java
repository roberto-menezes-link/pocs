package com.link.apache.camel.demo.routes.soap.v3;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * This class demonstrate how to expose a SOAP endpoint starting from java classes
 */
@Component
public class MyPojoRouteBuilder extends RouteBuilder {

    @Bean
    CxfEndpoint contact() {
        CxfEndpoint contactEndpoint = new CxfEndpoint();
        contactEndpoint.setServiceClass(ContactService.class);
        contactEndpoint.setAddress("/contact");

        return contactEndpoint;
    }

    @Override
    public void configure() throws Exception {
        from("cxf:bean:contact")
            .routeId("contact")
            .recipientList(simple("bean:inMemoryContactService?method=${header.operationName}"));
    }
}