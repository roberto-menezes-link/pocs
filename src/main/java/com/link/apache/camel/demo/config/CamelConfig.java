package com.link.apache.camel.demo.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelConfig extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        getContext().getGlobalOptions().put("CamelHttpConnectionTimeout", "1000");
        getContext().getGlobalOptions().put("CamelHttpSocketTimeout", "1000");
        getContext().getGlobalOptions().put("CamelHttpResponseTimeout", "1000");
    }
}

