package com.link.apache.camel.demo.routes.soap.v1;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * This class demonstrate how to expose a SOAP endpoint starting from java classes
 */
@Component
public class DynamicRouter extends RouteBuilder {

    @Bean
    CxfEndpoint dynamicRouterEndpoint() {
        CxfEndpoint contactEndpoint = new CxfEndpoint();
        contactEndpoint.setServiceClass(DynamicSoapService.class);
        contactEndpoint.setAddress("/routes");

        return contactEndpoint;
    }

    @Override
    public void configure() {
        from("cxf:bean:dynamicRouterEndpoint")
            .routeId("dynamicRouterEndpoint")
            .recipientList(simple("direct:${header.routeName}"))
            .log("Response ${body}");
    }
}