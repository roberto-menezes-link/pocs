package com.link.apache.camel.demo.routes;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.link.apache.camel.demo.model.Customer;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.apache.camel.component.rest.RestConstants.HTTP_METHOD;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Component
public class RestApi2Route extends RouteBuilder {

    @Value("${rest.api2}")
    private String restApi2;

    @Override
    public void configure() throws Exception {
        from("direct:callApi2")
                .log("Calling API 2 with transformed data: ${body}")
                .setHeader(HTTP_METHOD, constant("POST"))
                .setHeader(CONTENT_TYPE, constant("application/json"))
                .process(exchange -> {
                    String transformedData = exchange.getIn().getBody(String.class);
                    JsonMapper mapper = new JsonMapper();
                    Customer customer = mapper.readValue(transformedData, (Customer.class));
                    customer.setBalance(new BigDecimal(150));
                    String jsonString = mapper.writeValueAsString(customer);
                    exchange.getIn().setBody(jsonString);
                })
                .to(restApi2)
                .log("Response from API 2: ${body}")
                .to("direct:sendToActiveMQ");
    }
}