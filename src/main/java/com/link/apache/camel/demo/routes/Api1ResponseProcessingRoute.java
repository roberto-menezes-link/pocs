package com.link.apache.camel.demo.routes;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.link.apache.camel.demo.model.Customer;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Api1ResponseProcessingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:processApi1Response")
                .log("Processing response from API 1...")
                .process(exchange -> {
                    String api1Response = exchange.getIn().getBody(String.class);
                    JsonMapper mapper = new JsonMapper();
                    Customer customer = mapper.readValue(api1Response, (Customer.class));
                    customer.setBalance(new BigDecimal(50));
                    exchange.getIn().setBody(customer);
                    exchange.getIn().setHeader("API1Result", customer);
                })
                .log("Transformed data for API 2: ${body}")
                .to("direct:callApi2");
    }
}