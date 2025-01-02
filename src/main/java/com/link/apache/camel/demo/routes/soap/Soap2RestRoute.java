package com.link.apache.camel.demo.routes.soap;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.link.apache.camel.demo.model.Customer;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Soap2RestRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:soap2rest")
            .routeId("soap2rest")
            .removeHeaders("*")
            .process(exchange -> exchange.getIn().setBody(null))
            .to("https://linkmockapi.free.beeceptor.com/customers")
            .log("body from REST: ${body}")
            .process(exchange -> {
                    String api1Response = exchange.getIn().getBody(String.class);
                    JsonMapper mapper = new JsonMapper();
                    Customer customer = mapper.readValue(api1Response, (Customer.class));

                    exchange.getIn().setBody(customer.getName() + "-Rest");
                });
    }
}
